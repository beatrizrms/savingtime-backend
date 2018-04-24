package com.savingtime.model;

public class DataComemorativa {
	
	public String nomeDataComemorativa;
	public int codDiaSemana;
	public int peso;
	
	public String getNomeFeriado() {
		return nomeDataComemorativa;
	}
	public void setNomeFeriado(String nomeFeriado) {
		this.nomeDataComemorativa = nomeFeriado;
	}
	public int getCodDiaSemana() {
		return codDiaSemana;
	}
	public void setCodDiaSemana(int codDiaSemana) {
		this.codDiaSemana = codDiaSemana;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	@Override
	public String toString() {
		return "DataComemorativa [nomeDataComemorativa=" + nomeDataComemorativa + ", codDiaSemana=" + codDiaSemana
				+ ", peso=" + peso + "]";
	}

}
