package org.example;

import org.example.wrapper.exception.UgException;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {

        String host = ServerConst.HOST;
        int port = ServerConst.PORT;

        ChatClient client1 = new ChatClient(host, port);

        client1.connect();
        client1.chat();

        ChatClient client2 = new ChatClient(host, port);
        client2.connect();

        client2.chat();
    }
}
