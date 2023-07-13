package com.example.javaapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.javaapplication.databinding.ActivityDetailsBinding;
import com.example.javaapplication.model.data.NumberData;

import java.net.URISyntaxException;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        NumberData item = intent.getParcelableExtra(DetailsActivity.class.getSimpleName());
        binding.textViewNumber.setText(item != null ? String.valueOf(item.getNumber()) : "55");
        binding.textViewFact.setText(item != null ? item.getText() : "fact");

    }

    @Override
    public void onBackPressed() {
        getOnBackPressedDispatcher().onBackPressed();
    }
}
