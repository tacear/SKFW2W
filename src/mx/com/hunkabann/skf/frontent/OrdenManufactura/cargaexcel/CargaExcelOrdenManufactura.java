package mx.com.hunkabann.skf.frontent.OrdenManufactura.cargaexcel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.mapeo.TbCanal;
import mx.com.hunkabann.skf.mapeo.TbEmpleado;
import mx.com.hunkabann.skf.mapeo.TbEmpresa;
import mx.com.hunkabann.skf.mapeo.TbMaquinaDispositivo;
import mx.com.hunkabann.skf.mapeo.TbOperacionOrdenManufactura;
import mx.com.hunkabann.skf.mapeo.TbOrdenManufactura;
import mx.com.hunkabann.skf.mapeo.TbPlaca;
import mx.com.hunkabann.skf.mapeo.TbPlacaOm;
import mx.com.hunkabann.skf.mapeo.TbPlanControl;
import mx.com.hunkabann.skf.mapeo.TbProceso;
import mx.com.hunkabann.skf.mapeo.TbProductoTerminado;
import mx.com.hunkabann.skf.mapeo.TbSubArea;
import mx.com.hunkabann.skf.mapeo.TbUbicacionProcesoOm;
import mx.com.hunkabann.skf.mapeo.TbUsuario;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Filedownload;



public class CargaExcelOrdenManufactura {
	
	public static String pathIn = "";
	public static String pathOut = "";
	
	public static String Serie = "";
	public static String Folio = "";
	public static String Telefono = "";
	
	static String AutoPermSCT = "";
	static String AutoNumPermisoSCT = "";
	
	static String IdentConfigVehicular = "";
	static String IdentPlacaVM = "";
	static String IdentAnioModeloVM = "";
	
	static String SegAseguraRespCivil	 = "";
	static String SegPolizaRespCivil	 = "";
	static String SegAseguraMedAmbiente = "";	
	static String SegPolizaMedAmbiente = "";	
	static String SegAseguraCarga	 = "";
	static String SegPolizaCarga = "";	
	static String SegPrimaSeguro = "";
	
	static String RemSubTipoRem = "";	
	static String RemPlaca = "";
	
	static String TipoFigura = "";	
	static String RFCFigura = "";	
	static String NumLicencia = "";	
	static String NombreFigura = "";	
	static String NumRegIdTribFigura = "";	
	static String ResidenciaFiscalFigura = "";	
	static String Calle = "";	
	static String NumeroExterior = "";	
	static String NumeroInterior = "";	
	static String Colonia = "";	
	static String Localidad = "";	
	static String Referencia = "";	
	static String Municipio = "";	
	static String Estado = "";	
	static String Pais	 = "";
	static String CodigoPostal = "";
	static boolean Mercancias = false;
	
	String KeySell = "";
	
	static DataFormatter formatter = new DataFormatter(); 
	
	static PrintWriter writer = null;
	
	UsuarioService UserServ = new UsuarioService();


	
public void procesaExcelOrdenManufactura(String URL) throws FileNotFoundException, IOException {
	
	int nukidprodTerm = 0 ;
	
	
	try {
		
		File fichero=new File(URL);
		String Titulo = fichero.getName();
		
		String Nombre_xslx = Titulo.substring(0, Titulo.length() - 4);
		
//		String realString = Sessions.getCurrent().getWebApp().getRealPath("Reporte_de_Orden_de_Compra.xls");
        String ruta = Sessions.getCurrent().getWebApp().getRealPath(Nombre_xslx+"txt");
//        String contenido = "Contenido de ejemplo";
        File file = new File(ruta);
        // Si el archivo no existe es creado
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        
        
    
	
	
			String Error = "";
		
			TbOrdenManufactura ordenMAnu = new TbOrdenManufactura();
			TbEmpresa empresa = new TbEmpresa();
			TbEmpleado empleado = new TbEmpleado();
			TbPlaca placa = new TbPlaca();
			TbPlacaOm placaOM = new TbPlacaOm();
			TbCanal canal = new TbCanal();
			TbMaquinaDispositivo maqDisp = new TbMaquinaDispositivo();
			TbSubArea subarea = new TbSubArea();
			TbUbicacionProcesoOm UbicaOrdenManu = new TbUbicacionProcesoOm();
			TbPlanControl plancontrol = new TbPlanControl();
			TbUsuario user = new TbUsuario();
			TbProductoTerminado prodTerm = new TbProductoTerminado();
			TbOperacionOrdenManufactura operaOrden = new TbOperacionOrdenManufactura();
			
			
//			bdEmpresa.setNukIdEmpresa(1);
		
		Integer total=0;
	    	
//	 		String archivoExcel=ruta+ File.separator +archivo;
	    	  String archivoExcel = URL;
	 		System.out.println("**********************************carga "+archivoExcel);
	 		
	 		Workbook workbook = null;
			if(archivoExcel.indexOf(".xlsx")!=-1)
				workbook = new XSSFWorkbook(new FileInputStream(archivoExcel)) ;
			else if(archivoExcel.indexOf(".xls")!=-1)
				workbook = new HSSFWorkbook(new FileInputStream(archivoExcel)) ;
			else if(archivoExcel.indexOf(".csv")!=-1)
			workbook = new HSSFWorkbook(new FileInputStream(archivoExcel)) ;
			

			
		
			
			
			
			//Esta es la hoja
			Sheet sheet = workbook.getSheetAt(0);
			
			System.out.println("Numero de Lineas: " + sheet.getLastRowNum());
			
			total=sheet.getLastRowNum()+1;
			
			
			



			
			for(int i =0; i<=sheet.getLastRowNum(); i++){
				//Renglon				
					Row row = sheet.getRow(i);

					
					//Linea 1
//					Cell DatosCelda = row.getCell(0);
					String Usuario = row.getCell(0).toString();
					if(Usuario.equals("") || Usuario == null){
						
					}else{

						
						
//						if (UserServ.existeProdTerm("chSku", Usuario)) {
//							
//								System.out.println("El Producto Terminado ya Existe");
////								return;
//							
//						}else{
						
//						DecimalFormat mf = new DecimalFormat("#0.000");
//						String s = mf.parse(row.getCell(11).toString()); // "0,850"
//						
//						System.out.println("s ----------------------------------->> "+ s);
//
//						s = s.replace(',', '.'); // "0.850"

						double conv = Double.parseDouble(row.getCell(11).toString()); //0.85
						
						
//						 double input = 9476.2351;
//					      double roundedDbl = Precision.round(conv,2);  
//					      System.out.println("Rounded Double value: "+roundedDbl); 
					      
					      BigDecimal bd = new BigDecimal(conv).setScale(2, RoundingMode.HALF_UP);
					      double val2 = bd.doubleValue();
					      System.out.println("Rounded Double value: "+val2);
						
						System.out.println("conv ----------------------------------->> "+ conv);
						System.out.println("s ----------------------------------->> "+ val2);
					
						
						System.out.println("Isla  "+row.getCell(0).toString()+"	NombrePlaneador	 "+row.getCell(1).toString().replace(".0", "")+"	Maquina	 "+row.getCell(2).toString()+"	Ch	 "+row.getCell(3).toString().replace(".0", "")+"	Fecha	 "+row.getCell(4).toString()+"	Placa	 "+row.getCell(5).toString().replace(".0", "")+"	Hule	 "+row.getCell(6).toString()+"	NoParte	 "+row.getCell(7).toString()+"	Orden	 "+row.getCell(8).toString()+"	Pack	 "+row.getCell(9).toString()+"	Total	 "+row.getCell(10).toString().replace(".0", "")+"	HrsOrden	 "+row.getCell(11).toString()+"	DiasSurtido	 "+row.getCell(12).toString()+"	EstExt	 "+row.getCell(13).toString()+"	EstInt	 "+row.getCell(14).toString()+"	Resorte	 "+row.getCell(15).toString()+"	PzHr" +row.getCell(16).toString());
						
						if(UserServ.existeIsla(Usuario)){
							subarea = UserServ.getIsla(Usuario);
							
						}else{
														
							Error = Error+ " No se encuentra la isla: " + row.getCell(0).toString() +" de la orden de manufactura: "+ row.getCell(8).toString()  +"\n";
							
//							System.out.println(Error);
						}
						
						if(UserServ.existeEmpleado(row.getCell(1).toString().replace(".0", ""))){
							
							empleado = UserServ.getEmpleado(row.getCell(1).toString().replace(".0", ""));
							
						}else{
														
							Error = Error+ " No se encuentra el empleado: " + row.getCell(1).toString() +" de la orden de manufactura: "+ row.getCell(8).toString()  +"\n";
							
//							System.out.println(Error);
						}
						
						if(UserServ.existeMaquina(row.getCell(2).toString())){
							
							maqDisp = UserServ.getMaquina(row.getCell(2).toString());
							
						}else{
														
							Error = Error+ " No se encuentra la maquina o dispositivo: " + row.getCell(2).toString() +" de la orden de manufactura: "+ row.getCell(8).toString()  +"\n";
							
//							System.out.println(Error);
						}
						
						if(UserServ.existeCanal(row.getCell(3).toString().replace(".0", ""))){
							
							canal = UserServ.getCanal(row.getCell(3).toString().replace(".0", ""));
							
						}else{
														
							Error = Error+ " No se encuentra el canal: " + row.getCell(2).toString() +" de la orden de manufactura: "+ row.getCell(8).toString()  +"\n";
							
//							System.out.println(Error);
						}
						
						if(UserServ.existePlaca(row.getCell(5).toString().replace(".0", ""))){
							
							placa = UserServ.getPlaca(row.getCell(5).toString().replace(".0", ""));
							
						}else{
														
							Error = Error+ " No se encuentra la Placa: " + row.getCell(2).toString() +" de la orden de manufactura: "+ row.getCell(8).toString()  +"\n";
							
//							System.out.println(Error);
						}
						
						if(UserServ.existeProductoTerm(row.getCell(7).toString())){

							
							nukidprodTerm = UserServ.getProdSKU_PT(row.getCell(7).toString());
							
							
							
							if(UserServ.existeProductoTermMatPrim(nukidprodTerm)){
								
							}else{
								
								Error = Error+ " No Existe Informacion de la materia que se necesita para hacer el numero de parte: " + row.getCell(7).toString() +" de la orden de manufactura: "+ row.getCell(8).toString()  +"\n";
								
//								System.out.println(Error);
								
							}
							
							
						}else{
														
							Error = Error+ " No se encuentra el numero de parte: " + row.getCell(2).toString() +" de la orden de manufactura: "+ row.getCell(8).toString()  +"\n";
							
//							System.out.println(Error);
						}
						
						
						if(UserServ.existeUbicaOM(subarea.getNukIdSubArea(),canal.getNukIdCanal(),maqDisp.getNukIdMaquinaDisp())){
							
							UbicaOrdenManu = UserServ.getUbicaOM(subarea.getNukIdSubArea(),canal.getNukIdCanal(),maqDisp.getNukIdMaquinaDisp());
							
						}else{
														
							Error = Error+ " No se encuentra la relacion en Ubicacion,maquina y canal de la orden de manufactura: "+ row.getCell(8).toString()  +"\n";
							
//							System.out.println(Error);
						}
						
						
						if(Error.trim().equals("")){
							
							if(UserServ.existeOrdenManu(row.getCell(8).toString())){
								
								Error = "Ya fue dada de alta la Orden de Manufactura : "+row.getCell(8).toString()+ " con anterioridad";
								
							}else{
															
								
							
							
								Date date = new Date();
								Timestamp ts = new Timestamp(date.getTime());
								
								ordenMAnu.setChOrdenManufactura(row.getCell(8).toString());
								ordenMAnu.setChPack(row.getCell(9).toString());
								ordenMAnu.setNuTotal(0);
	//							row.getCell(11).toString()
								ordenMAnu.setNuHrsOrden(val2);
								
								double dbsurtido = Double.parseDouble(row.getCell(12).toString()); 
										      
							    BigDecimal bdDiasSurt = new BigDecimal(dbsurtido).setScale(2, RoundingMode.HALF_UP);
							    double DiasSurtido = bdDiasSurt.doubleValue();
								
								ordenMAnu.setNuDiasSurtido(DiasSurtido);
								
								double dbnuPiezasXhora = Double.parseDouble(row.getCell(16).toString()); 
							      
							    BigDecimal bdnuPiezasXhora = new BigDecimal(dbnuPiezasXhora).setScale(2, RoundingMode.HALF_UP);
							    double nuPiezasXhora = bdnuPiezasXhora.doubleValue();
							    
								
								ordenMAnu.setNuPiezasXhora(nuPiezasXhora);
								ordenMAnu.setDtFechaAlta(ts);
								ordenMAnu.setNuActivo(false);
								empresa.setNukIdEmpresa(1);
								ordenMAnu.setTbEmpresa(empresa);
								
								
								
								ordenMAnu.setTbUsuario(UserServ.getUsuario(empleado.getTbPersona().getNukIdPersona()));
								plancontrol = UserServ.getPlanControl(row.getCell(7).toString());
								ordenMAnu.setTbPlanControl(plancontrol);
								
								prodTerm.setNukIdProdTerm(nukidprodTerm);
								ordenMAnu.setTbProductoTerminado(prodTerm);
								
								int Id_OrdenManu = 0;
								try {
									Id_OrdenManu = UserServ.saveOrdenManu(ordenMAnu);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										System.out.println(e.toString());
										e.printStackTrace();
									}
								
								ordenMAnu.setNukIdOrdenManufactura(Id_OrdenManu);
								
								
								placaOM.setTbOrdenManufactura(ordenMAnu);
								placaOM.setTbPlaca(placa);
								
								
								try {
									UserServ.saveOrdenManu(ordenMAnu);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										System.out.println(e.toString());
										e.printStackTrace();
									}
								
								operaOrden.setTbEmpresa(empresa);
								operaOrden.setTbUbicacionProcesoOm(UbicaOrdenManu);
								operaOrden.setTbUsuario(UserServ.getUsuario(empleado.getTbPersona().getNukIdPersona()));
								operaOrden.setTbOrdenManufactura(ordenMAnu);
								operaOrden.setNuCantidad(0);
								operaOrden.setDtFecha(ts);
								operaOrden.setNuStatus("CREADA_INICIAL_MAQ_UBICA");
							}
							
							
						}
						
//						Error = Error+"\n";
//						Error = Error+"\n";
						
						
						
//				        bdProductos.setTbEmpresa(bdEmpresa);
//				        bdProductos.setChSku(row.getCell(0).toString());
//				        bdProductos.setChDescripcion(row.getCell(1).toString());
//				        double dobleTipoCamb = Double.parseDouble(row.getCell(2).toString());
//				        bdProductos.setNuTipoCambio(dobleTipoCamb);
//				        double dobleCostoProm = Double.parseDouble(row.getCell(3).toString());
//				        bdProductos.setNuCostoProm(dobleCostoProm);
//				        bdProductos.setNustatus(1);
				        
//				        try {
//							UserServ.saveProdTerm(bdProductos);
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								System.out.println(e.toString());
//								e.printStackTrace();
//							}
//						}
					}
					
			}
			
			
			if(!Error.trim().equals("")){
				
				bw.write(Error);
				
				System.out.println("Errores   ---------->>>>>>    " +Error);	
				
				bw.close();
				
				 byte[] blobAsBytesxml = method(file);
				
//				byte[] blobAsBytesxml = file.
				
				Filedownload.save(blobAsBytesxml, null, "Errores_CargaExcel_"+Nombre_xslx+"txt");
				
//				Executions.sendRedirect(Nombre_xslx+"txt");
			}
			
			
	    
			} catch (Exception e) {
		        e.printStackTrace();
		    }
	}

//public Boolean ValidaIsla(String isla){
//	
//	boolean isIsla = false;
//	
//	UserServ.existeIsla(Usuario);
//	
//	return null;
//	
//}

	public static byte[] method(File file)
	throws IOException
	{
	
		// Creating an object of FileInputStream to
		// read from a file
		FileInputStream fl = new FileInputStream(file);
		
		// Now creating byte array of same length as file
		byte[] arr = new byte[(int)file.length()];
		
		// Reading file content to byte array
		// using standard read() method
		fl.read(arr);
		
		// lastly closing an instance of file input stream
		// to avoid memory leakage
		fl.close();
		
		// Returning above byte array
		return arr;
	}



public static void borrarArchivo(String Archivo){
	 try{

        File archivo = new File(Archivo);

        boolean estatus = archivo.delete();;

        if (!estatus) {

            System.out.println("Error no se ha podido eliminar el  archivo");

       }else{

            System.out.println("Se ha eliminado el archivo exitosamente");

       }

    }catch(Exception e){

       System.out.println(e);

    }
}


}


