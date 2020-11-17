package com.ahmedabdelmohsen.mytasks.dialogs;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.ahmedabdelmohsen.mytasks.AlarmReceiver;
import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.main.viewmodel.TasksViewModel;

import java.util.Locale;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskDialog extends Dialog {

    public Activity activity;
    public EditText body;
    public ImageButton delete, hear;
    public Button save;
    public String bodyTask;
    public int idTask;
    public int requestCodeTask;
    public TasksViewModel viewModel;
    public TextToSpeech textToSpeech;

    public TaskDialog(@NonNull Activity activity, String bodyTask, int idTask, int requestCodeTask) {
        super(activity);
        this.activity = activity;
        this.bodyTask = bodyTask;
        this.idTask = idTask;
        this.requestCodeTask = requestCodeTask;
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
        hear = findViewById(R.id.iv_hear);
        save = findViewById(R.id.btn_save);

        body.setText(bodyTask);

        saveNewEditOfTask(); //save the new body of the task
        deleteTask(); //delete the task

        textToSpeech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int language = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(activity, "your phone language must be english to hear your task speech", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(activity, body.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, "initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        hearVoice();
    }

    public void saveNewEditOfTask() {
        save.setOnClickListener(v -> {
            Toast.makeText(activity, "Task has updated successfully", Toast.LENGTH_SHORT).show();
            viewModel.updateBody(body.getText().toString(), idTask)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            //textToSpeech.setPitch(0.1f);

                            dismiss();
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                        }
                    });

        });
    }

    public void hearVoice() {
        hear.setOnClickListener(v -> {
            Toast.makeText(activity, "this is work only if the text is english", Toast.LENGTH_SHORT).show();
            textToSpeech.setSpeechRate(0.7f);
            String s = body.getText().toString();
            int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
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
                                    AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                                    Intent intent = new Intent(activity, AlarmReceiver.class);
                                    PendingIntent pendingIntent =
                                            PendingIntent.getBroadcast(activity, requestCodeTask, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                    alarmManager.cancel(pendingIntent);
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

    @Override
    protected void onStop() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onStop();
    }
}
