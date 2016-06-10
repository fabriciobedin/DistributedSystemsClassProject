/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.clientsoap;

import java.util.List;

/**
 *
 * @author Victor
 */
public class Teste {

    public static void main(String[] args) {

        ClientSoap clientSoap = new ClientSoap();
        List<Carro> listCarros = clientSoap.ListaAnoModelo(2017, "A3");
        listCarros.forEach((car) -> {
            System.out.println(car.getCodigo());
//
//            
//
        });
    }

//    return listCarros ;
}
