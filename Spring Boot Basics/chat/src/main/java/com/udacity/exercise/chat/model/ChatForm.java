package com.udacity.exercise.chat.model;

public class ChatForm {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private String message;
    private String mode;

    @Override
    public String toString() {
        return "ChatForm{" +
                ", message='" + message + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
