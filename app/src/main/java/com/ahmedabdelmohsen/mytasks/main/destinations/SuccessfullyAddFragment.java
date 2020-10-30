package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedabdelmohsen.mytasks.databinding.FragmentSuccessfullyAddBinding;

public class SuccessfullyAddFragment extends Fragment {

    private FragmentSuccessfullyAddBinding binding;
    private View view;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSuccessfullyAddBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPostDelayed(view);
    }

    public void setPostDelayed(View view) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        }, 2000);
    }
}