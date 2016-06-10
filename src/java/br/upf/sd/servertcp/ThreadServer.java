/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.servertcp;

import br.upf.sd.dao.CarroDAO;
import br.upf.sd.model.Carro;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Fabricio Bedin
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
            envia.flush();
            System.out.println("String com menu enviada para cliente!");

            do {
                //servidor aguarda resposta com a operacao que o cliente selecionar
                System.out.println("Aguardando cliente...");
                operacao = recebe.readInt();
                System.out.println("Mensagem recebida com a operação " + operacao);

                //vai verificar se a operação informada existe no servidor
                //caso a operacção não exista, o cliente será informado e
                //o servidor aguardará nova operação
                System.out.println("Iniciando teste de operação");
                testeOperacao = validaOperacao(operacao);
                if (testeOperacao == true) {
                    envia.writeBoolean(true);
                    envia.flush();
                    System.out.println("Respondendo: operação válida!");
                } else {
                    envia.writeBoolean(false);
                    envia.flush();
                    System.out.println("Respondendo: operação inválida!");
                }
            } while (testeOperacao == false);

            switch (operacao) {
                case 1: {
                    //adicionar
                    Carro carro = new Carro();
                    
                    JSONObject carroObject = new JSONObject(recebe.readObject());
                    
                    System.out.println(carroObject.toString());
                    
                    carro.setCodigo(Integer.parseInt(carroObject.getString("codigo")));
                    carro.setMarca(carroObject.getString("marca"));
                    carro.setModelo(carroObject.getString("modelo"));
                    carro.setAno(Integer.parseInt(carroObject.getString("ano")));
                    carro.setPotencia(Float.parseFloat(carroObject.getString("potencia")));
                    carro.setCarga(Float.parseFloat(carroObject.getString("carga")));
                    carro.setComplemento(carroObject.getString("complemento"));
                    
                    
                    
                    System.out.println(carro.toString());
                    boolean verifica;
                    verifica = CarroDAO.getInstance().adicionarCarro(carro);
                    envia.writeBoolean(verifica);
                    envia.flush();
                   
                    
                      
                }

                
            case 2: {
                    //alterar
                    break;
                }

                case 3: {
                    //excluir
                    break;
                }

                case 4: {
                    //consultar
                    break;
                }

                case 5: {
                    //ano-modelo
                    break;
                }
                case 6: {
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
         * Método responsável por conter a mensagem inicial enviada para o
         * cliente. Nessa mensagem contém o menu com as operações disponíveis
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
                + "**                  1 - Adicionar                     4 - Consultar                     **\n"
                + "**                                                                                      **\n"
                + "**                  2 - Alterar                       5 - Ano/modelo                    **\n"
                + "**                                                                                      **\n"
                + "**                  3 - Excluir                       6 - Sair                          **\n"
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
    public static boolean validaOperacao(int operacao) {
        boolean retorno;
        if (operacao >= 7 || operacao <= 0) {
            retorno = false;
        } else {
            retorno = true;
        }
        return retorno;
    }
}
