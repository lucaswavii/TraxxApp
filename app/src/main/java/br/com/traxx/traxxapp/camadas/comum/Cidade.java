package br.com.traxx.traxxapp.camadas.comum;

import java.io.Serializable;

/**
 * Created by lucas.ricarte on 12/08/2016.
 */
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer key;
    private String nome;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return (this.getNome());
    }
}
