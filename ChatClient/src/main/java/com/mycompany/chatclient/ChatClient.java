package com.mycompany.chatclient;

public class ChatClient {

    public static void main(String[] args) {
        CClient client = new CClient();
        client.startConnection("127.0.0.1", 6666);
        client.sendMessage("Hello from the client!");
        client.stopConnection();
    }
}
