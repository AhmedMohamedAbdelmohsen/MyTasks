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
import android.widget.Toast;

import com.ahmedabdelmohsen.mytasks.InterfaceRecyclerViewItem;
import com.ahmedabdelmohsen.mytasks.TasksListAdapter;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentOtherDaysBinding;
import com.ahmedabdelmohsen.mytasks.dialogs.TaskDialog;
import com.ahmedabdelmohsen.mytasks.main.viewmodel.TasksViewModel;
import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OtherDaysFragment extends Fragment implements InterfaceRecyclerViewItem {
    private FragmentOtherDaysBinding binding;
    private View view;
    private TasksListAdapter adapter;
    private TasksViewModel viewModel;
    private InterfaceRecyclerViewItem listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listener = this;
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
        binding.rvThisWeek.setHasFixedSize(false);
        binding.rvThisWeek.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        String tomorrowDate = day + "/" + month + "/" + year;
        Calendar calendar2 = Calendar.getInstance();
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
        int month2 = calendar2.get(Calendar.MONTH) + 1;
        int year2 = calendar2.get(Calendar.YEAR);
        String todayDate = day2 + "/" + month2 + "/" + year2;

        viewModel.getAllTasksByOtherDate(todayDate, tomorrowDate).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TaskModel>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<TaskModel> taskModels) {
                        if (taskModels.size() == 0) {
                            binding.linearLayout.setVisibility(View.VISIBLE);
                        } else {
                            binding.linearLayout.setVisibility(View.GONE);
                        }
                        adapter = new TasksListAdapter(listener, (ArrayList<TaskModel>) taskModels);
                        binding.rvThisWeek.setAdapter(adapter);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onTaskClick(int id, boolean status) {
        if (status) {
            viewModel.update(false, id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        }
                    });
        } else {
            viewModel.update(true, id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                        }
                    });
        }
    }

    @Override
    public void onLongTaskClick(int id, String body, int requestCode) {
        TaskDialog taskDialog = new TaskDialog(requireActivity(), body, id,requestCode);
        taskDialog.show();
    }
}