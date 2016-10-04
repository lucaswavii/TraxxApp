package br.com.traxx.traxxapp.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.Util.ConexaoNet;
import br.com.traxx.traxxapp.camadas.comum.Moto;

/**
 * Created by lucas.ricarte on 16/09/2016.
 */
public class ModeloArrayAdapters extends ArrayAdapter<Moto> {

    public ModeloArrayAdapters(Context context, ArrayList<Moto> motos) {
        super(context, 0, motos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Moto motos = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listagem_modelos_motos, parent, false);
        }

        Ion.with((ImageView) convertView.findViewById(R.id.imgMotosModelos))
                .load("http://" + ConexaoNet.host + ":" + ConexaoNet.port + "/"+ motos.getImagem() );

        TextView tvName = (TextView) convertView.findViewById(R.id.tvModeloMoto);
        TextView tvDescricao = (TextView) convertView.findViewById(R.id.tvDescricao);

        tvName.setText(motos.getNome());
        tvDescricao.setText(motos.getDescricao());

        return convertView;
    }
}
