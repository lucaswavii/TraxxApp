package br.com.traxx.traxxapp.camadas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.com.traxx.traxxapp.Util.DataBase;
import br.com.traxx.traxxapp.camadas.comum.Moto;

/**
 * Created by lucas.ricarte on 16/08/2016.
 */
public class MotoDao extends DataBase{

    private final String TABLE = "MOTOS";
    public MotoDao(Context context) {
        super(context);
    }

    /**
     * @param moto
     * @return
     */

    public void insert(Moto moto) throws Exception {
        ContentValues values = new ContentValues();
        values.put("recurso", moto.getRecurso());
        values.put("nome", moto.getNome());
        values.put("descricao", moto.getDescricao());
        values.put("imagem",moto.getImagem());

        getDatabase().insert(TABLE, null, values);
    }
    /**
     * @param moto
     * @return
     */

    public void update(Moto moto) throws Exception {
        ContentValues values = new ContentValues();
        values.put("recurso", moto.getRecurso());
        values.put("nome", moto.getNome());
        values.put("descricao", moto.getDescricao());
        values.put("imagem",moto.getImagem());


        getDatabase().update(TABLE, values, "ID = ?", new String[] { "" + moto.getId() });
    }

    /**
     * @return cursor
     */

    public ArrayList<Moto> findAll() throws Exception {
        ArrayList<Moto> retorno = new ArrayList();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaMoto(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }

    /**
     * @param id
     * @return Moto
     */
    public Moto findById(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE ID = ?";
        String[] selectionArgs = new String[] { "" + id };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaMoto(cursor);
    }


    /**
     * @param recurso
     * @return Moto
     */
    public Moto pegaMotoPorChaveRecurso(Integer recurso) {
        String sql = "SELECT * FROM " + TABLE + " WHERE RECURSO = ? ";
        String[] selectionArgs = new String[] {"" + recurso};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaMoto(cursor);
    }


    /**
     * @param cursor
     * @return Moto
     */
    public Moto montaMoto(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0)));
        Integer recurso = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(1)));
        String nome = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2)));
        String descricao = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3)));
        Integer imagem = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(4)));

        return new Moto(id, recurso, nome, descricao, imagem);
    }

}
