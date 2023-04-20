package mx.com.hunkabann.skf.mapeo;



/**
 * TbCatCodigoDownTime entity. @author MyEclipse Persistence Tools
 */

public class TbUbicacionOM  implements java.io.Serializable {


    // Fields    
	private Integer idUbicaOM;
     private Integer idMaquinaDisp;
     private String chNombreMAquina;
     private Integer idcanal;
     private String chNombrecanal;
     private Integer idsubArea;
     private String chNombreSubArea;
     


    // Constructors

    /** default constructor */
    public TbUbicacionOM() {
    }

	/** minimal constructor */
    public TbUbicacionOM(Integer idUbicaOM) {
        this.idUbicaOM = idUbicaOM;
    }
    
    /** full constructor */
    public TbUbicacionOM(Integer idUbicaOM,Integer idMaquinaDisp, String chNombreMAquina,Integer idcanal, String chNombrecanal,
    		Integer idsubArea, String chNombreSubArea) {
        this.idUbicaOM = idUbicaOM;
        this.idMaquinaDisp = idMaquinaDisp;
        this.chNombreMAquina = chNombreMAquina;
        this.idcanal = idcanal;
        this.chNombrecanal = chNombrecanal;
        this.idsubArea = idsubArea;
        this.chNombreSubArea = chNombreSubArea;
    }

	public Integer getIdUbicaOM() {
		return idUbicaOM;
	}

	public void setIdUbicaOM(Integer idUbicaOM) {
		this.idUbicaOM = idUbicaOM;
	}

	public Integer getIdMaquinaDisp() {
		return idMaquinaDisp;
	}

	public void setIdMaquinaDisp(Integer idMaquinaDisp) {
		this.idMaquinaDisp = idMaquinaDisp;
	}

	public String getChNombreMAquina() {
		return chNombreMAquina;
	}

	public void setChNombreMAquina(String chNombreMAquina) {
		this.chNombreMAquina = chNombreMAquina;
	}

	public Integer getIdcanal() {
		return idcanal;
	}

	public void setIdcanal(Integer idcanal) {
		this.idcanal = idcanal;
	}

	public String getChNombrecanal() {
		return chNombrecanal;
	}

	public void setChNombrecanal(String chNombrecanal) {
		this.chNombrecanal = chNombrecanal;
	}

	public Integer getIdsubArea() {
		return idsubArea;
	}

	public void setIdsubArea(Integer idsubArea) {
		this.idsubArea = idsubArea;
	}

	public String getChNombreSubArea() {
		return chNombreSubArea;
	}

	public void setChNombreSubArea(String chNombreSubArea) {
		this.chNombreSubArea = chNombreSubArea;
	}

   
    // Property accessors

    



}