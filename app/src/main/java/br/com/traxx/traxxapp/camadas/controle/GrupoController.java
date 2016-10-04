package br.com.traxx.traxxapp.camadas.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.traxx.traxxapp.camadas.comum.Grupo;
import br.com.traxx.traxxapp.camadas.dao.GrupoDao;

/**
 * Created by lucas.ricarte on 23/08/2016.
 */
public class GrupoController {
    private static GrupoDao grupoDao;
    private static GrupoController instance;

    public static GrupoController getInstance(Context context) throws Exception {
        if (instance == null) {
            instance = new GrupoController();
            grupoDao = new GrupoDao(context);
        }
        return instance;
    }

    public void insert(Grupo grupo) throws Exception {
        grupoDao.insert(grupo);
    }
    public void update(Grupo grupo) throws Exception {
        grupoDao.update(grupo);
    }
    public ArrayList<Grupo> findAll() throws Exception {
        return grupoDao.findAll();
    }

    public Grupo findById(Integer id) throws Exception {
        Grupo grupo = grupoDao.findById(id);
        return grupo;
    }


    public Grupo pegaGrupoPorChaveRecurso(Integer recurso) throws Exception {
        Grupo grupo = grupoDao.pegaGrupoPorChaveRecurso(recurso);
        return grupo;
    }

    public Grupo findByMotorMoto(Integer moto, Integer tipo) throws Exception {
        Grupo grupo = grupoDao.pegaGrupoPorMotoTipo(moto, tipo);
        return grupo;
    }

}
