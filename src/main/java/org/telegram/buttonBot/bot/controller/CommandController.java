package org.telegram.buttonBot.bot.controller;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandController {
    public static void run(Update update) {


        String command = update.getMessage().getText();

//        System.out.println("command = " + command);

        if (command.equals("/start")){
            startOperation(update);
            return;
        }

        if (command.equals("/clearhistory")){
            clearHistory(update);
        }

        if (command.equals("/info")){
            infoOperation(update);
            return;
        }



    }

    private static void clearHistory(Update update) {
        if (DataBase.userMap.containsKey(BotConfig.chatId)) {
            DataBase.userMap.remove(BotConfig.chatId);
        }
        if (DataBase.registerStepsMap.containsKey(BotConfig.chatId)){
            DataBase.registerStepsMap.remove(BotConfig.chatId);
        }


        if (DataBase.masterStepMap.containsKey(BotConfig.chatId)) {
            DataBase.masterStepMap.remove(BotConfig.chatId);
        }

        LoginUI.run(update);

    }

    private static void infoOperation(Update update) {

        if (DataBase.userMap.containsKey(BotConfig.chatId)) {
            if (DataBase.userMap.get(BotConfig.chatId).isRegistered()) {
                String infoOfUser = DataBase.userMap.get(BotConfig.chatId).toString();
                SendMsg.send(BotConfig.chatId,infoOfUser);
                return;
            }
        }
        SendMsg.send(BotConfig.chatId, """
                    Siz hali ro'yhatdan o'tmagansiz
                    
                    Ro'yhatdan o'tish uchun /start tugmasini bosing""");

    }

    private static void startOperation(Update update) {
        if (DataBase.userMap.containsKey(BotConfig.chatId)){
            if (DataBase.userMap.get(BotConfig.chatId).isRegistered()){
                SendMsg.send(BotConfig.chatId, "Siz ro'yhatdan o'gansiz.");
            }else {
                clearHistory(update);
            }
        }else {
            clearHistory(update);
        }
    }
}
