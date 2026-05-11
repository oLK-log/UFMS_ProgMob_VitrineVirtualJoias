package com.example.mainactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainactivity.database.AppDatabase;
import com.example.mainactivity.model.Usuario;

import java.io.File;
import java.io.FileOutputStream;

public class CadastroUsuarioActivity extends AppCompatActivity{
    private ImageView imgFotoPerfil;
    private EditText editNome, editEmail, editSenha;
    private Button btnTirarFoto, btnCadastrar;
    //variavel para guardar foto antes de salvar
    private Bitmap fotoCapturada = null;
    private RadioGroup rgTipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario); //conexao xom xlm
        //fazer a conexao java e xml
        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnTirarFoto = findViewById(R.id.btnTirarFoto);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        rgTipoUsuario = findViewById(R.id.rgTipoUsuario);

        //configurar acao da camera
        btnTirarFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//cria a intencao de abrir camera
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                abrirCamera.launch(intentCamera);
            }
        });

        //Configurar acao de salvar no banco de dados
        btnCadastrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                salvarUsuarioNoBanco();
            }
        });
    }

    //pegar imagem após a camera fechar
    private final ActivityResultLauncher<Intent> abrirCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    //pega a foto, salva na variavel e mostra na tela
                    fotoCapturada = (Bitmap) result.getData().getExtras().get("data");
                    imgFotoPerfil.setImageBitmap(fotoCapturada);
                }
            }
    );

    //funcao para pegar a imagem(bit map) e salvar no armazenamentp
    private String salvarImagemLocal(Bitmap bitmap){
        if(bitmap == null) return "";
        try{
            //criando arquivo localmente
            File arquivo = new File(getFilesDir(), "perfil_" + System.currentTimeMillis()+ ".png");
            FileOutputStream out = new FileOutputStream(arquivo);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            //retornando endereco de URI onde a imagem está salva
            return Uri.fromFile(arquivo).toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    //Banco de DAdos - ROOM
    private void salvarUsuarioNoBanco() {
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {//se vazio
            Toast.makeText(this, "Preencha todos os campo!", Toast.LENGTH_SHORT).show();
            return;
        }

        String caminhoFoto = salvarImagemLocal(fotoCapturada);

        //tipo de perfil
        String tipoSelecionado = "CLIENTE";
        int idRadioSelecionado = rgTipoUsuario.getCheckedRadioButtonId();
        if(idRadioSelecionado == R.id.rbLojista){
            tipoSelecionado = "LOJISTA";
        }

        //molde usuario
        Usuario novoUsuario = new Usuario();
        novoUsuario.nome = nome;
        novoUsuario.email = email;
        novoUsuario.senha = senha;
        novoUsuario.fotoPath = caminhoFoto;
        novoUsuario.tipoPerfil=tipoSelecionado;

        //salva o molde
        AppDatabase.getInstance(this).usuarioDao().cadastrar(novoUsuario);

        //Limpar campis
        Toast.makeText(this, "Usuário cadastrado com Suscesso!", Toast.LENGTH_LONG).show();
        editNome.setText("");
        editEmail.setText("");
        editSenha.setText("");
        imgFotoPerfil.setImageBitmap(null);


    }

}