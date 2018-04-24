package com.savingtime.service;

import java.sql.SQLException;
import java.util.List;
import com.savingtime.dao.DaoMesa;
import com.savingtime.model.Mesa;
import static com.savingtime.utils.Utilidades.ATIVA_LIVRE;
import static com.savingtime.utils.Utilidades.ATIVA_RESERVADA;


public class ServiceMesa {
	
	
	public int cadastrarMesas(Mesa mesa) throws SQLException{
		mesa.setStatus(ATIVA_LIVRE);
		DaoMesa daomesa = new DaoMesa();
		return daomesa.cadastrarMesa(mesa);
	}
	
	public List<Mesa> consultarTodasMesas() throws SQLException{
		DaoMesa daomesa = new DaoMesa();
		return daomesa.consultarTodasMesas();
	}
	
	public List<Mesa> consultarMesasCapacidade(int capacidade) throws SQLException{
		DaoMesa daomesa = new DaoMesa();
		return daomesa.consultarMesasCapacidade(capacidade);
	}
	
	public List<Mesa> consultarMesasCapacidades(int capacidade) throws SQLException{
		DaoMesa daomesa = new DaoMesa();
		return daomesa.consultarMesasCapacidades(capacidade, ATIVA_LIVRE,ATIVA_RESERVADA);
	}
	
	public List<Mesa> consultarMesaStatus(String status) throws SQLException{
		DaoMesa daomesa = new DaoMesa();
		return daomesa.consultarMesaStatus(status);
	}
	
	public int consultarQtdMesaStatus() throws SQLException{
		DaoMesa daomesa = new DaoMesa();
		return daomesa.consultarQtdMesaStatus(ATIVA_LIVRE);
	}
	
	public int alterarMesa(Mesa mesa) throws SQLException{
		DaoMesa daomesa = new DaoMesa();
		return daomesa.alterarMesa(mesa);
	}

	public List<Mesa> mesasParaSimulacao() throws SQLException{
		DaoMesa daomesa = new DaoMesa();
		return daomesa.mesasParaSimulacao();
	}


}