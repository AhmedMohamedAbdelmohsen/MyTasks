package com.ahmedabdelmohsen.mytasks.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTask(TaskModel taskModel);

    @Query("select * from tasks_table where status =:status")
    public LiveData<List<TaskModel>> getAllTasksByStatus(boolean status);

}
