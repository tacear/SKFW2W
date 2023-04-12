package mx.com.hunkabann.skf.mapeo;

/**
 * TbPlacaProdTermId entity. @author MyEclipse Persistence Tools
 */

public class TbPlacaProdTermId implements java.io.Serializable {

	// Fields

	private Integer nukIdPlacaProdTerm;
	private Integer nukIdProdTerm;

	// Constructors

	/** default constructor */
	public TbPlacaProdTermId() {
	}

	/** full constructor */
	public TbPlacaProdTermId(Integer nukIdPlacaProdTerm, Integer nukIdProdTerm) {
		this.nukIdPlacaProdTerm = nukIdPlacaProdTerm;
		this.nukIdProdTerm = nukIdProdTerm;
	}

	// Property accessors

	public Integer getNukIdPlacaProdTerm() {
		return this.nukIdPlacaProdTerm;
	}

	public void setNukIdPlacaProdTerm(Integer nukIdPlacaProdTerm) {
		this.nukIdPlacaProdTerm = nukIdPlacaProdTerm;
	}

	public Integer getNukIdProdTerm() {
		return this.nukIdProdTerm;
	}

	public void setNukIdProdTerm(Integer nukIdProdTerm) {
		this.nukIdProdTerm = nukIdProdTerm;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbPlacaProdTermId))
			return false;
		TbPlacaProdTermId castOther = (TbPlacaProdTermId) other;

		return ((this.getNukIdPlacaProdTerm() == castOther
				.getNukIdPlacaProdTerm()) || (this.getNukIdPlacaProdTerm() != null
				&& castOther.getNukIdPlacaProdTerm() != null && this
				.getNukIdPlacaProdTerm().equals(
						castOther.getNukIdPlacaProdTerm())))
				&& ((this.getNukIdProdTerm() == castOther.getNukIdProdTerm()) || (this
						.getNukIdProdTerm() != null
						&& castOther.getNukIdProdTerm() != null && this
						.getNukIdProdTerm()
						.equals(castOther.getNukIdProdTerm())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getNukIdPlacaProdTerm() == null ? 0 : this
						.getNukIdPlacaProdTerm().hashCode());
		result = 37
				* result
				+ (getNukIdProdTerm() == null ? 0 : this.getNukIdProdTerm()
						.hashCode());
		return result;
	}

}