package com.doza.http1_1example.clientExample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDPRunner {
    public static void main(String[] args) {

        sendRequestToDomainName("Hello from UDP client".getBytes(), "localhost", 7777);
    }

    private static void sendRequestToDomainName(byte[] bytes, String host, int port) {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            var inetAddress = InetAddress.getByName(host);
            var datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, port);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
