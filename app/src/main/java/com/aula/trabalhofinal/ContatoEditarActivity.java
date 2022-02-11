package com.aula.trabalhofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aula.trabalhofinal.bancodedados.BancoDeDados;
import com.aula.trabalhofinal.bancodedados.ContatoBanco;

public class ContatoEditarActivity extends AppCompatActivity {
   private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private Button btn_editar;
    int uid;
    private BancoDeDados banco;
    private ContatoBanco contato;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contato);
        nome = findViewById(R.id.nome_atualizar);
        telefone = findViewById(R.id.numero_atualizar);
        endereco = findViewById(R.id.endereco_atualizar);
        btn_editar = findViewById(R.id.btn_atualizar);
        telefone.isEnabled();
           Intent intent = getIntent();
           nome.setText(intent.getStringExtra("nome"));
           telefone.setText(intent.getStringExtra("telefone"));
           endereco.setText(intent.getStringExtra("endereco"));
           uid = intent.getIntExtra("id", -1);


            btn_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(nome.getText().toString().equals("")|| telefone.getText().toString().equals("")|| endereco.getText().toString().equals("")){
                        Toast.makeText(ContatoEditarActivity.this, "Erro!", Toast.LENGTH_SHORT).show();
                    }else {
                        contato = new ContatoBanco();
                        contato.setNome(nome.getText().toString());
                        contato.setTelefone(telefone.getText().toString());
                        contato.setEndereco(endereco.getText().toString());
                        contato.setUid(uid);
                        banco = BancoDeDados.getDbInstance(ContatoEditarActivity.this);
                        banco.contatoDAO().update(contato);
                        Toast.makeText(ContatoEditarActivity.this,"Contato salvo!", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(ContatoEditarActivity.this, MainActivity.class);
                        startActivity(intent1);
                    }
                }
            });

    }
}
