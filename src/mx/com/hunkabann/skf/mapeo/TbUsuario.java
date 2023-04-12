package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * TbUsuario entity. @author MyEclipse Persistence Tools
 */

public class TbUsuario implements java.io.Serializable {

	// Fields

	private Integer nukIdUsuario;
	private TbPersona tbPersona;
	private TbPerfil tbPerfil;
	private String chUsuario;
	private String chPassword;
	private Timestamp dtFechaAlta;
	private Timestamp dtFechaExpira;
	private Boolean nuSession;
	private Boolean nuActivo;
	private Integer nuIntentos;
	private Set tbEntregaRecepcionsForNukIdUsuarioRecibe = new HashSet(0);
	private Set tbOperacionOrdenManufacturas = new HashSet(0);
	private Set tbOrdenManufacturas = new HashSet(0);
	private Set tbEntregaRecepcionsForNukIdUsuarioEntrega = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbUsuario() {
	}

	/** minimal constructor */
	public TbUsuario(Integer nukIdUsuario) {
		this.nukIdUsuario = nukIdUsuario;
	}

	/** full constructor */
	public TbUsuario(Integer nukIdUsuario, TbPersona tbPersona,
			TbPerfil tbPerfil, String chUsuario, String chPassword,
			Timestamp dtFechaAlta, Timestamp dtFechaExpira, Boolean nuSession,
			Boolean nuActivo, Integer nuIntentos,
			Set tbEntregaRecepcionsForNukIdUsuarioRecibe,
			Set tbOperacionOrdenManufacturas, Set tbOrdenManufacturas,
			Set tbEntregaRecepcionsForNukIdUsuarioEntrega) {
		this.nukIdUsuario = nukIdUsuario;
		this.tbPersona = tbPersona;
		this.tbPerfil = tbPerfil;
		this.chUsuario = chUsuario;
		this.chPassword = chPassword;
		this.dtFechaAlta = dtFechaAlta;
		this.dtFechaExpira = dtFechaExpira;
		this.nuSession = nuSession;
		this.nuActivo = nuActivo;
		this.nuIntentos = nuIntentos;
		this.tbEntregaRecepcionsForNukIdUsuarioRecibe = tbEntregaRecepcionsForNukIdUsuarioRecibe;
		this.tbOperacionOrdenManufacturas = tbOperacionOrdenManufacturas;
		this.tbOrdenManufacturas = tbOrdenManufacturas;
		this.tbEntregaRecepcionsForNukIdUsuarioEntrega = tbEntregaRecepcionsForNukIdUsuarioEntrega;
	}

	// Property accessors

	public Integer getNukIdUsuario() {
		return this.nukIdUsuario;
	}

	public void setNukIdUsuario(Integer nukIdUsuario) {
		this.nukIdUsuario = nukIdUsuario;
	}

	public TbPersona getTbPersona() {
		return this.tbPersona;
	}

	public void setTbPersona(TbPersona tbPersona) {
		this.tbPersona = tbPersona;
	}

	public TbPerfil getTbPerfil() {
		return this.tbPerfil;
	}

	public void setTbPerfil(TbPerfil tbPerfil) {
		this.tbPerfil = tbPerfil;
	}

	public String getChUsuario() {
		return this.chUsuario;
	}

	public void setChUsuario(String chUsuario) {
		this.chUsuario = chUsuario;
	}

	public String getChPassword() {
		return this.chPassword;
	}

	public void setChPassword(String chPassword) {
		this.chPassword = chPassword;
	}

	public Timestamp getDtFechaAlta() {
		return this.dtFechaAlta;
	}

	public void setDtFechaAlta(Timestamp dtFechaAlta) {
		this.dtFechaAlta = dtFechaAlta;
	}

	public Timestamp getDtFechaExpira() {
		return this.dtFechaExpira;
	}

	public void setDtFechaExpira(Timestamp dtFechaExpira) {
		this.dtFechaExpira = dtFechaExpira;
	}

	public Boolean getNuSession() {
		return this.nuSession;
	}

	public void setNuSession(Boolean nuSession) {
		this.nuSession = nuSession;
	}

	public Boolean getNuActivo() {
		return this.nuActivo;
	}

	public void setNuActivo(Boolean nuActivo) {
		this.nuActivo = nuActivo;
	}

	public Integer getNuIntentos() {
		return this.nuIntentos;
	}

	public void setNuIntentos(Integer nuIntentos) {
		this.nuIntentos = nuIntentos;
	}

	public Set getTbEntregaRecepcionsForNukIdUsuarioRecibe() {
		return this.tbEntregaRecepcionsForNukIdUsuarioRecibe;
	}

	public void setTbEntregaRecepcionsForNukIdUsuarioRecibe(
			Set tbEntregaRecepcionsForNukIdUsuarioRecibe) {
		this.tbEntregaRecepcionsForNukIdUsuarioRecibe = tbEntregaRecepcionsForNukIdUsuarioRecibe;
	}

	public Set getTbOperacionOrdenManufacturas() {
		return this.tbOperacionOrdenManufacturas;
	}

	public void setTbOperacionOrdenManufacturas(Set tbOperacionOrdenManufacturas) {
		this.tbOperacionOrdenManufacturas = tbOperacionOrdenManufacturas;
	}

	public Set getTbOrdenManufacturas() {
		return this.tbOrdenManufacturas;
	}

	public void setTbOrdenManufacturas(Set tbOrdenManufacturas) {
		this.tbOrdenManufacturas = tbOrdenManufacturas;
	}

	public Set getTbEntregaRecepcionsForNukIdUsuarioEntrega() {
		return this.tbEntregaRecepcionsForNukIdUsuarioEntrega;
	}

	public void setTbEntregaRecepcionsForNukIdUsuarioEntrega(
			Set tbEntregaRecepcionsForNukIdUsuarioEntrega) {
		this.tbEntregaRecepcionsForNukIdUsuarioEntrega = tbEntregaRecepcionsForNukIdUsuarioEntrega;
	}

}