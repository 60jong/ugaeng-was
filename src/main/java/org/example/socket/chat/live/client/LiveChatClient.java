package org.example.socket.chat.live.client;

import org.example.socket.chat.live.ChatReceiver;
import org.example.socket.chat.live.server.ServerChatReceiver;
import org.example.socket.chat.wrapper.UgSocket;
import org.example.socket.chat.wrapper.exception.UgException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class LiveChatClient {

    private static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    private final Socket socket;
    private final SocketAddress serverAddress;
    private final String clientName;

    private BufferedWriter writer;

    public LiveChatClient(String name, String host, int port) {
        clientName = name;
        serverAddress = new InetSocketAddress(host, port);
        socket = new Socket();
    }

    public void connect() {
        try
        {
            socket.connect(serverAddress);

            setWriter();
        }
        catch (IOException e)
        {
            throw new UgException("서버와의 연결 중 문제가 발생했습니다.", e);
        }
    }

    private void setWriter() {
        try
        {
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e)
        {
            throw new UgException("서버 소켓을 여는 과정에서 문제가 발생했습니다.", e);
        }
    }

    public void chat() {

        Thread receiver = new ClientChatReceiver(socket);
        receiver.start();

        while (socket.isConnected()) {
            try
            {
                String send = sendMessage(consoleReader.readLine());

                if (send.equals("BYE")) {
                    close();
                    return;
                }
            }
            catch (IOException e)
            {
                System.out.println("write again...");
            }
        }
    }

    private String sendMessage(String message) throws IOException {
        writer.write(messagePrefix() + message); writer.newLine();
        writer.flush();

        return message;
    }

    private void close() {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            throw new UgException("소켓를 닫는 과정에서 문제가 발생했습니다.", e);
        }
    }

    private String messagePrefix() {
        return String.format("%s > " ,clientName);
    }
}
