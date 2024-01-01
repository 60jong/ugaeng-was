package org.example.socket.chat.live;

import org.example.socket.chat.live.client.LiveChatClient;
import org.example.socket.chat.wrapper.exception.UgException;

import static org.example.socket.chat.ServerConst.*;

public class LiveChatClientMain {
    public static void main(String[] args) {

        try
        {
            LiveChatClient client1 = new LiveChatClient("60jong", HOST, PORT);
            client1.connect();
            client1.chat();
        }
         catch (UgException uge)
        {
            System.out.println("통신에 문제가 생겼습니다.");
        }
    }
}
