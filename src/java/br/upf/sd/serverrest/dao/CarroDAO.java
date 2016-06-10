/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Carro;

/**
 *
 * @author Luciano
 */
public class CarroDAO {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
    public CarroDAO()
    {
    
    }
    
    public boolean inserir(Carro carro)
    {
        String sql = "INSERT INTO carro(marca,modelo,ano,potencia,carga,complemento) VALUES(?,?,?,?,?,?)";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, carro.getMarca());
            pst.setString(2, carro.getModelo());
            pst.setInt(3, carro.getAno());
            pst.setFloat(4, carro.getPotencia());
            pst.setFloat(5, carro.getCarga());
            pst.setString(6, carro.getComplemento());
            if(pst.executeUpdate()>0)
            {
                retorno = true;
            }
                           
        } catch (SQLException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        
        return retorno;
    
    }
    public boolean atualizar(Carro carro)
    {
        String sql = "UPDATE carro SET marca=?,modelo=?,ano=?, potencia=?, carga=?, complemento=? where codigo=?";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, carro.getMarca());
            pst.setString(2, carro.getModelo());
            pst.setInt(3, carro.getAno());
            pst.setFloat(4, carro.getPotencia());
            pst.setFloat(5, carro.getCarga());
            pst.setString(6, carro.getComplemento());
            pst.setInt(7, carro.getCodigo());
            if(pst.executeUpdate()>0)
            {
                retorno = true;
            }
                
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        
        return retorno;
    
    }
    public boolean excluir(Carro carro)
    {
        String sql = "DELETE FROM carro where codigo=?";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setInt(1, carro.getCodigo());
            if(pst.executeUpdate()>0)
            {
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        
        return retorno;
    
    }
    
    public List<Carro> listar()
    {
         String sql = "SELECT * FROM carro";
        List<Carro> retorno = new ArrayList<Carro>();
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
           
            
            ResultSet res = pst.executeQuery();
            while(res.next())
            {
                Carro item = new Carro();
                item.setCodigo(res.getInt("codigo"));
                item.setMarca(res.getString("marca"));
                item.setModelo(res.getString("modelo"));
                item.setAno(res.getInt("ano"));
                item.setPotencia(res.getFloat("potencia"));
                item.setCarga(res.getFloat("carga"));
                item.setComplemento(res.getString("complemento"));
                
                retorno.add(item);
            }
               
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return retorno;
    
    
    }
    public Carro buscar(Carro carro)
    {
        String sql = "SELECT * FROM carro where codigo=?";
        Carro retorno = null;
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
           
            pst.setInt(1, carro.getCodigo());
            ResultSet res = pst.executeQuery();
            
            if(res.next())
                
            {
                
                retorno = new Carro();
                retorno.setCodigo(res.getInt("codigo"));
                retorno.setMarca(res.getString("marca"));
                retorno.setModelo(res.getString("modelo"));
                retorno.setAno(res.getInt("ano"));
                retorno.setPotencia(res.getFloat("potencia"));
                retorno.setCarga(res.getFloat("carga"));
                retorno.setComplemento(res.getString("complemento"));
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return retorno;
   
    }

}
