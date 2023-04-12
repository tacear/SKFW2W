package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * TbPlaca entity. @author MyEclipse Persistence Tools
 */

public class TbPlaca  implements java.io.Serializable {


    // Fields    

     private Integer nukIdPlaca;
     private TbTipoPlaca tbTipoPlaca;
     private TbEmpresa tbEmpresa;
     private TbSubArea tbSubArea;
     private String chPlaca;
     private String chDescripcion;
     private String chCanal;
     private String chStatus;
     private Integer nuCantidadPlacas;
     private Double nuTamano;
     private Integer nuCavidades;
     private Double nuPesoKg;
     private String chComentarios;
     private Timestamp dtFechaAlta;
     private Timestamp dtFechaModifica;
     private Boolean nuActvo;
     private Set tbPlacaProdTerms = new HashSet(0);
     private Set tbPlacaOms = new HashSet(0);
     private Set tbPlacaUbicacionPs = new HashSet(0);


    // Constructors

    /** default constructor */
    public TbPlaca() {
    }

	/** minimal constructor */
    public TbPlaca(Integer nukIdPlaca) {
        this.nukIdPlaca = nukIdPlaca;
    }
    
    /** full constructor */
    public TbPlaca(Integer nukIdPlaca, TbTipoPlaca tbTipoPlaca, TbEmpresa tbEmpresa, TbSubArea tbSubArea, String chPlaca, String chDescripcion, String chCanal, String chStatus, Integer nuCantidadPlacas, Double nuTamano, Integer nuCavidades, Double nuPesoKg, String chComentarios, Timestamp dtFechaAlta, Timestamp dtFechaModifica, Boolean nuActvo, Set tbPlacaProdTerms, Set tbPlacaOms, Set tbPlacaUbicacionPs) {
        this.nukIdPlaca = nukIdPlaca;
        this.tbTipoPlaca = tbTipoPlaca;
        this.tbEmpresa = tbEmpresa;
        this.tbSubArea = tbSubArea;
        this.chPlaca = chPlaca;
        this.chDescripcion = chDescripcion;
        this.chCanal = chCanal;
        this.chStatus = chStatus;
        this.nuCantidadPlacas = nuCantidadPlacas;
        this.nuTamano = nuTamano;
        this.nuCavidades = nuCavidades;
        this.nuPesoKg = nuPesoKg;
        this.chComentarios = chComentarios;
        this.dtFechaAlta = dtFechaAlta;
        this.dtFechaModifica = dtFechaModifica;
        this.nuActvo = nuActvo;
        this.tbPlacaProdTerms = tbPlacaProdTerms;
        this.tbPlacaOms = tbPlacaOms;
        this.tbPlacaUbicacionPs = tbPlacaUbicacionPs;
    }

   
    // Property accessors

    public Integer getNukIdPlaca() {
        return this.nukIdPlaca;
    }
    
    public void setNukIdPlaca(Integer nukIdPlaca) {
        this.nukIdPlaca = nukIdPlaca;
    }

    public TbTipoPlaca getTbTipoPlaca() {
        return this.tbTipoPlaca;
    }
    
    public void setTbTipoPlaca(TbTipoPlaca tbTipoPlaca) {
        this.tbTipoPlaca = tbTipoPlaca;
    }

    public TbEmpresa getTbEmpresa() {
        return this.tbEmpresa;
    }
    
    public void setTbEmpresa(TbEmpresa tbEmpresa) {
        this.tbEmpresa = tbEmpresa;
    }

    public TbSubArea getTbSubArea() {
        return this.tbSubArea;
    }
    
    public void setTbSubArea(TbSubArea tbSubArea) {
        this.tbSubArea = tbSubArea;
    }

    public String getChPlaca() {
        return this.chPlaca;
    }
    
    public void setChPlaca(String chPlaca) {
        this.chPlaca = chPlaca;
    }

    public String getChDescripcion() {
        return this.chDescripcion;
    }
    
    public void setChDescripcion(String chDescripcion) {
        this.chDescripcion = chDescripcion;
    }

    public String getChCanal() {
        return this.chCanal;
    }
    
    public void setChCanal(String chCanal) {
        this.chCanal = chCanal;
    }

    public String getChStatus() {
        return this.chStatus;
    }
    
    public void setChStatus(String chStatus) {
        this.chStatus = chStatus;
    }

    public Integer getNuCantidadPlacas() {
        return this.nuCantidadPlacas;
    }
    
    public void setNuCantidadPlacas(Integer nuCantidadPlacas) {
        this.nuCantidadPlacas = nuCantidadPlacas;
    }

    public Double getNuTamano() {
        return this.nuTamano;
    }
    
    public void setNuTamano(Double nuTamano) {
        this.nuTamano = nuTamano;
    }

    public Integer getNuCavidades() {
        return this.nuCavidades;
    }
    
    public void setNuCavidades(Integer nuCavidades) {
        this.nuCavidades = nuCavidades;
    }

    public Double getNuPesoKg() {
        return this.nuPesoKg;
    }
    
    public void setNuPesoKg(Double nuPesoKg) {
        this.nuPesoKg = nuPesoKg;
    }

    public String getChComentarios() {
        return this.chComentarios;
    }
    
    public void setChComentarios(String chComentarios) {
        this.chComentarios = chComentarios;
    }

    public Timestamp getDtFechaAlta() {
        return this.dtFechaAlta;
    }
    
    public void setDtFechaAlta(Timestamp dtFechaAlta) {
        this.dtFechaAlta = dtFechaAlta;
    }

    public Timestamp getDtFechaModifica() {
        return this.dtFechaModifica;
    }
    
    public void setDtFechaModifica(Timestamp dtFechaModifica) {
        this.dtFechaModifica = dtFechaModifica;
    }

    public Boolean getNuActvo() {
        return this.nuActvo;
    }
    
    public void setNuActvo(Boolean nuActvo) {
        this.nuActvo = nuActvo;
    }

    public Set getTbPlacaProdTerms() {
        return this.tbPlacaProdTerms;
    }
    
    public void setTbPlacaProdTerms(Set tbPlacaProdTerms) {
        this.tbPlacaProdTerms = tbPlacaProdTerms;
    }

    public Set getTbPlacaOms() {
        return this.tbPlacaOms;
    }
    
    public void setTbPlacaOms(Set tbPlacaOms) {
        this.tbPlacaOms = tbPlacaOms;
    }

    public Set getTbPlacaUbicacionPs() {
        return this.tbPlacaUbicacionPs;
    }
    
    public void setTbPlacaUbicacionPs(Set tbPlacaUbicacionPs) {
        this.tbPlacaUbicacionPs = tbPlacaUbicacionPs;
    }
   








}