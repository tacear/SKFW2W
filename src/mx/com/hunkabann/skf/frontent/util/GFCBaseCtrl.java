package mx.com.hunkabann.skf.frontent.util;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import mx.com.hunkabann.sipv.util.Constantes;
//import mx.com.hunkabann.sipv.util.HibernateUtil;
//import mx.com.hunkabann.sipv.workspace.UserWorkspace;

import mx.com.hunkabann.skf.util.Constantes;
import mx.com.hunkabann.skf.util.HibernateUtil;
import mx.com.hunkabann.skf.workspace.UserWorkspace;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.security.access.annotation.Secured;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Window;


abstract public class GFCBaseCtrl extends WindowBaseCtrl implements Serializable,Constantes {

	// private transient static final Logger logger =
	// Logger.getLogger(GFCBaseCtrl.class);
	private static final long serialVersionUID = -1171206258809472640L;

	protected transient AnnotateDataBinder binder;

	protected transient Map<String, Object> args;
	
	protected List<String> restricciones = new ArrayList<String>();
	
	protected String restriccion = "";
	
	protected int heightWindow = 900;
	
	protected int countRowsGeneral = 10;
//	protected int countRowsGeneral = 3;

	/**
	 * Get the params map that are overhanded at creation time. <br>
	 * Reading the params that are binded to the createEvent.<br>
	 * 
	 * @param event
	 * @return params map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCreationArgsMap(Event event) {
		CreateEvent ce = (CreateEvent) ((ForwardEvent) event).getOrigin();
		return ce.getArg();
	}

	public void doOnCreateCommon(Window w) throws Exception {
		binder = new AnnotateDataBinder(w);
		binder.loadAll();
	}

	@SuppressWarnings("unchecked")
	public void doOnCreateCommon(Window w, Event fe) throws Exception {
		doOnCreateCommon(w);
		CreateEvent ce = (CreateEvent) ((ForwardEvent) fe).getOrigin();
		args = ce.getArg();
	}

	private transient UserWorkspace userWorkspace;

	/**
	 * Workaround! Do not use it otherwise!
	 */
	//@Override
//	public void onEvent(Event evt) throws Exception {
//		/*
//		final Object controller = getController();
//		final Method mtd = ComponentsCtrl.getEventMethod(controller.getClass(), evt.getName());
//		
//		if (mtd != null) {
//			isAllowed(mtd);
//		}
//		*/
//		super.onEvent(evt);
//	}

	/**
	 * With this method we get the @Secured Annotation for a method.<br>
	 * Captured the method call and check if it's allowed. <br>
	 * 
	 * @param mtd
	 */
	@SuppressWarnings("unused")
	private void isAllowed(Method mtd) {
		Annotation[] annotations = mtd.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof Secured) {
				Secured secured = (Secured) annotation;
				for (String rightName : secured.value()) {
					if (!userWorkspace.isAllowed(rightName)) {
						throw new SecurityException("No tiene permisos para esta operación: \n\n" + " permiso: " + rightName + "\n\n"
								+ "en el metodo: " + mtd);
					}
				}
				return;
			}
		}
	}

	final protected UserWorkspace getUserWorkspace() {
		return userWorkspace;
	}

	public void setUserWorkspace(UserWorkspace userWorkspace) {
		this.userWorkspace = userWorkspace;
	}
	
	/**
	 * Identifica si existe un registro en la tabla parametro son las condiciones especificadas
	 * @param Tabla
	 * @param condiciones
	 * @return
	 */
	public boolean existeRegistroAvanzado (String Tabla, String condiciones) {
		boolean existe = false;
		Session s = null;
		Query q = null;
		String where = "";
		Integer resultado = 0;
		
		try {
			s = HibernateUtil.currentSession();
			where = condiciones;
			resultado = (Integer) s.createQuery(" select count (*) from " + Tabla + "  " + where).uniqueResult();
			if (resultado > 0)
				existe =true;
			
		} catch (Exception e) {
			System.err.println("ERROR--->>> "+e);
		} finally {
			if(s!=null)
				HibernateUtil.closeSession();
		}
		return existe;
	}

}
