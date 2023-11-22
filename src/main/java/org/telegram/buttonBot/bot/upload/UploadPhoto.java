package org.telegram.buttonBot.bot.upload;

import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class UploadPhoto extends Thread {

    public void run() {

        File file = new File("C://Users/HPE/Downloads/Telegram Desktop/imagePhoto.png");

        InputFile inputFile = new InputFile(file);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(inputFile);
//        sendPhoto
        sendPhoto.setChatId(DataBase.chatId);

        try {
            DataBase.bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
