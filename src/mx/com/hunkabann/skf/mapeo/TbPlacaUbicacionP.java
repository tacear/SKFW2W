package mx.com.hunkabann.skf.mapeo;



/**
 * TbPlacaUbicacionP entity. @author MyEclipse Persistence Tools
 */

public class TbPlacaUbicacionP  implements java.io.Serializable {


    // Fields    

     private TbPlacaUbicacionPId id;
     private TbPlaca tbPlaca;
     private TbUbicacionPlaca tbUbicacionPlaca;


    // Constructors

    /** default constructor */
    public TbPlacaUbicacionP() {
    }

    
    /** full constructor */
    public TbPlacaUbicacionP(TbPlacaUbicacionPId id, TbPlaca tbPlaca, TbUbicacionPlaca tbUbicacionPlaca) {
        this.id = id;
        this.tbPlaca = tbPlaca;
        this.tbUbicacionPlaca = tbUbicacionPlaca;
    }

   
    // Property accessors

    public TbPlacaUbicacionPId getId() {
        return this.id;
    }
    
    public void setId(TbPlacaUbicacionPId id) {
        this.id = id;
    }

    public TbPlaca getTbPlaca() {
        return this.tbPlaca;
    }
    
    public void setTbPlaca(TbPlaca tbPlaca) {
        this.tbPlaca = tbPlaca;
    }

    public TbUbicacionPlaca getTbUbicacionPlaca() {
        return this.tbUbicacionPlaca;
    }
    
    public void setTbUbicacionPlaca(TbUbicacionPlaca tbUbicacionPlaca) {
        this.tbUbicacionPlaca = tbUbicacionPlaca;
    }
   








}