package br.com.traxx.traxxapp.camadas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.com.traxx.traxxapp.Util.DataBase;
import br.com.traxx.traxxapp.camadas.comum.Item;

/**
 * Created by lucas.ricarte on 02/09/2016.
 */
public class ItemDao  extends DataBase {
    private final String TABLE = "ITENS";

    public ItemDao(Context context) {
        super(context);
    }

    /**
     * @param item
     * @return
     */

    public void insert(Item item) throws Exception {
        ContentValues values = new ContentValues();
        values.put("pedido", item.getPedido());
        values.put("recurso", item.getRecurso());
        values.put("quantidade", item.getQuantidade());


        getDatabase().insert(TABLE, null, values);
    }
    /**
     * @param item
     * @return
     */

    public void update(Item item) throws Exception {
        ContentValues values = new ContentValues();
        values.put("pedido", item.getPedido());
        values.put("recurso", item.getRecurso());
        values.put("quantidade", item.getQuantidade());

        getDatabase().update(TABLE, values, "id = ?", new String[] { "" + item.getId() });
    }

    /**
     * @return cursor
     */

    public ArrayList<Item> findAll(Long pedido) throws Exception {
        ArrayList<Item> retorno = new ArrayList();
        String sql = "SELECT * FROM " + TABLE + " where PEDIDO = ?";
        String[] selectionArgs = new String[] { "" + pedido };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaItem(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }


    /**
     * @param cursor
     * @return Grupo
     */

    public Item montaItem(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0)));
        Integer pedido = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(1)));
        Integer recurso = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(2)));
        Integer quantidade = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(3)));


        return new Item(id, recurso, quantidade, pedido);
    }
}
