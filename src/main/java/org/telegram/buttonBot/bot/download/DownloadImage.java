package org.telegram.buttonBot.bot.download;


import com.google.gson.Gson;
import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.download.model.ResponseObj;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.IOException;

import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.apache.commons.io.FileUtils.getFile;

public class DownloadImage {

    public static void run(Update update) throws TelegramApiException, IOException, InterruptedException {

//        System.out.println("update.getMessage().getPhoto().get(2) = " + update.getMessage().getPhoto().get(2));


        String host = "https://api.telegram.org";
        HttpRequest request = HttpRequest.newBuilder().
                 uri(URI.create(host + "/bot"
                        + BotConfig.botToken + "/getFile?file_id="
                        + update.getMessage().getPhoto().get(3).getFileId()))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();

        HttpResponse<String> response
                = client.send(request, HttpResponse.BodyHandlers.ofString());


        System.out.println("response.body() = " + response.body());


        Gson gson = new Gson();
        ResponseObj responseObj = gson.fromJson(response.body(), ResponseObj.class);

        System.out.println("request.uri().getQuery() = " + request.uri().getQuery());

        System.out.println("responseObj = " + responseObj);

        System.out.println("Photo: -> "+host+"/file/bot" + BotConfig.botToken + "/" + responseObj.getResult().file_path);

    }
}
