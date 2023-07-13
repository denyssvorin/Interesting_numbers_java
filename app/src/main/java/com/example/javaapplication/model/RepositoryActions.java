package com.example.javaapplication.model;

import androidx.lifecycle.LiveData;

import com.example.javaapplication.MainContract;
import com.example.javaapplication.model.data.NumberData;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

public interface RepositoryActions {
    List<NumberData> getSavedData();
    void requestNumberData(MainContract.Presenter onFinishedListener, int number);
    void requestRandomNumberData(MainContract.Presenter onFinishedListener);
}
