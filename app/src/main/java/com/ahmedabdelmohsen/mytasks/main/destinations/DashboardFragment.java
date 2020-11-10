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
import com.ahmedabdelmohsen.mytasks.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private View view;
    private FragmentManager fragmentManager;
    private PendingFragment pendingFragment = new PendingFragment();
    private CompletedFragment completedFragment = new CompletedFragment();
    private AllTasksFragment allTasksFragment = new AllTasksFragment();

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

        setDefaultFragmentContainer(savedInstanceState);
        getAllTasksFragment();
        getCompletedFragment();
        getPendingFragment();
    }

    //set fragment container Today by default
    private void setDefaultFragmentContainer(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.fragment_container_dashboard, completedFragment).commit();
        }
    }

    //change fragment container to fragment Today
    private void getCompletedFragment() {
        binding.today.setOnClickListener(v -> {
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.otherDays.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fad_in, R.anim.fad_out)
                    .replace(R.id.fragment_container_dashboard, completedFragment).commit();
        });
    }

    //change fragment container to fragment Tomorrow
    private void getPendingFragment() {
        binding.tomorrow.setOnClickListener(v -> {
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.otherDays.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fad_in, R.anim.fad_out)
                    .replace(R.id.fragment_container_dashboard, pendingFragment).commit();
        });
    }

    //change fragment container to fragment This Week
    private void getAllTasksFragment() {

        binding.otherDays.setOnClickListener(v -> {
            binding.otherDays.setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue));
            binding.tomorrow.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            binding.today.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey));
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fad_in, R.anim.fad_out)
                    .replace(R.id.fragment_container_dashboard, allTasksFragment).commit();
        });
    }

}