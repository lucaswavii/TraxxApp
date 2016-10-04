package br.com.traxx.traxxapp.Util;

import java.text.Normalizer;

/**
 * Created by lucas.ricarte on 09/09/2016.
 */
public class TrataStringAcentos {
    private String texto;

    public TrataStringAcentos(){}

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String LimpaTexto(){
        CharSequence cs = new StringBuilder(texto);

        texto = Normalizer.normalize(cs, Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return texto;
    }
}
