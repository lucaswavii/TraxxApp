package br.com.traxx.traxxapp.camadas.comum;

/**
 * Created by lucas.ricarte on 23/08/2016.
 */
public class Grupo {
    private Integer id;
    private Integer recurso;
    private Integer moto;
    private Integer tipo;
    private String nome;


    public Grupo(Integer id, Integer recurso, Integer moto, Integer tipo,  String nome ) {
        this.id = id;
        this.nome = nome;
        this.moto = moto;
        this.recurso = recurso;
        this.tipo = tipo;
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

    public Integer getMoto() {
        return moto;
    }
    public void setMoto(Integer moto) {
        this.moto = moto;
    }

    public Integer getTipo() {
        return tipo;
    }
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
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
