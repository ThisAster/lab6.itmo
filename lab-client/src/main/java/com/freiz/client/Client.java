package com.freiz.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import com.freiz.common.util.InputManager;
import com.freiz.common.util.OutputManager;
import com.freiz.common.exception.InvalidRequestException;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static void main(String[] args) throws SocketException, UnknownHostException, InvalidRequestException {
        SocketAddress address;

        try {
            address = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Address and port should be passed in as arguments");
            e.printStackTrace();
            return;
        }

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(address);
            InputManager userInputManager = new InputManager(System.in);
            OutputManager outputManager = new OutputManager();
            ClientApp clientApp = new ClientApp(socket, userInputManager, outputManager);
            clientApp.sendThenRecieve();
        } catch (IOException e) {
            throw new RuntimeException("?????");
        }
    }
}

