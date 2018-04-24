package com.savingtime.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.util.SystemOutLogger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utilidades {

	//STATUS PARA RESERVAS

	public static final String RESERVADA = "Reservada";

	//TRATAMENTO DE ERROS
	
	public static final int ERROR = -1;
	public static final int SUCCESS = 1;
	public static final int NAOPERMITIDO = -2;
	public static final int IMPEDIDO = 0;
	public static final int NOT_FOUND = 404;
	


	//STATUS PARA MESAS
	public static final String INATIVO = "Inativa";
	public static final String ATIVA_LIVRE = "Ativa - Livre";
	public static final String ATIVA_OCUPADA = "Ativa - Ocupada";
	public static final String ATIVA_RESERVADA = "Ativa - Reservada";

	//STATUS PARA ATENDIMENTO - CHECKOUT
	
	public static final String CANCELADO = "Cancelado";
	public static final String DESISTENCIA = "Desistencia";
	public static final String CONCLUIDO = "Concluído";
	public static final String ESPERA = "Em espera";
	public static final String ATENDIMENTO = "Em atendimento";
		
	public static List<String> getAllStatusMesa() {
		List<String> status = new ArrayList<String>();
		status.add(INATIVO);
		status.add(ATIVA_LIVRE);
		status.add(ATIVA_OCUPADA);
		status.add(ATIVA_RESERVADA);

		return status;
	 }
	
	 //STATUS PARA ATENDIMENTO	
	 public static List<String> getAllStatusAtendimento() {
		List<String> status = new ArrayList<String>();
		status.add(CANCELADO);
		status.add(DESISTENCIA);
		status.add(CONCLUIDO);
		return status;
	 }
	
	
	//FORMATAÇÃO DE DATA PARA INSERÇÃO NO BANCO
	 public static String formatarDataBanco(String data) throws ParseException{
		
		SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy"); 
		Date date = dt.parse(data); 
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");
		return dt1.format(date);
	 }
	
	
	 public static boolean validarDataRelatorio(String dataInicio, String dataFinal) throws ParseException {
			
		DateTimeFormatter f = DateTimeFormat.forPattern("dd-MM-yyyy");
	
		LocalDate inicio = null;
		LocalDate fim = null;
		
		inicio = f.parseLocalDate(dataInicio);
		fim = f.parseLocalDate(dataFinal);
		
		boolean retorno = inicio.isBefore(fim);
		
		return retorno;
		
	 }
	 
	 
	 public static boolean validarDataReserva(String dataReserva,String horaReserva) throws ParseException {
		 	 	
		SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");

		Date dataHoje = new Date();
		Date horaHoje = new Date();
		
		String dataHoje2 = formatDate.format(dataHoje);
		
		boolean validacao = validarDataRelatorio(dataReserva, formatDate.format(dataHoje));

		
		
		if(validacao){
			return false;
		
		}else	//verifica se a hora da reserva é maior ou igual a hora atual para a data de hoje
			if(dataHoje2.equals(dataReserva)){
				if(horaReserva.compareTo(formatHour.format(horaHoje)) < 0){
					System.out.println(dataReserva);
					System.out.println(horaReserva);
					return false;
				}
			}
        return true;
	  }
	 
	 /** 
	  * Espera somente datas no formato yyyy-MM-dd
	  * @param dataReserva
	  * @return
	  * @throws ParseException
	  */
	 public static int retornarDiaSemana(String dataReserva) throws ParseException{
		 
		 SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
		 Date dataAux = f.parse(dataReserva);
		 dataReserva = f.format(dataAux);
		 Date data = new Date();
		 GregorianCalendar cal = new GregorianCalendar();

		 data = f.parse(dataReserva);
		 cal.setTime(data);		
		 		 
		 int dia = cal.get(Calendar.DAY_OF_WEEK);
	 
		 return dia;
		 
	 }
	 
	 
	 public static Date formatarHora(String hora) throws ParseException{
		
		 SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");
			
			Date date = formatHour.parse(hora); 
			return date; 
	 
	 }
	 
	 public static String formatarHoraString(int hora) {
		if(hora < 10) {
			return "0" + hora  + ":00:00";
		}
		
		return "" + hora  + ":00:00";
		
	}
	 
	 
	 public static String minutesParseTime(long minutos) {
			String FORMAT = "%02d:%02d:%02d";
			return String.format(FORMAT,
		            TimeUnit.MINUTES.toHours(minutos),
		            TimeUnit.MINUTES.toMinutes(minutos%60),
		            0
		            );
		 }
	 
	 
	 public static String minutesParseTimeHome(long minutos) {
			String FORMAT = "%02d:%02d";
			return String.format(FORMAT,
		            TimeUnit.MINUTES.toHours(minutos),
		            TimeUnit.MINUTES.toMinutes(minutos%60)
		            );
		 }
	
	
     /**
	 * Somar hora recebendo um horario para calcular quanto tempo um atendimento duraria
	 * @param totalMinutos
	 * @param horaSomar
	 * @return
	 * @throws ParseException
	 */
	public static String somarHora(double totalMinutos, String horaSomar) throws ParseException{
		
		java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
	    gc.setTime(new java.util.Date());
	    
	    gc.setTime(formatarHora(horaSomar));
	    gc.add(java.util.Calendar.MINUTE, (int) totalMinutos);
	    
		 SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");
		 
	    return formatHour.format(gc.getTime());
	}
	 
	
	public static int converterHoraStringToInt(String horario) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    Date data = new Date();
	    
	    data = sdf.parse(horario);
		int horarioInt = data.getHours();
		
		return horarioInt;
	}

	public static String formatDateToString(Date data) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");   
	    String retorno;
	    retorno = sdf.format(data);
		
		return retorno;
	}
	
	public static String invertDateHour(String dataHora) throws ParseException{
		String retorno;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = format1.parse(dataHora);
		retorno = format2.format(date);		
		return retorno;
	}
	
	 
}
