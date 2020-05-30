package com.example.roomlivedatademo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* 
Created by Ashish Bharam on 23-May-20 at 12:59 AM.
Copyright (c) 2020 Ashish Bharam. All rights reserved.
*/

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDAO wordDAO();

    private static WordRoomDatabase Instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static WordRoomDatabase getDatabase(final Context context) {
        if (Instance == null) {
            synchronized (WordRoomDatabase.class) { // Thread safe Singleton double checked locking used
                if (Instance == null) {
                    // Create database here
                    Instance = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return Instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(Instance).execute();

            /*databaseWriteExecutor.execute(() ->{
                String[] words = {"Room", "LiveData", "ViewModel"};
                WordDAO dao = Instance.wordDAO();
                dao.deleteAll();
                Word word = new Word("Hello");
                dao.insert(word);

                for (String word: words) {
                    Word w = new Word(word);
                    dao.insert(w);
                }
            });*/
            /* Here is the code for creating the callback within the WordRoomDatabase class.
            Because you cannot do Room database operations on the UI thread,
            onOpen() uses the previously defined databaseWriteExecutor to execute a lambda
            on a background thread. The lambda deletes the contents of the database,
            then populates it with the words.
            PopulateDbAsync method was used to do the same task in background.
            Now databaseWriteExecutor is the new technique.
            */
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final WordDAO mDao;
        String[] words = {"Room", "LiveData", "ViewModel"};

        public PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();

          /*  for (int i = 0; i <= words.length - 1; i++) {
                Word word = new Word(words[i]);
                mDao.insert(word);
            }*/
            for (String word: words) {
                Word w = new Word(word);
                mDao.insert(w);
            }
            return null;
        }
    }
}
    // fallbackToDestructiveMigration() migration strategy for the database.
    // Wipes and rebuilds instead of migrating
    // if no Migration object.
    // Migration is not part of this practical.