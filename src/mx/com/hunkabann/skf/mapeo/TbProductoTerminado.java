package mx.com.hunkabann.skf.mapeo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * TbProductoTerminado entity. @author MyEclipse Persistence Tools
 */

public class TbProductoTerminado  implements java.io.Serializable {


    // Fields    

     private Integer nukIdProdTerm;
     private TbEmpresa tbEmpresa;
     private String chSku;
     private String chDescripcion;
     private Double nuCostoPromedio;
     private Timestamp dtFechaAlta;
     private Timestamp dtFechaModifica;
     private Boolean nuActivo;
     private Set tbEntregaRecepcions = new HashSet(0);
     private Set tbProdTermMatPrims = new HashSet(0);
     private Set tbPlacaProdTerms = new HashSet(0);
     private Set tbOrdenManufacturas = new HashSet(0);


    // Constructors

    /** default constructor */
    public TbProductoTerminado() {
    }

	/** minimal constructor */
    public TbProductoTerminado(Integer nukIdProdTerm) {
        this.nukIdProdTerm = nukIdProdTerm;
    }
    
    /** full constructor */
    public TbProductoTerminado(Integer nukIdProdTerm, TbEmpresa tbEmpresa, String chSku, String chDescripcion, Double nuCostoPromedio, Timestamp dtFechaAlta, Timestamp dtFechaModifica, Boolean nuActivo, Set tbEntregaRecepcions, Set tbProdTermMatPrims, Set tbPlacaProdTerms, Set tbOrdenManufacturas) {
        this.nukIdProdTerm = nukIdProdTerm;
        this.tbEmpresa = tbEmpresa;
        this.chSku = chSku;
        this.chDescripcion = chDescripcion;
        this.nuCostoPromedio = nuCostoPromedio;
        this.dtFechaAlta = dtFechaAlta;
        this.dtFechaModifica = dtFechaModifica;
        this.nuActivo = nuActivo;
        this.tbEntregaRecepcions = tbEntregaRecepcions;
        this.tbProdTermMatPrims = tbProdTermMatPrims;
        this.tbPlacaProdTerms = tbPlacaProdTerms;
        this.tbOrdenManufacturas = tbOrdenManufacturas;
    }

   
    // Property accessors

    public Integer getNukIdProdTerm() {
        return this.nukIdProdTerm;
    }
    
    public void setNukIdProdTerm(Integer nukIdProdTerm) {
        this.nukIdProdTerm = nukIdProdTerm;
    }

    public TbEmpresa getTbEmpresa() {
        return this.tbEmpresa;
    }
    
    public void setTbEmpresa(TbEmpresa tbEmpresa) {
        this.tbEmpresa = tbEmpresa;
    }

    public String getChSku() {
        return this.chSku;
    }
    
    public void setChSku(String chSku) {
        this.chSku = chSku;
    }

    public String getChDescripcion() {
        return this.chDescripcion;
    }
    
    public void setChDescripcion(String chDescripcion) {
        this.chDescripcion = chDescripcion;
    }

    public Double getNuCostoPromedio() {
        return this.nuCostoPromedio;
    }
    
    public void setNuCostoPromedio(Double nuCostoPromedio) {
        this.nuCostoPromedio = nuCostoPromedio;
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

    public Set getTbEntregaRecepcions() {
        return this.tbEntregaRecepcions;
    }
    
    public void setTbEntregaRecepcions(Set tbEntregaRecepcions) {
        this.tbEntregaRecepcions = tbEntregaRecepcions;
    }

    public Set getTbProdTermMatPrims() {
        return this.tbProdTermMatPrims;
    }
    
    public void setTbProdTermMatPrims(Set tbProdTermMatPrims) {
        this.tbProdTermMatPrims = tbProdTermMatPrims;
    }

    public Set getTbPlacaProdTerms() {
        return this.tbPlacaProdTerms;
    }
    
    public void setTbPlacaProdTerms(Set tbPlacaProdTerms) {
        this.tbPlacaProdTerms = tbPlacaProdTerms;
    }

    public Set getTbOrdenManufacturas() {
        return this.tbOrdenManufacturas;
    }
    
    public void setTbOrdenManufacturas(Set tbOrdenManufacturas) {
        this.tbOrdenManufacturas = tbOrdenManufacturas;
    }
   








}