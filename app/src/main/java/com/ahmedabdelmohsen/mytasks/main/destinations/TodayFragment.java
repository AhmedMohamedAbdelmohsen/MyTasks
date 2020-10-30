package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.annotation.SuppressLint;
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
import com.ahmedabdelmohsen.mytasks.databinding.FragmentTodayBinding;
import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class TodayFragment extends Fragment {
    private FragmentTodayBinding binding;
    private View view;
    private ArrayList<TaskModel> list = new ArrayList<>();
    private TasksListAdapter adapter = new TasksListAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTodayBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setData();

        binding.rvToday.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvToday.setHasFixedSize(true);
        binding.rvToday.setAdapter(adapter);
        adapter.setList(list);
    }

    public void setData() {
        list.add(new TaskModel(1, "Finish My Work on the new app", "1/7/2020", false));
        list.add(new TaskModel(2, "Sleep and take a rest", "11/7/2020", false));
        list.add(new TaskModel(3, "Drink My Coffee", "20/7/2020", true));
        list.add(new TaskModel(4, "Play Football with my friends", "21/7/2020", false));
        list.add(new TaskModel(5, "Stay with My Family and watch TV", "15/7/2020", true));
        list.add(new TaskModel(6, "Design a new app and UX & UI", "17/7/2020", true));
        list.add(new TaskModel(7, "Upload My App on Play Store as soon as", "10/7/2020", true));
        list.add(new TaskModel(8, "Finish My Work on the new app", "19/7/2020", false));

    }
}