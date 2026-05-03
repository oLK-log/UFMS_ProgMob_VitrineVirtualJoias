package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.R;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {
    private ImageView imgFotoProduto;
    private EditText editNomeProduto, editDescricaoProduto, editPrecoProduto;
    private Button btnSalvarProduto;
    private String uriFotoSelecionada = "";
    //abrir galeria local
    private ActivityResultLauncher<String> abrirGaleria;

    @Override
   protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        //fzd conexao XML e JAVA
        imgFotoProduto = findViewById(R.id.imgFotoProduto);
        editNomeProduto = findViewById(R.id.editNomeProduto);
        editDescricaoProduto = findViewById(R.id.editDescricaoProduto);
        editPrecoProduto = findViewById(R.id.editPrecoProduto);
        btnSalvarProduto = findViewById(R.id.btnSalvarProduto);
        //destino foto
        abrirGaleria = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if(uri != null){
                            uriFotoSelecionada = uri.toString();//guarda para o bd
                            imgFotoProduto.setImageURI(uri);
                        }
                    }
                }
        );
        //procura imagem
        imgFotoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                abrirGaleria.launch("image/*");
            }
        });
        //salvar bd
        btnSalvarProduto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                salvarProdutoNoBanco();
            }
        });
    }

    private void salvarProdutoNoBanco() {
        String nome = editNomeProduto.getText().toString();
        String descricao = editDescricaoProduto.getText().toString();
        String precoTexto = editPrecoProduto.getText().toString();
        //validacao
        if(nome.isEmpty() || precoTexto.isEmpty()) {
            Toast.makeText(this,"Nome e preço são campos obrigatórios!!", Toast.LENGTH_SHORT).show();
            return;
        }
        //pegar Lojista/user
        SharedPreferences preferencias = getSharedPreferences("sessao_vvv", Context.MODE_PRIVATE);
        int idLojista = preferencias.getInt("idUsuario", -1);//vai retornar -1 se der um erro
        if(idLojista == -1){
            Toast.makeText(this, "Erro na Sessao do Usuario. Faça login novamente!", Toast.LENGTH_LONG).show();
            return;
        }

        //Produto
        Produto novoProduto = new Produto();
        novoProduto.nome = nome;
        novoProduto.descricao = descricao;
        // conversoes
        novoProduto.preco = Double.parseDouble(precoTexto);
        novoProduto.usuarioId = idLojista;
        novoProduto.imagemUri = uriFotoSelecionada;

        //envio pro DB
        AppDatabase.getInstance(this).produtoDao().inserir(novoProduto);
        Toast.makeText(this, "Produto cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();

        //limpar formulario
        finish();
        /*
        editNomeProduto.setText("");
        editDescricaoProduto.setText("");
        editPrecoProduto.setText("");
        imgFotoProduto.setImageResource(android.R.drawable.ic_menu_camera);
        uriFotoSelecionada = "";
        */
    }

}
