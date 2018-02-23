package eu.michalbuda.android.swipecards;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import eu.michalbuda.android.swipecards.db.AppDatabase;
import eu.michalbuda.android.swipecards.db.entity.CardEntity;

import java.util.List;

import static eu.michalbuda.android.swipecards.ui.CardListFragment.TAG;

/**
 * Repository handling the work with cards.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<CardEntity>> mObservableCards;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableCards = new MediatorLiveData<>();

        mObservableCards.addSource(mDatabase.cardDao().loadAllCards(),
                cardEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableCards.postValue(cardEntities);
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

    public LiveData<CardEntity> loadCard(final int cardId) {
        return mDatabase.cardDao().loadCard(cardId);
    }

    public int rowCount(){
        return mObservableCards.getValue().size();
    }

    public int randomRow(){
        return (int) (Math.random() * rowCount());
    }

    public LiveData<CardEntity> loadCardRandom(){
        Log.d(TAG, "loadCardRandom: ");
        return mDatabase.cardDao().loadCardWithOffset(randomRow());
    }

}
