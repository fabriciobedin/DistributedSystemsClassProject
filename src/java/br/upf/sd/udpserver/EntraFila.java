package br.upf.sd.udpserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrei Tremea Basso
 */

public class EntraFila extends Thread{
    private final Buffering queue;
    private final DatagramSocket socketserver;
    
    public EntraFila(Buffering q, DatagramSocket soc){
        queue = q;
        socketserver = soc;
    }

    public void run()
    {
        byte[] dadosRecebido = new byte[5000];
        
        System.out.println("Servidor iniciado " + Servidor.getDataHora());
        while(true)
        {
            //System.out.println("Esperando requisição " + Servidor.getDataHora());
            DatagramPacket receivePacket = new DatagramPacket(dadosRecebido, dadosRecebido.length);
            try {
                socketserver.receive(receivePacket);
                System.out.println("Pacote Recebido do IP: " + receivePacket.getAddress() + " Porta: " + receivePacket.getPort() + " " + Servidor.getDataHora());
                try {
                    queue.insere(receivePacket);
                    System.out.println("Pacote adicionado na fila " + Servidor.getDataHora());
                } catch (InterruptedException ex) {
                    //
                }
                
            } catch (IOException ex) {
                //
            }
        }
        
    }
}
