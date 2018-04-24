package com.savingtime.calculos;

public class TeoriaFilas {

	
	//chegadas
	private double lambda;
	
	//media de atendimento
	private double mu;
	
	private double gama;
	
	private double rho;
	
	//quantidade mesas
	private int servidores;
	
	public TeoriaFilas (double lambda, double mu, int servidores) {
		
		setLambda(lambda);
		setMu(mu);
		setServidores(servidores);
		gama();
		rho();

	}
	
	// GETTERS AND SETTERS
	
	private double getGama() {
		return gama;
	}
	
	private double getRho() {
		return rho;
	}
	
	private double getLambda() {
		return lambda;
	}

	private void setLambda(double lambda) {
		this.lambda = lambda;
	}

	private double getMu() {
		return mu;
	}
	
	private void setMu(double mu) {
		System.out.println("Mu: "+(1/mu)*60);
		this.mu = (1/mu)*60;
	}

	private int getServidores() {
		return servidores;
	}

	private void setServidores(int servidores) {
		this.servidores = servidores;
	}

	
	
	/************  CALCULOS DA TEORIA DAS FILAS GERAL   ************/
	
	public double getTempoNaFila(){
		if(this.servidores > 1){
			return tempoMedioFilaMMK();
		} else {
			return tempoMedioFilaMM1();	
		}
	}
	
	public double getTempoNoSistema() {
		if(this.servidores > 1){
			return tempoMedioSistemaMMK();
		} else {
			return tempoMedioSistemaMM1();	
		}
	}
	
	public double getTempoDeAtendimento(){
		return mu;
	}
	
	public double getTaxaDeUtilizacao() {
		if(this.servidores > 1){
			return getGama();
		} else {
			return getRho();	
		}
	}
	
	public double getTamFila(){
		if(this.servidores > 1){
			return tamFilaMMK();
		} else {
			return tamFilaMM1();	
		}
	}
	
	
	/************  CALCULOS DA TEORIA DAS FILAS M/M/K  ************/

	
	/*
	 * Taxa de utilizaçâo considerando todos os servidores
	 */
	private void gama(){
		
		// testar depois pq alterações o Mu para 1/mu
		if(lambda < (mu*servidores)) {
			gama = ( lambda/(mu*servidores));
		}else{
			gama = 1; // tratar caso servidores nao é capaz de atender demanda
		}
			System.out.println("Gama: "+gama);
	}
	
	
	/*
	 * Probabilidade de 0 elementos no sistems de filas - p0
	 */	
	private double probabFilaVaziaMMK(){
		
		int servidores = getServidores();
		//double somatoria = 0;
		double somatoria = 0; 
		for(int i=0; i < servidores;i++){
			
			somatoria += ((Math.pow((lambda/mu),i))/ fatorial(i));  
		}
		
		somatoria += ( ((Math.pow((lambda/mu), servidores)) / fatorial(servidores)) * (1/(1-gama) ));
		
		return 1/ somatoria; 		
		
	}
	
	
	/*
	 * Probabilidade de n elementos no sistema de filas - pN
	 */	
	private double probabilidadeNMMK(int n){
		if(n < servidores){
			return (Math.pow((lambda/mu), n) / fatorial(n)) * probabFilaVaziaMMK();	
		}
		
		return Math.pow(gama,(n - servidores)) * ((lambda/mu)/fatorial(servidores)) * probabFilaVaziaMMK();	
	}
	
	

	/*
	 * Tamanho da Fila
	 */	
	
	
	private double tamFilaMMK(){
				
		return ((Math.pow((lambda/mu), servidores) * mu * lambda) / 
		
		((fatorial(servidores - 1) * ( Math.pow((servidores * mu) - lambda, 2 ))) / probabFilaVaziaMMK())) ;
	}
	
	
	/*
	 * Tamanho do Sistema (quantidade de tanto na fila quanto nas mesas)
	 */	
	
	
	private double tamSistemaMMK(){
		return (lambda/mu) + tamFilaMMK();
	}

	
	
	/*
	 * Tempo médio de espera na fila  
	 */	
	
	private double tempoMedioFilaMMK(){
		
		System.out.println("Tamanho da fila: "+ tamFilaMMK());

		return tamFilaMMK() / lambda;	
	}
	
	/*
	 * Tempo médio de atendimento - TMA
	 */	
	
	private double tempoMedioSistemaMMK(){
		return tamSistemaMMK() / lambda;
	}
	
	
	
	
	/************  CALCULOS DA TEORIA DAS FILAS M/M/1  ************/


	/*
	 * Taxa de utilizaçâo considerando apenas um servidor
	 */
	private void rho(){
		
		if(lambda <= mu) {
			rho = (lambda/mu);
		}else{
			rho = 1; // chegada > atendimento
		}
			
			System.out.println("Rho: "+rho);
	}
	
	
	private double probabFilaVaziaMM1(){
	
		return 1-rho;
	}
	
	private double probabilidadeNMM1(int n){
		
		return Math.pow(rho,n) *  probabFilaVaziaMM1();
	}
	
	
	private double tamFilaMM1(){
	
		return Math.pow(lambda, 2) / ((mu-lambda) * mu);
	}
	
	
	private double tamSistemaMM1(){
		return lambda / (mu-lambda);		
	}
	
	
	private double tempoMedioFilaMM1(){
		
		return lambda/ ((mu-lambda) * mu);
	}
	
	private double tempoMedioSistemaMM1(){
		return 1 / mu - lambda;
		
	}
	
	

	/*
	 * Fatorial - método de apoio as outros desta classe
	 */	
	
	private int fatorial(int valor){
		
		int fatorial = 1;	
		
		for( int i = 2; i <= valor; i++ ){
			fatorial *= i;
		}
		
		return fatorial;
		
	}

}
