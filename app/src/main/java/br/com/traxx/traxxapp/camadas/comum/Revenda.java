package br.com.traxx.traxxapp.camadas.comum;

import java.io.Serializable;

/**
 * Created by lucas.ricarte on 08/09/2016.
 */
public class Revenda implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer chave;
    private String codigo;
    private String nome;
    private String endereco;
    private String uf;
    private String cidade;
    private String fone;
    private String email;
    private Double latitude;
    private Double longitude;

    public Revenda(Integer chave, String codigo, String nome, String endereco, String uf, String cidade, String fone, String email, Double latitude, Double longitude ) {
        this.chave = chave;
        this.codigo = codigo;
        this.nome = nome;
        this.endereco = endereco;
        this.uf = uf;
        this.cidade = cidade;
        this.fone = fone;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getChave() {
        return chave;
    }

    public void setChave(Integer chave) {
        this.chave = chave;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
