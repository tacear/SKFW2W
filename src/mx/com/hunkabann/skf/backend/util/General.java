package mx.com.hunkabann.skf.backend.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mx.com.hunkabann.skf.util.Constantes;
import mx.com.hunkabann.skf.util.HibernateUtil;
import mx.com.hunkabann.skf.util.SessionBean;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import sun.misc.BASE64Encoder;

/**
 * Clase que contiene los metodos mas generales usados en la aplicacion
 * @author
 *
 */
public class General extends LlenaCombos implements Constantes
{
	private Logger logger = Logger.getLogger(this.getClass());

//	private TbBitacora i_obBitacora = new TbBitacora();
	private SessionBean i_obSBean = null;
//	private TbUsuario i_obUser = new TbUsuario();
//	private TbTienda i_obTienda = new TbTienda();
//	private TbAlmacen i_obAlamacen = new TbAlmacen();
//	private TbCorporativo i_obCorporativo = new TbCorporativo();
//	private TbCtosBitacora i_obCtosBitacora = new TbCtosBitacora();
	//carga
	protected BufferedWriter		out;
	protected InputStreamReader     inInputStream;
	protected char					i_enter						= 10;
	protected  SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm");


	private String stDivisa = "PESOS";
	private String stMn     = "M.N.";

	/**
	 * Metodo que obtiene el Objeto TbEntidadFederativa con base en una direccion, es decir regresa la entidad
	 * al que pertenece la direccion parametro
	 * @param direccion
	 * @return
	 */
//	public TbEntidadFederativa getEntidadFederativa(TbDireccion direccion) {
//		Session s = null;
//		Query q = null;
//
//		TbEntidadFederativa entidad = new TbEntidadFederativa();
//		//TbSecUser usuario = new TbSecUser();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbDireccion d where d.nukiddireccion="+direccion.getNukiddireccion());
//			if(q.list().size()>0)
//			{
//				direccion = (TbDireccion)q.list().get(0);
//				entidad =direccion.getTbEntidadFederativa();
//
//				q = s.createQuery("from TbEntidadFederativa e where e.nukidentidad="+entidad.getNukidentidad());
//				if(q.list().size()>0)
//				{
//					entidad = (TbEntidadFederativa)q.list().get(0);
//				}
//
//			}
//		}
//		catch (HibernateException e) {
//			System.err.println("ERROR--->>> "+e);
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		return entidad;
//	}


	/**
	 * Metodo que obtiene el Objeto TbCorporativo con base en una tienda, es decir regresa el corporativo
	 * al que pertenece la tienda parametro
	 * @param tienda
	 * @return
	 */
//	public TbCorporativo getCorporativo(TbTienda tienda) {
//		Session s = null;
//		Query q = null;
//
//		TbCorporativo corporativo = new TbCorporativo();
//
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbCorporativo c where c.nukidCorporativo="+tienda.getTbCorporativo().getNukidCorporativo());
//			if(q.list().size()>0)
//				corporativo = (TbCorporativo)q.list().get(0);
//		}
//		catch (HibernateException e) {
//			System.err.println("ERROR--->>> "+e);
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		return corporativo;
//	}


	/**
	 * Metodo que obtiene el Objeto TbDireccion con base en una tienda, es decir regresa la direccion
	 * de la tienda parametro
	 * @param tienda
	 * @return
	 */
//	public TbDireccion getDireccion(TbTienda tienda) {
//		Session s = null;
//		Query q = null;
//
//		TbDireccion direccion = new TbDireccion();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbTienda t where t.nukidTienda="+tienda.getNukidTienda());
//			if(q.list().size()>0)
//			{
//				tienda = (TbTienda)q.list().get(0);
//				direccion =tienda.getTbDireccion();
//
//				q = s.createQuery("from TbDireccion d where d.nukiddireccion="+direccion.getNukiddireccion());
//				if(q.list().size()>0)
//				{
//					direccion = (TbDireccion)q.list().get(0);
//				}
//
//			}
//		}
//		catch (HibernateException e) {
//			System.err.println("ERROR--->>> "+e);
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		return direccion;
//	}



	/**
	 * Metodo que obtiene el Objeto TbAlmacen con base en una tienda, es decir regresa el almacen (centro de distribuci�n)
	 * de la tienda parametro
	 * @param tienda
	 * @return
	 */

//	public TbAlmacen getCentroDistribucion(TbTienda tienda) {
//		Session s = null;
//		Query q = null;
//
//		TbAlmacen centroDistribucion = new TbAlmacen();
//
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbAlmacen a where a.nukidAlmacen="+tienda.getNukidAlmacen());
//			if(q.list().size()>0)
//				centroDistribucion = (TbAlmacen)q.list().get(0);
//		}
//		catch (HibernateException e) {
//			System.err.println("ERROR--->>> "+e);
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		return centroDistribucion;
//	}

	/**
	 * Metodo que obtiene el Objeto TbDireccion con base en un corporativo, es decir regresa la direccion
	 * del corporativo parametro
	 * @param corporativo
	 * @return
	 */

//	public TbDireccion getDireccion(TbCorporativo corporativo) {
//		Session s = null;
//		Query q = null;
//
//		TbDireccion direccion = new TbDireccion();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbCorporativo c where c.nukidCorporativo="+corporativo.getNukidCorporativo());
//			if(q.list().size()>0)
//			{
//				corporativo = (TbCorporativo)q.list().get(0);
//				direccion =corporativo.getTbDireccion();
//
//				q = s.createQuery("from TbDireccion d where d.nukiddireccion="+direccion.getNukiddireccion());
//				if(q.list().size()>0)
//				{
//					direccion = (TbDireccion)q.list().get(0);
//				}
//
//			}
//		}
//		catch (HibernateException e) {
//			System.err.println("ERROR--->>> "+e);
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		return direccion;
//	}

	/**
	 * Metodo que obtiene el Objeto TbCorporativo con base en un almacen, es decir regresa el corporativo
	 * al que pertenece al almacen parametro
	 * @param almacen
	 * @return
	 */

//	public TbCorporativo getCorporativo(TbAlmacen almacen) {
//		Session s = null;
//		Query q = null;
//
//		TbCorporativo corporativo = new TbCorporativo();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbCorporativo c where c.nukidCorporativo="+almacen.getTbCorporativo().getNukidCorporativo());
//			if(q.list().size()>0)
//				corporativo = (TbCorporativo)q.list().get(0);
//		}
//		catch (HibernateException e) {
//			System.err.println("ERROR--->>> "+e);
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		return corporativo;
//	}

	/**
	 * obtiene un objeto por id
	 */
	public Object getBusinessObjectPorId(Integer id, Class clazz, String campoId) {
		Object Object = null;
		Session s = null;
		try {
			String className = clazz.getName();
			s = HibernateUtil.currentSession();
			Query query = s.createQuery("FROM " + className + " c WHERE c."+ campoId +" = :id");
			query.setParameter("id", id);
			Iterator iterator = query.list().iterator();
			if (iterator.hasNext()) {
				Object = (Object) iterator.next();

			}
		} catch(Throwable e) {
			System.err.println("ERROR--->>> "+e);
		} finally {
			if(s!=null)
				HibernateUtil.closeSession();
		}
		return Object;

	}	// getBusinessObjectPorId


	/**
	 * obtiene un objeto por id
	 */
	public List<Object> getListBusinessObjectPorId(Integer id, Class clazz, String campoId) {
		Object Object = null;
		Session s = null;
		try {
			String className = clazz.getName();
			s = HibernateUtil.currentSession();
			Query query = s.createQuery("FROM " + className + " c WHERE c."+ campoId +" = :id");
			query.setParameter("id", id);
			return query.list();
			
		} catch(Throwable e) {
			System.err.println("ERROR--->>> "+e);
		} finally {
			if(s!=null)
				HibernateUtil.closeSession();
		}
		return null;

	}	// getBusinessObjectPorId
	/**
	 * Verifica la existencia de un valor en una columna especificada (string)
	 */
	public boolean existe (Class clazz, String campo, String valor)
	{
		boolean existe = false;
		Query query = null;
		String className = "";
		Session s = null;
		Iterator iterator = null;

		try
		{
			s = HibernateUtil.currentSession();
			className = clazz.getName();
			query = s.createQuery("FROM " + className + " c WHERE c." + campo + " = '" +valor + "'");
			iterator = query.list().iterator();
			if (iterator.hasNext())
			{
				existe =true;
			}

		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();

			className = null;
			query = null;
			iterator = null;
		}

		return existe;

	}	// existe

	/**
	 * Verifica la existencia de un valor en una columna especificada (string) de un corpotativo
	 */
	public boolean existe (Class clazz, String campo, String valor, Integer idCorporativo)
	{
		boolean existe = false;
		Query query = null;
		String className = "";
		Session s = null;
		Iterator iterator = null;

		try
		{
			s = HibernateUtil.currentSession();
			className = clazz.getName();
			query = s.createQuery("FROM " + className + " c WHERE c." + campo + " = '" +valor + "' and c.tbCorporativo.nukidCorporativo="+idCorporativo);
			iterator = query.list().iterator();
			if (iterator.hasNext())
			{
				existe =true;
			}

		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();

			className = null;
			query = null;
			iterator = null;
		}

		return existe;

	}	// existe


	/**
	 * Verifica la existencia de un valor en una columna especificada (string)
	 */
	public boolean existe (Class clazz, String campo, String valor, String condicion)
	{
		boolean existe = false;
		Query query = null;
		String className = "";
		Session s = null;
		Iterator iterator = null;

		try
		{
			s = HibernateUtil.currentSession();
			className = clazz.getName();
			query = s.createQuery("FROM " + className + " c " +
					" WHERE c." + campo + " = '" +valor + "' and c." +
					condicion);
			iterator = query.list().iterator();
			if (iterator.hasNext())
			{
				existe =true;
			}

		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();

			className = null;
			query = null;
			iterator = null;
		}

		return existe;

	}	// existe


	/**
	 * Guarda el contenido de un objeto en bitacora
	 * @param p_obSession	Sesion de la transacci�n
	 * @param p_ob			objeto a guardar en la bitacora
	 * @param p_inOperacion	operaci�n a registrar en la bit�cora
	 */
//	public synchronized void guardaBitacora(Session p_obSession, Object p_ob, int p_inOperacion) throws Exception
//	{
//
//		i_obSBean = (SessionBean) Sessions.getCurrent().getWebApp().getAttribute(SecurityContextHolder.getContext().getAuthentication().getName());
//
//		try
//		{
//			i_obUser.setNukidUsuario(i_obSBean.getIdUsuario());
//			i_obTienda.setNukidTienda(i_obSBean.getIdTienda());
//			i_obAlamacen.setNukidAlmacen(i_obSBean.getIdCentroDistribucion());
//			i_obCorporativo.setNukidCorporativo(i_obSBean.getIdCorporativo());
//			i_obCtosBitacora.setNukidCtoBitacora(p_inOperacion);
//
//			i_obBitacora.setTbSecUser(i_obUser);
//			i_obBitacora.setTbTienda( i_obSBean.getIdTienda() == null ? null : i_obTienda  );
//			i_obBitacora.setTbAlmacen( i_obSBean.getIdCentroDistribucion() == null ? null : i_obAlamacen );
//			i_obBitacora.setTbCorporativo( i_obSBean.getIdCorporativo() == null ? null : i_obCorporativo );
//			i_obBitacora.setDtfechaMovimiento(new Timestamp(new Date().getTime()));
//			i_obBitacora.setChvalor(Snoopy.urga(p_ob));
//			i_obBitacora.setTbCtosBitacora(new TbCtosBitacora(p_inOperacion));
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



	/**
	 * Guarda el contenido de un objeto en bitacora
	 * @param p_obSession	Sesion de la transacci�n
	 * @param p_ob			objeto a guardar en la bitacora
	 * @param p_inOperacion	operaci�n a registrar en la bit�cora
	 */
//	public synchronized void guardaBitacoraTx(Session p_obSession, Object p_ob, int p_inOperacion) throws Exception
//	{
//
//		i_obSBean = (SessionBean) Sessions.getCurrent().getWebApp().getAttribute(SecurityContextHolder.getContext().getAuthentication().getName());
//
//		TbBitacora l_obBitacora = new TbBitacora();
//
//
//		try
//		{
//			i_obUser.setNukidUsuario(i_obSBean.getIdUsuario());
//			i_obTienda.setNukidTienda(i_obSBean.getIdTienda());
//			i_obAlamacen.setNukidAlmacen(i_obSBean.getIdCentroDistribucion());
//			i_obCorporativo.setNukidCorporativo(i_obSBean.getIdCorporativo());
//			i_obCtosBitacora.setNukidCtoBitacora(p_inOperacion);
//
//			l_obBitacora.setTbSecUser(i_obUser);
//			l_obBitacora.setTbTienda( i_obSBean.getIdTienda() == null ? null : i_obTienda  );
//			l_obBitacora.setTbAlmacen( i_obSBean.getIdCentroDistribucion() == null ? null : i_obAlamacen );
//			l_obBitacora.setTbCorporativo( i_obSBean.getIdCorporativo() == null ? null : i_obCorporativo );
//			l_obBitacora.setDtfechaMovimiento(new Timestamp(new Date().getTime()));
//			l_obBitacora.setChvalor(Snoopy.urga(p_ob));
//			l_obBitacora.setTbCtosBitacora(new TbCtosBitacora(p_inOperacion));
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





	/**
	 * Metodo que regresa la cantidad parametro en letra
	 * @param word
	 * @return
	 */
	public String convertirALetra(String word)
	{
		String [] cantidad = {"","MIL","MILLONES","MIL"};
		int itMin = 0;
		int itMax = 3;
		String stTmp= " ";
		String stTotal = "";
		String stResiduo = "00";

		int longitud = word.length();

		int itTmp = word.indexOf(".");

		if (itTmp != -1)
		{
			stResiduo = word.substring(itTmp,longitud);
			if (stResiduo.length() == 1 )
				stResiduo = "00";
			else if (stResiduo.length() == 2 )
				stResiduo = stResiduo.substring(1,2) + "0";
			else if (stResiduo.length() > 2 )
				stResiduo = stResiduo.substring(1,3);

			word = word.substring(0,itTmp);
		}

		longitud= word.length() % 3;

		if (longitud == 1)
			word = "00"+ word;

		if (longitud == 2)
			word = "0"+ word;

		longitud = word.length();

		if ( (longitud == 9) && (word.charAt(longitud-7) == '1')
		                     && (word.charAt(longitud-8) == '0')
		                     && (word.charAt(longitud-9) == '0') )
			cantidad[2]="MILLON";

		longitud = word.length() / 3;

		if (longitud > 1)
		{
			itMax = longitud * 3;
			itMin = itMax -3;
		}

		for (int i = 0; i < longitud; i++)
		{
			stTmp = word.substring(itMin,itMax);

			if(!stTmp.equals("000"))
				stTotal = operacion(stTmp) + cantidad[i] + " " + stTotal;

			if ( (i+1) < longitud )
			{
				itMin -= 3;
				itMax -= 3;
			}
		}

		return stTotal.trim() + " " + stDivisa + " " + stResiduo + "/100 " + stMn;

	}//metodo



	/**
	 * Metodo auxiliar para obtener la cantidad en letra
	 * @param word1
	 * @return
	 */
	public String operacion(String word1)
	{

		String [] unidades = {"","UN","DOS","TRES","CUATRO","CINCO","SEIS","SIETE","OCHO","NUEVE"};
		String [] un     = {"","ONCE","DOCE","TRECE","CATORCE","QUINCE","DIECISEIS","DIECISIETE","DIECIOCHO","DIECINUEVE"};
		String [] decenas  = {"","DIEZ","VEINTI","TREINTA","CUARENTA","CINCUENTA","SESENTA","SETENTA","OCHENTA","NOVENTA"};
		String [] centenas = {"","CIENTO","DOSCIENTOS","TRESCIENTOS","CUATROCIENTOS","QUINIENTOS","SEISCIENTOS","SETECIENTOS","OCHOCIENTOS","NOVECIENTOS"};

		if (word1.equals("100"))
			return "CIEN ";

		int uno  = Integer.valueOf(word1.substring(0,1)).intValue();
		int dos  = Integer.valueOf(word1.substring(1,2)).intValue();
		int tres = Integer.valueOf(word1.substring(2,3)).intValue();


		word1 = "";

		word1 = centenas[uno] + " ";

		if ((dos == 1) && (tres != 0))
		{
			word1 += un[tres] + " ";
			return word1;
		}

		if((dos == 2)&&(tres == 0))
			return word1 += "VEINTE ";

		word1 += decenas[dos] ;

		if((dos != 0) && (dos != 2) && (tres != 0) )
			word1 += " Y ";

		word1 += unidades[tres] + " ";

		return word1;

	}	// operacion
	/**
	 * Metodo para calcular el descuento total de la suma de varios porcentajes de descuento
	 * @param p_astDescuento
	 * @return
	 */
	public Double getDescuentoTotal(String p_astDescuento) {
//		p_astDescuento = p_astDescuento.replace("+", ",");
		String[] l_astDescuento = p_astDescuento.split(",");
        if (l_astDescuento == null)
            return new Double(0);

        String l_stDescuento = "";
        float  l_flY = 100f;

        for (int i = 0; i < l_astDescuento.length; i++) {
            if(!l_astDescuento[i].equals("")) {
                l_flY = l_flY * (100 - Float.parseFloat(l_astDescuento[i])) / 100;
            }
        }

        l_flY = (100 - l_flY) * 100;
        l_flY = Math.round(l_flY);
        return new Double(l_flY / 100);

    }
	/**
	 * Metodo para calcular el iva, subtotal, total, total letra, descuento y el pocentaje de descuento
	 * @param listBoxDetalle
	 * @param tbIva
	 * @param tbSubtotal
	 * @param tbTotal
	 * @param tbTotalLetra
	 * @param tbDescuento
	 * @param tbDescPorcentaje
	 * @param tbDescuentoTotal
	 */
	public void calculaTotales(Listbox listBoxDetalle, Textbox tbIva,
			Textbox tbSubtotal, Textbox tbTotal, Textbox tbTotalLetra,
			Textbox tbDescuento, Textbox tbDescPorcentaje, Textbox tbDescuentoTotal, Double IVA) {


		String[] iva;
		List <Listitem> objList = new ArrayList<Listitem>();
		List <Listitem> items = new ArrayList<Listitem>();
		List <Listcell> cells = new ArrayList<Listcell>();
		List <Object> objects = new ArrayList<Object>();
		NumberFormat formatoNumero = NumberFormat.getInstance();
		formatoNumero.setMinimumFractionDigits(2);
		formatoNumero.setMaximumFractionDigits(2);
		BigDecimal descTotal = new BigDecimal(0);
		//TODO poner iva
		BigDecimal porcIva = new BigDecimal(IVA);

		boolean desglosaIva = true;
		try {
			//Se obtiene los listitems del listbox
			objList = listBoxDetalle.getChildren();
			for (Object o : objList){
				if (o instanceof Listitem)
					items.add((Listitem)o);
			}
			BigDecimal subtotal=new BigDecimal(0);
			BigDecimal ivaTotal=new BigDecimal(0);
			//Se recorren los items
			for (Listitem listItem: items){
				int cantidad = 0;
				BigDecimal importe = new BigDecimal(0);
				BigDecimal unitario = new BigDecimal(0);
				//Se obtienen los listcell del listitem
				cells = listItem.getChildren();
				//Se recorren los cells
				for (Listcell cell : cells){

					//Se obtiene los objetos del cell
					if(cell.getId().indexOf("cantidad_") > -1){
						objects = cell.getChildren();
						for (Object obj : objects){
							if (obj instanceof Intbox) {
								Intbox a  = (Intbox) obj;
								if(a.getValue() != null && !a.getValue().equals("")) {
									cantidad = a.getValue();
								}
								break;
							}
						}
						continue;
					}

					if(cell.getId().indexOf("unitario_") > -1){
						unitario = new BigDecimal(cell.getLabel());
						continue;
					}

					if(cell.getId().indexOf("descuento_") > -1){
						for(Object intbox : cell.getChildren())
						{
							if(intbox instanceof Intbox)
							{
								Intbox ib = (Intbox)intbox;
								if(ib.getValue()!=null && ib.getValue()!=0 && ib.getValue()<=100)
									unitario = unitario.subtract(unitario.multiply(new BigDecimal(ib.getValue()/100.0)));
								break;
							}
						}
						continue;
					}

					if(cell.getId().indexOf("importe_") > -1){
						importe = unitario.multiply(new BigDecimal(cantidad)) ;
						cell.setLabel("" +formatoNumero.format(importe));
						subtotal = subtotal.add(importe);
						continue;
					}

					if(cell.getId().indexOf("iva_") > -1){
						iva = cell.getLabel().toString().split(",");
						if(iva[0].equals("1"))
						{
							if(iva[1].equals("null") || new Double(iva[1]).doubleValue()==0)
								ivaTotal = ivaTotal.add(importe.multiply(porcIva.divide(new BigDecimal(100))));
							else
								ivaTotal = ivaTotal.add(importe.multiply((new BigDecimal(iva[1])).divide(new BigDecimal(100))));
						}
						continue;
					}
				}

			}

			tbSubtotal.setValue(""+formatoNumero.format(subtotal));
			if(tbDescuento.getValue()!=null && !tbDescuento.getValue().equals(""))
			{
				tbDescPorcentaje.setValue(formatoNumero.format(getDescuentoTotal(tbDescuento.getValue()))+"");
				descTotal = subtotal.multiply(new BigDecimal(tbDescPorcentaje.getValue()).divide(new BigDecimal(100)));
				tbDescuentoTotal.setValue(""+formatoNumero.format(descTotal));
				ivaTotal = ivaTotal.subtract(new BigDecimal(getDescuentoTotal(tbDescuento.getValue())).multiply(ivaTotal.divide(new BigDecimal(100))));
			}
			else
			{
				tbDescPorcentaje.setValue("");
				tbDescuentoTotal.setValue("");
			}

			tbIva.setValue(""+formatoNumero.format(ivaTotal));

			BigDecimal total =new BigDecimal(0);
			if(desglosaIva)
				total= subtotal.add(ivaTotal).subtract(descTotal);
			else
				total= subtotal.subtract(descTotal);
			tbTotal.setValue(""+formatoNumero.format(total) );
//			tbTotalLetra.setValue(convertirALetra(tbTotal.getValue().replace(",", "")));



		} catch (Exception e) {
			e.printStackTrace();
			logger.error("" + e.getMessage());
		}

	}
	/**
	 * Metodo para calcular el iva, subtotal, total, total letra, descuento y el pocentaje de descuento
	 * @param listBoxDetalle
	 * @param tbIva
	 * @param tbSubtotal
	 * @param tbTotal
	 * @param tbTotalLetra
	 * @param tbDescuento
	 * @param tbDescPorcentaje
	 * @param tbDescuentoTotal
	 */
	public void calculaTotalesFacturaProveedor(Listbox listBoxDetalle, Textbox tbIva,
			Textbox tbSubtotal, Textbox tbTotal, Textbox tbTotalLetra,
			Textbox tbDescuento, Textbox tbDescPorcentaje, Textbox tbDescuentoTotal, Double IVA) {


		String[] iva;
		List <Listitem> objList = new ArrayList<Listitem>();
		List <Listitem> items = new ArrayList<Listitem>();
		List <Listcell> cells = new ArrayList<Listcell>();
		List <Object> objects = new ArrayList<Object>();
		NumberFormat formatoNumero = NumberFormat.getInstance();
		formatoNumero.setMinimumFractionDigits(2);
		formatoNumero.setMaximumFractionDigits(2);
		BigDecimal descTotal = new BigDecimal(0);
		//TODO poner iva
		BigDecimal porcIva;
		porcIva=new BigDecimal(IVA);
		boolean desglosaIva = true;
		try {
			//Se obtiene los listitems del listbox
			objList = listBoxDetalle.getChildren();
			for (Object o : objList){
				if (o instanceof Listitem)
					items.add((Listitem)o);
			}
			BigDecimal subtotal=new BigDecimal(0);
			BigDecimal ivaTotal=new BigDecimal(0);
			//Se recorren los items
			for (Listitem listItem: items){
				int cantidad = 0;
				BigDecimal importe = new BigDecimal(0);
				BigDecimal unitario = new BigDecimal(0);
				//Se obtienen los listcell del listitem
				cells = listItem.getChildren();
				//Se recorren los cells
				for (Listcell cell : cells){

					//Se obtiene los objetos del cell
					if(cell.getId().indexOf("cantidad_") > -1){
						objects = cell.getChildren();
						for (Object obj : objects){
							if (obj instanceof Intbox) {
								Intbox a  = (Intbox) obj;
								if(a.getValue() != null && !a.getValue().equals("")) {
									cantidad = a.getValue();
								}
								break;
							}
						}
						continue;
					}

					if(cell.getId().indexOf("unitario_") > -1){
						objects = cell.getChildren();
						for (Object obj : objects){
							if (obj instanceof Doublebox) {
								Doublebox a  = (Doublebox) obj;
								if(a.getValue() != null) {
									unitario = new BigDecimal(a.getValue());
								}
								break;
							}
						}
						continue;
					}

					if(cell.getId().indexOf("descuento_") > -1){
						for(Object intbox : cell.getChildren())
						{
							if(intbox instanceof Intbox)
							{
								Intbox ib = (Intbox)intbox;
								if(ib.getValue()!=null && ib.getValue()!=0 && ib.getValue()<=100)
									unitario = unitario.subtract(unitario.multiply(new BigDecimal(ib.getValue()/100.0)));
								break;
							}
						}
						continue;
					}

					if(cell.getId().indexOf("importe_") > -1){
						importe = unitario.multiply(new BigDecimal(cantidad)) ;
						cell.setLabel("" +formatoNumero.format(importe));
						subtotal = subtotal.add(importe);
						continue;
					}

					if(cell.getId().indexOf("iva_") > -1){
						iva = cell.getLabel().toString().split(",");
						if(iva[0].equals("1"))
						{
							if(iva[1].equals("null") || new Double(iva[1]).doubleValue()==0)
								ivaTotal = ivaTotal.add(importe.multiply(porcIva.divide(new BigDecimal(100))));
							else
								ivaTotal = ivaTotal.add(importe.multiply((new BigDecimal(iva[1])).divide(new BigDecimal(100))));
						}
						continue;
					}
				}

			}

			tbSubtotal.setValue(""+formatoNumero.format(subtotal));
			if(tbDescuento.getValue()!=null && !tbDescuento.getValue().equals(""))
			{
				tbDescPorcentaje.setValue(formatoNumero.format(getDescuentoTotal(tbDescuento.getValue()))+"");
				descTotal = subtotal.multiply(new BigDecimal(tbDescPorcentaje.getValue()).divide(new BigDecimal(100)));
				tbDescuentoTotal.setValue(""+formatoNumero.format(descTotal));
				ivaTotal = ivaTotal.subtract(new BigDecimal(tbDescPorcentaje.getValue()).multiply(ivaTotal.divide(new BigDecimal(100))));
			}
			else
			{
				tbDescPorcentaje.setValue("");
				tbDescuentoTotal.setValue("");
			}

			tbIva.setValue(""+formatoNumero.format(ivaTotal));

			BigDecimal total =new BigDecimal(0);
			if(desglosaIva)
				total= subtotal.add(ivaTotal).subtract(descTotal);
			else
				total= subtotal.subtract(descTotal);
			tbTotal.setValue(""+formatoNumero.format(total) );
//			tbTotalLetra.setValue(convertirALetra());



		} catch (Exception e) {
			e.printStackTrace();
			logger.error("" + e.getMessage());
		}

	}


	/**
	 * obtiene un objeto por id
	 */
	public Object getBusinessObjectPorId(Integer id, Class clazz, String campoId, Session s) {
		Object Object = null;

		try {
			String className = clazz.getName();

			Query query = s.createQuery("FROM " + className + " c WHERE c."+ campoId +" = :id");
			query.setParameter("id", id);
			Iterator iterator = query.list().iterator();
			if (iterator.hasNext()) {
				Object = (Object) iterator.next();

			}
		} catch(Throwable e) {
			System.err.println("ERROR--->>> "+e);
		} finally {

		}
		return Object;

	}	// getBusinessObjectPorId


	/**
	 * Valida que existe el usuario par�metro con el password par�metro regresa 1 si el usuario no existe,
	 * 2 si no tiene el permiso para autorizar y 0 si existe y si tiene el permiso
	 * @param usuario
	 * @param pass
	 * @return
	 */
	public Integer validaUsuario (String usuario, String pass, Integer IdCorporativo){
		Integer retorno = 0;
		Session s = null;
		Query q = null;
//		TbUsuario user = null;
//		TbRol userRol = null;
//		try {
//
//			s = HibernateUtil.currentSession();
//			user = (TbUsuario) s.createQuery("from TbSecUser u where u.chlogin='" + usuario + "' and u.chpwd='" + sifrar(pass) + "'").uniqueResult();
//
//
//			if (user == null) {
//				//El usuario no existe
//				retorno = 1;
//			}
////				else {
////				//El usuario si existe. Se verifica que tenga permisos para autorizar
//////				userRol = (TbRol) s.createQuery("from TbSecUserrole ur  " +
//////						" ur.tbSecUser.nukidUsuario=" + user.getIdUsuario() + "" +
//////						" and ur.tbSecRole.nuautoriza=1").uniqueResult();
//////				if (userRol == null)
////					//El usuario no tiene permisos
////					retorno = 2;
////
////			}
//
//
//		} catch (Exception e) {
//			logger.error("" + e.getMessage());
//			e.printStackTrace();
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}


		return retorno;
	}

	/**
	 * Obtiene el usuario que autoriza
	 * @param usuario
	 * @param pass
	 * @return
	 */
//	public TbUsuario usuarioAutoriza (String usuario, String pass){
//		TbUsuario usuarioAutoriza = new TbUsuario();
//		Session s = null;
//
//		try {
//
//			s = HibernateUtil.currentSession();
//			usuarioAutoriza = (TbUsuario) s.createQuery("from TbSecUser u where u.chlogin='" + usuario + "' and u.chpwd='" + sifrar(pass) + "'").uniqueResult();
//
//		} catch (Exception e) {
//			logger.error("" + e.getMessage());
//			e.printStackTrace();
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//
//		return usuarioAutoriza;
//	}
	/**
	 * Encripta el password con SHA-1
	 * @param p_stPwd
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String sifrar(String p_stPwd) throws UnsupportedEncodingException
	{
		String l_stPassword = "";
		try
		{

			MessageDigest sha = null;

            //SHA-1 (Secure Hash Algorithm 1)
            sha = sha.getInstance("SHA-1");

            //Convert the plaintext password (eg, "hello") into a byte-representation using UTF-8 encoding format.
            //Apply this array to the message digest object created earlier
            sha.update(p_stPwd.getBytes("UTF-8"));

            //Create a String representation of the byte array representing the digested password value
            byte raw[] = sha.digest();

            //Return the String representation of the newly generated hash back to our registration servlet
            //so that it can be stored in the database.
            l_stPassword = (new BASE64Encoder()).encode(raw);



		}
	   catch( NoSuchAlgorithmException ex){
			logger.error("Operacion fallo: " + ex.getMessage());
			return "-1";
		}

	   return l_stPassword;
	}

	/**
	 * Obtiene los Objetos de las facturas por id que estan pagadas
	 * @param p_stIdsFacturas
	 * @return la lista con los objetos factura
	 */
//	public List<TbFactura> getDatosFacturas(String p_stIdsFacturas,Session s) {
//		List l_lisFact= new ArrayList<TbFactura>();
//
//		Query l_obQry = null;
//
//		try
//		{
//			logger.debug("<<getDatosFacturas"+p_stIdsFacturas);
//			l_obQry = s.createQuery("from TbFactura f where f.chstatus in ('1','2') " +
//					"and f.chfolio is null " +
//					"and f.nukidFactura in ("+p_stIdsFacturas+")");
//
//			List<TbFactura> l_obiter=l_obQry.list();
//
//			for (TbFactura l_obtbFac: l_obiter) {
//				l_obtbFac.getChfolio();
//				l_obtbFac.setTbCliente((TbCliente)getBusinessObjectPorId(l_obtbFac.getTbCliente().getNukidCliente(),TbCliente.class,"nukidCliente", s));
//				l_obtbFac.getTbCliente().setTbPersona((TbPersona)getBusinessObjectPorId(l_obtbFac.getTbCliente().getTbPersona().getNukidpersona(),TbPersona.class,"nukidpersona",s));
//				l_obtbFac.getTbCliente().getTbPersona().setTbDireccion((TbDireccion)getBusinessObjectPorId(l_obtbFac.getTbCliente().getTbPersona().getTbDireccion().getNukiddireccion(),TbDireccion.class,"nukiddireccion",s));
//				l_obtbFac.getTbCliente().getTbPersona().getTbDireccion().setTbEntidadFederativa((TbEntidadFederativa)getBusinessObjectPorId(l_obtbFac.getTbCliente().getTbPersona().getTbDireccion().getTbEntidadFederativa().getNukidentidad(), TbEntidadFederativa.class,"nukidentidad",s));
//				l_obtbFac.setTbCorporativo((TbCorporativo)getBusinessObjectPorId(l_obtbFac.getTbCorporativo().getNukidCorporativo(), TbCorporativo.class,"nukidCorporativo",s));
//				l_obtbFac.getTbCorporativo().setTbDireccion((TbDireccion)getBusinessObjectPorId(l_obtbFac.getTbCorporativo().getTbDireccion().getNukiddireccion(),TbDireccion.class,"nukiddireccion",s));
//				l_obtbFac.getTbCorporativo().getTbDireccion().setTbEntidadFederativa((TbEntidadFederativa)getBusinessObjectPorId(l_obtbFac.getTbCorporativo().getTbDireccion().getTbEntidadFederativa().getNukidentidad(), TbEntidadFederativa.class,"nukidentidad",s));
//				l_obtbFac.setTbTienda((TbTienda)getBusinessObjectPorId(l_obtbFac.getTbTienda().getNukidTienda(), TbTienda.class,"nukidTienda",s));
//				//Iterator<TbCtoFactura> l_iter=;
//				for (Iterator iterator = l_obtbFac.getTbCtoFacturas().iterator(); iterator
//						.hasNext();) {
//					TbCtoFactura tbCtoFactura = (TbCtoFactura) iterator.next();
//
//					tbCtoFactura.getChdescripcion();
//					tbCtoFactura.setTbProducto((TbProducto)getBusinessObjectPorId(tbCtoFactura.getTbProducto().getNukidProducto(), TbProducto.class, "nukidProducto", s));
//					tbCtoFactura.getTbProducto().getChdescripcion();
//
//				}
//
//				l_lisFact.add(l_obtbFac);
//			}
//		}
//		catch (HibernateException e) {
//			System.err.println("ERROR--->>> "+e);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally
//		{
//
//		}
//		logger.debug("<<l_lisFact"+l_lisFact.size());
//		return l_lisFact;
//	}
	/**
	 * Crea un archivo
	 * 
	 * @param p_stNombreArchivo
	 *            Nombre del archivo a crear
	 * @param p_stDatos
	 *            Datos de el archivo
	 * @param p_boAgregar
	 *            Indica si se agrega la informacion al archivo o se crea uno
	 *            nuevo
	 * @throws IOException
	 *             Errores en el flujo I/O
	 */
	public void creaArchivo(String p_stRuta,
			String p_stNombreArchivo,
			String p_stDatos,
			boolean p_boAgregar) throws IOException
	{

		File l_farchivoDestino = new File(p_stRuta + File.separatorChar + p_stNombreArchivo);
		
		out = new BufferedWriter(new FileWriter(l_farchivoDestino.getAbsolutePath(), p_boAgregar));
		out.write(p_stDatos);
		p_stDatos = null;
		p_stNombreArchivo = null;
		out.close();
        
	}
	
   
    
    public void headerLog(String p_stRuta,
			String p_stNombreArchivoLog,String p_stNombreArchivo,String tienda ) throws IOException {
        
        //crea archivo log inicio
        creaArchivo(p_stRuta,p_stNombreArchivoLog, 
            "*********************************************************" + (i_enter ) +
            "*************      Inicio de carga           ************" + (i_enter ) +
            "************* Tienda: "+tienda            +" ************" + (i_enter ) +
            "*********************************************************" + (i_enter ) +
                    "Se inicio carga para el archivo: " + p_stNombreArchivo+ (i_enter )+
                    "Fecha Inicio:"+ formato.format(new Date()) + (i_enter ) +
            "************************* Detalle ***********************" + (i_enter )                        
                    , true);
    }

    /**
     * @param p_time tiempo de proceso
     * @param p_TotReg total registro procesados
     * @param p_TotErrP total errores en tabla principal
     * @param p_TotErrS total errores en tabla secundaria
     * @throws IOException
     */
    public void footerLog(String p_stRuta,String p_stNombreArchivoLog,
			String p_stNombreArchivo,String p_TotReg, 
        String p_TotErr, String p_TotRegProc) throws IOException {
        
    
        
        //crea archivo log final
        creaArchivo(p_stRuta,p_stNombreArchivoLog, 
            "*********************************************" + (i_enter ) +
            "************* Resumen de carga **************" + (i_enter ) +
            "*********************************************" + (i_enter ) +
                    "Fin de carga del archivo: " + p_stNombreArchivo+ (i_enter )+
                    "Fecha Fin:"+ formato.format(new Date()) + (i_enter ) +
                    "Registros leidos: " + p_TotReg + (i_enter ) +
                    "Registros procesados: " + p_TotRegProc + (i_enter ) +
                    "Registros errores: " + p_TotErr + (i_enter ), true);
    }
	
	/**
	 * Metodo que regresa la cantidad parametro en letra
	 * @param word
	 * @return
	 */
	public String convertirALetraDlls(String word)
	{
		String [] cantidad = {"","MIL","MILLONES","MIL"};
		int itMin = 0;
		int itMax = 3;
		String stTmp= " ";
		String stTotal = "";
		String stResiduo = "00";

		int longitud = word.length();

		int itTmp = word.indexOf(".");

		if (itTmp != -1)
		{
			stResiduo = word.substring(itTmp,longitud);
			if (stResiduo.length() == 1 )
				stResiduo = "00";
			else if (stResiduo.length() == 2 )
				stResiduo = stResiduo.substring(1,2) + "0";
			else if (stResiduo.length() > 2 )
				stResiduo = stResiduo.substring(1,3);

			word = word.substring(0,itTmp);
		}

		longitud= word.length() % 3;

		if (longitud == 1)
			word = "00"+ word;

		if (longitud == 2)
			word = "0"+ word;

		longitud = word.length();

		if ( (longitud == 9) && (word.charAt(longitud-7) == '1')
		                     && (word.charAt(longitud-8) == '0')
		                     && (word.charAt(longitud-9) == '0') )
			cantidad[2]="MILLON";

		longitud = word.length() / 3;

		if (longitud > 1)
		{
			itMax = longitud * 3;
			itMin = itMax -3;
		}

		for (int i = 0; i < longitud; i++)
		{
			stTmp = word.substring(itMin,itMax);

			if(!stTmp.equals("000"))
				stTotal = operacion(stTmp) + cantidad[i] + " " + stTotal;

			if ( (i+1) < longitud )
			{
				itMin -= 3;
				itMax -= 3;
			}
		}

		//return stTotal.trim() + " " + stDivisa + " " + stResiduo + "/100 " + stMn;
		return stTotal.trim() + " DOLARES " + stResiduo + "/100 USD" ;

	}//metodo
	/**
	 * Metodo que regresa la cantidad parametro en letra
	 * @param word
	 * @return
	 */
	public String convertirALetraEuros(String word)
	{
		String [] cantidad = {"","MIL","MILLONES","MIL"};
		int itMin = 0;
		int itMax = 3;
		String stTmp= " ";
		String stTotal = "";
		String stResiduo = "00";

		int longitud = word.length();

		int itTmp = word.indexOf(".");

		if (itTmp != -1)
		{
			stResiduo = word.substring(itTmp,longitud);
			if (stResiduo.length() == 1 )
				stResiduo = "00";
			else if (stResiduo.length() == 2 )
				stResiduo = stResiduo.substring(1,2) + "0";
			else if (stResiduo.length() > 2 )
				stResiduo = stResiduo.substring(1,3);

			word = word.substring(0,itTmp);
		}

		longitud= word.length() % 3;

		if (longitud == 1)
			word = "00"+ word;

		if (longitud == 2)
			word = "0"+ word;

		longitud = word.length();

		if ( (longitud == 9) && (word.charAt(longitud-7) == '1')
		                     && (word.charAt(longitud-8) == '0')
		                     && (word.charAt(longitud-9) == '0') )
			cantidad[2]="MILLON";

		longitud = word.length() / 3;

		if (longitud > 1)
		{
			itMax = longitud * 3;
			itMin = itMax -3;
		}

		for (int i = 0; i < longitud; i++)
		{
			stTmp = word.substring(itMin,itMax);

			if(!stTmp.equals("000"))
				stTotal = operacion(stTmp) + cantidad[i] + " " + stTotal;

			if ( (i+1) < longitud )
			{
				itMin -= 3;
				itMax -= 3;
			}
		}

		//return stTotal.trim() + " " + stDivisa + " " + stResiduo + "/100 " + stMn;
		return stTotal.trim() + " EUR " + stResiduo + "/100 EURO" ;

	}//metodo
	
	/**
	 * Metodo que regresa la cantidad parametro en letra
	 * @param word
	 * @return
	 */
	public String convertirALetraDllsCana(String word)
	{
		String [] cantidad = {"","MIL","MILLONES","MIL"};
		int itMin = 0;
		int itMax = 3;
		String stTmp= " ";
		String stTotal = "";
		String stResiduo = "00";

		int longitud = word.length();

		int itTmp = word.indexOf(".");

		if (itTmp != -1)
		{
			stResiduo = word.substring(itTmp,longitud);
			if (stResiduo.length() == 1 )
				stResiduo = "00";
			else if (stResiduo.length() == 2 )
				stResiduo = stResiduo.substring(1,2) + "0";
			else if (stResiduo.length() > 2 )
				stResiduo = stResiduo.substring(1,3);

			word = word.substring(0,itTmp);
		}

		longitud= word.length() % 3;

		if (longitud == 1)
			word = "00"+ word;

		if (longitud == 2)
			word = "0"+ word;

		longitud = word.length();

		if ( (longitud == 9) && (word.charAt(longitud-7) == '1')
		                     && (word.charAt(longitud-8) == '0')
		                     && (word.charAt(longitud-9) == '0') )
			cantidad[2]="MILLON";

		longitud = word.length() / 3;

		if (longitud > 1)
		{
			itMax = longitud * 3;
			itMin = itMax -3;
		}

		for (int i = 0; i < longitud; i++)
		{
			stTmp = word.substring(itMin,itMax);

			if(!stTmp.equals("000"))
				stTotal = operacion(stTmp) + cantidad[i] + " " + stTotal;

			if ( (i+1) < longitud )
			{
				itMin -= 3;
				itMax -= 3;
			}
		}

		//return stTotal.trim() + " " + stDivisa + " " + stResiduo + "/100 " + stMn;
		return stTotal.trim() + " CANADIAN DOLLAR " + stResiduo + "/100 CAD" ;

	}//metodo
	
	
//	public List<TbProductoImpuesto > getImpuestosProducto (Integer idProducto) {
//		List<TbProductoImpuesto> response = new ArrayList<TbProductoImpuesto>();
//		Session s = null;
//		try {
//			s = HibernateUtil.currentSession();
//			response = s.createQuery("from TbProductoImpuesto pp where pp.tbProducto.nukidProducto =" + idProducto).list();
//			for (TbProductoImpuesto prod :  response){
//				prod.getTbProducto().getChkclaveProducto();
//				prod.getTbImpuestosSat().getCveimpuesto();
//			}
//		} catch(Exception e) {
//			logger.info("" + e.getMessage());
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		return response;
//	}
	
	
//	public List<TbProductoImpuesto > getImpuestosProducto (Integer idProducto, Session s) {
//		List<TbProductoImpuesto> response = new ArrayList<TbProductoImpuesto>();
//		try {
//			s = HibernateUtil.currentSession();
//			response = s.createQuery("from TbProductoImpuesto pp where pp.tbProducto.nukidProducto =" + idProducto).list();
//			for (TbProductoImpuesto prod :  response){
//				prod.getTbProducto().getNukidProducto();
//				prod.getTbImpuestosSat().getNukidimpuestosat();
//			}
//		} catch(Exception e) {
//			logger.info("" + e.getMessage());
//		} 
//		return response;
//	}
	
	
}	// end of file
