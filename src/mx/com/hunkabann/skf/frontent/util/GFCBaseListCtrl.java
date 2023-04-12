package mx.com.hunkabann.skf.frontent.util;

//import mx.com.hunkabann.sipv.frontend.util.pagging.PagedListWrapper;
//import mx.com.hunkabann.sipv.mapeo.TbPersona;
//import mx.com.hunkabann.sipv.util.HibernateUtil;

import mx.com.hunkabann.skf.frontent.util.pagging.PagedListWrapper;
import mx.com.hunkabann.skf.util.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;


/**
 * Extended the GFCBase controller for a pagedListWrapper for a single type.
 * 
 * @author bbruhns
 */
public class GFCBaseListCtrl<T> extends GFCBaseCtrl {

	private static final long serialVersionUID = -3741197830243792411L;

	private PagedListWrapper<T> pagedListWrapper;

	public PagedListWrapper<T> getPagedListWrapper() {
		return pagedListWrapper;
	}

	public void setPagedListWrapper(PagedListWrapper<T> pagedListWrapper) {
		this.pagedListWrapper = pagedListWrapper;
	}
	
	
	//Verifica si existe información en el qry con un count(*)
	public boolean ejecutaReporte(String qry) {
		boolean retorno = false;
		Session s = null;
		Integer resultado=0;
		
		try {
			s = HibernateUtil.currentSession();
			
			
			resultado = (Integer)s.createQuery(qry).uniqueResult();
		
				
			if(resultado > 0){
			
				retorno =true;
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(s!=null)
				HibernateUtil.closeSession();
			
		}
		return retorno;
	}
	
	
	
}
