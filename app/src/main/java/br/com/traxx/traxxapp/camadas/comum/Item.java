package br.com.traxx.traxxapp.camadas.comum;

import java.nio.DoubleBuffer;

/**
 * Created by lucas.ricarte on 31/08/2016.
 */
public class Item {

    private Integer id;
    private Integer recurso;
    private Integer quantidade;
    private Integer pedido;

    public Item(Integer id, Integer recurso, Integer quantidade, Integer pedido) {

        this.id = id;
        this.recurso = recurso;
        this.quantidade = quantidade;
        this.pedido = pedido;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecurso() {
        return recurso;
    }

    public void setRecurso(Integer recurso) {
        this.recurso = recurso;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getPedido() {
        return pedido;
    }

    public void setPedido(Integer pedido) {
        this.pedido = pedido;
    }


}
