package com.example.roomlivedatademo;

/* 
Created by Ashish Bharam on 23-May-20 at 12:37 AM.
Copyright (c) 2020 Ashish Bharam. All rights reserved.
*/

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
   private String mWord;

    public Word(@NonNull String mWord) {
        this.mWord = mWord;
    }

    @NonNull
    public String getWord() {
        return mWord;
    }
}
