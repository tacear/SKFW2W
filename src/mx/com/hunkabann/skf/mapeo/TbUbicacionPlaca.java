package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbUbicacionPlaca entity. @author MyEclipse Persistence Tools
 */

public class TbUbicacionPlaca implements java.io.Serializable {

	// Fields

	private Integer nukIdUbicaPlaca;
	private TbEmpresa tbEmpresa;
	private String chRack;
	private String chPasillo;
	private String chLocacion;
	private Boolean nuActivo;
	private Set tbPlacaUbicacionPs = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbUbicacionPlaca() {
	}

	/** minimal constructor */
	public TbUbicacionPlaca(Integer nukIdUbicaPlaca) {
		this.nukIdUbicaPlaca = nukIdUbicaPlaca;
	}

	/** full constructor */
	public TbUbicacionPlaca(Integer nukIdUbicaPlaca, TbEmpresa tbEmpresa,
			String chRack, String chPasillo, String chLocacion,
			Boolean nuActivo, Set tbPlacaUbicacionPs) {
		this.nukIdUbicaPlaca = nukIdUbicaPlaca;
		this.tbEmpresa = tbEmpresa;
		this.chRack = chRack;
		this.chPasillo = chPasillo;
		this.chLocacion = chLocacion;
		this.nuActivo = nuActivo;
		this.tbPlacaUbicacionPs = tbPlacaUbicacionPs;
	}

	// Property accessors

	public Integer getNukIdUbicaPlaca() {
		return this.nukIdUbicaPlaca;
	}

	public void setNukIdUbicaPlaca(Integer nukIdUbicaPlaca) {
		this.nukIdUbicaPlaca = nukIdUbicaPlaca;
	}

	public TbEmpresa getTbEmpresa() {
		return this.tbEmpresa;
	}

	public void setTbEmpresa(TbEmpresa tbEmpresa) {
		this.tbEmpresa = tbEmpresa;
	}

	public String getChRack() {
		return this.chRack;
	}

	public void setChRack(String chRack) {
		this.chRack = chRack;
	}

	public String getChPasillo() {
		return this.chPasillo;
	}

	public void setChPasillo(String chPasillo) {
		this.chPasillo = chPasillo;
	}

	public String getChLocacion() {
		return this.chLocacion;
	}

	public void setChLocacion(String chLocacion) {
		this.chLocacion = chLocacion;
	}

	public Boolean getNuActivo() {
		return this.nuActivo;
	}

	public void setNuActivo(Boolean nuActivo) {
		this.nuActivo = nuActivo;
	}

	public Set getTbPlacaUbicacionPs() {
		return this.tbPlacaUbicacionPs;
	}

	public void setTbPlacaUbicacionPs(Set tbPlacaUbicacionPs) {
		this.tbPlacaUbicacionPs = tbPlacaUbicacionPs;
	}

}