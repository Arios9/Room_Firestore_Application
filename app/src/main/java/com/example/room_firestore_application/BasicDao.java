package com.example.room_firestore_application;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.Local_Tables.Team;

import java.util.List;

@Dao
public interface BasicDao {

    @Insert
    void insert(Sport sport);

    @Update
    void update(Sport sport);

    @Delete
    void delete(Sport sport);

    @Query("SELECT * FROM sport")
    LiveData<List<Sport>> getSport();

    @Insert
    void insert(Athlete athlete);

    @Update
    void update(Athlete athlete);

    @Delete
    void delete(Athlete athlete);

    @Query("SELECT * FROM athlete")
    LiveData<List<Athlete>> getAthlete();

    @Insert
    void insert(Team team);

    @Update
    void update(Team team);

    @Delete
    void delete(Team team);

    @Query("SELECT * FROM team")
    LiveData<List<Team>> getTeam();

}
