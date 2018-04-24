package com.savingtime.service;

import com.savingtime.dao.DaoDataComemorativa;
import com.savingtime.model.DataComemorativa;
import static com.savingtime.utils.Utilidades.formatarDataBanco;

import java.text.ParseException;;

public class ServiceDataComemorativa {
	
	public DataComemorativa consultarDataComemorativa(String data) throws ParseException{
		DaoDataComemorativa daoDataCom = new DaoDataComemorativa();
		return daoDataCom.consultarDataComemorativa(formatarDataBanco(data));
	}
}


