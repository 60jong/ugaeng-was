package org.example.socket.chat.live;

import org.example.socket.chat.ServerConst;
import org.example.socket.chat.live.server.LiveChatServer;
import org.example.socket.chat.wrapper.exception.UgException;

public class LiveChatServerMain {

    public static void main(String[] args) {


        LiveChatServer server = new LiveChatServer(ServerConst.PORT);
        server.run();


    }
}
