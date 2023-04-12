package mx.com.hunkabann.skf.util;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class SessionBean  implements Serializable{
	private static final long serialVersionUID = 7243523491301843413L;
	Logger logger = Logger.getLogger(this.getClass());
	
	private Integer idUsuario;
	private String usuario;
	private Integer idCorporativo;
	private String imgCorporativo;
	private Integer idTienda;
	private Double ivaTienda;
	private String titulo;
	private String fondoTitulo;
	private String colorTitulo;
	private Integer idCentroDistribucion;
	private boolean firmado = false;
	private Integer tipoDoc=0;
	
	
	public SessionBean(){
		getLimpia();
	}
	
	public String getLimpia(){
		logger.info("limpia");
		idUsuario = null;
		usuario = "";
		idCorporativo = null;
		imgCorporativo = "";
		idTienda = null;
		ivaTienda = null;
		titulo = "";
		fondoTitulo = "";
		colorTitulo = "";
		idCentroDistribucion = null;
		firmado = false;
		return "";
	}
	
	public boolean isFirmado() {
		logger.info("firmado: " + firmado);
		return firmado;
	}
	public void setFirmado(boolean firmado) {
		this.firmado = firmado;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getIdCorporativo() {
		return idCorporativo;
	}

	public void setIdCorporativo(Integer idCorporativo) {
		this.idCorporativo = idCorporativo;
	}

	public String getImgCorporativo() {
		return imgCorporativo;
	}

	public void setImgCorporativo(String imgCorporativo) {
		this.imgCorporativo = imgCorporativo;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdTienda() {
		return idTienda;
	}

	public void setIdTienda(Integer idTienda) {
		this.idTienda = idTienda;
	}

	public Double getIvaTienda() {
		return ivaTienda;
	}

	public void setIvaTienda(Double ivaTienda) {
		this.ivaTienda = ivaTienda;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFondoTitulo() {
		return fondoTitulo;
	}

	public void setFondoTitulo(String fondoTitulo) {
		this.fondoTitulo = fondoTitulo;
	}

	public String getColorTitulo() {
		return colorTitulo;
	}

	public void setColorTitulo(String colorTitulo) {
		this.colorTitulo = colorTitulo;
	}

	public Integer getIdCentroDistribucion() {
		return idCentroDistribucion;
	}

	public void setIdCentroDistribucion(Integer idCentroDistribucion) {
		this.idCentroDistribucion = idCentroDistribucion;
	}

	/**
	 * @return the tipoDoc
	 */
	public Integer getTipoDoc() {
		return tipoDoc;
	}

	/**
	 * @param tipoDoc the tipoDoc to set
	 */
	public void setTipoDoc(Integer tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	

}
