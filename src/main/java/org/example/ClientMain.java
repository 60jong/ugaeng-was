package org.example;

import org.example.exception.UgException;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {

        String host = ServerConst.HOST;
        int port = ServerConst.PORT;

        ChatClient client1 = new ChatClient(host, port);
        try {
            client1.connect();
        } catch (IOException e) {
            throw new UgException(e);
        }
        client1.chat();

        ChatClient client2 = new ChatClient(host, port);
        try {
            client2.connect();
        } catch (IOException e) {
            throw new UgException(e);
        }
        client2.chat();
    }
}
