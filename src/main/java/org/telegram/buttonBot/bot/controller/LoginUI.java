package org.telegram.buttonBot.bot.controller;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.data.TextBase;
import org.telegram.buttonBot.bot.entity.User;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class LoginUI {

    public static void run(Update update) {

        if (DataBase.masterStepMap.containsKey(BotConfig.chatId) && DataBase.masterStepMap.get(BotConfig.chatId).equals(Steps.Master.LOGIN)) {

            if (DataBase.registerStepsMap.get(BotConfig.chatId).equals(Steps.Login.FILLNUMBER)) {

                if (update.getMessage().hasContact()) {
                    User user = DataBase.userMap.get(BotConfig.chatId);
                    completeRegister(update, user);
                    MainUI.run(update);
                } else {
                    sendStartMessage();
                }
                return;
            }

        } else {

            sendStartMessage();
            return;
        }
    }

    private static void sendStartMessage() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(List.of(new KeyboardRow(List.of(KeyboardButton.builder().text("Ro'yhatdan o'tish").requestContact(true).build()))));

        replyKeyboardMarkup.setResizeKeyboard(true);

        SendMsg.send(BotConfig.chatId, TextBase.Login.hello, replyKeyboardMarkup);

        DataBase.userMap.put(BotConfig.chatId,new User());
        DataBase.masterStepMap.put(BotConfig.chatId, Steps.Master.LOGIN);
        DataBase.registerStepsMap.put(BotConfig.chatId, Steps.Login.FILLNUMBER);
    }

    private static void completeRegister(Update update, User user) {
        Contact contact = update.getMessage().getContact();

        user.setChatId(BotConfig.chatId);
        user.setFirstName(contact.getFirstName());
        user.setPhoneNumber(contact.getPhoneNumber());
        user.setLastName(contact.getLastName());
        user.setUserName("@" + update.getMessage().getChat().getUserName());
        user.setRegistered(true);

        DataBase.masterStepMap.put(BotConfig.chatId, Steps.Master.MAIN);
        DataBase.registerStepsMap.remove(BotConfig.chatId);

        DataBase.userMap.put(BotConfig.chatId, user);
    }


}
