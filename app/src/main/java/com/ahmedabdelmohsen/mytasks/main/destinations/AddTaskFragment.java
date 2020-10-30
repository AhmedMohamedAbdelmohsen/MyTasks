package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentAddTaskBinding;
import com.ahmedabdelmohsen.mytasks.main.viewmodel.TasksViewModel;
import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTaskFragment extends Fragment {

    private FragmentAddTaskBinding binding;
    private View view;
    private TasksViewModel viewModel;

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

        viewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
        addTask();
    }

    public void addTask() {
        binding.btnAddTask.setOnClickListener(v -> {
            String body = binding.editText.getText().toString();
            viewModel.addTask(new TaskModel(body, "17/10/2020", false))
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
        });
    }
}