/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.servertcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author fabricio
 */
public class ThreadServer extends Thread {

    private Socket cliente;

    public ThreadServer(Socket cliente) {
        this.cliente = cliente;
    }

    public void run() {

        try {
            ObjectOutputStream envia = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream recebe = new ObjectInputStream(cliente.getInputStream());

            envia.writeObject(msgInicial());
            int opcao = (Integer)recebe.readInt();

            switch (opcao) {
                case 1: {
                    envia.writeObject("escolheu a opcao1");
                    break;
                }

                case 2: {

                    break;
                }

                case 3: {

                    break;
                }

                case 4: {

                    break;
                }

                case 5: {
                    recebe.close();
                    envia.close();
                    cliente.close();
                    break;
                }

            }
        } catch (Exception e) {
            System.out.println("Excecao ocorrida na thread: " + e.getMessage());
            try {
                cliente.close();
            } catch (Exception ec) {
            }
        }

    }

    public String msgInicial() {
        String ipServer = "anônimo";
        try {
            ipServer = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
        }

        String menu = (""
                + "\n******************************************************************************************\n"
                + "**                   Você está conectado ao servidor " + ipServer + "!                     **\n"
                + "******************************************************************************************\n"
                + "**                                                                                      **\n"
                + "**                     Digite abaixo o número da opção desejada                         **\n"
                + "**                                                                                      **\n"
                + "**                  1 - Listar                        3 - Adicionar                     **\n"
                + "**                                                                                      **\n"
                + "**                  2 - Editar                        4 - Apagar                        **\n"
                + "**                                                                                      **\n"
                + "**                                     5 - Sair                                         **\n"
                + "**                                                                                      **\n"
                + "******************************************************************************************\n");

        return menu;

    }

}
