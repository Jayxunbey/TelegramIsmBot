package org.telegram.buttonBot.bot.data;

import org.telegram.buttonBot.bot.entity.User;
import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    public static String botToken = "6534338373:AAF2FJb8VOC6I747vNTvL-jcu95A4XM8uF0";
    public static TelegramLongPollingBot bot=null;
    public static Long chatId = null;

    public static Map<Long, Steps.Master> masterStepMap = new HashMap<>();
    public static Map<Long, Steps.LoginSteps> registerStepsMap = new HashMap<>();
    public static Map<Long, User> userMap = new HashMap<>();

    public static boolean isRegistered(Long chatId) {

        return userMap.containsKey(chatId);

    }


//    public static
}
