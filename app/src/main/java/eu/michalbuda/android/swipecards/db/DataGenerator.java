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



    public static List<CardEntity> generateCards() {
        /* TODO */
        List<CardEntity> cards = new ArrayList<>(FIRST.length * SECOND.length);
        Random rnd = new Random();
        for (int i = 0; i < FIRST.length; i++) {
            for (int j = 0; j < SECOND.length; j++) {
                CardEntity card = new CardEntity();
                card.setName(FIRST[i] + " " + SECOND[j]);
                card.setGuessed(0);
                cards.add(card);
            }
        }

        return cards;
    }
}
