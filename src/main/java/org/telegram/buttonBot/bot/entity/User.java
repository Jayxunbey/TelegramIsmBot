package org.telegram.buttonBot.bot.entity;

import lombok.*;
import org.telegram.buttonBot.bot.step.Steps;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class User {

    private Long chatId;
    private String fullName;
    private String phoneNumber;
    private String userName;
    private boolean isRegistered;

}
