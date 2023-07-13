package com.example.javaapplication.presenter;

import com.example.javaapplication.MainContract;
import com.example.javaapplication.model.Repository;
import com.example.javaapplication.model.data.NumberData;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private Repository repository;
    private MainContract.View action;

    public MainPresenter(MainContract.View action, Repository repository) {
        this.action = action;
        this.repository = repository;
    }



    @Override
    public void getNumberData(int number) {
        action.showProgress();
        repository.requestNumberData(this, number);
    }

    @Override
    public void getRandomNumberData() {
        action.showProgress();
        repository.requestRandomNumberData(this);
    }

    @Override
    public List<NumberData> getStoredData() {
        return repository.getSavedData();
    }

    @Override
    public void onResultSuccess(NumberData data) {
        action.hideProgress();
        action.setRandomNumberData(data);
    }

    @Override
    public void onNumberResultSuccess(NumberData data) {
        action.hideProgress();
        action.setNumberData(data.text);
    }

    @Override
    public void onResultError(String error) {
        action.hideProgress();
        action.dataFetchFailed(error);
    }
}
