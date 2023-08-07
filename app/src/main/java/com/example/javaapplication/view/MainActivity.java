package com.example.javaapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javaapplication.MainContract;
import com.example.javaapplication.databinding.ActivityMainBinding;
import com.example.javaapplication.model.Repository;
import com.example.javaapplication.model.data.DataCallback;
import com.example.javaapplication.model.data.NumberData;
import com.example.javaapplication.presenter.MainPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, MainActivityAdapter.Listener {
    private static final String KEY_NUMBER_VALUE = "KEY_NUMBER_VALUE";
    private static final String KEY_FACT_VALUE = "KEY_FACT_VALUE";
    ActivityMainBinding binding;
    MainContract.Presenter mainPresenter;
    MainActivityAdapter numAdapter = new MainActivityAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            int number = savedInstanceState.getInt(KEY_NUMBER_VALUE);
            String fact = savedInstanceState.getString(KEY_FACT_VALUE);
            binding.editTextNumber.setText(String.valueOf(number));
            binding.textViewFact.setText(fact);
        }

        mainPresenter = new MainPresenter(this, new Repository(MainActivity.this));

        initRecyclerView();

        EditText editText = binding.editTextNumber;

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.editTextNumber.requestLayout();
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setCursorVisible(true);
            }
        });

        binding.buttonGetFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextNumber = String.valueOf(binding.editTextNumber.getText());
                if (!TextUtils.isEmpty(editTextNumber) || !editTextNumber.equals("")) {
                    String text = binding.editTextNumber.getText().toString();
                    int number = Integer.parseInt(text);

                    mainPresenter.getNumberData(number);

                } else {
                    Toast.makeText( MainActivity.this, "Number field can not be empty", Toast.LENGTH_SHORT)
                            .show();
                }
                hideKeyboard(MainActivity.this);
            }
        });

        binding.buttonGetRandomFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.getRandomNumberData();
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.recycler;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(numAdapter);

        getSavedNumberList();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setRandomNumberData(NumberData data) {
        binding.editTextNumber.setText(Integer.toString(data.number));
        binding.textViewFact.setText(data.text);
        binding.editTextNumber.setCursorVisible(false);

        singleInsertInNumberList(data);
    }

    @Override
    public void setNumberData(String data) {
        binding.textViewFact.setText(data);
        binding.editTextNumber.setCursorVisible(false);

        singleInsertInNumberList(new NumberData(Integer.
                parseInt(binding.editTextNumber.getText().toString()), data));
    }

    @Override
    public void dataFetchFailed(String error) {
        binding.textViewFact.setText(error);
    }

    @Override
    public void onClick(NumberData num) {
        startActivity(new Intent(this, DetailsActivity.class)
                .putExtra(DetailsActivity.class.getSimpleName(), num));

    }
    void getSavedNumberList() {
        mainPresenter.getStoredData(new DataCallback() {
            @Override
            public void onDataLoaded(List<NumberData> numberDataList) {
                numAdapter.setNumberList(numberDataList);
            }
        });

    }
    private void singleInsertInNumberList(NumberData numberData) {
        numAdapter.setSingleNumber(numberData);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        String editTextNumber = binding.editTextNumber.getText().toString();
        String fact = binding.textViewFact.getText().toString();

        if (!TextUtils.isEmpty(editTextNumber)) {
            int number = Integer.parseInt(editTextNumber);

            outState.putInt(KEY_NUMBER_VALUE, number);
            outState.putString(KEY_FACT_VALUE, fact);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.shutdownExecutorService();
    }
}