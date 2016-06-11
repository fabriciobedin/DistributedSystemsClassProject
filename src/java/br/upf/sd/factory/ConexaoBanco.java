/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.upf.sd.factory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Luciano
 */
public class ConexaoBanco {
    private static final String banco = 
            "jdbc:postgresql://ec2-52-67-44-247.sa-east-1.compute.amazonaws.com:5432/trabalhosd";
    /**  
     * O atributo driver representa a classe do Driver JDBC que será usada na 
     * conexão. Quando se utiliza outros bancos usa-se a classe apropriada a 
     * cada banco
     */
    private static final String driver = 
            "org.postgresql.Driver";
    /**
     * Os atributos usuario e senha representam usuario e senha do 
     * SGBD a ser usado na conexão
     */
    private static final String usuario = "postgres";
    private static final String senha = "";  
    /**
     * O atributo conexao representa um objeto que 
 contém a conexão com o banco de dados em si
     */
    private static Connection conexao = null;
    
    /**
     * Metodo que retorna uma conexão com o banco de dados
     * @return objeto java.sql.Connection
     */
    public static Connection getConexao(){
        // primeiro testo se o objeto conexao não foi inicializado
        if (conexao == null){
            try {
                // defino a classe do driver a ser usado
                Class.forName(driver);
                // criação da conexão com o BD
                conexao = DriverManager.getConnection(banco, usuario, senha);
            } catch (ClassNotFoundException ex) {
                System.out.println("Não encontrou o driver");
                ex.printStackTrace();
            } catch (SQLException es) {
                System.out.println("Erro ao conectar: "+es.getMessage());
                es.printStackTrace();
            }
        }
        return conexao;
    }
    /**
     * Método que recebe um comando SQL para ser executado
     * @param sql
     * @return um objeto java.sql.PreparedStatement
     */
    public static PreparedStatement getPreparedStatement(String sql){
        // testo se a conexão já foi criada
        if (conexao == null){
            // cria a conexão
            conexao = getConexao();
        }
        try {
            // retorna um objeto java.sql.PreparedStatement
            return conexao.prepareStatement(sql);
        } catch (SQLException e){
            System.out.println("Erro de sql: "+
                    e.getMessage());
        }
        return null;
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
  
    
    /**
     * metodo responsavel por fechar a conexao com o banco
     * @author Fabricio Bedin
     * @param conexao
     * @param pstmt
     */
    public void fecharConexao(Connection conexao, PreparedStatement pstmt) {

        try {

            if (conexao != null) {
                conexao.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
         
        } catch (Exception e) {
            System.out.println("Erro ao fechar conexao com o banco: "+ e);
        }
    }
    
}
