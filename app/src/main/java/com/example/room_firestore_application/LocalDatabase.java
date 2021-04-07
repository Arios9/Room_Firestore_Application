package com.example.room_firestore_application;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.Local_Tables.Team;

@Database(entities = {Sport.class, Athlete.class, Team.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract BasicDao basicDao();
}
