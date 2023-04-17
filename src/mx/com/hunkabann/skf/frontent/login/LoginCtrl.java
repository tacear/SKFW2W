package mx.com.hunkabann.skf.frontent.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import javax.json.Json;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
import javax.servlet.http.HttpSession;


import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.frontent.util.FDDateFormat;
import mx.com.hunkabann.skf.frontent.util.GFCBaseCtrl;
import mx.com.hunkabann.skf.frontent.util.MultiLineMessageBox;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.http.SimpleSession;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


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
	protected Image btnRegenera;
	protected Label label_Rege;
//	protected transient Listbox lb_Corporativo; // autowired
	
	String Usuario = "";
	
	//Servicio
	UsuarioService UserServ = new UsuarioService();
	
	
	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
	HttpSession session = (HttpSession) s.getNativeSession();	

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
		
//		Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		
//		SecurityContextHolder.getContext().getAuthentication().getName();
		
//		HttpServletRequest request = null;
//		HttpSession session = request.getSession(); //sessionCreated() is executed
//		  session.setAttribute("url", "mkyong.com"); 
//		  session.invalidate();  //sessionDestroyed() is executed
		
		
//		System.out.println(session);
//		System.out.println(session.getAttribute("SPRING_SECURITY_CONTEXT"));
//		System.out.println(((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName());
		

		
//		if(session != null){
//			if(session.getAttribute("SesionesSKF") != null){
//				if(!session.getAttribute("SesionesSKF").equals("")){
//					loginwin.onClose();
//					MultiLineMessageBox.show("YA HAY UNA SESION ACTIVA, FAVOR DE CERRARLA PARA PODER ENTRAR CON OTRO USUARIO", "I N F O R M A C I O N ", MultiLineMessageBox.OK, "I N F O R M A C I O N", true);
//					
//					return;
//				}else{
					doOnCreateCommon(loginwin); // do the autowire
					//		lb_Corporativo.focus();
							u.focus(); // set the focus on UserName
							
					//		lb_Corporativo.setModel(new ListModelList(UserServ.getCorporativo()));
					//		lb_Corporativo.setItemRenderer(new ComboFactoryModelItemRenderer());
							
							System.out.println(u.getText() +" ------ "+ u.getValue());
							
					//		
							
							loginwin.doModal();
//				}
//			}
//			else{
//		
//				doOnCreateCommon(loginwin); // do the autowire
//		//		lb_Corporativo.focus();
//				u.focus(); // set the focus on UserName
//				
//		//		lb_Corporativo.setModel(new ListModelList(UserServ.getCorporativo()));
//		//		lb_Corporativo.setItemRenderer(new ComboFactoryModelItemRenderer());
//				
//				System.out.println(u.getText() +" ------ "+ u.getValue());
//				session.setAttribute("SesionesSKF", u.getText());
//		//		
//				
//				loginwin.doModal();
//				
//				
////		
//			}
//		}

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
	
//	public void onSelect$lb_Corporativo(Event event) throws InterruptedException
//	{
//		TbCorporativo corp = new TbCorporativo();
//		UsuarioService usServ = new UsuarioService();
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		
////		if(lb_Corporativo.getSelectedIndex() < 0){
////			Messagebox.show("Selecciona la empresa con la cual quieres iniciar sesion", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////			return;
////		}else{
////			
////			corp =usServ.getTbCorporativoId(lb_Corporativo.getSelectedItem().getLabel());
////			
////			System.out.println("lb_Corporativo.getSelectedIndex(): "+ corp.getIdCorporativo());
////			System.out.println("lb_Corporativo.getSelectedIndex(): "+ lb_Corporativo.getSelectedItem().getLabel());
////
////			session.setAttribute("idCorp", corp.getIdCorporativo());
////		}
//
//		
//		
//	}
	
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
