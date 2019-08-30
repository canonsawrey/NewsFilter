package com.example.premierleaguenewsfilter.data;


import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DatabaseModule {
    @Provides
    @Singleton
    static AppDatabase providesDatabase(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, "players-db").build();
    }
}
