package com.example.roomlivedatademo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


/* 
Created by Ashish Bharam on 23-May-20 at 01:41 AM.
Copyright (c) 2020 Ashish Bharam. All rights reserved.
*/

public class WordViewModel extends AndroidViewModel {
    //If you need the application context (which has a lifecycle that lives as long as the application does),
    // use AndroidViewModel.
    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word){
        mRepository.insert(word);
    }
}
