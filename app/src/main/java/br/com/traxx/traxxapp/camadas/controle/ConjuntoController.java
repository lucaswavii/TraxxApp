package br.com.traxx.traxxapp.camadas.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.traxx.traxxapp.camadas.comum.Conjunto;
import br.com.traxx.traxxapp.camadas.dao.ConjuntoDao;

/**
 * Created by lucas.ricarte on 24/08/2016.
 */
public class ConjuntoController {
    private static ConjuntoDao conjuntoDao;
    private static ConjuntoController instance;

    public static ConjuntoController getInstance(Context context) throws Exception {
        if (instance == null) {
            instance = new ConjuntoController();
            conjuntoDao = new ConjuntoDao(context);
        }
        return instance;
    }

    public void insert(Conjunto conjunto) throws Exception {
        conjuntoDao.insert(conjunto);
    }
    public void update(Conjunto conjunto) throws Exception {
        conjuntoDao.update(conjunto);
    }
    public ArrayList<Conjunto> findAll() throws Exception {
        return conjuntoDao.findAll();
    }

    public Conjunto pegaConuntoPorChaveRecurso(Integer recurso) throws Exception {
        Conjunto conjunto = conjuntoDao.pegaConuntoPorChaveRecurso(recurso);
        return conjunto;
    }

    public ArrayList<Conjunto> pegaConjuntoPorGrupo(Integer grupo) throws Exception {
        ArrayList<Conjunto> Conjuntos = conjuntoDao.pegaConjuntoPorGrupo(grupo);
        return Conjuntos;
    }

    public Conjunto findById(Integer id) throws Exception {
        Conjunto conjunto = conjuntoDao.findById(id);
        return conjunto;
    }
}
