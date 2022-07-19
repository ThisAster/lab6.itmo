package com.freiz.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.FileManager;
import com.freiz.common.util.HistoryManagerImpl;
import com.freiz.common.util.InputManager;
import com.freiz.common.util.JsonParser;
import com.freiz.common.util.OutputManager;

public class ServerApp {
    final int constanta = 16;
    private final int maxPacket = 1 << constanta - 1;
    private final int port;
    private final CollectionManager collectionManager = new CollectionManager();
    private final OutputManager outputManager = new OutputManager();
    private final InputManager inputManager = new InputManager(System.in);
    private final HistoryManagerImpl historyManagerImpl = new HistoryManagerImpl();
    private final FileManager fileManager;
    private final Map<String, AbstractCommand> commands;
    private final Logger logger;
    public ServerApp(int port, FileManager fileManager) throws IOException {
        this.port = port;
        this.fileManager = fileManager;
        this.logger = Logger.getLogger("log");
        File file = new File("server.log");
        FileHandler fh = new FileHandler(file.getAbsolutePath(), true);
        this.commands = createClientCommandsMap(outputManager, inputManager);
    }

    public void start() throws ClassNotFoundException, InvalidRequestException, NotMaxException, NotMinException {
        try (DatagramChannel server = DatagramChannel.open()) {
            InetSocketAddress iAddress = new InetSocketAddress(port);
            server.bind(iAddress);
            server.configureBlocking(false);
            logger.info("start work");
            listen(server);
            logger.info("wait connection");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(port);
            System.out.println("DatagramChannel fall");
        }
    }

    private void listen(DatagramChannel server) throws ClassNotFoundException, InvalidRequestException, NotMaxException, NotMinException {
        while (true) {
            try {
                acceptConsoleInput();
                ByteBuffer buffer = ByteBuffer.allocate(maxPacket);
                SocketAddress address = server.receive(buffer);

                if (address != null) {
                    buffer.flip();
                    ByteArrayInputStream in = new ByteArrayInputStream(buffer.array());
                    ObjectInputStream is = new ObjectInputStream(in);
                    Request request = (Request) is.readObject();
                    logger.info("receive request, deserialize request");
                    String commandMessage = (String) request.getCommandName();
                    if (commands.containsKey(commandMessage)) {
                        AbstractCommand commandExe = commands.get(commandMessage);
                        CommandResultDto commandResultDto = commandExe.execute(request, collectionManager, historyManagerImpl);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject(commandResultDto);
                        logger.info("seriaize result");
                        byte[] secondaryBuffer = baos.toByteArray();
                        ByteBuffer mainBuffer = ByteBuffer.wrap(secondaryBuffer);
                        server.send(mainBuffer, address);
                        logger.info("By server send data to client");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.severe("cant connection");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found");
                logger.info("class not found");
                return;
            }
        }
    }
    public boolean acceptConsoleInput() throws IOException {
        if (System.in.available() > 0) {
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            switch (command) {
                case "save":
                    fileManager.save(JsonParser.serialize(collectionManager.getSpaceMarinesCollection()));
                    logger.fine("successful save");
                    break;
                case "exit":
                    System.out.println("Shutting down");
                    logger.info("server down");
                    System.exit(0);
                    return true;
                default:
                    System.out.println("Unknown command. Available commands are: save, exit");
            }
        }
        return false;
    }
    public static Map<String, AbstractCommand> createClientCommandsMap(OutputManager outputManager, InputManager inputManager) {
        HashMap<String, AbstractCommand> commands = new HashMap<>();
        commands.put("add", new AddCommand(outputManager, inputManager));
        commands.put("add_if_max", new AddIfMaxCommand(inputManager, outputManager));
        commands.put("add_if_min", new AddIfMinCommand(inputManager, outputManager));
        commands.put("clear", new ClearCommand());
        commands.put("count_greater_than_heart_count", new CountGreaterThanHeartCountCommand());
        commands.put("help", new HelpCommand());
        commands.put("history", new HistoryCommand());
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        commands.put("update", new UpdateCommand(inputManager, outputManager));
        commands.put("count_less_than_melee_weapon", new CountLessThanMeleeWeaponMeleeWeaponCommand());
        commands.put("filter_by_weapon_type", new FilterByWeaponTypeCommand());
        commands.put("remove_by_id", new RemoveByIdCommand());
        return commands;
    }
}
