package com.savingtime.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import com.savingtime.dao.DaoReserva;
import com.savingtime.model.Reserva;
import com.savingtime.utils.SendEmail;
import static com.savingtime.utils.Utilidades.validarDataReserva;
import static com.savingtime.utils.Utilidades.RESERVADA;
import static com.savingtime.utils.Utilidades.formatarDataBanco;
import static com.savingtime.utils.Utilidades.retornarDiaSemana;
import static com.savingtime.utils.Utilidades.CANCELADO;

public class ServiceReserva {

	/*
	 * Ao cadastrar uma reserva, um e-mail é disparado para o cliente com informações da reserva.
	 */
	
	
	public int cadastrarReserva(Reserva reserva) throws SQLException, ParseException{
		
		int[] retorno = new int[2];

		reserva.setStatusReserva(RESERVADA);
		reserva.setDataReserva(formatarDataBanco(reserva.getDataReserva()));
		reserva.setDiaSemana(retornarDiaSemana(reserva.getDataReserva()));
		String horaReserva = reserva.getHoraReserva();
		reserva.setHoraReserva(reserva.getDataReserva() + " " + horaReserva);
		String horaPrevisaoTermino = reserva.getHoraPrevisaoTermino();
		reserva.setHoraPrevisaoTermino(reserva.getDataReserva() + " " + horaPrevisaoTermino);
		DaoReserva daoreserv = new DaoReserva();
		retorno = daoreserv.cadastrarReserva(reserva);
		//verifica se o cadastro foi realizada ----   1 é realizado
		System.out.println("cadastrado");
		if(retorno[1] == 1){
			//verificar se o e-mail está vazio
			if(reserva.getEmail() != null){
				if(reserva.getEmail() != ""){
					SendEmail email = new SendEmail();
					reserva.setCodReserva(retorno[0]);
					email.enviarEmail(reserva);
				}
			}	
		
		}
		return retorno[1];
		
	}
									
	
	public int cancelarReserva(int codReserva) throws SQLException{
		DaoReserva daoreserv = new DaoReserva();
		return daoreserv.cancelarReserva(codReserva, CANCELADO);
		
	}
	
	public int alterarReserva(Reserva reserva) throws SQLException{
		DaoReserva daoreserv = new DaoReserva();
		try {
			reserva.setDataReserva(formatarDataBanco(reserva.getDataReserva()));
			reserva.setHoraReserva(reserva.getDataReserva() + " "+ reserva.getHoraReserva());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return daoreserv.alterarReserva(reserva);
	}
		
	
	public List<Reserva> consultarReservaCpf(String cpf) throws SQLException{
		DaoReserva daoreserv = new DaoReserva();
		return daoreserv.consultarReservaCpf(cpf);
		
	}
	
	public List<Reserva> consultarReservaCodigo(int codigo) throws SQLException{
		DaoReserva daoreserv = new DaoReserva();
		return daoreserv.consultarReservaCodigo(codigo);
		
	}
	
	public List<Reserva> consultarReservaCheckIn(String buscar) throws SQLException{
		DaoReserva daoreserv = new DaoReserva();
		return daoreserv.consultarReservaCheckIn(buscar);
	}	

	
	public List<Reserva> consultarReservaData(String dataInicio, String dataFinal) throws SQLException, ParseException{
		DaoReserva daoreserv = new DaoReserva();
		return daoreserv.consultarReservaData(formatarDataBanco(dataInicio), formatarDataBanco(dataFinal));
		
	}
	
	public List<Reserva> consultarTodasReservas() throws SQLException{
		DaoReserva daoreserv = new DaoReserva();
		return daoreserv.consultarTodasReservas();	
		
	}
	
	public List<Reserva> obterComprovanteDaReserva(int codReserva) throws SQLException{
		DaoReserva daoreserv = new DaoReserva();
		return daoreserv.obterComprovanteDaReserva(codReserva);	
		
	}
	
	public List<Reserva> reservasImpactantes(Reserva reserva) throws SQLException, ParseException {
		DaoReserva daoreserv = new DaoReserva();
		 return  daoreserv.reservasImpactantes(reserva);
	}
	
}	