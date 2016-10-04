package br.com.traxx.traxxapp.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lucas.ricarte on 12/08/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    private final static int VERSAO = 2;
    private final static String NOME = "traxx.sqlite";
    protected SQLiteDatabase database;

    /* Tabelas */
    private static final String CREATE_MOTOS   = "CREATE TABLE MOTOS (ID INTEGER PRIMARY KEY AUTOINCREMENT, RECURSO INTEGER NOT NULL, NOME VARCHAR( 150 ) NOT NULL, DESCRICAO TEXT, IMAGEM INTEGER NOT NULL );";

    private static final String CREATE_GRUPOS  = "CREATE TABLE GRUPOS (ID INTEGER PRIMARY KEY AUTOINCREMENT, RECURSO INTEGER NOT NULL, MOTO INTEGER NOT NULL, TIPO INTEGER NOT NULL, NOME VARCHAR( 150 ) NOT NULL );";

    private static final String CREATE_CONJUNTO  = "CREATE TABLE CONJUNTOS (ID INTEGER PRIMARY KEY AUTOINCREMENT, RECURSO INTEGER NOT NULL, GRUPO INTEGER NOT NULL, CODIGO VARCHAR( 150 ) NOT NULL, NOME VARCHAR( 150 ) NOT NULL, IMAGEM INTEGER NOT NULL );";

    private static final String CREATE_PECAS  = "CREATE TABLE PECAS (ID INTEGER PRIMARY KEY AUTOINCREMENT, RECURSO INTEGER NOT NULL, CONJUNTO INTEGER NOT NULL, CODIGO VARCHAR( 150 ) NOT NULL, NOME VARCHAR( 150 ) NOT NULL, IMAGEM INTEGER NOT NULL );";

    private static final String CREATE_PEDIDOS  = "CREATE TABLE PEDIDOS (ID INTEGER PRIMARY KEY AUTOINCREMENT, CLIENTE INTEGER NOT NULL, REVENDA INTEGER, EMISSAO VARCHAR(10), ENVIO VARCHAR(10) );";

    private static final String CREATE_ITENS  = "CREATE TABLE ITENS (ID INTEGER PRIMARY KEY AUTOINCREMENT, PEDIDO INTEGER NOT NULL, RECURSO INTEGER NOT NULL, QUANTIDADE INTEGER NOT NULL );";

    public DataBase(Context context) {
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOTOS);
        db.execSQL(CREATE_GRUPOS);
        db.execSQL(CREATE_CONJUNTO);
        db.execSQL(CREATE_PECAS);
        db.execSQL(CREATE_PEDIDOS);
        db.execSQL(CREATE_ITENS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MOTOS");
        db.execSQL("DROP TABLE IF EXISTS GRUPOS");
        db.execSQL("DROP TABLE IF EXISTS CONJUNTOS");
        db.execSQL("DROP TABLE IF EXISTS PECAS");
        db.execSQL("DROP TABLE IF EXISTS PEDIDOS");
        db.execSQL("DROP TABLE IF EXISTS ITENS");
        onCreate(db);
    }

    public SQLiteDatabase getDatabase() {
        if (database == null) {
            database = getWritableDatabase();
        }
        return database;
    }
}
