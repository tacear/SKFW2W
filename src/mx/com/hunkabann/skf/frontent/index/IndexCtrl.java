package mx.com.hunkabann.skf.frontent.index;

import java.io.IOException;
import java.io.Serializable;

import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.frontent.util.GFCBaseCtrl;
import mx.com.hunkabann.skf.util.SessionBean;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;





public class IndexCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -3407055074703929527L;
	private transient final static Logger logger = Logger.getLogger(IndexCtrl.class);

	private transient Image imgCorporativo;
	private transient Image logo;
	private transient String RutaImagen;

	private transient SessionBean sBean;

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected transient Menubar mainMenuBar; // autowired
	protected transient Window outerIndexWindow;

	//protected transient Column statusBarZKVersion; // autowired
	//protected transient Column statusBarAppVersion; // autowired
	protected transient Column statusBarColUser; // autowired

	protected transient Intbox currentDesktopHeight; // autowired
	protected transient Intbox currentDesktopWidth; // autowired
	protected transient Checkbox CBtreeMenu; // autowired
	protected transient Label label_TituloCentral;
	protected transient Div div1;

	protected transient Tabbox tabBoxIndexCenter;

	private UsuarioService userService;


	public IndexCtrl() {
		super();

		if (logger.isDebugEnabled()) {
			logger.debug("--> super()");
		}
	}

	public void onCreate$outerIndexWindow(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		outerIndexWindow.setSizable(true);
		outerIndexWindow.setMinwidth(1000);
		outerIndexWindow.setMinheight(500);

		Messagebox.setTemplate("/WEB-INF/pages/util/multiLineMessageBox.zul");

//		if(execution.isExplorer())
//			tabBoxIndexCenter.setWidth("98.5%");

		createMainTreeMenu();

		//RutaImagen = execution.getScheme()+"://"+execution.getServerName()+":"+execution.getServerPort()+"/logos/";

		getInfo();

		logo.setHeight("60px");
		logo.setWidth("150px");
		logo.setSrc("/images/LOGO.JPG");
		logo.setTooltiptext(Labels.getLabel("By"));

		imgCorporativo.setHeight("60px");
		imgCorporativo.setWidth("120px");
//		if(sBean.getImgCorporativo().toString().equals("LOGO.JPG"))
//			imgCorporativo.setSrc("/images/"+sBean.getImgCorporativo());
//		else
//			imgCorporativo.setSrc(RutaImagen+sBean.getImgCorporativo());
		label_TituloCentral.setValue("Portal Rolex");
//		label_TituloCentral.setStyle("color:"+sBean.getColorTitulo()+"; font-size:14px; font-weight:bold");
//		div1.setStyle("background-color:"+sBean.getFondoTitulo());
		label_TituloCentral.setStyle("color:black; font-size:14px; font-weight:bold");
		div1.setStyle("background-color:White");

		//statusBarZKVersion.setLabel("ZK version: " + doGetZkVersion());
		//statusBarAppVersion.setLabel("Zksample2 v1.0 / build: " + FDDateFormat.getDateFormater().format(new Date()));
//		statusBarColUser.setLabel(doGetLoggedInUser());

	}

	public void onClick$logo(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		Executions.sendRedirect(Labels.getLabel("Page"));
	}

	public void getInfo() {

//		TbUsuario user = getUserService().getUserByLoginname(doGetLoggedInUser());
//		TbTienda tienda = getUserService().getTbTienda(user);
//		TbCorporativo corporativo =getUserService().getTbCorporativo(user.getIdCorporativo());
//		TbCorporativo corporativo = getUserService()
//		TbAlmacen centroDistribucion = getUserService().getCentroDistribucion(user.getNukidUsuario());

		sBean= new SessionBean();
		sBean.getLimpia();
//		sBean.setIdUsuario(user.getIdUsuario());
//		sBean.setUsuario(user.getUsername());
//		sBean.setIdCorporativo(corporativo.getIdCorporativo());
//		if(corporativo.getChrutaimg()==null)
//			sBean.setImgCorporativo("LOGO.JPG");
//		else
//			sBean.setImgCorporativo(corporativo.getChrutaimg());
//		if(corporativo.getChtitulo()==null || corporativo.getChtitulo().equals(""))
////			sBean.setTitulo("HUNKERP");
//		sBean.setTitulo("EMPRESA");
//		else
//			sBean.setTitulo(corporativo.getChtitulo());
//		if(corporativo.getChfondoTitulo()==null)
////			sBean.setFondoTitulo("#4944F9");
//			sBean.setFondoTitulo("#A9F5F2");
//		else
//			sBean.setFondoTitulo(corporativo.getChfondoTitulo());
//		if(corporativo.getChcolorTitulo()==null)
////			sBean.setColorTitulo("white");
//			sBean.setColorTitulo("Black");
//		else
//			sBean.setColorTitulo(corporativo.getChcolorTitulo());
//		if(tienda!=null)
//		{
//			sBean.setIdTienda(tienda.getNukidTienda());
//			sBean.setIvaTienda(tienda.getNuiva());
//			sBean.setTipoDoc(tienda.getNutipoDoc().intValue());
//		}
//		if(centroDistribucion!=null)
//			sBean.setIdCentroDistribucion(centroDistribucion.getNukidAlmacen());

		Sessions.getCurrent().getWebApp().setAttribute(SecurityContextHolder.getContext().getAuthentication().getName(), sBean);
	}

	/**
	 * Gets the current desktop height and width and <br>
	 * stores it in the UserWorkspace properties. <br>
	 * We use these values for calculating the count of rows in the listboxes. <br>
	 *
	 * @param event
	 * @throws Exception
	 */
	public void onClientInfo(ClientInfoEvent event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Current desktop height :" + event.getDesktopHeight());
			logger.debug("Current desktop width  :" + event.getDesktopWidth());
		}

		setCurrentDesktopHeight(event.getDesktopHeight());
		setCurrentDesktopWidth(event.getDesktopWidth());
//		tabBoxIndexCenter.setHeight((getCurrentDesktopHeight()<700?600:getCurrentDesktopHeight()-91)+"px");
//		tabBoxIndexCenter.setWidth((getCurrentDesktopWidth()<900?700:getCurrentDesktopWidth()-155)+"px");

	}

	/**
	 * Returns the spring-security managed logged in user.<br>
	 */
	public String doGetLoggedInUser() {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return userName;
	}

	/**
	 * When the 'Logout' button is clicked.<br>
	 *
	 * @throws IOException
	 */
	public void onClick$btnLogout() throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("-->");
		}
//		Sessions.getCurrent().getWebApp().removeAttribute(SecurityContextHolder.getContext().getAuthentication().getName());
//		getUserWorkspace().doLogout(); // logout.
	}

	/**
	 * Creates the MainMenu as TreeMenu as default. <br>
	 */
	private void createMainTreeMenu() {

		// get an instance of the borderlayout defined in the index.zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");

		// get an instance of the searched west layout area
		West west = bl.getWest();
		// clear the center child comps
		west.getChildren().clear();

		// create the components from the mainmenu.zul-file and put
		// it in the west layout area
		Executions.createComponents("/WEB-INF/pages/mainTreeMenu.zul", west, null);
	}

	/**
	 * Shows the welcome page in the borderlayouts CENTER area.<br>
	 *
	 * @throws InterruptedException
	 */
	public void showWelcomePage() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/welcome.zul", center, null);
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// ++++++++++++++++++ getter / setter +++++++++++++++++++//
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//


	public void setCurrentDesktopHeight(int desktopHeight) {
		if (isTreeMenu() == true) {
			this.currentDesktopHeight.setValue(Integer.valueOf(desktopHeight));
		} else {
			this.currentDesktopHeight.setValue(Integer.valueOf(desktopHeight - 40));
		}
		if(this.currentDesktopHeight.getValue().intValue()>outerIndexWindow.getMinheight())
			outerIndexWindow.setHeight(this.currentDesktopHeight.getValue()+"px");
		else
			outerIndexWindow.setHeight(outerIndexWindow.getMinheight()+"px");
	}

	public int getCurrentDesktopHeight() {
		return currentDesktopHeight.getValue().intValue();
	}

	public void setCurrentDesktopWidth(int currentDesktopWidth) {
		this.currentDesktopWidth.setValue(Integer.valueOf(currentDesktopWidth));

		if(this.currentDesktopWidth.getValue().intValue()>outerIndexWindow.getMinwidth())
			outerIndexWindow.setWidth((this.currentDesktopWidth.getValue())+"px");
		else
			outerIndexWindow.setWidth(outerIndexWindow.getMinwidth()+"px");
	}

	public int getCurrentDesktopWidth() {
		return currentDesktopWidth.getValue().intValue();
	}

	public void setTreeMenu(boolean treeMenu) {
		this.CBtreeMenu.setChecked(treeMenu);
	}

	public boolean isTreeMenu() {
		return CBtreeMenu.isChecked();
	}

	public UsuarioService getUserService() {
		return userService;
	}

	public void setUserService(UsuarioService userService) {
		this.userService = userService;
	}

}
