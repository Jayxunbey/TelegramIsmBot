package org.telegram.buttonBot.bot.ui;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.data.KeyboardBase;
import org.telegram.buttonBot.bot.data.TextBase;
import org.telegram.buttonBot.bot.entity.User;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

public class LoginUI {

    public static void run(Update update) {

        if (DataBase.masterStepMap.containsKey(BotConfig.chatId) && DataBase.masterStepMap.get(BotConfig.chatId).equals(Steps.Master.LOGIN)) {

            if (DataBase.Login.registerStepsMap.get(BotConfig.chatId).equals(Steps.Login.FILLNUMBER)) {

                if (update.getMessage().hasContact()) {
                    User user = DataBase.userData.get(BotConfig.chatId);
                    completeRegister(update, user);

                    ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(true);

                    SendMsg.send(BotConfig.chatId,null, "✅ Muvaffaqiyatli ro'yhatdan o'tdingiz.", update.getMessage().getMessageId(),replyKeyboardRemove);
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

        SendMsg.send(BotConfig.chatId,null, TextBase.Login.hello, KeyboardBase.getContactKeyboard);

        DataBase.userData.put(BotConfig.chatId, new User());
        DataBase.masterStepMap.put(BotConfig.chatId, Steps.Master.LOGIN);
        DataBase.Login.registerStepsMap.put(BotConfig.chatId, Steps.Login.FILLNUMBER);
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
        DataBase.Login.registerStepsMap.remove(BotConfig.chatId);

        DataBase.userData.put(BotConfig.chatId, user);
    }


}
