package org.telegram.buttonBot.bot.controller;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.buttonBot.bot.ui.game.viaRandomly.GameRandomly;
import org.telegram.buttonBot.bot.ui.game.viabot.GameWithBot;
import org.telegram.buttonBot.bot.ui.game.viaFriend.GameWithFriend;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallBackQueryController {

    public static void run(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        Steps.Main mainMap = null;
        if (DataBase.Main.gameType.containsKey(BotConfig.chatId)) {
            mainMap = DataBase.Main.gameType.get(BotConfig.chatId);
        } else {
            try {
            if (DataBase.masterStepMap.get(BotConfig.chatId).equals(Steps.Master.MAIN)) {
                    mainMap = Steps.Main.valueOf(callbackQuery.getData());
            }
            }catch (RuntimeException e){
                return;
            }
        }


        switch (mainMap) {
            case GAMEWITHBOT: {
                GameWithBot.run(update);
                return;
            }
            case GAMEWITHFRIEND: {
                GameWithFriend.run(update);
                return;
            }
            case GAMERANDOMLY: {
                GameRandomly.run(update);
                return;
            }
        }
    }
}
