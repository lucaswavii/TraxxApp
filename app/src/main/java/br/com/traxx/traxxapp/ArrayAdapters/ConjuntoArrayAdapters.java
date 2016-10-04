package br.com.traxx.traxxapp.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.camadas.comum.Conjunto;

/**
 * Created by lucas.ricarte on 25/08/2016.
 */
public class ConjuntoArrayAdapters extends ArrayAdapter<Conjunto> {

    public ConjuntoArrayAdapters(Context context, ArrayList<Conjunto> conjuntos) {
        super(context, 0, conjuntos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Conjunto conjuntos = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view


        //TextView tvNome = (TextView) convertView.findViewById(R.id.tvListaConjuntoNome);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listagem_catalogo_conjunto, parent, false);
        }
        // Lookup view for data population
        final TextView tvListaConjuntoCodigo = (TextView) convertView.findViewById(R.id.tvListaConjuntoCodigo);
        final TextView tvListaConjuntoNome = (TextView) convertView.findViewById(R.id.tvListaConjuntoNome);

        tvListaConjuntoCodigo.setText(conjuntos.getCodigo());
        tvListaConjuntoNome.setText(conjuntos.getNome());
        // Return the completed view to render on screen
        return convertView;
    }
}
