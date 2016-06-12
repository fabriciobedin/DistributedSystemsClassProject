package br.upf.sd.tcpserver;

import br.upf.sd.dao.CarroDAO;
import br.upf.sd.model.Carro;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Fabricio Bedin
 */
public class ThreadServer extends Thread {

    private final Socket cliente;
    private ObjectOutputStream envia;
    private ObjectInputStream recebe;
    private boolean testeOperacao;
    boolean verifica = false;

    public ThreadServer(Socket cliente) {
        this.cliente = cliente;
    }

    public void run() {
        int operacao;
        System.out.println("Thread iniciada!");
        enviaMenu();
        operacao = recebeOperacao();

        switch (operacao) {
            case 1: {
                System.out.println("adicionando..");
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
            System.out.println("Erro ao enviar o menu");
            ex.printStackTrace();
        }
        System.out.println("String com menu enviada para cliente!");
    }

    public Integer recebeOperacao() {
        Integer operacao = 0;
        try {

            do {
                //servidor aguarda resposta com a operacao que o cliente selecionar
                System.out.println("Aguardando cliente...");

                operacao = recebe.readInt();
                System.out.println("Mensagem recebida com a operação " + operacao + " - " + ServerTCP.getDataHora());

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
        } catch (IOException ex) {
            System.out.println("Erro ao receber operação! ");
            ex.printStackTrace();
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
            System.out.println("Erro ao obter o endereço IP ");
            e.printStackTrace();
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
        if (operacao >= 8 || operacao <= 0) {
            retorno = false;
        } else {
            retorno = true;
        }
        return retorno;
    }

    public void instObjetos() {
        try {
            envia = new ObjectOutputStream(cliente.getOutputStream());
            recebe = new ObjectInputStream(cliente.getInputStream());
        } catch (IOException ex) {
            System.out.println("Erro ao instanciar um ObjetcOutputStream! ");
            ex.printStackTrace();
        }
    }

    public void adicionar() {
        try {
            

//            carro.setCodigo(Integer.parseInt(carroObjeto.getString("codigo")));
//            carro.setMarca(carroObjeto.getString("marca"));
//            carro.setModelo(carroObjeto.getString("modelo"));
//            carro.setAno(Integer.parseInt(carroObjeto.getString("ano")));
//            carro.setPotencia(Float.parseFloat(carroObjeto.getString("potencia")));
//            carro.setCarga(Float.parseFloat(carroObjeto.getString("carga")));
//            carro.setComplemento(carroObjeto.getString("complemento"));
//            System.out.println(carro);
//            CarroDAO dao = new CarroDAO();
//            dao.inserir(carro);
//            System.out.println("carro inserido no banco!");
            Carro carro = new Carro();
            JSONObject carroObjeto = new JSONObject(recebe.readObject());
            carroObjeto.toString();
            System.out.println(carroObjeto);
            
            String codigoo = (String) carroObjeto.get("codigo");
            System.out.println(codigoo);
            
            JSONParser parser = new JSONParser();

            JSONArray carroJArray = null;

            carroJArray = (JSONArray) parser.parse(new FileReader("saida.json"));

            for (Object o : carroJArray) {
                JSONObject object = (JSONObject) o;
                String codigo = (String) object.get("codigo");
                System.out.println(codigo);

                String city = (String) object.get("city");
                System.out.println(city);

                String job = (String) object.get("job");
                System.out.println(job);

                JSONArray cars = (JSONArray) object.get("cars");

                for (Object c : cars) {
                    System.out.println(c + "");
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {

        } catch (ParseException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarTodos() {

    }

    public void consultar() {
        try {
            int codListar;
            codListar = recebe.readInt();
            System.out.println("Consultando carro com o código: " + codListar);
            CarroDAO dao = new CarroDAO();
            Carro carro = new Carro();
            carro = dao.buscarCod(codListar);
            JSONObject carroObjeto = new JSONObject();
            carroObjeto.put("codigo", carro.getCodigo());
            carroObjeto.put("marca", carro.getMarca());
            carroObjeto.put("modelo", carro.getModelo());
            carroObjeto.put("ano", carro.getAno());
            carroObjeto.put("potencia", carro.getPotencia());
            carroObjeto.put("carga", carro.getCarga());
            carroObjeto.put("complemento", carro.getComplemento());
            envia.writeObject(carroObjeto.toString());

        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarAnoModelo() {

    }

    public void alterar() {

    }

    public void apagar() {
        int codApagar;
        try {
            codApagar = recebe.readInt();
            System.out.println("Apagando carro com o código: " + codApagar);
            CarroDAO dao = new CarroDAO();
            verifica = dao.apagarCod(codApagar);
            envia.writeBoolean(verifica);
            System.out.println("Confirmação enviada para cliente!");
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
