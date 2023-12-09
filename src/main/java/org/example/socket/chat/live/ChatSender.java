package org.example.socket.chat.live;

import org.example.socket.chat.wrapper.UgSocket;
import org.example.socket.chat.wrapper.exception.UgException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class ChatSender extends Thread {

    private final UgSocket socket;
    private final BufferedReader consoleReader;

    public ChatSender(UgSocket socket) {
        this.socket = socket;
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try
            {
                BufferedWriter out = socket.getWriter();
                out.write(consoleReader.readLine()); out.newLine();
                out.flush();

            }
            catch (IOException e)
            {
                throw new UgException(e);
            }
        }
    }
}
