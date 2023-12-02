package org.telegram.buttonBot.bot.data;

import org.telegram.buttonBot.bot.entity.User;
import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.buttonBot.bot.ui.game.StatisticsOfUser;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {

    public static Map<Long, Steps.Master> masterStepMap = new HashMap<>();

    public class Login{
        public static Map<Long, Steps.Login> registerStepsMap = new HashMap<>();

    }
    public class Main{
        public static Map<Long, Steps.Main> gameType = new HashMap<>();

        public static class GameType{
            public static Map<Long, Steps.Main.gameWithBot> gameWithBot = new HashMap<>();

        }
    }
    public static Map<Long, User> userData = new HashMap<>();

    public static Map<Long, List<StatisticsOfUser>> statisticsMap=new HashMap<>();

    public static boolean isRegistered(Long chatId) {
        return userData.containsKey(chatId);
    }


//    public static
}
