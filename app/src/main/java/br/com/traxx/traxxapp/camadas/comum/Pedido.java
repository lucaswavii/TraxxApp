package br.com.traxx.traxxapp.camadas.comum;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
/**
 * Created by lucas.ricarte on 31/08/2016.
 */
public class Pedido implements Serializable {

    private Integer id;
    private Integer cliente;
    private Integer revenda;
    private String emissao;
    private String baixa;
    private ArrayList<Item> itens = new ArrayList();

    public Pedido(Integer id, Integer cliente, Integer revenda, String emissao, String baixa, ArrayList<Item> itens) {
        this.id = id;
        this.cliente    = cliente;
        this.revenda    = revenda;
        this.itens      = itens;
        this.emissao    = emissao;
        this.baixa      = baixa;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getRevenda() {
        return revenda;
    }

    public void setRevenda(Integer revenda) {
        this.revenda = revenda;
    }

    public String getEmissao() {
        return emissao;
    }

    public void setEmissao(String emissao) {
        this.emissao = emissao;
    }

    public String getBaixa() {
        return baixa;
    }

    public void setBaixa(String baixa) {
        this.baixa = baixa;
    }

    public ArrayList<Item> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Item> itens) {
        this.itens = itens;
    }


    public void addItem(Item item) {
        itens.add(item);
    }

    public void removeItem(Item item) {
        itens.remove(item);
    }

    public void clearItens() {
        itens.clear();
    }
}
