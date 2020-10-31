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
import io.reactivex.Single;

@Dao
public interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addTask(TaskModel taskModel);

//    @Query("select * from tasks_table where status =:status")
//    public LiveData<List<TaskModel>> getAllTasksByStatus(boolean status);

    @Query("select * from tasks_table where date =:date")
    Observable<List<TaskModel>> getAllTasksByDate(String date);

    @Query("select * from tasks_table where date !=:today and date !=:tomorrow")
    Observable<List<TaskModel>> getAllTasksByOtherDate(String today, String tomorrow);

    @Query("update tasks_table set status=:status where id = :id")
    void update(boolean status, int id);

}
