package com.savingtime.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.savingtime.model.Estabelecimento;
import com.savingtime.service.ServiceEstabelecimento;


@Path("/estabelecimentorest") 
public class EstabelecimentoRest {
	
	@GET
	@Path("/get/dados/estabelecimento")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Estabelecimento> consultarDadosEstabelecimento() {
		
		ServiceEstabelecimento estabelecimentoService = new ServiceEstabelecimento();
		return estabelecimentoService.consultarDadosEstabelecimento();
	
	}

}
