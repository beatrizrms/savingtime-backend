package com.savingtime.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import static com.savingtime.utils.Utilidades.invertDateHour;
import com.savingtime.model.Reserva;

public class SendEmail {
	
	public SendEmail(){
		
	}
	
	public void enviarEmail(Reserva reserva) throws ParseException{
	
		SimpleEmail email = new SimpleEmail();
		
		String msg = "Olá Sr(a) "+reserva.getResponsavel()+"."+"\n\n"+"Agradeçemos a preferência pelo nosso restaurante. "
																+ "Segue abaixo as informações de sua reserva:\n\n"
																+ "Data/Hora: "+invertDateHour(reserva.getHoraReserva())+"\n"
																+ "Núm. Reserva: "+reserva.getCodReserva()+"\n"
																+ "Quantidade de pessoas: "+reserva.getQtPessoas()+"\n"
																+ "Categoria: "+reserva.getNomeCategoria()+"\n\n"
																+"Atenciosamente,\n\nSavingtime";
		try {
			email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
			email.setSmtpPort(587);  
			email.addTo(reserva.getEmail());
			email.setAuthentication("savingtimetcc@gmail.com", "goiaba15");
			email.setStartTLSEnabled(true);
			email.setSSLOnConnect(true);  
			email.setFrom("savingtimetcc@gmail.com", "Savingtime"); // remetente
			email.setSubject("SavingTime - Informações sobre a reserva (Não responda a este e-mail)"); // assunto do e-mail
			email.setMsg(msg); //conteudo do e-mail
			email.getMailSession().getProperties().setProperty("127.0.0.1", "8080");
			email.send(); //envia o e-mail
	
		} catch (EmailException e) {
			e.printStackTrace();
		} 
	}
}
