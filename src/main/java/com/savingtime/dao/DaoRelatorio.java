package com.savingtime.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.savingtime.conexao.AcessoBD;
import com.savingtime.model.Relatorio;

public class DaoRelatorio {
	
	public List<Relatorio> gerarRelatorio(String dataInicio, String dataFinal) throws SQLException {	
		
		List<Relatorio> relatorio = new ArrayList<Relatorio>();
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		
		String sqlSelect = "CALL RELATORIO (?,?)";
		
		try{
			
			ResultSet rs = null;

			CallableStatement cs = conn.prepareCall(sqlSelect);	
			cs.setString(1, dataInicio);
			cs.setString(2, dataFinal);
			rs = cs.executeQuery();
			
	        if(!rs.next()){
	        	return null;
	        }else{
	        	do{
	        		Relatorio relat = new Relatorio();
					relat.setData(rs.getString(1));
					relat.setCheckin(rs.getString(2));
					relat.setAtendimento(rs.getString(3));
					relat.setCheckout(rs.getString(4));
					relat.setPessoas(rs.getString(5));
					relat.setTelefone(rs.getString(6));
					relat.setStatusAtendimento(rs.getString(7));
					relat.setMesa(rs.getString(8));
					relat.setNomeCategoria(rs.getString(9));
					relat.setCliente(rs.getString(10));
					relat.setCodigoReserva(rs.getString(11));
					relat.setPagamento(rs.getString(12));
					relat.setStatusReserva(rs.getString(13));

					relatorio.add(relat);
	        	}while(rs.next());
	        	
	        	conn.close();
	        }	
	        	
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return relatorio;
	}

}
