/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.clientsoap;

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
            switch(port.adiciona(codigo, marca, modelo, ano, potencia, carga, complemento))
            {
                case 0:
                    return "Registro inserido com sucesso!";
                case 2:
                    return "Já existe um registroe com o código: " + Integer.toString(codigo) + "!";
                case 1:
                case 3:
                default:
                    return "Erro ao executar operação, contate o Suporte!";
            }
            
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
}
