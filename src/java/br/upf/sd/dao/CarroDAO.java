package br.upf.sd.dao;

import br.upf.sd.factory.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import br.upf.sd.factory.ConnectionFactory;
import br.upf.sd.factory.Conexao;
import br.upf.sd.model.Carro;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarroDAO extends ConnectionFactory {

   
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
            Logger.getLogger(br.upf.sd.dao.CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }finally{
            fecharConexao(null, pst, null);
            
        }
        
        return retorno;
    
    }
    public boolean atualizar(br.upf.sd.model.Carro carro)
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
            Logger.getLogger(br.upf.sd.dao.CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        
        return retorno;
    
    }
    public boolean excluir(br.upf.sd.model.Carro carro)
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
            Logger.getLogger(br.upf.sd.dao.CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        
        return retorno;
    
    }
    
    public List<br.upf.sd.model.Carro> listar()
    {
         String sql = "SELECT * FROM carro";
        List<br.upf.sd.model.Carro> retorno = new ArrayList<br.upf.sd.model.Carro>();
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
           
            
            ResultSet res = pst.executeQuery();
            while(res.next())
            {
                br.upf.sd.model.Carro item = new br.upf.sd.model.Carro();
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
            Logger.getLogger(br.upf.sd.dao.CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return retorno;
    
    
    }
    public br.upf.sd.model.Carro buscar(br.upf.sd.model.Carro carro)
    {
        String sql = "SELECT * FROM carro where codigo=?";
        br.upf.sd.model.Carro retorno = null;
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
           
            pst.setInt(1, carro.getCodigo());
            ResultSet res = pst.executeQuery();
            
            if(res.next())
                
                
            {
                
                retorno = new br.upf.sd.model.Carro();
                retorno.setCodigo(res.getInt("codigo"));
                retorno.setMarca(res.getString("marca"));
                retorno.setModelo(res.getString("modelo"));
                retorno.setAno(res.getInt("ano"));
                retorno.setPotencia(res.getFloat("potencia"));
                retorno.setCarga(res.getFloat("carga"));
                retorno.setComplemento(res.getString("complemento"));
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(br.upf.sd.dao.CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return retorno;
   
    }
    
//    
//    
//     
//    /**
//     * método responsével por listar todos os dados da tabela carro da bd
//     * trabalhosd, ordenados por codigo
//     *
//     * @author Fabricio Bedin
//     * @return ArrayList
//     */
//    public ArrayList<Carro> listarTodos() {
//        Connection conexao = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        String sql = null;
//        ArrayList<Carro> carros = new ArrayList();
//
//        conexao = abrirConexao();
//
//        try {
//            sql = "select * from carros order by codigo";
//            pstmt = conexao.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//
//                Carro car = new Carro();
//                car.setCodigo(rs.getInt("codigo"));
//                car.setMarca(rs.getString("marca"));
//                car.setModelo(rs.getString("modelo"));
//                car.setAno(rs.getInt("ano"));
//                car.setPotencia(rs.getFloat("potencia"));
//                car.setCarga(rs.getFloat("carga"));
//                car.setComplemento(rs.getString("complemento"));
//                carros.add(car);
//            }
//
//        } catch (Exception e) {
//            System.out.println("Erro ao listar todos os carros: " + e);
//            e.printStackTrace();
//        } finally {
//            fecharConexao(conexao, pstmt, rs);
//        }
//        return carros;
//
//    }
//
//    /**
//     * método responsável por listar todos os dados da tabela carro da bd
//     * trabalhosd, que possuam o codigo informado
//     *
//     * @author Fabricio Bedin
//     * @param codigo
//     * @return ArrayList
//     */
//    public Carro listarCodigo(Integer codigo) {
//        Connection conexao = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        String sql = null;
//        Carro car = new Carro();
//
//        try {
//            sql = "SELECT * FROM carro WHERE codigo = " + codigo;
//            pstmt = conexao.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            car.setCodigo(rs.getInt("codigo"));
//            car.setMarca(rs.getString("marca"));
//            car.setModelo(rs.getString("modelo"));
//            car.setAno(rs.getInt("ano"));
//            car.setPotencia(rs.getFloat("potencia"));
//            car.setCarga(rs.getFloat("carga"));
//            car.setComplemento(rs.getString("complemento"));
//
//        } catch (Exception e) {
//            System.out.println("Erro ao listar todos os carros: " + e);
//            e.printStackTrace();
//        } finally {
//            fecharConexao(conexao, pstmt, rs);
//        }
//        return car;
//
//    }
//
//    /**
//     * método responsável por listar todos os dados da tabela carro da bd
//     * trabalhosd, que possuam o ano e modelo informado
//     *
//     * @author Fabricio Bedin
//     * @param ano
//     * @param modelo
//     * @return ArrayList
//     */
//    public ArrayList<Carro> listarAnoModelo(Integer ano, String modelo) {
//        Connection conexao = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        String sql = null;
//        ArrayList<Carro> carros = new ArrayList();
//        try {
//            //String sqlAno    = (ano == -1) ? "(ano IS NULL OR ano = '')" : "ano = " + Integer.toString(ano);
//            //String sqlModelo = ("".equals(modelo)) ? "(modelo IS NULL OR modelo = '')" : "modelo = '" + modelo + "'";
//            sql = "SELECT * FROM carro WHERE ano = " + ano + " AND modelo = " + modelo;
//            pstmt = conexao.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//
//                Carro car = new Carro();
//                car.setCodigo(rs.getInt("codigo"));
//                car.setMarca(rs.getString("marca"));
//                car.setModelo(rs.getString("modelo"));
//                car.setAno(rs.getInt("ano"));
//                car.setPotencia(rs.getFloat("potencia"));
//                car.setCarga(rs.getFloat("carga"));
//                car.setComplemento(rs.getString("complemento"));
//                carros.add(car);
//            }
//
//        } catch (Exception e) {
//            System.out.println("Erro ao listar todos os carros: " + e);
//            e.printStackTrace();
//        } finally {
//            fecharConexao(conexao, pstmt, rs);
//        }
//        return carros;
//
//    }
//
//    /**
//     * metodo responsavel por adicionoar um novo carro na tabela carro da bd
//     * trabalhosd
//     *
//     * @author Fabricio Bedin
//     * @param carro
//     */
//    public boolean adicionarCarro(Carro carro) {
//        boolean retorno;
//        Connection conexao = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        String sql = null;
//
//        conexao = abrirConexao();
//
//        try {
//            sql = "INSERT INTO carros (codigo,marca,modelo,ano,potencia,carga,complemento) VALUES (?,?,?,?,?,?,?)";
//            pstmt = conexao.prepareStatement(sql);
//            pstmt.setInt(1, carro.getCodigo());
//            pstmt.setString(2, carro.getMarca());
//            pstmt.setString(3, carro.getModelo());
//            pstmt.setInt(4, carro.getAno());
//            pstmt.setFloat(5, carro.getPotencia());
//            pstmt.setFloat(6, carro.getCarga());
//            pstmt.setString(7, carro.getComplemento());
//
//            rs = pstmt.executeQuery();
//            retorno = true;
//
//        } catch (Exception e) {
//            System.out.println("Erro ao listar todos os carros: " + e);
//            e.printStackTrace();
//            retorno = false;
//        } finally {
//            fecharConexao(conexao, pstmt, rs);
//        }
//        return retorno;
//
//    }
//
//    /**
//     * metodo responsavel por apagar um dado da tabela carro da bd trabalhosd
//     * que possua o código informado
//     *
//     * @author Fabricio Bedin
//     * @param codigo
//     * @return boolean
//     * @throws SQLException
//     */
//    public boolean apagarCarro(Integer codigo) throws SQLException {
//        boolean retorno;
//        Connection conexao = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        String sql = null;
//        ArrayList<Carro> carros = new ArrayList();
//        try {
//            sql = "DELETE FROM carro WHERE codigo = " + codigo;
//            pstmt = conexao.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//            retorno = true;
//
//        } catch (Exception e) {
//            System.out.println("Erro ao listar todos os carros: " + e);
//            e.printStackTrace();
//            retorno = false;
//        } finally {
//            fecharConexao(conexao, pstmt, rs);
//        }
//        return retorno;
//    }
 
}
