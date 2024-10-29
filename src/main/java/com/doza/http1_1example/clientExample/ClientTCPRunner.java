package com.doza.http1_1example.clientExample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTCPRunner {
    public static void main(String[] args) {
        sendRequestToDomainName("localhost", 7777);
    }

    public static void sendRequestToDomainName(String domainName, int port) {
        InetAddress inetAddress = null;
        try {
            inetAddress = Inet4Address.getByName(domainName);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        //http - port = 80
        //https - port = 443
        try (var socket = new Socket(inetAddress, port);
             var outputStream = new DataOutputStream(socket.getOutputStream());
             var inputStream = new DataInputStream(socket.getInputStream());
             var scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                var request = scanner.nextLine();
                outputStream.writeUTF(request);
                var response = inputStream.readUTF();
                System.out.println("Response server: " + response);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}