package com.savingtime.rest;

import java.util.ArrayList;
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

import com.savingtime.calculos.Algoritmo;
import com.savingtime.calculos.SimulacaoReserva;
import com.savingtime.calculos.TeoriaFilas;
import com.savingtime.model.DataComemorativa;
import com.savingtime.model.Disponibilidade;
import com.savingtime.model.Home;
import com.savingtime.model.Reserva;
import com.savingtime.service.ServiceAtendimento;
import com.savingtime.service.ServiceDataComemorativa;
import com.savingtime.service.ServiceMesa;
import com.savingtime.utils.RestReturn;

@Path("/disponibilidaderest") 
public class DisponibilidadeRest {
	
	@POST
	@Path("/disponibilidade/reserva")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response verificarDisponibilidade(Reserva reserva) {
			
		Algoritmo algoritmo = new Algoritmo();
		Disponibilidade disp = new Disponibilidade();
		List<Disponibilidade> listDisp = new ArrayList<Disponibilidade>();

		try {
			
			disp  = algoritmo.disponibilidadeReserva(reserva);

			listDisp.add(disp);
			
			if(disp == null) {
				return Response.ok(new RestReturn(Status.NOT_FOUND, null, "Data/hora inválida.")).build();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(new RestReturn(Status.OK, listDisp,"Dados referente a disponibilidade da reserva")).build();
	}


	@GET
	@Path("/data/comemorativa/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dataComemorativa(@PathParam("data") String data) {
		
		List<DataComemorativa> result = new ArrayList<DataComemorativa>();
		DataComemorativa dataComemorativa = new DataComemorativa();
		ServiceDataComemorativa  serviceDataCom = new ServiceDataComemorativa();				
		
		try {
			dataComemorativa = serviceDataCom.consultarDataComemorativa(data);
			result.add(dataComemorativa);
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, result, null)).build();
	}
	

	@GET
	@Path("/get/dados/home")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)

	public Response getHome(@PathParam("data") String data) {
		
		Algoritmo algoritmo = new Algoritmo();
		List<Home> retorno;
		
		try {
			
			retorno = algoritmo.getDadosHome();
			if(retorno == null) {
				return Response.ok(new RestReturn(Status.FORBIDDEN, null, "Faltam dados do estabelecimento")).build();
			}
			if(retorno.isEmpty()) {
				return Response.ok(new RestReturn(Status.FORBIDDEN, null, "Estabelecimento esta fechado hoje")).build();
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, retorno, null)).build();
	}

}
