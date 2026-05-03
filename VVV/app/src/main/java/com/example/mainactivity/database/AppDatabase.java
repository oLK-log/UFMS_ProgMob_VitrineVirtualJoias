package com.example.mainactivity.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mainactivity.dao.ProdutoDao;
import com.example.mainactivity.dao.UsuarioDao;
import com.example.mainactivity.model.Produto;
import com.example.mainactivity.model.Usuario;

@Database(entities = {Usuario.class, Produto.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    //aqui eh conectado os DAOs
    public abstract UsuarioDao usuarioDao();
    public abstract ProdutoDao produtoDao();

    //variavel q vai guardar a conexao com o bd
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "banco_vvv")
                    .allowMainThreadQueries()//permite rodar o banco na mesma interface grafica
                    .build();
        }
        return INSTANCE;
    }
}
