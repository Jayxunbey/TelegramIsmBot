package org.telegram.buttonBot.bot.data;

import org.telegram.buttonBot.bot.entity.ChatGame;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Map;

public class TempBase {

    public static Map<Long, Boolean> chooseControlOfBot = new HashMap<>();
    public static Map<Long, ChatGame> chatGame = new HashMap<>();

    public static Map<Long, Message> lastMessage = new HashMap<>();

}
