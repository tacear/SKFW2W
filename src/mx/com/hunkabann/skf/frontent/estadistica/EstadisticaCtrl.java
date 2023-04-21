package mx.com.hunkabann.skf.frontent.estadistica;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import mx.com.hunkabann.skf.backend.EstadisticaDao;
import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.frontent.util.FDDateFormat;
import mx.com.hunkabann.skf.frontent.util.GFCBaseCtrl;

import org.apache.log4j.Logger;
import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.http.SimpleSession;
import org.zkoss.zul.*;


public class EstadisticaCtrl extends GFCBaseCtrl {

	private transient final static Logger logger = Logger.getLogger(EstadisticaCtrl.class);
	private static final long serialVersionUID = -71422545405325060L;

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends WindowBaseCtrl'.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window estadisticaWindow;	// autowired
	protected Label lbl_ServerTime;	// autowired
	protected Textbox u;	// autowired
	protected Image grafica;	// autowired
	protected Timer timer;
	
	private int countNum = 0;
	private int anchoGrafica = 900;
	private int altoGrafica = 550;
	private String tituloGrafica = "Soft Drink 3D Pie Chart";
//	private int delayTimer = 60000; // 1 minuto
	private int delayTimer = 10000; // 10 segundos
	
	private HashMap<String, String> confData = null;


	
	//Servicio
	UsuarioService UserServ = new UsuarioService();
	
	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
    HttpSession session = (HttpSession) s.getNativeSession();
	
    String Sesion_User = "";
    
	/**
	 * default constructor. <br>
	 */
	public EstadisticaCtrl() {
		super();

		if (logger.isDebugEnabled()) {
			logger.debug("--> super() ");
		}
		
	}	// constructor
	
	

	public void onCreate$estadisticaWindow(Event event) throws Exception {
		

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
				
		doOnCreateCommon(estadisticaWindow); // do the autowire

		// inicializa los datos necesarios para generar la gráfica
	    confData = new HashMap<String, String>();
	    confData.put("GRAFICA_TIMER_DELAY", "");
	    confData.put("GRAFICA_TITULO", "");
	    confData.put("GRAFICA_ANCHO", "");
	    confData.put("GRAFICA_ALTO", "");
		
	    EstadisticaDao dao = new EstadisticaDao();

	    dao.getConfigurationData(confData);
	    logger.info(confData);
	    
	    delayTimer = Integer.parseInt(confData.get("GRAFICA_TIMER_DELAY"));
	    tituloGrafica = confData.get("GRAFICA_TITULO");
	    anchoGrafica = Integer.parseInt(confData.get("GRAFICA_ANCHO"));
	    altoGrafica = Integer.parseInt(confData.get("GRAFICA_ALTO"));

		timer.setDelay(delayTimer); // establece el tiempo de lectura del timer
		timer.start();	// inicializa el timer
		dao = null;
		
		// genera la gráfica al entrar en la pantalla
		generateGraphic();
		
	}	// onCreate$EntregaRecepListWindow
	
	
	

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
		
	}	// onClick$button_ZKLoginDialog_Close
	
	
	
	
	
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
	public void onClick$btnClose() throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("--> Boton Cerrar loguin");
		}

		estadisticaWindow.onClose();
		
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
		
		estadisticaWindow.onClose();
		
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
			doOnCreateCommon(estadisticaWindow);
		} catch (Exception e) {
			e.printStackTrace();
		} // do the autowire

		u.focus(); // set the focus on UserName
		
		try {
			estadisticaWindow.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
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
			doOnCreateCommon(estadisticaWindow);
		} catch (Exception e) {
			e.printStackTrace();
		} // do the autowire

		u.focus(); // set the focus on UserName
		
		try {
			estadisticaWindow.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
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
	
	
	/**
	 * Genera la gráfica
	 * @throws IOException
	 */
	public void generateGraphic() throws IOException
	{
		logger.info("");
		
		// parametros está el create
		// titulo de la grafica
		// tamaño de la gráfica
		// timer en milis
	    DefaultPieDataset pieDataset = new DefaultPieDataset();
	    
	    EstadisticaDao dao = new EstadisticaDao();
	    HashMap<String, Double> data = dao.getGraficaData();
	    logger.info(data);

		for (String cve : data.keySet())
		    pieDataset.setValue(cve + " - " + data.get(cve), data.get(cve));
	    
	    //Create the chart
	    JFreeChart chart = ChartFactory.createPieChart3D(tituloGrafica, pieDataset, true, true, true);
	    
	    //Save chart as PNG into a stream
	     ByteArrayOutputStream bos = new ByteArrayOutputStream();
	     ChartUtilities.writeChartAsPNG(bos, chart, anchoGrafica, altoGrafica);
	     // create an image frpm the stream
	     AImage i = new AImage("x", bos.toByteArray());
	     grafica.setContent(i);	// update the image into desktop
	     
	     pieDataset = null;
	     chart = null;
	     bos = null;
	     i = null;
	     dao = null;
	     data.clear();
	     data = null;
	     
	}	// generateGraphic
	
	
	/**
	 * operación que se realiza en el timer
	 * @param event
	 * @throws IOException
	 */
	public void onTimer$timer(Event event) throws IOException{
		
		// TODO quitar esta sección
		countNum++;
		if(countNum >= 10)
			timer.stop();
		logger.info(countNum);
		// fin de sección a quitar
		
		generateGraphic();
		
	}	// onTimer$timer

}	// end of file
