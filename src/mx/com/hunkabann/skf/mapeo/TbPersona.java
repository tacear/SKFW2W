package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * TbPersona entity. @author MyEclipse Persistence Tools
 */

public class TbPersona implements java.io.Serializable {

	// Fields

	private Integer nukIdPersona;
	private TbTipoPersona tbTipoPersona;
	private String chNombre;
	private String chApPaterno;
	private String chApMaterno;
	private String chRazonSocial;
	private String chEmail;
	private String chTelefono;
	private String chDireccion;
	private String chRfc;
	private String chRegimenFiscal;
	private String chCp;
	private Timestamp dtFechaAlta;
	private Boolean nuActivo;
	private Set tbProveedors = new HashSet(0);
	private Set tbClientes = new HashSet(0);
	private Set tbUsuarios = new HashSet(0);
	private Set tbEmpleados = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbPersona() {
	}

	/** minimal constructor */
	public TbPersona(Integer nukIdPersona) {
		this.nukIdPersona = nukIdPersona;
	}

	/** full constructor */
	public TbPersona(Integer nukIdPersona, TbTipoPersona tbTipoPersona,
			String chNombre, String chApPaterno, String chApMaterno,
			String chRazonSocial, String chEmail, String chTelefono,
			String chDireccion, String chRfc, String chRegimenFiscal,
			String chCp, Timestamp dtFechaAlta, Boolean nuActivo,
			Set tbProveedors, Set tbClientes, Set tbUsuarios, Set tbEmpleados) {
		this.nukIdPersona = nukIdPersona;
		this.tbTipoPersona = tbTipoPersona;
		this.chNombre = chNombre;
		this.chApPaterno = chApPaterno;
		this.chApMaterno = chApMaterno;
		this.chRazonSocial = chRazonSocial;
		this.chEmail = chEmail;
		this.chTelefono = chTelefono;
		this.chDireccion = chDireccion;
		this.chRfc = chRfc;
		this.chRegimenFiscal = chRegimenFiscal;
		this.chCp = chCp;
		this.dtFechaAlta = dtFechaAlta;
		this.nuActivo = nuActivo;
		this.tbProveedors = tbProveedors;
		this.tbClientes = tbClientes;
		this.tbUsuarios = tbUsuarios;
		this.tbEmpleados = tbEmpleados;
	}

	// Property accessors

	public Integer getNukIdPersona() {
		return this.nukIdPersona;
	}

	public void setNukIdPersona(Integer nukIdPersona) {
		this.nukIdPersona = nukIdPersona;
	}

	public TbTipoPersona getTbTipoPersona() {
		return this.tbTipoPersona;
	}

	public void setTbTipoPersona(TbTipoPersona tbTipoPersona) {
		this.tbTipoPersona = tbTipoPersona;
	}

	public String getChNombre() {
		return this.chNombre;
	}

	public void setChNombre(String chNombre) {
		this.chNombre = chNombre;
	}

	public String getChApPaterno() {
		return this.chApPaterno;
	}

	public void setChApPaterno(String chApPaterno) {
		this.chApPaterno = chApPaterno;
	}

	public String getChApMaterno() {
		return this.chApMaterno;
	}

	public void setChApMaterno(String chApMaterno) {
		this.chApMaterno = chApMaterno;
	}

	public String getChRazonSocial() {
		return this.chRazonSocial;
	}

	public void setChRazonSocial(String chRazonSocial) {
		this.chRazonSocial = chRazonSocial;
	}

	public String getChEmail() {
		return this.chEmail;
	}

	public void setChEmail(String chEmail) {
		this.chEmail = chEmail;
	}

	public String getChTelefono() {
		return this.chTelefono;
	}

	public void setChTelefono(String chTelefono) {
		this.chTelefono = chTelefono;
	}

	public String getChDireccion() {
		return this.chDireccion;
	}

	public void setChDireccion(String chDireccion) {
		this.chDireccion = chDireccion;
	}

	public String getChRfc() {
		return this.chRfc;
	}

	public void setChRfc(String chRfc) {
		this.chRfc = chRfc;
	}

	public String getChRegimenFiscal() {
		return this.chRegimenFiscal;
	}

	public void setChRegimenFiscal(String chRegimenFiscal) {
		this.chRegimenFiscal = chRegimenFiscal;
	}

	public String getChCp() {
		return this.chCp;
	}

	public void setChCp(String chCp) {
		this.chCp = chCp;
	}

	public Timestamp getDtFechaAlta() {
		return this.dtFechaAlta;
	}

	public void setDtFechaAlta(Timestamp dtFechaAlta) {
		this.dtFechaAlta = dtFechaAlta;
	}

	public Boolean getNuActivo() {
		return this.nuActivo;
	}

	public void setNuActivo(Boolean nuActivo) {
		this.nuActivo = nuActivo;
	}

	public Set getTbProveedors() {
		return this.tbProveedors;
	}

	public void setTbProveedors(Set tbProveedors) {
		this.tbProveedors = tbProveedors;
	}

	public Set getTbClientes() {
		return this.tbClientes;
	}

	public void setTbClientes(Set tbClientes) {
		this.tbClientes = tbClientes;
	}

	public Set getTbUsuarios() {
		return this.tbUsuarios;
	}

	public void setTbUsuarios(Set tbUsuarios) {
		this.tbUsuarios = tbUsuarios;
	}

	public Set getTbEmpleados() {
		return this.tbEmpleados;
	}

	public void setTbEmpleados(Set tbEmpleados) {
		this.tbEmpleados = tbEmpleados;
	}

}