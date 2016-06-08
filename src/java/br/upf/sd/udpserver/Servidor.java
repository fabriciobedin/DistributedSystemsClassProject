package br.upf.sd.udpserver;

import java.io.IOException;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Andrei Tremea Basso
 */

public class Servidor {
    
    public static void main(String[] args) throws InterruptedException, IOException{
        
        int servidorporta = 2010;
        int tamanho = 10;
        DatagramSocket serverSocket;
        
        ExecutorService application = Executors.newCachedThreadPool();
        
        Buffering fila = new Buffering(tamanho);
        
        serverSocket = new DatagramSocket(servidorporta);
        
        application.execute(new EntraFila(fila, serverSocket));
        
        application.execute(new TiraFila(fila, serverSocket));
        
        application.shutdown();
    }
    
    public static String getDataHora(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(new Date()) + " ";  
    }
}
