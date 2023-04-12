package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbArea entity. @author MyEclipse Persistence Tools
 */

public class TbArea implements java.io.Serializable {

	// Fields

	private Integer nukIdArea;
	private TbEmpresa tbEmpresa;
	private String chClave;
	private String chNombre;
	private String chDescripcion;
	private Boolean nuActivo;
	private Set tbSubAreas = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbArea() {
	}

	/** minimal constructor */
	public TbArea(Integer nukIdArea) {
		this.nukIdArea = nukIdArea;
	}

	/** full constructor */
	public TbArea(Integer nukIdArea, TbEmpresa tbEmpresa, String chClave,
			String chNombre, String chDescripcion, Boolean nuActivo,
			Set tbSubAreas) {
		this.nukIdArea = nukIdArea;
		this.tbEmpresa = tbEmpresa;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.nuActivo = nuActivo;
		this.tbSubAreas = tbSubAreas;
	}

	// Property accessors

	public Integer getNukIdArea() {
		return this.nukIdArea;
	}

	public void setNukIdArea(Integer nukIdArea) {
		this.nukIdArea = nukIdArea;
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

	public Boolean getNuActivo() {
		return this.nuActivo;
	}

	public void setNuActivo(Boolean nuActivo) {
		this.nuActivo = nuActivo;
	}

	public Set getTbSubAreas() {
		return this.tbSubAreas;
	}

	public void setTbSubAreas(Set tbSubAreas) {
		this.tbSubAreas = tbSubAreas;
	}

}