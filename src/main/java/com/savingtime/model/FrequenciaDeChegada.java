package com.savingtime.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.savingtime.utils.Utilidades.converterHoraStringToInt;


public class FrequenciaDeChegada {
	
	
	public FrequenciaDeChegada(){
		
		
	}
	

	private int[] chegadaDeCliente = new int[24];
	
	private int quantidadeAtendimento;
	
	private List<int[]> listHorarioPico = new ArrayList<int[]>();
	
	
	@Override
	public String toString() {
		return "FrequenciaDeChegada [listHorarioPico=" + listHorarioPico + "]";
	}

	public int[] getChegadaDeCliente(){
		return chegadaDeCliente;
	}
	
	public int getChegadaDeClienteHora(int index) {
		return chegadaDeCliente[index];
	}

	public void setChegadaDeCliente(int[] chegadaDeCliente) {
		this.chegadaDeCliente = chegadaDeCliente;
	}
	
	public void setChegadaDeClienteHora(int index, int quantidadeAtendimento) {
		chegadaDeCliente[index] = quantidadeAtendimento;
	}
	
	
	public List<int[]> getListHorarioPico() {
		return listHorarioPico;
	}

	public void setListHorarioPico(List<int[]> listHorarioPico) {
		this.listHorarioPico = listHorarioPico;
	}
	
	public int maiorChegadaDeClientes(int inicio, int fim){
		quantidadeAtendimento = inicio;
		for(int i=inicio; i < fim; i++){
			if(chegadaDeCliente[i+1]> chegadaDeCliente[quantidadeAtendimento]){
				 quantidadeAtendimento = i+1;
			}
		}
		
		return quantidadeAtendimento;
	}
	
	public int menorChegadaDeClientes(){
		 quantidadeAtendimento = chegadaDeCliente[0];
		for(int i=0; i < chegadaDeCliente.length-1; i++){
			if(chegadaDeCliente[i+1]< quantidadeAtendimento){
				quantidadeAtendimento = chegadaDeCliente[i+1];
			}
		}
		
		return quantidadeAtendimento;
	}
	
	public int totalChegadaDeClientes(){
		int retorno = 0;
		for(int i=0; i < chegadaDeCliente.length; i++){
				retorno += chegadaDeCliente[i];
		}
		return retorno;	
	}


	public double mediaDeChegadaDoDia(String abertura, String fechamento, boolean isToday) throws ParseException{
		
		//tem que ser hora cheia
		//pegar a string e transformar em int para usar como indice do vetor chegadaDeCLiente
				
	    int horaAbertura  = converterHoraStringToInt(abertura);
		int horaFechamento = converterHoraStringToInt(fechamento);
		
		int horaAgora = new Date().getHours();
		int totalHorasTrabalhadas;
		int somaDia;
		
		if(horaAgora < horaFechamento && isToday){
			totalHorasTrabalhadas = horaAgora - horaAbertura;	
			
			somaDia = 0;
			for (int k = horaAbertura; k <= horaAgora; k++){
				somaDia+=chegadaDeCliente[k];
			}
			
		} else {
			totalHorasTrabalhadas = horaFechamento - horaAbertura;		
			somaDia = 0;
			for (int k = horaAbertura; k <= horaFechamento; k++){
				somaDia+=chegadaDeCliente[k];
			}
		}
		System.out.println("MEDIA: "+ somaDia/totalHorasTrabalhadas);
		return (double) somaDia/totalHorasTrabalhadas;
		
	}
	
	public List<int[]> definePico (String abertura, String fechamento,int qtdSemana) throws ParseException{
			
			double mediaDechegadaDoDia = mediaDeChegadaDoDia(abertura, fechamento, false);
			return recursivo(converterHoraStringToInt(abertura), converterHoraStringToInt(fechamento), chegadaDeCliente, mediaDechegadaDoDia);
	}
	
	
	public List<int[]> recursivo(int horaIni, int horaFim, int[] lista, double mediaDia) {
		
		int horarioMaiorChegada = maiorChegadaDeClientes(horaIni,horaFim);
		

		
		if((lista[horarioMaiorChegada] > mediaDia && horaIni != horaFim)/* || (lista[horarioMaiorChegada] > mediaDia && horaIni != horaFim) */) {
			int countPico = horarioMaiorChegada;
			int inicioPico = 0;
			int fimPico = 0;
			
			/** ESQUERDA **/
			if(countPico-1 < horaIni || (countPico-1 >= horaIni && !(lista[countPico-1] > mediaDia))) {
				inicioPico = horarioMaiorChegada;
			}
			
			while(countPico-1 >= horaIni && lista[countPico-1] > mediaDia){
				countPico--;
				inicioPico = countPico;
			}
			
			/** DIREITA **/
			countPico = horarioMaiorChegada;
			
			if(countPico+1 > horaFim || (countPico+1 <= horaFim && !(lista[countPico+1] > mediaDia))) {
				fimPico = horarioMaiorChegada;

			}
			
			while(countPico+1 <= horaFim && lista[countPico+1] > mediaDia){
				countPico++;
				fimPico = countPico;
			}
			
			int[] pico = {inicioPico,fimPico};
			System.out.println("Inicio Pico: "+inicioPico+"H");
			System.out.println("Fim Pico: "+fimPico+"H");
			System.out.println("\n");
			
			listHorarioPico.add(pico);
			
			if(horaIni != inicioPico)
				recursivo(horaIni, inicioPico-1, lista, mediaDia);
			if(horaFim != fimPico)
				recursivo(fimPico+1, horaFim, lista, mediaDia);
			
			
		} else {
			return listHorarioPico;
		}
		
		return listHorarioPico;
	}

	
	public int mediaPico(int inicioPico, int fimPico){
		int somaPico = 0, contador = 0;
		for (int i = inicioPico; i<fimPico; i++){
			contador++;
			somaPico+=chegadaDeCliente[i];
		}
		return somaPico/contador;
	}
	

}
