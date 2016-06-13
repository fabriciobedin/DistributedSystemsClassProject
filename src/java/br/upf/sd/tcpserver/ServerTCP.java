/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.tcpserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Fabricio Bedin
 */
public class ServerTCP{

    public static void main(String[] args) {
        int porta = 2006;
        boolean conectado = true;
        try {
            ServerSocket servidor = new ServerSocket(porta);
            int cont = 0;
            while (conectado) {
                cont++;
                System.out.println("servidor executando, porta " + porta + " aberta!! " + getDataHora());
                Socket cliente = servidor.accept();
                System.out.println("cliente conectado: " + cliente.getInetAddress().getHostAddress() + " - "+ getDataHora());
                ThreadServer clienteConectado = new ThreadServer(cliente, cont);
                clienteConectado.start();
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    public static String getDataHora(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(new Date()) + " ";  
    }

}
