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

package eu.michalbuda.android.swipecards.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import eu.michalbuda.android.swipecards.db.entity.CardEntity;

@Dao
public interface CardDao {
    @Query("SELECT * FROM cards")
    LiveData<List<CardEntity>> loadAllCards();

    @Query("SELECT * FROM cards LIMIT 1 OFFSET :offset")
    LiveData<CardEntity> loadCardWithOffset(int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CardEntity> cards);

    @Query("select * from cards where id = :cardId")
    LiveData<CardEntity> loadCard(int cardId);

    @Query("select * from cards where id = :cardId")
    CardEntity loadCardSync(int cardId);

    @Query("select * from cards where category = :categoryId LIMIT 1 OFFSET :offset")  //OFFSET :offset
    LiveData<CardEntity> loadRandomCardFromGroup(int categoryId, int offset);

}
