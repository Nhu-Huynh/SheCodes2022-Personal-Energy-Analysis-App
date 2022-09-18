package com.example.shecodes2022.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shecodes2022.adapters.TaskAdapter;
import com.example.shecodes2022.databinding.ActivityTaskManagementBinding;
import com.example.shecodes2022.listeners.TaskListener;
import com.example.shecodes2022.models.Task;
import com.example.shecodes2022.utilities.Constants;
import com.example.shecodes2022.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;
import java.util.List;

public class TaskManagementActivity extends AppCompatActivity {

    private ActivityTaskManagementBinding binding;
    private PreferenceManager preferenceManager;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
    }

    private void loadData() {
        Date current = new Date();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_TASK)
                .whereEqualTo(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            Task item = new Task();
                            item.difficulty = Integer.parseInt(queryDocumentSnapshot.getString(Constants.KEY_TASK_DIFFICULTY));
                        }
                        if (taskList.size() == 0) {
                            showToast("No task yet. Let's create new one!");
                        } else if (taskList.size() > 0) {
                            TaskAdapter taskAdapter = new TaskAdapter(taskList, (TaskListener) this);
                            binding.tasksRecyclerView.setAdapter(taskAdapter);
                            binding.tasksRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        showToast("Load data failed");
                    }
                });
    }

    private Integer calEnergy(Integer i, Integer j, List<Integer> energy) {
        Integer res = 0;
        for (Integer x = i; x <= j; x++) {
            res = res + energy.get(x);
        }
        res/=(j - i + 1);
        return res;
    }

    private Boolean isOccupied(int i, int j, List<Boolean> usedBy)
    {
        for (int x = i; x <= j; x++) {
            if (usedBy.get(x) == true)
                return true;
        }
        return false;
    }

    private void arrangeTask(Integer index, Integer maxEnergyHour, List<Integer> timeStart, List<Integer> energy, List<Task> taskList, List<Boolean> usedBy) {
        for (int j = maxEnergyHour; j >= 1; j--) {                      //find maximum energy

            for (int i = 1; i <= 24; i++) {       //check available time

                if (calEnergy(i, i + taskList.get(index).duration - 1, energy) == j // check max energy
                        && !isOccupied(i, i + taskList.get(index).duration - 1, usedBy)) {

                    timeStart.add(i);

                    for (int x = i; x <= i + taskList.get(index).duration - 1; x++) {
                        usedBy.set(x, true);
                    }

                    if (index == taskList.size()) {         //end
                        return;
                    }
                }
                arrangeTask(index + 1, maxEnergyHour, timeStart, energy, taskList, usedBy);
                timeStart.remove(timeStart.size() - 1);
                for (int x = i; x <= i + taskList.get(index).duration - 1; x++) {
                    usedBy.set(x, false);
                }
            }
        }
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}