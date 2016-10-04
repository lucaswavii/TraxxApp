package br.com.traxx.traxxapp.Activitys;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import br.com.traxx.traxxapp.ArrayAdapters.PecaArrayAdapters;
import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.Util.ConexaoNet;
import br.com.traxx.traxxapp.camadas.comum.Conjunto;
import br.com.traxx.traxxapp.camadas.comum.Item;
import br.com.traxx.traxxapp.camadas.comum.Peca;
import br.com.traxx.traxxapp.camadas.comum.Pedido;
import br.com.traxx.traxxapp.camadas.controle.ConjuntoController;
import br.com.traxx.traxxapp.camadas.controle.ItemController;
import br.com.traxx.traxxapp.camadas.controle.PecaController;

public class PecaActivity extends Activity {
    private ConjuntoController conjuntoController;
    private PecaController pecaController;
    private ItemController itemController;
    private Pedido pedido;
    private ArrayList<Item> itens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peca);

        try {
            itemController = ItemController.getInstance(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        if( dados.size() > 0 ){

            Integer pacoteConjunto = dados.getInt("conjunto");
            Integer pacoteimagem   = dados.getInt("imagem");
            pedido  = (Pedido) dados.getSerializable("pedido");
            Integer somaValores = 0;
            try {
                itens = itemController.findAll(Long.valueOf(pedido.getId().toString()));
                for(int i = 0; i < itens.size(); i++) {
                    somaValores += itens.get(i).getQuantidade();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ImageView imageView = (ImageView) findViewById(R.id.imgConjunto);
            TextView nomeConjunto = (TextView) findViewById(R.id.tvNomeConjunto);
            final TextView tvQuantidade = (TextView) findViewById(R.id.tvQuantidadeCotacao);
            final TextView tvQuantidadeItens = (TextView) findViewById(R.id.tvQuantidadeItens);

            tvQuantidade.setText(String.valueOf(itens.size()));
            tvQuantidadeItens.setText(somaValores.toString());
            try {
                conjuntoController = ConjuntoController.getInstance(getBaseContext());
                pecaController = PecaController.getInstance(getBaseContext());

                Conjunto conjunto = conjuntoController.pegaConuntoPorChaveRecurso(pacoteConjunto);
                nomeConjunto.setText(conjunto.getCodigo());



                Ion.with((ImageView) findViewById(R.id.imgConjunto))
                        .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/"+ pacoteimagem );

                ArrayList<Peca> pecas = pecaController.pegaPecaPorConjunto(conjunto.getRecurso());

                for(int i = 0; i < pecas.size(); i++) {
                    for(int j = 0; j < itens.size(); j++) {
                        if ( itens.get(j).getRecurso().equals(pecas.get(i).getRecurso())) {
                            pecas.get(i).setAdicionado(true);
                            break;
                        } else {
                            pecas.get(i).setAdicionado(false);
                        }
                    }
                }

                final PecaArrayAdapters pecaArrayAdapters = new PecaArrayAdapters(this, pecas);

                ListView listview = (ListView)findViewById(R.id.listViewPecas);
                listview.setAdapter(pecaArrayAdapters);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Peca pecas = pecaArrayAdapters.getItem(position);


                        final Dialog dialog = new Dialog(PecaActivity.this);
                        dialog.setContentView(R.layout.dialog_catalogo_pecas);
                        dialog.setTitle(pecas.getCodigo());

                        final TextView text = (TextView) dialog.findViewById(R.id.tvEscreveQuantidade);

                       // Ion.with((ImageView) dialog.findViewById(R.id.imgPecaEscolhida))
                        //        .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/"+ pecas.getImagem() );
                        dialog.show();

                        Button declineButton = (Button) dialog.findViewById(R.id.btn_IncluirItem);
                        // if decline button is clicked, close the custom dialog
                        declineButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ( text.getText().toString().length() > 0 ) {
                                    Integer quantidade = Integer.valueOf(text.getText().toString());
                                    if (quantidade > 0) {
                                        Item item = new Item(null, pecas.getRecurso(), quantidade,Integer.parseInt( pedido.getId().toString() ));

                                        try {
                                            itemController.insert(item);
                                            itens = itemController.findAll(Long.valueOf(pedido.getId().toString()));
                                            if (pecas.getRecurso().equals(item.getRecurso())) {
                                                pecas.setAdicionado(true);
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        Integer somaQuantidade = Integer.valueOf(tvQuantidadeItens.getText().toString()) + quantidade;
                                        tvQuantidadeItens.setText(String.valueOf( somaQuantidade ));
                                        tvQuantidade.setText(String.valueOf( itens.size()));
                                    }
                                } else {
                                    Toast.makeText(getBaseContext(),"Informe quantidade desejada ou click fora para cancelar este item.", Toast.LENGTH_LONG).show();
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                });
                listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Peca pecas = pecaArrayAdapters.getItem(position);
                        alert(  pecas );
                        return true;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void alert(  Peca Peca ){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(PecaActivity.this);
        alertadd.setTitle(Peca.getCodigo());
        LayoutInflater factory = LayoutInflater.from(PecaActivity.this);
        final View view = factory.inflate(R.layout.view_imagem_pecas, null);

        Ion.with( (ImageView) view.findViewById(R.id.imgPecas))
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/"+ Peca.getImagem() );

        TextView text= (TextView) view.findViewById(R.id.tvImagemConjuntoNome);
        text.setText(Peca.getNome());

        alertadd.setView(view);
        alertadd.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {

            }
        });
        alertadd.show();
    }

    public void btn_continuar_click(View view) {
        finish();
    }
}
