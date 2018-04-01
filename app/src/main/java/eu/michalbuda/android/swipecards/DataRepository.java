package eu.michalbuda.android.swipecards;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import eu.michalbuda.android.swipecards.db.AppDatabase;
import eu.michalbuda.android.swipecards.db.entity.CardEntity;
import eu.michalbuda.android.swipecards.db.entity.CategoryEntity;

import java.util.List;

import static eu.michalbuda.android.swipecards.ui.CategoryListFragment.TAG;


/**
 * Repository handling the work with cards.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<CardEntity>> mObservableCards;
    private MediatorLiveData<List<CategoryEntity>> mObservableCategories;

    private DataRepository(final AppDatabase database) {
        Log.d(TAG, "DataRepository: constructor started");
        mDatabase = database;
        mObservableCards = new MediatorLiveData<>();

        mObservableCards.addSource(mDatabase.cardDao().loadAllCards(), cardEntities -> {
                    Log.d(TAG, "DataRepository: cardEntities");
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableCards.postValue(cardEntities);
                    }
                });

        mObservableCategories = new MediatorLiveData<>();

        mObservableCategories.addSource(mDatabase.catsDao().loadNotEmptyCats(), categoryEntities -> {
            Log.d(TAG, "DataRepository: categoryEnities");
            if(mDatabase.getDatabaseCreated().getValue() != null){
                Log.d(TAG, "DataRepository: categoryEntities, getdb not null");
                mObservableCategories.postValue(categoryEntities);
            }
        });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of cards from the database and get notified when the data changes.
     */
    public LiveData<List<CardEntity>> getCards() {
        return mObservableCards;
    }

    public LiveData<List<CategoryEntity>> getCategories(){ return mObservableCategories;}

    public LiveData<CardEntity> loadCard(final int cardId) {
        return mDatabase.cardDao().loadCard(cardId);
    }

    public LiveData<CategoryEntity> loadCategory(final int catId){
        return mDatabase.catsDao().loadCat(catId);
    }

    public int rowCount(){
        return mObservableCards.getValue().size();
    }

    public int randomRow(){
        return (int) (Math.random() * rowCount());
    }

    public LiveData<CardEntity> loadCardRandom(){
        return mDatabase.cardDao().loadCardWithOffset(randomRow());
    }

    public LiveData<CardEntity> loadCardRandomFromGroup(int categoryId){
        return mDatabase.cardDao().loadCardRandomFromGroup(categoryId, randomRow());
    }

}
