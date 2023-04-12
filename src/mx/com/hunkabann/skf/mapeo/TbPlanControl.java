package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * TbPlanControl entity. @author MyEclipse Persistence Tools
 */

public class TbPlanControl implements java.io.Serializable {

	// Fields

	private Integer nukIdPlanControl;
	private TbTipoPlanControl tbTipoPlanControl;
	private String chClave;
	private String chNombre;
	private String chNumParte;
	private String chPlanControl;
	private Timestamp dtFechaAlta;
	private String filePdf;
	private Boolean nuActivo;
	private Set tbProcesos = new HashSet(0);
	private Set tbOrdenManufacturas = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbPlanControl() {
	}

	/** minimal constructor */
	public TbPlanControl(Integer nukIdPlanControl) {
		this.nukIdPlanControl = nukIdPlanControl;
	}

	/** full constructor */
	public TbPlanControl(Integer nukIdPlanControl,
			TbTipoPlanControl tbTipoPlanControl, String chClave,
			String chNombre, String chNumParte, String chPlanControl,
			Timestamp dtFechaAlta, String filePdf, Boolean nuActivo,
			Set tbProcesos, Set tbOrdenManufacturas) {
		this.nukIdPlanControl = nukIdPlanControl;
		this.tbTipoPlanControl = tbTipoPlanControl;
		this.chClave = chClave;
		this.chNombre = chNombre;
		this.chNumParte = chNumParte;
		this.chPlanControl = chPlanControl;
		this.dtFechaAlta = dtFechaAlta;
		this.filePdf = filePdf;
		this.nuActivo = nuActivo;
		this.tbProcesos = tbProcesos;
		this.tbOrdenManufacturas = tbOrdenManufacturas;
	}

	// Property accessors

	public Integer getNukIdPlanControl() {
		return this.nukIdPlanControl;
	}

	public void setNukIdPlanControl(Integer nukIdPlanControl) {
		this.nukIdPlanControl = nukIdPlanControl;
	}

	public TbTipoPlanControl getTbTipoPlanControl() {
		return this.tbTipoPlanControl;
	}

	public void setTbTipoPlanControl(TbTipoPlanControl tbTipoPlanControl) {
		this.tbTipoPlanControl = tbTipoPlanControl;
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

	public String getChNumParte() {
		return this.chNumParte;
	}

	public void setChNumParte(String chNumParte) {
		this.chNumParte = chNumParte;
	}

	public String getChPlanControl() {
		return this.chPlanControl;
	}

	public void setChPlanControl(String chPlanControl) {
		this.chPlanControl = chPlanControl;
	}

	public Timestamp getDtFechaAlta() {
		return this.dtFechaAlta;
	}

	public void setDtFechaAlta(Timestamp dtFechaAlta) {
		this.dtFechaAlta = dtFechaAlta;
	}

	public String getFilePdf() {
		return this.filePdf;
	}

	public void setFilePdf(String filePdf) {
		this.filePdf = filePdf;
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

	public Set getTbOrdenManufacturas() {
		return this.tbOrdenManufacturas;
	}

	public void setTbOrdenManufacturas(Set tbOrdenManufacturas) {
		this.tbOrdenManufacturas = tbOrdenManufacturas;
	}

}