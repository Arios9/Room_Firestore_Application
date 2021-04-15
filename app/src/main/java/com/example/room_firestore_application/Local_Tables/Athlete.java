package com.example.room_firestore_application.Local_Tables;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "athlete",
        foreignKeys = {
            @ForeignKey(
                    entity = Sport.class,
                    parentColumns = "id",
                    childColumns = "sid",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        }
)
public class Athlete implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "sid")
    private int sport_id;

    private String name;

    private String surname;

    private String country;

    private String city;

    private int birth_year;

    public Athlete(int sport_id, String name, String surname, String country, String city, int birth_year) {
        this.sport_id = sport_id;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.city = city;
        this.birth_year = birth_year;
    }

    protected Athlete(Parcel in) {
        id = in.readInt();
        sport_id = in.readInt();
        name = in.readString();
        surname = in.readString();
        country = in.readString();
        city = in.readString();
        birth_year = in.readInt();
    }

    public static final Creator<Athlete> CREATOR = new Creator<Athlete>() {
        @Override
        public Athlete createFromParcel(Parcel in) {
            return new Athlete(in);
        }

        @Override
        public Athlete[] newArray(int size) {
            return new Athlete[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSport_id() {
        return sport_id;
    }

    public void setSport_id(int sport_id) {
        this.sport_id = sport_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", sport_id=" + sport_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", birth_year=" + birth_year +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(sport_id);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeInt(birth_year);
    }
}
