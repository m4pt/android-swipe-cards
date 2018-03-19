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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import eu.michalbuda.android.swipecards.R;

public class MainActivity extends AppCompatActivity {

    private CategoryListFragment categoryListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Add category list fragment if this is first creation
        if (savedInstanceState == null) {
            categoryListFragment = new CategoryListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, categoryListFragment, CategoryListFragment.TAG).commit();
        }
    }

    /** Shows random card detail fragment */
    public void show() {

        CardFragment cardFragment = CardFragment.forCard();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("card")
                .replace(R.id.fragment_container,
                        cardFragment, null).commit();
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
        categoryListFragment = new CategoryListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, categoryListFragment, null)
                .commit();
    }

    public void show(int categoryId) {
        CardFragment cardFragment = CardFragment.forCard(categoryId);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        cardFragment, null).commit();
    }
}
