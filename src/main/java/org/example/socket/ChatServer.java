package org.example.socket;

import org.example.socket.chat.wrapper.UgServerSocket;
import org.example.socket.chat.wrapper.UgSocket;
import org.example.socket.chat.wrapper.exception.UgException;
import java.io.*;
import java.util.Scanner;

public class ChatServer {

    private static long clientSequence = 1l;
    private static final Scanner scanner = new Scanner(System.in);

    private final UgServerSocket socket;

    public ChatServer(int port) {
        socket = new UgServerSocket(port);

        System.out.println("server started");
    }

    public void run() {
        while (true) {
            Connection con = acceptClient();

            chat(con);
        }
    }

    private Connection acceptClient() {

        System.out.println("Waiting client...");
        UgSocket client = socket.accept();
        System.out.println("Connected!!");

        Connection con = new Connection(client);
        sendMessage(con, "Hello client-" + clientSequence++);

        return con;
    }

    private void chat(Connection con) {

        while (con.alive()) {
            receiveMessage(con);

            System.out.print("server > ");
            String send = sendMessage(con, scanner.nextLine());

            if (send.equals("BYE")) {
                con.close();
                break;
            }
        }
    }

    private void receiveMessage(Connection con) {
        try
        {
            String recv = con.getInputReader()
                             .readLine();

            System.out.println("client > " + recv);
        }
        catch (IOException e)
        {
            throw new UgException(e);
        }
    }

    private String sendMessage(Connection con, String message) {
        try
        {
            BufferedWriter out = con.getOutputReader();
            out.write(message);
            out.newLine();
            out.flush();

            return message;
        }
        catch (IOException e)
        {
            throw new UgException(e);
        }
    }
}
