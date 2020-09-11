package com.java.linzexi.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NewsEntity> newsEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(NewsEntity... newsEntities);

    @Update
    void updateNews(NewsEntity... newsEntities);

    @Query("select * from NewsEntity where type = :type order by time desc limit :size offset :offset")
    List<NewsEntity> loadNews(final String type, final int offset, final int size);

    @Query("select * from NewsEntity where _id = :id")
    NewsEntity loadNews(final String id);

    @Query("select * from NewsEntity where (type = 'paper' or type = 'news') and title like '%'||:query||'%' order by time desc")
    List<NewsEntity> searchNewsTitle(final String query);

    @Query("select * from NewsEntity where (type = 'paper' or type = 'news') and title like '%'||:query||'%' order by time desc limit :size")
    List<NewsEntity> searchNewsTitle(final String query, final int size);
}
