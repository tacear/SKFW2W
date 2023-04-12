package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbUbicacionProcesoOm entity. @author MyEclipse Persistence Tools
 */

public class TbUbicacionProcesoOm implements java.io.Serializable {

	// Fields

	private Integer nukIdUbicaProcessOm;
	private TbMaquinaDispositivo tbMaquinaDispositivo;
	private TbProceso tbProceso;
	private TbCanal tbCanal;
	private TbTipoLocacion tbTipoLocacion;
	private TbSubArea tbSubArea;
	private Set tbOperacionOrdenManufacturas = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbUbicacionProcesoOm() {
	}

	/** minimal constructor */
	public TbUbicacionProcesoOm(Integer nukIdUbicaProcessOm) {
		this.nukIdUbicaProcessOm = nukIdUbicaProcessOm;
	}

	/** full constructor */
	public TbUbicacionProcesoOm(Integer nukIdUbicaProcessOm,
			TbMaquinaDispositivo tbMaquinaDispositivo, TbProceso tbProceso,
			TbCanal tbCanal, TbTipoLocacion tbTipoLocacion,
			TbSubArea tbSubArea, Set tbOperacionOrdenManufacturas) {
		this.nukIdUbicaProcessOm = nukIdUbicaProcessOm;
		this.tbMaquinaDispositivo = tbMaquinaDispositivo;
		this.tbProceso = tbProceso;
		this.tbCanal = tbCanal;
		this.tbTipoLocacion = tbTipoLocacion;
		this.tbSubArea = tbSubArea;
		this.tbOperacionOrdenManufacturas = tbOperacionOrdenManufacturas;
	}

	// Property accessors

	public Integer getNukIdUbicaProcessOm() {
		return this.nukIdUbicaProcessOm;
	}

	public void setNukIdUbicaProcessOm(Integer nukIdUbicaProcessOm) {
		this.nukIdUbicaProcessOm = nukIdUbicaProcessOm;
	}

	public TbMaquinaDispositivo getTbMaquinaDispositivo() {
		return this.tbMaquinaDispositivo;
	}

	public void setTbMaquinaDispositivo(
			TbMaquinaDispositivo tbMaquinaDispositivo) {
		this.tbMaquinaDispositivo = tbMaquinaDispositivo;
	}

	public TbProceso getTbProceso() {
		return this.tbProceso;
	}

	public void setTbProceso(TbProceso tbProceso) {
		this.tbProceso = tbProceso;
	}

	public TbCanal getTbCanal() {
		return this.tbCanal;
	}

	public void setTbCanal(TbCanal tbCanal) {
		this.tbCanal = tbCanal;
	}

	public TbTipoLocacion getTbTipoLocacion() {
		return this.tbTipoLocacion;
	}

	public void setTbTipoLocacion(TbTipoLocacion tbTipoLocacion) {
		this.tbTipoLocacion = tbTipoLocacion;
	}

	public TbSubArea getTbSubArea() {
		return this.tbSubArea;
	}

	public void setTbSubArea(TbSubArea tbSubArea) {
		this.tbSubArea = tbSubArea;
	}

	public Set getTbOperacionOrdenManufacturas() {
		return this.tbOperacionOrdenManufacturas;
	}

	public void setTbOperacionOrdenManufacturas(Set tbOperacionOrdenManufacturas) {
		this.tbOperacionOrdenManufacturas = tbOperacionOrdenManufacturas;
	}

}