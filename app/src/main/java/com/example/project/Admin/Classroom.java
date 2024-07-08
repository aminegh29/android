package com.example.project.Admin;

import android.os.Parcel;
import android.os.Parcelable;

public class Classroom implements Parcelable {

    private String building;
    private String floor;
    private String number;
    private String type;

    public Classroom() {
        // Default constructor required for Firebase
    }

    public Classroom(String batiment, String etage, String numero, String type) {
        this.building = batiment;
        this.floor = etage;
        this.number = numero;
        this.type = type;
    }

    public String getBuilding() {

        return building;
    }

    public String getFloor() {

        return floor;
    }

    public String getNumber() {

        return number;
    }

    public String getType() {

        return type;
    }


    public String getName() {

        return "Building " + building + " - floor " + floor + " - Number " + number+ " -Type " + type;
    }


    protected Classroom(Parcel in) {
        building = in.readString();
        floor = in.readString();
        number = in.readString();
        type = in.readString();
    }

    public static final Creator<Classroom> CREATOR = new Creator<Classroom>() {
        @Override
        public Classroom createFromParcel(Parcel in) {
            return new Classroom(in);
        }

        @Override
        public Classroom[] newArray(int size) {
            return new Classroom[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(building);
        dest.writeString(floor);
        dest.writeString(number);
        dest.writeString(type);
    }
}
