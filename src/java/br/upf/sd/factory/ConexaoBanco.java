package br.upf.sd.factory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * 
 * @author Jaqson Dalbosco
 */
public class ConexaoBanco {



   static String driver = "org.postgresql.Driver";
   
    static Connection conexao = null;

    public static Connection getConexao(){
        try{
           if (conexao == null){
               Class.forName(driver);
               conexao = DriverManager.getConnection("jdbc:postgresql://ec2-52-67-44-247.sa-east-1.compute.amazonaws.com:5432/trabalhosd", "postgres", "");
           }
        }catch( ClassNotFoundException e1){
            System.out.println("Classe do driver não encontrada!");
            e1.printStackTrace();
        }catch( SQLException e2 ){
            System.out.println("Erro de conexão: "+e2.getMessage());
            e2.printStackTrace();
        }
        return conexao;
    }

    public static PreparedStatement getPreparedStatement(String sql){
        try {
            return getConexao().prepareStatement(sql);
        } catch (SQLException ex) {
            System.out.println("Erro ao preparar a instrução SQL!");
            ex.printStackTrace();
            return null;
        }
    }

    public static Statement getStatement(){
        try {
            return getConexao().createStatement();
        } catch (SQLException ex) {
            System.out.println("Erro ao criar Statement!");
            ex.printStackTrace();
            return null;
        }
    }

}
