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

import android.arch.lifecycle.Lifecycle;
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
import eu.michalbuda.android.swipecards.databinding.ListFragmentBinding;
import eu.michalbuda.android.swipecards.db.entity.CardEntity;
import eu.michalbuda.android.swipecards.model.Card;
import eu.michalbuda.android.swipecards.viewmodel.CardListViewModel;

import java.util.List;

public class CardListFragment extends Fragment {

    public static final String TAG = "CardListViewModel";

    private CardAdapter mCardAdapter;

    private ListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setOrientationPortrait();

        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);

        mCardAdapter = new CardAdapter(mCardClickCallback);
        mBinding.cardsList.setAdapter(mCardAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final CardListViewModel viewModel =
                ViewModelProviders.of(this).get(CardListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(CardListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getCards().observe(this, new Observer<List<CardEntity>>() {
            @Override
            public void onChanged(@Nullable List<CardEntity> myCards) {
                if (myCards != null) {
                    mBinding.setIsLoading(false);
                    mCardAdapter.setCardList(myCards);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
            }
        });
    }

    private final CardClickCallback mCardClickCallback = new CardClickCallback() {
        @Override
        public void onClick(Card card) {

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show();
            }
        }
    };
}
