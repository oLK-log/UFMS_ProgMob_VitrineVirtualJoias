package com.example.mainactivity.database.dao;

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

    //Query para buscar produto pelo ID
    @Query("SELECT * FROM produtos WHERE id = :idProduto")
    Produto buscarProdutoPorId(int idProduto);
    //atualiza o status de destaque de um produto específico
    @Query("UPDATE produtos SET isDestaque = :status WHERE id = :idProduto")
    void atualizarDestaque(int idProduto, boolean status);
    //Query para buscar os produtos em destaque/com estrela
    @Query("SELECT * FROM produtos WHERE isDestaque = 1")
    List<Produto> buscarProdutosEmDestaque();
    //Query para buscar um produto por nome
    @Query("SELECT * FROM produtos WHERE nome LIKE '%' || :termoBusca || '%'")
    List<Produto> buscarProdutosPorNome(String termoBusca);
}
