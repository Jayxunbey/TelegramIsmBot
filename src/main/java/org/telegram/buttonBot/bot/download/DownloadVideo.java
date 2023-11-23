package org.telegram.buttonBot.bot.download;

import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DownloadVideo {
    public static void run(Update update) {
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("https://api.telegram.org/bot"
                        + DataBase.botToken+"/getFile?file_id="
                        +update.getMessage().getVideo().getFileId()))
                .GET()
                .build();
        System.out.println("request.method() = " + request.method());
        System.out.println("request.uri().getQuery() = " + request.uri().getQuery());
        System.out.println("request.toString() = " + request.toString());

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("response.uri().toURL().toString() = " + response.uri().toURL().toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("response.body() = " + response.body());
    }
}
