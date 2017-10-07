package com.example.ferraz.meusremedios.model;

/**
 * Created by ferraz on 05/10/17.
 */

public class Medicamento {

    private String concentracao;
    private String dataCadastro;
    private String detentor;
    private String farmaco;
    private String formaFarmaceutica;
    private String nome;
    private String registro;

    public Medicamento() {

    }

    public Medicamento(String concentracao, String dataCadastro, String detentor, String farmaco, String formaFarmaceutica, String nome, String registro) {
        this.concentracao = concentracao;
        this.dataCadastro = dataCadastro;
        this.detentor = detentor;
        this.farmaco = farmaco;
        this.formaFarmaceutica = formaFarmaceutica;
        this.nome = nome;
        this.registro = registro;
    }

    public String getConcentracao() {
        return concentracao;
    }

    public void setConcentracao(String concentracao) {
        this.concentracao = concentracao;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDetentor() {
        return detentor;
    }

    public void setDetentor(String detentor) {
        this.detentor = detentor;
    }

    public String getFarmaco() {
        return farmaco;
    }

    public void setFarmaco(String farmaco) {
        this.farmaco = farmaco;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
}
