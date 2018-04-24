package com.savingtime.service;

import java.sql.SQLException;
import java.text.ParseException;

import com.savingtime.dao.DaoRelatorio;
import com.savingtime.utils.Excel;
import static com.savingtime.utils.Utilidades.validarDataRelatorio;
import static com.savingtime.utils.Utilidades.formatarDataBanco;


public class ServiceRelatorio {
	
	public int gerarRelatorio(String dataInicio, String dataFinal) throws SQLException, ParseException{
		if(!validarDataRelatorio(dataInicio,dataFinal)){
			return 0;
		}
		
		DaoRelatorio relatorio = new DaoRelatorio();
		Excel excel = new Excel();

		return excel.expExcel(relatorio.gerarRelatorio(formatarDataBanco(dataInicio), formatarDataBanco(dataFinal)));
		
	}	
}
