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

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import eu.michalbuda.android.swipecards.R;
import eu.michalbuda.android.swipecards.databinding.CardItemBinding;
import eu.michalbuda.android.swipecards.model.Card;

import java.util.List;
import java.util.Objects;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    List<? extends Card> mCardList;

    @Nullable
    private final CardClickCallback mCardClickCallback;

    public CardAdapter(@Nullable CardClickCallback clickCallback) {
        mCardClickCallback = clickCallback;
    }

    public void setCardList(final List<? extends Card> cardList) {
        if (mCardList == null) {
            mCardList = cardList;
            notifyItemRangeInserted(0, cardList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCardList.size();
                }

                @Override
                public int getNewListSize() {
                    return cardList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mCardList.get(oldItemPosition).getId() ==
                            cardList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Card newCard = mCardList.get(newItemPosition);
                    Card oldCard = mCardList.get(oldItemPosition);
                    return newCard.getId() == oldCard.getId()
                            && Objects.equals(newCard.getName(), oldCard.getName())
                            && newCard.getGuessed() == oldCard.getGuessed();
                }
            });
            mCardList = cardList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.card_item,
                        parent, false);
        binding.setCallback(mCardClickCallback);
        return new CardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.binding.setCard(mCardList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCardList == null ? 0 : mCardList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {

        final CardItemBinding binding;

        public CardViewHolder(CardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
