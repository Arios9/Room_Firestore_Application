package com.example.room_firestore_application.Local_Tables;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sport")
public class Sport implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String individual;

    private String gender;

    public Sport(String name, String individual, String gender) {
        this.name = name;
        this.individual = individual;
        this.gender = gender;
    }

    protected Sport(Parcel in) {
        id = in.readInt();
        name = in.readString();
        individual = in.readString();
        gender = in.readString();
    }

    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndividual() {
        return individual;
    }

    public void setIndividual(String individual) {
        this.individual = individual;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return  "id:" + id + "\n" + name + "\n" +
                individual + "\n" + gender ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(individual);
        dest.writeString(gender);
    }
}
