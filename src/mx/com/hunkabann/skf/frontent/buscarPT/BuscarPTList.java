package mx.com.hunkabann.skf.frontent.buscarPT;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.frontent.buscarPT.model.BuscarPTListModelItemRenderer;
import mx.com.hunkabann.skf.frontent.util.GFCBaseCtrl;
import mx.com.hunkabann.skf.frontent.util.MultiLineMessageBox;
import mx.com.hunkabann.skf.frontent.util.pagging.PagedListWrapper;
import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContext;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.http.SimpleSession;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class BuscarPTList extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = 2038742641853727975L;
	private transient static final Logger logger = Logger.getLogger(BuscarPTList.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected transient Window BuscarPTWindow; // autowired

	// filter components
	protected transient Checkbox checkbox_ShowAll; // autowired
	
	protected transient Textbox tb_sku_Prod; // aurowired
	protected transient Textbox tb_Folio; // autowired
	protected transient Textbox tb_SKU_Ubicacion;
	protected transient Textbox tb_Usuario;
	protected transient Listbox lb_Scrap_Merma;
	
	// listbox userList
	protected transient Borderlayout borderLayout_secUserList;
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
	protected transient Image button_Search;
	protected transient Image btnLogout;
	protected transient Button button_Reporte;
	
	protected transient Listbox listBoxOrden; // aurowired
	protected transient Paging paging_Orden; // aurowired
	
//	protected transient Image btnNew;
	protected transient Button btnPassword;
	protected transient Label Label_RFC;
//	protected transient Label label_PiePagina;
	
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
	
//	 Usuario = "";
//	PagedListWrapper<TbInventarioDetallePt> wrappper = new PagedListWrapper<TbInventarioDetallePt>();
	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
    HttpSession session = (HttpSession) s.getNativeSession();
	
	String Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
//	private SessionBean sBean = (SessionBean) Sessions.getCurrent().getWebApp().getAttribute(Usuario);
	
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
	
	String URL_PDF = "";
	
	String CadenaCondiciones = "";

	/**
	 * default constructor.<br>
	 */
	public BuscarPTList() {
		super();
		restricciones.clear();

		if (logger.isDebugEnabled()) {
			logger.debug("--> super()");
		}
	}
	

	public void onCreate$BuscarPTWindow(Event event) throws Exception {
		
		HttpSession session = (HttpSession) s.getNativeSession();		
		Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
		
		Properties email_props = new Properties();
		InputStream is = null;
		
		try {
			
		    is = getClass().getResourceAsStream("/email.properties");
		    email_props.load(is);
		    
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
		
		URL_PDF = email_props.getProperty("UrlSistema");

        int maxListBoxHeight = (heightWindow - 118);

        borderLayout_secUserList.setHeight(String.valueOf(maxListBoxHeight) + "px");

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
		paging_Orden.setPageSize(22);
		paging_Orden.setDetailed(true);

		checkbox_ShowAll.setChecked(true);
		
		// Set the ListModel.
//		wrappper.init("TbInventarioDetallePt", listBoxOrden, paging_Orden, restricciones);
		// set the itemRenderer
		listBoxOrden.setItemRenderer(new BuscarPTListModelItemRenderer());
		


	}

	/**
	 * Metodo encargado de mostrar la pantalla de busqueda de cliente para
	 * agregar a la factura
	 * 
	 * @param event
	 * @throws Exception
	 */
//	public void onClick$btnNew(Event event) throws Exception {
//
//		if (logger.isDebugEnabled()) {
//			if (event != null)
//				logger.debug("--> " + event.toString());
//		}
//		
//		detaCompro = null;
//		
//		System.out.println("Si entro para armar de parametros");
//		//Se inicializa por default la addenda
////		btnAgregaAddenda.setVisible(false);
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		
//		tb_Usuarios = usuarioService.getSimpleUserByUsername(Usuario);
//		map.put("tb_user", tb_Usuarios);
//		map.put("detaCompro", detaCompro);
//		map.put("Usuario", Usuario);
//		map.put("NameRol", NameRol);
//		map.put("id_user", id_user);
//		map.put("id_pr_rol", id_pr_rol);
//		map.put("id_Corporativo", id_Corporativo);
//		map.put("listBoxOrden", listBoxOrden);
//
//
//		try {
//					Executions.createComponents("/WEB-INF/pages/ordenCompra/ordenCompraDialog.zul", null, map);
//		} catch (Exception e) {
//			logger.error("onOpenWindow:: error opening window / "
//					+ e.getMessage());
//
//			// Show a error box
//			if (logger.isDebugEnabled()) {
//				String msg = e.getMessage();
//				String title = Labels.getLabel("message_Error");
//				MultiLineMessageBox.doSetTemplate();
//				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
//						"ERROR", true);
//			}
//
//		}
//	}
	
	/**
	 * Action onDoubleClick
	 * @param event
	 * @throws Exception
	 */
	public void onBuscarPTItemDoubleClicked(Event event) throws Exception {

		// get the selected object
		Listitem item = listBoxOrden.getSelectedItem();
//
		if (item != null) {
			// CAST AND STORE THE SELECTED OBJECT
//			TbInventarioDetallePt aOrden = (TbInventarioDetallePt) item.getAttribute("data");

			if (logger.isDebugEnabled()) {
//				logger.debug("--> " + aOrden.getNukIdInventDetallePt());
			}

//			showDetailView(aOrden);
		}
	}
	/**
	 * Opens the detail view. <br>
	 * Overhanded some params in a map if needed. <br>
	 *
	 */
//	private void showDetailView(TbInventarioDetallePt aOrden) throws Exception {
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
//		map.put("listBoxOrden", listBoxOrden);
//		map.put("modal", modal);
//		
//		
//		try {
//			Executions.createComponents("/WEB-INF/pages/invProdTerm/invProdTermDialog.zul", null, map);
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
	
	
	public void onClick$btnLogout(Event event) throws Exception {
		
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

			BuscarPTWindow.onClose();
	}
	
	public void onClick$button_XML(Event event) throws Exception {
//		Listitem item = listBoxOrden.getSelectedItem();
//		System.out.println("item "+item);
//		TbInventarioDetallePt anComprobante = null;
//		if (item == null) {
//			Messagebox.show("Debes seleccionar al menos un comprobante para descargar los archivos.", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//
//		}
//		 if (listBoxOrden.getSelectedCount() > 0) {
//			 for( Object o : listBoxOrden.getSelectedItems() ){
//					Listitem li = (Listitem)o;
//					TbInventarioDetallePt sit =(TbInventarioDetallePt)li.getAttribute("data");
////				anComprobante = (TbInventarioDetallePt) item.getAttribute("data");
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
////				TbInventarioDetallePt sit =(TbInventarioDetallePt)li.getAttribute("data");
////				
////				System.out.println(sit.getComprobanteId());
////		 }
		
		
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
				
				CadenaCondiciones += " and IDMP.id_Usuario in (" + id_cadenaids + ") ";
				
			}
			
//					restriccion = "tbUsuario.username like '%" +tb_Usuario.getText() +"%'";
//					restricciones.add(restriccion);		
			
//		}
		
		if (!tb_Folio.getValue().equals("") ) {
			checkbox_ShowAll.setChecked(false);
			
//			restriccion = "chParte like '%" + tb_Folio.getValue() +"%'";
//			restricciones.add(restriccion);
			
			CadenaCondiciones += " and IDMP.chParte like '%" + tb_Folio.getValue() +"%'";
					
		
		}
		
		
		if (!tb_sku_Prod.getValue().equals("") ) {
			checkbox_ShowAll.setChecked(false);
			String id_cadenaids ="";
			int id_contador= 0;
					
//			List<String> idsUsuarios_id = usuarioService.gettbProdTerm(tb_sku_Prod.getValue(),"chSku");
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
////				restriccion = "nukIdProductosTerminados in (" + id_cadenaids + ")";
////				restricciones.add(restriccion);		
//				
//				CadenaCondiciones += " and IDMP.nukIdProductosTerminados in (" + id_cadenaids + ")";
//				
//			}
//			else{
//				restriccion = "nukIdProductosTerminados in (" + 0 + ")";
//				restricciones.add(restriccion);	
//			}	
			
		}
		
		if (!tb_SKU_Ubicacion.getValue().equals("") ) {
			checkbox_ShowAll.setChecked(false);
			
			String id_cadenaids ="";
			int id_contador= 0;
					
//			List<String> idsUsuarios_id = usuarioService.gettbProdUbiTerm(tb_SKU_Ubicacion.getValue(),"chSku");
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
////				restriccion = "nukIdUbicaTerm in (" + id_cadenaids + ")";
////				restricciones.add(restriccion);		
//				
//				CadenaCondiciones += " and IDMP.nukIdUbicaTerm in (" + id_cadenaids + ")";
//				
//			}
//			else{
//				restriccion = "nukIdUbicaTerm in (" + 0 + ")";
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
//			restriccion = "nuScrapMerma in (" + id_select + ")";
//			restricciones.add(restriccion);	
			
			CadenaCondiciones += " and IDMP.nuScrapMerma in (" + id_select + ")";
			
		}
//		else{
//			restriccion = "nuScrapMerma in (" + 0 + ")";
//			restricciones.add(restriccion);	
//		}
		
//		usuarioService.getProductoTermExcel(CadenaCondiciones);
		CadenaCondiciones = "";
	}
	
	
	public void onClick$button_Search(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Valor el String: "+ lb_Scrap_Merma.getSelectedIndex());
		
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
					
//			List<String> idsUsuarios_id = usuarioService.gettbProdTerm(tb_sku_Prod.getValue(),"chSku");
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
//				restriccion = "nukIdProductosTerminados in (" + id_cadenaids + ")";
//				restricciones.add(restriccion);		
//				
//			}else{
//				restriccion = "nukIdProductosTerminados in (" + 0 + ")";
//				restricciones.add(restriccion);	
//			}	
			
		}
		
		if (!tb_SKU_Ubicacion.getValue().equals("") ) {
			checkbox_ShowAll.setChecked(false);
			
			String id_cadenaids ="";
			int id_contador= 0;
					
//			List<String> idsUsuarios_id = usuarioService.gettbProdUbiTerm(tb_SKU_Ubicacion.getValue(),"chSku");
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
//				restriccion = "nukIdUbicaTerm in (" + id_cadenaids + ")";
//				restricciones.add(restriccion);		
//				
//			}else{
//				restriccion = "nukIdUbicaTerm in (" + 0 + ")";
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
		
		
//		wrappper.init("TbInventarioDetallePt", listBoxOrden, paging_Orden, restricciones);
		
	}
	
	public void onOK$tb_Serie(Event event) throws JRException {
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
		
//		wrappper.init("TbInventarioDetallePt", listBoxOrden, paging_Orden, restricciones);
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
