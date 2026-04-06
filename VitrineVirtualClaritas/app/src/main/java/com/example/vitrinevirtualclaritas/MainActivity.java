package com.example.vitrinevirtualclaritas;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView barraNavegacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barraNavegacao = findViewById(R.id.barra_navegacao);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_fragmentos, new DestaqueFragment())
                    .commit();
        }

        barraNavegacao.setOnItemSelectedListener(item -> {
            Fragment fragmentSelecionado = null;

            int idBtnClicado = item.getItemId();

            if(idBtnClicado == R.id.nav_destaques) {
                fragmentSelecionado = new DestaqueFragment();
            } else if (idBtnClicado == R.id.nav_categorias) {
                fragmentSelecionado = new CategoriasFragment();
            } else if (idBtnClicado == R.id.nav_galeria) {
                fragmentSelecionado = new GaleriaFragment();
            }

            if(fragmentSelecionado != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragmentos, fragmentSelecionado).commit();
                return true;
            }
            return false;
        });

    }
}

// O setOnItemSelectedListener rodando em segundo plano. Ele acompanha os cliques dados na barra
//O replace().commit() coloca/realiza a troca de tela