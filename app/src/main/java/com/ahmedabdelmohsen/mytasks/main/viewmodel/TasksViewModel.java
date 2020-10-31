package com.ahmedabdelmohsen.mytasks.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ahmedabdelmohsen.mytasks.data.Repository;
import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class TasksViewModel extends AndroidViewModel {
    private final Repository repository;

    public TasksViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public Completable addTask(TaskModel taskModel) {
        return repository.addTask(taskModel);
    }

    public Observable<List<TaskModel>> getAllTasksDate(String date) {
        return repository.getAllTasksByDate(date);
    }

    public Observable<List<TaskModel>> getAllTasksByOtherDate(String today, String tomorrow) {
        return repository.getAllTasksByOtherDate(today, tomorrow);
    }
}
