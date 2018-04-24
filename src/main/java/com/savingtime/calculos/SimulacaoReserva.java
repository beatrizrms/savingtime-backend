package com.savingtime.calculos;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;





import com.savingtime.model.Estabelecimento;
import com.savingtime.model.FrequenciaDeChegada;
import com.savingtime.model.Mesa;
import com.savingtime.model.Reserva;
import com.savingtime.service.ServiceAtendimento;
import com.savingtime.service.ServiceEstabelecimento;
import com.savingtime.service.ServiceFrequenciaDeChegada;
import com.savingtime.service.ServiceMesa;
import com.savingtime.service.ServiceReserva;

import static com.savingtime.utils.Utilidades.retornarDiaSemana;
import static com.savingtime.utils.Utilidades.somarHora;
import static com.savingtime.utils.Utilidades.formatarHoraString;
import static com.savingtime.utils.Utilidades.formatarDataBanco;
import static com.savingtime.utils.Utilidades.minutesParseTime;



public class SimulacaoReserva{
	
	public ServiceEstabelecimento serviceEstabelecimento;
	public ServiceFrequenciaDeChegada  serviceFreqCheg;
	public ServiceReserva  serviceReserva;
	public ServiceMesa  serviceMesa;


	List<Reserva> reservasImpactantes = new ArrayList<Reserva>();
	List<Mesa> mesaSimulacao = new ArrayList<Mesa>();
	List<Mesa> mesasImpactadas = new ArrayList<Mesa>();

	
	/**
	 * Media de quantidade de clientes (atendimentos) no dia 
	 * (horario de abertura ou horario de pico - dependendo de que horario a reserva nova é)
	 * @param reserva
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	public double chegadaClientes(Reserva reserva) throws ParseException, SQLException {
		
		double retorno = -1;
		String dataReserva = reserva.getDataReserva();
		String horaReserva = reserva.getHoraReserva();
		Estabelecimento estabelecimento = new Estabelecimento();
		
		serviceEstabelecimento = new ServiceEstabelecimento();
		estabelecimento = serviceEstabelecimento.consultarHorarioFuncionamento(retornarDiaSemana(dataReserva),estabelecimento);
		
		String estabelecimentoHoraAbertura = estabelecimento.getHoraAbertura();
		String estabelecimentoHoraFechamento = estabelecimento.getHoraFechamento();
	
		FrequenciaDeChegada matrizChegada = new FrequenciaDeChegada();
		serviceFreqCheg = new ServiceFrequenciaDeChegada();

		int qtdSemana = serviceFreqCheg.getQtdSemana(formatarDataBanco(dataReserva));
		
		if (dentroHorarioFuncionamento(horaReserva, estabelecimento)){

			matrizChegada = serviceFreqCheg.chegadaDeClientes(retornarDiaSemana(dataReserva),matrizChegada);
			matrizChegada.definePico(estabelecimentoHoraAbertura, estabelecimentoHoraFechamento, qtdSemana);
			
			
			//Fazer verificação se hora esta dentro do horario de Pico
			
			List<int[]> listHorarioPico  = matrizChegada.getListHorarioPico();
			for(int i=0; i< listHorarioPico.size();i++){
				
				String inicioPico = formatarHoraString(listHorarioPico.get(i)[0]);
				String fimPico = formatarHoraString(listHorarioPico.get(i)[1]);
				
				if ((horaReserva.compareTo(inicioPico) > 0) && (horaReserva.compareTo(fimPico) < 0)){
					retorno = matrizChegada.mediaPico(listHorarioPico.get(i)[0],listHorarioPico.get(i)[1]);
					System.out.println("Qtd média de chegada de clientes no horário de pico: "+ retorno / qtdSemana);

					return retorno / qtdSemana;
				}
			}
					
			retorno = matrizChegada.mediaDeChegadaDoDia(estabelecimentoHoraAbertura, estabelecimentoHoraFechamento, false);	
				
		}
		
		if(retorno == -1){
			return retorno;
		}
		
		System.out.println("Qtd média de chegada de clientes por hora: "+ retorno / qtdSemana);

		return retorno / qtdSemana ;
	}
	
	/**
	 * Media de quantidade de clientes (atendimentos) no dia atual
	 * @param reserva
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	public double chegadaClientesHoje(String dataHoje) throws ParseException, SQLException {
		
		double retorno = 0;
		Estabelecimento estabelecimento = new Estabelecimento();
				
		serviceEstabelecimento = new ServiceEstabelecimento();
		estabelecimento = serviceEstabelecimento.consultarHorarioFuncionamento(retornarDiaSemana(dataHoje),estabelecimento);
		
		// verificar se estabelecimento veio nulo
		if(estabelecimento.getDiaSemana() == null){
			return -1.0;
		}
		
		String estabelecimentoHoraAbertura = estabelecimento.getHoraAbertura();
		String estabelecimentoHoraFechamento = estabelecimento.getHoraFechamento();
	
		serviceFreqCheg = new ServiceFrequenciaDeChegada();			  
		FrequenciaDeChegada matrizChegada = new FrequenciaDeChegada();			
		matrizChegada = serviceFreqCheg.chegadaDeClientesAtual(formatarDataBanco(dataHoje), matrizChegada);
				
		retorno = matrizChegada.mediaDeChegadaDoDia(estabelecimentoHoraAbertura, estabelecimentoHoraFechamento, true);	
				
		
		return retorno;
	}
	
	
	/**
	 * Retornar a media de tempo de atendimento de total
	 * @return tempo em minutos
	 * @throws SQLException
	 * @throws ParseException 
	 */
	
	//
	
	public double mediaAtendimentosTotal(String dataReserva) throws SQLException, ParseException {
		ServiceAtendimento serviceAtendimento = new ServiceAtendimento();
		
		serviceAtendimento.listaTempoMedioAtendimento(0, dataReserva);

		double media = serviceAtendimento.tempoMedioDeAtendimento();		
		System.out.println("Tempo Média Atendimento por dia da semana: "+ minutesParseTime((long)media));
		
		return media;
	}
	
	
	/**
	 * Simular mesas livres e "atrelar" reservas a mesas que mais se encaixam para retornar quantas mesas disponíveis sobram
	 * @param reserva
	 * @return num de mesas disponiveis
	 * @throws SQLException
	 * @throws ParseException 
	 */
	
	public int mesasLivres(Reserva reserva) throws SQLException, ParseException {
		serviceMesa = new ServiceMesa();
		mesaSimulacao = serviceMesa.mesasParaSimulacao();
		int retorno = mesaSimulacao.size();
		reservasImpactantes(reserva);
		/*
		int i = 0;
		int menor;
		*/
		if(reservasImpactantes != null){
			System.out.println("Total de reservas impactantes: "+reservasImpactantes.size());
			
			/*
			do{
				menor = mesaSimulacao.get(0).getCapacidade() - reservasImpactantes.get(i).getQtPessoas();
				if(menor <= 0) {
					menor = 1000; // achar soluçao melhor
				}
				int posicao = 0;
				for(int j=1; j < mesaSimulacao.size(); j++ ){
					int num =  mesaSimulacao.get(j).getCapacidade() - reservasImpactantes.get(i).getQtPessoas();
					if(num < menor && num >= 0){
						menor = num;
						posicao = j;
					}
				}
				mesasImpactadas.add(mesaSimulacao.get(posicao));
				System.out.println("Contador: "+posicao);

				mesaSimulacao.remove(posicao);
				
				i++;
			} while (i < reservasImpactantes.size() && i < mesaSimulacao.size());
			
			
			System.out.println("Quantidade de mesas livres: "+mesaSimulacao.size());*/
		
	
			retorno -= reservasImpactantes.size();
	}
		if(retorno < 0 ){
			System.out.println("Quantidade de Mesas livres: "+ 0);
			return 0;
		}
		
		System.out.println("Quantidade de Mesas livres: "+ retorno);
		return retorno;
	}
	
	/** Getters para mesas e reservas de impacto **/
	public List<Mesa> getMesasImpactadas() {
		return mesasImpactadas;
	}
	public List<Reserva> getReservasImpactantes() {
		return reservasImpactantes;
	}
	
	
	
	/**
	 * Pegar reservas que estejam "atrapalhando" a nova reserva
	 * @param reserva
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public List<Reserva> reservasImpactantes(Reserva reserva) throws SQLException, ParseException {
		 serviceReserva = new ServiceReserva();
		 reserva.setDataReserva(formatarDataBanco(reserva.getDataReserva()));
		 
		 reservasImpactantes = serviceReserva.reservasImpactantes(reserva);
		 return reservasImpactantes;
	}
	
	/**
	 * Retornar se uma data e hora está dentro do horario de funcionamento
	 * @param data
	 * @param hora
	 * @return true or false
	 * @throws ParseException 
	 */
	public boolean dentroHorarioFuncionamento(String hora, Estabelecimento estabelecimento) throws ParseException {

		String horaEstabAbertura = estabelecimento.getHoraAbertura();
		String horaEstabFechamento = estabelecimento.getHoraFechamento();
		
		System.out.println("Hora Reserva: "+ hora);
		System.out.println("Hora Abertura: "+ horaEstabAbertura);
		System.out.println("Hora Fechamento: "+ horaEstabFechamento);

	
		if(estabelecimento.getFechado() == 0){

			if ((hora.compareTo(horaEstabAbertura) > 0) && (hora.compareTo(horaEstabFechamento)<0)){
				return true;
			} 
		}
		
		return false;
	}
	
	
	
	/**
	 * Previsao de termino de uma reserva X de acordo com sua categoria
	 * @param reserva
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	
	public Reserva previsaoTerminoReserva(Reserva reserva) throws ParseException, SQLException {
		ServiceAtendimento serviceAtendimento = new ServiceAtendimento();
		String horaPrevisaoTerminoReserva = somarHora(serviceAtendimento.tempoMedioAtendimentoCategoria(reserva.getCodCategoria(),reserva.getDataReserva()), reserva.getHoraReserva()); 
		reserva.setHoraPrevisaoTermino(horaPrevisaoTerminoReserva);
		return reserva;
	}
	
}
