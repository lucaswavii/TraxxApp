package br.com.traxx.traxxapp.camadas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.sql.Date;
import java.util.ArrayList;

import br.com.traxx.traxxapp.Util.DataBase;
import br.com.traxx.traxxapp.camadas.comum.Pedido;

/**
 * Created by lucas.ricarte on 31/08/2016.
 */
public class PedidoDao extends DataBase {
    private final String TABLE = "PEDIDOS";

    public PedidoDao(Context context) {
        super(context);
    }

    /**
     * @param pedido
     * @return
     */

    public Long insert(Pedido pedido) throws Exception {
        ContentValues values = new ContentValues();
        values.put("cliente", pedido.getCliente());
        values.put("revenda", pedido.getRevenda());
        values.put("emissao", pedido.getEmissao());
        values.put("envio", pedido.getBaixa());

        return getDatabase().insert(TABLE, null, values);
    }
    /**
     * @param pedido
     * @return
     */

    public void update(Pedido pedido) throws Exception {
        ContentValues values = new ContentValues();
        values.put("cliente", pedido.getCliente());
        values.put("revenda", pedido.getRevenda());
        values.put("emissao", pedido.getEmissao());
        values.put("envio", pedido.getBaixa());
        getDatabase().update(TABLE, values, "id = ?", new String[] { "" + pedido.getId() });
    }


    /**
     * @param id
     * @return Peca
     */
    public Pedido findById(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE ID = ?";
        String[] selectionArgs = new String[] { "" + id };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaPedido(cursor);
    }


    /**
     * @return cursor
     */

    public ArrayList<Pedido> findAll() throws Exception {
        ArrayList<Pedido> retorno = new ArrayList();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaPedido(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }

    /**
     * @param
     * @return Pedido
     */
    public Pedido pegaPedidoEmAberto( Integer cliente) {
        String sql = "SELECT * FROM " + TABLE + " WHERE CLIENTE = ? AND ENVIO IS NULL ";
        String[] selectionArgs = new String[] { "" + cliente };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaPedido(cursor);
    }


    /**
     * @param
     * @return Pedido
     */
    public void apagaPedidoEmAberto() {
        getDatabase().delete(TABLE, "WHERE EMISSAO IS NOT NULL AND ENVIO IS NULL ", null);
    }

    /**
     * @param cursor
     * @return Pedido
     */
    public Pedido montaPedido(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0)));
        Integer cliente = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(1)));
        Integer revenda = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(2)));
        String emissao = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3)));
        String envio = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4)));
        return new Pedido(id, cliente, revenda, emissao, envio, null);
    }

}
