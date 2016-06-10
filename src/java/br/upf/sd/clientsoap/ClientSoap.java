/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.clientsoap;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fabricio
 */
public class ClientSoap {

    static ServerSoapService service;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    }

    /**
     *
     * @param codigo
     * @return
     */
    public Carro Consulta(int codigo) {
        try {
            service = new ServerSoapService();
            ServerSoap port = service.getServerSoapPort();
            return port.consulta(codigo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Carro carro = new Carro();
        carro.setCodigo(0);
        return carro;
    }

    public String Adiciona(int codigo, String marca, String modelo, int ano, float potencia, float carga, String complemento) {
        try {
            service = new ServerSoapService();
            ServerSoap port = service.getServerSoapPort();
            switch (port.adiciona(codigo, marca, modelo, ano, potencia, carga, complemento)) {
                case 0:
                    return "Registro inserido com sucesso!";
                case 2:
                    return "Já existe um registro com o código: " + Integer.toString(codigo) + "!";
                case 1:
                case 3:
                default:
                    return "Erro ao executar operação, contate o Suporte!";
            }

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String Excluir(int codigo) {
        try {
            service = new ServerSoapService();
            ServerSoap port = service.getServerSoapPort();
            switch (port.excluir(codigo)) {
                case 0:
                    return "Nenhum registro foi encontrado para o Código: " + Integer.toString(codigo) + "!";
                case 1:
                    return "Carro de código: " + Integer.toString(codigo) + " excluido com sucesso!";
                case -1:
                default:
                    return "Erro ao executar operação, contate o Suporte!";
            }

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String Altera(int codigo, String marca, String modelo, int ano, float potencia, float carga, String complemento) {
        try {
            service = new ServerSoapService();
            ServerSoap port = service.getServerSoapPort();
            switch (port.altera(codigo, marca, modelo, ano, potencia, carga, complemento)) {
                case 0:
                    return "Nenhum registro foi encontrado para o Código: " + Integer.toString(codigo) + "!";
                case 1:
                    return "Registro alterado com sucesso!";
                case -1:
                default:
                    return "Erro ao executar operação, contate o Suporte!";
            }

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Carro> ListaAnoModelo(int ano, String modelo) {
        List<Carro> listCarros = new ArrayList<>();
        try {
            service = new ServerSoapService();
            ServerSoap port = service.getServerSoapPort();

            listCarros = port.listaAnoModelo(ano, modelo);
        } catch (Exception e) {
        }

        return listCarros;
    }

}
