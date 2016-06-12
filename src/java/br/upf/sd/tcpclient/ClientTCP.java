package br.upf.sd.tcpclient;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Fabricio Bedin
 */
public class ClientTCP {

    private static final Scanner lerTeclado = new Scanner(System.in);
    private static final Scanner lerTeclado2 = new Scanner(System.in);
    private static ObjectOutputStream envia;
    private static ObjectInputStream recebe;
    private static final Integer porta = 2006;
    private static String ip;
    private static boolean verifica;
    private static String menu;
    private static Integer operacao;

    public static void main(String[] args) throws Exception {
        
        
        Scanner lerTeclado3 = new Scanner(System.in);
        Scanner lerTeclado4 = new Scanner(System.in);

        System.out.println("Olá, informe o endereço IP do servidor que deseja conectar e tecle enter.");
        pegaIp();
        conectaServidor();
        recebeMostraMenu();
        testaEnviaOperacao();

        switch (operacao) {
            case 1:
                adicionar();
                break;

            case 2: {
                //alterar

            }
            case 3: {
                //excluir
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

                break;
            }

            case 4: {
                //consultarPorCodigo
                System.out.println("\n************ Consultar Carro ************");
                System.out.print("Informe o código do carro: ");
                envia.writeInt(Integer.parseInt(lerTeclado4.nextLine()));

//                Carro carro = new Carro();
//
//                JSONObject carroObject = new JSONObject(recebe.readObject());
//
//                System.out.println(carroObject.toString());
//
//                carro.setCodigo(Integer.parseInt(carroObject.getString("codigo")));
//                carro.setMarca(carroObject.getString("marca"));
//                carro.setModelo(carroObject.getString("modelo"));
//                carro.setAno(Integer.parseInt(carroObject.getString("ano")));
//                carro.setPotencia(Float.parseFloat(carroObject.getString("potencia")));
//                carro.setCarga(Float.parseFloat(carroObject.getString("carga")));
//                carro.setComplemento(carroObject.getString("complemento"));
//
//                System.out.println("\n************ Carro recebido ************");
//                System.out.println("Codigo:" + carro.getCodigo());
//                System.out.println("Marca:" + carro.getMarca());
//                System.out.println("Modelo:" + carro.getModelo());
//                System.out.println("Ano:" + carro.getAno());
//                System.out.println("Potencia:" + carro.getPotencia());
//                System.out.println("Carga:" + carro.getCarga());
//                System.out.println("Complemento:" + carro.getComplemento());
//                System.out.println("------------------------------");
                JSONParser parser = new JSONParser();

                JSONArray carroJArray = (JSONArray) parser.parse(new FileReader("saida.json"));

                for (Object o : carroJArray) {
                    JSONObject object = (JSONObject) o;

                    String name = (String) object.get("name");
                    System.out.println(name);

                    String city = (String) object.get("city");
                    System.out.println(city);

                    String job = (String) object.get("job");
                    System.out.println(job);

                    JSONArray cars = (JSONArray) object.get("cars");

                    for (Object c : cars) {
                        System.out.println(c + "");
                    }

                }

                break;
            }

            case 5: {
                //ano-modelo
                break;
            }

        }

    }

    public static void adicionar() {
        try {
            JSONObject carroObjeto = new JSONObject();
            System.out.println("\n************ Adicionar Carro ************");
            System.out.print("\nDigite o código do Carro: ");
            carroObjeto.put("codigo", Integer.parseInt(lerTeclado2.nextLine()));
            System.out.print("\nDigite a marca do Carro: ");
            carroObjeto.put("marca", lerTeclado2.nextLine());
            System.out.print("\nDigite o modelo do Carro: ");
            carroObjeto.put("modelo", lerTeclado2.nextLine());
            System.out.print("\nDigite o ano do Carro: ");
            carroObjeto.put("ano", Integer.parseInt(lerTeclado2.nextLine()));
            System.out.print("\nDigite o potencia do Carro: ");
            carroObjeto.put("potencia", Float.parseFloat(lerTeclado2.nextLine()));
            System.out.print("\nDigite o carga do Carro: ");
            carroObjeto.put("carga", Float.parseFloat(lerTeclado2.nextLine()));
            System.out.print("\nDigite o complemento: ");
            carroObjeto.put("complemento", lerTeclado2.nextLine());

            envia.writeObject(carroObjeto.toString());
            System.out.println(carroObjeto);

            envia.flush();
            System.out.println("Dados enviados para o servidor. Aguardando confirmação...");

            verifica = recebe.readBoolean();
            if (verifica == true) {
                System.out.println("\n\n*********** Carro adicionado com sucesso! ***********");
            } else {
                System.out.println("\n\nxxxxxxxxxxx ERRO! Carro não adicionado xxxxxxxxxxx");
                System.out.println("Deseja tentar adicionar novamente? (s/n)");
            }
        } catch (JSONException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void conectaServidor() {
        //após informar um ip no formato válido, vai tentar se conectar ao servidor.

        try {
            Socket servidor = new Socket(ip, porta);
            recebe = new ObjectInputStream(servidor.getInputStream());
            envia = new ObjectOutputStream(servidor.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void pegaIp() {
        //vai verificar se o ip digitado é válido.
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
