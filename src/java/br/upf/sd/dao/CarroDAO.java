package br.upf.sd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import br.upf.sd.factory.ConnectionFactory;
import br.upf.sd.model.Carro;


public class CarroDAO extends ConnectionFactory {

    private static CarroDAO instance;
    
    //metodo responsável por criar uma instancia da classe CarroDAO (Singleton)
    public static CarroDAO getInstance() {
        if (instance == null) {
            instance = new CarroDAO();
        }
        return instance;
    }


    /**
     * metodo responsavel por listar todos os dados 
     * da tabela carro da bd trabalhosd,
     * ordenados por codigo
     * @author fabricio
     * @return ArrayList
     */
    public ArrayList<Carro> listarTodos() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        ArrayList<Carro> carros = new ArrayList();
        
        conexao = abrirConexao();
        
        try {
            sql = "select * from carros order by codigo";
            pstmt = conexao.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                Carro car = new Carro(); 
                car.setCodigo(rs.getInt("codigo"));
                car.setMarca(rs.getString("marca"));
                car.setModelo(rs.getString("modelo"));
                car.setAno(rs.getInt("ano"));
                car.setPotencia(rs.getFloat("potencia"));
                car.setCarga(rs.getFloat("carga"));
                car.setComplemento(rs.getString("complemento"));
                carros.add(car);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar todos os carros: " + e);
            e.printStackTrace();
        } finally {
            fecharConexao(conexao, pstmt, rs);
        }
        return carros;

    }
    
    /**
     * metodo responsavel por adicionoar um novo carro
     * na tabela carro da bd trabalhosd
     * @author fabricio
     * @param carros 
     */
    public void adicionarCarro(Carro carros) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        
        conexao = abrirConexao();
       
        try {
            sql = "INSERT INTO carros (codigo,marca,modelo,ano,potencia,carga,complemento) VALUES (?,?,?,?,?,?,?)";
            pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, carros.getCodigo());
            pstmt.setString(2, carros.getMarca());
            pstmt.setString(3, carros.getModelo());
            pstmt.setInt(4, carros.getAno());
            pstmt.setFloat(5, carros.getPotencia());
            pstmt.setFloat(6, carros.getCarga());
            pstmt.setString(7, carros.getComplemento());
            
            rs = pstmt.executeQuery();
            
            
        } catch (Exception e) {
            System.out.println("Erro ao listar todos os carros: " + e);
            e.printStackTrace();
        } finally {
            fecharConexao(conexao, pstmt, rs);
        }

    }
    
    
    //metodo para listar carros por id
    //select * from carros where codigo=" + codigo
    //metodo para alterar um carro
    //metodo para apagar um carro
    //@
}
