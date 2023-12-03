package org.telegram.buttonBot.bot.service;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.TempBase;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendMsg {

    public static void send(Long chatId, Update updateForEdit, String message) {


        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        TempBase.Last.lastMessage.put(BotConfig.chatId, Service.getMessageAsLast(sendMessage));

        try {
            BotConfig.bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static void send(Long chatId, Update updateForEdit, String message, ReplyKeyboard keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(keyboardMarkup);

        if (updateForEdit != null) {
            Service.deleteMessage(updateForEdit);
        }

        TempBase.Last.lastMessage.put(BotConfig.chatId, Service.getMessageAsLast(sendMessage));


        try {
            BotConfig.bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public static void send(Long chatId, Update updateForEdit, String message, Integer messageId) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyToMessageId(messageId);




        TempBase.Last.lastMessage.put(BotConfig.chatId, Service.getMessageAsLast(sendMessage));


        try {
            BotConfig.bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public static void send(Long chatId, Update updateForEdit, String message, Integer messageId, ReplyKeyboard keyboardMarkup) {

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyToMessageId(messageId);
        sendMessage.setReplyMarkup(keyboardMarkup);

        TempBase.Last.lastMessage.put(BotConfig.chatId, Service.getMessageAsLast(sendMessage));

        try {
            BotConfig.bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
