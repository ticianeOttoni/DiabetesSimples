package com.example.ticiane.diabetessimples.dao;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ticiane.diabetessimples.bean.Medida;
import com.example.ticiane.diabetessimples.bean.TipoMedida;

import java.util.ArrayList;
import java.util.List;

public class MedidaDAO extends ConfiguraAplicacaoDAO {

    // Tabela utilizada no SQLite
    private static final String TABLE_NAME = "Medida";
    private Context context;
    private SQLiteDatabase db;
    private TipoMedidaDAO tipoMedidaDAO;
    private TipoRefeicaoDAO tipoRefeicaoDAO;



    public MedidaDAO(Context context)
    {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        tipoMedidaDAO = new TipoMedidaDAO(context);
        tipoRefeicaoDAO = new TipoRefeicaoDAO(context);
    }


    // Método para inserção
    public long insert(Medida medida)
    {
        SQLiteStatement cmdInsert;
        String INSERT = "INSERT INTO "
                + TABLE_NAME + " (dataHora, codTipoMedida, codTipoRefeicao, valor) "+
                "VALUES (?,?,?,?)";
        cmdInsert = this.db.compileStatement(INSERT);

        cmdInsert.bindString(1, medida.getDataHora());
        cmdInsert.bindLong(2, medida.getTipoMedida().getCodigo());
        cmdInsert.bindLong(3, medida.getTipoRefeicao().getCodigo());
        cmdInsert.bindLong(4, medida.getValor());

// Método útil - podemos logar informações que
// aparecem no LogCat durante execução!
        Log.v("SQL", "strftime('%s','"+medida.getTipoMedida().getDescricao()+"')");
        return cmdInsert.executeInsert();
    }

    public void deleteAll()
    {
        this.db.delete(TABLE_NAME, null, null);
    }


    // Método para inserção
    public long delete(Medida medida)
    {
        long retorno = 0;

        try {
            SQLiteStatement cmdDelete;
            String DELETE = "DELETE FROM "
                    + TABLE_NAME + " WHERE codigo = ?";
            cmdDelete = this.db.compileStatement(DELETE);

            cmdDelete.bindLong(1, medida.getCodigo());
            cmdDelete.executeUpdateDelete();
            retorno = 1;
        }
        catch (Exception e){
            retorno = 2;
        }
        return retorno;
    }

    // Método para inserção
    public int update(Medida medida)
    {
        int retorno = 0;

        try {
            SQLiteStatement cmdUpdate;
            String INSERT = "UPDATE "
                    + TABLE_NAME + " SET dataHora = ?,  codTipoMedida = ?,  codTipoRefeicao = ?, valor = ? WHERE codigo = ? ";

            cmdUpdate = this.db.compileStatement(INSERT);

            cmdUpdate.bindString(1, medida.getDataHora());
            cmdUpdate.bindLong(2, medida.getTipoMedida().getCodigo());
            cmdUpdate.bindLong(3, medida.getTipoRefeicao().getCodigo());
            cmdUpdate.bindLong(4, medida.getValor());
            cmdUpdate.bindLong(5, medida.getCodigo());

            cmdUpdate.executeUpdateDelete();
            retorno = 1;
        } catch (Exception e){
            retorno = 2;
        }
        return  retorno;
    }


    public ArrayList<Medida> selectAll(TipoMedida filtraTipo)
    {
        ArrayList<Medida> list = new ArrayList<Medida>();
        String selection=null;
        String[] selectionArgs=null;
        if (filtraTipo!=null){
            selection = "codTipoMedida=?";
            selectionArgs = new String[]{ Long.toString(filtraTipo.getCodigo()) };
        }

        Cursor cursor = this.db.query(TABLE_NAME, new String[] { "codigo",
                        "dataHora", "codTipoMedida", "codTipoRefeicao", "valor" },
                selection, selectionArgs, null, null, "codigo");

        if (cursor.moveToFirst())
            do {
                Medida tipodr = new Medida(
                        cursor.getInt(0),
                        cursor.getString(1),
                        tipoMedidaDAO.findById(cursor.getInt(2)),
                        tipoRefeicaoDAO.findById(cursor.getInt(3)),
                        cursor.getInt(4)
                );
                list.add(tipodr);
            }
            while (cursor.moveToNext());
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
