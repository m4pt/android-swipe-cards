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

import java.util.List;

import eu.michalbuda.android.swipecards.R;
import eu.michalbuda.android.swipecards.databinding.ListFragmentBinding;
import eu.michalbuda.android.swipecards.db.entity.CardEntity;
import eu.michalbuda.android.swipecards.db.entity.CategoryEntity;
import eu.michalbuda.android.swipecards.model.Category;
import eu.michalbuda.android.swipecards.viewmodel.CardListViewModel;
import eu.michalbuda.android.swipecards.viewmodel.CategoryListViewModel;

/**
 * Created by Michal Buda on 2018-03-08.
 */

public class CategoryListFragment extends Fragment {

    public static final String TAG = "CategoryListViewModel";
    private CategoryAdapter mCategoryAdapter;
    private ListFragmentBinding mBinding;

    private CategoryClickCallback mCategoryClickCallback = new CategoryClickCallback() {
        @Override
        public void onClick(Category category) {
            if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){

                ((MainActivity)getActivity()).startGame(category.getId());
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setOrientationPortrait();
        //((MainActivity) getActivity()).hideStatusBar(false);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);

        mCategoryAdapter = new CategoryAdapter(mCategoryClickCallback);
        mBinding.categoryList.setAdapter(mCategoryAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final CategoryListViewModel categoryViewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);

        subscribeUi(categoryViewModel);

        final CardListViewModel cardListViewModel = ViewModelProviders.of(this).get(CardListViewModel.class);
        subscribeUi(cardListViewModel);
    }

    private void subscribeUi(CategoryListViewModel viewModel) {
        viewModel.getCategories().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(@Nullable List<CategoryEntity> categoryEntities) {
                if(categoryEntities != null){
                    mBinding.setIsLoading(false);
                    mCategoryAdapter.setCategoryList(categoryEntities);
                } else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });
    }

    private void subscribeUi(CardListViewModel viewModel) {
        viewModel.getCards().observe(this, new Observer<List<CardEntity>>() {
            @Override
            public void onChanged(@Nullable List<CardEntity> cardEntities) {
                if(cardEntities != null){
                    mBinding.setIsLoading(false);
                } else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });
    }


}
