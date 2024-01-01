package org.example.socket.chat.live.client;

import org.example.socket.chat.live.ChatReceiver;

import java.net.Socket;

public class ClientChatReceiver extends ChatReceiver {

    public ClientChatReceiver(Socket socket) {
        super(socket);
    }

}
