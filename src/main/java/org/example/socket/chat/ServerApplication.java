package org.example.socket.chat;


public class ServerApplication {
    public static void main(String[] args) {

        ChatServer server = new ChatServer(ServerConst.PORT);
        server.run();
    }
}