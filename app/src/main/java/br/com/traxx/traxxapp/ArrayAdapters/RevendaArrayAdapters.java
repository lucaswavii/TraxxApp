package br.com.traxx.traxxapp.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.camadas.comum.Revenda;

/**
 * Created by lucas.ricarte on 08/09/2016.
 */
public class RevendaArrayAdapters  extends ArrayAdapter<Revenda> {
    public RevendaArrayAdapters(Context context, ArrayList<Revenda> revendas) {
        super(context, 0, revendas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Revenda revenda = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listagem_catalogo_revenda, parent, false);
        }

        TextView tvNomeRevendaCatalogo = (TextView) convertView.findViewById(R.id.tvNomeRevendaCatalogo);
        TextView tvEmailRevendaCatalogo = (TextView) convertView.findViewById(R.id.tvEmailRevendaCatalogo);
        TextView tvFoneRevendaCatalogo = (TextView) convertView.findViewById(R.id.tvFoneRevendaCatalogo);
        tvNomeRevendaCatalogo.setText(revenda.getCodigo());
        tvEmailRevendaCatalogo.setText(revenda.getEmail());
        tvFoneRevendaCatalogo.setText(revenda.getFone());

        return convertView;

    }
}
