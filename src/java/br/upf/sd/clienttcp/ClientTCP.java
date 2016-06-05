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
        String ip = null;
        int operacao;
        boolean testeIP;
        boolean testeOperacao;
        String menu;
        Scanner lerTeclado = new Scanner(System.in);
        
        System.out.println("Olá, informe o endereço IP do servidor\nque deseja conectar e tecle enter.\n");
        
        //vai verificar se o ip digitado é válido.
        //caso não seja, vai informar que não está correto e solicitar
        //que o usuário digite novamente.
        do {
            ip = lerTeclado.nextLine();
            testeIP = validarIP(ip);
            if (testeIP == false) {
                System.out.println("\nxxxxxxxxxxxxxx IP inválido!!! xxxxxxxxxxxxxx");
                System.out.println("Por favor, digite novamente o endereço do servidor");
                System.out.println("Ele deverá seguir esse formato: 000.000.000.000");
            }
        } while (testeIP == false);
        
        //após informar um ip no formato válido, vai tentar se conectar ao servidor.
        try {
            Socket servidor = new Socket(ip, porta);
            ObjectInputStream recebe = new ObjectInputStream(servidor.getInputStream());
            ObjectOutputStream envia = new ObjectOutputStream(servidor.getOutputStream());

            menu = (String) recebe.readObject();
           
            do {
                System.out.println(menu);
                operacao = lerTeclado.nextInt();
                envia.writeInt(operacao);
                //vai enviar a operacao para o servidor e aguardar resposta
                //se a operação for válida, o servidor vai retornar true
                //caso seja inválida, ele vai retornar false e aguardar nova operação
                testeOperacao = recebe.readBoolean();
                if (testeOperacao == false){
                    System.out.println("xxxx Operação inválida!! xxxx");
                    System.out.println("Por favor, digite novamente..\n");
                }else{
                    System.out.println("\n--> Processando dados...");
                }
            } while (testeOperacao = false);

 
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
