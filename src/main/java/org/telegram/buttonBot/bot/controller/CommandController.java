package org.telegram.buttonBot.bot.controller;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.buttonBot.bot.ui.LoginUI;
import org.telegram.buttonBot.bot.ui.game.viabot.OnChatViaBot;
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

        if (command.equals("/stop")){
            mashrutGame(update);
            return;
        }



    }

    private static void mashrutGame(Update update) {
        if (DataBase.Main.gameType.containsKey(BotConfig.chatId)) {
            switch (DataBase.Main.gameType.get(BotConfig.chatId)) {
                case GAMEWITHBOT: {
                    System.out.println("WithBot Case Iishladi commandagi");
                    OnChatViaBot.stopGame(update);
                    return;
                }
                case GAMEWITHFRIEND: {}
                case GAMERANDOMLY: {}
            }
        }
    }

    private static void clearHistory(Update update) {
        if (DataBase.userData.containsKey(BotConfig.chatId)) {
            DataBase.userData.remove(BotConfig.chatId);
        }
        if (DataBase.Login.registerStepsMap.containsKey(BotConfig.chatId)){
            DataBase.Login.registerStepsMap.remove(BotConfig.chatId);
        }


        if (DataBase.masterStepMap.containsKey(BotConfig.chatId)) {
            DataBase.masterStepMap.remove(BotConfig.chatId);
        }

        LoginUI.run(update);

    }

    private static void infoOperation(Update update) {

        if (DataBase.userData.containsKey(BotConfig.chatId)) {
            if (DataBase.userData.get(BotConfig.chatId).isRegistered()) {
                String infoOfUser = DataBase.userData.get(BotConfig.chatId).toString();
                SendMsg.send(BotConfig.chatId,null,infoOfUser);
                return;
            }
        }
        SendMsg.send(BotConfig.chatId,update, """
                    Siz hali ro'yhatdan o'tmagansiz
                    
                    Ro'yhatdan o'tish uchun /start tugmasini bosing""");

    }

    private static void startOperation(Update update) {
        if (DataBase.userData.containsKey(BotConfig.chatId)){
            if (DataBase.userData.get(BotConfig.chatId).isRegistered()){
                SendMsg.send(BotConfig.chatId,null, "Siz ro'yhatdan o'gansiz.");
            }else {
                clearHistory(update);
            }
        }else {
            clearHistory(update);
        }
    }
}
