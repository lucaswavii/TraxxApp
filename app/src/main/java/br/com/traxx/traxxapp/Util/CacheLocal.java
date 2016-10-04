package br.com.traxx.traxxapp.Util;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.ArrayList;

import br.com.traxx.traxxapp.camadas.comum.Conjunto;
import br.com.traxx.traxxapp.camadas.comum.Grupo;
import br.com.traxx.traxxapp.camadas.comum.Moto;
import br.com.traxx.traxxapp.camadas.comum.Peca;
import br.com.traxx.traxxapp.camadas.controle.ConjuntoController;
import br.com.traxx.traxxapp.camadas.controle.GrupoController;
import br.com.traxx.traxxapp.camadas.controle.MotoController;
import br.com.traxx.traxxapp.camadas.controle.PecaController;

/**
 * Created by lucas.ricarte on 17/08/2016.
 */
public class CacheLocal {

    private Context context;
    private ArrayList<Moto> motos;
    public CacheLocal(Context context){
        this.context = context;
    }
    private MotoController motoController;
    private GrupoController grupoController;
    private ConjuntoController conjuntoController;
    private PecaController pecaController;

    public String normalizerToUnaccent(String s) {
        String normalized = Normalizer.normalize(s, Normalizer.Form.NFD);
        return normalized.replaceAll("[^\\p{ASCII}]", "");
    }

    public void BaixaModeloMotos() throws Exception {
        /*
         * Baixa Cache Motos
         *
         * */
        motoController = MotoController.getInstance(context);


        Ion.with(context).load("http://" + ConexaoNet.host + ":" + ConexaoNet.port +"/-1895812693")//products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "pegaModelos")
                .addQuery("referrer", "app traxx")
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                if (e != null) {
                    return;
                }

                try {
                    for(int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {

                        JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
                        Integer recurso = Integer.valueOf(object.get("chave").getAsString());
                        String nome = object.get("nome").getAsString();
                        String descricao = object.get("descricao").getAsString();
                        Integer arquivo = Integer.valueOf(object.get("imagem").getAsString());

                        Moto moto = motoController.pegaMotoPorChaveRecurso(recurso);
                        if (moto == null) {

                            Moto modelo = new Moto(null, recurso, nome, descricao, arquivo);
                            motoController.insert(modelo);

                        } else {
                            Integer id = moto.getId();
                            Moto modelo = new Moto(id, recurso, nome, descricao, arquivo);
                            motoController.update(modelo);
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void BaixaModeloGrupo() throws Exception {

        grupoController= GrupoController.getInstance(context);

        Ion.with(context).load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693")///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "pegaGrupo")
                .addQuery("referrer", "app traxx")
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                if (e != null) {
                    return;
                }

                try {

                    for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                        JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
                        Integer recurso     = Integer.valueOf(object.get("recurso").getAsString());
                        Integer moto        = Integer.valueOf(object.get("moto").getAsString());
                        Integer tipo        = Integer.valueOf(object.get("tipo").getAsString());
                        String nome         = object.get("codigo").getAsString();

                        Grupo grupo = grupoController.pegaGrupoPorChaveRecurso(recurso);
                        if (grupo == null) {
                            Grupo modelo = new Grupo(null, recurso, moto, tipo, nome);
                            grupoController.insert(modelo);
                        } else {
                            Integer id = grupo.getId();
                            Grupo modelo = new Grupo(id, recurso, moto, tipo, nome);
                            grupoController.update(modelo);
                        }
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    public void BaixaModeloConjunto() throws Exception {

        conjuntoController= ConjuntoController.getInstance(context);

        Ion.with(context).load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693")///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "pegaCatalogoChassiMotor")
                .addQuery("referrer", "app traxx")
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                if (e != null) {
                    return;
                }

                try {

                    for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                        JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
                        String codigo = object.get("codigo").getAsString();
                        String nome = normalizerToUnaccent(object.get("nome").getAsString());

                                //Normalizer.normalize(object.get("nome").getAsString().toString(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");//URLEncoder.encode(object.get("nome").getAsString(), "UTF-8");

                        Integer recurso = Integer.valueOf(object.get("recurso").getAsString());
                        Integer grupo = Integer.valueOf(object.get("grupo").getAsString());
                        Integer imagem = Integer.valueOf(object.get("imagem").getAsString());


                        Conjunto conjunto = conjuntoController.pegaConuntoPorChaveRecurso(recurso);
                        if (conjunto == null) {
                            Conjunto modelo = new Conjunto(null, recurso, grupo, codigo, nome, imagem);
                            conjuntoController.insert(modelo);
                        } else {
                            Integer id = conjunto.getId();
                            Conjunto modelo = new Conjunto(id, recurso, grupo, codigo, nome, imagem);
                            conjuntoController.update(modelo);
                        }
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }


    public void BaixaModeloPecas() throws Exception {
        pecaController = PecaController.getInstance(context);

        Ion.with(context).load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693")///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "pegaPecas")
                .addQuery("referrer", "app traxx")
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                if (e != null) {
                    return;
                }

                try {

                    for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                        JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
                        String codigo = object.get("codigo").getAsString();
                        String nome = normalizerToUnaccent(object.get("nome").getAsString());
                        Integer recurso = Integer.valueOf(object.get("recurso").getAsString());
                        Integer conjunto = Integer.valueOf(object.get("conjunto").getAsString());
                        Integer imagem = Integer.valueOf(object.get("imagem").getAsString());


                        Peca peca = pecaController.pegaPecaPorChaveRecurso(recurso);
                        if (peca == null) {
                            Peca modelo = new Peca(null, recurso, conjunto, codigo, nome, imagem, false);
                            pecaController.insert(modelo);
                        } else {
                            Integer id = peca.getId();
                            Peca modelo = new Peca(id, recurso, conjunto, codigo, nome, imagem, false);
                            pecaController.update(modelo);
                        }
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }
}
