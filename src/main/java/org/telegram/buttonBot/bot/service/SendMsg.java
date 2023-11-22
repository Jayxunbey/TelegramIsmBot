package org.telegram.buttonBot.bot.service;

import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendMsg {

    public static void send(Long chatId, String message) {


        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            DataBase.bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }



    }
}
