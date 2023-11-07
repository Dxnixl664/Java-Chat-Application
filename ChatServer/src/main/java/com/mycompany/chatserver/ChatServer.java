package com.mycompany.chatserver;

public class ChatServer {

    public static void main(String[] args) {
        CServer server = new CServer();
        server.start(6666);
    }
}
