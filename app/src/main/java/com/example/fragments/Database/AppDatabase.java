package com.example.fragments.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.fragments.Interfaces.PersonaDAO;
import com.example.fragments.models.Persona;

@Database(entities = {Persona.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonaDAO getItemDAO();
}
