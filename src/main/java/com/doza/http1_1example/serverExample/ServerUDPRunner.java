package com.doza.http1_1example.serverExample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class ServerUDPRunner {
    public static void main(String[] args) {

        try (var server = new DatagramSocket(7777)) {
            byte[] buffer = new byte[512];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            server.receive(packet);

            System.out.println(new String(buffer));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
