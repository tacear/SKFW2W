package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbCorporativo entity. @author MyEclipse Persistence Tools
 */

public class TbCorporativo implements java.io.Serializable {

	// Fields

	private Integer nukIdCorporativo;
	private String chClave;
	private String chNombre;
	private String chDescripcion;
	private Boolean nuActivo;
	private Set tbEmpresas = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbCorporativo() {
	}

	/** minimal constructor */
	public TbCorporativo(Integer nukIdCorporativo) {
		this.nukIdCorporativo = nukIdCorporativo;
	}

	/** full constructor */
	public TbCorporativo(Integer nukIdCorporativo, String chClave,
			String chNombre, String chDescripcion, Boolean nuActivo,
			Set tbEmpresas) {
		this.nukIdCorporativo = nukIdCorporativo;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.nuActivo = nuActivo;
		this.tbEmpresas = tbEmpresas;
	}

	// Property accessors

	public Integer getNukIdCorporativo() {
		return this.nukIdCorporativo;
	}

	public void setNukIdCorporativo(Integer nukIdCorporativo) {
		this.nukIdCorporativo = nukIdCorporativo;
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

	public Set getTbEmpresas() {
		return this.tbEmpresas;
	}

	public void setTbEmpresas(Set tbEmpresas) {
		this.tbEmpresas = tbEmpresas;
	}

}