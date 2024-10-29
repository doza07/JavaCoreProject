package com.doza.http1_1example.server;

public class HttpServerRunner {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(100, 1234);
        httpServer.run();
    }
}
