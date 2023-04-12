package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;

/**
 * TbComponentesMateriaPrima entity. @author MyEclipse Persistence Tools
 */

public class TbComponentesMateriaPrima implements java.io.Serializable {

	// Fields

	private Integer nukIdCompMateriaPrima;
	private TbMateriaPrima tbMateriaPrima;
	private TbComponentes tbComponentes;
	private Integer nuCantidad;
	private String chObservaciones;
	private Timestamp dtFechaAlta;
	private Timestamp dtFechaModifica;
	private Boolean nuActivo;

	// Constructors

	/** default constructor */
	public TbComponentesMateriaPrima() {
	}

	/** minimal constructor */
	public TbComponentesMateriaPrima(Integer nukIdCompMateriaPrima) {
		this.nukIdCompMateriaPrima = nukIdCompMateriaPrima;
	}

	/** full constructor */
	public TbComponentesMateriaPrima(Integer nukIdCompMateriaPrima,
			TbMateriaPrima tbMateriaPrima, TbComponentes tbComponentes,
			Integer nuCantidad, String chObservaciones, Timestamp dtFechaAlta,
			Timestamp dtFechaModifica, Boolean nuActivo) {
		this.nukIdCompMateriaPrima = nukIdCompMateriaPrima;
		this.tbMateriaPrima = tbMateriaPrima;
		this.tbComponentes = tbComponentes;
		this.nuCantidad = nuCantidad;
		this.chObservaciones = chObservaciones;
		this.dtFechaAlta = dtFechaAlta;
		this.dtFechaModifica = dtFechaModifica;
		this.nuActivo = nuActivo;
	}

	// Property accessors

	public Integer getNukIdCompMateriaPrima() {
		return this.nukIdCompMateriaPrima;
	}

	public void setNukIdCompMateriaPrima(Integer nukIdCompMateriaPrima) {
		this.nukIdCompMateriaPrima = nukIdCompMateriaPrima;
	}

	public TbMateriaPrima getTbMateriaPrima() {
		return this.tbMateriaPrima;
	}

	public void setTbMateriaPrima(TbMateriaPrima tbMateriaPrima) {
		this.tbMateriaPrima = tbMateriaPrima;
	}

	public TbComponentes getTbComponentes() {
		return this.tbComponentes;
	}

	public void setTbComponentes(TbComponentes tbComponentes) {
		this.tbComponentes = tbComponentes;
	}

	public Integer getNuCantidad() {
		return this.nuCantidad;
	}

	public void setNuCantidad(Integer nuCantidad) {
		this.nuCantidad = nuCantidad;
	}

	public String getChObservaciones() {
		return this.chObservaciones;
	}

	public void setChObservaciones(String chObservaciones) {
		this.chObservaciones = chObservaciones;
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

}