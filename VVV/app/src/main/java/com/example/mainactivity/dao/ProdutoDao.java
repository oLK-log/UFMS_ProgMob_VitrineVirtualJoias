package com.example.mainactivity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mainactivity.model.Produto;

import java.util.List;

@Dao
public interface ProdutoDao {
    @Insert
    void inserir(Produto produto);

    @Update
    void atualizar(Produto produto);

    @Delete
    void excluir(Produto produto);

    //Query para buscar produtos de uma loja por meio do codigo
    @Query("SELECT * FROM produtos WHERE codigoLoja = :codigoLoja")
    List<Produto> listaPorLoja(String codigoLoja);

    //Query para buscar produto pelo nome digitado no campo de pesquisa

    @Query("SELECT * FROM produtos WHERE nome LIKE '%' || :busca || '%'")
    List<Produto> buscarProdutos(String busca);
}
