package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbCatStatus entity. @author MyEclipse Persistence Tools
 */

public class TbCatStatus implements java.io.Serializable {

	// Fields

	private Integer nukIdStatus;
	private TbEmpresa tbEmpresa;
	private String chClave;
	private String chNombre;
	private String chDescripcion;
	private Boolean nuActivo;
	private Set tbOrdenManufacturas = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbCatStatus() {
	}

	/** minimal constructor */
	public TbCatStatus(Integer nukIdStatus) {
		this.nukIdStatus = nukIdStatus;
	}

	/** full constructor */
	public TbCatStatus(Integer nukIdStatus, TbEmpresa tbEmpresa,
			String chClave, String chNombre, String chDescripcion,
			Boolean nuActivo, Set tbOrdenManufacturas) {
		this.nukIdStatus = nukIdStatus;
		this.tbEmpresa = tbEmpresa;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.nuActivo = nuActivo;
		this.tbOrdenManufacturas = tbOrdenManufacturas;
	}

	// Property accessors

	public Integer getNukIdStatus() {
		return this.nukIdStatus;
	}

	public void setNukIdStatus(Integer nukIdStatus) {
		this.nukIdStatus = nukIdStatus;
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

	public Set getTbOrdenManufacturas() {
		return this.tbOrdenManufacturas;
	}

	public void setTbOrdenManufacturas(Set tbOrdenManufacturas) {
		this.tbOrdenManufacturas = tbOrdenManufacturas;
	}

}