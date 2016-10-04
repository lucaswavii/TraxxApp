package br.com.traxx.traxxapp.camadas.comum;

/**
 * Created by lucas.ricarte on 24/08/2016.
 */
public class Conjunto {
    private Integer id;
    private Integer recurso;
    private Integer grupo;
    private String codigo;
    private String nome;
    private Integer imagem;


    public Conjunto(Integer id, Integer recurso, Integer grupo, String codigo, String nome, Integer imagem  ) {
        this.id = id;
        this.recurso = recurso;
        this.grupo = grupo;
        this.codigo = codigo;
        this.nome = nome;
        this.imagem = imagem;
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

    public Integer getGrupo() {
        return grupo;
    }
    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
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

    public String toString() {
        return (this.getCodigo() + " - " + this.getNome());
    }

}
