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
    private ArrayList<TaskModel> list = new ArrayList<>();

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TasksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {

        holder.body.setText(list.get(position).getBody());

        boolean status = list.get(position).isStatus();
        if (status) {
            holder.body.setPaintFlags(holder.body.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.body.setPaintFlags(holder.body.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

    }

    public void setList(ArrayList<TaskModel> list) {
        this.list = list;
        notifyDataSetChanged();
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
