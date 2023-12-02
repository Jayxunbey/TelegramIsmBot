package org.telegram.buttonBot.bot.data;

import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardBase {
    public static InlineKeyboardMarkup mainInlineKeyboard;
    public static final ReplyKeyboardMarkup getContactKeyboard;
    static {
        getContactKeyboard = new ReplyKeyboardMarkup(
                List.of(
                        new KeyboardRow(List.of(
                                KeyboardButton.builder().text(TextBase.Login.getPhoneNumberButton).requestContact(true).build()))));

        getContactKeyboard.setResizeKeyboard(true);

    }
    public static Map<Steps.Main.gameWithBot, ReplyKeyboard> gameWithBot;


    static {
        mainInlineKeyboard = InlineKeyboardMarkup.builder().keyboardRow(List.of(
                        InlineKeyboardButton.builder().text("Bot bilan o'ynash")
                                .callbackData("GAMEWITHBOT")
                                .build())).keyboardRow(List.of(
                        InlineKeyboardButton.builder().text("Do'st bilan o'ynash")
                                .callbackData("GAMEWITHFRIEND")
                                .build())).keyboardRow(List.of(
                        InlineKeyboardButton.builder().text("Random o'yinchi")
                                .callbackData("GAMERANDOMLY")
                                .build()))
                .build();
    }
//    public static Map<Steps.Master, Map<Steps.Main, ReplyKeyboard>> gameWithBot;

    public static class GameWithBot {

        public static final Map<Steps.Main.gameWithBot, ReplyKeyboard> gameWithBot=new HashMap<>();





        static {
            //////////// first
            InlineKeyboardMarkup first = InlineKeyboardMarkup.builder()
                    .keyboardRow(List.of(
                            InlineKeyboardButton.builder().text("O'yinni boshlash")
                                    .callbackData("STARTGAME")
                                    .build()))
                    .keyboardRow(List.of(
                            InlineKeyboardButton.builder().text("⬅\uFE0F Qaytish")
                                    .callbackData("BACK")
                                    .build()))
                    .build();

            gameWithBot.put(Steps.Main.gameWithBot.STARTGAME, first);

            ///////// second

            InlineKeyboardMarkup second = InlineKeyboardMarkup.builder()
                    .keyboardRow(List.of(
                            InlineKeyboardButton.builder().text("O'yinni tugatish")
                                    .callbackData("BACK")
                                    .build()))
                    .build();

            gameWithBot.put(Steps.Main.gameWithBot.ONGAME, second);




        }
    }

    //    public static I;
    static {

    }

}
