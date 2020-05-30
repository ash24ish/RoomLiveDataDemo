package com.example.roomlivedatademo;

/* 
Created by Ashish Bharam on 23-May-20 at 12:47 AM.
Copyright (c) 2020 Ashish Bharam. All rights reserved.
*/

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    //There is no convenience annotation for deleting multiple entities, so annotate the deleteAll() method with the generic @Query.
    // Provide the SQL query as a string parameter to @Query.
    @Query("DELETE FROM WORD_TABLE")
    void deleteAll();

    @Query("Select * from word_table order by word ASC")
    LiveData<List<Word>> getAllWords();

    @Query("Select * from word_table order by word DESC")
    LiveData<List<Word>> getAllWordsDesc();



}
