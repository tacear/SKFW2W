package mx.com.hunkabann.skf.backend.util;

import org.apache.log4j.Logger;

/**
 * Clase que contiene los metodos usados para llenar combos
 * @author
 *
 */
public class LlenaCombos {

	private transient static final Logger logger = Logger.getLogger(LlenaCombos.class);

	/**
	 * Metodo que obtiene todos los corporativos activos
	 * @return Lista de nombres de corporativos activos
	 */
//	public List<ComboFactory> getCorporativos()
//	{
//		Session s = null;
//		TbCorporativo corporativo = null;
//		List<ComboFactory> corporativos = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbCorporativo c where c.nuactivo=1 order by c.chrazonSocial").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					corporativo = (TbCorporativo)exQ.get(i);
//					corporativos.add(new ComboFactory(corporativo.getNukidCorporativo(), corporativo.getChrazonSocial()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return corporativos;
//
//	}

	/**
	 * Metodo que obtiene todas las entidades federativas dadas de alta
	 * @return Lista de nombres de entidades federativas
	 */
//	public List<ComboFactory> getEntidadFederativa()
//	{
//		Session s = null;
//		Query q = null;
//		TbEntidadFederativa eF;
//		List<ComboFactory> entidad = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbEntidadFederativa e order by e.chnombre");
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					eF = (TbEntidadFederativa)exQ.get(i);
//					entidad.add(new ComboFactory(eF.getNukidentidad(), eF.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return entidad;
//
//	}

	/**
	 * Metodo que obtiene tipo de clientes activos
	 * @param p_idCorporativo
	 * @return Lista de nombres de tipo de clientes
	 */
//	public List<ComboFactory> getTipoCliente(Integer p_idCorporativo)
//	{
//		Session s = null;
//		Query q = null;
//		TbTipoCliente tc;
//		List<ComboFactory> tipoCliente = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbTipoCliente t where t.nuactivo=1  " +
//					" and t.tbCorporativo.nukidCorporativo= "+p_idCorporativo+
//					" order by t.chnombre");
//
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					tc = (TbTipoCliente)exQ.get(i);
//					tipoCliente.add(new ComboFactory(tc.getNukidTipoCliente(), tc.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return tipoCliente;
//
//	}

	/**
	 * Metodo que obtiene grupos de clientes activos
	 * @param tbCliente
	 * @return Lista de nombres de grupos de clientes
	 */
//	public List<ComboFactory> getGrupoCliente(Integer p_idCorporativo)
//	{
//		Session s = null;
//		Query q = null;
//		TbGrupoCliente gc;
//		List<ComboFactory> grupoCliente = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbGrupoCliente g where g.nuactivo=1 " +
//					" and g.tbCorporativo.nukidCorporativo= "+p_idCorporativo+
//					"order by g.chnombre");
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					gc = (TbGrupoCliente)exQ.get(i);
//					grupoCliente.add(new ComboFactory(gc.getNukidGrupoCliente(), gc.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return grupoCliente;
//
//	}

	/**
	 * Metodo que obtiene grupos de clientes activos
	 * @return Lista de nombres de grupos de clientes
	 */
//	public List<ComboFactory> getNivelPrecio(Integer p_idCorporativo)
//	{
//		Session s = null;
//		Query q = null;
//		TbPrecio p;
//		List<ComboFactory> nivelPrecio = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbPrecio p where p.nuactivo=1" +
//					" and p.tbCorporativo.nukidCorporativo= "+p_idCorporativo+
//					" order by p.chnombre");
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					p = (TbPrecio)exQ.get(i);
//					nivelPrecio.add(new ComboFactory(p.getNukidPrecio(), p.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return nivelPrecio;
//
//	}


	/**
	 * Medodo que obtiene todos los centros de distribucion del corporativo indicado
	 * y que estan activos
	 * @param id_corporativo
	 * @return
	 */
//	public List<ComboFactory> getCentrosDistribucion(Integer id_corporativo)	{
//		Session s = null;
//		TbAlmacen centro = null;
//		List<ComboFactory> centros = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbAlmacen a where a.nuactivo=1 and a.tbCorporativo.nukidCorporativo= " +id_corporativo + " order by a.chnombre").list() ;
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					centro = (TbAlmacen)exQ.get(i);
//					centros.add(new ComboFactory(centro.getNukidAlmacen(), centro.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return centros;
//
//	}


	/**
	 * Metodo que obtiene las tiendas del centro de distribuci�n parametros
	 * @param id_centroDist
	 * @return
	 */
//	public List<ComboFactory> getTiendas(Integer id_centroDist)	{
//		Session s = null;
//		TbTienda tienda = null;
//		List<ComboFactory> tiendas = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbTienda a where a.nuactivo=1 and a.nukidAlmacen= " +id_centroDist + "  order by a.chnombre").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					tienda = (TbTienda)exQ.get(i);
//					tiendas.add(new ComboFactory(tienda.getNukidTienda(), tienda.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return tiendas;
//
//	}


	/**
	 * Obtiene todas la tiendas de un corporativo
	 * @param p_inCorporativo
	 * @return
	 */
//	public List<ComboFactory> getTiendasCorp(Integer p_inCorporativo)	{
//		logger.info("p_inCorporativo: " + p_inCorporativo);
//		Session l_obSession = null;
//		TbTienda l_obTienda = null;
//		List<ComboFactory> tiendas = new ArrayList<ComboFactory>();
//		List l_lstTiendas = null;
//		String l_stQry = "";
//
//		try
//		{
//			l_stQry = " from TbTienda a where a.nuactivo = 1 " +
//					" and a.tbCorporativo.nukidCorporativo = " + p_inCorporativo + "  order by a.chnombre";
//
//			l_obSession = HibernateUtil.currentSession();
//			l_lstTiendas = l_obSession.createQuery(l_stQry).list();
//
//			if(l_lstTiendas.size()>0)
//			{
//				for(int i=0;i<l_lstTiendas.size();i++)
//				{
//					l_obTienda = (TbTienda)l_lstTiendas.get(i);
//					tiendas.add(new ComboFactory(l_obTienda.getNukidTienda(), l_obTienda.getChnombre()));
//				}
//			}
//
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(l_obSession!=null)
//				HibernateUtil.closeSession();
//
//			//libera recursos
//			l_lstTiendas = null;
//			l_obTienda = null;
//		}
//
//		return tiendas;
//
//	}	//getTiendas



//	public List<ComboFactory> getPerfil(Integer idPuesto)
//	{
//		Session s = null;
//		TbPerfil puesto = null;
//		List<ComboFactory> puestos = new ArrayList<ComboFactory>();
//		try
//		{
//			//TODO validar los puestos del corporativo
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbPerfil p where p.status=1 and p.idPerfil="+idPuesto + " order by p.chnombre").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					puesto = (TbPerfil)exQ.get(i);
//					puestos.add(new ComboFactory(puesto.getIdPerfil(), puesto.getChNombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return puestos;
//
//	}
	

	/**
	 * Obtiene la lista de los usuarios del corporativo parametro
	 * @param idCorporativo
	 * @return
	 */
//	public List<ComboFactory> getUsuarios(Integer idCorporativo)	{
//		Session s = null;
//		TbUsuario usuario = null;
//		List<ComboFactory> usuarios = new ArrayList<ComboFactory>();
//		General gen =  new General();
//		TbPersona persona = null;
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbSecUser p where p.nustatus=1 and p.tbCorporativo.nukidCorporativo="+idCorporativo+" order by p.tbPersona.chnombre").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					usuario = (TbUsuario)exQ.get(i);
//					persona = (TbPersona) gen.getBusinessObjectPorId(usuario.getTbPersona().getNukidpersona(), TbPersona.class, "nukidpersona");
//					usuarios.add(new ComboFactory(usuario.getNukidUsuario(), persona.getChnombre() + " " + persona.getChappaterno() + " " + persona.getChapmaterno()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return usuarios;
//
//	}

	/**
	 * Obtiene los usuarios del corporativo parametro y que no han sido asignados al personal
	 * @param idCorporativo
	 * @return
	 */
//	public List<ComboFactory> getUsuariosPersonal(Integer idCorporativo)	{
//		Session s = null;
//		TbUsuario usuario = null;
//		List<ComboFactory> usuarios = new ArrayList<ComboFactory>();
//		General gen =  new General();
//		TbPersona persona = null;
//		List exQ = new ArrayList();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			exQ = s.createQuery("from TbSecUser u where u.nustatus=1 and u.tbCorporativo.nukidCorporativo="+idCorporativo + " order by u.tbPersona.chnombre").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					usuario = (TbUsuario)exQ.get(i);
//					//Se valida si el usuario existe en TbPersonal
//					Integer existe = (Integer) s.createQuery("select count(*) from TbPersonal p where p.tbCorporativo.nukidCorporativo="+idCorporativo + " and p.tbSecUser.nukidUsuario="+usuario.getNukidUsuario()).uniqueResult();
//					if(existe == 0) {
//						persona = (TbPersona)  s.createQuery("from TbPersona p where nukidpersona="+usuario.getTbPersona().getNukidpersona()).uniqueResult();
//						usuarios.add(new ComboFactory(usuario.getNukidUsuario(), persona.getChnombre() + " " + persona.getChappaterno() + " " + persona.getChapmaterno()));
//					}
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return usuarios;
//
//	}	// getUsuariosPersonal


	/**
	 * Obtiene los tipos de forma de pago definidos en constantes
	 * @return
	 */
//	public List<ComboFactory> getTipoFormaPago()	{
//		List<ComboFactory> tipoFormaPago = new ArrayList<ComboFactory>();
//
//		tipoFormaPago.add(new ComboFactory(Constantes.IN_COBRANZA_CARGO, Constantes.ST_COBRANZA_CARGO));
//		tipoFormaPago.add(new ComboFactory(Constantes.IN_COBRANZA_ABONO, Constantes.ST_COBRANZA_ABONO));
//
//		return tipoFormaPago;
//
//	}	// getTipoFormaPago

	/**
	 * Obtiene las lineas de productos del corporativo para metro que esten activas
	 * @param idCorporativo
	 * @return
	 */
//	public List<ComboFactory> getLineasProducto(Integer idCorporativo,Integer idTienda)	{
//		Session s = null;
//		TbLineaProducto linea = null;
//		List<ComboFactory> lineas = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s
//					.createQuery(
//							"from TbLineaProducto lp where lp.nuactivo=1 and lp.tbCorporativo.nukidCorporativo="+idCorporativo+" and lp.tbTienda.nukidTienda="+idTienda+" order by lp.chdescripcionLinea").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					linea = (TbLineaProducto)exQ.get(i);
//					lineas.add(new ComboFactory(linea.getNukidLineaProducto(), linea.getChdescripcionLinea()));
//				}
//		} catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return lineas;
//
//	}

//	public List<ComboFactory> getProveedores(Integer idCorporativo)	{
//		Session s = null;
//		TbProveedor proveedor = null;
//		List<ComboFactory> proveedores = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbProveedor p where p.nuactivo=1 and p.tbCorporativo.nukidCorporativo="+idCorporativo+" order by p.chnombre").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					proveedor = (TbProveedor)exQ.get(i);
//					proveedores .add(new ComboFactory(proveedor.getNukidProveedor(), proveedor.getChnombre()));
//				}
//		} catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return proveedores;
//
//	}

//	public List<ComboFactory> getClientes(Integer idCorporativo)	{
//		Session s = null;
//		TbCliente cliente = null;
//		List<ComboFactory> clientes = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbCliente p where p.nuactivo=1 and p.tbCorporativo.nukidCorporativo="+idCorporativo+" order by p.tbPersona.chnombre, p.tbPersona.chrazonsocial").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					cliente = (TbCliente)exQ.get(i);
//					if(cliente.getTbPersona().getNutipo().intValue()==1)
//						clientes .add(new ComboFactory(cliente.getNukidCliente(), cliente.getTbPersona().getChnombre()+
//							" "+cliente.getTbPersona().getChappaterno()+" "+cliente.getTbPersona().getChapmaterno()));
//					else
//						clientes .add(new ComboFactory(cliente.getNukidCliente(), cliente.getTbPersona().getChrazonsocial()));
//
//				}
//		} catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return clientes;
//
//	}

	/**
	 * Obtiene a todos los proveedores activos del corporativo y del grupo especificados
	 * @param idCorporativo
	 * @param idGrupo
	 * @return
	 */

//	public List<ComboFactory> getProveedoresxGpo(Integer idCorporativo,Integer idGrupo)
//	{
//		Session s = null;
//		TbProveedor proveedor = null;
//		List<ComboFactory> proveedores = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbProveedor p where p.nuactivo=1  " +
//					"and p.tbGrupoProveedor.nukidGrupoProveedor="+ idGrupo +" and p.tbCorporativo.nukidCorporativo="+idCorporativo+" order by p.chnombre").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					proveedor = (TbProveedor)exQ.get(i);
//					proveedores .add(new ComboFactory(proveedor.getNukidProveedor(), proveedor.getChnombre()));
//				}
//		} catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return proveedores;
//
//	}
//
//	/**
//	 * Carga los datos del grupo proveedor en el combo
//	 * by RTC
//	 * @param idCorporativo
//	 * @return
//	 */
//	public List<ComboFactory> getGrupoProveedro(Integer idCorporativo)
//	{
//		Session s = null;
//		Query q = null;
//		TbGrupoProveedor gp;
//		List<ComboFactory> grupoProveedor = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbGrupoProveedor g where g.nuactivo=1 and g.tbCorporativo.nukidCorporativo="+idCorporativo+" order by g.chnombre");
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					gp = (TbGrupoProveedor)exQ.get(i);
//					grupoProveedor.add(new ComboFactory(gp.getNukidGrupoProveedor(), gp.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return grupoProveedor;
//
//	}
//
//	/**
//	 * Obtiene la lista del personal asignado a una tienda del corporativo parametro que esten activos
//	 * @param idCorporativo
//	 * @return Lista de nombres del personal
//	 */
//	public List<ComboFactory> getPersonalTienda(Integer idCorporativo)
//	{
//		Session s = null;
//		TbSecUser usuario = null;
//		List<ComboFactory> usuarios = new ArrayList<ComboFactory>();
//		General gen =  new General();
//		TbPersona persona = null;
//		TbPersonal personal = null;
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbPersonal p where p.tbSecUser.nustatus=1 " +
//					" and p.tbTienda.nukidTienda is not null" +
//					//" and p.tbPuesto.chnombre = 'CAJERO'" +
//					" and p.tbCorporativo.nukidCorporativo="+idCorporativo+" order by p.tbSecUser.tbPersona.chnombre").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					personal = (TbPersonal)exQ.get(i);
//					usuario = (TbSecUser)gen.getBusinessObjectPorId(personal.getTbSecUser().getNukidUsuario(),TbSecUser.class, "nukidUsuario");
//					persona = (TbPersona) gen.getBusinessObjectPorId(usuario.getTbPersona().getNukidpersona(), TbPersona.class, "nukidpersona");
//					usuarios.add(new ComboFactory(personal.getNukidPersonal(), persona.getChnombre() + " " + persona.getChappaterno() + " " + persona.getChapmaterno()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return usuarios;
//
//	}
//
//	/**
//	 * Obtiene la lista de las cajas del corporativo parametro
//	 * @param idCorporativo
//	 * @return Lista de nombres de cajas por coorporativo
//	 */
//	public List<ComboFactory> getCajas(Integer idTienda)
//	{
//		Session s = null;
//		TbCaja caja = null;
//		List<ComboFactory> cajas = new ArrayList<ComboFactory>();
//		try
//		{
//			//TODO validar los puestos del corporativo
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbCaja p where p.nuactivo=1 and p.tbTienda.nukidTienda="+idTienda + " order by p.chnombre").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					caja = (TbCaja)exQ.get(i);
//					cajas.add(new ComboFactory(caja.getNukidcaja(), caja.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return cajas;
//
//	}
//
//	/**
//	 * Medodo que obtiene todos los Grupos de Cliente
//	 *  que estan activos ordenados de forma ascendente por nombre
//	 * @param
//	 * @return Lista de nombres de grupos de clientes
//	 */
//	public List<ComboFactory> getGrupoClienteOrdenado(Integer p_inIdCorporativo)
//	{
//		Session s = null;
//		TbGrupoCliente grupo = null;
//		List<ComboFactory> grupos = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbGrupoCliente a where a.nuactivo=1 " +
//					" and a.tbCorporativo.nukidCorporativo="+p_inIdCorporativo+" order by a.chnombre").list() ;
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					grupo = (TbGrupoCliente)exQ.get(i);
//					grupos.add(new ComboFactory(grupo.getNukidGrupoCliente(), grupo.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return grupos;
//
//	}
//	/**
//	 * Medodo que obtiene todos las Formas de Pago de tipo 1 (cargo), 2 (abono)
//	 *  que estan activos ordenados de forma ascendente por nombre
//	 * @param
//	 * @return Lista de las formas de Pago
//	 */
//	public List<ComboFactory> getFormaPago(Integer p_inTipo, Integer idCorporativo)
//	{
//		Session s = null;
//		TbFormaPago formaPago = null;
//		List<ComboFactory> formaPagos = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbFormaPago a where a.tbCorporativo.nukidCorporativo=" + idCorporativo +"  and  a.nuactivo=1  and a.nutipo = "+p_inTipo+" order by a.chnombre").list() ;
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					formaPago = (TbFormaPago)exQ.get(i);
//					formaPagos.add(new ComboFactory(formaPago.getNukidFormaPago(), formaPago.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return formaPagos;
//
//	}
//	public List<ComboFactory> getFormaPago(Integer p_nutipo)
//	{
//		Session s = null;
//		TbFormaPago formaPago = null;
//		List<ComboFactory> formaPagos = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbFormaPago a where a.nuactivo=1  and a.nutipo = "+p_nutipo+" order by a.chnombre").list() ;
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					formaPago = (TbFormaPago)exQ.get(i);
//					formaPagos.add(new ComboFactory(formaPago.getNukidFormaPago(), formaPago.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return formaPagos;
//
//	}
//
//	/**
//	 * Metodo que obtiene todas las formas de pago a proveedores
//	 * @return
//	 */
//	public List<ComboFactory> getFormaPagoProveedor(Integer idCorporativo)
//	{
//		Session s = null;
//		TbCtoCtasXPagar formaPago = null;
//		List<ComboFactory> formaPagos = new ArrayList<ComboFactory>();
//		try
//		{
//
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbCtoCtasXPagar a where a.nuactivo=1  " +
//					" and a.tbCorporativo.nukidCorporativo = "+  idCorporativo +
//					" order by a.chdescripcion").list() ;
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					formaPago = (TbCtoCtasXPagar)exQ.get(i);
//					formaPagos.add(new ComboFactory(formaPago.getNukidCtoCtasXPagar(),formaPago.getChdescripcion()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return formaPagos;
//
//	}
//
//	/**
//	 * Obtiene la lista de los cajeros asignados a una caja
//	 * @param idCaja
//	 * @return Lista de nombres de los cajeros de una caja
//	 */
//	public List<ComboFactory> getCajeros(Integer idCaja)
//	{
//		Session s = null;
//		TbCajaPersonal cajaPersonal = null;
//		List<ComboFactory> cajeros= new ArrayList<ComboFactory>();
//		TbSecUser usuario = null;
//		TbPersona persona = null;
//		TbPersonal personal = null;
//		General gen =  new General();
//
//		try
//		{
//			//TODO validar los puestos del corporativo
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbCajaPersonal cp where cp.tbCaja.nukidcaja="+idCaja + " order by cp.nukidcajaPersonal").list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					cajaPersonal = (TbCajaPersonal)exQ.get(i);
//					personal = (TbPersonal) gen.getBusinessObjectPorId(cajaPersonal.getTbPersonal().getNukidPersonal(), TbPersonal.class, "nukidPersonal",s);
//					usuario = (TbSecUser)gen.getBusinessObjectPorId(personal.getTbSecUser().getNukidUsuario(),TbSecUser.class, "nukidUsuario",s);
//					persona = (TbPersona) gen.getBusinessObjectPorId(usuario.getTbPersona().getNukidpersona(), TbPersona.class, "nukidpersona",s);
//
//					cajeros.add(new ComboFactory(personal.getNukidPersonal(),persona.getChnombre() + " " + persona.getChappaterno() + " " + persona.getChapmaterno()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return cajeros;
//
//	}
//
//	/**
//	 * Obtiene la lista del personal asignado a una tienda del corporativo parametro que esten activos
//	 * @param idCorporativo
//	 * @return Lista de nombres del personal
//	 */
//	public List<ComboFactory> getPersonalCDTienda(Integer idCorporativo,Integer idCentro,Integer idTienda)
//	{
//		Session s = null;
//		TbSecUser usuario = null;
//		List<ComboFactory> usuarios = new ArrayList<ComboFactory>();
//		General gen =  new General();
//		TbPersona persona = null;
//		TbPersonal personal = null;
//		String qry="";
//		try
//		{
//			s = HibernateUtil.currentSession();
//			qry="from TbPersonal p where p.tbSecUser.nustatus=1 " +
//			" and p.tbTienda.nukidTienda is not null" +
//			" and p.tbCorporativo.nukidCorporativo="+idCorporativo;
//			//" and p.tbPuesto.chnombre = 'CAJERO'" +
//
//			if(idCentro!=null){
//				qry+=" and p.tbAlmacen.nukidAlmacen="+idCentro;
//			}
//
//			if(idTienda!=null){
//				qry+=" and p.tbTienda.nukidTienda="+idTienda;
//			}
//
//			qry+=" order by p.tbSecUser.tbPersona.chnombre";
//
//
//			List exQ = s.createQuery(qry).list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					personal = (TbPersonal)exQ.get(i);
//					usuario = (TbSecUser)gen.getBusinessObjectPorId(personal.getTbSecUser().getNukidUsuario(),TbSecUser.class, "nukidUsuario");
//					persona = (TbPersona) gen.getBusinessObjectPorId(usuario.getTbPersona().getNukidpersona(), TbPersona.class, "nukidpersona");
//					usuarios.add(new ComboFactory(personal.getNukidPersonal(), persona.getChnombre() + " " + persona.getChappaterno() + " " + persona.getChapmaterno()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return usuarios;
//
//	}
//	
//	public List<ComboFactory> getPaquetes(Integer id_corporativo)
//	{
//		Session s = null;
//		TbPaquetes centro = null;
//		List<ComboFactory> centros = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbPaquetes a where a.nuactivo=1  and a.tbCorporativo.nukidCorporativo= " +id_corporativo + " order by a.chnombre").list() ;
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					centro = (TbPaquetes)exQ.get(i);
//					centros.add(new ComboFactory(centro.getNukidPaquetes(), centro.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return centros;
//
//	}
//
//	/**
//	 * Obtiene los tipos de forma de pago definidos en constantes
//	 * @return
//	 */
//	public List<ComboFactory> getTipoCarga()
//	{
//		List<ComboFactory> tipo = new ArrayList<ComboFactory>();
//		tipo.add(new ComboFactory(1, "PRODUCTOS"));
//		tipo.add(new ComboFactory(2, "CLIENTES"));
//
//		return tipo;
//
//	}	// getTipoCarga
//	
//	
//	/**
//	 * MAMP se agrega para obtener solo los activos
//	 * @return
//	 */
//	public List<ComboFactory> getFormaPago()
//	{
//		Session s = null;
//		TbFormaPago formaPago = null;
//		List<ComboFactory> formaPagos = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbFormaPago a where a.nuactivo=1  order by a.chcodigo").list() ;
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					formaPago = (TbFormaPago)exQ.get(i);
//					formaPagos.add(new ComboFactory(formaPago.getNukidFormaPago(), formaPago.getChcodigo() + " - " + formaPago.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return formaPagos;
//
//	}
//	
//	/**
//	 * MAMP se agrega para obtener solo los activos
//	 * @return
//	 */
//	public List<ComboFactory> getMetodoPago()
//	{
//		Session s = null;
//		TbMetodoPago metodoPago = null;
//		List<ComboFactory> metodoPagos = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbMetodoPago a where a.nuactivo=1  order by a.chcodigo").list() ;
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					metodoPago = (TbMetodoPago)exQ.get(i);
//					metodoPagos.add(new ComboFactory(metodoPago.getNukidMetodoPago(), metodoPago.getChcodigo() + " - " + metodoPago.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return metodoPagos;
//
//	}
//	
//	
//	/**
//	 * Metodo que obtiene todos las regimenes fiscales
//	 * @return Lista de nombres de regimenes fiscales
//	 */
//	public List<ComboFactory> getRegimenFiscal()
//	{
//		Session s = null;
//		Query q = null;
//		TbRegimen eF;
//		List<ComboFactory> regimen = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbRegimen e where e.nuactivo = 1 order by e.clave");
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					eF = (TbRegimen)exQ.get(i);
//					regimen.add(new ComboFactory(eF.getNukidRegimen(), eF.getClave() + "-" + eF.getChregimen()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return regimen;
//
//	}
//	
//	
//	/**
//	 * Metodo que obtiene los regimenes fiscales aplicables al tipo de persona
//	 * @return Lista de nombres de regimenes fiscales
//	 */
//	public List<ComboFactory> getRegimenFiscalPersona(Integer tipo)
//	{
//		Session s = null;
//		Query q = null;
//		TbRegimen eF;
//		List<ComboFactory> regimen = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			String query = "from TbRegimen e where e.nuactivo = 1";
//			if(tipo == 1) //persona moral
//				query = query  + " and e.nupersonam = 1";
//			else if(tipo == 2) //persona fisica
//				query = query  + " and e.nupersonaf = 1";
//			else
//				query = query  + " and 1=0";
//			
//			query = query  + " order by e.clave ";
//			
//			q = s.createQuery(query);
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					eF = (TbRegimen)exQ.get(i);
//					regimen.add(new ComboFactory(eF.getNukidRegimen(), eF.getClave() + "-" + eF.getChregimen()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return regimen;
//
//	}
//	
//	
//	/**
//	 * Metodo que obtiene todos las impuestos del sat
//	 * @return Lista de nombres de impuestos
//	 */
//	public List<ComboFactory> getImpuestosSat()
//	{
//		Session s = null;
//		Query q = null;
//		TbImpuestosSat eF;
//		List<ComboFactory> impuesto = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbImpuestosSat e order by e.nombre");
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					eF = (TbImpuestosSat)exQ.get(i);
//					impuesto.add(new ComboFactory(eF.getNukidimpuestosat(), eF.getCveimpuesto() + "-" + eF.getNombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return impuesto;
//
//	}
//	
//	/**
//	 * Metodo que obtiene todos las monedas activas
//	 * @return Lista de nombres de regimenes fiscales
//	 */
//	public List<ComboFactory> getMoneda()
//	{
//		Session s = null;
//		Query q = null;
//		TbMoneda eF;
//		List<ComboFactory> moneda = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbMoneda e where e.nuactivo = 1 order by e.clave");
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					eF = (TbMoneda)exQ.get(i);
//					moneda.add(new ComboFactory(eF.getNukidmoneda(), eF.getClave() + " - " + eF.getDescripcion()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return moneda;
//
//	}
//	
//	
//
//	/**
//	 * Metodo que obtiene todos los usos de cfdi 
//	 * @return Lista de nombres de regimenes fiscales
//	 */
//	public List<ComboFactory> getUsoCfdi()
//	{
//		Session s = null;
//		Query q = null;
//		TbUsoCfdi eF;
//		List<ComboFactory> moneda = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbUsoCfdi e  order by e.clave desc");
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					eF = (TbUsoCfdi)exQ.get(i);
//					moneda.add(new ComboFactory(eF.getNukidusocfdi(), eF.getClave() + " - " + eF.getDescripcion()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return moneda;
//
//	}
//	
//	/**
//	 * Metodo que obtiene los regimenes fiscales del emisor (tienda)
//	 * @return Lista de nombres de regimenes fiscales
//	 */
//	public List<ComboFactory> getRemimenesEmisor(Integer idEmisor)
//	{
//		Session s = null;
//		Query q = null;
//		TbTiendaRegimen eF;
//		List<ComboFactory> response = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			q = s.createQuery("from TbTiendaRegimen e where e.tbTienda.nukidTienda= " + idEmisor + " order by e.tbRegimen.clave");
//			List exQ= q.list();
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++)
//				{
//					eF = (TbTiendaRegimen)exQ.get(i);
//					eF.getTbRegimen().getClave();
//					response.add(new ComboFactory(eF.getTbRegimen().getNukidRegimen(), eF.getTbRegimen().getClave() + " - " + eF.getTbRegimen().getChregimen()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return response;
//
//	}
//	
//	
//	/**
//	 * MAMP se agrega para obtener solo los activos
//	 * @return
//	 */
//	public List<ComboFactory> getRelacionSat()
//	{
//		Session s = null;
//		TbRelacionSat relacionSat = null;
//		List<ComboFactory> formaPagos = new ArrayList<ComboFactory>();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			List exQ = s.createQuery("from TbRelacionSat a where a.nuactivo=1  order by a.chcodigo").list() ;
//			if(exQ.size()>0)
//				for(int i=0;i<exQ.size();i++) {
//					relacionSat = (TbRelacionSat)exQ.get(i);
//					formaPagos.add(new ComboFactory(relacionSat.getNukidRelacionSat(), relacionSat.getChcodigo() + " - " + relacionSat.getChnombre()));
//				}
//		}
//		catch (HibernateException e) {
//			logger.error("" + e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//
//		return formaPagos;
//
//	}
}	// end of file
