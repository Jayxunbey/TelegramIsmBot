package org.telegram.buttonBot.bot.controller;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Controller {

    public static void run(Update update, TelegramLongPollingBot bot) {



        if (update.hasMessage()) {


            BotConfig.bot = bot;
            BotConfig.chatId = update.getMessage().getChatId();
            BotConfig.update=update;

            if (update.getMessage().isCommand()) {
                CommandController.run(update);
            } else
                MessageController.run(update);
            return;
        }

        if (update.hasCallbackQuery()) {
            BotConfig.bot = bot;
            BotConfig.chatId = update.getCallbackQuery().getMessage().getChatId();
            CallBackQueryController.run(update);
            return;
        }

    }
}
