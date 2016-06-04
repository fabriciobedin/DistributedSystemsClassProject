/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.model;

import java.util.Objects;

/**
 *
 * @author Fabricio
 */
public class Dados {
    
    private int acao;
    private Carro carro;
    private boolean retorno;

    public Dados(int acao, Carro carro, boolean retorno) {
        this.acao = acao;
        this.carro = carro;
        this.retorno = retorno;
    }

    public int getAcao() {
        return acao;
    }

    public Carro getCarro() {
        return carro;
    }

    public boolean isRetorno() {
        return retorno;
    }

    public void setAcao(int acao) {
        this.acao = acao;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public void setRetorno(boolean retorno) {
        this.retorno = retorno;
    }

}
