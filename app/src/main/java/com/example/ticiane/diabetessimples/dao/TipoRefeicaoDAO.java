package com.example.ticiane.diabetessimples.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.ticiane.diabetessimples.bean.TipoRefeicao;

import java.util.ArrayList;
import java.util.List;

public class TipoRefeicaoDAO extends ConfiguraAplicacaoDAO {

    // Tabela utilizada no SQLite
    private static final String TABLE_NAME = "TipoRefeicao";
    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;

    //Comando para realizar a inserção
    private static final String INSERT = "INSERT INTO "
            + TABLE_NAME + " (codigo, descricao) VALUES (?,?)";
    public TipoRefeicaoDAO(Context context)
    {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    // Método para inserção
    public long insert(TipoRefeicao tipoMedida)
    {
        this.insertStmt.bindLong(1, tipoMedida.getCodigo());
        this.insertStmt.bindString(2, tipoMedida.getDescricao());
        return this.insertStmt.executeInsert();
    }

    // Método onde passamos o código e ele retorna um objeto TipoRefeicao
// carregado com informações direto do banco de dados
    public TipoRefeicao findById(Integer codigo)
    {
        TipoRefeicao tipodr = new TipoRefeicao();
        String selectQuery = "SELECT codigo, descricao FROM  TipoRefeicao WHERE codigo=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] { codigo.toString()
        });
        if (cursor.moveToFirst()) {
            tipodr.setCodigo(cursor.getInt(0));
            tipodr.setDescricao(cursor.getString(1));
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
    public TipoRefeicao findByDescricao(String descricao)
    {
        TipoRefeicao tipodr = new TipoRefeicao();
        String selectQuery = "SELECT codigo, descricao FROM TipoRefeicao " +  "WHERE descricao=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] { descricao });
        if (cursor.moveToFirst()) {
            tipodr.setCodigo(cursor.getInt(0));
            tipodr.setDescricao(cursor.getString(1));
        }
        cursor.close();
        return tipodr;
    }


    // retorna-se uma lista com todos os gêneros
// (é este método que será usado no Spinner)
    public List<TipoRefeicao> selectAllTipoRefeicao()
    {
        String selectQuery = "SELECT codigo, descricao FROM  TipoRefeicao ";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {});
        return carregarLista(cursor);
    }

    // Este método é para carregar a lista recebendo um cursor carregado
// com a consulta do banco. Repare que mais de um método o utiliza, ou seja,
// reaproveitamente de código!
    private List<TipoRefeicao> carregarLista(Cursor cursor)
    {
        List<TipoRefeicao> list = new ArrayList<TipoRefeicao>();
        if (cursor.moveToFirst())
        {
            do
            {
                TipoRefeicao genero = new TipoRefeicao(
                        cursor.getInt(0),
                        cursor.getString(1)
                ) ;
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
