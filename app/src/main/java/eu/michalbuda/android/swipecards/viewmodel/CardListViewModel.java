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

package eu.michalbuda.android.swipecards.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import eu.michalbuda.android.swipecards.SwipeCardsApp;
import eu.michalbuda.android.swipecards.db.entity.CardEntity;

import java.util.List;

public class CardListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<CardEntity>> mObservableCards;

    public CardListViewModel(Application application) {
        super(application);

        mObservableCards = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableCards.setValue(null);

        LiveData<List<CardEntity>> cards = ((SwipeCardsApp) application).getRepository().getCards();

        // observe the changes of the cards from the database and forward them
        mObservableCards.addSource(cards, mObservableCards::setValue);
    }

    /**
     * Expose the LiveData Cards query so the UI can observe it.
     */
    public LiveData<List<CardEntity>> getCards() {
        return mObservableCards;
    }
}
