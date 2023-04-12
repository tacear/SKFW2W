package mx.com.hunkabann.skf.mapeo;

/**
 * TbPlacaOmId entity. @author MyEclipse Persistence Tools
 */

public class TbPlacaOmId implements java.io.Serializable {

	// Fields

	private Integer nukIdPlacaOm;
	private Integer nukIdPlaca;
	private Integer nukIdOrdenManufactura;

	// Constructors

	/** default constructor */
	public TbPlacaOmId() {
	}

	/** full constructor */
	public TbPlacaOmId(Integer nukIdPlacaOm, Integer nukIdPlaca,
			Integer nukIdOrdenManufactura) {
		this.nukIdPlacaOm = nukIdPlacaOm;
		this.nukIdPlaca = nukIdPlaca;
		this.nukIdOrdenManufactura = nukIdOrdenManufactura;
	}

	// Property accessors

	public Integer getNukIdPlacaOm() {
		return this.nukIdPlacaOm;
	}

	public void setNukIdPlacaOm(Integer nukIdPlacaOm) {
		this.nukIdPlacaOm = nukIdPlacaOm;
	}

	public Integer getNukIdPlaca() {
		return this.nukIdPlaca;
	}

	public void setNukIdPlaca(Integer nukIdPlaca) {
		this.nukIdPlaca = nukIdPlaca;
	}

	public Integer getNukIdOrdenManufactura() {
		return this.nukIdOrdenManufactura;
	}

	public void setNukIdOrdenManufactura(Integer nukIdOrdenManufactura) {
		this.nukIdOrdenManufactura = nukIdOrdenManufactura;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbPlacaOmId))
			return false;
		TbPlacaOmId castOther = (TbPlacaOmId) other;

		return ((this.getNukIdPlacaOm() == castOther.getNukIdPlacaOm()) || (this
				.getNukIdPlacaOm() != null
				&& castOther.getNukIdPlacaOm() != null && this
				.getNukIdPlacaOm().equals(castOther.getNukIdPlacaOm())))
				&& ((this.getNukIdPlaca() == castOther.getNukIdPlaca()) || (this
						.getNukIdPlaca() != null
						&& castOther.getNukIdPlaca() != null && this
						.getNukIdPlaca().equals(castOther.getNukIdPlaca())))
				&& ((this.getNukIdOrdenManufactura() == castOther
						.getNukIdOrdenManufactura()) || (this
						.getNukIdOrdenManufactura() != null
						&& castOther.getNukIdOrdenManufactura() != null && this
						.getNukIdOrdenManufactura().equals(
								castOther.getNukIdOrdenManufactura())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getNukIdPlacaOm() == null ? 0 : this.getNukIdPlacaOm()
						.hashCode());
		result = 37
				* result
				+ (getNukIdPlaca() == null ? 0 : this.getNukIdPlaca()
						.hashCode());
		result = 37
				* result
				+ (getNukIdOrdenManufactura() == null ? 0 : this
						.getNukIdOrdenManufactura().hashCode());
		return result;
	}

}