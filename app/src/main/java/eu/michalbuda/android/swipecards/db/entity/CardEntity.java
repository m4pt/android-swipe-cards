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

package eu.michalbuda.android.swipecards.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import eu.michalbuda.android.swipecards.model.Card;

@Entity(tableName = "cards")
public class CardEntity implements Card {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int guessed; // 0 - no views by user, 1 - guessed, 2 - not guessed

    public CardEntity() {
        this.guessed = 0;
    }

    public CardEntity(String name) {
        this.name = name;
    }

    public CardEntity(int id, String name, int guessed) {
        this.id = id;
        this.name = name;
        this.guessed = guessed;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getGuessed() {
        return guessed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGuessed(int guessed) {
        this.guessed = guessed;
    }
}