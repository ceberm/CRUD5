package com.example.fragments.Interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.fragments.models.Persona;

import java.util.List;

@Dao
public interface PersonaDAO {

    //CRUD methods

    @Insert
    public void insert(Persona... personas);

    @Update
    public void update(Persona... personas);

    @Delete
    public void delete(Persona... personas);

    @Query("SELECT * FROM personas")
    public List<Persona> getPersonas();

    @Query("SELECT COUNT(*) as count FROM personas")
    public int getCount();
}
