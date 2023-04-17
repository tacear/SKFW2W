package mx.com.hunkabann.skf.mapeo;



/**
 * TbPlacaProdTerm entity. @author MyEclipse Persistence Tools
 */

public class TbPlacaProdTerm  implements java.io.Serializable {


    // Fields    

     private TbPlacaProdTermId id;
     private TbPlaca tbPlaca;
     private TbEmpresa tbEmpresa;
     private TbProductoTerminado tbProductoTerminado;


    // Constructors

    /** default constructor */
    public TbPlacaProdTerm() {
    }

	/** minimal constructor */
    public TbPlacaProdTerm(TbPlacaProdTermId id, TbProductoTerminado tbProductoTerminado) {
        this.id = id;
        this.tbProductoTerminado = tbProductoTerminado;
    }
    
    /** full constructor */
    public TbPlacaProdTerm(TbPlacaProdTermId id, TbPlaca tbPlaca, TbEmpresa tbEmpresa, TbProductoTerminado tbProductoTerminado) {
        this.id = id;
        this.tbPlaca = tbPlaca;
        this.tbEmpresa = tbEmpresa;
        this.tbProductoTerminado = tbProductoTerminado;
    }

   
    // Property accessors

    public TbPlacaProdTermId getId() {
        return this.id;
    }
    
    public void setId(TbPlacaProdTermId id) {
        this.id = id;
    }

    public TbPlaca getTbPlaca() {
        return this.tbPlaca;
    }
    
    public void setTbPlaca(TbPlaca tbPlaca) {
        this.tbPlaca = tbPlaca;
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
   








}