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
}
