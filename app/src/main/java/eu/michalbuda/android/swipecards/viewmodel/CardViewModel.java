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
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import eu.michalbuda.android.swipecards.SwipeCardsApp;
import eu.michalbuda.android.swipecards.DataRepository;
import eu.michalbuda.android.swipecards.db.entity.CardEntity;
import eu.michalbuda.android.swipecards.model.Card;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private final LiveData<CardEntity> mObservableCard;

    public ObservableField<CardEntity> card = new ObservableField<>();

    private final int mCardId;


    public CardViewModel(@NonNull Application application, DataRepository repository,
            final int cardId) {
        super(application);
        mCardId = cardId;

        mObservableCard = repository.loadCard(mCardId);
    }

    public LiveData<CardEntity> getObservableCard() {
        return mObservableCard;
    }

    public void setCard(CardEntity card) {
        this.card.set(card);
    }

    /**
     * A creator is used to inject the card ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the card ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mCardId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int cardId) {
            mApplication = application;
            mCardId = cardId;
            mRepository = ((SwipeCardsApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CardViewModel(mApplication, mRepository, mCardId);
        }
    }
}
