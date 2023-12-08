package org.example.socket.chat.wrapper;

import org.example.socket.chat.wrapper.exception.UgException;

import java.io.IOException;
import java.net.ServerSocket;

public class UgServerSocket {


    private final ServerSocket socket;

    public UgServerSocket(int port) {
        try
        {
            this.socket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            throw new UgException("server socket open failed", e);
        }
    }

    public UgSocket accept() {
        try
        {
            return new UgSocket(socket.accept());
        }
        catch (IOException e)
        {
            throw new UgException("connection failed", e);
        }
    }
}
