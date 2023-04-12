package mx.com.hunkabann.skf.backend.util;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import mx.com.hunkabann.skf.util.TableStructure;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;


public class HibernatePager {
private Logger logger = Logger.getLogger(this.getClass());
	
	
	// variables
	private int i_inItemIni = 1; // elemento inicial en la pagina
	private long i_inItemFin = 0; // elemento final en la pagina
	private int i_inRegPag = 0; // registros por pagina
	private int i_inPagsToShow = 0; // cantidad de paginas a mostrar
	private int i_inPagActual = 0; // pagina actual
	private int i_inPosPag = 0; // indica la posicion del apagina actual en el
								// total de paginas
	private int i_inIndex = 0; // indice del vector de paginas
	private int i_inPagIni = 0; // indica la pagina inicial a mostrar
	private int i_inPagFin = 0; // indica la pagina final a mostrar
	private int i_inTotalPags = 0; // total de paginas
	//private String i_stTotal = "0"; // total de elementos
	private long i_inTotal = 0; // total de elementos
	private String i_stURL = ""; // contiene el url del jsp donde se va a
									// paginar

	// objetos
	private Vector<Integer> i_vtPages = new Vector();
	private List i_liResultados = new ArrayList();
	private Session i_obSession = null;
	TableStructure structure = null;
	
	
	
	/**
	 * constructor nulo
	 */
	public HibernatePager()
	{
		
	}
	
	


	
	
	/**
	 * ejecuta un query paginado
	 * @param p_stSQLIni
	 * @param p_astParametros
	 * @param p_stSQLFin
	 * @param p_inRxP
	 * @param p_inTotal
	 * @param p_inPagToShow
	 * @throws Exception
	 */
	public void executeQuery(String p_stSQLIni, String[][] p_astParametros,
			String p_stSQLFin, int p_inRxP, long p_inTotal,
			int p_inPagToShow) throws Exception {
		// va a armar el query y ejecutarlo tomando en cuenta el ofsset
		logger.info(" executeQuery() -->pager - executeQuery ");
		// parametros[x][3]: 0 - cadena, 1 - valor, 2 - condicion valor de "-1"
		// (campo diferente de "")

		int l_inIni = 0;
		String l_stSQL = "";
		int l_inCont = 0;
		int l_inOffset = 0;


		// inicializacion
		i_inRegPag = p_inRxP;
		i_stURL = "";// p_stURL;
		i_inPagsToShow = 10; // p_inPages;
		i_inTotal = p_inTotal;

		// verifica si es necesario calcular el total de elementos o registros
		if (i_inTotal == 0)
			calculaTotal(p_stSQLIni, p_astParametros, p_stSQLFin, i_inTotal);


		// calcula los demas valores a partir del total
		calculaStatus(p_inPagToShow);
		l_inOffset = getOffset();
		i_inItemIni = l_inOffset + 1;

		// arma los parametros
		if (p_astParametros != null) {
			for (int i = 0; i < p_astParametros.length; i++) 
			{
				if (!p_astParametros[i][2].equals("")
					&& !p_astParametros[i][2].equals("%") 
					&& !p_astParametros[i][2].equals("%%")) 
				{
					if (p_astParametros[i][1] != null
							&& !p_astParametros[i][1].equals("")
							&& !p_astParametros[i][1].equals("-1"))
						l_stSQL += p_astParametros[i][0];
				} 
				else 
				{
					if (p_astParametros[i][1] != null
							&& !p_astParametros[i][1].equals("")
							&& !p_astParametros[i][1].equals("%")
							&& !p_astParametros[i][1].equals("%%"))
						l_stSQL += p_astParametros[i][0];
				}
			}
		}

		l_stSQL = p_stSQLIni + l_stSQL + p_stSQLFin;// + " limit " + p_stLimite
													// + " offset " +
													// l_stOffset;
		
		logger.info("Pager.l_stSQL: " + l_stSQL);
		
		

		try {

			i_liResultados = new ArrayList();
			
			Query q = i_obSession.createQuery(l_stSQL);
			
			q.setFirstResult(l_inOffset);
			q.setMaxResults(p_inRxP);
			l_inCont = 0;

			// establece los valores de los parametros
			if (p_astParametros != null) {
				for (int i = 0; i < p_astParametros.length; i++) 
				{
					if (!p_astParametros[i][2].equals("")
						&& !p_astParametros[i][2].equals("%%") 
						&& !p_astParametros[i][2].equals("%")) 
					{
						// comparacion contra -1 (claves de los combos)
						if (p_astParametros[i][1] != null
								&& !p_astParametros[i][1].equals("")
								&& !p_astParametros[i][1].equals("-1")) {

							q.setParameter(l_inCont++, p_astParametros[i][1]);
							logger.info("Pager.parametro: " + l_inCont + "  valor: " + p_astParametros[i][1]);
						}
					} 
					else 
					{
						// todas las demas
						if (p_astParametros[i][1] != null
								&& !p_astParametros[i][1].equals("")
								&& !p_astParametros[i][1].equals("%%") 
								&& !p_astParametros[i][1].equals("%")) 
						{

							q.setParameter(l_inCont++, p_astParametros[i][1]);
							logger.info("Pager.parametro: " + l_inCont + "  valor: " + p_astParametros[i][1]);

						}
					}
				}
			}


			i_liResultados = q.setMaxResults(i_inRegPag).list();
			recorreResultados(i_liResultados);
			
			// libera recursos
			p_astParametros = null;
			l_stSQL = null;


		} catch (Exception ex) {
			logger.info("Pager.ERROR: " + ex.toString());
			throw new Exception("Pager - executeQuery, SQL[" + l_stSQL
					+ "]  " + ex);
		} 


	} // executeQuery

	public void executeQuery(String p_stSQLIni, String[][] p_astParametros,
			String p_stSQLFin, int p_inRxP, int p_inOffset,
			int p_inTotal, String p_stURL, int p_inPages)
			throws SQLException {
		// va a armar el query y ejecutarlo tomando en cuenta el ofsset

		// parametros[x][3]: 0 - cadena, 1 - valor, 2 - condicion valor de "-1"
		// (campo diferente de "")

		int l_inIni = 0;
		String l_stSQL = "";
		int l_inCont = 0;

		// inicializacion
		//p_stOffset = p_stOffset == null ? "0" : p_stOffset;
		//p_stTotal = p_stTotal == null ? "0" : p_stTotal;
		i_inRegPag = p_inRxP;
		i_inItemIni = p_inOffset + 1;
		i_stURL = p_stURL;
		i_inPagsToShow = p_inPages;
		i_inTotal = p_inTotal;


		try {

			calculaTotal(p_stSQLIni, p_astParametros, p_stSQLFin, p_inTotal);


			// arma los parametros
			if (p_astParametros != null) {
				for (int i = 0; i < p_astParametros.length; i++) 
				{
					if (!p_astParametros[i][2].equals("")
						&& !p_astParametros[i][2].equals("%%") 
						&& !p_astParametros[i][2].equals("%")) 
					{
						if (p_astParametros[i][1] != null
								&& !p_astParametros[i][1].equals("")
								&& !p_astParametros[i][1].equals("-1"))
							l_stSQL += p_astParametros[i][0];

					} 
					else 
					{
						if (p_astParametros[i][1] != null
							&& !p_astParametros[i][1].equals("")
							&& !p_astParametros[i][1].equals("%%")
							&& !p_astParametros[i][1].equals("%"))
							l_stSQL += p_astParametros[i][0];

					}
				}
			}

			l_stSQL = p_stSQLIni + l_stSQL + p_stSQLFin;// + " limit " + p_stLimite
														// + " offset " +
														// p_stOffset;
			i_liResultados = new ArrayList();

			Query q = i_obSession.createQuery(l_stSQL);
			
			
			
			q.setFirstResult(p_inOffset);
			q.setMaxResults(p_inRxP);
			l_inCont = 0;

			// establece los valores de los parametros
			if (p_astParametros != null) {
				for (int i = 0; i < p_astParametros.length; i++) 
				{
					if (!p_astParametros[i][2].equals("")
						&& !p_astParametros[i][2].equals("%%") 
						&& !p_astParametros[i][2].equals("%")) 
					{
						// comparacion contra -1 (claves de los combos)
						if (p_astParametros[i][1] != null
								&& !p_astParametros[i][1].equals("")
								&& !p_astParametros[i][1].equals("-1")) {

							q.setParameter(l_inCont++, p_astParametros[i][1]);
						}
					} 
					else 
					{
						// todas las demas
						if (p_astParametros[i][1] != null
							&& !p_astParametros[i][1].equals("")
							&& !p_astParametros[i][1].equals("%%") 
							&& !p_astParametros[i][1].equals("%")) 
						{

							q.setParameter(l_inCont++, p_astParametros[i][1]);

						}
					}
				}
			}

			i_liResultados = q.list();
			recorreResultados(i_liResultados);

			// calcula los demas valores a partir de los resultados
			calculaStatus();

			// libera recursos
			p_astParametros = null;
			l_stSQL = null;


		} catch (Exception ex) {
			throw new SQLException("Pager - executeQuery, SQL[" + l_stSQL
					+ "]  " + ex);
		} 


	} // executeQuery

	
	
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
		boolean l_boExiste = false;
		Object [  ]  value =  {  null  } ;
		TableStructure l_obCurrectStruct = null;
		TableStructure l_obStructure = null;
		
		if(getStructure() != null)
			l_obCurrectStruct = getStructure().getStructure();
		
		l_obStructure = l_obCurrectStruct;
		
		logger.info("recorre todos los resutados");
		if(l_obCurrectStruct == null)
			return;
		
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
				}
				if(l_boExiste)
				{
					break;
				}

			}
			
			if(l_obCurrectStruct.getMiembros().size() > 0)
			{
				ejecutaHijos(l_obCurrectStruct, l_obResult);
			}
		}
		
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
	
	
	
	
	

	
	/**
	 * obtiene el offset de la pagina actual
	 * @return
	 */
	private int getOffset() {
		
		if (i_inPagActual - 1 < 0)
			return 0;

		return i_vtPages.elementAt(i_inPagActual - 1);
		
	}	// getOffset
	
	

	public void calculaTotal(String p_stSQLIni, String[][] p_astParametros,
			String p_stSQLFin, long p_inTotal) throws Exception {

		// arma el query y calcula el total de registros

		// parametros[x][3]: 0 - cadena, 1 - valor, 2 - condicion valor de "-1"
		// (campo diferente de "")
		
		logger.info("Pager.calculaTotal");

		int l_inIni = 0;
		String l_stTmp = "";
		String l_stSQLIni = p_stSQLIni;
		String l_stSQLFin = p_stSQLFin;
		String l_stSQL = "";
		String [] l_astTokens = null;
		int l_inIndex = 0;
		int l_inCont = 0;
		

		// se van a extraer todos los campos para dejar solo count(*), en el
		// select
		l_stTmp = l_stSQLIni.trim();
		l_astTokens = l_stTmp.split(" ");
		
		for(int i = 0; i < l_astTokens.length; i++)
		{
			l_inIndex = i;
			
			if(l_astTokens[i].equalsIgnoreCase("FROM"))
				break;
			
		}
		
		l_stSQLIni = "select count(" + l_astTokens[l_inIndex + 2] + ") ";
		
		for(int i = l_inIndex; i < l_astTokens.length; i++)
			l_stSQLIni += l_astTokens[i] + " ";
		
		

		// se elimina la ordenacion del select
		if (l_stSQLFin.toUpperCase().indexOf("ORDER BY") > -1) {
			l_stTmp = l_stSQLFin.toUpperCase();
			l_inIni = l_stTmp.indexOf("ORDER BY");
			l_stSQLFin = p_stSQLFin.substring(0, l_inIni);
		}

		l_inCont = 0;

		if (p_astParametros != null) {
			for (int i = 0; i < p_astParametros.length; i++) 
			{
				if (!p_astParametros[i][2].equals("")
					&& !p_astParametros[i][2].trim().equals("%") 
					&& !p_astParametros[i][2].trim().equals("%%")) 
				{
					if (p_astParametros[i][1] != null
							&& !p_astParametros[i][1].equals("")
							&& !p_astParametros[i][1].equals("-1"))
						l_stSQL += p_astParametros[i][0];

				} 
				else 
				{
					if (p_astParametros[i][1] != null
							&& !p_astParametros[i][1].equals("")
							&& !p_astParametros[i][1].trim().equals("%")
							&& !p_astParametros[i][1].trim().equals("%%"))
						l_stSQL += p_astParametros[i][0];

				}
			}
		}

		l_stSQL = l_stSQLIni + l_stSQL + l_stSQLFin;
		
		logger.info("en pager.calcula query:" + l_stSQL);
		
		try 
		{

			Query q = i_obSession.createQuery(l_stSQL);

			if (p_astParametros != null) {
				int param[] = new int[p_astParametros.length];
				for (int i = 0; i < p_astParametros.length; i++) 
				{
					if (!p_astParametros[i][2].equals("")
						&& !p_astParametros[i][2].trim().equals("%") 
						&& !p_astParametros[i][2].trim().equals("%%")) 
					{
						// comparacion contra -1 (claves de los combos)
						if (p_astParametros[i][1] != null
								&& !p_astParametros[i][1].equals("")
								&& !p_astParametros[i][1].equals("-1")) 
						{
							logger.info("p_astParametros[" + i + "][1]:" + (p_astParametros[i][1].endsWith("Z") ? "%" +
							 p_astParametros[i][1] : p_astParametros[i][1]) + "," + l_inCont);
							 q.setParameter(l_inCont++,
							 (p_astParametros[i][1].endsWith("Z%") ? "%" +
							 p_astParametros[i][1] : p_astParametros[i][1]));
							 

						}
					} else {
						// todas las demas
						if (p_astParametros[i][1] != null
								&& !p_astParametros[i][1].equals("")
								&& !p_astParametros[i][1].trim().equals("%")
							&& !p_astParametros[i][1].trim().equals("%%")) 
						{
							logger.info("else p_astParametros[" + i + "][1]: " + p_astParametros[i][1]);
							logger.info("else p_astParametros[" + i + "][1]: " + (p_astParametros[i][1].endsWith("Z") ? "%" +
							 p_astParametros[i][1] : p_astParametros[i][1]));
							 q.setParameter(l_inCont++,
							 (p_astParametros[i][1].endsWith("Z%") ? "%" +
							 p_astParametros[i][1] : p_astParametros[i][1]));

						}
					}
				}
			}

			if(q.list().size() == 1)
				i_inTotal = (Long)q.list().get(0);
			else
				i_inTotal = q.list().size();

			logger.info("Es en Pager.calculaTotal tamanio consulta:" + i_inTotal);

		} catch (Exception ex) {
			throw new Exception("Pager - calculaTotal, SQL[" + l_stSQL + "]  " + ex);
		}


		// libera recursos
		l_stTmp = null;
		l_stSQLIni = null;
		l_stSQLFin = null;
		l_stSQL = null;

	} // calculaTotal

	
	
	/**
	 * calcula el estatus del paginador
	 */
	private void calculaStatus() {
		
		long l_inTotal = i_inTotal;
		
		i_inTotalPags = (int) l_inTotal / i_inRegPag;

		// calcula el item final de la pagina
		if (i_inItemIni + i_inRegPag < l_inTotal)
			i_inItemFin = i_inItemIni + i_inRegPag - 1;
		else
			i_inItemFin = l_inTotal;

		// calcula el total de paginas
		if (l_inTotal / i_inRegPag > i_inTotalPags)
			i_inTotalPags++;

		if (i_inTotalPags * i_inRegPag < l_inTotal)
			i_inTotalPags++;

		// calcula la pagina actual
		i_inPagActual = (int) i_inItemIni / i_inRegPag;
		i_inPagActual++;

		// establece los offset por pagina
		calculateOffsetPerPage();
		
		
		// establece las paginas a visualizar
		getPageLimits();

	} // calculaStatus
	
	

	private void calculaStatus(int p_inPagToShow) {
		long l_inTotal = i_inTotal;
		i_inTotalPags = (int) l_inTotal / i_inRegPag;
		
		if (l_inTotal != 0)
			i_inTotalPags = l_inTotal % i_inRegPag > 0 ? i_inTotalPags + 1 : i_inTotalPags;
		else
			i_inTotalPags = 0;

		if (p_inPagToShow < 1) {
			p_inPagToShow = 1;
		}

		if (p_inPagToShow > i_inTotalPags) {
			p_inPagToShow = i_inTotalPags;
		}

		// calcula el offset
		if (i_inTotalPags != 0)
			i_inItemIni = (p_inPagToShow - 1) * i_inRegPag + 1;
		else
			i_inItemIni = 0;

		// calcula el item final de la pagina
		if (i_inItemIni + i_inRegPag < l_inTotal)
			i_inItemFin = i_inItemIni + i_inRegPag - 1;
		else
			i_inItemFin = l_inTotal;

		// calcula el total de paginas
		if (l_inTotal / i_inRegPag > i_inTotalPags)
			i_inTotalPags++;

		if (i_inTotalPags * i_inRegPag < l_inTotal)
			i_inTotalPags++;

		// calcula la pagina actual
		// i_inPagActual = (int) i_inItemIni / i_inRegPag;
		// i_inPagActual++;
		i_inPagActual = p_inPagToShow;

		// establece los offset de todas las paginas
		calculateOffsetPerPage();
		
		// establece las paginas a visualizar
		getPageLimits();

	} // calculaStatus

	
	/**
	 * establece un vector con el offset por pagina
	 */
	private void calculateOffsetPerPage() {

		int l_inOffset = 0;
		i_vtPages.removeAllElements();
		i_vtPages = null;
		i_vtPages = new Vector<Integer>();

		// establece en un vector todas las paginas posibles
		for (int i = 0; i < i_inTotalPags; i++) {
			i_vtPages.add(l_inOffset);

			l_inOffset += i_inRegPag;
		}

	} // calculateOffsetPerPage
	
	

	public int getFirstItem() {
		return i_inItemIni;

	} // getFirstItem

	public long getLastItem() {
		return i_inItemFin;

	} // getLastItem

	public boolean isFirstPage() {
		// indica si esta en la primera pagina

		if (i_inPagActual == 1)
			return true;

		return false;

	} // isFirstOne

	public boolean isLastPage() {
		// indica si esta en la ultima pagina

		if (i_inPagActual == i_inTotalPags)
			return true;

		return false;

	} // isLastOne

	public boolean nextPage() {
		// indica si hay mas paginas en el vector de paginas y avanza,
		// estableciendo el rango de paginas visibles

		if (i_inTotalPags <= i_inPagsToShow) {
			// el total de paginas es menor a la cantidad de paginas a mostrar
			// System.out.println("el total de paginas es menor a la cantidad de
			// paginas a mostrar");


			if (i_inIndex > i_vtPages.size())
				return false;
		}

		if (i_inIndex < i_inPagIni || i_inPagFin < i_inIndex) {
			// el total de paginas es menor a la cantidad de paginas a mostrar
			// System.out.println("2 el total de paginas es menor a la cantidad
			// de paginas a mostrar");
			return false;
		}

		if (i_inIndex > i_vtPages.size() - 1) {
			// para indicar que ya no hay paginas terminando el total de ellas
			// System.out.println("para indicar que ya no hay paginas terminando
			// el total de ellas");
			return false;
		}


		i_inIndex++;
		return true;

	} // nextPage

	
	
	
	/**
	 * identifica las paginas mínima y másxima a mostrar a partir de la pagina seleccionada
	 */
	private void getPageLimits() {

		int l_inMedia = (int) i_inPagsToShow / 2;

		i_inPagIni = i_inPagActual - l_inMedia;
		i_inPagFin = i_inPagActual + l_inMedia;



		if (i_inPagIni < 1) {

			i_inPagIni = 1;
			i_inPagFin = i_inPagsToShow;
		} else if (i_inPagFin > i_inTotalPags) {

			i_inPagIni = i_inTotalPags - i_inPagsToShow;
			i_inPagFin = i_inTotalPags;
		}

		if (i_inPagFin - i_inPagIni < i_inPagsToShow) {

			i_inPagIni--;
		} else if (i_inPagFin - i_inPagIni < i_inPagsToShow) {

			i_inPagIni++;
		}

		i_inIndex = i_inPagIni;

		if (i_inIndex < 0)
			i_inIndex = 0;



	} // getPageLimits
	
	
	

//	public String getFirstPage() {
//		// regresa el url para la primera pagina
//		return (String) i_vtPages.elementAt(0);
//
//	} // getFirstPage

	
	/**
	 * Regresa cual es la ultima pagina
	 */
	public int getLastPage() {
		
		return i_vtPages.size();

	} // getLastPage
	

	/**
	 * regresa la pagina anterior
	 * @return
	 */
	public int getPrevPage() {
		// obtiene el url con el offset anterior
		return i_inPagActual - 1 < 1 ? 1 : i_inPagActual - 1;
		
	}	// getPrevPage
	
	

	/**
	 * obtiene la siguiente pagina
	 * @return
	 */
	public int getNextPage() {
		// obtiene el url con el offset siguiente
		return i_inPagActual + 1 > i_vtPages.size() ? i_vtPages.size() : i_inPagActual + 1;

	} // getNextPage

//	public String getPageUrl() {
//		// obtiene el url con su respectivo offset
//		return (String) i_vtPages.elementAt(i_inIndex - 1);
//
//	} // getPageUrl
	
	

	/**
	 * Obtiene la pagina actual
	 */
	public int getPageNumber() {
		// obtiene el numero de pagina actual
		return i_inIndex;
		
	}	// getPageNumber
	
	
	/**
	 * regresa el indice de la pagina actual
	 * @return
	 */
	public int getCurrentPage()
	{
		return i_inPagActual;
		
	}	// getCurrentPage
	
	

	
	/**
	 * identifica si elindice está en la pagina actual
	 * @return
	 */
	public boolean isCurrentPage() {
		if (i_inIndex == i_inPagActual)
			return true;

		return false;

	} // isCurrentPage
	
	


	/**
	 * Libera recursos ocupados por el pager
	 * @throws SQLException
	 */
	public void release() throws SQLException {
		try {
			if (i_liResultados != null)
			{
				i_liResultados.clear();
				i_liResultados = null;
			}

			i_vtPages.removeAllElements();
			i_vtPages = null;
			
		} catch (Exception ex) {
			throw new SQLException("Pager - release,  " + ex);
		}

	} // release

	
	/**
	 * Regresa una lista con los resultados del query
	 * @return
	 */
	public List geResultados() {
		return i_liResultados;
		
	}	// geResultados

	public void setResultados(List resultados) {
		i_liResultados = resultados;
	}



	public Session getSession() {
		return i_obSession;
	}



	public void setSession(Session iObSession) {
		i_obSession = iObSession;
	}



	public TableStructure getStructure() {
		return structure;
	}



	public void setStructure(TableStructure structure) {
		this.structure = structure;
	}


	public long getTotalRegistros() {
		return i_inTotal;
	}

} // end of file


