package br.upf.sd.udpserver;

import br.upf.sd.factory.ConexaoBanco;

import br.upf.sd.model.Carro;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrei Tremea Basso
 */

public class TiraFila extends Thread{
    private final Buffering queue;
    private final DatagramSocket socketserver;
    
    public TiraFila(Buffering q, DatagramSocket soc){
        queue = q;
        socketserver = soc;
    }

    @Override
    public void run(){
        String dadosvemcliente;
        DatagramPacket pacotes;
        byte[] msg;
        Carro carro;
        Carro carrorecebido;
        int acao;
        String retornadadoscliente = null;
        List<Carro> listacarro = null;
        
       
        
        while (true) {                
            try {    
              
                pacotes = queue.retira();
                System.out.println("Pacote retirado da fila " + Servidor.getDataHora());
              
                dadosvemcliente = new String(pacotes.getData());
                dadosvemcliente = dadosvemcliente.replaceAll("\0", "");
                String[] parts = dadosvemcliente.split(":");

                //pega o codigo da acao vindo do cliente
                acao = Integer.parseInt(parts[0]);
                
                //pega dados do carro enviados do cliente
                carro = new Carro();
                
                if(!"null".equals(parts[1])){
                    carro.setCodigo(Integer.parseInt(parts[1]));
                }
                
                if(!"null".equals(parts[2])){
                    carro.setMarca(parts[2]);
                }
                
                if(!"null".equals(parts[3])){
                    carro.setModelo(parts[3]);
                }
                
                if(!"null".equals(parts[4])){
                    carro.setAno(Integer.parseInt(parts[4]));
                }
                
                if(!"null".equals(parts[5])){
                    carro.setPotencia(Float.parseFloat(parts[5]));
                }
                
                if(!"null".equals(parts[6])){
                    carro.setCarga(Float.parseFloat(parts[6]));
                }
                
                if(!"null".equals(parts[7])){
                    carro.setComplemento(parts[7]);
                }
                
                //Faz a ação comforme o que cliente solicitou
                
                switch (acao) {
                    case 1:
                        try {
                            addCarro(carro);
                            retornadadoscliente = "Carro inserido com sucesso!";
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage() + Servidor.getDataHora());
                            if(ex.getErrorCode() == 0){
                                retornadadoscliente = "Não foi possível inserir o carro: Código já está em uso";
                            }else{
                                retornadadoscliente = "Carro não inserido";
                            }
                            
                        }   break;
                    case 2:
                        try {
                            carrorecebido = concarro(carro.getCodigo());
                            System.out.println("Cliente Consultou um carro");
                            retornadadoscliente =  "Codigo: " + carrorecebido.getCodigo() + " Marca: " +
                                    carrorecebido.getMarca() + " Modelo:" +
                                    carrorecebido.getModelo() + " Ano:" +
                                    carrorecebido.getAno() + " Potencia:" +
                                    carrorecebido.getPotencia() + " Carga:" +
                                    carrorecebido.getCarga() + " Complemento:" +
                                    carrorecebido.getComplemento();
                        } catch (SQLException ex) {
                            System.out.println(Servidor.getDataHora() + ex.getMessage());
                            retornadadoscliente = "Consulta não realizada!!!";
                        }   break;
                    case 3:
                        try {
                            alteracarro(carro);
                            retornadadoscliente = "Carro alterado com sucesso!";
                        } catch (SQLException ex) {
                            System.out.println(Servidor.getDataHora() + ex.getMessage());
                            retornadadoscliente = "Não foi possível alterar o carro";
                        }   break;
                    case 4:
                        try {
                            deletarcarro(carro);
                            retornadadoscliente = "Carro deletado com sucesso!";
                        } catch (SQLException ex) {
                            System.out.println(Servidor.getDataHora() + ex.getMessage());
                            retornadadoscliente = "Não foi possível deletar o carro";
                        }   break;
                    case 5:
                        try {
                            listacarro = listaranomodelo(carro.getAno(), carro.getModelo());
                             retornadadoscliente = listacarro.toString();
                            
                        } catch (SQLException ex) {
                            System.out.println(Servidor.getDataHora() + ex.getMessage());
                            retornadadoscliente = "Não foi possível listar os dados";
                        }
                        break;
                    case 6:
                        try {
                            listacarro = listarcarro();
                            retornadadoscliente = listacarro.toString();
                            
                        } catch (SQLException ex) {
                            System.out.println(Servidor.getDataHora() + ex.getMessage());
                            retornadadoscliente = "Não foi possível listar os dados";
                        }   break;
                       
                }

                System.out.println(retornadadoscliente);
               
                msg = retornadadoscliente.getBytes();
                pacotes = new DatagramPacket(msg, msg.length, pacotes.getAddress(), pacotes.getPort());
                try {
                    socketserver.send(pacotes);
                    System.out.println("Servidor Respondeu para o Ip " + pacotes.getAddress() + " Porta: " + pacotes.getPort() + " " + Servidor.getDataHora());
                } catch (IOException ex) {
                    //
                }
                
            } catch (InterruptedException ex) {
                //
            }
        }
    }
    
    //Metodos para o banco
     public static void addCarro(Carro carro) throws SQLException{
                                          
            String sql = "insert into Carro (codigo, marca, modelo, ano, potencia, carga, complemento) values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = ConexaoBanco.getPreparedStatement(sql);
            stmt.setInt(1, carro.getCodigo());
            stmt.setString(2, carro.getMarca());
            stmt.setString(3, carro.getModelo());
            stmt.setInt(4, carro.getAno());
            stmt.setFloat(5, carro.getPotencia());
            stmt.setFloat(6, carro.getCarga());
            stmt.setString(7, carro.getComplemento());
            stmt.executeUpdate();

        
    }
     
    
           
    public static Carro concarro(Integer cod) throws SQLException{
        Carro car = new Carro();
        Statement stmt = ConexaoBanco.getStatement();
        ResultSet resultado = stmt.executeQuery("select * from Carro where codigo = " + cod);

        resultado.next();
        if(!resultado.wasNull()){
            car.setCodigo(resultado.getInt("codigo"));
            car.setMarca(resultado.getString("marca"));
            car.setModelo(resultado.getString("modelo"));
            car.setAno(resultado.getInt("ano"));
            car.setPotencia(resultado.getFloat("potencia"));
            car.setCarga(resultado.getFloat("carga"));
            car.setComplemento(resultado.getString("complemento"));
        }        
        return car;
    }
    
    
    
    public static List<Carro> listaranomodelo(Integer ano, String modelo) throws SQLException{
        Carro car = new Carro();
        List<Carro> lista = new ArrayList();
        Statement stmt = ConexaoBanco.getStatement();
        ResultSet resultado = stmt.executeQuery("select * from Carro where ano = " + ano + " and modelo = '" + modelo + "'");
        
        while(resultado.next()){
            car.setCodigo(resultado.getInt("codigo"));
            car.setMarca(resultado.getString("marca"));
            car.setModelo(resultado.getString("modelo"));
            car.setAno(resultado.getInt("ano"));
            car.setPotencia(resultado.getFloat("potencia"));
            car.setCarga(resultado.getFloat("carga"));
            car.setComplemento(resultado.getString("complemento"));
            
            lista.add(car);
        }
        
        return lista;
    }
        
    
    
    public static List<Carro> listarcarro() throws SQLException{
        
        List<Carro> lista = new ArrayList();
        Statement stmt = ConexaoBanco.getStatement();
        ResultSet resultado = stmt.executeQuery("select * from Carro");
        
        while(resultado.next()){
            Carro car = new Carro();
            car.setCodigo(resultado.getInt("codigo"));
            car.setMarca(resultado.getString("marca"));
            car.setModelo(resultado.getString("modelo"));
            car.setAno(resultado.getInt("ano"));
            car.setPotencia(resultado.getFloat("potencia"));
            car.setCarga(resultado.getFloat("carga"));
            car.setComplemento(resultado.getString("complemento"));
            
            lista.add(car);
           
        }
        
       
        return lista;
    }
    
    
        public static void deletarcarro(Carro carro) throws SQLException{
            String sql = "delete from Carro where codigo = ?";
            PreparedStatement stmt = ConexaoBanco.getPreparedStatement(sql);
            stmt.setInt(1, carro.getCodigo());
            stmt.executeUpdate();
            
    }
    
    
    
    public static void alteracarro(Carro carro) throws SQLException{
            //                                        
            String sql = "update Carro set marca = ?, modelo = ?, ano = ?, potencia = ?, carga = ?, complemento = ? where codigo = ?";
            PreparedStatement stmt = ConexaoBanco.getPreparedStatement(sql);
            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setInt(3, carro.getAno());
            stmt.setFloat(4, carro.getPotencia());
            stmt.setFloat(5, carro.getCarga());
            stmt.setString(6, carro.getComplemento());
            stmt.setInt(7, carro.getCodigo());
            stmt.executeUpdate();

    }
}
