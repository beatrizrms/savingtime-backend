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

import com.savingtime.model.Atendimento;
import com.savingtime.model.Categoria;
import com.savingtime.service.ServiceAtendimento;
import com.savingtime.service.ServiceCategoria;
import com.savingtime.utils.RestReturn;
import static com.savingtime.utils.Utilidades.getAllStatusAtendimento;

@Path("/atendimentorest") 
public class AtendimentoRest {
	

	@POST
	@Path("/efetuar/checkin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response efetuarCheckIn(Atendimento atendimento) {
		int retorno = 0;	
		ServiceAtendimento serviceAtendimento = new ServiceAtendimento();
		try {
			 retorno = serviceAtendimento.efetuarCheckIn(atendimento);

			// -1 equals error
			if(retorno == -1) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(new RestReturn(Status.OK, retorno ,"Check-in efetuado com sucesso")).build();
	}

	
	
	
	@GET
	@Path("/iniciar/atendimento/{codigo}/{mesa}/{codCategoria}/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response iniciarAtendimento(@PathParam("codigo")int codigo,@PathParam("mesa")int mesa, @PathParam("codCategoria")int codCategoria, @PathParam("data")String data) {
		
		ServiceAtendimento serviceAtendimento = new ServiceAtendimento();

		try {
			int retorno = serviceAtendimento.iniciarAtendimento(codigo,mesa,codCategoria, data);

			if(retorno == -1) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			/*} else if(retorno == 0) {
				return Response.ok(new RestReturn(Status.CONFLICT, null, "ALGUM TRATAMENTO")).build();*/
			} else if(retorno == 404) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Atendimento não encontrado")).build();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(new RestReturn(Status.OK, null, "Atendimento iniciado com sucesso!")).build();
	
	}
	
	
	@GET
	@Path("/efetuar/checkout/{codigo}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response efetuarCheckOut(@PathParam("codigo") int codigo, @PathParam("status") String status) {
		ServiceAtendimento serviceAtendimento = new ServiceAtendimento();
		
		System.out.println(codigo+ "  ----  "+status);

		try {
			int retorno = serviceAtendimento.efetuarCheckOut(codigo, status);
			if(retorno == -1) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			/*} else if(retorno == 0) {
				return Response.ok(new RestReturn(Status.CONFLICT, null, "ALGUM TRATAMENTO")).build();*/
			} else if(retorno == 404) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Atendimento não encontrado")).build();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(new RestReturn(Status.OK, null, "Check-out efetuado com sucesso!")).build();
	}
	

	
	
	@GET
	@Path("/fila/espera")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response filaEspera() throws SQLException {
		
		ServiceAtendimento serviceAtendimento = new ServiceAtendimento();		
		List<Atendimento> listatend;
		
		try {
			listatend = serviceAtendimento.filaEspera();
			if(listatend == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Não há fila de espera.")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listatend, null)).build();

		
	
	}
	
	@GET
	@Path("/lista/atendimento")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listaAtendimento() throws SQLException {
		
		ServiceAtendimento serviceAtendimento = new ServiceAtendimento();		
		List<Atendimento> listatend;
		
		try {
			listatend = serviceAtendimento.listaAtendimento();
			if(listatend == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Não há atendimentos no momento.")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listatend, null)).build();

	}

	
	
	@GET
	@Path("/cancelar/checkin/{codigo}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancelarCheckIn(@PathParam("codigo")int codigo, @PathParam("status") String status) {
		
		
		ServiceAtendimento serviceAtendimento = new ServiceAtendimento();
		try {
			int retorno = serviceAtendimento.cancelarCheckIn(codigo,status);
			
			// -1 equals error
			if(retorno == -1) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(new RestReturn(Status.OK, null,"Atendimento cancelado com sucesso")).build();
	
	}
	
	
	//GET PARA LISTA DE STATUS DE ATENDIMENTO
	
	@GET
	@Path("/get/status/atendimento")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getStatusAtendimento() {
		return getAllStatusAtendimento();
	
	}
	
	//GET PARA LISTA DE TIPOS DE EVENTO
	
	@GET
	@Path("/get/tipo/evento/{qtdpessoas}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatusAtendimento(@PathParam("qtdpessoas") int qtdpessoas) {
		ServiceCategoria servicecategoria = new ServiceCategoria();
		List<Categoria> retorno;
		try{
			retorno = servicecategoria.getListaCategorias(qtdpessoas);
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(new RestReturn(Status.OK, retorno, null)).build();
	
	}


}
