/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.servertcp;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author fabricio
 */
public class ServerTCP{

    public static void main(String[] args) {
        int porta = 2006;
        boolean conectado = true;
        try {
            ServerSocket servidor = new ServerSocket(porta);
            System.out.println("servidor ouvindo a porta 2006");
            
            while (conectado) {
                Socket cliente = servidor.accept();
                System.out.println("cliente conectado: " + cliente.getInetAddress().getHostAddress());
                
                ThreadServer clienteConectado = new ThreadServer(cliente);
                clienteConectado.start();
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
