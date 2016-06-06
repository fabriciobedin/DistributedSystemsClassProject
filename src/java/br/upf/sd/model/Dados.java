/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Fabricio
 */
public class Dados implements Serializable{
    
    private int operacao;
    private Carro carro;
    private List alteracao;
    private boolean retorno;

    public Dados(int operacao, Carro carro, boolean retorno) {
        this.operacao = operacao;
        this.carro = carro;
        this.retorno = retorno;
    }

    public Dados(Carro carro) {
        this.carro = carro;
    }

    public Dados() {
    }

    public Dados(int operacao, Carro carro, List alteracao, boolean retorno) {
        this.operacao = operacao;
        this.carro = carro;
        this.alteracao = alteracao;
        this.retorno = retorno;
    }

    public int getOperacao() {
        return operacao;
    }

    public Carro getCarro() {
        return carro;
    }

    public List getAlteracao() {
        return alteracao;
    }

    public boolean isRetorno() {
        return retorno;
    }

    public void setOperacao(int operacao) {
        this.operacao = operacao;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public void setAlteracao(List alteracao) {
        this.alteracao = alteracao;
    }

    public void setRetorno(boolean retorno) {
        this.retorno = retorno;
    }
}
