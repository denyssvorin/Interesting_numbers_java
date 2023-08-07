package com.example.javaapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaapplication.R;
import com.example.javaapplication.databinding.ItemNumBinding;
import com.example.javaapplication.model.data.NumberData;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.NumberViewHolder> {

    private List<NumberData> numberDataList;

    private Listener listener;

    public MainActivityAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setNumberList(List<NumberData> numberList) {
        this.numberDataList = numberList;
        notifyDataSetChanged();
    }

    public void setSingleNumber(NumberData numberData) {
        numberDataList.add(numberData);
        Collections.sort(numberDataList);
        notifyDataSetChanged();
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder {
        private ItemNumBinding binding;

        public NumberViewHolder(View item) {
            super(item);
            binding = ItemNumBinding.bind(item);
        }

        public void bind(NumberData numberData, Listener listener) {
            binding.textViewNumberHistory.setText(String.valueOf(numberData.getNumber()));
            binding.textViewFactHistory.setText(numberData.getText());
            itemView.setOnClickListener(v -> listener.onClick(numberData));
        }
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_num, parent, false);
        return new NumberViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        holder.bind(numberDataList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return this.numberDataList.size();
    }
    interface Listener {
        void onClick(NumberData numberData);
    }
}