package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private EditText editLoginEmail, editLoginSenha;
    private Button btnEntrar;
    private TextView txtIrParaCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //conectando xml ao java
        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginSenha = findViewById(R.id.editLoginSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        txtIrParaCadastro = findViewById(R.id.txtIrParaCadastro);

        //acoes
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarLogin();
            }
        });

        txtIrParaCadastro.setOnClickListener(new View.OnClickListener() { //captura a intencao de mudar de tela
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //banco de dados
    private void tentarLogin() {
        String email = editLoginEmail.getText().toString();
        String senha = editLoginSenha.getText().toString();

        if(email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha o e-mail e senha!", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuarioLogado = AppDatabase.getInstance(this).usuarioDao().fazerLogin(email, senha);
        if(usuarioLogado != null){
            //guardar ID do lojista
            android.content.SharedPreferences preferenciais = getSharedPreferences("sessao_vvv", MODE_PRIVATE);
            preferenciais.edit().putInt("idUsuario", usuarioLogado.id).apply();
            //informar que usuario está logado
            Toast.makeText(this,"Usuário "+ usuarioLogado.nome + "logado", Toast.LENGTH_LONG).show();
            //ponto de acesso para a tela principal
            //intent do Login para Vitrine
            Intent intent = new Intent(LoginActivity.this, VitrineActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "O E-mail ou senha está incorreto! Verifique e tente novamente.", Toast.LENGTH_LONG).show();
        }
    }
}