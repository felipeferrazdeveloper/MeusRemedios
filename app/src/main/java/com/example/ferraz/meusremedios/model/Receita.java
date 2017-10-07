package com.example.ferraz.meusremedios.model;

import android.icu.util.Calendar;

import java.util.Date;


/**
 * Created by ferraz on 04/10/17.
 */

public class Receita {

    private Medicamento medicamento;
    private Date dataInicio;
    private Date dataTermino;
    private int frequencia;
    private Date lastUsage;
    private String observacoes;

    public Receita() {
    }

    public Receita(Medicamento medicamento, String observacoes) {
        this.medicamento = medicamento;
        this.observacoes = observacoes;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }

    public void setFrequencia(int frequencia, String Frequencia) {
        switch (Frequencia){
            case "Hora(s)" :
                this.frequencia = frequencia;
                break;
            case "Dia(s)" :
                this.frequencia = frequencia*24;
                break;
            case "Semana(s)" :
                this.frequencia = frequencia*168;
                break;
            case "Mês(es)" :
                this.frequencia = frequencia*672;
                break;
        }
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public void setDataTermino(int periodo, String time) {
        Date data = dataInicio;
        Calendar calendar = Calendar.getInstance();
        if(this.dataInicio != null){
            switch (time){
                case "Dia(s)" :
                    calendar.add(Calendar.DAY_OF_YEAR, periodo);
                    break;
                case "Semana(s)" :
                    calendar.add(Calendar.DAY_OF_YEAR, periodo*7);
                    break;
                case "Mês(es)" :
                    calendar.add(Calendar.DAY_OF_YEAR, periodo*30);
                    break;
                case "Ano(s)" :
                    calendar.add(Calendar.DAY_OF_YEAR, periodo*365);
                    break;
            }
            this.dataTermino = calendar.getTime();
        }
    }

    public Date getNextDateTimeToMedicate(){
        Calendar returnValue;
        if(this.lastUsage == null){
            returnValue = Calendar.getInstance();
            returnValue.setTime(dataInicio);
            returnValue.add(Calendar.HOUR, this.frequencia);
        }
        else{
            returnValue = Calendar.getInstance();
            returnValue.setTime(lastUsage);
            returnValue.add(Calendar.HOUR, this.frequencia);
        }
        return returnValue.getTime();
    }

//    public void useMedicine(){
//        this.lastUsage = Calendar.getInstance();
//    }

    public void setDataInicio(int year, int month, int day, int hour, int minute) {
        Calendar data = Calendar.getInstance();
        data.set(year, month,day,hour, minute);
        dataInicio = data.getTime();
    }
}
