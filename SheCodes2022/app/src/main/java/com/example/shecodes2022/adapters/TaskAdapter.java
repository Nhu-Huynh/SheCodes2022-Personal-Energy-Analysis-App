package com.example.shecodes2022.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shecodes2022.databinding.ItemContainerTaskBinding;
import com.example.shecodes2022.listeners.TaskListener;
import com.example.shecodes2022.models.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList;
    private final TaskListener taskListener;

    public TaskAdapter(List<Task> taskList, TaskListener taskListener) {
        this.taskList = taskList;
        this.taskListener = taskListener;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        ItemContainerTaskBinding binding;

        TaskViewHolder(ItemContainerTaskBinding itemContainerTaskBinding) {
            super(itemContainerTaskBinding.getRoot());
            binding = itemContainerTaskBinding;
        }

        void setTaskData(Task taskItem) {
            binding.task.setText(taskItem.taskName);
//            binding.checkbox.setChecked(taskItem.done);
            setListener(taskItem);
        }

        void setListener(Task taskItem) {
            binding.task.setOnClickListener(v -> taskListener.onTaskClicked(taskItem));
            binding.checkbox.setOnClickListener(v -> taskListener.onTaskClicked(taskItem));
//            binding.clear.setOnClickListener(v -> taskListener.onClearClicked(taskItem));
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerTaskBinding itemContainerTaskBinding = ItemContainerTaskBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new TaskViewHolder(itemContainerTaskBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.setTaskData(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
