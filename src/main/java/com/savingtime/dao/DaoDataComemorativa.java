package com.savingtime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.savingtime.conexao.AcessoBD;
import com.savingtime.model.DataComemorativa;


public class DaoDataComemorativa {

	public DataComemorativa consultarDataComemorativa(String data){
		
        DataComemorativa dataCom = new DataComemorativa();

		try{
			
			AcessoBD bd = new AcessoBD();	
			Connection conn = bd.obtemConexao();
			
	        String sqlSelect = "SELECT NOME_DATA_COMEMORATIVA, COD_DIA_SEMANA, PESO FROM DATA_COMEMORATIVA WHERE DATA_COMEMORATIVA = ?";
	        ResultSet rs = null;
	         
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
        	stm.setString(1, data);
            rs = stm.executeQuery();
            
            
            if (!rs.next()) {      
                return null; 
            } else {
            	dataCom.setNomeFeriado(rs.getString(1));
            	dataCom.setCodDiaSemana(rs.getInt(2));
            	dataCom.setPeso(rs.getInt(3));
            }	
	            
		}catch (Exception e){			
			e.printStackTrace();
			return null;
		}
		
	    return dataCom;
	}
}
