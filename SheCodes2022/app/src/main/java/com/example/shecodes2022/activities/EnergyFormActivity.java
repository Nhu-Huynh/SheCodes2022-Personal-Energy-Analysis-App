package com.example.shecodes2022.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.shecodes2022.MainActivity;
import com.example.shecodes2022.R;
import com.example.shecodes2022.databinding.ActivityEnergyFormBinding;
import com.example.shecodes2022.utilities.Constants;
import com.example.shecodes2022.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EnergyFormActivity extends AppCompatActivity {

    private ActivityEnergyFormBinding binding;
    private PreferenceManager preferenceManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnergyFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        setListener();
    }


    private void setListener() {

        binding.veryLow.setOnClickListener(v -> {
            saveRecord(1);
            startActivity(new Intent(getApplicationContext(), HabitFormActivity.class));
        });

        binding.low.setOnClickListener(v -> {
            saveRecord(2);
            startActivity(new Intent(getApplicationContext(), HabitFormActivity.class));
        });

        binding.neutral.setOnClickListener(v -> {
            saveRecord(3);
            startActivity(new Intent(getApplicationContext(), HabitFormActivity.class));
        });

        binding.high.setOnClickListener(v -> {
            saveRecord(4);
            startActivity(new Intent(getApplicationContext(), HabitFormActivity.class));
        });

        binding.veryHigh.setOnClickListener(v -> {
            saveRecord(5);
            startActivity(new Intent(getApplicationContext(), HabitFormActivity.class));
        });
    }

    private void saveRecord(Integer energyLevel) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> newRecord = new HashMap<>();
        newRecord.put(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        newRecord.put(Constants.KEY_ENERGY_LEVEL, energyLevel);
        newRecord.put(Constants.KEY_TIMESTAMP, new Date());
        database.collection(Constants.KEY_ENERGY_RECORD).add(newRecord);
    }

}