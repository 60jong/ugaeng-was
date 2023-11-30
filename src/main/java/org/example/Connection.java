package org.example;

import org.example.exception.UgException;

import java.io.IOException;
import java.net.Socket;

public class Connection {

    private final Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public boolean alive() {
        return socket.isConnected();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new UgException(e);
        }
    }
}
