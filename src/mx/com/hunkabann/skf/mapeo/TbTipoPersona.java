package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbTipoPersona entity. @author MyEclipse Persistence Tools
 */

public class TbTipoPersona implements java.io.Serializable {

	// Fields

	private Integer nukIdTipoPersona;
	private TbEmpresa tbEmpresa;
	private String chClave;
	private String chNombre;
	private String chDescripcion;
	private Boolean nuActivo;
	private Set tbPersonas = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbTipoPersona() {
	}

	/** minimal constructor */
	public TbTipoPersona(Integer nukIdTipoPersona) {
		this.nukIdTipoPersona = nukIdTipoPersona;
	}

	/** full constructor */
	public TbTipoPersona(Integer nukIdTipoPersona, TbEmpresa tbEmpresa,
			String chClave, String chNombre, String chDescripcion,
			Boolean nuActivo, Set tbPersonas) {
		this.nukIdTipoPersona = nukIdTipoPersona;
		this.tbEmpresa = tbEmpresa;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.nuActivo = nuActivo;
		this.tbPersonas = tbPersonas;
	}

	// Property accessors

	public Integer getNukIdTipoPersona() {
		return this.nukIdTipoPersona;
	}

	public void setNukIdTipoPersona(Integer nukIdTipoPersona) {
		this.nukIdTipoPersona = nukIdTipoPersona;
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

	public Set getTbPersonas() {
		return this.tbPersonas;
	}

	public void setTbPersonas(Set tbPersonas) {
		this.tbPersonas = tbPersonas;
	}

}