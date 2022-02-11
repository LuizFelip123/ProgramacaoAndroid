package com.aula.trabalhofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aula.trabalhofinal.bancodedados.BancoDeDados;
import com.aula.trabalhofinal.bancodedados.ContatoBanco;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private EditText editDestino;
    private Button btnAdicionarContato;
    private CustomAdapter adapter;
    private RecyclerView recycler;
    private BancoDeDados banco;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        setSupportActionBar(myToolbar);
        btnAdicionarContato = findViewById(R.id.btn_adicionar);

        intiRecyclerView();
        banco = BancoDeDados.getDbInstance(this.getApplicationContext());
        List<ContatoBanco> contatos = banco.contatoDAO().getAll();
        adapter.setListaContato(contatos);
        btnAdicionarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroContatoActivity.class);
                startActivity(intent);
            }
        });


    }
    //iniciar o RecyclerView
    private  void intiRecyclerView(){
        recycler = findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(itemDecoration);

        adapter = new CustomAdapter(MainActivity.this);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(int idContato) {
                Intent intent = new Intent(MainActivity.this, ContatoActivity.class);
                intent.putExtra("uid", idContato);
                startActivity(intent);
            }
        });

    }
}