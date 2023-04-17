package mx.com.hunkabann.skf.mapeo;

import java.util.HashSet;
import java.util.Set;


/**
 * TbTipoPlanControl entity. @author MyEclipse Persistence Tools
 */

public class TbTipoPlanControl  implements java.io.Serializable {


    // Fields    

     private Integer nukIdTipoPlanControl;
     private TbEmpresa tbEmpresa;
     private String chClave;
     private String chNombre;
     private String chDescripcion;
     private Boolean nuActivo;
     private Set tbPlanControls = new HashSet(0);


    // Constructors

    /** default constructor */
    public TbTipoPlanControl() {
    }

	/** minimal constructor */
    public TbTipoPlanControl(Integer nukIdTipoPlanControl) {
        this.nukIdTipoPlanControl = nukIdTipoPlanControl;
    }
    
    /** full constructor */
    public TbTipoPlanControl(Integer nukIdTipoPlanControl, TbEmpresa tbEmpresa, String chClave, String chNombre, String chDescripcion, Boolean nuActivo, Set tbPlanControls) {
        this.nukIdTipoPlanControl = nukIdTipoPlanControl;
        this.tbEmpresa = tbEmpresa;
        this.chClave = chClave;
        this.chNombre = chNombre;
        this.chDescripcion = chDescripcion;
        this.nuActivo = nuActivo;
        this.tbPlanControls = tbPlanControls;
    }

   
    // Property accessors

    public Integer getNukIdTipoPlanControl() {
        return this.nukIdTipoPlanControl;
    }
    
    public void setNukIdTipoPlanControl(Integer nukIdTipoPlanControl) {
        this.nukIdTipoPlanControl = nukIdTipoPlanControl;
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

    public Set getTbPlanControls() {
        return this.tbPlanControls;
    }
    
    public void setTbPlanControls(Set tbPlanControls) {
        this.tbPlanControls = tbPlanControls;
    }
   








}