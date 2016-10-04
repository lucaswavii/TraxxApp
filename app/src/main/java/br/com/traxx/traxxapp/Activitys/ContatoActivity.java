package br.com.traxx.traxxapp.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.traxx.traxxapp.R;

public class ContatoActivity extends AppCompatActivity {

    private static final String[] opcoes = { "Selecione","Reclamações", "Elogíos", "Contato", "Outras Informações" };
    ArrayAdapter<String> aOpcoes;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        getSupportActionBar().hide();

        aOpcoes = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, opcoes);

        spinner = (Spinner) findViewById(R.id.spTipoContato);
        spinner.setAdapter(aOpcoes);
        spinner.setSelection(0);

    }

    public void EnvioContato(){
        if(spinner.getSelectedItem().equals("Selecione")) {
            Toast.makeText(getBaseContext(), "Informe o Tipo de Contato", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Contato Enviado Com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(ContatoActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}
