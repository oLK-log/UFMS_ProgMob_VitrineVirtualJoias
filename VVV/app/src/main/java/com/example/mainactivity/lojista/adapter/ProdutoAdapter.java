package com.example.mainactivity.lojista.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.R;
import com.example.mainactivity.lojista.CadastroProdutoActivity;
import com.example.mainactivity.model.Produto;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {
    private List<Produto> listaProdutos;
    //construtor
    public ProdutoAdapter(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    //met para atualizar os dados do adaptador
    public void atualizarLista(List<Produto> novaLista) {
        this.listaProdutos = novaLista;
        notifyDataSetChanged();
    }

    //criar molde
    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto_gerenciar, parent, false);
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
        //acao de clique no cartao do produto
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                android.content.Intent intent = new android.content.Intent(v.getContext(), CadastroProdutoActivity.class);//chama tela de cadastro
                intent.putExtra("idProdutoEditado", produtoAtual.id);//usa id do produto
                v.getContext().startActivity(intent);
            }
        });
        //logica para estrela de Destaque
            //surgimento
        if(produtoAtual.isDestaque){
            holder.estrelaDestaque.setImageResource(android.R.drawable.btn_star_big_on);
        }else {
            holder.estrelaDestaque.setImageResource(android.R.drawable.btn_star_big_off);
        }
            //clique
        holder.estrelaDestaque.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean novoStatus = !produtoAtual.isDestaque;
                produtoAtual.isDestaque = novoStatus;

                if(novoStatus){
                    holder.estrelaDestaque.setImageResource(android.R.drawable.btn_star_big_on);
                }else{
                    holder.estrelaDestaque.setImageResource(android.R.drawable.btn_star_big_off);
                }

                com.example.mainactivity.database.AppDatabase.getInstance(v.getContext()).produtoDao().atualizarDestaque(produtoAtual.id, novoStatus);
            }
        });
    }

    //definir tamanho da rolagem - comunicacao com lista
    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    //guardar referencias
    static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemProduto, estrelaDestaque;
        TextView txtItemNome, txtItemDescricao, txtItemPreco;

        public ProdutoViewHolder(@NonNull View itemView){
            super(itemView);
            imgItemProduto = itemView.findViewById(R.id.imgItemProduto);
            txtItemNome = itemView.findViewById(R.id.txtItemNome);
            txtItemDescricao = itemView.findViewById(R.id.txtItemDescricao);
            txtItemPreco = itemView.findViewById(R.id.txtItemPreco);
            estrelaDestaque = itemView.findViewById(R.id.estrelaDestaque);
        }
    }

}
