package com.savingtime.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.savingtime.model.Reserva;
import com.savingtime.conexao.AcessoBD;
import static com.savingtime.utils.Utilidades.ERROR;
import static com.savingtime.utils.Utilidades.SUCCESS;
import static com.savingtime.utils.Utilidades.invertDateHour;


public class DaoReserva {
	
	public int[] cadastrarReserva(Reserva reserva) throws SQLException {	
		int[] retorno = new int[2];
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		String sqlSelect = "INSERT INTO RESERVA(DATA_RESERVA, HORA_RESERVA, QTD_PESSOAS, CPF, TELEFONE,"
				+ " COD_CATEGORIA, STATUS_RESERVA, PAGAMENTO, COMPROVANTE, NOME_RESPONSAVEL, EMAIL, DIA_SEMANA, HORA_PREVISAO_TERMINO) VALUES (?,?,?,?,?,?,?,?,?,?,?,DAYOFWEEK(DATA_RESERVA),?);";

		try{
			PreparedStatement stm = conn.prepareStatement(sqlSelect);
				stm.setString(1, reserva.getDataReserva());
				stm.setString(2, reserva.getHoraReserva());
				stm.setInt(3, reserva.getQtPessoas());
				stm.setString(4, reserva.getCpf());
				stm.setString(5, reserva.getTelefone());
				stm.setInt(6, reserva.getCodCategoria());
				stm.setString(7, reserva.getStatusReserva());
				stm.setString(8, reserva.getPagamento());
				stm.setString(9, reserva.getComprovante());	
				stm.setString(10, reserva.getResponsavel());
				stm.setString(11, reserva.getEmail());
				stm.setString(12, reserva.getHoraPrevisaoTermino());	

				stm.executeUpdate();
				
				stm = conn.prepareStatement("SELECT COD_RESERVA FROM RESERVA ORDER BY COD_RESERVA DESC LIMIT 1;");
				ResultSet rs = stm.executeQuery();
				
				while(rs.next()) {
					retorno[0] = rs.getInt(1);
				}
				stm.close();
		}catch (Exception e){
			e.printStackTrace();
			retorno[1] = ERROR;
			return retorno;
		}
		
		retorno[1] = SUCCESS;
		return retorno;
	}
	
	
	public int alterarReserva(Reserva reserva) throws SQLException {	
		
		/*if(rodar algoritmo preditivo e não der pra alterar com os parametros passados)){
			return IMPEDIDO;
		}*/
		
		
		try{
			AcessoBD bd = new AcessoBD();
			Connection conn = bd.obtemConexao();	
			String sqlSelect = "UPDATE RESERVA SET DATA_RESERVA=?, HORA_RESERVA=?, QTD_PESSOAS=?, CPF=?, TELEFONE=?, COD_CATEGORIA=?,"+
								" PAGAMENTO=?, COMPROVANTE=?,NOME_RESPONSAVEL=?, EMAIL=? WHERE COD_RESERVA=?;";
					
			PreparedStatement stm = conn.prepareStatement(sqlSelect);	
				
				stm.setString(1, reserva.getDataReserva());
				stm.setString(2, reserva.getHoraReserva());
				stm.setInt(3, reserva.getQtPessoas());
				stm.setString(4, reserva.getCpf());
				stm.setString(5, reserva.getTelefone());
				stm.setInt(6, reserva.getCodCategoria());
				stm.setString(7, reserva.getPagamento());
				stm.setString(8, reserva.getComprovante());
				stm.setString(9, reserva.getResponsavel());
				stm.setString(10, reserva.getEmail());	
				stm.setInt(11, reserva.getCodReserva());
				stm.executeUpdate();
				stm.close();
		
		}catch (Exception e){
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	
	public int cancelarReserva(int codReserva, String status) throws SQLException {	
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		String sqlSelect = "UPDATE RESERVA SET STATUS_RESERVA=? WHERE COD_RESERVA =?;";

		try{
			PreparedStatement stm = conn.prepareStatement(sqlSelect);
				
				stm.setString(1,status);
				stm.setInt(2,codReserva); 
				stm.executeUpdate();
				stm.close();
		}catch (Exception e){
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	
	public List<Reserva> consultarReservaCpf(String cpf) throws SQLException {
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();       
        String sqlSelect = "SELECT DISTINCT R.COD_RESERVA,DATE_FORMAT(R.DATA_RESERVA, '%d/%m/%Y') AS DATA,DATE_FORMAT(R.HORA_RESERVA,'%T') AS HORA, "
        				 + "R.QTD_PESSOAS, R.CPF, R.HORA_PREVISAO_TERMINO, R.TELEFONE, R.COD_CATEGORIA, C.NOME_CATEGORIA, R.STATUS_RESERVA, R.PAGAMENTO, NOME_RESPONSAVEL, "
        				 + " R.EMAIL FROM RESERVA R JOIN CATEGORIA C ON R.COD_CATEGORIA = C.COD_CATEGORIA WHERE R.CPF=?;";
        ResultSet rs = null;
        List<Reserva> listreserva = new ArrayList<Reserva>();
        try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
            stm.setString(1, cpf);
            rs = stm.executeQuery();
         
            if(!rs.next()){
            	//Não encontrado na base dados
                System.out.print("reservas não encontrada na base de dados");            
                return null; 

              //tratar caso n�o tenha reserva cadastradas. 
            }else{
            	
            	do{
            	         		
            		Reserva reserva = new Reserva();
            		
		            reserva.setCodReserva(rs.getInt(1));
	            	reserva.setDataReserva(rs.getString(2));
	            	reserva.setHoraReserva(rs.getString(3));
		            reserva.setQtPessoas(rs.getInt(4));
		            reserva.setCpf(rs.getString(5));
		            reserva.setHoraPrevisaoTermino(invertDateHour(rs.getString(6)));
		            reserva.setTelefone(rs.getString(7));
		            reserva.setCodCategoria(rs.getInt(8));	
		            reserva.setNomeCategoria(rs.getString(9));
	            	reserva.setStatusReserva(rs.getString(10));
		            reserva.setPagamento(rs.getString(11));
		            reserva.setResponsavel(rs.getString(12));
		            reserva.setEmail(rs.getString(13));
		            listreserva.add(reserva);
		            
            	}while(rs.next());
	            
            }
            	
            
		}catch (Exception e){			
			e.printStackTrace();
			System.out.println("erro");
		}
		
        return listreserva;  
	
	}	
	
	//consulta por cpf ou por codigo da reserva
	
	public List<Reserva> consultarReservaCheckIn(String buscar) throws SQLException {
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();       
        String sqlSelect = "SELECT DISTINCT R.COD_RESERVA, DATE_FORMAT(R.DATA_RESERVA, '%d/%m/%Y') AS DATA, DATE_FORMAT(R.HORA_RESERVA,'%T'), "
        				 + "R.QTD_PESSOAS, R.CPF, R.HORA_PREVISAO_TERMINO, R.TELEFONE,C.NOME_CATEGORIA,C.COD_CATEGORIA, R.STATUS_RESERVA, R.PAGAMENTO, R.NOME_RESPONSAVEL, "
        				 + "R.EMAIL FROM RESERVA R JOIN CATEGORIA C ON R.COD_CATEGORIA = C.COD_CATEGORIA WHERE (R.CPF=? OR R.COD_RESERVA=?) "
        				 + "AND R.DATA_RESERVA = DATE_FORMAT(NOW(), '%Y-%m-%d')"; 

        ResultSet rs = null;
        List<Reserva> listreserva = new ArrayList<Reserva>();
        try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
           stm.setString(1, buscar);
           stm.setString(2, buscar);

            rs = stm.executeQuery();
         
            if(!rs.next()){
            	//Não encontrado na base dados
                System.out.print("reservas não encontrada na base de dados");            
                return null; 

              //tratar caso n�o tenha reserva cadastradas. 
            }else{
            	
            	do{
            		Reserva reserva = new Reserva();
            		
		            reserva.setCodReserva(rs.getInt(1));
	            	reserva.setDataReserva(rs.getString(2));
	            	reserva.setHoraReserva(rs.getString(3));
		            reserva.setQtPessoas(rs.getInt(4));
		            reserva.setCpf(rs.getString(5));
		            reserva.setHoraPrevisaoTermino(invertDateHour(rs.getString(6)));
		            reserva.setTelefone(rs.getString(7));
		            reserva.setNomeCategoria(rs.getString(8));	
		            reserva.setCodCategoria(rs.getInt(9));
	            	reserva.setStatusReserva(rs.getString(10));
		            reserva.setPagamento(rs.getString(11));
		            reserva.setResponsavel(rs.getString(12));
		            reserva.setEmail(rs.getString(12));
		            listreserva.add(reserva);
		            
            	}while(rs.next());
	            
            }
            	
            	
            
		}catch (Exception e){	
			e.printStackTrace();
			System.out.println("erro");
		}
		
        return listreserva;  
	
	}	

	
	
	
	
	public List<Reserva> consultarReservaData(String dataInicio, String dataFinal) throws SQLException {
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();       
        
		
		String sqlSelect = "SELECT DISTINCT R.COD_RESERVA, DATE_FORMAT(R.DATA_RESERVA, '%d/%m/%Y') AS DATA, TIME_FORMAT(R.HORA_RESERVA,'%T'), "
						 + "R.QTD_PESSOAS, R.CPF,R.HORA_PREVISAO_TERMINO, R.TELEFONE, C.COD_CATEGORIA, R.STATUS_RESERVA, R.PAGAMENTO, R.NOME_RESPONSAVEL, "
						 + "R.EMAIL, C.NOME_CATEGORIA FROM RESERVA R JOIN CATEGORIA C ON R.COD_CATEGORIA = C.COD_CATEGORIA WHERE DATA_RESERVA BETWEEN" 
						 +  "'"+dataInicio+"'" + "AND" + "'"+dataFinal+"' ORDER BY DATA_RESERVA, HORA_RESERVA;"; 
		
		ResultSet rs = null;
        
        
        List<Reserva> listreserva = new ArrayList<Reserva>();
     
        try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
            rs = stm.executeQuery();
         
            if(!rs.next()){
            	//Não encontrado na base dados
                System.out.print("reservas não encontrada na base de dados");            
                return null;
            }else{
            	
            	do{
            		Reserva reserva = new Reserva();    		
		            reserva.setCodReserva(rs.getInt(1));
	            	reserva.setDataReserva(rs.getString(2));
	            	reserva.setHoraReserva(rs.getString(3));
		            reserva.setQtPessoas(rs.getInt(4));
		            reserva.setCpf(rs.getString(5));
		            reserva.setHoraPrevisaoTermino(invertDateHour(rs.getString(6)));
		            reserva.setTelefone(rs.getString(7));
		            reserva.setCodCategoria(rs.getInt(8));	            
	            	reserva.setStatusReserva(rs.getString(9));
		            reserva.setPagamento(rs.getString(10));
		            reserva.setResponsavel(rs.getString(11));
		            reserva.setEmail(rs.getString(12));
		            reserva.setNomeCategoria(rs.getString(13));
    	            listreserva.add(reserva);

            	}while(rs.next());    
            }    
            
            stm.close();
		}catch (Exception e){	
			e.printStackTrace();
			System.out.println("erro");
		}
		
        return listreserva;  
	}
	
	
	
	public List<Reserva> consultarTodasReservas() throws SQLException {
		
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();       
        String sqlSelect = "SELECT DISTINCT R.COD_RESERVA, DATE_FORMAT(R.DATA_RESERVA, '%d/%m/%Y') AS DATA, DATE_FORMAT(R.HORA_RESERVA, '%T'), "
        				 + "R.QTD_PESSOAS, R.CPF, R.HORA_PREVISAO_TERMINO, R.TELEFONE, C.COD_CATEGORIA, R.STATUS_RESERVA, R.PAGAMENTO, R.NOME_RESPONSAVEL, "
        				 + "R.EMAIL, C.NOME_CATEGORIA FROM RESERVA R JOIN CATEGORIA C ON R.COD_CATEGORIA = C.COD_CATEGORIA WHERE R.DATA_RESERVA >= CURDATE() AND R.DATA_RESERVA <= (CURDATE() + 60)"
        				 + "ORDER BY R.DATA_RESERVA, R.HORA_RESERVA;";

        ResultSet rs = null;
        
        List<Reserva> listreserva = new ArrayList<Reserva>();
     
        try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
            rs = stm.executeQuery();
         
            if(!rs.next()){
            	//Não encontrado na base dados
                System.out.print("reservas não encontrada na base de dados");            
                return null;
                
            }else{
            	
            	do{
            		
            		Reserva reserva = new Reserva();
            		
		            reserva.setCodReserva(rs.getInt(1));
	            	reserva.setDataReserva(rs.getString(2));
	            	reserva.setHoraReserva(rs.getString(3));
		            reserva.setQtPessoas(rs.getInt(4));
		            reserva.setCpf(rs.getString(5));
		            reserva.setHoraPrevisaoTermino(invertDateHour(rs.getString(6)));
		            reserva.setTelefone(rs.getString(7));
		            reserva.setCodCategoria(rs.getInt(8));	            
	            	reserva.setStatusReserva(rs.getString(9));
		            reserva.setPagamento(rs.getString(10));
		            reserva.setResponsavel(rs.getString(11));
		            reserva.setEmail(rs.getString(12));
		            reserva.setNomeCategoria(rs.getString(13));
		            listreserva.add(reserva);
		            
            	}while(rs.next());    
            }    
            
            stm.close();
		}catch (Exception e){		
			e.printStackTrace();
			System.out.println("erro");
		}
		
        return listreserva;  
	}
	
	
	public List<Reserva> consultarReservaCodigo(int codReserva) throws SQLException {
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();       
        String sqlSelect = "SELECT DISTINCT R.COD_RESERVA, DATE_FORMAT(R.DATA_RESERVA, '%d/%m/%Y') AS DATA, DATE_FORMAT(R.HORA_RESERVA,'%T'), "
        				 + "R.QTD_PESSOAS, R.CPF, R.HORA_PREVISAO_TERMINO, R.TELEFONE, C.COD_CATEGORIA, R.STATUS_RESERVA, R.PAGAMENTO, R.NOME_RESPONSAVEL, "
        				 + "R.EMAIL, C.NOME_CATEGORIA FROM RESERVA R JOIN CATEGORIA C ON R.COD_CATEGORIA = C.COD_CATEGORIA WHERE R.COD_RESERVA=? "
        				 + "ORDER BY R.DATA_RESERVA, R.HORA_RESERVA;";
        
        ResultSet rs = null;
        List<Reserva> listreserva = new ArrayList<Reserva>();

        try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
            stm.setInt(1, codReserva);
            rs = stm.executeQuery();
         
            if(!rs.next()){
            	//Não encontrado na base dados
                System.out.print("reservas não encontrada na base de dados");            
                return null;
                
            }else{
	            
            	do{	
            		
            		Reserva reserva = new Reserva();
            		
		            reserva.setCodReserva(rs.getInt(1));
	            	reserva.setDataReserva(rs.getString(2));
	            	reserva.setHoraReserva(rs.getString(3));
		            reserva.setQtPessoas(rs.getInt(4));
		            reserva.setCpf(rs.getString(5));
		            reserva.setHoraPrevisaoTermino(invertDateHour(rs.getString(6)));
		            reserva.setTelefone(rs.getString(7));
		            reserva.setCodCategoria(rs.getInt(8));	            
	            	reserva.setStatusReserva(rs.getString(9));
		            reserva.setPagamento(rs.getString(10));
		            reserva.setResponsavel(rs.getString(11));
		            reserva.setEmail(rs.getString(12));
		            reserva.setNomeCategoria(rs.getString(13));
		            listreserva.add(reserva);
	            }while(rs.next());    

            }    
            
		}catch (Exception e){			
			System.out.println("erro");
		}
		
        return listreserva;  
	}
	
	
public List<Reserva> obterComprovanteDaReserva(int codReserva) throws SQLException {
		
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();       
        String sqlSelect = "SELECT COMPROVANTE FROM RESERVA WHERE COD_RESERVA=?;";
        ResultSet rs = null;
        List<Reserva> comprovante = new ArrayList<Reserva>();
        
        try{
        	PreparedStatement stm = conn.prepareStatement(sqlSelect);
            stm.setInt(1, codReserva);
            rs = stm.executeQuery();
         
            if(rs.next()){
            	Reserva reserva = new Reserva();
            	reserva.setComprovante(rs.getString(1));
            	comprovante.add(reserva);
		    }    
            
		}catch (Exception e){			
			System.out.println("erro");
		}
		
        return comprovante;  
	}



	//Verificar a chamada da procedure	
	public List<Reserva> reservasImpactantes (Reserva reserva) throws SQLException{ 
		
		List<Reserva> listReserva = new ArrayList<Reserva>();
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		
		String sqlSelect = "CALL RESERVAS_IMPACTANTES (?,?,?)";
		
		try{
			
			ResultSet rs = null;
			
			String horaReserva = reserva.getDataReserva() + " " + reserva.getHoraReserva();
			String horaPrevisaoTermino = reserva.getDataReserva() + " " + reserva.getHoraPrevisaoTermino();

		
			CallableStatement cs = conn.prepareCall(sqlSelect);	
			cs.setString(1, reserva.getDataReserva());
			cs.setString(2, horaReserva);
			cs.setString(3, horaPrevisaoTermino);
			
			rs = cs.executeQuery();
			
			System.out.println(cs);
			
		    if(!rs.next()){
		    	return null;
		    }else{
		    	do{
		    		Reserva reservaAux = new Reserva();
		    		reservaAux.setCodReserva(rs.getInt(1));
		    		reservaAux.setNomeCategoria(rs.getString(2));
		    		reservaAux.setQtPessoas(rs.getInt(3));
		    		reservaAux.setHoraReserva(rs.getString(4));
		    		reservaAux.setHoraPrevisaoTermino(rs.getString(5));

					listReserva.add(reservaAux);
		    	}while(rs.next());
		    	
		    	conn.close();
		    }	
		    	
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return listReserva;
		
	}
			
	
}	
