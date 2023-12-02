package org.telegram.buttonBot.bot.step;

public class Steps {



    public enum Master{
        LOGIN,
        MAIN


    }

    public enum Main{
        GAMEWITHBOT,
        GAMEWITHFRIEND,
        GAMERANDOMLY;
        public enum gameWithBot{
            STARTGAME,
            ONGAME;
        }
        public enum gameWithFriend{

        }
        public enum gameRandomly{

        }
    }
    public enum Login {
        FILLNUMBER;
    }



}
