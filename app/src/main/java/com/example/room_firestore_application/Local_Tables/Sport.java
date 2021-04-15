package com.example.room_firestore_application.Local_Tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sport")
public class Sport {

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

//    public String getNameOfSport(int id){
//        return
//    }
    @Override
    public String toString() {
        return  "id:" + id + "\n" + name + "\n" +
                individual + "\n" + gender ;
    }
}
