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

package eu.michalbuda.android.swipecards.db;

import eu.michalbuda.android.swipecards.db.entity.CardEntity;
import eu.michalbuda.android.swipecards.db.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    private static final String[] FIRST = new String[]{
            "Specjalna edycja", "Nowe g", "Tanie", "Wysokiej jakosci", "Uzywane"};
    private static final String[] SECOND = new String[]{
            "Malpa", "Kurczak", "Cos tam", "Okular"};

    private static final String[] FIRSTCAT = new String[]{
            "First", "Second", "Third", "Fourth", "Fifth"};
    private static final String[] SECONDCAT = new String[]{
            "Category"};



    public static List<CardEntity> generateCards() {
        /* TODO */
        List<CardEntity> cards = new ArrayList<>(FIRST.length * SECOND.length);
        for (int i = 0; i < FIRST.length; i++) {
            for (int j = 0; j < SECOND.length; j++) {
                CardEntity card = new CardEntity();
                card.setName(FIRST[i] + " " + SECOND[j]);
                //card.setCategory( (int) ((FIRSTCAT.length * SECONDCAT.length) * Math.random() + 1));
                card.setCategory(1);
                cards.add(card);
            }
        }

        return cards;
    }

    public static List<CategoryEntity> generateCategories() {
        /* TODO */
        List<CategoryEntity> cats = new ArrayList<>(FIRSTCAT.length * SECONDCAT.length);
        for (int i = 0; i < FIRSTCAT.length; i++) {
            for (int j = 0; j < SECONDCAT.length; j++) {
                CategoryEntity cat = new CategoryEntity();
                cat.setName(FIRSTCAT[i] + " . " + SECONDCAT[j]);
                cats.add(cat);
            }
        }
        return cats;
    }
}
