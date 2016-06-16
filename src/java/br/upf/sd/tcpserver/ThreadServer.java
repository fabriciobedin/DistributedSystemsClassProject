package br.upf.sd.tcpserver;

import br.upf.sd.dao.CarroDAO;
import br.upf.sd.model.Carro;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Fabricio Bedin
 */
public class ThreadServer extends Thread {

    private final Socket cliente;
    private final Integer contThread;
    private ObjectOutputStream envia;
    private ObjectInputStream recebe;
    private boolean testeOperacao;
    boolean verifica = false;

    public ThreadServer(Socket cliente, Integer contThread) {
        this.cliente = cliente;
        this.contThread = contThread;
    }

    @Override
    public void run() {
        int operacao;
        System.out.println("Thread "+ contThread+" - iniciada ****************************************** - IP: "+cliente.getInetAddress().getHostAddress()+" - " +ServerTCP.getDataHora());
        enviaMenu();
        operacao = recebeOperacao();

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
            }
        }
    }

    public void enviaMenu() {
        instObjetos();
        try {
            envia.writeObject(menu());
            envia.flush();
        } catch (IOException ex) {
            System.out.println("Thread "+ contThread+" - "+"Erro ao enviar o menu"+ " - " + ServerTCP.getDataHora());
        }
        System.out.println("Thread "+ contThread+" - "+"String com menu enviada para cliente!"+ " - " + ServerTCP.getDataHora());
    }

    public Integer recebeOperacao() {
        Integer operacao = 0;
        try {

            do {
                //servidor aguarda resposta com a operacao que o cliente selecionar
                System.out.println("Thread "+ contThread+" - "+"Aguardando cliente"+ " - " + ServerTCP.getDataHora());

                operacao = recebe.readInt();
                System.out.println("Thread "+ contThread+" - "+"Mensagem recebida com a operação " + operacao + " - " + ServerTCP.getDataHora());

                //vai verificar se a operação informada existe no servidor
                //caso a operacção não exista, o cliente será informado e
                //o servidor aguardará nova operação
                System.out.println("Thread "+ contThread+" - "+"Iniciando teste de operação"+ " - " + ServerTCP.getDataHora());
                testeOperacao = validaOperacao(operacao);
                if (testeOperacao == true) {
                    envia.writeBoolean(true);
                    envia.flush();
                    System.out.println("Thread "+ contThread+" - "+"Respondendo: operação válida!"+ " - " + ServerTCP.getDataHora());
                } else {
                    envia.writeBoolean(false);
                    envia.flush();
                    System.out.println("Thread "+ contThread+" - "+"Respondendo: operação inválida!"+ " - " + ServerTCP.getDataHora());
                }
            } while (testeOperacao == false);
        } catch (IOException ex) {
            System.out.println("Thread "+ contThread+" - "+"Erro ao receber operação! "+ " - " + ServerTCP.getDataHora());
        }
        return operacao;
    }

    /**
     * Método responsável por conter a mensagem inicial enviada para o cliente.
     * Nessa mensagem contém o menu com as operações disponíveis
     *
     * @return String
     */
    public String menu() {
        String ipServer = "anônimo";
        try {
            ipServer = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            System.out.println("Thread "+ contThread+" - "+"Erro ao obter o endereço IP"+ " - " + ServerTCP.getDataHora());
        }

        String menu = (""
                + "\n******************************************************************************************\n"
                + "**                   Você está conectado ao servidor " + ipServer + "!                     **\n"
                + "******************************************************************************************\n"
                + "**                                                                                      **\n"
                + "**                     Digite abaixo o número da opção desejada                         **\n"
                + "**                                                                                      **\n"
                + "**                  1 - Adicionar                     4 - Ano/modelo                    **\n"
                + "**                                                                                      **\n"
                + "**                  2 - Listar Todos                  5 - Alterar                       **\n"
                + "**                                                                                      **\n"
                + "**                  3 - Consultar                     6 - Apagar                        **\n"
                + "**                                                                                      **\n"
                + "**                                     7 - Sair                                         **\n"
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
        retorno = !(operacao >= 8 || operacao <= 0);
        return retorno;
    }

    public void instObjetos() {
        try {
            envia = new ObjectOutputStream(cliente.getOutputStream());
            recebe = new ObjectInputStream(cliente.getInputStream());
        } catch (IOException ex) {
            System.out.println("Thread "+ contThread+" - "+"Erro ao instanciar um ObjetcOutputStream!"+ " - " + ServerTCP.getDataHora());
            ex.printStackTrace();
        }
    }

    public void adicionar() {
        try {

            String dadosRecebidos = (String) recebe.readObject();
            String[] parts = dadosRecebidos.split(":");
            Carro carro = new Carro();
            if (!"null".equals(parts[0])) {
                carro.setCodigo(Integer.parseInt(parts[0]));
            }
            if (!"null".equals(parts[1])) {
                carro.setMarca(parts[1]);
            }
            if (!"null".equals(parts[2])) {
                carro.setModelo(parts[2]);
            }
            if (!"null".equals(parts[3])) {
                carro.setAno(Integer.parseInt(parts[3]));
            }
            if (!"null".equals(parts[4])) {
                carro.setPotencia(Float.parseFloat(parts[4]));
            }
            if (!"null".equals(parts[5])) {
                carro.setCarga(Float.parseFloat(parts[5]));
            }
            if (!"null".equals(parts[6])) {
                carro.setComplemento(parts[6]);
            }
            CarroDAO dao = new CarroDAO();
            boolean ver = dao.inserir(carro);
            System.out.println("Thread "+ contThread+" - "+"Cliente inseriu o carro: " + carro.getModelo() + " - " + ServerTCP.getDataHora());

            if (ver == true) {
                envia.writeBoolean(true);
                envia.flush();
                System.out.println("Thread "+ contThread+" - "+"Servidor confirmou inclusâo ao cliente - " + ServerTCP.getDataHora());
            } else {
                envia.writeBoolean(false);
                envia.flush();
                System.out.println("Thread "+ contThread+" - "+"Servidor informou ao cliente que não conseguiu incluir o carro - " + ServerTCP.getDataHora());
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarTodos() {
        System.out.println("Thread "+ contThread+" - "+"Cliente solicitou listar todos! - " + ServerTCP.getDataHora());
        try {
            CarroDAO dao = new CarroDAO();
            List ListaCarro = dao.listar();
            envia.writeObject(ListaCarro.toString());
            envia.flush();
            System.out.println("Thread "+ contThread+" - "+"Lista enviada para o cliente - " + ServerTCP.getDataHora());
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultar() {
        
        try {
            
            Integer codListar = recebe.readInt();
            System.out.println("Thread "+ contThread+" - "+"Cliente consultou carro de código: "+ codListar + " - " + ServerTCP.getDataHora());
            
            Carro carro = new Carro();
            CarroDAO dao = new CarroDAO();
            carro = dao.buscarCod(codListar);
            
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
            System.out.println("Thread "+ contThread+" - "+"Carro enviado para cliente - " + ServerTCP.getDataHora());
            

        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public void consultarAnoModelo() {
        System.out.println("Thread "+ contThread+" - "+"Cliente solicitou listar ano/modelo! - " + ServerTCP.getDataHora());
        try {
            String dadosRecebidos = (String) recebe.readObject();
            
            Carro carro = new Carro();
            String[] parts = dadosRecebidos.split(":");
            if (!"null".equals(parts[0])) {
                carro.setModelo(parts[0]);
            }
            if (!"null".equals(parts[1])) {
                carro.setAno(Integer.parseInt(parts[1]));
            }
            
            CarroDAO dao = new CarroDAO();
            List ListaCarro = dao.listarAnoModelo(carro.getAno(), carro.getModelo());
            envia.writeObject(ListaCarro.toString());
            envia.flush();
            System.out.println("Thread "+ contThread+" - "+"Lista enviada para o cliente " + ServerTCP.getDataHora());
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void alterar() {
        try {
            
            Integer codListar = recebe.readInt();
            System.out.println("Thread "+ contThread+" - "+"Cliente consultou carro de código: "+ codListar + " - " + ServerTCP.getDataHora());
            
            Carro carro = new Carro();
            CarroDAO dao = new CarroDAO();
            carro = dao.buscarCod(codListar);
            
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
            System.out.println("Thread "+ contThread+" - "+"Carro enviado para cliente - " + ServerTCP.getDataHora());
            
            String dadosRecebidos = (String) recebe.readObject();
            String[] parts = dadosRecebidos.split(":");
            Carro carro2 = new Carro();
            if (!"null".equals(parts[0])) {
                carro2.setCodigo(Integer.parseInt(parts[0]));
            }
            if (!"null".equals(parts[1])) {
                carro2.setMarca(parts[1]);
            }
            if (!"null".equals(parts[2])) {
                carro2.setModelo(parts[2]);
            }
            if (!"null".equals(parts[3])) {
                carro2.setAno(Integer.parseInt(parts[3]));
            }
            if (!"null".equals(parts[4])) {
                carro2.setPotencia(Float.parseFloat(parts[4]));
            }
            if (!"null".equals(parts[5])) {
                carro2.setCarga(Float.parseFloat(parts[5]));
            }
            if (!"null".equals(parts[6])) {
                carro2.setComplemento(parts[6]);
            }
            CarroDAO dao2 = new CarroDAO();
            boolean ver = dao.atualizar(carro2);
            System.out.println("Thread "+ contThread+" - "+"Cliente alterou o carro: " + carro2.getCodigo()+ " - " + ServerTCP.getDataHora());

            if (ver == true) {
                envia.writeBoolean(true);
                envia.flush();
                System.out.println("Thread "+ contThread+" - "+"Servidor confirmou a alteração ao cliente - " + ServerTCP.getDataHora());
            } else {
                envia.writeBoolean(false);
                envia.flush();
                System.out.println("Thread "+ contThread+" - "+"Servidor informou ao cliente que não conseguiu alterar o carro - " + ServerTCP.getDataHora());
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
      }

    public void apagar() {
        int codApagar;
        try {
            codApagar = recebe.readInt();
            System.out.println("Thread "+ contThread+" - "+"Apagando carro com o código: " + codApagar+ " - " + ServerTCP.getDataHora());
            CarroDAO dao = new CarroDAO();
            verifica = dao.apagarCod(codApagar);
            envia.writeBoolean(verifica);
            envia.flush();
            System.out.println("Thread "+ contThread+" - "+"Confirmação enviada para cliente!"+ " - " + ServerTCP.getDataHora());
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sair() {
        try {
            recebe.close();
            envia.close();

        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
