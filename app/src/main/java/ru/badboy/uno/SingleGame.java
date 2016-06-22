package ru.badboy.uno;

import android.content.Context;

/**
 * Created by Евгений on 09.01.2016.
 */
public class SingleGame {
    private static UnoGame sGame;
    private Context mAppContext;

    private SingleGame(Context appContext) {
        mAppContext = appContext;
    }

    public static UnoGame getInstance(Context c) {
        return sGame;
    }

    public static void setGame(UnoGame game){
        sGame = game;
    }
}
