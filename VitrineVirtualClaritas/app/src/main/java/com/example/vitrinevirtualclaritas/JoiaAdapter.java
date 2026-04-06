package com.example.vitrinevirtualclaritas;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class JoiaAdapter extends ArrayAdapter<Joia> {

    public JoiaAdapter(Context context, List<Joia> joias){
        super(context, 0, joias);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Joia joiaAtual = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grade_joia, parent, false);
        }

        ImageView imagemJoia = convertView.findViewById(R.id.img_produto_grade);
        TextView nomeJoia = convertView.findViewById(R.id.txt_nome_produto);
        TextView precoJoia = convertView.findViewById(R.id.txt_preco_produto);

        imagemJoia.setImageResource(joiaAtual.getImagem());
        nomeJoia.setText(joiaAtual.getNome());
        precoJoia.setText(joiaAtual.getPreco());

        return convertView;
    }
}
