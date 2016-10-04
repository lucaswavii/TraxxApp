package br.com.traxx.traxxapp.camadas.comum;

/**
 * Created by lucas.ricarte on 26/08/2016.
 */
public class Peca {
    private Integer id;
    private Integer recurso;
    private Integer conjunto;
    private String codigo;
    private String nome;
    private Integer imagem;
    private Boolean adicionado;
    public Peca(Integer id, Integer recurso, Integer conjunto, String codigo, String nome, Integer imagem, Boolean adicionado  ) {
        this.id = id;
        this.recurso = recurso;
        this.conjunto = conjunto;
        this.codigo = codigo;
        this.nome = nome;
        this.imagem = imagem;
        this.adicionado = adicionado;
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

    public Integer getConjunto() {
        return conjunto;
    }
    public void setConjunto(Integer conjunto) {
        this.conjunto = conjunto;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getImagem() {
        return imagem;
    }
    public void setImagem(Integer imagem) {
        this.imagem = imagem;
    }

    public Boolean getAdicionado() {
        return adicionado;
    }
    public void setAdicionado(Boolean adicionado) {
        this.adicionado = adicionado;
    }

    public String toString() {
        return (this.getCodigo() + " - " + this.getNome());
    }
}
