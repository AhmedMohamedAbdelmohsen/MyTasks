package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
        onMenuItemSelected();
    }

    public void closeMenu() {
        binding.ibtnCloseMenu.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.bounce);
            binding.ibtnCloseMenu.setAnimation(animation);

            requireActivity().onBackPressed();
        });
    }

    public void onMenuItemSelected() {
        binding.tvDashboard.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(MenuFragmentDirections.actionMenuFragmentToDashboardFragment());
        });
        binding.tvCalender.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(MenuFragmentDirections.actionMenuFragmentToCalenderFragment());
        });
        binding.tvRateUs.setOnClickListener(v->{
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + "com.ahmedabdelmohsen.whereitmadein")));
            }catch (Exception e){
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + requireActivity().getPackageName())));
            }
        });
        binding.tvOurWorks.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(
                    "https://play.google.com/store/apps/developer?id=Ahmed+M.AbdElmohsen"));
            intent.setPackage("com.android.vending");
            startActivity(intent);
        });
    }
}