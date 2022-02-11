package com.aula.trabalhofinal;



import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.trabalhofinal.bancodedados.BancoDeDados;
import com.aula.trabalhofinal.bancodedados.ContatoBanco;

import org.w3c.dom.Text;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private OnItemClickListener onItemClickListener;
    private Context context;
    private List<ContatoBanco> listaContatos;
    //construtor
    public CustomAdapter(Context context){
        this.context = context;
    }

    public void setListaContato(List<ContatoBanco> listaContato){
        this.listaContatos= listaContato;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int posicao){
        holder.id = this.listaContatos.get(posicao).uid;
        holder.tNome.setText(this.listaContatos.get(posicao).nome);
        holder.tTelefone.setText(this.listaContatos.get(posicao).telefone);
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(listaContatos != null) return listaContatos.size();

        return 0;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        public int id;
        public TextView tNome;
        public TextView tTelefone;
        public ContatoBanco contato;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tNome = itemView.findViewById(R.id.nomeContato);
            tTelefone = itemView.findViewById(R.id.telefoneContato);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     int posicao = getAdapterPosition();
                     if(posicao!=RecyclerView.NO_POSITION && listaContatos!= null){

                        int idContato = listaContatos.get(posicao).uid;
                         onItemClickListener.onClickItem(idContato);
                     }

                }
            });
        }

    }

}
