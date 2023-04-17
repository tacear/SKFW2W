package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;


/**
 * TbProdTermMatPrim entity. @author MyEclipse Persistence Tools
 */

public class TbProdTermMatPrim  implements java.io.Serializable {


    // Fields    

     private Integer nukIdProdTermProdMatPrim;
     private TbMateriaPrima tbMateriaPrima;
     private TbProductoTerminado tbProductoTerminado;
     private Double nuCantidad;
     private String chObservaciones;
     private Timestamp dtFechaAlta;
     private Timestamp dtFechaModifica;
     private Boolean nuActivo;


    // Constructors

    /** default constructor */
    public TbProdTermMatPrim() {
    }

	/** minimal constructor */
    public TbProdTermMatPrim(Integer nukIdProdTermProdMatPrim) {
        this.nukIdProdTermProdMatPrim = nukIdProdTermProdMatPrim;
    }
    
    /** full constructor */
    public TbProdTermMatPrim(Integer nukIdProdTermProdMatPrim, TbMateriaPrima tbMateriaPrima, TbProductoTerminado tbProductoTerminado, Double nuCantidad, String chObservaciones, Timestamp dtFechaAlta, Timestamp dtFechaModifica, Boolean nuActivo) {
        this.nukIdProdTermProdMatPrim = nukIdProdTermProdMatPrim;
        this.tbMateriaPrima = tbMateriaPrima;
        this.tbProductoTerminado = tbProductoTerminado;
        this.nuCantidad = nuCantidad;
        this.chObservaciones = chObservaciones;
        this.dtFechaAlta = dtFechaAlta;
        this.dtFechaModifica = dtFechaModifica;
        this.nuActivo = nuActivo;
    }

   
    // Property accessors

    public Integer getNukIdProdTermProdMatPrim() {
        return this.nukIdProdTermProdMatPrim;
    }
    
    public void setNukIdProdTermProdMatPrim(Integer nukIdProdTermProdMatPrim) {
        this.nukIdProdTermProdMatPrim = nukIdProdTermProdMatPrim;
    }

    public TbMateriaPrima getTbMateriaPrima() {
        return this.tbMateriaPrima;
    }
    
    public void setTbMateriaPrima(TbMateriaPrima tbMateriaPrima) {
        this.tbMateriaPrima = tbMateriaPrima;
    }

    public TbProductoTerminado getTbProductoTerminado() {
        return this.tbProductoTerminado;
    }
    
    public void setTbProductoTerminado(TbProductoTerminado tbProductoTerminado) {
        this.tbProductoTerminado = tbProductoTerminado;
    }

    public Double getNuCantidad() {
        return this.nuCantidad;
    }
    
    public void setNuCantidad(Double nuCantidad) {
        this.nuCantidad = nuCantidad;
    }

    public String getChObservaciones() {
        return this.chObservaciones;
    }
    
    public void setChObservaciones(String chObservaciones) {
        this.chObservaciones = chObservaciones;
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

    public Boolean getNuActivo() {
        return this.nuActivo;
    }
    
    public void setNuActivo(Boolean nuActivo) {
        this.nuActivo = nuActivo;
    }
   








}