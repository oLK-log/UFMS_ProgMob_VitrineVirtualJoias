package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.R;
import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {
    private TextView txtTituloTelaCadastro;
    private ImageView imgFotoProduto;
    private EditText editNomeProduto, editDescricaoProduto, editPrecoProduto;
    private Button btnSalvarProduto, btnExcluirProduto;
    private String uriFotoSelecionada = "";
    //abrir galeria local
    private ActivityResultLauncher<String> abrirGaleria;
    private int idProdutoEdicao = -1;//-1 significa que é produto novo

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
        btnExcluirProduto = findViewById(R.id.btnExcluirProduto);
        txtTituloTelaCadastro = findViewById(R.id.txtTituloTelaCadastro);
        //destino foto
        abrirGaleria = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if(uri != null){
                            //ft temporaria
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
        //verificar se o ID do produto foi pego pelo adapter
        idProdutoEdicao = getIntent().getIntExtra("idProdutoEditado", -1);
        if(idProdutoEdicao != -1){
            prepararTelaEdicao();
        }
        //salvar bd
        btnSalvarProduto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                salvarProdutoNoBanco();
            }
        });

        //Excluir
        btnExcluirProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produto produtoDeletado = new Produto();
                produtoDeletado.id = idProdutoEdicao;
                AppDatabase.getInstance(CadastroProdutoActivity.this).produtoDao().excluir(produtoDeletado);
                Toast.makeText(CadastroProdutoActivity.this, "Produto excluído com sucesso!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void prepararTelaEdicao() {
        Produto produtoAntigo = AppDatabase.getInstance(this).produtoDao().buscarProdutoPorId(idProdutoEdicao);

        if(produtoAntigo != null){
            editNomeProduto.setText(produtoAntigo.nome);
            editDescricaoProduto.setText(produtoAntigo.descricao);
            editPrecoProduto.setText(String.valueOf(produtoAntigo.preco));
            uriFotoSelecionada = produtoAntigo.imagemUri;
            if(uriFotoSelecionada !=null && !uriFotoSelecionada.isEmpty()){
                try {
                    imgFotoProduto.setImageURI(Uri.parse(uriFotoSelecionada));
                }catch (Exception e){
                    imgFotoProduto.setImageResource(android.R.drawable.ic_menu_camera);
                }
            }
            //muda o texto do btn Salvar para Atualizar
            btnSalvarProduto.setText("Atualizar");
            //Faz o botao excluir ficar visivel
            btnExcluirProduto.setVisibility(View.VISIBLE);
            //mudar titulo tela para "Atualizar Produto"
            txtTituloTelaCadastro.setText("Atualizar Produto");

        }
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
        //salvando a imagem
        String caminhoDefinitivoDaFoto=uriFotoSelecionada;
        if(!uriFotoSelecionada.isEmpty() && uriFotoSelecionada.startsWith("content://")){
            caminhoDefinitivoDaFoto=salvarImagemNaMemoriaDoApp(Uri.parse(uriFotoSelecionada));
        }


        //Produto
        Produto novoProduto = new Produto();
        novoProduto.nome = nome;
        novoProduto.descricao = descricao;
        // conversoes
        novoProduto.preco = Double.parseDouble(precoTexto);
        novoProduto.usuarioId = idLojista;
        //salvar caminho
        novoProduto.imagemUri = caminhoDefinitivoDaFoto;

        //logica de decisao-inserir ou atualizar
        if(idProdutoEdicao!= -1){ //edicao
            novoProduto.id = idProdutoEdicao;
            AppDatabase.getInstance(this).produtoDao().atualizar(novoProduto);
            Toast.makeText(this, "Produto atualizado com sucesso", Toast.LENGTH_SHORT).show();
        } else { //produto novo
            //envio pro DB
            AppDatabase.getInstance(this).produtoDao().inserir(novoProduto);
            Toast.makeText(this, "Produto cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();
        }

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
    //metodo para resolver problema de nao manter foto
    //pega a foto temporária e faz uma cópia local no App
    private String salvarImagemNaMemoriaDoApp(Uri uriGaleria) {
        try{
            java.io.InputStream inputStream = getContentResolver().openInputStream(uriGaleria);
            String nomeArquivo = "produto" + System.currentTimeMillis() + ".jpg";
            java.io.File arquivoInterno = new java.io.File(getFilesDir(), nomeArquivo);
            java.io.FileOutputStream outputStream= new java.io.FileOutputStream(arquivoInterno);

            //copiar dados
            byte[] buffer = new byte[1024];
            int tamanho;
            while ((tamanho = inputStream.read(buffer))>0) {
                outputStream.write(buffer, 0, tamanho);
            }
            //encerrar conexoes
            outputStream.close();
            inputStream.close();

            return Uri.fromFile(arquivoInterno).toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";//retorna vazio para não bugar o banco
        }
    }


}
