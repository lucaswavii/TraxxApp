package br.com.traxx.traxxapp.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.traxx.traxxapp.R;
import br.com.traxx.traxxapp.camadas.comum.Cotacao;

/**
 * Created by lucas.ricarte on 22/09/2016.
 */

public class CotacaoArrayAdapters extends ArrayAdapter<Cotacao> {


    public CotacaoArrayAdapters(Context context, ArrayList<Cotacao> Cotacoes) {
        super(context, 0, Cotacoes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cotacao cotacoes = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listagem_cotacao_revenda, parent, false);
        }

        // Lookup view for data population
        CheckBox ckBoxCotacaoRevenda = (CheckBox) convertView.findViewById(R.id.ckBoxCotacaoRevenda);
        ckBoxCotacaoRevenda.setTag(cotacoes);
        ckBoxCotacaoRevenda.setText(cotacoes.getRevenda().toString());
        /*ckBoxCotacaoRevenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox chk = (CheckBox) v;
                String cotacao = (String) chk.getTag();
                if(chk.isChecked()) {

                }
            }
        });*/

        TextView tvItemcotacao = (TextView) convertView.findViewById(R.id.tvItemcotacao);
        tvItemcotacao.setText(cotacoes.getRecurso().toString());

        TextView tvQuantidadeCotacao = (TextView) convertView.findViewById(R.id.tvQuantidadeCotacao);
        tvQuantidadeCotacao.setText(cotacoes.getQuantidade().toString());

        TextView tvEstoqueCotacao = (TextView) convertView.findViewById(R.id.tvEstoqueCotacao);
        tvEstoqueCotacao.setText(cotacoes.getAtendido());



        TextView tvUnitarioCotacao = (TextView) convertView.findViewById(R.id.tvUnitarioCotacao);
        tvUnitarioCotacao.setText(cotacoes.getValor().toString());

        return convertView;
    }
}
