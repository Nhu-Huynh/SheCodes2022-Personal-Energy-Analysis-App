package com.example.shecodes2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import com.example.shecodes2022.activities.FormSleepActivity;
import com.example.shecodes2022.activities.ProfileActivity;
import com.example.shecodes2022.activities.TaskManagementActivity;
import com.example.shecodes2022.databinding.ActivityMainBinding;
import com.example.shecodes2022.models.EnergyRecord;
import com.example.shecodes2022.models.Task;
import com.example.shecodes2022.utilities.Constants;
import com.example.shecodes2022.utilities.PreferenceManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Entry> entries = new ArrayList<>();
    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_main);
//        LineChart chart = (LineChart) findViewById(R.id.chart);
        loadUserDetails();
        LineChart chart = (LineChart) binding.chart;

        loadChartStaticData();

        LineDataSet lineDataSet = new LineDataSet(entries, "Daily Energy Flow");
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(new LineData(lineDataSet));
        chart.setAutoScaleMinMaxEnabled(true);
        chart.invalidate();

        setListeners();
    }

    private void setListeners() {
        binding.imageProfile.setOnClickListener(
                v -> startActivity(new Intent(getApplicationContext(), ProfileActivity.class)));

        binding.imageButton3.setOnClickListener(
                v -> startActivity(new Intent(getApplicationContext(), TaskManagementActivity.class)));

        binding.imageButton4.setOnClickListener(
                v -> startActivity(new Intent(getApplicationContext(), FormSleepActivity.class)));
    }

    private void loadUserDetails() {
        String userName = preferenceManager.getString(Constants.KEY_NAME);
        binding.helloName.setText("hello " + userName + "!");
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);
    }


    private void loadChartData() {           // initialize dataset
        List<EnergyRecord> energyRecords = new ArrayList<>();
        String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_ENERGY_RECORD)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (currentUserId.equals(queryDocumentSnapshot.getString(Constants.KEY_USER_ID))) {
                                EnergyRecord record = new EnergyRecord();
                                record.hour = queryDocumentSnapshot.getTimestamp(Constants.KEY_TIMESTAMP).toDate().getHours();
                                record.level = Integer.parseInt(queryDocumentSnapshot.getString(Constants.KEY_ENERGY_LEVEL));
                                energyRecords.add(record);
                            }
                        }
                    }
                    else {
                        showToast("Load data failed");
                    }
                });
        for (int i = 0; i <25; i++) {
            Integer sum = 0, count = 0;
            for (EnergyRecord record : energyRecords) {
                if (record.hour == i) {
                    count++;
                    sum += record.level;
                }
            }
            if (count > 0) {
                entries.add(new Entry(i, sum/count));
            }
        }
    }

    private void loadChartStaticData() {
        entries.add(new Entry(7, 8));
        entries.add(new Entry(8, 6));
        entries.add(new Entry(9, 5));
        entries.add(new Entry(10, 9));
        entries.add(new Entry(11, 10));
        entries.add(new Entry(12, 9));
        entries.add(new Entry(13, 9));
        entries.add(new Entry(14, 8));
        entries.add(new Entry(15, 6));
        entries.add(new Entry(16, 7));
        entries.add(new Entry(17, 7));
        entries.add(new Entry(18, 8));
        entries.add(new Entry(19, 8));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}