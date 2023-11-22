package org.telegram.buttonBot.bot.controller;

import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.entity.User;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.telegrambots.meta.api.objects.Update;

public class LoginUI {

    public static void run(Update update) {

        if (DataBase.masterStepMap.containsKey(DataBase.chatId) &&
                DataBase.masterStepMap.get(DataBase.chatId).equals(Steps.Master.LOGIN)) {

            if (DataBase.registerStepsMap.get(DataBase.chatId).equals(Steps.LoginSteps.EnterFullName)) {
                getFullName(update);
                return;
            }

            if (DataBase.registerStepsMap.get(DataBase.chatId).equals(Steps.LoginSteps.EnterNumber)) {

                User user = DataBase.userMap.get(DataBase.chatId);

                getNumber(update, user);

                completeRegister(update, user);
                MainUI.run(update);
                return;
            }

        } else {
            SendMsg.send(DataBase.chatId, "Assalomu aleykum.\nBo'timizga hush kelibsiz." +
                    "\nRo'yhatdan o'tish uchun ism familiyangizni kiriting:");
            DataBase.masterStepMap.put(DataBase.chatId, Steps.Master.LOGIN);
            DataBase.registerStepsMap.put(DataBase.chatId, Steps.LoginSteps.EnterFullName);
            return;
        }
    }

    private static void completeRegister(Update update, User user) {
        user.setChatId(DataBase.chatId);
        user.setUserName(update.getMessage().getChat().getUserName());
        user.setRegistered(true);

        DataBase.masterStepMap.put(DataBase.chatId, Steps.Master.MAIN);
        DataBase.registerStepsMap.remove(DataBase.chatId);

        DataBase.userMap.put(DataBase.chatId, user);
    }

    private static void getNumber(Update update, User user) {
        if (update.getMessage().hasText()) {

            String phoneNumber = update.getMessage().getText();
            user = DataBase.userMap.get(DataBase.chatId);
            user.setPhoneNumber(phoneNumber);
            DataBase.userMap.put(DataBase.chatId, user);

            SendMsg.send(DataBase.chatId, "Siz nihoyatda go'zal ro'yhatdan o'tdingiz!");

        }
        else {
            SendMsg.send(DataBase.chatId, "Iltimos qaytadan tel nomeizni kiriting:");
        }
    }

    private static void getFullName(Update update) {
        if (update.getMessage().hasText()) {

            String fullName = update.getMessage().getText();

            User user = new User();
            user.setFullName(fullName);
            DataBase.userMap.put(DataBase.chatId, user);

            SendMsg.send(DataBase.chatId, "Tel nomerizni kiriting (example: +998901234567)");
            DataBase.registerStepsMap.put(DataBase.chatId, Steps.LoginSteps.EnterNumber);
        }
        else {
            SendMsg.send(DataBase.chatId, "Iltimos qaytadan ism familiyangizni kiriting:");
        }
    }
}
