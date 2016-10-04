package br.com.traxx.traxxapp.camadas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.com.traxx.traxxapp.Util.DataBase;
import br.com.traxx.traxxapp.camadas.comum.Peca;

/**
 * Created by lucas.ricarte on 26/08/2016.
 */
public class PecaDao extends DataBase {
    private final String TABLE = "PECAS";

    public PecaDao(Context context) {
        super(context);
    }

    /**
     * @param peca
     * @return
     */

    public void insert(Peca peca) throws Exception {
        ContentValues values = new ContentValues();
        values.put("recurso", peca.getRecurso());
        values.put("conjunto", peca.getConjunto());
        values.put("codigo", peca.getCodigo());
        values.put("nome", peca.getNome());
        values.put("imagem", peca.getImagem());


        getDatabase().insert(TABLE, null, values);
    }
    /**
     * @param peca
     * @return
     */

    public void update(Peca peca) throws Exception {
        ContentValues values = new ContentValues();
        values.put("recurso", peca.getRecurso());
        values.put("conjunto", peca.getConjunto());
        values.put("codigo", peca.getCodigo());
        values.put("nome", peca.getNome());
        values.put("imagem", peca.getImagem());

        getDatabase().update(TABLE, values, "id = ?", new String[] { "" + peca.getId() });
    }

    /**
     * @return cursor
     */

    public ArrayList<Peca> findAll() throws Exception {
        ArrayList<Peca> retorno = new ArrayList();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaPeca(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }


    /**
     * @param id
     * @return Peca
     */
    public Peca findById(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE ID = ?";
        String[] selectionArgs = new String[] { "" + id };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaPeca(cursor);
    }




    /**
     * @param recurso
     * @return Conjunto
     */
    public Peca pegaPecaPorChaveRecurso(Integer recurso) {
        String sql = "SELECT * FROM " + TABLE + " WHERE RECURSO = ? " ;
        String[] selectionArgs = new String[] {"" + recurso};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaPeca(cursor);
    }



    public ArrayList<Peca> pegaPecaPorConjunto( Integer conjunto ) throws Exception {
        ArrayList<Peca> retorno = new ArrayList();
        String sql = "SELECT * FROM " + TABLE + " WHERE CONJUNTO = ? ";
        String[] selectionArgs = new String[] {"" + conjunto };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaPeca(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }
    /**
     * @param cursor
     * @return Peca
     */
    public Peca montaPeca(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0)));
        Integer recurso = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(1)));
        Integer conjunto = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(2)));
        String codigo = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3)));
        String nome = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4)));
        Integer imagem = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(5)));


        return new Peca(id, recurso, conjunto, codigo, nome, imagem, false);
    }
}
