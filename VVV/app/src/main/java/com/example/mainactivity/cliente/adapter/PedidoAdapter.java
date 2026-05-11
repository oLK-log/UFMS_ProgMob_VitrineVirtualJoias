package com.example.mainactivity.cliente.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainactivity.R;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.ItemPedido;
import com.example.mainactivity.model.Produto;
import java.util.List;
public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    private List<ItemPedido> listaItens;
    private OnItemRemovidoListener listener;

    //escutando mudanca valor
    public interface OnItemRemovidoListener {
        void onRemover(ItemPedido item);
    }

    public PedidoAdapter(List<ItemPedido> listaItens, OnItemRemovidoListener listener) {
        this.listaItens = listaItens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_lista, parent, false);
        return new PedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        ItemPedido item = listaItens.get(position);

        Produto p = AppDatabase.getInstance(holder.itemView.getContext()).produtoDao().buscarProdutoPorId(item.produtoId);
        if(p != null){
            holder.txtNome.setText(p.nome);
            holder.txtQtdPreco.setText(item.quantidade + " un. x "+ String.format("R$ %.2f", p.preco));

            if(p.imagemUri != null && !p.imagemUri.isEmpty()) {
                try{
                    holder.img.setImageURI(Uri.parse(p.imagemUri));
                }catch (SecurityException e){
                    holder.img.setImageResource(android.R.drawable.ic_menu_camera);
                }
            }
        }
        holder.btnRemover.setOnClickListener(V -> listener.onRemover(item));
    }

    @Override
    public int getItemCount(){
        return listaItens.size();
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtNome, txtQtdPreco;
        ImageButton btnRemover;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgItemPedido);
            txtNome = itemView.findViewById(R.id.txtItemNome);
            txtQtdPreco = itemView.findViewById(R.id.txtItemQtdPreco);
            btnRemover = itemView.findViewById(R.id.btnRemoverItemPedido);
        }
    }
}
