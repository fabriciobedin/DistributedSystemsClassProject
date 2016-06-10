package br.upf.sd.clienttcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.Scanner;
import org.json.JSONObject;

/**
 *
 * @author Fabricio Bedin
 */
public class ClientTCP {

    public static void main(String[] args) throws Exception {
        int porta = 2006;
        String ip = null;
        int operacao = 0;
        boolean testeIP;
        boolean testeOperacao;
        boolean verifica;
        String menu;
        String enviardados;
        byte[] msgenvia = new byte[3000];
        Scanner lerTeclado = new Scanner(System.in);
        Scanner lerTeclado2 = new Scanner(System.in);
        Scanner lerTeclado3 = new Scanner(System.in);
        Scanner lerTeclado4 = new Scanner(System.in);

        System.out.println("Olá, informe o endereço IP do servidor que deseja conectar e tecle enter.");

        //vai verificar se o ip digitado é válido.
        do {
            System.out.print("--> ");
            ip = lerTeclado.nextLine();
            testeIP = validarIP(ip);
            if (testeIP == false) {
                System.out.println("\nxxxxxxxxxxxxxx IP inválido!!! xxxxxxxxxxxxxx");
                System.out.println("Por favor, digite novamente o endereço do servidor");
                System.out.println("Ele deverá seguir esse formato: 000.000.000.000");
            }
        } while (testeIP == false);

        //após informar um ip no formato válido, vai tentar se conectar ao servidor.
        Socket servidor = new Socket(ip, porta);
        ObjectInputStream recebe = new ObjectInputStream(servidor.getInputStream());
        ObjectOutputStream envia = new ObjectOutputStream(servidor.getOutputStream());

        //recebe mensagem inicial
        menu = (String) recebe.readObject();
        System.out.println(menu);

        do {
            System.out.print("--> ");
            operacao = lerTeclado.nextInt();
            envia.writeInt(operacao);
            envia.flush();

            //vai enviar a operacao para o servidor e aguardar resposta se é valida ou não
            testeOperacao = recebe.readBoolean();
            if (testeOperacao == false) {
                System.out.println("\nxxxx Operação inválida!! xxxx");
                System.out.println("Por favor, digite novamente..");

            } else {
                System.out.println("\nProcessando dados...");
            }
        } while (testeOperacao == false);
        lerTeclado.close();

        switch (operacao) {
            //adicionar carro
            case 1:
                JSONObject myObj = new JSONObject();
                System.out.println("\n************ Adicionar Carro ************");
                System.out.print("\nDigite o código do Carro: ");
                myObj.put("codigo", Integer.parseInt(lerTeclado2.nextLine()));

                System.out.print("\nDigite a marca do Carro: ");
                myObj.put("marca", lerTeclado2.nextLine());

                System.out.print("\nDigite o modelo do Carro: ");
                myObj.put("modelo", lerTeclado2.nextLine());

                System.out.print("\nDigite o ano do Carro: ");
                myObj.put("ano", Integer.parseInt(lerTeclado2.nextLine()));

                System.out.print("\nDigite o potencia do Carro: ");
                myObj.put("potencia", Float.parseFloat(lerTeclado2.nextLine()));

                System.out.print("\nDigite o carga do Carro: ");
                myObj.put("carga", Float.parseFloat(lerTeclado2.nextLine()));

                System.out.print("\nDigite o complemento: ");
                myObj.put("complemento", lerTeclado2.nextLine());

                String dadosEnvio = myObj.toString();
                System.out.println("dados que serão enviados: " + dadosEnvio);

                envia.writeObject(dadosEnvio);
                envia.flush();
                System.out.println("Dados enviados para o servidor. Aguardando confirmação...");

                verifica = recebe.readBoolean();
                if (verifica == true) {
                    System.out.println("\n\n*********** Carro adicionado com sucesso! ***********");
                } else {
                    System.out.println("\n\nxxxxxxxxxxx ERRO! Carro não adicionado xxxxxxxxxxx");
                    System.out.println("Deseja tentar adicionar novamente? (s/n)");
                }
                
                break;

            case 2: {
                //alterar

            }
            case 3: {
                //excluir
                System.out.println("\n************ Excluir Carro ************");
                System.out.print("Informe o código do carro: ");
                envia.writeInt(Integer.parseInt(lerTeclado3.nextLine()));
                
                System.out.println("Solicitação enviada para o servidor. Aguardando confirmação...");
                
                verifica = recebe.readBoolean();
                if (verifica == true) {
                    System.out.println("\n\n*********** Carro adicionado com sucesso! ***********");
                } else {
                    System.out.println("\n\nxxxxxxxxxxx ERRO! Carro não adicionado xxxxxxxxxxx");
                    System.out.println("Deseja tentar adicionar novamente? (s/n)");
                }
              
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
        if (itens.length > 4) {
            retorno = false;
        }
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
