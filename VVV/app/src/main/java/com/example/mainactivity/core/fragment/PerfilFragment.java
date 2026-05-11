package com.example.mainactivity.core.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.R;
import com.example.mainactivity.core.LoginActivity;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Usuario;

public class PerfilFragment extends Fragment {
    private ImageView imgAbaPerfil;
    private TextView txtNomeAbaPerfil, txtTipoPerfil;

    @Nullable//indica que um parametro, retorno de metodo ou varioavel pode conter um valor null
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        Button btnSairSessao = view.findViewById(R.id.btnSairSessao);
        imgAbaPerfil = view.findViewById(R.id.imgAbaPerfil);
        txtNomeAbaPerfil = view.findViewById(R.id.txtNomeAbaPerfil);
        txtTipoPerfil = view.findViewById(R.id.txtTipoPerfil);

        carregarDadosDoUsuario();

        btnSairSessao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences preferenciais = getActivity().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
                preferenciais.edit().clear().apply();//limpa dados de sessa
                Toast.makeText(getContext(), getString(R.string.msg_logout), Toast.LENGTH_SHORT).show();
                //Voltando para tela de Login
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

                if(getActivity() != null){
                    getActivity().finish();
                }
            }
        });
        return view;
    }

    private void carregarDadosDoUsuario(){
        SharedPreferences preferenciais = getContext().getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
        int idUsuario = preferenciais.getInt("idUsuario", -1);

        if(idUsuario == -1){//se visitante
            txtNomeAbaPerfil.setText("Visitante");
            imgAbaPerfil.setImageResource(android.R.drawable.ic_menu_gallery);
            txtTipoPerfil.setVisibility(View.INVISIBLE);
            return;
        }else{
            Usuario usuario = AppDatabase.getInstance(getContext()).usuarioDao().buscarUsuarioPorId(idUsuario);
            if(usuario != null){
                txtNomeAbaPerfil.setText(usuario.nome);
                if (usuario.fotoPath != null && !usuario.fotoPath.isEmpty()) {
                    try {
                        imgAbaPerfil.setImageURI(Uri.parse(usuario.fotoPath));
                    } catch (SecurityException e) {
                        imgAbaPerfil.setImageResource(android.R.drawable.ic_menu_camera);
                    }
                }
            }
            //colocar o tipo de usuario
            if(usuario.tipoPerfil.equals("CLIENTE")){
                txtTipoPerfil.setText("Cliente");
            }else {
                txtTipoPerfil.setText("Lojista");
            }
        }
    }
}
