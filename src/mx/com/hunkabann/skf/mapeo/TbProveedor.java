package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;

/**
 * TbProveedor entity. @author MyEclipse Persistence Tools
 */

public class TbProveedor implements java.io.Serializable {

	// Fields

	private Integer nukIdProovedor;
	private TbPersona tbPersona;
	private String chClave;
	private String chNombre;
	private Timestamp dtFechaAlta;
	private Integer nuDiaPago;
	private Boolean nuActivo;

	// Constructors

	/** default constructor */
	public TbProveedor() {
	}

	/** minimal constructor */
	public TbProveedor(Integer nukIdProovedor) {
		this.nukIdProovedor = nukIdProovedor;
	}

	/** full constructor */
	public TbProveedor(Integer nukIdProovedor, TbPersona tbPersona,
			String chClave, String chNombre, Timestamp dtFechaAlta,
			Integer nuDiaPago, Boolean nuActivo) {
		this.nukIdProovedor = nukIdProovedor;
		this.tbPersona = tbPersona;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.dtFechaAlta = dtFechaAlta;
		this.nuDiaPago = nuDiaPago;
		this.nuActivo = nuActivo;
	}

	// Property accessors

	public Integer getNukIdProovedor() {
		return this.nukIdProovedor;
	}

	public void setNukIdProovedor(Integer nukIdProovedor) {
		this.nukIdProovedor = nukIdProovedor;
	}

	public TbPersona getTbPersona() {
		return this.tbPersona;
	}

	public void setTbPersona(TbPersona tbPersona) {
		this.tbPersona = tbPersona;
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

	public Timestamp getDtFechaAlta() {
		return this.dtFechaAlta;
	}

	public void setDtFechaAlta(Timestamp dtFechaAlta) {
		this.dtFechaAlta = dtFechaAlta;
	}

	public Integer getNuDiaPago() {
		return this.nuDiaPago;
	}

	public void setNuDiaPago(Integer nuDiaPago) {
		this.nuDiaPago = nuDiaPago;
	}

	public Boolean getNuActivo() {
		return this.nuActivo;
	}

	public void setNuActivo(Boolean nuActivo) {
		this.nuActivo = nuActivo;
	}

}