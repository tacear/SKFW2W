package mx.com.hunkabann.skf.frontent.OrdenManufactura.model;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.mapeo.TbCatStatus;
import mx.com.hunkabann.skf.mapeo.TbCatUm;
import mx.com.hunkabann.skf.mapeo.TbMateriaPrima;
import mx.com.hunkabann.skf.mapeo.TbOrdenManufactura;
import mx.com.hunkabann.skf.mapeo.TbProdTermMatPrim;
import mx.com.hunkabann.skf.mapeo.TbProductoTerminado;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContext;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.http.SimpleSession;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


/**
 * Item renderer for listitems in the listbox.
 * 
 * @author bbruhns
 * @author sgerth
 * 
 */
public class ProductoListModelItemRenderer implements ListitemRenderer, Serializable {

	private static final long serialVersionUID = 1L;
	private transient static final Logger logger = Logger.getLogger(ProductoListModelItemRenderer.class);
	
	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
    HttpSession session = (HttpSession) s.getNativeSession();
	
//	String Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
	
	UsuarioService userService = new UsuarioService();
	String NameRol = "";
	int id_user;
	int id_pr_rol;
	int id_Corporativo;

	public void render(Listitem item, Object data) throws Exception {

		
		TbProdTermMatPrim inv_Detalle_MP = (TbProdTermMatPrim) data;
		
		TbProductoTerminado ProdTerm = new TbProductoTerminado();
		TbMateriaPrima MatPrim = new TbMateriaPrima();
		TbCatUm UM = new TbCatUm();
		
		TbCatStatus Status = new TbCatStatus();
		
		ProdTerm = userService.getPRoductoTerm(inv_Detalle_MP.getTbProductoTerminado().getNukIdProdTerm());
//		ProdTerm.setChSku(userService.getPRoductoTerm(inv_Detalle_MP.getTbProductoTerminado().getNukIdProdTerm(),inv_Detalle_MP.getTbEmpresa().getNukIdEmpresa()));
		MatPrim = userService.getMAteriaPrima(inv_Detalle_MP.getTbMateriaPrima().getNukIdMateriaPrima());
		Status = userService.getCatStatus(inv_Detalle_MP.getTbProductoTerminado().getNukIdProdTerm());
		UM = userService.getCatUM(MatPrim.getTbCatUm().getNukIdCatUm());
//		ProdTermMatePrim = userService.getProdTermMatePrim(inv_Detalle_MP.getTbProductoTerminado().getNukIdProdTerm());
		
//		inv_Detalle_MP.getTbCatStatus().getChNombre()
//		TbUsuario ob_User = userService.getIDUsuario(inv_Detalle_MP.getTbUsuario().getIdUsuario());
//		TbProductos ob_Productos = userService.getIDProductoMP(inv_Detalle_MP.getTbProductos().getNukIdProductos());
//		TbUbicacionMateriaPrim ob_Ubicaciones = userService.getIDUbicacionMP(inv_Detalle_MP.getTbUbicacionMateriaPrim().getNukIdUbicaMatPrim());
		
		if (logger.isDebugEnabled()) {
			logger.debug("--> render" );
		}
		
			Listcell lc;
//			System.out.println( "mODEL cliente.getUsername(): " + cliente.getUsername());
//			System.out.println( "mODEL persona.getChNombre(): " + persona.getChNombre());
			System.out.println("Valor de la cantidad ------- >>>>>> " + inv_Detalle_MP.getNuCantidad() +" Operacion ----->>>> "+ (inv_Detalle_MP.getNuCantidad()/1000));
			lc = new Listcell((inv_Detalle_MP.getNuCantidad())+"");
			lc.setParent(item);
			
			System.out.println("Valor de SKU Producto Terminado ---------------------------->>>  " +ProdTerm.getChSku() + ProdTerm.getChDescripcion());
//			
//						lc = new Listcell(ProdTerm.getChSku());
//						lc.setParent(item);
//						lc = new Listcell(inv_Detalle_MP.getTbMateriaPrima().getNukIdMateriaPrima()+"");
//						lc.setParent(item);
						lc = new Listcell(MatPrim.getChSku());
						lc.setParent(item);
						lc = new Listcell(MatPrim.getChDescripcion());
						lc.setParent(item);
						lc = new Listcell(UM.getChNombre());
						lc.setParent(item);

						item.setAttribute("data", data);
						ComponentsCtrl.applyForward(item, "onDoubleClick=onOrdenCompraItemDoubleClicked");
						
					}
		
		
		
	

//	}

}