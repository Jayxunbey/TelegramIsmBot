package org.telegram.buttonBot.bot.ui.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameService {
    public static boolean hasNameOnBase(String text) {

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("src/main/resources/namesql.txt"));

            String string;
            while ((string=reader.readLine())!=null){
                String[] split = string.split(",");
                if (split[2].equalsIgnoreCase(text)){
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return false;
    }
}
