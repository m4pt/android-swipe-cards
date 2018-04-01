/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.michalbuda.android.swipecards.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import eu.michalbuda.android.swipecards.Game;
import eu.michalbuda.android.swipecards.R;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static CategoryListFragment categoryListFragment;
    protected static Game game;
    private static CountDownTimer countDownTimer;
    private static CardFragment cardFragment;
    private static android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        fragmentManager = getSupportFragmentManager();

        // Add category list fragment if this is first creation
        if (savedInstanceState == null) {
            categoryListFragment = new CategoryListFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, categoryListFragment, CategoryListFragment.TAG)
                    .commit();
        }
    }

    public void setOrientationLandscape(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void setOrientationPortrait(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void hideStatusBar(boolean hide){
        View decorView = getWindow().getDecorView();

        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(hide){
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

            actionBar.hide();
        } else {
            // Show the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);

            //actionBar.show();
        }
    }

    @Override
    public void onBackPressed() {
        // TODO: 2018-03-20 exit from app on double backpressed

        Log.d(TAG, "onBackPressed: ");

        cancelGame();

        categoryListFragment = new CategoryListFragment();

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, categoryListFragment, null)
                .commit();
    }

    public void show(int categoryId) {

        cardFragment = CardFragment.forCard(categoryId);

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,
                        cardFragment, null)
                .commit();
    }

    public void userAnswered(boolean guessed){
        // TODO: 2018-03-21
        stopTimer();
        game.setAnswer(guessed);
        nextRound();
    }


    public void nextRound(){
        stopTimer();
        game.nextRound();

        if(!game.isGameOver()){
            show(game.getCategoryId());
            startTimer();
        } else {
            // TODO: 2018-03-21 game over fragment
            Log.d(TAG, "nextRound: no next round - game is over");
        }

    }


    public void timeOutInterruption(){
        nextRound();
    }

    public void startGame(int categoryId){

        Log.d(TAG, "startGame: ");

        // TODO: 2018-03-19 check if number of notGuessed cards in DB is sufficient
        // TODO: 2018-03-19   if not -> get new cards from cloud to DB
        checkIfRecordsInDBAreSufficient();

        game = Game.getInstance();
        game.setCategoryId(categoryId);

        // show card
        show(game.getCategoryId());

        // start countdown timer
        startTimer();

    }

    public void cancelGame(){
        Log.d(TAG, "stopGame: ");

        game = null;
        stopTimer();
    }

    private void checkIfRecordsInDBAreSufficient() {
        // TODO: 2018-03-21
    }

    private void startTimer(){

        countDownTimer = new CountDownTimer(game.ROUND_TIME_MILLIS, game.COUNT_INTERVAL_MILLIS) {

            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.d(TAG, "onFinish: done!");
                timeOutInterruption();
            }
        }.start();
    }

    private void stopTimer(){
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }


}
