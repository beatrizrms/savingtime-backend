package com.savingtime.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.savingtime.conexao.AcessoBD;
import com.savingtime.model.Mesa;

import static com.savingtime.utils.Utilidades.ERROR;
import static com.savingtime.utils.Utilidades.ATIVA_RESERVADA;
import static com.savingtime.utils.Utilidades.ATIVA_LIVRE;
import static com.savingtime.utils.Utilidades.ATIVA_OCUPADA;
import static com.savingtime.utils.Utilidades.IMPEDIDO;
import static com.savingtime.utils.Utilidades.SUCCESS;
import static com.savingtime.utils.Utilidades.NOT_FOUND;
import static com.savingtime.utils.Utilidades.NAOPERMITIDO;




public class DaoMesa {
	

	public int cadastrarMesa(Mesa mesa) throws SQLException {	
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		/*String sqlSelect = "SELECT COUNT(*) FROM MESAS WHERE (NUM_MESA = ? AND STATUS_MESA = "+ATIVA_LIVRE+") OR "+
								 "(NUM_MESA = ? AND STATUS_MESA = "+ATIVA_RESERVADA +") OR "+
								 "(NUM_MESA = ? AND STATUS_MESA = "+ATIVA_OCUPADA+");";*/

		
		String sqlSelect = "SELECT COUNT(*) FROM MESA WHERE NUM_MESA = ? AND STATUS_MESA = "+"'"+ATIVA_LIVRE+"'"+" OR "+
				 "NUM_MESA = ? AND STATUS_MESA = "+"'"+ATIVA_RESERVADA+"'"+" OR "+
				 "NUM_MESA = ? AND STATUS_MESA = "+"'"+ATIVA_OCUPADA+"'"+";";
		
		try{
	        ResultSet rs = null;
			PreparedStatement stm = conn.prepareStatement(sqlSelect);
			stm.setInt(1, mesa.getNumMesa());
			stm.setInt(2, mesa.getNumMesa());
			stm.setInt(3, mesa.getNumMesa());
			rs = stm.executeQuery();
			rs.next();
			if(rs.getInt(1) == 0){		
				String sqlInsert = "call CADASTRAR_MESAS (?,?,?)";
					
				CallableStatement cs = conn.prepareCall(sqlInsert);	
				cs.setInt(1, mesa.getNumMesa());
				cs.setInt(2, mesa.getCapacidade());
				cs.setString(3, mesa.getStatus());
				cs.executeUpdate();	
				cs.close();
				
			}else{
				return IMPEDIDO;
			}
						  
			
		}catch (Exception e){
			e.printStackTrace();
			return ERROR;

		}
	
		return SUCCESS;

	}
	
	public int alterarMesa(Mesa mesa) throws SQLException {	
		
		
		List<Mesa> mesas = consultarMesa(mesa.getCodigo());
		//verifica se a mesa está no banco de dados
		if(mesas == null) {

			return NOT_FOUND;
		}
				//verifica se a mesa está ocupada, caso esteja, não permite alterações
		if(mesas.get(0).getStatus().equals(ATIVA_OCUPADA)  || mesa.getStatus().equals(ATIVA_OCUPADA)){
			return NAOPERMITIDO;
		}else{
			
			
			//verifica se existe uma mesa ativa com o mesmo número de descrição, pois não deverá ter mesas ativas com o mesmo número
			AcessoBD bd = new AcessoBD();
			Connection conn = bd.obtemConexao();	
			String sqlSelect = "SELECT COD_MESA FROM MESA WHERE NUM_MESA = ?;";
			
			try{
		        ResultSet rs = null;
				PreparedStatement stm = conn.prepareStatement(sqlSelect);
				stm.setInt(1, mesa.getNumMesa());
	            rs = stm.executeQuery();
				
				if(!rs.next() || rs.getInt(1) == mesa.getCodigo()){
					 
					String sqlUpdate = "UPDATE MESA SET CAPACIDADE=?, STATUS_MESA=?, NUM_MESA=? WHERE COD_MESA=?;";
					stm = conn.prepareStatement(sqlUpdate);	
					stm.setInt(1, mesa.getCapacidade());
					stm.setString(2, mesa.getStatus());
					stm.setInt(3, mesa.getNumMesa());
					stm.setInt(4, mesa.getCodigo());
					stm.executeUpdate();
					stm.close();
				} else {
					 return IMPEDIDO;
				}
									  
				}catch (Exception e){
					e.printStackTrace();
					return ERROR;
				}
			}
			
		return SUCCESS;
	}
				
	public List<Mesa> consultarTodasMesas() throws SQLException {
		List<Mesa> listmesa;
		
		try{
			AcessoBD bd = new AcessoBD();	
			Connection conn = bd.obtemConexao();
			
	        String sqlSelect = "SELECT * FROM MESA ORDER BY STATUS_MESA, CAPACIDADE";
	        ResultSet rs = null;
	        listmesa = new ArrayList<Mesa>();
	        
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
            rs = stm.executeQuery();
         
            if (!rs.next()) {      
                return null; 
            } else {
            
            	do {
            		Mesa mesa = new Mesa();
	            	mesa.setCodigo(rs.getInt(1));
	            	mesa.setNumMesa(rs.getInt(2));
	                mesa.setCapacidade(rs.getInt(3));
	                mesa.setStatus(rs.getString(4));
	                listmesa.add(mesa);
            	} while (rs.next());         
            	   
            }
	            
		}catch (Exception e){			
			e.printStackTrace();
			return null;
		}
		
	    return listmesa;
	}
	
	
	// CONSULTAR UMA MESA
	public List<Mesa> consultarMesa(int cod) throws SQLException {
		AcessoBD bd = new AcessoBD();	
		Connection conn = bd.obtemConexao();

        String sqlSelect ="SELECT COD_MESA, NUM_MESA, CAPACIDADE, STATUS_MESA FROM  MESA WHERE COD_MESA = ?;";
        ResultSet rs = null;
        List<Mesa> listmesa = new ArrayList<Mesa>();
        
        try{
        	
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
        	stm.setInt(1, cod);
        	rs = stm.executeQuery();
            if(!rs.next()){           
                return null; 
            }else{
	        	do{
	                Mesa mesa = new Mesa();
	                mesa.setCodigo(rs.getInt(1));
	                mesa.setNumMesa(rs.getInt(2));
	                mesa.setCapacidade(rs.getInt(3));
	                mesa.setStatus(rs.getString(4));
	                listmesa.add(mesa);
	        	} while(rs.next());    
             }  
            
		}catch (Exception e){			
			e.printStackTrace();
			return null; 
		}
		
	    return listmesa;  
	}
	
	
	
	public List<Mesa> consultarMesasCapacidade(int capacidade) throws SQLException {
		AcessoBD bd = new AcessoBD();	
		Connection conn = bd.obtemConexao();

        String sqlSelect ="SELECT COD_MESA AS 'CÓDIGO DA MESA', NUM_MESA AS 'NÚMERO DA MESA', CAPACIDADE, STATUS_MESA AS"
        				+ " 'STATUS DA MESA' FROM  MESA WHERE CAPACIDADE = ?;";
        ResultSet rs = null;
        List<Mesa> listmesa = new ArrayList<Mesa>();
        
        try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
        	stm.setInt(1, capacidade);
        	rs = stm.executeQuery();
            if(!rs.next()){ 
            	//Não encontrado na base dados
                System.out.print("mesa não encontrada na base de dados");            
                return null; 

            }else{
		        	do{
		                Mesa mesa = new Mesa();
		                mesa.setCodigo(rs.getInt(1));
		                mesa.setNumMesa(rs.getInt(2));
		                mesa.setCapacidade(rs.getInt(3));
		                mesa.setStatus(rs.getString(4));
		                listmesa.add(mesa);
		        	} while(rs.next());    
	             }  
            
		}catch (Exception e){			
			e.printStackTrace();
			return null; 
		}
		
	    return listmesa;  
	}
	
	//LISTAR MESAS COM CAPACIDADE MENOR OU IGUAL A SOLICITADA
	public List<Mesa> consultarMesasCapacidades(int capacidade, String statusLivre, String statusReservada) throws SQLException {
		AcessoBD bd = new AcessoBD();	
		Connection conn = bd.obtemConexao();

        String sqlSelect ="SELECT COD_MESA AS 'CÓDIGO DA MESA', NUM_MESA AS 'NÚMERO DA MESA', CAPACIDADE,"
        				+ " STATUS_MESA AS 'STATUS DA MESA' FROM  MESA "+ 
        				  "WHERE CAPACIDADE>=? AND STATUS_MESA=? OR STATUS_MESA=? ORDER BY CAPACIDADE;";
        
        ResultSet rs = null;
        List<Mesa> listmesa = new ArrayList<Mesa>();
        
        try{
        	
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
        	stm.setInt(1, capacidade);
        	stm.setString(2, statusLivre);
        	stm.setString(3, statusReservada);
        	rs = stm.executeQuery();
            if(!rs.next()){ 
            	//Não encontrado na base dados
                System.out.print("mesa não encontrada na base de dados");            
                return null; 

            }else{
		        	do{
		                Mesa mesa = new Mesa();
		                mesa.setCodigo(rs.getInt(1));
		                mesa.setNumMesa(rs.getInt(2));
		                mesa.setCapacidade(rs.getInt(3));
		                mesa.setStatus(rs.getString(4));
		                listmesa.add(mesa);
		        	} while(rs.next());    
	             }  
            
		}catch (Exception e){	
			e.printStackTrace();
			return null; 
		}
		
	    return listmesa;  
	}


	
	public List<Mesa> consultarMesaStatus(String status) throws SQLException {
		AcessoBD bd = new AcessoBD();	
		Connection conn = bd.obtemConexao();
         
        String sqlSelect = "SELECT COD_MESA AS 'CÓDIGO DA MESA', NUM_MESA AS 'NÚMERO DA MESA', CAPACIDADE,"
        				 + " STATUS_MESA AS 'STATUS DA MESA' FROM  MESA WHERE STATUS_MESA =?;";

        ResultSet rs = null;
        List<Mesa> listmesa = new ArrayList<Mesa>();
        
        
        try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
        	stm.setString(1, status);
        	rs = stm.executeQuery();
        	
        	if(!rs.next()){
        		//Não encontrado na base dados
                System.out.print("mesa não encontrada na base de dados");            
                return null;             
        	}else{            	
	        		do{
	            		Mesa mesa = new Mesa();
	            		mesa.setCodigo(rs.getInt(1));
	            		mesa.setNumMesa(rs.getInt(2));
	            		mesa.setCapacidade(rs.getInt(3));
	            		mesa.setStatus(rs.getString(4));
		                listmesa.add(mesa);            
	            	}while(rs.next());    
        		}
        	
		}catch (Exception e){	
			e.printStackTrace();
			return null; 
		}
		
	    return listmesa;  
	}
	
	public int consultarQtdMesaStatus(String status) throws SQLException {
		AcessoBD bd = new AcessoBD();	
		Connection conn = bd.obtemConexao();
        int quantidade = 0; 
        String sqlSelect = "SELECT COUNT(*) FROM MESA WHERE STATUS_MESA = ?;";
        				 

        ResultSet rs = null;        
        
        try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
            stm.setString(1, status);
            rs = stm.executeQuery();
         
            if(!rs.next()){
                return 0; 
	            
            }else{
            	quantidade = rs.getInt(1);
            }
            	
            
		}catch (Exception e){	
			e.printStackTrace();
			System.out.println("erro");
		}
		
	    return quantidade;  
	}

	
	
	public List<Mesa> mesasParaSimulacao() throws SQLException {	
		
		AcessoBD bd = new AcessoBD();	
		Connection conn = bd.obtemConexao();
         
        String sqlSelect = "SELECT COD_MESA, NUM_MESA, CAPACIDADE,  'Ativa - Livre' as 'STATUS_MESA' FROM MESA"
        		+ " WHERE STATUS_MESA != 'Inativa' ORDER BY CAPACIDADE;";

        ResultSet rs = null;
        List<Mesa> listmesa = new ArrayList<Mesa>();
        
        
        try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
        	rs = stm.executeQuery();
        	
        	if(!rs.next()){
        		//Não encontrado na base dados
                System.out.print("mesa não encontrada na base de dados");            
                return null;             
        	}else{            	
	        		do{
	            		Mesa mesa = new Mesa();
	            		mesa.setCodigo(rs.getInt(1));
	            		mesa.setNumMesa(rs.getInt(2));
	            		mesa.setCapacidade(rs.getInt(3));
	            		mesa.setStatus(rs.getString(4));
		                listmesa.add(mesa);            
	            	}while(rs.next());    
        		}
        	
		}catch (Exception e){	
			e.printStackTrace();
			return null; 
		}
		
	    return listmesa;  
	}
	
	

}
