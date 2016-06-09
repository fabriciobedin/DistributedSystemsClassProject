package br.upf.sd.udpclient;

import br.upf.sd.model.Carro;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrei Tremea Basso
 */

public class Cliente {

       public static void main(String[] args) throws UnknownHostException{
        byte[] msgenvia = new byte[3000];
        byte[] msgrecebe = new byte[3000];
        String enviardados;
        String resposta;
        int porta = 2010;
        //alterar o ip do servidor
        String ip = "localhost";
        InetAddress servidor = null;
        
      
        servidor = InetAddress.getByName(ip);

        
        DatagramSocket soc;
        DatagramPacket pct;
        
        
       // EnviaDados enviaDados;
        //controle da opcao do cliente
        Integer acao = 0;
        Carro carro;
        //para leitura dos dados digitados pelo cliente
        BufferedReader dados = new BufferedReader(new InputStreamReader(System.in));
        
        
        
        Scanner scanner = new Scanner( System.in );
        
               System.out.println("---------------------------------------------------------------------------");
               System.out.println("1 - Adicionar |"
                        + " 2 - Consultar por Codigo |"
                        + " 3 - Alterar |"
                        + " 4 - Excluir |"
                        + " 5 - Listar Ano e Modelo |"
                        + " 6 - Listar Carros ");
                System.out.println("Informe a Ação:");
            
                //ler um int acao
                acao = scanner.nextInt();
            

        carro = new Carro();

        switch (acao) {
            //adicionar carro
            case 1:
                System.out.println("Adicionar Carro");
                try {
                    System.out.println("Digite o código do Carro: ");
                    carro.setCodigo(Integer.parseInt(dados.readLine()));

                    System.out.println("Digite a marca do Carro: ");
                    carro.setMarca(dados.readLine());

                    System.out.println("Digite o modelo do Carro: ");
                    carro.setModelo(dados.readLine());

                    System.out.println("Digite o ano do Carro: ");
                    carro.setAno(Integer.parseInt(dados.readLine()));

                    System.out.println("Digite o potencia do Carro: ");
                    carro.setPotencia(Float.parseFloat(dados.readLine()));

                    System.out.println("Digite o carga do Carro: ");
                    carro.setCarga(Float.parseFloat(dados.readLine()));

                    System.out.println("Digite o complemento(Opicional): ");
                    carro.setComplemento(dados.readLine());

                } catch (IOException | NumberFormatException e) {
                    System.out.println("Carro não foi adicionado");
                }
                break;
             //consultar
            case 2:
                System.out.println("Consulta Carro");
                System.out.println("Digite o código do carro: ");
                try {
                    carro.setCodigo(Integer.parseInt(dados.readLine()));
                } catch (IOException ex) {
                    System.out.println("Consulta não realizada");
                }   break;
              //alterar
            case 3:                             
                System.out.println("Alterar Carro");
                try {
                    System.out.println("Digite o código do Carro: ");
                    carro.setCodigo(Integer.parseInt(dados.readLine()));

                    System.out.println("Digite a marca do Carro: ");
                    carro.setMarca(dados.readLine());

                    System.out.println("Digite o modelo do Carro: ");
                    carro.setModelo(dados.readLine());

                    System.out.println("Digite o ano do Carro: ");
                    carro.setAno(Integer.parseInt(dados.readLine()));

                    System.out.println("Digite o potencia do Carro: ");
                    carro.setPotencia(Float.parseFloat(dados.readLine()));

                    System.out.println("Digite o carga do Carro: ");
                    carro.setCarga(Float.parseFloat(dados.readLine()));

                    System.out.println("Digite o complemento(Opicional): ");
                    carro.setComplemento(dados.readLine());

                } catch (IOException | NumberFormatException e) {
                    System.out.println("Não conseguiu alterar o carro");
                }   break;
             //excluir
            case 4:
                System.out.println("Excluir Carro");
                System.out.println("Digite o código do carro: ");
                try {
                    carro.setCodigo(Integer.parseInt(dados.readLine()));
                } catch (IOException ex) {
                   System.out.println("Não conseguiu deletar o carro");
                }   break;
                //listar por ano e modelo
            case 5:
                System.out.println("Listar Carro por Ano e Modelo");
                try {
                    System.out.println("Digite o modelo: ");
                    carro.setModelo(dados.readLine());
                    System.out.println("Digite o ano: ");
                    carro.setAno(Integer.parseInt(dados.readLine()));
                } catch (IOException ex) {
                  System.out.println("Não Conseguiu Listar por Ano e ModeloS");
                }   break;
            case 6://listar carros
                System.out.println("Listar Carros");
                break;
        }
        
        //Preparando envio para o servidor
        //dados para enviar para o servidor UDP
        enviardados = acao + ":" +
                      carro.getCodigo() + ":" +
                      carro.getMarca() + ":" +
                      carro.getModelo() + ":" +
                      carro.getAno() + ":" +
                      carro.getPotencia() + ":" +
                      carro.getCarga() + ":" +
                      carro.getComplemento();
        msgenvia = enviardados.getBytes();
        
        //enviando para o servidor
        try {
            soc = new DatagramSocket();
            pct = new DatagramPacket(msgenvia, msgenvia.length, servidor, porta);
            soc.send(pct);
            System.out.println("Enviou conteudo para o servidor");

            //resposta vinda do servidor
            pct = new DatagramPacket(msgrecebe, msgrecebe.length);
            soc.receive(pct);
            resposta = new String(pct.getData());
            System.out.println("Cliente recebeu do servidor: " + resposta);
            
            soc.close();
        } catch (SocketException ex) {
           //
        } catch (IOException ex) {
           //
        }
    }
}
