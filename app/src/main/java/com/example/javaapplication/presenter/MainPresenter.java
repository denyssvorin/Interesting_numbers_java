package com.example.javaapplication.presenter;

import com.example.javaapplication.MainContract;
import com.example.javaapplication.model.Repository;
import com.example.javaapplication.model.data.DataCallback;
import com.example.javaapplication.model.data.NumberData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainPresenter implements MainContract.Presenter {

    private Repository repository;
    private MainContract.View action;
    private ExecutorService executorService;

    public MainPresenter(MainContract.View action, Repository repository) {
        this.action = action;
        this.repository = repository;
    }


    @Override
    public void getNumberData(int number) {
        action.showProgress();
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                MainPresenter.this.repository.requestNumberData(MainPresenter.this, number);
            }
        });
    }

    @Override
    public void getRandomNumberData() {
        action.showProgress();

        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                MainPresenter.this.repository.requestRandomNumberData(MainPresenter.this);
            }
        });
    }

    @Override
    public void getStoredData(DataCallback callback) {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                List<NumberData> data = repository.getSavedData();
                callback.onDataLoaded(data);
            }
        });
        shutdownExecutorService();
    }

    @Override
    public void onResultSuccess(NumberData data) {
        action.hideProgress();
        action.setRandomNumberData(data);
    }

    @Override
    public void onResultError(String error) {
        action.hideProgress();
        action.dataFetchFailed(error);
    }

    public void shutdownExecutorService() {
        executorService.shutdown();
    }
}
