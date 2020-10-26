package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private View view;
    private FragmentManager fragmentManager;
    private final TodayFragment todayFragment = new TodayFragment();
    private final TomorrowFragment tomorrowFragment = new TomorrowFragment();
    private final ThisWeekFragment thisWeekFragment = new ThisWeekFragment();

    public HomeFragment() {
    }

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

        fragmentManager = requireActivity().getSupportFragmentManager();

        setDefaultFragmentContainer(savedInstanceState);
        getTodayFragment();
        getTomorrowFragment();
        getThisWeekFragment();
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
            binding.thisWeek.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            fragmentManager.beginTransaction().replace(R.id.fragment_container, todayFragment).commit();
        });
    }
    //change fragment container to fragment Tomorrow
    private void getTomorrowFragment() {
        binding.tomorrow.setOnClickListener(v -> {
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.thisWeek.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            fragmentManager.beginTransaction().replace(R.id.fragment_container, tomorrowFragment).commit();
        });
    }
    //change fragment container to fragment This Week
    private void getThisWeekFragment() {

        binding.thisWeek.setOnClickListener(v -> {
            binding.thisWeek.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            fragmentManager.beginTransaction().replace(R.id.fragment_container, thisWeekFragment).commit();
        });
    }

}