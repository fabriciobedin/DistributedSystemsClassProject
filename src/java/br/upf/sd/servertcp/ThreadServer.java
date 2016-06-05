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
        int operacao;
        boolean testeOperacao;
        
        try {
            System.out.println("Thread iniciada!");
            ObjectOutputStream envia = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream recebe = new ObjectInputStream(cliente.getInputStream());
            
            //após o cliente se conectar ao servidor, receberá uma mensagem com o menu disponível
            envia.writeObject(msgInicial());
            System.out.println("String com menu enviada para cliente!");
            
            //servidor aguarda resposta com a operacao que o cliente selecionar
            operacao = recebe.readInt();
            System.out.println("Mensagem recebida com a operação "+ operacao);

            //vai verificar se a operação informada existe no servidor
            //caso a operacção não exista, o cliente será informado e
            //o servidor aguardará nova operação
            
            do {
                System.out.println("Iniciando teste de operação");
                testeOperacao = validaOperacao(operacao);
                if (testeOperacao == true) {
                    envia.writeBoolean(true);
                    System.out.println("Respondendo: operação válida!");
                } else {
                    envia.writeBoolean(false);
                    System.out.println("Respondendo: operação inválida!");
                }
            } while (testeOperacao = false);

            switch (operacao) {
                case 1: {
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
                    Thread.currentThread().stop();
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

    /**
     * Método responsável por conter a mensagem inicial enviada para o cliente.
     * Nessa mensagem contém o menu com as operações disponíveis
     *
     * @return String
     */
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

    /**
     * Método responsável por testar se a opção escolhida pelo cliente é válida
     * ou não. Retorna uma boolean verdadeira se estiver correta ou uma boolean
     * falsa se estiver incorreta.
     *
     * @param operacao
     * @return boolean
     */
    public boolean validaOperacao(int operacao) {
        boolean retorno = true;
        if (operacao >= 6 || operacao <= 0) {
            retorno = false;
        } else {
            retorno = true;
        }
        return retorno;
    }

}
