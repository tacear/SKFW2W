package mx.com.hunkabann.skf.frontent.util.pagging;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//import mx.com.hunkabann.sipv.util.HibernateUtil;
//import mx.com.hunkabann.sipv.util.TableStructure;

import mx.com.hunkabann.skf.util.HibernateUtil;
import mx.com.hunkabann.skf.util.TableStructure;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;


public class PagedListWrapper<E> extends ListModelList implements Serializable 
{

	private static final long serialVersionUID = -7399762307122148637L;
	static final Logger logger = Logger.getLogger(PagedListWrapper.class);
	
	String hibernateObject = "";
	int firstResult=0;
	int maxResults = 0;
	@SuppressWarnings("unchecked")
	List results = new ArrayList();
	String orderBy = "";
	List<String> restricciones = new ArrayList<String>();
	private TableStructure structure;

	// Service that calls the DAO methods

	// param. The listboxes paging component
	private Paging paging;

	// param. The SearchObject

	// not used yet. so it's init to 'true'.
	private final boolean supportPaging = true;

	/**
	 * default constructor.<br>
	 */
	public PagedListWrapper() {
		super();
	}

	
	public void init(String hibernateObject1, Listbox listBox, Paging paging1, List<String> restricciones1, String orderBy ) {
		setPaging(paging1);
		listBox.setRows(paging1.getPageSize());
		setListeners(listBox);
		
		setRestricciones(restricciones1);
		setHibernateObject(hibernateObject1);
		this.orderBy = orderBy;
		initModel();
	}
	
	public void init(String hibernateObject1, Listbox listBox, Paging paging1, List<String> restricciones1 ) {
		setPaging(paging1);
		listBox.setRows(paging1.getPageSize());
		setListeners(listBox);
		
		setRestricciones(restricciones1);
		setHibernateObject(hibernateObject1);
		initModel();
	}
	
	/**
	 * Establece los parámetros para llenar los list de las pantallas ListCtrl
	 * @param hibernateObject1
	 * @param listBox
	 * @param paging1
	 * @param restricciones1
	 * @param p_obStructure
	 */
	public void init(String hibernateObject1, Listbox listBox, Paging paging1, List<String> restricciones1, TableStructure p_obStructure, String orderBy  ) 
	{
		setPaging(paging1);
		listBox.setRows(paging1.getPageSize());
		setListeners(listBox);
		
		setRestricciones(restricciones1);
		setHibernateObject(hibernateObject1);
		this.setStructure(p_obStructure);
		this.orderBy = orderBy;
		initModel();
	}
	

	/**
	 * Establece los parámetros para llenar los list de las pantallas ListCtrl
	 * @param hibernateObject1
	 * @param listBox
	 * @param paging1
	 * @param restricciones1
	 * @param p_obStructure
	 */
	public void init(String hibernateObject1, Listbox listBox, Paging paging1, List<String> restricciones1, TableStructure p_obStructure ) 
	{
		setPaging(paging1);
		listBox.setRows(paging1.getPageSize());
		setListeners(listBox);
		
		setRestricciones(restricciones1);
		setHibernateObject(hibernateObject1);
		this.setStructure(p_obStructure);
		initModel();
	}
	
	
	
	private void initModel() 
	{
		setFirstResult(0);
		setMaxResults(getPageSize());
		// clear old data
		clear();
		
		
		// hay que pasarle los parametros de paginacion al metodo
		if(structure == null)
		{
			getResultados(getFirstResult(), getMaxResults());
		}
		else
		{
			getResultados(true);
		}
		
		// sele tiene que poner el total a partir de getResultados
		//getPaging().setTotalSize((getResults().size()));
		
		// addAll debe de ir dentro del if
		//addAll(getResults().subList(getFirstResult(), ((getFirstResult())+getMaxResults())>getResults().size()?getResults().size():(getFirstResult())+getMaxResults()));
		addAll(getResults());
		
	}	// initModel
	
	
	
	/**
	 * Recorre los recusultados para obtener los datos de las tablas hijas
	 * @param p_obResults
	 */
	private void recorreResultados(List p_obResults)
	{
		Method[] l_arM = null;
		Method[] l_arM2 = null;
		Method	m1 = null;
		Object l_ob = new Object(); 
		
		String l_stTabla ="";
		Class l_class = null;
		String l_stMetodo = "";
		String l_stMetodo2 = "";
		Object l_obResult = null;
		TableStructure l_obCurrectStruct = structure.getStructure();
		TableStructure l_obStructure = l_obCurrectStruct;
		boolean l_boExiste = false;
		Object [  ]  value =  {  null  } ;
		
		
		//logger.info("recorre todos los resutados");
		
		// encuentra los correspondientes en la bd y los obtiene
		for(int jj = 0; jj < p_obResults.size(); jj++)
		{
			
			l_ob = p_obResults.get(jj);
			l_class = l_ob.getClass();
			
			// Para ordenar sin usar el lazy, LDTF; 26/May/2010
			for(int i = 0; i < l_obStructure.getMiembros().size(); i++)
			{
				
				// recorre los miembros
				l_obCurrectStruct = l_obStructure.get(i);
				l_stTabla = l_obCurrectStruct.getTabla().substring(1);
			
				l_boExiste = false; 
				
				//logger.info("recorre los metodos");
				
				//obtiene todos los metodos de cada uno de los elementos del resultado
				l_arM = l_class.getDeclaredMethods();
				for(int j = 0; j < l_arM.length; j++)
				{
					// recorre los metodos
					
					if(l_arM[j].getName().endsWith(l_stTabla) && l_arM[j].getName().startsWith("g"))
					{
						//logger.info(l_arM[j].getName() + " ----" + l_stTabla);
						l_stMetodo = l_arM[j].getName();
						//logger.info(l_stMetodo);
						try
						{
							m1 = l_class.getMethod(l_stMetodo, null);
							//logger.info(m1.getName());
							l_obResult = m1.invoke(l_ob, null);
							l_obResult.toString();
							//logger.info(m1.invoke(l_ob, null));
							l_arM2 =(m1.invoke(l_ob, null)).getClass().getDeclaredMethods();
							for(int m = 0; m < l_arM2.length; m++)
							{
								l_stMetodo2 = l_arM2[m].getName();
								//logger.info("-> " + l_stMetodo2);
								break;
							}
							
							ejecutaHijos(l_obCurrectStruct, l_obResult);
							
							l_boExiste = true;
							break;
						}
						catch(Exception e)
						{
							logger.info(l_stTabla + "  <<<<   ----   ");
							//e.printStackTrace();
						}
					}	//	encuentra el metodo relacionado a una tabla dentro de la estructura
					
				}	// recorre los metodos del objeto que representa una tabla
				
				
			}	// recorre la estructura
			
			
		}	// recorre los resultados del query (List)
		
		// libera recursos
		l_stTabla = null;
		l_class = null;
		l_stMetodo = null;
		l_stMetodo2 = null;
		l_arM = null;
		l_arM2 = null;
		m1 = null;
		l_ob = null; 
		
		
	}	//recorreResultados
	
	
	
	/**
	 * Obtiene los resultados evitando el lazy
	 * @param p_bo
	 */
	private void getResultados(boolean p_bo)
	{
		List r = new ArrayList();
		
		Session s = null;
		Query q = null;
		String qry = "";
		int l_inPos = 0;
		
		try
		{
			s = HibernateUtil.currentSession();
			
			if(getHibernateObject().indexOf("select") != -1 || getHibernateObject().indexOf("Select") != -1
					|| getHibernateObject().toUpperCase().trim().indexOf("WHERE") != -1)
				qry = getHibernateObject();
			else
				qry = "from " + getHibernateObject();
			
			if(getRestricciones().size()>0)
			{
				for(int i = 0; i < getRestricciones().size(); i++)
				{
					if(i == 0)
						qry += " where " + getRestricciones().get(i).toString();
					else
						qry += " and " + getRestricciones().get(i).toString();
				}
			}
			
			if(!orderBy.equalsIgnoreCase(""))
				qry += " order by " + orderBy;
			
			logger.info("QRY "+ qry);
			q = s.createQuery(qry);
			r = q.list();
			
			if(structure != null)
			{
				recorreResultados(r);
				
			}	// existe estrutura
		
		} 
		catch (HibernateException e) 
		{
			System.err.println("Error ->> "+e);
	
			return;
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();
			
			// libera recursos
			q = null;
			qry = null;
			
		}
		
		setResults(r);
		
	}	// getResultados
	

	/**
	 * Obtiene los hijos de la estructura de hibernate respecto de la estructura definida
	 */
	private void ejecutaHijos(TableStructure p_obStructure, Object p_obj)
	{
		
		String l_stTabla ="";
		Class l_class = null;
		String l_stMetodo = "";
		String l_stMetodo2 = "";
		Object l_obResult = null;
		Method[] l_arM = null;
		Method[] l_arM2 = null;
		Method	m1 = null;
		TableStructure l_obCurrectStruct = null;
		boolean l_boExiste = false;
		String l_stAlgo = "";
		

		//logger.info("recorre todos los resutados");
		// recorre los miembros de la estructura
		for(int i = 0; i < p_obStructure.getMiembros().size(); i++)
		{
			
			l_obCurrectStruct = p_obStructure.get(i);
			l_stTabla = l_obCurrectStruct.getTabla().substring(1);
			
			
			l_class = p_obj.getClass();
			l_boExiste = false;
			//logger.info("recorre los metodos");
			
			//obtiene todos los metodos de cada uno de los elementos del resultado
			l_arM = l_class.getDeclaredMethods();
			
			for(int j = 0; j < l_arM.length; j++)
			{
				// recorre los metodos
				
				if(l_arM[j].getName().endsWith(l_stTabla) && l_arM[j].getName().startsWith("g"))
				{
					//logger.info(l_arM[j].getName() + " ----" + l_stTabla);
					l_stMetodo = l_arM[j].getName();
					//logger.info(l_stMetodo);
					try
					{
						m1 = l_class.getMethod(l_stMetodo, null);
						//logger.info(m1.getName());
						l_obResult = m1.invoke(p_obj, null);
						l_stAlgo = l_obResult.toString();
						//logger.info(l_obResult);
						//logger.info(m1.invoke(p_obj, null));
						l_arM2 =(m1.invoke(p_obj, null)).getClass().getDeclaredMethods();
						
						for(int m = 0; m < l_arM2.length; m++)
						{
							l_stMetodo2 = l_arM2[m].getName();
							//logger.info(l_stMetodo2);
							break;
						}
						
						l_boExiste = true;
						break;
					}
					catch(Exception e)
					{
						logger.info(l_stTabla + "  <<<<   ----   ");
						//e.printStackTrace();
					}
					
				}	// lee los miembros que comienzan con "g"
				
				if(l_boExiste)
				{
					break;
				}

			}
			
			if(l_obCurrectStruct.getMiembros().size() > 0)
			{
				ejecutaHijos(l_obCurrectStruct, l_obResult); // hace el llamado de forma recursiva
			}
			
		}	// recorre los miembros de la estructura
		
		// libera recursos
		l_stTabla = null;
		l_class = null;
		l_stMetodo = null;
		l_stMetodo2 = null;
		l_obResult = null;
		l_arM = null;
		l_arM2 = null;
		m1 = null;
		l_obCurrectStruct = null;
		
		
	}	// ejecutaHijos
	
	
	@SuppressWarnings("unchecked")
	private void getResultados(int p_inFirstResult, int p_inMaxResults)
	{
		List r = new ArrayList();
		Method[] l_arM = null;
		Method[] l_arM2 = null;
		Method	m1 = null;
		Method	m2 = null;
		Object l_ob = new Object(); 
		Object l_ob1 = new Object();
		Long l_total = null;
		
		Session s = null;
		Query q = null;
		String qry = "";
		int l_inPos = 0;
		
		try
		{
			s = HibernateUtil.currentSession();
			
			if(getHibernateObject().indexOf("select") != -1 || getHibernateObject().indexOf("Select") != -1)
				qry = getHibernateObject();
			else
				qry = "select count(*) from " + getHibernateObject();
			

			if(getRestricciones().size() > 0)
			{
				for(int i = 0; i < getRestricciones().size(); i++)
				{
					if(i == 0)
						qry += " where " + getRestricciones().get(i).toString();
					else
						qry += " and " + getRestricciones().get(i).toString();
				}
				
			}
			
			logger.info("QRY: " + qry);
			q = s.createQuery(qry);
			l_total = (Long)q.uniqueResult();
			getPaging().setTotalSize(l_total.intValue());
			
			
			if(getHibernateObject().indexOf("select") != -1 || getHibernateObject().indexOf("Select") != -1)
				qry = getHibernateObject();
			else
				qry = "from " + getHibernateObject();
			
			if(getRestricciones().size() > 0)
			{
				for(int i = 0; i < getRestricciones().size(); i++)
				{
					if(i == 0)
						qry += " where " + getRestricciones().get(i).toString();
					else
						qry += " and " + getRestricciones().get(i).toString();
				}
				
			}
			
			if(!orderBy.equalsIgnoreCase(""))
				qry += " order by " + orderBy;
			
			logger.info("QRY: " + qry);
			q = s.createQuery(qry);
			q.setFirstResult(p_inFirstResult);
			q.setMaxResults(p_inMaxResults);
			r = q.list();
			
			l_inPos = orderBy.indexOf(".");
			
			if(l_inPos != -1)
			{
				// Para ordenar sin usar el lazy, LDTF; 26/Mar/2010
				String l_stMetodo = "";
				String l_stMetodo2 = "";
				String l_stTabla = orderBy.substring(1, l_inPos);
				Class l_class = null;
				
				for(int i = 0; i < r.size(); i++)
				{
					l_ob = r.get(i);
					l_class = l_ob.getClass();
					//logger.info(l_ob);
					l_arM = l_class.getDeclaredMethods();
					
					for(int j = 0; j < l_arM.length; j++)
					{
						if(l_arM[j].getName().endsWith(l_stTabla) && l_arM[j].getName().startsWith("g"))
						{
							l_stMetodo = l_arM[j].getName();
							//logger.info(l_stMetodo);
							try
							{
								m1 = l_class.getMethod(l_stMetodo, null);
								//logger.info(m1.getName());
								l_ob1 = m1.invoke(l_ob, null);
								l_ob1.toString();
								//logger.info(m1.invoke(l_ob, null));
								l_arM2 =(m1.invoke(l_ob, null)).getClass().getDeclaredMethods();
								
								for(int jj = 0; jj < l_arM2.length; jj++)
								{
									l_stMetodo2 = l_arM2[j].getName();
									//logger.info(l_stMetodo2);
									break;
								}
								
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							
							break;
							
						}	// solo lee los metdos que comienzan con "g"
						
					}	// barre los metodos de cada clase (objeto) recuperado
						
				}	// barre el total de resultados
			}
		
		} catch (HibernateException e) {
			System.err.println("Error ->> " + e);
	
			return;
		}
		
		finally
		{
			if(s != null)
				HibernateUtil.closeSession();
		}
		
		setResults(r);
		
	}	// getResultados


	/**
	 * Refreshes the list by calling the DAO methode with the modified search
	 * object. <br>
	 * 
	 * @param so
	 *            SearchObject, holds the entity and properties to search. <br>
	 * @param start
	 *            Row to start. <br>
	 * @param pageSize
	 *            Count rows to fetch. <br>
	 */
	void refreshModel(int start) {
		setFirstResult(start);
		setMaxResults(getPageSize());

		// clear old data
		clear();
		
		if(structure == null)
			getResultados(getFirstResult(), getMaxResults());
		else
			getResultados(true);
		

		//addAll(getResults().subList(getFirstResult(), ((getFirstResult())+getMaxResults())>getResults().size()?getResults().size():(getFirstResult())+getMaxResults()));
		addAll(getResults());

		
	}

	boolean isSupportPagging() {
		return supportPaging;
	}

	public void clearFilters() {
		initModel();
	}

	/**
	 * Sets the listeners. <br>
	 * <br>
	 * 1. "onPaging" for the paging component. <br>
	 * 2. "onSort" for all listheaders that have a sortDirection declared. <br>
	 * All not used Listheaders must me declared as:
	 * listheader.setSortAscending(""); listheader.setSortDescending(""); <br>
	 */
	private void setListeners(Listbox listBox) {

		// Add 'onPaging' listener to the paging component
		getPaging().addEventListener("onPaging", new OnPagingEventListener());

		Listhead listhead = listBox.getListhead();
		List<?> list = listhead.getChildren();

		OnSortEventListener onSortEventListener = new OnSortEventListener();
		for (Object object : list) {
			if (object instanceof Listheader) {
				Listheader lheader = (Listheader) object;

				if (lheader.getSortAscending() != null || lheader.getSortDescending() != null) {

					if (logger.isDebugEnabled()) {
						logger.debug("--> : " + lheader.getId());
					}
					lheader.addEventListener("onSort", onSortEventListener);
				}
			}
		}
		listBox.setModel(this);
	}

	/**
	 * "onPaging" eventlistener for the paging component. <br>
	 * <br>
	 * Calculates the next page by currentPage and pageSize values. <br>
	 * Calls the methode for refreshing the data with the new rowStart and
	 * pageSize. <br>
	 */
	public final class OnPagingEventListener implements EventListener {
		//@Override
		public void onEvent(Event event) throws Exception {

			PagingEvent pe = (PagingEvent) event;
			int pageNo = pe.getActivePage();
			int start = pageNo * getPageSize();

			if (logger.isDebugEnabled()) {
				logger.debug("--> : " + start + "/" + getPageSize());
			}

			// refresh the list
			refreshModel(start);
		}
	}

	/**
	 * "onSort" eventlistener for the listheader components. <br>
	 * <br>
	 * Checks wich listheader is clicked and checks which orderDirection must be
	 * set. <br>
	 * 
	 * Calls the methode for refreshing the data with the new ordering. and the
	 * remembered rowStart and pageSize. <br>
	 */
	public final class OnSortEventListener implements EventListener, Serializable {
		private static final long serialVersionUID = 1L;

		//@Override
		public void onEvent(Event event) throws Exception {
			final Listheader lh = (Listheader) event.getTarget();
			final String sortDirection = lh.getSortDirection();
			
			if ("ascending".equals(sortDirection)) {
				final Comparator<?> cmpr = lh.getSortDescending();
				if (cmpr instanceof FieldComparator) {
					orderBy = ((FieldComparator) cmpr).getOrderBy();
					//orderBy = StringUtils.substringBefore(orderBy, "DESC").trim();
				}
			} else if ("descending".equals(sortDirection) || "natural".equals(sortDirection) || Strings.isBlank(sortDirection)) {
				final Comparator<?> cmpr = lh.getSortAscending();
				if (cmpr instanceof FieldComparator) {
						orderBy = ((FieldComparator) cmpr).getOrderBy();
					//orderBy = StringUtils.substringBefore(orderBy, "ASC").trim();
				}
			}

			if (logger.isDebugEnabled()) {
				logger.debug("--> : " + lh.getId() + "/" + sortDirection);
				logger.debug("--> added   : orderBy " + orderBy);
			}

			if (isSupportPagging()) {
				// refresh the list
				getPaging().setActivePage(0);
				refreshModel(0);
			}
		}
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// ++++++++++++++++++ getter / setter +++++++++++++++++++//
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//



	public int getPageSize() {
		return getPaging().getPageSize();
	}

	Paging getPaging() {
		return paging;
	}

	private void setPaging(Paging paging) {
		this.paging = paging;
	}


	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public String getHibernateObject() {
		return hibernateObject;
	}

	public void setHibernateObject(String hibernateObject) {
		this.hibernateObject = hibernateObject;
	}

	@SuppressWarnings("unchecked")
	public List getResults() {
		return results;
	}

	@SuppressWarnings("unchecked")
	public void setResults(List results) {
		this.results = results;
	}

	public List<String> getRestricciones() {
		return restricciones;
	}

	public void setRestricciones(List<String> restricciones) {
		this.restricciones = restricciones;
	}
	
	/**
	 * @return the structure
	 */
	public TableStructure getStructure() {
		return structure;
	}

	/**
	 * @param structure the structure to set
	 */
	public void setStructure(TableStructure structure) {
		this.structure = structure;
	}


}	// end of class
