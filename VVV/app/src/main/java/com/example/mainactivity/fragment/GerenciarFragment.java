package com.example.mainactivity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.CadastroProdutoActivity;
import com.example.mainactivity.R;
import com.example.mainactivity.adapter.ProdutoAdapter;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class GerenciarFragment extends Fragment {
    private FloatingActionButton fabAdicionarProduto;
    private RecyclerView listaProdutos;
    private ProdutoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gerenciar, container, false);
        fabAdicionarProduto = view.findViewById(R.id.fabAdicionarProduto);
        listaProdutos = view.findViewById(R.id.listaProdutos);
        //formato da lista - vertical
        listaProdutos.setLayoutManager(new LinearLayoutManager(getContext()));

        fabAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), CadastroProdutoActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    //metodo resume para salvar estado
    @Override
    public void onResume() {
        super.onResume();
        carregarProdutosNaLista();
    }

    //metodo que pega o usuario logado, os produtos desse usuario do db, retorna pro adapter na recycleView
    private void carregarProdutosNaLista() {
        SharedPreferences preferencias = getActivity().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
        int idLojista = preferencias.getInt("idUsuario", -1);

        if(idLojista != -1) {
            List<Produto> produtoDoBanco = AppDatabase.getInstance(getContext())
                    .produtoDao().buscarProdutosDoLojista(idLojista);
            adapter = new ProdutoAdapter(produtoDoBanco);
            listaProdutos.setAdapter(adapter);
        }
    }

}