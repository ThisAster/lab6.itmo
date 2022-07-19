package com.freiz.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.freiz.common.commands.AbstractCommand;
import com.freiz.common.commands.AddCommand;
import com.freiz.common.commands.AddIfMaxCommand;
import com.freiz.common.commands.AddIfMinCommand;
import com.freiz.common.commands.ClearCommand;
import com.freiz.common.commands.CountGreaterThanHeartCountCommand;
import com.freiz.common.commands.CountLessThanMeleeWeaponMeleeWeaponCommand;
import com.freiz.common.commands.FilterByWeaponTypeCommand;
import com.freiz.common.commands.HelpCommand;
import com.freiz.common.commands.HistoryCommand;
import com.freiz.common.commands.InfoCommand;
import com.freiz.common.commands.RemoveByIdCommand;
import com.freiz.common.commands.ShowCommand;
import com.freiz.common.commands.UpdateCommand;
import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.network.Request;
import com.freiz.common.util.InputManager;
import com.freiz.common.util.OutputManager;

public class ClientApp {
    private final DatagramSocket client;
    private final InputManager inputManager;
    private final Map<String, AbstractCommand> commandsClientMap = new HashMap<>();
    private final OutputManager outputManager;
    private final Logger logger;
    public ClientApp(DatagramSocket client, InputManager inputManager, OutputManager outputManager) throws IOException {
        this.client = client;
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.logger = Logger.getLogger("log");
        File lf = new File("client.log");
        FileHandler fh = new FileHandler(lf.getAbsolutePath(), true);
        logger.addHandler(fh);
        commandsClientMap.put("add", new AddCommand(this.outputManager, this.inputManager));
        commandsClientMap.put("add_if_max", new AddIfMaxCommand(this.inputManager, this.outputManager));
        commandsClientMap.put("add_if_min", new AddIfMinCommand(this.inputManager, this.outputManager));
        commandsClientMap.put("clear", new ClearCommand());
        commandsClientMap.put("count_greater_than_heart_count", new CountGreaterThanHeartCountCommand());
        commandsClientMap.put("help", new HelpCommand());
        commandsClientMap.put("history", new HistoryCommand());
        commandsClientMap.put("info", new InfoCommand());
        commandsClientMap.put("show", new ShowCommand());
        commandsClientMap.put("update", new UpdateCommand(this.inputManager, this.outputManager));
        commandsClientMap.put("count_less_than_melee_weapon", new CountLessThanMeleeWeaponMeleeWeaponCommand());
        commandsClientMap.put("filter_by_weapon_type", new FilterByWeaponTypeCommand());
        commandsClientMap.put("remove_by_id", new RemoveByIdCommand());
    }
    public boolean exeFile(String input, InputManager inputManager1) {
        if (input.startsWith("execute_script")) {
            String[] args = input.split(" ");
            if (args.length == 1 || args[1] == "") {
                logger.info("bad args");
                return true;
            }
            File fileName = new File(args[1]);
            if (fileName.canRead()) {
                try {
                    inputManager1.connectToFile(fileName);
                    logger.fine("successful executing");
                    return true;
                } catch (UnsupportedOperationException | IOException e) {
                    logger.severe("cant connect");
                    return true;
                }
            }

        } else if (input.startsWith("exit")) {
            logger.fine("successful exit");
            System.exit(-1);
            }
        return false;
    }
    public Object deserialize(byte[] data) {
        try {
        ByteArrayInputStream bos = new ByteArrayInputStream(data);
        ObjectInputStream objectOutputStream = new ObjectInputStream(bos);
        return objectOutputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendThenRecieve() throws InvalidRequestException {
        InputManager scanner = new InputManager(System.in);
        while (true) {
            try {
                String messageToSend = scanner.nextLine();
                String[] args = messageToSend.split(" ");
                if (exeFile(messageToSend, scanner)) {
                    continue;
                }
                if (commandsClientMap.containsKey(args[0])) {
                    AbstractCommand someCommandSend = commandsClientMap.get(args[0]);
                    Request request = someCommandSend.packageRequest(args);
                    logger.info("created request, that in packed arguments by command");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(request);
                    DatagramPacket packet = new DatagramPacket(baos.toByteArray(), baos.size());
                    client.send(packet);
                    logger.info("By client send data to server");
                    final int constanta = 16;
                    final int maxPacket = 1 << constanta - 1;
                    byte[] secondaryBuffer = new byte[maxPacket];
                    DatagramPacket packetFromServer = new DatagramPacket(secondaryBuffer, maxPacket);
                    logger.info("created packet, that will send to server");
                    client.receive(packetFromServer);
                    logger.info("By client was received data from server");
                    secondaryBuffer = packetFromServer.getData();
                    Object data = deserialize(secondaryBuffer);
                    logger.info("deserialize data");
                    CommandResultDto commandResultDto = new CommandResultDto(data.toString());
                    System.out.println(commandResultDto.toString());
                } else {
                    System.out.println("Class not found, please write a help to see look the available command");
                }
            } catch (IOException | InvalidRequestException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

