package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.ahmedabdelmohsen.mytasks.InterfaceRecyclerViewItem;
import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.TasksListAdapter;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentCalenderBinding;
import com.ahmedabdelmohsen.mytasks.main.viewmodel.TasksViewModel;
import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CalenderFragment extends Fragment implements InterfaceRecyclerViewItem {
    private FragmentCalenderBinding binding;
    private View view;
    private String newDate;
    private TasksListAdapter adapter;
    private TasksViewModel viewModel;
    private InterfaceRecyclerViewItem listener;
    private String todayDate;
    private String tomorrowDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listener = this;
        // Inflate the layout for this fragment
        binding = FragmentCalenderBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setBackButton();
        setDateByDefault();
        getAllTasksByDate();
        getDateFromCalender();
    }

    //set date today by default
    public void setDateByDefault() {
        //today Date
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        newDate = day + "/" + month + "/" + year;
        todayDate = day + "/" + month + "/" + year;
        //tomorrow Date
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_YEAR, 1);
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
        int month2 = calendar2.get(Calendar.MONTH) + 1;
        int year2 = calendar2.get(Calendar.YEAR);
        tomorrowDate = day2 + "/" + month2 + "/" + year2;

    }

    //get Date from Calender View
    private void getDateFromCalender() {
        binding.calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            month = month + 1;
            newDate = day + "/" + month + "/" + year;
            getAllTasksByDate();
        });

    }

    //get all tasks by date in calender
    public void getAllTasksByDate() {
        viewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
        binding.rvTasks.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvTasks.setHasFixedSize(false);

        viewModel.getAllTasksDate(newDate).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TaskModel>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<TaskModel> taskModels) {
                        if (newDate.equals(todayDate)) {
                            binding.tvCurrentDay.setText(R.string.today_you_have);
                        } else if (newDate.equals(tomorrowDate)) {
                            binding.tvCurrentDay.setText(R.string.tomorrow_you_have);
                        } else {
                            binding.tvCurrentDay.setText(R.string.on_this_date);
                        }
                        adapter = new TasksListAdapter(listener, (ArrayList<TaskModel>) taskModels);
                        int size = taskModels.size();
                        binding.tvNumberTasks.setText(String.valueOf(size));
                        adapter.notifyDataSetChanged();
                        binding.rvTasks.setAdapter(adapter);
                    }


                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setBackButton() {
        binding.ibtnBack.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });
    }

    @Override
    public void onTaskClick(int id, boolean status) {

    }

    @Override
    public void onLongTaskClick(int id, String body) {

    }
}