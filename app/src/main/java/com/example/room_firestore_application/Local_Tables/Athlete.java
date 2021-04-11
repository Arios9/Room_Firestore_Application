package com.example.room_firestore_application.Local_Tables;

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
public class Athlete {

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

    public Athlete() {

    }

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
}
