package com.savingtime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.savingtime.conexao.AcessoBD;
import com.savingtime.model.Estabelecimento;


public class DaoEstabelecimento {

	public List<Estabelecimento> consultarDadosEstabelecimento(){	
		
		List<Estabelecimento> listestabelecimento;
		
		try{
			AcessoBD bd = new AcessoBD();	
			Connection conn = bd.obtemConexao();
			
	        String sqlSelect = "SELECT DIA_SEMANA, HORA_ABERTURA, HORA_FECHAMENTO, FECHADO FROM ESTABELECIMENTO";
	        ResultSet rs = null;
	        listestabelecimento = new ArrayList<Estabelecimento>();
	        
	    	PreparedStatement stm = conn.prepareStatement(sqlSelect);
	        rs = stm.executeQuery();
	     
	        if (!rs.next()) {      
	            return null; 
	        } else {
	        
	        	do {
	        		Estabelecimento estabelecimento = new Estabelecimento();
	        		estabelecimento.setDiaSemana(rs.getString(1));
	        		estabelecimento.setHoraAbertura(rs.getString(2));
	        		estabelecimento.setHoraFechamento(rs.getString(3));
	        		estabelecimento.setFechado(rs.getInt(4));
	        		listestabelecimento.add(estabelecimento);
	        	} while (rs.next());         
	        	   
	        }
	            
		}catch (Exception e){			
			e.printStackTrace();
			return null;
		}
		
	    return listestabelecimento;
	}
	
	
	public Estabelecimento consultarHorarioFuncionamento(int diaSemana, Estabelecimento estabelecimento){	
		

		try{
			AcessoBD bd = new AcessoBD();	
			Connection conn = bd.obtemConexao();
	        String sqlSelect = "SELECT DIA_SEMANA, HORA_ABERTURA, HORA_FECHAMENTO, FECHADO FROM ESTABELECIMENTO WHERE COD_ESTABELECIMENTO =?";
	        ResultSet rs = null;	        
	    	PreparedStatement stm = conn.prepareStatement(sqlSelect);
        	stm.setInt(1, diaSemana);
	        rs = stm.executeQuery();
	        
	        if(rs.next()){
	        	estabelecimento.setDiaSemana(rs.getString(1));
	        	estabelecimento.setHoraAbertura(rs.getString(2));
	        	estabelecimento.setHoraFechamento(rs.getString(3));
	        	estabelecimento.setFechado(rs.getInt(4));
	        }
	      
		}catch (Exception e){			
			e.printStackTrace();
			return null;
		}
		
	    return estabelecimento;
	}

}
	
	
	


