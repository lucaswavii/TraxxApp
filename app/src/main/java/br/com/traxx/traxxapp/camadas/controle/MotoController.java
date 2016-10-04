package br.com.traxx.traxxapp.camadas.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.traxx.traxxapp.camadas.comum.Moto;
import br.com.traxx.traxxapp.camadas.dao.MotoDao;

/**
 * Created by lucas.ricarte on 16/08/2016.
 */
public class MotoController {
    private static MotoDao motoDao;
    private static MotoController instance;

    public static MotoController getInstance(Context context) throws Exception {
        if (instance == null) {
            instance = new MotoController();
            motoDao = new MotoDao(context);
        }
        return instance;
    }
    public void insert(Moto moto) throws Exception {
        motoDao.insert(moto);
    }
    public void update(Moto moto) throws Exception {
        motoDao.update(moto);
    }
    public ArrayList<Moto> findAll() throws Exception {
        return motoDao.findAll();
    }

    public Moto pegaMotoPorChaveRecurso(Integer recurso) throws Exception {
        Moto moto = motoDao.pegaMotoPorChaveRecurso(recurso);
        return moto;
    }

    public Moto findById(Integer id) throws Exception {
        Moto moto = motoDao.findById(id);
        return moto;
    }


}
