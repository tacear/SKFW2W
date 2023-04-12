package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbTipoPlaca entity. @author MyEclipse Persistence Tools
 */

public class TbTipoPlaca implements java.io.Serializable {

	// Fields

	private Integer nukIdTipoPlaca;
	private TbEmpresa tbEmpresa;
	private String chClave;
	private String chNombre;
	private String chDescripcion;
	private Boolean nuActivo;
	private Set tbPlacas = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbTipoPlaca() {
	}

	/** minimal constructor */
	public TbTipoPlaca(Integer nukIdTipoPlaca) {
		this.nukIdTipoPlaca = nukIdTipoPlaca;
	}

	/** full constructor */
	public TbTipoPlaca(Integer nukIdTipoPlaca, TbEmpresa tbEmpresa,
			String chClave, String chNombre, String chDescripcion,
			Boolean nuActivo, Set tbPlacas) {
		this.nukIdTipoPlaca = nukIdTipoPlaca;
		this.tbEmpresa = tbEmpresa;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.nuActivo = nuActivo;
		this.tbPlacas = tbPlacas;
	}

	// Property accessors

	public Integer getNukIdTipoPlaca() {
		return this.nukIdTipoPlaca;
	}

	public void setNukIdTipoPlaca(Integer nukIdTipoPlaca) {
		this.nukIdTipoPlaca = nukIdTipoPlaca;
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

	public Set getTbPlacas() {
		return this.tbPlacas;
	}

	public void setTbPlacas(Set tbPlacas) {
		this.tbPlacas = tbPlacas;
	}

}