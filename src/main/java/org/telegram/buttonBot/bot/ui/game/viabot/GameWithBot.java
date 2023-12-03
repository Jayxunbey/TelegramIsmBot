package org.telegram.buttonBot.bot.ui.game.viabot;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.data.KeyboardBase;
import org.telegram.buttonBot.bot.data.TempBase;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.buttonBot.bot.ui.MainUI;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GameWithBot {

    public static void run(Update update) {

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if (DataBase.Main.gameType.containsKey(BotConfig.chatId)) {
                String data = callbackQuery.getData();
                if (!TempBase.chatGame.containsKey(BotConfig.chatId)) {
                    if (data.equals("BACK")) {
                        DataBase.Main.gameType.remove(BotConfig.chatId);
                        MainUI.run(update);
                        return;
                    }
                    if (data.equals("STARTGAME")) {
                        DataBase.Main.GameType.gameWithBot.put(BotConfig.chatId, Steps.Main.gameWithBot.ONGAME);
                        OnChatViaBot.run(update);
                        return;
                    }
                    return;
                } else {
                    OnChatViaBot.run(update);
                }
            } else {
                DataBase.Main
                        .gameType
                        .put(BotConfig.chatId,
                                Steps.Main.valueOf("GAMEWITHBOT"));
                //// game with bot o'yinni boshlash uchun kod
                String text = "Siz \uD83E\uDD16Bot bilan  o'ynash rejimidasiz, \n" +
                        "Ism o'yini \uD83C\uDFAE siz va bot orasida olib boriladi (\uD83D\uDC68\uD83C\uDFFC\u200D\uD83D\uDCBB-\uD83E\uDD16)\n" +
                        "\n" +
                        "O'yinni boshlash uchun quyidagi tugmani bosing \uD83D\uDC47";

                SendMsg.send(BotConfig.chatId, update, text, KeyboardBase.GameWithBot.gameWithBot.get(Steps.Main.gameWithBot.STARTGAME));
                DataBase.Main.gameType.put(BotConfig.chatId, Steps.Main.GAMEWITHBOT);
            }

            return;
        }

        if (update.hasMessage()) {
            if (TempBase.chatGame.containsKey(BotConfig.chatId)) {
                // o'yinda.
                OnChatViaBot.run(update);
            } else {
                // O'yinda emas
            }
            return;
        }

    }

}
