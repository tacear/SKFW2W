package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * TbMateriaPrima entity. @author MyEclipse Persistence Tools
 */

public class TbMateriaPrima implements java.io.Serializable {

	// Fields

	private Integer nukIdMateriaPrima;
	private TbEmpresa tbEmpresa;
	private TbCatUm tbCatUm;
	private String chSku;
	private String chDescripcion;
	private Double nuCostoPromedio;
	private Timestamp dtFechaAlta;
	private Timestamp dtFechaModifica;
	private Boolean nuActivo;
	private Set tbEntregaRecepcions = new HashSet(0);
	private Set tbProdTermMatPrims = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbMateriaPrima() {
	}

	/** minimal constructor */
	public TbMateriaPrima(Integer nukIdMateriaPrima) {
		this.nukIdMateriaPrima = nukIdMateriaPrima;
	}

	/** full constructor */
	public TbMateriaPrima(Integer nukIdMateriaPrima, TbEmpresa tbEmpresa,
			TbCatUm tbCatUm, String chSku, String chDescripcion,
			Double nuCostoPromedio, Timestamp dtFechaAlta,
			Timestamp dtFechaModifica, Boolean nuActivo,
			Set tbEntregaRecepcions, Set tbProdTermMatPrims) {
		this.nukIdMateriaPrima = nukIdMateriaPrima;
		this.tbEmpresa = tbEmpresa;
		this.tbCatUm = tbCatUm;
		this.chSku = chSku;
		this.chDescripcion = chDescripcion;
		this.nuCostoPromedio = nuCostoPromedio;
		this.dtFechaAlta = dtFechaAlta;
		this.dtFechaModifica = dtFechaModifica;
		this.nuActivo = nuActivo;
		this.tbEntregaRecepcions = tbEntregaRecepcions;
		this.tbProdTermMatPrims = tbProdTermMatPrims;
	}

	// Property accessors

	public Integer getNukIdMateriaPrima() {
		return this.nukIdMateriaPrima;
	}

	public void setNukIdMateriaPrima(Integer nukIdMateriaPrima) {
		this.nukIdMateriaPrima = nukIdMateriaPrima;
	}

	public TbEmpresa getTbEmpresa() {
		return this.tbEmpresa;
	}

	public void setTbEmpresa(TbEmpresa tbEmpresa) {
		this.tbEmpresa = tbEmpresa;
	}

	public TbCatUm getTbCatUm() {
		return this.tbCatUm;
	}

	public void setTbCatUm(TbCatUm tbCatUm) {
		this.tbCatUm = tbCatUm;
	}

	public String getChSku() {
		return this.chSku;
	}

	public void setChSku(String chSku) {
		this.chSku = chSku;
	}

	public String getChDescripcion() {
		return this.chDescripcion;
	}

	public void setChDescripcion(String chDescripcion) {
		this.chDescripcion = chDescripcion;
	}

	public Double getNuCostoPromedio() {
		return this.nuCostoPromedio;
	}

	public void setNuCostoPromedio(Double nuCostoPromedio) {
		this.nuCostoPromedio = nuCostoPromedio;
	}

	public Timestamp getDtFechaAlta() {
		return this.dtFechaAlta;
	}

	public void setDtFechaAlta(Timestamp dtFechaAlta) {
		this.dtFechaAlta = dtFechaAlta;
	}

	public Timestamp getDtFechaModifica() {
		return this.dtFechaModifica;
	}

	public void setDtFechaModifica(Timestamp dtFechaModifica) {
		this.dtFechaModifica = dtFechaModifica;
	}

	public Boolean getNuActivo() {
		return this.nuActivo;
	}

	public void setNuActivo(Boolean nuActivo) {
		this.nuActivo = nuActivo;
	}

	public Set getTbEntregaRecepcions() {
		return this.tbEntregaRecepcions;
	}

	public void setTbEntregaRecepcions(Set tbEntregaRecepcions) {
		this.tbEntregaRecepcions = tbEntregaRecepcions;
	}

	public Set getTbProdTermMatPrims() {
		return this.tbProdTermMatPrims;
	}

	public void setTbProdTermMatPrims(Set tbProdTermMatPrims) {
		this.tbProdTermMatPrims = tbProdTermMatPrims;
	}

}