package com.ahmedabdelmohsen.mytasks.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class Repository {
    private TasksDao tasksDao;

    public Repository(Application application) {
        AppDataBase appDataBase = AppDataBase.getInstance(application);
        this.tasksDao = appDataBase.tasksDao();
    }

    public Completable addTask(TaskModel taskModel) {
        return tasksDao.addTask(taskModel);
    }

    public Observable<List<TaskModel>> getAllTasks() {
        return tasksDao.getAllTasks();
    }
}
