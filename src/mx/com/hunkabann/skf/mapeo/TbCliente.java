package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;


/**
 * TbCliente entity. @author MyEclipse Persistence Tools
 */

public class TbCliente  implements java.io.Serializable {


    // Fields    

     private Integer nukIdCliente;
     private TbPersona tbPersona;
     private String chClave;
     private String chNombre;
     private Timestamp dtFechaAlta;
     private Integer nuDiaPago;
     private Boolean nuActivo;


    // Constructors

    /** default constructor */
    public TbCliente() {
    }

	/** minimal constructor */
    public TbCliente(Integer nukIdCliente) {
        this.nukIdCliente = nukIdCliente;
    }
    
    /** full constructor */
    public TbCliente(Integer nukIdCliente, TbPersona tbPersona, String chClave, String chNombre, Timestamp dtFechaAlta, Integer nuDiaPago, Boolean nuActivo) {
        this.nukIdCliente = nukIdCliente;
        this.tbPersona = tbPersona;
        this.chClave = chClave;
        this.chNombre = chNombre;
        this.dtFechaAlta = dtFechaAlta;
        this.nuDiaPago = nuDiaPago;
        this.nuActivo = nuActivo;
    }

   
    // Property accessors

    public Integer getNukIdCliente() {
        return this.nukIdCliente;
    }
    
    public void setNukIdCliente(Integer nukIdCliente) {
        this.nukIdCliente = nukIdCliente;
    }

    public TbPersona getTbPersona() {
        return this.tbPersona;
    }
    
    public void setTbPersona(TbPersona tbPersona) {
        this.tbPersona = tbPersona;
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

    public Timestamp getDtFechaAlta() {
        return this.dtFechaAlta;
    }
    
    public void setDtFechaAlta(Timestamp dtFechaAlta) {
        this.dtFechaAlta = dtFechaAlta;
    }

    public Integer getNuDiaPago() {
        return this.nuDiaPago;
    }
    
    public void setNuDiaPago(Integer nuDiaPago) {
        this.nuDiaPago = nuDiaPago;
    }

    public Boolean getNuActivo() {
        return this.nuActivo;
    }
    
    public void setNuActivo(Boolean nuActivo) {
        this.nuActivo = nuActivo;
    }
   








}