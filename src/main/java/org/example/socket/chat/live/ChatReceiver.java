package org.example.socket.chat.live;

import org.example.socket.chat.Connection;
import org.example.socket.chat.wrapper.UgSocket;
import org.example.socket.chat.wrapper.exception.UgException;

import java.io.IOException;

public abstract class ChatReceiver extends Thread {

    private final Connection con;

    public ChatReceiver(Connection con) {
        this.con = con;
    }

    public ChatReceiver(UgSocket socket) {
        this.con = new Connection(socket);
    }

    @Override
    public void run() {
        while (con.alive()) {
            try
            {
                System.out.println(con.getInputReader().readLine());
            }
            catch (IOException e)
            {
                throw new UgException(e);
            }
        }
    }
}
