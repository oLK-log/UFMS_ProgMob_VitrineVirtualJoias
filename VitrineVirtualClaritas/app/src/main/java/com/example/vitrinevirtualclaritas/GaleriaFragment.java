
package com.example.vitrinevirtualclaritas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class GaleriaFragment extends Fragment {
    public GaleriaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        View view = inflater.inflate(R.layout.fragment_galeria, container, false);
        GridView gridJoias = view.findViewById(R.id.grid_joias);
        List<Joia> listaDeJoias=new ArrayList<>();

        listaDeJoias.add(new Joia(R.drawable.aneisgrid1, "Anel cintilante", "R$ 1 500,00"));
        listaDeJoias.add(new Joia(R.drawable.conjuntogrid2, "Conjunto Jóias Irís", "R$ 3 000,00"));
        listaDeJoias.add(new Joia(R.drawable.conjuntogrid3, "Conjunto Jóias Eternium", "R$ 10 000,00"));
        listaDeJoias.add(new Joia(R.drawable.brincosgrid4, "Brincos nuance", "R$ 800,00"));

        JoiaAdapter adapter = new JoiaAdapter(getContext(), listaDeJoias);
        gridJoias.setAdapter(adapter);

        gridJoias.setOnItemClickListener((parente, viewClicada, position, id) -> {
            Joia joiaClicada = listaDeJoias.get(position);
            Toast.makeText(getContext(), "Escolha Feita! Produto " + joiaClicada.getNome(), Toast.LENGTH_SHORT).show();
            android.media.MediaPlayer player = android.media.MediaPlayer.create(getContext(), R.raw.som_magic);
            player.start();
            //boa pratica
            player.setOnCompletionListener(mp -> mp.release());
        });

        return view;
    }
}

