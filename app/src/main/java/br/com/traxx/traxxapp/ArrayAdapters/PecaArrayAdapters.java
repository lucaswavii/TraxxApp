package br.com.traxx.traxxapp.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.camadas.comum.Peca;
import java.util.ArrayList;
/**
 * Created by lucas.ricarte on 29/08/2016.
 */
public class PecaArrayAdapters extends ArrayAdapter<Peca> {

    public PecaArrayAdapters(Context context, ArrayList<Peca> pecas) {
        super(context, 0, pecas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Peca pecas = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listagem_catalogo_peca, parent, false);
        }

        TextView tvPecaCodigo = (TextView) convertView.findViewById(R.id.tvPecaCodigo);
        TextView tvPecaNome = (TextView) convertView.findViewById(R.id.tvPecaNome);
        tvPecaCodigo.setText(pecas.getCodigo());
        tvPecaNome.setText(pecas.getNome());
        ImageView imgCheck = (ImageView) convertView.findViewById(R.id.imgCheck);


        if ( pecas.getAdicionado() == true) {
            imgCheck.setImageResource(R.drawable.checked);
        }
        // Return the completed view to render on screen
        return convertView;

    }

    public void clickImagem(View view) {

    }
}
