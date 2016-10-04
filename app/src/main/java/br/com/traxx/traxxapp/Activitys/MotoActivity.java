package br.com.traxx.traxxapp.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Array;
import java.util.ArrayList;

import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.Util.ConexaoNet;

public class MotoActivity extends AppCompatActivity {

    private ImageView proximo;
    private TextView texto;
    private int cont;
    private Integer recurso;
    private ArrayList<MotoModelos> motoModelos;
    private ProgressDialog mprogressDialog;
    private SharedPreferences settings;
    public static final String PREFS_NAME = "Preferences";
    private Integer cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moto);
        getSupportActionBar().hide();

        settings = getSharedPreferences(PREFS_NAME, 0);

        cliente = Integer.valueOf(settings.getString("cliente", ""));

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();


        motoModelos = new ArrayList<MotoModelos>();

        if( dados.size() > 0 ){

            recurso = dados.getInt("recurso");
        }

        cont = 0;
        texto = (TextView) findViewById(R.id.tvMensagemDetalheMoto);
        proximo = (ImageView) findViewById(R.id.imgPainelFotos);

        mprogressDialog = ProgressDialog.show(this, "Aguarde", "Carregando Imagens...");

        Ion.with(getBaseContext())
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693") ///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "pegaDetalhesModelo")
                .addQuery("referrer", "app traxx")
                .addQuery("recurso", String.valueOf(recurso))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (e != null) {
                            Toast.makeText(MotoActivity.this, "Erro ao carregar modelo de motos.", Toast.LENGTH_LONG).show();
                            mprogressDialog.dismiss();
                            return;
                        }


                        if ( result != null ) {

                            for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                                JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
                                String descricao = String.valueOf(object.get("descricao").getAsString());
                                Integer imagem = Integer.valueOf(object.get("imagem").getAsString());
                                MotoModelos motoDetalhe = new MotoModelos(descricao, imagem );
                                motoModelos.add(motoDetalhe);
                            }

                            if (cont == 0) {
                                if ( motoModelos.size() > 0 ) {

                                    Integer imagem = motoModelos.get(cont).getImagem();
                                    String descricao = motoModelos.get(cont).getDescricao();

                                    Ion.with(proximo)
                                            .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/" + imagem);
                                    texto.setText(descricao);
                                }
                            }
                            mprogressDialog.dismiss();
                        }
                    }
                });

    }

    public void proximo(View v) {
        cont++;

        if (cont < 0) {
            cont = 0;
        }

        if (cont > motoModelos.size()-1) {
            cont = 0;
        }
        if ( motoModelos.size() > 0 ) {
            Integer imagem = motoModelos.get(cont).getImagem();
            String descricao = motoModelos.get(cont).getDescricao();
            Ion.with(proximo)
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/" + imagem);
            texto.setText(descricao);
        }
    }

    public void anterior(View v) {
        cont--;

        if (cont < 0) {
            cont = motoModelos.size()-1;
        }

        if (cont > motoModelos.size()-1) {
            cont = 0;
        }
        if ( motoModelos.size() > 0 ) {
            Integer imagem = motoModelos.get(cont).getImagem();
            String descricao = motoModelos.get(cont).getDescricao();
            Ion.with(proximo)
                    .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/" + imagem);
            texto.setText(descricao);
        }

    }

    public class MotoModelos {
        private String descricao;
        private Integer imagem;

        public MotoModelos(String descricao, Integer imagem) {
            this.descricao = descricao;
            this.imagem = imagem;
        }

        public String getDescricao() {
            return descricao;
        }
        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public Integer getImagem() {
            return imagem;
        }
        public void setImagem(Integer imagem) {
            this.imagem = imagem;
        }
    }


    public void SolicitacaoOrcamento(View v) {
        mprogressDialog = ProgressDialog.show(this, "Aguarde", "Enviando Solicitação de Orçamento...");
        Ion.with(getBaseContext())
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693") ///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "solicitarOrcamento")
                .addQuery("referrer", "app traxx")
                .addQuery("recurso", String.valueOf(recurso))
                .addQuery("cliente", String.valueOf(cliente))


                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (e != null) {
                            Toast.makeText(MotoActivity.this, "Erro ao carregar modelo de motos.", Toast.LENGTH_LONG).show();
                            mprogressDialog.dismiss();
                            return;
                        }

                        mprogressDialog.dismiss();

                        if ( result != null ) {
                            Toast.makeText(getBaseContext(), "Solicitação cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent i = new Intent(MotoActivity.this, MainActivity.class);
                            startActivity(i);
                        }

                    }
                });
    }

}
