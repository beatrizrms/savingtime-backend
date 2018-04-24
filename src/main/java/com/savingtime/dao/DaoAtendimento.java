package com.savingtime.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.savingtime.conexao.AcessoBD;
import com.savingtime.model.Atendimento;
import com.savingtime.model.Reserva;

import static com.savingtime.utils.Utilidades.ERROR;
import static com.savingtime.utils.Utilidades.SUCCESS;
import static com.savingtime.utils.Utilidades.invertDateHour;




public class DaoAtendimento {
	
	public int efetuarCheckIn(Atendimento atend) throws SQLException {	
		ResultSet rs;
		int codAtendimento = -1;
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		String sqlSelect2 = "SELECT MAX(COD_ATENDIMENTO) AS 'CÓDIGO DO ATENDIMENTO' FROM ATENDIMENTO";

		if(atend.getNumReserva() != null){
		
			String sqlSelect = "CALL CHECKIN_RESERVA (?,?,?,?,?,?)";
			
			try{
	
				CallableStatement cs = conn.prepareCall(sqlSelect);	
				cs.setInt(1, atend.getCodCategoria());
				cs.setString(2, atend.getNomeResponsavel());
				cs.setString(3, atend.getStatus());
				cs.setString(4, atend.getTelefone());
				cs.setInt(5, atend.getNumReserva());
				cs.setInt(6, atend.getQtPessoas());	
				cs.executeUpdate();
				cs.close();
				
				PreparedStatement stm = conn.prepareStatement(sqlSelect2);
				rs = stm.executeQuery();
				
				while(rs.next()) {
					codAtendimento = rs.getInt(1);				
				}
	
				
			}catch (Exception e){
				e.printStackTrace();
				return ERROR;
			}
			return codAtendimento;
			
		}else{
			
			try{
				
				String sqlSelect = "CALL EFETUAR_CHECKIN (?,?,?,?,?)";		
				CallableStatement cs = conn.prepareCall(sqlSelect);	
		
				cs.setInt(1, atend.getCodCategoria());
				cs.setString(2, atend.getNomeResponsavel());
				cs.setString(3, atend.getStatus());
				cs.setString(4, atend.getTelefone());
				cs.setInt(5, atend.getQtPessoas());
				cs.executeUpdate();
				cs.close();
				
				
				PreparedStatement stm = conn.prepareStatement(sqlSelect2);
				rs = stm.executeQuery();
				
				while(rs.next()) {
					codAtendimento = rs.getInt(1);				
				}
		
			}catch (Exception e){
				e.printStackTrace();
				return ERROR;
			}
		}	
			
		return codAtendimento;
	}

	public int iniciarAtendimento(int mesa,int codigo,String statusAtendimento, String statusMesa, String tmaFormatado) throws SQLException {	
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		String sqlSelect = "call INICIAR_ATENDIMENTO (?,?,?,?,?)";

		try{
			        
			CallableStatement cs = conn.prepareCall(sqlSelect);	
			cs.setInt(1, mesa);
			cs.setInt(2, codigo);
			cs.setString(3, statusMesa);
			cs.setString(4, statusAtendimento);
			cs.setString(5, tmaFormatado);
			
			cs.executeUpdate();
			cs.close();
		}catch (Exception e){
			e.printStackTrace();
			return ERROR;

		}
	
		return SUCCESS;

	}
	
	
	public int efetuarCheckOut(int codigo,String statusAtendimento,String statusMesa) throws SQLException {	
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		String sqlSelect = "call EFETUAR_CHECKOUT (?,?,?)";

		try{
			        
			CallableStatement cs = conn.prepareCall(sqlSelect);	
			cs.setInt(1, codigo);
			cs.setString(2, statusMesa);
			cs.setString(3, statusAtendimento);
			cs.executeUpdate();
			cs.close();
		}catch (Exception e){
			e.printStackTrace();
			return ERROR;

		}
	
		return SUCCESS;

	}
	public List<DateTime> tempoMedioDeAtendimento(int codCategoria, int diaSemana) throws SQLException {	
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		
		List<DateTime> listHora = new ArrayList<DateTime>();
		
		try{
			
			SimpleDateFormat dtf = new  SimpleDateFormat("HH:mm:ss");
			
			if(codCategoria != 0){
				
				ResultSet rs = null;
				String sqlSelect = "CALL LISTA_TEMPO_ATENDIMENTO_CATEGORIA(?);";
				CallableStatement cs = conn.prepareCall(sqlSelect);	
				cs.setInt(1, codCategoria);
				rs = cs.executeQuery();
				
				while(rs.next()){
					DateTime dt;	
					if(rs.getString(1) != null) {
						dt = new DateTime(dtf.parse(rs.getString(1)));
						listHora.add(dt);	
					}
				}
				
				conn.close();
			
			}else{
				
				ResultSet rs = null;
				String sqlSelect = "CALL LISTA_TEMPO_ATENDIMENTO_DIA_SEMANA(?);";
				CallableStatement cs = conn.prepareCall(sqlSelect);	
				cs.setInt(1, diaSemana);
				rs = cs.executeQuery();
				
				while(rs.next()){
					DateTime dt;		
					if(rs.getString(1) != null) {
						dt = new DateTime(dtf.parse(rs.getString(1)));
						listHora.add(dt);
					}
					
				}
				
				conn.close();			
			}
		        	
		}catch (Exception e){
			e.printStackTrace();
		}		
			
		return listHora;
	}

	public List<DateTime> listaPrevisaoCheckoutData(String data) throws SQLException {	
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		
		List<DateTime> listHora = new ArrayList<DateTime>();
		
		try{
			
			SimpleDateFormat dtf = new  SimpleDateFormat("HH:mm:ss");
				ResultSet rs = null;
				String sqlSelect = "CALL LISTA_PREVISAO_CHECKOUT_DATA(?);";
				CallableStatement cs = conn.prepareCall(sqlSelect);	
				cs.setString(1, data);
				rs = cs.executeQuery();
				
				while(rs.next()){
					DateTime dt;		
					if(rs.getString(1) != null) {
						dt = new DateTime(dtf.parse(rs.getString(1)));
						listHora.add(dt);
					}
				}
				
				conn.close();			
		        	
		}catch (Exception e){
			e.printStackTrace();
		}		
			
		return listHora;
	}
	
		
	public List<Atendimento> listaAtendimento(String status) throws SQLException {	
		
		List<Atendimento> listatend = new ArrayList<Atendimento>();
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		String sqlSelect = "SELECT A.COD_ATENDIMENTO, C.NOME_CATEGORIA, TIME_FORMAT(A.HORA_CHECKIN ,'%T'), TIME_FORMAT(A.HORA_ATENDIMENTO,'%T'), "+
						   "A.NOME_RESPONSAVEL, A.QTD_PESSOAS, DATE_FORMAT(A.DATA_EVENTO,'%d/%m/%Y') as DATA, A.STATUS_ATENDIMENTO, A.PREVISAO_CHECKOUT, "+
						   "A.TELEFONE, A.COD_RESERVA,M.NUM_MESA, C.COD_CATEGORIA FROM CATEGORIA C JOIN ATENDIMENTO A ON C.COD_CATEGORIA = A.COD_CATEGORIA "+ 
						   "JOIN MESA_ATENDIMENTO MA ON A.COD_ATENDIMENTO = MA.COD_ATENDIMENTO "+
						   "JOIN MESA M ON M.COD_MESA = MA.COD_MESA WHERE A.STATUS_ATENDIMENTO=? "+
						   "ORDER BY A.HORA_ATENDIMENTO AND A.QTD_PESSOAS;";
		try{
		
			ResultSet rs = null;
			PreparedStatement stm = conn.prepareStatement(sqlSelect);
            stm.setString(1, status);
			rs = stm.executeQuery();
			
	        if(!rs.next()){
	        	return null;
	        }else{
	        	do{

	        		Atendimento atend = new Atendimento();
					atend.setCodigoAtendimento(rs.getInt(1));
					atend.setNomeCategoria(rs.getString(2));
					atend.setHoraCheckIn(rs.getString(3));
					atend.setHoraAtendimento(rs.getString(4));
					atend.setNomeResponsavel(rs.getString(5));
					atend.setQtPessoas(rs.getInt(6));
					atend.setData(rs.getString(7));
					atend.setStatus(rs.getString(8));
					atend.setHoraPrevisaoCheckOut(invertDateHour(rs.getString(9)));
					atend.setTelefone(rs.getString(10));
					atend.setNumReserva(rs.getInt(11));
					atend.setNumMesa(rs.getInt(12));
					atend.setCodCategoria(rs.getInt(13));

					listatend.add(atend);
	        	}while(rs.next());
	        	
	        	stm.close();
	        }	
	        	
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return listatend;
	}
	
	
	public List<Atendimento> filaEspera(String status) throws SQLException {	
			
			List<Atendimento> filaEspera = new ArrayList<Atendimento>();
			AcessoBD bd = new AcessoBD();
			Connection conn = bd.obtemConexao();	
			String sqlSelect = "SELECT A.COD_ATENDIMENTO, C.NOME_CATEGORIA, TIME_FORMAT(A.HORA_CHECKIN,'%T'), " +
								"A.NOME_RESPONSAVEL, A.QTD_PESSOAS, DATE_FORMAT(A.DATA_EVENTO,'%d/%m/%Y') as DATA, A.STATUS_ATENDIMENTO, "+
								"A.TELEFONE, A.COD_RESERVA, C.COD_CATEGORIA FROM ATENDIMENTO A JOIN CATEGORIA C ON C.COD_CATEGORIA = A.COD_CATEGORIA WHERE STATUS_ATENDIMENTO=? "+
								"ORDER BY A.HORA_CHECKIN AND A.QTD_PESSOAS; ";
			try{
			
				ResultSet rs = null;
				PreparedStatement stm = conn.prepareStatement(sqlSelect);
	            stm.setString(1, status);
		        rs = stm.executeQuery();
		     
		        if(!rs.next()){
		
		        }else{
		        	do{
		        		Atendimento atend = new Atendimento();
		        		atend.setCodigoAtendimento(rs.getInt(1));
						atend.setNomeCategoria(rs.getString(2));
						atend.setHoraCheckIn(rs.getString(3));
						atend.setNomeResponsavel(rs.getString(4));
						atend.setQtPessoas(rs.getInt(5));
						atend.setData(rs.getString(6));
						atend.setStatus(rs.getString(7));
						atend.setTelefone(rs.getString(8));
						atend.setNumReserva(rs.getInt(9));
						atend.setCodCategoria(rs.getInt(10));
						filaEspera.add(atend);
						
		        	}while(rs.next());
		        	
		        	stm.close();
		        }	
		        	
			}catch (Exception e){
				e.printStackTrace();
			}
			
			return filaEspera;
		}

	
	public int qtdFilaEspera(String status) throws SQLException {
		AcessoBD bd = new AcessoBD();	
		Connection conn = bd.obtemConexao();
        int quantidade = 0; 
        String sqlSelect = "SELECT COUNT(*) FROM ATENDIMENTO WHERE STATUS_ATENDIMENTO = ?;";
        				 

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

	
	
	
	
	public int cancelarCheckIn(int codigo, String status) throws SQLException {	
		AcessoBD bd = new AcessoBD();
		Connection conn = bd.obtemConexao();	
		String sqlSelect = "UPDATE ATENDIMENTO SET  HORA_ATENDIMENTO = CURRENT_TIMESTAMP(), HORA_CHECKOUT = CURRENT_TIMESTAMP(),"
							+ " STATUS_ATENDIMENTO=? WHERE COD_ATENDIMENTO=?;";
		
		try{
			PreparedStatement stm = conn.prepareStatement(sqlSelect);
				stm.setString(1, status);
				stm.setInt(2, codigo);
				stm.executeUpdate();
				stm.close();
		}catch (Exception e){
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;	
	}
	
}