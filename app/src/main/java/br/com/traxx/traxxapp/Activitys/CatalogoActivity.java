package br.com.traxx.traxxapp.Activitys;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import br.com.traxx.traxxapp.ArrayAdapters.ConjuntoArrayAdapters;
import br.com.traxx.traxxapp.ArrayAdapters.GrupoArrayAdapters;
import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.Util.ConexaoNet;
import br.com.traxx.traxxapp.camadas.comum.Conjunto;
import br.com.traxx.traxxapp.camadas.comum.Grupo;
import br.com.traxx.traxxapp.camadas.comum.Item;
import br.com.traxx.traxxapp.camadas.comum.Moto;
import br.com.traxx.traxxapp.camadas.comum.Pedido;
import br.com.traxx.traxxapp.camadas.controle.ConjuntoController;
import br.com.traxx.traxxapp.camadas.controle.GrupoController;
import br.com.traxx.traxxapp.camadas.controle.ItemController;
import br.com.traxx.traxxapp.camadas.controle.MotoController;
import br.com.traxx.traxxapp.camadas.controle.PecaController;
import br.com.traxx.traxxapp.camadas.controle.PedidoController;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class CatalogoActivity extends AppCompatActivity {

    private static final int Activity_CATALOGO = 1;
    public static final String PREFS_NAME = "Preferences";
    private final static int VOICE_RESULT = 1;
    private GrupoController grupoController;
    private ConjuntoController conjuntoController;
    private SharedPreferences settings;
    private ListView listview;
    private Pedido pedido;
    private ArrayList<Item> itens;
    private PedidoController pedidoController;
    private ItemController itemController;
    private Long pedidoID;
    private ProgressDialog mprogressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
        getSupportActionBar().hide();
        try {
            final boolean sair = false;
            /* Preenche as Preferências */
            settings = getSharedPreferences(PREFS_NAME, 0);
            Integer modelo = Integer.valueOf(settings.getString("modelo", ""));
            Integer cliente = Integer.valueOf(settings.getString("cliente", ""));


            pedidoController = PedidoController.getInstance(getBaseContext());
            itemController = ItemController.getInstance(getBaseContext());

            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
            String dateString = sdf.format(date);

            if ( pedido == null ) {
                pedido = new Pedido(null, cliente, null, dateString, null, itens);
                pedidoID = pedidoController.insert(pedido);
            }

            listview = (ListView)findViewById(R.id.listView);

            MotoController motoController = MotoController.getInstance(getBaseContext());
            Moto moto = motoController.pegaMotoPorChaveRecurso(modelo);

            Ion.with((ImageView) findViewById(R.id.imgMoto))
                    .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/"+ moto.getImagem() );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnChassiClick(View view) {

        try {
            settings = getSharedPreferences(PREFS_NAME, 0);
            Integer modelo = Integer.valueOf(settings.getString("modelo", ""));
            MotoController motoController = MotoController.getInstance(getBaseContext());

            Moto moto = motoController.pegaMotoPorChaveRecurso(modelo);

            grupoController = GrupoController.getInstance(getBaseContext());
            Grupo grupo = grupoController.findByMotorMoto(moto.getRecurso(), -1895831221 );//Classe Chassi

            ConjuntoController conjuntoController = ConjuntoController.getInstance(getBaseContext());

            ArrayList<Conjunto> conjuntos = conjuntoController.pegaConjuntoPorGrupo(grupo.getRecurso());

            final ConjuntoArrayAdapters conjuntoArrayAdapters = new ConjuntoArrayAdapters(this, conjuntos);
            listview.setAdapter(null);
            listview.setAdapter(conjuntoArrayAdapters);
            listview.deferNotifyDataSetChanged();
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                    Conjunto conjuntos = conjuntoArrayAdapters.getItem(position);

                    Bundle pacote = new Bundle();
                    pacote.putInt("conjunto", conjuntos.getRecurso());
                    pacote.putInt("imagem", conjuntos.getImagem());


                    try {
                        pedido = pedidoController.findById( Integer.valueOf(pedidoID.toString()) );
                        pacote.putSerializable("pedido", pedido);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(CatalogoActivity.this, PecaActivity.class);
                    intent.putExtras(pacote);
                    startActivityForResult(intent,VOICE_RESULT );

                }
            });
            listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Conjunto conjuntos = conjuntoArrayAdapters.getItem(position);
                    alert(  conjuntos );
                    return true;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnMotorClick(View view) {
        try {
            settings = getSharedPreferences(PREFS_NAME, 0);
            Integer modelo = Integer.valueOf(settings.getString("modelo", ""));

            MotoController motoController = MotoController.getInstance(getBaseContext());

            Moto moto = motoController.pegaMotoPorChaveRecurso(modelo);

            grupoController = GrupoController.getInstance(getBaseContext());
            Grupo grupo = grupoController.findByMotorMoto( moto.getRecurso(), -1895831220 );//Classe Motor

            ConjuntoController conjuntoController = ConjuntoController.getInstance(getBaseContext());

            ArrayList<Conjunto> conjuntos = conjuntoController.pegaConjuntoPorGrupo(grupo.getRecurso());
            final ConjuntoArrayAdapters conjuntoArrayAdapters = new ConjuntoArrayAdapters(this, conjuntos);
            listview.setAdapter(null);
            listview.setAdapter(conjuntoArrayAdapters);
            listview.deferNotifyDataSetChanged();
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Conjunto conjuntos = conjuntoArrayAdapters.getItem(position);

                    Bundle pacote = new Bundle();
                    pacote.putInt("conjunto", conjuntos.getRecurso());
                    pacote.putInt("imagem", conjuntos.getImagem());
                    try {
                        pedido = pedidoController.findById( Integer.valueOf(pedidoID.toString()) );
                        pacote.putSerializable("pedido", pedido);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(CatalogoActivity.this, PecaActivity.class);
                    intent.putExtras(pacote);
                    startActivityForResult(intent,VOICE_RESULT );
                }
            });
            listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Conjunto conjuntos = conjuntoArrayAdapters.getItem(position);
                    alert(  conjuntos );
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void alert(  Conjunto conjuntos ){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(CatalogoActivity.this);
        alertadd.setTitle(conjuntos.getCodigo());
        LayoutInflater factory = LayoutInflater.from(CatalogoActivity.this);
        final View view = factory.inflate(R.layout.view_imagem_pecas, null);

        Ion.with( (ImageView) view.findViewById(R.id.imgPecas))
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/"+ conjuntos.getImagem() );

        TextView text= (TextView) view.findViewById(R.id.tvImagemConjuntoNome);
        text.setText(conjuntos.getNome());

        alertadd.setView(view);
        alertadd.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
            }
        });
        alertadd.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Integer somaValores = 0;
        try {
            itens = itemController.findAll(Long.valueOf(pedido.getId().toString()));
            for(int i = 0; i < itens.size(); i++) {
                somaValores += itens.get(i).getQuantidade();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        final TextView tvCarrinhoTotal = (TextView) findViewById(R.id.tvCarrinhoTotal);
        final TextView tvCarrinhoItens = (TextView) findViewById(R.id.tvCarrinhoItens);
        tvCarrinhoTotal.setText(String.valueOf(itens.size()));
        tvCarrinhoItens.setText(String.valueOf(somaValores));
    }

    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event ) {
        if ( ( keyCode == KeyEvent.KEYCODE_BACK ) ) {
            this.confirmExit( CatalogoActivity.this );
            Log.i( "INFO", "back button pressed" );
            return true;
        } else {
            return super.onKeyDown( keyCode, event );
        }
    }

    public static void confirmExit( final Activity activity ) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder( activity );
        alertbox.setMessage( "Deseja Sair do Catálogo?\nATENÇÃO:\nCaso saia do catálogo seu pedido será perdido." );

        alertbox.setPositiveButton( "Sim", new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface arg0, int arg1 ) {
                activity.finish();
            }
        } );

        alertbox.setNegativeButton( "Não", new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface arg0, int arg1 ) {

            }
        } );

        alertbox.show();
    }

    public void FinalizarCompraClick(View view){
        try {
            mprogressDialog = ProgressDialog.show(this, "Aguarde", "Carregando Imagens...");
            pedido = pedidoController.findById( Integer.valueOf(pedidoID.toString()) );
            itens = itemController.findAll(Long.valueOf(pedido.getId().toString()));
            if ( itens.size() > 0) {
                Bundle pacote = new Bundle();
                pacote.putSerializable("pedido", pedido);

                Intent i = new Intent(CatalogoActivity.this, FechamentoActivity.class);
                i.putExtras(pacote);
                startActivity(i);
            } else {
                Toast.makeText(this,"Não existem itens para finalização deste pedido.", Toast.LENGTH_LONG).show();
            }
            mprogressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
