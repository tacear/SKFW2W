package mx.com.hunkabann.skf.backend;

import java.sql.*;

import mx.com.hunkabann.skf.util.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;

public class ConfiguracionDao {
	
	private Logger logger = Logger.getLogger(getClass());
	
	public String getValorConfiguracion(String cve){
		
		String l_return  = null;
		
		if(cve == null || cve.trim().isEmpty())
			return l_return;
		
		Session s = null;
		Statement stm = null;
		ResultSet rs = null;
		String l_stQry = null;

		try {
			
			s = HibernateUtil.currentSession();
			stm = s.connection().createStatement();

			l_stQry = "select chDescripcion from Tb_Configuracion where chClave = '" + cve + "'";
			
			logger.debug("l_stQry: " + l_stQry);
			
			rs = stm.executeQuery(l_stQry);

			if (rs.next()) 
				l_return = rs.getString(1);
				
		} catch (Exception e) {
			
			logger.error("" + e.getMessage());
			e.printStackTrace();
			
		} finally {
			
			if(s != null)
				HibernateUtil.closeSession();
		}
		
		return l_return;
		
	}	// getValorConfiguracion


}	// end of class
