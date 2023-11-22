package org.telegram.buttonBot.bot.controller;

import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandController {
    public static void run(Update update) {


        String command = update.getMessage().getText();

        System.out.println("command = " + command);

        if (command.equals("/start")){
            if (DataBase.userMap.containsKey(DataBase.chatId)){
                if (DataBase.userMap.get(DataBase.chatId).isRegistered()){
                    SendMsg.send(DataBase.chatId, "Siz ro'yhatdan o'gansiz.");
                }else {
                    LoginUI.run(update);
                }
            }else {
                LoginUI.run(update);
            }
            return;
        }


    }
}
