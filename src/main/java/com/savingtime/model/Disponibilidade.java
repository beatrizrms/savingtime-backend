package com.savingtime.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Disponibilidade {
	
	@XmlElement public double tamFila;
	@XmlElement public String tempoEspera; // formatar
	@XmlElement public List<Reserva> reservasImpactantes;
	@XmlElement public int qtdMesasLivres;
	@XmlElement public Reserva reservaAtual;
	@XmlElement public double taxaUtilizacao;
	 public DataComemorativa dataComemorativa;	
	
	public double getTamFila() {
		return tamFila;
	}
	public void setTamFila(double tamFila) {
		this.tamFila = tamFila;
	}
	public String getTempoEspera() {
		return tempoEspera;
	}
	public void setTempoEspera(String tempoEspera) {
		this.tempoEspera = tempoEspera;
	}
	public List<Reserva> getReservasImpactantes() {
		return reservasImpactantes;
	}
	public void setReservasImpactantes(List<Reserva> reservasImpactantes) {
		this.reservasImpactantes = reservasImpactantes;
	}
	public int getQtdMesasLivres() {
		return qtdMesasLivres;
	}
	public void setQtdMesasLivres(int qtdMesasLivres) {
		this.qtdMesasLivres = qtdMesasLivres;
	}
	public Reserva getReservaAtual() {
		return reservaAtual;
	}
	public void setReservaAtual(Reserva reservaAtual) {
		this.reservaAtual = reservaAtual;
	}
	public DataComemorativa getDataComemorativa() {
		return dataComemorativa;
	}
	public void setDataComemorativa(DataComemorativa dataComemorativa) {
		this.dataComemorativa = dataComemorativa;
	}
	public double getTaxaUtilizacao() {
		return taxaUtilizacao;
	}
	public void setTaxaUtilizacao(double taxaUtilizacao) {
		System.out.println("Taxa de Utilizacao: " + taxaUtilizacao);
		this.taxaUtilizacao = taxaUtilizacao;
	}
	
}
