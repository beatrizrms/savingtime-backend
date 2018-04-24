package com.savingtime.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Categoria {
	
	@XmlElement public int codCategoria;
	@XmlElement public String nomeCategoria;
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	@Override
	public String toString() {
		return "Categoria [codCategoria=" + codCategoria + ", nomeCategoria=" + nomeCategoria + "]";
	}
	public int getCodCategoria() {
		return codCategoria;
	}
	public void setCodCategoria(int codCategoria) {
		this.codCategoria = codCategoria;
	}
	
}
