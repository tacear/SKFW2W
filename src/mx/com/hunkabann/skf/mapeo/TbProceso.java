package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * TbProceso entity. @author MyEclipse Persistence Tools
 */

public class TbProceso implements java.io.Serializable {

	// Fields

	private Integer nukIdProceso;
	private TbEmpresa tbEmpresa;
	private String chNumProceso;
	private String chNombre;
	private String chDescripcion;
	private Timestamp dtFechaAlta;
	private Boolean nuActivo;
	private Set tbUbicacionProcesoOms = new HashSet(0);
	private Set tbPlanControls = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbProceso() {
	}

	/** minimal constructor */
	public TbProceso(Integer nukIdProceso) {
		this.nukIdProceso = nukIdProceso;
	}

	/** full constructor */
	public TbProceso(Integer nukIdProceso, TbEmpresa tbEmpresa,
			String chNumProceso, String chNombre, String chDescripcion,
			Timestamp dtFechaAlta, Boolean nuActivo, Set tbUbicacionProcesoOms,
			Set tbPlanControls) {
		this.nukIdProceso = nukIdProceso;
		this.tbEmpresa = tbEmpresa;
		this.chNumProceso = chNumProceso;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.dtFechaAlta = dtFechaAlta;
		this.nuActivo = nuActivo;
		this.tbUbicacionProcesoOms = tbUbicacionProcesoOms;
		this.tbPlanControls = tbPlanControls;
	}

	// Property accessors

	public Integer getNukIdProceso() {
		return this.nukIdProceso;
	}

	public void setNukIdProceso(Integer nukIdProceso) {
		this.nukIdProceso = nukIdProceso;
	}

	public TbEmpresa getTbEmpresa() {
		return this.tbEmpresa;
	}

	public void setTbEmpresa(TbEmpresa tbEmpresa) {
		this.tbEmpresa = tbEmpresa;
	}

	public String getChNumProceso() {
		return this.chNumProceso;
	}

	public void setChNumProceso(String chNumProceso) {
		this.chNumProceso = chNumProceso;
	}

	public String getChNombre() {
		return this.chNombre;
	}

	public void setChNombre(String chNombre) {
		this.chNombre = chNombre;
	}

	public String getChDescripcion() {
		return this.chDescripcion;
	}

	public void setChDescripcion(String chDescripcion) {
		this.chDescripcion = chDescripcion;
	}

	public Timestamp getDtFechaAlta() {
		return this.dtFechaAlta;
	}

	public void setDtFechaAlta(Timestamp dtFechaAlta) {
		this.dtFechaAlta = dtFechaAlta;
	}

	public Boolean getNuActivo() {
		return this.nuActivo;
	}

	public void setNuActivo(Boolean nuActivo) {
		this.nuActivo = nuActivo;
	}

	public Set getTbUbicacionProcesoOms() {
		return this.tbUbicacionProcesoOms;
	}

	public void setTbUbicacionProcesoOms(Set tbUbicacionProcesoOms) {
		this.tbUbicacionProcesoOms = tbUbicacionProcesoOms;
	}

	public Set getTbPlanControls() {
		return this.tbPlanControls;
	}

	public void setTbPlanControls(Set tbPlanControls) {
		this.tbPlanControls = tbPlanControls;
	}

}