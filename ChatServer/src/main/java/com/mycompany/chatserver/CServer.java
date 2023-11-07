package com.mycompany.chatserver;

import java.io.*;
import java.net.*;

public class CServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started, awaiting connections");
            
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");
            
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            String greeting = in.readLine();
            if ("hello server".equals(greeting)) {
                out.println("hello client");
            } else {
                out.println("unrecognized greeting");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }
    
    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
