package org.telegram.buttonBot.bot.data;

import org.telegram.buttonBot.bot.entity.User;
import org.telegram.buttonBot.bot.step.Steps;

import java.util.HashMap;
import java.util.Map;

public class DataBase {

    public static Map<Long, Steps.Master> masterStepMap = new HashMap<>();
    public static Map<Long, Steps.Login> registerStepsMap = new HashMap<>();
    public static Map<Long, User> userMap = new HashMap<>();

    public static boolean isRegistered(Long chatId) {

        return userMap.containsKey(chatId);

    }


//    public static
}
