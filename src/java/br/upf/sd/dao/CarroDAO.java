package br.upf.sd.dao;

import br.upf.sd.factory.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import br.upf.sd.factory.ConnectionFactory;
import br.upf.sd.model.Carro;
import java.sql.SQLException;

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
     * método responsével por listar todos os dados da tabela carro da bd
     * trabalhosd, ordenados por codigo
     *
     * @author Fabricio Bedin
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
     * método responsável por listar todos os dados da tabela carro da bd
     * trabalhosd, que possuam o codigo informado
     *
     * @author Fabricio Bedin
     * @param codigo
     * @return ArrayList
     */
    public Carro listarCodigo(Integer codigo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Carro car = new Carro();

        try {
            sql = "SELECT * FROM carro WHERE codigo = " + codigo;
            pstmt = conexao.prepareStatement(sql);
            rs = pstmt.executeQuery();

            car.setCodigo(rs.getInt("codigo"));
            car.setMarca(rs.getString("marca"));
            car.setModelo(rs.getString("modelo"));
            car.setAno(rs.getInt("ano"));
            car.setPotencia(rs.getFloat("potencia"));
            car.setCarga(rs.getFloat("carga"));
            car.setComplemento(rs.getString("complemento"));

        } catch (Exception e) {
            System.out.println("Erro ao listar todos os carros: " + e);
            e.printStackTrace();
        } finally {
            fecharConexao(conexao, pstmt, rs);
        }
        return car;

    }

    /**
     * método responsável por listar todos os dados da tabela carro da bd
     * trabalhosd, que possuam o ano e modelo informado
     *
     * @author Fabricio Bedin
     * @param ano
     * @param modelo
     * @return ArrayList
     */
    public ArrayList<Carro> listarAnoModelo(Integer ano, String modelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        ArrayList<Carro> carros = new ArrayList();
        try {
            //String sqlAno    = (ano == -1) ? "(ano IS NULL OR ano = '')" : "ano = " + Integer.toString(ano);
            //String sqlModelo = ("".equals(modelo)) ? "(modelo IS NULL OR modelo = '')" : "modelo = '" + modelo + "'";
            sql = "SELECT * FROM carro WHERE ano = " + ano + " AND modelo = " + modelo;
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
     * metodo responsavel por adicionoar um novo carro na tabela carro da bd
     * trabalhosd
     *
     * @author Fabricio Bedin
     * @param carro
     */
    public boolean adicionarCarro(Carro carro) {
        boolean retorno;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;

        conexao = abrirConexao();

        try {
            sql = "INSERT INTO carros (codigo,marca,modelo,ano,potencia,carga,complemento) VALUES (?,?,?,?,?,?,?)";
            pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, carro.getCodigo());
            pstmt.setString(2, carro.getMarca());
            pstmt.setString(3, carro.getModelo());
            pstmt.setInt(4, carro.getAno());
            pstmt.setFloat(5, carro.getPotencia());
            pstmt.setFloat(6, carro.getCarga());
            pstmt.setString(7, carro.getComplemento());

            rs = pstmt.executeQuery();
            retorno = true;

        } catch (Exception e) {
            System.out.println("Erro ao listar todos os carros: " + e);
            e.printStackTrace();
            retorno = false;
        } finally {
            fecharConexao(conexao, pstmt, rs);
        }
        return retorno;

    }

    /**
     * metodo responsavel por apagar um dado da tabela carro da bd trabalhosd
     * que possua o código informado
     *
     * @author Fabricio Bedin
     * @param codigo
     * @return boolean
     * @throws SQLException
     */
    public boolean apagarCarro(Integer codigo) throws SQLException {
        boolean retorno;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        ArrayList<Carro> carros = new ArrayList();
        try {
            sql = "DELETE FROM carro WHERE codigo = " + codigo;
            pstmt = conexao.prepareStatement(sql);
            rs = pstmt.executeQuery();
            retorno = true;

        } catch (Exception e) {
            System.out.println("Erro ao listar todos os carros: " + e);
            e.printStackTrace();
            retorno = false;
        } finally {
            fecharConexao(conexao, pstmt, rs);
        }
        return retorno;
    }
}
