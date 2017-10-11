package com.gm.repository.di.module;

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import dagger.Module;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Dagger RoomDatabase Module
 */
@Module
public class DBModule {

    public interface RoomConfiguration<DB extends RoomDatabase> {
        void configRoom(Context context, RoomDatabase.Builder<DB> builder);

        RoomConfiguration EMPTY = new RoomConfiguration() {
            @Override
            public void configRoom(Context context, RoomDatabase.Builder builder) {

            }
        };
    }
}
