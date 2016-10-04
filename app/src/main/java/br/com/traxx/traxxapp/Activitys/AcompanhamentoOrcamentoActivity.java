package br.com.traxx.traxxapp.Activitys;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.traxx.traxxapp.ArrayAdapters.CotacaoArrayAdapters;
import br.com.traxx.traxxapp.ArrayAdapters.RevendaArrayAdapters;
import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.Util.ConexaoNet;
import br.com.traxx.traxxapp.Util.TrataStringAcentos;
import br.com.traxx.traxxapp.camadas.comum.Cotacao;

public class AcompanhamentoOrcamentoActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "Preferences";

    private SharedPreferences settings;
    private ArrayList<Cotacao> cotacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acompanhamento_orcamento);
        getSupportActionBar().hide();

        settings = getSharedPreferences(PREFS_NAME, 0);
        Integer cliente = Integer.valueOf(settings.getString("cliente", ""));

        cotacoes = new ArrayList<Cotacao>();

        Ion.with(getApplicationContext())
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693") ///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "pegaOrcamentoPendentesCliente")
                .addQuery("referrer", "app traxx")
                .addQuery("pessoa", String.valueOf(cliente))

                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                if (e != null) {
                    Toast.makeText(AcompanhamentoOrcamentoActivity.this, "Ocorreu um erro ao tentar buscar os registros:" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    return;
                }


                if ( result != null) {
                    for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                        JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();

                        Integer chave = object.get("chave").getAsInt();
                        String revenda = object.get("revenda").getAsString();
                        String status = object.get("status").getAsString();
                        String recurso = object.get("recurso").getAsString();
                        String quantidade = object.get("quantidade").getAsString();
                        String atendido = object.get("atendido").getAsString();
                        String valor = object.get("valor").getAsString();

                        Cotacao cotacao = new Cotacao( chave, revenda, status, recurso, quantidade, atendido, valor);
                        cotacoes.add(cotacao);
                    }

                    final CotacaoArrayAdapters cotacaoArrayAdapters = new CotacaoArrayAdapters(getBaseContext(), cotacoes);
                    ListView listview = (ListView) findViewById(R.id.listViewCotacao);

                    listview.setAdapter(cotacaoArrayAdapters);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            final Cotacao cotacao = cotacaoArrayAdapters.getItem(position);
                        }
                    });
                }

            }
        });

    }
}
