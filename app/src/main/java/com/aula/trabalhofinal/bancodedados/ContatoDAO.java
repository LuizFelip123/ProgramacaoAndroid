package com.aula.trabalhofinal.bancodedados;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContatoDAO {
    @Query("SELECT * FROM  contatobanco")
    List<ContatoBanco>  getAll();

    @Query("SELECT * FROM contatobanco WHERE uid in (:userIds)")
    List<ContatoBanco> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM CONTATOBANCO WHERE uid in (:userIds)")
    ContatoBanco loadAllByIds(int userIds);

    @Insert
    void insertAll(List<ContatoBanco> contatos);

    @Insert
    void insert(ContatoBanco contato);

    @Update
    void update(ContatoBanco contatoBanco);

    @Delete
    void delete(ContatoBanco contatoBanco);
}
