package eu.michalbuda.android.swipecards;

import android.util.Log;

import java.util.ArrayList;

import eu.michalbuda.android.swipecards.ui.CardFragment;

/**
 * Created by Michal Buda on 2018-03-19.
 */

public class Game {

    private static Game sInstance;

    static final String TAG = "Game class";
    public final int CARD_QUANTITY = 5;
    public static final int ROUND_TIME_MILLIS = 10000;
    public static final int COUNT_INTERVAL_MILLIS = 1000;
    private int categoryId;

    private ArrayList<CardFragment> cardFragments;
    private int round = 0;


        public static Game getInstance(){
        if(sInstance == null){
            synchronized (Game.class) {
                if(sInstance == null){
                    sInstance = new Game();
                    sInstance.round = 0;
                }
            }
        }
        return sInstance;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void nextRound(){
        Log.d(TAG, "nextRound: ");
        round++;
    }
    
    public void setGuessed(){
        // TODO: 2018-03-20
    }

    public void setNotGuessed(){
        // TODO: 2018-03-20
    }

    public void setCardFragments(ArrayList<CardFragment> cardFragments) {
        this.cardFragments = cardFragments;
    }

    public int getRound() {
        return round;
    }

    public boolean isGameOver(){
        return (round >= CARD_QUANTITY);
    }

    public boolean isCardsListFull(){
        return (cardFragments.size() == CARD_QUANTITY);
    }

    public ArrayList<CardFragment> getCardFragments() {
        return cardFragments;
    }

    public CardFragment getCurrentCardFragment() {
        Log.d(TAG, "getCurrentCardFragment: " + cardFragments.get(round));
        return cardFragments.get(round);
    }

    public void setAnswer(boolean guessed) {
        if(guessed) setGuessed();
        else setNotGuessed();
    }





}
