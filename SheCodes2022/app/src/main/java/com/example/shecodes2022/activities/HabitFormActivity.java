package com.example.shecodes2022.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shecodes2022.MainActivity;
import com.example.shecodes2022.databinding.ActivityHabitFormBinding;

public class HabitFormActivity extends AppCompatActivity {

    private ActivityHabitFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHabitFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSkip.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), MainActivity.class)));

        binding.button6.setOnClickListener(
                v -> {
                    showToast("saved successfully");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
        );

        binding.button8.setOnClickListener(
                v -> {
                    showToast("saved successfully");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
        );

        binding.button9.setOnClickListener(
                v -> {
                    showToast("saved successfully");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
        );
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}