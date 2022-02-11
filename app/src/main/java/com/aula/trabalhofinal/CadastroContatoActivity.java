package com.aula.trabalhofinal;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aula.trabalhofinal.bancodedados.BancoDeDados;
import com.aula.trabalhofinal.bancodedados.ContatoBanco;

public class CadastroContatoActivity extends AppCompatActivity {
    private Button botaoAdicionar;
    private ContatoBanco contato;
    private EditText editNome, editTelefone, editEndereco;
    public static final String CHANNEL_1_ID ="1";
    private BancoDeDados bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);
        botaoAdicionar = findViewById(R.id.btn_adicionar);
        editEndereco = findViewById(R.id.campo_endereco);
        editNome = findViewById(R.id.campo_nome);
        editTelefone = findViewById(R.id.campo_telefone);
        //Criar a Instância do banco de dados
        bd = BancoDeDados.getDbInstance(this.getApplicationContext());

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,0);
        createNotificationChannel();

        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editEndereco.getText().toString().equals("") || editNome.getText().toString().equals("")|| editTelefone.getText().toString().equals("")) {
                    Toast.makeText(CadastroContatoActivity.this, "Campo vazio!", Toast.LENGTH_SHORT).show();
                }else{
                    contato = new ContatoBanco(editEndereco.getText().toString(), editNome.getText().toString(), editTelefone.getText().toString());
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(CadastroContatoActivity.this, CHANNEL_1_ID)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle("Contato Adicionado!")
                            .setContentText(contato.getNome())
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(contato.toString()))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(CadastroContatoActivity.this);


                    notificationManager.notify(1, builder.build());
                    //voltar para a tela principal e armazenar no banco de dados

                    bd.contatoDAO().insert(contato);
                    Intent intentMain = new Intent(CadastroContatoActivity.this, MainActivity.class);
                    startActivity(intentMain);
                }
            }
        });
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}