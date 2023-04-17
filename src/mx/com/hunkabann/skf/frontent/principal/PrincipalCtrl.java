package mx.com.hunkabann.skf.frontent.principal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;

import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.frontent.util.GFCBaseCtrl;
import mx.com.hunkabann.skf.frontent.util.GFCBaseListCtrl;
import mx.com.hunkabann.skf.frontent.util.MultiLineMessageBox;
import mx.com.hunkabann.skf.frontent.util.pagging.PagedListWrapper;
import mx.com.hunkabann.skf.mapeo.DetalleRol;
import mx.com.hunkabann.skf.mapeo.TbPerfil;
import mx.com.hunkabann.skf.mapeo.TbPersona;
import mx.com.hunkabann.skf.mapeo.TbRol;
import mx.com.hunkabann.skf.mapeo.TbUsuario;
import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContext;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.http.SimpleSession;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class PrincipalCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6139454778139881103L;
	private transient static final Logger logger = Logger.getLogger(PrincipalCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected transient Window IndexPrincipalWindow; // autowired

	// filter components
	protected transient Checkbox checkbox_Puesto_ShowAll; // autowired
	protected transient Textbox tb_folio;
	protected transient Textbox tb_uuid;
	protected transient Datebox bd_FechaIni;
	protected transient Datebox bd_FechaFin;
	protected transient Textbox tb_rfc;
	protected transient Listbox  cb_CatPuestoActivo;
	protected transient Button button_downloadXML;
	protected transient Button button_downloadPDF;
	protected transient Button button_Search;
	protected transient Image btnNew;
	protected transient Button btnPassword;
	protected transient Label Label_RFC;
	protected transient Label label_PiePagina;
	protected transient Label label_PiePagina_Corp;
//	protected transient Image btn_Reporte;
	
	protected transient Label label_User;
	
	
	protected transient Image btnInvProdFisc;
	protected transient Image btnInvProdTerm;
	
	
	protected transient Image btnComparaXaMp;
	protected transient Image btnComparaXaPt;
	
	
	//Imagenes como botones
	protected transient Image img_salir;
	protected transient Image img_EntregaRecepcion;
	protected transient Image img_OrdenMan;
	protected transient Image img_Operacion;
	protected transient Image img_Reporte;
	protected transient Image img_Trazabilidad;
	
	protected transient Image img_Home;
	
	
	protected transient Hbox hbox_Salir;
	protected transient Hbox hbox_OrdenManu;
	protected transient Hbox hbox_EntregaRecepcion;
	protected transient Hbox hbox_Operacion;
	protected transient Hbox hbox_Reporte;
	protected transient Hbox hbox_Trazabilidad;
	
	
	
	// Listbox
	protected transient Borderlayout borderLayout_Puesto; // autowired
	protected transient Listbox listBoxOrden; // aurowired
	protected transient Paging paging_Orden; // aurowired
	
	protected transient Listheader listheader_fecha;
	protected transient Listheader listheader_Nombre;
	protected transient Listheader listheader_Folio;
	protected transient Listheader listheader_Subtotal;
	protected transient Listheader listheader_Total;
	protected transient Listheader listheader_RFC;
	protected transient Listheader listheader_uuid;
	String Rfc = "";
	String Nombre = "";
	String Administrador = "";
	String FechaInicial = "";
	String FechaFinal = "";
	String FechaIni = "";
    String FechaFin = "";
	
	int blobLengthxml;
	int blobLengthpdf;
	int blobLengthpdf2;
	
	byte[] blobAsBytespdf2;
	byte[] blobAsBytespdf;
	byte[] blobAsBytesxml;
	
	String Usuario = "";
	
	
	
	
//	private SessionBean sBean = (SessionBean) Sessions.getCurrent().getWebApp().getAttribute(SecurityContextHolder.getContext().getAuthentication().getName());


	protected transient Jasperreport report;

	// row count for listbox
	private transient int countRows;
	
	UsuarioService usuarioService = new UsuarioService();
//	VentaService ventaserv = new VentaService();
	
	String NameRol = "";
	String User = "";
	int id_user;
	int id_pr_rol;
	int id_Corporativo;
	
//	TbDetalleComprobante detaCompro = new TbDetalleComprobante();
	
	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
	
//	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
//    HttpSession session = (HttpSession) s.getNativeSession();
	
	HttpSession session = (HttpSession) s.getNativeSession();	
	
	String Sesion_User = "";
    

//	private transient PerfilService PerfilService;

	/**
	 * default constructor.<br>
	 */
	public PrincipalCtrl() {
		super();

		if (logger.isDebugEnabled()) {
			logger.debug("--> super()");
		}
	}

	public void onCreate$IndexPrincipalWindow(Event event) throws Exception {
		
			
//		Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
		
		
//		Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
		
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		
		try {
			((SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
			
			if(((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName() != null){
//				loginwin.onClose();
//				MultiLineMessageBox.show("YA HAY UNA SESION ACTIVA, FAVOR DE CERRARLA PARA PODER ENTRAR CON OTRO USUARIO", "I N F O R M A C I O N ", MultiLineMessageBox.OK, "I N F O R M A C I O N", true);
				
				Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
				TbUsuario anuser = usuarioService.getUserByUsername(Usuario);
				id_user = usuarioService.getIdByUserName(Usuario);
				
				TbPerfil perfil = usuarioService.gettbPerfil(anuser.getTbPerfil().getNukIdPerfil());
				
//				tbperfi
				
				TbPersona Persona = usuarioService.getPersona(anuser);
				
//				List<TbRol> listaDetalleMahle = new ArrayList<TbRol>();
//				
//				listaDetalleMahle = usuarioService.getRoles(anuser.getTbPerfil().getNukIdPerfil());
				
				for (DetalleRol game: usuarioService.getRoles(anuser.getTbPerfil().getNukIdPerfil())) {
					System.out.println("Clave: " + game.getClave() +"  Nombre: "+ game.getNombre());
					
										
					
                    
                    if(game.getNombre().equals("ORDEN MANUFACTURA")){
                 	   hbox_OrdenManu.setVisible(true);
                    }
                    if(game.getNombre().equals("TRAZABILIDAD")){
                 	   hbox_Trazabilidad.setVisible(true);               	   
					   }
                    if(game.getNombre().equals("REPORTES")){
                 	   hbox_Reporte.setVisible(true); 
                    }
                    if(game.getNombre().equals("OPERACION")){
                 	   hbox_Operacion.setVisible(true);
                    }
                    if(game.getNombre().equals("ENTREGA RECEPCION")){
                 	   hbox_EntregaRecepcion.setVisible(true);
                    }
                    
                    hbox_Salir.setVisible(true);
				}
				
				String Empleado = "Empleado: "+ Persona.getChNombre().toUpperCase()+" "+Persona.getChApPaterno().toUpperCase()+" "+Persona.getChApMaterno().toUpperCase() +" / ROL: "+ perfil.getChNombre() ;
				System.out.println("Empleado--------------->> " +Empleado);
				label_User.setVisible(true);
				
				label_User.setValue(Empleado);
				
				System.out.println("label_User--------------->> " +label_User.getValue());
				
				doOnCreateCommon(IndexPrincipalWindow); // do the autowire
				
//				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			
			label_User.setVisible(false);
			hbox_Salir.setVisible(false);
			
			doOnCreateCommon(IndexPrincipalWindow); // do the autowire
			
		}
		
		
		
		
		/**
		 * Calculate how many rows have been place in the listbox. Get the
		 * currentDesktopHeight from a hidden Intbox from the index.zul that are
		 * filled by onClientInfo() in the indexCtroller
		 */
		
		
//		id_user = usuarioService.getIdByUserName(Usuario);
//		TbUsuario anuser = usuarioService.getUserByUsername(Usuario);
		
//		TbEmpresa anEmpre = usuarioService.getEmpresaByID(anuser.getNukIdEmpresa());
		
//		System.out.println(session.getAttribute("idCorp"));
		
//		hbox_New_OC.setVisible(false);
//		hbox_InvProdFisc.setVisible(false);
//		hbox_InvProdTerm.setVisible(false);
//		hbox_Usuarios.setVisible(false);
//		hbox_busque.setVisible(false);
//		hbox_BuscarPT.setVisible(false);
//		hbox_ComparaXaMp.setVisible(false);
//		hbox_ComparaXaPt.setVisible(false);
		
//		System.out.println("Usuario ----------------------------------------->>>> " +((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName());
//		System.out.println("----------------------------------------->>>> " +session.getAttribute("SesionesSKF"));
//		System.out.println("----------------------------------------->>>> " +session.getAttribute("Empleado"));
//		
//		if(session.getAttribute("Empleado") != null){
//			System.out.println("Valor de la Sesion: "+ session.getAttribute("SesionesSKF"));
//			System.out.println("Valor de la Sesion: "+ session.getAttribute("Empleado"));
//			
//			label_User.setVisible(true);
//			label_User.setValue(session.getAttribute("Empleado").toString());
			
			

			
			
//			if(session.getAttribute("SesionesSKF").equals("GPCarbajal")){
//				hbox_Salir.setVisible(true);
//				hbox_OrdenManu.setVisible(true);
//				hbox_EntregaRecepcion.setVisible(true);
//				hbox_Operacion.setVisible(true);
//				hbox_Reporte.setVisible(true);
//				hbox_Trazabilidad.setVisible(true);
//			}
//			if(session.getAttribute("SesionesSKF").equals("IDBarcenas")){
//				hbox_Salir.setVisible(true);
//				hbox_OrdenManu.setVisible(false);
//				hbox_EntregaRecepcion.setVisible(true);
//				hbox_Operacion.setVisible(true);
//				hbox_Reporte.setVisible(false);
//				hbox_Trazabilidad.setVisible(false);
//			}
//			if(session.getAttribute("SesionesSKF").equals("VAOrtega")){
//				hbox_Salir.setVisible(true);
//				hbox_OrdenManu.setVisible(true);
//				hbox_EntregaRecepcion.setVisible(false);
//				hbox_Operacion.setVisible(false);
//				hbox_Reporte.setVisible(true);
//				hbox_Trazabilidad.setVisible(false);
//			}
			
//			hbox_Salir.setVisible(true);
//			
//			hbox_OrdenManu.setVisible(true);
//		}
//		if(anuser.getIdCorporativo() != null){
//		
//		id_Corporativo = anuser.getIdCorporativo();
		
		
//		if(!Sesion_User.equals("")){
			
				
//		}
		
		
		
		
	}
	
	public void onClick$img_Usuario(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		
		
			showWelcomePageUsuario();
		
		
		
	}
	
	public void onClick$button_Usuario(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		
		if(session.getAttribute("SPRING_SECURITY_CONTEXT") == null){
			System.out.println("session.getAttribute(SPRING_SECURITY_CONTEXT) = null -------------" + session.getAttribute("SPRING_SECURITY_CONTEXT"));
		}
		
		try {
			((SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
			
			if(((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName() != null){
//				loginwin.onClose();
				MultiLineMessageBox.show("YA HAY UNA SESION ACTIVA, FAVOR DE CERRARLA PARA PODER ENTRAR CON OTRO USUARIO", "I N F O R M A C I O N ", MultiLineMessageBox.OK, "I N F O R M A C I O N", true);
				
				return;
			}else{
				showWelcomePageLoguin();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Esta Vacio");
			showWelcomePageLoguin();
			
		}
		
		
			
		
		
		
		
		
	}
	public void showWelcomePageLoguin() throws InterruptedException {
		
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
		
//		TbUsuario anUser = new TbUsuario();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Sesion_User", Sesion_User);
		map.put("label_User", label_User);
		map.put("hbox_Salir", hbox_Salir);
		map.put("hbox_OrdenManu", hbox_OrdenManu);
		map.put("hbox_EntregaRecepcion", hbox_EntregaRecepcion);
		map.put("hbox_Operacion", hbox_Operacion);
		map.put("hbox_Reporte", hbox_Reporte);
		map.put("hbox_Trazabilidad", hbox_Trazabilidad);
		
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", null);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/login.zul", center, map);
		
	}
	
	
	
	public void showWelcomePageUsuario() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
		
//		TbUsuario anUser = new TbUsuario();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("usuario", anUser);
		map.put("label_User", label_User);
		
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", null);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/directorio/userList.zul", center, map);
	}
	
	
	public void onClick$img_OrdenMan(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		
		img_OrdenMan.setSrc("/images/skf/menu/OM_01.png");
		img_EntregaRecepcion.setSrc("/images/skf/menu/ER_00.png");
		img_Operacion.setSrc("/images/skf/menu/Operacion00.png");
		img_Reporte.setSrc("/images/skf/menu/Reporte00.png");
		img_Trazabilidad.setSrc("/images/skf/menu/Trazabilidad00.png");

		
		
		showWelcomePageOrden();
	}
	
	public void onClick$btnPlantilla_CartaPorte(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		showWelcomePageCartaPorte();
		
		
	}
	
	public void onClick$btnInvProdFisc(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		showWelcomePageInvProdFisc();
		
		
	}
	
	
	public void onClick$btnInvProdTerm(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		showWelcomePageInvProdTerm();
		
		
	}
	
	
	
	public void onClick$img_Buscar(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		showWelcomePageBuscar();


	}
	
	public void onClick$btnComparaXaMp(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		showWelcomePageComparaXAMP();


	}
	
	public void onClick$btnComparaXaPt(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		showWelcomePageComparaXAPT();


	}
	
	
	public void onClick$btn_BuscarPT(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		showWelcomePageBuscarPT();


	}
	
	
	
	public void onClick$img_EntregaRecepcion(Event event) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

//		System.out.println("Si Funciono el Onclic");
////		showWelcomePageNewOrden();
//		
//		CargaExcelUser caExcel = new CargaExcelUser();
//        try {
//            mediaArchivo = Fileupload.get("Seleccione un Excel para Cargar", "Cargar Excel");
//            if (mediaArchivo == null) {
//                Messagebox.show("No Cuenta con Archivo Excel para Cargar", "�I n f o r m a t i v o!", 1, "");
//                return;
//            }
//            Ruta_mediaArchivo = saveFile(mediaArchivo, mediaArchivo.getName());
//            Messagebox.show("Iniciando la Carga del Documento", "� Informaci�n !", 1, "");
////            this.SignCFD();
//            caExcel.procesaExcelUsers(Ruta_mediaArchivo);
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Messagebox.show("Termino la Carga del Documento", "� Informaci�n !", 1, "");
//        try {
//            dodelete(this.Ruta_mediaArchivo);
//        }
//        catch (Exception e2) {
//            System.out.println(e2.toString());
//        }
		
		img_EntregaRecepcion.setSrc("/images/skf/menu/ER_01.png");
		img_OrdenMan.setSrc("/images/skf/menu/OM_00.png");
		img_Operacion.setSrc("/images/skf/menu/Operacion00.png");
		img_Reporte.setSrc("/images/skf/menu/Reporte00.png");
		img_Trazabilidad.setSrc("/images/skf/menu/Trazabilidad00.png");
		
		showWelcomePageCargaInicial();
		
		
		
	}
	
	public void onClick$img_Operacion(Event event) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

//		System.out.println("Si Funciono el Onclic");
////		showWelcomePageNewOrden();
//		
//		CargaExcelUser caExcel = new CargaExcelUser();
//        try {
//            mediaArchivo = Fileupload.get("Seleccione un Excel para Cargar", "Cargar Excel");
//            if (mediaArchivo == null) {
//                Messagebox.show("No Cuenta con Archivo Excel para Cargar", "�I n f o r m a t i v o!", 1, "");
//                return;
//            }
//            Ruta_mediaArchivo = saveFile(mediaArchivo, mediaArchivo.getName());
//            Messagebox.show("Iniciando la Carga del Documento", "� Informaci�n !", 1, "");
////            this.SignCFD();
//            caExcel.procesaExcelUsers(Ruta_mediaArchivo);
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Messagebox.show("Termino la Carga del Documento", "� Informaci�n !", 1, "");
//        try {
//            dodelete(this.Ruta_mediaArchivo);
//        }
//        catch (Exception e2) {
//            System.out.println(e2.toString());
//        }
		
		img_EntregaRecepcion.setSrc("/images/skf/menu/ER_00.png");
		img_OrdenMan.setSrc("/images/skf/menu/OM_00.png");
		img_Operacion.setSrc("/images/skf/menu/Operacion01.png");
		img_Reporte.setSrc("/images/skf/menu/Reporte00.png");
		img_Trazabilidad.setSrc("/images/skf/menu/Trazabilidad00.png");
		
		showWelcomePageOperacion();
		
		
		
	}
	
	public void onClick$img_Reporte(Event event) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

//		System.out.println("Si Funciono el Onclic");
////		showWelcomePageNewOrden();
//		
//		CargaExcelUser caExcel = new CargaExcelUser();
//        try {
//            mediaArchivo = Fileupload.get("Seleccione un Excel para Cargar", "Cargar Excel");
//            if (mediaArchivo == null) {
//                Messagebox.show("No Cuenta con Archivo Excel para Cargar", "�I n f o r m a t i v o!", 1, "");
//                return;
//            }
//            Ruta_mediaArchivo = saveFile(mediaArchivo, mediaArchivo.getName());
//            Messagebox.show("Iniciando la Carga del Documento", "� Informaci�n !", 1, "");
////            this.SignCFD();
//            caExcel.procesaExcelUsers(Ruta_mediaArchivo);
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Messagebox.show("Termino la Carga del Documento", "� Informaci�n !", 1, "");
//        try {
//            dodelete(this.Ruta_mediaArchivo);
//        }
//        catch (Exception e2) {
//            System.out.println(e2.toString());
//        }
		
		img_EntregaRecepcion.setSrc("/images/skf/menu/ER_00.png");
		img_OrdenMan.setSrc("/images/skf/menu/OM_00.png");
		img_Operacion.setSrc("/images/skf/menu/Operacion00.png");
		img_Reporte.setSrc("/images/skf/menu/Reporte01.png");
		img_Trazabilidad.setSrc("/images/skf/menu/Trazabilidad00.png");
		
		showWelcomePageReporte();
		
		
		
	}
	
	
	
	public void onClick$img_Home(Event event) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		img_EntregaRecepcion.setSrc("/images/skf/menu/ER_00.png");
		img_OrdenMan.setSrc("/images/skf/menu/OM_00.png");
		img_Operacion.setSrc("/images/skf/menu/Operacion00.png");
		img_Reporte.setSrc("/images/skf/menu/Reporte00.png");
		img_Trazabilidad.setSrc("/images/skf/menu/Trazabilidad00.png");
		
//		showWelcomePageCargaInicial();
		
		
		
	}
	
	public void onClick$img_Trazabilidad(Event event) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

//		System.out.println("Si Funciono el Onclic");
////		showWelcomePageNewOrden();
//		
//		CargaExcelUser caExcel = new CargaExcelUser();
//        try {
//            mediaArchivo = Fileupload.get("Seleccione un Excel para Cargar", "Cargar Excel");
//            if (mediaArchivo == null) {
//                Messagebox.show("No Cuenta con Archivo Excel para Cargar", "�I n f o r m a t i v o!", 1, "");
//                return;
//            }
//            Ruta_mediaArchivo = saveFile(mediaArchivo, mediaArchivo.getName());
//            Messagebox.show("Iniciando la Carga del Documento", "� Informaci�n !", 1, "");
////            this.SignCFD();
//            caExcel.procesaExcelUsers(Ruta_mediaArchivo);
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Messagebox.show("Termino la Carga del Documento", "� Informaci�n !", 1, "");
//        try {
//            dodelete(this.Ruta_mediaArchivo);
//        }
//        catch (Exception e2) {
//            System.out.println(e2.toString());
//        }
		
		img_EntregaRecepcion.setSrc("/images/skf/menu/ER_00.png");
		img_OrdenMan.setSrc("/images/skf/menu/OM_00.png");
		img_Operacion.setSrc("/images/skf/menu/Operacion00.png");
		img_Reporte.setSrc("/images/skf/menu/Reporte00.png");
		img_Trazabilidad.setSrc("/images/skf/menu/Trazabilidad01.png");
		
//		showWelcomePageCargaInicial();
		
		
		
	}
	
	
	
	public void showWelcomePageCargaInicial() throws Exception {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		detaCompro = null;
		
		System.out.println("Si entro para armar de parametros");
		//Se inicializa por default la addenda
//		btnAgregaAddenda.setVisible(false);
		HashMap<String, Object> map = new HashMap<String, Object>();
		int modal = 0;
//		tb_Usuarios = usuarioService.getSimpleUserByUsername(Usuario);
//		map.put("tb_user", tb_Usuarios);
//		map.put("detaCompro", detaCompro);
//		map.put("Usuario", Usuario);
//		map.put("NameRol", NameRol);
//		map.put("id_user", id_user);
//		map.put("id_pr_rol", id_pr_rol);
//		map.put("id_Corporativo", id_Corporativo);
//		map.put("listBoxOrden", listBoxOrden);
//		map.put("modal", modal);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/entregarecepcion/entregarecepcionDialog.zul", center, map);
	}
	
	public void showWelcomePageReporte() throws Exception {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		detaCompro = null;
		
		System.out.println("Si entro para armar de parametros");
		//Se inicializa por default la addenda
//		btnAgregaAddenda.setVisible(false);
		HashMap<String, Object> map = new HashMap<String, Object>();
		int modal = 0;
//		tb_Usuarios = usuarioService.getSimpleUserByUsername(Usuario);
//		map.put("tb_user", tb_Usuarios);
//		map.put("detaCompro", detaCompro);
//		map.put("Usuario", Usuario);
//		map.put("NameRol", NameRol);
//		map.put("id_user", id_user);
//		map.put("id_pr_rol", id_pr_rol);
//		map.put("id_Corporativo", id_Corporativo);
//		map.put("listBoxOrden", listBoxOrden);
//		map.put("modal", modal);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/estadistica/estadisticaDialog.zul", center, map);
	}
	
	
	public void showWelcomePageOperacion() throws Exception {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		detaCompro = null;
		
		System.out.println("Si entro para armar de parametros");
		//Se inicializa por default la addenda
//		btnAgregaAddenda.setVisible(false);
		HashMap<String, Object> map = new HashMap<String, Object>();
		int modal = 0;
//		tb_Usuarios = usuarioService.getSimpleUserByUsername(Usuario);
//		map.put("tb_user", tb_Usuarios);
//		map.put("detaCompro", detaCompro);
//		map.put("Usuario", Usuario);
//		map.put("NameRol", NameRol);
//		map.put("id_user", id_user);
//		map.put("id_pr_rol", id_pr_rol);
//		map.put("id_Corporativo", id_Corporativo);
//		map.put("listBoxOrden", listBoxOrden);
//		map.put("modal", modal);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/operacion/operacionDialog.zul", center, map);
	}
	
	
	
	public void showWelcomePageNewOrden() throws Exception {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		detaCompro = null;
		
		System.out.println("Si entro para armar de parametros");
		//Se inicializa por default la addenda
//		btnAgregaAddenda.setVisible(false);
		HashMap<String, Object> map = new HashMap<String, Object>();
		int modal = 0;
//		tb_Usuarios = usuarioService.getSimpleUserByUsername(Usuario);
//		map.put("tb_user", tb_Usuarios);
//		map.put("detaCompro", detaCompro);
		map.put("Usuario", Usuario);
		map.put("NameRol", NameRol);
		map.put("id_user", id_user);
		map.put("id_pr_rol", id_pr_rol);
		map.put("id_Corporativo", id_Corporativo);
		map.put("listBoxOrden", listBoxOrden);
		map.put("modal", modal);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/factura/facturaDialog.zul", center, map);
	}
	
	public void onClick$btn_Reporte(Event event) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Si Funciono el Onclic");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		showWelcomePageReportes();
		
	}
	
	
	
	public void showWelcomePageReportes() throws Exception {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		detaCompro = null;
		
		System.out.println("Si entro para armar de parametros");
		//Se inicializa por default la addenda
//		btnAgregaAddenda.setVisible(false);
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		int modal = 0;
//		Usuario);
//		map.put("NameRol", NameRol);
//		map.put("id_user", id_user)tb_Usuarios = usuarioService.getSimpleUserByUsername(Usuario);
//		map.put("tb_user", tb_Usuarios);
//		map.put("detaCompro", detaCompro);
//		map.put("Usuario", ;
//		map.put("id_pr_rol", id_pr_rol);
//		map.put("id_Corporativo", id_Corporativo);
//		map.put("listBoxOrden", listBoxOrden);
//		map.put("modal", modal);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/Reportes/reportes.zul", center, null);
	}
	
	
	
	public void showWelcomePageOrden() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		TbDetalleComprobante anUser = new TbDetalleComprobante();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("ordenCompra", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", listBoxOrden);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/ordenManufactura/ordenManufacturaList.zul", center, map);
	}
	
	public void showWelcomePageCartaPorte() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		TbDetalleComprobante anUser = new TbDetalleComprobante();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("ordenCompra", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", listBoxOrden);
		map.put("CartaPorteListPlantilla", 1);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/catalogos/cartaporte/cartaporteList.zul", center, map);
	}
	
	public void showWelcomePageInvProdFisc() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		TbDetalleComprobante anUser = new TbDetalleComprobante();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("ordenCompra", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		int modal = 0;
		map.put("listBoxOrden", listBoxOrden);
		map.put("modal", modal);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/invProdFisc/invProdFisicDialog.zul", center, map);
	}
	
	public void showWelcomePageInvProdTerm() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		TbDetalleComprobante anUser = new TbDetalleComprobante();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("ordenCompra", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		int modal = 0;
		map.put("listBoxOrden", listBoxOrden);
		map.put("modal", modal);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/invProdTerm/invProdTermDialog.zul", center, map);
	}
	
	public void showWelcomePageBuscar() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		TbDetalleComprobante anUser = new TbDetalleComprobante();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("ordenCompra", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", listBoxOrden);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/ordenCompra/ordenCompraList.zul", center, map);
	}
	
	
	public void showWelcomePageComparaXAMP() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("listBoxOrden", listBoxOrden);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/comparaXaMp/comparaXaMpList.zul", center, map);
	}
	
	public void showWelcomePageComparaXAPT() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("listBoxOrden", listBoxOrden);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/comparaXaPt/comparaXaPtList.zul", center, map);
	}
	
	
	
	public void showWelcomePageBuscarPT() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		TbDetalleComprobante anUser = new TbDetalleComprobante();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("ordenCompra", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", listBoxOrden);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/buscarPT/buscarPTList.zul", center, map);
	}
	
	public void onClick$id_carga_facturas(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Vamos a ver que onda con la carga de facruras, ok!!");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		showWelcomePageCargaFactura();
	}
	
	
	public void showWelcomePageCargaFactura() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();

		System.out.println("Si Funciono el Onclic de carga de facturas");
	
		
//		TbDetalleComprobante anOC = new TbDetalleComprobante();

		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("orden", anOC);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", null);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/comprobante/cargacomprobanteList.zul", center, map);
	}
	
	public void onClick$busqueda_facturas(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Búsqueda de facruras, ok!!");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		showWelcomePageBusquedaFactura();
//		TbUsuario anUser = new TbUsuario();
////		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("usuario", anUser);
//		/*
//		 * we can additionally handed over the listBox, so we have in the dialog
//		 * access to the listbox Listmodel. This is fine for synchronizing the
//		 * data in the userListbox from the dialog when we do a delete, edit or
//		 * insert a user.
//		 */
//		map.put("listBoxOrden", null);
//
//		// call the zul-file with the parameters packed in a map
//		try {
//			Executions.createComponents("/WEB-INF/pages/directorio/userList.zul", null, map);
//		} catch (Exception e) {
//			logger.error("onOpenWindow:: error opening window / " + e.getMessage());
//
//			// Show a error box
//			if (logger.isDebugEnabled()) {
//			String msg = e.getMessage();
//			String title = Labels.getLabel("message_Error");
//			MultiLineMessageBox.doSetTemplate();
//		
//			
//			MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK, "ERROR", true);
//			}
//		}
	}
	
	
	public void showWelcomePageBusquedaFactura() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		TbUsuario anUser = new TbUsuario();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("usuario", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", null);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/comprobante/busquedaComprobanteList.zul", center, map);
	}
	
	public void onClick$carga_complementos(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Vamos a ver que onda con la búsquedaa de facruras, ok!!");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		showWelcomePageCargaComplementos();
//		TbUsuario anUser = new TbUsuario();
////		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("usuario", anUser);
//		/*
//		 * we can additionally handed over the listBox, so we have in the dialog
//		 * access to the listbox Listmodel. This is fine for synchronizing the
//		 * data in the userListbox from the dialog when we do a delete, edit or
//		 * insert a user.
//		 */
//		map.put("listBoxOrden", null);
//
//		// call the zul-file with the parameters packed in a map
//		try {
//			Executions.createComponents("/WEB-INF/pages/directorio/userList.zul", null, map);
//		} catch (Exception e) {
//			logger.error("onOpenWindow:: error opening window / " + e.getMessage());
//
//			// Show a error box
//			if (logger.isDebugEnabled()) {
//			String msg = e.getMessage();
//			String title = Labels.getLabel("message_Error");
//			MultiLineMessageBox.doSetTemplate();
//		
//			
//			MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK, "ERROR", true);
//			}
//		}
	}
	
	
	public void showWelcomePageCargaComplementos() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		TbUsuario anUser = new TbUsuario();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("usuario", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", null);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/comprobante/cargaComplementosList.zul", center, map);
	}
	public void onClick$busqueda_complementos(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Complementos!!");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		showWelcomePageBusquedaComplemento();
//		TbUsuario anUser = new TbUsuario();
////		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("usuario", anUser);
//		/*
//		 * we can additionally handed over the listBox, so we have in the dialog
//		 * access to the listbox Listmodel. This is fine for synchronizing the
//		 * data in the userListbox from the dialog when we do a delete, edit or
//		 * insert a user.
//		 */
//		map.put("listBoxOrden", null);
//
//		// call the zul-file with the parameters packed in a map
//		try {
//			Executions.createComponents("/WEB-INF/pages/directorio/userList.zul", null, map);
//		} catch (Exception e) {
//			logger.error("onOpenWindow:: error opening window / " + e.getMessage());
//
//			// Show a error box
//			if (logger.isDebugEnabled()) {
//			String msg = e.getMessage();
//			String title = Labels.getLabel("message_Error");
//			MultiLineMessageBox.doSetTemplate();
//		
//			
//			MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK, "ERROR", true);
//			}
//		}
	}
	
	
	public void showWelcomePageBusquedaComplemento() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
	
//		TbUsuario anUser = new TbUsuario();
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("usuario", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", null);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/comprobante/busquedaComplementoList.zul", center, map);
	}	
	public void onClick$carga_img_carga_sap(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		System.out.println("Descargar archivo para SAP");
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		//showWelcomePageDescargaArchivoSap();

	}
	
	
	
	
	public void onClick$button_downloadXML(Event event) throws JRException, IOException, SerialException, SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		try {
			Listitem item = listBoxOrden.getSelectedItem();
			if(item!=null) {
//				TbEmpresa Obj_cfds = (TbEmpresa) item.getAttribute("data");
				
//				System.out.println("XMLComprobante ---------------->>  " + Obj_cfds.getXmlComprobante());
//				System.out.println("PDFComprobante ---------------->>  " + Obj_cfds.getPdf());
//				System.out.println("PDF2Comprobante ---------------->>  " + Obj_cfds.getPdf2());
				
				
//				if(Obj_cfds.getXml() != null){
//		
//					blobLengthxml = (int) Obj_cfds.getXml().length();  
//					
////					byte[] blobAsBytesxml = Obj_cfds.getXml().getBytes(1, blobLengthxml);
//		      
//					try {
//						Messagebox.show("Inciando la Descarga del Comprobante XML", "¡Informacion!", org.zkoss.zul.Messagebox.OK, "");
//						Filedownload.save(blobAsBytesxml, null, Obj_cfds.getRfcReceptor()+"_Comprobante_Nomina.xml");
//
//					} catch (Exception e) {
//						
//						Messagebox.show("Ocurrio un Error Durante la descarga del XML", "¡Error!", org.zkoss.zul.Messagebox.OK, "");
//					}
//				}
				
//				if(Obj_cfds.getPdf() != null){
//				
//					blobLengthpdf = (int) Obj_cfds.getPdf().length();  
//					
//					byte[] blobAsBytespdf = Obj_cfds.getPdf().getBytes(1, blobLengthpdf);
//					
//			        try {
//			        	Messagebox.show("Inciando la Descarga del Comprobante pdf", "�Informacion!", org.zkoss.zul.Messagebox.OK, "");
//							Filedownload.save(blobAsBytespdf, null, Obj_cfds.getRfcReceptor()+"_Comprobante_Nomina.pdf");
//	
//					
//					} catch (Exception e) {
//						
//						Messagebox.show("Ocurrio un Error Durante la descarga del PDF", "�Error!", org.zkoss.zul.Messagebox.OK, "");
//					}
//				}
				
//				if(Obj_cfds.getPdf2() != null){
//					
//					blobLengthpdf2 = (int) Obj_cfds.getPdf2().length();  
//					byte[] blobAsBytespdf2 = Obj_cfds.getPdf2().getBytes(1, blobLengthpdf2);
//					
//			        try {
//			        	Messagebox.show("Inciando la Descarga del Comprobante pdf Monte de Piedad", "�Informacion!", org.zkoss.zul.Messagebox.OK, "");
//							Filedownload.save(blobAsBytespdf2, null, Obj_cfds.getReceptorRfc()+"_Comprobante_Nomina_Monte2.pdf");
//	
//					
//					} catch (Exception e) {
//						
//						Messagebox.show("Ocurrio un Error Durante la descarga del PDF de Monte de Piedad", "�Error!", org.zkoss.zul.Messagebox.OK, "");
//					}
//				}
				
				
				
			}else {
				Messagebox.show("Seleccione el Comprobante que se requiere Descargar", "¡Información!", org.zkoss.zul.Messagebox.OK, "");
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		blobLengthxml = 0;
		blobLengthpdf = 0;
//		blobLengthpdf2 = 0;
		
//		blobAsBytespdf2 = null;
		blobAsBytespdf = null;
		blobAsBytesxml = null;
		
	}
	
	public void onClick$img_salir(Event event) throws JRException, SQLException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		Executions.sendRedirect("/j_spring_logout");
	}
	
	public void onClick$btnLogout(Event event) throws JRException, SQLException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

//		s.
		
		Executions.sendRedirect("/j_spring_logout");
	}
	
	public void onClick$btnPassword(Event event) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		
		// lee al usuario
//		System.out.println(Usuario);
//		tb_Usuarios = usuarioService.getSimpleUserByUsername(Usuario);
		
		// si el pwd viejo es cero levanta pantalla para actualizar el pwd
//		if(tb_Usuarios != null)
//		{
//		
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("usuario", tb_Usuarios);
//			
//			try
//			{
//				
//				Executions.createComponents("/WEB-INF/pages/cambiaPwd.zul", null, map);
//				
//			}
//			catch (Exception e)
//			{
//				
//				e.printStackTrace();
//				
//			}
//		}

//		Executions.sendRedirect("/j_spring_logout");
	}
	
//	public void onClick$btnNew(Event event) throws JRException, SQLException, IOException, InterruptedException {
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		TbEmpresa anUser = new TbEmpresa();
////		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("newOrden", anUser);
//		/*
//		 * we can additionally handed over the listBox, so we have in the dialog
//		 * access to the listbox Listmodel. This is fine for synchronizing the
//		 * data in the userListbox from the dialog when we do a delete, edit or
//		 * insert a user.
//		 */
//		map.put("listBoxOrden", null);
//
//		// call the zul-file with the parameters packed in a map
//		try {
//			Executions.createComponents("/WEB-INF/pages/ordenCompra/ordenCompraDialog.zul", null, map);
//		} catch (Exception e) {
//			logger.error("onOpenWindow:: error opening window / " + e.getMessage());
//
//			// Show a error box
//			if (logger.isDebugEnabled()) {
//			String msg = e.getMessage();
//			String title = Labels.getLabel("message_Error");
//			MultiLineMessageBox.doSetTemplate();
//		
//			
//			MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK, "ERROR", true);
//			}
//
//		}
//
//	
//	}
	
	/**
	 * Metodo encargado de mostrar la pantalla de busqueda de cliente para
	 * agregar a la factura
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onClick$btnNew(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
//		detaCompro = null;
		
		System.out.println("Si entro para armar de parametros");
		//Se inicializa por default la addenda
//		btnAgregaAddenda.setVisible(false);
		HashMap<String, Object> map = new HashMap<String, Object>();
		
//		tb_Usuarios = usuarioService.getSimpleUserByUsername(Usuario);
//		map.put("tb_user", tb_Usuarios);
//		map.put("detaCompro", detaCompro);
		map.put("Usuario", Usuario);
		map.put("NameRol", NameRol);
		map.put("id_user", id_user);
		map.put("id_pr_rol", id_pr_rol);
		map.put("id_Corporativo", id_Corporativo);
		map.put("listBoxOrden", listBoxOrden);
		
		
				
//		map.put("label_codigoBarras", label_CodBarrasFactura);
//		map.put("tb_codigoBarras", tb_CodigoFactura);
//		map.put("isFactura", "SI");
//		map.put("btnAgregaAddenda", btnAgregaAddenda);
//		

		try {
					Executions.createComponents("/WEB-INF/pages/ordenCompra/ordenCompraDialog.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
		
//		contieneAddenda();
//		contieneDetallista();
	}
	
	
	public void onClick$btnUser(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
//		TbUsuario anUser = new TbUsuario();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("usuario", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", null);

		// call the zul-file with the parameters packed in a map
		try {
			Executions.createComponents("/WEB-INF/pages/directorio/userList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / " + e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
			String msg = e.getMessage();
			String title = Labels.getLabel("message_Error");
			MultiLineMessageBox.doSetTemplate();
		
			
			MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK, "ERROR", true);
			}

		}

	
	}
	
	public void onClick$btnPerfil(Event event) throws JRException, SQLException, IOException, InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
//		TbPerfil anUser = new TbPerfil();
		Executions.sendRedirect("/WEB-INF/pages/catalogos/perfil/PerfilList.zul");
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("perfil", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
//		map.put("listBoxOrden", null);

		// call the zul-file with the parameters packed in a map
//		try {
//			Executions.createComponents("/WEB-INF/pages/catalogos/perfil/PerfilList.zul", null, map);
//		} catch (Exception e) {
//			logger.error("onOpenWindow:: error opening window / " + e.getMessage());
//
//			// Show a error box
//			if (logger.isDebugEnabled()) {
//			String msg = e.getMessage();
//			String title = Labels.getLabel("message_Error");
//			MultiLineMessageBox.doSetTemplate();
//		
//			
//			MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK, "ERROR", true);
//			}

//		}

	
	}
	
	

	/**
	 * Action onDoubleClick
	 * @param event
	 * @throws Exception
	 */
	public void onOrdenCompraItemDoubleClicked(Event event) throws Exception {

		// get the selected object
		Listitem item = listBoxOrden.getSelectedItem();

		if (item != null) {
			// CAST AND STORE THE SELECTED OBJECT
//			TbDetalleComprobante aOrden = (TbDetalleComprobante) item.getAttribute("data");

//			if (logger.isDebugEnabled()) {
//				logger.debug("--> " + aOrden.getIdDetalleComprobante());
//			}
//
//			showDetailView(aOrden);
		}
	}

//	/**
//	 * Call a new empty entry. <br>
//	 */
//	public void onClick$button_PuestoList_NewPuesto(Event event) throws Exception {
//
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		// create a new branch object
//		/** !!! DO NOT BREAK THE TIERS !!! */
//		// We don't create a new DomainObject() in the frontend.
//		// We GET it from the backend.
//		TbPuesto aPuesto = null;
//
//		showDetailView(aPuesto);
//
//	}

	
	
	/**
	 * Opens the detail view. <br>
	 * Overhanded some params in a map if needed. <br>
	 *
	 */
//	private void showDetailView(TbDetalleComprobante aOrden) throws Exception {
//
//		/*
//		 * We can call our Dialog zul-file with parameters. So we can call them
//		 * with a object of the selected item. For handed over these parameter
//		 * only a Map is accepted. So we put the object in a HashMap.
//		 */
//
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("aOrden", aOrden);
//		map.put("NameRol", NameRol);
////		map.put("detaCompro", detaCompro);
//		map.put("listBoxOrden", listBoxOrden);
//		
//		
//		/*
//		 * we can additionally handed over the listBox, so we have in the dialog
//		 * access to the listbox Listmodel. This is fine for syncronizing the
//		 * data in the customerListbox from the dialog when we do a delete, edit
//		 * or insert a customer.
//		 */
////		map.put("listBoxOrden", listBoxOrden);
//
//		// call the zul-file with the parameters packed in a map
//		try {
//			Executions.createComponents("/WEB-INF/pages/ordenCompra/ordenCompraDialog.zul", null, map);
//		} catch (Exception e) {
//			logger.error("onOpenWindow:: error opening window / " + e.getMessage());
//
//			// Show a error box
//			if (logger.isDebugEnabled()) {
//				String msg = e.getMessage();
//				String title = Labels.getLabel("message_Error");
//				MultiLineMessageBox.doSetTemplate();
//				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK, "ERROR", true);
//			}
//
//		}
//	}
	
	public void showWelcomePageProveedor() throws InterruptedException {
		// get an instance of the borderlayout defined in the zul-file
		Borderlayout bl = (Borderlayout) Path.getComponent("/IndexPrincipalWindow/border_layout_Index");
		// get an instance of the searched CENTER layout area
		Center center = bl.getCenter();
		// clear the center child comps
		center.getChildren().clear();
		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		System.out.println("Si Funciono el Onclic");
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		TbUsuario anUser = new TbUsuario();
//		Executions.sendRedirect("/WEB-INF/pages/directorio/userList.zul");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("usuario", anUser);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for synchronizing the
		 * data in the userListbox from the dialog when we do a delete, edit or
		 * insert a user.
		 */
		map.put("listBoxOrden", null);
		// call the zul-file and put it in the center layout area
		Executions.createComponents("/WEB-INF/pages/welcome.zul", center, map);
	}

	/**
	 * when the checkBox 'Show All' for filtering is checked. <br>
	 *
	 * @param event
	 */
	public void onCheck$checkbox_Puesto_ShowAll(Event event) {
		//System.out.println("checkbox_Puesto_ShowAll"+checkbox_Puesto_ShowAll.isChecked());
		if (logger.isDebugEnabled()) {
			logger.debug("**--> " + event.toString());
		}

		checkbox_Puesto_ShowAll.setChecked(true);
//		tb_CatNombrePuesto.setValue("");
//		tb_CatDescPuesto.setValue("");
		cb_CatPuestoActivo.setSelectedIndex(0);

		restricciones.clear();

//		if(sBean.getIdCorporativo()!=null)
//		{
//			restriccion = "tbCorporativo.nukidCorporativo="+sBean.getIdCorporativo();
//			restricciones.add(restriccion);
//		}
		// Set the ListModel.
		
//		getPagedListWrapper().init("TbDetalleComprobante", listBoxOrden, paging_Orden, restricciones);

	}

	/**
	 * Filters <br>
	 */
	public void onClick$button_Search(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			if (event!=null)
			logger.debug("--> " + event.toString());
		}
//		Listitem Listitem = cb_CatPuestoActivo.getSelectedItem();

		restricciones.clear();

		if(tb_folio.getValue()!=null || tb_uuid.getValue()!=null || tb_rfc.getValue() !=null)
		{

//			checkbox_Puesto_ShowAll.setChecked(false);

			restriccion="folio like '%"+tb_folio.getValue().toUpperCase()+"%'";
			restricciones.add(restriccion);

			if(!tb_uuid.getValue().equals("")){
				restriccion="UUID like '%"+tb_uuid.getValue()+"%'";
				restricciones.add(restriccion);
			}
			
			
			
			FechaIni = bd_FechaIni.getText();
			 String[] posicion = FechaIni.split("/");
	        for (int i = 0; i < posicion.length; i++) {
	       	 FechaInicial = posicion[1]+"/"+posicion[0]+"/"+posicion[2];

	         }
	        
	        FechaFin = bd_FechaFin.getText();
			 String[] posicionfin = FechaFin.split("/");
	       for (int i = 0; i < posicionfin.length; i++) {
	    	   FechaFinal = posicionfin[1]+"/"+posicionfin[0]+"/"+posicionfin[2];
	    	  
	        }
			
			restriccion="fecha BETWEEN '"+FechaInicial+"' AND '"+FechaFinal+"'";
			restricciones.add(restriccion);
			
			if(!Administrador.equals("1")){
				
			restriccion="cfds0_.receptorRfc = '"+Rfc+"'";
			restricciones.add(restriccion);
			
			}else{
				if(!tb_rfc.getValue().equals("")){
					restriccion="cfds0_.receptorRfc like '%"+tb_rfc.getValue().toUpperCase()+"%'";
					restricciones.add(restriccion);
				}
			}

//			getPagedListWrapper().init("TbPuesto", listBoxOrden, paging_Orden, restricciones);
//			wrappper.init("TbDetalleComprobante", listBoxOrden, paging_Orden, restricciones);

			if(listBoxOrden.getModel().getSize() == 0)
	        {
	            Messagebox.show("No se encontro ningun Registro", "Información", org.zkoss.zul.Messagebox.OK, "");

	        }
		}

	}


	
	public void onOK$tb_folio(Event event) throws JRException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		try {
			onClick$button_Search(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onOK$tb_rfc(Event event) throws JRException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		try {
			onClick$button_Search(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onOK$tb_uuid(Event event) throws JRException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		try {
			onClick$button_Search(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// ++++++++++++++++++ getter / setter +++++++++++++++++++//
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	private String saveFile(final Media media, final String nombre) {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        String pathImagenes = "";
        String DireccionCompleta = "";
        try {
            final InputStream fin = media.getStreamData();
            in = new BufferedInputStream(fin);
            final String realString = Sessions.getCurrent().getWebApp().getRealPath("");
            final String l_stCreKey = String.valueOf(realString.substring(0, realString.lastIndexOf(File.separator))) + File.separator;
            pathImagenes = String.valueOf(l_stCreKey) + "logos";
            final File dirImagenes = new File(pathImagenes);
            if (!dirImagenes.exists()) {
                dirImagenes.mkdirs();
            }
            File file = null;
            file = new File(String.valueOf(pathImagenes) + File.separator + nombre);
            final OutputStream fout = new FileOutputStream(file);
            out = new BufferedOutputStream(fout);
            final byte[] buffer = new byte[1024];
            for (int ch = in.read(buffer); ch != -1; ch = in.read(buffer)) {
                out.write(buffer, 0, ch);
            }
            PrincipalCtrl.logger.info((Object)("sucessed upload :" + media.getName()));
            DireccionCompleta = String.valueOf(pathImagenes) + File.separator + nombre;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e2) {
            throw new RuntimeException(e2);
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException e3) {
                throw new RuntimeException(e3);
            }
        }
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        catch (IOException e3) {
            throw new RuntimeException(e3);
        }
        return DireccionCompleta;
    }

	public void dodelete(final String Ruta) {
        try {
            final File archivo = new File(Ruta);
            final boolean estatus = archivo.delete();
            if (!estatus) {
                System.out.println("Error no se ha podido eliminar el  archivo");
            }
            else {
                System.out.println("Se ha eliminado el archivo exitosamente");
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}
