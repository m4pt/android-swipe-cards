package eu.michalbuda.android.swipecards.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import eu.michalbuda.android.swipecards.SwipeCardsApp;
import eu.michalbuda.android.swipecards.db.entity.CardEntity;
import eu.michalbuda.android.swipecards.db.entity.CategoryEntity;

/**
 * Created by Michal Buda on 2018-03-08.
 */

public class CategoryListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<CategoryEntity>> mObservableCategories;

    public CategoryListViewModel(@NonNull Application application) {
        super(application);

        mObservableCategories = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableCategories.setValue(null);

        LiveData<List<CategoryEntity>> categories = ((SwipeCardsApp) application).getRepository().getCategories();

        // observe the changes of the cards from the database and forward them
        mObservableCategories.addSource(categories, mObservableCategories::setValue);

    }

    /**
     * Expose the LiveData Categories query so the UI can observe it.
     */
    public LiveData<List<CategoryEntity>> getCategories() {
        return mObservableCategories;
    }
}
