package org.example.socket.chat.live.server;

import org.example.socket.chat.Connection;
import org.example.socket.chat.live.ChatReceiver;
import org.example.socket.chat.wrapper.UgSocket;
import org.example.socket.chat.wrapper.exception.UgException;

import java.io.IOException;

public class ServerChatReceiver extends ChatReceiver {

    public ServerChatReceiver(Connection con) {
        super(con);
    }

}
