package br.com.traxx.traxxapp.camadas.controle;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import br.com.traxx.traxxapp.camadas.comum.Pedido;
import br.com.traxx.traxxapp.camadas.dao.PedidoDao;

/**
 * Created by lucas.ricarte on 31/08/2016.
 */
public class PedidoController {
    private static PedidoDao pedidoDao;
    private static PedidoController instance;

    public static PedidoController getInstance(Context context) throws Exception {
        if (instance == null) {
            instance = new PedidoController();
            pedidoDao = new PedidoDao(context);
        }
        return instance;
    }

    public Long insert(Pedido pedido) throws Exception {
        return pedidoDao.insert(pedido);
    }
    public void update(Pedido pedido) throws Exception {
        pedidoDao.update(pedido);
    }
    public ArrayList<Pedido> findAll() throws Exception {
        return pedidoDao.findAll();
    }
    public Pedido pegaPedidoEmAberto( Integer cliente ) throws Exception {
        Pedido pedido = pedidoDao.pegaPedidoEmAberto(cliente);
        return pedido;
    }

    public void apagaPedidoEmAberto() throws Exception {
        pedidoDao.apagaPedidoEmAberto();
    }

    public Pedido findById(Integer id) throws Exception {
        Pedido pedido = pedidoDao.findById(id);
        return pedido;
    }
}
