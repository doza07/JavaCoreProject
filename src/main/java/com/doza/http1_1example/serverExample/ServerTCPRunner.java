package com.doza.http1_1example.serverExample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.util.Scanner;

public class ServerTCPRunner {
    public static void main(String[] args) {
        createServer(7777);
    }
    public static void createServer(int port) {

        try (ServerSocket serverSocket = new ServerSocket(port);
             var socket = serverSocket.accept();
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             var scanner = new Scanner(System.in)) {
            var request = dataInputStream.readUTF();
            while (!"stop".equals(request)) {
                System.out.println("Client request: " + request);
                String response = scanner.next();
                dataOutputStream.writeUTF("Response Server: " + response);
                request = dataInputStream.readUTF();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
