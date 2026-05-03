package com.example.mainactivity.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mainactivity.model.Usuario;

@Dao
public interface UsuarioDao {
    @Insert
    long cadastrar(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    Usuario fazerLogin(String email, String senha);
}
