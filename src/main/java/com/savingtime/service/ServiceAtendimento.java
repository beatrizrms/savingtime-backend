package com.savingtime.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.savingtime.dao.DaoAtendimento;
import com.savingtime.model.Atendimento;

import static com.savingtime.utils.Utilidades.ATIVA_LIVRE;
import static com.savingtime.utils.Utilidades.ATIVA_OCUPADA;
import static com.savingtime.utils.Utilidades.ESPERA;
import static com.savingtime.utils.Utilidades.minutesParseTime;
import static com.savingtime.utils.Utilidades.ATENDIMENTO;
import static com.savingtime.utils.Utilidades.retornarDiaSemana;
import static com.savingtime.utils.Utilidades.formatarDataBanco;





public class ServiceAtendimento {
	
	List<DateTime> listHora = new ArrayList<DateTime>();
	
	public ServiceAtendimento(){
		
	}
	
	public int  efetuarCheckIn(Atendimento atend) throws SQLException, ParseException{
		
		atend.setStatus(ESPERA);
		DaoAtendimento daoatend = new DaoAtendimento();
		return daoatend.efetuarCheckIn(atend);
	}
	
	
	public int iniciarAtendimento(int codigo, int mesa, int codCategoria, String data) throws ParseException, SQLException{
		double totalMinutos = tempoMedioAtendimentoCategoria(codCategoria , formatarDataBanco(data));
		long tmaAux = (long) totalMinutos;
		String tmaFormatado = minutesParseTime(tmaAux);
		DaoAtendimento daoatend = new DaoAtendimento();
		return daoatend.iniciarAtendimento(mesa, codigo,ATENDIMENTO,ATIVA_OCUPADA,tmaFormatado);
	}
	
	public int efetuarCheckOut(int codigo, String status) throws SQLException{						
		DaoAtendimento daoatend = new DaoAtendimento();
		return daoatend.efetuarCheckOut(codigo,status,ATIVA_LIVRE);	
	}
	
	public List<Atendimento> filaEspera() throws SQLException{
		DaoAtendimento daoatend = new DaoAtendimento();
		return daoatend.filaEspera(ESPERA);	
	}
	
	public int qtdFilaEspera() throws SQLException{
		DaoAtendimento daoatend = new DaoAtendimento();
		return daoatend.qtdFilaEspera(ESPERA);	
	}
	
	public List<Atendimento> listaAtendimento() throws SQLException{
		DaoAtendimento daoatend = new DaoAtendimento();
		return daoatend.listaAtendimento(ATENDIMENTO);	
	}
	
	public int cancelarCheckIn(int codigo, String status) throws SQLException{	
		DaoAtendimento daoatend = new DaoAtendimento();
		return daoatend.cancelarCheckIn(codigo,status);
	}
	
	public double tempoMedioDeAtendimento() throws SQLException, ParseException{	
		
		double tma = 0;
		
		for(int i=0; i < listHora.size(); i++){
			
			tma += listHora.get(i).getMinuteOfDay();
		}
		
		tma = tma /listHora.size();
		
		return tma;
	}
	
	public void listaTempoMedioAtendimento(int codCategoria, String dataReserva) throws SQLException, ParseException{	
		
		DaoAtendimento daoatend = new DaoAtendimento();
		listHora = daoatend.tempoMedioDeAtendimento(codCategoria, retornarDiaSemana(dataReserva));
		
	}
	
	public void listaPrevisaoCheckoutData(String data) throws SQLException, ParseException{	
		
		DaoAtendimento daoatend = new DaoAtendimento();
		listHora = daoatend.listaPrevisaoCheckoutData(data);
		
	}
	
	/**
	 * Retornar a media de tempo de atendimento de uma categoria em minutos
	 * @param nomeCategoria
	 * @return tempo em minutos
	 * @throws SQLException
	 * @throws ParseException 
	 */
	
	
	public double tempoMedioAtendimentoCategoria(int codCategoria, String dataReserva) throws SQLException, ParseException {
		
		
		listaTempoMedioAtendimento(codCategoria,dataReserva);
		
		double media = tempoMedioDeAtendimento();
		double desvPad = desvioPadraoAtendimentos(media);
		System.out.println("Tempo médio de atendimento da categoria: "+minutesParseTime((long)media));

		System.out.println("Desvio Padrao Categoria: "+desvPad);
		
		return media+desvPad;
	}
	
	
	
	public double mediaAtendimentosHoje(String data) throws SQLException, ParseException {
		
		listaPrevisaoCheckoutData(formatarDataBanco(data));

		double media = tempoMedioDeAtendimento();				
		return media;
	}
	
	
	public double desvioPadraoAtendimentos(double media) throws SQLException, ParseException{			
		
		if (listHora.size() == 1) {
			return 0.0;
		} else {
			double somatorio = 0l;
			for (int i = 0; i < listHora.size(); i++) {
				double result = listHora.get(i).getMinuteOfDay() - media;
				somatorio = somatorio + result * result;
			}
			
			double desvPadrao = Math.sqrt(((double) 1 /( listHora.size()-1))* somatorio);
			
			return desvPadrao;
		}
		
	}
	
}