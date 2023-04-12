package mx.com.hunkabann.skf.frontent.reports;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;

//import mx.com.hunkabann.sipv.util.HibernateUtil;
import mx.com.hunkabann.skf.util.HibernateUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Window;


public class JRreportWindow extends Window implements Serializable {

	private static final long serialVersionUID = -5587316458377274805L;
	private transient static final Logger logger = Logger.getLogger(JRreportWindow.class);

	private transient JRreportWindow window;
	private transient Iframe report;
	private transient Jasperreport jasperreport;

	/* The parent that calls the report */
	private transient Component parent;

	/* if true, shows the ReportWindow in ModalMode */
	private transient boolean modal;

	/* Report params like subreports, title, author etc. */
	private transient HashMap<String, Object> reportParams;

	/* Reportname with whole path must ends with .jasper (it's compiled) */
	private transient String reportPathName;

	/* 'pdf', 'xml', .... */
	private transient String type;

	//Session
	private transient Session s = null;

	private transient byte[] buf = null;

	private transient InputStream mediais =null;

	private transient AMedia amedia = null;

	private transient Panel panel = new Panel();

	private transient Panelchildren panelchildren = new Panelchildren();

	private transient Grid grid = new Grid();

	private transient Columns columns = new Columns();

	private transient Column column = new Column();

	private transient Hbox hbox = new Hbox();

	private transient Button button = new Button();

	/**
	 * Constructor.<br>
	 * <br>
	 * Creates a report window container.<br>
	 *
	 * @param parent
	 * @param modal
	 * @param reportParams
	 * @param reportPathName
	 * @param type
	 * @throws JRException
	 */
	public JRreportWindow(Component parent, boolean modal, HashMap<String, Object> reportParams, String reportPathName, String type) throws JRException {
		super();
		this.parent = parent;
		this.modal = modal;
		this.reportParams = reportParams;
		this.reportPathName = reportPathName;
		this.type = type;
		this.window = this;

		try {
			createReport();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void createReport() throws FileNotFoundException, JRException {

		if ((Boolean) modal == null) {
			modal = true;
		}

		if (reportPathName.equals("")) {
			throw new FileNotFoundException(reportPathName);
		}
		if (type.equals("")) {
			type = "pdf";
		}

		this.setParent(parent);

		//this.setTitle((String)reportParams.get("Title"));
		panel.setParent(window);
		panel.setTitle((String)reportParams.get("Title"));
		panelchildren.setParent(panel);
		grid.setParent(panelchildren);
		columns.setParent(grid);
		column.setParent(columns);
		column.setAlign("right");
		hbox.setParent(column);
		hbox.setAlign("end");
		button.setParent(hbox);
		button.setImage("/images/icons/btn_exitdoor2_16x16.gif");
		button.setTooltiptext("Salir");
		button.addEventListener("onClick", new OnCloseReportEventListener());

		this.setVisible(true);
		this.setMaximizable(true);
		this.setMinimizable(true);
		this.setSizable(false);
		this.setClosable(true);
		this.setHeight("550px");
		this.setWidth("750px");
//		this.addEventListener("onClose", new OnCloseReportEventListener());

		try
		{

			s = HibernateUtil.currentSession();
	        Connection conn = s.connection();

			buf =  JasperRunManager.runReportToPdf(reportPathName, reportParams, conn);

	        //prepare the AMedia for iframe
	        mediais = new ByteArrayInputStream(buf);

	        amedia = new AMedia((String)reportParams.get("Title")+"Report.pdf", "pdf", "application/pdf", mediais);
		}
		catch (HibernateException e) {
			System.err.println("Error ->> "+e);
		}
		finally{
			if(s != null)
				HibernateUtil.closeSession();
		}


		report = new Iframe();
		report.setId("jasperReportId");
		report.setParent(panelchildren); // needed ?
		report.setHeight("492px");
		report.setWidth("750px");
		report.setContent(amedia);

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + report.getId());
		}

		this.appendChild(report);

		if (modal == true) {
			try {
				this.doModal();
			} catch (SuspendNotAllowedException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Constructor.<br>
	 * <br>
	 * Creates a report window container.<br>
	 *
	 * @param parent
	 * @param modal
	 * @param reportParams
	 * @param reportPathName
	 * @param ds
	 * @param type
	 * @throws JRException
	 */
	public JRreportWindow(Component parent, boolean modal, HashMap<String, Object> reportParams, String reportPathName, JRDataSource ds, String type) throws JRException {
		super();
		this.parent = parent;
		this.modal = modal;
		this.reportParams = reportParams;
		this.reportPathName = reportPathName;
		this.type = type;
		this.window = this;

		try {
			createReport(ds);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void createReport(JRDataSource ds) throws FileNotFoundException {

		if ((Boolean) modal == null) {
			modal = true;
		}

		if (reportPathName.isEmpty()) {
			throw new FileNotFoundException(reportPathName);
		}

		if (ds == null) {
			throw new FileNotFoundException("JRDataSource is empty");
		}

		if (type.isEmpty()) {
			type = "pdf";
		}

		this.setParent(parent);

		//this.setTitle((String)reportParams.get("Title"));
		panel.setParent(window);
		panel.setTitle((String)reportParams.get("Title"));
		panelchildren.setParent(panel);
		grid.setParent(panelchildren);
		columns.setParent(grid);
		column.setParent(columns);
		column.setAlign("right");
		hbox.setParent(column);
		hbox.setAlign("end");
		button.setParent(hbox);
		button.setImage("/images/icons/btn_exitdoor2_16x16.gif");
		button.setTooltiptext("Salir");
		button.addEventListener("onClick", new OnCloseReportEventListener());

		this.setVisible(true);
		this.setMaximizable(true);
		this.setMinimizable(true);
		this.setSizable(false);
		this.setClosable(true);
		this.setHeight("550px");
		this.setWidth("750px");
//		this.addEventListener("onClose", new OnCloseReportEventListener());

		jasperreport = new Jasperreport();
		jasperreport.setId("jasperReportId");
		jasperreport.setSrc(reportPathName);
		jasperreport.setParameters(reportParams);
		jasperreport.setDatasource(ds);
		jasperreport.setName((String)reportParams.get("Title")+".pdf");
		jasperreport.setType(type);
		jasperreport.setParent(panelchildren); // needed ?
		jasperreport.setHeight("492px");
		jasperreport.setWidth("750px");

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + jasperreport.getId());
		}

		this.appendChild(jasperreport);

		if (modal == true) {
			try {
				this.doModal();
			} catch (SuspendNotAllowedException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public JRreportWindow(Component parent, AMedia amedia, HashMap<String, Object> reportParams) throws FileNotFoundException {
		super();
		this.parent = parent;
		this.window = this;
		this.reportParams = reportParams;
		this.modal = true;
		createReport(amedia);
	}

	private void createReport(AMedia amedia) throws FileNotFoundException {

		this.setParent(parent);

		//this.setTitle((String)reportParams.get("Title"));
		panel.setParent(window);
		panel.setTitle((String)reportParams.get("Title"));
		panelchildren.setParent(panel);
		grid.setParent(panelchildren);
		columns.setParent(grid);
		column.setParent(columns);
		column.setAlign("right");
		hbox.setParent(column);
		hbox.setAlign("end");
		button.setParent(hbox);
		button.setImage("/images/icons/btn_exitdoor2_16x16.gif");
		button.setTooltiptext("Salir");
		button.addEventListener("onClick", new OnCloseReportEventListener());

		this.setVisible(true);
		this.setMaximizable(true);
		this.setMinimizable(true);
		this.setSizable(false);
		this.setClosable(true);
		this.setHeight("550px");
		this.setWidth("750px");

		report = new Iframe();
		report.setId("jasperReportId");
		report.setParent(panelchildren); // needed ?
		report.setHeight("492px");
		report.setWidth("750px");
		report.setContent(amedia);

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + report.getId());
		}

		this.appendChild(report);

		if (modal == true) {
			try {
				this.doModal();
			} catch (SuspendNotAllowedException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * EventListener for closing the Report Window.<br>
	 *
	 * @author sge
	 *
	 */
	public final class OnCloseReportEventListener implements EventListener {
		public void onEvent(Event event) throws Exception {
			closeReportWindow();
		}
	}

	/**
	 * We must clear something to prevent errors or problems <br>
	 * by opening the report several times. <br>
	 */
	private void closeReportWindow() {

		if (logger.isDebugEnabled()) {
			logger.debug("detach Report and close ReportWindow");
		}

		this.removeEventListener("onClick", new OnCloseReportEventListener());

		if(report!=null)
			report.detach();
		if(jasperreport!=null)
			jasperreport.detach();

		this.getChildren().clear();
		window.onClose();

	}
}