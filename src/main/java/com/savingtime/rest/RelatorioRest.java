package com.savingtime.rest;

import java.io.File;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.savingtime.service.ServiceRelatorio;
import com.savingtime.utils.RestReturn;


@Path("/relatoriorest") 
public class RelatorioRest {
	

	@GET
	@Path("/gerar/relatorio/{dataInicio}/{dataFinal}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response gerarRelatorio(@PathParam("dataInicio")String dataInicio, @PathParam("dataFinal")String dataFinal) throws SQLException {
		
		ServiceRelatorio relatorio = new ServiceRelatorio();
		
		try{
			int retorno = relatorio.gerarRelatorio(dataInicio, dataFinal);
				
			if(retorno == 0){
				return Response.ok(new RestReturn(Status.NOT_ACCEPTABLE, null,"Data inicial deve ser menor que a data final")).build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(new RestReturn(Status.OK, null , "Gerado com sucesso")).build();
	}
	
	
	@GET
    @Path("/download/xls")
    @Produces("application/vnd.ms-excel")
    public Response downloadExcelFile() {
 
		String TEMPFOLDER = System.getenv("OPENSHIFT_TMP_DIR");
		File file = new File((TEMPFOLDER+"relatorio.xls"));
        ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"relatorio.xls\"");
        return responseBuilder.build();
    }
	
	

}
