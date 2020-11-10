package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ahmedabdelmohsen.mytasks.AddTaskActivity;
import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private View view;
    private FragmentManager fragmentManager;
    private final TodayFragment todayFragment = new TodayFragment();
    private final TomorrowFragment tomorrowFragment = new TomorrowFragment();
    private final OtherDaysFragment otherDaysFragment = new OtherDaysFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentManager = getChildFragmentManager();

        setDefaultFragmentContainer(savedInstanceState);
        getTodayFragment();
        getTomorrowFragment();
        getOtherDaysFragment();
        startReveal();
        openMenu();
    }

    //set fragment container Today by default
    private void setDefaultFragmentContainer(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, todayFragment).commit();
        }
    }

    //change fragment container to fragment Today
    private void getTodayFragment() {
        binding.today.setOnClickListener(v -> {
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.otherDays.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fad_in, R.anim.fad_out)
                    .replace(R.id.fragment_container, todayFragment).commit();
        });
    }

    //change fragment container to fragment Tomorrow
    private void getTomorrowFragment() {
        binding.tomorrow.setOnClickListener(v -> {
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.otherDays.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fad_in, R.anim.fad_out)
                    .replace(R.id.fragment_container, tomorrowFragment).commit();
        });
    }

    //change fragment container to fragment This Week
    private void getOtherDaysFragment() {

        binding.otherDays.setOnClickListener(v -> {
            binding.otherDays.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fad_in, R.anim.fad_out)
                    .replace(R.id.fragment_container, otherDaysFragment).commit();
        });
    }

    //start Edit Activity Reveal
    public void startReveal() {
        final FloatingActionButton button = requireActivity().findViewById(R.id.fab);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), AddTaskActivity.class));
            }

        });
    }

    //start menu fragment
    public void openMenu() {
        binding.ibtnMenu.setOnClickListener(v -> {
            //set animation to menu button icon
            Animation animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.bounce);
            binding.ibtnMenu.setAnimation(animation);

            Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToMenuFragment());
        });
    }
}