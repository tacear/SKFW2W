package mx.com.hunkabann.skf.frontent.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;

import main.java.skf.client.CatalogoBase;
import main.java.skf.client.Response;
import main.java.skf.client.client;
import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.frontent.util.ComboFactoryModelItemRenderer;
import mx.com.hunkabann.skf.frontent.util.FDDateFormat;
import mx.com.hunkabann.skf.frontent.util.GFCBaseCtrl;
import mx.com.hunkabann.skf.frontent.util.GFCBaseListCtrl;
import mx.com.hunkabann.skf.frontent.util.MultiLineMessageBox;
import mx.com.hunkabann.skf.frontent.util.WindowBaseCtrl;
//import mx.com.hunkabann.skf.mapeo.TbCorporativo;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.http.SimpleSession;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.google.gson.Gson;

import java.util.Map;


public class LoginCtrl extends GFCBaseCtrl {

	private transient final static Logger logger = Logger.getLogger(LoginCtrl.class);
	private static final long serialVersionUID = -71422545405325060L;

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends WindowBaseCtrl'.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window loginwin; // autowired
	protected Label lbl_ServerTime; // autowired
	protected Textbox u; // autowired
	protected Textbox p;
	protected Image btnRegenera;
	protected Label label_Rege;
	protected transient Listbox lb_Corporativo; // autowired
	protected Label label_User;
	
	
	protected transient Hbox hbox_Salir;
	protected transient Hbox hbox_OrdenManu;
	protected transient Hbox hbox_EntregaRecepcion;
	protected transient Hbox hbox_Operacion;
	protected transient Hbox hbox_Reporte;
	protected transient Hbox hbox_Trazabilidad;
	
	//Servicio
	UsuarioService UserServ = new UsuarioService();
	
	
	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
    HttpSession session = (HttpSession) s.getNativeSession();
	
    String Sesion_User = "";


	/**
	 * default constructor. <br>
	 */
	public LoginCtrl() {
		super();

		if (logger.isDebugEnabled()) {
			logger.debug("--> super() ");
		}
	}

	public void onCreate$loginwin(Event event) throws Exception {
		

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		
		//System.out.println("ATC  Valor del Usuario -------------->>>>" + session.getAttribute("SesionesSKF"));
		
		if(session != null){
			if(session.getAttribute("SesionesSKF") != null){
				if(!session.getAttribute("SesionesSKF").equals("")){
					loginwin.onClose();
					MultiLineMessageBox.show("YA HAY UNA SESION ACTIVA, FAVOR DE CERRARLA PARA PODER ENTRAR CON OTRO USUARIO", "I N F O R M A C I O N ", MultiLineMessageBox.OK, "I N F O R M A C I O N", true);
					
					return;
				}
			}else{
				
				Map<String, Object> args = getCreationArgsMap(event);
				
				if (args.containsKey("label_User"))
					label_User = (Label) args.get("label_User");
				
				if (args.containsKey("Sesion_User"))
					Sesion_User = (String) args.get("Sesion_User");
				
				if (args.containsKey("hbox_Salir"))
					hbox_Salir = (Hbox) args.get("hbox_Salir");
				
				if (args.containsKey("hbox_OrdenManu"))
					hbox_OrdenManu = (Hbox) args.get("hbox_OrdenManu");
				
				if (args.containsKey("hbox_EntregaRecepcion"))
					hbox_EntregaRecepcion = (Hbox) args.get("hbox_EntregaRecepcion");
				
				if (args.containsKey("hbox_Operacion"))
					hbox_Operacion = (Hbox) args.get("hbox_Operacion");
				
				if (args.containsKey("hbox_Reporte"))
					hbox_Reporte = (Hbox) args.get("hbox_Reporte");
				
				if (args.containsKey("hbox_Trazabilidad"))
					hbox_Trazabilidad = (Hbox) args.get("hbox_Trazabilidad");
				
				doOnCreateCommon(loginwin); // do the autowire
//				lb_Corporativo.focus();
				u.focus(); // set the focus on UserName
				
				
				
				
				
				
				
				
				
				
				
				loginwin.doModal();
				
				
			}
			
			
			
			
		}
		
		
		
		
		
		
		
		
//		HttpServletRequest request = null;
		
//		AuthenticationException exception = null;
//		HttpServletRequest req = null;
//		  session.setAttribute("url", "mkyong.com"); 
//		  session.invalidate();  //sessionDestroyed() is executed
		
		

		

	}

	/**
	 * when the "close" button is clicked. <br>
	 * 
	 * @throws IOException
	 */
	public void onClick$button_ZKLoginDialog_Close() throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("-->");
		}
		
		Executions.sendRedirect("/j_spring_logout");
	}
	
	/**
	 * when the "getServerTime" button is clicked. <br>
	 * 
	 * @throws IOException
	 */
	public void onClick$button_ZKLoginDialog_ServerTime() throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("--> get the server date/time");
		}

		// TODO get the tomcat servers time, if the TimeServer doesn't answers.
		long l = getCurrentHttpTokenTime();

		// FIXME Zeitzone wird hier ignoriert! Es ist nicht ersichtig, in
		// welcher Zeitzone der Server l�uft.
		String dateStr = FDDateFormat.getDateTimeLongFormater().format(l);

		lbl_ServerTime.setMultiline(true);
		lbl_ServerTime.setValue("Fecha y Hora:\n" + dateStr);
		
	}	// onClick$button_ZKLoginDialog_ServerTime
	
	/**
	 * when the "getServerTime" button is clicked. <br>
	 * 
	 * @throws IOException
	 */
	public void onClick$btnCerrar() throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("--> Boton Cerrar loguin");
		}

		loginwin.onClose();
		
	}	// onClick$button_ZKLoginDialog_ServerTime0
	
	/**
	 * when the "getServerTime" button is clicked. <br>
	 * 
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public void onClick$btnEntrar() throws IOException, InterruptedException {

		if (logger.isDebugEnabled()) {
			logger.debug("--> Boton Cerrar loguin");
			
			
		}
		
//		String codRet = "";
//		
//		SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		
		//clienteWs.loguin(u.getValue(), p.getValue());
		
		System.out.println(loguin(u.getValue(), p.getValue()));

//		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
//			      u.getValue(), p.getValue());
//			  
//
//		System.out.println("Nombre "+ user.getName());
//		
//		if(codRet.equals(""))
//		{
//			if(u.equals("") || !u.getValue().equals("admon"))
//			{
//				//System.out.println("usuario no valido -----------");
////				throw new UsernameNotFoundException("Invalid User");
//				codRet = "Usuario no Valido";
//				MultiLineMessageBox.show(codRet, "Error", MultiLineMessageBox.OK, "ERROR", true);
//				return;
//			}
//		}
//		
//		if(codRet.equals(""))
//		{
//			if(p.equals("") || !p.getValue().equals("admon"))
//			{
//				//System.out.println("usuario no valido -----------");
////				throw new UsernameNotFoundException("Invalid User");
//				codRet = "Usuario no Valido";
//				MultiLineMessageBox.show(codRet, "Error", MultiLineMessageBox.OK, "ERROR", true);
//				return;
//			}
//		}
		
//		int intentos = 0;
//		
//		if(session.getAttribute(user.getName()) == null)
//		{
//			intentos = 1;
//		}
//		else
//		{
//			intentos = Integer.parseInt(session.getAttribute(user.getName()).toString());
//			intentos++;
//			 
//		}
//		
//		
//		 
//		session.setAttribute(user.getName(), intentos + "");
//		 
//		if(intentos > 2)
//		{
//			UsuarioService service= new UsuarioService();
//			try {
////				service.setUserInactiveByUserName(user.getName());
//				codRet = "Se excedio el Maximo de Intentos Permitidos";
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//			MultiLineMessageBox.show(codRet, "Error", MultiLineMessageBox.OK, "ERROR", true);
//			
//			session.removeAttribute(user.getName());
//			return; 
//		}
		
//		if(codRet.equals(""))
//		{
//			codRet = "Datos invalidos, favor de Verificarlos";
//			
//			MultiLineMessageBox.show(codRet, "Error", MultiLineMessageBox.OK, "ERROR", true);
//			return;
//		}
		
		loginwin.onClose();
		
		
		
		
//		if(!User.equals("")){
			
//		}
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("usuario", "Gerardo Perez Carbajal");
//		/*
//		 * we can additionally handed over the listBox, so we have in the dialog
//		 * access to the listbox Listmodel. This is fine for synchronizing the
//		 * data in the userListbox from the dialog when we do a delete, edit or
//		 * insert a user.
//		 */
////		map.put("listBoxOrden", null);
//		// call the zul-file and put it in the center layout area
//		Executions.createComponents("/indexSKF.zul", null, map);
		
//		System.out.println("login.zul?login_error=" + codRet);	
		
	}	// onClick$button_ZKLoginDialog_ServerTime
	
	
	public void onClick$label_Rege(Event event)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try
		{
			
			Executions.createComponents("/WEB-INF/pages/generaPwd.zul", null, map);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
			
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		try {
			doOnCreateCommon(loginwin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // do the autowire

		u.focus(); // set the focus on UserName
		
		try {
			loginwin.doModal();
		} catch (SuspendNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public String loguin(String Usuario, String pass) throws IOException, InterruptedException{
		
		hbox_Salir.setVisible(false);
		hbox_OrdenManu.setVisible(false);
		hbox_EntregaRecepcion.setVisible(false);
		hbox_Operacion.setVisible(false);
		hbox_Reporte.setVisible(false);
		hbox_Trazabilidad.setVisible(false);
		
		String Res ="";
		
		System.out.println("SKF - Cliente de ejemplo para API ");

		try {
			URL url = new URL("http://localhost:5000/api/Login");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "*/*");
			con.setDoOutput(true);

			//String jsonInputString = "{\"username\":\"GPCarbajal\",\"password\":\"Gerardo24\"}";
			String jsonInputString = "{\"username\":\"" + Usuario + "\",\"password\":\"" + pass+ "\"}";

			
				OutputStream os = con.getOutputStream();
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			

			int responseCode = con.getResponseCode();
			System.out.println("POST Response Code :: " + responseCode);

			if (responseCode == 200) {
				
				
				
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
					StringBuilder response = new StringBuilder();
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					System.out.println(response.toString());

					Gson g = new Gson();                                             
					                                                                 
					Response objResponse = g.fromJson(response.toString(), Response.class);
					if (!objResponse.success) {
						 System.out.println("información obtenida con Gson");
						 System.out.println(objResponse.message);
						 MultiLineMessageBox.show(objResponse.message, "Error", MultiLineMessageBox.OK, "ERROR", true);
						 return objResponse.message;
						 }
					else {
					System.out.println("informacón obtenida con Gson");
					System.out.println(objResponse.data.name);
					label_User.setVisible(true);
					
					String Empleado = "Empleado: "+ objResponse.data.name.toUpperCase() +" / ROL: " +objResponse.data.perfil.descripcion;
					label_User.setValue(Empleado);
					session.setAttribute("Empleado",Empleado);
					
					System.out.println("*****roles");
					Iterator itr=objResponse.data.roles.iterator();

	                while(itr.hasNext()){

	                        Object e = itr.next();

	                        CatalogoBase s=(CatalogoBase)e;

	                        System.out.println("Valor de Id: " + s.id);

	                        System.out.println("Valor Descripción: " + s.descripcion);
	                        
	                        
	                        hbox_Salir.setVisible(true);
							
							
							
							
							
	                        
	                           if(s.descripcion.equals("ORDEN MANUFACTURA")){
	                        	   hbox_OrdenManu.setVisible(true);
		                       }
		                       if(s.descripcion.equals("TRAZABILIDAD")){
		                    	   hbox_Trazabilidad.setVisible(true);               	   
							   }
		                       if(s.descripcion.equals("REPORTES")){
		                    	   hbox_Reporte.setVisible(true); 
		                       }
		                       if(s.descripcion.equals("OPERACION")){
		                    	   hbox_Operacion.setVisible(true);
		                       }
		                       if(s.descripcion.equals("ENTREGA RECEPCION")){
		                    	   hbox_EntregaRecepcion.setVisible(true);
		                       }
		                       
		                       hbox_Salir.setVisible(true);

	                }
					
	                System.out.println("*****perfil");
					System.out.println(objResponse.data.perfil.id);
					System.out.println(objResponse.data.perfil.descripcion);
					}
					System.out.println("información obtenida con Json");
//					System.out.println(ExtractResponse(response.toString()));
					
					Res =response.toString();
				                        
//					return ExtractResponse(response.toString()).toString();
					
//					if(Usuario.equals("GPCarbajal")){
//						label_User.setVisible(true);
//						label_User.setValue("Empleado: "+ "Gerardo Perez Carbajal");
//						
//						hbox_Salir.setVisible(true);
//						hbox_OrdenManu.setVisible(true);
//						hbox_EntregaRecepcion.setVisible(true);
//						hbox_Operacion.setVisible(true);
//						hbox_Reporte.setVisible(true);
//						hbox_Trazabilidad.setVisible(true);
//					}
//					if(Usuario.equals("IDBarcenas")){
//						label_User.setVisible(true);
//						label_User.setValue("Empleado: "+ "Ivan Daniel Barcenas Gomez");
//						
//						hbox_Salir.setVisible(true);
//						hbox_OrdenManu.setVisible(false);
//						hbox_EntregaRecepcion.setVisible(true);
//						hbox_Operacion.setVisible(true);
//						hbox_Reporte.setVisible(false);
//						hbox_Trazabilidad.setVisible(false);
//					}
//					if(Usuario.equals("VAOrtega")){
//						label_User.setVisible(true);
//						label_User.setValue("Empleado: "+ "Victor Alfonso Alfaro Ortega");
//						
//						hbox_Salir.setVisible(true);
//						hbox_OrdenManu.setVisible(true);
//						hbox_EntregaRecepcion.setVisible(false);
//						hbox_Operacion.setVisible(false);
//						hbox_Reporte.setVisible(true);
//						hbox_Trazabilidad.setVisible(false);
//					}
					
					
					
					
					
					
//					Sesion_User =  ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
					 session.setAttribute("SesionesSKF",Usuario);
					 
				
			} else {
				InputStream errorstream = con.getErrorStream();
				String response = "";
				String responseLine = null;
				BufferedReader br = new BufferedReader(new InputStreamReader(errorstream));
				while ((responseLine = br.readLine()) != null) {
					response += responseLine;
				}
				System.out.println("Response: " + response);
				
				Res = response;
				MultiLineMessageBox.show(response, "Error", MultiLineMessageBox.OK, "ERROR", true);
//				return response;
//				return response;
				
			}

		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}
		
		return Res;
		
	}

	public static JsonObject ExtractResponse(String response) {

		JsonReader reader = Json.createReader(new StringReader(response));

		JsonObject jsonObject = reader.readObject();

		boolean isValid = jsonObject.getBoolean("success");
		if (!isValid)
			return null;

		JsonObject infoUsuario = jsonObject.getJsonObject("data");

		return infoUsuario;
	}
	
	public void onClick$btnRegenera(Event event)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try
		{
			
			Executions.createComponents("/WEB-INF/pages/generaPwd.zul", null, map);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
			
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		try {
			doOnCreateCommon(loginwin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // do the autowire

		u.focus(); // set the focus on UserName
		
		try {
			loginwin.doModal();
		} catch (SuspendNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	

	/**
	 * Get the actual date/time on server. <br>
	 * Not used at time.<br>
	 * 
	 * @return String of date/time
	 */
	@SuppressWarnings("unused")
	private String getDateTime() {
		return FDDateFormat.getDateTimeLongFormater().format(new Date());
	}

	/**
	 * Get a date/time from a web server for the one-time-password
	 * synchronizing.<br>
	 * <br>
	 * We became our time with calling a PHP Function on a webserver.<br>
	 * This time-Url and time is used only for synchronizing the tokenizer <br>
	 * application on the users PC and the server method for calculate the <br>
	 * user token. So the running user-application must have an internet access. <br>
	 * In the case of non internet connection of the users pc, the tokenizer<br>
	 * takes the time from the users pc clock. So the user can set the pc clock
	 * to the servers time manually.<br>
	 * 
	 * <pre>
	 * File: time.php
	 * --------------
	 * 1. &lt;?php
	 * 2. echo mktime();
	 * 3. ?&gt;
	 * --------------
	 * End-File. = 3 lines
	 * </pre>
	 * 
	 * @return
	 */
	private long getCurrentHttpTokenTime() {

		String urlString = "http://unixtime.forsthaus.de/time.php";

		try {
			final URL url = new URL(urlString);
			final URLConnection conn = url.openConnection();
			final InputStream istream = conn.getInputStream();
			try {
				final StringBuilder sb = new StringBuilder();

				int ch = -1;
				while ((ch = istream.read()) != -1) {
					sb.append((char) ch);
				}
				long l1 = Long.parseLong(sb.toString());

				return l1 * 1000;
			} catch (NumberFormatException e) {
				throw new RuntimeException(e);
			} finally {
				istream.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}	// end of file
