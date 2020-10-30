package com.ahmedabdelmohsen.mytasks.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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

    public Observable<List<TaskModel>> getAllTasks() {
        return repository.getAllTasks();
    }
}
