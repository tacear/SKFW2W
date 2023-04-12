package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbRol entity. @author MyEclipse Persistence Tools
 */

public class TbRol implements java.io.Serializable {

	// Fields

	private Integer nukIdRol;
	private TbEmpresa tbEmpresa;
	private String chClave;
	private String chNombre;
	private String chDescripcion;
	private Boolean nuActivo;
	private Set tbPerfils = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbRol() {
	}

	/** minimal constructor */
	public TbRol(Integer nukIdRol) {
		this.nukIdRol = nukIdRol;
	}

	/** full constructor */
	public TbRol(Integer nukIdRol, TbEmpresa tbEmpresa, String chClave,
			String chNombre, String chDescripcion, Boolean nuActivo,
			Set tbPerfils) {
		this.nukIdRol = nukIdRol;
		this.tbEmpresa = tbEmpresa;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.nuActivo = nuActivo;
		this.tbPerfils = tbPerfils;
	}

	// Property accessors

	public Integer getNukIdRol() {
		return this.nukIdRol;
	}

	public void setNukIdRol(Integer nukIdRol) {
		this.nukIdRol = nukIdRol;
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

	public Set getTbPerfils() {
		return this.tbPerfils;
	}

	public void setTbPerfils(Set tbPerfils) {
		this.tbPerfils = tbPerfils;
	}

}