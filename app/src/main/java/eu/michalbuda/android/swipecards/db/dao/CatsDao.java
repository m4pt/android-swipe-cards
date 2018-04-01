package eu.michalbuda.android.swipecards.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import eu.michalbuda.android.swipecards.db.entity.CardEntity;
import eu.michalbuda.android.swipecards.db.entity.CategoryEntity;

/**
 * Created by Michal Buda on 2018-03-07.
 */
@Dao
public interface CatsDao {
    @Query("SELECT * FROM categories")
    LiveData<List<CategoryEntity>> loadAllCats();

    @Query("SELECT * FROM categories where id IN (SELECT DISTINCT category from cards)")
    LiveData<List<CategoryEntity>> loadNotEmptyCats();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CategoryEntity> cats);

    @Query("select * from categories where id = :catId")
    LiveData<CategoryEntity> loadCat(int catId);

    @Query("select * from categories where id = :catId")
    CategoryEntity loadCatSync(int catId);
}
