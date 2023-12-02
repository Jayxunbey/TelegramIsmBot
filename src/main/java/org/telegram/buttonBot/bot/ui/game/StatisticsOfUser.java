package org.telegram.buttonBot.bot.ui.game;

import lombok.Getter;
import lombok.Setter;
import org.telegram.buttonBot.bot.entity.User;

@Setter
@Getter
public class StatisticsOfUser {
        private int score;
        private User partner;
        private boolean isWinner;

}