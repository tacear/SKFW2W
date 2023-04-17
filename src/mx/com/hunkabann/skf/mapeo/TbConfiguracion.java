package mx.com.hunkabann.skf.mapeo;



/**
 * TbConfiguracion entity. @author MyEclipse Persistence Tools
 */

public class TbConfiguracion  implements java.io.Serializable {


    // Fields    

     private Integer nukIdConficuracion;
     private TbEmpresa tbEmpresa;
     private String chClave;
     private String chNombre;
     private String chDescripcion;
     private Double nuTipoCambio;
     private Boolean nuActivo;


    // Constructors

    /** default constructor */
    public TbConfiguracion() {
    }

	/** minimal constructor */
    public TbConfiguracion(Integer nukIdConficuracion) {
        this.nukIdConficuracion = nukIdConficuracion;
    }
    
    /** full constructor */
    public TbConfiguracion(Integer nukIdConficuracion, TbEmpresa tbEmpresa, String chClave, String chNombre, String chDescripcion, Double nuTipoCambio, Boolean nuActivo) {
        this.nukIdConficuracion = nukIdConficuracion;
        this.tbEmpresa = tbEmpresa;
        this.chClave = chClave;
        this.chNombre = chNombre;
        this.chDescripcion = chDescripcion;
        this.nuTipoCambio = nuTipoCambio;
        this.nuActivo = nuActivo;
    }

   
    // Property accessors

    public Integer getNukIdConficuracion() {
        return this.nukIdConficuracion;
    }
    
    public void setNukIdConficuracion(Integer nukIdConficuracion) {
        this.nukIdConficuracion = nukIdConficuracion;
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

    public Double getNuTipoCambio() {
        return this.nuTipoCambio;
    }
    
    public void setNuTipoCambio(Double nuTipoCambio) {
        this.nuTipoCambio = nuTipoCambio;
    }

    public Boolean getNuActivo() {
        return this.nuActivo;
    }
    
    public void setNuActivo(Boolean nuActivo) {
        this.nuActivo = nuActivo;
    }
   








}