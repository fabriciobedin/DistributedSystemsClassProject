/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.serversoap;

import br.upf.sd.model.Carro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
     * Web service operation
     *
     * @param codigo
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

    private static Carro populaCarro(ResultSet rs) throws SQLException {
        Carro carro = new Carro();

        carro.setCodigo(rs.getInt("codigo"));
        carro.setMarca(rs.getString("marca"));
        carro.setModelo(rs.getString("modelo"));
        carro.setAno(rs.getInt("ano"));
        carro.setPotencia(rs.getFloat("potencia"));
        carro.setCarga(rs.getFloat("carga"));
        carro.setComplemento(rs.getString("complemento"));

        return carro;
    }

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

}
