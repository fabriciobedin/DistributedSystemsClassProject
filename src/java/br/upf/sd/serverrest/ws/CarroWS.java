/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dao.CarroDAO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import modelo.Carro;

/**
 * REST Web Service
 *
 * @author Luciano
 */
@Path("Carro")
public class CarroWS {

    @Context
    private UriInfo context;

    public CarroWS() {
    }

       
    @GET
    @Produces("application/json")
    @Path("Carro/get/{codigo}")
    public String getCarro(@PathParam("codigo") int codigo)
    {
        Carro u = new Carro();
        u.setCodigo(codigo);
        
        CarroDAO dao = new CarroDAO();
        u = dao.buscar(u);
        
        Gson g = new Gson();
        return g.toJson(u);
    }
    
    @GET
    @Produces("application/json")
    @Path("Carro/list")
    public String listCarro()
    {
        List<Carro> lista;
        
        CarroDAO dao = new CarroDAO();
        lista = dao.listar();
        
        Gson g = new Gson();
        return g.toJson(lista);
    }
    
    @PUT
    @Consumes("application/json")
    @Path("Carro/atualizar")
    public String atualizar (String content) {
    Gson g = new Gson();
        Carro u = (Carro) g.fromJson(content, Carro.class);
        
        CarroDAO dao = new CarroDAO();
        dao.atualizar(u);
        return "Alterado!";
    }
    
    
    @POST
    @Consumes({"application/json"})
    @Produces("application/json")
    @Path("Carro/inserir")
    public String inserir (String content) {
        
        Gson g = new Gson();
        Carro u = (Carro) g.fromJson(content, Carro.class);
        
        CarroDAO dao = new CarroDAO();
        dao.inserir(u);
        return "Cadastrado!";
    }
    
    
    
    @DELETE
    @Path("Carro/excluir/{codigo}")
    @Consumes("application/json")
    public String excluir(@PathParam("codigo") int codigo)
    {
        Carro u = new Carro();
        u.setCodigo(codigo);
        
        CarroDAO dao = new CarroDAO();
        dao.excluir(u);
        return "Excluido";
    }
    
}
