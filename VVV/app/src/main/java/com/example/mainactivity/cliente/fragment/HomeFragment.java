package com.example.mainactivity.cliente.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.cliente.adapter.VitrineAdapter;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Produto;

import java.util.List;
public class HomeFragment extends Fragment{
    private RecyclerView recyclerVitrine;
    private VitrineAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerVitrine = view.findViewById(R.id.recyclerVitrine);
        recyclerVitrine.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    //atualizar a vitrine quandp p cliente entrar
    @Override
    public void onResume(){
        super.onResume();
        carregarVitrine();
    }
    private void carregarVitrine(){
        //pega produtos em destaque
        List<Produto> produtosDestaque = AppDatabase.getInstance(getContext()).produtoDao().buscarProdutosEmDestaque();
        adapter = new VitrineAdapter(produtosDestaque);//envia lista
        recyclerVitrine.setAdapter(adapter);//conecta na tela
    }
}
