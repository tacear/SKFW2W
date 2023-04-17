package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * TbOrdenManufactura entity. @author MyEclipse Persistence Tools
 */

public class TbOrdenManufactura  implements java.io.Serializable {


    // Fields    

     private Integer nukIdOrdenManufactura;
     private TbUsuario tbUsuario;
     private TbPlanControl tbPlanControl;
     private TbEmpresa tbEmpresa;
     private TbProductoTerminado tbProductoTerminado;
     private TbCatStatus tbCatStatus;
     private String chOrdenManufactura;
     private String chPack;
     private Integer nuTotal;
     private Double nuHrsOrden;
     private Double nuDiasSurtido;
     private Double nuPiezasXhora;
     private Timestamp dtFechaAlta;
     private Timestamp dtFechaMod;
     private Boolean nuActivo;
     private Integer nuValidaMp;
     private Integer nuValidaHule;
     private Timestamp dtFechaPlaneacion;
     private Set tbPlacaOms = new HashSet(0);
     private Set tbOperacionOrdenManufacturas = new HashSet(0);
     private Set tbEntregaRecepcions = new HashSet(0);


    // Constructors

    /** default constructor */
    public TbOrdenManufactura() {
    }

	/** minimal constructor */
    public TbOrdenManufactura(Integer nukIdOrdenManufactura) {
        this.nukIdOrdenManufactura = nukIdOrdenManufactura;
    }
    
    /** full constructor */
    public TbOrdenManufactura(Integer nukIdOrdenManufactura, TbUsuario tbUsuario, TbPlanControl tbPlanControl, TbEmpresa tbEmpresa, TbProductoTerminado tbProductoTerminado, TbCatStatus tbCatStatus, String chOrdenManufactura, String chPack, Integer nuTotal, Double nuHrsOrden, Double nuDiasSurtido, Double nuPiezasXhora, Timestamp dtFechaAlta, Timestamp dtFechaMod, Boolean nuActivo, Integer nuValidaMp, Integer nuValidaHule, Timestamp dtFechaPlaneacion, Set tbPlacaOms, Set tbOperacionOrdenManufacturas, Set tbEntregaRecepcions) {
        this.nukIdOrdenManufactura = nukIdOrdenManufactura;
        this.tbUsuario = tbUsuario;
        this.tbPlanControl = tbPlanControl;
        this.tbEmpresa = tbEmpresa;
        this.tbProductoTerminado = tbProductoTerminado;
        this.tbCatStatus = tbCatStatus;
        this.chOrdenManufactura = chOrdenManufactura;
        this.chPack = chPack;
        this.nuTotal = nuTotal;
        this.nuHrsOrden = nuHrsOrden;
        this.nuDiasSurtido = nuDiasSurtido;
        this.nuPiezasXhora = nuPiezasXhora;
        this.dtFechaAlta = dtFechaAlta;
        this.dtFechaMod = dtFechaMod;
        this.nuActivo = nuActivo;
        this.nuValidaMp = nuValidaMp;
        this.nuValidaHule = nuValidaHule;
        this.dtFechaPlaneacion = dtFechaPlaneacion;
        this.tbPlacaOms = tbPlacaOms;
        this.tbOperacionOrdenManufacturas = tbOperacionOrdenManufacturas;
        this.tbEntregaRecepcions = tbEntregaRecepcions;
    }

   
    // Property accessors

    public Integer getNukIdOrdenManufactura() {
        return this.nukIdOrdenManufactura;
    }
    
    public void setNukIdOrdenManufactura(Integer nukIdOrdenManufactura) {
        this.nukIdOrdenManufactura = nukIdOrdenManufactura;
    }

    public TbUsuario getTbUsuario() {
        return this.tbUsuario;
    }
    
    public void setTbUsuario(TbUsuario tbUsuario) {
        this.tbUsuario = tbUsuario;
    }

    public TbPlanControl getTbPlanControl() {
        return this.tbPlanControl;
    }
    
    public void setTbPlanControl(TbPlanControl tbPlanControl) {
        this.tbPlanControl = tbPlanControl;
    }

    public TbEmpresa getTbEmpresa() {
        return this.tbEmpresa;
    }
    
    public void setTbEmpresa(TbEmpresa tbEmpresa) {
        this.tbEmpresa = tbEmpresa;
    }

    public TbProductoTerminado getTbProductoTerminado() {
        return this.tbProductoTerminado;
    }
    
    public void setTbProductoTerminado(TbProductoTerminado tbProductoTerminado) {
        this.tbProductoTerminado = tbProductoTerminado;
    }

    public TbCatStatus getTbCatStatus() {
        return this.tbCatStatus;
    }
    
    public void setTbCatStatus(TbCatStatus tbCatStatus) {
        this.tbCatStatus = tbCatStatus;
    }

    public String getChOrdenManufactura() {
        return this.chOrdenManufactura;
    }
    
    public void setChOrdenManufactura(String chOrdenManufactura) {
        this.chOrdenManufactura = chOrdenManufactura;
    }

    public String getChPack() {
        return this.chPack;
    }
    
    public void setChPack(String chPack) {
        this.chPack = chPack;
    }

    public Integer getNuTotal() {
        return this.nuTotal;
    }
    
    public void setNuTotal(Integer nuTotal) {
        this.nuTotal = nuTotal;
    }

    public Double getNuHrsOrden() {
        return this.nuHrsOrden;
    }
    
    public void setNuHrsOrden(Double nuHrsOrden) {
        this.nuHrsOrden = nuHrsOrden;
    }

    public Double getNuDiasSurtido() {
        return this.nuDiasSurtido;
    }
    
    public void setNuDiasSurtido(Double nuDiasSurtido) {
        this.nuDiasSurtido = nuDiasSurtido;
    }

    public Double getNuPiezasXhora() {
        return this.nuPiezasXhora;
    }
    
    public void setNuPiezasXhora(Double nuPiezasXhora) {
        this.nuPiezasXhora = nuPiezasXhora;
    }

    public Timestamp getDtFechaAlta() {
        return this.dtFechaAlta;
    }
    
    public void setDtFechaAlta(Timestamp dtFechaAlta) {
        this.dtFechaAlta = dtFechaAlta;
    }

    public Timestamp getDtFechaMod() {
        return this.dtFechaMod;
    }
    
    public void setDtFechaMod(Timestamp dtFechaMod) {
        this.dtFechaMod = dtFechaMod;
    }

    public Boolean getNuActivo() {
        return this.nuActivo;
    }
    
    public void setNuActivo(Boolean nuActivo) {
        this.nuActivo = nuActivo;
    }

    public Integer getNuValidaMp() {
        return this.nuValidaMp;
    }
    
    public void setNuValidaMp(Integer nuValidaMp) {
        this.nuValidaMp = nuValidaMp;
    }

    public Integer getNuValidaHule() {
        return this.nuValidaHule;
    }
    
    public void setNuValidaHule(Integer nuValidaHule) {
        this.nuValidaHule = nuValidaHule;
    }

    public Timestamp getDtFechaPlaneacion() {
        return this.dtFechaPlaneacion;
    }
    
    public void setDtFechaPlaneacion(Timestamp dtFechaPlaneacion) {
        this.dtFechaPlaneacion = dtFechaPlaneacion;
    }

    public Set getTbPlacaOms() {
        return this.tbPlacaOms;
    }
    
    public void setTbPlacaOms(Set tbPlacaOms) {
        this.tbPlacaOms = tbPlacaOms;
    }

    public Set getTbOperacionOrdenManufacturas() {
        return this.tbOperacionOrdenManufacturas;
    }
    
    public void setTbOperacionOrdenManufacturas(Set tbOperacionOrdenManufacturas) {
        this.tbOperacionOrdenManufacturas = tbOperacionOrdenManufacturas;
    }

    public Set getTbEntregaRecepcions() {
        return this.tbEntregaRecepcions;
    }
    
    public void setTbEntregaRecepcions(Set tbEntregaRecepcions) {
        this.tbEntregaRecepcions = tbEntregaRecepcions;
    }
   








}