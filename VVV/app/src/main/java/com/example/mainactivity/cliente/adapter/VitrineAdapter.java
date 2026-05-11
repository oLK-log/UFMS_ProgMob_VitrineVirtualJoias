package com.example.mainactivity.cliente.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mainactivity.R;
import com.example.mainactivity.model.Produto;

import java.util.List;
import android.content.Intent;
import com.example.mainactivity.cliente.DetalhesProdutoActivity;

public class VitrineAdapter extends RecyclerView.Adapter<VitrineAdapter.VitrineViewHolder> {
    private List<Produto> listaProdutos;
    public VitrineAdapter(List<Produto> listaProdutos){
        this.listaProdutos = listaProdutos;
    }

    @NonNull
    @Override
    public VitrineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto_vitrine, parent, false);
        return new VitrineViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull VitrineViewHolder holder, int position){
        Produto produtoAtual = listaProdutos.get(position);

        holder.txtVitrineNome.setText(produtoAtual.nome);
        holder.txtVitrinePreco.setText(String.format("R$ %.2f", produtoAtual.preco));

        if(produtoAtual.imagemUri != null && !produtoAtual.imagemUri.isEmpty()){
            try{
                holder.imgVitrineProduto.setImageURI(Uri.parse(produtoAtual.imagemUri));
            }catch (SecurityException e){
                holder.imgVitrineProduto.setImageResource(android.R.drawable.ic_menu_camera);
            }
        }else {
            holder.imgVitrineProduto.setImageResource(android.R.drawable.ic_menu_camera);
        }
        //Implementar clique no futurp levando para tela de Detalhes do Produto
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), DetalhesProdutoActivity.class);
                intent.putExtra("idProdutoDetalhe", produtoAtual.id);//id p/ prox tela utilizar
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return listaProdutos.size();
    }

    //guarda os dados do item_vitrine.xml
    static class VitrineViewHolder extends RecyclerView.ViewHolder{
        ImageView imgVitrineProduto;
        TextView txtVitrineNome, txtVitrinePreco;
        public VitrineViewHolder(@NonNull View itemView){
            super(itemView);
            imgVitrineProduto = itemView.findViewById(R.id.imgVitrineProduto);
            txtVitrineNome = itemView.findViewById(R.id.txtVitrineNome);
            txtVitrinePreco = itemView.findViewById(R.id.txtVitrinePreco);
        }
    }

    // Metodo para trocar a lista atual pela lista filtrada da busca
    public void atualizarLista(List<Produto> novaLista) {
        this.listaProdutos = novaLista; //
        notifyDataSetChanged();
    }

}
