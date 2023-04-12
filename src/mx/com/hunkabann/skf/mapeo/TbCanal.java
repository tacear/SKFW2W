package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * TbCanal entity. @author MyEclipse Persistence Tools
 */

public class TbCanal implements java.io.Serializable {

	// Fields

	private Integer nukIdCanal;
	private TbEmpresa tbEmpresa;
	private String chClave;
	private String chNombre;
	private String chDescripcion;
	private Timestamp ctFechaAlta;
	private Boolean nuActivo;
	private Set tbUbicacionProcesoOms = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbCanal() {
	}

	/** minimal constructor */
	public TbCanal(Integer nukIdCanal) {
		this.nukIdCanal = nukIdCanal;
	}

	/** full constructor */
	public TbCanal(Integer nukIdCanal, TbEmpresa tbEmpresa, String chClave,
			String chNombre, String chDescripcion, Timestamp ctFechaAlta,
			Boolean nuActivo, Set tbUbicacionProcesoOms) {
		this.nukIdCanal = nukIdCanal;
		this.tbEmpresa = tbEmpresa;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.ctFechaAlta = ctFechaAlta;
		this.nuActivo = nuActivo;
		this.tbUbicacionProcesoOms = tbUbicacionProcesoOms;
	}

	// Property accessors

	public Integer getNukIdCanal() {
		return this.nukIdCanal;
	}

	public void setNukIdCanal(Integer nukIdCanal) {
		this.nukIdCanal = nukIdCanal;
	}

	public TbEmpresa getTbEmpresa() {
		return this.tbEmpresa;
	}

	public void setTbEmpresa(TbEmpresa tbEmpresa) {
		this.tbEmpresa = tbEmpresa;
	}

	public String getChClave() {
		return this.chClave;
	}

	public void setChClave(String chClave) {
		this.chClave = chClave;
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

	public Timestamp getCtFechaAlta() {
		return this.ctFechaAlta;
	}

	public void setCtFechaAlta(Timestamp ctFechaAlta) {
		this.ctFechaAlta = ctFechaAlta;
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

}