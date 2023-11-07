package com.mycompany.chatclient;

public class ChatClient {

    public static void main(String[] args) {
        CClient client = new CClient();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        System.out.println(response);
        client.stopConnection();
    }
}
