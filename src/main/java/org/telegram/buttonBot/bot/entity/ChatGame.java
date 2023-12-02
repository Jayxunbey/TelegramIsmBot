package org.telegram.buttonBot.bot.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.buttonBot.bot.ui.game.StatisticsOfUser;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;


@Setter
@Getter
@AllArgsConstructor
@ToString
public class ChatGame {
    private final User firstPlayer;
    private final User secondPlayer;
    private Map<String, Message> usedWords;
    private User queue;
    private Map<User, StatisticsOfUser> userStatisticsMap;
}
