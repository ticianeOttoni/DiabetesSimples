package com.example.ticiane.diabetessimples.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ticiane on 29/11/2016.
 */

public class ConfiguraAplicacaoDAO {

    private static final String DATABASE_NAME = "diabetesdb.db";
    private static final int DATABASE_VERSION = 2;

    protected static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db)
        {
    // Cria as duas tabelas necessárias para armazenar as informações
            db.execSQL("CREATE TABLE TipoMedida (codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "descricao TEXT, valorIdeal INTEGER, valorOtimo INTEGER)");
            db.execSQL("CREATE TABLE TipoRefeicao (codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "descricao TEXT)");
            db.execSQL("CREATE TABLE Medida (codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "dataHora TEXT, codTipoMedida INTEGER, codTipoRefeicao INTEGER, valor INTEGER)");

            //db.execSQL("CREATE TABLE Situacao (codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    //"descricao TEXT, codTipoMedida INTEGER, valorIdeal INTEGER, valorOtimo INTEGER)");

            db.execSQL("INSERT INTO TipoMedida VALUES (1, 'Jejum',90,85)");
            db.execSQL("INSERT INTO TipoMedida VALUES (2, 'Uma hora após a refeição',140,130)");
            db.execSQL("INSERT INTO TipoMedida VALUES (3, 'Duas horas após a refeição',130,120)");

            db.execSQL("INSERT INTO TipoRefeicao VALUES (1, '-')");
            db.execSQL("INSERT INTO TipoRefeicao VALUES (2, 'Café da manhã')");
            db.execSQL("INSERT INTO TipoRefeicao VALUES (3, 'Almoço')");
            db.execSQL("INSERT INTO TipoRefeicao VALUES (4, 'Jantar')");



        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int
                newVersion) {
            db.execSQL("DROP TABLE IF EXISTS TipoMedida");
            db.execSQL("DROP TABLE IF EXISTS TipoRefeicao");
            db.execSQL("DROP TABLE IF EXISTS Medida");
            onCreate(db);
        }
    }
}
