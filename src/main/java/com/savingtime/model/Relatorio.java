package com.savingtime.model;

import javax.xml.bind.annotation.XmlElement;

public class Relatorio {
	@XmlElement
	private String data, checkin, atendimento, checkout, telefone, statusAtendimento,
					nomeCategoria, cliente, codigoReserva, pagamento, statusReserva, mesa, pessoas;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(String atendimento) {
		this.atendimento = atendimento;
	}

	public String getCheckout() {
		return checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	public String getPessoas() {
		return pessoas;
	}

	public void setPessoas(String pessoas) {
		this.pessoas = pessoas;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getStatusAtendimento() {
		return statusAtendimento;
	}

	public void setStatusAtendimento(String statusAtendimento) {
		this.statusAtendimento = statusAtendimento;
	}

	public String getMesa() {
		return mesa;
	}

	public void setMesa(String mesa) {
		this.mesa = mesa;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCodigoReserva() {
		return codigoReserva;
	}

	public void setCodigoReserva(String codigoReserva) {
		this.codigoReserva = codigoReserva;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

	public String getStatusReserva() {
		return statusReserva;
	}

	public void setStatusReserva(String statusReserva) {
		this.statusReserva = statusReserva;
	}
	
	
}
