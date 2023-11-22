package org.telegram.buttonBot.bot.upload;

import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class UploadDocument extends Thread{
    public void run()  {

        File file = new File("C://Users/HPE/Downloads/Telegram Desktop/6.MOV");

        InputFile inputFile = new InputFile(file);

        SendDocument sendDocument = new SendDocument(DataBase.chatId.toString(), inputFile);

        try {
            DataBase.bot.execute(sendDocument);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }



    }
}
