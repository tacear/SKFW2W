package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * TbTipoLocacion entity. @author MyEclipse Persistence Tools
 */

public class TbTipoLocacion  implements java.io.Serializable {


    // Fields    

     private Integer nukIdTipoLocacion;
     private TbEmpresa tbEmpresa;
     private String chClave;
     private String chNombre;
     private String chDescripcion;
     private Timestamp dtFecha;
     private Boolean nuActivo;
     private Set tbUbicacionProcesoOms = new HashSet(0);


    // Constructors

    /** default constructor */
    public TbTipoLocacion() {
    }

	/** minimal constructor */
    public TbTipoLocacion(Integer nukIdTipoLocacion) {
        this.nukIdTipoLocacion = nukIdTipoLocacion;
    }
    
    /** full constructor */
    public TbTipoLocacion(Integer nukIdTipoLocacion, TbEmpresa tbEmpresa, String chClave, String chNombre, String chDescripcion, Timestamp dtFecha, Boolean nuActivo, Set tbUbicacionProcesoOms) {
        this.nukIdTipoLocacion = nukIdTipoLocacion;
        this.tbEmpresa = tbEmpresa;
        this.chClave = chClave;
        this.chNombre = chNombre;
        this.chDescripcion = chDescripcion;
        this.dtFecha = dtFecha;
        this.nuActivo = nuActivo;
        this.tbUbicacionProcesoOms = tbUbicacionProcesoOms;
    }

   
    // Property accessors

    public Integer getNukIdTipoLocacion() {
        return this.nukIdTipoLocacion;
    }
    
    public void setNukIdTipoLocacion(Integer nukIdTipoLocacion) {
        this.nukIdTipoLocacion = nukIdTipoLocacion;
    }

    public TbEmpresa getTbEmpresa() {
        return this.tbEmpresa;
    }
    
    public void setTbEmpresa(TbEmpresa tbEmpresa) {
        this.tbEmpresa = tbEmpresa;
    }

    public String getChClave() {
        return this.chClave;
    }
    
    public void setChClave(String chClave) {
        this.chClave = chClave;
    }

    public String getChNombre() {
        return this.chNombre;
    }
    
    public void setChNombre(String chNombre) {
        this.chNombre = chNombre;
    }

    public String getChDescripcion() {
        return this.chDescripcion;
    }
    
    public void setChDescripcion(String chDescripcion) {
        this.chDescripcion = chDescripcion;
    }

    public Timestamp getDtFecha() {
        return this.dtFecha;
    }
    
    public void setDtFecha(Timestamp dtFecha) {
        this.dtFecha = dtFecha;
    }

    public Boolean getNuActivo() {
        return this.nuActivo;
    }
    
    public void setNuActivo(Boolean nuActivo) {
        this.nuActivo = nuActivo;
    }

    public Set getTbUbicacionProcesoOms() {
        return this.tbUbicacionProcesoOms;
    }
    
    public void setTbUbicacionProcesoOms(Set tbUbicacionProcesoOms) {
        this.tbUbicacionProcesoOms = tbUbicacionProcesoOms;
    }
   








}