package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;


/**
 * TbEntregaRecepcion entity. @author MyEclipse Persistence Tools
 */

public class TbEntregaRecepcion  implements java.io.Serializable {


    // Fields    

     private Integer nukIdEntregaRecepcion;
     private TbUsuario tbUsuarioByNukIdUsuarioEntrega;
     private TbUsuario tbUsuarioByNukIdUsuarioRecibe;
     private TbOrdenManufactura tbOrdenManufactura;
     private TbMateriaPrima tbMateriaPrima;
     private TbEmpresa tbEmpresa;
     private TbProductoTerminado tbProductoTerminado;
     private Timestamp dtFechaEntrega;
     private Integer nuCantidadEntrega;
     private Timestamp dtFechaRecibe;
     private Integer nuCantidadRecibe;
     private String chObservaciones;


    // Constructors

    /** default constructor */
    public TbEntregaRecepcion() {
    }

	/** minimal constructor */
    public TbEntregaRecepcion(Integer nukIdEntregaRecepcion, TbEmpresa tbEmpresa) {
        this.nukIdEntregaRecepcion = nukIdEntregaRecepcion;
        this.tbEmpresa = tbEmpresa;
    }
    
    /** full constructor */
    public TbEntregaRecepcion(Integer nukIdEntregaRecepcion, TbUsuario tbUsuarioByNukIdUsuarioEntrega, TbUsuario tbUsuarioByNukIdUsuarioRecibe, TbOrdenManufactura tbOrdenManufactura, TbMateriaPrima tbMateriaPrima, TbEmpresa tbEmpresa, TbProductoTerminado tbProductoTerminado, Timestamp dtFechaEntrega, Integer nuCantidadEntrega, Timestamp dtFechaRecibe, Integer nuCantidadRecibe, String chObservaciones) {
        this.nukIdEntregaRecepcion = nukIdEntregaRecepcion;
        this.tbUsuarioByNukIdUsuarioEntrega = tbUsuarioByNukIdUsuarioEntrega;
        this.tbUsuarioByNukIdUsuarioRecibe = tbUsuarioByNukIdUsuarioRecibe;
        this.tbOrdenManufactura = tbOrdenManufactura;
        this.tbMateriaPrima = tbMateriaPrima;
        this.tbEmpresa = tbEmpresa;
        this.tbProductoTerminado = tbProductoTerminado;
        this.dtFechaEntrega = dtFechaEntrega;
        this.nuCantidadEntrega = nuCantidadEntrega;
        this.dtFechaRecibe = dtFechaRecibe;
        this.nuCantidadRecibe = nuCantidadRecibe;
        this.chObservaciones = chObservaciones;
    }

   
    // Property accessors

    public Integer getNukIdEntregaRecepcion() {
        return this.nukIdEntregaRecepcion;
    }
    
    public void setNukIdEntregaRecepcion(Integer nukIdEntregaRecepcion) {
        this.nukIdEntregaRecepcion = nukIdEntregaRecepcion;
    }

    public TbUsuario getTbUsuarioByNukIdUsuarioEntrega() {
        return this.tbUsuarioByNukIdUsuarioEntrega;
    }
    
    public void setTbUsuarioByNukIdUsuarioEntrega(TbUsuario tbUsuarioByNukIdUsuarioEntrega) {
        this.tbUsuarioByNukIdUsuarioEntrega = tbUsuarioByNukIdUsuarioEntrega;
    }

    public TbUsuario getTbUsuarioByNukIdUsuarioRecibe() {
        return this.tbUsuarioByNukIdUsuarioRecibe;
    }
    
    public void setTbUsuarioByNukIdUsuarioRecibe(TbUsuario tbUsuarioByNukIdUsuarioRecibe) {
        this.tbUsuarioByNukIdUsuarioRecibe = tbUsuarioByNukIdUsuarioRecibe;
    }

    public TbOrdenManufactura getTbOrdenManufactura() {
        return this.tbOrdenManufactura;
    }
    
    public void setTbOrdenManufactura(TbOrdenManufactura tbOrdenManufactura) {
        this.tbOrdenManufactura = tbOrdenManufactura;
    }

    public TbMateriaPrima getTbMateriaPrima() {
        return this.tbMateriaPrima;
    }
    
    public void setTbMateriaPrima(TbMateriaPrima tbMateriaPrima) {
        this.tbMateriaPrima = tbMateriaPrima;
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

    public Timestamp getDtFechaEntrega() {
        return this.dtFechaEntrega;
    }
    
    public void setDtFechaEntrega(Timestamp dtFechaEntrega) {
        this.dtFechaEntrega = dtFechaEntrega;
    }

    public Integer getNuCantidadEntrega() {
        return this.nuCantidadEntrega;
    }
    
    public void setNuCantidadEntrega(Integer nuCantidadEntrega) {
        this.nuCantidadEntrega = nuCantidadEntrega;
    }

    public Timestamp getDtFechaRecibe() {
        return this.dtFechaRecibe;
    }
    
    public void setDtFechaRecibe(Timestamp dtFechaRecibe) {
        this.dtFechaRecibe = dtFechaRecibe;
    }

    public Integer getNuCantidadRecibe() {
        return this.nuCantidadRecibe;
    }
    
    public void setNuCantidadRecibe(Integer nuCantidadRecibe) {
        this.nuCantidadRecibe = nuCantidadRecibe;
    }

    public String getChObservaciones() {
        return this.chObservaciones;
    }
    
    public void setChObservaciones(String chObservaciones) {
        this.chObservaciones = chObservaciones;
    }
   








}