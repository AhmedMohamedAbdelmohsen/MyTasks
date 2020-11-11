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
import com.ahmedabdelmohsen.mytasks.databinding.FragmentAllTasksBinding;
import com.ahmedabdelmohsen.mytasks.main.viewmodel.TasksViewModel;
import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AllTasksFragment extends Fragment implements InterfaceRecyclerViewItem {
    private FragmentAllTasksBinding binding;
    private View view;
    private InterfaceRecyclerViewItem listener;
    private TasksViewModel viewModel;
    private TasksListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listener = this;
        // Inflate the layout for this fragment
        binding = FragmentAllTasksBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAllTasks();
    }

    public void getAllTasks() {
        viewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
        binding.rvAllTasks.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvAllTasks.setHasFixedSize(false);

        viewModel.getAllTasks()
                .subscribeOn(Schedulers.computation())
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
                        binding.rvAllTasks.setAdapter(adapter);
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

    }
}