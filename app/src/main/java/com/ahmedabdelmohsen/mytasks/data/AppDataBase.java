package com.ahmedabdelmohsen.mytasks.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;

@Database(entities = TaskModel.class, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract TasksDao tasksDao();

    private static AppDataBase INSTANCE;

    public static synchronized AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "tasks_dp")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return INSTANCE;
    }

    public static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
