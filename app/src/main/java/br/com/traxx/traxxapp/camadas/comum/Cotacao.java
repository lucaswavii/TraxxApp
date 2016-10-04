package br.com.traxx.traxxapp.camadas.comum;

import java.io.Serializable;
import java.io.StreamCorruptedException;

/**
 * Created by lucas.ricarte on 22/09/2016.
 */

public class Cotacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer chave;
    private String revenda;
    private String status;
    private String recurso;
    private String quantidade;
    private String atendido;
    private String valor;

    public Cotacao(Integer chave, String revenda, String status, String recurso, String quantidade, String atendido, String valor ) {
        this.chave = chave;
        this.revenda = revenda;
        this.status = status;
        this.recurso = recurso;
        this.quantidade = quantidade;
        this.atendido = atendido;
        this.valor = valor;

    }

    public Integer getChave() {
        return chave;
    }

    public void setChave(Integer chave) {
        this.chave = chave;
    }

    public String getRevenda() {
        return revenda;
    }

    public void setRevenda(String revenda) {
        this.revenda = revenda;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getAtendido() {
        return atendido;
    }

    public void setAtendido(String atendido) {
        this.atendido = atendido;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
