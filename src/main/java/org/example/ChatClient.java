package org.example;

import org.example.wrapper.UgSocket;
import org.example.wrapper.exception.UgException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class ChatClient {

    private static final Scanner scanner = new Scanner(System.in);

    private final UgSocket socket;
    private final SocketAddress serverAddress;

    public ChatClient(String host, int port) {
        serverAddress = new InetSocketAddress(host, port);
        socket = new UgSocket(new Socket());
    }

    public void connect() {
        socket.connect(serverAddress);
    }

    public void chat() {

        while (socket.isConnected()) {
            if (receiveMessage().equals("BYE")) {
                close();
                break;
            }

            System.out.print("client > ");
            sendMessage(scanner.nextLine());
        }
        close();
    }

    private void close() {
        socket.close();
    }

    private String receiveMessage() {
        try
        {
            String recv = socket.getReader()
                                .readLine();

            System.out.println("server > " + recv);
            return recv;
        }
        catch (IOException e)
        {
            throw new UgException(e);
        }
    }

    private void sendMessage(String message) {

        try
        {
            BufferedWriter out = socket.getWriter();

            out.write(message); out.newLine();
            out.flush();
        }
        catch (IOException e)
        {
            throw new UgException(e);
        }
    }
}
