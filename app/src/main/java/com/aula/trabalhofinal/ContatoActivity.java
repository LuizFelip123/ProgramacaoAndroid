package com.aula.trabalhofinal;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.aula.trabalhofinal.bancodedados.BancoDeDados;
import com.aula.trabalhofinal.bancodedados.ContatoBanco;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContatoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private BancoDeDados banco;
    private ContatoBanco contato;
    private TextView nome, endereco, telefone;
    private LatLng latLng;
    private GoogleMap mMap;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
       ;
        myToolbar = findViewById(R.id.toolbar_contato);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.menu_contato);

        banco = BancoDeDados.getDbInstance(this.getApplicationContext());
        Intent intent = getIntent();
        int id = intent.getIntExtra("uid", -1);
        contato = banco.contatoDAO().loadAllByIds(id);
        nome = findViewById(R.id.nome);
        endereco = findViewById(R.id.endereco);
        telefone = findViewById(R.id.telefone);
        nome.setText(contato.getNome());
        endereco.setText(contato.getEndereco());
        telefone.setText(contato.getTelefone());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getLocation(endereco.getText().toString());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contato, menu);
        return true;
    }
    public void getLocation(String nomeLocal) {
        Log.v("", nomeLocal);
        Geocoder geocoder = new Geocoder(ContatoActivity.this);
        List<Address> listaEndereco = new ArrayList<>();
        try {
            Geocoder.isPresent();
            listaEndereco = geocoder.getFromLocationName(nomeLocal, 1);

            if (listaEndereco != null) {
                Log.v("", "erro");

                Log.v("", listaEndereco.get(0).getCountryName());
                Log.v("", listaEndereco.get(0).toString());

                latLng = new LatLng(listaEndereco.get(0).getLatitude(), listaEndereco.get(0).getLongitude());

            }
        } catch (IOException e) {
            Toast.makeText(ContatoActivity.this, "erro", Toast.LENGTH_SHORT).show();
            Log.v("", String.valueOf(Geocoder.isPresent()));
            Log.v("", e.getMessage());
            Log.d("myapp", Log.getStackTraceString(new IOException()));

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.op_excluir:
                excluir();
                return true;
            case R.id.op_editar:
                if(contato!=null) {
                    Intent intent = new Intent(ContatoActivity.this, ContatoEditarActivity.class);
                    intent.putExtra("nome", contato.getNome());
                    intent.putExtra("endereco", contato.getEndereco());
                    intent.putExtra("telefone", contato.getTelefone());
                    intent.putExtra("id", contato.getUid());
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void excluir(){
        if(contato!= null){
            banco = BancoDeDados.getDbInstance(ContatoActivity.this);
            banco.contatoDAO().delete(contato);
            Toast.makeText(ContatoActivity.this, "Contato excluido", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ContatoActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        if(latLng!=null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title(" "));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}