package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;


/**
 * TbOperacionOrdenManufactura entity. @author MyEclipse Persistence Tools
 */

public class TbOperacionOrdenManufactura  implements java.io.Serializable {


    // Fields    

     private Integer nukIdOperacionOm;
     private TbUsuario tbUsuario;
     private TbUbicacionProcesoOm tbUbicacionProcesoOm;
     private TbOrdenManufactura tbOrdenManufactura;
     private TbEmpresa tbEmpresa;
     private Integer nuCantidad;
     private Timestamp dtFecha;
     private Double nuHora;
     private String chCodigoScrap;
     private Integer nuScrap;
     private String chCodigoFtq;
     private Integer nuFtq;
     private String chCodigoDownTime;
     private Integer nuDownTime;
     private String nuStatus;
     private Boolean nuActivo;


    // Constructors

    /** default constructor */
    public TbOperacionOrdenManufactura() {
    }

	/** minimal constructor */
    public TbOperacionOrdenManufactura(Integer nukIdOperacionOm) {
        this.nukIdOperacionOm = nukIdOperacionOm;
    }
    
    /** full constructor */
    public TbOperacionOrdenManufactura(Integer nukIdOperacionOm, TbUsuario tbUsuario, TbUbicacionProcesoOm tbUbicacionProcesoOm, TbOrdenManufactura tbOrdenManufactura, TbEmpresa tbEmpresa, Integer nuCantidad, Timestamp dtFecha, Double nuHora, String chCodigoScrap, Integer nuScrap, String chCodigoFtq, Integer nuFtq, String chCodigoDownTime, Integer nuDownTime, String nuStatus, Boolean nuActivo) {
        this.nukIdOperacionOm = nukIdOperacionOm;
        this.tbUsuario = tbUsuario;
        this.tbUbicacionProcesoOm = tbUbicacionProcesoOm;
        this.tbOrdenManufactura = tbOrdenManufactura;
        this.tbEmpresa = tbEmpresa;
        this.nuCantidad = nuCantidad;
        this.dtFecha = dtFecha;
        this.nuHora = nuHora;
        this.chCodigoScrap = chCodigoScrap;
        this.nuScrap = nuScrap;
        this.chCodigoFtq = chCodigoFtq;
        this.nuFtq = nuFtq;
        this.chCodigoDownTime = chCodigoDownTime;
        this.nuDownTime = nuDownTime;
        this.nuStatus = nuStatus;
        this.nuActivo = nuActivo;
    }

   
    // Property accessors

    public Integer getNukIdOperacionOm() {
        return this.nukIdOperacionOm;
    }
    
    public void setNukIdOperacionOm(Integer nukIdOperacionOm) {
        this.nukIdOperacionOm = nukIdOperacionOm;
    }

    public TbUsuario getTbUsuario() {
        return this.tbUsuario;
    }
    
    public void setTbUsuario(TbUsuario tbUsuario) {
        this.tbUsuario = tbUsuario;
    }

    public TbUbicacionProcesoOm getTbUbicacionProcesoOm() {
        return this.tbUbicacionProcesoOm;
    }
    
    public void setTbUbicacionProcesoOm(TbUbicacionProcesoOm tbUbicacionProcesoOm) {
        this.tbUbicacionProcesoOm = tbUbicacionProcesoOm;
    }

    public TbOrdenManufactura getTbOrdenManufactura() {
        return this.tbOrdenManufactura;
    }
    
    public void setTbOrdenManufactura(TbOrdenManufactura tbOrdenManufactura) {
        this.tbOrdenManufactura = tbOrdenManufactura;
    }

    public TbEmpresa getTbEmpresa() {
        return this.tbEmpresa;
    }
    
    public void setTbEmpresa(TbEmpresa tbEmpresa) {
        this.tbEmpresa = tbEmpresa;
    }

    public Integer getNuCantidad() {
        return this.nuCantidad;
    }
    
    public void setNuCantidad(Integer nuCantidad) {
        this.nuCantidad = nuCantidad;
    }

    public Timestamp getDtFecha() {
        return this.dtFecha;
    }
    
    public void setDtFecha(Timestamp dtFecha) {
        this.dtFecha = dtFecha;
    }

    public Double getNuHora() {
        return this.nuHora;
    }
    
    public void setNuHora(Double nuHora) {
        this.nuHora = nuHora;
    }

    public String getChCodigoScrap() {
        return this.chCodigoScrap;
    }
    
    public void setChCodigoScrap(String chCodigoScrap) {
        this.chCodigoScrap = chCodigoScrap;
    }

    public Integer getNuScrap() {
        return this.nuScrap;
    }
    
    public void setNuScrap(Integer nuScrap) {
        this.nuScrap = nuScrap;
    }

    public String getChCodigoFtq() {
        return this.chCodigoFtq;
    }
    
    public void setChCodigoFtq(String chCodigoFtq) {
        this.chCodigoFtq = chCodigoFtq;
    }

    public Integer getNuFtq() {
        return this.nuFtq;
    }
    
    public void setNuFtq(Integer nuFtq) {
        this.nuFtq = nuFtq;
    }

    public String getChCodigoDownTime() {
        return this.chCodigoDownTime;
    }
    
    public void setChCodigoDownTime(String chCodigoDownTime) {
        this.chCodigoDownTime = chCodigoDownTime;
    }

    public Integer getNuDownTime() {
        return this.nuDownTime;
    }
    
    public void setNuDownTime(Integer nuDownTime) {
        this.nuDownTime = nuDownTime;
    }

    public String getNuStatus() {
        return this.nuStatus;
    }
    
    public void setNuStatus(String nuStatus) {
        this.nuStatus = nuStatus;
    }

    public Boolean getNuActivo() {
        return this.nuActivo;
    }
    
    public void setNuActivo(Boolean nuActivo) {
        this.nuActivo = nuActivo;
    }
   








}