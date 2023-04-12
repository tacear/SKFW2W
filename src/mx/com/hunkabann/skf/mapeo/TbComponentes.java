package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * TbComponentes entity. @author MyEclipse Persistence Tools
 */

public class TbComponentes implements java.io.Serializable {

	// Fields

	private Integer nukIdComponente;
	private TbEmpresa tbEmpresa;
	private String chSku;
	private String chDescripcion;
	private Double nuCostoPromedio;
	private Timestamp dtFechaAlta;
	private Timestamp dtFechaModifica;
	private Boolean nuActivo;
	private Set tbComponentesMateriaPrimas = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbComponentes() {
	}

	/** minimal constructor */
	public TbComponentes(Integer nukIdComponente) {
		this.nukIdComponente = nukIdComponente;
	}

	/** full constructor */
	public TbComponentes(Integer nukIdComponente, TbEmpresa tbEmpresa,
			String chSku, String chDescripcion, Double nuCostoPromedio,
			Timestamp dtFechaAlta, Timestamp dtFechaModifica, Boolean nuActivo,
			Set tbComponentesMateriaPrimas) {
		this.nukIdComponente = nukIdComponente;
		this.tbEmpresa = tbEmpresa;
		this.chSku = chSku;
		this.chDescripcion = chDescripcion;
		this.nuCostoPromedio = nuCostoPromedio;
		this.dtFechaAlta = dtFechaAlta;
		this.dtFechaModifica = dtFechaModifica;
		this.nuActivo = nuActivo;
		this.tbComponentesMateriaPrimas = tbComponentesMateriaPrimas;
	}

	// Property accessors

	public Integer getNukIdComponente() {
		return this.nukIdComponente;
	}

	public void setNukIdComponente(Integer nukIdComponente) {
		this.nukIdComponente = nukIdComponente;
	}

	public TbEmpresa getTbEmpresa() {
		return this.tbEmpresa;
	}

	public void setTbEmpresa(TbEmpresa tbEmpresa) {
		this.tbEmpresa = tbEmpresa;
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

	public Set getTbComponentesMateriaPrimas() {
		return this.tbComponentesMateriaPrimas;
	}

	public void setTbComponentesMateriaPrimas(Set tbComponentesMateriaPrimas) {
		this.tbComponentesMateriaPrimas = tbComponentesMateriaPrimas;
	}

}