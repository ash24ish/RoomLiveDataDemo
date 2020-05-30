package com.example.roomlivedatademo;

/* 
Created by Ashish Bharam on 23-May-20 at 01:24 AM.
Copyright (c) 2020 Ashish Bharam. All rights reserved.
*/

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDAO mWordDAO;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDAO = db.wordDAO();
        mAllWords = mWordDAO.getAllWords();
    }

    LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

   public void insert(Word word){
        new insertAsyncTask(mWordDAO).execute(word);
    }

  /*  void insert(Word word){
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDAO.insert(word);
        });
    }*/ //new way of doing it without asynctask

    /*
    We need to not run the insert on the main thread, so we use the ExecutorService
    we created in the WordRoomDatabase to perform the insert on a background thread.
    */

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDAO mAsyncDAO;
        private insertAsyncTask(WordDAO dao) {
            mAsyncDAO = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncDAO.insert(words[0]);
            return null;
        }
    }
}
