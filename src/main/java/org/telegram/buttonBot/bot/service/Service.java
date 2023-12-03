package org.telegram.buttonBot.bot.service;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.TempBase;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Service {
    public static void deleteMessage(Update update) {
        DeleteMessage deleteMessage = new DeleteMessage();

        if (update.hasCallbackQuery()){
            // Call back queryniki
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if (callbackQuery.getMessage()!=null) {
                deleteMessage.setMessageId(callbackQuery.getMessage().getMessageId());
                deleteMessage.setChatId(BotConfig.chatId);
                try {
                    BotConfig.bot.execute(deleteMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            // Messasgeniki
            Message message = update.getMessage();
            deleteMessage.setMessageId(message.getMessageId());
            deleteMessage.setChatId(BotConfig.chatId);
        }


    }

    public static void removeInlineKeyboardFromMessage(Integer messageId) {

        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(BotConfig.chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(TempBase.Last.lastMessage.get(BotConfig.chatId).getText());

        try {
            BotConfig.bot.execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public static Integer getMessageId(Update update) {
        if (update.hasCallbackQuery()){
            if (update.getCallbackQuery().getMessage()!=null){
                return update.getCallbackQuery().getMessage().getMessageId();
            }
            return 0;
        }else {
            return update.getMessage().getMessageId();
        }
    }

    public static Message getMessageAsLast(SendMessage sendMessage) {
        Message messageForLastMessage = new Message();
        messageForLastMessage.setText(sendMessage.getText());
        messageForLastMessage.setMessageId(getMessageId(BotConfig.update)+1);

        try {
            messageForLastMessage.setReplyMarkup((InlineKeyboardMarkup) sendMessage.getReplyMarkup());
        }catch (RuntimeException e){
            System.out.println("Inline Keyboardi yoqqu");
        }

        return messageForLastMessage;
    }


//    public static ReplyKeyboard getKeyboardMarkup(ReplyKeyboard replyKeyboard) {
//
//    }
}
