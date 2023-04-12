package mx.com.hunkabann.skf.mapeo;

/**
 * TbPlacaUbicacionPId entity. @author MyEclipse Persistence Tools
 */

public class TbPlacaUbicacionPId implements java.io.Serializable {

	// Fields

	private Integer nukIdPlacaUbicaP;
	private Integer nukIdPlaca;
	private Integer nukIdUbicaPlaca;

	// Constructors

	/** default constructor */
	public TbPlacaUbicacionPId() {
	}

	/** full constructor */
	public TbPlacaUbicacionPId(Integer nukIdPlacaUbicaP, Integer nukIdPlaca,
			Integer nukIdUbicaPlaca) {
		this.nukIdPlacaUbicaP = nukIdPlacaUbicaP;
		this.nukIdPlaca = nukIdPlaca;
		this.nukIdUbicaPlaca = nukIdUbicaPlaca;
	}

	// Property accessors

	public Integer getNukIdPlacaUbicaP() {
		return this.nukIdPlacaUbicaP;
	}

	public void setNukIdPlacaUbicaP(Integer nukIdPlacaUbicaP) {
		this.nukIdPlacaUbicaP = nukIdPlacaUbicaP;
	}

	public Integer getNukIdPlaca() {
		return this.nukIdPlaca;
	}

	public void setNukIdPlaca(Integer nukIdPlaca) {
		this.nukIdPlaca = nukIdPlaca;
	}

	public Integer getNukIdUbicaPlaca() {
		return this.nukIdUbicaPlaca;
	}

	public void setNukIdUbicaPlaca(Integer nukIdUbicaPlaca) {
		this.nukIdUbicaPlaca = nukIdUbicaPlaca;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbPlacaUbicacionPId))
			return false;
		TbPlacaUbicacionPId castOther = (TbPlacaUbicacionPId) other;

		return ((this.getNukIdPlacaUbicaP() == castOther.getNukIdPlacaUbicaP()) || (this
				.getNukIdPlacaUbicaP() != null
				&& castOther.getNukIdPlacaUbicaP() != null && this
				.getNukIdPlacaUbicaP().equals(castOther.getNukIdPlacaUbicaP())))
				&& ((this.getNukIdPlaca() == castOther.getNukIdPlaca()) || (this
						.getNukIdPlaca() != null
						&& castOther.getNukIdPlaca() != null && this
						.getNukIdPlaca().equals(castOther.getNukIdPlaca())))
				&& ((this.getNukIdUbicaPlaca() == castOther
						.getNukIdUbicaPlaca()) || (this.getNukIdUbicaPlaca() != null
						&& castOther.getNukIdUbicaPlaca() != null && this
						.getNukIdUbicaPlaca().equals(
								castOther.getNukIdUbicaPlaca())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getNukIdPlacaUbicaP() == null ? 0 : this
						.getNukIdPlacaUbicaP().hashCode());
		result = 37
				* result
				+ (getNukIdPlaca() == null ? 0 : this.getNukIdPlaca()
						.hashCode());
		result = 37
				* result
				+ (getNukIdUbicaPlaca() == null ? 0 : this.getNukIdUbicaPlaca()
						.hashCode());
		return result;
	}

}