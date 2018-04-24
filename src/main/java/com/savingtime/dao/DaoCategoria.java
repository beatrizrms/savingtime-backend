package com.savingtime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.savingtime.conexao.AcessoBD;
import com.savingtime.model.Categoria;

public class DaoCategoria {
	
public List<Categoria> consultarCategoria(int qtdPessoas){	
		
		List<Categoria> listcategoria;
		
		try{
			AcessoBD bd = new AcessoBD();	
			Connection conn = bd.obtemConexao();
			
	        String sqlSelect = "SELECT COD_CATEGORIA, NOME_CATEGORIA FROM CATEGORIA WHERE "+ qtdPessoas +" BETWEEN DE AND ATE";
	        ResultSet rs = null;
	        listcategoria = new ArrayList<Categoria>();
	        
	    	PreparedStatement stm = conn.prepareStatement(sqlSelect);
	        rs = stm.executeQuery();
	     
	        if (!rs.next()) {      
	            return null; 
	        } else {
	        
	        	do {
	        		Categoria categoria = new Categoria();
	        		categoria.setCodCategoria(rs.getInt(1));
	        		categoria.setNomeCategoria(rs.getString(2));
	        		listcategoria.add(categoria);
	        	} while (rs.next());         
	        	   
	        }
	            
		}catch (Exception e){			
			e.printStackTrace();
			return null;
		}
		
	    return listcategoria;
	}

}
