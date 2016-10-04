package br.com.traxx.traxxapp.camadas.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.traxx.traxxapp.camadas.comum.Item;
import br.com.traxx.traxxapp.camadas.dao.ItemDao;

/**
 * Created by lucas.ricarte on 02/09/2016.
 */
public class ItemController {
    private static ItemDao itemDao;
    private static ItemController instance;

    public static ItemController getInstance(Context context) throws Exception {
        if (instance == null) {
            instance = new ItemController();
            itemDao = new ItemDao(context);
        }
        return instance;
    }

    public void insert(Item item) throws Exception {
        itemDao.insert(item);
    }
    public void update(Item item) throws Exception {
        itemDao.update(item);
    }
    public ArrayList<Item> findAll( Long pedido) throws Exception {
        return itemDao.findAll( pedido );
    }
}
