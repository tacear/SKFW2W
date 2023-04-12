package mx.com.hunkabann.skf.frontent.OrdenManufactura;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

//import mx.com.hunkabann.saas.frontent.util.GFCBaseListCtrl;
//import mx.com.hunkabann.saas.mapeo.TbUsuario;
import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.frontent.OrdenManufactura.model.PlacaListModelItemRenderer;
import mx.com.hunkabann.skf.frontent.OrdenManufactura.model.ProductoListModelItemRenderer;
import mx.com.hunkabann.skf.frontent.principal.model.PrincipalListModelItemRenderer;
import mx.com.hunkabann.skf.frontent.util.GFCBaseCtrl;
import mx.com.hunkabann.skf.frontent.util.GFCBaseListCtrl;
import mx.com.hunkabann.skf.frontent.util.MultiLineMessageBox;
import mx.com.hunkabann.skf.frontent.util.pagging.PagedListWrapper;
import mx.com.hunkabann.skf.mapeo.TbCatStatus;
import mx.com.hunkabann.skf.mapeo.TbOrdenManufactura;
import mx.com.hunkabann.skf.mapeo.TbPlacaOm;
import mx.com.hunkabann.skf.mapeo.TbProdTermMatPrim;
import mx.com.hunkabann.skf.mapeo.TbProductoTerminado;
import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContext;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.http.SimpleSession;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class OrdenManufacturaDialogCtrlV1 extends GFCBaseListCtrl<TbOrdenManufactura> implements Serializable {

	private static final long serialVersionUID = 2038742641853727975L;
	private transient static final Logger logger = Logger.getLogger(OrdenManufacturaDialogCtrlV1.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected transient Window ordenCompraV1DialogWindow; // autowired

	// filter components
	protected transient Checkbox checkbox_ShowAll; // autowired
	
	protected transient Textbox tb_sku_Prod; // aurowired
	protected transient Textbox tb_Folio; // autowired
	protected transient Textbox tb_SKU_Ubicacion;
	protected transient Textbox tb_Usuario;
	protected transient Listbox lb_Scrap_Merma;
	
	// listbox userList
	protected transient Borderlayout borderlayoutUser;
	protected transient Paging paging_UserList;
	protected transient Listbox listBoxUser;
	protected transient Listheader listheader_UserList_usrLoginname;
	protected transient Listheader listheader_UserList_nombre;
	protected transient Listheader listheader_UserList_usrEnabled;

	// checkRights
	protected transient Button button_UserList_NewUser;
	protected transient Button btnClose;
	protected transient Hbox hbox_UserList_SearchUsers;
	protected transient Button button_UserList_Search;
	protected transient Image img_Cargar;
	protected transient Image img_Logout;
	protected transient Button button_Reporte;
	
	protected transient Listbox listBoxOrden; // aurowired
	protected transient Paging paging_Orden; // aurowired
	
	int modal = 0;
	
	protected transient Listbox listBoxOrdenManu; // aurowired
	
	protected transient Listbox listBoxProducto; // aurowired
	protected transient Paging paging_OrdenProducto; // aurowired
	
	
	protected transient Listbox listBoxPLACA; // aurowired
	protected transient Paging paging_OrdenPLACA; // aurowired
	
	
	
	
	
	
//	protected transient Image btnNew;
	protected transient Button btnPassword;
	protected transient Label Label_RFC;
	protected transient Label Label_OM;
	protected transient Label Label_PARTE;
	
	protected transient Label Label_OMP;
	protected transient Label Label_PARTEP;
	protected transient Label Label_TOTALP;
	
	protected transient Label Label_OMPLACA;
	protected transient Label Label_PARTEPLACA;
	
	
	//hbox
	protected transient Hbox hbox_Usuarios;
//	protected transient Hbox hbox_Facturas;
//	protected transient Hbox hbox_Contraseña;
	
	protected transient Listheader listheader_IdOrden;
	protected transient Listheader listheader_Proveedor;
	protected transient Listheader listheader_fecha;
	protected transient Listheader listheader_Identificador;
	protected transient Listheader listheader_Total;
	protected transient Listheader listheader_Moneda;
	protected transient Listheader listheader_Estatus;
	
	
	protected transient Tab B;
	protected transient Tab C;
	
//	 Usuario = "";
	PagedListWrapper<TbOrdenManufactura> wrappper = new PagedListWrapper<TbOrdenManufactura>();
	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
    HttpSession session = (HttpSession) s.getNativeSession();
	
//	String Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
//	private SessionBean sBean = (SessionBean) Sessions.getCurrent().getWebApp().getAttribute(Usuario);
    
    PagedListWrapper<TbPlacaOm> wrappperPlaca = new PagedListWrapper<TbPlacaOm>();
	
//	TbDetalleComprobante detaCompro = new TbDetalleComprobante();

	// row count for listbox
	private transient int countRows;
	
	UsuarioService usuarioService = new UsuarioService();
//	VentaService ventaserv = new VentaService();
	
	String NameRol = "";
	int id_user;
	int id_pr_rol;
	int id_Corporativo;
	
//	TbUsuario tb_Usuarios = new TbUsuario();
//	TbCorporativo corporativo = new TbCorporativo();
	
	String CadenaCondiciones = "";
	
	String URL_PDF = "";
	
	private Media House_mediaArchivo;
	String House_Ruta_mediaArchivo;
	
	TbOrdenManufactura OrdenManu = new TbOrdenManufactura();

	/**
	 * default constructor.<br>
	 */
	public OrdenManufacturaDialogCtrlV1() {
		super();
		restricciones.clear();

		if (logger.isDebugEnabled()) {
			logger.debug("--> super()");
		}
	}
	

	public void onCreate$ordenCompraV1DialogWindow(Event event) throws Exception {
		
		HttpSession session = (HttpSession) s.getNativeSession();		
//		Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
		
//		Properties email_props = new Properties();
//		InputStream is = null;
//		
//		try {
//			
//		    is = getClass().getResourceAsStream("/email.properties");
//		    email_props.load(is);
//		    
//		} 
//		catch(Exception e) 
//		{
//			e.printStackTrace();
//		}
//		
//		
//		
//		/URL_PDF = email_props.getProperty("UrlSistema");
		
		Map<String, Object> args = getCreationArgsMap(event);
		
		if (args.containsKey("listBoxOrden")) {
			listBoxOrden = (Listbox) args.get("listBoxOrden");
		} else {
			listBoxOrden = null;
		}
		
		if (args.containsKey("modal"))
			modal = (Integer) args.get("modal");
		
		if (args.containsKey("aOrden")){
			OrdenManu = (TbOrdenManufactura) args.get("aOrden");
		} else {
			OrdenManu= null;		
		}
		
		
		TbProductoTerminado ProdTerm = new TbProductoTerminado();
		
		TbCatStatus Status = new TbCatStatus();
		
		ProdTerm = usuarioService.getPRoductoTerm(OrdenManu.getTbProductoTerminado().getNukIdProdTerm());
//		ProdTerm.setChSku(userService.getPRoductoTerm(inv_Detalle_MP.getTbProductoTerminado().getNukIdProdTerm(),inv_Detalle_MP.getTbEmpresa().getNukIdEmpresa()));
		Status = usuarioService.getCatStatus(OrdenManu.getTbProductoTerminado().getNukIdProdTerm());
		
		
		Label_PARTE.setValue("NUM PARTE: "+ ProdTerm.getChSku());
		
		Label_OM.setValue("ORDEN MANUFACTURA: " + OrdenManu.getChOrdenManufactura());

        int maxListBoxHeight = (heightWindow - 118);

        borderlayoutUser.setHeight(String.valueOf(maxListBoxHeight) + "px");

        /**
		 * Calculate how many rows have been place in the listbox. Get the
		 * currentDesktopHeight from a hidden Intbox from the index.zul that are
		 * filled by onClientInfo() in the indexCtroller
		 */
		
		
//		id_user = usuarioService.getIdByUserName(Usuario);
		
//		tb_Usuarios = usuarioService.getUserByUsername(Usuario);
		
//		System.out.println("session.getAttribute ----------------------->> " +session.getAttribute("idCorp"));
//		
//		id_Corporativo = ((Integer) session.getAttribute("idCorp")).intValue();
		
//		System.out.println("session.getAttribute ----------------------->> " +tb_Usuarios.getIdCorporativo());
		
//		id_Corporativo = tb_Usuarios.getIdCorporativo();
//		
//		
//		corporativo = usuarioService.getTbCorporativo(id_Corporativo);
		
//		id_Corporativo = usuarioService.getIdByCorporativo(Usuario);
		
		restricciones.clear();
		
//		restriccion = " rfc = '" + corporativo.getRfc()+ "'" ;
//		restricciones.add(restriccion);
		
//		if(Usuario.equals("admin") || Usuario.equals("admin_Serv")){
////			if(Usuario.equals("admin")){
//				hbox_Usuarios.setVisible(true);
////				hbox_Facturas.setVisible(true);
////				hbox_Contraseña.setVisible(true);
////				btnNew.setVisible(true);
////			}
//				
//				
//		}else{
		
			
//			id_pr_rol = usuarioService.getIdByPerfilRol(id_user);
//			
//			NameRol = usuarioService.getNameRol(id_pr_rol);
			
			
			
//			if(NameRol.equals("Rol_Solicitante")){
//				hbox_Usuarios.setVisible(false);
////				hbox_Facturas.setVisible(false);
//				
////				restriccion = "id_Usuario = " + id_user ;
////				restricciones.add(restriccion);
////				
////				//restriccion = "nuautoriza is NULL " ;
////				//restricciones.add(restriccion);
////				
////				restriccion = "nuautorizadoCxP = " + 0;
////				restricciones.add(restriccion);
//			}
//			if(NameRol.equals("Rol_Autorizador")){
//				hbox_Usuarios.setVisible(false);
////				hbox_Facturas.setVisible(false);
//				
//							
////				if(tb_Usuarios.getAutoriza()==1){
////					restriccion = "id_User_Autoriza = " + id_user ;
////					restricciones.add(restriccion);
////					
////					restriccion = "nuautoriza is NULL " ;
////					restricciones.add(restriccion);
////				}
////				if(tb_Usuarios.getAutorizaArea()==1){
////					
////				restriccion = "nuautoriza = " + AUTO_ORDEN_COMPRA ;
////				restricciones.add(restriccion);
////				
////				restriccion = "nuautorizaArea  is NULL"  ;
////				restricciones.add(restriccion);
////				
////				restriccion = "Id_User_Autoriza_Area = " + id_user ;
////				restricciones.add(restriccion);
////				}
//			}
//			if(NameRol.equals("Rol_Proveedor")){
//				
//				TbDetalleComprobante detalle = new TbDetalleComprobante();
//				
//				detalle = ventaserv.getDetalleOC(id_user);
//				
//				hbox_Usuarios.setVisible(false);
////				hbox_Facturas.setVisible(true);
//				
//				System.out.println("Detalle Autoriza = "+ detalle.getIdUserAutorizaArea());
//				
////				if(detalle.getIdUserAutoriza() != null){
////					restriccion = "nuautoriza = " + AUTO_ORDEN_COMPRA ;
////					restricciones.add(restriccion);
////				}
////				if(detalle.getIdUserAutorizaArea() != null){
////				
////					restriccion = "nuautorizaArea = " + AUTO_ORDEN_COMPRA ;
////					restricciones.add(restriccion);
////				}
//				
////				restriccion = "Id_Proveedor = " + id_user ;
////				restricciones.add(restriccion);
//				
//			}
//			if(NameRol.equals("Rol_Admin")){
//				hbox_Usuarios.setVisible(true);
////				hbox_Facturas.setVisible(true);
////				hbox_Contraseña.setVisible(true);
////				btnNew.setVisible(true);
//			}
//			if(NameRol.equals("Rol_CxP")){
//				hbox_Usuarios.setVisible(true);
////				hbox_Facturas.setVisible(false);
////				hbox_Contraseña.setVisible(true);
////				btnNew.setVisible(true);
//			}
//			if(NameRol.equals("Rol_Soporte")){
//				hbox_Usuarios.setVisible(false);
////				hbox_Facturas.setVisible(false);
////				hbox_Contraseña.setVisible(false);
////				btnNew.setVisible(false);
//			}
//		}
		

//		try {
//
//			System.out.println("Usuario:    " + Usuario);
//			
////			label_PiePagina.setValue("Usuario:    " + Usuario);
//
//		} catch (Exception e) {
//
//			Messagebox.show(e.toString(), "¡Error!", org.zkoss.zul.Messagebox.OK, "");
//		}
		
		
//		// not used listheaders must be declared like ->
//		listheader_IdOrden.setSortAscending(new FieldComparator("serie", true));
//		listheader_IdOrden.setSortDescending(new FieldComparator("serie", false));
//		listheader_fecha.setSortAscending(new FieldComparator("folio", true));
//		listheader_fecha.setSortDescending(new FieldComparator("folio", false));
//		listheader_Proveedor.setSortAscending(new FieldComparator("UUID", true));
//		listheader_Proveedor.setSortDescending(new FieldComparator("UUID", false));
//		listheader_Identificador.setSortAscending(new FieldComparator("rfc", true));
//		listheader_Identificador.setSortDescending(new FieldComparator("rfc", false));
//		listheader_Total.setSortAscending(new FieldComparator("receptor_rfc", true));
//		listheader_Total.setSortDescending(new FieldComparator("receptor_rfc", false));
//		listheader_Moneda.setSortAscending(new FieldComparator("receptor_nombre", true));
//		listheader_Moneda.setSortDescending(new FieldComparator("receptor_nombre", false));
//		listheader_Estatus.setSortAscending(new FieldComparator("estatusFactura", true));
//		listheader_Estatus.setSortDescending(new FieldComparator("estatusFactura", false));

		// set the paging params
		paging_Orden.setPageSize(20);
		paging_Orden.setDetailed(true);
//
//		checkbox_ShowAll.setChecked(true);
//		
//		// Set the ListModel.
//		wrappper.init("TbOrdenManufactura", listBoxOrden, paging_Orden, restricciones);
//		// set the itemRenderer
//		listBoxOrden.setItemRenderer(new PrincipalListModelItemRenderer());
		
		
		
		
//		restricciones.clear();
		
		
//		
//		
		
		ordenCompraV1DialogWindow.doModal();

	}
	
	public void onClick$B(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		restricciones.clear();
		System.out.println("Si Funciono el Onclic");
		
		TbProductoTerminado ProdTerm = new TbProductoTerminado();
		
		ProdTerm = usuarioService.getPRoductoTerm(OrdenManu.getTbProductoTerminado().getNukIdProdTerm());
		
		Label_PARTEP.setValue("NUM PARTE: "+ ProdTerm.getChSku());
		
		Label_OMP.setValue("ORDEN MANUFACTURA: " + OrdenManu.getChOrdenManufactura());
		
		Label_TOTALP.setValue("TOTAL: " + OrdenManu.getNuTotal());
		
		
		paging_OrdenProducto.setPageSize(20);
		paging_OrdenProducto.setDetailed(true);
		
		
		restriccion = " nukIdProdTerm = " + OrdenManu.getTbProductoTerminado().getNukIdProdTerm() +"" ;
		restricciones.add(restriccion);
		
		wrappper.init("TbProdTermMatPrim", listBoxProducto, paging_OrdenProducto, restricciones);
		// set the itemRenderer
		listBoxProducto.setItemRenderer(new ProductoListModelItemRenderer());
		
		restricciones.clear();
		
	}
	
	
	public void onClick$C(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		restricciones.clear();
		System.out.println("Si Funciono el Onclic");
		
		TbProductoTerminado ProdTerm = new TbProductoTerminado();
		
		ProdTerm = usuarioService.getPRoductoTerm(OrdenManu.getTbProductoTerminado().getNukIdProdTerm());
		
		Label_OMPLACA.setValue("ORDEN MANUFACTURA: " + OrdenManu.getChOrdenManufactura());
		
		Label_PARTEPLACA.setValue("NUM PARTE: "+ ProdTerm.getChSku());
		
		
		paging_OrdenPLACA.setPageSize(20);
		paging_OrdenPLACA.setDetailed(true);
		
		
		restriccion = " nukIdOrdenManufactura = " + OrdenManu.getNukIdOrdenManufactura()+"" ;
		restricciones.add(restriccion);
		
		wrappperPlaca.init("TbPlacaOm", listBoxPLACA, paging_OrdenPLACA, restricciones);
//		// set the itemRenderer
		listBoxPLACA.setItemRenderer(new PlacaListModelItemRenderer());
		
		restricciones.clear();
		
	}

	/**
	 * Metodo encargado de mostrar la pantalla de busqueda de cliente para
	 * agregar a la factura
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onClick$img_Cargar(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si Funciono el Onclic");
		
//		CargaExcelCatHouse caExcel = new CargaExcelCatHouse();
        try {
        	House_mediaArchivo = Fileupload.get("Seleccione un Excel para Cargar", "Cargar Excel");
            if (House_mediaArchivo == null) {
            	MultiLineMessageBox.show("No Cuenta con Archivo Excel para Cargar", "¡I n f o r m a t i v o!", 1, "");
                return;
            }
            House_Ruta_mediaArchivo = saveFile(House_mediaArchivo, House_mediaArchivo.getName());
            MultiLineMessageBox.show("Iniciando la Carga del Documento", "¡ Información !", 1, "");
//            this.SignCFD();
//            caExcel.procesaExcelCatHouse(House_Ruta_mediaArchivo);
            
            Thread.sleep(10000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        MultiLineMessageBox.show("Termino la Carga del Documento", "¡ Información !", 1, "");
        try {
            dodelete(this.House_Ruta_mediaArchivo);
        }
        catch (Exception e2) {
            System.out.println(e2.toString());
        }
	}
	
	private String  saveFile(Media media, String nombre) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		String pathImagenes ="";
		String DireccionCompleta ="";
		
		try {
			
			InputStream fin = media.getStreamData();
			in = new BufferedInputStream(fin);


//			File baseDir = new File("");
			
//			
			String realString=Sessions.getCurrent().getWebApp().getRealPath("");
			String l_stCreKey=realString.substring(0, realString.lastIndexOf(File.separator))+File.separator;
		
//			if(pathImagenes.lastIndexOf("bin")>-1)
//				pathImagenes = pathImagenes.substring(0,pathImagenes.lastIndexOf("bin"));
//			else
//				pathImagenes+=File.separator;
			
			pathImagenes = l_stCreKey +"logos";

			File dirImagenes =  new File(pathImagenes);

			if (!dirImagenes.exists()) {
				dirImagenes.mkdirs();
			}

			File file = null;
			file = new File(pathImagenes + File.separator + nombre);

			OutputStream fout = new FileOutputStream(file);
			out = new BufferedOutputStream(fout);
			byte buffer[] = new byte[1024];
			int ch = in.read(buffer);
			while (ch != -1) {
				out.write(buffer, 0, ch);
				ch = in.read(buffer);
			}
			logger.info("sucessed upload :" + media.getName());
			DireccionCompleta = pathImagenes + File.separator + nombre;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (out != null)
					out.close();

				if (in != null)
					in.close();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		return DireccionCompleta;
	}
	
	public void dodelete(String Ruta){
		 try{

	            File archivo = new File(Ruta);

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
	
	/**
	 * Action onDoubleClick
	 * @param event
	 * @throws Exception
	 */
	public void onOrdenCompraItemDoubleClicked(Event event) throws Exception {

		// get the selected object
		Listitem item = listBoxOrden.getSelectedItem();
//
		if (item != null) {
			// CAST AND STORE THE SELECTED OBJECT
			TbOrdenManufactura aOrden = (TbOrdenManufactura) item.getAttribute("data");

			if (logger.isDebugEnabled()) {
//				logger.debug("--> " + aOrden.getNukIdInventDetalleMp());
			}

			showDetailView(aOrden);
		}
	}
	
	/**
	 * Opens the detail view. <br>
	 * Overhanded some params in a map if needed. <br>
	 *
	 */
	private void showDetailView(TbOrdenManufactura aOrden) throws Exception {

		/*
		 * We can call our Dialog zul-file with parameters. So we can call them
		 * with a object of the selected item. For handed over these parameter
		 * only a Map is accepted. So we put the object in a HashMap.
		 */

		int modal = 1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("aOrden", aOrden);
		map.put("listBoxOrden", listBoxOrden);
		map.put("modal", modal);
		
		
		try {
			Executions.createComponents("/WEB-INF/pages/invProdFisc/invProdFisicDialog.zul", null, map);
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
//		int modal = 1;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("aOrden", aOrden);
//		map.put("NameRol", NameRol);
//		map.put("listBoxOrden", listBoxOrden);
//		map.put("modal", modal);
//		
//		
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
	
	
	public void onClick$btn_Cerrar(Event event) throws Exception {
		
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

			ordenCompraV1DialogWindow.onClose();
	}
	
//	public void onClick$button_Reporte(Event event) throws Exception {
//
//		
//		SimpleDateFormat formato  =  new SimpleDateFormat("yyyyMMdd");
//		
//		String Condicion_RFC = "";
//		String Condicion_Identificador = "";
//		String Condicion_Fecha_Ini = "";
//		String Condicion_Fecha_Fin = "";
//		String Condicion_Fechas = "";
//		String Condicion_Estatus = "";
//		
//		
//		if (!tb_Nombre.getValue().equals("") ) {
////			checkbox_Proveedor_ShowAll.setChecked(false);
//			
//				String id_cadenaids ="";
//				int id_contador= 0;
//						
//				List<String> idsUsuarios_id = usuarioService.gettbPersona(id_Corporativo, tb_Nombre.getValue(),"chNombre");
//				if(idsUsuarios_id.size()>0){
//					for(String str_id : idsUsuarios_id)
//					{
//						if(id_contador == 0){
//							id_cadenaids= id_cadenaids+str_id;
//							id_contador++;
//						}else{
//							id_cadenaids= id_cadenaids+","+str_id;
//						}
//					    //imprimimos el objeto pivote
////					    System.out.println(str);
////					    cadenaids= str;
//					}
//					System.out.println("Cadena con IDs = "+id_cadenaids);
////					checkbox_UserList_ShowAll.setChecked(false);
//					Condicion_RFC = " and Id_Proveedor in (" + id_cadenaids + ")";
////					restricciones.add(restriccion);			
//					
//				}else{
//					Condicion_RFC = "";
//				}	
//				
//			
//		
//		}
//		
//		if (!tb_Razon.getValue().equals("") ) {
////			checkbox_Proveedor_ShowAll.setChecked(false);
//			
//				String id_cadenaids ="";
//				int id_contador= 0;
//						
//				List<String> idsUsuarios_id = usuarioService.gettbPersona(id_Corporativo, tb_Razon.getValue(),"chRazonSocial");
//				if(idsUsuarios_id.size()>0){
//					for(String str_id : idsUsuarios_id)
//					{
//						if(id_contador == 0){
//							id_cadenaids= id_cadenaids+str_id;
//							id_contador++;
//						}else{
//							id_cadenaids= id_cadenaids+","+str_id;
//						}
//					    //imprimimos el objeto pivote
////					    System.out.println(str);
////					    cadenaids= str;
//					}
//					System.out.println("Cadena con IDs = "+id_cadenaids);
////					checkbox_UserList_ShowAll.setChecked(false);
//					Condicion_RFC = " and Id_Proveedor in (" + id_cadenaids + ")";
////					restricciones.add(restriccion);			
//					
//				}else{
//					Condicion_RFC = "";
//				}	
//				
//			
//		
//		}
//		
//		if (!tb_Identi_OC.getValue().equals("") ) {
//			
//			Condicion_Identificador = " and ident_oc like '%" + tb_Identi_OC.getValue() + "%'";
////			restricciones.add(restriccion);
//		}else{
//			Condicion_Identificador = "";
//		}
//		
//		if(bd_FechaIni.getValue()!=null && !bd_FechaIni.getValue().equals("") && bd_FechaFin.getValue()!=null && !bd_FechaFin.getValue().equals("")){
//			Condicion_Fechas = " and fecha_Creacion BETWEEN '"+formato.format( bd_FechaIni.getValue()) +" 00:00' AND '"+formato.format( bd_FechaFin.getValue())+" 23:59'";
////			restricciones.add(restriccion);
//		}else{
//			
//			Condicion_Fechas = "";
//			
//			if(bd_FechaIni.getValue()!=null && !bd_FechaIni.getValue().equals("") )
//			{
//				Condicion_Fecha_Ini = " and fecha_Creacion >= '"+ formato.format( bd_FechaIni.getValue())+" 00:00'";
////				restricciones.add(restriccion);
//			}else{
//				Condicion_Fecha_Ini = "";
//			}
//	
//			if(bd_FechaFin.getValue()!=null && !bd_FechaFin.getValue().equals("") )
//			{
//				Condicion_Fecha_Fin = " and fecha_Creacion <= '"+ formato.format( bd_FechaFin.getValue())+" 23:59'";
////				restricciones.add(restriccion);
//			}else{
//				Condicion_Fecha_Fin = "";
//			}
//		}
//		
//		if(lb_Status.getSelectedItem() != null && !lb_Status.getSelectedItem().getLabel().equals("SELECCIONE")){
//			if(lb_Status.getSelectedItem().getLabel()!=null && !lb_Status.getSelectedItem().getLabel().equals("") )
//			{
//				Condicion_Estatus = " and Estatus_Factura like '%"+ lb_Status.getSelectedItem().getLabel()+"%'";
////				restricciones.add(restriccion);
//			}else{
//				Condicion_Estatus = "";
//			}
//		}
//
//		
//		Messagebox.show("Inicia el Proceso del Reporte", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//		
//		
//		
//
//		ventaserv.getOrdenCompraExcel(Condicion_RFC,Condicion_Identificador,Condicion_Fecha_Ini,Condicion_Fecha_Fin,Condicion_Fechas,Condicion_Estatus,id_Corporativo);
//		
//		
//	}
	public void onClick$button_XML(Event event) throws Exception {
//		Listitem item = listBoxOrden.getSelectedItem();
//		System.out.println("item "+item);
//		TbInventarioDetalleMp anComprobante = null;
//		if (item == null) {
//			Messagebox.show("Debes seleccionar al menos un comprobante para descargar los archivos.", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//
//		}
//		 if (listBoxOrden.getSelectedCount() > 0) {
//			 for( Object o : listBoxOrden.getSelectedItems() ){
//					Listitem li = (Listitem)o;
//					TbInventarioDetalleMp sit =(TbInventarioDetalleMp)li.getAttribute("data");
////				anComprobante = (TbInventarioDetalleMp) item.getAttribute("data");
//				System.out.println(sit.getNukIdInventDetalleMp());
////				 System.out.println("anComprobante "+sit.getReceptorNombre()+"_"+ sit.getRfc()+"_"+sit.getSerie()+sit.getFolio());
////					if(sit.getXmlComprobante()!=null){
////						System.out.println("Tiene xml "+sit.getXmlComprobante());
////						Messagebox.show("Iniciando la descarga del comprobante xml.", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////						saveFileXML(sit.getRfc()+"_"+sit.getSerie()+sit.getFolio(),sit.getXmlComprobante());
////						
////						
////						Messagebox.show("Termino la descarga del comprobante xml.", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////						Thread.sleep(5000);
//////						
//////						System.out.println("url: "+ URL_PDF);
////						
////						File carpeta = new File(URL_PDF);
////						String[] listado = carpeta.list();
////						if (listado == null || listado.length == 0) {
////						    System.out.println("No hay elementos dentro de la carpeta actual");
////						    return;
////						}
////						else {
////							int unaVezPDF = 0;
////						    for (int i=0; i< listado.length; i++) {
////						        System.out.println(listado[i]);
////						        if(unaVezPDF == 0){
////							        if (listado[i].startsWith(sit.getRfc()+"_"+sit.getSerie()+sit.getFolio())){
////							        	
////							        	Messagebox.show("Iniciando la descarga del comprobante PDF.", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////							        	String PDF = "";
////							        	PDF = listado[i].replace(".pdf", "").replace(".PDF", "").replace(".xml", "").replace(".XML", "");
////										saveFilePDF(listado[i].replace(".pdf", ""), URL_PDF);
////										
////										Messagebox.show("Termino la descarga del comprobante PDF.", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////										Thread.sleep(5000);
////										
////										unaVezPDF++;
////							        }
////							    }
////						    }
////						}
//////						
////						
////							}
////					saveFilePDF(sit.getRfc()+"_"+sit.getSerie()+sit.getFolio(), URL_PDF);
//	    	   }
//		 }
//		 
////		 for( Object o : listBoxOrden.getSelectedItems() ){
////				Listitem li = (Listitem)o;
////				TbInventarioDetalleMp sit =(TbInventarioDetalleMp)li.getAttribute("data");
////				
////				System.out.println(sit.getComprobanteId());
////		 }
		
		if (!tb_Usuario.getValue().equals("") ) {
//			checkbox_ShowAll.setChecked(false);
			
			String id_cadenaids ="";
			int id_contador= 0;
					
//			List<String> idsUsuarios_id = usuarioService.gettbUsuario(tb_Usuario.getValue(),"username");
//			if(idsUsuarios_id.size()>0){
//				for(String str_id : idsUsuarios_id)
//				{
//					if(id_contador == 0){
//						id_cadenaids= id_cadenaids+str_id;
//						id_contador++;
//					}else{
//						id_cadenaids= id_cadenaids+","+str_id;
//					}
//				    //imprimimos el objeto pivote
////				    System.out.println(str);
////				    cadenaids= str;
//				}
//				System.out.println("Cadena con IDs = "+id_cadenaids);
////				restriccion = "id_Usuario in (" + id_cadenaids + ")";
////				restricciones.add(restriccion);		
//				
//				CadenaCondiciones += " and IDMP.id_Usuario in (" + id_cadenaids + ") ";
//				
//			}
			
		}
		
		if (!tb_Folio.getValue().equals("") ) {
//			checkbox_ShowAll.setChecked(false);
			
//			restriccion = "chParte like '%" + tb_Folio.getValue() +"%'";
//			restricciones.add(restriccion);
			
			CadenaCondiciones += " and IDMP.chParte like '%" + tb_Folio.getValue() +"%'";
					
		
		}
		
		
		if (!tb_sku_Prod.getValue().equals("") ) {
			checkbox_ShowAll.setChecked(false);
			String id_cadenaids ="";
			int id_contador= 0;
					
//			List<String> idsUsuarios_id = usuarioService.gettbProd(tb_sku_Prod.getValue(),"chSku");
//			if(idsUsuarios_id.size()>0){
//				for(String str_id : idsUsuarios_id)
//				{
//					if(id_contador == 0){
//						id_cadenaids= id_cadenaids+str_id;
//						id_contador++;
//					}else{
//						id_cadenaids= id_cadenaids+","+str_id;
//					}
//				    //imprimimos el objeto pivote
////				    System.out.println(str);
////				    cadenaids= str;
//				}
//						
//				
//				CadenaCondiciones += " and IDMP.nukIdProductos in (" + id_cadenaids + ")";
//				
//			}
			
			
		}
		
		if (!tb_SKU_Ubicacion.getValue().equals("") ) {
			checkbox_ShowAll.setChecked(false);
			
			String id_cadenaids ="";
			int id_contador= 0;
					
//			List<String> idsUsuarios_id = usuarioService.gettbProdUbi(tb_SKU_Ubicacion.getValue(),"chSku");
//			if(idsUsuarios_id.size()>0){
//				for(String str_id : idsUsuarios_id)
//				{
//					if(id_contador == 0){
//						id_cadenaids= id_cadenaids+str_id;
//						id_contador++;
//					}else{
//						id_cadenaids= id_cadenaids+","+str_id;
//					}
//				    //imprimimos el objeto pivote
////				    System.out.println(str);
////				    cadenaids= str;
//				}
////				System.out.println("Cadena con IDs = "+id_cadenaids);
////				restriccion = "nukIdUbicaMatPrim in (" + id_cadenaids + ")";
////				restricciones.add(restriccion);		
//				
//				CadenaCondiciones += " and IDMP.nukIdUbicaMatPrim in (" + id_cadenaids + ")";
//				
//			}
				
			
			
			
		
		}
		
		System.out.println("Valor el String: "+ lb_Scrap_Merma.getSelectedIndex());
		
		String id_select= "";
		if(lb_Scrap_Merma.getSelectedIndex() > 0){
			if(lb_Scrap_Merma.getSelectedIndex() == 1){
				id_select = "1";
			}
			if(lb_Scrap_Merma.getSelectedIndex() == 2){
				id_select = "2";
			}
			if(lb_Scrap_Merma.getSelectedIndex() == 3){
				id_select = "1,2";
			}
//			restriccion = "nuScrapMerma in (" + id_select + ")";
//			restricciones.add(restriccion);	
			
			CadenaCondiciones += " and IDMP.nuScrapMerma in (" + id_select + ")";
			
		}
		
		
//		usuarioService.getOrdenCompraExcel(CadenaCondiciones);
		
		CadenaCondiciones="";
	}
	
	
	public void onClick$button_Search(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Valor el String: "+ lb_Scrap_Merma.getSelectedIndex());
		
//		TbProductos prod = new TbProductos();
//		prod = usuarioService.getProdMPBySKU(tb_sku_Prod.getText());
//		TbUbicacionMateriaPrim ubic = new TbUbicacionMateriaPrim();
//		ubic = usuarioService.getUbicacionMPbYsku(tb_SKU_Ubicacion.getText());
//		TbUsuario user = new TbUsuario();
//		user = usuarioService.getUsuarioByName(tb_Usuario.getText());
		
		restricciones = new ArrayList<String>();
		
		SimpleDateFormat formato  =  new SimpleDateFormat("yyyyMMdd");
		

		if (!tb_Usuario.getValue().equals("") ) {
			checkbox_ShowAll.setChecked(false);
			
			String id_cadenaids ="";
			int id_contador= 0;
					
//			List<String> idsUsuarios_id = usuarioService.gettbUsuario(tb_Usuario.getValue(),"username");
//			if(idsUsuarios_id.size()>0){
//				for(String str_id : idsUsuarios_id)
//				{
//					if(id_contador == 0){
//						id_cadenaids= id_cadenaids+str_id;
//						id_contador++;
//					}else{
//						id_cadenaids= id_cadenaids+","+str_id;
//					}
//				    //imprimimos el objeto pivote
////				    System.out.println(str);
////				    cadenaids= str;
//				}
//				System.out.println("Cadena con IDs = "+id_cadenaids);
//				restriccion = "id_Usuario in (" + id_cadenaids + ")";
//				restricciones.add(restriccion);		
//				
//			}else{
//				restriccion = "id_Usuario in (" + 0 + ")";
//				restricciones.add(restriccion);	
//			}	
			
//					restriccion = "tbUsuario.username like '%" +tb_Usuario.getText() +"%'";
//					restricciones.add(restriccion);		
			
		}
		
		if (!tb_Folio.getValue().equals("") ) {
			checkbox_ShowAll.setChecked(false);
			
			restriccion = "chParte like '%" + tb_Folio.getValue() +"%'";
			restricciones.add(restriccion);
					
		
		}
		
		
		if (!tb_sku_Prod.getValue().equals("") ) {
			checkbox_ShowAll.setChecked(false);
			String id_cadenaids ="";
			int id_contador= 0;
					
//			List<String> idsUsuarios_id = usuarioService.gettbProd(tb_sku_Prod.getValue(),"chSku");
//			if(idsUsuarios_id.size()>0){
//				for(String str_id : idsUsuarios_id)
//				{
//					if(id_contador == 0){
//						id_cadenaids= id_cadenaids+str_id;
//						id_contador++;
//					}else{
//						id_cadenaids= id_cadenaids+","+str_id;
//					}
//				    //imprimimos el objeto pivote
////				    System.out.println(str);
////				    cadenaids= str;
//				}
//				System.out.println("Cadena con IDs = "+id_cadenaids);
//				restriccion = "nukIdProductos in (" + id_cadenaids + ")";
//				restricciones.add(restriccion);		
//				
//			}else{
//				restriccion = "nukIdProductos in (" + 0 + ")";
//				restricciones.add(restriccion);	
//			}	
			
		}
		
		if (!tb_SKU_Ubicacion.getValue().equals("") ) {
			checkbox_ShowAll.setChecked(false);
			
			String id_cadenaids ="";
			int id_contador= 0;
					
//			List<String> idsUsuarios_id = usuarioService.gettbProdUbi(tb_SKU_Ubicacion.getValue(),"chSku");
//			if(idsUsuarios_id.size()>0){
//				for(String str_id : idsUsuarios_id)
//				{
//					if(id_contador == 0){
//						id_cadenaids= id_cadenaids+str_id;
//						id_contador++;
//					}else{
//						id_cadenaids= id_cadenaids+","+str_id;
//					}
//				    //imprimimos el objeto pivote
////				    System.out.println(str);
////				    cadenaids= str;
//				}
//				System.out.println("Cadena con IDs = "+id_cadenaids);
//				restriccion = "nukIdUbicaMatPrim in (" + id_cadenaids + ")";
//				restricciones.add(restriccion);		
//				
//			}else{
//				restriccion = "nukIdUbicaMatPrim in (" + 0 + ")";
//				restricciones.add(restriccion);	
//			}	
			
			
			
		
		}
		
		System.out.println("Valor el String: "+ lb_Scrap_Merma.getSelectedIndex());
		
		String id_select= "";
		if(lb_Scrap_Merma.getSelectedIndex() > 0){
			if(lb_Scrap_Merma.getSelectedIndex() == 1){
				id_select = "1";
			}
			if(lb_Scrap_Merma.getSelectedIndex() == 2){
				id_select = "2";
			}
			if(lb_Scrap_Merma.getSelectedIndex() == 3){
				id_select = "1,2";
			}
			restriccion = "nuScrapMerma in (" + id_select + ")";
			restricciones.add(restriccion);	
			
		}else{
			restriccion = "nuScrapMerma in (" + 0 + ")";
			restricciones.add(restriccion);	
		}
		
		
		
//		restriccion = " rfc = '" + corporativo.getRfc()+ "'" ;
//		restricciones.add(restriccion);
		
//		restriccion = " id_Corporativo = " + id_Corporativo ;
//		restricciones.add(restriccion);
		
		
//		wrappper.init("TbInventarioDetalleMp", listBoxOrden, paging_Orden, restricciones);
		
	}
	
	public void onOK$tb_sku_Prod(Event event) throws JRException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		try {
//			lb_ClienteRol.setSelectedIndex(0);
			onClick$button_Search(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void onOK$tb_Folio(Event event) throws JRException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		try {
//			lb_ClienteRol.setSelectedIndex(0);
			onClick$button_Search(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void onOK$tb_SKU_Ubicacion(Event event) throws JRException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		try {
//			lb_ClienteRol.setSelectedIndex(0);
			onClick$button_Search(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void onOK$tb_Usuario(Event event) throws JRException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		try {
//			lb_ClienteRol.setSelectedIndex(0);
			onClick$button_Search(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void onOK$tb_Nombre(Event event) throws JRException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		try {
//			lb_ClienteRol.setSelectedIndex(0);
			onClick$button_Search(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * when the checkBox 'Show All' for filtering is checked.<br>
	 *
	 * @param event
	 */
	public void onCheck$checkbox_ShowAll(Event event) {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		tb_sku_Prod.setValue("");
		tb_Folio.setValue("");
		tb_SKU_Ubicacion.setValue("");
		
		restricciones.clear();

//		restriccion = " rfc = '" + corporativo.getRfc()+ "'" ;
//		restricciones.add(restriccion);
		
//		wrappper.init("TbInventarioDetalleMp", listBoxOrden, paging_Orden, restricciones);
//		}
	}
	
	public void saveFileXML(String name, Blob blob){
		System.out.println("Se va a salvar un archivo xml");
		Blob dataBlob = null;
		InputStream is = null;
		try {
			dataBlob = new SerialBlob(blob);
			is = dataBlob.getBinaryStream();
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Filedownload.save(is, "xml", name+".xml");
		
	}
	
	public void saveFilePDF(String name,String URL){
		System.out.println("Se va a salvar un archivo pdf");
//		Blob dataBlob = null;
		InputStream is = null;
//		try {
//			dataBlob = new SerialBlob(blob);
//			is = dataBlob.getBinaryStream();
//		} catch (SerialException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		 try {
			 System.out.println("vALOR DE LA dIRECCION: "+ URL+name+".pdf");
			 String TodoPDF = URL+name+".pdf";
			is = new FileInputStream(TodoPDF);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Filedownload.save(is, "pdf", name+".pdf");
		
	}
}
