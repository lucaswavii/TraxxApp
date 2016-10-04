package br.com.traxx.traxxapp.Activitys;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.parser.JSONArrayParser;
import com.koushikdutta.async.parser.StringParser;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import br.com.traxx.traxxapp.ArrayAdapters.RevendaArrayAdapters;
import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.Util.ConexaoNet;

import br.com.traxx.traxxapp.Util.TrataStringAcentos;
import br.com.traxx.traxxapp.camadas.comum.Item;
import br.com.traxx.traxxapp.camadas.comum.Pedido;
import br.com.traxx.traxxapp.camadas.comum.Revenda;
import br.com.traxx.traxxapp.camadas.controle.ItemController;
import br.com.traxx.traxxapp.camadas.controle.PedidoController;

public class FechamentoActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "Preferences";
    private SharedPreferences settings;
    private ArrayList<Revenda> revendas;
    private Pedido pedido;
    private ArrayList<Item> itens;
    private ItemController itemController;
    private PedidoController pedidoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechamento);

        settings = getSharedPreferences(PREFS_NAME, 0);
        Integer uf = Integer.valueOf(settings.getString("uf", ""));
        Integer cidade = Integer.valueOf(settings.getString("cidade", ""));

        try {
            Intent intent = getIntent();
            Bundle dados = intent.getExtras();
            if( dados.size() > 0 ){
                pedido  = (Pedido) dados.getSerializable("pedido");
                itemController = ItemController.getInstance(getBaseContext());
                pedidoController = PedidoController.getInstance(getBaseContext());
                itens = itemController.findAll(Long.valueOf(pedido.getId().toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        revendas = new ArrayList<Revenda>();

        final TextView tvNomeDaRevendaPeca = (TextView) findViewById(R.id.tvNomeDaRevendaPeca);
        final TextView tvEnderecoDaRevendaPeca = (TextView) findViewById(R.id.tvEnderecoDaRevendaPeca);
        final TextView tvCidadeUfRevendaPeca = (TextView) findViewById(R.id.tvCidadeUfRevendaPeca);
        final TextView tvFoneRevendaPeca = (TextView) findViewById(R.id.tvFoneRevendaPeca);
        final TextView tvEmailRevendaPeca = (TextView) findViewById(R.id.tvEmailRevendaPeca);

        Ion.with(getApplicationContext())
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693") ///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "buscarRevendaMaisProxima")
                .addQuery("referrer", "app traxx")
                .addQuery("uf", String.valueOf(uf))
                .addQuery("cidade", String.valueOf(cidade))
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                if (e != null) {
                    Toast.makeText(FechamentoActivity.this, "Ocorreu um erro ao tentar gravar o registro:" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    return;
                }


                if ( result != null) {
                    for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                        JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();

                        TrataStringAcentos trataStringAcentos = new TrataStringAcentos();

                        Integer chave = object.get("chave").getAsInt();
                        String codigo = object.get("codigo").getAsString();
                        trataStringAcentos.setTexto(codigo);
                        codigo = trataStringAcentos.LimpaTexto();



                        String nome = object.get("nome").getAsString();
                        trataStringAcentos.setTexto(nome);
                        nome = trataStringAcentos.LimpaTexto();

                        String endereco = object.get("endereco").getAsString();
                        trataStringAcentos.setTexto(endereco);
                        endereco = trataStringAcentos.LimpaTexto();

                        String uf = object.get("uf").getAsString();
                        trataStringAcentos.setTexto(uf);
                        uf = trataStringAcentos.LimpaTexto();


                        String cidade = object.get("cidade").getAsString();
                        trataStringAcentos.setTexto(cidade);
                        cidade = trataStringAcentos.LimpaTexto();

                        String fone = object.get("fone").getAsString();
                        String email = object.get("email").getAsString();
                        Double latitude = object.get("latitude").getAsDouble();
                        Double longitude= object.get("longitude").getAsDouble();

                        Revenda revenda = new Revenda(chave,codigo, nome, endereco, uf, cidade, fone, email, latitude, longitude);
                        revendas.add(revenda);
                    }
                    if(revendas.size() > 0 ) {
                        tvNomeDaRevendaPeca.setText(revendas.get(0).getNome().toString());
                        tvEnderecoDaRevendaPeca.setText(revendas.get(0).getEndereco().toString());
                        tvCidadeUfRevendaPeca.setText(revendas.get(0).getUf().toString().concat("/").concat(revendas.get(0).getCidade().toString()));
                        tvFoneRevendaPeca.setText(revendas.get(0).getFone().toString());
                        tvEmailRevendaPeca.setText(revendas.get(0).getEmail().toString());
                    } else {
                        tvNomeDaRevendaPeca.setText("");
                        tvEnderecoDaRevendaPeca.setText("");
                        tvCidadeUfRevendaPeca.setText("");
                        tvFoneRevendaPeca.setText("");
                        tvEmailRevendaPeca.setText("");
                    }

                    final RevendaArrayAdapters arrayAdapterRevendas = new RevendaArrayAdapters(getBaseContext(), revendas);
                    ListView listview = (ListView) findViewById(R.id.listViewRevendas);
                    listview.setAdapter(arrayAdapterRevendas);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            final Revenda revenda = arrayAdapterRevendas.getItem(position);
                            pedido.setRevenda(revenda.getChave());

                            try {
                                pedidoController.update(pedido);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                            tvNomeDaRevendaPeca.setText(revenda.getNome().toString());
                            tvEnderecoDaRevendaPeca.setText(revenda.getEndereco().toString());
                            tvCidadeUfRevendaPeca.setText(revenda.getUf().concat("/").concat(revenda.getCidade().toString()));
                            tvFoneRevendaPeca.setText(revenda.getFone().toString());
                            tvEmailRevendaPeca.setText(revenda.getEmail().toString());
                        }
                    });
                } else {
                    Toast.makeText(getBaseContext(), "Ocorreu erros ao tentar gravar usuario. Servidor Indisponível.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void SendPedido(){

        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

        xml += "<infNFe Id='1' versao='3.10'>";

        for(int i = 0; i < itens.size(); i++) {
            xml += "<det nItem='" + String.valueOf(i) + "'>";
            xml += "<prod>";
            xml += "<cProd>" + itens.get(i).getRecurso()+"</cProd>";
            xml += "<cQde>" + itens.get(i).getQuantidade()+"</cQde>";
            xml += "</prod>";
            xml += "</det>";
        }

        xml += "</infNFe>";


        Ion.with(getApplicationContext())
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693") ///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "criaPedidoMobile")
                .addQuery("referrer", "app traxx")
                .addQuery("id", pedido.getId().toString())
                .addQuery("emissao", pedido.getEmissao().toString())
                .addQuery("cliente", pedido.getCliente().toString())
                .addQuery("revenda", pedido.getRevenda().toString())
                .addQuery( "data",xml )
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                if (e != null) {
                    Toast.makeText(FechamentoActivity.this, "Ocorreu um erro ao tentar gravar o registro:" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                if ( result != null) {
                }

            }
        });

    }

    public void confirmExit(final Activity activity) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder( activity );
        alertbox.setMessage( "Deseja Finalizar Pedido?\nAtenção:\nEntraremos em contato em breve para finalização total da venda.O contato poderá ser por email, celular ou por aplicativos de mensagem." );

        alertbox.setPositiveButton( "Sim", new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface arg0, int arg1 ) {
                SendPedido();
                activity.finish();
                Intent i = new Intent(FechamentoActivity.this, MainActivity.class);
                startActivity(i);
                Toast.makeText(activity,"Pedido enviado com sucesso.", Toast.LENGTH_LONG).show();

            }
        } );

        alertbox.setNegativeButton( "Não", new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface arg0, int arg1 ) {

            }
        } );

        alertbox.show();
    }

    public void EnviaPedidos(View view){
        confirmExit(this);
    }
}
