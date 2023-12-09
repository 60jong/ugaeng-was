package org.example.socket.chat;

import java.io.IOException;

public class ServerApplication {
    public static void main(String[] args) throws IOException {

        ChatServer server = new ChatServer(ServerConst.PORT);
        server.run();
    }
}