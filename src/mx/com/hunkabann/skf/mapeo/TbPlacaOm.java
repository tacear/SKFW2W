package mx.com.hunkabann.skf.mapeo;

/**
 * TbPlacaOm entity. @author MyEclipse Persistence Tools
 */

public class TbPlacaOm implements java.io.Serializable {

	// Fields

	private TbPlacaOmId id;
	private TbPlaca tbPlaca;
	private TbOrdenManufactura tbOrdenManufactura;

	// Constructors

	/** default constructor */
	public TbPlacaOm() {
	}

	/** full constructor */
	public TbPlacaOm(TbPlacaOmId id, TbPlaca tbPlaca,
			TbOrdenManufactura tbOrdenManufactura) {
		this.id = id;
		this.tbPlaca = tbPlaca;
		this.tbOrdenManufactura = tbOrdenManufactura;
	}

	// Property accessors

	public TbPlacaOmId getId() {
		return this.id;
	}

	public void setId(TbPlacaOmId id) {
		this.id = id;
	}

	public TbPlaca getTbPlaca() {
		return this.tbPlaca;
	}

	public void setTbPlaca(TbPlaca tbPlaca) {
		this.tbPlaca = tbPlaca;
	}

	public TbOrdenManufactura getTbOrdenManufactura() {
		return this.tbOrdenManufactura;
	}

	public void setTbOrdenManufactura(TbOrdenManufactura tbOrdenManufactura) {
		this.tbOrdenManufactura = tbOrdenManufactura;
	}

}