package com.doza.http1_1example.clientExample;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientExample {
    public static void main(String[] args) {
//        getRequest("https://www.google.com");
        postRequest("https://www.google.com");
    }

    private static void getRequest(String uri) {
        var builder = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        var request = HttpRequest.newBuilder(URI.create(uri))
                .GET()
                .build();
        try {
            var response = builder.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            System.out.println(request.headers());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void postRequest(String uri) {
        var builder = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = null;
            request = HttpRequest.newBuilder(URI.create(uri))
                    .POST(HttpRequest.BodyPublishers.ofString("HEllO!"))
                    .build();
        try {
            var response = builder.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            System.out.println(request.headers());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
