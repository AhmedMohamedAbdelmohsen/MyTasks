package com.ahmedabdelmohsen.mytasks.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.main.viewmodel.TasksViewModel;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskDialog extends Dialog {

    public Activity activity;
    public EditText body;
    public ImageButton delete;
    public Button save;
    public String bodyTask;
    public int idTask;
    public TasksViewModel viewModel;

    public TaskDialog(@NonNull Activity activity, String bodyTask, int idTask) {
        super(activity);
        this.activity = activity;
        this.bodyTask = bodyTask;
        this.idTask = idTask;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_tasks);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);

        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(TasksViewModel.class);
        body = findViewById(R.id.et_body);
        delete = findViewById(R.id.iv_delete);
        save = findViewById(R.id.btn_save);

        body.setText(bodyTask);

        saveNewEditOfTask(); //save the new body of the task
        deleteTask(); //delete the task
    }

    public void saveNewEditOfTask() {
        save.setOnClickListener(v -> {
            viewModel.updateBody(body.getText().toString(), idTask)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(activity, "Task has updated successfully", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                        }
                    });
        });
    }

    public void deleteTask() {
        delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Delete Task");
            builder.setMessage("are you sure you want to delete\nthis task ??");
            builder.setPositiveButton("Yes", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    viewModel.deleteTask(idTask)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    Toast.makeText(activity, "Task has deleted successfully", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }

                                @Override
                                public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                                }
                            });
                }
            });
            builder.setNegativeButton("No", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.create().show();
        });
    }
}
