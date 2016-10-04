package br.com.traxx.traxxapp.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.traxx.traxxapp.ArrayAdapters.ModeloArrayAdapters;
import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.camadas.comum.Moto;
import br.com.traxx.traxxapp.camadas.controle.MotoController;

public class ModelosActivity extends AppCompatActivity {

    private ListView listview;
    private MotoController motoController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelos);
        getSupportActionBar().hide();




        ArrayList<Moto> motos;
        try {

            listview = (ListView)findViewById(R.id.listViewModelosMotos);

            motoController = MotoController.getInstance(getBaseContext());
            motos = motoController.findAll();
            final ModeloArrayAdapters modeloArrayAdapters = new ModeloArrayAdapters(this, motos);
            listview.setAdapter(null);
            listview.setAdapter(modeloArrayAdapters);
            listview.deferNotifyDataSetChanged();
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                    Moto moto = modeloArrayAdapters.getItem(position);

                    Bundle pacote = new Bundle();
                    pacote.putInt("recurso", moto.getRecurso());


                    Intent intent = new Intent(ModelosActivity.this, MotoActivity.class);
                    intent.putExtras(pacote);
                    startActivity(intent);

                }
            });
            listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
