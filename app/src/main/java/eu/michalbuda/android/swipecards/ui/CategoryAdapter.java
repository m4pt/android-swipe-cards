package eu.michalbuda.android.swipecards.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import eu.michalbuda.android.swipecards.R;
import eu.michalbuda.android.swipecards.databinding.CategoryItemBinding;
import eu.michalbuda.android.swipecards.model.Category;

import static eu.michalbuda.android.swipecards.ui.CategoryListFragment.TAG;

/**
 * Created by Michal Buda on 2018-03-08.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    List<? extends Category> mCategoryList;

    @Nullable
    private final CategoryClickCallback mCategoryClickCallback;

    public CategoryAdapter(@Nullable CategoryClickCallback clickCallback){
        mCategoryClickCallback = clickCallback;
    }

    public void setCategoryList(final List<? extends Category> categoryList){
        if(mCategoryList == null) {
            mCategoryList = categoryList;
            notifyItemRangeInserted(0, categoryList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCategoryList.size();
                }

                @Override
                public int getNewListSize() {
                    return categoryList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mCategoryList.get(oldItemPosition).getId() == categoryList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Category newCategory = mCategoryList.get(newItemPosition);
                    Category oldCategory = mCategoryList.get(oldItemPosition);
                    return newCategory.getId() == oldCategory.getId() && Objects.equals(newCategory.getName(), oldCategory.getName());
                }
            });
            mCategoryList = categoryList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CategoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.category_item, parent, false);
        binding.setCallback(mCategoryClickCallback);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.binding.setCategory(mCategoryList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCategoryList == null ? 0 : mCategoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        final CategoryItemBinding binding;

        public CategoryViewHolder(CategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
