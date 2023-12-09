package org.example.socket.chat.live.client;

import org.example.socket.chat.live.ChatReceiver;
import org.example.socket.chat.wrapper.UgSocket;

public class ClientChatReceiver extends ChatReceiver {

    public ClientChatReceiver(UgSocket socket) {
        super(socket);
    }

}
