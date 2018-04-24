package com.savingtime.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static com.savingtime.utils.Utilidades.SUCCESS;
import com.savingtime.model.Relatorio;

public class Excel {

	public int expExcel(List<Relatorio> relat) {

		
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet firstSheet = workbook.createSheet("Aba1");

		FileOutputStream fos = null;

		try {
			String TEMPFOLDER = System.getenv("OPENSHIFT_TMP_DIR");
			fos = new FileOutputStream(new File(TEMPFOLDER+"relatorio.xls"));
			HSSFRow column = firstSheet.createRow(0);
			
			column.createCell(0).setCellValue( "DATA");
			column.createCell(1).setCellValue( "HORA CHECKIN");
			column.createCell(2).setCellValue( "HORA ATENDIMENTO");
			column.createCell(3).setCellValue( "HORA CHECKOUT");
			column.createCell(4).setCellValue( "QUANTIDADE PESSOAS");
			column.createCell(5).setCellValue( "TELEFONE");
			column.createCell(6).setCellValue( "STATUS ATENDIMENTO");
			column.createCell(7).setCellValue( "Nº MESA");
			column.createCell(8).setCellValue( "NOME CATEGORIA");
			column.createCell(9).setCellValue( "CLIENTE");
			column.createCell(10).setCellValue( "CODIGO RESERVA");
			column.createCell(11).setCellValue( "PAGAMENTO");
			column.createCell(12).setCellValue( "STATUS RESERVA");
			

			for (int i =0; i < relat.size(); i++) {
				HSSFRow row = firstSheet.createRow(i+1);
			
				row.createCell(0).setCellValue(relat.get(i).getData());
				row.createCell(1).setCellValue(relat.get(i).getCheckin());
				row.createCell(2).setCellValue(relat.get(i).getAtendimento());
				row.createCell(3).setCellValue(relat.get(i).getCheckout());
				row.createCell(4).setCellValue(relat.get(i).getPessoas());
				row.createCell(5).setCellValue(relat.get(i).getTelefone());
				row.createCell(6).setCellValue(relat.get(i).getStatusAtendimento());
				row.createCell(7).setCellValue(relat.get(i).getMesa());
				row.createCell(8).setCellValue(relat.get(i).getNomeCategoria());
				row.createCell(9).setCellValue(relat.get(i).getCliente());
				row.createCell(10).setCellValue(relat.get(i).getCodigoReserva());
				row.createCell(11).setCellValue(relat.get(i).getPagamento());
				row.createCell(12).setCellValue(relat.get(i).getStatusReserva());

			} // fim do for

			workbook.write(fos);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro ao exportar arquivo");
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// fim do metodo exp
		return SUCCESS;
	}

}



