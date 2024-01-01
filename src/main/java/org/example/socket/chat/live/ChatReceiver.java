package org.example.socket.chat.live;

import org.example.socket.chat.wrapper.exception.UgException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public abstract class ChatReceiver extends Thread {

    private final Socket socket;
    private final BufferedReader reader;

    public ChatReceiver(Socket socket) {
        this.socket = socket;
        this.reader = createBufferedReader();
    }

    private BufferedReader createBufferedReader() {
        try
        {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e)
        {
            throw new UgException("상대 소켓과의 연결 상태가 올바르지 않습니다.", e);
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try
            {
                String receive = reader.readLine();

                String content = receive.split(" ")[2];

                System.out.println(receive);

                if (content.equals("BYE")) {
                    socket.close();
                    break;
                }
            }
            catch (SocketException se)
            {
                break;
            }
            catch (IOException e)
            {
                throw new UgException(e);
            }
        }

        System.out.println("수신 쓰레드를 종료합니다.");
    }
}
