package com.mycompany.chatserver;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CServer {
    private ServerSocket serverSocket;
    private Set<ClientHandler> clientHandlers = new HashSet<>();
    private ExecutorService pool = Executors.newCachedThreadPool();
    
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started, awaiting connections");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandlers.add(clientHandler);
                pool.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }
    
    void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(message);
        }
    }
    
    void removeClientHandler(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }
    
    public void stop() {
        try {
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.stop();
            }
            pool.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}