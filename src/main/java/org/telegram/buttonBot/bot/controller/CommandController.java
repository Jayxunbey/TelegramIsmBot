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
        if (DataBase.userMap.containsKey(DataBase.chatId)) {
            DataBase.userMap.remove(DataBase.chatId);
        }
        if (DataBase.registerStepsMap.containsKey(DataBase.chatId)){
            DataBase.registerStepsMap.remove(DataBase.chatId);
        }


        if (DataBase.masterStepMap.containsKey(DataBase.chatId)) {
            DataBase.masterStepMap.remove(DataBase.chatId);
        }

        LoginUI.run(update);

    }

    private static void infoOperation(Update update) {

        if (DataBase.userMap.containsKey(DataBase.chatId)) {
            if (DataBase.userMap.get(DataBase.chatId).isRegistered()) {
                String infoOfUser = DataBase.userMap.get(DataBase.chatId).toString();
                SendMsg.send(DataBase.chatId,infoOfUser);
                return;
            }
        }
        SendMsg.send(DataBase.chatId, """
                    Siz hali ro'yhatdan o'tmagansiz
                    
                    Ro'yhatdan o'tish uchun /start tugmasini bosing""");

    }

    private static void startOperation(Update update) {
        if (DataBase.userMap.containsKey(DataBase.chatId)){
            if (DataBase.userMap.get(DataBase.chatId).isRegistered()){
                SendMsg.send(DataBase.chatId, "Siz ro'yhatdan o'gansiz.");
            }else {
                clearHistory(update);
            }
        }else {
            clearHistory(update);
        }
    }
}
