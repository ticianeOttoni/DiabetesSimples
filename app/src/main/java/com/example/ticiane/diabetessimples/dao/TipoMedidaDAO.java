package com.example.ticiane.diabetessimples.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.ticiane.diabetessimples.bean.TipoMedida;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ticiane on 29/11/2016.
 */

public class TipoMedidaDAO extends ConfiguraAplicacaoDAO {

    // Tabela utilizada no SQLite
    private static final String TABLE_NAME = "TipoMedida";
    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;
    private SQLiteStatement updateStmt;
    private SQLiteStatement deleteStmt;

    //Comando para realizar a inserção
    private static final String INSERT = "INSERT INTO "
            + TABLE_NAME + " (descricao, valorIdeal, valorOtimo) VALUES (?,?,?)";
    //Comando para realizar o update
    private static final String UPDATE = "UPDATE " + TABLE_NAME
            +  " SET descricao=?, valorIdeal=?, valorOtimo=? WHERE codigo=?";
    //Comando para realizar a remoção
    private static final String DELETE = "DELETE FROM " + TABLE_NAME
            +  " WHERE codigo=?";
    public TipoMedidaDAO(Context context)
    {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
        this.updateStmt = this.db.compileStatement(UPDATE);
        this.deleteStmt = this.db.compileStatement(DELETE);
    }

    // Método para inserção
    public long insert(TipoMedida tipoMedida)
    {
        this.insertStmt.bindString(1, tipoMedida.getDescricao());
        this.insertStmt.bindLong(2, tipoMedida.getValorIdeal());
        this.insertStmt.bindLong(3, tipoMedida.getValorOtimo());
        return this.insertStmt.executeInsert();
    }

    // Método para update
    public long update(TipoMedida tipoMedida)
    {
        this.updateStmt.bindString(1, tipoMedida.getDescricao());
        this.updateStmt.bindLong(2, tipoMedida.getValorIdeal());
        this.updateStmt.bindLong(3, tipoMedida.getValorOtimo());
        this.updateStmt.bindLong(4, tipoMedida.getCodigo());
        int retorno;
        try {
            retorno = this.updateStmt.executeUpdateDelete();
        } catch (Exception e){
            retorno = 2;
        }
        return retorno;
    }

    // Método para delete
    public long delete(TipoMedida tipoMedida)
    {
        this.deleteStmt.bindLong(1, tipoMedida.getCodigo());
        int retorno;
        try {
            retorno = this.deleteStmt.executeUpdateDelete();
        } catch (Exception e){
            retorno = 2;
        }
        return retorno;
    }

    // Método onde passamos o código e ele retorna um objeto TipoMedida
// carregado com informações direto do banco de dados
    public TipoMedida findById(Integer codigo)
    {
        TipoMedida tipodr = new TipoMedida();
        String selectQuery = "SELECT codigo, descricao, valorIdeal, valorOtimo FROM  TipoMedida WHERE codigo=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] { codigo.toString()
        });
        if (cursor.moveToFirst()) {
            tipodr.setCodigo(cursor.getInt(0));
            tipodr.setDescricao(cursor.getString(1));
            tipodr.setValorIdeal(cursor.getInt(2));
            tipodr.setValorOtimo(cursor.getInt(3));
        }
        cursor.close();
        return tipodr;
    }

    // Método apaga tudo (!)
    public void deleteAll()
    {
        this.db.delete(TABLE_NAME, null, null);
    }
    // Método onde passamos o NOME DA TIPOMEDIA e ele retorna um objeto
// Genero carregado com informações direto do banco de dados
    public TipoMedida findByDescricao(String descricao)
    {
        TipoMedida tipodr = new TipoMedida();
        String selectQuery = "SELECT codigo, descricao, valorIdeal, valorOtimo FROM TipoMedida " +  "WHERE descricao=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] { descricao });
        if (cursor.moveToFirst()) {
            tipodr.setCodigo(cursor.getInt(0));
            tipodr.setDescricao(cursor.getString(1));
            tipodr.setValorIdeal(cursor.getInt(2));
            tipodr.setValorOtimo(cursor.getInt(3));
        }
        cursor.close();
        return tipodr;
    }


    // retorna-se uma lista com todos os gêneros
// (é este método que será usado no Spinner)
    public List<TipoMedida> selectAllTipoMedida()
    {
        String selectQuery = "SELECT codigo, descricao, valorIdeal, valorOtimo FROM  TipoMedida ";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {});
        return carregarLista(cursor);
    }

    // Este método é para carregar a lista recebendo um cursor carregado
// com a consulta do banco. Repare que mais de um método o utiliza, ou seja,
// reaproveitamente de código!
    private List<TipoMedida> carregarLista(Cursor cursor)
    {
        List<TipoMedida> list = new ArrayList<TipoMedida>();
        if (cursor.moveToFirst())
        {
            do
            {
                TipoMedida genero = new TipoMedida(
                        cursor.getInt(0),
                        cursor.getString(1)
                ) ;
                genero.setValorIdeal(cursor.getInt(2));
                genero.setValorOtimo(cursor.getInt(3));
                list.add(genero);
            }
            while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return list;
    }
    // Encerra conexão com o banco.
    public void encerrarDB() {
        this.db.close();
    }
}
