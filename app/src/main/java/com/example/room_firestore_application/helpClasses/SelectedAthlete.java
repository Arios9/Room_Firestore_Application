package com.example.room_firestore_application.helpClasses;

public class SelectedAthlete {

    private String Name;
    private String Score;
    private String ID;

    public SelectedAthlete(String name, String score, String id) {
        Name = name;
        Score = score;
        ID = id;
    }

    public String getName() {
        return Name;
    }

    public String getScore() {
        return Score;
    }

    public String getId() {
        return ID;
    }

}
