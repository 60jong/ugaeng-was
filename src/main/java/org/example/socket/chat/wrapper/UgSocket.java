package org.example.socket.chat.wrapper;

import org.example.socket.chat.wrapper.exception.UgException;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class UgSocket {

    private final Socket socket;

    public UgSocket(Socket socket) {
        this.socket = socket;
    }

    public void close() {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            throw new UgException("client socket close failed", e);
        }
    }

    public BufferedReader getReader() {
        try
        {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e)
        {
            throw new UgException(e);
        }
    }

    public BufferedWriter getWriter() {
        try
        {
            return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e)
        {
            throw new UgException(e);
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void connect(SocketAddress serverAddress) {
        try
        {
            socket.connect(serverAddress);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
