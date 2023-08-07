package com.example.javaapplication.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;

import com.example.javaapplication.MainContract;
import com.example.javaapplication.model.api.NumberApi;
import com.example.javaapplication.model.data.NumberData;
import com.example.javaapplication.model.data.NumberDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlinx.coroutines.flow.Flow;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository implements RepositoryActions {
    Context context;
    NumberDatabase db;

    public Repository(Context context) {
        this.context = context;
        try {
            db = NumberDatabase.getDbInstance(context.getApplicationContext());
        } catch (NullPointerException e) {
            Log.e("TAG", "Repository: " + e);
        }
    }

    @Override
    public List<NumberData> getSavedData() {
        return db.numberDao().getNumbers();
    }

    private final Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://numbersapi.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build();

    private final NumberApi api = retrofit.create(NumberApi.class);

    @Override
    public void requestNumberData(MainContract.Presenter onFinishedListener, int number) {
        Call<NumberData> call = api.getNumberData(number);
        call.enqueue(new Callback<NumberData>() {
            @Override
            public void onResponse(Call<NumberData> call, Response<NumberData> response) {
                if (response.isSuccessful()) {
                    NumberData responseData = response.body();

                    if (responseData != null) {
                        onFinishedListener.onResultSuccess(responseData);
                        saveData(responseData.number, responseData.text);
                    } else {
                        Log.i("TAG", "onResponse: Server returned error:" + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<NumberData> call, Throwable t) {
                Log.i("TAG", "onFailure:" + t.getMessage());
                onFinishedListener.onResultError(t.getMessage());
            }
        });
    }

    @Override
    public void requestRandomNumberData(MainContract.Presenter onFinishedListener) {
        Log.i("TAG", "requestRandomNumberData: called");
        Call<NumberData> call = api.getRandomNumberData();
        call.enqueue(new Callback<NumberData>() {
            @Override
            public void onResponse(Call<NumberData> call, Response<NumberData> response) {
                if (response.isSuccessful()) {
                    NumberData responseData = response.body();

                    if (responseData != null) {
                        onFinishedListener.onResultSuccess(responseData);
                        saveData(responseData.number, responseData.text);

                        Log.i("TAG", "onResponse: requestRandomNumber");
                    } else {
                        Log.i("TAG", "onResponse: Server returned error:" + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<NumberData> call, Throwable t) {
                Log.i("TAG", "onFailure:" + t.getMessage());
                onFinishedListener.onResultError(t.getMessage());
            }
        });
    }

    private void saveData(int number, String text) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                db.numberDao().insertNumberData(new NumberData(number, text));
            }
        });
    }
}
