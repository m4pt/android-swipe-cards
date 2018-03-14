package eu.michalbuda.android.swipecards.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import eu.michalbuda.android.swipecards.model.Category;

/**
 * Created by Michal Buda on 2018-03-07.
 */

@Entity(tableName = "categories")
public class CategoryEntity implements Category {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public CategoryEntity() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }


}
