package com.ahmedabdelmohsen.mytasks.main.destinations;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.ahmedabdelmohsen.mytasks.AddTaskActivity;
import com.ahmedabdelmohsen.mytasks.R;
import com.ahmedabdelmohsen.mytasks.databinding.FragmentHomeBinding;
import com.ahmedabdelmohsen.mytasks.main.viewmodel.TasksViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    private FragmentHomeBinding binding;
    private View view;
    private FragmentManager fragmentManager;
    private final TodayFragment todayFragment = new TodayFragment();
    private final TomorrowFragment tomorrowFragment = new TomorrowFragment();
    private final OtherDaysFragment otherDaysFragment = new OtherDaysFragment();
    private static final String SHARED_PREFS = "sharedPrefs";
    private TasksViewModel viewModel;

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
        viewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);

        fragmentManager = getChildFragmentManager();

        setDefaultFragmentContainer(savedInstanceState);
        getTodayFragment();
        getTomorrowFragment();
        getOtherDaysFragment();
        startReveal();
        openMenu();
        openDeleteDialog();
        setFabPrompt();
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

    //open delete dialog
    public void openDeleteDialog() {
        binding.ibtnDelete.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireActivity(), v);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.delete_menu);
            popupMenu.show();
        });
    }

    //set tap fab target
    public void setFabPrompt() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("state", false)) {
            binding.tvNoListHome.setVisibility(View.VISIBLE);
            new MaterialTapTargetPrompt.Builder(requireActivity())
                    .setTarget(R.id.fab)
                    .setPrimaryText("Add Task")
                    .setSecondaryText("Click her to add your first task")
                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                        @Override
                        public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {

                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {
                                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("state", true).apply();
                                setTaskPrompt();
                            }
                        }
                    }).show();
        }

    }

    //set tap task target
    public void setTaskPrompt() {
        new MaterialTapTargetPrompt.Builder(requireActivity())
                .setTarget(R.id.tv_no_list_home)
                .setPrimaryText("Long click")
                .setSecondaryText("After adding a task you can update or delete it by long click on any task.")
                .setBackButtonDismissEnabled(true)
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                    @Override
                    public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                                || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                            binding.tvNoListHome.setVisibility(View.GONE);
                        }
                    }
                })
                .setPromptBackground(new RectanglePromptBackground())
                .setPromptFocal(new RectanglePromptFocal())
                .show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.today_tasks:
                deleteAllTodayTasks();
                Toast.makeText(requireActivity(), "today", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tomorrow_tasks:
                deleteAllTomorrowTasks();
                Toast.makeText(requireActivity(), "tomorrow", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.other_tasks:
                deleteAllOtherTasks();
                Toast.makeText(requireActivity(), "other tasks", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(requireActivity(), "default", Toast.LENGTH_SHORT).show();
                return true;
        }

    }

    //delete today tasks from database
    public void deleteAllTodayTasks() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Delete Today Tasks");
        builder.setMessage("are you sure you want to delete\nall today tasks ??");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                String todayDate = day + "/" + month + "/" + year;
                viewModel.deleteAllTasksByDate(todayDate)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                Toast.makeText(requireActivity(), "Error to delete", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    //delete tomorrow tasks from database
    public void deleteAllTomorrowTasks() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Delete Tomorrow Tasks");
        builder.setMessage("are you sure you want to delete\nall tomorrow tasks ??");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                String tomorrowDate =  day + "/" + month + "/" + year;
                viewModel.deleteAllTasksByDate(tomorrowDate)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                Toast.makeText(requireActivity(), "Error to delete", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    //delete other tasks from database
    public void deleteAllOtherTasks() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Delete Other Tasks");
        builder.setMessage("are you sure you want to delete\nall other tasks ??");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                String tomorrowDate = day + "/" + month + "/" + year;
                Calendar calendar2 = Calendar.getInstance();
                int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
                int month2 = calendar2.get(Calendar.MONTH) + 1;
                int year2 = calendar2.get(Calendar.YEAR);
                String todayDate = day2 + "/" + month2 + "/" + year2;
                viewModel.deleteAllTasksByOtherDate(todayDate,tomorrowDate)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                Toast.makeText(requireActivity(), "Error to delete", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}