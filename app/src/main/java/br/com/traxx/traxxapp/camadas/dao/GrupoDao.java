package br.com.traxx.traxxapp.camadas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.com.traxx.traxxapp.Util.DataBase;
import br.com.traxx.traxxapp.camadas.comum.Grupo;

/**
 * Created by lucas.ricarte on 23/08/2016.
 */
public class GrupoDao extends DataBase {

    private final String TABLE = "GRUPOS";

    public GrupoDao(Context context) {
        super(context);
    }

    /**
     * @param grupo
     * @return
     */
    //Integer id, Integer recurso, Integer moto, Integer tipo,  String nome

    public void insert(Grupo grupo) throws Exception {
        ContentValues values = new ContentValues();
        values.put("recurso", grupo.getRecurso());
        values.put("moto", grupo.getMoto());
        values.put("tipo", grupo.getTipo());
        values.put("nome", grupo.getNome());
        getDatabase().insert(TABLE, null, values);
    }
    /**
     * @param grupo
     * @return
     */

    public void update(Grupo grupo) throws Exception {
        ContentValues values = new ContentValues();
        values.put("recurso", grupo.getRecurso());
        values.put("moto", grupo.getMoto());
        values.put("tipo", grupo.getTipo());
        values.put("nome", grupo.getNome());

        getDatabase().update(TABLE, values, "id = ?", new String[] { "" + grupo.getId() });
    }

    /**
     * @return cursor
     */

    public ArrayList<Grupo> findAll() throws Exception {
        ArrayList<Grupo> retorno = new ArrayList();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaGrupo(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }

    /**
     * @param id
     * @return Moto
     */
    public Grupo findById(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE ID = ?";
        String[] selectionArgs = new String[] { "" + id };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaGrupo(cursor);
    }
    /**
     * @param recurso
     * @return Moto
     */
    public Grupo pegaGrupoPorChaveRecurso(Integer recurso) {
        String sql = "SELECT * FROM " + TABLE + " WHERE RECURSO = ?" ;
        String[] selectionArgs = new String[]{ "" + recurso };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaGrupo(cursor);
    }

   // Integer id, Integer recurso, Integer moto, Integer tipo,  String nome
    /**
     * @param moto
     * *@param tipo
    * @return Grupo
    */
    public Grupo pegaGrupoPorMotoTipo(Integer moto, Integer tipo) {
        String sql = "SELECT * FROM " + TABLE + " WHERE MOTO = ? and TIPO = ? ";
        String[] selectionArgs = new String[] {"" + moto, "" +   tipo}; // Chassi
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaGrupo(cursor);
    }

       /**
     * @param cursor
     * @return Grupo
     */
    public Grupo montaGrupo(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0)));
        Integer recurso = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(1)));
        Integer moto = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(2)));
        Integer tipo = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(3)));
        String nome = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4)));

        return new Grupo(id, recurso, moto, tipo, nome);
    }
}
