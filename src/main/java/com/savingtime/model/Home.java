package com.savingtime.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Home {
	
	@XmlElement private int pessoasFila;
	@XmlElement private int mesasLivres;
	@XmlElement private String tempoEspera;
	
	public int getPessoasFila() {
		return pessoasFila;
	}
	public void setPessoasFila(int pessoasFila) {
		this.pessoasFila = pessoasFila;
	}
	public int getMesasLivres() {
		return mesasLivres;
	}
	public void setMesasLivres(int mesasLivres) {
		this.mesasLivres = mesasLivres;
	}
	public String getTempoEspera() {
		return tempoEspera;
	}
	public void setTempoEspera(String string) {
		this.tempoEspera = string;
	}
	@Override
	public String toString() {
		return "Home [pessoasFila=" + pessoasFila + ", mesasLivres="
				+ mesasLivres + ", tempoEspera=" + tempoEspera + "]";
	}
	
	
	

}
