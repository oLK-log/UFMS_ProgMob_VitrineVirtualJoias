package com.example.mainactivity.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.model.Produto;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {
    private List<Produto> listaProdutos;
    //construtor
    public ProdutoAdapter(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    //criar molde
    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }
    //preencher molde
    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produtoAtual = listaProdutos.get(position);

        holder.txtItemNome.setText(produtoAtual.nome);
        holder.txtItemDescricao.setText(produtoAtual.descricao);
        //formatar o que aparece no campo texto para dinheiro
        holder.txtItemPreco.setText(String.format("R$ %.2f", produtoAtual.preco));

        //verificar se tem foto
        if(produtoAtual.imagemUri != null && !produtoAtual.imagemUri.isEmpty()) {
            try {
                // Tenta carregar a imagem com a Uri salva
                holder.imgItemProduto.setImageURI(Uri.parse(produtoAtual.imagemUri));
            } catch (SecurityException e) {
                holder.imgItemProduto.setImageResource(android.R.drawable.ic_menu_camera);
            }
        } else {
            holder.imgItemProduto.setImageResource(android.R.drawable.ic_menu_camera);
        }
    }

    //definir tamanho da rolagem - comunicacao com lista
    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    //guardar referencias
    static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemProduto;
        TextView txtItemNome, txtItemDescricao, txtItemPreco;

        public ProdutoViewHolder(@NonNull View itemView){
            super(itemView);
            imgItemProduto = itemView.findViewById(R.id.imgItemProduto);
            txtItemNome = itemView.findViewById(R.id.txtItemNome);
            txtItemDescricao = itemView.findViewById(R.id.txtItemDescricao);
            txtItemPreco = itemView.findViewById(R.id.txtItemPreco);
        }
    }

}
