package org.example.socket.chat.wrapper.exception;

public class UgException extends RuntimeException {

    public UgException(Exception exception) {
        super(exception);
    }

    public UgException(String message, Exception exception) {
        super(message, exception);
    }
}
