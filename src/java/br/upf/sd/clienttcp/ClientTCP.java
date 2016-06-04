/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.clienttcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author fabricio
 */
public class ClientTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        int porta = 2006;
        String host = null;
        int opcao = 0;
        boolean teste;
        String menu;
        Scanner lerTeclado = new Scanner(System.in);

        System.out.println("Olá, informe o endereço IP do servidor\nque deseja conectar e tecle enter.\n");
        do {
            host = lerTeclado.nextLine();
            teste = validarIP(host);
            if (teste == false) {
                System.out.println("\nIP inválido!!!");
                System.out.println("Por favor, digite novamente o endereço do servidor");
                System.out.println("Ele deverá estar nesse modelo: 000.000.000.000");
            }
        } while (teste == false);

        try {
            Socket servidor = new Socket(host, porta);
            ObjectInputStream recebe = new ObjectInputStream(servidor.getInputStream());
            ObjectOutputStream envia = new ObjectOutputStream(servidor.getOutputStream());

            menu = (String) recebe.readObject();

            do {
                System.out.println(menu);
                opcao = lerTeclado.nextInt();

                if (opcao >= 6 || opcao <= 0) {
                    System.out.println("Opção inválida!!!");
                    System.out.println("Por favor, digite o número novamente\n\n");
                }

            } while (opcao >= 6 || opcao < 0);

            System.out.println("--> Buscando dados...");
            envia.write(opcao);
            
            
            
            

        } catch (Exception e) {
        }

    }

    /**
     * Verifica se um endereço IP é válido
     *
     * @param ipAddress String contendo o endereço IP a ser testado
     * @return TRUE caso o IP seja válido e FALSE caso seja inválido
     */
    public static boolean validarIP(String ipAddress) {
        boolean retorno = true;
        //Minimo 1.1.1.1 Máximo 255.255.255.255
        //if (ipAddress.length() < 7 || ipAddress.length() > 15) {
        //    retorno = false;
        //}
        //Quebrando a string em array pelo símbolo . deve ser gerado um array 
        //com 4 itens
        String[] itens = ipAddress.split(".");
        //if (itens.length != 4) {
        //    retorno = false;
        //}
        try {
            for (String item : itens) {
                if (Integer.parseInt(item) < 1 || Integer.parseInt(item) > 255) {
                    retorno = false;
                }
            }
            //Exception capsulada pois o objetivo do método é só testar o endereço    
        } catch (NumberFormatException ex) {
            retorno = false;
        }
        return retorno;
    }

}
