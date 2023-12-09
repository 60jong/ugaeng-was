package org.example.socket.chat.live.server;

import org.example.socket.chat.Connection;
import org.example.socket.chat.wrapper.UgServerSocket;
import org.example.socket.chat.wrapper.UgSocket;
import org.example.socket.chat.wrapper.exception.UgException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class LiveChatServer {

    private static long clientSequence = 1l;
    private static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    private final UgServerSocket socket;

    public LiveChatServer(int port) {
        socket = new UgServerSocket(port);

        System.out.println("server started");
    }

    public void run() {
        do {
            Connection con = acceptClient();

            chat(con);
        } while (true);
    }

    private Connection acceptClient() {

        System.out.println("...Waiting client...");
        UgSocket client = socket.accept();
        System.out.println("Connected!!");

        Connection con = new Connection(client);
        sendMessage(con, "Hello client-" + clientSequence++);

        return con;
    }

    private void chat(Connection con) {

        ServerChatReceiver receiver = new ServerChatReceiver(con);
        receiver.start();

        while (con.alive()) {
            try
            {
                String send = sendMessage(con, consoleReader.readLine());

                if (send.equals("BYE")) {
                    con.close();
                    break;
                }
            }
            catch (IOException e)
            {
                System.out.println("write again...");
            }
        }
    }

    private String sendMessage(Connection con, String message) {
        try
        {
            BufferedWriter out = con.getOutputReader();
            out.write(messagePrefix() + message);
            out.newLine();
            out.flush();

            return message;
        }
        catch (IOException e)
        {
            throw new UgException(e);
        }
    }

    private String messagePrefix() {
        return String.format("%s > ", "server");
    }
}
