package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;


/**
 * TbCatUm entity. @author MyEclipse Persistence Tools
 */

public class TbCatUm  implements java.io.Serializable {


    // Fields    

     private Integer nukIdCatUm;
     private TbEmpresa tbEmpresa;
     private String chClave;
     private String chNombre;
     private String chDescripcion;
     private Boolean nuActivo;
     private Set tbMateriaPrimas = new HashSet(0);


    // Constructors

    /** default constructor */
    public TbCatUm() {
    }

	/** minimal constructor */
    public TbCatUm(Integer nukIdCatUm) {
        this.nukIdCatUm = nukIdCatUm;
    }
    
    /** full constructor */
    public TbCatUm(Integer nukIdCatUm, TbEmpresa tbEmpresa, String chClave, String chNombre, String chDescripcion, Boolean nuActivo, Set tbMateriaPrimas) {
        this.nukIdCatUm = nukIdCatUm;
        this.tbEmpresa = tbEmpresa;
        this.chClave = chClave;
        this.chNombre = chNombre;
        this.chDescripcion = chDescripcion;
        this.nuActivo = nuActivo;
        this.tbMateriaPrimas = tbMateriaPrimas;
    }

   
    // Property accessors

    public Integer getNukIdCatUm() {
        return this.nukIdCatUm;
    }
    
    public void setNukIdCatUm(Integer nukIdCatUm) {
        this.nukIdCatUm = nukIdCatUm;
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

    public Boolean getNuActivo() {
        return this.nuActivo;
    }
    
    public void setNuActivo(Boolean nuActivo) {
        this.nuActivo = nuActivo;
    }

    public Set getTbMateriaPrimas() {
        return this.tbMateriaPrimas;
    }
    
    public void setTbMateriaPrimas(Set tbMateriaPrimas) {
        this.tbMateriaPrimas = tbMateriaPrimas;
    }
   








}