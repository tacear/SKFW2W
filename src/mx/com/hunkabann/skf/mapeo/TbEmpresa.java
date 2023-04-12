package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;

/**
 * TbEmpresa entity. @author MyEclipse Persistence Tools
 */

public class TbEmpresa implements java.io.Serializable {

	// Fields

	private Integer nukIdEmpresa;
	private TbCorporativo tbCorporativo;
	private String chClave;
	private String chNombre;
	private String chDescripcion;
	private String chResidenciaFiscal;
	private String chRegimenFiscal;
	private Boolean nuActivo;
	private Set tbProcesos = new HashSet(0);
	private Set tbRols = new HashSet(0);
	private Set tbUbicacionPlacas = new HashSet(0);
	private Set tbMaquinaDispositivos = new HashSet(0);
	private Set tbTipoLocacions = new HashSet(0);
	private Set tbConfiguracions = new HashSet(0);
	private Set tbProductoTerminados = new HashSet(0);
	private Set tbAreas = new HashSet(0);
	private Set tbCatCodigoFtqs = new HashSet(0);
	private Set tbOrdenManufacturas = new HashSet(0);
	private Set tbEntregaRecepcions = new HashSet(0);
	private Set tbTipoPersonas = new HashSet(0);
	private Set tbPlacaProdTerms = new HashSet(0);
	private Set tbCatStatuses = new HashSet(0);
	private Set tbCatUms = new HashSet(0);
	private Set tbCanals = new HashSet(0);
	private Set tbOperacionOrdenManufacturas = new HashSet(0);
	private Set tbPerfils = new HashSet(0);
	private Set tbCatCodigoScraps = new HashSet(0);
	private Set tbMateriaPrimas = new HashSet(0);
	private Set tbCatCodigoDownTimes = new HashSet(0);
	private Set tbTipoPlacas = new HashSet(0);
	private Set tbTipoPlanControls = new HashSet(0);
	private Set tbPlacas = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbEmpresa() {
	}

	/** minimal constructor */
	public TbEmpresa(Integer nukIdEmpresa) {
		this.nukIdEmpresa = nukIdEmpresa;
	}

	/** full constructor */
	public TbEmpresa(Integer nukIdEmpresa, TbCorporativo tbCorporativo,
			String chClave, String chNombre, String chDescripcion,
			String chResidenciaFiscal, String chRegimenFiscal,
			Boolean nuActivo, Set tbProcesos, Set tbRols,
			Set tbUbicacionPlacas, Set tbMaquinaDispositivos,
			Set tbTipoLocacions, Set tbConfiguracions,
			Set tbProductoTerminados, Set tbAreas, Set tbCatCodigoFtqs,
			Set tbOrdenManufacturas, Set tbEntregaRecepcions,
			Set tbTipoPersonas, Set tbPlacaProdTerms, Set tbCatStatuses,
			Set tbCatUms, Set tbCanals, Set tbOperacionOrdenManufacturas,
			Set tbPerfils, Set tbCatCodigoScraps, Set tbMateriaPrimas,
			Set tbCatCodigoDownTimes, Set tbTipoPlacas, Set tbTipoPlanControls,
			Set tbPlacas) {
		this.nukIdEmpresa = nukIdEmpresa;
		this.tbCorporativo = tbCorporativo;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.chResidenciaFiscal = chResidenciaFiscal;
		this.chRegimenFiscal = chRegimenFiscal;
		this.nuActivo = nuActivo;
		this.tbProcesos = tbProcesos;
		this.tbRols = tbRols;
		this.tbUbicacionPlacas = tbUbicacionPlacas;
		this.tbMaquinaDispositivos = tbMaquinaDispositivos;
		this.tbTipoLocacions = tbTipoLocacions;
		this.tbConfiguracions = tbConfiguracions;
		this.tbProductoTerminados = tbProductoTerminados;
		this.tbAreas = tbAreas;
		this.tbCatCodigoFtqs = tbCatCodigoFtqs;
		this.tbOrdenManufacturas = tbOrdenManufacturas;
		this.tbEntregaRecepcions = tbEntregaRecepcions;
		this.tbTipoPersonas = tbTipoPersonas;
		this.tbPlacaProdTerms = tbPlacaProdTerms;
		this.tbCatStatuses = tbCatStatuses;
		this.tbCatUms = tbCatUms;
		this.tbCanals = tbCanals;
		this.tbOperacionOrdenManufacturas = tbOperacionOrdenManufacturas;
		this.tbPerfils = tbPerfils;
		this.tbCatCodigoScraps = tbCatCodigoScraps;
		this.tbMateriaPrimas = tbMateriaPrimas;
		this.tbCatCodigoDownTimes = tbCatCodigoDownTimes;
		this.tbTipoPlacas = tbTipoPlacas;
		this.tbTipoPlanControls = tbTipoPlanControls;
		this.tbPlacas = tbPlacas;
	}

	// Property accessors

	public Integer getNukIdEmpresa() {
		return this.nukIdEmpresa;
	}

	public void setNukIdEmpresa(Integer nukIdEmpresa) {
		this.nukIdEmpresa = nukIdEmpresa;
	}

	public TbCorporativo getTbCorporativo() {
		return this.tbCorporativo;
	}

	public void setTbCorporativo(TbCorporativo tbCorporativo) {
		this.tbCorporativo = tbCorporativo;
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

	public String getChResidenciaFiscal() {
		return this.chResidenciaFiscal;
	}

	public void setChResidenciaFiscal(String chResidenciaFiscal) {
		this.chResidenciaFiscal = chResidenciaFiscal;
	}

	public String getChRegimenFiscal() {
		return this.chRegimenFiscal;
	}

	public void setChRegimenFiscal(String chRegimenFiscal) {
		this.chRegimenFiscal = chRegimenFiscal;
	}

	public Boolean getNuActivo() {
		return this.nuActivo;
	}

	public void setNuActivo(Boolean nuActivo) {
		this.nuActivo = nuActivo;
	}

	public Set getTbProcesos() {
		return this.tbProcesos;
	}

	public void setTbProcesos(Set tbProcesos) {
		this.tbProcesos = tbProcesos;
	}

	public Set getTbRols() {
		return this.tbRols;
	}

	public void setTbRols(Set tbRols) {
		this.tbRols = tbRols;
	}

	public Set getTbUbicacionPlacas() {
		return this.tbUbicacionPlacas;
	}

	public void setTbUbicacionPlacas(Set tbUbicacionPlacas) {
		this.tbUbicacionPlacas = tbUbicacionPlacas;
	}

	public Set getTbMaquinaDispositivos() {
		return this.tbMaquinaDispositivos;
	}

	public void setTbMaquinaDispositivos(Set tbMaquinaDispositivos) {
		this.tbMaquinaDispositivos = tbMaquinaDispositivos;
	}

	public Set getTbTipoLocacions() {
		return this.tbTipoLocacions;
	}

	public void setTbTipoLocacions(Set tbTipoLocacions) {
		this.tbTipoLocacions = tbTipoLocacions;
	}

	public Set getTbConfiguracions() {
		return this.tbConfiguracions;
	}

	public void setTbConfiguracions(Set tbConfiguracions) {
		this.tbConfiguracions = tbConfiguracions;
	}

	public Set getTbProductoTerminados() {
		return this.tbProductoTerminados;
	}

	public void setTbProductoTerminados(Set tbProductoTerminados) {
		this.tbProductoTerminados = tbProductoTerminados;
	}

	public Set getTbAreas() {
		return this.tbAreas;
	}

	public void setTbAreas(Set tbAreas) {
		this.tbAreas = tbAreas;
	}

	public Set getTbCatCodigoFtqs() {
		return this.tbCatCodigoFtqs;
	}

	public void setTbCatCodigoFtqs(Set tbCatCodigoFtqs) {
		this.tbCatCodigoFtqs = tbCatCodigoFtqs;
	}

	public Set getTbOrdenManufacturas() {
		return this.tbOrdenManufacturas;
	}

	public void setTbOrdenManufacturas(Set tbOrdenManufacturas) {
		this.tbOrdenManufacturas = tbOrdenManufacturas;
	}

	public Set getTbEntregaRecepcions() {
		return this.tbEntregaRecepcions;
	}

	public void setTbEntregaRecepcions(Set tbEntregaRecepcions) {
		this.tbEntregaRecepcions = tbEntregaRecepcions;
	}

	public Set getTbTipoPersonas() {
		return this.tbTipoPersonas;
	}

	public void setTbTipoPersonas(Set tbTipoPersonas) {
		this.tbTipoPersonas = tbTipoPersonas;
	}

	public Set getTbPlacaProdTerms() {
		return this.tbPlacaProdTerms;
	}

	public void setTbPlacaProdTerms(Set tbPlacaProdTerms) {
		this.tbPlacaProdTerms = tbPlacaProdTerms;
	}

	public Set getTbCatStatuses() {
		return this.tbCatStatuses;
	}

	public void setTbCatStatuses(Set tbCatStatuses) {
		this.tbCatStatuses = tbCatStatuses;
	}

	public Set getTbCatUms() {
		return this.tbCatUms;
	}

	public void setTbCatUms(Set tbCatUms) {
		this.tbCatUms = tbCatUms;
	}

	public Set getTbCanals() {
		return this.tbCanals;
	}

	public void setTbCanals(Set tbCanals) {
		this.tbCanals = tbCanals;
	}

	public Set getTbOperacionOrdenManufacturas() {
		return this.tbOperacionOrdenManufacturas;
	}

	public void setTbOperacionOrdenManufacturas(Set tbOperacionOrdenManufacturas) {
		this.tbOperacionOrdenManufacturas = tbOperacionOrdenManufacturas;
	}

	public Set getTbPerfils() {
		return this.tbPerfils;
	}

	public void setTbPerfils(Set tbPerfils) {
		this.tbPerfils = tbPerfils;
	}

	public Set getTbCatCodigoScraps() {
		return this.tbCatCodigoScraps;
	}

	public void setTbCatCodigoScraps(Set tbCatCodigoScraps) {
		this.tbCatCodigoScraps = tbCatCodigoScraps;
	}

	public Set getTbMateriaPrimas() {
		return this.tbMateriaPrimas;
	}

	public void setTbMateriaPrimas(Set tbMateriaPrimas) {
		this.tbMateriaPrimas = tbMateriaPrimas;
	}

	public Set getTbCatCodigoDownTimes() {
		return this.tbCatCodigoDownTimes;
	}

	public void setTbCatCodigoDownTimes(Set tbCatCodigoDownTimes) {
		this.tbCatCodigoDownTimes = tbCatCodigoDownTimes;
	}

	public Set getTbTipoPlacas() {
		return this.tbTipoPlacas;
	}

	public void setTbTipoPlacas(Set tbTipoPlacas) {
		this.tbTipoPlacas = tbTipoPlacas;
	}

	public Set getTbTipoPlanControls() {
		return this.tbTipoPlanControls;
	}

	public void setTbTipoPlanControls(Set tbTipoPlanControls) {
		this.tbTipoPlanControls = tbTipoPlanControls;
	}

	public Set getTbPlacas() {
		return this.tbPlacas;
	}

	public void setTbPlacas(Set tbPlacas) {
		this.tbPlacas = tbPlacas;
	}

}