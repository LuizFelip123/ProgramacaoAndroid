package com.aula.trabalhofinal.bancodedados;

import android.content.Context;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContatoBanco.class}, version = 1)
public abstract class BancoDeDados extends RoomDatabase {
    public abstract ContatoDAO contatoDAO();

    private static BancoDeDados INSTANCE;

    public static BancoDeDados getDbInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BancoDeDados.class,"Contatos")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
