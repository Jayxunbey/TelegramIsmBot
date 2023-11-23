package org.telegram.buttonBot.bot;
import org.telegram.buttonBot.bot.controller.CommandController;
import org.telegram.buttonBot.bot.controller.MessageController;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.controller.LoginUI;
import org.telegram.buttonBot.bot.download.DownloadController;
import org.telegram.buttonBot.bot.download.DownloadImage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotUpdate extends TelegramLongPollingBot {



    public TelegramBotUpdate(){
        super("6534338373:AAF2FJb8VOC6I747vNTvL-jcu95A4XM8uF0");
    }
    @Override

    public void onUpdateReceived(Update update) {



            DataBase.bot=this;
            DataBase.chatId=update.getMessage().getChatId();


        DownloadController.run(update);

        /**
         * Command buyruqlar kelsa kerakli joyga yo'naltiradi
         * masalan: /start, /currency
         */
/*
        Ro'yhatdan o'tish joyi

        if (update.hasMessage()){
            if (update.getMessage().isCommand()) {
                CommandController.run(update);
                return;
            }
        }

        if (update.hasMessage()){
            MessageController.run(update);
        }
*/


    }

    @Override
    public String getBotUsername() {
        return "https://t.me/LearnButtonBot";
    }

}
