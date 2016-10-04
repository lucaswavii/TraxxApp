package br.com.traxx.traxxapp.camadas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.com.traxx.traxxapp.Util.DataBase;
import br.com.traxx.traxxapp.camadas.comum.Conjunto;

/**
 * Created by lucas.ricarte on 24/08/2016.
 */
public class ConjuntoDao extends DataBase {

    private final String TABLE = "CONJUNTOS";

    public ConjuntoDao(Context context) {
        super(context);
    }

    /**
     * @param conjunto
     * @return
     */

    public void insert(Conjunto conjunto) throws Exception {
        ContentValues values = new ContentValues();
        values.put("recurso", conjunto.getRecurso());
        values.put("grupo", conjunto.getGrupo());
        values.put("codigo", conjunto.getCodigo());
        values.put("nome", conjunto.getNome());
        values.put("imagem", conjunto.getImagem());


        getDatabase().insert(TABLE, null, values);
    }
    /**
     * @param conjunto
     * @return
     */

    public void update(Conjunto conjunto) throws Exception {
        ContentValues values = new ContentValues();
        values.put("recurso", conjunto.getRecurso());
        values.put("grupo", conjunto.getGrupo());
        values.put("codigo", conjunto.getCodigo());
        values.put("nome", conjunto.getNome());
        values.put("imagem", conjunto.getImagem());

        getDatabase().update(TABLE, values, "id = ?", new String[] { "" + conjunto.getId() });
    }

    /**
     * @return cursor
     */

    public ArrayList<Conjunto> findAll() throws Exception {
        ArrayList<Conjunto> retorno = new ArrayList();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaConjunto(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }


    /**
     * @param id
     * @return Moto
     */
    public Conjunto findById(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE ID = ?";
        String[] selectionArgs = new String[] { "" + id };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaConjunto(cursor);
    }




    /**
    * @param recurso
    * @return Conjunto
    */
    public Conjunto pegaConuntoPorChaveRecurso(Integer recurso) {
        String sql = "SELECT * FROM " + TABLE + " WHERE RECURSO = ? " ;
       String[] selectionArgs = new String[] {"" + recurso};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaConjunto(cursor);
    }



    public ArrayList<Conjunto> pegaConjuntoPorGrupo( Integer grupo ) throws Exception {
        ArrayList<Conjunto> retorno = new ArrayList();
        String sql = "SELECT * FROM " + TABLE + " WHERE GRUPO = ? ";
        String[] selectionArgs = new String[] {"" + grupo };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaConjunto(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }
    /**
     * @param cursor
     * @return Grupo
     */
    public Conjunto montaConjunto(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0)));
        Integer recurso = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(1)));
        Integer grupo = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(2)));
        String codigo = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3)));
        String nome = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4)));
        Integer imagem = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(5)));


        return new Conjunto(id, recurso, grupo, codigo, nome, imagem);
    }

}
