package br.com.traxx.traxxapp.camadas.comum;

/**
 * Created by lucas.ricarte on 16/08/2016.
 */
public class Moto {
    private Integer id;
    private Integer recurso;
    private String nome;
    private String descricao;
    private Integer imagem;

    public Moto(Integer id, Integer recurso, String nome, String descricao, Integer imagem) {
        this.id = id;
        this.recurso = recurso;
        this.nome = nome;
        this.descricao = descricao;
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
    public void setRecurso(Integer chave) {
        this.recurso = recurso;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getImagem() {
        return imagem;
    }
    public void setImagem(Integer imagem) {
        this.imagem = imagem;
    }

    public String toString() {
        return (this.getNome());
    }
}
