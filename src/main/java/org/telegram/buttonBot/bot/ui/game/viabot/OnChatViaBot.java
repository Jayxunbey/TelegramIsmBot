package org.telegram.buttonBot.bot.ui.game.viabot;

import org.apache.http.client.utils.CloneUtils;
import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.data.KeyboardBase;
import org.telegram.buttonBot.bot.data.TempBase;
import org.telegram.buttonBot.bot.entity.ChatGame;
import org.telegram.buttonBot.bot.entity.User;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.buttonBot.bot.step.Steps;
import org.telegram.buttonBot.bot.ui.game.GameService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnChatViaBot {
    public static void run(Update update) {

        if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();

            if (data.equals("BACK")){
                // Sog'likni saqlash amallari Yoziladi
                DataBase.Main.gameType.remove(BotConfig.chatId);
                TempBase.chatGame.remove(BotConfig.chatId);
                GameWithBot.run(update);
                return;
            }
        }

        if (!TempBase.chatGame.containsKey(BotConfig.chatId)) {
            // Hali o'yin boshlanmagan
            User firstPlayer = DataBase.userData.get(BotConfig.chatId);
            User secondPlayer = new User(0l, "Telegram bot", null, null, BotConfig.bot.getBotUsername(), true);
            ChatGame chatGame = new ChatGame(firstPlayer, secondPlayer, new HashMap<>(), firstPlayer, new HashMap<>());
            TempBase.chatGame.put(BotConfig.chatId, chatGame);
            SendMsg.send(BotConfig.chatId, update,"\uD83C\uDFAE O'yin boshlandi.\n" +
                    "\n" +
                    "Ism yuborish navbati sizniki:", KeyboardBase.GameWithBot.gameWithBot.get(Steps.Main.gameWithBot.ONGAME));
        } else {
            if (update.getMessage().hasText()){
                //text yuborgan
                checkAndSend(update);
            }else {
                // text yubormagan

            }

        }
    }

    private static void checkAndSend(Update update) {


        ChatGame chatGame = TempBase.chatGame.get(BotConfig.chatId);
        // shu yerda bir birga habar almashiladi
        String text = update.getMessage().getText();

        if (chatGame.getUsedWords().containsKey(text)) {


            SendMessage message = new SendMessage();
            message.setChatId(BotConfig.chatId);
            message.setReplyToMessageId(chatGame.getUsedWords().get(text).getMessageId());
            message.setText("\uD83D\uDCE8 Bu ism avval foydalanilgan ❌\n" +
                    "\n" +
                    "\uD83D\uDD04 qaytadan urining");

            try {
                BotConfig.bot.execute(message);
            } catch (TelegramApiException e) {
                message.setReplyToMessageId(null);
                message.setText("\uD83D\uDCE8 Bu ism avval foydalanilgan ❌\n" +
                        "\n" +
                        "\uD83D\uDD0D avvalgi habarga reply qila olmadim chunki o'chirilgan ekan \uD83D\uDEAE\n" +
                        "\n" +
                        "\uD83D\uDD04 qaytadan urining");
                try {
                    BotConfig.bot.execute(message);
                } catch (TelegramApiException ex) {
                    throw new RuntimeException(ex);
                }
            }
            /*SendMsg.send(BotConfig.chatId,
                    null,
                    "Siz bu [so'zni](http://example.com) ishlatgansiz",
                    update.getMessage().getMessageId(),
                    KeyboardBase.GameWithBot.gameWithBot.get(Steps.Main.gameWithBot.ONGAME));
*/
        }else {

            if (GameService.hasNameOnBase(text)) {

                SendMessage message = new SendMessage();
                message.setChatId(BotConfig.chatId);

                String answerOfBot=getNameForBot(text);

                message.setText("\uD83D\uDCE5 Yuborilgan ism tasdiqlandi ✅\n" +
                        "     \uD83D\uDD38 Ism: "+text+"\n" +
                        "     \uD83D\uDD0E oxirgi harfi:  '"+text.toLowerCase().charAt(text.length()-1)+"'\n" +
                        "\n" +
                        "\uD83E\uDD16Botning javobi ↩\uFE0F:\n" +
                        "    \uD83D\uDD38 Ism: Naima\n" +
                        "    \uD83D\uDD0E oxirgi harfi :  'a'\n" +
                        "\n" +
                        "\uD83D\uDC68\uD83C\uDFFC\u200D\uD83D\uDCBB Navbat sizniki:\n" +
                        "\n" +
                        "✏\uFE0F 'A' harfidan boshlanadigan ism yuboring!");

                try {
                    BotConfig.bot.execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

                Message updateMessage = update.getMessage();
                Message clone;
                try {
                    clone = (Message) CloneUtils.clone(updateMessage);
                    clone.setMessageId(updateMessage.getMessageId()+1);
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }

                Map<String, Message> word = chatGame.getUsedWords();
                word.put(text, update.getMessage());
                word.put(answerOfBot, clone);
                chatGame.setUsedWords(word);

                TempBase.chatGame.put(BotConfig.chatId, chatGame);
            }else {
                SendMessage message = new SendMessage();
                message.setChatId(BotConfig.chatId);
                message.setText("⛔\uFE0F Siz yuborgan ism mavjud emas \uD83E\uDD37\u200D♂\uFE0F\n" +
                        "\n" +
                        "\uD83D\uDD04 qaytadan 'A' harfidan  boshlanadigan ism kiriting!");

                try {
                    BotConfig.bot.execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }

/*
        if (isValid(text)){
            Service.removeInlineKeyboardFromMessage(TempBase.lastMessage.get(BotConfig.chatId).getMessageId());
            SendMsg.send(BotConfig.chatId, update,"\uD83D\uDC68\uD83C\uDFFC\u200D\uD83D\uDCBBSiz yuborgan ism:  \"Jayxun\"\n" +
                            " (Oxirgi harfi \"n\" ga teng.)\n" +
                            "\n" +
                            "\uD83E\uDD16Botning javob qilib yuborgan ismi: \"Naima\"\n" +
                            "(Oxirgi harfi \"a\" ga teng.)\n" +
                            "\n" +
                            "\uD83D\uDD79 Navbat sizniki \n" +
                            "Siz 'A' harfidan boshlanadigan ism yuborishingiz kerak:",
                    update.getMessage().getMessageId(),
                    KeyboardBase.GameWithBot.gameWithBot.get(Steps.Main.gameWithBot.ONGAME));

//                Update lastMessage = TempBase.lastMessage.get(BotConfig.chatId);

            Message message = TempBase.lastMessage.get(BotConfig.chatId);
            System.out.println("message = " + message);
        }else {

        }

*/

    }

    private static String getNameForBot(String text) {

        String endCharacter = ""+text.charAt(text.length()-1);
        String result;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/namesql.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<String> listOfLine = reader.lines().filter(s -> {
            if (s.split(",")[0].equalsIgnoreCase(String.valueOf(text.charAt(text.length()-1)))) {
                return true;
            }
            return false;
        }).toList();


        if (TempBase.chooseControlOfBot.containsKey(BotConfig.chatId)){
            if (TempBase.chooseControlOfBot.get(BotConfig.chatId)){
                listOfLine.sort(Comparator.reverseOrder());
            }else {
                listOfLine.sort(Comparator.naturalOrder());
            }
        } else {
            TempBase.chooseControlOfBot.put(BotConfig.chatId, true);
            listOfLine.sort(Comparator.naturalOrder());
        }


        for (String string : listOfLine) {
             result = string.split(",")[2];
            if (!result.equalsIgnoreCase(text)) {
                return result;
            }
        }

        return null;
    }

    private static boolean sendIfAvaliable(ChatGame chatGame, String text) {

        if (chatGame.getUsedWords().containsKey(text)) {

        }

        return false;
    }

    private static boolean isNameUsed(String text) {
return false;
    }

    private static boolean isValid(String text) {



        return false;
    }
}
