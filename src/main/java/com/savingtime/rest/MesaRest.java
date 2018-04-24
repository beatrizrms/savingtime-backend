package com.savingtime.rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONException;

import com.savingtime.model.Mesa;
import com.savingtime.service.ServiceMesa;
import com.savingtime.utils.RestReturn;
import static com.savingtime.utils.Utilidades.getAllStatusMesa;

@Path("/mesarest") 
public class MesaRest {

	@POST
	@Path("/cadastrar/mesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarMesas(Mesa mesa) throws JSONException {
		
		ServiceMesa servicemesas = new ServiceMesa();
		try {
			int retorno = servicemesas.cadastrarMesas(mesa);
			
			// -1 equals error
			if(retorno == -1) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}else
				if(retorno == 0){
					
					return Response.ok(new RestReturn(Status.CONFLICT, null,"Já exite uma mesa cadastrada com o"
																		+ " número informado, favor escolher outro.")).build();
				}
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(new RestReturn(Status.OK, null,"Mesa Cadastrada com sucesso")).build();
	}

		
	@POST
	@Path("/alterar/mesa")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterarMesas(Mesa mesa) throws SQLException {
		ServiceMesa serviceMesa = new ServiceMesa();
		
		try {
			int retorno = serviceMesa.alterarMesa(mesa);	
			if(retorno == -1) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			} else if(retorno == 0) {
				return Response.ok(new RestReturn(Status.CONFLICT, null, "O número da mesa já está em uso e não pode ser alterada.")).build();
			}else if(retorno == -2) {
				return Response.ok(new RestReturn(Status.CONFLICT, null, "Não é permitido alterar uma mesa com o status ocupado.")).build( );
			} else if(retorno == 404) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Mesa não encontrada")).build();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(new RestReturn(Status.OK, null, "Mesa alterada com sucesso")).build();
	}
	
	
	
	@GET
	@Path("/consultar/mesas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarTodasMesas() throws SQLException {
		
		ServiceMesa servicemesa = new ServiceMesa();
		List<Mesa> listmesa;
		
		try {
			listmesa = servicemesa.consultarTodasMesas();
			if(listmesa == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Não há mesas")).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listmesa, null)).build();
	}
	
	@GET
	@Path("/consultar/mesas/capacidade/{capacidade}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consultarMesasCapacidade(@PathParam("capacidade")int capacidade) throws SQLException {
		
		ServiceMesa serviceMesa = new ServiceMesa();
		
		List<Mesa> listmesa;
		
		try {
			listmesa = serviceMesa.consultarMesasCapacidade(capacidade);
			if(listmesa == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Não há mesas")).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listmesa, null)).build();
	}
	
	
	//API PARA CONSULTAR MESAS QUE SEJA IGUAL OU MAIOR A CAPACIDADE DO ATENDIMENTO PARA ATENDER O CLIENTE
	@GET
	@Path("/consultar/mesas/capacidades/{capacidade}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consultarMesasCapacidades(@PathParam("capacidade")int capacidade) throws SQLException {
		
		ServiceMesa serviceMesa = new ServiceMesa();
		List<Mesa> listmesa;
		
		try {
			listmesa = serviceMesa.consultarMesasCapacidades(capacidade);
			if(listmesa == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Não há mesas")).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listmesa, null)).build();
	}


	@GET
	@Path("/consultar/mesas/status/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consultarMesas(@PathParam("status")String status) throws SQLException {
		
		ServiceMesa serviceMesa = new ServiceMesa();
		
		List<Mesa> listmesa;
		
		try {
			listmesa = serviceMesa.consultarMesaStatus(status);
			if(listmesa == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Não há mesas")).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listmesa, null)).build();
	} 	 	
	
	@GET
	@Path("/get/status/mesa")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getStatusMesa() {
		return getAllStatusMesa();
	
	}	
	
}
