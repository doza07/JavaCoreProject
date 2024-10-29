package com.doza.http1_1example.clientExample;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class URLExampleRunner {
    public static void main(String[] args) {

        getRequest("https://google.com");
    }

    private static void getRequest(String url) {
        try {
            var urlCite = new URL(url);
            URLConnection urlConnection = urlCite.openConnection();
            Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
            for (String string : headerFields.keySet()) {
                System.out.println(string + ": " + headerFields.get(string));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
