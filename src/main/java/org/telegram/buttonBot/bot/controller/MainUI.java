package org.telegram.buttonBot.bot.controller;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MainUI {

    public static void run(Update update) {







        SendMsg.send(BotConfig.chatId, "Botdan bemalol foydalanib tashivuring jo'ram");
        System.out.println("DataBase.userMap.get(BotConfig.chatId) = " + DataBase.userMap.get(BotConfig.chatId));




    }
}
