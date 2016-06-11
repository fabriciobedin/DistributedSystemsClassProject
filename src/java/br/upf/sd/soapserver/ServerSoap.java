/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.soapserver;

import br.upf.sd.model.Carro;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Fabricio
 */
@WebService(name = "ServerSoap", serviceName = "ServerSoapService")
public class ServerSoap {

    private Connection conn = null;

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    private static Carro populaCarro(ResultSet rs) throws SQLException {
        Carro carro = new Carro();
        
        if (rs.getInt("codigo") > 0) {
            carro.setCodigo(rs.getInt("codigo"));
            carro.setMarca(rs.getString("marca"));
            carro.setModelo(rs.getString("modelo"));
            carro.setAno(rs.getInt("ano"));
            carro.setPotencia(rs.getFloat("potencia"));
            carro.setCarga(rs.getFloat("carga"));
            carro.setComplemento(rs.getString("complemento"));
        }

        return carro;
    }

    /**
     * @return
     */
    private boolean criarConexao() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            return false;
        }

        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://ec2-52-67-44-247.sa-east-1.compute.amazonaws.com:5432/trabalhosd",
                    "postgres",
                    ""
            );

        } catch (SQLException e) {
            return false;
        }

        return conn != null;
    }

    /**
     * @param codigo
     * @param marca
     * @param modelo
     * @param ano
     * @param potencia
     * @param carga
     * @param complemento
     * @return
     */
    private String montarInsert(int codigo, String marca, String modelo, int ano, float potencia, float carga, String complemento) {
        List<String> campos = new ArrayList<>();
        List<String> values = new ArrayList<>();

        if (codigo > 0) {
            campos.add("codigo");
            values.add(Integer.toString(codigo));
        }

        if (marca != null && marca != "") {
            campos.add("marca");
            values.add("'" + marca + "'");
        }

        if (modelo != null && modelo != "") {
            campos.add("modelo");
            values.add("'" + modelo + "'");
        }

        if (ano > 0) {
            campos.add("ano");
            values.add(Integer.toString(ano));
        }

        if (potencia > 0) {
            campos.add("potencia");
            values.add(Float.toString(potencia));
        }

        if (carga > 0) {
            campos.add("carga");
            values.add(Float.toString(carga));
        }

        if (complemento != null && complemento != "") {
            campos.add("complemento");
            values.add("'" + complemento + "'");
        }

        String strSql;
        strSql = "INSERT INTO carro (" + String.join(", ", campos) + ") "
                + "VALUES (" + String.join(", ", values) + ")";

        return strSql;
    }

    /**
     * @param codigo
     * @param marca
     * @param modelo
     * @param ano
     * @param potencia
     * @param carga
     * @param complemento
     * @return
     */
    private String montarUpdate(int codigo, String marca, String modelo, int ano, float potencia, float carga, String complemento) {
        List<String> operacoes = new ArrayList<>();

        if (marca != null) {
            operacoes.add("marca = '" + marca + "'");
        }

        if (modelo != null) {
            operacoes.add("modelo = '" + marca + "'");
        }

        operacoes.add("ano = " + ((ano == -1) ? "NULL" : Integer.toString(ano)));

        operacoes.add("potencia = " + ((potencia == -1) ? "NULL" : Float.toString(potencia)));

        operacoes.add("carga = " + ((carga == -1) ? "NULL" : Float.toString(carga)));

        if (complemento != null) {
            operacoes.add("complemento = '" + complemento + "'");
        }

        if (!operacoes.isEmpty()) {
            return "UPDATE carro SET " + String.join(", ", operacoes) + " WHERE codigo = " + Integer.toString(codigo);
        }

        return "-1";
    }

    /**
     * @param codigo
     * @return
     */
    @WebMethod(operationName = "Consulta")
    public Carro Consulta(@WebParam(name = "codigo") int codigo) {

        Carro carro = new Carro();

        if (this.criarConexao()) {

            try {

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM carro WHERE codigo = " + codigo);
                while (rs.next()) {
                    carro = populaCarro(rs);
                }
                rs.close();
                st.close();

            } catch (SQLException e) {
                //TODO implementar SOAPFault
            }

        } else {
            //TODO implementar SOAPFault
        }
        return carro;
    }

    /**
     * @param codigo
     * @param marca
     * @param modelo
     * @param ano
     * @param potencia
     * @param carga
     * @param complemento
     * @return
     */
    @WebMethod(operationName = "Adiciona")
    public int Adiciona(@WebParam(name = "codigo") int codigo, @WebParam(name = "marca") String marca, @WebParam(name = "modelo") String modelo, @WebParam(name = "ano") int ano, @WebParam(name = "potencia") float potencia, @WebParam(name = "carga") float carga, @WebParam(name = "complemento") String complemento) {
        if (codigo > 0) {
            if (criarConexao()) {
                try {
                    Statement st = conn.createStatement();
                    st.executeUpdate(montarInsert(codigo, marca, modelo, ano, potencia, carga, complemento));
                    return 0;
                } catch (SQLException e) {
                    if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            }
        }

        return 3;
    }

    /**
     * @param codigo
     * @return
     */
    @WebMethod(operationName = "Excluir")
    public int Excluir(@WebParam(name = "codigo") int codigo) {
        if (codigo > 0) {
            if (criarConexao()) {
                try {
                    Statement st = conn.createStatement();
                    st.executeUpdate("DELETE FROM carro WHERE codigo = " + Integer.toString(codigo));
                    return st.getUpdateCount();
                } catch (SQLException e) {
                    return -1;
                }
            }
        }

        return -1;
    }

    /**
     * @param codigo
     * @param marca
     * @param modelo
     * @param ano
     * @param potencia
     * @param carga
     * @param complemento
     * @return
     */
    @WebMethod(operationName = "Altera")
    public int Altera(@WebParam(name = "codigo") int codigo, @WebParam(name = "marca") String marca, @WebParam(name = "modelo") String modelo, @WebParam(name = "ano") int ano, @WebParam(name = "potencia") float potencia, @WebParam(name = "carga") float carga, @WebParam(name = "complemento") String complemento) {
        if (codigo > 0) {
            if (criarConexao()) {
                try {
                    String sql = montarUpdate(codigo, marca, modelo, ano, potencia, carga, complemento);
                    if (sql != "") {

                        Statement st = conn.createStatement();
                        st.executeUpdate(sql);
                        return st.getUpdateCount();

                    }
                } catch (SQLException e) {
                    return -1;
                }
            }
        }

        return -1;
    }

    /**
     * @param ano
     * @param modelo
     * @return
     */
    @WebMethod(operationName = "ListaAnoModelo")
    public List<Carro> ListaAnoModelo(@WebParam(name = "ano") int ano, @WebParam(name = "modelo") String modelo) {
        List<Carro> listCarros = new ArrayList<>();
        if (this.criarConexao()) {
            try {
                String sqlAno    = (ano == -1) ? "(ano IS NULL OR ano = '')" : "ano = " + Integer.toString(ano);
                String sqlModelo = ("".equals(modelo)) ? "(modelo IS NULL OR modelo = '')" : "modelo = '" + modelo + "'";
                
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM carro WHERE  " + sqlAno + " AND " + sqlModelo);
                while (rs.next()) {
                    listCarros.add(populaCarro(rs));
                }
                rs.close();
                st.close();

            } catch (SQLException e) {
                //TODO implementar SOAPFault
            }

        } else {
            //TODO implementar SOAPFault
        }

        return listCarros;
    }

}
