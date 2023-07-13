package com.example.javaapplication.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import kotlinx.parcelize.Parcelize;

@Entity(tableName = "numbers_table")

@Parcelize
public final class NumberData implements Parcelable {

    public NumberData(){}

    @PrimaryKey(autoGenerate = false)
    public int number = 0;
    @ColumnInfo(name = "fact")
    public String text = "fact";

    public NumberData(int number, String fact) {
        this.number = number;
        this.text = fact;
    }

    private NumberData(Parcel in) {
        number = in.readInt();
        text = in.readString();
    }

    public static final Creator<NumberData> CREATOR = new Creator<NumberData>() {
        @Override
        public NumberData createFromParcel(Parcel in) {
            return new NumberData(in);
        }

        @Override
        public NumberData[] newArray(int size) {
            return new NumberData[size];
        }
    };

    public int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(text);
    }
}
