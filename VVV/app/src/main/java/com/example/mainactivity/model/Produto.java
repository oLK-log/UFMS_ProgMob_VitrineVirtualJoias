package com.example.mainactivity.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "produtos")
public class Produto {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nome;
    public String descricao;
    public double preco;
    public String imagemUri;
    public int usuarioId;
}
