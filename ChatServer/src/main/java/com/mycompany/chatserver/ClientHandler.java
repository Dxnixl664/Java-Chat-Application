package com.mycompany.chatserver;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private CServer server;
    private PrintWriter out;
    private BufferedReader in;
    
    public ClientHandler(Socket socket, CServer server) {
        this.clientSocket = socket;
        this.server = server;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                server.broadcastMessage(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }
    
    void sendMessage(String message) {
        out.println(message);
    }
    
    void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            server.removeClientHandler(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
