package com.mycompany.chatclient;

import java.io.*;
import java.net.*;

public class CClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread listenerThread;
    private boolean stopFlag = false;
    
    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
            // Starting separate thread to listen for messages from server
            listenerThread = new Thread(() -> {
                try {
                    String fromServer;
                    while (!stopFlag && (fromServer = in.readLine()) != null) {
                        System.out.println("Server: " + fromServer);
                    }
                } catch (IOException e) {
                    if (!stopFlag) {
                        e.printStackTrace();
                    }
                }
            });
            listenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String msg) {
        out.println(msg);
    }
    
    public void stopConnection() {
        stopFlag = true;
        try {
            if (listenerThread != null) {
                listenerThread.join(); // Wait for listener thread to finish
            }
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
