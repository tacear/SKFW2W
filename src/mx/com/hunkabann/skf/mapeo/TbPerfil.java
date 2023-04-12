package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbPerfil entity. @author MyEclipse Persistence Tools
 */

public class TbPerfil implements java.io.Serializable {

	// Fields

	private Integer nukIdPerfil;
	private TbEmpresa tbEmpresa;
	private String chClave;
	private String chNombre;
	private String chDescripcion;
	private Boolean nuActivo;
	private Set tbRols = new HashSet(0);
	private Set tbUsuarios = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbPerfil() {
	}

	/** minimal constructor */
	public TbPerfil(Integer nukIdPerfil) {
		this.nukIdPerfil = nukIdPerfil;
	}

	/** full constructor */
	public TbPerfil(Integer nukIdPerfil, TbEmpresa tbEmpresa, String chClave,
			String chNombre, String chDescripcion, Boolean nuActivo,
			Set tbRols, Set tbUsuarios) {
		this.nukIdPerfil = nukIdPerfil;
		this.tbEmpresa = tbEmpresa;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.nuActivo = nuActivo;
		this.tbRols = tbRols;
		this.tbUsuarios = tbUsuarios;
	}

	// Property accessors

	public Integer getNukIdPerfil() {
		return this.nukIdPerfil;
	}

	public void setNukIdPerfil(Integer nukIdPerfil) {
		this.nukIdPerfil = nukIdPerfil;
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

	public Set getTbRols() {
		return this.tbRols;
	}

	public void setTbRols(Set tbRols) {
		this.tbRols = tbRols;
	}

	public Set getTbUsuarios() {
		return this.tbUsuarios;
	}

	public void setTbUsuarios(Set tbUsuarios) {
		this.tbUsuarios = tbUsuarios;
	}

}