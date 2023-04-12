package mx.com.hunkabann.skf.frontent.buscarPT.model;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import mx.com.hunkabann.skf.backend.UsuarioService;

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
public class BuscarPTListModelItemRenderer implements ListitemRenderer, Serializable {

	private static final long serialVersionUID = 1L;
	private transient static final Logger logger = Logger.getLogger(BuscarPTListModelItemRenderer.class);
	
	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
    HttpSession session = (HttpSession) s.getNativeSession();
	
	String Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
	
	UsuarioService userService = new UsuarioService();
	String NameRol = "";
	int id_user;
	int id_pr_rol;
	int id_Corporativo;

	public void render(Listitem item, Object data) throws Exception {

		
//		TbInventarioDetallePt inv_Detalle_MP = (TbInventarioDetallePt) data;
//		
//		TbUsuario ob_User = userService.getIDUsuario(inv_Detalle_MP.getTbUsuario().getIdUsuario());
//		TbProductosTerminados ob_Productos = userService.getIDProductoPT(inv_Detalle_MP.getTbProductosTerminados().getNukIdProductosTerminados());
//		TbUbicacionTerminados ob_Ubicaciones = userService.getIDUbicacionPT(inv_Detalle_MP.getTbUbicacionTerminados().getNukIdUbicaTerm());
		
		if (logger.isDebugEnabled()) {
			logger.debug("--> render" );
		}
		
			Listcell lc;
//			System.out.println( "mODEL cliente.getUsername(): " + cliente.getUsername());
//			System.out.println( "mODEL persona.getChNombre(): " + persona.getChNombre());
			lc = new Listcell("");
			lc.setParent(item);
			
						lc = new Listcell("");
						lc.setParent(item);
						
						lc = new Listcell("");
						lc.setParent(item);
						lc = new Listcell("");
						lc.setParent(item);
						lc = new Listcell("");
						lc.setParent(item);
						
						String Scrap_Merma = "";
						
						lc = new Listcell(Scrap_Merma);
						lc.setParent(item);

						item.setAttribute("data", data);
						ComponentsCtrl.applyForward(item, "onDoubleClick=onBuscarPTItemDoubleClicked");
						
					}
		
		
		
	

//	}

}