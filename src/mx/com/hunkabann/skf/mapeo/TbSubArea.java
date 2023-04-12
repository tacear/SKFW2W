package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbSubArea entity. @author MyEclipse Persistence Tools
 */

public class TbSubArea implements java.io.Serializable {

	// Fields

	private Integer nukIdSubArea;
	private TbArea tbArea;
	private String chClave;
	private String chNombre;
	private String chDescripcion;
	private Boolean nuActivo;
	private Set tbPlacas = new HashSet(0);
	private Set tbUbicacionProcesoOms = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbSubArea() {
	}

	/** minimal constructor */
	public TbSubArea(Integer nukIdSubArea) {
		this.nukIdSubArea = nukIdSubArea;
	}

	/** full constructor */
	public TbSubArea(Integer nukIdSubArea, TbArea tbArea, String chClave,
			String chNombre, String chDescripcion, Boolean nuActivo,
			Set tbPlacas, Set tbUbicacionProcesoOms) {
		this.nukIdSubArea = nukIdSubArea;
		this.tbArea = tbArea;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.nuActivo = nuActivo;
		this.tbPlacas = tbPlacas;
		this.tbUbicacionProcesoOms = tbUbicacionProcesoOms;
	}

	// Property accessors

	public Integer getNukIdSubArea() {
		return this.nukIdSubArea;
	}

	public void setNukIdSubArea(Integer nukIdSubArea) {
		this.nukIdSubArea = nukIdSubArea;
	}

	public TbArea getTbArea() {
		return this.tbArea;
	}

	public void setTbArea(TbArea tbArea) {
		this.tbArea = tbArea;
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

	public Boolean getNuActivo() {
		return this.nuActivo;
	}

	public void setNuActivo(Boolean nuActivo) {
		this.nuActivo = nuActivo;
	}

	public Set getTbPlacas() {
		return this.tbPlacas;
	}

	public void setTbPlacas(Set tbPlacas) {
		this.tbPlacas = tbPlacas;
	}

	public Set getTbUbicacionProcesoOms() {
		return this.tbUbicacionProcesoOms;
	}

	public void setTbUbicacionProcesoOms(Set tbUbicacionProcesoOms) {
		this.tbUbicacionProcesoOms = tbUbicacionProcesoOms;
	}

}