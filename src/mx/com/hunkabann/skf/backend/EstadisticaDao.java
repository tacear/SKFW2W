package mx.com.hunkabann.skf.backend;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import mx.com.hunkabann.skf.util.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;

public class EstadisticaDao {
	
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Obtiene los datos para graficar
	 * @return
	 */
	public HashMap<String, Double> getGraficaData(){
		
		HashMap<String, Double> l_return  = new HashMap<String, Double>();
		
		Session s = null;
		Statement stm = null;
		ResultSet rs = null;
		String l_stQry = null;

		try {
			
			s = HibernateUtil.currentSession();
			stm = s.connection().createStatement();

			l_stQry = "select om.chOrdenManufactura, oom.nuCantidad, om.nuTotal, round( (cast(oom.nuCantidad as float) / cast(om.nuTotal as float)) * 100 , 2, 1) as avance "
					+ " from Tb_Orden_Manufactura om "
					+ " left outer join Tb_Operacion_OrdenManufactura oom on om.nukIdOrdenManufactura = oom.nukIdOrdenManufactura "
					+ " where om.nuActivo = 1 ";
			
			logger.debug("l_stQry: " + l_stQry);
			
			rs = stm.executeQuery(l_stQry);

			while (rs.next()) {
				
				if(!l_return.containsKey(rs.getString(1).trim()))
					l_return.put(rs.getString(1).trim(), rs.getDouble(4));
				
			}
				
		} catch (Exception e) {
			
			logger.error("" + e.getMessage());
			e.printStackTrace();
			
		} finally {
			
			if(s != null)
				HibernateUtil.closeSession();
		}
		
		return l_return;
		
	}	// getGraficaData
	
	
	/**
	 * Obtiene los datos de configuración para las gráficas
	 * @param conf
	 */
	public void getConfigurationData(HashMap<String, String> conf)
	{
		if(conf == null || conf.isEmpty())
			return;
		
		ConfiguracionDao dao = new ConfiguracionDao();
		
		for (String cve : conf.keySet())
			conf.put(cve, dao.getValorConfiguracion(cve));

		dao = null;
		
	}	// getConfigurationData

}	// end of class
