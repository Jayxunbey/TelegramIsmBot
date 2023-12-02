package org.telegram.buttonBot.bot;

import org.telegram.buttonBot.bot.controller.Controller;
import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBotUpdate extends TelegramLongPollingBot {


    public TelegramBotUpdate() {
        super(BotConfig.botToken);
    }

    @Override

    public void onUpdateReceived(Update update) {


        System.out.println("update = " + update);

        Controller.run(update,this);

    }


    @Override
    public String getBotUsername() {
        return "https://t.me/Universal_Jayxuns_bot";
    }

}
