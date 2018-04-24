package com.savingtime.model;

public class Estabelecimento {
	
	//Atributos
	private String diaSemana, horaAbertura, horaFechamento;
	private int fechado;
	
	public Estabelecimento(){	
	}
	
	
	public String getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	public String getHoraAbertura() {
		return horaAbertura;
	}
	public void setHoraAbertura(String horaAbertura) {
		this.horaAbertura = horaAbertura;
	}
	public String getHoraFechamento() {
		return horaFechamento;
	}
	public void setHoraFechamento(String horaFechamento) {
		this.horaFechamento = horaFechamento;
	}
	public int getFechado() {
		return fechado;
	}
	public void setFechado(int fechado) {
		this.fechado = fechado;
	}


	@Override
	public String toString() {
		return "Estabelecimento [diaSemana=" + diaSemana + ", horaAbertura="
				+ horaAbertura + ", horaFechamento=" + horaFechamento
				+ ", fechado=" + fechado + "]";
	}


}
