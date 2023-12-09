package org.example.socket.chat.live;

import org.example.socket.chat.ServerConst;
import org.example.socket.chat.live.client.LiveChatClient;

import static org.example.socket.chat.ServerConst.*;

public class LiveChatClientMain {
    public static void main(String[] args) {
        LiveChatClient client1 = new LiveChatClient("60jong", HOST, PORT);
        client1.connect();
        client1.chat();

        LiveChatClient client2 = new LiveChatClient("ugaeng", HOST, PORT);
        client2.connect();
        client2.chat();
    }
}
