package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * TbMaquinaDispositivo entity. @author MyEclipse Persistence Tools
 */

public class TbMaquinaDispositivo  implements java.io.Serializable {


    // Fields    

     private Integer nukIdMaquinaDisp;
     private TbEmpresa tbEmpresa;
     private String chClave;
     private String chNombre;
     private String chDescripcion;
     private Timestamp dtFechaAlta;
     private Boolean nuActivo;
     private Set tbUbicacionProcesoOms = new HashSet(0);


    // Constructors

    /** default constructor */
    public TbMaquinaDispositivo() {
    }

	/** minimal constructor */
    public TbMaquinaDispositivo(Integer nukIdMaquinaDisp) {
        this.nukIdMaquinaDisp = nukIdMaquinaDisp;
    }
    
    /** full constructor */
    public TbMaquinaDispositivo(Integer nukIdMaquinaDisp, TbEmpresa tbEmpresa, String chClave, String chNombre, String chDescripcion, Timestamp dtFechaAlta, Boolean nuActivo, Set tbUbicacionProcesoOms) {
        this.nukIdMaquinaDisp = nukIdMaquinaDisp;
        this.tbEmpresa = tbEmpresa;
        this.chClave = chClave;
        this.chNombre = chNombre;
        this.chDescripcion = chDescripcion;
        this.dtFechaAlta = dtFechaAlta;
        this.nuActivo = nuActivo;
        this.tbUbicacionProcesoOms = tbUbicacionProcesoOms;
    }

   
    // Property accessors

    public Integer getNukIdMaquinaDisp() {
        return this.nukIdMaquinaDisp;
    }
    
    public void setNukIdMaquinaDisp(Integer nukIdMaquinaDisp) {
        this.nukIdMaquinaDisp = nukIdMaquinaDisp;
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

    public Set getTbUbicacionProcesoOms() {
        return this.tbUbicacionProcesoOms;
    }
    
    public void setTbUbicacionProcesoOms(Set tbUbicacionProcesoOms) {
        this.tbUbicacionProcesoOms = tbUbicacionProcesoOms;
    }
   








}