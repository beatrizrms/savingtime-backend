package com.savingtime.rest;

import java.sql.SQLException;
import java.text.ParseException;
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

import com.savingtime.model.Reserva;
import com.savingtime.service.ServiceReserva;
import com.savingtime.utils.RestReturn;

@Path("/reservarest") 
public class ReservaRest {
	
	@POST
	@Path("/cadastrar/reserva")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrarReserva(Reserva reserva) throws ParseException {
		
		ServiceReserva serviceReserva = new ServiceReserva();
		try {
			int retorno = serviceReserva.cadastrarReserva(reserva);
			
			// -1 equals error
			if(retorno == -1) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}else
				if(retorno == 0){
					return Response.ok(new RestReturn(Status.NOT_ACCEPTABLE, null,"Data e/ou hora informada deve ser igual ou superior a data/hora de hoje.")).build();
	
				}
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(new RestReturn(Status.OK, null,"Reserva Cadastrada com sucesso")).build();

	
	}
	
	//Somente muda o status para cancelado
	@GET
	@Path("/cancelar/reserva/{codigo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelarReserva(@PathParam("codigo") int codReserva) {
		
		ServiceReserva serviceReserva = new ServiceReserva();
		try {
			int retorno = serviceReserva.cancelarReserva(codReserva);

			
			// -1 equals error
			if(retorno == -1) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(new RestReturn(Status.OK, null,"Reserva Cancelada com sucesso")).build();

				
	}

	
	// Altera��o de reservas
	@POST
	@Path("/alterar/reserva")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterarReserva(Reserva reserva) {
		
		ServiceReserva serviceReserva = new ServiceReserva();
		
		try {
			int retorno = serviceReserva.alterarReserva(reserva);	
			if(retorno == -1) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			/*} else if(retorno == 0) {
				return Response.ok(new RestReturn(Status.CONFLICT, null, "Não é possivel alterar a reserva(retorno do algoritmo preditivo)")).build();*/
			} else if(retorno == 404) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Reserva não encontrada")).build();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(new RestReturn(Status.OK, null, "Reserva alterada com sucesso")).build();

			
	}	
		
	
	 //Consulta de reservas por intervalo de data
	@GET
	@Path("/consultar/reserva/data/{datainicio}/{datafim}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	
	public Response consultarReservasData(@PathParam("datainicio")String dataInicio, @PathParam("datafim")String dataFim) throws SQLException {
		ServiceReserva serviceReserva = new ServiceReserva();
		List<Reserva> listreserva;
				
		try {
			listreserva = serviceReserva.consultarReservaData(dataInicio, dataFim);
			if(listreserva == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Não há reservas no intervalo de datas informado.")).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listreserva, null)).build();
		
	}

	//Consulta de reserva por cpf
	@GET
	@Path("/consultar/reserva/cpf/{cpf}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consultarReservasCpf(@PathParam("cpf") String cpf) throws SQLException {
		
		ServiceReserva serviceReserva = new ServiceReserva();
		List<Reserva> listreserva;
		
		try {
			listreserva = serviceReserva.consultarReservaCpf(cpf);
			if(listreserva == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Reserva não encontrada.")).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listreserva, null)).build();
		
	}
	
	// Consultar reserva por codigo
	
	@GET
	@Path("/consultar/reserva/codigo/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consultarReservaCodigo(@PathParam("codigo") int codigo) throws SQLException{
		
		ServiceReserva serviceReserva = new ServiceReserva();
		List<Reserva> listreserva;
		
		try {
			listreserva = serviceReserva.consultarReservaCodigo(codigo);
			if(listreserva == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Reserva não encontrada.")).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listreserva, null)).build();

	}
	
	
	//buscar por cpf ou po codigo
	@GET
	@Path("/consultar/reserva/checkin/{buscar}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response efetuarCheckInReserva(@PathParam("buscar") String buscar) {
		
		ServiceReserva serviceReserva = new ServiceReserva();
		List<Reserva> listreserva;
		
		try {
			listreserva = serviceReserva.consultarReservaCheckIn(buscar);
			if(listreserva == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Não há reservas agendadas para a data de hoje para o cliente solicitado.")).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listreserva, null)).build();
	}


	@GET
	@Path("/consultar/reservas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarTodasReservas() throws SQLException {
		// Chamar a service para consulta de todas reservas
		ServiceReserva serviceReserva = new ServiceReserva();
		List<Reserva> listreserva;
		
		try {
			listreserva = serviceReserva.consultarTodasReservas();
			if(listreserva == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Não há reservas.")).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listreserva, null)).build();
	}	
		
	@GET
	@Path("/obter/comprovante/{codReserva}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response obterComprovanteDaReserva(@PathParam("codReserva")int codReserva) {
		
		ServiceReserva serviceReserva = new ServiceReserva();
		List<Reserva> listreserva;
		
		try {
			listreserva = serviceReserva.obterComprovanteDaReserva(codReserva);
			
			if(listreserva.get(0).getComprovante()  == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Não há comprovante de pagamento da reserva.")).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, listreserva, null)).build();
	}


}
