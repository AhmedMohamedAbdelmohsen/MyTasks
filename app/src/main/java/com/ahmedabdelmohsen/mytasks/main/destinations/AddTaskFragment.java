package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentAddTaskBinding;
import com.ahmedabdelmohsen.mytasks.main.viewmodel.TasksViewModel;
import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTaskFragment extends Fragment {

    private FragmentAddTaskBinding binding;
    private View view;
    private TasksViewModel viewModel;
    private MaterialDatePicker.Builder<Long> builder;
    private MaterialDatePicker<Long> materialDatePicker;
    private CalendarConstraints.Builder constraintsBuilder;
    private Calendar calendar;
    private String selectedDate = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class); //set view model
        calendar = Calendar.getInstance(); // set calender

        selectToday();
        selectTomorrow();
        selectDate();
        addTask();
    }

    public void addTask() {
        binding.btnAddTask.setOnClickListener(v -> {

            String body = binding.editText.getText().toString();
            if (TextUtils.isEmpty(body)) {
                binding.editText.setError("please write your task");
            } else if (TextUtils.isEmpty(selectedDate)) {
                binding.tvWhen.setError("please choose day");
                Toast.makeText(requireActivity(), "please choose day", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.addTask(new TaskModel(body, selectedDate, false))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                            }
                        });
                Navigation.findNavController(v).navigate(AddTaskFragmentDirections.actionAddTaskFragmentToSuccessfullAddFragment());
            }
        });
    }

    public void selectDate() {
        //set calender open on today date
        calendar.clear();
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        //set constraints builder
        constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());
        //get DatePicker fragment
        builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Calender");
        builder.setSelection(today);
        builder.setCalendarConstraints(constraintsBuilder.build());
        materialDatePicker = builder.build();

        binding.tvSelectDate.setOnClickListener(v -> {
            binding.tvSelectDate.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.tvTomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.tvToday.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            materialDatePicker.show(requireActivity().getSupportFragmentManager(), "DATE_PICKER");
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                selectedDate = simpleDateFormat.format(materialDatePicker.getSelection());
                Toast.makeText(requireActivity(), selectedDate, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void selectToday() {
        binding.tvToday.setOnClickListener(v -> {
            binding.tvSelectDate.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.tvTomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.tvToday.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));

            calendar.clear();
            calendar = Calendar.getInstance(); // set calender
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            selectedDate =  day + "/" + month + "/" + year;
           // selectedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
            Toast.makeText(requireActivity(), selectedDate, Toast.LENGTH_SHORT).show();
        });
    }

    public void selectTomorrow() {
        binding.tvTomorrow.setOnClickListener(v -> {
            binding.tvSelectDate.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.tvTomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.tvToday.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            calendar.clear();
            calendar = Calendar.getInstance(); // set calender
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            selectedDate =  day + "/" + month + "/" + year;
          //  selectedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
            Toast.makeText(requireActivity(), selectedDate, Toast.LENGTH_SHORT).show();
        });
    }
}