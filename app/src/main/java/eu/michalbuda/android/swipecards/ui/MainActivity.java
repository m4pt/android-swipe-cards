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
    protected static Game game;
    private static CategoryListFragment categoryListFragment;
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

    public void setOrientationLandscape() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void setOrientationPortrait() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void hideStatusBar(boolean hide) {
        View decorView = getWindow().getDecorView();

        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (hide) {
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

    private void show(int categoryId) {

        cardFragment = CardFragment.forCard(categoryId);

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,
                        cardFragment, null)
                .commit();
    }

    public void userAnswered(boolean guessed) {
        // TODO: 2018-03-21
        stopTimer();
        game.setAnswer(guessed);
        nextRound();
    }

    public void nextRound() {
        stopTimer();

        // TODO: 2018-04-03 next round view (few seconds for animation and next round starts)

        game.nextRound();

        if (!game.isGameOver()) {
            show(game.getCategoryId());
            startTimer();
        } else {
            // TODO: 2018-03-21 game over fragment
            Log.d(TAG, "nextRound: no next round - game is over");
            gameOver();
        }

    }

    private void gameOver() {
        // TODO: 2018-04-03     game result/statistics view
        onBackPressed();
    }

    public void startGame(int categoryId) {

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

    public void cancelGame() {
        Log.d(TAG, "stopGame: ");

        game = null;
        stopTimer();
    }

    private void checkIfRecordsInDBAreSufficient() {
        // TODO: 2018-03-21
    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(Game.ROUND_TIME_MILLIS, Game.COUNT_INTERVAL_MILLIS) {

            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: seconds remaining: " + millisUntilFinished / 1000);
                // TODO: 2018-04-03 sound from remaining <5 seconds
            }

            public void onFinish() {

                // TODO: 2018-04-03 end of round sound if time is over

                Log.d(TAG, "onFinish: done!");
                timeOutInterruption();
            }
        }.start();
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void timeOutInterruption() {
        nextRound();
    }


}
