package com.doza.http1_1example.client;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

public class HttpClientRunner {
    public static void main(String[] args) {
        var client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:1234"))
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src", "resources", "json", "example.json")))
                    .build();

            var response1 = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            var response2 = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            var response3 = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            var response4 = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            var response5 = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n" + response1.get().body());
            System.out.println("\n" + response2.get().body());
            System.out.println("\n" + response3.get().body());
            System.out.println("\n" + response4.get().body());
            System.out.println("\n" + response5.get().body());
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
