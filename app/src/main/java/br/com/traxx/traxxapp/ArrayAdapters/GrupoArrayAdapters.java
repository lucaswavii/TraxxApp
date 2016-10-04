package br.com.traxx.traxxapp.ArrayAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.camadas.comum.Grupo;

/**
 * Created by lucas.ricarte on 24/08/2016.
 */
public class GrupoArrayAdapters extends ArrayAdapter<Grupo> {


    public GrupoArrayAdapters(Context context, ArrayList<Grupo> grupos) {
        super(context, 0, grupos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Grupo grupos = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listagem_catalogo_grupo, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvconjunto);
        //TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object
        tvName.setText(grupos.getNome());
        // Return the completed view to render on screen
        return convertView;


    }
}