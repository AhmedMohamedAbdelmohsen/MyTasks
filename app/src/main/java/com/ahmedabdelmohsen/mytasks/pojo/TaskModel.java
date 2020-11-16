package com.ahmedabdelmohsen.mytasks.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks_table")
public class TaskModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String body;
    private String date;
    private boolean status;
    private int requestCode;

    public TaskModel(String body, String date, boolean status, int requestCode) {
        this.body = body;
        this.date = date;
        this.status = status;
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
