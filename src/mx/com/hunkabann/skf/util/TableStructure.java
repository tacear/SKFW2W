package mx.com.hunkabann.skf.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/** 
 * Genera una estructura que va atener la info para evitar lo lazys
 * @author Luis Trevilla
 *
 */
public class TableStructure 
{
	Logger logger = Logger.getLogger(this.getClass());
	private List miembros;
	private String tabla;
	private TableStructure structure;
	private int nivel = 0;
	
	public TableStructure()
	{
		miembros = new ArrayList();
	}
	
	
	/**
	 * Agrega estructura de tabla
	 * @param p_stStructura
	 */
	public void agregaEstructura(String p_stStructura)
	{
		TableStructure l_obStructure = null;
		TableStructure l_obNewStructure = null;
		int l_inLevel = 0;
		boolean l_boExiste = false;
		String [] l_astEstructura = p_stStructura.split("\\.");
		
		//logger.info(p_stStructura);
		//logger.info(l_astEstructura.length);
		
		// si es la primera vez la instacía
		if(structure == null)
		{
			structure = new TableStructure();
			structure.setNivel(l_inLevel);
		}
		
		if(l_astEstructura.length == 0)
		{
			l_obNewStructure = new TableStructure();
			l_obNewStructure.setNivel(1);
			l_obNewStructure.setTabla(p_stStructura);
			structure.add(l_obNewStructure);
			return;
		}
		
		
		//recorre la estructura a ingresar
		for(int i = 0; i < l_astEstructura.length; i++)
		{
			if(i == 0)
				l_obStructure = structure;
			
			l_boExiste = false;
			for(int j = 0; j < l_obStructure.getMiembros().size(); j++)
			{
				// verifica si existe en la estructura
				if(l_astEstructura[i].equals(l_obStructure.get(j).getTabla()))
				{
					// existe lo encuentra
					l_boExiste = true;
					l_obStructure = l_obStructure.get(j);
					break;
				}
			}
			
			if(!l_boExiste)
			{
				// lo agrega
				l_obNewStructure = new TableStructure();
				l_obNewStructure.setNivel(i + 1);
				l_obNewStructure.setTabla(l_astEstructura[i]);
				l_obStructure.add(l_obNewStructure);
				l_obStructure = l_obNewStructure;
			}
		
		}
		
		// libera recursos
		l_astEstructura = null;
		
	}	// agregaEstructura
	
	
	public void add(TableStructure p_stMiembro)
	{
		miembros.add(p_stMiembro);
	}
	
	public TableStructure get(int p_inPos)
	{
		return (TableStructure)miembros.get(p_inPos);
	}
	
	public List getMiembros() {
		return miembros;
	}
	
	public void setMiembros(List miembros) {
		this.miembros = miembros;
	}
	
	public String getTabla() {
		return tabla;
	}
	
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
	
	public TableStructure getStructure() {
		return structure;
	}

	public void setStructure(TableStructure structure) {
		this.structure = structure;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

}	// end of class
