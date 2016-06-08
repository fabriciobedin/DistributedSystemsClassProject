package br.upf.sd.udpserver;

import java.net.DatagramPacket;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Andrei Tremea Basso
 */

public class Buffering {
    private final ArrayBlockingQueue<DatagramPacket> buffering;
    
    public Buffering(int t)
    {
        buffering = new ArrayBlockingQueue<DatagramPacket>( t );
    }
    
    //insere pacote na fila
    public void insere( DatagramPacket pacote ) throws InterruptedException{
        buffering.put(pacote);
    }
    
    //retira pacote da fila
    public DatagramPacket retira() throws InterruptedException{
        DatagramPacket pacote = buffering.take();
        
        return pacote;
    }
}
