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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import eu.michalbuda.android.swipecards.R;
import eu.michalbuda.android.swipecards.databinding.CardFragmentBinding;
import eu.michalbuda.android.swipecards.db.entity.CardEntity;
import eu.michalbuda.android.swipecards.viewmodel.CardViewModel;


public class CardFragment extends Fragment {

    private static final String KEY_CARD_ID = "card_id";

    private CardFragmentBinding mBinding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.card_fragment, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CardViewModel.Factory factory = new CardViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_CARD_ID));

        final CardViewModel model = ViewModelProviders.of(this, factory)
                .get(CardViewModel.class);

        mBinding.setCardViewModel(model);

        subscribeToModel(model);
    }

    private void subscribeToModel(final CardViewModel model) {

        // Observe card data
        model.getObservableCard().observe(this, new Observer<CardEntity>() {
            @Override
            public void onChanged(@Nullable CardEntity cardEntity) {
                model.setCard(cardEntity);
            }
        });


    }

    /** Creates card fragment for specific card ID */
    public static CardFragment forCard(int cardId) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CARD_ID, cardId);
        fragment.setArguments(args);
        return fragment;
    }
}
