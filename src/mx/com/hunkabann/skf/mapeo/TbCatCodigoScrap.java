package mx.com.hunkabann.skf.mapeo;

/**
 * TbCatCodigoScrap entity. @author MyEclipse Persistence Tools
 */

public class TbCatCodigoScrap implements java.io.Serializable {

	// Fields

	private Integer nukIdCatCodScrap;
	private TbEmpresa tbEmpresa;
	private String chCodigo;
	private String chNombre;
	private String chDescripcion;
	private Boolean nuActivo;

	// Constructors

	/** default constructor */
	public TbCatCodigoScrap() {
	}

	/** minimal constructor */
	public TbCatCodigoScrap(Integer nukIdCatCodScrap) {
		this.nukIdCatCodScrap = nukIdCatCodScrap;
	}

	/** full constructor */
	public TbCatCodigoScrap(Integer nukIdCatCodScrap, TbEmpresa tbEmpresa,
			String chCodigo, String chNombre, String chDescripcion,
			Boolean nuActivo) {
		this.nukIdCatCodScrap = nukIdCatCodScrap;
		this.tbEmpresa = tbEmpresa;
		this.chCodigo = chCodigo;
		this.chNombre = chNombre;
		this.chDescripcion = chDescripcion;
		this.nuActivo = nuActivo;
	}

	// Property accessors

	public Integer getNukIdCatCodScrap() {
		return this.nukIdCatCodScrap;
	}

	public void setNukIdCatCodScrap(Integer nukIdCatCodScrap) {
		this.nukIdCatCodScrap = nukIdCatCodScrap;
	}

	public TbEmpresa getTbEmpresa() {
		return this.tbEmpresa;
	}

	public void setTbEmpresa(TbEmpresa tbEmpresa) {
		this.tbEmpresa = tbEmpresa;
	}

	public String getChCodigo() {
		return this.chCodigo;
	}

	public void setChCodigo(String chCodigo) {
		this.chCodigo = chCodigo;
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

}