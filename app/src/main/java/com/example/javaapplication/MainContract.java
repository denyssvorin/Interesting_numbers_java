package com.example.javaapplication;

import com.example.javaapplication.model.data.DataCallback;
import com.example.javaapplication.model.data.NumberData;

import java.util.List;

public interface MainContract {

    interface Presenter {
        void getNumberData(int number);

        void getRandomNumberData();

        void getStoredData(DataCallback callback);

        void onResultSuccess(NumberData data);

        void onResultError(String error);

        void shutdownExecutorService();
    }

    interface View {
        void showProgress();

        void hideProgress();

        void setRandomNumberData(NumberData data);

        void setNumberData(String data);

        void dataFetchFailed(String error);
    }

}

