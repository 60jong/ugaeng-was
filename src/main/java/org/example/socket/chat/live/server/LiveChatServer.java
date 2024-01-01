package org.example.socket.chat.live.server;

import org.example.socket.chat.wrapper.exception.UgException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.TreeSet;

public class LiveChatServer {

    private static long clientSequence = 1l;
    private static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    private final ServerSocket serverSocket;

    private  BufferedWriter writer;

    public LiveChatServer(int port) {
        serverSocket = openServerSocket(port);
        setServerTimeout(60);

        try {
            System.out.println(serverSocket.getReceiveBufferSize());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[server started]");
    }

    private ServerSocket openServerSocket(int port) {

        ServerSocket socket = null;
        try
        {
            socket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            throw new UgException("서버 소켓 생성에 실패했습니다.", e);
        }

        return socket;
    }

    private void setServerTimeout(int second) {
        try
        {
            serverSocket.setSoTimeout(second * 1000);
        }
        catch (SocketException e)
        {
            throw new UgException("닫힌 소켓입니다.", e);
        }
    }

    public void run() {
        do {
            Socket client = connectToClient();

            try
            {
                chat(client);
            }
            catch (Exception e)
            {
                System.out.println("...Connection ended...");
            }
        } while (true);
    }

    private Socket connectToClient() {
        // accept client
        Socket client = null;
        while (client == null) {
            client = acceptClient();
            try {
                System.out.println(client.getSendBufferSize());
                System.out.println(client.getReceiveBufferSize());
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }

        setWriter(client);

        // send welcome message to client
        sendWelcomeMessage();

        return client;
    }

    private void sendWelcomeMessage() {
        try
        {
            sendMessage("Hello_client-" + clientSequence++);
        }
        catch (IOException e)
        {
            System.out.println("sending welcome message failed");
        }
    }

    private void setWriter(Socket client) {
        try
        {
            this.writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        }
        catch (IOException e)
        {
            throw new UgException("클라이언트 소켓을 여는 과정에서 문제가 발생했습니다.", e);
        }
    }

    private Socket acceptClient() {

        System.out.println("...Waiting client...");

        Socket client = null;
        try 
        {
            client = serverSocket.accept();
        } 
        catch (IOException e) 
        {
            System.out.println("클라이언트 연결에 실패했습니다.");
        }

        System.out.println("Connected to [ IP : " + client.getInetAddress() + " ]");
        return client;
    }


    private void chat(Socket client) {

        Thread receiver = new ServerChatReceiver(client);
        receiver.start();

        while (client.isConnected()) {
            try
            {
                String send = sendMessage(consoleReader.readLine());

                if (send.equals("BYE")) {
                    client.close();
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

    private String messagePrefix() {
        return String.format("%s > ", "server");
    }
}
