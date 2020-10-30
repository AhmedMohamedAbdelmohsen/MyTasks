package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentAddTaskBinding;

public class AddTaskFragment extends Fragment {

    private FragmentAddTaskBinding binding;
    private View view;

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
        addTask();
    }

    public void addTask() {
        binding.btnAddTask.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(AddTaskFragmentDirections.actionAddTaskFragmentToSuccessfullAddFragment()));
    }
}