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

import com.ahmedabdelmohsen.mytasks.TasksListAdapter;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentOtherDaysBinding;
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

public class OtherDaysFragment extends Fragment {
    private FragmentOtherDaysBinding binding;
    private View view;
    private TasksListAdapter adapter = new TasksListAdapter();
    private TasksViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOtherDaysBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllTasksInOtherDate();
    }
    //get all other tasks
    public void getAllTasksInOtherDate() {
        viewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
        binding.rvThisWeek.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvThisWeek.setHasFixedSize(true);
        binding.rvThisWeek.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String tomorrowDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        Calendar calendar2 = Calendar.getInstance();
        String todayDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar2.getTime());

        viewModel.getAllTasksByOtherDate(todayDate, tomorrowDate).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TaskModel>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<TaskModel> taskModels) {
                        adapter.setList((ArrayList<TaskModel>) taskModels);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}