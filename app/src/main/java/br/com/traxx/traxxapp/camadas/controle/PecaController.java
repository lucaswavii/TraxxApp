package br.com.traxx.traxxapp.camadas.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.traxx.traxxapp.camadas.comum.Peca;
import br.com.traxx.traxxapp.camadas.dao.PecaDao;

/**
 * Created by lucas.ricarte on 26/08/2016.
 */
public class PecaController {
    private static PecaDao pecaDao;
    private static PecaController instance;

    public static PecaController getInstance(Context context) throws Exception {
        if (instance == null) {
            instance = new PecaController();
            pecaDao = new PecaDao(context);
        }
        return instance;
    }

    public void insert(Peca peca) throws Exception {
        pecaDao.insert(peca);
    }
    public void update(Peca peca) throws Exception {
        pecaDao.update(peca);
    }
    public ArrayList<Peca> findAll() throws Exception {
        return pecaDao.findAll();
    }

    public Peca pegaPecaPorChaveRecurso(Integer recurso) throws Exception {
        Peca peca = pecaDao.pegaPecaPorChaveRecurso(recurso);
        return peca;
    }

    public ArrayList<Peca> pegaPecaPorConjunto(Integer conjunto) throws Exception {
        ArrayList<Peca> pecas = pecaDao.pegaPecaPorConjunto(conjunto);
        return pecas;
    }

    public Peca findById(Integer id) throws Exception {
        Peca pecas = pecaDao.findById(id);
        return pecas;
    }
}
