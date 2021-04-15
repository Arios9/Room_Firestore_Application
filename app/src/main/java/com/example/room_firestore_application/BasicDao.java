package com.example.room_firestore_application;

import android.database.Cursor;

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
    List<Sport> getSport();


    @Query("SELECT * FROM sport WHERE gender='Male'")
    List<Sport> getMaleSport();

    @Query("SELECT * FROM sport WHERE gender='Female'")
    List<Sport> getFemaleSport();

    @Query("SELECT * FROM sport WHERE name='Football'")
    List<Sport> getFootball();

    @Insert
    void insert(Athlete athlete);

    @Update
    void update(Athlete athlete);

    @Delete
    void delete(Athlete athlete);

    @Query("SELECT * FROM athlete")
    List<Athlete> getAthlete();

    @Query("SELECT a.name FROM 'sport' a INNER JOIN 'athlete' b ON a.id = b.sid WHERE b.sid = :sportid")
    String getAthleteSport(int sportid);

    @Query("SELECT * FROM athlete WHERE country LIKE :countryName")
    List<Athlete> getAthleteCountry(String countryName);

    @Query("SELECT * FROM athlete WHERE birth_year < :maxAge AND birth_year > :minAge")
    List<Athlete> getAthleteBetweenAges(int minAge, int maxAge);

    @Query("WITH S AS (SELECT sport.id, sport.name, athlete.sid, athlete.name " +
            "FROM sport INNER JOIN athlete ON sport.id = athlete.sid)" +
            "SELECT name FROM S")
    String getNSportName();
    @Insert
    void insert(Team team);

    @Update
    void update(Team team);

    @Delete
    void delete(Team team);

    @Query("SELECT * FROM team")
    List<Team> getTeam();

    @Query("SELECT * FROM team where sid LIKE :sportId")
    List<Team> getTeamSportId(int sportId);

    @Query("SELECT * FROM team where name LIKE :teamName")
    List<Team> getTeamSportId(String teamName);



}
