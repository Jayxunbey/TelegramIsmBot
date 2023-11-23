package org.telegram.buttonBot.bot.download;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class DownloadController {
    public static void run(Update update) {


        if (update.hasMessage()) {
            if (update.getMessage().hasDocument()) {
                DownloadDocument.run(update);
            }

            if (update.getMessage().hasVideo()) {
                DownloadVideo.run(update);
            }

            if (update.getMessage().hasVoice()){
                DownloadVoice.run(update);
            }

            if (update.getMessage().hasAudio()){
                DownloadAudio.run(update);
            }


            if (update.getMessage().hasPhoto()){
                try {
                    DownloadImage.run(update);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }else {
            System.out.println("Message yoq");
        }

    }
}
