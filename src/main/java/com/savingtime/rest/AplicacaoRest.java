package com.savingtime.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest/")
public class AplicacaoRest extends Application {
	@Override
	public Set<Class<?>> getClasses(){
		final Set<Class<?>> classes = new HashSet<Class<?>>(); 
		classes.add(com.savingtime.rest.AtendimentoRest.class);
		classes.add(com.savingtime.rest.DisponibilidadeRest.class);
		classes.add(com.savingtime.rest.EstabelecimentoRest.class);
		classes.add(com.savingtime.rest.MesaRest.class);
		classes.add(com.savingtime.rest.RelatorioRest.class);
		classes.add(com.savingtime.rest.ReservaRest.class);
	
		return classes;	
		
	}

}
