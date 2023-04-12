package mx.com.hunkabann.skf.backend.util;

import java.util.Iterator;

import mx.com.hunkabann.skf.util.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;


public class GeneralService {
	
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
//	private TbUsuario i_obUser = new TbUsuario();
//	private TbCtoBitacora i_obCtoBitacora = new TbCtoBitacora();
//	private TbBitacora i_obBitacora = new TbBitacora();
//	private TbInterface i_obInterface= new TbInterface();
	
	private HibernatePager pager = new HibernatePager();
	
	

	
	/**
	 * obtiene un objeto por id
	 */
	public Object getBusinessObjectPorId(Integer p_inId, Class p_clazz, String p_stCampoId) {
		
		Object l_Object = null;
		Session l_session = null;
		String l_stClassName = null;
		Query l_query = null;
		Iterator l_iterator = null;
		
		try {
			
			l_stClassName = p_clazz.getName();
			l_session = HibernateUtil.currentSession();
			l_query = l_session.createQuery("FROM " + l_stClassName + " c WHERE c." + p_stCampoId + " = :id");
			l_query.setParameter("id", p_inId);
			
			l_iterator = l_query.list().iterator();
			
			if (l_iterator.hasNext()) 
				l_Object = (Object) l_iterator.next();

			
		} catch(Throwable e) {
			
			System.err.println("ERROR--->>> "+e);
			
		} finally {
			
			if(l_session != null)
				HibernateUtil.closeSession();
			
			// libera recursos
			l_stClassName = null;
			l_query = null;
			l_iterator = null;
			
		}
		
		return l_Object;

	}	// getBusinessObjectPorId
	
	
	
	
	
	/**
	 * obtiene un objeto por id
	 */
	public Object getBusinessObjectPorId(Integer p_Inid, Class p_clazz, String p_stCampoId, Session p_session) {
		
		Object l_Object = null;
		String l_stClassName = null;
		Query l_query = null;
		Iterator l_iterator = null;

		try {
			
			l_stClassName = p_clazz.getName();

			l_query = p_session.createQuery("FROM " + l_stClassName + " c WHERE c." + p_stCampoId + " = :id");
			l_query.setParameter("id", p_Inid);
			l_iterator = l_query.list().iterator();
			
			if (l_iterator.hasNext()) 
				l_Object = (Object) l_iterator.next();

			
		} catch(Throwable e) {
			
			System.err.println("ERROR --->>> " + e);
			
		} finally {
			
			// libera recursos
			l_stClassName = null;
			l_query = null;
			l_iterator = null;


		}
		
		return l_Object;

	}	// getBusinessObjectPorId
	
	
	
	/**
	 * Verifica la existencia de registros en e query de entrada
	 */
	public boolean existe (String p_stQuery)
	{
		
		boolean l_boExiste = false;
		Query l_query = null;
		Session l_session = null;
		Iterator l_iterator = null;

		try
		{
			l_session = HibernateUtil.currentSession();

			l_query = l_session.createQuery(p_stQuery);
			l_iterator = l_query.setMaxResults(1).list().iterator();
			
			if (l_iterator.hasNext())
				l_boExiste = true;

		}
		catch (Exception e)
		{
			
			logger.error(e.getMessage());
			
		}
		finally
		{
			if(l_session != null)
				HibernateUtil.closeSession();

			// libera recursos
			l_query = null;
			l_iterator = null;
		}

		return l_boExiste;

	}	// existe
	
	
	
	
	/**
	 * Verifica la existencia de un valor en una columna especificada (string)
	 */
	public boolean existe (Class p_clazz, String p_stCampo, String p_stValor)
	{
		
		boolean l_boExiste = false;
		Query l_query = null;
		String l_stClassName = "";
		Session l_session = null;
		Iterator l_iterator = null;

		try
		{
			l_session = HibernateUtil.currentSession();
			l_stClassName = p_clazz.getName();
			l_query = l_session.createQuery("FROM " + l_stClassName + " c WHERE c." + p_stCampo + " = '" + p_stValor + "'");
			l_iterator = l_query.setMaxResults(1).list().iterator();
			
			if (l_iterator.hasNext())
				l_boExiste = true;

		}
		catch (Exception e)
		{
			
			logger.error(e.getMessage());
			
		}
		finally
		{
			if(l_session != null)
				HibernateUtil.closeSession();

			// libera recursos
			l_stClassName = null;
			l_query = null;
			l_iterator = null;
		}

		return l_boExiste;

	}	// existe
	
	
	
	
	/**
	 * Verifica la existencia de un valor en una columna especificada (string)
	 */
	public boolean existe (Class p_clazz, String p_stCampo, String p_stValor, String p_stCondicion)
	{
		
		boolean l_boExiste = false;
		Query l_query = null;
		String l_stClassName = "";
		Session l_session = null;
		Iterator l_iterator = null;

		try
		{
			l_session = HibernateUtil.currentSession();
			l_stClassName = p_clazz.getName();
			l_query = l_session.createQuery("FROM " + l_stClassName + " c " +
					" WHERE c." + p_stCampo + " = '" + p_stValor + "' and c." +
					p_stCondicion);
			l_iterator = l_query.setMaxResults(1).list().iterator();
			
			if (l_iterator.hasNext())
				l_boExiste = true;

		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			if(l_session != null)
				HibernateUtil.closeSession();

			l_stClassName = null;
			l_query = null;
			l_iterator = null;
		}

		return l_boExiste;

	}	// existe
	
	
	
	
//	/**
//	 * Regresa el nombre del ComboItem asociado a un id
//	 * @return
//	 */
//	public String getComboItemName(List<ComboItem> p_listaDatos, Integer p_nikdCombo)
//	{
//		String l_stReturn = "";
//		
//		if(p_nikdCombo == null)
//			return l_stReturn;
//		
//		for(ComboItem comboItem : p_listaDatos )
//		{
//			if(p_nikdCombo == comboItem.getId())
//				return comboItem.getNombre();
//		}
//		
//		return l_stReturn;
//		
//	} // getComboItemName





	public HibernatePager getPager() {
		return pager;
	}





	public void setPager(HibernatePager pager) {
		this.pager = pager;
	}
	
	
	
	
	
//	/**
//	 * Guarda el contenido de un objeto en bitacora
//	 * @param p_obSession	Sesion de la transacci�n
//	 * @param p_ob			objeto a guardar en la bitacora
//	 * @param p_inOperacion	operaci�n a registrar en la bit�cora
//	 */
//	public synchronized void guardaBitacora(Session p_obSession, Object p_ob, int p_inOperacion) throws Exception
//	{
//
//		try
//		{
//			if(SessionBean.getIdUser() == 0)
//				i_obUser = null;
//			else
//				i_obUser.setNukiduser(SessionBean.getIdUser());
//
//			if(SessionBean.getIdInterface() == null)
//				i_obInterface = null;
//			else
//				i_obInterface.setNukidinterface(SessionBean.getIdInterface());
//			
//			i_obCtoBitacora.setNukidCtoBitacora(p_inOperacion);
//
//			i_obBitacora.setTbSecUser(i_obUser);
//			i_obBitacora.setDtfecha(new Timestamp(new Date().getTime()));
//			i_obBitacora.setChvalor(Snoopy.urga(p_ob));
//			i_obBitacora.setTbCtoBitacora(new TbCtoBitacora(p_inOperacion));
//			i_obBitacora.setTbInterface(i_obInterface);
//			i_obBitacora.setNusesion(SessionBean.getSesion());
//
//			p_obSession.save(i_obBitacora);
//		}
//		catch(Exception e)
//		{
//			throw new Exception("Fallo en bit�cora - " + e);
//		}
//
//
//	}	//	guardaBitacora
	



//	/**
//	 * Guarda el contenido de un objeto en bitacora
//	 * @param p_obSession	Sesion de la transacci�n
//	 * @param p_ob			objeto a guardar en la bitacora
//	 * @param p_inOperacion	operaci�n a registrar en la bit�cora
//	 */
//	public synchronized void guardaBitacoraTx(Session p_obSession, Object p_ob, int p_inOperacion) throws Exception
//	{
//
//		TbBitacora l_obBitacora = new TbBitacora();
//
//
//		try
//		{
//			if(SessionBean.getIdUser() == 0)
//				i_obUser = null;
//			else
//				i_obUser.setNukiduser(SessionBean.getIdUser());
//			
//			if(SessionBean.getIdInterface() == null)
//				i_obInterface = null;
//			else
//				i_obInterface.setNukidinterface(SessionBean.getIdInterface());
//			
//			i_obCtoBitacora.setNukidCtoBitacora(p_inOperacion);
//
//			l_obBitacora.setTbSecUser(i_obUser);
//			l_obBitacora.setDtfecha(new Timestamp(new Date().getTime()));
//			l_obBitacora.setChvalor(Snoopy.urga(p_ob));
//			l_obBitacora.setTbCtoBitacora(new TbCtoBitacora(p_inOperacion));
//			l_obBitacora.setTbInterface(i_obInterface);
//			l_obBitacora.setNusesion(SessionBean.getSesion());
//
//			p_obSession.save(l_obBitacora);
//		}
//		catch(Exception e)
//		{
//			throw new Exception("Fallo en bit�cora - " + e);
//		}
//		finally
//		{
//			l_obBitacora = null;
//		}
//
//
//	}	//	guardaBitacora
	
	

} // end of file



