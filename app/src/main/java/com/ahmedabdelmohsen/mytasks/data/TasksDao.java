package com.ahmedabdelmohsen.mytasks.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("select * from tasks_table where date =:date")
    Observable<List<TaskModel>> getAllTasksByDate(String date);

    @Query("select * from tasks_table where date !=:today and date !=:tomorrow")
    Observable<List<TaskModel>> getAllTasksByOtherDate(String today, String tomorrow);

    @Query("select * from tasks_table where status =:status")
    Observable<List<TaskModel>> getAllTasksByStatus(boolean status);

    @Query("select * from tasks_table")
    Observable<List<TaskModel>> getAllTasks();

    @Query("update tasks_table set status=:status where id = :id")
    Completable update(boolean status, int id);

    @Query("update tasks_table set body=:body where id = :id")
    Completable updateBody(String body, int id);

    @Query("delete from tasks_table where id=:id")
    Completable deleteTask(int id);

    @Query("delete from tasks_table where date =:date")
    Completable deleteAllTasksByDate(String date);

    @Query("delete from tasks_table where date !=:today and date !=:tomorrow")
    Completable deleteAllTasksByOtherDate(String today, String tomorrow);
}
