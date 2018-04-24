package com.savingtime.calculos;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.savingtime.model.DataComemorativa;
import com.savingtime.model.Disponibilidade;
import com.savingtime.model.Home;
import com.savingtime.model.Reserva;
import com.savingtime.service.ServiceAtendimento;
import com.savingtime.service.ServiceDataComemorativa;
import com.savingtime.service.ServiceMesa;

import static com.savingtime.utils.Utilidades.formatDateToString;
import static com.savingtime.utils.Utilidades.minutesParseTime;
import static com.savingtime.utils.Utilidades.minutesParseTimeHome;
import static com.savingtime.utils.Utilidades.validarDataReserva;

public class Algoritmo {
	
	SimulacaoReserva simulacaoReserva;
	TeoriaFilas teoriaFilas;
	Disponibilidade disp = new Disponibilidade();
	List<Reserva> listReservasImpac;
	ServiceDataComemorativa serviceDataCom;
	double porcento10 = 0.10;
	double porcento20 = 0.20;
	private double porcentagemDeInterferencia;

	
	/**
	 * Disp de reserva futura
	 * @return
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public Disponibilidade disponibilidadeReserva(Reserva reserva) throws ParseException, SQLException {
		
		
		if(!validarDataReserva(reserva.getDataReserva(), reserva.getHoraReserva())){
			System.out.println("A data ta errada");
			return null;
		}else{
				
			DataComemorativa dataComemorativa = calcularDataComemorativa(reserva);
		
			simulacaoReserva = new SimulacaoReserva();
			
			reserva = simulacaoReserva.previsaoTerminoReserva(reserva);
			
			double lambda = simulacaoReserva.chegadaClientes(reserva);
			
			if(lambda == -1){
				return null;
			}
			
			double mu =  simulacaoReserva.mediaAtendimentosTotal(reserva.getDataReserva());
			int servidores = simulacaoReserva.mesasLivres(reserva);
			
			System.out.println("Lambda:" + lambda +
								"\nServidores:" +servidores +
								"\nMu:" + mu);
			listReservasImpac = simulacaoReserva.getReservasImpactantes();
			
			if(servidores == 0){
				teoriaFilas = new TeoriaFilas(lambda, mu, 1);
			}else{
				teoriaFilas = new TeoriaFilas(lambda, mu, servidores);
			}
			
			// *60 conversão para minutos
			double tempFila = teoriaFilas.getTempoNaFila()*60;
			
			System.out.println("Temp fila double: " +tempFila);
			long tempFilaAux = (long) tempFila;
			System.out.println("Temp fila long: "+tempFilaAux);

			disp.setTempoEspera(minutesParseTime(tempFilaAux));			
			disp.setTamFila(teoriaFilas.getTamFila());	

			if(teoriaFilas.getTaxaDeUtilizacao() <= 0 && porcentagemDeInterferencia < 0){
				disp.setTaxaUtilizacao(teoriaFilas.getTaxaDeUtilizacao());
			} else {
				if(teoriaFilas.getTaxaDeUtilizacao() < Math.abs(porcentagemDeInterferencia)){
					disp.setTaxaUtilizacao(teoriaFilas.getTaxaDeUtilizacao());
				} else {
					disp.setTaxaUtilizacao(teoriaFilas.getTaxaDeUtilizacao() + porcentagemDeInterferencia);
				}
			}
			
			// permanecer no final
			disp.setQtdMesasLivres(servidores);
			disp.setReservaAtual(reserva);
			disp.setReservasImpactantes(listReservasImpac);
			disp.setDataComemorativa(dataComemorativa);
			
			return disp;
		}
		
	}
	
	public DataComemorativa calcularDataComemorativa(Reserva reserva) throws ParseException {
		 serviceDataCom = new ServiceDataComemorativa();
		 DataComemorativa dataComemorativa = serviceDataCom.consultarDataComemorativa(reserva.getDataReserva());
		 if(dataComemorativa != null){
			 if(dataComemorativa.getPeso() == -2) {
				 porcentagemDeInterferencia = -porcento20;
				 System.out.println("% de interfenrecia: "+porcentagemDeInterferencia);
			 } else if(dataComemorativa.getPeso() == -1) {
				 porcentagemDeInterferencia = -porcento10;
				 System.out.println("% de interfenrecia: "+porcentagemDeInterferencia);
			 }else if(dataComemorativa.getPeso() == 0) {
				 porcentagemDeInterferencia = 0.0;
				 System.out.println("% de interfenrecia: "+porcentagemDeInterferencia);
			 }else if(dataComemorativa.getPeso() == 1) {
			 	porcentagemDeInterferencia = porcento10;
				 System.out.println("% de interfenrecia: "+porcentagemDeInterferencia);
		 	}else if(dataComemorativa.getPeso() == 2) {
			 	porcentagemDeInterferencia = porcento20;
				 System.out.println("% de interfenrecia: "+porcentagemDeInterferencia);
		 	}
		}
		 
		return dataComemorativa;
	}

	public List<Home> getDadosHome() throws ParseException, SQLException {
		
		ServiceMesa  serviceMesa = new ServiceMesa();
		ServiceAtendimento  serviceAtendimento = new ServiceAtendimento();	
		Home home = new Home();
		List<Home> retorno = new ArrayList<Home>();
		
		TeoriaFilas tf;
		SimulacaoReserva simulacaoReserva = new SimulacaoReserva();

		Date data = new Date(); 
		String dataHoje = formatDateToString(data);		
		double lambda = simulacaoReserva.chegadaClientesHoje(dataHoje);
		System.out.println("Lambda: "+lambda);
		
		if(lambda == -1.0) {
			return null; 
		} 
		
		double mu =  serviceAtendimento.mediaAtendimentosHoje(dataHoje);
		int servidores = serviceMesa.consultarQtdMesaStatus();
		System.out.println("Mu: "+mu);
		System.out.println("Servidores: "+servidores);

		
		if(servidores == 0){
			tf = new TeoriaFilas(lambda, mu, 1);
		}else{
			tf = new TeoriaFilas(lambda, mu, servidores);
		}
		
		
		home.setMesasLivres(servidores);
		home.setPessoasFila(serviceAtendimento.qtdFilaEspera());
		
		//*60 conversão para mintuos
		double tempFila = tf.getTempoNaFila()*60;
		
		System.out.println("Tempo de espera na fila (double)" + tempFila );
		
		long tempFilaAux = (long) tempFila;
		System.out.println("Tempo de espera na fila (long)" + tempFilaAux );

		home.setTempoEspera(minutesParseTimeHome(tempFilaAux));
		
		retorno.add(home);
		
		return retorno;
	}
	

}
