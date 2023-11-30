package org.example;

import org.example.exception.UgException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {

    private static long clientSequence = 1l;

    private final ServerSocket socket;

    private BufferedReader in = null;
    private BufferedWriter out = null;

    public ChatServer(int port) {
        try {
            this.socket = new ServerSocket(port);
            System.out.println("server started");
        } catch (IOException e) {
            throw new UgException(e);
        }
    }

    public void run() throws IOException {
        while (true) {
            Connection con = acceptClient();

            chat(con);
        }
    }

    private Connection acceptClient() {
        Socket client = null;
        try {
            System.out.println("waiting client");
            client = socket.accept();

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            sendMessage("server > Hello client-" + clientSequence++);

        } catch (IOException e) {
            throw new UgException(e);
        }

        return new Connection(client);
    }

    private void chat(Connection con) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (con.alive()) {
            receiveMessage(con);

            String send = sendMessage(scanner.next());

            if (send.equals("BYE")) {
                con.close();
                break;
            }
        }
    }

    private void receiveMessage(Connection con) throws IOException {
        String recv = in.readLine();

        System.out.println("client > " + recv);
    }

    private String sendMessage(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();

            return message;
        } catch (IOException e) {
            throw new UgException(e);
        }
    }
}
