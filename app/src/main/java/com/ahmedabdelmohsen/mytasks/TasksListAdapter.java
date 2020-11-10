package com.ahmedabdelmohsen.mytasks;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedabdelmohsen.mytasks.pojo.TaskModel;

import java.util.ArrayList;

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.TasksViewHolder> {
    private ArrayList<TaskModel> list;
    private static InterfaceRecyclerViewItem listener;

    public TasksListAdapter(InterfaceRecyclerViewItem listener, ArrayList<TaskModel> list) {
        this.listener = listener;
        this.list = list;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TasksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        TaskModel task = list.get(position);

        holder.body.setText(task.getBody());

        if (task.isStatus()) {
            holder.body.setPaintFlags(holder.body.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.body.setPaintFlags(holder.body.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTaskClick(task.getId(), task.isStatus());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TasksViewHolder extends RecyclerView.ViewHolder {
        TextView body;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.tv_task_body);

        }

    }

}
