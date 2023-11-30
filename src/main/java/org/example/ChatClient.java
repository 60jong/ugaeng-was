package org.example;

import org.example.exception.UgException;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class ChatClient {

    private final Socket socket;
    private final SocketAddress serverAddress;
    private BufferedReader in;
    private BufferedWriter out;

    public ChatClient(String host, int port) {
        socket = new Socket();
        serverAddress = new InetSocketAddress(host, port);
    }

    public void connect() throws IOException {
        socket.connect(serverAddress);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void chat() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (socket.isConnected()) {
            if (receiveMessage().equals("BYE")) {
                socket.close();
                break;
            }

            sendMessage(scanner.next());
        }

        close();
    }

    private void close() throws IOException {
        socket.close();
    }

    private String receiveMessage() throws IOException {
        String recv = in.readLine();

        System.out.println("server > " + recv);
        return recv;
    }

    private void sendMessage(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new UgException(e);
        }
    }
}
