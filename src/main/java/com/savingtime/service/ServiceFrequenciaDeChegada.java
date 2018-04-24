package com.savingtime.service;

import java.sql.SQLException;
import com.savingtime.dao.DaoFrequenciaDeChegada;
import com.savingtime.model.FrequenciaDeChegada;


public class ServiceFrequenciaDeChegada {
	
	public FrequenciaDeChegada chegadaDeClientes(int diaSemana,FrequenciaDeChegada freqCheg ) throws SQLException{
		DaoFrequenciaDeChegada daoFreqCheg = new DaoFrequenciaDeChegada();
		return 	daoFreqCheg.chegadaDeClientes(diaSemana,freqCheg);
	}
	
	public int getQtdSemana(String data) throws SQLException{
		DaoFrequenciaDeChegada daoFreqCheg = new DaoFrequenciaDeChegada();
		return 	daoFreqCheg.getQtdSemana(data);
	}

	public FrequenciaDeChegada chegadaDeClientesAtual(String dataHoje, FrequenciaDeChegada freqCheg) throws SQLException {
		DaoFrequenciaDeChegada daoFreqCheg = new DaoFrequenciaDeChegada();
		return 	daoFreqCheg.chegadaDeClientesAtual(dataHoje,freqCheg);
	}
	

}
