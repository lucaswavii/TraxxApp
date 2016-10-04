package br.com.traxx.traxxapp.Activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import br.com.traxx.traxxapp.camadas.comum.Cidade;
import br.com.traxx.traxxapp.camadas.comum.Grupo;
import br.com.traxx.traxxapp.camadas.comum.Moto;
import br.com.traxx.traxxapp.camadas.comum.Uf;
import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.Util.ConexaoNet;
import br.com.traxx.traxxapp.Util.Mask;
import br.com.traxx.traxxapp.camadas.controle.GrupoController;
import br.com.traxx.traxxapp.camadas.controle.MotoController;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "Preferences";

    private ConexaoNet validaNet;
    private ArrayList<Uf> estados;
    private ArrayList<Cidade> localidades;
    private ArrayList<Moto> motos;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();

        ConexaoNet conexaoNet = new ConexaoNet(this, getBaseContext());

        final EditText primeiroNome = (EditText) findViewById(R.id.etPrimeiroNome);
        primeiroNome.setBackgroundResource(R.drawable.edittext_bg);


        final EditText segundoNome = (EditText) findViewById(R.id.etSegundoNome);
        segundoNome.setBackgroundResource(R.drawable.edittext_bg);

        final EditText celular = (EditText) findViewById(R.id.etCelular);
        celular.addTextChangedListener(Mask.insert("(##)#####-####", celular));
        celular.setBackgroundResource(R.drawable.edittext_bg);


        final EditText dataCompra = (EditText) findViewById(R.id.etDataCompra);
        dataCompra.addTextChangedListener(Mask.insert("##/##/####", dataCompra));
        dataCompra.setBackgroundResource(R.drawable.edittext_bg);

        final EditText email = (EditText) findViewById(R.id.etEmail);
        email.setBackgroundResource(R.drawable.edittext_bg);

        final Spinner spCidade = (Spinner)findViewById(R.id.spCidade);
        spCidade.setBackgroundResource(R.drawable.spinner_custom);


        final Spinner spUf = (Spinner)findViewById(R.id.spUf);
        spUf.setBackgroundResource(R.drawable.spinner_custom);
        spUf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selecionado = spUf.getSelectedItemPosition();
                ProgressDialog progressDialogCidade = ProgressDialog.show(RegistroActivity.this, "Aguarde", "Carregando Cidade...");
                if (selecionado != 0) {
                    String uf = String.valueOf(estados.get(spUf.getSelectedItemPosition()).getKey());
                    Ion.with(getBaseContext())
                            .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693") ///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                            .addQuery("action", "pegaCidadePorUf")
                            .addQuery("referrer", "app traxx")
                            .addQuery("uf", uf)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    if (e != null) {
                                        Toast.makeText(RegistroActivity.this, "Erro ao carregar cidades.", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    if ( result != null ) {

                                        Cidade cidade = new Cidade();
                                        cidade.setKey(0);
                                        cidade.setNome("Selecione Cidade");
                                        localidades = new ArrayList<Cidade>();
                                        localidades.add(cidade);

                                        for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                                            JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
                                            cidade = new Cidade();
                                            cidade.setKey(Integer.valueOf(object.get("key").getAsString()));
                                            cidade.setNome(object.get("name").getAsString());
                                            localidades.add(cidade);
                                        }
                                        ArrayAdapter cidadeAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, localidades);
                                        spCidade.setAdapter(cidadeAdapter);


                                    }

                                }
                            });

                } else {
                    Cidade cidade = new Cidade();
                    cidade.setKey(0);
                    cidade.setNome("Selecione Cidade");
                    localidades = new ArrayList<Cidade>();
                    localidades.add(cidade);
                    ArrayAdapter cidadeAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, localidades);
                    spCidade.setAdapter(cidadeAdapter);

                }
                progressDialogCidade.dismiss();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Ion.with(getBaseContext())
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693") ///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                .addQuery("action", "pegaUf")
                .addQuery("referrer", "app traxx")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ProgressDialog progressDialogUf = ProgressDialog.show(RegistroActivity.this, "Aguarde", "Carregando Uf...");
                        if (e != null) {
                            Toast.makeText(RegistroActivity.this, "Erro ao carregar uf" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if ( result != null) {
                            Uf ufs = new Uf();
                            estados = new ArrayList<Uf>();

                            for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                                JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
                                ufs = new Uf();
                                ufs.setKey(Integer.valueOf(object.get("key").getAsString()));
                                ufs.setNome(object.get("name").getAsString());
                                estados.add(ufs);
                            }
                            ArrayAdapter ufAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, estados);
                            spUf.setAdapter(ufAdapter);

                            progressDialogUf.dismiss();
                        }
                    }
                });

        final Spinner spModelo = (Spinner)findViewById(R.id.spModelo);
        spModelo.setBackgroundResource(R.drawable.spinner_custom);


        try {
            ProgressDialog progressDialogMotos = ProgressDialog.show(RegistroActivity.this, "Aguarde", "Carregando Modelos...");
            motos = new ArrayList<Moto>();
            MotoController motoController = MotoController.getInstance(getBaseContext());

            motos = motoController.findAll();

            ArrayAdapter modeloAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, motos);
            spModelo.setAdapter(modeloAdapter);
            progressDialogMotos.dismiss();

        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Preenche as Preferências */
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        String nome = settings.getString("nome", "");
        //primeiroNome.setEnabled( nome != "" ? false : true );
        primeiroNome.setText( nome );

        String sobrenome = settings.getString("sobrenome", "");
        //segundoNome.setEnabled( sobrenome != "" ? false : true );
        segundoNome.setText(sobrenome);


        String fone = settings.getString("fone", "");
        // celular.setEnabled( fone != "" ? false : true );
        celular.setText(fone);

        String compra = settings.getString("dataCompra", "");
        //dataCompra.setEnabled( compra != "" ? false : true );
        dataCompra.setText(compra);

        String seuEmail = settings.getString("email", "");
        //email.setEnabled( seuEmail != "" ? false : true );
        email.setText(seuEmail);
    }


    public void btnGravarClick(View view){

        final EditText etNome = (EditText)findViewById(R.id.etPrimeiroNome);
        final EditText etSobreNome = (EditText)findViewById(R.id.etSegundoNome);
        final EditText etCelular = (EditText)findViewById(R.id.etCelular);
        final EditText etDataCompra = (EditText)findViewById(R.id.etDataCompra);
        final EditText etEmail = (EditText)findViewById(R.id.etEmail);
        final Spinner spUf = (Spinner)findViewById(R.id.spUf);
        final Spinner spCidade = (Spinner)findViewById(R.id.spCidade);
        final Spinner spModelo = (Spinner)findViewById(R.id.spModelo);

        // spUf.getSelectedItemPosition()
        boolean bloqueio = false;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Validação");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                closeOptionsMenu();
            }
        });

        String mensagem = "";

        if (etNome.getText().length() == 0 && etNome.getText().length()<2){
            mensagem += "Campo Nome é Obrigatório." + "\n";
            bloqueio = true;
         //   builder.setMessage("Campo Nome é Obrigatório");
          //  alerta = builder.create();
          //  alerta.show();
        }


        if (etSobreNome.getText().length() == 0 ){
            mensagem += "O campo Segundo Nome é Obrigado." + "\n";
            bloqueio = true;
           // builder.setMessage("O campo Segundo Nome é Obrigado.");
            // alerta = builder.create();
            //alerta.show();
        }


        if (etCelular.getText().length() == 0 ){
            mensagem += "O campo Celular é Obrigado." + "\n";
            bloqueio = true;
          //  builder.setMessage("O campo Celular é Obrigado.");
            //  alerta = builder.create();
            //alerta.show();
        }

        if (etDataCompra.getText().length() == 0 ){

            mensagem += "O campo Data Compra é Obrigado." + "\n";
            bloqueio = true;
            //builder.setMessage("O campo Data Compra é Obrigado.");
            //alerta = builder.create();
            //alerta.show();
        }

        if (etEmail.getText().length() == 0 ){
            mensagem += "O campo e-mail é Obrigado." + "\n";
            bloqueio = true;
            //builder.setMessage("O campo e-mail é Obrigado.");
            //alerta = builder.create();
            //alerta.show();
        }

        if(estados.get(spUf.getSelectedItemPosition()).getKey() == 0 ) {
            mensagem += "O campo Estado é Obrigado." + "\n";
            bloqueio = true;
         //   builder.setMessage("O campo Estado é Obrigado.");
            //   alerta = builder.create();
            //alerta.show();
        }

        if(localidades.get(spCidade.getSelectedItemPosition()).getKey() == 0 ){
            mensagem += "O campo Cidade é Obrigado." + "\n";
            bloqueio = true;
           // builder.setMessage("O campo Cidade é Obrigado.");
           // alerta = builder.create();
           // alerta.show();
        }

        if(motos.get(spModelo.getSelectedItemPosition()).getRecurso() == 0 ){

            mensagem += "O campo Modelo da Moto é Obrigado." + "\n";
            bloqueio = true;
            //builder.setMessage("O campo Modelo da Moto é Obrigado.");
            //alerta = builder.create();
            //alerta.show();
        }




        ConexaoNet conexaoNet = new ConexaoNet(this, getBaseContext());
        if ( !conexaoNet.VerificaConexao() ) {
            mensagem += "Sem Conexão com Internet! Verifique Conexão." + "\n";
            bloqueio = true;
        }

        if ( !conexaoNet.VerificaServidor()){
            mensagem += "Servidor Indisponível." + "\n";
            bloqueio = true;
        }

        if ( bloqueio ) {
            builder.setMessage(mensagem);
            alerta = builder.create();
            alerta.show();
        } else {



            Ion.with(getApplicationContext())
                    .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/-1895812693") ///products/Custom/library/Traxx Mobile/CadastroMobileTraxx.ijs
                    .addQuery("action", "cadastraUsuario")
                    .addQuery("referrer", "app traxx")
                    .addQuery("nome", etNome.getText().toString())
                    .addQuery("sobrenome", etSobreNome.getText().toString())
                    .addQuery("fone", etCelular.getText().toString())
                    .addQuery("dataCompra", etDataCompra.getText().toString())
                    .addQuery("email", etEmail.getText().toString())
                    .addQuery("uf", String.valueOf(estados.get(spUf.getSelectedItemPosition()).getKey()))
                    .addQuery("cidade", String.valueOf(localidades.get(spCidade.getSelectedItemPosition()).getKey()))
                    .addQuery("modelo", String.valueOf(motos.get(spModelo.getSelectedItemPosition()).getRecurso()))
                    .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {

                    ProgressDialog progressDialog = ProgressDialog.show(RegistroActivity.this, "Aguarde", "Processando...");
                    if (e != null) {
                        Toast.makeText(RegistroActivity.this, "Ocorreu um erro ao tentar gravar o registro:" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if ( result != null) {
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("nome", etNome.getText().toString());
                        editor.putString("sobrenome", etSobreNome.getText().toString());
                        editor.putString("fone", etCelular.getText().toString());
                        editor.putString("dataCompra", etDataCompra.getText().toString());
                        editor.putString("email", etEmail.getText().toString());
                        editor.putString("uf", String.valueOf(estados.get(spUf.getSelectedItemPosition()).getKey()));
                        editor.putString("cidade", String.valueOf(localidades.get(spCidade.getSelectedItemPosition()).getKey()));
                        editor.putString("modelo", String.valueOf(motos.get(spModelo.getSelectedItemPosition()).getRecurso()));

                        for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                            JsonObject object = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
                            editor.putString("cliente", object.get("chave").getAsString());
                        }

                        editor.commit();

                        Toast.makeText(getBaseContext(), "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();

                        Intent i = new Intent(RegistroActivity.this, MainActivity.class);
                        startActivity(i);


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getBaseContext(), "Ocorreu erros ao tentar gravar usuario. Servidor Indisponível.", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }

    }
}