package com.java.linzexi.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NewsEntity> newsEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(NewsEntity... newsEntities);

    @Query("select * from NewsEntity where type = :type order by time desc limit :size offset :offset")
    List<NewsEntity> loadNews(final String type, final int offset, final int size);

    @Query("select * from NewsEntity where _id = :id")
    NewsEntity loadNews(final String id);

    /*@Query("select * from NewsEntity where type = :type order by time desc")
    DataSource.Factory<Integer, NewsEntity> newsByTime(final String type);*/

    /*@Query("SELECT NewsEntity.* FROM NewsEntity JOIN NewsFts ON (NewsEntity._id = productsFts.rowid) "
            + "WHERE NewsFts MATCH :query")
    LiveData<List<NewsEntity>> searchAllNews(String query);*/
}