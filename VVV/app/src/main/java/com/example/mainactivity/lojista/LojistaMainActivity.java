package com.example.mainactivity.lojista;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.R;
import com.example.mainactivity.core.fragment.PerfilFragment;
import com.example.mainactivity.lojista.fragment.GerenciarFragment;
import com.example.mainactivity.lojista.fragment.InicioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LojistaMainActivity extends AppCompatActivity {

    private BottomNavigationView barraNavegacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_lojista);

        // Mapeando a nossa barra de navegação
        barraNavegacao = findViewById(R.id.barra_navegacao);

        // Lógica para trocar os Fragmentos
        //tela padrao
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.palco_fragmento, new InicioFragment()).commit();
        }
        //Ouvintes para os cliques
        barraNavegacao.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentSelecionado = null;
                int itemId = item.getItemId();
                if(itemId == R.id.menu_inicio){
                    fragmentSelecionado = new InicioFragment();
                } else if (itemId == R.id.menu_gerenciarProdutos) {
                    fragmentSelecionado = new GerenciarFragment();
                } else if( itemId == R.id.menu_perfil){
                    fragmentSelecionado = new PerfilFragment();
                }
                //colocar no palco/tela
                if(fragmentSelecionado != null){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.palco_fragmento, fragmentSelecionado).commit();
                    return true;
                }
                return false;
            }
        });
    }
}