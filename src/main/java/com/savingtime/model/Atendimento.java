package com.savingtime.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Atendimento {

	@XmlElement private String status, nomeResponsavel, nomeCategoria, telefone, data,horaCheckIn, horaCheckOut, horaAtendimento,horaPrevisaoCheckOut;
	@XmlElement private int qtPessoas, numMesa, diaSemana, codCategoria;
	@XmlElement private long codigoAtendimento;
	@XmlElement private Integer numReserva;
	
	public Atendimento(){
			
	}
	
	public int getDiaSemana() {
		return diaSemana;
	}
	
	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}
	
	public String getHoraPrevisaoCheckOut() {
		return horaPrevisaoCheckOut;
	}
	
	public void setHoraPrevisaoCheckOut(String horaPrevisaoCheckout) {
		this.horaPrevisaoCheckOut = horaPrevisaoCheckout;
	}
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	
	public void setCodCategoria(int codCategoria) {
		this.codCategoria = codCategoria;
	}
	
	public int getCodCategoria() {
		return codCategoria;
	}
	
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	public String getHoraCheckIn() {
		return horaCheckIn;
	}
	public void setHoraCheckIn(String horaCheckIn) {
		this.horaCheckIn = horaCheckIn;
	}
	public String getHoraCheckOut() {
		return horaCheckOut;
	}
	public void setHoraCheckOut(String horaCheckOut) {
		this.horaCheckOut = horaCheckOut;
	}
	public String getHoraAtendimento() {
		return horaAtendimento;
	}
	public void setHoraAtendimento(String horaAtendimento) {
		this.horaAtendimento = horaAtendimento;
	}
	public int getQtPessoas() {
		return qtPessoas;
	}
	public void setQtPessoas(int qtPessoas) {
		this.qtPessoas = qtPessoas;
	}
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsanvel) {
		this.nomeResponsavel = nomeResponsanvel;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public long getCodigoAtendimento() {
		return codigoAtendimento;
	}

	public void setCodigoAtendimento(int codigoAtendimento) {
		this.codigoAtendimento = codigoAtendimento;
	}

	public Integer getNumReserva() {
		return numReserva;
	}

	public void setNumReserva(Integer numReserva) {
		this.numReserva = numReserva;
	}

	public int getNumMesa() {
		return numMesa;
	}

	public void setNumMesa(int numMesa) {
		this.numMesa = numMesa;
	}

	@Override
	public String toString() {
		return "Atendimento [status=" + status + ", nomeResponsavel=" + nomeResponsavel + ", nomeCategoria="
				+ nomeCategoria + ", telefone=" + telefone + ", data=" + data + ", horaCheckIn=" + horaCheckIn
				+ ", horaCheckOut=" + horaCheckOut + ", horaAtendimento=" + horaAtendimento + ", qtPessoas=" + qtPessoas
				+ ", numMesa=" + numMesa + ", diaSemana=" + diaSemana + ", codCategoria=" + codCategoria
				+ ", codigoAtendimento=" + codigoAtendimento + ", numReserva=" + numReserva + "]";
	}

	
	
}
