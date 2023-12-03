package org.telegram.buttonBot.bot.ui.game.viabot;

import org.telegram.buttonBot.bot.data.BotConfig;
import org.telegram.buttonBot.bot.data.DataBase;
import org.telegram.buttonBot.bot.data.TempBase;
import org.telegram.buttonBot.bot.entity.ChatGame;
import org.telegram.buttonBot.bot.entity.User;
import org.telegram.buttonBot.bot.service.SendMsg;
import org.telegram.buttonBot.bot.ui.game.GameService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

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
            SendMsg.send(BotConfig.chatId, update,"\uD83C\uDFAEO'yin boshlandi \uD83D\uDFE2\n" +
                    "\n" +
                    "\uD83D\uDD34 o'yinni to'xtatish uchun /stop buyrug'ini yuboring.\n" +
                    "\n" +
                    "⏩ Navbat sizniki, biror ism yuboring!");
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

        if (chatGame.getUsedWords().containsKey(text.toLowerCase())) {


            SendMessage message = new SendMessage();
            message.setChatId(BotConfig.chatId);
            if (chatGame.getUsedWords().get(text)!=null){
                message.setReplyToMessageId(chatGame.getUsedWords().get(text).getMessageId());
            }
            message.setText("\uD83D\uDCE8 Bu ism avval foydalanilgan ❌\n" +
                    "\n" +
                    "\uD83D\uDD04 qaytadan '"+TempBase.Last.lastLetter.get(BotConfig.chatId)+"' harfidan boshlanadigan ism yuboring");

            try {
                BotConfig.bot.execute(message);
            } catch (TelegramApiException e) {
                message.setReplyToMessageId(null);
                message.setText("\uD83D\uDCE8 Bu ism avval foydalanilgan ❌\n" +
                        "\n" +
                        "\uD83D\uDD0D avvalgi habarga reply qila olmadim chunki o'chirilgan ekan \uD83D\uDEAE\n" +
                        "\n" +
                        "\uD83D\uDD04 qaytadan '"+TempBase.Last.lastLetter.get(BotConfig.chatId)+"' harfidan boshlanadigan ism yuboring");
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
                        "    \uD83D\uDD38 Ism: "+answerOfBot+"\n" +
                        "    \uD83D\uDD0E oxirgi harfi :  '"+answerOfBot.charAt(answerOfBot.length()-1)+"'\n" +
                        "\n" +
                        "\uD83D\uDC68\uD83C\uDFFC\u200D\uD83D\uDCBB Navbat sizniki:\n" +
                        "\n" +
                        "✏\uFE0F '"+String.valueOf(answerOfBot.charAt(answerOfBot.length()-1)).toUpperCase()+"' harfidan boshlanadigan ism yuboring!");

                try {
                    BotConfig.bot.execute(message);
                    TempBase.Last.lastLetter.put(BotConfig.chatId, String.valueOf(answerOfBot.charAt(answerOfBot.length()-1)).toUpperCase());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

                Message updateMessage = update.getMessage();



                Map<String, Message> word = chatGame.getUsedWords();
                word.put(text.toLowerCase(), update.getMessage());
                word.put(answerOfBot.toLowerCase(), null);
                chatGame.setUsedWords(word);

                Message message1 = new Message();
                message1.setMessageId(updateMessage.getMessageId()+1);
                TempBase.Last.lastMessage.put(BotConfig.chatId,message1 );

                TempBase.chatGame.put(BotConfig.chatId, chatGame);
            }else {
                SendMessage message = new SendMessage();
                message.setChatId(BotConfig.chatId);

                if (TempBase.chatGame.get(BotConfig.chatId).getUsedWords().isEmpty()) {
                    message.setText("⛔\uFE0F Siz yuborgan ism mavjud emas \uD83E\uDD37\u200D♂\uFE0F\n" +
                            "\n" +
                            "\uD83D\uDD04 qaytadan ism kiriting!");

                }else {
                    message.setText("⛔\uFE0F Siz yuborgan ism mavjud emas \uD83E\uDD37\u200D♂\uFE0F\n" +
                            "\n" +
                            "\uD83D\uDD04 qaytadan '"+TempBase.Last.lastLetter.get(BotConfig.chatId)+"' harfidan  boshlanadigan ism kiriting!");

                }

                try {
                    BotConfig.bot.execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }

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

        List<String> listOfLines = new ArrayList<>(reader.lines().filter(s -> {
            if (s.split(",")[0].equalsIgnoreCase(String.valueOf(text.charAt(text.length()-1)))) {
                return true;
            }
            return false;
        }).toList());

        if (TempBase.chooseControlOfBot.containsKey(BotConfig.chatId)){
            if (TempBase.chooseControlOfBot.get(BotConfig.chatId)){
                listOfLines.sort(Comparator.reverseOrder());
            }else {
                listOfLines.sort(Comparator.naturalOrder());
            }
        } else {
            TempBase.chooseControlOfBot.put(BotConfig.chatId, true);
            listOfLines.sort(Comparator.naturalOrder());
        }




        for (String string : listOfLines) {
             result = string.split(",")[2];
            if (!result.equalsIgnoreCase(text)) {
                if (isValid(result)) {
                    return result;
                }

            }
        }

        return null;
    }

    private static boolean isValid(String text) {


        if (TempBase.chatGame.get(BotConfig.chatId).getUsedWords().containsKey(text.toLowerCase())){
            return false;
        }

        return true;
    }

    public static void stopGame(Update update) {
        if (DataBase.Main.GameType.gameWithBot.containsKey(BotConfig.chatId)){

            DataBase.Main.GameType.gameWithBot.remove(BotConfig.chatId);
            TempBase.chatGame.remove(BotConfig.chatId);

            System.out.println("stopGame Ham ishladi");
            CallbackQuery callbackQuery = new CallbackQuery();
            callbackQuery.setData("BACK");

            update.setCallbackQuery(callbackQuery);
            OnChatViaBot.run(update);
        }
    }
}
