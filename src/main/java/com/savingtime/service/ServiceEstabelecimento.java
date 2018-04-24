package com.savingtime.service;

import java.util.List;

import com.savingtime.dao.DaoEstabelecimento;
import com.savingtime.model.Estabelecimento;

public class ServiceEstabelecimento {

	public List<Estabelecimento> consultarDadosEstabelecimento(){
		DaoEstabelecimento daoEstabelecimento = new DaoEstabelecimento();
		return 	daoEstabelecimento.consultarDadosEstabelecimento();
	}
	
	public Estabelecimento consultarHorarioFuncionamento(int diaSemana,Estabelecimento estabelecimento){
		DaoEstabelecimento daoEstabelecimento = new DaoEstabelecimento();
		return 	daoEstabelecimento.consultarHorarioFuncionamento(diaSemana,estabelecimento);
	}

}
