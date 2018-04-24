package com.savingtime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.savingtime.conexao.AcessoBD;
import com.savingtime.model.FrequenciaDeChegada;

public class DaoFrequenciaDeChegada {
	
	public FrequenciaDeChegada chegadaDeClientes(int diaSemana, FrequenciaDeChegada freqCheg ) throws SQLException {
		
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();       
      		
		for(int i=0; i < freqCheg.getChegadaDeCliente().length;i++){
			
			String hora;
			
			if(i < 10) {
				hora = "0" + i;
			} else {
				hora = ""+i;
			}			
			
			String sqlSelect = "SELECT COUNT(*) AS ATENDIMENTOS FROM ATENDIMENTO WHERE TIME_FORMAT(HORA_CHECKIN,'%T') BETWEEN '"+hora+":00:00'"
					+"AND '"+hora+":59:59' AND DIA_SEMANA = ? ORDER BY HORA_CHECKIN";
       
		
			ResultSet rs = null;
	        
			try{
	        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
	            stm.setInt(1, diaSemana);
	            rs = stm.executeQuery();
	         
	            if(!rs.next()){
	                return null; 
		            
	            }else{
	            	freqCheg.setChegadaDeClienteHora(i,rs.getInt(1)) ;
	            }
	            	
	            
			}catch (Exception e){	
				e.printStackTrace();
				System.out.println("erro");
			}
		}
        return freqCheg;  
	
	}	

	
	
	public int getQtdSemana(String data) throws SQLException{
		
		int retorno = 0;
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();  
		
		String sqlSelect = "SELECT COUNT(CONTAGEM) FROM (SELECT COUNT(DATA_EVENTO) AS CONTAGEM "
						 + "FROM ATENDIMENTO WHERE DAYOFWEEK(DATA_EVENTO) = DAYOFWEEK(?) "
						 + "GROUP BY DATE(DATA_EVENTO) ) AS SEMANAS_PASSADAS;"; 

		ResultSet rs = null;
        
		try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
            stm.setString(1, data);
            rs = stm.executeQuery();
         
            if(!rs.next()){
                return -1; 
	            
            }else{
            	retorno = (rs.getInt(1)) ;
            }
            	
            
		}catch (Exception e){	
			e.printStackTrace();
			System.out.println("erro");
		}
	
		return  retorno;	
	}



	public FrequenciaDeChegada chegadaDeClientesAtual(String dataHoje, FrequenciaDeChegada freqCheg) throws SQLException {
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();       
      		
		for(int i=0; i < freqCheg.getChegadaDeCliente().length;i++){
			
			String hora;
			
			if(i < 10) {
				hora = "0" + i;
			} else {
				hora = ""+i;
			}			
			
			String sqlSelect = "SELECT COUNT(*) AS ATENDIMENTOS FROM ATENDIMENTO WHERE TIME_FORMAT(HORA_CHECKIN,'%T') BETWEEN '"+hora+":00:00'"
					+"AND '"+hora+":59:59' AND DATA_EVENTO = ? ORDER BY HORA_CHECKIN";
       
		
			ResultSet rs = null;
	        
			try{
	        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
	            stm.setString(1, dataHoje);
	            rs = stm.executeQuery();
	         
	            if(!rs.next()){
	                return null; 
		            
	            }else{
	            	freqCheg.setChegadaDeClienteHora(i,rs.getInt(1)) ;
	            }
	            	
	            
			}catch (Exception e){	
				e.printStackTrace();
				System.out.println("erro");
			}
		}
        return freqCheg;  
	}	

	
}
