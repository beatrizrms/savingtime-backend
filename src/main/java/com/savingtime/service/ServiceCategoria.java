package com.savingtime.service;

import java.util.List;

import com.savingtime.dao.DaoCategoria;
import com.savingtime.model.Categoria;

public class ServiceCategoria {
	
	public List<Categoria> getListaCategorias(int qtdPessoas) {
		DaoCategoria daocategoria = new DaoCategoria();
		return daocategoria.consultarCategoria(qtdPessoas);
	}

}
