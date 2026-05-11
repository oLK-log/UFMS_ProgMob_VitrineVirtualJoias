package com.example.mainactivity.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_pedido")
public class ItemPedido {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int usuarioId;
    public int produtoId;
    public int quantidade;
}
