package com.savingtime.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Reserva {


	@XmlElement private String dataReserva, horaReserva, horaPrevisaoTermino, cpf, nomeCategoria, telefone, statusReserva, comprovante, pagamento, responsavel, email;
	@XmlElement private int codReserva, qtPessoas, diaSemana, codCategoria;
	
	
	public Reserva(){
		
		
	}
	
	
	public String getHoraPrevisaoTermino() {
		return horaPrevisaoTermino;
	}


	public void setHoraPrevisaoTermino(String horaPrevisaoTermino) {
		this.horaPrevisaoTermino = horaPrevisaoTermino;
	}
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	
	public int getDiaSemana() {
		return diaSemana;
	}
	
	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}
	
	public int getCodCategoria() {
		return codCategoria;
	}
	
	public void setCodCategoria(int codCategoria) {
		this.codCategoria = codCategoria;
	}


	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}


	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getCodReserva() {
		return codReserva;
	}

	public void setCodReserva(int codReserva) {
		this.codReserva = codReserva;
	}

	public int getQtPessoas() {
		return qtPessoas;
	}

	public void setQtPessoas(int qtPessoas) {
		this.qtPessoas = qtPessoas;
	}

	public String getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(String dataReserva) {
		this.dataReserva = dataReserva;
	}

	public String getHoraReserva() {
		return horaReserva;
	}

	public void setHoraReserva(String horaReserva) {
		this.horaReserva = horaReserva;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getStatusReserva() {
		return statusReserva;
	}


	public void setStatusReserva(String statusReserva) {
		this.statusReserva = statusReserva;
	}


	public String getPagamento() {
		return pagamento;
	}


	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}


	public String getComprovante() {
		return comprovante;
	}


	public void setComprovante(String comprovante) {
		this.comprovante = comprovante;
	}


	public String getResponsavel() {
		return responsavel;
	}


	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "Reserva [dataReserva=" + dataReserva + ", horaReserva=" + horaReserva + ", cpf=" + cpf
				+ ", nomeCategoria=" + nomeCategoria + ", telefone=" + telefone + ", statusReserva=" + statusReserva
				+ ", comprovante=" + comprovante + ", pagamento=" + pagamento + ", responsavel=" + responsavel
				+ ", email=" + email + ", codReserva=" + codReserva + ", qtPessoas=" + qtPessoas + ", diaSemana="
				+ diaSemana + "]";
	}
	
	
}
