package com.example.mainactivity.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "usuarios")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    public int idUsuario;

    public String nome;
    public String email;
    public String senha;
    public String fotoPath;
    public String tipoPerfil;
}
