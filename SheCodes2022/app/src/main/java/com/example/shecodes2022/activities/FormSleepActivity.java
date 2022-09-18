package com.example.shecodes2022.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shecodes2022.R;
import com.example.shecodes2022.databinding.ActivityFormSleepBinding;
import com.example.shecodes2022.databinding.ActivityHabitFormBinding;

public class FormSleepActivity extends AppCompatActivity {

    private ActivityFormSleepBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormSleepBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}