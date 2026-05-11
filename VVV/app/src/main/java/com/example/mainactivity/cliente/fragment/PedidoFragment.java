package com.example.mainactivity.cliente.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.cliente.adapter.PedidoAdapter;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.database.dao.PedidoDao;
import com.example.mainactivity.model.ItemPedido;
import com.example.mainactivity.model.Produto;

import java.util.List;
public class PedidoFragment extends Fragment{
    private RecyclerView rvItensPedido;
    private TextView txtPedidoVazio, txtValorTotalPedido;
    private LinearLayout layoutRodapePedido;
    private Button btnFinalizarPedido;
    private PedidoAdapter adapter;
    private List<ItemPedido> listaItens;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido, container, false);

        rvItensPedido = view.findViewById(R.id.rvItensPedido);
        txtPedidoVazio= view.findViewById(R.id.txtPedidoVazio);
        txtValorTotalPedido=view.findViewById(R.id.txtValorTotalPedido);
        layoutRodapePedido=view.findViewById(R.id.layoutRodapePedido);
        btnFinalizarPedido=view.findViewById(R.id.btnFinalizarPedido);

        rvItensPedido.setLayoutManager(new LinearLayoutManager(getContext()));//configura a lista p ser verrtical
        carregarPedido();
        //Solicitar dados se n tiver logado
        btnFinalizarPedido.setOnClickListener(v->{
            //Tocando efeito sonoro
            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.som_magic);
            if(mediaPlayer != null){
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            }
            Toast.makeText(getContext(), "Pedido Enviado", Toast.LENGTH_SHORT).show();
        });
        return view;
    }

    //met q carrega o pedido ao pegar o usuario, trazer a lista de itens
    private void carregarPedido(){
        SharedPreferences preferenciais = getContext().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
        int idusuario = preferenciais.getInt("idUsuario", -1);//visitante eh -1

        PedidoDao pedidoDao = AppDatabase.getInstance(getContext()).pedidoDao();
        listaItens=pedidoDao.buscarPedidoDoUsuario(idusuario);

        if(listaItens.isEmpty()){
            rvItensPedido.setVisibility(View.GONE);
            layoutRodapePedido.setVisibility(View.GONE);
            txtPedidoVazio.setVisibility(View.VISIBLE);
        }else {
            rvItensPedido.setVisibility(View.VISIBLE);
            layoutRodapePedido.setVisibility(View.VISIBLE);
            txtPedidoVazio.setVisibility(View.GONE);
            //instanciando adapter
            adapter = new PedidoAdapter(listaItens, item -> removerItem(item));
            rvItensPedido.setAdapter(adapter);

            calcularTotal();
        }
        //txtPedidoVazio.setVisibility(View.INVISIBLE);
    }

    //met para remover item da lista de pedido
    private void removerItem(ItemPedido item){
        PedidoDao pedidoDao = AppDatabase.getInstance(getContext()).pedidoDao();
        pedidoDao.removerItem(item);
        Toast.makeText(getContext(), "Item removido!", Toast.LENGTH_SHORT).show();
        carregarPedido();
    }

    //meth para calcular o total do pedido - preco x qutd
    private void calcularTotal(){
        double total = 0.0;
        AppDatabase db = AppDatabase.getInstance(getContext());

        for(ItemPedido item : listaItens){
            Produto p = db.produtoDao().buscarProdutoPorId(item.produtoId);
            if(p!=null){
                total += p.preco * item.quantidade;
            }
        }
        txtValorTotalPedido.setText(String.format("R$ %.2f", total));
    }

}
