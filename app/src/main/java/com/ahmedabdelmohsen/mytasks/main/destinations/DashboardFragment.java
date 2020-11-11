package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.annotation.SuppressLint;
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
import com.ahmedabdelmohsen.mytasks.databinding.FragmentDashboardBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private View view;
    private FragmentManager fragmentManager;
    private final PendingFragment pendingFragment = new PendingFragment();
    private final CompletedFragment completedFragment = new CompletedFragment();
    private final AllTasksFragment allTasksFragment = new AllTasksFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getChildFragmentManager();

        setCurrentDate();
        setBackButton();
        setDefaultFragmentContainer(savedInstanceState);
        getAllTasksFragment();
        getCompletedFragment();
        getPendingFragment();
    }

    //set fragment container Completed by default
    private void setDefaultFragmentContainer(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container_dashboard, completedFragment).commit();
        }
    }

    //change fragment container to fragment Completed Tasks
    private void getCompletedFragment() {
        binding.today.setOnClickListener(v -> {
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.otherDays.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fad_in, R.anim.fad_out)
                    .replace(R.id.fragment_container_dashboard, completedFragment).commit();
        });
    }

    //change fragment container to fragment Pending Tasks
    private void getPendingFragment() {
        binding.tomorrow.setOnClickListener(v -> {
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.otherDays.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fad_in, R.anim.fad_out)
                    .replace(R.id.fragment_container_dashboard, pendingFragment).commit();
        });
    }

    //change fragment container to fragment All tasks
    private void getAllTasksFragment() {

        binding.otherDays.setOnClickListener(v -> {
            binding.otherDays.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));

            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fad_in, R.anim.fad_out)
                    .replace(R.id.fragment_container_dashboard, allTasksFragment).commit();
        });
    }

    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
        String currentDate = dateFormat.format(calendar.getTime());
        binding.tvCurrentDate.setText(currentDate);
    }

    private void setBackButton() {
        binding.ibtnBack.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });
    }
}