package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentBlankBinding;

public class BlankFragment extends Fragment {

    private FragmentBlankBinding binding;
    private View view;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBlankBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setPOstDelay(view);
    }

    public void setPOstDelay(View view) {
        handler.postDelayed(() -> {
            Navigation.findNavController(view).navigate(BlankFragmentDirections.actionBlankFragmentToAddTaskFragment());
        }, 700);
    }
}