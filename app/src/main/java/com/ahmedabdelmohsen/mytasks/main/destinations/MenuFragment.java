package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentMenuBinding;


public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeMenu();
    }

    public void closeMenu() {
        binding.ibtnCloseMenu.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.bounce);
            binding.ibtnCloseMenu.setAnimation(animation);

            requireActivity().onBackPressed();
        });
    }
}