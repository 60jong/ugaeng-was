package org.example.socket.chat;

import org.example.socket.chat.wrapper.UgSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Connection {

    private final UgSocket socket;

    public Connection(UgSocket socket) {
        this.socket = socket;
    }

    public boolean alive() {
        return socket.isConnected();
    }

    public void close() {
        socket.close();
    }

    public BufferedReader getInputReader() {
        return socket.getReader();
    }

    public BufferedWriter getOutputReader() {
        return socket.getWriter();
    }
}
