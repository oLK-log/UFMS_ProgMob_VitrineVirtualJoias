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

    //Query para buscar produto pelo nome digitado no campo de pesquisa

    @Query("SELECT * FROM produtos WHERE nome LIKE '%' || :busca || '%'")
    List<Produto> buscarProdutos(String busca);

    //Query que busca todos os produtos que pertencem a um Lojista específico
    @Query("SELECT * FROM produtos WHERE usuarioId = :idUsuario")
    List<Produto> buscarProdutosDoLojista(int idUsuario);
}
