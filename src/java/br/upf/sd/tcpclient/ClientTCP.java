package br.upf.sd.tcpclient;

import br.upf.sd.model.Carro;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Fabricio Bedin
 */
public class ClientTCP {

    private static ObjectOutputStream envia;
    private static ObjectInputStream recebe;
    private static final Integer porta = 2006;
    private static String ip;
    private static boolean verifica;
    private static String menu;
    private static Integer operacao;

    public static void main(String[] args) {
        Scanner lerTeclado = new Scanner(System.in);
        System.out.println("Olá, informe o endereço IP do servidor que deseja conectar e tecle enter.");
        pegaIp();
        conectaServidor();
        recebeMostraMenu();
        testaEnviaOperacao();

        switch (operacao) {
            case 1: {
                adicionar();
                break;
            }
            case 2: {
                listarTodos();
                break;
            }
            case 3: {
                consultar();
                break;
            }
            case 4: {
                consultarAnoModelo();
                break;
            }
            case 5: {
                alterar();
                break;
            }
            case 6: {
                apagar();
                break;
            }
            case 7: {
                sair();
                break;
            }
        }
    }

    public static void adicionar() {
        Scanner lerTeclado = new Scanner(System.in);
        try {
            Carro carro = new Carro();
            System.out.println("\n************ Adicionar Carro ************");
            System.out.print("\nDigite o código do Carro: ");
            carro.setCodigo(Integer.parseInt(lerTeclado.nextLine()));

            System.out.print("\nDigite a marca do Carro: ");
            carro.setMarca(lerTeclado.nextLine());

            System.out.print("\nDigite o modelo do Carro: ");
            carro.setModelo(lerTeclado.nextLine());

            System.out.print("\nDigite o ano do Carro: ");
            carro.setAno(Integer.parseInt(lerTeclado.nextLine()));

            System.out.print("\nDigite o potencia do Carro: ");
            carro.setPotencia(Float.parseFloat(lerTeclado.nextLine()));

            System.out.print("\nDigite o carga do Carro: ");
            carro.setCarga(Float.parseFloat(lerTeclado.nextLine()));

            System.out.print("\nDigite o complemento: ");
            carro.setComplemento(lerTeclado.nextLine());

            String enviarDados
                    = carro.getCodigo() + ":"
                    + carro.getMarca() + ":"
                    + carro.getModelo() + ":"
                    + carro.getAno() + ":"
                    + carro.getPotencia() + ":"
                    + carro.getCarga() + ":"
                    + carro.getComplemento();

            envia.writeObject(enviarDados);
            envia.flush();
            System.out.println("Dados enviados para o servidor. Aguardando confirmação...");

            verifica = recebe.readBoolean();
            if (verifica == true) {
                System.out.println("\n\n*********** Carro adicionado com sucesso! ***********");
            } else {
                System.out.println("\n\nxxxxxxxxxxx ERRO! Carro não adicionado xxxxxxxxxxx");
            }
//       
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void listarTodos() {
        try {

            String dadosRecebidos = recebe.readObject().toString();
            System.out.println(dadosRecebidos);

        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void consultar() {
        Scanner lerTeclado = new Scanner(System.in);
        try {
            System.out.println("\n************ Consultar Carro ************");
            System.out.print("Informe o código do carro: ");

            envia.writeInt(Integer.parseInt(lerTeclado.nextLine()));

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

            System.out.println("\n************ Carro recebido ************");
            System.out.println("Codigo:" + carro.getCodigo());
            System.out.println("Marca:" + carro.getMarca());
            System.out.println("Modelo:" + carro.getModelo());
            System.out.println("Ano:" + carro.getAno());
            System.out.println("Potencia:" + carro.getPotencia());
            System.out.println("Carga:" + carro.getCarga());
            System.out.println("Complemento:" + carro.getComplemento());
            System.out.println("------------------------------");

        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void consultarAnoModelo() {
        Scanner lerTeclado = new Scanner(System.in);
        try {
            Carro carro = new Carro();
            System.out.println("\n************ Listar Ano/Modelo ************");

            System.out.print("\nDigite o modelo do Carro: ");
            carro.setModelo(lerTeclado.nextLine());

            System.out.print("\nDigite o ano do Carro: ");
            carro.setAno(Integer.parseInt(lerTeclado.nextLine()));

            String enviarDados = (carro.getModelo() + ":" + carro.getAno());
            System.out.println(enviarDados);
            envia.writeObject(enviarDados);
            System.out.println("Dados enviados para o servidor. Aguardando resposta...");
            String dadosRecebidos = recebe.readObject().toString();
            System.out.println(dadosRecebidos);
            
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void alterar() {

    }

    public static void apagar() {
        Scanner lerTeclado = new Scanner(System.in);
        try {
            System.out.println("\n************ Excluir Carro ************");
            System.out.print("Informe o código do carro: ");

            envia.writeInt(Integer.parseInt(lerTeclado.nextLine()));

            System.out.println("Solicitação enviada para o servidor. Aguardando confirmação...");

            verifica = recebe.readBoolean();
            if (verifica == true) {
                System.out.println("\n\n*********** Carro apagado com sucesso! ***********");
            } else {
                System.out.println("\n\nxxxxxxxxxxx ERRO! Carro não apagado xxxxxxxxxxx");
                System.out.println("Deseja tentar adicionar novamente? (s/n)");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sair() {
        try {
            recebe.close();
            envia.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void conectaServidor() {
        try {
            Socket servidor = new Socket(ip, porta);
            recebe = new ObjectInputStream(servidor.getInputStream());
            envia = new ObjectOutputStream(servidor.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pegaIp() {
        Scanner lerTeclado = new Scanner(System.in);
        boolean testeIP;
        do {
            System.out.print("--> ");
            ip = lerTeclado.nextLine();
            testeIP = validaIP(ip);
            if (testeIP == false) {
                System.out.println("\nxxxxxxxxxxxxxx IP inválido!!! xxxxxxxxxxxxxx");
                System.out.println("Por favor, digite novamente o endereço do servidor");
                System.out.println("Ele deverá seguir esse formato: 000.000.000.000");
            }
        } while (testeIP == false);
    }

    public static void recebeMostraMenu() {
        try {
            menu = (String) recebe.readObject();
            System.out.println(menu);
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void testaEnviaOperacao() {
        Scanner lerTeclado = new Scanner(System.in);
        boolean testeOperacao;
        try {
            do {
                System.out.print("--> ");
                operacao = lerTeclado.nextInt();
                envia.writeInt(operacao);
                envia.flush();

                //vai enviar a operacao para o servidor e aguardar resposta se é válida ou não
                testeOperacao = recebe.readBoolean();
                if (testeOperacao == false) {
                    System.out.println("\nxxxx Operação inválida!! xxxx");
                    System.out.println("Por favor, digite novamente..");

                } else {
                    System.out.println("\nProcessando dados...");
                }
            } while (testeOperacao == false);
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Verifica se um endereço IP é válido
     *
     * @param ipAddress String contendo o endereço IP a ser testado
     * @return TRUE caso o IP seja válido e FALSE caso seja inválido
     */
    public static boolean validaIP(String ipAddress) {
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
