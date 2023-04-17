package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;


/**
 * TbEmpleado entity. @author MyEclipse Persistence Tools
 */

public class TbEmpleado  implements java.io.Serializable {


    // Fields    

     private Integer nukIdEmpleado;
     private TbPersona tbPersona;
     private String chNumEmpleado;
     private Timestamp dtFechaIniLabora;
     private Timestamp dtFechaCumple;
     private Boolean nuActivo;


    // Constructors

    /** default constructor */
    public TbEmpleado() {
    }

	/** minimal constructor */
    public TbEmpleado(Integer nukIdEmpleado) {
        this.nukIdEmpleado = nukIdEmpleado;
    }
    
    /** full constructor */
    public TbEmpleado(Integer nukIdEmpleado, TbPersona tbPersona, String chNumEmpleado, Timestamp dtFechaIniLabora, Timestamp dtFechaCumple, Boolean nuActivo) {
        this.nukIdEmpleado = nukIdEmpleado;
        this.tbPersona = tbPersona;
        this.chNumEmpleado = chNumEmpleado;
        this.dtFechaIniLabora = dtFechaIniLabora;
        this.dtFechaCumple = dtFechaCumple;
        this.nuActivo = nuActivo;
    }

   
    // Property accessors

    public Integer getNukIdEmpleado() {
        return this.nukIdEmpleado;
    }
    
    public void setNukIdEmpleado(Integer nukIdEmpleado) {
        this.nukIdEmpleado = nukIdEmpleado;
    }

    public TbPersona getTbPersona() {
        return this.tbPersona;
    }
    
    public void setTbPersona(TbPersona tbPersona) {
        this.tbPersona = tbPersona;
    }

    public String getChNumEmpleado() {
        return this.chNumEmpleado;
    }
    
    public void setChNumEmpleado(String chNumEmpleado) {
        this.chNumEmpleado = chNumEmpleado;
    }

    public Timestamp getDtFechaIniLabora() {
        return this.dtFechaIniLabora;
    }
    
    public void setDtFechaIniLabora(Timestamp dtFechaIniLabora) {
        this.dtFechaIniLabora = dtFechaIniLabora;
    }

    public Timestamp getDtFechaCumple() {
        return this.dtFechaCumple;
    }
    
    public void setDtFechaCumple(Timestamp dtFechaCumple) {
        this.dtFechaCumple = dtFechaCumple;
    }

    public Boolean getNuActivo() {
        return this.nuActivo;
    }
    
    public void setNuActivo(Boolean nuActivo) {
        this.nuActivo = nuActivo;
    }
   








}