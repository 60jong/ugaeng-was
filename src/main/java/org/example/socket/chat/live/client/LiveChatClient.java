package org.example.socket.chat.live.client;

import org.example.socket.chat.live.ChatReceiver;
import org.example.socket.chat.live.ChatSender;
import org.example.socket.chat.wrapper.UgSocket;
import org.example.socket.chat.wrapper.exception.UgException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class LiveChatClient {

    private static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    private final UgSocket socket;
    private final SocketAddress serverAddress;
    private final String clientName;

    public LiveChatClient(String name, String host, int port) {
        clientName = name;
        serverAddress = new InetSocketAddress(host, port);
        socket = new UgSocket(new Socket());
    }

    public void connect() {
        socket.connect(serverAddress);
    }

    public void chat() {

        ChatReceiver receiver = new ClientChatReceiver(socket);
        receiver.start();

        while (socket.isConnected()) {
            try
            {
                sendMessage(consoleReader.readLine());
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        close();
    }

    private void close() {
        socket.close();
    }


    private void sendMessage(String message) {

        try
        {
            BufferedWriter out = socket.getWriter();

            out.write(messagePrefix() + message); out.newLine();
            out.flush();
        }
        catch (IOException e)
        {
            throw new UgException(e);
        }
    }

    private String messagePrefix() {
        return String.format("%s > " ,clientName);
    }
}
