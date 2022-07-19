package com.freiz.server;

import java.io.IOException;

import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.util.FileManager;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException, InvalidRequestException, NotMaxException, NotMinException {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("The port should be passed in as the first argument");
            e.printStackTrace();
            return;
        }
        FileManager fileManager = new FileManager("file.json");
        ServerApp serverApp = new ServerApp(port, fileManager);
        try {
            serverApp.start();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            return;
        }
    }
}
