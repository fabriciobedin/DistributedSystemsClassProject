package br.upf.sd.clienttcp;

import br.upf.sd.model.Dados;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author fabricio
 */
public class ClientTCPTeste {

    public static void main(String[] args) throws Exception {
        int porta = 2006;
        String ip = null;
        int operacao = 0;
        boolean testeIP;
        boolean testeOperacao;
        String menu;
        Scanner lerTeclado = new Scanner(System.in);

        System.out.println("Olá, informe o endereço IP do servidor que deseja conectar e tecle enter.");
        

        //vai verificar se o ip digitado é válido.
        //caso não seja, vai informar que não está correto e solicitar
        //que o usuário digite novamente.
        do {
            System.out.print("-->");
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
            System.out.print("-->");
            operacao = lerTeclado.nextInt();
            envia.writeInt(operacao);
            envia.flush();
            //vai enviar a operacao para o servidor e aguardar resposta
            //se a operação for válida, o servidor vai retornar true
            //caso seja inválida, ele vai retornar false e aguardar nova operação
            boolean aaa = new Boolean(recebe.readBoolean());
            testeOperacao = aaa;
            if (testeOperacao == false) {
                System.out.println("\nxxxx Operação inválida!! xxxx");
                System.out.println("Por favor, digite novamente..");

            } else {
                System.out.println("\nProcessando dados...");
            }
        } while (testeOperacao == false);
        
        Dados dados = new Dados();
        dados = (Dados) recebe.readObject();

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
