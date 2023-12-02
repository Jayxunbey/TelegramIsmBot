package org.telegram.buttonBot.bot.ui;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.data.KeyboardBase;
import org.telegram.buttonBot.bot.data.TextBase;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.buttonBot.bot.ui.game.viaFriend.GameWithFriend;
import org.telegram.buttonBot.bot.ui.game.viaRandomly.GameRandomly;
import org.telegram.buttonBot.bot.ui.game.viabot.GameWithBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MainUI {

    public static void run(Update update) {


        if (!DataBase.Main.gameType.containsKey(BotConfig.chatId)) {
            Update forSend=null;
            if (!update.hasMessage()) {
                // Hali bitta yam o'yin turini tanlamagan
                forSend = update;
            }

            SendMsg.send(BotConfig.chatId, forSend, TextBase.Login.text, KeyboardBase.mainInlineKeyboard);
            return;
        } else {
            // Tanlangan o'yin clasiga yo'naltiriladi

            switch (DataBase.Main.gameType.get(BotConfig.chatId)) {
                case GAMEWITHBOT: {
                    GameWithBot.run(update);
                    return;
                }
                case GAMERANDOMLY: {
                    GameRandomly.run(update);
                    return;
                }
                case GAMEWITHFRIEND: {
                    GameWithFriend.run(update);
                    return;
                }
            }


        }


        System.out.println("DataBase.userMap.get(BotConfig.chatId) = " + DataBase.userData.get(BotConfig.chatId));


    }
}
