package mx.com.hunkabann.skf.backend;

import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import mx.com.hunkabann.skf.frontent.util.ComboFactory;
import mx.com.hunkabann.skf.mapeo.DetalleRol;
import mx.com.hunkabann.skf.mapeo.TbCanal;
import mx.com.hunkabann.skf.mapeo.TbCatCodigoDownTime;
import mx.com.hunkabann.skf.mapeo.TbCatCodigoScrap;
import mx.com.hunkabann.skf.mapeo.TbCatStatus;
import mx.com.hunkabann.skf.mapeo.TbCatUm;
import mx.com.hunkabann.skf.mapeo.TbEmpleado;
import mx.com.hunkabann.skf.mapeo.TbMaquinaDispositivo;
import mx.com.hunkabann.skf.mapeo.TbMateriaPrima;
import mx.com.hunkabann.skf.mapeo.TbOrdenManufactura;
import mx.com.hunkabann.skf.mapeo.TbPerfil;
import mx.com.hunkabann.skf.mapeo.TbPersona;
import mx.com.hunkabann.skf.mapeo.TbPlaca;
import mx.com.hunkabann.skf.mapeo.TbPlacaOm;
import mx.com.hunkabann.skf.mapeo.TbPlanControl;
import mx.com.hunkabann.skf.mapeo.TbProdTermMatPrim;
import mx.com.hunkabann.skf.mapeo.TbProductoTerminado;
import mx.com.hunkabann.skf.mapeo.TbRol;
import mx.com.hunkabann.skf.mapeo.TbSubArea;
import mx.com.hunkabann.skf.mapeo.TbUbicacionProcesoOm;
import mx.com.hunkabann.skf.mapeo.TbUsuario;
import mx.com.hunkabann.skf.util.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.http.SimpleSession;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Messagebox;



public class UsuarioService  {
	private transient static final Logger logger = Logger.getLogger(UsuarioService.class);
	
	private int intentos = 0;
	private String userName = "";
	private String email = "";
	private String newPwd = "";
	
	protected transient Hbox hbox_Salir;
	protected transient Hbox hbox_OrdenManu;
	protected transient Hbox hbox_EntregaRecepcion;
	protected transient Hbox hbox_Operacion;
	protected transient Hbox hbox_Reporte;
	protected transient Hbox hbox_Trazabilidad;
	
//	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
//	HttpSession session = (HttpSession) s.getNativeSession();	
	
	public TbProductoTerminado getPRoductoTerm(Integer p_stUsername)
{

	Session s = null;
	TbProductoTerminado rol = new TbProductoTerminado();
	String nombre = "";
	try
	{
		s = HibernateUtil.currentSession();
		
		System.out.println();
		rol = (TbProductoTerminado) s.createQuery("from TbProductoTerminado p where p.nukIdProdTerm=" + p_stUsername ).uniqueResult();
//		nombre = (String) s.createQuery("select p.chSku from TbProductoTerminado p where p.nukIdProdTerm=" + p_stUsername ).uniqueResult();
//		
//		System.out.println("nombre ---------------->>  " + nombre);
//		if(nombre==null)
//			return "";
	}
	catch (HibernateException e) {

		logger.error(e.getMessage());
	}
	finally
	{
		if(s!=null)
			HibernateUtil.closeSession();
	}
	return rol;
}
	
	public TbMateriaPrima getMAteriaPrima(Integer p_stUsername)
	{

		Session s = null;
		TbMateriaPrima rol = new TbMateriaPrima();
		String nombre = "";
		try
		{
			s = HibernateUtil.currentSession();
			
			System.out.println();
			rol = (TbMateriaPrima) s.createQuery("from TbMateriaPrima p where p.nukIdMateriaPrima=" + p_stUsername ).uniqueResult();
//			nombre = (String) s.createQuery("select p.chSku from TbProductoTerminado p where p.nukIdProdTerm=" + p_stUsername ).uniqueResult();
//			
//			System.out.println("nombre ---------------->>  " + nombre);
//			if(nombre==null)
//				return "";
		}
		catch (HibernateException e) {

			logger.error(e.getMessage());
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();
		}
		return rol;
	}
	
	public TbCatStatus getCatStatus(Integer p_stUsername)
	{

		Session s = null;
		TbCatStatus rol = new TbCatStatus();
		try
		{
			s = HibernateUtil.currentSession();
			rol = (TbCatStatus) s.createQuery("from TbCatStatus p where p.nukIdStatus=" + p_stUsername ).uniqueResult();
		}
		catch (HibernateException e) {

			logger.error(e.getMessage());
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();
		}
		return rol;
	}
	
	public TbCatUm getCatUM(Integer p_stUsername)
	{

		Session s = null;
		TbCatUm rol = new TbCatUm();
		try
		{
			s = HibernateUtil.currentSession();
			rol = (TbCatUm) s.createQuery("from TbCatUm p where p.nukIdCatUm=" + p_stUsername ).uniqueResult();
		}
		catch (HibernateException e) {

			logger.error(e.getMessage());
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();
		}
		return rol;
	}
	
	public TbPlaca getPlaca(Integer p_stUsername)
	{

		Session s = null;
		TbPlaca rol = new TbPlaca();
		try
		{
			s = HibernateUtil.currentSession();
			rol = (TbPlaca) s.createQuery("from TbPlaca p where p.nukIdPlaca=" + p_stUsername ).uniqueResult();
		}
		catch (HibernateException e) {

			logger.error(e.getMessage());
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();
		}
		return rol;
	}
	
	public TbPlanControl getPlanControl(String p_stUsername)
	{

		Session s = null;
		TbPlanControl rol = new TbPlanControl();
		try
		{
			s = HibernateUtil.currentSession();
			rol = (TbPlanControl) s.createQuery("from TbPlanControl p where p.chNumParte=" + p_stUsername ).uniqueResult();
		}
		catch (HibernateException e) {

			logger.error(e.getMessage());
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();
		}
		return rol;
	}
	
//	public TbProdTermMatPrim getProdTermMatePrim(Integer p_stUsername)
//	{
//
//		Session s = null;
//		TbProdTermMatPrim rol = new TbProdTermMatPrim();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			rol = s.createQuery("from TbProdTermMatPrim p where p.tbProductoTerminado.nukIdProdTerm=" + p_stUsername );
//		}
//		catch (HibernateException e) {
//
//			logger.error(e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		return rol;
//	}
	
	
//	public TbProdTermMatPrim getProdTermMatePrim(Integer p_stUsername)
//	{
//		TbProdTermMatPrim l_obUser = new TbProdTermMatPrim();
//		Session l_obSession = null;
//		String l_stQry = "";
//		List l_obResult = null;
//
//		try
//		{
//			l_obSession = HibernateUtil.currentSession();
//			l_stQry = "from TbProdTermMatPrim p where p.tbProductoTerminado.nukIdProdTerm=" + p_stUsername;
//			l_obResult = l_obSession.createQuery(l_stQry).list();
//			if(l_obResult.size()>0)
//				l_obUser = (TbProdTermMatPrim)l_obResult.get(0);
//		} 
//		catch (HibernateException e) {
//			logger.error(e.getMessage());
//			return null;
//		}
//		finally{
//			if(l_obSession != null)
//				HibernateUtil.closeSession();
//		}
//		return l_obUser;
//		
//	}	//getUserByLoginname
	
	public TbRol getrol(Integer SKU) 
	{
		
		
		Session s = null;
		Statement stm = null;
		ResultSet rs = null;
		boolean valor_Registro = false;
		TbRol uso = new TbRol();
		try {
			s = HibernateUtil.currentSession();


			stm = s.connection().createStatement();

				rs = stm.executeQuery("select rol.nukIdRol, rol.chClave, rol.chNombre , rol.chDescripcion from tb_rol as rol, tb_perfil as per, Tb_Perfil_Rol as pr where per.nukIdPerfil = pr.nukIdPerfil and rol.nukIdRol = pr.nukIdRol and per.nukIdPerfil= " +SKU);
				
				
				

				while (rs.next()) {
//					System.out.println("VAlor del Qry: " + rs.getDouble(1));
					uso.setNukIdRol(rs.getInt(1));
					uso.setChClave(rs.getString(2));
					uso.setChNombre(rs.getString(3));
					uso.setChDescripcion(rs.getString(4));
//					if(uso.getNuCantidad() == 0){
//						valor_Registro = false;
//					}else{
//						valor_Registro = true;
//					}
					
				}
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			e.printStackTrace();
		} finally {
			if(s!=null)
				HibernateUtil.closeSession();
		}
		return uso;
	}
	
	public TbUsuario getUsuario(Integer SKU) 
	{
		
		
		Session s = null;
		Statement stm = null;
		ResultSet rs = null;
		boolean valor_Registro = false;
		TbUsuario uso = new TbUsuario();
		try {
			s = HibernateUtil.currentSession();


			stm = s.connection().createStatement();

				rs = stm.executeQuery("select nukIdUsuario from Tb_Usuario where nukIdPersona = " +SKU);
				
				
				

				while (rs.next()) {
//					System.out.println("VAlor del Qry: " + rs.getDouble(1));
					uso.setNukIdUsuario(rs.getInt(1));
					
//					if(uso.getNuCantidad() == 0){
//						valor_Registro = false;
//					}else{
//						valor_Registro = true;
//					}
					
				}
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			e.printStackTrace();
		} finally {
			if(s!=null)
				HibernateUtil.closeSession();
		}
		return uso;
	}

	
	public TbUsuario getUserByUsername(String p_stUsername) throws Exception
	{
		TbUsuario l_return = null;
		Session l_sesion = null;
		String l_stQry = "";
		
		HibernateUtil hibernate = new HibernateUtil();
		
		try
		{
			l_sesion = hibernate.currentSession();
			
			// por fecha
//			l_stQry = "from Usuarios u " +
//					" where u.username = :username " +
//					" and u.pwdExpire > :hoy" +
//					" and u.activo = :activo";
//			l_return = (Usuarios)l_sesion.createQuery(l_stQry)
//						.setParameter("username", p_stUsername)
//						.setTimestamp("hoy", new Date())
//						.setParameter("activo", "1")
//						.uniqueResult();
			
			// solo usuarios activos
//			l_stQry = "from Usuarios u " +
//					" where u.username = :username " +
//					" and u.activo = :activo";
//			l_return = (Usuarios)l_sesion.createQuery(l_stQry)
//				.setParameter("username", p_stUsername)
//				.setParameter("activo", "1")
//				.uniqueResult();
			
			
			l_stQry = "from TbUsuario u " +
					" where u.chUsuario = :username ";
			l_return = (TbUsuario)l_sesion.createQuery(l_stQry)
				.setParameter("username", p_stUsername)
				.uniqueResult();
			
			
		}
		catch(Exception e)
		{
			
			throw e;
		}
		finally
		{
			hibernate.closeSession();
		}
		
		return l_return;
		
	}	// getUserByUsername
//	
//	@SuppressWarnings("unchecked")
//	public TbUsuario getUserByLoginname(String p_stLogin)
//	{
//		TbUsuario l_obUser = new TbUsuario();
//		Session l_obSession = null;
//		String l_stQry = "";
//		List l_obResult = null;
//
//		try
//		{
//			l_obSession = HibernateUtil.currentSession();
//			l_stQry = "from TbUsuario s where s.username='" + p_stLogin + "' and status=1";
//			l_obResult = l_obSession.createQuery(l_stQry).list();
//			if(l_obResult.size()>0)
//				l_obUser = (TbUsuario)l_obResult.get(0);
//		} 
//		catch (HibernateException e) {
//			logger.error(e.getMessage());
//			return null;
//		}
//		finally{
//			if(l_obSession != null)
//				HibernateUtil.closeSession();
//		}
//		return l_obUser;
//		
//	}	//getUserByLoginname
//	
//	@SuppressWarnings("unchecked")
//	public TbUsuario getUserByLoginname1(String p_stLogin)
//	{
//		TbUsuario l_obUser = new TbUsuario();
//		Session l_obSession = null;
//		String l_stQry = "";
//		List l_obResult = null;
//
//		try
//		{
//			l_obSession = HibernateUtil.currentSession();
//			l_stQry = "from TbUsuario s where s.username='" + p_stLogin + "'";
//			l_obResult = l_obSession.createQuery(l_stQry).list();
//			if(l_obResult.size()>0)
//				l_obUser = (TbUsuario)l_obResult.get(0);
//		} 
//		catch (HibernateException e) {
//			logger.error(e.getMessage());
//			return null;
//		}
//		finally{
//			if(l_obSession != null)
//				HibernateUtil.closeSession();
//		}
//		return l_obUser;
//		
//	}	//getUserByLoginname
//	
////	public TbPerfilTbUsuario getPerfilUsersd(String p_stUsername) throws Exception
////	{
////		TbPerfilTbUsuario l_return = null;
////		Session l_sesion = null;
////		String l_stQry = "";
////		
////		try
////		{
////			l_sesion = HibernateUtil.currentSession();
////			l_stQry = "from TbPerfilTbUsuario s where s.tbUsuario.idUsuario=" + p_stUsername ;
////			l_return = (TbPerfilTbUsuario)l_sesion.createQuery(l_stQry)
////						.setParameter("id", p_stUsername)
////						.uniqueResult();
////			
////			
////		}
////		catch(Exception e)
////		{
////			throw e;
////		}
////		finally
////		{
////			HibernateUtil.closeSession();
////		}
////		
////		return l_return;
////		
////	}	// getUserByUsername
////	
////	public TbPerfilTbUsuario getPerfilUser(Integer p_stUsername, Integer id_corporativo)
////	{
////
////		Session s = null;
////		TbPerfilTbUsuario rol = new TbPerfilTbUsuario();
////		try
////		{
////			s = HibernateUtil.currentSession();
////			rol = (TbPerfilTbUsuario) s.createQuery("from TbPerfilTbUsuario p where p.tbUsuario.idUsuario=" + p_stUsername + " and p.tbCorporativo.idCorporativo ="+id_corporativo).uniqueResult();
////		}
////		catch (HibernateException e) {
////
////			logger.error(e.getMessage());
////		}
////		finally
////		{
////			if(s!=null)
////				HibernateUtil.closeSession();
////		}
////		return rol;
////	}
//	
//	public TbUsuario getUsuarioSolo(String p_stUsername)
//	{
//
//		Session s = null;
//		TbUsuario rol = new TbUsuario();
//		try
//		{
//			s = HibernateUtil.currentSession();
//			rol = (TbUsuario) s.createQuery("from TbUsuario p where p.username='"+p_stUsername+"'").uniqueResult();
//		}
//		catch (HibernateException e) {
//
//			logger.error(e.getMessage());
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		return rol;
//	}
//	
//	
//	public TbUsuario getSimpleUserByUsername(String p_stUsername) throws Exception
//	{
//		TbUsuario l_return = null;
//		Session l_sesion = null;
//		String l_stQry = "";
//		
//		try
//		{
//			l_sesion = HibernateUtil.currentSession();
//			l_stQry = "from TbUsuario u " +
//					" where u.username = :username ";
//			l_return = (TbUsuario)l_sesion.createQuery(l_stQry)
//						.setParameter("username", p_stUsername)
//						.uniqueResult();
//			
//		}
//		catch(Exception e)
//		{
//			throw e;
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//		}
//		
//		return l_return;
//		
//	}	// getUserByUsername
//	
//	
//	
	public List<Integer> getRightsByUser(TbUsuario p_user) throws Exception
	{
		List<Integer> l_return = null;
//		TbUsuario LreturnUser = new TbUsuario();
		Session l_sesion = null;
		String l_stQry = "";
		
		Collection<Integer>  rights;
		
		try
		{
			l_sesion = HibernateUtil.currentSession();
			l_stQry = "Select u.nukIdUsuario from TbUsuario u where u.nukIdUsuario = " + p_user.getNukIdUsuario();
			System.out.println(l_stQry);
			l_return = l_sesion.createQuery(l_stQry).list();
//			LreturnUser= (TbUsuario) l_sesion.createQuery(l_stQry).list();
			
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		
		rights = l_return;
		
		
//		if (rights.size() == 0) {
////			throw new UsernameNotFoundException("Invalid User");
//			session.setAttribute("SesionesSKF","");
//		}else{
//			
//			session.setAttribute("SesionesSKF", LreturnUser.getNukIdUsuario());
//			
//		}
		
			
		
		
		
		return l_return;
		
		
	}	// getRightsByUser
//	
//	
//	
////	public int getIntentosByUser(String p_stUserName) throws Exception
////	{
////		Session l_session = null;
////		userName = p_stUserName;
////		
////		try
////		{
////			l_session = HibernateUtil.currentSession();
////			
////			l_session.doWork(new Work() {
////				  public void execute(Connection connection) throws SQLException {
////					  Statement l_stmt = connection.createStatement();
////					  ResultSet l_rs = null;
////					  String l_stQry = "";
////					  
////					  l_stQry = "select case when intentos is null then 0 " +
////					  		"else intentos end as cuantos from TbUsuario where username = '" + userName + "'";
////					  
////					  l_rs = l_stmt.executeQuery(l_stQry);
////					  l_rs.next();
////					  
////					  intentos = l_rs.getInt(1);
////						
////					  //libera recursos
////					  l_stQry = null;
////					  l_rs.close();
////					  l_rs = null;
////					  l_stmt.close();
////					  l_stmt = null;
////						
////					    
////				  }
////				  
////			});	// termina zona de jdbc
////			
////		}
////		catch(Exception e)
////		{
////			throw e;
////			
////		}
////		finally
////		{
////			HibernateUtil.closeSession();
////			
////		}
////		
////		
////		return intentos;
////		
////	}
//	
//	
//	public List<TbUsuario> getUserByEmail(String p_stEMail) throws Exception
//	{
//		List<TbUsuario> l_return = null;
//		Session l_sesion = null;
//		String l_stQry = "";
//		
//		
//		try
//		{
//			l_sesion = HibernateUtil.currentSession();
//			l_stQry = "from TbUsuario u where u.email = :email";
//			l_return = l_sesion.createQuery(l_stQry).setParameter("email", p_stEMail).list();
//			
//		}
//		catch(Exception e)
//		{
//			throw e;
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//		}
//		
//		return l_return;
//		
//	}	// getUserByUsername
//	
//	
//	public void actualizaPwd(String p_stEmail, String p_stNewPwd) throws Exception
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		email = p_stEmail;
//		newPwd = p_stNewPwd;
//		
//		List<TbUsuario> users = getUserByEmail(p_stEmail);
//		
//		Properties props = new Properties();
//		InputStream is = null;
//		boolean enviado = false;
//		Calendar cal = new GregorianCalendar();
//		 
//		try {
//			
//		    is = getClass().getResourceAsStream("/email.properties");
//		    props.load(is);
//		    
//		} 
//		catch(Exception e) 
//		{
//			e.printStackTrace();
//		}
//		
//		cal.setTime(new Date());
//		cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(props.getProperty("diasVigencia")));
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//			
//			
//			
//			for(TbUsuario user: users)
//			{
//				user.setPassword(p_stNewPwd);
//				//user.setActivo("1");
////				int estatus = Double.parseDouble("1");
//				user.setStatus(1);
//				user.setPwdExpire(new Timestamp(cal.getTimeInMillis()));
//				l_session.update(user);
//			}
//			
//			tx.commit();
//			
//		}
//		catch(Exception e)
//		{
//			tx.rollback();
//			throw e;
//			
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//			
//		}
//		
//		
//	}	// actualizaPwd
//	
//	
//	
//	public void actualizaPwdByUsername(TbUsuario p_user, String p_stNewPwd) throws Exception
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		newPwd = p_stNewPwd;
//		
//		Properties props = new Properties();
//		InputStream is = null;
//		Calendar cal = new GregorianCalendar();
//		 
////		try {
////			
////		    is = getClass().getResourceAsStream("/email.properties");
////		    props.load(is);
////		    
////		} 
////		catch(Exception e) 
////		{
////			e.printStackTrace();
////		}
////		
//		cal.setTime(new Date());
//		cal.add(Calendar.MONTH, 12);
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//			
//			
//			
//			p_user.setPassword(p_stNewPwd);
////			p_user.setActivo("1");
////			double estatus = Double.parseDouble("1");
//			p_user.setStatus(1);
//			p_user.setPwdExpire(new Timestamp(cal.getTimeInMillis()));
//			p_user.setPwdHistory("1");
//			
//			l_session.update(p_user);
//			
//			tx.commit();
//			
//		}
//		catch(Exception e)
//		{
//			tx.rollback();
//			throw e;
//			
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//			
//		}
//		
//		
//	}	// actualizaPwd
//	
//	
	public void setUserInactiveByUserName(String p_stUserName) throws Exception
	{
		Session l_session = null;
		Transaction tx = null;
		TbUsuario user = null;
		
		user = getUserByUsername(p_stUserName);
		
		try
		{
			l_session = HibernateUtil.currentSession();
			tx = l_session.beginTransaction();
			
//			user.setActivo("0");
//			double estatus = Double.parseDouble("0");
			user.setNuActivo(false);
			l_session.update(user);
			
			tx.commit();
			
		}
		catch(Exception e)
		{
			tx.rollback();
			throw e;
			
		}
		finally
		{
			HibernateUtil.closeSession();
			
		}
		
		
	}	// setUserInactineByUserName
//	
//	/**
//	 * Obtiene el id del usuario
//	 * @param idUser
//	 * @return
//	 */
//	public Integer getIdByUser() {
//		Session s = null;
//		Integer idUsuario = null;
//		try
//		{
//			s = HibernateUtil.currentSession();
//			idUsuario = (Integer) s.createQuery("select max(c.idUsuario) from TbUsuario c").uniqueResult();
//			
//			if(idUsuario==null)
//				return -1;
//
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		
//		
//		
//		return idUsuario;
//	}
//	
//	public Integer getIdByCorporativo(String id_user) {
//		Session s = null;
//		Integer idUsuario = null;
//		try
//		{
//			s = HibernateUtil.currentSession();
//			idUsuario = (Integer) s.createQuery("select c.idCorporativo  from TbUsuario c where c.username='"+id_user+"'").uniqueResult();
//			
//			if(idUsuario==null)
//				return -1;
//
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		
//		
//		
//		return idUsuario;
//	}
//	
//	public Integer getIdByCorporativoIdUSer(int id_user) {
//		Session s = null;
//		Integer idUsuario = null;
//		try
//		{
//			s = HibernateUtil.currentSession();
//			idUsuario = (Integer) s.createQuery("select c.idCorporativo  from TbUsuario c where c.idUsuario='"+id_user+"'").uniqueResult();
//			
//			if(idUsuario==null)
//				return -1;
//
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		
//		
//		
//		return idUsuario;
//	}
	/**
	 * Obtiene el id del usuario
	 * @param idUser
	 * @return
	 */
	public Integer getIdByUserName(String Name) {
		Session s = null;
		Integer idUsuario = null;
		try
		{
			s = HibernateUtil.currentSession();
			idUsuario = (Integer) s.createQuery("select c.nukIdUsuario from TbUsuario c where chUsuario = '"+Name+"'").uniqueResult();
			
			if(idUsuario==null)
				return -1;

		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(s!=null)
				HibernateUtil.closeSession();
		}
		
		
		
		return idUsuario;
	}
//	
//	/**
//	 * Obtiene el id del Rol
//	 * @param idUser
//	 * @return
//	 */
//	public Integer getIdByPerfilRol(int id_user) {
//		Session s = null;
//		Integer idUsuario = null;
//		try
//		{
//			s = HibernateUtil.currentSession();
//			idUsuario = (Integer) s.createQuery("select p.tbRol.idRol from TbPerfilTbRol p where p.tbUsuario.idUsuario = '"+id_user+"'").uniqueResult();
////			l_stQry = "Select p.id.idRfc from PartnersUsuarios p where p.id.idUsuario = " + p_user.getIdUsuario();
//			
//			if(idUsuario==null)
//				return -1;
//
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		
//		
//		
//		return idUsuario;
//	}
//	
//	/**
//	 * Obtiene el id del usuario
//	 * @param idUser
//	 * @return
//	 */
//	public String getNameRol(int id) {
//		Session s = null;
//		String idUsuario = null;
//		try
//		{
//			s = HibernateUtil.currentSession();
//			idUsuario = (String) s.createQuery("select c.chNombre from TbRol c where c.idRol = '"+id+"'").uniqueResult();
//			
//			if(idUsuario==null)
//				return "";
//
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//		
//		
//		
//		return idUsuario;
//	}
//	
//	public synchronized Integer save(TbUsuario anUser) throws Exception 
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		Integer l_id = null;
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//
//			// cuando se ingresa una tabla por primera vez el total de 
//			// registros que tiene es cero.
////			p_UserName.setNuindex(0);
//			l_session.save(anUser);
//			
//			tx.commit();
//			
//			l_id = anUser.getIdUsuario();
//		}
//		catch (Exception e) {
//			tx.rollback();
//			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//		}
//		finally
//		{
//			if(l_session != null)
//				HibernateUtil.closeSession();
//			
//			tx = null;
//		}
//		
//		
//		return l_id;
//		
//	}	// save
//	
//	public void  saveUpdate(TbUsuario anUser) throws Exception 
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		Integer l_id = null;
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//
//			// cuando se ingresa una tabla por primera vez el total de 
//			// registros que tiene es cero.
////			p_UserName.setNuindex(0);
//			l_session.saveOrUpdate(anUser);
//			
//			tx.commit();
//			
//			l_id = anUser.getIdUsuario();
//		}
//		catch (Exception e) {
//			tx.rollback();
//			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//		}
//		finally
//		{
//			if(l_session != null)
//				HibernateUtil.closeSession();
//			
//			tx = null;
//		}
//		
//		
////		return l_id;
//		
//	}	// save
//	
//	public synchronized Integer saveProd(TbProductos anUser) throws Exception 
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		Integer l_id = null;
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//
//			// cuando se ingresa una tabla por primera vez el total de 
//			// registros que tiene es cero.
////			p_UserName.setNuindex(0);
//			l_session.save(anUser);
//			
//			tx.commit();
//			
//			l_id = anUser.getNukIdProductos();
//		}
//		catch (Exception e) {
//			tx.rollback();
//			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//		}
//		finally
//		{
//			if(l_session != null)
//				HibernateUtil.closeSession();
//			
//			tx = null;
//		}
//		
//		
//		return l_id;
//		
//	}	// save
//	
	public synchronized Integer saveOrdenManu(TbOrdenManufactura anUser) throws Exception 
	{
		Session l_session = null;
		Transaction tx = null;
		Integer l_id = null;
		
		try
		{
			l_session = HibernateUtil.currentSession();
			tx = l_session.beginTransaction();


			l_session.save(anUser);
			
			tx.commit();
			
			l_id = anUser.getNukIdOrdenManufactura();
		}
		catch (Exception e) {
			tx.rollback();
			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
		}
		finally
		{
			if(l_session != null)
				HibernateUtil.closeSession();
			
			tx = null;
		}
		
		
		return l_id;
		
	}	// save
	
	public synchronized Integer savePlacaOm(TbPlacaOm anUser) throws Exception 
	{
		Session l_session = null;
		Transaction tx = null;
		Integer l_id = null;
		
		try
		{
			l_session = HibernateUtil.currentSession();
			tx = l_session.beginTransaction();

			// cuando se ingresa una tabla por primera vez el total de 
			// registros que tiene es cero.
//			p_UserName.setNuindex(0);
			l_session.save(anUser);
			
			tx.commit();
			
			l_id = anUser.getTbPlaca().getNukIdPlaca();
		}
		catch (Exception e) {
			tx.rollback();
			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
		}
		finally
		{
			if(l_session != null)
				HibernateUtil.closeSession();
			
			tx = null;
		}
		
		
		return l_id;
		
	}	// save
//	
//	public synchronized Integer saveUbiMp(TbUbicacionMateriaPrim anUser) throws Exception 
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		Integer l_id = null;
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//
//			// cuando se ingresa una tabla por primera vez el total de 
//			// registros que tiene es cero.
////			p_UserName.setNuindex(0);
//			l_session.save(anUser);
//			
//			tx.commit();
//			
//			l_id = anUser.getNukIdUbicaMatPrim();
//		}
//		catch (Exception e) {
//			tx.rollback();
//			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//		}
//		finally
//		{
//			if(l_session != null)
//				HibernateUtil.closeSession();
//			
//			tx = null;
//		}
//		
//		
//		return l_id;
//		
//	}	// save
//	
//	public synchronized Integer saveDetaInvXA(TbInventarioDetalleMpXa anUser) throws Exception 
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		Integer l_id = null;
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//
//			// cuando se ingresa una tabla por primera vez el total de 
//			// registros que tiene es cero.
////			p_UserName.setNuindex(0);
//			l_session.save(anUser);
//			
//			tx.commit();
//			
//			l_id = anUser.getNukIdInventDetalleMpXa();
//		}
//		catch (Exception e) {
//			tx.rollback();
//			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//		}
//		finally
//		{
//			if(l_session != null)
//				HibernateUtil.closeSession();
//			
//			tx = null;
//		}
//		
//		
//		return l_id;
//		
//	}	// save
//	
//	public synchronized Integer saveDetaInvPTXA(TbInventarioDetallePtXa anUser) throws Exception 
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		Integer l_id = null;
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//
//			// cuando se ingresa una tabla por primera vez el total de 
//			// registros que tiene es cero.
////			p_UserName.setNuindex(0);
//			l_session.save(anUser);
//			
//			tx.commit();
//			
//			l_id = anUser.getNukIdInventDetallePtXa();
//		}
//		catch (Exception e) {
//			tx.rollback();
//			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//		}
//		finally
//		{
//			if(l_session != null)
//				HibernateUtil.closeSession();
//			
//			tx = null;
//		}
//		
//		
//		return l_id;
//		
//	}	// save
//	
//	public synchronized Integer saveUbiPT(TbUbicacionTerminados anUser) throws Exception 
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		Integer l_id = null;
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//
//			// cuando se ingresa una tabla por primera vez el total de 
//			// registros que tiene es cero.
////			p_UserName.setNuindex(0);
//			l_session.save(anUser);
//			
//			tx.commit();
//			
//			l_id = anUser.getNukIdUbicaTerm();
//		}
//		catch (Exception e) {
//			tx.rollback();
//			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//		}
//		finally
//		{
//			if(l_session != null)
//				HibernateUtil.closeSession();
//			
//			tx = null;
//		}
//		
//		
//		return l_id;
//		
//	}	// save
//	
//	public synchronized Integer saveHouse(TbCatHouse anUser) throws Exception 
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		Integer l_id = null;
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//
//			// cuando se ingresa una tabla por primera vez el total de 
//			// registros que tiene es cero.
////			p_UserName.setNuindex(0);
//			l_session.save(anUser);
//			
//			tx.commit();
//			
//			l_id = anUser.getNukIdHouse();
//		}
//		catch (Exception e) {
//			tx.rollback();
//			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//		}
//		finally
//		{
//			if(l_session != null)
//				HibernateUtil.closeSession();
//			
//			tx = null;
//		}
//		
//		
//		return l_id;
//		
//	}	// save
//	
//	public synchronized Integer saveEmpaque(TbCatEmpaques anUser) throws Exception 
//	{
//		Session l_session = null;
//		Transaction tx = null;
//		Integer l_id = null;
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//
//			// cuando se ingresa una tabla por primera vez el total de 
//			// registros que tiene es cero.
////			p_UserName.setNuindex(0);
//			l_session.save(anUser);
//			
//			tx.commit();
//			
//			l_id = anUser.getNukIdEmpaques();
//		}
//		catch (Exception e) {
//			tx.rollback();
//			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//		}
//		finally
//		{
//			if(l_session != null)
//				HibernateUtil.closeSession();
//			
//			tx = null;
//		}
//		
//		
//		return l_id;
//		
//	}	// save
//	
//	/**
//	 * Obtiene el id del usuario
//	 * @param idUser
//	 * @return
//	 */
////	public Integer getIdByPartner() {
////		Session s = null;
////		Integer idUsuario = null;
////		try
////		{
////			s = HibernateUtil.currentSession();
////			idUsuario = (Integer) s.createQuery("select max(p.idRfc) from Partners p").uniqueResult();
////			
////			if(idUsuario==null)
////				return -1;
////
////		}
////		catch (Exception e) {
////			e.printStackTrace();
////		} finally {
////			if(s!=null)
////				HibernateUtil.closeSession();
////		}
////		
////		
////		
////		return idUsuario;
////	}
//	
//	
//	
////	public synchronized Integer savePartners(TbRfc anUser) throws Exception 
////	{
////		Session l_session = null;
////		Transaction tx = null;
////		Integer l_id = null;
////		
////		try
////		{
////			l_session = HibernateUtil.currentSession();
////			tx = l_session.beginTransaction();
////
////			// cuando se ingresa una tabla por primera vez el total de 
////			// registros que tiene es cero.
//////			p_UserName.setNuindex(0);
////			l_session.save(anUser);
////			
////			tx.commit();
////			
////			l_id = anUser.getIdRfc();
////		}
////		catch (Exception e) {
////			tx.rollback();
////			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
////		}
////		finally
////		{
////			if(l_session != null)
////				HibernateUtil.closeSession();
////			
////			tx = null;
////		}
////		
////		
////		return l_id;
////		
////	}	// save
////	
////	public synchronized Integer savePartnersUpdate(TbRfc anUser) throws Exception 
////	{
////		Session l_session = null;
////		Transaction tx = null;
////		Integer l_id = null;
////		
////		try
////		{
////			l_session = HibernateUtil.currentSession();
////			tx = l_session.beginTransaction();
////
////			// cuando se ingresa una tabla por primera vez el total de 
////			// registros que tiene es cero.
//////			p_UserName.setNuindex(0);
////			l_session.saveOrUpdate(anUser);
////			
////			tx.commit();
////			
////			l_id = anUser.getIdRfc();
////		}
////		catch (Exception e) {
////			tx.rollback();
////			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
////		}
////		finally
////		{
////			if(l_session != null)
////				HibernateUtil.closeSession();
////			
////			tx = null;
////		}
////		
////		
////		return l_id;
////		
////	}	// save
////	
////	public synchronized Integer saveRfcUser(TbRfcTbUsuario anUserRFC) throws Exception 
////	{
////		Session l_session = null;
////		Transaction tx = null;
////		Integer l_id = null;
////		
////		try
////		{
////			l_session = HibernateUtil.currentSession();
////			tx = l_session.beginTransaction();
////
////			// cuando se ingresa una tabla por primera vez el total de 
////			// registros que tiene es cero.
//////			p_UserName.setNuindex(0);
////			l_session.save(anUserRFC);
////			
////			tx.commit();
////			
//////			l_id = anUserRFC.getIdRfcUsuario();
////			l_id = anUserRFC.getId().getIdRfc();
////		}
////		catch (Exception e) {
////			tx.rollback();
////			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
////		}
////		finally
////		{
////			if(l_session != null)
////				HibernateUtil.closeSession();
////			
////			tx = null;
////		}
////		
////		
////		return l_id;
////		
////	}	// save
////	
////	public synchronized Integer savePerfilRol(TbPerfilTbRol anUserRFC) throws Exception 
////	{
////		Session l_session = null;
////		Transaction tx = null;
////		Integer l_id = null;
////		
////		try
////		{
////			l_session = HibernateUtil.currentSession();
////			tx = l_session.beginTransaction();
////
////			// cuando se ingresa una tabla por primera vez el total de 
////			// registros que tiene es cero.
//////			p_UserName.setNuindex(0);
////			l_session.save(anUserRFC);
////			
////			tx.commit();
////			
//////			l_id = anUserRFC.getIdPerfilRol();
////			l_id = anUserRFC.getId().getIdPerfil();
////		}
////		catch (Exception e) {
////			tx.rollback();
////			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
////		}
////		finally
////		{
////			if(l_session != null)
////				HibernateUtil.closeSession();
////			
////			tx = null;
////		}
////		
////		
////		return l_id;
////		
////	}	// save
//	
//	public void savePerfilRolUpdate(Integer anPerfil,Integer anrol, Integer idUsuario, Integer idRol, Integer idPerfil) throws Exception
//	{
//		Statement stm = null;
//		Session l_session = null;
//		Transaction tx = null;
//		String qry = "";
//
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//			stm = l_session.connection().createStatement();
//			
//			qry = " UPDATE [Tb_Perfil_Tb_Rol] SET Id_rol = " +anrol+", Id_Perfil = " +anPerfil+ " WHERE Id_Usuario=" + idUsuario+" and Id_rol = " +idRol+" and Id_Perfil = " +idPerfil;
//			System.out.println("qry " + qry);
////			UPDATE [Tb_Detalle_Comprobante] SET Estatus_Factura = '' ,[nuautoriza] = 1 WHERE Id_Detalle_Comprobante=25
//			stm.executeUpdate(qry);
//			
//			//l_session.update(DetaComp);
//			
//			tx.commit();
//			
//		}
//		catch(Exception e)
//		{
//			tx.rollback();
//			throw e;
//			
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//			
//		}
//		
//		
//	}	// setUserInactineByUserName
////	public synchronized Integer savePerfilUser(TbPerfilTbUsuario anUserRFC) throws Exception 
////	{
////		Session l_session = null;
////		Transaction tx = null;
////		Integer l_id = null;
////		
////		try
////		{
////			l_session = HibernateUtil.currentSession();
////			tx = l_session.beginTransaction();
////
////			// cuando se ingresa una tabla por primera vez el total de 
////			// registros que tiene es cero.
//////			p_UserName.setNuindex(0);
////			l_session.save(anUserRFC);
////			
////			tx.commit();
////			
////			l_id = anUserRFC.getId().getIdPerfil();
////		}
////		catch (Exception e) {
////			tx.rollback();
////			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
////		}
////		finally
////		{
////			if(l_session != null)
////				HibernateUtil.closeSession();
////			
////			tx = null;
////		}
////		
////		
////		return l_id;
////		
////	}	// save
//	
//	public void savePerfilUserUpdate(Integer anPerfilRol, Integer idUsuario, Integer idPerfil) throws Exception
//	{
//		Statement stm = null;
//		Session l_session = null;
//		Transaction tx = null;
//		String qry = "";
//
//		
//		try
//		{
//			l_session = HibernateUtil.currentSession();
//			tx = l_session.beginTransaction();
//			stm = l_session.connection().createStatement();
//			
//			qry = " UPDATE [Tb_Perfil_Tb_Usuario] SET Id_Perfil = " +anPerfilRol+ " WHERE Id_Usuario=" + idUsuario+" and Id_Perfil = " +idPerfil;
//			System.out.println("qry " + qry);
////			UPDATE [Tb_Detalle_Comprobante] SET Estatus_Factura = '' ,[nuautoriza] = 1 WHERE Id_Detalle_Comprobante=25
//			stm.executeUpdate(qry);
//			
//			//l_session.update(DetaComp);
//			
//			tx.commit();
//			
//		}
//		catch(Exception e)
//		{
//			tx.rollback();
//			throw e;
//			
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//			
//		}
//		
//		
//	}	// setUserInactineByUserName
//	
////	public synchronized Integer savePersona(TbPersona anPerson) throws Exception 
////	{
////		Session l_session = null;
////		Transaction tx = null;
////		Integer l_id = null;
////		
////		try
////		{
////			l_session = HibernateUtil.currentSession();
////			tx = l_session.beginTransaction();
////
////			// cuando se ingresa una tabla por primera vez el total de 
////			// registros que tiene es cero.
//////			p_UserName.setNuindex(0);
////			l_session.save(anPerson);
////			
////			tx.commit();
////			
////			l_id = anPerson.getIdPersona();
////		}
////		catch (Exception e) {
////			tx.rollback();
////			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
////		}
////		finally
////		{
////			if(l_session != null)
////				HibernateUtil.closeSession();
////			
////			tx = null;
////		}
////		
////		
////		return l_id;
////		
////	}	// save
////	
////	public synchronized Integer savePersonaUpdate(TbPersona anPerson) throws Exception 
////	{
////		Session l_session = null;
////		Transaction tx = null;
////		Integer l_id = null;
////		
////		try
////		{
////			l_session = HibernateUtil.currentSession();
////			tx = l_session.beginTransaction();
////
////			// cuando se ingresa una tabla por primera vez el total de 
////			// registros que tiene es cero.
//////			p_UserName.setNuindex(0);
////			l_session.saveOrUpdate(anPerson);
////			
////			tx.commit();
////			
////			l_id = anPerson.getIdPersona();
////		}
////		catch (Exception e) {
////			tx.rollback();
////			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
////		}
////		finally
////		{
////			if(l_session != null)
////				HibernateUtil.closeSession();
////			
////			tx = null;
////		}
////		
////		
////		return l_id;
////		
////	}	// save
//
//
////	public Integer saveParUser(PartnersUsuarios pUse) throws Exception 
////	{
////		Session l_session = null;
////		Transaction tx = null;
////		Integer l_id = null;
////		
////		try
////		{
////			l_session = HibernateUtil.currentSession();
////			tx = l_session.beginTransaction();
////
////			// cuando se ingresa una tabla por primera vez el total de 
////			// registros que tiene es cero.
//////			p_UserName.setNuindex(0);
////			l_session.save(pUse);
////			
////			tx.commit();
////			
////			l_id = pUse.getId().getIdRfc();
////		}
////		catch (Exception e) {
////			tx.rollback();
////			throw new Exception(this.getClass().getName() + " - " + e.getMessage());
////		}
////		finally
////		{
////			if(l_session != null)
////				HibernateUtil.closeSession();
////			
////			tx = null;
////		}
////		
////		
////		return l_id;
////		
////	}	// save
//	
//	
//	public void saveOrUpdateUsuario(TbUsuario user) {
//		
//		Session s = null;
//		Transaction tx = null;
//		int l_inPcio = 0;
//		
//		try
//		{
//			s = HibernateUtil.currentSession();
//			tx = s.beginTransaction();
//			
//			
//				s.saveOrUpdate(user);
//
//			
//			tx.commit();
//		}
//		catch (Exception e) {
//			tx.rollback();
//			System.err.println("ERROR--->>> "+e);
//		}
//		finally
//		{
//			if(s!=null)
//				HibernateUtil.closeSession();
//		}
//	}
//	
////	public void saveOrUpdatePart(Partners Part) {
////		Session s = null;
////		Transaction tx = null;
////		int l_inPcio = 0;
////		
////		try
////		{
////			s = HibernateUtil.currentSession();
////			tx = s.beginTransaction();
////			
////	           	s.saveOrUpdate(Part);
////			
////			tx.commit();
////		}
////		catch (Exception e) {
////			tx.rollback();
////			System.err.println("ERROR--->>> "+e);
////		}
////		finally
////		{
////			if(s!=null)
////				HibernateUtil.closeSession();
////		}
////	}
//
//
////	public Integer getObtieneIdRFC(Integer idUsuario) {
////		Session s = null;
////		Integer id_RFC = null;
////		try
////		{
////			s = HibernateUtil.currentSession();
////		id_RFC = (Integer) s.createQuery("select p.id.idRfc from PartnersUsuarios p Where p.id.idUsuario ="+idUsuario).uniqueResult();
////			
////			if(id_RFC==null)
////				return -1;
////
////		}
////		catch (Exception e) {
////			e.printStackTrace();
////		} finally {
////			if(s!=null)
////				HibernateUtil.closeSession();
////		}
////		
////		
////		
////		return id_RFC;
////	}
//
//
////	public List getObtieneRFC(Integer lIdtables) {/**
////		 * Obtiene los registros de las tablas registradas en la base de datos
////		 * @return
////		 */
//		public synchronized List getObtieneRFC(Integer RFC) throws Exception
//		{
//			List l_table = null;
//			Session l_session = null;
//			
//			
//			try
//			{
//				l_session = HibernateUtil.currentSession();
//				l_table = l_session.createQuery("from Partners a where a.idRfc = "+RFC).list();
//				
//			}
//			catch (Exception e) {
//				throw new Exception (this.getClass().getName() + " - " + e.getMessage());
//			}
//			finally
//			{
//				if(l_session != null)
//					HibernateUtil.closeSession();
//			}
//			
//			return l_table;
//			
//		}	// getTables
//
//
//		public Integer getIdByPartner(String nombre) {
//			Session s = null;
//			Integer idPartner = null;
//			try
//			{
//				s = HibernateUtil.currentSession();
//				idPartner = (Integer) s.createQuery("select p.idRfc from Partners p Where p.nombre ='"+nombre+"'").uniqueResult();
//				
//				if(idPartner==null)
//					return -1;
//
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			
//			
//			
//			return idPartner;
//			
//		}
//
//
////		public void saveOrUpdatePart1(Partners part) {
//////			Session s = null;
//////			Integer idUsuario = null;
//////			try
//////			{
//////				s = HibernateUtil.currentSession();
//////				idUsuario = (Integer) s.createQuery("update set c.nombre = '"+part.getNombre()+"', c.rfc = '"+part.getRfc()+"' from Partners c Where idRfc ="+part.getIdRfc()).uniqueResult();
//////				
////////				if(idUsuario==null)
////////					return -1;
//////
//////			}
//////			catch (Exception e) {
//////				e.printStackTrace();
//////			} finally {
//////				if(s!=null)
//////					HibernateUtil.closeSession();
//////			}
////			
////			
////			
//////			return idUsuario;
////			Session s = null;
////			Transaction tx = null;
////			int l_inPcio = 0;
////			
////			try
////			{
////				s = HibernateUtil.currentSession();
////				tx = s.beginTransaction();
////				
////				
////				
//////				for(Usuarios user: users)
//////				{
////				part.setNombre(part.getNombre());
////				part.setRfc(part.getRfc());
//////					user.setPassword(p_stNewPwd);
//////					user.setActivo("1");
//////					user.setPwdExpire(new Timestamp(cal.getTimeInMillis()));
////					s.update(part);
//////				}
////				
////				tx.commit();
//////				s = HibernateUtil.currentSession();
//////				tx = s.beginTransaction();
//////				System.out.println("Id Partners------------------------------------>>"+part.getIdRfc());
//////		           	s.saveOrUpdate(part);
//////				
//////				tx.commit();
////			}
////			catch (Exception e) {
////				tx.rollback();
////				System.err.println("ERROR--->>> "+e);
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			
////		}
//		
////		/**
////		 * Obtiene el id del usuario
////		 * @param idUser
////		 * @return
////		 */
////		public Integer getIdByUser() {
////			Session s = null;
////			Integer idUsuario = null;
////			try
////			{
////				s = HibernateUtil.currentSession();
////				idUsuario = (Integer) s.createQuery("select max(c.idUsuario) from Usuarios c").uniqueResult();
////				
////				if(idUsuario==null)
////					return -1;
////
////			}
////			catch (Exception e) {
////				e.printStackTrace();
////			} finally {
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			
////			
////			
////			return idUsuario;
////		}
//		/**
//		 * verifica si ya existe el usuario en la bd
//		 */
//		public boolean existe (String campo, String valor) {
//			boolean existe = false;
//			Session s = null;
//			String where = "";
//			TbUsuario user =new TbUsuario()
//			;
//			
//			try {
//				s = HibernateUtil.currentSession();
//				where = "where s." + campo + "='" +valor + "'";
//				user = (TbUsuario) s.createQuery("from TbUsuario s " + where).uniqueResult();
//				if (user.getIdUsuario()!= null)
//					existe =true;
//				
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return existe;
//		}
//		
//		/**
//		 * verifica si ya existe el usuario en la bd
//		 */
//		public boolean existeProd (String campo, String valor) {
//			boolean existe = false;
//			Session s = null;
//			String where = "";
//			TbProductos user =new TbProductos();
//			
//			try {
//				s = HibernateUtil.currentSession();
//				where = "where s." + campo + "='" +valor + "'";
//				user = (TbProductos) s.createQuery("from TbProductos s " + where).uniqueResult();
//				if (user.getNukIdProductos()!= null)
//					existe =true;
//				
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return existe;
//		}
//		
//		/**
//		 * verifica si ya existe el usuario en la bd
//		 */
//		public boolean existeUbiMP (String campo, String valor) {
//			boolean existe = false;
//			Session s = null;
//			String where = "";
//			TbUbicacionMateriaPrim user =new TbUbicacionMateriaPrim()
//			;
//			
//			try {
//				s = HibernateUtil.currentSession();
//				where = "where s." + campo + "='" +valor + "'";
//				user = (TbUbicacionMateriaPrim) s.createQuery("from TbUbicacionMateriaPrim s " + where).uniqueResult();
//				if (user.getNukIdUbicaMatPrim()!= null)
//					existe =true;
//				
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return existe;
//		}
//		
//		/**
//		 * verifica si ya existe el usuario en la bd
//		 */
//		public boolean existeUbiPT (String campo, String valor) {
//			boolean existe = false;
//			Session s = null;
//			String where = "";
//			TbUbicacionTerminados user =new TbUbicacionTerminados()
//			;
//			
//			try {
//				s = HibernateUtil.currentSession();
//				where = "where s." + campo + "='" +valor + "'";
//				user = (TbUbicacionTerminados) s.createQuery("from TbUbicacionTerminados s " + where).uniqueResult();
//				if (user.getNukIdUbicaTerm()!= null)
//					existe =true;
//				
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return existe;
//		}
//		
//		
		/**
		 * verifica si ya existe el usuario en la bd
		 */
		public boolean existeIsla(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbSubArea user =new TbSubArea();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chNombre ='" +valor + "'";
				user = (TbSubArea) s.createQuery("from TbSubArea s " + where).uniqueResult();
				if (user.getNukIdSubArea()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return existe;
		}
		
		/**
		 * verifica si ya existe el usuario en la bd
		 */
		public TbSubArea getIsla(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbSubArea user =new TbSubArea();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chNombre ='" +valor + "'";
				user = (TbSubArea) s.createQuery("from TbSubArea s " + where).uniqueResult();
				if (user.getNukIdSubArea()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return user;
		}
		
		public boolean existeEmpleado(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbEmpleado user =new TbEmpleado();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chNumEmpleado ='" +valor + "'";
				user = (TbEmpleado) s.createQuery("from TbEmpleado s " + where).uniqueResult();
				if (user.getNukIdEmpleado()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return existe;
		}
		
		public TbEmpleado getEmpleado(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbEmpleado user =new TbEmpleado();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chNumEmpleado ='" +valor + "'";
				user = (TbEmpleado) s.createQuery("from TbEmpleado s " + where).uniqueResult();
				if (user.getNukIdEmpleado()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return user;
		}
		
		public boolean existeMaquina(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbMaquinaDispositivo user =new TbMaquinaDispositivo();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chNombre ='" +valor + "'";
				user = (TbMaquinaDispositivo) s.createQuery("from TbMaquinaDispositivo s " + where).uniqueResult();
				if (user.getNukIdMaquinaDisp()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return existe;
		}
		
		public TbMaquinaDispositivo getMaquina(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbMaquinaDispositivo user =new TbMaquinaDispositivo();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chNombre ='" +valor + "'";
				user = (TbMaquinaDispositivo) s.createQuery("from TbMaquinaDispositivo s " + where).uniqueResult();
				if (user.getNukIdMaquinaDisp()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return user;
		}
		
		public boolean existeCanal(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbCanal user =new TbCanal();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chNombre ='" +valor + "'";
				user = (TbCanal) s.createQuery("from TbCanal s " + where).uniqueResult();
				if (user.getNukIdCanal()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return existe;
		}
		
		public TbCanal getCanal(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbCanal user =new TbCanal();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chNombre ='" +valor + "'";
				user = (TbCanal) s.createQuery("from TbCanal s " + where).uniqueResult();
				if (user.getNukIdCanal()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return user;
		}
		
		public boolean existePlaca(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbPlaca user =new TbPlaca();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chPlaca ='" +valor + "'";
				user = (TbPlaca) s.createQuery("from TbPlaca s " + where).uniqueResult();
				if (user.getNukIdPlaca()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return existe;
		}
		
		public TbPlaca getPlaca(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbPlaca user =new TbPlaca();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chPlaca ='" +valor + "'";
				user = (TbPlaca) s.createQuery("from TbPlaca s " + where).uniqueResult();
				if (user.getNukIdPlaca()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return user;
		}
		
		public boolean existeProductoTerm(String valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbProductoTerminado user =new TbProductoTerminado();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chSku ='" +valor + "'";
				user = (TbProductoTerminado) s.createQuery("from TbProductoTerminado s " + where).uniqueResult();
				if (user.getNukIdProdTerm()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return existe;
		}
		
		public boolean existeProductoTermMatPrim(int valor) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbProdTermMatPrim user =new TbProdTermMatPrim();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.tbProductoTerminado.nukIdProdTerm =" +valor ;
				user = (TbProdTermMatPrim) s.createQuery("from TbProdTermMatPrim s " + where).uniqueResult();
				if (user.getNukIdProdTermProdMatPrim()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return existe;
		}
		
		public boolean existeUbicaOM(int IdSubarea,int IdCanal,int IdMAquinaDisp) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbUbicacionProcesoOm user =new TbUbicacionProcesoOm();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.tbSubArea.nukIdSubArea =" +IdSubarea + " and s.tbCanal.nukIdCanal " +IdCanal + " and s.tbMaquinaDispositivo.nukIdMaquinaDisp= "+IdMAquinaDisp;
				user = (TbUbicacionProcesoOm) s.createQuery("from TbUbicacionProcesoOm s " + where).uniqueResult();
				if (user.getNukIdUbicaProcessOm()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return existe;
		}
		
		public TbUbicacionProcesoOm getUbicaOM(int IdSubarea,int IdCanal,int IdMAquinaDisp) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbUbicacionProcesoOm user =new TbUbicacionProcesoOm();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.tbSubArea.nukIdSubArea =" +IdSubarea + " and s.tbCanal.nukIdCanal " +IdCanal + " and s.tbMaquinaDispositivo.nukIdMaquinaDisp= "+IdMAquinaDisp;
				user = (TbUbicacionProcesoOm) s.createQuery("from TbUbicacionProcesoOm s " + where).uniqueResult();
				if (user.getNukIdUbicaProcessOm()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return user;
		}
		
		
		
		public boolean existeOrdenManu(String OrdenManu) {
			boolean existe = false;
			Session s = null;
			String where = "";
			TbOrdenManufactura user =new TbOrdenManufactura();
			
			try {
				s = HibernateUtil.currentSession();
				where = "where s.chOrdenManufactura ='" +OrdenManu+"'";
				user = (TbOrdenManufactura) s.createQuery("from TbOrdenManufactura s " + where).uniqueResult();
				if (user.getNukIdOrdenManufactura()!= null)
					existe =true;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return existe;
		}
//	
//		
//		/**
//		 * verifica si ya existe el usuario en la bd
//		 */
//		public boolean existeCatHouse (String campo, String valor) {
//			boolean existe = false;
//			Session s = null;
//			String where = "";
//			TbCatHouse user =new TbCatHouse()
//			;
//			
//			try {
//				s = HibernateUtil.currentSession();
//				where = "where s." + campo + "='" +valor + "'";
//				user = (TbCatHouse) s.createQuery("from TbCatHouse s " + where).uniqueResult();
//				if (user.getNukIdHouse()!= null)
//					existe =true;
//				
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return existe;
//		}
//		
//		/**
//		 * verifica si ya existe el usuario en la bd
//		 */
//		public boolean existeCatEmpaque (String campo, String valor) {
//			boolean existe = false;
//			Session s = null;
//			String where = "";
//			TbCatEmpaques user =new TbCatEmpaques()
//			;
//			
//			try {
//				s = HibernateUtil.currentSession();
//				where = "where s." + campo + "='" +valor + "'";
//				user = (TbCatEmpaques) s.createQuery("from TbCatEmpaques s " + where).uniqueResult();
//				if (user.getNukIdEmpaques()!= null)
//					existe =true;
//				
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return existe;
//		}
//		
//		/**
//		 * verifica si ya existe el usuario en la bd
//		 */
////		public boolean existeRFC1 (String campo, String valor) {
////			boolean existe = false;
////			Session s = null;
////			String where = "";
////			TbRfc user =new TbRfc()
////			;
////			
////			try {
////				s = HibernateUtil.currentSession();
////				where = "where s." + campo + "='" +valor + "'";
////				user = (TbRfc) s.createQuery("from TbRfc s " + where).uniqueResult();
////				if (user.getIdRfc()!= null)
////					existe =true;
////				
////			} catch (Exception e) {
////				logger.error(e.getMessage());
////			} finally {
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return existe;
////		}
////		
////		/**
////		 * verifica si ya existe el usuario en la bd
////		 */
////		public boolean existeRFC(String valor) {
////			boolean existe = false;
////			Session s = null;
//////			String where = "";
////			TbRfc user =new TbRfc()
////			;
////			
////			try {
////				s = HibernateUtil.currentSession();
//////				where = "where s." + campo + "='" +valor + "'";
////				user = (TbRfc) s.createQuery("from TbRfc s where s.chRfc =" + valor + " and status = 1").uniqueResult();
////				if (user.getIdRfc()!= null)
////					existe =true;
////				
////			} catch (Exception e) {
////				logger.error(e.getMessage());
////			} finally {
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return existe;
////		}
////	
		public TbPersona getPersona(TbUsuario cliente) {

			Session s = null;
			TbPersona persona = new TbPersona();
			try
			{
				s = HibernateUtil.currentSession();
				System.out.println("-------------------cliente.getIdUsuario() = "+ cliente.getChUsuario());
				System.out.println("cliente.getIdUsuario() = "+ cliente.getNukIdUsuario());
				System.out.println("Usuario = cliente.getIdUsuario(): "+ cliente.getTbPersona().getNukIdPersona());
				persona = (TbPersona) s.createQuery("from TbPersona p where p.nukIdPersona="+cliente.getNukIdUsuario()+" and nuActivo= 1").uniqueResult();
			}
			catch (HibernateException e) {

				logger.error(e.getMessage());
			}
			finally
			{
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return persona;
		}
////		
////		public TbPersona getPersonaEmail(TbUsuario cliente,String Email) {
////
////			Session s = null;
////			TbPersona persona = new TbPersona();
////			try
////			{
////				s = HibernateUtil.currentSession();
////				System.out.println("-------------------cliente.getIdUsuario() = "+ cliente.getUsername());
////				System.out.println("cliente.getIdUsuario() = "+ cliente.getIdUsuario());
////				System.out.println("Usuario = cliente.getIdUsuario(): "+ cliente.getIdUsuario());
////				persona = (TbPersona) s.createQuery("from TbPersona p where p.tbUsuario.idUsuario="+cliente.getIdUsuario()+" and status= 1 and chEmail = '"+ Email +"'").uniqueResult();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return persona;
////		}
////		
////		public TbPersona getPersonaUSer(TbUsuario cliente) {
////
////			Session s = null;
////			TbPersona persona = new TbPersona();
////			try
////			{
////				s = HibernateUtil.currentSession();
////				System.out.println("cliente.getIdUsuario() = "+ cliente.getUsername());
////				System.out.println("cliente.getIdUsuario() = "+ cliente.getIdUsuario());
////				persona = (TbPersona) s.createQuery("from TbPersona p where p.tbUsuario.idUsuario="+cliente.getIdUsuario()+"").uniqueResult();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return persona;
////		}
////		
////		public static TbPersona getPersonaAuto(int cliente) {
////
////			Session s = null;
////			TbPersona persona = new TbPersona();
////			try
////			{
////				s = HibernateUtil.currentSession();
////				persona = (TbPersona) s.createQuery("from TbPersona p where p.tbUsuario.idUsuario="+cliente).uniqueResult();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return persona;
////		}
////
////		public static TbPersona getPersonaProveedor(int cliente) {
////
////			Session s = null;
////			TbPersona persona = new TbPersona();
////			try
////			{
////				s = HibernateUtil.currentSession();
////				persona = (TbPersona) s.createQuery("from TbPersona p where p.tbUsuario.idUsuario="+cliente).uniqueResult();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return persona;
////		}
////		
		public TbPerfil gettbPerfil(Integer id_perfil) {

			Session s = null;
			TbPerfil persona = new TbPerfil();
			try
			{
				s = HibernateUtil.currentSession();
				persona = (TbPerfil) s.createQuery("from TbPerfil p where p.nukIdPerfil="+id_perfil+"").uniqueResult();
			}
			catch (HibernateException e) {

				logger.error(e.getMessage());
			}
			finally
			{
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return persona;
		}
////		
////		public TbRol gettbRol(Integer id_perfil) {
////
////			Session s = null;
////			TbRol persona = new TbRol();
////			try
////			{
////				s = HibernateUtil.currentSession();
////				persona = (TbRol) s.createQuery("from TbRol p where p.idRol="+id_perfil+"").uniqueResult();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return persona;
////		}
//		
//				
//		public Integer getIdPerfil(TbUsuario cliente) {
//			Session s = null;
//			Integer idPartner = null;
//			try
//			{
//				s = HibernateUtil.currentSession();
//				idPartner = (Integer) s.createQuery("select p.tbPerfil.idPerfil from TbPerfilTbRol p Where p.tbUsuario.idUsuario = "+cliente.getIdUsuario()+"").uniqueResult();
//				
//				if(idPartner==null)
//					return -1;
//
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			
//			
//			
//			return idPartner;
//			
//		}
//		
//		public Integer getIdRol(TbUsuario cliente) {
//			Session s = null;
//			Integer idPartner = null;
//			try
//			{
//				s = HibernateUtil.currentSession();
//				idPartner = (Integer) s.createQuery("select p.tbRol.idRol from TbPerfilTbRol p Where p.tbUsuario.idUsuario = "+cliente.getIdUsuario()+"").uniqueResult();
//				
//				if(idPartner==null)
//					return -1;
//
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			
//			
//			
//			return idPartner;
//			
//		}
//		
////		public List<ComboFactory> getRol()
////		{
////			Session s = null;
////			Query q = null;
////			TbRol gc;
////			List<ComboFactory> RolCliente = new ArrayList<ComboFactory>();
////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbRol g where g.status=1  and id_Rol <> 1 " +
////						"order by chNombre");
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbRol)exQ.get(i);
////						RolCliente.add(new ComboFactory(gc.getIdRol(), gc.getChNombre()));
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return RolCliente;
////
////		}
////		
////		public static TbRol getRol(TbUsuario cliente) {
////
////			Session s = null;
////			TbRol rol = new TbRol();
////			try
////			{
////				s = HibernateUtil.currentSession();
////				rol = (TbRol) s.createQuery("from TbRol p where p.idUsuario="+cliente.getIdUsuario()+"").uniqueResult();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return rol;
////		}
//		
//		
//		public TbUsuario getUserPojoSinEstatus(Integer cliente) {
//
//			Session s = null;
//			TbUsuario rol = new TbUsuario();
//			try
//			{
//				s = HibernateUtil.currentSession();
//				rol = (TbUsuario) s.createQuery("from TbUsuario p where p.idUsuario="+cliente+"").uniqueResult();
//			}
//			catch (HibernateException e) {
//
//				logger.error(e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return rol;
//		}
//		
////		public TbRfc getRFCPojoSinEstatus(Integer cliente) {
////
////			Session s = null;
////			TbRfc rol = new TbRfc();
////			try
////			{
////				s = HibernateUtil.currentSession();
////				rol = (TbRfc) s.createQuery("from TbRfc p where p.idRfc="+cliente+"").uniqueResult();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return rol;
////		}
//		
//		public TbUsuario getUserPojo(Integer cliente) {
//
//			Session s = null;
//			TbUsuario rol = new TbUsuario();
//			try
//			{
//				s = HibernateUtil.currentSession();
//				rol = (TbUsuario) s.createQuery("from TbUsuario p where p.idUsuario="+cliente).uniqueResult();
//			}
//			catch (HibernateException e) {
//
//				logger.error(e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return rol;
//		}
//		
////		public TbRfc getRFCPojo(Integer cliente) {
////
////			Session s = null;
////			TbRfc rol = new TbRfc();
////			try
////			{
////				s = HibernateUtil.currentSession();
////				rol = (TbRfc) s.createQuery("from TbRfc p where p.idRfc="+cliente+" and status= 1").uniqueResult();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return rol;
////		}
//		
////		public TbRol getRol() {
////			TbRol grupool=null;
////			Session s = null;
////
////			try
////			{
////				s = HibernateUtil.currentSession();
////				grupool = (TbRol)s.createQuery("from TbRol g" );
////				if(grupool==null)
////					return new TbRol();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return grupool;
////		}
//		
////		public TbPerfil getPerfil() {
////			TbPerfil grupoPerf=null;
////			Session s = null;
////
////			try
////			{
////				s = HibernateUtil.currentSession();
////				grupoPerf = (TbPerfil)s.createQuery("from TbPerfil g" );
////				if(grupoPerf==null)
////					return new TbPerfil();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return grupoPerf;
////		}
//		
////		public List<ComboFactory> getPerfil()
////		{
////			Session s = null;
////			Query q = null;
////			TbPerfil gc;
////			List<ComboFactory> PerfilCliente = new ArrayList<ComboFactory>();
////			PerfilCliente.add(new ComboFactory(0, "SELECCIONE"));
////			
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbPerfil g where g.status=1 " +
////						"order by chNombre");
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbPerfil)exQ.get(i);
////						PerfilCliente.add(new ComboFactory(gc.getIdPerfil(), gc.getChNombre()));
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return PerfilCliente;
////
////		}
////		
//		public synchronized List getRol_List() throws Exception
//		{
//			List l_table = null;
//			Session l_session = null;
//			
//			
//			try
//			{
//				l_session = HibernateUtil.currentSession();
//				l_table = l_session.createQuery("from TbRol a" ).list();
//				
//			}
//			catch (Exception e) {
//				throw new Exception (this.getClass().getName() + " - " + e.getMessage());
//			}
//			finally
//			{
//				if(l_session != null)
//					HibernateUtil.closeSession();
//			}
//			
//			return l_table;
//			
//		}	// getTables
//		
//		public synchronized List getPerfil_List() throws Exception
//		{
//			List l_table = null;
//			Session l_session = null;
//			
//			
//			
//			try
//			{
//				l_session = HibernateUtil.currentSession();
//				l_table = l_session.createQuery("from TbPerfil a" ).list();
//				
//			}
//			catch (Exception e) {
//				throw new Exception (this.getClass().getName() + " - " + e.getMessage());
//			}
//			finally
//			{
//				if(l_session != null)
//					HibernateUtil.closeSession();
//			}
//			
//			return l_table;
//			
//		}	// getTables
//		
//		/**
//		 * Obtiene los registros de las tablas registradas en la base de datos
//		 * @return
//		 */
//		public synchronized List getTables(String Usuario) throws Exception
//		{
//			List l_table = null;
//			Session l_session = null;
//			
//			
//			try
//			{
//				l_session = HibernateUtil.currentSession();
//				l_table = l_session.createQuery("from TbRfc a where a.chRfc = '"+Usuario+"' order by a.chRfc").list();
//				
//			}
//			catch (Exception e) {
//				throw new Exception (this.getClass().getName() + " - " + e.getMessage());
//			}
//			finally
//			{
//				if(l_session != null)
//					HibernateUtil.closeSession();
//			}
//			
//			return l_table;
//			
//		}	// getTables
//		
////		public List<ComboFactory> getArea()
////		{
////			Session s = null;
////			Query q = null;
////			TbPerfil gc;
////			List<ComboFactory> areaCliente = new ArrayList<ComboFactory>();
////			areaCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbPerfil g where status=1 " +
//////						" and g.tbCorporativo.nukidCorporativo= "+p_idCorporativo+
////						"order by g.chNombre");
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbPerfil)exQ.get(i);
////						areaCliente.add(new ComboFactory(gc.getIdPerfil(), gc.getChNombre()));
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return areaCliente;
////
////		}
////		
////		public List<ComboFactory> getCorporativo()
////		{
////			Session s = null;
////			Query q = null;
////			TbCorporativo gc;
////			List<ComboFactory> areaCliente = new ArrayList<ComboFactory>();
////			areaCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbCorporativo g where status=1 " +
//////						" and g.tbCorporativo.nukidCorporativo= "+p_idCorporativo+
////						"order by g.chnombre");
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbCorporativo)exQ.get(i);
////						areaCliente.add(new ComboFactory(gc.getIdCorporativo(), gc.getChnombre()));
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return areaCliente;
////
////		}
////		public List<ComboFactory> getRolCombo()
////		{
////			Session s = null;
////			Query q = null;
////			TbRol gc;
////			List<ComboFactory> RolCliente = new ArrayList<ComboFactory>();
////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbRol g where status=1 and id_Rol <> 1 " +
//////						" and g.tbCorporativo.nukidCorporativo= "+p_idCorporativo+
////						"order by g.chNombre");
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbRol)exQ.get(i);
////						RolCliente.add(new ComboFactory(gc.getIdRol(), gc.getChNombre()));
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return RolCliente;
////
////		}
////		
////		/**
////		 * Obtiene el corporativo por usuario
////		 * @param user
////		 * @return
////		 */
////		public TbCorporativo getTbCorporativo(Integer user) {
////			TbCorporativo corporativo=null;
////			Session s = null;
////			
////			try
////			{
////				s = HibernateUtil.currentSession();
////				corporativo = (TbCorporativo) s.createQuery("from TbCorporativo c where c.idCorporativo="+user).uniqueResult();
////				if(corporativo!=null)
////					return corporativo;
////				else
////					corporativo = new TbCorporativo();
////			}
////			catch (Exception e) {
////				logger.error(e.getMessage());
////			} finally {
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			
////			return corporativo;
////		}
////		
////		/**
////		 * Obtiene el corporativo por usuario
////		 * @param user
////		 * @return
////		 */
////		public TbCorporativo getTbCorporativoId(String user) {
////			TbCorporativo corporativo=null;
////			Session s = null;
////			
////			try
////			{
////				s = HibernateUtil.currentSession();
////				corporativo = (TbCorporativo) s.createQuery("from TbCorporativo c where c.chnombre='"+user+"'").uniqueResult();
////				if(corporativo!=null)
////					return corporativo;
////				else
////					corporativo = new TbCorporativo();
////			}
////			catch (Exception e) {
////				logger.error(e.getMessage());
////			} finally {
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			
////			return corporativo;
////		}
////		
////		@SuppressWarnings("unchecked")
////		public TbRfcTbUsuario getRfcUSer(TbPerfilTbUsuario peruser,int id_corporativo)
////		{
////			TbRfcTbUsuario l_obUser = new TbRfcTbUsuario();
////			Session l_obSession = null;
////			String l_stQry = "";
////			List l_obResult = null;
////
////			try
////			{
////				l_obSession = HibernateUtil.currentSession();
////				l_stQry = "from TbRfcTbUsuario s where s.tbCorporativo.idCorporativo="+id_corporativo+" and s.tbUsuario.idUsuario="+peruser.getTbUsuario().getIdUsuario();
////				l_obResult = l_obSession.createQuery(l_stQry).list();
////				if(l_obResult.size()>0)
////					l_obUser = (TbRfcTbUsuario)l_obResult.get(0);
////			} 
////			catch (HibernateException e) {
////				logger.error(e.getMessage());
////				return null;
////			}
////			finally{
////				if(l_obSession != null)
////					HibernateUtil.closeSession();
////			}
////			return l_obUser;
////			
////		}	//getUserByLoginname
////		
////		
////		
////		@SuppressWarnings("unchecked")
////		public TbRfcTbUsuario gettbRfcUSer(int id_corp,int id_user)
////		{
////			TbRfcTbUsuario l_obUser = new TbRfcTbUsuario();
////			Session l_obSession = null;
////			String l_stQry = "";
////			List l_obResult = null;
////
////			try
////			{
////				l_obSession = HibernateUtil.currentSession();
////				l_stQry = "from TbRfcTbUsuario s where s.tbCorporativo.idCorporativo="+id_corp + " and s.tbUsuario.idUsuario="+id_user;
////				l_obResult = l_obSession.createQuery(l_stQry).list();
////				if(l_obResult.size()>0)
////					l_obUser = (TbRfcTbUsuario)l_obResult.get(0);
////			} 
////			catch (HibernateException e) {
////				logger.error(e.getMessage());
////				return null;
////			}
////			finally{
////				if(l_obSession != null)
////					HibernateUtil.closeSession();
////			}
////			return l_obUser;
////			
////		}	//getUserByLoginname
////		
////		/**
////		 * Obtiene el corporativo por usuario
////		 * @param user
////		 * @return
////		 */
////		public TbProductoComprobante getTbProductoCompro(Integer user) {
////			TbProductoComprobante corporativo=null;
////			Session s = null;
////			
////			try
////			{
////				s = HibernateUtil.currentSession();
////				corporativo = (TbProductoComprobante) s.createQuery("from TbProductoComprobante c where c.idDetalleComprobante="+user).uniqueResult();
////				if(corporativo!=null)
////					return corporativo;
////				else
////					corporativo = new TbProductoComprobante();
////			}
////			catch (Exception e) {
////				logger.error(e.getMessage());
////			} finally {
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			
////			return corporativo;
////		}
//		
////		public List<String> getperRol(Integer id_rol)
////		{
////			Session s = null;
////			Query q = null;
////			TbPerfilTbRol gc;
////			List<String> RolCliente = new ArrayList<String>();
//////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbPerfilTbRol p where p.tbRol.idRol="+id_rol);
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbPerfilTbRol)exQ.get(i);
////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
////						RolCliente.add(gc.getTbUsuario().getIdUsuario()+"");
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return RolCliente;
////
////		}
//		
////		public List<String> getperRol(Integer id_rol)
////		{
////			Session s = null;
////			List<TbUso> detalles = new ArrayList<TbUso>();
//////			List<Integer> idsProductos = new ArrayList<Integer>();
////			Statement stm = null;
////			ResultSet rs = null;
////			
////			List<String> RolCliente = new ArrayList<String>();
////			try {
////				s = HibernateUtil.currentSession();
////
//////				List lista = s.createQuery("select p.idProductoComprobante from TbProductoComprobante p where p.idDetalleComprobante =  " + factura.getIdDetalleComprobante()).list();
////	//
//////				for (int i = 0 ; i<lista.size() ; i++){
//////					int idProducto = (Integer) lista.get(i) ;
//////					logger.info("distintos: " + idProducto);
//////					idsProductos.add(idProducto);
//////				}
////				
////
////				stm = s.connection().createStatement();
////
//////				for (int  i = 0 ; i <idsProductos.size() ; i++) {
//////					rs = stm.executeQuery("select sum (a.nucantidad),sum (a.nuimporte), a.chdescripcion, b.chkclave_producto, b.chunidad_salida,sum (a.nuentregado),a.nuprecio_unitario,a.nuiva_unitario,sum(nuiva_total)" +
//////							"	from tb_cto_factura a, tb_producto b where a.nukid_producto=b.nukid_producto and a.nukid_factura=" + factura.getIdDetalleComprobante()+ " and" +
//////							" a.nukid_producto= " + idsProductos.get(i)+ " group by a.nukid_factura, a.chdescripcion, b.chkclave_producto, b.chunidad_salida,a.nuprecio_unitario, a.nuiva_unitario");
////					
////					
////					rs = stm.executeQuery("select Id_Usuario from Tb_Perfil_Tb_rol where Id_rol =" + id_rol);
////					
////					
//////					TbDetalleComprobante ctoFactura = new TbDetalleComprobante();
//////					TbProductoComprobante producto =  new TbProductoComprobante();
//////					TbProductoServicio prodser = new TbProductoServicio();
//////					TbU uso = new TbUso();
//////					prodser.setIdProductoServicio(idsProductos.get(i));
////
////					while (rs.next()) {
//////						uso.setIdUso(rs.getInt(1));
//////						uso.setCUsoCfdi(rs.getString(2));
//////						uso.setDescripcion(rs.getString(3));
////						
//////						RolCliente.add(new ComboFactory(uso.getIdUso(), uso.getCUsoCfdi()+" - "+ uso.getDescripcion()));
////						RolCliente.add(rs.getInt(1)+"");
//////						producto.setJustificacion(rs.getString(4));
//////						producto.setClave(rs.getString(5));
//////						
////////						producto.setTbProductoServicio(prodser);
//////						producto.setUnidad(rs.getString(6));
//////						producto.setUnidad(rs.getString(3));
//////						producto.setNuprecioUnitario(rs.getDouble(7));
//////						producto.setNuivaUnitario(rs.getDouble(8));
//////						producto.setNuivaTotal(rs.getDouble(9));
////
////					}
//////					detalles.add(producto);
////
//////				}
////
////
////			} catch (Exception e) {
////				logger.error("" + e.getMessage());
////				e.printStackTrace();
////			} finally {
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return RolCliente;
////		}
//		
		public List<DetalleRol> getRoles(Integer SKU)
		{
			Session s = null;
			Statement stm = null;
			ResultSet rs = null;
			TbRol rol = new TbRol();

			Map<String, DetalleRol> mapa = new HashMap<String, DetalleRol>();
			List<DetalleRol> response = new ArrayList<DetalleRol>();
			try {
				s = HibernateUtil.currentSession();


				

				stm = s.connection().createStatement();


					
					rs = stm.executeQuery("select rol.nukIdRol, rol.chClave, rol.chNombre , rol.chDescripcion from tb_rol as rol, tb_perfil as per, Tb_Perfil_Rol as pr where per.nukIdPerfil = pr.nukIdPerfil and rol.nukIdRol = pr.nukIdRol and per.nukIdPerfil= " +SKU);
					int i= 0;
					
					while (rs.next()) {
						
						rol.setNukIdRol(rs.getInt(1));
						System.out.println("rol.getNukIdRol( ----->>>> " + rol.getNukIdRol());
						rol.setChClave(rs.getString(2));
						System.out.println("rol.getChClave()( ----->>>> " + rol.getChClave());
						rol.setChNombre(rs.getString(3));
						System.out.println("rol.getChNombre()( ----->>>> " + rol.getChNombre());
						rol.setChDescripcion(rs.getString(4));
						System.out.println("rol.getChDescripcion()( ----->>>> " + rol.getChDescripcion());
						
						DetalleRol d = new DetalleRol(i,rol.getChClave(), rol.getChNombre(), rol.getChDescripcion());
						
						mapa.put(rol.getNukIdRol()+"", d);
						i++;

					}
					
					response.addAll(mapa.values());
					

			} catch (Exception e) {
				logger.error("" + e.getMessage());
				e.printStackTrace();
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return response;
		}
//		
//		public List<String> getComprobantesIds(String name)
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			
//			List<String> RolCliente = new ArrayList<String>();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				
//
//				stm = s.connection().createStatement();
//
//
//					
//					rs = stm.executeQuery("select id_comprobante from Tb_Comprobantes where folio like '%" + name+ "%'");
//
//
//					while (rs.next()) {
//						RolCliente.add(rs.getInt(1)+"");
//
//					}
//
//
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return RolCliente;
//		}
//		
//		public List<String> getComprobantesIdsOrden(String name)
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			
//			List<String> RolCliente = new ArrayList<String>();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				
//
//				stm = s.connection().createStatement();
//
//
//					
//					rs = stm.executeQuery("select c.id_comprobante from Tb_Comprobantes as C, Tb_Detalle_Comprobante DC where C.id_Detalle_Comprobante = DC.id_Detalle_Comprobante and DC.ident_oc like '%"+name+"%'");
//
//
//					while (rs.next()) {
//						RolCliente.add(rs.getInt(1)+"");
//
//					}
//
//
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return RolCliente;
//		}
//		
////		public List<String> getperRol(Integer id_rol)
////		{
////			Session s = null;
////			Query q = null;
////			TbPerfilTbRol gc;
////			List<String> RolCliente = new ArrayList<String>();
//////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbPerfilTbRol p where p.tbRol.idRol="+id_rol);
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbPerfilTbRol)exQ.get(i);
////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
////						RolCliente.add(gc.getTbUsuario().getIdUsuario()+"");
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return RolCliente;
////
////		}
//		
//		public List<String> getUsuarioAuto1(Integer id_Corp, Integer id_User)
//		{
//			Session s = null;
//			Query q = null;
//			TbUsuario gc;
//			List<String> RolCliente = new ArrayList<String>();
////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbUsuario p where p.idCorporativo="+id_Corp+ " and p.autoriza = "+ 1);
//				q = s.createQuery("from TbUsuario p where p.idCorporativo="+id_Corp+ " and p.autoriza = "+ 1);
////				q = s.createQuery(" from TbUsuario u where u.idUsuario in (SELECT pr.idUsuario FROM TbPerfilTbrol pr where pr.idPerfil = (SELECT tpr.idPerfil FROM TbPerfilTbrol tpr WHERE tpr.idUsuario = "+id_User+") and pr.idCorporativo = "+id_Corp+" ) and u.autoriza = "+ 1);
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbUsuario)exQ.get(i);
////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
//						RolCliente.add(gc.getIdUsuario()+"");
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return RolCliente;
//
//		}
//		
//		public List<String> getUsuarioAuto(Integer id_Corp, Integer id_User) 
//		{
//			Session s = null;
////			List<TbUso> detalles = new ArrayList<TbUso>();
////			TbUsuario gc;
////			List<TbUsuario> RolCliente = new ArrayList<TbUsuario>();
//			List<String> RolCliente = new ArrayList<String>();
//			Statement stm = null;
//			ResultSet rs = null;
//			
////			List<ComboFactory> PerfilCliente = new ArrayList<ComboFactory>();
////			PerfilCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try {
//				s = HibernateUtil.currentSession();
//
////				List lista = s.createQuery("select p.idProductoComprobante from TbProductoComprobante p where p.idDetalleComprobante =  " + factura.getIdDetalleComprobante()).list();
//	//
////				for (int i = 0 ; i<lista.size() ; i++){
////					int idProducto = (Integer) lista.get(i) ;
////					logger.info("distintos: " + idProducto);
////					idsProductos.add(idProducto);
////				}
//				
//
//				stm = s.connection().createStatement();
//
////				for (int  i = 0 ; i <idsProductos.size() ; i++) {
////					rs = stm.executeQuery("select sum (a.nucantidad),sum (a.nuimporte), a.chdescripcion, b.chkclave_producto, b.chunidad_salida,sum (a.nuentregado),a.nuprecio_unitario,a.nuiva_unitario,sum(nuiva_total)" +
////							"	from tb_cto_factura a, tb_producto b where a.nukid_producto=b.nukid_producto and a.nukid_factura=" + factura.getIdDetalleComprobante()+ " and" +
////							" a.nukid_producto= " + idsProductos.get(i)+ " group by a.nukid_factura, a.chdescripcion, b.chkclave_producto, b.chunidad_salida,a.nuprecio_unitario, a.nuiva_unitario");
//					
//					
//					rs = stm.executeQuery("select Id_Usuario from Tb_Usuario where id_Usuario in (SELECT Id_Usuario  FROM Tb_Perfil_Tb_rol  where Id_Perfil = (SELECT Id_Perfil  FROM Tb_Perfil_Tb_rol  WHERE Id_Usuario = "+id_User+") and id_Corporativo = "+id_Corp+" ) and autoriza = 1 ");
//					
//					System.out.println("--------------------->>>  " + "select Id_Usuario from Tb_Usuario where id_Usuario in (SELECT Id_Usuario  FROM Tb_Perfil_Tb_rol  where Id_Perfil = (SELECT Id_Perfil  FROM Tb_Perfil_Tb_rol  WHERE Id_Usuario = "+id_User+") and id_Corporativo = "+id_Corp+" ) and autoriza = 1 ");
//					
//					
//					TbUsuario uso = new TbUsuario();
//
//					while (rs.next()) {
//						uso.setIdUsuario(rs.getInt(1));
////						uso.setCUsoCfdi(rs.getString(2));
////						uso.setDescripcion(rs.getString(3));
//						
//						RolCliente.add(uso.getIdUsuario()+"");
//
//					}
//
////				}
//
//
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return RolCliente;
//		}
//		
//		
//		
//		public List<String> getUsuarioAutoArea1(Integer id_Corp)
//		{
//			Session s = null;
//			Query q = null;
//			TbUsuario gc;
//			List<String> RolCliente = new ArrayList<String>();
////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
//				q = s.createQuery("from TbUsuario p where p.idCorporativo="+id_Corp+ " and p.autorizaArea = "+ 1);
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbUsuario)exQ.get(i);
////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
//						RolCliente.add(gc.getIdUsuario()+"");
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return RolCliente;
//
//		}
//		
//		public List<String> getUsuarioAutoArea(Integer id_Corp, Integer id_User) 
//		{
//			Session s = null;
////			List<TbUso> detalles = new ArrayList<TbUso>();
////			TbUsuario gc;
////			List<TbUsuario> RolCliente = new ArrayList<TbUsuario>();
//			List<String> RolCliente = new ArrayList<String>();
//			Statement stm = null;
//			ResultSet rs = null;
//			
////			List<ComboFactory> PerfilCliente = new ArrayList<ComboFactory>();
////			PerfilCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try {
//				s = HibernateUtil.currentSession();
//
////				List lista = s.createQuery("select p.idProductoComprobante from TbProductoComprobante p where p.idDetalleComprobante =  " + factura.getIdDetalleComprobante()).list();
//	//
////				for (int i = 0 ; i<lista.size() ; i++){
////					int idProducto = (Integer) lista.get(i) ;
////					logger.info("distintos: " + idProducto);
////					idsProductos.add(idProducto);
////				}
//				
//
//				stm = s.connection().createStatement();
//
////				for (int  i = 0 ; i <idsProductos.size() ; i++) {
////					rs = stm.executeQuery("select sum (a.nucantidad),sum (a.nuimporte), a.chdescripcion, b.chkclave_producto, b.chunidad_salida,sum (a.nuentregado),a.nuprecio_unitario,a.nuiva_unitario,sum(nuiva_total)" +
////							"	from tb_cto_factura a, tb_producto b where a.nukid_producto=b.nukid_producto and a.nukid_factura=" + factura.getIdDetalleComprobante()+ " and" +
////							" a.nukid_producto= " + idsProductos.get(i)+ " group by a.nukid_factura, a.chdescripcion, b.chkclave_producto, b.chunidad_salida,a.nuprecio_unitario, a.nuiva_unitario");
//					
//					
//					rs = stm.executeQuery("select Id_Usuario from Tb_Usuario where id_Usuario in (SELECT Id_Usuario  FROM Tb_Perfil_Tb_rol  where Id_Perfil = (SELECT Id_Perfil  FROM Tb_Perfil_Tb_rol  WHERE Id_Usuario = "+id_User+") and id_Corporativo = "+id_Corp+" ) and Autoriza_Area = 1 ");
//					
//					
//					TbUsuario uso = new TbUsuario();
//
//					while (rs.next()) {
//						uso.setIdUsuario(rs.getInt(1));
////						uso.setCUsoCfdi(rs.getString(2));
////						uso.setDescripcion(rs.getString(3));
//						
//						RolCliente.add(uso.getIdUsuario()+"");
//
//					}
//
////				}
//
//
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return RolCliente;
//		}
//		
//		public List<String> getUsuarioProveedorUser1()
//		{
//			Session s = null;
//			Query q = null;
//			TbUsuario gc;
//			List<String> RolCliente = new ArrayList<String>();
////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
//				q = s.createQuery("from TbUsuario p where p.status= 1");
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbUsuario)exQ.get(i);
////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
//						RolCliente.add(gc.getIdUsuario()+"");
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return RolCliente;
//
//		}
//		
//		public List<String> getUsuarioProveedorUser(Integer id_Corp, Integer id_User) 
//		{
//			Session s = null;
////			List<TbUso> detalles = new ArrayList<TbUso>();
////			TbUsuario gc;
////			List<TbUsuario> RolCliente = new ArrayList<TbUsuario>();
//			List<String> RolCliente = new ArrayList<String>();
//			Statement stm = null;
//			ResultSet rs = null;
//			
////			List<ComboFactory> PerfilCliente = new ArrayList<ComboFactory>();
////			PerfilCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try {
//				s = HibernateUtil.currentSession();
//
////				List lista = s.createQuery("select p.idProductoComprobante from TbProductoComprobante p where p.idDetalleComprobante =  " + factura.getIdDetalleComprobante()).list();
//	//
////				for (int i = 0 ; i<lista.size() ; i++){
////					int idProducto = (Integer) lista.get(i) ;
////					logger.info("distintos: " + idProducto);
////					idsProductos.add(idProducto);
////				}
//				
//
//				stm = s.connection().createStatement();
//
////				for (int  i = 0 ; i <idsProductos.size() ; i++) {
////					rs = stm.executeQuery("select sum (a.nucantidad),sum (a.nuimporte), a.chdescripcion, b.chkclave_producto, b.chunidad_salida,sum (a.nuentregado),a.nuprecio_unitario,a.nuiva_unitario,sum(nuiva_total)" +
////							"	from tb_cto_factura a, tb_producto b where a.nukid_producto=b.nukid_producto and a.nukid_factura=" + factura.getIdDetalleComprobante()+ " and" +
////							" a.nukid_producto= " + idsProductos.get(i)+ " group by a.nukid_factura, a.chdescripcion, b.chkclave_producto, b.chunidad_salida,a.nuprecio_unitario, a.nuiva_unitario");
//					
//					
//					rs = stm.executeQuery("select Id_Usuario from Tb_Usuario where id_Usuario in (SELECT Id_Usuario  FROM Tb_Perfil_Tb_rol  where Id_Perfil = (SELECT Id_Perfil  FROM Tb_Perfil_Tb_rol  WHERE Id_Usuario = "+id_User+") and id_Corporativo = "+id_Corp+" ) and status= 1 ");
//					
//					
//					TbUsuario uso = new TbUsuario();
//
//					while (rs.next()) {
//						uso.setIdUsuario(rs.getInt(1));
////						uso.setCUsoCfdi(rs.getString(2));
////						uso.setDescripcion(rs.getString(3));
//						
//						RolCliente.add(uso.getIdUsuario()+"");
//
//					}
//
////				}
//
//
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return RolCliente;
//		}
//		
////		public List<String> getUsuarioProveedorRFC(String RFC)
////		{
////			Session s = null;
////			Query q = null;
////			TbRfc gc;
////			List<String> RolCliente = new ArrayList<String>();
//////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbRfc p where p.status= 1 and chRfc like '%"+RFC+"%'");
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbRfc)exQ.get(i);
//////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
////						RolCliente.add(gc.getIdRfc()+"");
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return RolCliente;
////
////		}
////		
////		public List<String> getUsuarioProveedorSAP(String RFC)
////		{
////			Session s = null;
////			Query q = null;
////			TbRfc gc;
////			List<String> RolCliente = new ArrayList<String>();
//////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbRfc p where p.status= 1 and numSAP like '%"+RFC+"%'");
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbRfc)exQ.get(i);
//////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
////						RolCliente.add(gc.getIdRfc()+"");
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return RolCliente;
////
////		}
////		
////		public List<String> getUsuarioProveedorPersona(String Campo,String Valor)
////		{
////			Session s = null;
////			Query q = null;
////			TbPersona gc;
////			List<String> RolCliente = new ArrayList<String>();
//////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbPersona p where p.status= 1 and "+Campo+" like '%"+Valor+"%'");
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbPersona)exQ.get(i);
//////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
////						RolCliente.add(gc.getTbUsuario().getIdUsuario()+"");
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return RolCliente;
////
////		}
////		
////		public List<String> gettbRfcUSerUser(int id_corp,String id_RFC)
////		{
////			Session s = null;
////			Query q = null;
////			TbRfcTbUsuario gc;
////			List<String> RolCliente = new ArrayList<String>();
//////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbRfcTbUsuario s where s.tbCorporativo.idCorporativo="+id_corp + " and s.tbRfc.idRfc in ("+id_RFC+")");
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbRfcTbUsuario)exQ.get(i);
//////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
////						RolCliente.add(gc.getTbUsuario().getIdUsuario()+"");
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return RolCliente;
////
////		}
////		
////		public List<String> gettbPersona(int id_corp,String id_RFC,String Campo)
////		{
////			Session s = null;
////			Query q = null;
////			TbPersona gc;
////			List<String> RolCliente = new ArrayList<String>();
//////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbPersona s where s.tbCorporativo.idCorporativo="+id_corp + " and s."+Campo+" like '%"+id_RFC+"%'");
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbPersona)exQ.get(i);
//////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
////						RolCliente.add(gc.getTbUsuario().getIdUsuario()+"");
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return RolCliente;
////
////		}
//		
////		@SuppressWarnings("unchecked")
////		public TbRfcTbUsuario gettbRfcUSerUser(int id_corp,int id_user)
////		{
////			TbRfcTbUsuario l_obUser = new TbRfcTbUsuario();
////			Session l_obSession = null;
////			String l_stQry = "";
////			List l_obResult = null;
////
////			try
////			{qwds
////				l_obSession = HibernateUtil.currentSession();
////				l_stQry = "from TbRfcTbUsuario s where s.tbCorporativo.idCorporativo="+id_corp + " and s.tbRfc.idRfc="+id_user;
////				l_obResult = l_obSession.createQuery(l_stQry).list();
////				if(l_obResult.size()>0)
////					l_obUser = (TbRfcTbUsuario)l_obResult.get(0);
////			} 
////			catch (HibernateException e) {
////				logger.error(e.getMessage());
////				return null;
////			}
////			finally{
////				if(l_obSession != null)
////					HibernateUtil.closeSession();
////			}
////			return l_obUser;
////			
////		}	//getUserByLoginname
//		
////		public TbMoneda gettbMoneda(String id_Moneda) {
////
////			Session s = null;
////			TbMoneda persona = new TbMoneda();
////			try
////			{
////				s = HibernateUtil.currentSession();
////				persona = (TbMoneda) s.createQuery("from TbMoneda p where p.idMoneda="+id_Moneda+"").uniqueResult();
////			}
////			catch (HibernateException e) {
////
////				logger.error(e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return persona;
////		}
//		
//		public boolean getUsuarioGFI(Integer id_Corp) 
//		{
//			Session s = null;
////			List<String> RolCliente = new ArrayList<String>();
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
////				for (int  i = 0 ; i <idsProductos.size() ; i++) {
////					rs = stm.executeQuery("select sum (a.nucantidad),sum (a.nuimporte), a.chdescripcion, b.chkclave_producto, b.chunidad_salida,sum (a.nuentregado),a.nuprecio_unitario,a.nuiva_unitario,sum(nuiva_total)" +
////							"	from tb_cto_factura a, tb_producto b where a.nukid_producto=b.nukid_producto and a.nukid_factura=" + factura.getIdDetalleComprobante()+ " and" +
////							" a.nukid_producto= " + idsProductos.get(i)+ " group by a.nukid_factura, a.chdescripcion, b.chkclave_producto, b.chunidad_salida,a.nuprecio_unitario, a.nuiva_unitario");
//					
//					
//					rs = stm.executeQuery("select count(Id_Usuario) from Tb_Usuario where status= 1 and nuCorreoGFI = 1 and id_corporativo =" +id_Corp);
//					
//					
//					TbUsuario uso = new TbUsuario();
//
//					while (rs.next()) {
//						uso.setIdUsuario(rs.getInt(1));
////						uso.setCUsoCfdi(rs.getString(2));
////						uso.setDescripcion(rs.getString(3));
//						
////						RolCliente.add(uso.getIdUsuario()+"");
//						if(uso.getIdUsuario() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//
////				}
//
//
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return valor_Registro;
//		}
//		
//		public Integer getUsuarioGFI_Id_User(Integer id_Corp) 
//		{
//			Session s = null;
////			List<String> RolCliente = new ArrayList<String>();
//			Statement stm = null;
//			ResultSet rs = null;
//			int valor_Registro = 0;
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
////				for (int  i = 0 ; i <idsProductos.size() ; i++) {
////					rs = stm.executeQuery("select sum (a.nucantidad),sum (a.nuimporte), a.chdescripcion, b.chkclave_producto, b.chunidad_salida,sum (a.nuentregado),a.nuprecio_unitario,a.nuiva_unitario,sum(nuiva_total)" +
////							"	from tb_cto_factura a, tb_producto b where a.nukid_producto=b.nukid_producto and a.nukid_factura=" + factura.getIdDetalleComprobante()+ " and" +
////							" a.nukid_producto= " + idsProductos.get(i)+ " group by a.nukid_factura, a.chdescripcion, b.chkclave_producto, b.chunidad_salida,a.nuprecio_unitario, a.nuiva_unitario");
//					
//					
//					rs = stm.executeQuery("select Id_Usuario from Tb_Usuario where status= 1 and nuCorreoGFI = 1 and id_corporativo =" +id_Corp);
//					
//					
//					TbUsuario uso = new TbUsuario();
//
//					while (rs.next()) {
//						uso.setIdUsuario(rs.getInt(1));
////						uso.setCUsoCfdi(rs.getString(2));
////						uso.setDescripcion(rs.getString(3));
//						
////						RolCliente.add(uso.getIdUsuario()+"");
//						valor_Registro = rs.getInt(1);
//					}
//
////				}
//
//
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return valor_Registro;
//		}
//		
////		public String getUsuarioGFI_Correo(Integer id_Corp, Integer id_Usuario) 
////		{
////			Session s = null;
//////			List<String> RolCliente = new ArrayList<String>();
////			Statement stm = null;
////			ResultSet rs = null;
////			String valor_Registro = "";
////			try {
////				s = HibernateUtil.currentSession();
////
////
////				stm = s.connection().createStatement();
////
//////				for (int  i = 0 ; i <idsProductos.size() ; i++) {
//////					rs = stm.executeQuery("select sum (a.nucantidad),sum (a.nuimporte), a.chdescripcion, b.chkclave_producto, b.chunidad_salida,sum (a.nuentregado),a.nuprecio_unitario,a.nuiva_unitario,sum(nuiva_total)" +
//////							"	from tb_cto_factura a, tb_producto b where a.nukid_producto=b.nukid_producto and a.nukid_factura=" + factura.getIdDetalleComprobante()+ " and" +
//////							" a.nukid_producto= " + idsProductos.get(i)+ " group by a.nukid_factura, a.chdescripcion, b.chkclave_producto, b.chunidad_salida,a.nuprecio_unitario, a.nuiva_unitario");
////					
////					
////					rs = stm.executeQuery("select chEmail from Tb_Persona where id_corporativo =" +id_Corp +" and Id_Usuario ="+id_Usuario);
////					
////					
////					TbPersona uso = new TbPersona();
////
////					while (rs.next()) {
////						uso.setChEmail(rs.getString(1));
//////						uso.setCUsoCfdi(rs.getString(2));
//////						uso.setDescripcion(rs.getString(3));
////						
//////						RolCliente.add(uso.getIdUsuario()+"");
////						valor_Registro = rs.getString(1);
////					}
////
//////				}
////
////
////			} catch (Exception e) {
////				logger.error("" + e.getMessage());
////				e.printStackTrace();
////			} finally {
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////			return valor_Registro;
////		}
////		
////				
////		public List<String> gettbPersonaCxP(int id_Perfil)
////		{
////			Session s = null;
////			Query q = null;
////			TbPerfilTbRol gc;
////			List<String> RolCliente = new ArrayList<String>();
//////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
////			try
////			{
////				s = HibernateUtil.currentSession();
////				q = s.createQuery("from TbPerfilTbRol p where p.tbPerfil.idPerfil ="+ id_Perfil + " and p.tbRol.idRol ="+10);
////				List exQ= q.list();
////				if(exQ.size()>0)
////					for(int i=0;i<exQ.size();i++)
////					{
////						gc = (TbPerfilTbRol)exQ.get(i);
//////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
////						RolCliente.add(gc.getTbUsuario().getIdUsuario()+"");
////					}
////			}
////			catch (HibernateException e) {
////				logger.error("" + e.getMessage());
////			}
////			finally
////			{
////				if(s!=null)
////					HibernateUtil.closeSession();
////			}
////
////			return RolCliente;
////
////		}
////		
////		public TbComprobantes getComprobantes(Integer id_Corporativo) throws ClassNotFoundException
////		{
////			Properties email_props = new Properties();
////			InputStream is = null;
////			
////			try {
////				
////			    is = getClass().getResourceAsStream("/email.properties");
////			    email_props.load(is);
////			    
////			} 
////			catch(Exception e) 
////			{
////				e.printStackTrace();
////			}
////			
////			Statement stm = null;
////			Session l_session = null;
////			Transaction tx = null;
////			String qry = "";
////			
////			 TbComprobantes compro = new TbComprobantes();
////			
////			List<TbComprobantes> detalles = new ArrayList<TbComprobantes>();
////			
////			Connection con = null;
////			 ResultSet resultSet = null;
////			//Class.forName(sDriver).newInstance(); 
////			//con = DriverManager.getConnection(sURL,"sginvoice","12121212qw");
////			
////			try {
////				System.out.println("Conexion: "+ email_props.getProperty("conexion"));
//////				 String connectionUrl = "jdbc:sqlserver://127.0.0.1:1433;" +
//////				            "databaseName=Rolex4;user=sa; password=12121212qw;"; 
////				String connectionUrl = email_props.getProperty("conexion");
////				
////				System.out.println("connectionUrl: "+ connectionUrl);
////				 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
////			        con = DriverManager.getConnection(connectionUrl);
////				  System.out.println("Conectado.");
////				  Statement statement = con.createStatement();
////				  
////				  String selectSql = "SELECT TOP 10 folio, serie from CE_comprobantes";
////		            resultSet = statement.executeQuery(selectSql);
////
////		            // Print results from select statement
////		           
////		            while (resultSet.next()) {
////		                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
////		                
////		                compro.setFolio(resultSet.getString(1));
////		                compro.setSerie(resultSet.getString(2));
////		            }
////		            
////		            detalles.add(compro);
////				} 
////				catch (SQLException ex) 
////				{
////				  System.out.println("Error.");
////				}
////			return compro;
////			
////
////			
////				
////
//////			        try (Connection connection = DriverManager.getConnection(connectionUrl);
//////			                Statement statement = connection.createStatement();) {
////
////			            // Create and execute a SELECT SQL statement.
//////			            String selectSql = "SELECT TOP 10 Title, FirstName, LastName from SalesLT.Customer";
//////			            resultSet = statement.executeQuery(selectSql);
////	//
//////			            // Print results from select statement
//////			            while (resultSet.next()) {
//////			                System.out.println(resultSet.getString(2) + " " + resultSet.getString(3));
//////			            }
//////			        }
//////			        catch (SQLException e) {
//////			            e.printStackTrace();
//////			        }
////		
////			
////		}
//		
//		public Integer getUsuarioUserCount(Integer id_Corp) 
//		{
//			Session s = null;
////			List<String> RolCliente = new ArrayList<String>();
//			Statement stm = null;
//			ResultSet rs = null;
//			int valor_Registro = 0;
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
////				for (int  i = 0 ; i <idsProductos.size() ; i++) {
////					rs = stm.executeQuery("select sum (a.nucantidad),sum (a.nuimporte), a.chdescripcion, b.chkclave_producto, b.chunidad_salida,sum (a.nuentregado),a.nuprecio_unitario,a.nuiva_unitario,sum(nuiva_total)" +
////							"	from tb_cto_factura a, tb_producto b where a.nukid_producto=b.nukid_producto and a.nukid_factura=" + factura.getIdDetalleComprobante()+ " and" +
////							" a.nukid_producto= " + idsProductos.get(i)+ " group by a.nukid_factura, a.chdescripcion, b.chkclave_producto, b.chunidad_salida,a.nuprecio_unitario, a.nuiva_unitario");
//					
//					
//					rs = stm.executeQuery("select count(Id_Usuario) from Tb_Usuario where id_corporativo =" +id_Corp);
//					
//					
//					TbUsuario uso = new TbUsuario();
//
//					while (rs.next()) {
//						uso.setIdUsuario(rs.getInt(1));
////						uso.setCUsoCfdi(rs.getString(2));
////						uso.setDescripcion(rs.getString(3));
//						
////						RolCliente.add(uso.getIdUsuario()+"");
//						valor_Registro = rs.getInt(1);
//					}
//
////				}
//
//
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return valor_Registro;
//		}
//		
//		
//		public List<ComboFactory> getEmpaque()
//		{
//			Session s = null;
//			Query q = null;
//			TbCatEmpaques gc;
//			List<ComboFactory> PerfilCliente = new ArrayList<ComboFactory>();
//			PerfilCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
//				q = s.createQuery("from TbCatEmpaques g where g.nustatus=1 " +
//						"order by chSkuempaque");
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbCatEmpaques)exQ.get(i);
////						PerfilCliente.add(new ComboFactory(gc.getNukIdEmpaques(), gc.getChSkuempaque()+" - "+ gc.getChDescripcion()));
//						PerfilCliente.add(new ComboFactory(gc.getNukIdEmpaques(), gc.getChSkuempaque()));
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return PerfilCliente;
//
//		}
//		
//		public static List<ComboFactory> getEmpaqueID(int nombre)
//		{
//			Session s = null;
//			Query q = null;
//			TbCatEmpaques gc;
//			List<ComboFactory> PerfilCliente = new ArrayList<ComboFactory>();
//			PerfilCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
//				q = s.createQuery("from TbCatEmpaques g where nukIdEmpaques ="+nombre+ " and g.nustatus=1 " +
//						"order by chSkuempaque");
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbCatEmpaques)exQ.get(i);
////						PerfilCliente.add(new ComboFactory(gc.getNukIdEmpaques(), gc.getChSkuempaque()+" - "+ gc.getChDescripcion()));
//						PerfilCliente.add(new ComboFactory(gc.getNukIdEmpaques(), gc.getChSkuempaque()));
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return PerfilCliente;
//
//		}
//		
//		public List<ComboFactory> getHouse()
//		{
//			Session s = null;
//			Query q = null;
//			TbCatHouse gc;
//			List<ComboFactory> PerfilCliente = new ArrayList<ComboFactory>();
//			PerfilCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
//				q = s.createQuery("from TbCatHouse g where g.nustatus=1 " +
//						"order by chSkuhause");
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbCatHouse)exQ.get(i);
////						PerfilCliente.add(new ComboFactory(gc.getNukIdEmpaques(), gc.getChSkuempaque()+" - "+ gc.getChDescripcion()));
//						PerfilCliente.add(new ComboFactory(gc.getNukIdHouse(), gc.getChSkuhause()));
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return PerfilCliente;
//
//		}
//		
//		public boolean getProdSKU_MP(String SKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select count(nukIdProductos) from Tb_Productos where nustatus= 1 and chSKU ='" +SKU+"'");
//					
//					System.out.println("select count(nukIdProductos) from Tb_Productos where nustatus= 1 and chSKU ='" +SKU+"'");
//					
//					
//					TbProductos uso = new TbProductos();
//
//					while (rs.next()) {
//						uso.setNukIdProductos(rs.getInt(1));
//						if(uso.getNukIdProductos() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return valor_Registro;
//		}
//		
		public int getProdSKU_PT(String SKU) 
		{
			Session s = null;
			Statement stm = null;
			ResultSet rs = null;
			boolean valor_Registro = false;
			int ValorId = 0;
			try {
				s = HibernateUtil.currentSession();


				stm = s.connection().createStatement();

					rs = stm.executeQuery("select nukIdProdTerm from Tb_Producto_Terminado where nuActivo= 1 and chSKU ='" +SKU+"'");
					
					
					TbProductoTerminado uso = new TbProductoTerminado();

					while (rs.next()) {
						uso.setNukIdProdTerm(rs.getInt(1));
						ValorId = uso.getNukIdProdTerm();
//						if(uso.getNukIdProdTerm() != 0){
//							valor_Registro = true;
//						}else{
//							valor_Registro = false;
//						}
						
					}
			} catch (Exception e) {
				logger.error("" + e.getMessage());
				e.printStackTrace();
			} finally {
				if(s!=null)
					HibernateUtil.closeSession();
			}
			return ValorId;
		}
//		
//		public boolean getUbiSKU_MP(String SKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select count(nukIdUbicaMatPrim) from Tb_Ubicacion_MateriaPrim where nustatus= 1 and chSKU ='" +SKU+"'");
//					
//					System.out.println("select count(nukIdUbicaMatPrim) from Tb_Ubicacion_MateriaPrim where nustatus= 1 and chSKU ='" +SKU+"'");
//					
//					TbUbicacionMateriaPrim uso = new TbUbicacionMateriaPrim();
//
//					while (rs.next()) {
//						uso.setNukIdUbicaMatPrim(rs.getInt(1));
//						if(uso.getNukIdUbicaMatPrim() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return valor_Registro;
//		}
//		
//		public boolean getUbiSKU_PT(String SKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select count(nukIdUbicaTerm) from Tb_Ubicacion_Terminados where nustatus= 1 and chSKU ='" +SKU+"'");
//					
//					
//					TbUbicacionTerminados uso = new TbUbicacionTerminados();
//
//					while (rs.next()) {
//						uso.setNukIdUbicaTerm(rs.getInt(1));
//						if(uso.getNukIdUbicaTerm() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return valor_Registro;
//		}
//		
//		public boolean getCatEmpaque(String SKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select count(nukIdEmpaques) from Tb_Cat_Empaques where nustatus= 1 and chSKUEmpaque ='" +SKU+"'");
//					
//					
//					TbCatEmpaques uso = new TbCatEmpaques();
//
//					while (rs.next()) {
//						uso.setNukIdEmpaques(rs.getInt(1));
//						if(uso.getNukIdEmpaques() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return valor_Registro;
//		}
//		
//		@SuppressWarnings("unchecked")
//		public TbProductos getProductoMPBySKU1(String p_stLogin)
//		{
//			TbProductos l_obUser = new TbProductos();
//			Session l_obSession = null;
//			String l_stQry = "";
//			List l_obResult = null;
//
//			try
//			{
//				l_obSession = HibernateUtil.currentSession();
//				l_stQry = "from TbProductos s where s.chSKU='" + p_stLogin + "' and nustatus=1";
//				l_obResult = l_obSession.createQuery(l_stQry).list();
//				if(l_obResult.size()>0)
//					l_obUser = (TbProductos)l_obResult.get(0);
//			} 
//			catch (HibernateException e) {
//				logger.error(e.getMessage());
//				return null;
//			}
//			finally{
//				if(l_obSession != null)
//					HibernateUtil.closeSession();
//			}
//			return l_obUser;
//			
//		}	//getUserByLoginname
//		
//		public TbProductos getProductoMPBySKU(String SKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbProductos uso = new TbProductos();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdProductos from Tb_Productos where nustatus= 1 and chSKU ='" +SKU+"'");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdProductos(rs.getInt(1));
//						if(uso.getNukIdProductos() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public TbProductosTerminados getProductoPTBySKU(String SKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbProductosTerminados uso = new TbProductosTerminados();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdProductosTerminados from Tb_Productos_Terminados where nustatus= 1 and chSKU ='" +SKU+"'");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdProductosTerminados(rs.getInt(1));
//						if(uso.getNukIdProductosTerminados() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		@SuppressWarnings("unchecked")
//		public TbUbicacionMateriaPrim getUbicacionMPBySKU1(String p_stLogin)
//		{
//			TbUbicacionMateriaPrim l_obUser = new TbUbicacionMateriaPrim();
//			Session l_obSession = null;
//			String l_stQry = "";
//			List l_obResult = null;
//
//			try
//			{
//				l_obSession = HibernateUtil.currentSession();
//				l_stQry = "from TbUbicacionMateriaPrim s where s.chSKU='" + p_stLogin + "' and nustatus=1";
//				l_obResult = l_obSession.createQuery(l_stQry).list();
//				if(l_obResult.size()>0)
//					l_obUser = (TbUbicacionMateriaPrim)l_obResult.get(0);
//			} 
//			catch (HibernateException e) {
//				logger.error(e.getMessage());
//				return null;
//			}
//			finally{
//				if(l_obSession != null)
//					HibernateUtil.closeSession();
//			}
//			return l_obUser;
//			
//		}	//getUserByLoginname
//		
//		public TbUbicacionMateriaPrim getUbicacionMPBySKU(String SKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbUbicacionMateriaPrim uso = new TbUbicacionMateriaPrim();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdUbicaMatPrim from Tb_Ubicacion_MateriaPrim where nustatus= 1 and chSKU ='" +SKU+"'");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdUbicaMatPrim(rs.getInt(1));
//						if(uso.getNukIdUbicaMatPrim() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public TbEmpresa getEmpresaByIDA(int SKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbEmpresa uso = new TbEmpresa();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdEmpresa from Tb_Empresa where nustatus= 1 and nukIdEmpresa =" +SKU);
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdEmpresa(rs.getInt(1));
//						if(uso.getNukIdEmpresa() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public TbUbicacionTerminados getUbicacionPTBySKU(String SKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbUbicacionTerminados uso = new TbUbicacionTerminados();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdUbicaTerm from Tb_Ubicacion_Terminados where nustatus= 1 and chSKU ='" +SKU+"'");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdUbicaTerm(rs.getInt(1));
//						if(uso.getNukIdUbicaTerm() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		@SuppressWarnings("unchecked")
//		public TbCatHouse getHouse(String p_stLogin)
//		{
//			TbCatHouse l_obUser = new TbCatHouse();
//			Session l_obSession = null;
//			String l_stQry = "";
//			List l_obResult = null;
//
//			try
//			{
//				l_obSession = HibernateUtil.currentSession();
//				l_stQry = "from TbCatHouse s where s.chSkuhause='" + p_stLogin + "' and nustatus=1";
//				l_obResult = l_obSession.createQuery(l_stQry).list();
//				if(l_obResult.size()>0)
//					l_obUser = (TbCatHouse)l_obResult.get(0);
//			} 
//			catch (HibernateException e) {
//				logger.error(e.getMessage());
//				return null;
//			}
//			finally{
//				if(l_obSession != null)
//					HibernateUtil.closeSession();
//			}
//			return l_obUser;
//			
//		}	//getUserByLoginname
//		
////		@SuppressWarnings("unchecked")
////		public TbProductosTerminados getProductoPTBySKU(String p_stLogin)
////		{
////			TbProductosTerminados l_obUser = new TbProductosTerminados();
////			Session l_obSession = null;
////			String l_stQry = "";
////			List l_obResult = null;
////
////			try
////			{
////				l_obSession = HibernateUtil.currentSession();
////				l_stQry = "from TbProductosTerminados s where s.chSKU='" + p_stLogin + "' and nustatus=1";
////				l_obResult = l_obSession.createQuery(l_stQry).list();
////				if(l_obResult.size()>0)
////					l_obUser = (TbProductosTerminados)l_obResult.get(0);
////			} 
////			catch (HibernateException e) {
////				logger.error(e.getMessage());
////				return null;
////			}
////			finally{
////				if(l_obSession != null)
////					HibernateUtil.closeSession();
////			}
////			return l_obUser;
////			
////		}	//getUserByLoginname
//		
////		@SuppressWarnings("unchecked")
////		public TbUbicacionTerminados getUbicacionPTBySKU(String p_stLogin)
////		{
////			TbUbicacionTerminados l_obUser = new TbUbicacionTerminados();
////			Session l_obSession = null;
////			String l_stQry = "";
////			List l_obResult = null;
////
////			try
////			{
////				l_obSession = HibernateUtil.currentSession();
////				l_stQry = "from TbUbicacionTerminados s where s.chSKU='" + p_stLogin + "' and nustatus=1";
////				l_obResult = l_obSession.createQuery(l_stQry).list();
////				if(l_obResult.size()>0)
////					l_obUser = (TbUbicacionTerminados)l_obResult.get(0);
////			} 
////			catch (HibernateException e) {
////				logger.error(e.getMessage());
////				return null;
////			}
////			finally{
////				if(l_obSession != null)
////					HibernateUtil.closeSession();
////			}
////			return l_obUser;
////			
////		}	//getUserByLoginname
//		
//		@SuppressWarnings("unchecked")
//		public TbCatEmpaques getEmpaques(String p_stLogin)
//		{
//			
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbCatEmpaques uso = new TbCatEmpaques();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdEmpaques,chSKUEmpaque from Tb_Cat_Empaques where nustatus = 1 and chSKUEmpaque ='" +p_stLogin+"'");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdEmpaques(rs.getInt(1));
//						uso.setChSkuempaque(rs.getString(2));
//						if(uso.getNukIdEmpaques() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
////			TbCatEmpaques l_obUser = new TbCatEmpaques();
////			Session l_obSession = null;
////			String l_stQry = "";
////			List l_obResult = null;
////
////			try
////			{
////				l_obSession = HibernateUtil.currentSession();
////				l_stQry = "from TbCatEmpaques s where s.chSkuempaque='" + p_stLogin + "' and nustatus=1";
////				l_obResult = l_obSession.createQuery(l_stQry).list();
////				if(l_obResult.size()>0)
////					l_obUser = (TbCatEmpaques)l_obResult.get(0);
////			} 
////			catch (HibernateException e) {
////				logger.error(e.getMessage());
////				return null;
////			}
////			finally{
////				if(l_obSession != null)
////					HibernateUtil.closeSession();
////			}
////			return l_obUser;
//			
//		}	//getUserByLoginname
//		
//		@SuppressWarnings("unchecked")
//		public TbCatEmpaques getEmpaquesId(int p_stLogin)
//		{
//			
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbCatEmpaques uso = new TbCatEmpaques();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdEmpaques,chSKUEmpaque from Tb_Cat_Empaques where nustatus = 1 and nukIdEmpaques =" +p_stLogin+"");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdEmpaques(rs.getInt(1));
//						uso.setChSkuempaque(rs.getString(2));
//						if(uso.getNukIdEmpaques() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
////			TbCatEmpaques l_obUser = new TbCatEmpaques();
////			Session l_obSession = null;
////			String l_stQry = "";
////			List l_obResult = null;
////
////			try
////			{
////				l_obSession = HibernateUtil.currentSession();
////				l_stQry = "from TbCatEmpaques s where s.chSkuempaque='" + p_stLogin + "' and nustatus=1";
////				l_obResult = l_obSession.createQuery(l_stQry).list();
////				if(l_obResult.size()>0)
////					l_obUser = (TbCatEmpaques)l_obResult.get(0);
////			} 
////			catch (HibernateException e) {
////				logger.error(e.getMessage());
////				return null;
////			}
////			finally{
////				if(l_obSession != null)
////					HibernateUtil.closeSession();
////			}
////			return l_obUser;
//			
//		}	//getUserByLoginname
//		
//		public synchronized Integer saveMP(TbInventarioDetalleMp anUser) throws Exception 
//		{
//			Session l_session = null;
//			Transaction tx = null;
//			Integer l_id = null;
//			
//			try
//			{
//				l_session = HibernateUtil.currentSession();
//				tx = l_session.beginTransaction();
//
//				// cuando se ingresa una tabla por primera vez el total de 
//				// registros que tiene es cero.
////				p_UserName.setNuindex(0);
//				l_session.save(anUser);
//				
//				tx.commit();
//				
//				l_id = anUser.getNukIdInventDetalleMp();
//			}
//			catch (Exception e) {
//				tx.rollback();
//				throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//			}
//			finally
//			{
//				if(l_session != null)
//					HibernateUtil.closeSession();
//				
//				tx = null;
//			}
//			
//			
//			return l_id;
//			
//		}	// save
//		
//		public synchronized Integer saveUpdateMP(TbInventarioDetalleMp anUser) throws Exception 
//		{
//			Session l_session = null;
//			Transaction tx = null;
//			Integer l_id = null;
//			
//			try
//			{
//				l_session = HibernateUtil.currentSession();
//				tx = l_session.beginTransaction();
//
//				// cuando se ingresa una tabla por primera vez el total de 
//				// registros que tiene es cero.
////				p_UserName.setNuindex(0);
//				l_session.update(anUser);
//				
//				tx.commit();
//				
//				l_id = anUser.getNukIdInventDetalleMp();
//			}
//			catch (Exception e) {
//				tx.rollback();
//				throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//			}
//			finally
//			{
//				if(l_session != null)
//					HibernateUtil.closeSession();
//				
//				tx = null;
//			}
//			
//			
//			return l_id;
//			
//		}	// save
//		
//		
//		
//		public synchronized Integer savePT(TbInventarioDetallePt anUser) throws Exception 
//		{
//			Session l_session = null;
//			Transaction tx = null;
//			Integer l_id = null;
//			
//			try
//			{
//				l_session = HibernateUtil.currentSession();
//				tx = l_session.beginTransaction();
//
//				// cuando se ingresa una tabla por primera vez el total de 
//				// registros que tiene es cero.
////				p_UserName.setNuindex(0);
//				l_session.save(anUser);
//				
//				tx.commit();
//				
//				l_id = anUser.getNukIdInventDetallePt();
//			}
//			catch (Exception e) {
//				tx.rollback();
//				throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//			}
//			finally
//			{
//				if(l_session != null)
//					HibernateUtil.closeSession();
//				
//				tx = null;
//			}
//			
//			
//			return l_id;
//			
//		}	// save
//		
//		public synchronized Integer saveUpdatePT(TbInventarioDetallePt anUser) throws Exception 
//		{
//			Session l_session = null;
//			Transaction tx = null;
//			Integer l_id = null;
//			
//			try
//			{
//				l_session = HibernateUtil.currentSession();
//				tx = l_session.beginTransaction();
//
//				// cuando se ingresa una tabla por primera vez el total de 
//				// registros que tiene es cero.
////				p_UserName.setNuindex(0);
//				l_session.update(anUser);
//				
//				tx.commit();
//				
//				l_id = anUser.getNukIdInventDetallePt();
//			}
//			catch (Exception e) {
//				tx.rollback();
//				throw new Exception(this.getClass().getName() + " - " + e.getMessage());
//			}
//			finally
//			{
//				if(l_session != null)
//					HibernateUtil.closeSession();
//				
//				tx = null;
//			}
//			
//			
//			return l_id;
//			
//		}	// save
//		
//		public boolean getInvDetalleMP(String ProdSKU, String UbiSKU,String FolioSKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//				System.out.println("Valor del Qry: " + "select count(IDMP.nukIdProductos) from Tb_InventarioDetalle_MP IDMP, Tb_Productos pMP, Tb_Ubicacion_MateriaPrim UMP where IDMP.nukIdProductos = pMP.nukIdProductos and IDMP.nukIdUbicaMatPrim = UMP.nukIdUbicaMatPrim and pMP.chSKU = '" +ProdSKU+"' and UMP.chSKU = '"+UbiSKU+"' and IDMP.chParte= '"+FolioSKU+"'");
//
//					rs = stm.executeQuery("select count(IDMP.nukIdProductos) from Tb_InventarioDetalle_MP IDMP, Tb_Productos pMP, Tb_Ubicacion_MateriaPrim UMP where IDMP.nukIdProductos = pMP.nukIdProductos and IDMP.nukIdUbicaMatPrim = UMP.nukIdUbicaMatPrim and pMP.chSKU = '" +ProdSKU+"' and UMP.chSKU = '"+UbiSKU+"' and IDMP.chParte= '"+FolioSKU+"'");
//					
//					
//					TbInventarioDetalleMp uso = new TbInventarioDetalleMp();
//
//					while (rs.next()) {
//						uso.setNukIdInventDetalleMp(rs.getInt(1));
//						if(uso.getNukIdInventDetalleMp() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return valor_Registro;
//		}
//		
//		public boolean getInvDetallePT(String ProdSKU, String UbiSKU,String FolioSKU) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//				System.out.println("Valor del Qry: " + "select count(IDMP.nukIdProductosTerminados) from Tb_InventarioDetalle_PT IDMP, Tb_Productos_Terminados pMP, Tb_Ubicacion_Terminados UMP where IDMP.nukIdProductosTerminados = pMP.nukIdProductosTerminados and IDMP.nukIdUbicaTerm = UMP.nukIdUbicaTerm and pMP.chSKU = '" +ProdSKU+"' and UMP.chSKU = '"+UbiSKU+"' and IDMP.chParte= '"+FolioSKU+"'");
//
//					rs = stm.executeQuery("select count(IDMP.nukIdProductosTerminados) from Tb_InventarioDetalle_PT IDMP, Tb_Productos_Terminados pMP, Tb_Ubicacion_Terminados UMP where IDMP.nukIdProductosTerminados = pMP.nukIdProductosTerminados and IDMP.nukIdUbicaTerm = UMP.nukIdUbicaTerm and pMP.chSKU = '" +ProdSKU+"' and UMP.chSKU = '"+UbiSKU+"' and IDMP.chParte= '"+FolioSKU+"'");
//					
//					
//					TbInventarioDetallePt uso = new TbInventarioDetallePt();
//
//					while (rs.next()) {
//						uso.setNukIdInventDetallePt(rs.getInt(1));
//						if(uso.getNukIdInventDetallePt() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return valor_Registro;
//		}
//		
//		public TbUsuario getIDUsuario(int id_user) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbUsuario uso = new TbUsuario();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select id_Usuario,username, pwd_history from Tb_Usuario where status= 1 and id_Usuario =" +id_user+"");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setIdUsuario(rs.getInt(1));
//						uso.setUsername(rs.getString(2));
//						uso.setPwdHistory(rs.getString(3));
//						if(uso.getIdUsuario() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public TbProductos getIDProductoMP(int id_user) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbProductos uso = new TbProductos();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdProductos,chSKU,chDescripcion,chUnidadMedida from Tb_Productos where nustatus= 1 and nukIdProductos =" +id_user+"");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdProductos(rs.getInt(1));
//						uso.setChSku(rs.getString(2));
//						uso.setChDescripcion(rs.getString(3));
//						uso.setChUnidadMedida(rs.getString(4));
//						if(uso.getNukIdProductos() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public TbProductosTerminados getIDProductoPT(int id_user) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbProductosTerminados uso = new TbProductosTerminados();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdProductosTerminados,chSKU,chDescripcion from Tb_Productos_Terminados where nustatus= 1 and nukIdProductosTerminados =" +id_user+"");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdProductosTerminados(rs.getInt(1));
//						uso.setChSku(rs.getString(2));
//						uso.setChDescripcion(rs.getString(3));
////						uso.setChUnidadMedida(rs.getString(4));
//						if(uso.getNukIdProductosTerminados() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public TbUbicacionMateriaPrim getIDUbicacionMP(int id_user) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbUbicacionMateriaPrim uso = new TbUbicacionMateriaPrim();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdUbicaMatPrim,chSKU from Tb_Ubicacion_MateriaPrim where nustatus= 1 and nukIdUbicaMatPrim =" +id_user+"");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdUbicaMatPrim(rs.getInt(1));
//						uso.setChSku(rs.getString(2));
//						//uso.setChDescripcion(rs.getString(3));
//						if(uso.getNukIdUbicaMatPrim() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public TbUbicacionTerminados getIDUbicacionPT(int id_user) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbUbicacionTerminados uso = new TbUbicacionTerminados();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdUbicaTerm,chSKU from Tb_Ubicacion_Terminados where nustatus= 1 and nukIdUbicaTerm =" +id_user+"");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdUbicaTerm(rs.getInt(1));
//						uso.setChSku(rs.getString(2));
//						//uso.setChDescripcion(rs.getString(3));
//						if(uso.getNukIdUbicaTerm() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public List<String> gettbUsuario(String id_RFC,String Campo)
//		{
//			Session s = null;
//			Query q = null;
//			TbUsuario gc;
//			List<String> RolCliente = new ArrayList<String>();
////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
//				q = s.createQuery("from TbUsuario s where s."+Campo+" like '%"+id_RFC+"%'");
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbUsuario)exQ.get(i);
////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
//						RolCliente.add(gc.getIdUsuario()+"");
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return RolCliente;
//
//		}
//		public TbUsuario getUsuarioByName(String name_user) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbUsuario uso = new TbUsuario();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select id_Usuario,username from Tb_Usuario where status= 1 and username LIKE '%" +name_user+"'%");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setIdUsuario(rs.getInt(1));
//						uso.setUsername(rs.getString(2));
//						if(uso.getIdUsuario() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public TbProductos getProdMPBySKU(String SKU_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbProductos uso = new TbProductos();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdProductos,chSKU,chDescripcion from Tb_Productos where nustatus= 1 and chSKU LIKE '%" +SKU_Prod+"'%");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdProductos(rs.getInt(1));
//						uso.setChSku(rs.getString(2));
//						uso.setChDescripcion(rs.getString(3));
//						if(uso.getNukIdProductos() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public TbEmpresa getEmpresaByID(int SKU_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbEmpresa uso = new TbEmpresa();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdEmpresa,chClave_Empresa,chEmpresa from Tb_Empresa where nustatus= 1 and nukIdEmpresa =" +SKU_Prod);
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdEmpresa(rs.getInt(1));
//						uso.setChClaveEmpresa(rs.getString(2));
//						uso.setChEmpresa(rs.getString(3));
//						if(uso.getNukIdEmpresa() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public List<String> gettbProd(String id_RFC,String Campo)
//		{
//			Session s = null;
//			Query q = null;
//			TbProductos gc;
//			List<String> RolCliente = new ArrayList<String>();
////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
//				q = s.createQuery("from TbProductos s where s."+Campo+" like '%"+id_RFC+"%'");
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbProductos)exQ.get(i);
////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
//						RolCliente.add(gc.getNukIdProductos()+"");
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return RolCliente;
//
//		}
//		
//		public List<String> gettbProdTerm(String id_RFC,String Campo)
//		{
//			Session s = null;
//			Query q = null;
//			TbProductosTerminados gc;
//			List<String> RolCliente = new ArrayList<String>();
////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
//				q = s.createQuery("from TbProductosTerminados s where s."+Campo+" like '%"+id_RFC+"%'");
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbProductosTerminados)exQ.get(i);
////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
//						RolCliente.add(gc.getNukIdProductosTerminados()+"");
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return RolCliente;
//
//		}
//		
//		public TbUbicacionMateriaPrim getUbicacionMPbYsku(String sku_ubi) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			boolean valor_Registro = false;
//			TbUbicacionMateriaPrim uso = new TbUbicacionMateriaPrim();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nukIdUbicaMatPrim,chSKU from Tb_Ubicacion_MateriaPrim where nustatus= 1 and chSKU =" +sku_ubi+"");
//					
//					
//					
//
//					while (rs.next()) {
//						uso.setNukIdUbicaMatPrim(rs.getInt(1));
//						uso.setChSku(rs.getString(2));
//						//uso.setChDescripcion(rs.getString(3));
//						if(uso.getNukIdUbicaMatPrim() == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return uso;
//		}
//		
//		public List<String> gettbProdUbi(String id_RFC,String Campo)
//		{
//			Session s = null;
//			Query q = null;
//			TbUbicacionMateriaPrim gc;
//			List<String> RolCliente = new ArrayList<String>();
////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
//				q = s.createQuery("from TbUbicacionMateriaPrim s where s."+Campo+" like '%"+id_RFC+"%'");
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbUbicacionMateriaPrim)exQ.get(i);
////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
//						RolCliente.add(gc.getNukIdUbicaMatPrim()+"");
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return RolCliente;
//
//		}
//		
//		public List<String> gettbProdUbiTerm(String id_RFC,String Campo)
//		{
//			Session s = null;
//			Query q = null;
//			TbUbicacionTerminados gc;
//			List<String> RolCliente = new ArrayList<String>();
////			RolCliente.add(new ComboFactory(0, "SELECCIONE"));
//			try
//			{
//				s = HibernateUtil.currentSession();
//				q = s.createQuery("from TbUbicacionTerminados s where s."+Campo+" like '%"+id_RFC+"%'");
//				List exQ= q.list();
//				if(exQ.size()>0)
//					for(int i=0;i<exQ.size();i++)
//					{
//						gc = (TbUbicacionTerminados)exQ.get(i);
////						System.out.println("gc.getTbUsuario().getIdUsuario(): " +gc.getTbUsuario().getIdUsuario());
//						RolCliente.add(gc.getNukIdUbicaTerm()+"");
//					}
//			}
//			catch (HibernateException e) {
//				logger.error("" + e.getMessage());
//			}
//			finally
//			{
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//
//			return RolCliente;
//
//		}
//		
//		/**
//		 * Obtiene los distintos de detalles de la factura y quellos que son el mismo los agrupa
//		 * @param factura
//		 * @return
//		 */
//		public List<BeanInvMP> getOrdenCompraExcel(String P_Condicion) 
//		{
//			String realString = Sessions.getCurrent().getWebApp().getRealPath("Reporte_de_InventarioFisico.xls");
//			
//			Session s = null;
//			List<BeanInvMP> detalles = new ArrayList<BeanInvMP>();
////			List<Integer> idsProductos = new ArrayList<Integer>();
//			Statement stm = null;
//			ResultSet rs = null;
//			try {
//				s = HibernateUtil.currentSession();
//	
////				List lista = s.createQuery("select p.idProductoComprobante from TbProductoComprobante p where p.idDetalleComprobante =  " + factura.getIdDetalleComprobante()).list();
//	//
////				for (int i = 0 ; i<lista.size() ; i++){
////					int idProducto = (Integer) lista.get(i) ;
////					logger.info("distintos: " + idProducto);
////					idsProductos.add(idProducto);
////				}
//	
//				stm = s.connection().createStatement();
//	
////				for (int  i = 0 ; i <idsProductos.size() ; i++) {
////					rs = stm.executeQuery("select sum (a.nucantidad),sum (a.nuimporte), a.chdescripcion, b.chkclave_producto, b.chunidad_salida,sum (a.nuentregado),a.nuprecio_unitario,a.nuiva_unitario,sum(nuiva_total)" +
////							"	from tb_cto_factura a, tb_producto b where a.nukid_producto=b.nukid_producto and a.nukid_factura=" + factura.getIdDetalleComprobante()+ " and" +
////							" a.nukid_producto= " + idsProductos.get(i)+ " group by a.nukid_factura, a.chdescripcion, b.chkclave_producto, b.chunidad_salida,a.nuprecio_unitario, a.nuiva_unitario");
//				
//				
//				if(P_Condicion.trim().equals("")){
//					System.out.println("vALOR DEL qRY ------------>>>>  " + "select pMP.chSKU, pMP.chDescripcion, UMP.chSKU, u.pwd_history, IDMP.Cantidad, IDMP.chParte,IDMP.chObservaciones,pMP.chUnidadMedida,pMP.nuCostoProm from Tb_InventarioDetalle_MP IDMP, Tb_Productos pMP, Tb_Ubicacion_MateriaPrim UMP,Tb_Usuario U where IDMP.nukIdProductos = pMP.nukIdProductos and IDMP.nukIdUbicaMatPrim = UMP.nukIdUbicaMatPrim and IDMP.id_Usuario = U.id_Usuario");
//					rs = stm.executeQuery("select pMP.chSKU, pMP.chDescripcion, UMP.chSKU, u.pwd_history, IDMP.Cantidad, IDMP.chParte,IDMP.chObservaciones,pMP.chUnidadMedida,pMP.nuCostoProm from Tb_InventarioDetalle_MP IDMP, Tb_Productos pMP, Tb_Ubicacion_MateriaPrim UMP,Tb_Usuario U where IDMP.nukIdProductos = pMP.nukIdProductos and IDMP.nukIdUbicaMatPrim = UMP.nukIdUbicaMatPrim and IDMP.id_Usuario = U.id_Usuario");
//				}else{
//					System.out.println("vALOR DEL qRY ------------>>>>  " + "select pMP.chSKU, pMP.chDescripcion, UMP.chSKU, u.pwd_history, IDMP.Cantidad, IDMP.chParte,IDMP.chObservaciones,pMP.chUnidadMedida,pMP.nuCostoProm from Tb_InventarioDetalle_MP IDMP, Tb_Productos pMP, Tb_Ubicacion_MateriaPrim UMP,Tb_Usuario U where IDMP.nukIdProductos = pMP.nukIdProductos and IDMP.nukIdUbicaMatPrim = UMP.nukIdUbicaMatPrim and IDMP.id_Usuario = U.id_Usuario");
//					rs = stm.executeQuery("select pMP.chSKU, pMP.chDescripcion, UMP.chSKU, u.pwd_history, IDMP.Cantidad, IDMP.chParte,IDMP.chObservaciones,pMP.chUnidadMedida,pMP.nuCostoProm from Tb_InventarioDetalle_MP IDMP, Tb_Productos pMP, Tb_Ubicacion_MateriaPrim UMP,Tb_Usuario U where IDMP.nukIdProductos = pMP.nukIdProductos and IDMP.nukIdUbicaMatPrim = UMP.nukIdUbicaMatPrim and IDMP.id_Usuario = U.id_Usuario "+P_Condicion);
//				}
//					
//					
//					
//					
////					TbDetalleComprobante ctoFactura = new TbDetalleComprobante();
////					TbProductoComprobante producto =  new TbProductoComprobante();
//					
//					 WritableWorkbook workbook =
//						    Workbook.createWorkbook(new File(realString));
//	
//						    //Se crea una nueva hoja dentro del libro
//						    WritableSheet sheet =
//						    workbook.createSheet("InventarioFisicoMP", 0);
//	
//	
//						    sheet.addCell(new jxl.write.Label(0, 0, "SKU PRODUCTO"));
//					        sheet.addCell(new jxl.write.Label(1, 0, "DESCRIPCION DEL PRODUCTO"));
//					        sheet.addCell(new jxl.write.Label(2, 0, "SKU UBICACION"));
//					        sheet.addCell(new jxl.write.Label(3, 0, "USUARIO"));
//					        sheet.addCell(new jxl.write.Label(4, 0, "CANTIDAD"));
//					        sheet.addCell(new jxl.write.Label(5, 0, "FOLIO"));
//					        sheet.addCell(new jxl.write.Label(6, 0, "OBSERVACIONES"));
//					        sheet.addCell(new jxl.write.Label(7, 0, "UNIDAD DE MEDIDA"));
//					        sheet.addCell(new jxl.write.Label(8, 0, "COSTO UNITARIO"));
//					        sheet.addCell(new jxl.write.Label(9, 0, "TOTAL MX"));
//					        sheet.addCell(new jxl.write.Label(10, 0, "FIFO"));
//					        sheet.addCell(new jxl.write.Label(11, 0, "HOUSE"));
//					        
//					        
//					                
//					        int r = 1;
//					        
//					BeanInvMP OC = new BeanInvMP();
////					prodser.setIdProductoServicio(idsProductos.get(i));
////					int indice = 1;
//					
//					ArrayList<String>List_SKU=new ArrayList<String>();
//					
//					while (rs.next()) {
//						List_SKU.add(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4)+"|"+rs.getString(5)+"|"+rs.getString(6)+"|"+rs.getString(7)+"|"+rs.getString(8)+"|"+rs.getString(9));
//						
//					
//					
//					}
//					
////					 WritableFont wfontStatus = new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
////					    WritableCellFormat fCellstatus = new WritableCellFormat(wfontStatus);
//					
//					 for (int i=0;i<List_SKU.size();i++) {
//						 
//						 DecimalFormat df = new DecimalFormat("#.00");
//						 
//						 	
//						 	
//						 	String[ ] arrayRegistros = List_SKU.get(i).split("\\|");
//
//					 		// Aqu� ya tenemos un array en el que cada elemento es un color.
//
//					 		for (int y = 0; y < arrayRegistros.length; y++) {
//					 		     System.out.println(arrayRegistros[y]);
//					 		    
//					 		       
//					 		       	
//					 		       
//					 		}
//					 		
//					 		System.out.println("arrayRegistros[0]"+ arrayRegistros[0]);
//				 		    sheet.addCell(new jxl.write.Label(0, r, arrayRegistros[0]));
//				 		   System.out.println("arrayRegistros[1]"+ arrayRegistros[1]);
//				 		    sheet.addCell(new jxl.write.Label(1, r, arrayRegistros[1]));
//				 		   System.out.println("arrayRegistros[2]"+ arrayRegistros[2]);
//				 		    sheet.addCell(new jxl.write.Label(2, r, arrayRegistros[2]));
//				 		   System.out.println("arrayRegistros[3]"+ arrayRegistros[3]);
//				 			sheet.addCell(new jxl.write.Label(3, r, arrayRegistros[3]));
//				 			System.out.println("arrayRegistros[4]"+ arrayRegistros[4]);
//				 			sheet.addCell(new jxl.write.Label(4, r, arrayRegistros[4]));
//				 			System.out.println("arrayRegistros[5]"+ arrayRegistros[5]);
//				 			sheet.addCell(new jxl.write.Label(5, r, arrayRegistros[5]));
//				 			System.out.println("arrayRegistros[6]"+ arrayRegistros[6]);
//				 			double CantProd = Double.parseDouble(arrayRegistros[4]);
//				 			sheet.addCell(new jxl.write.Label(6, r, arrayRegistros[6]));
//				 			System.out.println("arrayRegistros[7]"+ arrayRegistros[7]);
//				 			sheet.addCell(new jxl.write.Label(7, r, arrayRegistros[7]));
//				 			System.out.println("arrayRegistros[8]"+ arrayRegistros[8]);
//				 			sheet.addCell(new jxl.write.Label(8, r, arrayRegistros[8]));
//				 			double CostoUni = Double.parseDouble(arrayRegistros[8]);
//				 			
//				 			System.out.println("arrayRegistros[9]"+ df.format((CantProd * CostoUni)));
//				 			sheet.addCell(new jxl.write.Label(9, r, df.format((CantProd * CostoUni))));
//				 			
//				 			System.out.println("Este es el Articulo: " +arrayRegistros[0]);
//				 			
//				 			System.out.println("Este es el Articulo para el Fifo: " +getFifoXa(arrayRegistros[0]));
//				 			List<String> idsProd_id = getFifoXa(arrayRegistros[0]);
//				 			
//				 			System.out.println("Verificacion de la informacion de la longitud ------------->> "+ getFifoXa(arrayRegistros[0]).size());
//				 			if(getFifoXa(arrayRegistros[0]).size() > 0){
//				 				System.out.println(Collections.max(idsProd_id).replace(".0", ""));
//					 			System.out.println("rs.getString(10) "+ Collections.max(idsProd_id).replace(".0", ""));
//					 					        
//					 			sheet.addCell(new jxl.write.Label(10, r, Collections.max(idsProd_id).replace(".0", "")));
//				 			}else{
//				 				sheet.addCell(new jxl.write.Label(10, r, "1221226"));
//				 			}
//				 			
//				 			System.out.println("Este es el Articulo para el HOUSE: " +getHouseXa(arrayRegistros[0]));
//				 			List<String> idsProdHouse_id = getHouseXa(arrayRegistros[0]);
//				 			
//				 			System.out.println("Verificacion de la informacion de la longitud ------------->> "+ getHouseXa(arrayRegistros[0]).size());
//				 			if(getHouseXa(arrayRegistros[0]).size() > 0){
//				 				System.out.println(Collections.max(idsProdHouse_id).replace(".0", ""));
//					 			System.out.println("rs.getString(11) "+ Collections.max(idsProdHouse_id).replace(".0", ""));
//					 					        
//					 			sheet.addCell(new jxl.write.Label(11, r, Collections.max(idsProdHouse_id).replace(".0", "")));
//				 			}else{
//				 				sheet.addCell(new jxl.write.Label(11, r, ""));
//				 			}
//				 				
//				 			r++;
//					 }
//					
//					 
//					 
////					while (rs.next()) {
////						DecimalFormat df = new DecimalFormat("#.00");
////						
////						System.out.println("rs.getString(1) "+ rs.getString(1));
////						OC.setId_Detalle_Comprobante(rs.getString(1));
////						System.out.println("rs.getString(2) "+ rs.getString(2));
////						OC.setNombreCorporativo(rs.getString(2));
////						System.out.println("rs.getString(3) "+ rs.getString(3));
////						OC.setCO_Nombre(rs.getString(3));
////						System.out.println("rs.getString(4) "+ rs.getString(4));
////						OC.setCO_Ap_Paterno(rs.getString(4));
////						
////						double CantProd = Double.parseDouble(rs.getString(5));
////						
////						
////						System.out.println("rs.getString(5) "+ rs.getString(5));
////						OC.setCO_Ap_Materno(df.format(CantProd));
////						System.out.println("rs.getString(6) "+ rs.getString(6));
////						OC.setCO_Razon_Social(rs.getString(6));
////						System.out.println("rs.getString(7) "+ rs.getString(7));
////						OC.setCO_Fecha_Entrega(rs.getString(7));
////						System.out.println("rs.getString(8) "+ rs.getString(8));
////						OC.setCO_Fecha_Max_Entrega(rs.getString(8));
////						
////						double CostoUni = Double.parseDouble(rs.getString(9));
////						
////						System.out.println("rs.getString(9) "+ rs.getString(9));
////						OC.setCO_Status_Orden(df.format(CostoUni));
////						
////						
////						
////						
////						
////						
////						
////						System.out.println("CALCULO "+ df.format((CantProd * CostoUni)));
////						OC.setCO_Observaciones(df.format((CantProd * CostoUni)));
////						
////						
////						
//////						System.out.println("rs.getString(11) "+ getFifoXa(OC.getId_Detalle_Comprobante()));
//////						OC.setCO_MP_Clave(rs.getString(11));
//////						System.out.println("detalleFac.getCO_Identificador(): " +OC.getCO_Identificador());
////						 sheet.addCell(new jxl.write.Label(0, r, OC.getId_Detalle_Comprobante()));
////					        sheet.addCell(new jxl.write.Label(1, r, OC.getNombreCorporativo()));
//////			
////					        sheet.addCell(new jxl.write.Label(2, r, OC.getCO_Nombre()));
////					        sheet.addCell(new jxl.write.Label(3, r, OC.getCO_Ap_Paterno()));			        	
////					        sheet.addCell(new jxl.write.Label(4, r, OC.getCO_Razon_Social()));
////					        sheet.addCell(new jxl.write.Label(5, r, OC.getCO_Ap_Materno()));
////					        sheet.addCell(new jxl.write.Label(6, r, OC.getCO_Fecha_Max_Entrega()));
////					        sheet.addCell(new jxl.write.Label(7, r, OC.getCO_Fecha_Entrega()));
////					        sheet.addCell(new jxl.write.Label(8, r, OC.getCO_Status_Orden()));
////					        
////					        
////					        sheet.addCell(new jxl.write.Label(9, r, OC.getCO_Observaciones()));
////					        
////					        List<String> idsProd_id = getFifoXa(OC.getId_Detalle_Comprobante());
////					        
////					        System.out.println(Collections.max(idsProd_id));
////					        System.out.println("rs.getString(11) "+ Collections.max(idsProd_id));
////					        
////					        sheet.addCell(new jxl.write.Label(10, r, Collections.max(idsProd_id)));
////						
////					
////					r++;
////					}
//					
//					 workbook.write();
//					    workbook.close();
//					    r = 1;
//					    
//					    Thread.sleep(3000);
//					    
//					    Messagebox.show("Termina el Proceso del Reporte", "� Informaci�n !", org.zkoss.zul.Messagebox.OK, "");
//						 Executions.sendRedirect("/Reporte_de_InventarioFisico.xls");
//					
//	
////				}
//	
//	
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return detalles;
//		}
//		
//		
//		public List<String> getFifoXa(String Sku_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			String SumaCant="";
//			List<String> ComparaXA = new ArrayList<String>();
//			
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select chFifo from Tb_InventarioDetalle_MP_XA as XA  where XA.chSKU = '" +Sku_Prod+"'");
//					
////					System.out.println("select SUM(XA.Cantidad) from Tb_InventarioDetalle_MP_XA as XA where XA.chSKU = '" +Sku_Prod+"'");
//					
//
//					while (rs.next()) {
//						SumaCant = rs.getString(1);
////						CostoUni = rs.getDouble(2);
////						String Valor = (SumaCant+"|"+CostoUni);
//						
//						ComparaXA.add(SumaCant);
//
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return ComparaXA;
//		}
//		
//		public List<String> getHouseXa(String Sku_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			String SumaCant="";
//			List<String> ComparaXA = new ArrayList<String>();
//			
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select nuHouse from Tb_InventarioDetalle_MP_XA as XA  where XA.chSKU = '" +Sku_Prod+"'");
//					
////					System.out.println("select SUM(XA.Cantidad) from Tb_InventarioDetalle_MP_XA as XA where XA.chSKU = '" +Sku_Prod+"'");
//					
//
//					while (rs.next()) {
//						SumaCant = rs.getString(1);
////						CostoUni = rs.getDouble(2);
////						String Valor = (SumaCant+"|"+CostoUni);
//						
//						ComparaXA.add(SumaCant);
//
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return ComparaXA;
//		}
//		/**
//		 * Obtiene los distintos de detalles de la factura y quellos que son el mismo los agrupa
//		 * @param factura
//		 * @return
//		 */
//		public List<BeanInvMP> getProductoTermExcel(String P_Condicion) 
//		{
//			String realString = Sessions.getCurrent().getWebApp().getRealPath("Reporte_de_InventarioFisico_ProductoTerminado.xls");
//			
//			 DecimalFormat df = new DecimalFormat("#.00");
//			
//			Session s = null;
//			List<BeanInvMP> detalles = new ArrayList<BeanInvMP>();
////			List<Integer> idsProductos = new ArrayList<Integer>();
//			Statement stm = null;
//			ResultSet rs = null;
//			try {
//				s = HibernateUtil.currentSession();
//				stm = s.connection().createStatement();
//	
//				if(P_Condicion.trim().equals("")){
//					System.out.println("select pMP.chSKU, UMP.chSKU, u.pwd_history, IDMP.Cantidad, IDMP.chParte,IDMP.chObservaciones,EMP.chSKUEmpaque,pMP.nuCostoProm from Tb_InventarioDetalle_PT IDMP, Tb_Productos_Terminados pMP, Tb_Ubicacion_Terminados UMP,Tb_Usuario U,Tb_Cat_Empaques EMP where IDMP.nukIdProductosTerminados = pMP.nukIdProductosTerminados and IDMP.nukIdUbicaTerm = UMP.nukIdUbicaTerm and IDMP.id_Usuario = U.id_Usuario and IDMP.nukIdEmpaques = EMP.nukIdEmpaques");
//					rs = stm.executeQuery("select pMP.chSKU, UMP.chSKU, u.pwd_history, IDMP.Cantidad, IDMP.chParte,IDMP.chObservaciones,EMP.chSKUEmpaque,pMP.nuCostoProm from Tb_InventarioDetalle_PT IDMP, Tb_Productos_Terminados pMP, Tb_Ubicacion_Terminados UMP,Tb_Usuario U,Tb_Cat_Empaques EMP where IDMP.nukIdProductosTerminados = pMP.nukIdProductosTerminados and IDMP.nukIdUbicaTerm = UMP.nukIdUbicaTerm and IDMP.id_Usuario = U.id_Usuario and IDMP.nukIdEmpaques = EMP.nukIdEmpaques");
//				}else{
//					System.out.println("select pMP.chSKU, UMP.chSKU, u.pwd_history, IDMP.Cantidad, IDMP.chParte,IDMP.chObservaciones,EMP.chSKUEmpaque,pMP.nuCostoProm from Tb_InventarioDetalle_PT IDMP, Tb_Productos_Terminados pMP, Tb_Ubicacion_Terminados UMP,Tb_Usuario U,Tb_Cat_Empaques EMP where IDMP.nukIdProductosTerminados = pMP.nukIdProductosTerminados and IDMP.nukIdUbicaTerm = UMP.nukIdUbicaTerm and IDMP.id_Usuario = U.id_Usuario and IDMP.nukIdEmpaques = EMP.nukIdEmpaques "+P_Condicion);
//					rs = stm.executeQuery("select pMP.chSKU, UMP.chSKU, u.pwd_history, IDMP.Cantidad, IDMP.chParte,IDMP.chObservaciones,EMP.chSKUEmpaque,pMP.nuCostoProm from Tb_InventarioDetalle_PT IDMP, Tb_Productos_Terminados pMP, Tb_Ubicacion_Terminados UMP,Tb_Usuario U,Tb_Cat_Empaques EMP where IDMP.nukIdProductosTerminados = pMP.nukIdProductosTerminados and IDMP.nukIdUbicaTerm = UMP.nukIdUbicaTerm and IDMP.id_Usuario = U.id_Usuario and IDMP.nukIdEmpaques = EMP.nukIdEmpaques "+P_Condicion);
//				}
//					
//					
//					
//					 WritableWorkbook workbook =
//						    Workbook.createWorkbook(new File(realString));
//	
//						    //Se crea una nueva hoja dentro del libro
//						    WritableSheet sheet =
//						    workbook.createSheet("InventarioFisicoPT", 0);
//	
//	
//						    sheet.addCell(new jxl.write.Label(0, 0, "SKU PRODUCTO"));
//					        sheet.addCell(new jxl.write.Label(1, 0, "SKU UBICACION"));
//					        sheet.addCell(new jxl.write.Label(2, 0, "USUARIO"));
//					        sheet.addCell(new jxl.write.Label(3, 0, "FOLIO"));
//					        sheet.addCell(new jxl.write.Label(4, 0, "CANTIDAD"));
//					        sheet.addCell(new jxl.write.Label(5, 0, "OBSERVACIONES"));
//					        sheet.addCell(new jxl.write.Label(6, 0, "EMPAQUE"));
//					        sheet.addCell(new jxl.write.Label(7, 0, "COSTO UNITARIO"));
//					        sheet.addCell(new jxl.write.Label(8, 0, "TOTAL MXN"));
////					        sheet.addCell(new jxl.write.Label(10, 0, "Coste"));
////					        sheet.addCell(new jxl.write.Label(11, 0, "Fecha Creacion"));
////					        sheet.addCell(new jxl.write.Label(12, 0, "Proveedor"));
////					        sheet.addCell(new jxl.write.Label(13, 0, "Cuenta"));
////					        sheet.addCell(new jxl.write.Label(14, 0, "Orden"));
////					        sheet.addCell(new jxl.write.Label(15, 0, "Perfil"));
////					        sheet.addCell(new jxl.write.Label(16, 0, "Rechazo"));
////					        sheet.addCell(new jxl.write.Label(17, 0, "Subtotal"));
////					        sheet.addCell(new jxl.write.Label(18, 0, "Iva"));
////					        sheet.addCell(new jxl.write.Label(19, 0, "Retencion"));
////					        sheet.addCell(new jxl.write.Label(20, 0, "Estatus"));
//					        
//					        
//					                
//					        int r = 1;
//					        
//					BeanInvMP OC = new BeanInvMP();
////					prodser.setIdProductoServicio(idsProductos.get(i));
////					int indice = 1;
//					while (rs.next()) {
//						System.out.println("rs.getString(1) "+ rs.getString(1));
//						OC.setId_Detalle_Comprobante(rs.getString(1));
//						System.out.println("rs.getString(2) "+ rs.getString(2));
//						OC.setNombreCorporativo(rs.getString(2));
//						System.out.println("rs.getString(3) "+ rs.getString(3));
//						OC.setCO_Nombre(rs.getString(3));
//						System.out.println("rs.getString(4) "+ rs.getString(4));
//						
//						
//						double Cantidad = Double.parseDouble(rs.getString(4));
//						OC.setCO_Ap_Paterno(df.format(Cantidad));
//						System.out.println("rs.getString(5) "+ rs.getString(5));
//						OC.setCO_Ap_Materno(rs.getString(5));
//						System.out.println("rs.getString(6) "+ rs.getString(6));
//						OC.setCO_Razon_Social(rs.getString(6));
//						System.out.println("rs.getString(7) "+ rs.getString(7));
//						OC.setCO_Fecha_Entrega(rs.getString(7));
//						System.out.println("rs.getString(8) "+ rs.getString(8));
//						double CostoUni = Double.parseDouble(rs.getString(8));
//						OC.setCO_Fecha_Max_Entrega(df.format(CostoUni));
//	
////						System.out.println("detalleFac.getCO_Identificador(): " +OC.getCO_Identificador());
//						 sheet.addCell(new jxl.write.Label(0, r, OC.getId_Detalle_Comprobante()));
//					        sheet.addCell(new jxl.write.Label(1, r, OC.getNombreCorporativo()));
////					        
//					        sheet.addCell(new jxl.write.Label(2, r, OC.getCO_Nombre()));
//					        sheet.addCell(new jxl.write.Label(3, r, OC.getCO_Ap_Materno()));			        	
//					        sheet.addCell(new jxl.write.Label(4, r, OC.getCO_Ap_Paterno()));
//					        sheet.addCell(new jxl.write.Label(5, r, OC.getCO_Razon_Social()));
////					        
//					        sheet.addCell(new jxl.write.Label(6, r, OC.getCO_Fecha_Entrega()));
////					        
//					        sheet.addCell(new jxl.write.Label(7, r, OC.getCO_Fecha_Max_Entrega()));
//					        
//					        
//					        
//					        
//					        
//					        
//					        sheet.addCell(new jxl.write.Label(8, r, df.format((Cantidad *CostoUni))));
//					r++;
//					}
//					
//					 workbook.write();
//					    workbook.close();
//					    r = 1;
//					    
//					    Thread.sleep(3000);
//					    
//					    Messagebox.show("Termina el Proceso del Reporte", "� Informaci�n !", org.zkoss.zul.Messagebox.OK, "");
//						 Executions.sendRedirect("/Reporte_de_InventarioFisico_ProductoTerminado.xls");
//					
//	
////				}
//	
//	
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return detalles;
//		}
//		
//		
//		public double getComparaProd(String Sku_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			double SumaCant=0;
//			boolean valor_Registro = false;
//			TbInventarioDetalleMp uso = new TbInventarioDetalleMp();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select SUM(IDMP.Cantidad) from Tb_Productos pMP,Tb_InventarioDetalle_MP IDMP,  Tb_Ubicacion_MateriaPrim UMP  where IDMP.nukIdProductos = pMP.nukIdProductos and IDMP.nukIdUbicaMatPrim = UMP.nukIdUbicaMatPrim and pMP.chSKU = '" +Sku_Prod+"'");
//					
//					
//					
//
//					while (rs.next()) {
//						SumaCant = rs.getDouble(1);
////						uso.setChSku(rs.getString(2));
////						uso.setChDescripcion(rs.getString(3));
////						uso.setChUnidadMedida(rs.getString(4));
//						if(SumaCant == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return SumaCant;
//		}
//		
//		public List<String> getComparaProdSUM(String Sku_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			double SumaCant=0;
//			double CostoUni=0;
//			boolean valor_Registro = false;
//			List<String> ComparaXA = new ArrayList<String>();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select SUM(IDMP.Cantidad), pMP.nuCostoProm from Tb_Productos pMP,Tb_InventarioDetalle_MP IDMP,  Tb_Ubicacion_MateriaPrim UMP  where IDMP.nukIdProductos = pMP.nukIdProductos and IDMP.nukIdUbicaMatPrim = UMP.nukIdUbicaMatPrim and pMP.chSKU = '" +Sku_Prod+"' group by pMP.nuCostoProm");
//					
//					
//					
//
//					while (rs.next()) {
//						SumaCant = rs.getDouble(1);
//						CostoUni = rs.getDouble(2);
//						String Valor = (SumaCant+"|"+CostoUni);
//						
//						ComparaXA.add(Valor);
//						
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return ComparaXA;
//		}
//		
//		public double getComparaProdXA(String Sku_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			double SumaCant=0;
//			boolean valor_Registro = false;
//			TbInventarioDetalleMp uso = new TbInventarioDetalleMp();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select SUM(XA.Cantidad) from Tb_InventarioDetalle_MP_XA as XA where XA.chSKU = '" +Sku_Prod+"'");
//					
////					System.out.println("select SUM(XA.Cantidad) from Tb_InventarioDetalle_MP_XA as XA where XA.chSKU = '" +Sku_Prod+"'");
//					
//
//					while (rs.next()) {
//						SumaCant = rs.getDouble(1);
////						uso.setChSku(rs.getString(2));
////						uso.setChDescripcion(rs.getString(3));
////						uso.setChUnidadMedida(rs.getString(4));
//						if(SumaCant == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return SumaCant;
//		}
//		
//		public List<String> getComparaProdXASum(String Sku_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			double SumaCant=0;
//			double CostoUni=0;
//			List<String> ComparaXA = new ArrayList<String>();
//			
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select SUM(XA.Cantidad), XA.nuCostoUni from Tb_InventarioDetalle_MP_XA as XA where XA.chSKU = '" +Sku_Prod+"' group by XA.nuCostoUni");
//					
////					System.out.println("select SUM(XA.Cantidad) from Tb_InventarioDetalle_MP_XA as XA where XA.chSKU = '" +Sku_Prod+"'");
//					
//
//					while (rs.next()) {
//						SumaCant = rs.getDouble(1);
//						CostoUni = rs.getDouble(2);
//						String Valor = (SumaCant+"|"+CostoUni);
//						
//						ComparaXA.add(Valor);
////						uso.setChSku(rs.getString(2));
////						uso.setChDescripcion(rs.getString(3));
////						uso.setChUnidadMedida(rs.getString(4));
////						if(SumaCant == 0){
////							valor_Registro = false;
////						}else{
////							valor_Registro = true;
////						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return ComparaXA;
//		}
//		
//		
//		public int getComparaProdTerm(String Sku_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			int SumaCant=0;
//			boolean valor_Registro = false;
////			TbInventarioDetalleMp uso = new TbInventarioDetalleMp();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select SUM(IDMP.Cantidad) from Tb_Productos_Terminados pMP,Tb_InventarioDetalle_PT IDMP,  Tb_Ubicacion_Terminados UMP  where IDMP.nukIdProductosTerminados = pMP.nukIdProductosTerminados and IDMP.nukIdUbicaTerm = UMP.nukIdUbicaTerm and pMP.chSKU = '" +Sku_Prod+"'");
//					
//					
//					
//
//					while (rs.next()) {
//						SumaCant = rs.getInt(1);
////						uso.setChSku(rs.getString(2));
////						uso.setChDescripcion(rs.getString(3));
////						uso.setChUnidadMedida(rs.getString(4));
//						if(SumaCant == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return SumaCant;
//		}
//		
//		public List<String> getComparaProdTermSUM(String Sku_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			double SumaCant=0;
//			double CostoUni=0;
//			boolean valor_Registro = false;
//			List<String> ComparaXA = new ArrayList<String>();
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select SUM(IDMP.Cantidad), pMP.nuCostoProm from Tb_Productos_Terminados pMP,Tb_InventarioDetalle_PT IDMP,  Tb_Ubicacion_Terminados UMP  where IDMP.nukIdProductosTerminados = pMP.nukIdProductosTerminados and IDMP.nukIdUbicaTerm = UMP.nukIdUbicaTerm and pMP.chSKU = '" +Sku_Prod+"' group by pMP.nuCostoProm");
//					
//					
//					
//
//					while (rs.next()) {
//						SumaCant = rs.getDouble(1);
//						CostoUni = rs.getDouble(2);
//						String Valor = (SumaCant+"|"+CostoUni);
//						
//						ComparaXA.add(Valor);
//						
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return ComparaXA;
//		}
//		
//		public List<String> getComparaProdTermXASUM(String Sku_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			double SumaCant=0;
//			double CostoUni=0;
//			List<String> ComparaXA = new ArrayList<String>();
//			
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select SUM(XA.Cantidad), XA.nuCostoUni from Tb_InventarioDetallePT_XA as XA where XA.chSKU = '" +Sku_Prod+"' group by XA.nuCostoUni");
//					
////					System.out.println("select SUM(XA.Cantidad) from Tb_InventarioDetalle_MP_XA as XA where XA.chSKU = '" +Sku_Prod+"'");
//					
//
//					while (rs.next()) {
//						SumaCant = rs.getDouble(1);
//						CostoUni = rs.getDouble(2);
//						String Valor = (SumaCant+"|"+CostoUni);
//						
//						ComparaXA.add(Valor);
////						uso.setChSku(rs.getString(2));
////						uso.setChDescripcion(rs.getString(3));
////						uso.setChUnidadMedida(rs.getString(4));
////						if(SumaCant == 0){
////							valor_Registro = false;
////						}else{
////							valor_Registro = true;
////						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return ComparaXA;
//		}
//		
//		public int getComparaProdTermXA(String Sku_Prod) 
//		{
//			Session s = null;
//			Statement stm = null;
//			ResultSet rs = null;
//			int SumaCant=0;
//			boolean valor_Registro = false;
//			try {
//				s = HibernateUtil.currentSession();
//
//
//				stm = s.connection().createStatement();
//
//					rs = stm.executeQuery("select SUM(XA.Cantidad) from Tb_InventarioDetallePT_XA as XA where XA.chSKU = '" +Sku_Prod+"'");
//					
//					
//					
//
//					while (rs.next()) {
//						SumaCant = rs.getInt(1);
////						uso.setChSku(rs.getString(2));
////						uso.setChDescripcion(rs.getString(3));
////						uso.setChUnidadMedida(rs.getString(4));
//						if(SumaCant == 0){
//							valor_Registro = false;
//						}else{
//							valor_Registro = true;
//						}
//						
//					}
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return SumaCant;
//		}
//		
//		/**
//		 * Obtiene los distintos de detalles de la factura y quellos que son el mismo los agrupa
//		 * @param factura
//		 * @return
//		 */
//		public List<BeanInvMP> getDetalleMP_XA_Excel() 
//		{
//			String realString = Sessions.getCurrent().getWebApp().getRealPath("Reporte_de_Inventario_vs_XA.xls");
//			
//			DecimalFormat df = new DecimalFormat("#.00");
//			
//			Session s = null;
//			List<BeanInvMP> detalles = new ArrayList<BeanInvMP>();
////			List<Integer> idsProductos = new ArrayList<Integer>();
//			Statement stm = null;
//			ResultSet rs = null;
//			try {
//				s = HibernateUtil.currentSession();
//	
//	
//				stm = s.connection().createStatement();
//				
//					rs = stm.executeQuery("select chSKU from  Tb_Productos ");
//
//					 WritableWorkbook workbook =
//						    Workbook.createWorkbook(new File(realString));
//	
//						    //Se crea una nueva hoja dentro del libro
//						    WritableSheet sheet =
//						    workbook.createSheet("ComparaInvMPConXA", 0);
//	
//	
//						    sheet.addCell(new jxl.write.Label(0, 0, "SKU PRODUCTO"));
//					        sheet.addCell(new jxl.write.Label(1, 0, "CONTEO XA MATERIA PRIMA"));
//					        sheet.addCell(new jxl.write.Label(2, 0, "PRECIO XA MATERIA PRIMA"));
//					        sheet.addCell(new jxl.write.Label(3, 0, "CONTEO INVENTARIO MATERIA PRIMA"));
//					        sheet.addCell(new jxl.write.Label(4, 0, "PRECIO INVENTARIO MATERIA PRIMA"));
//					        sheet.addCell(new jxl.write.Label(5, 0, "RESULTADO DEL CONTEO XA Vs MP"));
//					        sheet.addCell(new jxl.write.Label(6, 0, "RESULTADO DEL PRECIO XA Vs MP"));
//					        
//					        
//					                
//					        int r = 1;
//					        
//					BeanInvMP OC = new BeanInvMP();
//					
//					ArrayList<String>List_SKU=new ArrayList<String>();
//					
//					while (rs.next()) {
//						List_SKU.add(rs.getString(1));
//						
//					
//					
//					}
//					
//					 WritableFont wfontStatus = new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
//					    WritableCellFormat fCellstatus = new WritableCellFormat(wfontStatus);
//					
//					 for (int i=0;i<List_SKU.size();i++) {
////									 	
//						 	sheet.addCell(new jxl.write.Label(0, r, List_SKU.get(i)));
//						 	
//						 	
//						 	
//						 	
//						 	
//						 	/** Inicia  */
//						 	
//						 	
//						 	String id_cadenaids ="";
//						 	int id_contador= 0;
//						 	
//						 	String Cantidad = "0";
//						 	String CostoUni = "0";
//						 			
//						 		List<String> idsUsuarios_id = getComparaProdXASum(List_SKU.get(i));
//						 		if(idsUsuarios_id.size()>0){
//						 			for(String str_id : idsUsuarios_id)
//						 			{
//						 				if(id_contador == 0){
//						 					id_cadenaids= id_cadenaids+str_id;
//						 					id_contador++;
//						 				}else{
//						 					id_cadenaids= id_cadenaids+","+str_id;
//						 				}
//						 				//imprimimos el objeto pivote
////						 					System.out.println(str);
////						 					cadenaids= str;
//						 			}
//						 			System.out.println("Cadena con IDs = "+id_cadenaids);
////						 				checkbox_UserList_ShowAll.setChecked(false);
////						 			Condicion_RFC = " and Id_Proveedor in (" + id_cadenaids + ")";
////						 				restricciones.add(restriccion);		
//						 			
//						 			String[ ] arrayColores = id_cadenaids.split(",");
//
//						 		// Aqu� ya tenemos un array en el que cada elemento es un color.
//
//						 		for (int x = 0; x < arrayColores.length; x++) {
//						 		       System.out.println(arrayColores[x]);
//						 		       
//						 		      String[ ] arrayMontos = arrayColores[x].split("\\|");
//
//								 		// Aqu� ya tenemos un array en el que cada elemento es un color.
//
//								 		for (int y = 0; y < arrayMontos.length; y++) {
//								 		       System.out.println(arrayMontos[y]);
//								 		       
//								 		       	Cantidad = arrayMontos[0];
//											 	CostoUni = arrayMontos[1];
//								 		       
//								 		}
//						 		       
//						 		       
//						 		}
//						 			
//						 		}else{
////						 		Condicion_RFC = "";
//						 		}
//						 	
//						 		double d_Cantidad = Double.parseDouble(Cantidad);
//						 		double d_CostoUni = Double.parseDouble(CostoUni);
//						 	
//						 	/** Termina */
//						 		
//						 		if(df.format(d_Cantidad).equals(".00")){
//							 		sheet.addCell(new jxl.write.Label(1, r, "0.00"));
//							 	}else{
//							 		sheet.addCell(new jxl.write.Label(1, r, df.format(d_Cantidad)));
//							 	}
//						 		
//						 		
//						 		if(df.format(d_CostoUni*d_Cantidad).equals(".00")){
//							 		sheet.addCell(new jxl.write.Label(2, r, "0.00"));
//							 	}else{
//							 		sheet.addCell(new jxl.write.Label(2, r, df.format(d_CostoUni*d_Cantidad)));
//							 	}
//						 	
//						 	
//						 	
//						 	
//						 	// Inicia lectura de Operaciones del producto
//						 	
//						 	String id_cadenaidsProd ="";
//						 	int id_contador_Prod= 0;
//						 	
//						 	String Cantidad_Prod = "0";
//						 	String CostoUni_Prod = "0";
//						 			
//						 		List<String> idsProd_id = getComparaProdSUM(List_SKU.get(i));
//						 		if(idsProd_id.size()>0){
//						 			for(String str_id_prod : idsProd_id)
//						 			{
//						 				if(id_contador_Prod == 0){
//						 					id_cadenaidsProd= id_cadenaidsProd+str_id_prod;
//						 					id_contador_Prod++;
//						 				}else{
//						 					id_cadenaidsProd= id_cadenaidsProd+","+str_id_prod;
//						 				}
//						 				//imprimimos el objeto pivote
////						 					System.out.println(str);
////						 					cadenaids= str;
//						 			}
//						 			System.out.println("Cadena con IDs = "+id_cadenaidsProd);
////						 				checkbox_UserList_ShowAll.setChecked(false);
////						 			Condicion_RFC = " and Id_Proveedor in (" + id_cadenaids + ")";
////						 				restricciones.add(restriccion);		
//						 			
//						 			String[ ] arrayProductos = id_cadenaidsProd.split(",");
//
//						 		// Aqu� ya tenemos un array en el que cada elemento es un color.
//
//						 		for (int z = 0; z < arrayProductos.length; z++) {
//						 		       System.out.println(arrayProductos[z]);
//						 		       
//						 		      String[ ] arrayMontos = arrayProductos[z].split("\\|");
//
//								 		// Aqu� ya tenemos un array en el que cada elemento es un color.
//
//								 		for (int v = 0; v < arrayMontos.length; v++) {
//								 		       System.out.println(arrayMontos[v]);
//								 		       
//								 		      Cantidad_Prod = arrayMontos[0];
//								 		     CostoUni_Prod = arrayMontos[1];
//								 		       
//								 		}
//						 		       
//						 		       
//						 		}
//						 			
//						 		}else{
////						 		Condicion_RFC = "";
//						 		}
//						 	
//						 		double d_Cantidad_prod = Double.parseDouble(Cantidad_Prod);
//						 		double d_CostoUni_prod = Double.parseDouble(CostoUni_Prod);
//						 	
//						 	// Termina la Lectura de operaciones del producto
//						 	if(df.format(d_Cantidad_prod).equals(".00")){
//						 		sheet.addCell(new jxl.write.Label(3, r, "0.00"));
//						 	}else{
//						 		sheet.addCell(new jxl.write.Label(3, r, df.format(d_Cantidad_prod)));
//						 	}
//						 	
//						 	if(df.format(d_CostoUni_prod*d_Cantidad_prod).equals(".00")){
//						 		sheet.addCell(new jxl.write.Label(4, r, "0.00"));
//						 	}else{
//						 		sheet.addCell(new jxl.write.Label(4, r, df.format(d_CostoUni_prod*d_Cantidad_prod)));
//						 	}
//					        
//					        
//					        
//					        double Operacion = (getComparaProd(List_SKU.get(i)) - getComparaProdXA(List_SKU.get(i)));
//					        
//					        
//					        if(Operacion < 0 ){
//					        	sheet.addCell(new jxl.write.Label(5, r, df.format(Operacion),fCellstatus));
//					        }else{
//					        	if(df.format(Operacion).equals(".00")){
//							 		sheet.addCell(new jxl.write.Label(5, r, "0.00"));
//							 	}else{
//							 		sheet.addCell(new jxl.write.Label(5, r, df.format(Operacion)));
//							 	}
//					        	
//					        	
//					        }
//					        
//					        double OperaPrecio = ((d_CostoUni_prod*d_Cantidad_prod) - (d_CostoUni*d_Cantidad));
//					        
//					        if(OperaPrecio < 0 ){
//					        	sheet.addCell(new jxl.write.Label(6, r, df.format(OperaPrecio),fCellstatus));
//					        }else{
//					        	if(df.format(OperaPrecio).equals(".00")){
//							 		sheet.addCell(new jxl.write.Label(6, r, "0.00"));
//							 	}else{
//							 		sheet.addCell(new jxl.write.Label(6, r, df.format(OperaPrecio)));
//							 	}
//					        	
//					        	
//					        }
//					        
////					        sheet.addCell(new jxl.write.Label(3, r, (getComparaProdXA(List_SKU.get(i)) - getComparaProd(List_SKU.get(i)))+""));
//					        
//					       r++;
//					     
//					    }
//					
//					 workbook.write();
//					    workbook.close();
//					    r = 1;
//					    
//					    Thread.sleep(3000);
//					    
//					    Messagebox.show("Termina el Proceso del Reporte", "� Informaci�n !", org.zkoss.zul.Messagebox.OK, "");
//						 Executions.sendRedirect("/Reporte_de_Inventario_vs_XA.xls");
//					
//	
////				}
//	
//	 
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return detalles;
//		}
//		
//		/**
//		 * Obtiene los distintos de detalles de la factura y quellos que son el mismo los agrupa
//		 * @param factura
//		 * @return
//		 */
//		public List<BeanInvMP> getDetallePT_XA_Excel() 
//		{
//			String realString = Sessions.getCurrent().getWebApp().getRealPath("Reporte_de_Inventario_PT_vs_XA.xls");
//			
//			DecimalFormat df = new DecimalFormat("#.00");
//			
//			Session s = null;
//			List<BeanInvMP> detalles = new ArrayList<BeanInvMP>();
////			List<Integer> idsProductos = new ArrayList<Integer>();
//			Statement stm = null;
//			ResultSet rs = null;
//			try {
//				s = HibernateUtil.currentSession();
//	
//	
//				stm = s.connection().createStatement();
//				
//					rs = stm.executeQuery("select chSKU from  Tb_Productos_Terminados ");
//
//					 WritableWorkbook workbook =
//						    Workbook.createWorkbook(new File(realString));
//	
//						    //Se crea una nueva hoja dentro del libro
//						    WritableSheet sheet =
//						    workbook.createSheet("ComparaInvPTConXA", 0);
//	
//	
////						    sheet.addCell(new jxl.write.Label(0, 0, "SKU PRODUCTO"));
////						    sheet.addCell(new jxl.write.Label(1, 0, "CONTEO XA PRODUCTO TERMINADO"));
////					        sheet.addCell(new jxl.write.Label(2, 0, "CONTEO INVENTARIO PRODUCTO TERMINADO"));
////					        sheet.addCell(new jxl.write.Label(3, 0, "RESULTADO DE LA COMPARACION"));
//					        
//					        sheet.addCell(new jxl.write.Label(0, 0, "SKU PRODUCTO"));
//					        sheet.addCell(new jxl.write.Label(1, 0, "CONTEO XA PRODUCTO TERMINADO"));
//					        sheet.addCell(new jxl.write.Label(2, 0, "PRECIO XA PRODUCTO TERMINADO"));
//					        sheet.addCell(new jxl.write.Label(3, 0, "CONTEO INVENTARIO PRODUCTO TERMINADO"));
//					        sheet.addCell(new jxl.write.Label(4, 0, "PRECIO INVENTARIO PRODUCTO TERMINADOINVENTARIO PRODUCTO TERMINADO"));
//					        sheet.addCell(new jxl.write.Label(5, 0, "RESULTADO DEL CONTEO XA Vs PT"));
//					        sheet.addCell(new jxl.write.Label(6, 0, "RESULTADO DEL PRECIO XA Vs PT"));
//					        
//					        
//					                
//					        int r = 1;
//					        
//					        ArrayList<String>List_SKU=new ArrayList<String>();
//							
//							while (rs.next()) {
//								List_SKU.add(rs.getString(1));
//								
//							
//							
//							}
//							
//							
//							
//						    WritableFont wfontStatus = new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
//						    WritableCellFormat fCellstatus = new WritableCellFormat(wfontStatus);
//
//							
//							 for (int i=0;i<List_SKU.size();i++) {
////											 	
//								 	sheet.addCell(new jxl.write.Label(0, r, List_SKU.get(i)));
//								 	
//							       
//							       /** Inicia  */
//								 	
//								 	
//								 	String id_cadenaids ="";
//								 	int id_contador= 0;
//								 	
//								 	String Cantidad = "0";
//								 	String CostoUni = "0";
//								 			
//								 		List<String> idsUsuarios_id = getComparaProdTermXASUM(List_SKU.get(i));
//								 		if(idsUsuarios_id.size()>0){
//								 			for(String str_id : idsUsuarios_id)
//								 			{
//								 				if(id_contador == 0){
//								 					id_cadenaids= id_cadenaids+str_id;
//								 					id_contador++;
//								 				}else{
//								 					id_cadenaids= id_cadenaids+","+str_id;
//								 				}
//								 				//imprimimos el objeto pivote
////								 					System.out.println(str);
////								 					cadenaids= str;
//								 			}
//								 			System.out.println("Cadena con IDs = "+id_cadenaids);
////								 				checkbox_UserList_ShowAll.setChecked(false);
////								 			Condicion_RFC = " and Id_Proveedor in (" + id_cadenaids + ")";
////								 				restricciones.add(restriccion);		
//								 			
//								 			String[ ] arrayColores = id_cadenaids.split(",");
//
//								 		// Aqu� ya tenemos un array en el que cada elemento es un color.
//
//								 		for (int x = 0; x < arrayColores.length; x++) {
//								 		       System.out.println(arrayColores[x]);
//								 		       
//								 		      String[ ] arrayMontos = arrayColores[x].split("\\|");
//
//										 		// Aqu� ya tenemos un array en el que cada elemento es un color.
//
//										 		for (int y = 0; y < arrayMontos.length; y++) {
//										 		       System.out.println(arrayMontos[y]);
//										 		       
//										 		       	Cantidad = arrayMontos[0];
//													 	CostoUni = arrayMontos[1];
//										 		       
//										 		}
//								 		       
//								 		       
//								 		}
//								 			
//								 		}else{
////								 		Condicion_RFC = "";
//								 		}
//								 	
//								 		double d_Cantidad = Double.parseDouble(Cantidad);
//								 		double d_CostoUni = Double.parseDouble(CostoUni);
//								 	
//								 	/** Termina */
//								 		
//								 		if(df.format(d_Cantidad).equals(".00")){
//									 		sheet.addCell(new jxl.write.Label(1, r, "0.00"));
//									 	}else{
//									 		sheet.addCell(new jxl.write.Label(1, r, df.format(d_Cantidad)));
//									 	}
//								 		
//								 		
//								 		if(df.format(d_CostoUni*d_Cantidad).equals(".00")){
//									 		sheet.addCell(new jxl.write.Label(2, r, "0.00"));
//									 	}else{
//									 		sheet.addCell(new jxl.write.Label(2, r, df.format(d_CostoUni*d_Cantidad)));
//									 	}
//								 	
//								 	
//								 	
//								 	
//								 	// Inicia lectura de Operaciones del producto
//								 	
//								 	String id_cadenaidsProd ="";
//								 	int id_contador_Prod= 0;
//								 	
//								 	String Cantidad_Prod = "0";
//								 	String CostoUni_Prod = "0";
//								 			
//								 		List<String> idsProd_id = getComparaProdTermSUM(List_SKU.get(i));
//								 		if(idsProd_id.size()>0){
//								 			for(String str_id_prod : idsProd_id)
//								 			{
//								 				if(id_contador_Prod == 0){
//								 					id_cadenaidsProd= id_cadenaidsProd+str_id_prod;
//								 					id_contador_Prod++;
//								 				}else{
//								 					id_cadenaidsProd= id_cadenaidsProd+","+str_id_prod;
//								 				}
//								 				//imprimimos el objeto pivote
////								 					System.out.println(str);
////								 					cadenaids= str;
//								 			}
//								 			System.out.println("Cadena con IDs = "+id_cadenaidsProd);
////								 				checkbox_UserList_ShowAll.setChecked(false);
////								 			Condicion_RFC = " and Id_Proveedor in (" + id_cadenaids + ")";
////								 				restricciones.add(restriccion);		
//								 			
//								 			String[ ] arrayProductos = id_cadenaidsProd.split(",");
//
//								 		// Aqu� ya tenemos un array en el que cada elemento es un color.
//
//								 		for (int z = 0; z < arrayProductos.length; z++) {
//								 		       System.out.println(arrayProductos[z]);
//								 		       
//								 		      String[ ] arrayMontos = arrayProductos[z].split("\\|");
//
//										 		// Aqu� ya tenemos un array en el que cada elemento es un color.
//
//										 		for (int v = 0; v < arrayMontos.length; v++) {
//										 		       System.out.println(arrayMontos[v]);
//										 		       
//										 		      Cantidad_Prod = arrayMontos[0];
//										 		     CostoUni_Prod = arrayMontos[1];
//										 		       
//										 		}
//								 		       
//								 		       
//								 		}
//								 			
//								 		}else{
////								 		Condicion_RFC = "";
//								 		}
//								 	
//								 		double d_Cantidad_prod = Double.parseDouble(Cantidad_Prod);
//								 		double d_CostoUni_prod = Double.parseDouble(CostoUni_Prod);
//								 	
//								 	// Termina la Lectura de operaciones del producto
//								 	if(df.format(d_Cantidad_prod).equals(".00")){
//								 		sheet.addCell(new jxl.write.Label(3, r, "0.00"));
//								 	}else{
//								 		sheet.addCell(new jxl.write.Label(3, r, df.format(d_Cantidad_prod)));
//								 	}
//								 	
//								 	if(df.format(d_CostoUni_prod*d_Cantidad_prod).equals(".00")){
//								 		sheet.addCell(new jxl.write.Label(4, r, "0.00"));
//								 	}else{
//								 		sheet.addCell(new jxl.write.Label(4, r, df.format(d_CostoUni_prod*d_Cantidad_prod)));
//								 	}
//							        
//							        
//							        
//							        double Operacion = (getComparaProdTerm(List_SKU.get(i)) - getComparaProdTermXA(List_SKU.get(i)));
//							        
//							        
//							        if(Operacion < 0 ){
//							        	sheet.addCell(new jxl.write.Label(5, r, df.format(Operacion),fCellstatus));
//							        }else{
//							        	if(df.format(Operacion).equals(".00")){
//									 		sheet.addCell(new jxl.write.Label(5, r, "0.00"));
//									 	}else{
//									 		sheet.addCell(new jxl.write.Label(5, r, df.format(Operacion)));
//									 	}
//							        	
//							        	
//							        }
//							        
//							        double OperaPrecio = ((d_CostoUni_prod*d_Cantidad_prod) - (d_CostoUni*d_Cantidad));
//							        
//							        if(OperaPrecio < 0 ){
//							        	sheet.addCell(new jxl.write.Label(6, r, df.format(OperaPrecio),fCellstatus));
//							        }else{
//							        	if(df.format(OperaPrecio).equals(".00")){
//									 		sheet.addCell(new jxl.write.Label(6, r, "0.00"));
//									 	}else{
//									 		sheet.addCell(new jxl.write.Label(6, r, df.format(OperaPrecio)));
//									 	}
//							        	
//							        	
//							        }
//							        
////							        sheet.addCell(new jxl.write.Label(3, r, (getComparaProdXA(List_SKU.get(i)) - getComparaProd(List_SKU.get(i)))+""));
//							        
//							       r++;
//							     
//							    }
//					
//					 workbook.write();
//					    workbook.close();
//					    r = 1;
//					    
//					    Thread.sleep(3000);
//					    
//					    Messagebox.show("Termina el Proceso del Reporte", "� Informaci�n !", org.zkoss.zul.Messagebox.OK, "");
//						 Executions.sendRedirect("/Reporte_de_Inventario_PT_vs_XA.xls");
//					
//	
////				}
//	
//	
//			} catch (Exception e) {
//				logger.error("" + e.getMessage());
//				e.printStackTrace();
//			} finally {
//				if(s!=null)
//					HibernateUtil.closeSession();
//			}
//			return detalles;
//		}
		
	

	public List<ComboFactory> getCatScrap()
	{
		Session s = null;
		Query q = null;
		TbCatCodigoScrap gc;
		List<ComboFactory> PerfilCliente = new ArrayList<ComboFactory>();
		PerfilCliente.add(new ComboFactory(0, "SELECCIONE"));
		try
		{
			s = HibernateUtil.currentSession();
			q = s.createQuery("from TbCatCodigoScrap g where g.nuActivo=1 " +
					"order by chCodigo");
			List exQ= q.list();
			if(exQ.size()>0)
				for(int i=0;i<exQ.size();i++)
				{
					gc = (TbCatCodigoScrap)exQ.get(i);
					PerfilCliente.add(new ComboFactory(gc.getNukIdCatCodScrap(), gc.getChCodigo()+" - "+ gc.getChNombre()));
				}
		}
		catch (HibernateException e) {
			logger.error("" + e.getMessage());
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();
		}

		return PerfilCliente;

	}
	
	public List<ComboFactory> getCatTiempoMuerto()
	{
		Session s = null;
		Query q = null;
		TbCatCodigoDownTime gc;
		List<ComboFactory> PerfilCliente = new ArrayList<ComboFactory>();
		PerfilCliente.add(new ComboFactory(0, "SELECCIONE"));
		try
		{
			s = HibernateUtil.currentSession();
			q = s.createQuery("from TbCatCodigoDownTime g where g.nuActivo=1 " +
					"order by chCodigo");
			List exQ= q.list();
			if(exQ.size()>0)
				for(int i=0;i<exQ.size();i++)
				{
					gc = (TbCatCodigoDownTime)exQ.get(i);
					PerfilCliente.add(new ComboFactory(gc.getNukIdCatCodigoDownTime(), gc.getChCodigo()+" - "+ gc.getChNombre()));
				}
		}
		catch (HibernateException e) {
			logger.error("" + e.getMessage());
		}
		finally
		{
			if(s!=null)
				HibernateUtil.closeSession();
		}

		return PerfilCliente;

	}

}	// end of class