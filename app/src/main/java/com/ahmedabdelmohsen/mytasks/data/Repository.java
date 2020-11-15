package com.ahmedabdelmohsen.mytasks.data;

import android.app.Application;

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

    public Observable<List<TaskModel>> getAllTasksByDate(String date) {
        return tasksDao.getAllTasksByDate(date);
    }

    public Observable<List<TaskModel>> getAllTasksByOtherDate(String today, String tomorrow) {
        return tasksDao.getAllTasksByOtherDate(today, tomorrow);
    }

    public Observable<List<TaskModel>> getAllTasksByStatus(boolean status) {
        return tasksDao.getAllTasksByStatus(status);
    }

    public Observable<List<TaskModel>> getAllTasks() {
        return tasksDao.getAllTasks();
    }

    public Completable update(boolean status, int id) {
        return tasksDao.update(status, id);
    }

    public Completable updateBody(String body, int id) {
        return tasksDao.updateBody(body, id);
    }

    public Completable deleteTask(int id) {
        return tasksDao.deleteTask(id);
    }

    public Completable deleteAllTasksByDate(String date) {
        return tasksDao.deleteAllTasksByDate(date);
    }

    public Completable deleteAllTasksByOtherDate(String today, String tomorrow) {
        return tasksDao.deleteAllTasksByOtherDate(today, tomorrow);
    }
}
