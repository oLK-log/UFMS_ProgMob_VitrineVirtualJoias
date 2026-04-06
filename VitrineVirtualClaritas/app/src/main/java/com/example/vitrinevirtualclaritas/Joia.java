package com.example.vitrinevirtualclaritas;

public class Joia {
    private int imagem;
    private String nome;
    private String preco;

    //contrutor
    public Joia(int imagem, String nome, String preco){
        this.imagem = imagem;
        this.nome = nome;
        this.preco = preco;
    }

    //methods
    public int getImagem() {
        return imagem;
    }
    public String getNome() {
        return nome;
    }
    public String getPreco() {
        return preco;
    }
}
