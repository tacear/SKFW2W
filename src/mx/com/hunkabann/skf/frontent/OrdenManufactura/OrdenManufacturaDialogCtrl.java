package mx.com.hunkabann.skf.frontent.OrdenManufactura;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.servlet.http.HttpSession;

import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.frontent.reports.JRreportWindow;
import mx.com.hunkabann.skf.frontent.util.ButtonStatusCtrl;
import mx.com.hunkabann.skf.frontent.util.ComboFactory;
import mx.com.hunkabann.skf.frontent.util.GFCBaseCtrl;
import mx.com.hunkabann.skf.frontent.util.GFCBaseListCtrl;
import mx.com.hunkabann.skf.frontent.util.MultiLineMessageBox;
import mx.com.hunkabann.skf.mapeo.TbOrdenManufactura;
import mx.com.hunkabann.skf.util.CfdUtil;
import mx.com.hunkabann.skf.util.Codificador;
import mx.com.hunkabann.skf.util.MailSender;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContext;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.http.SimpleSession;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import utils.FileFilter;

import com.lowagie.text.Document;



public class OrdenManufacturaDialogCtrl extends GFCBaseListCtrl<TbOrdenManufactura> implements Serializable {

	private static final long serialVersionUID = -546886879998950467L;
	private transient static final Logger logger = Logger.getLogger(OrdenManufacturaDialogCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected transient Window ordenCompraDialogWindow; // autowired
	protected transient Tab tab_UserDialog_Details; // autowired
	
	//Variables para la Factura
//	protected transient Groupbox gb_Factura;
	protected transient Textbox txt_Factura_verzion;
	protected transient Textbox txt_Factura_Serie;
	protected transient Textbox txt_Factura_Folio;
	protected transient Datebox dt_Factura_Fecha;
	protected transient Textbox txt_Factura_Condi_Pago;
	protected transient Textbox txt_Factura_Subtotal;
	protected transient Textbox txt_Factura_Descuento;
	protected transient Textbox txt_Factura_Moneda;
	protected transient Textbox txt_Factura_Tipo_Cambio;
	protected transient Textbox txt_Factura_Total;
	protected transient Listbox lb_Factura_Tipo_Compro;	
	protected transient Textbox txt_Factura_Metodo_Pago;
	protected transient Textbox txt_Factura_Lugar_Expedi;
	protected transient Textbox txt_Factura_Confirma;

	protected transient Groupbox gb_Factura_02_Relac;
	protected transient Listbox lb_Factura_Relacion;
	
	protected transient Textbox txt_Factura_RElacion_UUID;

	protected transient Groupbox gb_Factura_04_Emi;
	protected transient Textbox txt_Factura_Emi_RFC;
	protected transient Textbox txt_Factura_Emi_Nombre;
	protected transient Textbox txt_Factura_Emi_Regimen;

	protected transient Groupbox gb_Factura_05_Receptor;
	protected transient Textbox txt_Factura_Recep_RFC;
	protected transient Textbox txt_Factura_Recep_Nombre;
	protected transient Textbox txt_Factura_Recep_Residencia;
	protected transient Textbox txt_Factura_Recep_No_Tribu;
//	Listbox lb_UsoCFDI = new Listbox();

	protected transient Groupbox gb_Factura_06_concep;
	protected transient Textbox txt_Factura_Conceptos_Clave;
	protected transient Textbox txt_Factura_Conceptos_No_Identi;
	protected transient Doublebox txt_Factura_Conceptos_Cantidad;
	protected transient Textbox txt_Factura_Conceptos_ClaveUni;
	protected transient Textbox txt_Factura_Conceptos_Unidad;
	protected transient Textbox txt_Factura_Conceptos_Desc;
	protected transient Doublebox txt_Factura_Conceptos_ValorUni;
	protected transient Doublebox txt_Factura_Conceptos_Importe;
	protected transient Doublebox txt_Factura_Conceptos_Descuento;

	protected transient Groupbox gb_Factura_07_Imp_Tras;
	protected transient Doublebox txt_Factura_Imp_Tras_Base;
	protected transient Listbox lb_Factura_Imp_Tras_Imp;
	protected transient Listbox lb_Factura_Imp_Tras_Factor;
	protected transient Listbox lb_Factura_Imp_Tras_Tasa;
	protected transient Doublebox txt_Factura_Imp_Tras_Importe;

	protected transient Groupbox gb_Factura_08_Imp_Rete;
	protected transient Doublebox txt_Factura_Imp_Ret_Base; 	
	protected transient Listbox lb_Factura_Imp_Ret_Impuesto;
	protected transient Listbox lb_Factura_Imp_Ret_Factor;
	protected transient Listbox lb_Factura_Imp_Ret_Tasa;
	protected transient Doublebox txt_Factura_Imp_Ret_Importe;

	protected transient Groupbox gb_Factura_09_Info_Aduana;
	protected transient Textbox txt_Factura_Aduana_Pedimento;

	protected transient Groupbox gb_Factura_10_Predial;
	protected transient Textbox txt_Factura_Predial_Cuenta;

//	protected transient Groupbox gb_Factura_11_Compl_Part;
//	protected transient Textbox txt_Factura_parte_Clave;
//	protected transient Textbox txt_Factura_parte_Identi;
//	protected transient Textbox txt_Factura_parte_Cantidad;
//	protected transient Textbox txt_Factura_parte_Unidad;
//	protected transient Textbox txt_Factura_parte_Desc;
//	protected transient Textbox txt_Factura_parte_Unita;
//	protected transient Textbox txt_Factura_parte_Importe;

	protected transient Groupbox gb_Factura_12_Tot_Imp;
	protected transient Textbox txt_Factura_Total_Rete;
	protected transient Textbox txt_Factura_Total_Trasl;

	protected transient Groupbox gb_Factura_13_Imp_Rete;
	protected transient Textbox txt_Factura_13_Impuesto_Rete;
	protected transient Textbox txt_Factura_13_Importe_Rete;

	protected transient Groupbox gb_Factura_14_Imp_Traslado;
	protected transient Textbox txt_Factura_14_Impuesto_Traslado;
	protected transient Textbox txt_Factura_14_Factor_Traslado;
	protected transient Textbox txt_Factura_14_Tasa_Traslado;
	protected transient Textbox txt_Factura_14_Importe_Traslado;
	
	//Terminan las Variables para la Factura
	
	// inician Variables para Addenda TEAYLOR FARMS

	protected transient Groupbox gb_add_Farms;

	protected transient Groupbox gb_Add_Farms_00;
	protected transient Textbox txt_add_Farms_00_CURRENCY;

	protected transient Groupbox gb_Add_Farms_01;
	protected transient Textbox txt_add_Farms_01_SHIPPING_DATE;
	protected transient Textbox txt_add_Farms_01_SHIPPED_FROM;
	protected transient Textbox txt_add_Farms_01_FREIGHT_TERMS;

	protected transient Groupbox gb_Add_Farms_02;
	protected transient Textbox txt_add_Farms_02_BUYER_ID;

	protected transient Groupbox gb_Add_Farms_03;
	protected transient Textbox txt_add_Farms_03_PO;

	protected transient Groupbox gb_Add_Farms_04;
	protected transient Textbox txt_add_Farms_04_Remittance_Address;

	protected transient Groupbox gb_Add_Farms_05;
	protected transient Textbox txt_add_Farms_05_SHIPPED_TO_NAME;
	protected transient Textbox txt_add_Farms_05_SHIPPED_TO_ADDRESS;
	protected transient Textbox txt_add_Farms_05_SHIPPED_TO_CITY;
	protected transient Textbox txt_add_Farms_05_SHIPPED_ID_TAX_RFC;

	protected transient Groupbox gb_Add_Farms_06;
	protected transient Textbox txt_add_Farms_06_OUR_ORDER_No;

	protected transient Groupbox gb_Add_Farms_07;
	protected transient Textbox txt_add_Farms_07_SALESMAN;

	protected transient Groupbox gb_Add_Farms_08;
	protected transient Textbox txt_add_Farms_08_CARRIER;
	protected transient Textbox txt_add_Farms_08_LICENSE_No;

	protected transient Groupbox gb_Add_Farms_09;
	protected transient Textbox txt_add_Farms_09_WRITTEN_AMOUNT_ENGLISH;
	protected transient Textbox txt_add_Farms_09_WRITTEN_AMOUNT_SPANISH;

	protected transient Groupbox gb_Add_Farms_10;
	protected transient Textbox txt_add_Farms_10_TOTAL_QUANTITY;

	protected transient Groupbox gb_Add_Farms_11;
	protected transient Textbox txt_add_Farms_11_COMMENTS_LINE_1;

	protected transient Groupbox gb_Add_Farms_12;
	protected transient Textbox txt_add_Farms_12_COMMENTS_LINE_2;

	protected transient Groupbox gb_Add_Farms_13;
	protected transient Textbox txt_add_Farms_13_COMMENTS_LINE_3;

	protected transient Groupbox gb_Add_Farms_14;
	protected transient Textbox txt_add_Farms_14_COMMENTS_LINE_4;

	protected transient Groupbox gb_Add_Farms_15;
	protected transient Textbox txt_add_Farms_15_WEIGHT_CMTY;
	protected transient Textbox txt_add_Farms_15_WEIGHT_DSC;
	protected transient Textbox txt_add_Farms_15_WEIGHT_NET_WT;
	
	// Termina Variables para Addenda TEAYLOR FARMS
	
	//Inician Variables para Carta Porte (CP)
	protected transient Groupbox gb_Comple_CP;
	
	protected transient Groupbox gb_00_CP;
	protected transient Textbox txt_CatPort_00_Version;
	protected transient Listbox lb_CatPort_00_TranspInternac;
	protected transient Listbox lb_CatPort_00_EntradaSalidaMerc;
	protected transient Listbox lb_CatPort_00_ViaEntradaSalida;
	protected transient Textbox dbb_CatPort_00_TotalDistRec;
	
	protected transient Groupbox gb_01_CP;
	
	protected transient Listbox lb_CatPort_01_TipoEstacion;
	protected transient Textbox txt_CatPort_01_DistanciaRecorrida;
	
	protected transient Groupbox gb_02_CP;
	
	protected transient Textbox txt_CatPort_02_Ubi_Ori_IDOrigen;
	protected transient Textbox txt_CatPort_02_Ubi_Ori_RFCRemitente;
	protected transient Textbox txt_CatPort_02_Ubi_Ori_NombreRemitente;
	protected transient Textbox txt_CatPort_02_Ubi_Ori_NumRegIdTrib;
	protected transient Textbox txt_CatPort_02_Ubi_Ori_ResidenciaFiscal;
	protected transient Textbox txt_CatPort_02_Ubi_Ori_NumEstacion;
	protected transient Textbox txt_CatPort_02_Ubi_Ori_NombreEstacion;
	protected transient Listbox lb_CatPort_02_Ubi_Ori_NavegacionTrafico;
	protected transient Datebox dt_CatPort_02_Ubi_Ori_NFechaHoraSalida;
	
	protected transient Groupbox gb_04_4_CP;
	
	protected transient Textbox txt_CatPort_04_4_Ubi_Direc_Calle;
	protected transient Textbox txt_CatPort_04_4_Ubi_Direc_NumeroExterior;
	protected transient Textbox txt_CatPort_04_4_Ubi_Direc_NumeroInterior;
	protected transient Textbox txt_CatPort_04_4_Ubi_Direc_Colonia;
	protected transient Textbox txt_CatPort_04_4_Ubi_Direc_Localidad;
	protected transient Textbox txt_CatPort_04_4_Ubi_Direc_Referencia;
	protected transient Textbox txt_CatPort_04_4_Ubi_Direc_Municipio;
	protected transient Textbox txt_CatPort_04_4_Ubi_Direc_Estado;
	protected transient Textbox txt_CatPort_04_4_Ubi_Direc_Pais;
	protected transient Textbox txt_CatPort_04_4_Ubi_Direc_CodigoPostal;
	
	protected transient Groupbox gb_03_CP;
	
	protected transient Textbox txt_CatPort_03_Ubi_Dest_IDDestino;
	protected transient Textbox txt_CatPort_03_Ubi_Dest_RFCDestinatario;
	protected transient Textbox txt_CatPort_03_Ubi_Dest_NombreDestinatario;
	protected transient Textbox txt_CatPort_03_Ubi_Dest_NumRegIdTrib;
	protected transient Textbox txt_CatPort_03_Ubi_Dest_ResidenciaFiscal;
	protected transient Textbox txt_CatPort_03_Ubi_Dest_NumEstacion;
	protected transient Textbox txt_CatPort_03_Ubi_Dest_NombreEstacion;
	protected transient Listbox lb_CatPort_03_Ubi_Dest_NavegacionTrafico;
	protected transient Datebox dt_CatPort_03_Ubi_Dest_FechaHoraProgLlegada;
	
	protected transient Groupbox gb_04_CP;
	
	protected transient Textbox txt_CatPort_04_Ubi_Direc_Calle;
	protected transient Textbox txt_CatPort_04_Ubi_Direc_NumeroExterior;
	protected transient Textbox txt_CatPort_04_Ubi_Direc_NumeroInterior;
	protected transient Textbox txt_CatPort_04_Ubi_Direc_Colonia;
	protected transient Textbox txt_CatPort_04_Ubi_Direc_Localidad;
	protected transient Textbox txt_CatPort_04_Ubi_Direc_Referencia;
	protected transient Textbox txt_CatPort_04_Ubi_Direc_Municipio;
	protected transient Textbox txt_CatPort_04_Ubi_Direc_Estado;
	protected transient Textbox txt_CatPort_04_Ubi_Direc_Pais;
	protected transient Textbox txt_CatPort_04_Ubi_Direc_CodigoPostal;
	
	protected transient Groupbox gb_05_CP;
	
	protected transient Textbox txt_CatPort_05_Merca_Gral_PesoBrutoTotal;
	protected transient Textbox txt_CatPort_05_Merca_Gral_UnidadPeso;
	protected transient Textbox txt_CatPort_05_Merca_Gral_PesoNetoTotal;
	protected transient Textbox txt_CatPort_05_Merca_Gral_NumTotalMercancias;
	protected transient Textbox txt_CatPort_05_Merca_Gral_CargoPorTasacion;
	
	protected transient Groupbox gb_06_CP;
	
	protected transient Textbox txt_CatPort_06_Merca_BienesTransp;
	protected transient Textbox txt_CatPort_06_Merca_ClaveSTCC;
	protected transient Textbox txt_CatPort_06_Merca_Descripcion;
	protected transient Textbox txt_CatPort_06_Merca_Cantidad;
	protected transient Textbox txt_CatPort_06_Merca_ClaveUnidad;
	protected transient Textbox txt_CatPort_06_Merca_Unidad;
	protected transient Textbox txt_CatPort_06_Merca_Dimensiones;
	protected transient Textbox txt_CatPort_06_Merca_MaterialPeligroso;
	protected transient Textbox txt_CatPort_06_Merca_CveMaterialPeligroso;
	protected transient Textbox txt_CatPort_06_Merca_Embalaje;
	protected transient Textbox txt_CatPort_06_Merca_DescripEmbalaje;
	protected transient Textbox txt_CatPort_06_Merca_PesoEnKg;
	protected transient Textbox txt_CatPort_06_Merca_ValorMercancia;
	protected transient Textbox txt_CatPort_06_Merca_Moneda;
	protected transient Textbox txt_CatPort_06_Merca_FraccionArancelaria;
	protected transient Textbox txt_CatPort_06_Merca_UUIDComercioExt;
	
	protected transient Groupbox gb_07_CP;
	
	protected transient Textbox txt_CatPort_07_Merca_CantTrans_Cantidad;
	protected transient Textbox txt_CatPort_07_Merca_CantTrans_IDOrigen;
	protected transient Textbox txt_CatPort_07_Merca_CantTrans_IDDestino;
	protected transient Listbox lb_CatPort_07_Merca_CantTrans_CvesTransporte;
	
	protected transient Groupbox gb_08_CP;
	
	protected transient Textbox txt_CatPort_08_Merca_DetaMerc_UnidadPeso;
	protected transient Textbox txt_CatPort_08_Merca_DetaMerc_PesoBruto;
	protected transient Textbox txt_CatPort_08_Merca_DetaMerc_PesoNeto;
	protected transient Textbox txt_CatPort_08_Merca_DetaMerc_PesoTara;
	protected transient Textbox txt_CatPort_08_Merca_DetaMerc_NumPiezas;
	
	protected transient Groupbox gb_09_CP;
	
	protected transient Textbox txt_CatPort_09_Merca_AutoTransF_PermSCT;
	protected transient Textbox txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT;
	protected transient Textbox txt_CatPort_09_Merca_AutoTransF_NombreAseg;
	protected transient Textbox txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro;
	
	protected transient Groupbox gb_10_CP;
	
	protected transient Textbox txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular;
	protected transient Textbox txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM;
	protected transient Textbox txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM;
	
	protected transient Groupbox gb_11_CP;
	
	protected transient Textbox txt_CatPort_11_Merca_AutoTransF_Remolque_SubTipoRem;
	protected transient Textbox txt_CatPort_11_Merca_AutoTransF_Remolque_Placa;
	
	
	protected transient Groupbox gb_12_CP;
	
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_PermSCT;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NumPermisoSCT;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NombreAseg;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NumPolizaSeguro;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_Matricula;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NumeroOMI;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NombreEmbarc;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_TipoCarga;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NumCertITC;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_Eslora;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_Manga;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_Calado;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_LineaNaviera;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NumViaje;
	protected transient Textbox txt_CatPort_12_Merca_TransMaritimo_NumConocEmbarc;
		
	protected transient Groupbox gb_13_CP;
	
	protected transient Textbox txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor;
	protected transient Listbox lb_CatPort_13_Merca_TransMaritimo_Cont_TipoContenedor;
	protected transient Textbox txt_CatPort_13_Merca_TransMaritimo_Cont_NumPrecinto;
		
	protected transient Groupbox gb_14_CP;
	
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_PermSCT;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_NumPermisoSCT;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_MatriculaAeronave;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_NombreAseg;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_NumPolizaSeguro;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_NumeroGuia;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_LugarContrato;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_RFCTransportista;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_CodigoTransportista;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_NumRegIdTribTranspor;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalTranspor;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_NombreTransportista;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_RFCEmbarcador;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_NumRegIdTribEmbarc;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalEmbarc;
	protected transient Textbox txt_CatPort_14_Merca_TransAereo_NombreEmbarcador;
	
	protected transient Groupbox gb_15_CP;
	
	protected transient Listbox lb_CatPort_15_Merca_TransFerrov_TipoDeServicio;
	protected transient Textbox txt_CatPort_15_Merca_TransFerrov_NombreAseg;
	protected transient Textbox txt_CatPort_15_Merca_TransFerrov_NumPolizaSeguro;
	protected transient Textbox txt_CatPort_15_Merca_TransFerrov_Concesionario;
	
	protected transient Groupbox gb_16_CP;
	
	protected transient Textbox txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso;
	protected transient Textbox txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado;
	
	protected transient Groupbox gb_17_CP;
	
	protected transient Listbox lb_CatPort_17_Merca_TransFerrov_Carro_TipoCarro;
	protected transient Textbox txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro;
	protected transient Textbox txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro;
	protected transient Textbox txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro;
	
	protected transient Groupbox gb_18_CP;
	
	protected transient Listbox lb_CatPort_18_Merca_TransFerrov_Carro_Cont_TipoContenedor;
	protected transient Textbox txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio;
	protected transient Textbox txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia;
	
	protected transient Groupbox gb_19_CP;
	
	protected transient Listbox lb_CatPort_19_FigTrans_CveTransporte;
	
	protected transient Groupbox gb_20_CP;
	
	protected transient Textbox txt_CatPort_20_FigTrans_Oper_RFCOperador;
	protected transient Textbox txt_CatPort_20_FigTrans_Oper_NumLicencia;
	protected transient Textbox txt_CatPort_20_FigTrans_Oper_NombreOperador;
	protected transient Textbox txt_CatPort_20_FigTrans_Oper_NumRegIdTribOperador;
	protected transient Textbox txt_CatPort_20_FigTrans_Oper_ResidenciaFiscalOperador;
	
	protected transient Groupbox gb_21_CP;
	
	protected transient Textbox txt_CatPort_21_FigTrans_Oper_Domi_Calle;
	protected transient Textbox txt_CatPort_21_FigTrans_Oper_Domi_NumeroExterior;
	protected transient Textbox txt_CatPort_21_FigTrans_Oper_Domi_NumeroInterior;
	protected transient Textbox txt_CatPort_21_FigTrans_Oper_Domi_Colonia;
	protected transient Textbox txt_CatPort_21_FigTrans_Oper_Domi_Localidad;
	protected transient Textbox txt_CatPort_21_FigTrans_Oper_Domi_Referencia;
	protected transient Textbox txt_CatPort_21_FigTrans_Oper_Domi_Municipio;
	protected transient Textbox txt_CatPort_21_FigTrans_Oper_Domi_Estado;
	protected transient Textbox txt_CatPort_21_FigTrans_Oper_Domi_Pais;
	protected transient Textbox txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal;
	
	protected transient Groupbox gb_22_CP;
	
	protected transient Textbox txt_CatPort_22_FigTrans_Prop_RFCPropietario;
	protected transient Textbox txt_CatPort_22_FigTrans_Prop_NombrePropietario;
	protected transient Textbox txt_CatPort_22_FigTrans_Prop_NumRegIdTribPropietario;
	protected transient Textbox txt_CatPort_22_FigTrans_Prop_ResidenciaFiscalPropietario;
	
	protected transient Groupbox gb_23_CP;
	
	protected transient Textbox txt_CatPort_23_FigTrans_Prop_Domi_Calle;
	protected transient Textbox txt_CatPort_23_FigTrans_Prop_Domi_NumeroExterior;
	protected transient Textbox txt_CatPort_23_FigTrans_Prop_Domi_NumeroInterior;
	protected transient Textbox txt_CatPort_23_FigTrans_Prop_Domi_Colonia;
	protected transient Textbox txt_CatPort_23_FigTrans_Prop_Domi_Localidad;
	protected transient Textbox txt_CatPort_23_FigTrans_Prop_Domi_Referencia;
	protected transient Textbox txt_CatPort_23_FigTrans_Prop_Domi_Municipio;
	protected transient Textbox txt_CatPort_23_FigTrans_Prop_Domi_Estado;
	protected transient Textbox txt_CatPort_23_FigTrans_Prop_Domi_Pais;
	protected transient Textbox txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal;
	
	protected transient Groupbox gb_24_CP;
	
	protected transient Textbox txt_CatPort_24_FigTrans_Arr_RFCArrendatario;
	protected transient Textbox txt_CatPort_24_FigTrans_Arr_NombreArrendatario;
	protected transient Textbox txt_CatPort_24_FigTrans_Arr_NumRegIdTribArrendatario;
	protected transient Textbox txt_CatPort_24_FigTrans_Arr_ResidenciaFiscalArrendatario;
	
	protected transient Groupbox gb_25_CP;
	
	protected transient Textbox txt_CatPort_25_FigTrans_Arr_Domi_Calle;
	protected transient Textbox txt_CatPort_25_FigTrans_Arr_Domi__NumeroExterior;
	protected transient Textbox txt_CatPort_25_FigTrans_Arr_Domi__NumeroInterior;
	protected transient Textbox txt_CatPort_25_FigTrans_Arr_Domi__Colonia;
	protected transient Textbox txt_CatPort_25_FigTrans_Arr_Domi__Localidad;
	protected transient Textbox txt_CatPort_25_FigTrans_Arr_Domi__Referencia;
	protected transient Textbox txt_CatPort_25_FigTrans_Arr_Domi__Municipio;
	protected transient Textbox txt_CatPort_25_FigTrans_Arr_Domi__Estado;
	protected transient Textbox txt_CatPort_25_FigTrans_Arr_Domi__Pais;
	protected transient Textbox txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal;
	
	protected transient Groupbox gb_26_CP;
	
	protected transient Textbox txt_CatPort_26_FigTrans_Noti_RFCNotificado;
	protected transient Textbox txt_CatPort_26_FigTrans_Noti_NombreNotificado;
	protected transient Textbox txt_CatPort_26_FigTrans_Noti_NumRegIdTribNotificado;
	protected transient Textbox txt_CatPort_26_FigTrans_Noti_ResidenciaFiscalNotificado;
	
	protected transient Groupbox gb_27_CP;
	
	protected transient Textbox txt_CatPort_27_FigTrans_Noti_Domi_Calle;
	protected transient Textbox txt_CatPort_27_FigTrans_Noti_Domi__NumeroExterior;
	protected transient Textbox txt_CatPort_27_FigTrans_Noti_Domi__NumeroInterior;
	protected transient Textbox txt_CatPort_27_FigTrans_Noti_Domi__Colonia;
	protected transient Textbox txt_CatPort_27_FigTrans_Noti_Domi__Localidad;
	protected transient Textbox txt_CatPort_27_FigTrans_Noti_Domi__Referencia;
	protected transient Textbox txt_CatPort_27_FigTrans_Noti_Domi__Municipio;
	protected transient Textbox txt_CatPort_27_FigTrans_Noti_Domi__Estado;
	protected transient Textbox txt_CatPort_27_FigTrans_Noti_Domi__Pais;
	protected transient Textbox txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal;
	
	// Termina Variables para Carta Porte (CP)
	

	//Inician Variables para Complemento de Pagos (CRP)

	protected transient Groupbox gb_Comple_Pagos;

	protected transient Groupbox gb_01_Pagos;
	protected transient Datebox dt_Pago_01_FechaPago;
	protected transient Listbox lb_Pago_01_FormaDePagoP;
	protected transient Listbox lb_Pago_01_MonedaP;
	protected transient Textbox txt_Pago_01_TipoCambioP;
	protected transient Textbox txt_Pago_01_Monto;
	protected transient Textbox txt_Pago_01_NumOperacion;
	protected transient Textbox txt_Pago_01_RfcEmisorCtaOrd;
	protected transient Textbox txt_Pago_01_NomBancoOrdExt;
	protected transient Textbox txt_Pago_01_CtaOrdenante;
	protected transient Textbox txt_Pago_01_RfcEmisorCtaBen;
	protected transient Textbox txt_Pago_01_CtaBeneficiario;
	protected transient Textbox txt_Pago_01_TipoCadPago;
	protected transient Textbox txt_Pago_01_CertPago;
	protected transient Textbox txt_Pago_01_CadPago;
	protected transient Textbox txt_Pago_01_SelloPago;

	protected transient Groupbox gb_02_Pagos;
	protected transient Textbox txt_Pago_02_IdDocumento;
	protected transient Textbox txt_Pago_02_Serie;
	protected transient Textbox txt_Pago_02_Folio;
	protected transient Listbox lb_Pago_02_MonedaDR;
	protected transient Textbox txt_Pago_02_TipoCambioDR;
	protected transient Listbox lb_Pago_02_MetodoDePagoDR;
	protected transient Textbox txt_Pago_02_NumParcialidad;
	protected transient Textbox txt_Pago_02_ImpSaldoAnt	;
	protected transient Textbox txt_Pago_02_ImpPagado;
	protected transient Textbox txt_Pago_02_ImpSaldoInsoluto;

//	protected transient Groupbox gb_03_Pagos;
//	protected transient Textbox txt_Pago_03_TotalImpuestosRetenidos;
//	protected transient Textbox txt_Pago_03_TotalImpuestosTrasladados;
//
//	protected transient Groupbox gb_04_Pagos;
//	protected transient Textbox txt_Pago_04_Impuesto;
//	protected transient Textbox txt_Pago_04_Importe;
//
//
//	protected transient Groupbox gb_05_Pagos;
//	protected transient Textbox txt_Pago_05_Impuesto;
//	protected transient Textbox txt_Pago_05_TipoFactor;
//	protected transient Textbox txt_Pago_05_TasaOCuota;
//	protected transient Textbox txt_Pago_05_Importe;
	
	//Termina Variables para Complemento de Pagos (CRP)
	
	//Inician Variables para Complemento Comercio Exterior

	
	protected transient Groupbox gb_Comple_Comer_Ext;

	protected transient Groupbox gb_Comer_Exte_00;
	protected transient Textbox txt_Ext_00_Version;
	protected transient Listbox lb_Ext_00_MotivoTraslado;
	protected transient Textbox txt_Ext_00_TipoOperacion;
	protected transient Textbox txt_Ext_00_ClaveDePedimento;
	protected transient Listbox lb_Ext_00_CertificadoOrigen;
	protected transient Textbox txt_Ext_00_NumCertificadoOrigen;
	protected transient Textbox txt_Ext_00_NumeroExportadorConfiable;
	protected transient Textbox txt_Ext_00_Incoterm;
	protected transient Textbox txt_Ext_00_Subdivision;
	protected transient Textbox txt_Ext_00_Observaciones;
	protected transient Textbox txt_Ext_00_TipoCambioUSD;
	protected transient Textbox txt_Ext_00_TotalUSD;

	protected transient Groupbox gb_Comer_Exte_01;
	protected transient Textbox txt_Ext_01_Curp;



	protected transient Groupbox gb_Comer_Exte_02;
	protected transient Textbox txt_Ext_02_Calle;	
	protected transient Textbox txt_Ext_02_NumeroExterior;
	protected transient Textbox txt_Ext_02_NumeroInterior;
	protected transient Textbox txt_Ext_02_Colonia;
	protected transient Textbox txt_Ext_02_Localidad;
	protected transient Textbox txt_Ext_02_Referencia;
	protected transient Textbox txt_Ext_02_Municipio;
	protected transient Textbox txt_Ext_02_Estado;
	protected transient Textbox txt_Ext_02_Pais;
	protected transient Textbox txt_Ext_02_CodigoPostal;


	protected transient Groupbox gb_Comer_Exte_03;
	protected transient Textbox txt_Ext_03_NumRegIdTrib;
	protected transient Textbox txt_Ext_03_ResidenciaFiscal;


	protected transient Groupbox gb_Comer_Exte_04;
	protected transient Textbox txt_Ext_04_NumRegIdTrib;


	protected transient Groupbox gb_Comer_Exte_06;
	protected transient Textbox txt_Ext_06_Calle;
	protected transient Textbox txt_Ext_06_NumeroExterior;
	protected transient Textbox txt_Ext_06_NumeroInterior;
	protected transient Textbox txt_Ext_06_Colonia;
	protected transient Textbox txt_Ext_06_Localidad;
	protected transient Textbox txt_Ext_06_Referencia;
	protected transient Textbox txt_Ext_06_Municipio;
	protected transient Textbox txt_Ext_06_Estado;
	protected transient Textbox txt_Ext_06_Pais;
	protected transient Textbox txt_Ext_06_CodigoPostal;


	protected transient Groupbox gb_Comer_Exte_07;
	protected transient Textbox txt_Ext_07_NumRegIdTrib;
	protected transient Textbox txt_Ext_07_Nombre;

	protected transient Groupbox gb_Comer_Exte_08;
	protected transient Textbox txt_Ext_08_Calle;
	protected transient Textbox txt_Ext_08_NumeroExterior;
	protected transient Textbox txt_Ext_08_NumeroInterior;
	protected transient Textbox txt_Ext_08_Colonia;
	protected transient Textbox txt_Ext_08_Localidad;
	protected transient Textbox txt_Ext_08_Referencia;
	protected transient Textbox txt_Ext_08_Municipio;
	protected transient Textbox txt_Ext_08_Estado;
	protected transient Textbox txt_Ext_08_Pais;
	protected transient Textbox txt_Ext_08_CodigoPostal;


	protected transient Groupbox gb_Comer_Exte_09;
	protected transient Textbox txt_Ext_09_NoIdentificacion;
	protected transient Textbox txt_Ext_09_FraccionArancelaria;
	protected transient Textbox txt_Ext_09_CantidadAduana;
	protected transient Textbox txt_Ext_09_UnidadAduana;
	protected transient Textbox txt_Ext_09_ValorUnitarioAduana;
	protected transient Textbox txt_Ext_09_ValorDolares;


	protected transient Groupbox gb_Comer_Exte_10;
	protected transient Textbox txt_Ext_10_Marca;
	protected transient Textbox txt_Ext_10_Modelo;
	protected transient Textbox txt_Ext_10_SubModelo;
	protected transient Textbox txt_Ext_10_NumeroSerie;

	//Terminan Variables para Complemento Comercio Exterior
	
	//VAriables Lista
	protected transient Label itemCount;
	protected transient Label itemRelaCount;
	protected transient Listbox itemList;
	protected transient Listbox itemRelaList;
	protected transient Label itemCountTras;
	protected transient Listbox itemListTras;
	
	protected transient Label itemCRPCount;
	protected transient Listbox itemCRPList;
	
	protected transient Label itemFarmsCount;
	protected transient Listbox itemFarmsList;
	
	protected transient Label label_TipoCambioP;
	
	
	
	
	

	// panel account details
//	protected transient Textbox txt_UserName; // autowired
//	protected transient Textbox txt_RFC; // autowired
//	protected transient Textbox txt_Proveedor; // autowired
	protected transient Textbox txt_Proveedor; // autowired
	protected transient Textbox txt_Documento1; // autowired
	protected transient Textbox txt_Documento2; // autowired
	protected transient Textbox txt_Documento3; // autowired
	protected transient Datebox bd_FechaEntrega; // autowired
	protected transient Datebox bd_FechaMax; // autowired
	protected transient Listbox listBoxDetalleFactura; // autowired
	protected transient Textbox txt_Autorizador; // autowired
	protected transient Listbox lb_Estatus_Orden; // autowired
	protected transient Textbox txt_Autorizador_Area; // autowired
	protected transient Textbox tb_Identi_OC; // autowired
	
//	protected transient Textbox tb_bancoDialog; // autowired
//	protected transient Textbox tb_DocRPDialog; // autowired
	
	protected transient Textbox txt_CorreoProv;
	protected transient Textbox txt_CorreoAuto;
	protected transient Textbox txt_CorreoAutoArea;
	protected transient Textbox tb_Rechazo;
	
	
	
	// Detalle
	protected transient Textbox tb_TotalLetraFactura;
//	protected transient Listbox listBoxDetalleFactura;
	protected transient Textbox tb_SubtotalFactura;
	protected transient Textbox tb_IvaFactura;
	protected transient Textbox tb_RetIvaFactura;
	protected transient Textbox tb_RetISRFactura;
	// protected transient Textbox tb_TipoCFactura;
	protected transient Textbox tb_TotalFactura;
	protected transient Doublebox db_TipoCambio;
	protected transient Listbox cb_Moneda;
	protected transient Listbox cb_Iva;
	protected transient Listbox cb_tasa;
	protected transient Listbox lb_Cuentas;
	protected transient Listbox lb_Ordenes;
	protected transient Listbox lb_cc;
	protected transient Listbox lb_FormaPagoFactDialog;
	protected transient Listbox lb_Departamento;
	protected transient Listbox lb_MetodoPagoFactDialog;
	protected transient Listbox lb_UsoDialog;
	protected transient Listbox lb_unidad;
	
	
	protected transient Textbox tb_ObservacionesFactura;
	
	protected transient Row id_row_Autoriza_area;
	protected transient Row row_ordenes;
	protected transient Row row_Depa;
	
	
	
	protected transient Row row_Rechazo;
	protected transient Radiogroup Radiogroup_autorizaArea;
	protected transient Radiogroup Radiogroup_autoriza;
	protected transient Radio Radio_autoriza;
	protected transient Radio Radio_no_autoriza;
	
	protected transient Radio Radio_autoriza_Area;
	protected transient Radio Radio_no_autoriza_Area;
	
	protected transient Radiogroup GoodRadiogroup;
	protected transient Radio Goog_ok;
	protected transient Radio Good_Dont;
	protected transient Label label_Good_Rechazo;
	protected transient Textbox tb_Good_Rechazo;
	
	protected transient Row row_ck_Valida;
//	protected transient Checkbox check_Valida;
	
	
	List detalle = new ArrayList();
	List detalleRelacion = new ArrayList();
//	List<TbProductoComprobante> listaDetalleFactura = new ArrayList<TbProductoComprobante>();
	
	int index_Rela = 0;
	int index_Concepto = 0;
	
	
	
	protected transient Label label_tipoCambio; // autowired
//	protected transient Doublebox db_TipoCambio;	
//	protected transient Checkbox check_admin;
	
//	protected transient Label label_Autorizo; // autowired
//	protected transient Checkbox chek_autoriza; // autowired
//	protected transient Label label_AutorizoArea; // autowired
	protected transient Checkbox ch_Retenciones; // autowired
	
// panel security token, SORRY logic it's internally

	// panel granted roles
	protected transient Listbox listBoxDetails_UserRoles; // autowired
	protected transient Listheader listheader_UserDialog_UserRoleId; // autowired
	protected transient Listheader listheader_UserDialog_UserRoleShortDescription; // autowired

	// overhanded vars per params
	private transient Listbox listBoxOrden; // overhanded
//	private transient TbUsuario l_obUser; // overhanded
//	private transient TbDetalleComprobante l_obComprobanteDetalle; // overhanded
	
//	private transient TbPersona l_obPersona; // overhanded
//	private transient TbCorporativo tbcorp;
//	private transient TbDireccion l_obDireccion; // overhanded
//	private transient TbEntidadFederativa l_obEntidadFederativa; // overhanded

	// old value vars for edit mode. that we can check if something
	// on the values are edited since the last init.
	private transient String oldVar_usrLoginname;
	private transient String oldVar_usrPassword;
	private transient String oldVar_usrPasswordRetype;
	private transient String oldVar_usrFirstname;
	private transient String oldVar_usrLastname;
	private transient String oldVar_usrEmail;
	private transient boolean oldVar_usrEnabled;
	private transient String oldVar_aMaterno;
	private transient String oldVar_CURP;
	private transient String oldVar_razonSocial;
	private transient String oldVar_RFC;
	private transient String oldVar_calle;
	private transient String oldVar_numeroExt;
	private transient String oldVar_numeroInt;
	private transient String oldVar_colonia;
	private transient String oldVar_CP;
	private transient String oldVar_localidad;
	private transient String oldVar_ciudad;
	private transient String oldVar_delMun;
	private transient String oldVar_tel1;
	private transient String oldVar_tel2;
	private transient String oldVar_fax;
	private transient Listitem oldVar_entidad;

	private transient boolean validationOn;

	// Button controller for the CRUD buttons
	private transient final String btnCtroller_ClassPrefix = "button_UserDialog_";
	private transient ButtonStatusCtrl btnCtrl;
	protected transient Button btnNew; // autowired
	protected transient Button btnEdit; // autowired
	protected transient Button btnDelete; // autowired
	protected transient Button btn_Guardar; // autowired
	protected transient Button btn_Cerrar; // autowired
	protected transient Button btn_Actualizar; // autowired
	protected transient Button btn_Modificar; // autowired
	protected transient Button btn_Estaciones; // autowired
	protected transient Button btn_ClaveUnidadPeso; // autowired
	protected transient Button btn_Deta_ClaveUnidadPeso; // autowired
	protected transient Button btn_Plantilla; // autowired
	
	
	
	
	protected transient Button bt_upload1; // autowired
	protected transient Button bt_upload2; // autowired
	protected transient Button bt_upload3; // autowired
	
	protected transient Button button_PrintCFD;
	
	protected transient Button btn_download_Cotiza;
	protected transient Button btn_download_2;
	protected transient Button btn_download_3;
	
//	protected transient Button btnAgregaProd; // autowired
	protected transient Button btnAgregaProdLista; // autowired
	
	protected transient Button btnBorraProd; // autowired
	protected transient Button btn_Autorizador; // autowired
	protected transient Button btn_Autorizador_Area; // autowired
	
	protected transient Button btn_Guarda_Auto; // autowired
	protected transient Button btn_Guarda_Auto_Area; // autowired
	protected transient Button btn_Guarda_ValidaOC;// autowired
	
	
	
	private Media mediaArchivo1;
	private Media mediaArchivo2;
	private Media mediaArchivo3;
	
	String Ruta_mediaArchivo1 = "";
	String Ruta_mediaArchivo2 = "";
	String Ruta_mediaArchivo3 = "";
	
//	TbDetalleComprobante Memory_anDetaCompro = new TbDetalleComprobante();

	
	protected transient Intbox contadorDetalles;
	

	// checkRights
	protected transient Button btnHelp; // autowired
	protected transient Tabpanel tabpanel_UserDialog_Details; // autowired
	
//	private transient UserService userService;
	
//	private List<TbRol> roles= new ArrayList<TbRol>();
	UsuarioService UserServ = new UsuarioService();
//	VentaService ventaserv = new VentaService();
//	ComprobanteService comp_Serv = new ComprobanteService();
	Codificador codi = new Codificador(); 
	String Cifrado = "";
	String PWD = "";
	int id_Usuario;
	int id_Partner;
	int IdUser = 0;
	int IdPartner = 0; 
	
	protected transient Textbox  IdProveedor;
	protected transient Textbox  IdUsuarioAuto;
	protected transient Textbox  IdUsuarioAutoArea;
	protected transient Label 	 label_RetIva;
//	protected transient Combobox ib_clave;
//	protected transient Textbox  tb_RetIvaFactura;
	
	
	
//	int IdProveedor = 0; /
//	int IdUsuarioAuto = 0; 
//	int IdUsuarioAutoArea = 0; 
	
	Integer idRFCs = null;
	Integer idUsuarios = null;
	Integer idOrden = null;
	String TasaIVa = "";
	
	UsuarioService usuarioService = new UsuarioService();
//	VentaService ventaServ = new VentaService();
//	TbDetalleComprobante MemoryDetalleComp = new TbDetalleComprobante(); 
	
//	int id_corporativo;
//	String NameRol = "";
	
	int id_corporativo;
	String NameRol = "";
	int id_user;
	int id_pr_rol;
//	int id_Corporativo;
	int modal = 0;
	int idproducto = 0;
	String Correo_Creador = "";
	
	double tot_concepto = 0;
	double tot_descuento = 0;
	double tot_subtotal = 0;
	double tot_total = 0;
	double tot_traslado = 0;
	double tot_retenciones = 0;
	
	UsuarioService userserv = new UsuarioService();
//	TbPerfilTbUsuario perfil_user = new TbPerfilTbUsuario();
//	TbCorporativo getcorp = new TbCorporativo();
	
//	PagedListWrapper<TbUsuario> wrappper = new PagedListWrapper<TbUsuario>();
	SimpleSession s = (SimpleSession) Executions.getCurrent().getDesktop().getSession();
    HttpSession session = (HttpSession) s.getNativeSession();
	String Usuario = ((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
	
	String Url_Sistema = "";
//	String In = "";
	String Email_Sistema = "";
	
	CfdUtil cfdUtil = new CfdUtil();
	
	public static String pathIn = "";
	public static String pathOut = "";
	public static String keyId = "";
	
//	private SessionBean sBean = (SessionBean) Sessions.getCurrent().getWebApp().getAttribute(SecurityContextHolder.getContext().getAuthentication().getName());

	/**
	 * default constructor.<br>
	 */
	public OrdenManufacturaDialogCtrl() {
		super();
		
		Locale favoriteLocale = org.zkoss.util.Locales.getLocale ("en_US");
		session.setAttribute (org.zkoss.web.Attributes.PREFERRED_LOCALE, favoriteLocale);
		org.zkoss.util.Locales.setThreadLocal (org.zkoss.util.Locales.getLocale ("en_US"));
		

		if (logger.isDebugEnabled()) {
			logger.debug("--> super()");
		}
	}

	/**
	 * Before binding the data and calling the dialog window we check, if the
	 * zul-file is called with a parameter for a selected user object in a Map.
	 * 
	 * @param event
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void onCreate$ordenCompraDialogWindow(Event event) throws Exception {
		
		Locale.setDefault(new Locale("en", "US"));
		
//		super.doAfterCompose (ordenCompraDialogWindow);
		
		Properties email_props = new Properties();
		InputStream is = null;
		
		try {
			
		    is = getClass().getResourceAsStream("/email.properties");
		    email_props.load(is);
		    
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
//		ComponentsCtrl.applyForward(txt_Factura_Conceptos_Importe, "onFocus=onFocusUnitario");
//		ComponentsCtrl.applyForward(txt_Factura_Conceptos_Importe, "onBlur=onBlurUnitario");
////		if (tipoDocequals("COTIZACION" )){
////			ib_cantidad.setReadonly(false);
//		ComponentsCtrl.applyForward(txt_Factura_Conceptos_Importe, "onFocus=onFocusCantidad");
//		ComponentsCtrl.applyForward(txt_Factura_Conceptos_Importe, "onBlur=onBlurCantidad");
		
		Url_Sistema = email_props.getProperty("UrlSistema");
		Email_Sistema = email_props.getProperty("Email");
		
//		l_obUser = usuarioService.getUserByLoginname(Usuario);
		
//		System.out.println(l_obUser.getIdCorporativo());
		
//		id_corporativo = ((Integer) session.getAttribute("idCorp")).intValue();
//		id_corporativo = l_obUser.getIdCorporativo();
		
		btn_Modificar.setVisible(false);
		
//		perfil_user = usuarioService.getPerfilUser(l_obUser.getIdUsuario(), id_corporativo);
//		getcorp = ventaserv.getTbCorporativo(id_corporativo);
//		gb_Factura.setOpen(true);
//		
		parametrosIni();
//		
//		System.out.println("Valor del RFC: " + getcorp.getRfc());
//		System.out.println("true o false del RFC: " + !getcorp.getRfc().equals("TFM011030DE8") );
//		
//		System.out.println("Valor del RFC: " + getcorp.getRfc());
//		System.out.println("true o false del RFC: " + !getcorp.getRfc().equals("TFB180413878") );
//		
////		gb_add_Farms.setVisible(true);
//		
//		if(getcorp.getRfc().startsWith("TFM011030DE8")){
//			gb_add_Farms.setVisible(true);
//		}
//		
//		
//		if(getcorp.getRfc().startsWith("TFB180413878")){
//			gb_add_Farms.setVisible(true);
//		}
		
		
		
		
//		CrearArchivo();

		
		
		
//		id_corporativo =  l_obUser.getIdCorporativo();
		

//		id_row_Autoriza_area.setVisible(false);
//		row_ordenes.setVisible(false);
//		row_Depa.setVisible(false);
//		row_Rechazo.setVisible(false);
//		Radiogroup_autorizaArea.setVisible(false);
//		Radiogroup_autoriza.setVisible(false);
//		
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		txt_Documento1.setDisabled(true);
//		txt_Documento2.setDisabled(true);
//		txt_Documento3.setDisabled(true);
//		
//		lb_Estatus_Orden.setDisabled(true);
//		
//		contadorDetalles = new Intbox();
//		
//		contadorDetalles.setValue(0);
//		
//		row_ck_Valida.setVisible(false);
		
//		check_Valida.setLabel("Valida");
		
		/* set comps cisible dependent of the users rights */
		//doCheckRights();
//		txt_CatPort_02_Ubi_Ori_NumEstacion.setDisabled(false);
//		btnDelete.setVisible(false);
		// create the Button Controller. Disable not used buttons during working
//		btnCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnSave, btnClose);
//		TbComprobantes comprobante = new TbComprobantes();
//		listBoxOrden = new Listbox();

		// get the params map that are overhanded by creation.
		Map<String, Object> args = getCreationArgsMap(event);
		
		
//		map.put("aOrden", aOrden);

//		if (args.containsKey("aOrden")){
//			comprobante = (TbComprobantes) args.get("aOrden");
//		} else {
//			comprobante= null;		
//		}
		
//		if (args.containsKey("detaCompro")) {
//			setL_obComprobanteDetalle((TbDetalleComprobante) args.get("detaCompro"));
//		} else {
//			setL_obComprobanteDetalle(null);		
//		}

		// we get the listBox Object for the users list. So we have access
		// to it and can synchronize the shown data when we do insert, edit or
		// delete users here.
		if (args.containsKey("listBoxOrden")) {
			listBoxOrden = (Listbox) args.get("listBoxOrden");
		} else {
			listBoxOrden = null;
		}
		
		if (args.containsKey("modal"))
			modal = (Integer) args.get("modal");
		
		if (args.containsKey("NameRol"))
			NameRol = (String) args.get("NameRol");
		
		if(NameRol.equals("Rol_Autorizador")){
			
		
//			if(modal == 1){
//				if(l_obUser.getAutoriza()==1){
//					Radiogroup_autorizaArea.setVisible(false);
//					Radiogroup_autoriza.setVisible(true);
//				}
//				if(l_obUser.getAutorizaArea()==1){
//					Radiogroup_autoriza.setVisible(false);
//					Radiogroup_autorizaArea.setVisible(true);
//				}
//				btnAgregaProdLista.setDisabled(true);
//				btnBorraProd.setDisabled(true);
//				ch_Retenciones.setDisabled(true);
//				
////				if(comprobante.getRetOrden() != null){
////					ch_Retenciones.setChecked(true);
////				}else{
////					ch_Retenciones.setChecked(false);
////				}
//				
//			}
			
			
			
		}


		
		
//		if(!tb_TotalFactura.getValue().equals("") && tb_TotalFactura.getValue() != null){
//			
//			
//			double doble = Double.parseDouble(tb_TotalFactura.getValue());
////			if(doble > getcorp.getCantidadMax()){
////				id_row_Autoriza_area.setVisible(true);
////			}else{
////				id_row_Autoriza_area.setVisible(false);
////			}
//				
//		}
		
		
//		cb_Iva.setModel(new ListModelList(ventaServ.getIVA(id_corporativo)));
//		cb_Iva.setItemRenderer(new ComboFactoryModelItemRenderer());
//		
//		lb_Cuentas.setModel(new ListModelList(ventaServ.getCuenta(id_corporativo)));
//		lb_Cuentas.setItemRenderer(new ComboFactoryModelItemRenderer());
//		
//		lb_cc.setModel(new ListModelList(ventaServ.getCc(id_corporativo)));
//		lb_cc.setItemRenderer(new ComboFactoryModelItemRenderer());
//		
//		cb_Moneda.setModel(new ListModelList(ventaServ.getMonedaManual()));
//		cb_Moneda.setItemRenderer(new ComboFactoryModelItemRenderer());
//		
////		lb_UsoCFDI = new Listbox();
//		
//		lb_UsoDialog.setModel(new ListModelList(ventaServ.getUsoCFDI()));
//		lb_UsoDialog.setItemRenderer(new ComboFactoryModelItemRenderer());
//		
//		lb_FormaPagoFactDialog.setModel(new ListModelList(ventaServ.getFormaOc()));
//		lb_FormaPagoFactDialog.setItemRenderer(new ComboFactoryModelItemRenderer());
//		
//		lb_MetodoPagoFactDialog.setModel(new ListModelList(ventaServ.getMetodoOc()));
//		lb_MetodoPagoFactDialog.setItemRenderer(new ComboFactoryModelItemRenderer());
//		
//		
//		lb_Pago_01_FormaDePagoP.setModel(new ListModelList(ventaServ.getFormaOc()));
//		lb_Pago_01_FormaDePagoP.setItemRenderer(new ComboFactoryModelItemRenderer());
//		
//		lb_Pago_01_MonedaP.setModel(new ListModelList(ventaServ.getMonedaManualPago()));
//		lb_Pago_01_MonedaP.setItemRenderer(new ComboFactoryModelItemRenderer());
//		
//		lb_Pago_02_MonedaDR.setModel(new ListModelList(ventaServ.getMonedaManualDR()));
//		lb_Pago_02_MonedaDR.setItemRenderer(new ComboFactoryModelItemRenderer());
//		
//		lb_Pago_02_MetodoDePagoDR.setModel(new ListModelList(ventaServ.getMetodoOc()));
//		lb_Pago_02_MetodoDePagoDR.setItemRenderer(new ComboFactoryModelItemRenderer());
		
//		ListModel dictModel = new SimpleListModel(ventaServ.getEstaciones());
//		Cb_CatPort_02_Ubi_Ori_NumEstacion.setModel(dictModel);
//		Cb_CatPort_02_Ubi_Ori_NumEstacion.setAutodrop(true);
//		Cb_CatPort_02_Ubi_Ori_NumEstacion.setAutocomplete(true);
		
				
//		System.out.println("Valor de comprobante: "+ comprobante.getIdent_oc() + " - " + comprobante.getNuautorizaArea());
		
		// set Field Properties
		doSetFieldProperties();
//		System.out.println("getL_obComprobanteDetalle(): "+ comprobante.getBanco());

//		doShowDialog(comprobante,modal);
	}
	
	public void ValidaArchivo() {
		
	}
	
	 public void CrearArchivo() {
//	    public static void main(String ars[]){
		 
		 SignCFD();
			
		 String Tipo_Comprobante = "";
		 
//		 SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-mm-dd"); 
//		 
//		 String FechaCompro = objSDF.format(dt_Factura_Fecha.getValue());
//		 System.out.println("------>>> "+ FechaCompro + " <----------> "+dt_Factura_Fecha.getValue());
		 
		 
	        try {
	            PrintWriter writer = new PrintWriter(pathIn+txt_Factura_Emi_RFC.getValue()+"_"+txt_Factura_Serie.getValue()+txt_Factura_Folio.getValue()+".XML", "UTF-8");
	            
	            Listitem item_Met_Pag = lb_MetodoPagoFactDialog.getSelectedItem();
	            ListModelList lml_Met_Pag = (ListModelList) lb_MetodoPagoFactDialog.getListModel();
        		ComboFactory comboFactory_Met_Pag = (ComboFactory) lml_Met_Pag.get(item_Met_Pag.getIndex());
        		
	            System.out.println(comboFactory_Met_Pag.getNombre());
	            
	            String[] LisProduct_MP = comboFactory_Met_Pag.getNombre().split("-");
			    
	            String Metodo_Pago = "";
				   
				for (int i = 0; i < LisProduct_MP.length; i++) {
							
					Metodo_Pago = LisProduct_MP[0];
					System.out.println(LisProduct_MP[0]); 
				}
	            
	            Listitem item_For_Pag = lb_FormaPagoFactDialog.getSelectedItem();
	            ListModelList lml_For_Pag = (ListModelList) lb_FormaPagoFactDialog.getListModel();
        		ComboFactory comboFactory_For_Pag = (ComboFactory) lml_For_Pag.get(item_For_Pag.getIndex());
        		
        		String[] LisProductF = comboFactory_For_Pag.getNombre().split("-");
        		
        		 String Forma_Pago = "";
				   
 				for (int i = 0; i < LisProductF.length; i++) {
 							
 					Forma_Pago = LisProductF[0];
 					System.out.println(LisProductF[0]); 
 				}
 				
 				if(lb_Factura_Tipo_Compro.getSelectedItem().getLabel().equals("Ingreso")){
 					Tipo_Comprobante = "I";
 				}
				if(lb_Factura_Tipo_Compro.getSelectedItem().getLabel().equals("Egreso")){
					Tipo_Comprobante = "E";				
				 	}
				if(lb_Factura_Tipo_Compro.getSelectedItem().getLabel().equals("Traslado")){
					Tipo_Comprobante = "T";
					}
				if(lb_Factura_Tipo_Compro.getSelectedItem().getLabel().equals("Pago")){
					Tipo_Comprobante = "P";
					}
				
				System.out.println("1 ---------------->  "+txt_Factura_verzion.getValue() 		);
				System.out.println("2 ---------------->  "+txt_Factura_Serie.getValue()         );
				System.out.println("3 ---------------->  "+txt_Factura_Folio.getValue()         );
				System.out.println("4 ---------------->  "+dt_Factura_Fecha.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T")              );
				System.out.println("5 ---------------->  "+Forma_Pago.trim()                    );
				System.out.println("6 ---------------->  "+txt_Factura_Condi_Pago.getValue()    );
				System.out.println("7 ---------------->  "+txt_Factura_Subtotal.getValue()      );
				System.out.println("8 ---------------->  "+txt_Factura_Descuento.getValue()     );
				System.out.println("9 ---------------->  "+cb_Moneda.getSelectedItem().getLabel()        );
				System.out.println("10 ---------------->  "+txt_Factura_Tipo_Cambio.getValue()   );
				System.out.println("11 ---------------->  "+txt_Factura_Total.getValue()         );
				System.out.println("12 ---------------->  "+Tipo_Comprobante                     );
				System.out.println("13 ---------------->  "+Metodo_Pago.trim()                   );
				System.out.println("14 ---------------->  "+txt_Factura_Lugar_Expedi.getValue()  );
				System.out.println("15 ---------------->  "+txt_Factura_Confirma.getValue()      );
				
				String NameSpaceInstance = "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
				String cfd3 = "xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\"";
				String ShemaLocation = "xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd";
				String Termina_ShemaLocation = "\"";
				String NameSpaceCartaPorte = "xmlns:cartaporte20=\"http://www.sat.gob.mx/CartaPorte20\"";
				String NameSpacePagos = "xmlns:pago10=\"http://www.sat.gob.mx/Pagos\"";
				String ShemaLocationCartaPorte ="http://www.sat.gob.mx/CartaPorte20 http://www.sat.gob.mx/sitio_internet/cfd/CartaPorte/CartaPorte20.xsd";
				String ShemaLocationPagos ="http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd";
				String ShemaTipoComprobante = "";
				
				
				String CadenaLinea01 = " Version=\""+txt_Factura_verzion.getValue()+"\"";
				
				if(!txt_Factura_Serie.getValue().equals("")){
					CadenaLinea01 = CadenaLinea01 + " Serie=\""+txt_Factura_Serie.getValue()+"\"";
				}
				if(!txt_Factura_Folio.getValue().equals("")){
					CadenaLinea01 = CadenaLinea01 + " Folio=\""+txt_Factura_Folio.getValue()+"\"";
				}
				if(!dt_Factura_Fecha.getText().equals("")){
					CadenaLinea01 = CadenaLinea01 + " Fecha=\""+dt_Factura_Fecha.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T") +"\"";
				}
				
				CadenaLinea01 = CadenaLinea01 + " Sello=\"\"";
				CadenaLinea01 = CadenaLinea01 + " NoCertificado=\"00000000000000000000\"";
				CadenaLinea01 = CadenaLinea01 + " Certificado=\"\"";
				
				
				if(!txt_Factura_Subtotal.getValue().equals("")){
					CadenaLinea01 = CadenaLinea01 + " SubTotal=\""+txt_Factura_Subtotal.getValue() +"\"";
				}
				
				if(!txt_Factura_Descuento.getValue().equals("")){
					CadenaLinea01 = CadenaLinea01 + " Descuento=\""+txt_Factura_Descuento.getValue() +"\"";
				}
								
				if(!cb_Moneda.getSelectedItem().getLabel().equals("")){
					CadenaLinea01 = CadenaLinea01 + " Moneda=\""+cb_Moneda.getSelectedItem().getLabel() +"\"";
				}
				
				if(!txt_Factura_Tipo_Cambio.getValue().equals("")){
					CadenaLinea01 = CadenaLinea01 + " TipoCambio=\""+txt_Factura_Tipo_Cambio.getValue() +"\"";
				}
				
				if(!txt_Factura_Total.getValue().equals("")){
					CadenaLinea01 = CadenaLinea01 + " Total=\""+txt_Factura_Total.getValue() +"\"";
				}
				
				Listitem itemTipo = lb_Factura_Tipo_Compro.getSelectedItem();
				String nombre = itemTipo.getLabel();
				
				if (nombre.startsWith("Traslado") || nombre.startsWith("Pago")){ 
								
				
				}else{
					if(!Forma_Pago.trim().equals("")){
						CadenaLinea01 = CadenaLinea01 + " FormaPago=\""+Forma_Pago.trim() +"\"";
					}
				}
				
				if(!Tipo_Comprobante.trim().equals("")){
					CadenaLinea01 = CadenaLinea01 + " TipoDeComprobante=\""+Tipo_Comprobante +"\"";
				}
				
				if (nombre.startsWith("Traslado") || nombre.startsWith("Pago")){ 
					
					
				}else{
					if(!Metodo_Pago.trim().equals("")){
						CadenaLinea01 = CadenaLinea01 + " MetodoPago=\""+Metodo_Pago.trim() +"\"";
					}
				}
				
				
				if(!txt_Factura_Lugar_Expedi.getValue().equals("")){
					CadenaLinea01 = CadenaLinea01 + " LugarExpedicion=\""+txt_Factura_Lugar_Expedi.getValue() +"\"";
				}
				
				if(!txt_Factura_Confirma.getValue().equals("")){
					CadenaLinea01 = CadenaLinea01 + " Confirmacion=\""+txt_Factura_Confirma.getValue() +"\"";
				}
				
				CadenaLinea01 = CadenaLinea01 + ">";
				
				
				 writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				
					 if(Tipo_Comprobante.equals("I")){
						 if(lb_CatPort_00_TranspInternac.getSelectedIndex() > 0){
							 ShemaTipoComprobante ="<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+NameSpaceCartaPorte +" "+ ShemaLocation+" "+ShemaLocationCartaPorte+Termina_ShemaLocation+ CadenaLinea01;
						 }else{
							 ShemaTipoComprobante ="<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+ ShemaLocation+Termina_ShemaLocation+ CadenaLinea01;
						 }
						
		 				}
					if(Tipo_Comprobante.equals("E")){
						ShemaTipoComprobante ="<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+ ShemaLocation+Termina_ShemaLocation+ CadenaLinea01;	
					 	}
					if(Tipo_Comprobante.equals("T")){
						if(lb_CatPort_00_TranspInternac.getSelectedIndex() > 0){
							 ShemaTipoComprobante ="<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+NameSpaceCartaPorte +" "+ ShemaLocation+" "+ShemaLocationCartaPorte+Termina_ShemaLocation+ CadenaLinea01;
						 }else{
							 ShemaTipoComprobante ="<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+ ShemaLocation+Termina_ShemaLocation+ CadenaLinea01;
						 }
						
						}
					if(Tipo_Comprobante.equals("P")){
						ShemaTipoComprobante ="<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+NameSpacePagos +" "+ ShemaLocation+" "+ShemaLocationPagos+Termina_ShemaLocation+ CadenaLinea01;
						}
					
					writer.println(ShemaTipoComprobante);
					
//				 if( lb_CatPort_00_TranspInternac.getValue() != null) {
//					 if(lb_CatPort_00_TranspInternac.getSelectedIndex() > 0){
//						 writer.println("<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+NameSpaceCartaPorte +" "+ ShemaLocation+" "+ShemaLocationCartaPorte+Termina_ShemaLocation+ CadenaLinea01);
//					 }else{
//						 if(Tipo_Comprobante.equals("P")){
//							 writer.println("<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+NameSpacePagos +" "+ ShemaLocation+" "+ShemaLocationPagos+Termina_ShemaLocation+ CadenaLinea01);
//						 }else{
//							 writer.println("<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+ ShemaLocation+Termina_ShemaLocation+ CadenaLinea01);
//						 }
//					 }
//				 }else{
//					 writer.println("<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+ ShemaLocation+Termina_ShemaLocation+ CadenaLinea01);
//				 }
//				 writer.println("<cfdi:Comprobante "+NameSpaceInstance+" "+cfd3+" "+ ShemaLocation+Termina_ShemaLocation+ CadenaLinea01); 
	            
//	            writer.println("01|"+txt_Factura_verzion.getValue() +"|"+txt_Factura_Serie.getValue() +"|"+txt_Factura_Folio.getValue() +"|"+FechaCompro+"T01:00:00" +"|"+Forma_Pago.trim() +"|"+ txt_Factura_Condi_Pago.getValue() +"|"+txt_Factura_Subtotal.getValue() +"|"+txt_Factura_Descuento.getValue() +"|"+cb_Moneda.getSelectedItem().getLabel() +"|"+txt_Factura_Tipo_Cambio.getValue() +"|"+txt_Factura_Total.getValue() +"|"+Tipo_Comprobante +"|"+Metodo_Pago.trim()+"|"+txt_Factura_Lugar_Expedi.getValue() +"|"+txt_Factura_Confirma.getValue());
	            
	          //Segunda y Tercera Linea Relacionados
//	            if(gb_Factura_02_Relac.isOpen() || lb_Factura_Relacion.getSelectedIndex() > 0){
	            if(lb_Factura_Relacion.getSelectedIndex() > 0){
	            	if (lb_Factura_Relacion.getSelectedIndex() > 0){
//		            	Listitem item_Rela = lb_Factura_Relacion.getSelectedItem();
//		        		ListModelList lml_Rela = (ListModelList) lb_Factura_Relacion.getListModel();
//		        		ComboFactory comboFactory_Rela = (ComboFactory) lml_Rela.get(item_Rela.getIndex());
		        		System.out.println("Relacion: "+ lb_Factura_Relacion.getSelectedItem().getLabel());
//		        		 Listitem item_Rela = lb_Factura_Relacion.getSelectedItem();
//		        		 System.out.println(lb_Factura_Relacion.getListModel());
//		 	            ListModelList lml_Rela = (ListModelList) lb_Factura_Relacion.getListModel();
//		 	            System.out.println(item_Rela.getIndex());
//		         		ComboFactory comboFactory_Rela = (ComboFactory) lml_Rela.get(item_Rela.getIndex());
		        		
		        		
		        		String[] LisRelacion = lb_Factura_Relacion.getSelectedItem().getLabel().split("-");
		        		
		        		 String Relacion = "";
						   
		 				for (int i = 0; i < LisRelacion.length; i++) {
		 							
		 					Relacion = LisRelacion[0];
		 					System.out.println(LisRelacion[0]); 
		 				}
		        		
//		            	writer.println("02|"+Relacion);
		            	
		            	writer.println("<cfdi:CfdiRelacionados TipoRelacion=\""+Relacion+"\">"); 
		            	
		            	            	
		            	for (Object item : itemRelaList.getItems()) {
		                	//Los tenemos que ir cargando porque los items de las pï¿½ginas que no se hayan visitado
		                	//no estarï¿½n cargados
		        			itemRelaList.renderItem((Listitem) item);
		        	        String i = "";
		        	        for (Object cell : ((Listitem) item).getChildren()) {
		        	        	//Solo mostramos las celdas que estï¿½n visibles
		        	        	if(((Listcell)cell).getListheader()!=null){
		        		        	 if(((Listcell)cell).getListheader().isVisible()){
		        		        		 i += ((Listcell) cell).getLabel() ;
		        		        	 }
		        	        	}
		        	        }
		        	       
		        	        System.out.println(i + "\n");
//		        	        writer.println("03|"+i);
		        	        
		        	        writer.println("<cfdi:CfdiRelacionado UUID=\""+i+"\"/>"); 
//		        	        sb.append(i + "\n");
		                }
		            	writer.println("</cfdi:CfdiRelacionados>");
	            	}
	            }
	            
	            
	            String CadenaLinea04 = "<cfdi:Emisor";
				
				if(!txt_Factura_Emi_RFC.getValue().equals("")){
					CadenaLinea04 = CadenaLinea04 + " Rfc=\""+txt_Factura_Emi_RFC.getValue().toUpperCase()+"\"";
				}
				if(!txt_Factura_Emi_Nombre.getValue().equals("")){
					CadenaLinea04 = CadenaLinea04 + " Nombre=\""+txt_Factura_Emi_Nombre.getValue()+"\"";
				}
				if(!txt_Factura_Emi_Regimen.getValue().equals("")){
					CadenaLinea04 = CadenaLinea04 + " RegimenFiscal=\""+txt_Factura_Emi_Regimen.getValue()+"\"";
				}
				
				CadenaLinea04 = CadenaLinea04 + "/>";
	            
				//Cuarta Linea Emisor
				writer.println(CadenaLinea04);
				
	            //Cuarta Linea Emisor
//	          //Conceptos
        		String CadenaLinea06Encabezado = "<cfdi:Conceptos>";
				String CadenaLinea06Pie = "</cfdi:Conceptos>";
				
				String CadenaLinea07ImpTrasIni = "		<cfdi:Traslados>";
 				String CadenaLinea07ImpTrasFin = "		</cfdi:Traslados>";
 				String CadenaLinea07ImpIni = "	<cfdi:Impuestos>";
 				String CadenaLinea07ImpFin = "	</cfdi:Impuestos>";
 				String CadenaLinea08ImpRetenIni = "		<cfdi:Retenciones>";
 				String CadenaLinea08ImpRetenFin = "		</cfdi:Retenciones>";
				
				
	            
	            //Quinta Linea Receptor
	            Listitem item_uso = lb_UsoDialog.getSelectedItem();
        		ListModelList lml_uso = (ListModelList) lb_UsoDialog.getListModel();
        		ComboFactory comboFactory_Uso = (ComboFactory) lml_uso.get(item_uso.getIndex());
        		
        		String[] LisProductUSO = comboFactory_Uso.getNombre().split("-");
        		
        		 String Uso_CFDI = "";
				   
  				for (int i = 0; i < LisProductUSO.length; i++) {
  							
  					Uso_CFDI = LisProductUSO[0];
  					System.out.println(LisProductUSO[0]); 
  				}
  				
  				 String CadenaLinea05 = "<cfdi:Receptor";
 				
 				if(!txt_Factura_Recep_RFC.getValue().trim().equals("")){
 					CadenaLinea05 = CadenaLinea05 + " Rfc=\""+txt_Factura_Recep_RFC.getValue().trim().toUpperCase()+"\"";
 				}
 				if(!txt_Factura_Recep_Nombre.getValue().equals("")){
 					CadenaLinea05 = CadenaLinea05 + " Nombre=\""+txt_Factura_Recep_Nombre.getValue()+"\"";
 				}
 				if(!txt_Factura_Recep_Residencia.getValue().equals("")){
 					CadenaLinea05 = CadenaLinea05 + " ResidenciaFiscal=\""+txt_Factura_Recep_Residencia.getValue()+"\"";
 				}
 				if(!txt_Factura_Recep_No_Tribu.getValue().equals("")){
 					CadenaLinea05 = CadenaLinea05 + " NumRegIdTrib=\""+txt_Factura_Recep_No_Tribu.getValue()+"\"";
 				}
 				if(!Uso_CFDI.trim().equals("")){
 					CadenaLinea05 = CadenaLinea05 + " UsoCFDI=\""+Uso_CFDI.trim()+"\"";
 				}
 				
 				CadenaLinea05 = CadenaLinea05 + "/>";
  				
        		
        		if (lb_UsoDialog.getSelectedIndex() > 0 ){
//        			 writer.println("05|"+txt_Factura_Recep_RFC.getValue()+"|"+txt_Factura_Recep_Nombre.getValue()+"|"+txt_Factura_Recep_Residencia.getValue()+"|"+txt_Factura_Recep_No_Tribu.getValue()+"|"+Uso_CFDI.trim());
        			 writer.println(CadenaLinea05);
        		}else{
        			Messagebox.show("Selecciona el Uso CFDI es obligatorio ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
        			return;
        		}
        		
        		writer.println(CadenaLinea06Encabezado);
//        		for (int indice = 0; indice < detalle.size(); indice++) {
//					System.out.println("Valor de la cadena: "
//							+ detalle.get(indice));
//
//					String[] LisProduct = detalle.get(indice).toString().split(
//							";");
					
        		
					for (Object item : itemList.getItems()) {
	                	//Los tenemos que ir cargando porque los items de las pï¿½ginas que no se hayan visitado
	                	//no estarï¿½n cargados
						itemList.renderItem((Listitem) item);
	        	        String J = "";
	        	        for (Object cell : ((Listitem) item).getChildren()) {
	        	        	//Solo mostramos las celdas que estï¿½n visibles
	        	        	if(((Listcell)cell).getListheader()!=null){
	        		        	 if(((Listcell)cell).getListheader().isVisible()){
	        		        		 J += ((Listcell) cell).getLabel() ;
	        		        	 }
	        	        	}
	        	        }
	        	       
	        	        System.out.println(J + "\n");
//	        	        writer.println("03|"+i);
	        	        
	        	        String[] LisProduct = J.split("\\|");
	        	        
	        	       
//	        	        sb.append(i + "\n");
//	                }
//	        	        String[] LisProductint = null;
	        	        int valorcadena = 0;

					for (int i = 0; i < LisProduct.length; i++) {
						
						System.out.println("Aqui el total: "+ LisProduct.length);
						
						valorcadena =  LisProduct.length;
						
						System.out.println(i +" - Aqui los productos----->> " +LisProduct[i]);

						
//						String[] LisProductint = LisProduct[i].split(",");
						
						
					
					 

//						if (i == 8) {

//							for (int A = 0; A < LisProductint.length; A++) {
//								// System.out.println(LisProduct[A].replace("[",
//								// ""));
//								System.out.println("VAlor de A: " + A
//										+ " Valor de la lista: "
//										+ LisProductint[A]);
//								
//								
//
//							}
//							writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
//						}
//						
//						if (LisProductint.length == 14) {
//
//							for (int A = 0; A < LisProductint.length; A++) {
//								// System.out.println(LisProduct[A].replace("[",
//								// ""));
//								System.out.println("VAlor de A: " + A
//										+ " Valor de la lista: "
//										+ LisProductint[A]);
//								
//								
//
//							}
//							
//							writer.println("06|"+LisProductint[0]+"|"+LisProductint[1]+"|"+LisProductint[2]+"|"+LisProductint[3]+"|"+LisProductint[4]+"|"+LisProductint[5]+"|"+LisProductint[6]+"|"+LisProductint[7]+"|"+LisProductint[8]);
//							writer.println("07|"+LisProductint[9]+"|"+LisProductint[10]+"|"+LisProductint[11]+"|"+LisProductint[12]+"|"+LisProductint[13]);
//
//						}
//						
//						if (LisProductint.length == 19) {
//
//							for (int A = 0; A < LisProductint.length; A++) {
//								// System.out.println(LisProduct[A].replace("[",
//								// ""));
//								System.out.println("VAlor de A: " + A
//										+ " Valor de la lista: "
//										+ LisProductint[A]);
//								
//							
//
//							}
//							
//							writer.println("06|"+LisProductint[0]+"|"+LisProductint[1]+"|"+LisProductint[2]+"|"+LisProductint[3]+"|"+LisProductint[4]+"|"+LisProductint[5]+"|"+LisProductint[6]+"|"+LisProductint[7]+"|"+LisProductint[8]);
//							writer.println("07|"+LisProductint[9]+"|"+LisProductint[10]+"|"+LisProductint[11]+"|"+LisProductint[12]+"|"+LisProductint[13]);
//							writer.println("08|"+LisProductint[14]+"|"+LisProductint[15]+"|"+LisProductint[16]+"|"+LisProductint[17]+"|"+LisProductint[18]);
//
//						}
//
				}
					
//					String CadenaLinea06Encabezado = "<cfdi:Conceptos>";
//					String CadenaLinea06Pie = "</cfdi:Conceptos>";
	 				
	 				String CadenaLinea06Concepto = "	<cfdi:Concepto";
	        			
//	 				writer.println(CadenaLinea06Encabezado);
	 				
//	 				String CadenaLinea07ImpTrasIni = "<cfdi:Traslados>";
//	 				String CadenaLinea07ImpTrasFin = "</cfdi:Traslados>";
//	 				String CadenaLinea07ImpIni = "<cfdi:Impuestos>";
//	 				String CadenaLinea07ImpFin = "</cfdi:Impuestos>";
//	 				String CadenaLinea08ImpRetenIni = "<cfdi:Retenciones>";
//	 				String CadenaLinea08ImpRetenFin = "</cfdi:Retenciones>";
//					writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
					
	 				String CadenaLinea07ImpTras = "			<cfdi:Traslado ";
	 				String CadenaLinea08ImpRet = "			<cfdi:Retencion ";
	 				
					if (valorcadena == 9 || valorcadena == 8) {

//						for (int A = 0; A < LisProductint.length; A++) {
//							// System.out.println(LisProduct[A].replace("[",
//							// ""));
//							System.out.println("VAlor de A: " + A
//									+ " Valor de la lista: "
//									+ LisProductint[A]);
//							
//							
//
//						}
						if(!LisProduct[0].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveProdServ=\""+LisProduct[0]+"\"";
						}
						if(!LisProduct[1].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " NoIdentificacion=\""+LisProduct[1]+"\"";
						}
						if(!LisProduct[2].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Cantidad=\""+LisProduct[2]+"\"";
						}
						if(!LisProduct[3].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveUnidad=\""+LisProduct[3]+"\"";
						}
						if(!LisProduct[4].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Unidad=\""+LisProduct[4]+"\"";
						}
						if(!LisProduct[5].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descripcion=\""+LisProduct[5]+"\"";
						}
						if(!LisProduct[6].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ValorUnitario=\""+LisProduct[6]+"\"";
						}
						if(!LisProduct[7].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
						}
						if(valorcadena == 9){
							if(!LisProduct[8].equals("")){
								CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
							}
						}
						
						CadenaLinea06Concepto = CadenaLinea06Concepto +">";
						
						writer.println(CadenaLinea06Concepto);
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
						writer.println("	</cfdi:Concepto>");
						
						CadenaLinea06Concepto="";
						
					}
					
					if (valorcadena == 10) {

//						for (int A = 0; A < LisProductint.length; A++) {
//							// System.out.println(LisProduct[A].replace("[",
//							// ""));
//							System.out.println("VAlor de A: " + A
//									+ " Valor de la lista: "
//									+ LisProductint[A]);
//							
//							
//
//						}
						if(!LisProduct[0].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveProdServ=\""+LisProduct[0]+"\"";
						}
						if(!LisProduct[1].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " NoIdentificacion=\""+LisProduct[1]+"\"";
						}
						if(!LisProduct[2].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Cantidad=\""+LisProduct[2]+"\"";
						}
						if(!LisProduct[3].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveUnidad=\""+LisProduct[3]+"\"";
						}
						if(!LisProduct[4].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Unidad=\""+LisProduct[4]+"\"";
						}
						if(!LisProduct[5].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descripcion=\""+LisProduct[5]+"\"";
						}
						if(!LisProduct[6].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ValorUnitario=\""+LisProduct[6]+"\"";
						}
						if(!LisProduct[7].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
						}
						if(!LisProduct[8].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
						}
						
						CadenaLinea06Concepto = CadenaLinea06Concepto +">";
						
						writer.println(CadenaLinea06Concepto);
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
//						<cfdi:InformacionAduanera NumeroPedimento="21  47  3807  8003832"/>
						if(LisProduct[9].startsWith("Aduana_Pedimento")){
							writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[9].replace("Aduana_Pedimento:", "")+"\"/>");
						}
						
						if(LisProduct[9].startsWith("Cuenta_Predial")){
							writer.println("<cfdi:CuentaPredial Numero=\""+LisProduct[9].replace("Cuenta_Predial:", "")+"\"/>");
						}
						
						
//						writer.println("09|"+LisProduct[9].replace("Aduana_Pedimento:", ""));
						
						writer.println("	</cfdi:Concepto>");
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
				
						
					}
					
					if (valorcadena == 11) {

//						for (int A = 0; A < LisProductint.length; A++) {
//							// System.out.println(LisProduct[A].replace("[",
//							// ""));
//							System.out.println("VAlor de A: " + A
//									+ " Valor de la lista: "
//									+ LisProductint[A]);
//							
//							
//
//						}
						if(!LisProduct[0].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveProdServ=\""+LisProduct[0]+"\"";
						}
						if(!LisProduct[1].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " NoIdentificacion=\""+LisProduct[1]+"\"";
						}
						if(!LisProduct[2].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Cantidad=\""+LisProduct[2]+"\"";
						}
						if(!LisProduct[3].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveUnidad=\""+LisProduct[3]+"\"";
						}
						if(!LisProduct[4].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Unidad=\""+LisProduct[4]+"\"";
						}
						if(!LisProduct[5].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descripcion=\""+LisProduct[5]+"\"";
						}
						if(!LisProduct[6].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ValorUnitario=\""+LisProduct[6]+"\"";
						}
						if(!LisProduct[7].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
						}
						if(!LisProduct[8].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
						}
						
						CadenaLinea06Concepto = CadenaLinea06Concepto +">";
						
						writer.println(CadenaLinea06Concepto);
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
//						<cfdi:InformacionAduanera NumeroPedimento="21  47  3807  8003832"/>
						
						writer.println("		<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[9].replace("Aduana_Pedimento:", "")+"\"/>");
//						writer.println("09|"+LisProduct[9].replace("Aduana_Pedimento:", ""));
						
						writer.println("		<cfdi:CuentaPredial Numero=\""+LisProduct[10].replace("Cuenta_Predial:", "")+"\"/>");
//						asas
						writer.println("	</cfdi:Concepto>");
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
				
						
					}
					
					if (valorcadena == 14) {

//						for (int A = 0; A < LisProductint.length; A++) {
//							// System.out.println(LisProduct[A].replace("[",
//							// ""));
//							System.out.println("VAlor de A: " + A
//									+ " Valor de la lista: "
//									+ LisProductint[A]);
//							
//							
//
//						}
						if(!LisProduct[0].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveProdServ=\""+LisProduct[0]+"\"";
						}
						if(!LisProduct[1].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " NoIdentificacion=\""+LisProduct[1]+"\"";
						}
						if(!LisProduct[2].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Cantidad=\""+LisProduct[2]+"\"";
						}
						if(!LisProduct[3].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveUnidad=\""+LisProduct[3]+"\"";
						}
						if(!LisProduct[4].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Unidad=\""+LisProduct[4]+"\"";
						}
						if(!LisProduct[5].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descripcion=\""+LisProduct[5]+"\"";
						}
						if(!LisProduct[6].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ValorUnitario=\""+LisProduct[6]+"\"";
						}
						if(!LisProduct[7].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
						}
						if(!LisProduct[8].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
						}
						
						CadenaLinea06Concepto = CadenaLinea06Concepto +">";
						
						writer.println(CadenaLinea06Concepto);
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
//						String CadenaLinea07ImpTrasIni = "<cfdi:Traslados>";
//		 				String CadenaLinea07ImpTrasFin = "</cfdi:Traslados>";
//		 				String CadenaLinea07ImpIni = "<cfdi:Impuestos>";
//		 				String CadenaLinea07ImpFin = "</cfdi:Impuestos>";
//		 				String CadenaLinea08ImpRetenIni = "<cfdi:Retenciones>";
//		 				String CadenaLinea08ImpRetenFin = "</cfdi:Retenciones>";

						
//						writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[9].replace("Aduana_Pedimento:", "")+"\"/>");
			 				writer.println(CadenaLinea07ImpIni);
				 				writer.println(CadenaLinea07ImpTrasIni);
				 				
					 				if(!LisProduct[9].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Base=\""+LisProduct[9]+"\"";
									}
					 				
					 				if(!LisProduct[10].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Impuesto=\""+LisProduct[10]+"\"";
									}
					 				
					 				if(!LisProduct[11].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TipoFactor=\""+LisProduct[11]+"\"";
									}
					 				
					 				if(!LisProduct[12].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TasaOCuota=\""+LisProduct[12]+"\"";
									}
					 				
					 				if(!LisProduct[13].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
									}
					 				
					 				CadenaLinea07ImpTras = CadenaLinea07ImpTras +"/>";
					 				
					 				writer.println(CadenaLinea07ImpTras);
				 				writer.println(CadenaLinea07ImpTrasFin);
			 				writer.println(CadenaLinea07ImpFin);
						
						writer.println("	</cfdi:Concepto>");

						
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
//						writer.println("07|"+LisProduct[9]+"|"+LisProduct[10]+"|"+LisProduct[11]+"|"+LisProduct[12]+"|"+LisProduct[13]);
						

					}
					if (valorcadena == 15) {

//						for (int A = 0; A < LisProductint.length; A++) {
//							// System.out.println(LisProduct[A].replace("[",
//							// ""));
//							System.out.println("VAlor de A: " + A
//									+ " Valor de la lista: "
//									+ LisProductint[A]);
//							
//							
//
//						}
						if(!LisProduct[0].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveProdServ=\""+LisProduct[0]+"\"";
						}
						if(!LisProduct[1].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " NoIdentificacion=\""+LisProduct[1]+"\"";
						}
						if(!LisProduct[2].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Cantidad=\""+LisProduct[2]+"\"";
						}
						if(!LisProduct[3].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveUnidad=\""+LisProduct[3]+"\"";
						}
						if(!LisProduct[4].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Unidad=\""+LisProduct[4]+"\"";
						}
						if(!LisProduct[5].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descripcion=\""+LisProduct[5]+"\"";
						}
						if(!LisProduct[6].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ValorUnitario=\""+LisProduct[6]+"\"";
						}
						if(!LisProduct[7].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
						}
						if(!LisProduct[8].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
						}
						
						CadenaLinea06Concepto = CadenaLinea06Concepto +">";
						
						writer.println(CadenaLinea06Concepto);
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
//						String CadenaLinea07ImpTrasIni = "<cfdi:Traslados>";
//		 				String CadenaLinea07ImpTrasFin = "</cfdi:Traslados>";
//		 				String CadenaLinea07ImpIni = "<cfdi:Impuestos>";
//		 				String CadenaLinea07ImpFin = "</cfdi:Impuestos>";
//		 				String CadenaLinea08ImpRetenIni = "<cfdi:Retenciones>";
//		 				String CadenaLinea08ImpRetenFin = "</cfdi:Retenciones>";

						
//						writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[9].replace("Aduana_Pedimento:", "")+"\"/>");
			 				writer.println(CadenaLinea07ImpIni);
				 				writer.println(CadenaLinea07ImpTrasIni);
				 				
					 				if(!LisProduct[9].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Base=\""+LisProduct[9]+"\"";
									}
					 				
					 				if(!LisProduct[10].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Impuesto=\""+LisProduct[10]+"\"";
									}
					 				
					 				if(!LisProduct[11].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TipoFactor=\""+LisProduct[11]+"\"";
									}
					 				
					 				if(!LisProduct[12].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TasaOCuota=\""+LisProduct[12]+"\"";
									}
					 				
					 				if(!LisProduct[13].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
									}
					 				
					 				CadenaLinea07ImpTras = CadenaLinea07ImpTras +"/>";
					 				
					 				writer.println(CadenaLinea07ImpTras);
				 				writer.println(CadenaLinea07ImpTrasFin);
			 				writer.println(CadenaLinea07ImpFin);
			 				
//			 				writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[14].replace("Aduana_Pedimento:", "")+"\"/>");
			 				
			 				if(LisProduct[14].startsWith("Aduana_Pedimento")){
								writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[14].replace("Aduana_Pedimento:", "")+"\"/>");
							}
							
							if(LisProduct[14].startsWith("Cuenta_Predial")){
								writer.println("<cfdi:CuentaPredial Numero=\""+LisProduct[14].replace("Cuenta_Predial:", "")+"\"/>");
							}
						
						writer.println("	</cfdi:Concepto>");
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
//						writer.println("07|"+LisProduct[9]+"|"+LisProduct[10]+"|"+LisProduct[11]+"|"+LisProduct[12]+"|"+LisProduct[13]);
//						writer.println("09|"+LisProduct[14].replace("Aduana_Pedimento:", ""));

					}
					
					if (valorcadena == 16) {

//						for (int A = 0; A < LisProductint.length; A++) {
//							// System.out.println(LisProduct[A].replace("[",
//							// ""));
//							System.out.println("VAlor de A: " + A
//									+ " Valor de la lista: "
//									+ LisProductint[A]);
//							
//							
//
//						}
						if(!LisProduct[0].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveProdServ=\""+LisProduct[0]+"\"";
						}
						if(!LisProduct[1].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " NoIdentificacion=\""+LisProduct[1]+"\"";
						}
						if(!LisProduct[2].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Cantidad=\""+LisProduct[2]+"\"";
						}
						if(!LisProduct[3].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveUnidad=\""+LisProduct[3]+"\"";
						}
						if(!LisProduct[4].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Unidad=\""+LisProduct[4]+"\"";
						}
						if(!LisProduct[5].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descripcion=\""+LisProduct[5]+"\"";
						}
						if(!LisProduct[6].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ValorUnitario=\""+LisProduct[6]+"\"";
						}
						if(!LisProduct[7].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
						}
						if(!LisProduct[8].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
						}
						
						CadenaLinea06Concepto = CadenaLinea06Concepto +">";
						
						writer.println(CadenaLinea06Concepto);
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
//						String CadenaLinea07ImpTrasIni = "<cfdi:Traslados>";
//		 				String CadenaLinea07ImpTrasFin = "</cfdi:Traslados>";
//		 				String CadenaLinea07ImpIni = "<cfdi:Impuestos>";
//		 				String CadenaLinea07ImpFin = "</cfdi:Impuestos>";
//		 				String CadenaLinea08ImpRetenIni = "<cfdi:Retenciones>";
//		 				String CadenaLinea08ImpRetenFin = "</cfdi:Retenciones>";

						
//						writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[9].replace("Aduana_Pedimento:", "")+"\"/>");
			 				writer.println(CadenaLinea07ImpIni);
				 				writer.println(CadenaLinea07ImpTrasIni);
				 				
					 				if(!LisProduct[9].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Base=\""+LisProduct[9]+"\"";
									}
					 				
					 				if(!LisProduct[10].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Impuesto=\""+LisProduct[10]+"\"";
									}
					 				
					 				if(!LisProduct[11].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TipoFactor=\""+LisProduct[11]+"\"";
									}
					 				
					 				if(!LisProduct[12].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TasaOCuota=\""+LisProduct[12]+"\"";
									}
					 				
					 				if(!LisProduct[13].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
									}
					 				
					 				CadenaLinea07ImpTras = CadenaLinea07ImpTras +"/>";
					 				
					 				writer.println(CadenaLinea07ImpTras);
				 				writer.println(CadenaLinea07ImpTrasFin);
			 				writer.println(CadenaLinea07ImpFin);
			 				
			 				writer.println("		<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[14].replace("Aduana_Pedimento:", "")+"\"/>");
			 				
			 				writer.println("		<cfdi:CuentaPredial Numero=\""+LisProduct[15].replace("Cuenta_Predial:", "")+"\"/>");
						
						writer.println("	</cfdi:Concepto>");
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
//						writer.println("07|"+LisProduct[9]+"|"+LisProduct[10]+"|"+LisProduct[11]+"|"+LisProduct[12]+"|"+LisProduct[13]);
//						writer.println("09|"+LisProduct[14].replace("Aduana_Pedimento:", ""));

					}
					
					
					
					if (valorcadena == 19) {

//						for (int A = 0; A < LisProduct.length; A++) {
//							// System.out.println(LisProduct[A].replace("[",
//							// ""));
//							System.out.println("VAlor de A: " + A
//									+ " Valor de la lista: "
//									+ LisProduct[A]);
//							
//						
//
//						}
						if(!LisProduct[0].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveProdServ=\""+LisProduct[0]+"\"";
						}
						if(!LisProduct[1].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " NoIdentificacion=\""+LisProduct[1]+"\"";
						}
						if(!LisProduct[2].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Cantidad=\""+LisProduct[2]+"\"";
						}
						if(!LisProduct[3].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveUnidad=\""+LisProduct[3]+"\"";
						}
						if(!LisProduct[4].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Unidad=\""+LisProduct[4]+"\"";
						}
						if(!LisProduct[5].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descripcion=\""+LisProduct[5]+"\"";
						}
						if(!LisProduct[6].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ValorUnitario=\""+LisProduct[6]+"\"";
						}
						if(!LisProduct[7].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
						}
						if(!LisProduct[8].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
						}
						
						CadenaLinea06Concepto = CadenaLinea06Concepto +">";
						
						writer.println(CadenaLinea06Concepto);
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
//						String CadenaLinea07ImpTrasIni = "<cfdi:Traslados>";
//		 				String CadenaLinea07ImpTrasFin = "</cfdi:Traslados>";
//		 				String CadenaLinea07ImpIni = "<cfdi:Impuestos>";
//		 				String CadenaLinea07ImpFin = "</cfdi:Impuestos>";
//		 				String CadenaLinea08ImpRetenIni = "<cfdi:Retenciones>";
//		 				String CadenaLinea08ImpRetenFin = "</cfdi:Retenciones>";

						
//						writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[9].replace("Aduana_Pedimento:", "")+"\"/>");
			 				writer.println(CadenaLinea07ImpIni);
				 				writer.println(CadenaLinea07ImpTrasIni);
				 				
					 				if(!LisProduct[9].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Base=\""+LisProduct[9]+"\"";
									}
					 				
					 				if(!LisProduct[10].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Impuesto=\""+LisProduct[10]+"\"";
									}
					 				
					 				if(!LisProduct[11].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TipoFactor=\""+LisProduct[11]+"\"";
									}
					 				
					 				if(!LisProduct[12].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TasaOCuota=\""+LisProduct[12]+"\"";
									}
					 				
					 				if(!LisProduct[13].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
									}
					 				
					 				CadenaLinea07ImpTras = CadenaLinea07ImpTras +"/>";
					 				
					 				writer.println(CadenaLinea07ImpTras);
				 				writer.println(CadenaLinea07ImpTrasFin);
				 				
				 				writer.println(CadenaLinea08ImpRetenIni);
				 				
					 				if(!LisProduct[14].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Base=\""+LisProduct[14]+"\"";
									}
					 				
					 				if(!LisProduct[15].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Impuesto=\""+LisProduct[15]+"\"";
									}
					 				
					 				if(!LisProduct[16].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " TipoFactor=\""+LisProduct[16]+"\"";
									}
					 				
					 				if(!LisProduct[17].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " TasaOCuota=\""+LisProduct[17]+"\"";
									}
					 				
					 				if(!LisProduct[18].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Importe=\""+LisProduct[18]+"\"";
									}
					 				
					 				CadenaLinea08ImpRet = CadenaLinea08ImpRet +"/>";
					 				
					 				writer.println(CadenaLinea08ImpRet);
			 				writer.println(CadenaLinea08ImpRetenFin);
				 				
			 				writer.println(CadenaLinea07ImpFin);
			 				
//			 				writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[14].replace("Aduana_Pedimento:", "")+"\"/>");
						
						writer.println("	</cfdi:Concepto>");
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
//						writer.println("07|"+LisProduct[9]+"|"+LisProduct[10]+"|"+LisProduct[11]+"|"+LisProduct[12]+"|"+LisProduct[13]);
//						writer.println("08|"+LisProduct[14]+"|"+LisProduct[15]+"|"+LisProduct[16]+"|"+LisProduct[17]+"|"+LisProduct[18]);

					}
					
					if (valorcadena == 20) {

//						for (int A = 0; A < LisProduct.length; A++) {
//							// System.out.println(LisProduct[A].replace("[",
//							// ""));
//							System.out.println("VAlor de A: " + A
//									+ " Valor de la lista: "
//									+ LisProduct[A]);
//							
//						
//
//						}
						if(!LisProduct[0].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveProdServ=\""+LisProduct[0]+"\"";
						}
						if(!LisProduct[1].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " NoIdentificacion=\""+LisProduct[1]+"\"";
						}
						if(!LisProduct[2].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Cantidad=\""+LisProduct[2]+"\"";
						}
						if(!LisProduct[3].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveUnidad=\""+LisProduct[3]+"\"";
						}
						if(!LisProduct[4].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Unidad=\""+LisProduct[4]+"\"";
						}
						if(!LisProduct[5].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descripcion=\""+LisProduct[5]+"\"";
						}
						if(!LisProduct[6].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ValorUnitario=\""+LisProduct[6]+"\"";
						}
						if(!LisProduct[7].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
						}
						if(!LisProduct[8].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
						}
						
						CadenaLinea06Concepto = CadenaLinea06Concepto +">";
						
						writer.println(CadenaLinea06Concepto);
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
//						String CadenaLinea07ImpTrasIni = "<cfdi:Traslados>";
//		 				String CadenaLinea07ImpTrasFin = "</cfdi:Traslados>";
//		 				String CadenaLinea07ImpIni = "<cfdi:Impuestos>";
//		 				String CadenaLinea07ImpFin = "</cfdi:Impuestos>";
//		 				String CadenaLinea08ImpRetenIni = "<cfdi:Retenciones>";
//		 				String CadenaLinea08ImpRetenFin = "</cfdi:Retenciones>";

						
//						writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[9].replace("Aduana_Pedimento:", "")+"\"/>");
			 				writer.println(CadenaLinea07ImpIni);
				 				writer.println(CadenaLinea07ImpTrasIni);
				 				
					 				if(!LisProduct[9].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Base=\""+LisProduct[9]+"\"";
									}
					 				
					 				if(!LisProduct[10].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Impuesto=\""+LisProduct[10]+"\"";
									}
					 				
					 				if(!LisProduct[11].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TipoFactor=\""+LisProduct[11]+"\"";
									}
					 				
					 				if(!LisProduct[12].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TasaOCuota=\""+LisProduct[12]+"\"";
									}
					 				
					 				if(!LisProduct[13].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
									}
					 				
					 				CadenaLinea07ImpTras = CadenaLinea07ImpTras +"/>";
					 				
					 				writer.println(CadenaLinea07ImpTras);
				 				writer.println(CadenaLinea07ImpTrasFin);
				 				
				 				writer.println(CadenaLinea08ImpRetenIni);
				 				
					 				if(!LisProduct[14].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Base=\""+LisProduct[14]+"\"";
									}
					 				
					 				if(!LisProduct[15].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Impuesto=\""+LisProduct[15]+"\"";
									}
					 				
					 				if(!LisProduct[16].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " TipoFactor=\""+LisProduct[16]+"\"";
									}
					 				
					 				if(!LisProduct[17].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " TasaOCuota=\""+LisProduct[17]+"\"";
									}
					 				
					 				if(!LisProduct[18].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Importe=\""+LisProduct[18]+"\"";
									}
					 				
					 				CadenaLinea08ImpRet = CadenaLinea08ImpRet +"/>";
					 				
					 				writer.println(CadenaLinea08ImpRet);
			 				writer.println(CadenaLinea08ImpRetenFin);
				 				
			 				writer.println(CadenaLinea07ImpFin);
			 				
//			 				writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[19].replace("Aduana_Pedimento:", "")+"\"/>");
			 				
			 				if(LisProduct[19].startsWith("Aduana_Pedimento")){
								writer.println("		<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[19].replace("Aduana_Pedimento:", "")+"\"/>");
							}
							
							if(LisProduct[19].startsWith("Cuenta_Predial")){
								writer.println("		<cfdi:CuentaPredial Numero=\""+LisProduct[19].replace("Cuenta_Predial:", "")+"\"/>");
							}
						
						writer.println("	</cfdi:Concepto>");
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
//						writer.println("07|"+LisProduct[9]+"|"+LisProduct[10]+"|"+LisProduct[11]+"|"+LisProduct[12]+"|"+LisProduct[13]);
//						writer.println("08|"+LisProduct[14]+"|"+LisProduct[15]+"|"+LisProduct[16]+"|"+LisProduct[17]+"|"+LisProduct[18]);
//						writer.println("09|"+LisProduct[19].replace("Aduana_Pedimento:", ""));

					}
					
					if (valorcadena == 21) {

//						for (int A = 0; A < LisProduct.length; A++) {
//							// System.out.println(LisProduct[A].replace("[",
//							// ""));
//							System.out.println("VAlor de A: " + A
//									+ " Valor de la lista: "
//									+ LisProduct[A]);
//							
//						
//
//						}
						if(!LisProduct[0].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveProdServ=\""+LisProduct[0]+"\"";
						}
						if(!LisProduct[1].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " NoIdentificacion=\""+LisProduct[1]+"\"";
						}
						if(!LisProduct[2].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Cantidad=\""+LisProduct[2]+"\"";
						}
						if(!LisProduct[3].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ClaveUnidad=\""+LisProduct[3]+"\"";
						}
						if(!LisProduct[4].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Unidad=\""+LisProduct[4]+"\"";
						}
						if(!LisProduct[5].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descripcion=\""+LisProduct[5]+"\"";
						}
						if(!LisProduct[6].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " ValorUnitario=\""+LisProduct[6]+"\"";
						}
						if(!LisProduct[7].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
						}
						if(!LisProduct[8].equals("")){
							CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
						}
						
						CadenaLinea06Concepto = CadenaLinea06Concepto +">";
						
						writer.println(CadenaLinea06Concepto);
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
						
//						String CadenaLinea07ImpTrasIni = "<cfdi:Traslados>";
//		 				String CadenaLinea07ImpTrasFin = "</cfdi:Traslados>";
//		 				String CadenaLinea07ImpIni = "<cfdi:Impuestos>";
//		 				String CadenaLinea07ImpFin = "</cfdi:Impuestos>";
//		 				String CadenaLinea08ImpRetenIni = "<cfdi:Retenciones>";
//		 				String CadenaLinea08ImpRetenFin = "</cfdi:Retenciones>";

						
//						writer.println("<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[9].replace("Aduana_Pedimento:", "")+"\"/>");
			 				writer.println(CadenaLinea07ImpIni);
				 				writer.println(CadenaLinea07ImpTrasIni);
				 				
					 				if(!LisProduct[9].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Base=\""+LisProduct[9]+"\"";
									}
					 				
					 				if(!LisProduct[10].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Impuesto=\""+LisProduct[10]+"\"";
									}
					 				
					 				if(!LisProduct[11].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TipoFactor=\""+LisProduct[11]+"\"";
									}
					 				
					 				if(!LisProduct[12].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " TasaOCuota=\""+LisProduct[12]+"\"";
									}
					 				
					 				if(!LisProduct[13].equals("")){
					 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
									}
					 				
					 				CadenaLinea07ImpTras = CadenaLinea07ImpTras +"/>";
					 				
					 				writer.println(CadenaLinea07ImpTras);
				 				writer.println(CadenaLinea07ImpTrasFin);
				 				
				 				writer.println(CadenaLinea08ImpRetenIni);
				 				
					 				if(!LisProduct[14].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Base=\""+LisProduct[14]+"\"";
									}
					 				
					 				if(!LisProduct[15].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Impuesto=\""+LisProduct[15]+"\"";
									}
					 				
					 				if(!LisProduct[16].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " TipoFactor=\""+LisProduct[16]+"\"";
									}
					 				
					 				if(!LisProduct[17].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " TasaOCuota=\""+LisProduct[17]+"\"";
									}
					 				
					 				if(!LisProduct[18].equals("")){
					 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Importe=\""+LisProduct[18]+"\"";
									}
					 				
					 				CadenaLinea08ImpRet = CadenaLinea08ImpRet +"/>";
					 				
					 				writer.println(CadenaLinea08ImpRet);
			 				writer.println(CadenaLinea08ImpRetenFin);
				 				
			 				writer.println(CadenaLinea07ImpFin);
			 				
			 				writer.println("		<cfdi:InformacionAduanera NumeroPedimento=\""+LisProduct[19].replace("Aduana_Pedimento:", "")+"\"/>");
			 				
			 				writer.println("		<cfdi:CuentaPredial Numero=\""+LisProduct[20].replace("Cuenta_Predial:", "")+"\"/>");
						
						writer.println("	</cfdi:Concepto>");
//						writer.println("06|"+LisProduct[0]+"|"+LisProduct[1]+"|"+LisProduct[2]+"|"+LisProduct[3]+"|"+LisProduct[4]+"|"+LisProduct[5]+"|"+LisProduct[6]+"|"+LisProduct[7]+"|"+LisProduct[8]);
//						writer.println("07|"+LisProduct[9]+"|"+LisProduct[10]+"|"+LisProduct[11]+"|"+LisProduct[12]+"|"+LisProduct[13]);
//						writer.println("08|"+LisProduct[14]+"|"+LisProduct[15]+"|"+LisProduct[16]+"|"+LisProduct[17]+"|"+LisProduct[18]);
//						writer.println("09|"+LisProduct[19].replace("Aduana_Pedimento:", ""));

					}
					
					
					
					}
					
					
					writer.println(CadenaLinea06Pie);
					
        		
        		//Sexta linea Conceptos
//        		writer.println("06|"+txt_Factura_Conceptos_Clave.getValue()+"|"+txt_Factura_Conceptos_No_Identi.getValue()+"|"+txt_Factura_Conceptos_Cantidad.getValue()+"|"+txt_Factura_Conceptos_ClaveUni.getValue()+"|"+txt_Factura_Conceptos_Unidad.getValue()+"|"+txt_Factura_Conceptos_Desc.getValue()+"|"+txt_Factura_Conceptos_ValorUni.getValue()+"|"+txt_Factura_Conceptos_Importe.getValue()+"|"+txt_Factura_Conceptos_Descuento.getValue());
        		
        		
//        		if(gb_Factura_07_Imp_Tras.isOpen()){
//        			writer.println("07|"+txt_Factura_Imp_Tras_Base.getValue()+"|"+txt_Factura_Imp_Tras_Imp.getValue()+"|"+txt_Factura_Imp_Tras_Factor.getValue()+"|"+txt_Factura_Imp_Tras_Tasa.getValue()+"|"+txt_Factura_Imp_Tras_Importe.getValue());
//        		}
        		
//        		if(gb_Factura_08_Imp_Rete.isOpen()){
//        			writer.println("08|"+txt_Factura_Imp_Ret_Base.getValue()+"|"+txt_Factura_Imp_Ret_Importe.getValue()+"|"+txt_Factura_Imp_Ret_Factor.getValue()+"|"+txt_Factura_Imp_Ret_Tasa.getValue()+"|"+txt_Factura_Imp_Ret_Importe.getValue());
//        		}
	           
//        		if(gb_Factura_09_Info_Aduana.isOpen() || !txt_Factura_Aduana_Pedimento.getValue().equals("")){
//        			writer.println("09|"+txt_Factura_Aduana_Pedimento.getValue());
//        		}
        		
//        		if(gb_Factura_10_Predial.isOpen() || !txt_Factura_Predial_Cuenta.getValue().equals("")){
//        			writer.println("10|"+txt_Factura_Predial_Cuenta.getValue());
//        		}
        		
//        		if(gb_Factura_11_Compl_Part.isOpen() || !txt_Factura_parte_Clave.getValue().equals("")|| !txt_Factura_parte_Identi.getValue().equals("")|| !txt_Factura_parte_Cantidad.getValue().equals("")|| !txt_Factura_parte_Unidad.getValue().equals("")|| !txt_Factura_parte_Desc.getValue().equals("")|| !txt_Factura_parte_Unita.getValue().equals("")|| !txt_Factura_parte_Importe.getValue().equals("")){
//        			writer.println("11|"+txt_Factura_parte_Clave.getValue()+"|"+txt_Factura_parte_Identi.getValue()+"|"+txt_Factura_parte_Cantidad.getValue()+"|"+txt_Factura_parte_Unidad.getValue()+"|"+txt_Factura_parte_Desc.getValue()+"|"+txt_Factura_parte_Unita.getValue()+"|"+txt_Factura_parte_Importe.getValue());
//        		}
        		
        		String ImpuestosTotales = "<cfdi:Impuestos";
        		
//        		if(gb_Factura_12_Tot_Imp.isOpen()|| !txt_Factura_Total_Rete.getValue().equals("")|| !txt_Factura_Total_Trasl.getValue().equals("")){
        		if(!txt_Factura_Total_Rete.getValue().equals("")|| !txt_Factura_Total_Trasl.getValue().equals("")){
        			
        			if(!txt_Factura_Total_Trasl.getValue().equals("")){
        				ImpuestosTotales = ImpuestosTotales + " TotalImpuestosTrasladados=\""+txt_Factura_Total_Trasl.getValue()+"\"";
        			}
        			
        			if(!txt_Factura_Total_Rete.getValue().equals("")){
        				ImpuestosTotales = ImpuestosTotales + " TotalImpuestosRetenidos=\""+txt_Factura_Total_Rete.getValue()+"\"";
        			}
        			
        			ImpuestosTotales = ImpuestosTotales +">";
        			
        			writer.println(ImpuestosTotales);
        			
//        			writer.println("12|"+txt_Factura_Total_Rete.getValue()+"|"+txt_Factura_Total_Trasl.getValue());
        		}
        		
        		String ImpuestosTotReten = "<cfdi:Retencion";
        		
//        		if(gb_Factura_13_Imp_Rete.isOpen()|| !txt_Factura_13_Impuesto_Rete.getValue().equals("")|| !txt_Factura_13_Importe_Rete.getValue().equals("")){
        		if(!txt_Factura_13_Impuesto_Rete.getValue().equals("")|| !txt_Factura_13_Importe_Rete.getValue().equals("")){
        			writer.println(CadenaLinea08ImpRetenIni);
        			
	        			if(!txt_Factura_13_Impuesto_Rete.getValue().equals("")){
	        				ImpuestosTotReten = ImpuestosTotReten + " Impuesto=\""+txt_Factura_13_Impuesto_Rete.getValue()+"\"";
	        			}
	        			if(!txt_Factura_13_Importe_Rete.getValue().equals("")){
	        				ImpuestosTotReten = ImpuestosTotReten + " Importe=\""+txt_Factura_13_Importe_Rete.getValue()+"\"";
	        			}
	        			ImpuestosTotReten = ImpuestosTotReten + "/>";
	        			writer.println(ImpuestosTotReten);
	        			
	        		writer.println(CadenaLinea08ImpRetenFin);
//        			writer.println("13|"+txt_Factura_13_Impuesto_Rete.getValue()+"|"+txt_Factura_13_Importe_Rete.getValue());
        		}
        		
        		String ImpuestosTotTras = "			<cfdi:Traslado";
//        		if(gb_Factura_14_Imp_Traslado.isOpen() || !txt_Factura_14_Impuesto_Traslado.getValue().equals("")|| !txt_Factura_14_Factor_Traslado.getValue().equals("")|| !txt_Factura_14_Tasa_Traslado.getValue().equals("")|| !txt_Factura_14_Importe_Traslado.getValue().equals("")){
        		if(!txt_Factura_14_Impuesto_Traslado.getValue().equals("")|| !txt_Factura_14_Factor_Traslado.getValue().equals("")|| !txt_Factura_14_Tasa_Traslado.getValue().equals("")|| !txt_Factura_14_Importe_Traslado.getValue().equals("")){
        			writer.println(CadenaLinea07ImpTrasIni);
        			
        			if(!txt_Factura_14_Impuesto_Traslado.getValue().equals("")){
        				ImpuestosTotTras = ImpuestosTotTras + " Impuesto=\""+txt_Factura_14_Impuesto_Traslado.getValue()+"\"";
        			}
        			if(!txt_Factura_14_Factor_Traslado.getValue().equals("")){
        				ImpuestosTotTras = ImpuestosTotTras + " TipoFactor=\""+txt_Factura_14_Factor_Traslado.getValue()+"\"";
        			}
        			if(!txt_Factura_14_Tasa_Traslado.getValue().equals("")){
        				ImpuestosTotTras = ImpuestosTotTras + " TasaOCuota=\""+txt_Factura_14_Tasa_Traslado.getValue()+"\"";
        			}
        			if(!txt_Factura_14_Importe_Traslado.getValue().equals("")){
        				ImpuestosTotTras = ImpuestosTotTras + " Importe=\""+txt_Factura_14_Importe_Traslado.getValue()+"\"";
        			}
        			ImpuestosTotTras = ImpuestosTotTras + "/>";
        			
        			writer.println(ImpuestosTotTras);
        			
        		writer.println(CadenaLinea07ImpTrasFin);
//        			writer.println("14|"+txt_Factura_14_Impuesto_Traslado.getValue()+"|"+txt_Factura_14_Factor_Traslado.getValue()+"|"+txt_Factura_14_Tasa_Traslado.getValue()+"|"+txt_Factura_14_Importe_Traslado.getValue());
        		}
        		
//        		if(gb_Factura_14_Imp_Traslado.isOpen() || !txt_Factura_14_Impuesto_Traslado.getValue().equals("")|| !txt_Factura_14_Factor_Traslado.getValue().equals("")|| !txt_Factura_14_Tasa_Traslado.getValue().equals("")|| !txt_Factura_14_Importe_Traslado.getValue().equals("") || gb_Factura_13_Imp_Rete.isOpen()|| !txt_Factura_13_Impuesto_Rete.getValue().equals("")|| !txt_Factura_13_Importe_Rete.getValue().equals("")){
        		if(!txt_Factura_14_Impuesto_Traslado.getValue().equals("")|| !txt_Factura_14_Factor_Traslado.getValue().equals("")|| !txt_Factura_14_Tasa_Traslado.getValue().equals("")|| !txt_Factura_14_Importe_Traslado.getValue().equals("") || gb_Factura_13_Imp_Rete.isOpen()|| !txt_Factura_13_Impuesto_Rete.getValue().equals("")|| !txt_Factura_13_Importe_Rete.getValue().equals("")){	
        			writer.println("</cfdi:Impuestos>");
        		}
        		
        		
        		String Carta_Porte_00 = "		<cartaporte20:CartaPorte";
        		
//        		if(gb_Comple_CP.isOpen() || gb_00_CP.isOpen() || !lb_CatPort_00_TranspInternac.getValue().equals("")|| !lb_CatPort_00_EntradaSalidaMerc.getValue().equals("")|| lb_CatPort_00_ViaEntradaSalida.getSelectedIndex() > 0 || !txt_CatPort_00_TotalDistRec.getValue().equals("")){
	        	if(lb_CatPort_00_TranspInternac.getSelectedIndex() > 0){
	        		if(lb_CatPort_00_TranspInternac.getSelectedIndex() > 0 ){
	        			
	        		
	        			writer.println("	<cfdi:Complemento>");
	        		
	        		    //Inicia Linea 00 Carta Porte    		
		        		if(!txt_CatPort_00_Version.getValue().equals("")){
		        			Carta_Porte_00 = Carta_Porte_00 + " Version=\""+txt_CatPort_00_Version.getValue()+"\"";
		    			}
		        		
		        		if(lb_CatPort_00_TranspInternac.getSelectedIndex() > 0){
		        			Carta_Porte_00 = Carta_Porte_00 + " TranspInternac=\""+lb_CatPort_00_TranspInternac.getSelectedItem().getLabel()+"\"";
		    			}
		        		
		        		if(lb_CatPort_00_EntradaSalidaMerc.getSelectedIndex() > 0){
		        			Carta_Porte_00 = Carta_Porte_00 + " EntradaSalidaMerc=\""+lb_CatPort_00_EntradaSalidaMerc.getSelectedItem().getLabel()+"\"";
		    			}
		        		
		        		if(lb_CatPort_00_ViaEntradaSalida.getSelectedIndex() > 0){
		        			
		        			String[] LisViaEntradaSalida = lb_CatPort_00_ViaEntradaSalida.getSelectedItem().getLabel().split("-");
			        		
			        		 String ViaEntradaSalida = "";
							   
			 				for (int i = 0; i < LisViaEntradaSalida.length; i++) {
			 							
			 					ViaEntradaSalida = LisViaEntradaSalida[0];
			 					System.out.println(LisViaEntradaSalida[0]); 
			 				}
		        			Carta_Porte_00 = Carta_Porte_00 + " ViaEntradaSalida=\""+ViaEntradaSalida+"\"";
		    			}
		        		
		        		if(!dbb_CatPort_00_TotalDistRec.getValue().equals("")){
		        			Carta_Porte_00 = Carta_Porte_00 + " TotalDistRec=\""+dbb_CatPort_00_TotalDistRec.getValue()+"\"";
		    			}
		        		
		        		Carta_Porte_00 = Carta_Porte_00 + ">";
		        		
		        		writer.println(Carta_Porte_00);
		        		
		        		//Termina Linea 00 Carta Porte
		        		
		        		//Inicia Linea 01 Ubicacion Carta Porte   
		        		
		        		String Carta_Ubicacion_01 = "			<cartaporte20:Ubicacion ";
		        		
		        		writer.println("		<cartaporte20:Ubicaciones>");
		        		
	//	        		if(gb_01_CP.isOpen() || !txt_CatPort_01_DistanciaRecorrida.getValue().equals("")|| lb_CatPort_01_TipoEstacion.getSelectedIndex() > 0 ){
		        		if(!txt_CatPort_01_DistanciaRecorrida.getValue().equals("")|| lb_CatPort_01_TipoEstacion.getSelectedIndex() > 0 ){
		        			
		        			if(lb_CatPort_01_TipoEstacion.getSelectedIndex() > 0){
			        			
			        			String[] LisTipoEstacion = lb_CatPort_01_TipoEstacion.getSelectedItem().getLabel().split("-");
				        		
				        		 String TipoEstacion = "";
								   
				 				for (int i = 0; i < LisTipoEstacion.length; i++) {
				 							
				 					TipoEstacion = LisTipoEstacion[0];
				 					System.out.println(LisTipoEstacion[0]); 
				 				}
				 				Carta_Ubicacion_01 = Carta_Ubicacion_01 + " TipoEstacion=\""+TipoEstacion+"\"";
			    			}
			        		
			        		if(!txt_CatPort_01_DistanciaRecorrida.getValue().equals("")){
			        			Carta_Ubicacion_01 = Carta_Ubicacion_01 + " DistanciaRecorrida=\""+txt_CatPort_01_DistanciaRecorrida.getValue()+"\"";
			    			}
			        			
			        		Carta_Ubicacion_01 = Carta_Ubicacion_01 + ">";
			        		
			        		writer.println(Carta_Ubicacion_01);
			        		
				        		//Inicia Linea 02 Ubicacion Origen Carta Porte 
			        			String Carta_Ubicacion_02_Origen = "				<cartaporte20:Origen";
	//		        			if(gb_02_CP.isOpen() || !txt_CatPort_02_Ubi_Ori_IDOrigen.getValue().equals("")|| !txt_CatPort_02_Ubi_Ori_RFCRemitente.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NombreRemitente.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NumRegIdTrib.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_ResidenciaFiscal.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NumEstacion.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NombreEstacion.getValue().equals("") || !lb_CatPort_02_Ubi_Ori_NavegacionTrafico.getValue().equals("") || !dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getValue().equals("") ){
			        			if(!txt_CatPort_02_Ubi_Ori_IDOrigen.getValue().equals("")|| !txt_CatPort_02_Ubi_Ori_RFCRemitente.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NombreRemitente.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NumRegIdTrib.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_ResidenciaFiscal.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NumEstacion.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NombreEstacion.getValue().equals("") || lb_CatPort_02_Ubi_Ori_NavegacionTrafico.getSelectedIndex() > 0 || !dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getValue().equals("") ){
			        				
			        			       				
			        				if(!txt_CatPort_02_Ubi_Ori_IDOrigen.getValue().equals("")){
			        					Carta_Ubicacion_02_Origen = Carta_Ubicacion_02_Origen + " IDOrigen=\""+txt_CatPort_02_Ubi_Ori_IDOrigen.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_02_Ubi_Ori_RFCRemitente.getValue().equals("")){
			        					Carta_Ubicacion_02_Origen = Carta_Ubicacion_02_Origen + " RFCRemitente=\""+txt_CatPort_02_Ubi_Ori_RFCRemitente.getValue().toUpperCase()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_02_Ubi_Ori_NombreRemitente.getValue().equals("")){
			        					Carta_Ubicacion_02_Origen = Carta_Ubicacion_02_Origen + " NombreRemitente=\""+txt_CatPort_02_Ubi_Ori_NombreRemitente.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_02_Ubi_Ori_NumRegIdTrib.getValue().equals("")){
			        					Carta_Ubicacion_02_Origen = Carta_Ubicacion_02_Origen + " NumRegIdTrib=\""+txt_CatPort_02_Ubi_Ori_NumRegIdTrib.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_02_Ubi_Ori_ResidenciaFiscal.getValue().equals("")){
			        					Carta_Ubicacion_02_Origen = Carta_Ubicacion_02_Origen + " ResidenciaFiscal=\""+txt_CatPort_02_Ubi_Ori_ResidenciaFiscal.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_02_Ubi_Ori_NumEstacion.getValue().equals("")){
			        					Carta_Ubicacion_02_Origen = Carta_Ubicacion_02_Origen + " NumEstacion=\""+txt_CatPort_02_Ubi_Ori_NumEstacion.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_02_Ubi_Ori_NombreEstacion.getValue().equals("")){
			        					Carta_Ubicacion_02_Origen = Carta_Ubicacion_02_Origen + " NombreEstacion=\""+txt_CatPort_02_Ubi_Ori_NombreEstacion.getValue()+"\"";
					    			}
			        				
			        				if(lb_CatPort_02_Ubi_Ori_NavegacionTrafico.getSelectedIndex() > 0){
			        					Carta_Ubicacion_02_Origen = Carta_Ubicacion_02_Origen + " NavegacionTrafico=\""+lb_CatPort_02_Ubi_Ori_NavegacionTrafico.getSelectedItem().getLabel()+"\"";
					    			}
			        				
			        				if(!dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getText().equals("")){
			        					Carta_Ubicacion_02_Origen = Carta_Ubicacion_02_Origen + " FechaHoraSalida=\""+dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T")+"\"";
					    			}
	//		        				System.out.println("Fecha Texto: " + dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T"));
			        				Carta_Ubicacion_02_Origen = Carta_Ubicacion_02_Origen + "/>";
					        		
					        		writer.println(Carta_Ubicacion_02_Origen);
			        			}
			        			
			        			
				        		//Termina Linea 02 Ubicacion Origen Carta Porte 
			        			
			        			//Inicia Linea 04 Ubicacion Domicilio Destino Carta Porte 
			        			
			        			String Carta_Ubicacion_04_4_Domicilio = "				<cartaporte20:Domicilio";
	//		        			if(gb_04_4_CP.isOpen() || !txt_CatPort_04_4_Ubi_Direc_Calle.getValue().equals("")|| !txt_CatPort_04_4_Ubi_Direc_NumeroExterior.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_NumeroInterior.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Colonia.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Localidad.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Referencia.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Municipio.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Estado.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Pais.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_CodigoPostal.getValue().equals("")){
			        			if(!txt_CatPort_04_4_Ubi_Direc_Calle.getValue().equals("")|| !txt_CatPort_04_4_Ubi_Direc_NumeroExterior.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_NumeroInterior.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Colonia.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Localidad.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Referencia.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Municipio.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Estado.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Pais.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_CodigoPostal.getValue().equals("")){
	
			        				
			        				if(!txt_CatPort_04_4_Ubi_Direc_Calle.getValue().equals("")){
			        					Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + " Calle=\""+txt_CatPort_04_4_Ubi_Direc_Calle.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_4_Ubi_Direc_NumeroExterior.getValue().equals("")){
			        					Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + " NumeroExterior=\""+txt_CatPort_04_4_Ubi_Direc_NumeroExterior.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_4_Ubi_Direc_NumeroInterior.getValue().equals("")){
			        					Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + " NumeroInterior=\""+txt_CatPort_04_4_Ubi_Direc_NumeroInterior.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_4_Ubi_Direc_Colonia.getValue().equals("")){
			        					Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + " Colonia=\""+txt_CatPort_04_4_Ubi_Direc_Colonia.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_4_Ubi_Direc_Localidad.getValue().equals("")){
			        					Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + " Localidad=\""+txt_CatPort_04_4_Ubi_Direc_Localidad.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_4_Ubi_Direc_Referencia.getValue().equals("")){
			        					Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + " Referencia=\""+txt_CatPort_04_4_Ubi_Direc_Referencia.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_4_Ubi_Direc_Municipio.getValue().equals("")){
			        					Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + " Municipio=\""+txt_CatPort_04_4_Ubi_Direc_Municipio.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_4_Ubi_Direc_Estado.getValue().equals("")){
			        					Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + " Estado=\""+txt_CatPort_04_4_Ubi_Direc_Estado.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_4_Ubi_Direc_Pais.getValue().equals("")){
			        					
			        					Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + " Pais=\""+txt_CatPort_04_4_Ubi_Direc_Pais.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_4_Ubi_Direc_CodigoPostal.getValue().equals("")){
			        					
			        					Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + " CodigoPostal=\""+txt_CatPort_04_4_Ubi_Direc_CodigoPostal.getValue()+"\"";
					    			}
	
	//		        				System.out.println("Fecha Texto: " + dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T"));
			        				Carta_Ubicacion_04_4_Domicilio = Carta_Ubicacion_04_4_Domicilio + "/>";
					        		
					        		writer.println(Carta_Ubicacion_04_4_Domicilio);
			        			}
			        			
			        			//Termina Linea 04 Ubicacion Domicilio Destino Carta Porte 
				        		
				        					        		
			        		writer.println("			</cartaporte:Ubicacion>");
		        		}
		        		
		        		if(!txt_CatPort_01_DistanciaRecorrida.getValue().equals("")|| lb_CatPort_01_TipoEstacion.getSelectedIndex() > 0 ){
		        			
		        			  		
			        		writer.println(Carta_Ubicacion_01);
			        		
				        	      		
				        		//Inicia Linea 03 Ubicacion Destino Carta Porte 
				        		
			        			String Carta_Ubicacion_03_Destino = "				<cartaporte20:Destino";
	//		        			if(gb_03_CP.isOpen() || !txt_CatPort_03_Ubi_Dest_IDDestino.getValue().equals("")|| !txt_CatPort_03_Ubi_Dest_RFCDestinatario.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NombreDestinatario.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NumRegIdTrib.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_ResidenciaFiscal.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NumEstacion.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NombreEstacion.getValue().equals("") || !lb_CatPort_03_Ubi_Dest_NavegacionTrafico.getValue().equals("") || !dt_CatPort_03_Ubi_Dest_FechaHoraProgLlegada.getValue().equals("") ){
			        			if(!txt_CatPort_03_Ubi_Dest_IDDestino.getValue().equals("")|| !txt_CatPort_03_Ubi_Dest_RFCDestinatario.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NombreDestinatario.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NumRegIdTrib.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_ResidenciaFiscal.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NumEstacion.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NombreEstacion.getValue().equals("") || lb_CatPort_03_Ubi_Dest_NavegacionTrafico.getSelectedIndex() > 0 || !dt_CatPort_03_Ubi_Dest_FechaHoraProgLlegada.getValue().equals("") ){
	
			        			
			        				if(!txt_CatPort_03_Ubi_Dest_IDDestino.getValue().equals("")){
			        					Carta_Ubicacion_03_Destino = Carta_Ubicacion_03_Destino + " IDDestino=\""+txt_CatPort_03_Ubi_Dest_IDDestino.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_03_Ubi_Dest_RFCDestinatario.getValue().equals("")){
			        					Carta_Ubicacion_03_Destino = Carta_Ubicacion_03_Destino + " RFCDestinatario=\""+txt_CatPort_03_Ubi_Dest_RFCDestinatario.getValue().toUpperCase()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_03_Ubi_Dest_NombreDestinatario.getValue().equals("")){
			        					Carta_Ubicacion_03_Destino = Carta_Ubicacion_03_Destino + " NombreDestinatario=\""+txt_CatPort_03_Ubi_Dest_NombreDestinatario.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_03_Ubi_Dest_NumRegIdTrib.getValue().equals("")){
			        					Carta_Ubicacion_03_Destino = Carta_Ubicacion_03_Destino + " NumRegIdTrib=\""+txt_CatPort_03_Ubi_Dest_NumRegIdTrib.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_03_Ubi_Dest_ResidenciaFiscal.getValue().equals("")){
			        					Carta_Ubicacion_03_Destino = Carta_Ubicacion_03_Destino + " ResidenciaFiscal=\""+txt_CatPort_03_Ubi_Dest_ResidenciaFiscal.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_03_Ubi_Dest_NumEstacion.getValue().equals("")){
			        					Carta_Ubicacion_03_Destino = Carta_Ubicacion_03_Destino + " NumEstacion=\""+txt_CatPort_03_Ubi_Dest_NumEstacion.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_03_Ubi_Dest_NombreEstacion.getValue().equals("")){
			        					Carta_Ubicacion_03_Destino = Carta_Ubicacion_03_Destino + " NombreEstacion=\""+txt_CatPort_03_Ubi_Dest_NombreEstacion.getValue()+"\"";
					    			}
			        				
			        				if(lb_CatPort_03_Ubi_Dest_NavegacionTrafico.getSelectedIndex() > 0){
			        					Carta_Ubicacion_03_Destino = Carta_Ubicacion_03_Destino + " NavegacionTrafico=\""+lb_CatPort_03_Ubi_Dest_NavegacionTrafico.getSelectedItem().getLabel()+"\"";
					    			}
			        				
			        				if(!dt_CatPort_03_Ubi_Dest_FechaHoraProgLlegada.getText().equals("")){
			        					
			        					Carta_Ubicacion_03_Destino = Carta_Ubicacion_03_Destino + " FechaHoraProgLlegada=\""+dt_CatPort_03_Ubi_Dest_FechaHoraProgLlegada.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T")+"\"";
					    			}
	//		        				System.out.println("Fecha Texto: " + dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T"));
			        				Carta_Ubicacion_03_Destino = Carta_Ubicacion_03_Destino + "/>";
					        		
					        		writer.println(Carta_Ubicacion_03_Destino);
			        			}
			        			
				        		//Termina Linea 03 Ubicacion Destino Carta Porte 
			        		
			        			//Inicia Linea 04 Ubicacion Domicilio Destino Carta Porte 
			        			
			        			String Carta_Ubicacion_04_Domicilio = "				<cartaporte20:Domicilio";
	//		        			if(gb_04_CP.isOpen() || !txt_CatPort_04_Ubi_Direc_Calle.getValue().equals("")|| !txt_CatPort_04_Ubi_Direc_NumeroExterior.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_NumeroInterior.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Colonia.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Localidad.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Referencia.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Municipio.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Estado.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Pais.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_CodigoPostal.getValue().equals("")){
			        			if(!txt_CatPort_04_Ubi_Direc_Calle.getValue().equals("")|| !txt_CatPort_04_Ubi_Direc_NumeroExterior.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_NumeroInterior.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Colonia.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Localidad.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Referencia.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Municipio.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Estado.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Pais.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_CodigoPostal.getValue().equals("")){
	
			        				
			        				if(!txt_CatPort_04_Ubi_Direc_Calle.getValue().equals("")){
			        					Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + " Calle=\""+txt_CatPort_04_Ubi_Direc_Calle.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_Ubi_Direc_NumeroExterior.getValue().equals("")){
			        					Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + " NumeroExterior=\""+txt_CatPort_04_Ubi_Direc_NumeroExterior.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_Ubi_Direc_NumeroInterior.getValue().equals("")){
			        					Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + " NumeroInterior=\""+txt_CatPort_04_Ubi_Direc_NumeroInterior.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_Ubi_Direc_Colonia.getValue().equals("")){
			        					Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + " Colonia=\""+txt_CatPort_04_Ubi_Direc_Colonia.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_Ubi_Direc_Localidad.getValue().equals("")){
			        					Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + " Localidad=\""+txt_CatPort_04_Ubi_Direc_Localidad.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_Ubi_Direc_Referencia.getValue().equals("")){
			        					Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + " Referencia=\""+txt_CatPort_04_Ubi_Direc_Referencia.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_Ubi_Direc_Municipio.getValue().equals("")){
			        					Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + " Municipio=\""+txt_CatPort_04_Ubi_Direc_Municipio.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_Ubi_Direc_Estado.getValue().equals("")){
			        					Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + " Estado=\""+txt_CatPort_04_Ubi_Direc_Estado.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_Ubi_Direc_Pais.getValue().equals("")){
			        					
			        					Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + " Pais=\""+txt_CatPort_04_Ubi_Direc_Pais.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_04_Ubi_Direc_CodigoPostal.getValue().equals("")){
			        					
			        					Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + " CodigoPostal=\""+txt_CatPort_04_Ubi_Direc_CodigoPostal.getValue()+"\"";
					    			}
	
	//		        				System.out.println("Fecha Texto: " + dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T"));
			        				Carta_Ubicacion_04_Domicilio = Carta_Ubicacion_04_Domicilio + "/>";
					        		
					        		writer.println(Carta_Ubicacion_04_Domicilio);
			        			}
			        			
			        			//Termina Linea 04 Ubicacion Domicilio Destino Carta Porte 
			        		
			        		writer.println("			</cartaporte:Ubicacion>");
		        		}
		        		
		        		  
	        		
		        		writer.println("		</cartaporte:Ubicaciones>");
		        		
		        		//Termina Linea 01 Ubicacion Carta Porte
		        		
		        		//Inicia Linea 05 Mercancias General Carta Porte
		        		
		        		String Carta_05_Mercancias = "		<cartaporte20:Mercancias";
	//        			if(gb_05_CP.isOpen() || !txt_CatPort_05_Merca_Gral_PesoBrutoTotal.getValue().equals("")|| !txt_CatPort_05_Merca_Gral_UnidadPeso.getValue().equals("") || !txt_CatPort_05_Merca_Gral_PesoNetoTotal.getValue().equals("") || !txt_CatPort_05_Merca_Gral_NumTotalMercancias.getValue().equals("") || !txt_CatPort_05_Merca_Gral_CargoPorTasacion.getValue().equals("")){
	        			if(!txt_CatPort_05_Merca_Gral_PesoBrutoTotal.getValue().equals("")|| !txt_CatPort_05_Merca_Gral_UnidadPeso.getValue().equals("") || !txt_CatPort_05_Merca_Gral_PesoNetoTotal.getValue().equals("") || !txt_CatPort_05_Merca_Gral_NumTotalMercancias.getValue().equals("") || !txt_CatPort_05_Merca_Gral_CargoPorTasacion.getValue().equals("")){
	
	        				
	        			
	        				if(!txt_CatPort_05_Merca_Gral_PesoBrutoTotal.getValue().equals("")){
	        					Carta_05_Mercancias = Carta_05_Mercancias + " PesoBrutoTotal=\""+txt_CatPort_05_Merca_Gral_PesoBrutoTotal.getValue()+"\"";
			    			}
	        				
	        				if(!txt_CatPort_05_Merca_Gral_UnidadPeso.getValue().equals("")){
	        					Carta_05_Mercancias = Carta_05_Mercancias + " UnidadPeso=\""+txt_CatPort_05_Merca_Gral_UnidadPeso.getValue()+"\"";
			    			}
	        				
	        				if(!txt_CatPort_05_Merca_Gral_PesoNetoTotal.getValue().equals("")){
	        					Carta_05_Mercancias = Carta_05_Mercancias + " PesoNetoTotal=\""+txt_CatPort_05_Merca_Gral_PesoNetoTotal.getValue()+"\"";
			    			}
	        				
	        				if(!txt_CatPort_05_Merca_Gral_NumTotalMercancias.getValue().equals("")){
	        					Carta_05_Mercancias = Carta_05_Mercancias + " NumTotalMercancias=\""+txt_CatPort_05_Merca_Gral_NumTotalMercancias.getValue()+"\"";
			    			}
	        				
	        				if(!txt_CatPort_05_Merca_Gral_CargoPorTasacion.getValue().equals("")){
	        					Carta_05_Mercancias = Carta_05_Mercancias + " CargoPorTasacion=\""+txt_CatPort_05_Merca_Gral_CargoPorTasacion.getValue()+"\"";
			    			}
	        				
	        				
	//        				System.out.println("Fecha Texto: " + dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T"));
	        				Carta_05_Mercancias = Carta_05_Mercancias + ">";
			        		
			        		writer.println(Carta_05_Mercancias);
			        		
				        		//Inicia Linea 06 Mercancias Carta Porte
				        		
				        		String Carta_06_Mercancia = "			<cartaporte20:Mercancia";
	//			        		if(gb_06_CP.isOpen() || !txt_CatPort_06_Merca_BienesTransp.getValue().equals("")|| !txt_CatPort_06_Merca_ClaveSTCC.getValue().equals("") || !txt_CatPort_05_Merca_Gral_PesoNetoTotal.getValue().equals("") || !txt_CatPort_06_Merca_Descripcion.getValue().equals("") || !txt_CatPort_06_Merca_Cantidad.getValue().equals("") || !txt_CatPort_06_Merca_ClaveUnidad.getValue().equals("") || !txt_CatPort_06_Merca_Unidad.getValue().equals("") || !txt_CatPort_06_Merca_Dimensiones.getValue().equals("") || !txt_CatPort_06_Merca_MaterialPeligroso.getValue().equals("") || !txt_CatPort_06_Merca_CveMaterialPeligroso.getValue().equals("") || !txt_CatPort_06_Merca_Embalaje.getValue().equals("") || !txt_CatPort_06_Merca_DescripEmbalaje.getValue().equals("") || !txt_CatPort_06_Merca_PesoEnKg.getValue().equals("") || !txt_CatPort_06_Merca_ValorMercancia.getValue().equals("") || !txt_CatPort_06_Merca_Moneda.getValue().equals("") || !txt_CatPort_06_Merca_FraccionArancelaria.getValue().equals("") || !txt_CatPort_06_Merca_UUIDComercioExt.getValue().equals("")){
				        		if(!txt_CatPort_06_Merca_BienesTransp.getValue().equals("")|| !txt_CatPort_06_Merca_ClaveSTCC.getValue().equals("") || !txt_CatPort_05_Merca_Gral_PesoNetoTotal.getValue().equals("") || !txt_CatPort_06_Merca_Descripcion.getValue().equals("") || !txt_CatPort_06_Merca_Cantidad.getValue().equals("") || !txt_CatPort_06_Merca_ClaveUnidad.getValue().equals("") || !txt_CatPort_06_Merca_Unidad.getValue().equals("") || !txt_CatPort_06_Merca_Dimensiones.getValue().equals("") || !txt_CatPort_06_Merca_MaterialPeligroso.getValue().equals("") || !txt_CatPort_06_Merca_CveMaterialPeligroso.getValue().equals("") || !txt_CatPort_06_Merca_Embalaje.getValue().equals("") || !txt_CatPort_06_Merca_DescripEmbalaje.getValue().equals("") || !txt_CatPort_06_Merca_PesoEnKg.getValue().equals("") || !txt_CatPort_06_Merca_ValorMercancia.getValue().equals("") || !txt_CatPort_06_Merca_Moneda.getValue().equals("") || !txt_CatPort_06_Merca_FraccionArancelaria.getValue().equals("") || !txt_CatPort_06_Merca_UUIDComercioExt.getValue().equals("")){
				        			
				        			
			        				if(!txt_CatPort_06_Merca_BienesTransp.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " BienesTransp=\""+txt_CatPort_06_Merca_BienesTransp.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_ClaveSTCC.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " ClaveSTCC=\""+txt_CatPort_06_Merca_ClaveSTCC.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_Descripcion.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " Descripcion=\""+txt_CatPort_06_Merca_Descripcion.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_Cantidad.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " Cantidad=\""+txt_CatPort_06_Merca_Cantidad.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_ClaveUnidad.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " ClaveUnidad=\""+txt_CatPort_06_Merca_ClaveUnidad.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_Unidad.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " Unidad=\""+txt_CatPort_06_Merca_Unidad.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_Dimensiones.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " Dimensiones=\""+txt_CatPort_06_Merca_Dimensiones.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_MaterialPeligroso.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " MaterialPeligroso=\""+txt_CatPort_06_Merca_MaterialPeligroso.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_CveMaterialPeligroso.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " CveMaterialPeligroso=\""+txt_CatPort_06_Merca_CveMaterialPeligroso.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_Embalaje.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " Embalaje=\""+txt_CatPort_06_Merca_Embalaje.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_DescripEmbalaje.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " DescripEmbalaje=\""+txt_CatPort_06_Merca_DescripEmbalaje.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_PesoEnKg.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " PesoEnKg=\""+txt_CatPort_06_Merca_PesoEnKg.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_ValorMercancia.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " ValorMercancia=\""+txt_CatPort_06_Merca_ValorMercancia.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_Moneda.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " Moneda=\""+txt_CatPort_06_Merca_Moneda.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_FraccionArancelaria.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " FraccionArancelaria=\""+txt_CatPort_06_Merca_FraccionArancelaria.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_06_Merca_UUIDComercioExt.getValue().equals("")){
			        					Carta_06_Mercancia = Carta_06_Mercancia + " UUIDComercioExt=\""+txt_CatPort_06_Merca_UUIDComercioExt.getValue()+"\"";
					    			}
			        				
			        	
			        				
			        				Carta_06_Mercancia = Carta_06_Mercancia + ">";
					        		
					        		writer.println(Carta_06_Mercancia);
					        		
					        		
						        		//Inicia Linea 07 Mercancias CantidadTransporta Carta Porte 
					        		
						        		String Carta_07_Mercancia_CantidadTransporta = "				<cartaporte20:CantidadTransporta";
	//					        		if(gb_07_CP.isOpen() || !txt_CatPort_07_Merca_CantTrans_Cantidad.getValue().equals("")|| !txt_CatPort_07_Merca_CantTrans_IDOrigen.getValue().equals("") || !txt_CatPort_07_Merca_CantTrans_IDDestino.getValue().equals("") || !lb_CatPort_07_Merca_CantTrans_CvesTransporte.getValue().equals("")){
						        		if(!txt_CatPort_07_Merca_CantTrans_Cantidad.getValue().equals("")|| !txt_CatPort_07_Merca_CantTrans_IDOrigen.getValue().equals("") || !txt_CatPort_07_Merca_CantTrans_IDDestino.getValue().equals("") || lb_CatPort_07_Merca_CantTrans_CvesTransporte.getSelectedIndex() > 0){
						        			
						        			
					        				if(!txt_CatPort_07_Merca_CantTrans_Cantidad.getValue().equals("")){
					        					Carta_07_Mercancia_CantidadTransporta = Carta_07_Mercancia_CantidadTransporta + " Cantidad=\""+txt_CatPort_07_Merca_CantTrans_Cantidad.getValue()+"\"";
							    			}
					        				
					        				if(!txt_CatPort_07_Merca_CantTrans_IDOrigen.getValue().equals("")){
					        					Carta_07_Mercancia_CantidadTransporta = Carta_07_Mercancia_CantidadTransporta + " IDOrigen=\""+txt_CatPort_07_Merca_CantTrans_IDOrigen.getValue()+"\"";
							    			}
					        				
					        				if(!txt_CatPort_07_Merca_CantTrans_IDDestino.getValue().equals("")){
					        					Carta_07_Mercancia_CantidadTransporta = Carta_07_Mercancia_CantidadTransporta + " IDDestino=\""+txt_CatPort_07_Merca_CantTrans_IDDestino.getValue()+"\"";
							    			}
					        				
					        				if(lb_CatPort_07_Merca_CantTrans_CvesTransporte.getSelectedIndex() > 0){
					    	        			
					    	        			String[] LisClaveTransporte = lb_CatPort_07_Merca_CantTrans_CvesTransporte.getSelectedItem().getLabel().split("-");
					    		        		
					    		        		 String ClaveTransporte = "";
					    						   
					    		 				for (int i = 0; i < LisClaveTransporte.length; i++) {
					    		 							
					    		 					ClaveTransporte = LisClaveTransporte[0];
					    		 					System.out.println(LisClaveTransporte[0]); 
					    		 				}
					    		 				Carta_07_Mercancia_CantidadTransporta = Carta_07_Mercancia_CantidadTransporta + " CvesTransporte=\""+ClaveTransporte+"\"";
					    	    			}
					        				
					        				
					        				
					        				
					        				Carta_07_Mercancia_CantidadTransporta = Carta_07_Mercancia_CantidadTransporta + "/>";
							        		
							        		writer.println(Carta_07_Mercancia_CantidadTransporta);
						        		}
					        			
						        		//Termina Linea 07 Mercancias CantidadTransporta Carta Porte 
						        		
						        		//Inicia Linea 08 Mercancias CantidadTransporta Carta Porte 
						        		
						        		String Carta_08_Mercancia_DetalleMercancia = "				<cartaporte20:DetalleMercancia";
	//					        		if(gb_08_CP.isOpen() || !txt_CatPort_08_Merca_DetaMerc_UnidadPeso.getValue().equals("")|| !txt_CatPort_08_Merca_DetaMerc_PesoBruto.getValue().equals("") || !txt_CatPort_08_Merca_DetaMerc_PesoNeto.getValue().equals("") || !txt_CatPort_08_Merca_DetaMerc_PesoTara.getValue().equals("") || !txt_CatPort_08_Merca_DetaMerc_NumPiezas.getValue().equals("")){
						        		if(!txt_CatPort_08_Merca_DetaMerc_UnidadPeso.getValue().equals("")|| !txt_CatPort_08_Merca_DetaMerc_PesoBruto.getValue().equals("") || !txt_CatPort_08_Merca_DetaMerc_PesoNeto.getValue().equals("") || !txt_CatPort_08_Merca_DetaMerc_PesoTara.getValue().equals("") || !txt_CatPort_08_Merca_DetaMerc_NumPiezas.getValue().equals("")){
				
						        			
						        			
					        				if(!txt_CatPort_08_Merca_DetaMerc_UnidadPeso.getValue().equals("")){
					        					Carta_08_Mercancia_DetalleMercancia = Carta_08_Mercancia_DetalleMercancia + " UnidadPeso=\""+txt_CatPort_08_Merca_DetaMerc_UnidadPeso.getValue()+"\"";
							    			}
					        				
					        				if(!txt_CatPort_08_Merca_DetaMerc_PesoBruto.getValue().equals("")){
					        					Carta_08_Mercancia_DetalleMercancia = Carta_08_Mercancia_DetalleMercancia + " PesoBruto=\""+txt_CatPort_08_Merca_DetaMerc_PesoBruto.getValue()+"\"";
							    			}
					        				
					        				if(!txt_CatPort_08_Merca_DetaMerc_PesoNeto.getValue().equals("")){
					        					Carta_08_Mercancia_DetalleMercancia = Carta_08_Mercancia_DetalleMercancia + " PesoNeto=\""+txt_CatPort_08_Merca_DetaMerc_PesoNeto.getValue()+"\"";
							    			}
					        				
					        				if(!txt_CatPort_08_Merca_DetaMerc_PesoTara.getValue().equals("")){
					        					Carta_08_Mercancia_DetalleMercancia = Carta_08_Mercancia_DetalleMercancia + " PesoTara=\""+txt_CatPort_08_Merca_DetaMerc_PesoTara.getValue()+"\"";
							    			}
					        				
					        				if(!txt_CatPort_08_Merca_DetaMerc_NumPiezas.getValue().equals("")){
					        					Carta_08_Mercancia_DetalleMercancia = Carta_08_Mercancia_DetalleMercancia + " NumPiezas=\""+txt_CatPort_08_Merca_DetaMerc_NumPiezas.getValue()+"\"";
							    			}
					        				
					        				
					        				Carta_08_Mercancia_DetalleMercancia = Carta_08_Mercancia_DetalleMercancia + "/>";
							        		
							        		writer.println(Carta_08_Mercancia_DetalleMercancia);
						        		}
					        			
						        		//Termina Linea 08 Mercancias CantidadTransporta Carta Porte 
					        		
					        	
					        		
					        		
					        	writer.println("			</cartaporte:Mercancia>");
				        		
				        		//Termina Linea 06 Mercancias Carta Porte 
					        	
					        	//Inicia Linea 09 AutotransporteFederal Carta Porte 
					        	
					        	String Carta_09_AutotransporteFederal= "			<cartaporte20:AutotransporteFederal";
	//			        		if(gb_09_CP.isOpen() || !txt_CatPort_09_Merca_AutoTransF_PermSCT.getValue().equals("")|| !txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT.getValue().equals("") || !txt_CatPort_09_Merca_AutoTransF_NombreAseg.getValue().equals("") || !txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro.getValue().equals("") ){
				        		if(!txt_CatPort_09_Merca_AutoTransF_PermSCT.getValue().equals("")|| !txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT.getValue().equals("") || !txt_CatPort_09_Merca_AutoTransF_NombreAseg.getValue().equals("") || !txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro.getValue().equals("") ){
				        			
				        			
			        				if(!txt_CatPort_09_Merca_AutoTransF_PermSCT.getValue().equals("")){
			        					Carta_09_AutotransporteFederal = Carta_09_AutotransporteFederal + " PermSCT=\""+txt_CatPort_09_Merca_AutoTransF_PermSCT.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT.getValue().equals("")){
			        					Carta_09_AutotransporteFederal = Carta_09_AutotransporteFederal + " NumPermisoSCT=\""+txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_09_Merca_AutoTransF_NombreAseg.getValue().equals("")){
			        					Carta_09_AutotransporteFederal = Carta_09_AutotransporteFederal + " NombreAseg=\""+txt_CatPort_09_Merca_AutoTransF_NombreAseg.getValue()+"\"";
					    			}
			        				
			        				if(!txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro.getValue().equals("")){
			        					Carta_09_AutotransporteFederal = Carta_09_AutotransporteFederal + " NumPolizaSeguro=\""+txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro.getValue()+"\"";
					    			}
			        				
			        				
			        				
			        				Carta_09_AutotransporteFederal = Carta_09_AutotransporteFederal + ">";
					        		
					        		writer.println(Carta_09_AutotransporteFederal);
					        		
						        			//Inicia Linea 10 IdentificacionVehicular Carta porte
						        			
							        		String Carta_10_IdentificacionVehicular= "				<cartaporte20:IdentificacionVehicular";
	//						        		if(gb_10_CP.isOpen() || !txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular.getValue().equals("")|| !txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM.getValue().equals("") || !txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM.getValue().equals("") ){
							        		if(!txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular.getValue().equals("")|| !txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM.getValue().equals("") || !txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM.getValue().equals("") ){
							        			
						        				if(!txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular.getValue().equals("")){
						        					Carta_10_IdentificacionVehicular = Carta_10_IdentificacionVehicular + " ConfigVehicular=\""+txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM.getValue().equals("")){
						        					Carta_10_IdentificacionVehicular = Carta_10_IdentificacionVehicular + " PlacaVM=\""+txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM.getValue().equals("")){
						        					Carta_10_IdentificacionVehicular = Carta_10_IdentificacionVehicular + " AnioModeloVM=\""+txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM.getValue()+"\"";
								    			}
						        				
						        				
						        				Carta_10_IdentificacionVehicular = Carta_10_IdentificacionVehicular + "/>";
								        		
								        		writer.println(Carta_10_IdentificacionVehicular);
								        		
							        		}
							        		
							        		//Termina Linea 10 IdentificacionVehicular Carta porte
							        		
						        			//Inicia Linea 10 IdentificacionVehicular Carta porte
						        			
							        		String Carta_11_Remolque= "					<cartaporte20:Remolque";
	//						        		if(gb_11_CP.isOpen() || !txt_CatPort_11_Merca_AutoTransF_Remolque_SubTipoRem.getValue().equals("")|| !txt_CatPort_11_Merca_AutoTransF_Remolque_Placagb_12_CP.getValue().equals("") ){
							        		if(!txt_CatPort_11_Merca_AutoTransF_Remolque_SubTipoRem.getValue().equals("") || !txt_CatPort_11_Merca_AutoTransF_Remolque_Placa.getValue().equals("") ){	
							        			writer.println("			<cartaporte20:Remolques>");
					
							        				if(!txt_CatPort_11_Merca_AutoTransF_Remolque_SubTipoRem.getValue().equals("")){
							        					Carta_11_Remolque = Carta_11_Remolque + " SubTipoRem=\""+txt_CatPort_11_Merca_AutoTransF_Remolque_SubTipoRem.getValue()+"\"";
									    			}
							        				
							        				if(!txt_CatPort_11_Merca_AutoTransF_Remolque_Placa.getValue().equals("")){
							        					Carta_11_Remolque = Carta_11_Remolque + " Placa=\""+txt_CatPort_11_Merca_AutoTransF_Remolque_Placa.getValue()+"\"";
									    			}
							        				
							        				
							        				Carta_11_Remolque = Carta_11_Remolque + "/>";
									        		
									        		writer.println(Carta_11_Remolque);
								        		
								        		writer.println("			</cartaporte:Remolques>");
								        		
							        		}
							        		
							        		//Termina Linea 10 IdentificacionVehicular Carta porte
						        		
						        		
					        		
					        		writer.println("			</cartaporte:AutotransporteFederal>");
				        		}
					        	
					        	//Termina Linea 09 AutotransporteFederal Carta Porte 
				        		
				        		
				        		//Inicia Linea 12 Transporte Maritimo Carta Porte
				        		
				        		String Carta_12_TransporteMaritimo= "			<cartaporte20:TransporteMaritimo";
	//			        		if(gb_12_CP.isOpen() || !txt_CatPort_12_Merca_TransMaritimo_PermSCT.getValue().equals("")|| !txt_CatPort_12_Merca_TransMaritimo_NumPermisoSCT.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NombreAseg.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumPolizaSeguro.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Matricula.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumeroOMI.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NombreEmbarc.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_TipoCarga.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumCertITC.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Eslora.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Manga.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Calado.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_LineaNaviera.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumViaje.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumConocEmbarc.getValue().equals("") ){
				        		if(!txt_CatPort_12_Merca_TransMaritimo_PermSCT.getValue().equals("")|| !txt_CatPort_12_Merca_TransMaritimo_NumPermisoSCT.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NombreAseg.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumPolizaSeguro.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Matricula.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumeroOMI.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NombreEmbarc.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_TipoCarga.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumCertITC.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Eslora.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Manga.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Calado.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_LineaNaviera.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumViaje.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumConocEmbarc.getValue().equals("") ){
				        			
	//			        			writer.println("			<cartaporte20:Remolques>");
				        			
				        				if(!txt_CatPort_12_Merca_TransMaritimo_PermSCT.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " PermisoSCT=\""+txt_CatPort_12_Merca_TransMaritimo_PermSCT.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NumPermisoSCT.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NumPermisoSCT=\""+txt_CatPort_12_Merca_TransMaritimo_NumPermisoSCT.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NombreAseg.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NombreAseg=\""+txt_CatPort_12_Merca_TransMaritimo_NombreAseg.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NumPolizaSeguro.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NumPolizaSeguro=\""+txt_CatPort_12_Merca_TransMaritimo_NumPolizaSeguro.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " TipoEmbarcacion=\""+txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_Matricula.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " Matricula=\""+txt_CatPort_12_Merca_TransMaritimo_Matricula.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NumeroOMI.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NumeroOMI=\""+txt_CatPort_12_Merca_TransMaritimo_NumeroOMI.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " AnioEmbarcacion=\""+txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NombreEmbarc.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NombreEmbarc=\""+txt_CatPort_12_Merca_TransMaritimo_NombreEmbarc.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NacionalidadEmbarc=\""+txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " UnidadDeArqBruto=\""+txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_TipoCarga.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " TipoCarga=\""+txt_CatPort_12_Merca_TransMaritimo_TipoCarga.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NumCertITC.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NumCertITC=\""+txt_CatPort_12_Merca_TransMaritimo_NumCertITC.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_Eslora.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " Eslora=\""+txt_CatPort_12_Merca_TransMaritimo_Eslora.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_Manga.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " Manga=\""+txt_CatPort_12_Merca_TransMaritimo_Manga.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_Calado.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " Calado=\""+txt_CatPort_12_Merca_TransMaritimo_Calado.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_LineaNaviera.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " LineaNaviera=\""+txt_CatPort_12_Merca_TransMaritimo_LineaNaviera.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NombreAgenteNaviero=\""+txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NumAutorizacionNaviero=\""+txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NumViaje.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NumViaje=\""+txt_CatPort_12_Merca_TransMaritimo_NumViaje.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_12_Merca_TransMaritimo_NumConocEmbarc.getValue().equals("")){
				        					Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + " NumConocEmbarc=\""+txt_CatPort_12_Merca_TransMaritimo_NumConocEmbarc.getValue()+"\"";
						    			}
				        				
				        				
				        				Carta_12_TransporteMaritimo = Carta_12_TransporteMaritimo + ">";
						        		
						        		writer.println(Carta_12_TransporteMaritimo);
					        		
	//				        		writer.println("			</cartaporte:Remolques>");
						        		
						        		//Inicia Linea 13 Transporte Maritimo Contenedor Carta Porte
						        		
						        		String Carta_13_TransporteMaritimo_Contenedor= "				<cartaporte20:Contenedor";
	//					        		if(gb_13_CP.isOpen() || !txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor.getValue().equals("")|| !lb_CatPort_13_Merca_TransMaritimo_Cont_TipoContenedor.getValue().equals("") || !txt_CatPort_13_Merca_TransMaritimo_Cont_NumPrecinto.getValue().equals("")){
						        		if(!txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor.getValue().equals("")|| lb_CatPort_13_Merca_TransMaritimo_Cont_TipoContenedor.getSelectedIndex() > 0 || !txt_CatPort_13_Merca_TransMaritimo_Cont_NumPrecinto.getValue().equals("")){
						        			
	//					        			writer.println("			<cartaporte20:Remolques>");
				
						        				if(!txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor.getValue().equals("")){
						        					Carta_13_TransporteMaritimo_Contenedor = Carta_13_TransporteMaritimo_Contenedor + " MatriculaContenedor=\""+txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor.getValue()+"\"";
								    			}
						        				
						        				if(lb_CatPort_13_Merca_TransMaritimo_Cont_TipoContenedor.getSelectedIndex() > 0){
						    	        			
						    	        			String[] LisTipocContenedor = lb_CatPort_13_Merca_TransMaritimo_Cont_TipoContenedor.getSelectedItem().getLabel().split("-");
						    		        		
						    		        		 String TipocContenedor = "";
						    						   
						    		 				for (int i = 0; i < LisTipocContenedor.length; i++) {
						    		 							
						    		 					TipocContenedor = LisTipocContenedor[0];
						    		 					System.out.println(LisTipocContenedor[0]); 
						    		 				}
						    		 				Carta_13_TransporteMaritimo_Contenedor = Carta_13_TransporteMaritimo_Contenedor + " TipoContenedor=\""+TipocContenedor+"\"";
						    	    			}
						        				
						        				
						        				if(!txt_CatPort_13_Merca_TransMaritimo_Cont_NumPrecinto.getValue().equals("")){
						        					Carta_13_TransporteMaritimo_Contenedor = Carta_13_TransporteMaritimo_Contenedor + " NumPrecinto=\""+txt_CatPort_13_Merca_TransMaritimo_Cont_NumPrecinto.getValue()+"\"";
								    			}
						        				
						        				Carta_13_TransporteMaritimo_Contenedor = Carta_13_TransporteMaritimo_Contenedor + "/>";
								        		
								        		writer.println(Carta_13_TransporteMaritimo_Contenedor);
							        		
							        		
						        		}
						        		
						        		//Termina Linea 13 Transporte Maritimo Contenedor Carta Porte
						        		
						        		
						        		writer.println("			</cartaporte:TransporteMaritimo>");
					        		
				        		}
				        		
				        		//Termina Linea 12 Transporte Maritimo Carta Porte
				        		
				        		//Inicia Linea 14 Transporte Aereo Carta Porte
				        		
				        		String Carta_14_Aereo= "			<cartaporte20:TransporteAereo";
	//			        		if(gb_14_CP.isOpen() || !txt_CatPort_14_Merca_TransAereo_PermSCT.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_NumPermisoSCT.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_MatriculaAeronave.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NombreAseg.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NumPolizaSeguro.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NumeroGuia.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_LugarContrato.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_RFCTransportista.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_CodigoTransportista.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NumRegIdTribTranspor.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalTranspor.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NombreTransportista.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_RFCEmbarcador.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_NumRegIdTribEmbarc.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalEmbarc.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_NombreEmbarcador.getValue().equals("")){
				        		if(!txt_CatPort_14_Merca_TransAereo_PermSCT.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_NumPermisoSCT.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_MatriculaAeronave.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NombreAseg.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NumPolizaSeguro.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NumeroGuia.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_LugarContrato.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_RFCTransportista.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_CodigoTransportista.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NumRegIdTribTranspor.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalTranspor.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NombreTransportista.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_RFCEmbarcador.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_NumRegIdTribEmbarc.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalEmbarc.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_NombreEmbarcador.getValue().equals("")){
				        			
	//			        			writer.println("			<cartaporte20:Remolques>");
		
				        				if(!txt_CatPort_14_Merca_TransAereo_PermSCT.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " PermisoSCT=\""+txt_CatPort_14_Merca_TransAereo_PermSCT.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_NumPermisoSCT.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " NumPermisoSCT=\""+txt_CatPort_14_Merca_TransAereo_NumPermisoSCT.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_MatriculaAeronave.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " MatriculaAeronave=\""+txt_CatPort_14_Merca_TransAereo_MatriculaAeronave.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_NombreAseg.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " NombreAseg=\""+txt_CatPort_14_Merca_TransAereo_NombreAseg.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_NumPolizaSeguro.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " NumPolizaSeguro=\""+txt_CatPort_14_Merca_TransAereo_NumPolizaSeguro.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_NumeroGuia.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " NumeroGuia=\""+txt_CatPort_14_Merca_TransAereo_NumeroGuia.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_LugarContrato.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " LugarContrato=\""+txt_CatPort_14_Merca_TransAereo_LugarContrato.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_RFCTransportista.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " RFCTransportista=\""+txt_CatPort_14_Merca_TransAereo_RFCTransportista.getValue().toUpperCase()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_CodigoTransportista.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " CodigoTransportista=\""+txt_CatPort_14_Merca_TransAereo_CodigoTransportista.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_NumRegIdTribTranspor.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " NumRegIdTranspor=\""+txt_CatPort_14_Merca_TransAereo_NumRegIdTribTranspor.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalTranspor.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " ResidenciaFisalTranspor=\""+txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalTranspor.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_NombreTransportista.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " NombreTransportista=\""+txt_CatPort_14_Merca_TransAereo_NombreTransportista.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_RFCEmbarcador.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " RFCEmbarcador=\""+txt_CatPort_14_Merca_TransAereo_RFCEmbarcador.getValue().toUpperCase()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_NumRegIdTribEmbarc.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " NumRegIdEmbarc=\""+txt_CatPort_14_Merca_TransAereo_NumRegIdTribEmbarc.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalEmbarc.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " ResidenciaFiscalEmbarc=\""+txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalEmbarc.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_14_Merca_TransAereo_NombreEmbarcador.getValue().equals("")){
				        					Carta_14_Aereo = Carta_14_Aereo + " NombreEmbarcador=\""+txt_CatPort_14_Merca_TransAereo_NombreEmbarcador.getValue()+"\"";
						    			}
				        				
				        				
				        				Carta_14_Aereo = Carta_14_Aereo + "/>";
						        		
						        		writer.println(Carta_14_Aereo);
					        		
					        		
				        		}
				        		
				        		//Termina Linea 14 Transporte Aereo Carta Porte
				        		
				        		//Inicia Linea 15 Transporte Ferroviario Carta Porte
				        		
				        		String Carta_15_TransporteFerroviario= "			<cartaporte20:TransporteFerroviario";
	//			        		if(gb_15_CP.isOpen() || !lb_CatPort_15_Merca_TransFerrov_TipoDeServicio.getValue().equals("")|| !txt_CatPort_15_Merca_TransFerrov_NombreAseg.getValue().equals("") || !txt_CatPort_15_Merca_TransFerrov_NumPolizaSeguro.getValue().equals("") || !txt_CatPort_15_Merca_TransFerrov_Concesionario.getValue().equals("")){
				        		if(lb_CatPort_15_Merca_TransFerrov_TipoDeServicio.getSelectedIndex() > 0|| !txt_CatPort_15_Merca_TransFerrov_NombreAseg.getValue().equals("") || !txt_CatPort_15_Merca_TransFerrov_NumPolizaSeguro.getValue().equals("") || !txt_CatPort_15_Merca_TransFerrov_Concesionario.getValue().equals("")){
				        			
	//			        			writer.println("			<cartaporte20:Remolques>");
				        				
					        			if(lb_CatPort_15_Merca_TransFerrov_TipoDeServicio.getSelectedIndex() > 0){
				    	        			
				    	        			String[] LisTipoDeServicio = lb_CatPort_15_Merca_TransFerrov_TipoDeServicio.getSelectedItem().getLabel().split("-");
				    		        		
				    		        		 String TipoDeServicio = "";
				    						   
				    		 				for (int i = 0; i < LisTipoDeServicio.length; i++) {
				    		 							
				    		 					TipoDeServicio = LisTipoDeServicio[0];
				    		 					System.out.println(LisTipoDeServicio[0]); 
				    		 				}
				    		 				Carta_15_TransporteFerroviario = Carta_15_TransporteFerroviario + " TipoDeServicio=\""+TipoDeServicio+"\"";
				    	    			}
				        			
				        				if(!txt_CatPort_15_Merca_TransFerrov_NombreAseg.getValue().equals("")){
				        					Carta_15_TransporteFerroviario = Carta_15_TransporteFerroviario + " NombreAseg=\""+txt_CatPort_15_Merca_TransFerrov_NombreAseg.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_15_Merca_TransFerrov_NumPolizaSeguro.getValue().equals("")){
				        					Carta_15_TransporteFerroviario = Carta_15_TransporteFerroviario + " NumPolizaSeguro=\""+txt_CatPort_15_Merca_TransFerrov_NumPolizaSeguro.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_15_Merca_TransFerrov_Concesionario.getValue().equals("")){
				        					Carta_15_TransporteFerroviario = Carta_15_TransporteFerroviario + " Concesionario=\""+txt_CatPort_15_Merca_TransFerrov_Concesionario.getValue()+"\"";
						    			}
				        				
				        				Carta_15_TransporteFerroviario = Carta_15_TransporteFerroviario + ">";
						        		
						        		writer.println(Carta_15_TransporteFerroviario);
						        		
						        		
							        		//Inicia Linea 16 Transporte Ferroviario Derechos De Paso Carta Porte
							        		
							        		String Carta_16_TransporteFerroviario_DerechosDePaso= "				<cartaporte20:DerechosDePaso";
	//						        		if(gb_16_CP.isOpen() || !txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso.getValue().equals("")|| !txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado.getValue().equals("") ){
							        		if(!txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso.getValue().equals("")|| !txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado.getValue().equals("") ){
							        			
		//					        			writer.println("			<cartaporte20:Remolques>");
					
							        				if(!txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso.getValue().equals("")){
							        					Carta_16_TransporteFerroviario_DerechosDePaso = Carta_16_TransporteFerroviario_DerechosDePaso + " TipoDerechoDePaso=\""+txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso.getValue()+"\"";
									    			}
							        				
							        				if(!txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado.getValue().equals("")){
							        					Carta_16_TransporteFerroviario_DerechosDePaso = Carta_16_TransporteFerroviario_DerechosDePaso + " KilometrajePagado=\""+txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado.getValue()+"\"";
									    			}
							        				
							        				Carta_16_TransporteFerroviario_DerechosDePaso = Carta_16_TransporteFerroviario_DerechosDePaso + " />";
									        		
									        		writer.println(Carta_16_TransporteFerroviario_DerechosDePaso);
								        		
									        		
									        		
								        		
							        		}
							        		
							        		//Termina Linea 16 Transporte Ferroviario Derechos De Paso Carta Porte
							        		
							        		//Inicia Linea 17 Transporte Ferroviario Carro Carta Porte
							        		
							        		String Carta_17_TransporteFerroviario_Carro= "				<cartaporte20:Carro";
	//						        		if(gb_17_CP.isOpen() || !lb_CatPort_17_Merca_TransFerrov_Carro_TipoCarro.getValue().equals("") || !txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro.getValue().equals("") || !txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro.getValue().equals("") || !txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro.getValue().equals("") ){
							        		if(lb_CatPort_17_Merca_TransFerrov_Carro_TipoCarro.getSelectedIndex() > 0 || !txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro.getValue().equals("") || !txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro.getValue().equals("") || !txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro.getValue().equals("") ){
							        			
		//					        			writer.println("			<cartaporte20:Remolques>");
					
							        			if(lb_CatPort_17_Merca_TransFerrov_Carro_TipoCarro.getSelectedIndex() > 0){
						    	        			
						    	        			String[] LisTipoCarro = lb_CatPort_17_Merca_TransFerrov_Carro_TipoCarro.getSelectedItem().getLabel().split("-");
						    		        		
						    		        		 String TipoCarro = "";
						    						   
						    		 				for (int i = 0; i < LisTipoCarro.length; i++) {
						    		 							
						    		 					TipoCarro = LisTipoCarro[0];
						    		 					System.out.println(LisTipoCarro[0]); 
						    		 				}
						    		 				Carta_17_TransporteFerroviario_Carro = Carta_17_TransporteFerroviario_Carro + " TipoCarro=\""+TipoCarro+"\"";
						    	    			}
							        				
							        				if(!txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro.getValue().equals("")){
							        					Carta_17_TransporteFerroviario_Carro = Carta_17_TransporteFerroviario_Carro + " MatriculaCarro=\""+txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro.getValue()+"\"";
									    			}
							        				
							        				if(!txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro.getValue().equals("")){
							        					Carta_17_TransporteFerroviario_Carro = Carta_17_TransporteFerroviario_Carro + " GuiaCarro=\""+txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro.getValue()+"\"";
									    			}
							        				
							        				if(!txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro.getValue().equals("")){
							        					Carta_17_TransporteFerroviario_Carro = Carta_17_TransporteFerroviario_Carro + " ToneladasNetasCarro=\""+txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro.getValue()+"\"";
									    			}
							        				
							        				Carta_17_TransporteFerroviario_Carro = Carta_17_TransporteFerroviario_Carro + ">";
									        		
									        		writer.println(Carta_17_TransporteFerroviario_Carro);
								        		
										        		//Inicia Linea 18 Transporte Ferroviario Carro Contenedor Carta Porte
										        		
										        		String Carta_18_TransporteFerroviario_Carro_Contene= "					<cartaporte20:Contenedor";
	//									        		if(gb_18_CP.isOpen() || !lb_CatPort_18_Merca_TransFerrov_Carro_Cont_TipoContenedor.getValue().equals("") || !txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio.getValue().equals("") || !txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia.getValue().equals("")){
										        		if(lb_CatPort_18_Merca_TransFerrov_Carro_Cont_TipoContenedor.getSelectedIndex() > 0  || !txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio.getValue().equals("") || !txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia.getValue().equals("")){
										        			
					//					        			writer.println("			<cartaporte20:Remolques>");
										        			if(lb_CatPort_18_Merca_TransFerrov_Carro_Cont_TipoContenedor.getSelectedIndex() > 0){
									    	        			
									    	        			String[] LisTipoContenedor = lb_CatPort_18_Merca_TransFerrov_Carro_Cont_TipoContenedor.getSelectedItem().getLabel().split("-");
									    		        		
									    		        		 String TipoContenedor = "";
									    						   
									    		 				for (int i = 0; i < LisTipoContenedor.length; i++) {
									    		 							
									    		 					TipoContenedor = LisTipoContenedor[0];
									    		 					System.out.println(LisTipoContenedor[0]); 
									    		 				}
									    		 				Carta_18_TransporteFerroviario_Carro_Contene = Carta_18_TransporteFerroviario_Carro_Contene + " TipoContenedor=\""+TipoContenedor+"\"";
									    	    			}
								
										        				
										        				
										        				if(!txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio.getValue().equals("")){
										        					Carta_18_TransporteFerroviario_Carro_Contene = Carta_18_TransporteFerroviario_Carro_Contene + " PesoContenedorVacio=\""+txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio.getValue()+"\"";
												    			}
										        				
										        				if(!txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia.getValue().equals("")){
										        					Carta_18_TransporteFerroviario_Carro_Contene = Carta_18_TransporteFerroviario_Carro_Contene + " PesoNetoMercancia=\""+txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia.getValue()+"\"";
												    			}
										        				
										        				Carta_18_TransporteFerroviario_Carro_Contene = Carta_18_TransporteFerroviario_Carro_Contene + "/>";
												        		
												        		writer.println(Carta_18_TransporteFerroviario_Carro_Contene);
											        		
											        		
										        		}
										        		
										        		//Termina Linea 18 Transporte Ferroviario Carro Contenedor Carta Porte
									        		
									        		writer.println(				"</cartaporte:Carro>");
								        		
							        		}
							        		
							        		//Termina Linea 17 Transporte Ferroviario Carro Carta Porte
					        		
						        		
						        		writer.println("			</cartaporte:TransporteFerroviario>");
					        		
				        		}
				        		
				        		//Termina Linea 15 Transporte Ferroviario Carta Porte
				        		
			        		
				        		}
				        	writer.println("		</cartaporte:Mercancias>");
	        			}
	        			
		        		//Termina Linea 05 Mercancias General Carta Porte
	        			
	        			//Inicia Linea 19 FiguraTransporte Carta Porte
		        		
		        		String Carta_19_FiguraTransporte= "		<cartaporte20:FiguraTransporte";
	//	        		if(gb_19_CP.isOpen() || !txt_CatPort_19_FigTrans_CveTransporte.getValue().equals("")){
		        		if(lb_CatPort_19_FigTrans_CveTransporte.getSelectedIndex() > 0){
		        			
	//					        			writer.println("			<cartaporte20:Remolques>");
		        			if(lb_CatPort_19_FigTrans_CveTransporte.getSelectedIndex() > 0){
			        			
			        			String[] LisCveTransporte = lb_CatPort_19_FigTrans_CveTransporte.getSelectedItem().getLabel().split("-");
				        		
				        		 String CveTransporte = "";
								   
				 				for (int i = 0; i < LisCveTransporte.length; i++) {
				 							
				 					CveTransporte = LisCveTransporte[0];
				 					System.out.println(LisCveTransporte[0]); 
				 				}
				 				Carta_19_FiguraTransporte = Carta_19_FiguraTransporte + " CveTransporte=\""+CveTransporte+"\"";
			    			}
	
	//	        				if(!txt_CatPort_19_FigTrans_CveTransporte.getValue().equals("")){
	//	        					
	//			    			}
		        				
		        				Carta_19_FiguraTransporte = Carta_19_FiguraTransporte + ">";
				        		
				        		writer.println(Carta_19_FiguraTransporte);
				        		
				        		//Inicia Linea 20 FiguraTransporte Operador Carta Porte
				        		
				        		String Carta_20_FiguraTransporte_Opera= "				<cartaporte20:Operador";
	//			        		if(gb_20_CP.isOpen() || !txt_CatPort_20_FigTrans_Oper_RFCOperador.getValue().equals("") || !txt_CatPort_20_FigTrans_Oper_NumLicencia.getValue().equals("") || !txt_CatPort_20_FigTrans_Oper_NombreOperador.getValue().equals("") || !txt_CatPort_20_FigTrans_Oper_NumRegIdTribOperador.getValue().equals("") || !txt_CatPort_20_FigTrans_Oper_ResidenciaFiscalOperador.getValue().equals("")){
				        		if(!txt_CatPort_20_FigTrans_Oper_RFCOperador.getValue().equals("") || !txt_CatPort_20_FigTrans_Oper_NumLicencia.getValue().equals("") || !txt_CatPort_20_FigTrans_Oper_NombreOperador.getValue().equals("") || !txt_CatPort_20_FigTrans_Oper_NumRegIdTribOperador.getValue().equals("") || !txt_CatPort_20_FigTrans_Oper_ResidenciaFiscalOperador.getValue().equals("")){
				        			
						        			writer.println("			<cartaporte20:Operadores>");
		
				        				if(!txt_CatPort_20_FigTrans_Oper_RFCOperador.getValue().equals("")){
				        					Carta_20_FiguraTransporte_Opera = Carta_20_FiguraTransporte_Opera + " RFCOperador=\""+txt_CatPort_20_FigTrans_Oper_RFCOperador.getValue().toUpperCase()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_20_FigTrans_Oper_NumLicencia.getValue().equals("")){
				        					Carta_20_FiguraTransporte_Opera = Carta_20_FiguraTransporte_Opera + " NumLicencia=\""+txt_CatPort_20_FigTrans_Oper_NumLicencia.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_20_FigTrans_Oper_NombreOperador.getValue().equals("")){
				        					Carta_20_FiguraTransporte_Opera = Carta_20_FiguraTransporte_Opera + " NombreOperador=\""+txt_CatPort_20_FigTrans_Oper_NombreOperador.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_20_FigTrans_Oper_NumRegIdTribOperador.getValue().equals("")){
				        					Carta_20_FiguraTransporte_Opera = Carta_20_FiguraTransporte_Opera + " NumRegIdTribOperador=\""+txt_CatPort_20_FigTrans_Oper_NumRegIdTribOperador.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_20_FigTrans_Oper_ResidenciaFiscalOperador.getValue().equals("")){
				        					Carta_20_FiguraTransporte_Opera = Carta_20_FiguraTransporte_Opera + " ResidenciaFiscalOperador=\""+txt_CatPort_20_FigTrans_Oper_ResidenciaFiscalOperador.getValue()+"\"";
						    			}
				        				
				        				Carta_20_FiguraTransporte_Opera = Carta_20_FiguraTransporte_Opera + ">";
						        		
						        		writer.println(Carta_20_FiguraTransporte_Opera);
						        		
						        		//Inicia Linea 21 FiguraTransporte Operador Domicilio Carta Porte
						        		
						        		String Carta_21_FiguraTransporte_Opera_Domi= "					<cartaporte20:Domicilio";
	//					        		if(gb_21_CP.isOpen() || !txt_CatPort_21_FigTrans_Oper_Domi_Calle.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_NumeroExterior.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_NumeroInterior.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Colonia.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Localidad.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Referencia.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Municipio.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Estado.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Pais.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal.getValue().equals("")){
						        		if(!txt_CatPort_21_FigTrans_Oper_Domi_Calle.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_NumeroExterior.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_NumeroInterior.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Colonia.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Localidad.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Referencia.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Municipio.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Estado.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Pais.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal.getValue().equals("")){
						        			
								        			
				
						        				if(!txt_CatPort_21_FigTrans_Oper_Domi_Calle.getValue().equals("")){
						        					Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + " Calle=\""+txt_CatPort_21_FigTrans_Oper_Domi_Calle.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_21_FigTrans_Oper_Domi_NumeroExterior.getValue().equals("")){
						        					Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + " NumeroExterior=\""+txt_CatPort_21_FigTrans_Oper_Domi_NumeroExterior.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_21_FigTrans_Oper_Domi_NumeroInterior.getValue().equals("")){
						        					Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + " NumeroInterior=\""+txt_CatPort_21_FigTrans_Oper_Domi_NumeroInterior.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_21_FigTrans_Oper_Domi_Colonia.getValue().equals("")){
						        					Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + " Colonia=\""+txt_CatPort_21_FigTrans_Oper_Domi_Colonia.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_21_FigTrans_Oper_Domi_Localidad.getValue().equals("")){
						        					Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + " Localidad=\""+txt_CatPort_21_FigTrans_Oper_Domi_Localidad.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_21_FigTrans_Oper_Domi_Referencia.getValue().equals("")){
						        					Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + " Referencia=\""+txt_CatPort_21_FigTrans_Oper_Domi_Referencia.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_21_FigTrans_Oper_Domi_Municipio.getValue().equals("")){
						        					Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + " Municipio=\""+txt_CatPort_21_FigTrans_Oper_Domi_Municipio.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_21_FigTrans_Oper_Domi_Estado.getValue().equals("")){
						        					Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + " Estado=\""+txt_CatPort_21_FigTrans_Oper_Domi_Estado.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_21_FigTrans_Oper_Domi_Pais.getValue().equals("")){
						        					Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + " Pais=\""+txt_CatPort_21_FigTrans_Oper_Domi_Pais.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal.getValue().equals("")){
						        					Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + " CodigoPostal=\""+txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal.getValue()+"\"";
								    			}
						        				Carta_21_FiguraTransporte_Opera_Domi = Carta_21_FiguraTransporte_Opera_Domi + "/>";
								        		
								        		writer.println(Carta_21_FiguraTransporte_Opera_Domi);
								        		
							        		
						        		}
						        		
						        		//Termina Linea 20 FiguraTransporte Operador Domicilio Carta Porte
						        		
						        		
						        		writer.println("				</cartaporte:Operador>");
					        		
						        		writer.println("			</cartaporte:Operadores>");
					        		
				        		}
				        		
				        		//Termina Linea 20 FiguraTransporte Operador Carta Porte
				        		
				        		//Inicia Linea 22 FiguraTransporte Propietario Carta Porte
				        		
				        		String Carta_22_FiguraTransporte_Propietario= "			<cartaporte20:Propietario";
	//			        		if(gb_22_CP.isOpen() || !txt_CatPort_22_FigTrans_Prop_RFCPropietario.getValue().equals("") || !txt_CatPort_22_FigTrans_Prop_NombrePropietario.getValue().equals("") || !txt_CatPort_22_FigTrans_Prop_NumRegIdTribPropietario.getValue().equals("") || !txt_CatPort_22_FigTrans_Prop_ResidenciaFiscalPropietario.getValue().equals("")){
				        		if(!txt_CatPort_22_FigTrans_Prop_RFCPropietario.getValue().equals("") || !txt_CatPort_22_FigTrans_Prop_NombrePropietario.getValue().equals("") || !txt_CatPort_22_FigTrans_Prop_NumRegIdTribPropietario.getValue().equals("") || !txt_CatPort_22_FigTrans_Prop_ResidenciaFiscalPropietario.getValue().equals("")){
				        			
						        			
		
				        				if(!txt_CatPort_22_FigTrans_Prop_RFCPropietario.getValue().equals("")){
				        					Carta_22_FiguraTransporte_Propietario = Carta_22_FiguraTransporte_Propietario + " RFCPropietario=\""+txt_CatPort_22_FigTrans_Prop_RFCPropietario.getValue().toUpperCase()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_22_FigTrans_Prop_NombrePropietario.getValue().equals("")){
				        					Carta_22_FiguraTransporte_Propietario = Carta_22_FiguraTransporte_Propietario + " NombrePropietario=\""+txt_CatPort_22_FigTrans_Prop_NombrePropietario.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_22_FigTrans_Prop_NumRegIdTribPropietario.getValue().equals("")){
				        					Carta_22_FiguraTransporte_Propietario = Carta_22_FiguraTransporte_Propietario + " NumRegIdTribPropietario=\""+txt_CatPort_22_FigTrans_Prop_NumRegIdTribPropietario.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_22_FigTrans_Prop_ResidenciaFiscalPropietario.getValue().equals("")){
				        					Carta_22_FiguraTransporte_Propietario = Carta_22_FiguraTransporte_Propietario + " ResidenciaFiscalPropietario=\""+txt_CatPort_22_FigTrans_Prop_ResidenciaFiscalPropietario.getValue()+"\"";
						    			}
				        				
				        				
				        				Carta_22_FiguraTransporte_Propietario = Carta_22_FiguraTransporte_Propietario + ">";
						        		
						        		writer.println(Carta_22_FiguraTransporte_Propietario);
						        		
						        		//Inicia Linea 23 FiguraTransporte Propietario Domicilio Carta Porte
						        		
						        		String Carta_23_FiguraTransporte_Propietario_Domi= "				<cartaporte20:Domicilio";
	//					        		if(gb_23_CP.isOpen() || !txt_CatPort_23_FigTrans_Prop_Domi_Calle.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_NumeroExterior.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_NumeroInterior.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Colonia.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Localidad.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Referencia.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Municipio.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Estado.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Pais.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal.getValue().equals("")){
						        		if(!txt_CatPort_23_FigTrans_Prop_Domi_Calle.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_NumeroExterior.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_NumeroInterior.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Colonia.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Localidad.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Referencia.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Municipio.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Estado.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Pais.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal.getValue().equals("")){
						        			
								        			
				
						        				if(!txt_CatPort_23_FigTrans_Prop_Domi_Calle.getValue().equals("")){
						        					Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + " Calle=\""+txt_CatPort_23_FigTrans_Prop_Domi_Calle.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_23_FigTrans_Prop_Domi_NumeroExterior.getValue().equals("")){
						        					Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + " NumeroExterior=\""+txt_CatPort_23_FigTrans_Prop_Domi_NumeroExterior.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_23_FigTrans_Prop_Domi_NumeroInterior.getValue().equals("")){
						        					Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + " NumeroInterior=\""+txt_CatPort_23_FigTrans_Prop_Domi_NumeroInterior.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_23_FigTrans_Prop_Domi_Colonia.getValue().equals("")){
						        					Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + " Colonia=\""+txt_CatPort_23_FigTrans_Prop_Domi_Colonia.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_23_FigTrans_Prop_Domi_Localidad.getValue().equals("")){
						        					Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + " Localidad=\""+txt_CatPort_23_FigTrans_Prop_Domi_Localidad.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_23_FigTrans_Prop_Domi_Referencia.getValue().equals("")){
						        					Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + " Referencia=\""+txt_CatPort_23_FigTrans_Prop_Domi_Referencia.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_23_FigTrans_Prop_Domi_Municipio.getValue().equals("")){
						        					Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + " Municipio=\""+txt_CatPort_23_FigTrans_Prop_Domi_Municipio.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_23_FigTrans_Prop_Domi_Estado.getValue().equals("")){
						        					Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + " Estado=\""+txt_CatPort_23_FigTrans_Prop_Domi_Estado.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_23_FigTrans_Prop_Domi_Pais.getValue().equals("")){
						        					Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + " Pais=\""+txt_CatPort_23_FigTrans_Prop_Domi_Pais.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal.getValue().equals("")){
						        					Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + " CodigoPostal=\""+txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal.getValue()+"\"";
								    			}
						        				Carta_23_FiguraTransporte_Propietario_Domi = Carta_23_FiguraTransporte_Propietario_Domi + "/>";
								        		
								        		writer.println(Carta_23_FiguraTransporte_Propietario_Domi);
								        		
							        		
						        		}
						        		
						        		//Termina Linea 23 FiguraTransporte Propietario Domicilio Carta Porte
						        		
						        		
						        		writer.println("			</cartaporte:Propietario>");
					        		
						        	
					        		
				        		}
				        		
				        		//Termina Linea 22 FiguraTransporte Propietario Carta Porte
				        		
				        		//Inicia Linea 24 FiguraTransporte Arrendatario Carta Porte
				        		
				        		String Carta_24_FiguraTransporte_Arrenda= "			<cartaporte20:Arrendatario";
	//			        		if(gb_24_CP.isOpen() || !txt_CatPort_24_FigTrans_Arr_RFCArrendatario.getValue().equals("") || !txt_CatPort_24_FigTrans_Arr_NombreArrendatario.getValue().equals("") || !txt_CatPort_24_FigTrans_Arr_NumRegIdTribArrendatario.getValue().equals("") || !txt_CatPort_24_FigTrans_Arr_ResidenciaFiscalArrendatario.getValue().equals("")){
				        		if(!txt_CatPort_24_FigTrans_Arr_RFCArrendatario.getValue().equals("") || !txt_CatPort_24_FigTrans_Arr_NombreArrendatario.getValue().equals("") || !txt_CatPort_24_FigTrans_Arr_NumRegIdTribArrendatario.getValue().equals("") || !txt_CatPort_24_FigTrans_Arr_ResidenciaFiscalArrendatario.getValue().equals("")){
				        			
						        			
		
				        				if(!txt_CatPort_24_FigTrans_Arr_RFCArrendatario.getValue().equals("")){
				        					Carta_24_FiguraTransporte_Arrenda = Carta_24_FiguraTransporte_Arrenda + " RFCArrendatario=\""+txt_CatPort_24_FigTrans_Arr_RFCArrendatario.getValue().toUpperCase()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_24_FigTrans_Arr_NombreArrendatario.getValue().equals("")){
				        					Carta_24_FiguraTransporte_Arrenda = Carta_24_FiguraTransporte_Arrenda + " NombreArrendatario=\""+txt_CatPort_24_FigTrans_Arr_NombreArrendatario.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_24_FigTrans_Arr_NumRegIdTribArrendatario.getValue().equals("")){
				        					Carta_24_FiguraTransporte_Arrenda = Carta_24_FiguraTransporte_Arrenda + " NumRegIdTribArrendatario=\""+txt_CatPort_24_FigTrans_Arr_NumRegIdTribArrendatario.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_24_FigTrans_Arr_ResidenciaFiscalArrendatario.getValue().equals("")){
				        					Carta_24_FiguraTransporte_Arrenda = Carta_24_FiguraTransporte_Arrenda + " ResidenciaFiscalArrendatario=\""+txt_CatPort_24_FigTrans_Arr_ResidenciaFiscalArrendatario.getValue()+"\"";
						    			}
				        				
				        				
				        				Carta_24_FiguraTransporte_Arrenda = Carta_24_FiguraTransporte_Arrenda + ">";
						        		
						        		writer.println(Carta_24_FiguraTransporte_Arrenda);
						        		
						        		//Inicia Linea 25 FiguraTransporte Arrendatario Domicilio Carta Porte
						        		
						        		String Carta_25_FiguraTransporte_Arrenda_Domi= "				<cartaporte20:Domicilio ";
	//					        		if(gb_25_CP.isOpen() || !txt_CatPort_25_FigTrans_Arr_Domi_Calle.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__NumeroExterior.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__NumeroInterior.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Colonia.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Localidad.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Referencia.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Municipio.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Estado.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Pais.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal.getValue().equals("")){
						        		if(!txt_CatPort_25_FigTrans_Arr_Domi_Calle.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__NumeroExterior.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__NumeroInterior.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Colonia.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Localidad.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Referencia.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Municipio.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Estado.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Pais.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal.getValue().equals("")){
						        			
								        			
				
						        				if(!txt_CatPort_25_FigTrans_Arr_Domi_Calle.getValue().equals("")){
						        					Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + " Calle=\""+txt_CatPort_25_FigTrans_Arr_Domi_Calle.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_25_FigTrans_Arr_Domi__NumeroExterior.getValue().equals("")){
						        					Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + " NumeroExterior=\""+txt_CatPort_25_FigTrans_Arr_Domi__NumeroExterior.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_25_FigTrans_Arr_Domi__NumeroInterior.getValue().equals("")){
						        					Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + " NumeroInterior=\""+txt_CatPort_25_FigTrans_Arr_Domi__NumeroInterior.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_25_FigTrans_Arr_Domi__Colonia.getValue().equals("")){
						        					Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + " Colonia=\""+txt_CatPort_25_FigTrans_Arr_Domi__Colonia.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_25_FigTrans_Arr_Domi__Localidad.getValue().equals("")){
						        					Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + " Localidad=\""+txt_CatPort_25_FigTrans_Arr_Domi__Localidad.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_25_FigTrans_Arr_Domi__Referencia.getValue().equals("")){
						        					Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + " Referencia=\""+txt_CatPort_25_FigTrans_Arr_Domi__Referencia.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_25_FigTrans_Arr_Domi__Municipio.getValue().equals("")){
						        					Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + " Municipio=\""+txt_CatPort_25_FigTrans_Arr_Domi__Municipio.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_25_FigTrans_Arr_Domi__Estado.getValue().equals("")){
						        					Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + " Estado=\""+txt_CatPort_25_FigTrans_Arr_Domi__Estado.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_25_FigTrans_Arr_Domi__Pais.getValue().equals("")){
						        					Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + " Pais=\""+txt_CatPort_25_FigTrans_Arr_Domi__Pais.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal.getValue().equals("")){
						        					Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + " CodigoPostal=\""+txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal.getValue()+"\"";
								    			}
						        				Carta_25_FiguraTransporte_Arrenda_Domi = Carta_25_FiguraTransporte_Arrenda_Domi + "/>";
								        		
								        		writer.println(Carta_25_FiguraTransporte_Arrenda_Domi);
								        		
							        		
						        		}
						        		
						        		//Termina Linea 25 FiguraTransporte Arrendatario Domicilio Carta Porte
						        		
						        		
						        		writer.println("			</cartaporte:Arrendatario>");
						        		
						        		
					        		
						        	
					        		
				        		}
				        		
				        		//Termina Linea 24 FiguraTransporte Arrendatario Carta Porte
				        		
				        		//Inicia Linea 26 FiguraTransporte Notificado Carta Porte
				        		
				        		String Carta_26_FiguraTransporte_Notificado= "			<cartaporte20:Notificado";
	//			        		if(gb_26_CP.isOpen() || !txt_CatPort_26_FigTrans_Noti_RFCNotificado.getValue().equals("") || !txt_CatPort_26_FigTrans_Noti_NombreNotificado.getValue().equals("") || !txt_CatPort_26_FigTrans_Noti_NumRegIdTribNotificado.getValue().equals("") || !txt_CatPort_26_FigTrans_Noti_ResidenciaFiscalNotificado.getValue().equals("")){
				        		if(!txt_CatPort_26_FigTrans_Noti_RFCNotificado.getValue().equals("") || !txt_CatPort_26_FigTrans_Noti_NombreNotificado.getValue().equals("") || !txt_CatPort_26_FigTrans_Noti_NumRegIdTribNotificado.getValue().equals("") || !txt_CatPort_26_FigTrans_Noti_ResidenciaFiscalNotificado.getValue().equals("")){
				        			
						        			
		
				        				if(!txt_CatPort_26_FigTrans_Noti_RFCNotificado.getValue().equals("")){
				        					Carta_26_FiguraTransporte_Notificado = Carta_26_FiguraTransporte_Notificado + " RFCNotificado=\""+txt_CatPort_26_FigTrans_Noti_RFCNotificado.getValue().toUpperCase()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_26_FigTrans_Noti_NombreNotificado.getValue().equals("")){
				        					Carta_26_FiguraTransporte_Notificado = Carta_26_FiguraTransporte_Notificado + " NombreNotificado=\""+txt_CatPort_26_FigTrans_Noti_NombreNotificado.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_26_FigTrans_Noti_NumRegIdTribNotificado.getValue().equals("")){
				        					Carta_26_FiguraTransporte_Notificado = Carta_26_FiguraTransporte_Notificado + " NumRegIdTribNotificado=\""+txt_CatPort_26_FigTrans_Noti_NumRegIdTribNotificado.getValue()+"\"";
						    			}
				        				
				        				if(!txt_CatPort_26_FigTrans_Noti_ResidenciaFiscalNotificado.getValue().equals("")){
				        					Carta_26_FiguraTransporte_Notificado = Carta_26_FiguraTransporte_Notificado + " ResidenciaFiscalNotificado=\""+txt_CatPort_26_FigTrans_Noti_ResidenciaFiscalNotificado.getValue()+"\"";
						    			}
				        				
				        				
				        				Carta_26_FiguraTransporte_Notificado = Carta_26_FiguraTransporte_Notificado + ">";
						        		
						        		writer.println(Carta_26_FiguraTransporte_Notificado);
						        		
						        		//Inicia Linea 27 FiguraTransporte Notificado Domicilio Carta Porte
						        		
						        		String Carta_27_FiguraTransporte_Notificado_Domi= "				<cartaporte20:Domicilio";
	//					        		if(gb_27_CP.isOpen() || !txt_CatPort_27_FigTrans_Noti_Domi_Calle.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__NumeroExterior.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__NumeroInterior.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Colonia.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Localidad.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Referencia.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Municipio.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Estado.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Pais.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal.getValue().equals("")){
						        		if(!txt_CatPort_27_FigTrans_Noti_Domi_Calle.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__NumeroExterior.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__NumeroInterior.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Colonia.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Localidad.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Referencia.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Municipio.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Estado.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Pais.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal.getValue().equals("")){
						        			
								        			
				
						        				if(!txt_CatPort_27_FigTrans_Noti_Domi_Calle.getValue().equals("")){
						        					Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + " Calle=\""+txt_CatPort_27_FigTrans_Noti_Domi_Calle.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_27_FigTrans_Noti_Domi__NumeroExterior.getValue().equals("")){
						        					Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + " NumeroExterior=\""+txt_CatPort_27_FigTrans_Noti_Domi__NumeroExterior.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_27_FigTrans_Noti_Domi__NumeroInterior.getValue().equals("")){
						        					Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + " NumeroInterior=\""+txt_CatPort_27_FigTrans_Noti_Domi__NumeroInterior.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_27_FigTrans_Noti_Domi__Colonia.getValue().equals("")){
						        					Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + " Colonia=\""+txt_CatPort_27_FigTrans_Noti_Domi__Colonia.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_27_FigTrans_Noti_Domi__Localidad.getValue().equals("")){
						        					Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + " Localidad=\""+txt_CatPort_27_FigTrans_Noti_Domi__Localidad.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_27_FigTrans_Noti_Domi__Referencia.getValue().equals("")){
						        					Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + " Referencia=\""+txt_CatPort_27_FigTrans_Noti_Domi__Referencia.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_27_FigTrans_Noti_Domi__Municipio.getValue().equals("")){
						        					Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + " Municipio=\""+txt_CatPort_27_FigTrans_Noti_Domi__Municipio.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_27_FigTrans_Noti_Domi__Estado.getValue().equals("")){
						        					Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + " Estado=\""+txt_CatPort_27_FigTrans_Noti_Domi__Estado.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_27_FigTrans_Noti_Domi__Pais.getValue().equals("")){
						        					Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + " Pais=\""+txt_CatPort_27_FigTrans_Noti_Domi__Pais.getValue()+"\"";
								    			}
						        				
						        				if(!txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal.getValue().equals("")){
						        					Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + " CodigoPostal=\""+txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal.getValue()+"\"";
								    			}
						        				Carta_27_FiguraTransporte_Notificado_Domi = Carta_27_FiguraTransporte_Notificado_Domi + "/>";
								        		
								        		writer.println(Carta_27_FiguraTransporte_Notificado_Domi);
								        		
							        		
						        		}
						        		
						        		//Termina Linea 27 FiguraTransporte Notificado Domicilio Carta Porte
						        		
						        		
						        		writer.println("			</cartaporte:Notificado>");
						        		
						        		
					        		
						        	
					        		
				        		}
				        		
				        		//Termina Linea 26 FiguraTransporte Notificado Carta Porte
				        		
				        		writer.println(		"</cartaporte:FiguraTransporte>");
			        		
		        		}
		        		
		        		//Termina Linea 19 FiguraTransporte Carta Porte
		        		
		        		
		        		writer.println("	</cartaporte:CartaPorte>");
	        		writer.println("	</cfdi:Complemento>");
	        		
	        		}
		        }
	        	
	        	
	        	 if(Tipo_Comprobante.equals("P")){
					
	        	writer.println("	<cfdi:Complemento>");
	        	
	        	//Inicia Crear Complemento de Pagos
        		
        		
        		writer.println( "		<pago10:Pagos xmlns:pago10=\"http://www.sat.gob.mx/Pagos\" Version=\"1.0\">");
	        		if(lb_Pago_01_FormaDePagoP.getSelectedIndex() > 0 || lb_Pago_01_MonedaP.getSelectedIndex() > 0 || !txt_Pago_01_TipoCambioP.getValue().equals("") || !txt_Pago_01_Monto.getValue().equals("") || !txt_Pago_01_NumOperacion.getValue().equals("") || !txt_Pago_01_RfcEmisorCtaOrd.getValue().equals("") || !txt_Pago_01_NomBancoOrdExt.getValue().equals("") || !txt_Pago_01_CtaOrdenante.getValue().equals("") || !txt_Pago_01_RfcEmisorCtaBen.getValue().equals("") || !txt_Pago_01_CtaBeneficiario.getValue().equals("") || !txt_Pago_01_TipoCadPago.getValue().equals("") || !txt_Pago_01_CertPago.getValue().equals("") || !txt_Pago_01_CadPago.getValue().equals("") || !txt_Pago_01_SelloPago.getValue().equals("")){
	        			
	        			String Pago_01="			<pago10:Pago";
	        			
	        			
	        			if(!dt_Pago_01_FechaPago.getValue().equals("")){
	        				Pago_01 = Pago_01 + " FechaPago=\""+dt_Pago_01_FechaPago.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T")+"\"";
		    			}
        				
//	        			 Listitem item_RC_For_Pag = lb_Pago_01_FormaDePagoP.getSelectedItem();
//	     	            ListModelList lml_RC_For_Pag = (ListModelList) lb_Pago_01_FormaDePagoP.getListModel();
//	             		ComboFactory comboFactory_RC_For_Pag = (ComboFactory) lml_RC_For_Pag.get(item_RC_For_Pag.getIndex());
	             		
	             		String[] LisProductF_RC = lb_Pago_01_FormaDePagoP.getSelectedItem().getLabel().split("-");
	             		
	             		 String RC_Forma_Pago = "";
	     				   
	      				for (int i = 0; i < LisProductF_RC.length; i++) {
	      							
	      					RC_Forma_Pago = LisProductF_RC[0];
	      					System.out.println(LisProductF_RC[0]); 
	      				}
	      				
        				if(!RC_Forma_Pago.trim().equals("")){
        					Pago_01 = Pago_01 + " FormaDePagoP=\""+RC_Forma_Pago.trim() +"\"";
		    			}
        				
        				if(lb_Pago_01_MonedaP.getSelectedIndex() > 0){
        					Pago_01 = Pago_01 + " MonedaP=\""+lb_Pago_01_MonedaP.getSelectedItem().getLabel()+"\"";
		    			}
        				
        				if(!txt_Pago_01_TipoCambioP.getValue().equals("")){
        					Pago_01 = Pago_01 + " TipoCambioP=\""+txt_Pago_01_TipoCambioP.getValue()+"\"";
        				}
        				
        				if(!txt_Pago_01_Monto.getValue().equals("")){
        					Pago_01 = Pago_01 + " Monto=\""+txt_Pago_01_Monto.getValue()+"\"";
        				}

        				
        				if(!txt_Pago_01_NumOperacion.getValue().equals("")){
        					Pago_01 = Pago_01 + " NumOperacion=\""+txt_Pago_01_NumOperacion.getValue()+"\"";
        				}

        				
        				if(!txt_Pago_01_RfcEmisorCtaOrd.getValue().equals("")){
        					Pago_01 = Pago_01 + " RfcEmisorCtaOrd=\""+txt_Pago_01_RfcEmisorCtaOrd.getValue().toUpperCase()+"\"";
        				}

        				
        				if(!txt_Pago_01_NomBancoOrdExt.getValue().equals("")){
        					Pago_01 = Pago_01 + " NomBancoOrdExt=\""+txt_Pago_01_NomBancoOrdExt.getValue()+"\"";
        				}

        				
        				if(!txt_Pago_01_CtaOrdenante.getValue().equals("")){
        					Pago_01 = Pago_01 + " CtaOrdenante=\""+txt_Pago_01_CtaOrdenante.getValue()+"\"";
        				}

        				
        				if(!txt_Pago_01_RfcEmisorCtaBen.getValue().equals("")){
        					Pago_01 = Pago_01 + " RfcEmisorCtaBen=\""+txt_Pago_01_RfcEmisorCtaBen.getValue().toUpperCase()+"\"";
        				}

        				
        				if(!txt_Pago_01_CtaBeneficiario.getValue().equals("")){
        					Pago_01 = Pago_01 + " CtaBeneficiario=\""+txt_Pago_01_CtaBeneficiario.getValue()+"\"";
        				}

        				
        				if(!txt_Pago_01_TipoCadPago.getValue().equals("")){
        					Pago_01 = Pago_01 + " TipoCadPago=\""+txt_Pago_01_TipoCadPago.getValue()+"\"";
        				}

        				
        				if(!txt_Pago_01_CertPago.getValue().equals("")){
        					Pago_01 = Pago_01 + " CertPago=\""+txt_Pago_01_CertPago.getValue()+"\"";
        				}

        				
        				if(!txt_Pago_01_CadPago.getValue().equals("")){
        					Pago_01 = Pago_01 + " CadPago=\""+txt_Pago_01_CadPago.getValue()+"\"";
        				}

        				
        				if(!txt_Pago_01_SelloPago.getValue().equals("")){
        					Pago_01 = Pago_01 + " SelloPago=\""+txt_Pago_01_SelloPago.getValue()+"\"";
        				}
        				
        				Pago_01 = Pago_01 + ">";
        				
        				
	        			writer.println(Pago_01);
	        			
	        			
	        		}
//	        		if(!txt_Pago_02_IdDocumento.getValue().equals("") || !txt_Pago_02_Serie.getValue().equals("") || !txt_Pago_02_Folio.getValue().equals("") || !txt_Pago_02_MonedaDR.getValue().equals("") || !txt_Pago_02_TipoCambioDR.getValue().equals("") || !txt_Pago_02_MetodoDePagoDR.getValue().equals("") || !txt_Pago_02_NumParcialidad.getValue().equals("") || !txt_Pago_02_ImpSaldoAnt.getValue().equals("") || !txt_Pago_02_ImpPagado.getValue().equals("") || !txt_Pago_02_ImpSaldoInsoluto.getValue().equals("") ){
	        		
	        			
	        		
	        			
	        			for (Object item : itemCRPList.getItems()) {
		                	//Los tenemos que ir cargando porque los items de las pï¿½ginas que no se hayan visitado
		                	//no estarï¿½n cargados
	        				itemCRPList.renderItem((Listitem) item);
		        	        String CRP = "";
		        	        for (Object cell : ((Listitem) item).getChildren()) {
		        	        	//Solo mostramos las celdas que estï¿½n visibles
		        	        	if(((Listcell)cell).getListheader()!=null){
		        		        	 if(((Listcell)cell).getListheader().isVisible()){
		        		        		 CRP += ((Listcell) cell).getLabel() ;
		        		        	 }
		        	        	}
		        	        }
		        	       
		        	        System.out.println(CRP + "\n");
		        	        
		        	        String[] LisRelaCRP = CRP.split("\\|");
		        	        
		        	       

		        	        int valorcadena_CRP = 0;

								for (int i = 0; i < LisRelaCRP.length; i++) {
									
									System.out.println("Aqui el total: "+ LisRelaCRP.length);
									
									valorcadena_CRP =  LisRelaCRP.length;
									
									System.out.println(i +" - Aqui los productos----->> " +LisRelaCRP[i]);
								}
								
								String Pago_02="				<pago10:DoctoRelacionado";
								
								if(!LisRelaCRP[0].equals("")){
			        				Pago_02 = Pago_02 + " IdDocumento=\""+LisRelaCRP[0]+"\"";
				    			}
			        			
			        			if(!LisRelaCRP[1].equals("")){
			        				Pago_02 = Pago_02 + " Serie=\""+LisRelaCRP[1]+"\"";
				    			}
			        			
			        			if(!LisRelaCRP[2].equals("")){
			        				Pago_02 = Pago_02 + " Folio=\""+LisRelaCRP[2]+"\"";
				    			}
			        			
			        			if(!LisRelaCRP[3].equals("")){
			        				Pago_02 = Pago_02 + " MonedaDR=\""+LisRelaCRP[3]+"\"";
				    			}
			        			
			        			if(!LisRelaCRP[4].equals("")){
			        				Pago_02 = Pago_02 + " TipoCambioDR=\""+LisRelaCRP[4]+"\"";
				    			}
			        			
			        			if(!LisRelaCRP[5].equals("")){
			        				Pago_02 = Pago_02 + " MetodoDePagoDR=\""+LisRelaCRP[5]+"\"";
				    			}
			        			
			        			if(!LisRelaCRP[6].equals("")){
			        				Pago_02 = Pago_02 + " NumParcialidad=\""+LisRelaCRP[6]+"\"";
				    			}
			        			
			        			if(!LisRelaCRP[7].equals("")){
			        				Pago_02 = Pago_02 + " ImpSaldoAnt=\""+LisRelaCRP[7]+"\"";
				    			}
			        			
			        			if(!LisRelaCRP[8].equals("")){
			        				Pago_02 = Pago_02 + " ImpPagado=\""+LisRelaCRP[8]+"\"";
				    			}
			        			
			        			if(!LisRelaCRP[9].equals("")){
			        				Pago_02 = Pago_02 + " ImpSaldoInsoluto=\""+LisRelaCRP[9]+"\"";
				    			}
			        			
			        			Pago_02 = Pago_02 + "/>";
			        			
			        			
			        			writer.println(Pago_02);
								
	        			}		
	        			
	        			
	        			writer.println("			</pago10:Pago>");
	                    
	        			writer.println("		</pago10:Pagos>");
	        			
	        	writer.println("	</cfdi:Complemento>");
	        			
	        	 }
        		//Termina Crear Complemento de Pagos
        		
        		
//        		protected transient Textbox txt_CatPort_00_Version;
//        		protected transient Textbox lb_CatPort_00_TranspInternac;
//        		protected transient Textbox lb_CatPort_00_EntradaSalidaMerc;
//        		protected transient Textbox txt_CatPort_00_ViaEntradaSalida;
//        		protected transient Textbox txt_CatPort_00_TotalDistRec;
        		
        		
        		//Inicia Crear Complemento Comercio
        		if(gb_Comple_Comer_Ext.isOpen()){
	        		if(gb_Comer_Exte_00.isOpen()  || !txt_Ext_00_Version.getValue().equals("") || lb_Ext_00_MotivoTraslado.getSelectedIndex() > 0 || !txt_Ext_00_TipoOperacion.getValue().equals("") || !txt_Ext_00_ClaveDePedimento.getValue().equals("") || lb_Ext_00_CertificadoOrigen.getSelectedIndex() > 0 || !txt_Ext_00_NumCertificadoOrigen.getValue().equals("") || !txt_Ext_00_NumeroExportadorConfiable.getValue().equals("") || !txt_Ext_00_Incoterm.getValue().equals("") || !txt_Ext_00_Subdivision.getValue().equals("") || !txt_Ext_00_Observaciones.getValue().equals("") || !txt_Ext_00_TipoCambioUSD.getValue().equals("") || !txt_Ext_00_TotalUSD.getValue().equals("")){
	        			writer.println("00|"+txt_Ext_00_Version.getValue()+"|"+lb_Ext_00_MotivoTraslado.getSelectedIndex()+"|"+txt_Ext_00_TipoOperacion.getValue()+"|"+txt_Ext_00_ClaveDePedimento.getValue()+"|"+lb_Ext_00_CertificadoOrigen.getSelectedIndex()+"|"+txt_Ext_00_NumCertificadoOrigen.getValue()+"|"+txt_Ext_00_NumeroExportadorConfiable.getValue()+"|"+txt_Ext_00_Incoterm.getValue()+"|"+txt_Ext_00_Subdivision.getValue()+"|"+txt_Ext_00_Observaciones.getValue()+"|"+txt_Ext_00_TipoCambioUSD.getValue()+"|"+txt_Ext_00_TotalUSD.getValue());
	        		}
	        		if(gb_Comer_Exte_00.isOpen() || !txt_Ext_01_Curp.getValue().equals("")){
	        			writer.println("01|"+txt_Ext_01_Curp.getValue());
	        		}
	        		if(gb_Comer_Exte_00.isOpen() || !txt_Ext_02_Calle.getValue().equals("")|| !txt_Ext_02_NumeroExterior.getValue().equals("")|| !txt_Ext_02_NumeroInterior.getValue().equals("")|| !txt_Ext_02_Colonia.getValue().equals("")|| !txt_Ext_02_Localidad.getValue().equals("")|| !txt_Ext_02_Referencia.getValue().equals("")|| !txt_Ext_02_Municipio.getValue().equals("")|| !txt_Ext_02_Estado.getValue().equals("")|| !txt_Ext_02_Pais.getValue().equals("")|| !txt_Ext_02_CodigoPostal.getValue().equals("")){
	        			writer.println("02|"+txt_Ext_02_Calle.getValue()+"|"+txt_Ext_02_NumeroExterior.getValue()+"|"+txt_Ext_02_NumeroInterior.getValue()+"|"+txt_Ext_02_Colonia.getValue()+"|"+txt_Ext_02_Localidad.getValue()+"|"+txt_Ext_02_Referencia.getValue()+"|"+txt_Ext_02_Municipio.getValue()+"|"+txt_Ext_02_Estado.getValue()+"|"+txt_Ext_02_Pais.getValue()+"|"+txt_Ext_02_CodigoPostal.getValue());
	        		}
	        		if(gb_Comer_Exte_00.isOpen() || !txt_Ext_03_NumRegIdTrib.getValue().equals("") || !txt_Ext_03_ResidenciaFiscal.getValue().equals("")){
	        			writer.println("03|"+txt_Ext_03_NumRegIdTrib.getValue()+"|"+txt_Ext_03_ResidenciaFiscal.getValue());
	        		}
	        		if(gb_Comer_Exte_00.isOpen() || !txt_Ext_04_NumRegIdTrib.getValue().equals("")){
	        			writer.println("04|"+txt_Ext_04_NumRegIdTrib.getValue());
	        		}
	        		if(gb_Comer_Exte_00.isOpen()|| !txt_Ext_06_Calle.getValue().equals("")|| !txt_Ext_06_NumeroExterior.getValue().equals("")|| !txt_Ext_06_NumeroInterior.getValue().equals("")|| !txt_Ext_06_Colonia.getValue().equals("")|| !txt_Ext_06_Localidad.getValue().equals("")|| !txt_Ext_06_Referencia.getValue().equals("")|| !txt_Ext_06_Municipio.getValue().equals("")|| !txt_Ext_06_Estado.getValue().equals("")|| !txt_Ext_06_Pais.getValue().equals("")|| !txt_Ext_06_CodigoPostal.getValue().equals("")){
	        			writer.println("06|"+txt_Ext_06_Calle.getValue()+"|"+txt_Ext_06_NumeroExterior.getValue()+"|"+txt_Ext_06_NumeroInterior.getValue()+"|"+txt_Ext_06_Colonia.getValue()+"|"+txt_Ext_06_Localidad.getValue()+"|"+txt_Ext_06_Referencia.getValue()+"|"+txt_Ext_06_Municipio.getValue()+"|"+txt_Ext_06_Estado.getValue()+"|"+txt_Ext_06_Pais.getValue()+"|"+txt_Ext_06_CodigoPostal.getValue());
	        		}
	        		if(gb_Comer_Exte_00.isOpen() || !txt_Ext_07_NumRegIdTrib.getValue().equals("") || !txt_Ext_07_Nombre.getValue().equals("")){
	        			writer.println("07|"+txt_Ext_07_NumRegIdTrib.getValue()+"|"+txt_Ext_07_Nombre.getValue());
	        		}
	        		if(gb_Comer_Exte_00.isOpen()|| !txt_Ext_08_Calle.getValue().equals("")|| !txt_Ext_08_NumeroExterior.getValue().equals("")|| !txt_Ext_08_NumeroInterior.getValue().equals("")|| !txt_Ext_08_Colonia.getValue().equals("")|| !txt_Ext_08_Localidad.getValue().equals("")|| !txt_Ext_08_Referencia.getValue().equals("")|| !txt_Ext_08_Municipio.getValue().equals("")|| !txt_Ext_08_Estado.getValue().equals("")|| !txt_Ext_08_Pais.getValue().equals("")|| !txt_Ext_08_CodigoPostal.getValue().equals("")){
	        			writer.println("08|"+txt_Ext_08_Calle.getValue()+"|"+txt_Ext_08_NumeroExterior.getValue()+"|"+txt_Ext_08_NumeroInterior.getValue()+"|"+txt_Ext_08_Colonia.getValue()+"|"+txt_Ext_08_Localidad.getValue()+"|"+txt_Ext_08_Referencia.getValue()+"|"+txt_Ext_08_Municipio.getValue()+"|"+txt_Ext_08_Estado.getValue()+"|"+txt_Ext_08_Pais.getValue()+"|"+txt_Ext_08_CodigoPostal.getValue());
	        		}
	        		if(gb_Comer_Exte_00.isOpen() || !txt_Ext_09_NoIdentificacion.getValue().equals("") || !txt_Ext_09_FraccionArancelaria.getValue().equals("") || !txt_Ext_09_CantidadAduana.getValue().equals("") || !txt_Ext_09_UnidadAduana.getValue().equals("") || !txt_Ext_09_ValorUnitarioAduana.getValue().equals("") || !txt_Ext_09_ValorDolares.getValue().equals("")){
	        			writer.println("09|"+txt_Ext_09_NoIdentificacion.getValue()+"|"+txt_Ext_09_FraccionArancelaria.getValue()+"|"+txt_Ext_09_CantidadAduana.getValue()+"|"+txt_Ext_09_UnidadAduana.getValue()+"|"+txt_Ext_09_ValorUnitarioAduana.getValue()+"|"+txt_Ext_09_ValorDolares.getValue());
	        		}
	        		if(gb_Comer_Exte_00.isOpen() || !txt_Ext_10_Marca.getValue().equals("") || !txt_Ext_10_Modelo.getValue().equals("") || !txt_Ext_10_SubModelo.getValue().equals("") || !txt_Ext_10_NumeroSerie.getValue().equals("") ){
	        			writer.println("10|"+txt_Ext_10_Marca.getValue()+"|"+txt_Ext_10_Modelo.getValue()+"|"+txt_Ext_10_SubModelo.getValue()+"|"+txt_Ext_10_NumeroSerie.getValue());
	        		}
        		}
        		//Termina Crear Complemento Comercio
        		
        		        		
        		
        		//Inicia Crear Addenda Teaylor
        		if(gb_add_Farms.isOpen() || !txt_add_Farms_00_CURRENCY.getValue().equals("") || !txt_add_Farms_01_SHIPPING_DATE.getValue().equals("") || !txt_add_Farms_01_SHIPPED_FROM.getValue().equals("") || !txt_add_Farms_01_FREIGHT_TERMS.getValue().equals("") || !txt_add_Farms_02_BUYER_ID.getValue().equals("") || !txt_add_Farms_03_PO.getValue().equals("") || !txt_add_Farms_04_Remittance_Address.getValue().equals("")|| !txt_add_Farms_05_SHIPPED_TO_NAME.getValue().equals("") || !txt_add_Farms_05_SHIPPED_TO_ADDRESS.getValue().equals("") || !txt_add_Farms_05_SHIPPED_TO_CITY.getValue().equals("") || !txt_add_Farms_05_SHIPPED_ID_TAX_RFC.getValue().equals("")){
        			writer.println("<cfdi:Addenda>");
        			writer.println("	<AddendaTaylorF>");
        			writer.println("		<PrintableInfo>");
        			
        			
        				
        			
	        		if(gb_Add_Farms_00.isOpen() || !txt_add_Farms_00_CURRENCY.getValue().equals("")){
//	        			writer.println("00|"+txt_add_Farms_00_CURRENCY.getValue());
	        			writer.println("			<currency>"+txt_add_Farms_00_CURRENCY.getValue()+"</currency>");
	        			
	        		}else{
	        			writer.println("			<currency/>");
	        		}
	        		
	        		if(gb_Add_Farms_01.isOpen() || !txt_add_Farms_01_SHIPPING_DATE.getValue().equals("") || !txt_add_Farms_01_SHIPPED_FROM.getValue().equals("") || !txt_add_Farms_01_FREIGHT_TERMS.getValue().equals("")){
	        			writer.println("			<shipingInfo>");
	        			
	        			if(!txt_add_Farms_01_SHIPPING_DATE.getValue().equals("")){
	        				writer.println("				<shippingDate>"+txt_add_Farms_01_SHIPPING_DATE.getValue()+"</shippingDate>");
	        			}else{
	        				writer.println("				<shippingDate/>");
	        			}
	        			
	        			if(!txt_add_Farms_01_SHIPPED_FROM.getValue().equals("")){
	        				writer.println("				<shippedFrom>"+txt_add_Farms_01_SHIPPED_FROM.getValue()+"</shippedFrom>");
	        			}else{
	        				writer.println("				<shippedFrom/>");
	        			}

						if(!txt_add_Farms_01_FREIGHT_TERMS.getValue().equals("")){
							writer.println("				<freightTerms>"+txt_add_Farms_01_FREIGHT_TERMS.getValue()+"</freightTerms>");
	        			}else{
	        				writer.println("				<freightTerms/>");
	        			}
//	        			writer.println("01|"+txt_add_Farms_01_SHIPPING_DATE.getValue()+"|"+txt_add_Farms_01_SHIPPED_FROM.getValue()+"|"+txt_add_Farms_01_FREIGHT_TERMS.getValue());
	        			writer.println("			</shipingInfo>");
	        		}
	        		
	        		if(gb_Add_Farms_02.isOpen() || !txt_add_Farms_02_BUYER_ID.getValue().equals("")){
//	        			writer.println("02|"+txt_add_Farms_02_BUYER_ID.getValue());
	        			writer.println("				<buyerId>"+txt_add_Farms_02_BUYER_ID.getValue()+"</buyerId>");
        			}else{
        				writer.println("				<buyerId/>");
        			}
	        		
	        		if(gb_Add_Farms_03.isOpen() || !txt_add_Farms_03_PO.getValue().equals("")){
//	        			writer.println("03|"+txt_add_Farms_03_PO.getValue());
	        			writer.println("				<po>"+txt_add_Farms_03_PO.getValue()+"</po>");
        			}else{
        				writer.println("				<po/>");
        			}
	        		
	        		if(gb_Add_Farms_04.isOpen() || !txt_add_Farms_04_Remittance_Address.getValue().equals("")){
//	        			writer.println("04|"+txt_add_Farms_04_Remittance_Address.getValue());
	        			writer.println("				<remittanceAddress>"+txt_add_Farms_04_Remittance_Address.getValue()+"</remittanceAddress>");
        			}else{
        				writer.println("				<remittanceAddress/>");
        			}
	        		
	        		if(gb_Add_Farms_05.isOpen() || !txt_add_Farms_05_SHIPPED_TO_NAME.getValue().equals("") || !txt_add_Farms_05_SHIPPED_TO_ADDRESS.getValue().equals("") || !txt_add_Farms_05_SHIPPED_TO_CITY.getValue().equals("") || !txt_add_Farms_05_SHIPPED_ID_TAX_RFC.getValue().equals("")){
	        			writer.println("			<shippingTo>");
//	        			writer.println("05|"+txt_add_Farms_05_SHIPPED_TO_NAME.getValue()+"|"+txt_add_Farms_05_SHIPPED_TO_ADDRESS.getValue()+"|"+txt_add_Farms_05_SHIPPED_TO_CITY.getValue()+"|"+txt_add_Farms_05_SHIPPED_ID_TAX_RFC.getValue());
	        			if(!txt_add_Farms_05_SHIPPED_TO_NAME.getValue().equals("")){
//		        			
		        			writer.println("				<name>"+txt_add_Farms_05_SHIPPED_TO_NAME.getValue()+"</name>");
	        			}else{
	        				writer.println("				<name/>");
	        			}
	        			
	        			if(!txt_add_Farms_05_SHIPPED_TO_ADDRESS.getValue().equals("")){
//		        			
		        			writer.println("				<address>"+txt_add_Farms_05_SHIPPED_TO_ADDRESS.getValue()+"</address>");
	        			}else{
	        				writer.println("				<address/>");
	        			}
	        			
	        			if(!txt_add_Farms_05_SHIPPED_TO_CITY.getValue().equals("")){
//		        			
		        			writer.println("				<city>"+txt_add_Farms_05_SHIPPED_TO_CITY.getValue()+"</city>");
	        			}else{
	        				writer.println("				<city/>");
	        			}
	        			
	        			if(!txt_add_Farms_05_SHIPPED_ID_TAX_RFC.getValue().equals("")){
//		        			
		        			writer.println("				<fedIdTax>"+txt_add_Farms_05_SHIPPED_ID_TAX_RFC.getValue()+"</fedIdTax>");
	        			}else{
	        				writer.println("				<fedIdTax/>");
	        			}
	        			
	        			writer.println("			</shippingTo>");
	        		}
	        		
	        			        		
	        		if(gb_Add_Farms_06.isOpen() || !txt_add_Farms_06_OUR_ORDER_No.getValue().equals("")){
//	        			writer.println("06|"+txt_add_Farms_06_OUR_ORDER_No.getValue());
	        			writer.println("			<ourOderNo>"+txt_add_Farms_06_OUR_ORDER_No.getValue()+"</ourOderNo>");
        			}else{
        				writer.println("			<ourOderNo/>");
        			}
	        		
	        		
	        		if(gb_Add_Farms_07.isOpen() || !txt_add_Farms_07_SALESMAN.getValue().equals("")){
//	        			writer.println("07|"+txt_add_Farms_07_SALESMAN.getValue());
	        			writer.println("			<salesMan>"+txt_add_Farms_07_SALESMAN.getValue()+"</salesMan>");
        			}else{
        				writer.println("			<salesMan/>");
        			}
	        		
	        		if(gb_Add_Farms_08.isOpen() || !txt_add_Farms_08_CARRIER.getValue().equals("") || !txt_add_Farms_08_LICENSE_No.getValue().equals("")){
	        			writer.println("			<transport>");
//	        			writer.println("08|"+txt_add_Farms_08_CARRIER.getValue()+"|"+txt_add_Farms_08_LICENSE_No.getValue());
	        			
	        			if(!txt_add_Farms_08_CARRIER.getValue().equals("")){
//		        			
		        			writer.println("				<carrier>"+txt_add_Farms_08_CARRIER.getValue()+"</carrier>");
	        			}else{
	        				writer.println("				<carrier/>");
	        			}
	        			
	        			if(!txt_add_Farms_08_LICENSE_No.getValue().equals("")){
//		        			
		        			writer.println("				<licenseNo>"+txt_add_Farms_08_LICENSE_No.getValue()+"</licenseNo>");
	        			}else{
	        				writer.println("				<licenseNo/>");
	        			}
	        			
	        			
	        			writer.println("			</transport>");
	        		}
	        		
	        		if(gb_Add_Farms_09.isOpen() || !txt_add_Farms_09_WRITTEN_AMOUNT_ENGLISH.getValue().equals("") || !txt_add_Farms_09_WRITTEN_AMOUNT_SPANISH.getValue().equals("")){
//	        			writer.println("09|"+txt_add_Farms_09_WRITTEN_AMOUNT_ENGLISH.getValue()+"|"+txt_add_Farms_09_WRITTEN_AMOUNT_SPANISH.getValue());
	        			writer.println("			<writtenAmount>");
	        			
	        			if(!txt_add_Farms_09_WRITTEN_AMOUNT_ENGLISH.getValue().equals("")){
//		        			
		        			writer.println("				<english>"+txt_add_Farms_09_WRITTEN_AMOUNT_ENGLISH.getValue()+"</english>");
	        			}else{
	        				writer.println("				<english/>");
	        			}
	        			
	        			if(!txt_add_Farms_09_WRITTEN_AMOUNT_SPANISH.getValue().equals("")){
//		        			
		        			writer.println("				<spanish>"+txt_add_Farms_09_WRITTEN_AMOUNT_SPANISH.getValue()+"</spanish>");
	        			}else{
	        				writer.println("				<spanish/>");
	        			}
	        			
	        			writer.println("			</writtenAmount>");
	        			
	        			
	        		}
	        		
	        		if(gb_Add_Farms_10.isOpen() || !txt_add_Farms_10_TOTAL_QUANTITY.getValue().equals("")){
//	        			writer.println("10|"+txt_add_Farms_10_TOTAL_QUANTITY.getValue());
	        			writer.println("			<totalQuantity>"+txt_add_Farms_10_TOTAL_QUANTITY.getValue()+"</totalQuantity>");
        			}else{
        				writer.println("			<totalQuantity/>");
        			}
	        		
	        		if(gb_Add_Farms_11.isOpen() || !txt_add_Farms_11_COMMENTS_LINE_1.getValue().equals("")){
//	        			writer.println("11|"+txt_add_Farms_11_COMMENTS_LINE_1.getValue());
	        			writer.println("			<commentsLine1>"+txt_add_Farms_11_COMMENTS_LINE_1.getValue()+"</commentsLine1>");
	        			
	        		}else{
	        			writer.println("			<commentsLine1/>");
	        		}
	        		
	        		if(gb_Add_Farms_12.isOpen() || !txt_add_Farms_12_COMMENTS_LINE_2.getValue().equals("")){
//	        			writer.println("12|"+txt_add_Farms_12_COMMENTS_LINE_2.getValue());
	        			writer.println("			<commentsLine2>"+txt_add_Farms_12_COMMENTS_LINE_2.getValue()+"</commentsLine2>");
	        			
	        		}else{
	        			writer.println("			<commentsLine2/>");
	        		}
	        		
	        		if(gb_Add_Farms_13.isOpen() || !txt_add_Farms_13_COMMENTS_LINE_3.getValue().equals("")){
//	        			writer.println("13|"+txt_add_Farms_13_COMMENTS_LINE_3.getValue());
	        			writer.println("			<commentsLine3>"+txt_add_Farms_13_COMMENTS_LINE_3.getValue()+"</commentsLine3>");
	        			
	        		}else{
	        			writer.println("			<commentsLine3/>");
	        		}
	        		
	        		if(gb_Add_Farms_14.isOpen() || !txt_add_Farms_14_COMMENTS_LINE_4.getValue().equals("")){
//	        			writer.println("14|"+txt_add_Farms_14_COMMENTS_LINE_4.getValue());
	        			writer.println("			<commentsLine4>"+txt_add_Farms_14_COMMENTS_LINE_4.getValue()+"</commentsLine4>");
	        			
	        		}else{
	        			writer.println("			<commentsLine4/>");
	        		}
	        		if(gb_Add_Farms_15.isOpen() || !txt_add_Farms_15_WEIGHT_CMTY.getValue().equals("") || !txt_add_Farms_15_WEIGHT_DSC.getValue().equals("") || !txt_add_Farms_15_WEIGHT_NET_WT.getValue().equals("")){
//	        			writer.println("15|"+txt_add_Farms_15_WEIGHT_CMTY.getValue()+"|"+txt_add_Farms_15_WEIGHT_DSC.getValue()+"|"+txt_add_Farms_15_WEIGHT_NET_WT.getValue());
	        			writer.println("			<weights>");
	        			
//	        			
	      		            	            	
	      		            	for (Object item : itemFarmsList.getItems()) {
	      		                	//Los tenemos que ir cargando porque los items de las pï¿½ginas que no se hayan visitado
	      		                	//no estarï¿½n cargados
	      		            		itemFarmsList.renderItem((Listitem) item);
	      		        	        String i = "";
	      		        	        for (Object cell : ((Listitem) item).getChildren()) {
	      		        	        	//Solo mostramos las celdas que estï¿½n visibles
	      		        	        	if(((Listcell)cell).getListheader()!=null){
	      		        		        	 if(((Listcell)cell).getListheader().isVisible()){
	      		        		        		 i += ((Listcell) cell).getLabel() ;
	      		        		        	 }
	      		        	        	}
	      		        	        	
	      		        	        	
	      		        	        }
	      		        	        
	      		        	        System.out.println(i);
	      		        	      String[] Lis_Farms = i.split("\\|");
	      					    
//	      			            String Metodo_Pago = "";
	      						   
	      						for (int x = 0; x < Lis_Farms.length; x++) {
	      									
//	      							Metodo_Pago = LisProduct_MP[0];
//	      							System.out.println(LisProduct_MP[0]);
	      							System.out.println(Lis_Farms[0]);
	      							System.out.println(Lis_Farms[1]);
	      							System.out.println(Lis_Farms[2]);
	      							
	      							
	      						}
	      						writer.println("				<weight cmty=\""+Lis_Farms[0]+"\" dsc=\""+Lis_Farms[1]+"\" netWt=\""+Lis_Farms[2]+"\"/>");
//	      		        	        fgdfg
	      		        	       
//	      		        	        System.out.println(i + "\n");
//	      		        	        writer.println("03|"+i);
//	      		        	        
//	      		        	        writer.println("cfdi:CfdiRelacionado UUID=\""+i+"\"/>"); 
//	      		        	        sb.append(i + "\n");
	      		                }
//	      		            	writer.println("</cfdi:CfdiRelacionados>");
//	      	            	}
//	      	            }
	        			
//	        			writer.println("				<weight cmty=\""+txt_add_Farms_15_WEIGHT_CMTY.getValue()+"\" dsc=\""+txt_add_Farms_15_WEIGHT_DSC.getValue()+"\" netWt=\""+txt_add_Farms_15_WEIGHT_NET_WT.getValue()+"\"/>");
	        			
	        			writer.println("			</weights>");
	        		}
	        		
	        		writer.println("		</PrintableInfo>");
        			writer.println("	</AddendaTaylorF>");
        			writer.println("</cfdi:Addenda>");
	        		
        			
        			
        		
        		}
        		//Termina Crear  Addenda Teaylor
        		writer.println("</cfdi:Comprobante>");
	            writer.close();
	            
//	            Timbrado(pathIn,txt_Factura_Emi_RFC.getValue()+"_"+txt_Factura_Serie.getValue()+txt_Factura_Folio.getValue()+".XML");
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
//	 public void Timbrado(String PATH, String Nombre){
//		 
//				
//				//inicializamos la clase signcfdrequest
//				signcfdrequest cfdrequest = new signcfdrequest();
//				
//				//inicializamos nuesta clase
//				signcfd nada = new signcfd();
//				
//				try
//				{					
//					//asignamos los archivos a procesar en este caso solo es uno
//					cfdrequest.setcfd2signlist(cfd2signlist(pathin,nombre));
//					//asignamos la bandera para timbrar, si solo quieren sellar ponemos false
//					cfdrequest.setrequeststamp(true);
//					//el numero de serie del certificado, dado de alta en la bd
//					cfdrequest.setkeyid(keyid);
//					
//					//inicializamos la clase response
//					signcfdresponse cfdresponse = new signcfdresponse();
//					
//					//realizamos la conexion al webservice con la clase sgconecction
//					cfdresponse = (signcfdresponse)utils.sgconecction.getinvoicewsstub().processmessage(cfdrequest);
//					
//					//recorremos la respuesta por si hay mas de una
//					for(int i = 0; i < cfdresponse.getcfd2signlistresp().length; i++) 
//					{
//						//si el geterrormsg es nulo quiere decir que todo lo hizo bien
//						if(cfdresponse.getcfd2signlistresp(i).geterrormsg() == null)
//						{
//							system.out.println("el comprobante ha sido almacenado correctamente");
//							messagebox.show("el comprobante ha sido almacenado correctamente ", "! informaci�n !", org.zkoss.zul.messagebox.ok, "");
//							//guarda el archivo ya procesado, leyendo los bytes del xml que regresa el ws
//							utils.fileutils.savebytestofile(pathout + cfdresponse.getcfd2signlistresp(i).getcfd().getfilename(), cfdresponse.getcfd2signlistresp(i).getcfd().getdata());
//							system.out.println("se ha guardado el archivo en la siguiente ruta: " + pathout);
//							
//							borrararchivo(pathin+nombre);
//						}
//						//hubo un error al el xml
//						else
//						{
//							messagebox.show(" "+cfdresponse.getcfd2signlistresp(i).geterrormsg()+" ", "! aviso !", org.zkoss.zul.messagebox.ok, "");
//							system.err.println("se ha encontrado el siguiente error: " + cfdresponse.getcfd2signlistresp(i).geterrormsg());
//							borrararchivo(pathin+nombre);
//						}
//					}
//					
//				}
//				catch (exception e)
//				{
//					e.printstacktrace();
//				}
//			
//	 }
	 
	 public static void borrarArchivo(String Archivo){
		 try{
	
	         File archivo = new File(Archivo);
	
	         boolean estatus = archivo.delete();;
	
	         if (!estatus) {
	
	             System.out.println("Error no se ha podido eliminar el  archivo");
	
	        }else{
	
	             System.out.println("Se ha eliminado el archivo exitosamente");
	
	        }
	
	     }catch(Exception e){
	
	        System.out.println(e);
	
	     }
	}
	 
	 public void CrearComplementoComercioExt() {
		 
		 
		 
	 }

	/**
	 * SetVisible for components by checking if there's a right for it.
	 */
	@SuppressWarnings("unused")
//	private void doCheckRights() {
//
//		UserWorkspace workspace = getUserWorkspace();
//
//		ordenCompraDialogWindow.setVisible(true);
//
//		tab_UserDialog_Details.setVisible(true);
//		tabpanel_UserDialog_Details.setVisible(true);
//
//		btnHelp.setVisible(true);
//		btnNew.setVisible(true);
//		btnEdit.setVisible(true);
//		btnDelete.setVisible(true);
//		btnSave.setVisible(true);
//		btnClose.setVisible(true);
//	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++ Components events +++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * If we close the dialog window. <br>
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onClose$ordenCompraDialogWindow(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		doClose();
	}
	
	public void onSelect$cb_Moneda(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());		}
	
		Listitem itemMoneda = cb_Moneda.getSelectedItem();
		String nombre = itemMoneda.getLabel();
		if( cb_Moneda.getSelectedIndex() >= 0){
			if (nombre.startsWith("MXN")){ 
				label_tipoCambio.setVisible(false);
				db_TipoCambio.setVisible(false);
				
				gb_Comple_Pagos.setVisible(false);
			}
			if (nombre.startsWith("USD")){ 
				label_tipoCambio.setVisible(true);
				db_TipoCambio.setVisible(true);
				
				gb_Comple_Pagos.setVisible(false);
			}
			if (nombre.startsWith("XXX")){ 
				label_tipoCambio.setVisible(true);
				db_TipoCambio.setVisible(true);
				
				gb_Comple_Pagos.setVisible(true);
//				lb_Factura_Tipo_Compro.setSelectedIndex(4);
			}
				
			
		}else{
			label_tipoCambio.setVisible(false);
			db_TipoCambio.setVisible(false);
		}

		
	}
	
	public void onSelect$lb_Pago_01_MonedaP(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());		}
	
		Listitem itemMoneda = lb_Pago_01_MonedaP.getSelectedItem();
		String nombre = itemMoneda.getLabel();
		if( cb_Moneda.getSelectedIndex() >= 0){
			if (nombre.startsWith("MXN")){ 
				label_TipoCambioP.setVisible(false);
				txt_Pago_01_TipoCambioP.setVisible(false);
				
			}else{
				label_TipoCambioP.setVisible(true);
				txt_Pago_01_TipoCambioP.setVisible(true);
			}
			
		}	
		
	}
	
	
	
	public void onSelect$lb_Factura_Tipo_Compro(Event event) throws InterruptedException {
		
		
		
		Listitem itemTipo = lb_Factura_Tipo_Compro.getSelectedItem();
		String nombre = itemTipo.getLabel();
		if( lb_Factura_Tipo_Compro.getSelectedIndex() >= 0){
			if (nombre.startsWith("Pago")){ 
//				label_tipoCambio.setVisible(false);
//				db_TipoCambio.setVisible(false);
				lb_FormaPagoFactDialog.setSelectedIndex(0);
				lb_MetodoPagoFactDialog.setSelectedIndex(0);
				
				cb_Moneda.setSelectedIndex(3);
				gb_Comple_Pagos.setVisible(true);
				gb_Comple_CP.setVisible(false);
			}else{
				gb_Comple_Pagos.setVisible(false);
			}
			
			if (nombre.startsWith("Traslado")){ 
//				label_tipoCambio.setVisible(false);
//				db_TipoCambio.setVisible(false);
				lb_FormaPagoFactDialog.setSelectedIndex(0);
				lb_MetodoPagoFactDialog.setSelectedIndex(0);
				gb_Comple_CP.setVisible(true);
			}
			
			if (nombre.startsWith("Ingreso")){ 
//				label_tipoCambio.setVisible(false);
//				db_TipoCambio.setVisible(false);
				gb_Comple_CP.setVisible(true);
			}
			
			if (nombre.startsWith("Egreso")){ 
//				label_tipoCambio.setVisible(false);
//				db_TipoCambio.setVisible(false);
				gb_Comple_CP.setVisible(false);
			}
			
//			if (nombre.startsWith("Ingreso")){ 
////				label_tipoCambio.setVisible(false);
////				db_TipoCambio.setVisible(false);
//				
//				gb_Comple_CP.setVisible(true);
//			}else{
//				gb_Comple_CP.setVisible(false);
//			}
		}
		
	}
	
//	public void onSelect$ib_clave(Event event) throws InterruptedException {
//		System.out.println("Si entra");
//	}
//	public void onOK$ib_clave(Event event) throws JRException {
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//		System.out.println("Si entra al enter");
//		
//		
//	}
	
	
	public void onSelect$cb_Iva(Event event) throws InterruptedException {
		
		

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());		}
	
		Listitem itemMoneda = cb_Iva.getSelectedItem();
		String nombre = itemMoneda.getLabel();


//		cb_tasa.setModel(new ListModelList(ventaServ.getTasa(id_corporativo,nombre)));
//		cb_tasa.setItemRenderer(new ComboFactoryModelItemRenderer());
				
	}
	
public void onSelect$lb_Cuentas(Event event) throws InterruptedException {
		
		

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());		}
	
		Listitem item = lb_Cuentas.getSelectedItem();
		ListModelList lml1 = (ListModelList) lb_Cuentas.getListModel();
		ComboFactory comboFactory = (ComboFactory) lml1.get(item.getIndex());
		
		
		System.out.println("comboFactory.getId(): "+comboFactory.getId());
		
//		if(item != null && comboFactory.getId() != -1){
//			TbFormaPago formaPagoTmp = new TbFormaPago();
//			
//			formaPagoTmp = (TbFormaPago) ventaService.getBusinessObjectPorId(
//					comboFactory.getId(), TbFormaPago.class,
//					"nukidFormaPago");
//		}

		
//		TbCuenta obcuenta = ventaServ.getCuentaTb(comboFactory.getId());
		


//		if(obcuenta.getOrden() == 1){
//			lb_Ordenes.setModel(new ListModelList(ventaServ.getOrdenes(id_corporativo)));
//			lb_Ordenes.setItemRenderer(new ComboFactoryModelItemRenderer());
//			
//			row_ordenes.setVisible(true);
//			
//		}else{
//			row_ordenes.setVisible(false);
//		}
//		
//		if(obcuenta.getDepartamento() == 1){
//			
//			lb_Departamento.setModel(new ListModelList(ventaServ.getPerfilOc()));
//			lb_Departamento.setItemRenderer(new ComboFactoryModelItemRenderer());
//			
//			row_Depa.setVisible(true);
//		}else{
//			row_Depa.setVisible(false);
//		}
		
		
		
		
		
//		if( cb_Moneda.getSelectedIndex() >= 0){
//			if (nombre.startsWith("MXN")){ 
//				label_tipoCambio.setVisible(false);
//				db_TipoCambio.setVisible(false);
//			}else{
//				label_tipoCambio.setVisible(true);
//				db_TipoCambio.setVisible(true);
//			}
//		}else{
//			label_tipoCambio.setVisible(false);
//			db_TipoCambio.setVisible(false);
//		}

		
	}

public void onSelect$lb_CatPort_00_TranspInternac(Event event) throws InterruptedException {
	
	

	if (logger.isDebugEnabled()) {
		logger.debug("--> " + event.toString());		}

		if(lb_CatPort_00_TranspInternac.getSelectedIndex() > 0){
			if(lb_CatPort_00_TranspInternac.getSelectedIndex() == 1){
				lb_CatPort_00_EntradaSalidaMerc.setVisible(true);
				
				lb_CatPort_00_ViaEntradaSalida.setVisible(true);
				
				
			}else{
				lb_CatPort_00_EntradaSalidaMerc.setVisible(false);
				lb_CatPort_00_ViaEntradaSalida.setVisible(false);
				lb_CatPort_00_EntradaSalidaMerc.setSelectedIndex(0);
				lb_CatPort_00_ViaEntradaSalida.setSelectedIndex(0);
			}
		}

	
}

public void onCheck$ch_Retenciones(Event event) throws Exception {
	
	

	if (logger.isDebugEnabled()) {
		logger.debug("--> " + event.toString());		}

	System.out.println("Selecciona el Check o no Soluciona");
if (cb_Moneda.getSelectedIndex() < 0){
		
		Messagebox.show("Selecciona Moneda", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
		return;
		
	}

	
	if(ch_Retenciones.isChecked()){
		label_RetIva.setVisible(true);
		tb_RetIvaFactura.setVisible(true);
	}else{
		label_RetIva.setVisible(false);
		tb_RetIvaFactura.setVisible(false);
	}
		

	boolean desglosaIva = true;
//	ventaserv.setIshonorario(checkbox_Hono.isChecked());
//	if (tb_RFCCteFactura.getValue() != null
//			&& !tb_RFCCteFactura.getValue().equals(""))
//		desglosaIva = true;
	
	
	String moneda = "MXN";
	Listitem itemMoneda = cb_Moneda.getSelectedItem();
	String nombre = itemMoneda.getLabel();
	if (nombre.startsWith("EUR")) 
		moneda = "EUR";
	else if (nombre.startsWith("USD")) 
		moneda = "USD";
	else if (nombre.startsWith("CAD")) 
		moneda = "CAD";
	
//	Listitem itemTasa = cb_tasa.getSelectedItem();
//	String ValorTasa = itemTasa.getLabel();
	
	double dobleTasa = Double.parseDouble(cb_tasa.getSelectedItem().getLabel());
	System.out.println("dobleTasa: "+ dobleTasa);
	System.out.println("cb_tasa.getSelectedItem().getLabel( : "+ cb_tasa.getSelectedItem().getLabel());
//	ventaserv.calcuaTotalesDirect(listBoxDetalleFactura, detalle,
//			desglosaIva,cb_tasa.getSelectedItem().getLabel(), tb_IvaFactura, tb_RetIvaFactura, tb_RetISRFactura,
//			tb_SubtotalFactura, tb_TotalFactura, tb_TotalLetraFactura,
//			false, moneda,ch_Retenciones);
	
if(!tb_TotalFactura.getValue().equals("") && tb_TotalFactura.getValue() != null){
		
		
		double doble = Double.parseDouble(tb_TotalFactura.getValue());
//		if(doble > getcorp.getCantidadMax()){
//			id_row_Autoriza_area.setVisible(true);
//		}else{
//			id_row_Autoriza_area.setVisible(false);
//		}
			
	}
	
}

	/**
	 * when the "save" button is clicked. <br>
	 * 
	 * @param event
	 * @throws Exception 
	 */
	public void onClick$btn_Guardar(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Entro a Guardar--> " + event.toString());
		}
		
//		 System.out.printf("%.2f: Default locale\n", 3.1415926535);
//	        System.out.printf(Locale.GERMANY, "%.2f: Germany locale\n", 3.1415926535);
//	        System.out.printf(Locale.US, "%.2f: US locale\n", 3.1415926535);
		
			
//		recorrelista();
		
//		System.out.println("Fecha Valor: " + dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getValue());
//		System.out.println("Fecha Texto: " + dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getText());
//		System.out.println("Fecha Texto: " + dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getText().replace(" AM ", "T").replace(" PM ", "T").replace(" a.m. ", "T").replace(" p.m. ", "T").replace(" A.M. ", "T").replace(" P.M. ", "T"));
		
		if(!Valida()){
			return;
		}
		
		
		
		
//		System.out.println("Lista: "+ itemList.getItems().size());
		
		if(itemList.getItems().size() == 0){
			Messagebox.show("Agrega por lo menos 1 Concepto ", "! Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
		}
		
		try {
			System.out.println("Entro a Crear un Archivo");
			
			CrearArchivo();
			
			System.out.println("Salio a Crear un Archivo");
//			Messagebox.show("Se Creo el Archivo Exitosamente ", "! Información !", org.zkoss.zul.Messagebox.OK, "");
		} catch (Exception e) {
			Messagebox.show("Se encontro un error favor se ponerse en contacto con el Administrador del sistema, Error: "+e.toString(), "! Información !", org.zkoss.zul.Messagebox.OK, "");
			// TODO: handle exception
		}
		
	
		
//		Limpiar();
		

	}
	
	public void recorrelista(){
		
		String puntoYComa = ";"; 
		
		for (Object item : itemRelaList.getItems()) {
        	//Los tenemos que ir cargando porque los items de las pï¿½ginas que no se hayan visitado
        	//no estarï¿½n cargados
			itemRelaList.renderItem((Listitem) item);
	        String i = "";
	        for (Object cell : ((Listitem) item).getChildren()) {
	        	//Solo mostramos las celdas que estï¿½n visibles
	        	if(((Listcell)cell).getListheader()!=null){
		        	 if(((Listcell)cell).getListheader().isVisible()){
		        		 i += ((Listcell) cell).getLabel() ;
		        	 }
	        	}
	        }
	        System.out.println(i + "\n");
//	        sb.append(i + "\n");
        }
		
	}
	
//	public void onClick$ib_clave(Event event) throws Exception {
//
//
//
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());		}
//
//		System.out.println("Selecciona el Check o no Soluciona");
//	if (cb_Moneda.getSelectedIndex() < 0){
//			
//			Messagebox.show("Selecciona Moneda", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			return;
//			
//		}
//
//		
//		if(ch_Retenciones.isChecked()){
//			label_RetIva.setVisible(true);
//			tb_RetIvaFactura.setVisible(true);
//		}else{
//			label_RetIva.setVisible(false);
//			tb_RetIvaFactura.setVisible(false);
//		}
//			
//
//		boolean desglosaIva = true;
////		ventaserv.setIshonorario(checkbox_Hono.isChecked());
////		if (tb_RFCCteFactura.getValue() != null
////				&& !tb_RFCCteFactura.getValue().equals(""))
////			desglosaIva = true;
//		
//		
//		String moneda = "MXN";
//		Listitem itemMoneda = cb_Moneda.getSelectedItem();
//		String nombre = itemMoneda.getLabel();
//		if (nombre.startsWith("EUR")) 
//			moneda = "EUR";
//		else if (nombre.startsWith("USD")) 
//			moneda = "USD";
//		else if (nombre.startsWith("CAD")) 
//			moneda = "CAD";
//		
////		Listitem itemTasa = cb_tasa.getSelectedItem();
////		String ValorTasa = itemTasa.getLabel();
//		
//		double dobleTasa = Double.parseDouble(cb_tasa.getSelectedItem().getLabel());
//		System.out.println("dobleTasa: "+ dobleTasa);
//		System.out.println("cb_tasa.getSelectedItem().getLabel( : "+ cb_tasa.getSelectedItem().getLabel());
//		ventaserv.calcuaTotalesDirect(listBoxDetalleFactura, detalle,
//				desglosaIva,cb_tasa.getSelectedItem().getLabel(), tb_IvaFactura, tb_RetIvaFactura, tb_RetISRFactura,
//				tb_SubtotalFactura, tb_TotalFactura, tb_TotalLetraFactura,
//				false, moneda,ch_Retenciones);
//		
//	if(!tb_TotalFactura.getValue().equals("") && tb_TotalFactura.getValue() != null){
//			
//			
//			double doble = Double.parseDouble(tb_TotalFactura.getValue());
//			if(doble > getcorp.getCantidadMax()){
//				id_row_Autoriza_area.setVisible(true);
//			}else{
//				id_row_Autoriza_area.setVisible(false);
//			}
//				
//		}
//
//		
//	}
	
	/**
	 * when the "save" button is clicked. <br>
	 * 
	 * @param event
	 * @throws Exception 
	 */
	public void onClick$btn_Guarda_ValidaOC(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		
		MailSender msProv = new MailSender();
		MailSender msCreador = new MailSender();
		MailSender msCXP = new MailSender();
		
		Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime());
		
//		if(Goog_ok.isChecked() == true){
//			MemoryDetalleComp.setNuautorizadoCxP(ACTIVA_OC_CXP);
//			MemoryDetalleComp.setEstatusFactura(IN_STATUS_ORDEN_COMPRA_PROCESO_PAGO);
//		}
//		if(Good_Dont.isChecked() == true){
//			if (tb_Good_Rechazo.getValue().equals("")){
//				
//				Messagebox.show("Ingresa una descripciï¿½n por el cual no se esta dando el visto bueno a la Factura", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				return;
//				
//			}
//			
//			MemoryDetalleComp.setNuautorizadoCxP(NO_ACTIVA_OC_CXP);
//			MemoryDetalleComp.setTexto_good(tb_Good_Rechazo.getValue());
//			MemoryDetalleComp.setEstatusFactura(IN_STATUS_ORDEN_COMPRA_AUTORIZADA);
//			
//		}
//		
//		MemoryDetalleComp.setFecha_good(ts);
//		MemoryDetalleComp.setId_usuario_good(l_obUser.getIdUsuario());
//		
//		
//		
//		TbComprobantes detalle = new TbComprobantes();
//		TbRfcTbUsuario rfcuser = new TbRfcTbUsuario();
//		TbRfc rfc = new TbRfc();
//		
//		rfcuser = userserv.gettbRfcUSer(id_corporativo, MemoryDetalleComp.getIdProveedor());
//		rfc = userserv.getRFCPojo(rfcuser.getTbRfc().getIdRfc());
//		
//		Calendar c = Calendar.getInstance();
//				
//		System.out.println(rfc.getCredito());
		
//		c.add(Calendar.DAY_OF_YEAR, Integer.parseInt(rfc.getCredito()));
//		System.out.println("+52 dÃ­as: " + formatearCalendar(c));
//		
//		Timestamp timestamp = new Timestamp(c.getTimeInMillis());
//		
//		detalle.setFecha_limite_pago(timestamp);
//		
//		try{
//			
//			if(MemoryDetalleComp.getNuautorizadoCxP() == 1){
//				ventaServ.setUpdateActivaOC_CXP(MemoryDetalleComp);
//				
//				ventaServ.setUpdateActivaFact_CXP(detalle, MemoryDetalleComp.getIdDetalleComprobante());
//			}
//			if(MemoryDetalleComp.getNuautorizadoCxP() == 0){
//				ventaServ.setUpdateActivaOC_CXP(MemoryDetalleComp);
//				
//				ventaServ.setDeleteActivaFact_CXP(detalle, MemoryDetalleComp.getIdDetalleComprobante());
//			}
//		
		
		
		
//		Messagebox.show("Se Activo Correctamente la Factura", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//		if(anOrden.getNuautorizadoCxP() == 1){
			Goog_ok.setVisible(true);
			Good_Dont.setVisible(true);
//			check_Valida.setChecked(true);
//			check_Valida.setDisabled(true);
			btn_Guarda_ValidaOC.setDisabled(true);
			Goog_ok.setDisabled(true);
			Good_Dont.setDisabled(true);
			tb_Good_Rechazo.setDisabled(true);
			
//			if(MemoryDetalleComp.getNuautorizadoCxP() == 1){
//				
//				msProv.setTo(txt_CorreoProv.getValue().trim());
//				msProv.setMessage("Su Factura fue Aceptada");
////				msProv.setMessage("Su Factura fue rechazada"+  "\n" + " Motivo del rechazo: "+ tb_Good_Rechazo.getValue());
//				
//				if(msProv.sendMessageUser())
//				{
//				
//					Messagebox.show("Correo enviado correctamente al Proveedor", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//				}
//				else
//				{
//					Messagebox.show("No se pudo Realizar la operaciï¿½n del correo al Proveedor", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//					
//				}
//				
//				msCreador.setTo(Correo_Creador.trim());
//				msCreador.setMessage("Su Factura fue Aceptada");
////				msCreador.setMessage("Su Factura fue rechazada"+  "\n" + " Motivo del rechazo: "+ tb_Good_Rechazo.getValue());
//				
//				if(msCreador.sendMessageUser())
//				{
//				
//					Messagebox.show("Correo enviado correctamente al Creador de la Orden", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//				}
//				else
//				{
//					Messagebox.show("No se pudo Realizar la operaciï¿½n del correo al Creador de la Orden", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//					
//				}
//				
//				
//				
//				TbPerfilTbUsuario perf_User = usuarioService.getPerfilUser(MemoryDetalleComp.getIdProveedor(), id_corporativo);
//				
//				perf_User.getTbPerfil().getIdPerfil();
//				
//				List<String> str_User = usuarioService.gettbPersonaCxP(perf_User.getTbPerfil().getIdPerfil());
//				
////				ArrayList<String> array = new ArrayList<>();
//				int iTest = 0;
//				for(int i=0; i < str_User.size(); i++){
//				  System.out.println(str_User.get(i));
//				  				  
//				  	msCXP.setTo(usuarioService.getUsuarioGFI_Correo(id_corporativo, Integer.parseInt(str_User.get(i))));
//					msCXP.setMessage("Su Factura fue Aceptada");
////					msCreador.setMessage("Su Factura fue rechazada"+  "\n" + " Motivo del rechazo: "+ tb_Good_Rechazo.getValue());
//					
//					if(msCXP.sendMessageUser())
//					{
//					
//						Messagebox.show("Correo enviado correctamente al Creador de la Orden", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//					}
//					else
//					{
//						Messagebox.show("No se pudo Realizar la operaciï¿½n del correo al Creador de la Orden", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//						
//					}
//				}
//				
//				
//				
//				
//				
//				
//			}
//			if(MemoryDetalleComp.getNuautorizadoCxP() == 0){
//				
//				msProv.setTo(txt_CorreoProv.getValue().trim());
////				msProv.setMessage("Su Factura fue Aceptada");
//				msProv.setMessage("Su Factura fue rechazada"+  "\n" + " Motivo del rechazo: "+ MemoryDetalleComp.getTexto_good());
//				
//				if(msProv.sendMessageOrden())
//				{
//				
//					Messagebox.show("Correo enviado correctamente al Proveedor", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//				}
//				else
//				{
//					Messagebox.show("No se pudo Realizar la operaciï¿½n del correo al Proveedor", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//					
//				}
//				
//				msCreador.setTo(Correo_Creador.trim());
////				msCreador.setMessage("Su Factura fue Aceptada");
//				msCreador.setMessage("Su Factura fue rechazada"+  "\n" + " Motivo del rechazo: "+ MemoryDetalleComp.getTexto_good());
//				
//				if(msCreador.sendMessageOrden())
//				{
//				
//					Messagebox.show("Correo enviado correctamente al Creador de la Orden", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//				}
//				else
//				{
//					Messagebox.show("No se pudo Realizar la operaciï¿½n del correo al Creador de la Orden", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//					
//				}
//				
//				TbPerfilTbUsuario perf_User = usuarioService.getPerfilUser(MemoryDetalleComp.getIdProveedor(), id_corporativo);
//				
//				perf_User.getTbPerfil().getIdPerfil();
//				
//				List<String> str_User = usuarioService.gettbPersonaCxP(perf_User.getTbPerfil().getIdPerfil());
//				
////				ArrayList<String> array = new ArrayList<>();
//				int iTest = 0;
//				for(int i=0; i < str_User.size(); i++){
//				  System.out.println(str_User.get(i));
//				  				  
//				  	msCXP.setTo(usuarioService.getUsuarioGFI_Correo(id_corporativo, Integer.parseInt(str_User.get(i))));
//					msCXP.setMessage("Su Factura fue rechazada"+  "\n" + " Motivo del rechazo: "+ MemoryDetalleComp.getTexto_good());
////					msCreador.setMessage("Su Factura fue rechazada"+  "\n" + " Motivo del rechazo: "+ tb_Good_Rechazo.getValue());
//					
//					if(msCXP.sendMessageUser())
//					{
//					
//						Messagebox.show("Correo enviado correctamente al Creador de la Orden", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//					}
//					else
//					{
//						Messagebox.show("No se pudo Realizar la operaciï¿½n del correo al Creador de la Orden", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//						
//					}
//				}
//			}
//			
//			
//		}catch (Exception e) {
//			e.toString();
//			Messagebox.show("No se pudo Activar la Factura", "ï¿½ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////			check_Valida.setChecked(false);
////			check_Valida.setDisabled(false);
//			btn_Guarda_ValidaOC.setDisabled(false);
//		}
		
	}
	
	
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$bt_upload1(Event event) throws InterruptedException {
	
		try {
			
				mediaArchivo1 = Fileupload.get( "Seleccione un archivo","Cargar archivo");
				if(mediaArchivo1 != null){
				Ruta_mediaArchivo1 = saveFile(mediaArchivo1, mediaArchivo1.getName());
	//			System.out.println("Ruta: "+ Ruta_actaConstitu);
								
				
				txt_Documento1.setValue(mediaArchivo1.getName());
				
				txt_Documento1.setDisabled(true);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$bt_upload2(Event event) throws InterruptedException {
		
		try {
			mediaArchivo2 = Fileupload.get( "Seleccione un archivo","Cargar archivo");
			if(mediaArchivo2 != null){
				Ruta_mediaArchivo2 = saveFile(mediaArchivo2, mediaArchivo2.getName());
				System.out.println(mediaArchivo2.getName());
				txt_Documento2.setValue(mediaArchivo2.getName());
				txt_Documento2.setDisabled(true);
	//			mediaArchivo1.getContentType()
	//			seleccionArchivo=true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$bt_upload3(Event event) throws InterruptedException {
		
		try {
			mediaArchivo3 = Fileupload.get( "Seleccione un archivo","Cargar archivo");
			if(mediaArchivo3 != null){
				Ruta_mediaArchivo3 = saveFile(mediaArchivo3, mediaArchivo3.getName());
				System.out.println(mediaArchivo3.getName());
				txt_Documento3.setValue(mediaArchivo3.getName());
				txt_Documento3.setDisabled(true);
	//			mediaArchivo1.getContentType()
	//			seleccionArchivo=true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_Estaciones(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_02_Ubi_Ori_NumEstacion", txt_CatPort_02_Ubi_Ori_NumEstacion);
		map.put("txt_CatPort_02_Ubi_Ori_NombreEstacion", txt_CatPort_02_Ubi_Ori_NombreEstacion);
		map.put("Operador_Destinatario", 1);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/estaciones/estacionesList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_EstacionesDest(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_03_Ubi_Dest_NumEstacion", txt_CatPort_03_Ubi_Dest_NumEstacion);
		map.put("txt_CatPort_03_Ubi_Dest_NombreEstacion", txt_CatPort_03_Ubi_Dest_NombreEstacion);
		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/estaciones/estacionesList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_ClaveUnidadPeso(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_05_Merca_Gral_UnidadPeso", txt_CatPort_05_Merca_Gral_UnidadPeso);
		map.put("Unidad_Peso", 1);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/claveunidadpeso/claveunidadpesoList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_ClaveUnidad_Concepto(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_Factura_Conceptos_ClaveUni", txt_Factura_Conceptos_ClaveUni);
//		map.put("txt_CatPort_06_Merca_Unidad", txt_CatPort_06_Merca_Unidad);	
		map.put("ClaveUnidad", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/claveunidad/claveunidadList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_Deta_ClaveUnidadPeso(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_08_Merca_DetaMerc_UnidadPeso", txt_CatPort_08_Merca_DetaMerc_UnidadPeso);
		map.put("Unidad_Peso", 2);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/claveunidadpeso/claveunidadpesoList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_Plantilla(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_08_Merca_DetaMerc_UnidadPeso", txt_CatPort_08_Merca_DetaMerc_UnidadPeso);
//		map.put("Unidad_Peso", 2);
		map.put("CartaPorteListPlantilla", 2);
		map.put("txt_CatPort_00_Version", txt_CatPort_00_Version);
		map.put("lb_CatPort_00_TranspInternac", lb_CatPort_00_TranspInternac);
		map.put("lb_CatPort_00_EntradaSalidaMerc", lb_CatPort_00_EntradaSalidaMerc);
		map.put("lb_CatPort_00_ViaEntradaSalida", lb_CatPort_00_ViaEntradaSalida);
		map.put("dbb_CatPort_00_TotalDistRec", dbb_CatPort_00_TotalDistRec);
		map.put("lb_CatPort_01_TipoEstacion", lb_CatPort_01_TipoEstacion);
		map.put("txt_CatPort_01_DistanciaRecorrida", txt_CatPort_01_DistanciaRecorrida);
		map.put("txt_CatPort_02_Ubi_Ori_IDOrigen", txt_CatPort_02_Ubi_Ori_IDOrigen);
		map.put("txt_CatPort_02_Ubi_Ori_RFCRemitente", txt_CatPort_02_Ubi_Ori_RFCRemitente);
		map.put("txt_CatPort_02_Ubi_Ori_NombreRemitente", txt_CatPort_02_Ubi_Ori_NombreRemitente);
		map.put("txt_CatPort_02_Ubi_Ori_NumRegIdTrib", txt_CatPort_02_Ubi_Ori_NumRegIdTrib);
		map.put("txt_CatPort_02_Ubi_Ori_ResidenciaFiscal", txt_CatPort_02_Ubi_Ori_ResidenciaFiscal);
		map.put("txt_CatPort_02_Ubi_Ori_NumEstacion", txt_CatPort_02_Ubi_Ori_NumEstacion);
		map.put("txt_CatPort_02_Ubi_Ori_NombreEstacion", txt_CatPort_02_Ubi_Ori_NombreEstacion);
		map.put("lb_CatPort_02_Ubi_Ori_NavegacionTrafico", lb_CatPort_02_Ubi_Ori_NavegacionTrafico);
		map.put("dt_CatPort_02_Ubi_Ori_NFechaHoraSalida", dt_CatPort_02_Ubi_Ori_NFechaHoraSalida);
		map.put("txt_CatPort_04_4_Ubi_Direc_Calle", txt_CatPort_04_4_Ubi_Direc_Calle);
		map.put("txt_CatPort_04_4_Ubi_Direc_NumeroExterior", txt_CatPort_04_4_Ubi_Direc_NumeroExterior);
		map.put("txt_CatPort_04_4_Ubi_Direc_NumeroInterior", txt_CatPort_04_4_Ubi_Direc_NumeroInterior);
		map.put("txt_CatPort_04_4_Ubi_Direc_Colonia", txt_CatPort_04_4_Ubi_Direc_Colonia);
		map.put("txt_CatPort_04_4_Ubi_Direc_Localidad", txt_CatPort_04_4_Ubi_Direc_Localidad);
		map.put("txt_CatPort_04_4_Ubi_Direc_Referencia", txt_CatPort_04_4_Ubi_Direc_Referencia);
		map.put("txt_CatPort_04_4_Ubi_Direc_Municipio", txt_CatPort_04_4_Ubi_Direc_Municipio);
		map.put("txt_CatPort_04_4_Ubi_Direc_Estado", txt_CatPort_04_4_Ubi_Direc_Estado);
		map.put("txt_CatPort_04_4_Ubi_Direc_Pais", txt_CatPort_04_4_Ubi_Direc_Pais);
		map.put("txt_CatPort_04_4_Ubi_Direc_CodigoPostal", txt_CatPort_04_4_Ubi_Direc_CodigoPostal);
		map.put("txt_CatPort_03_Ubi_Dest_IDDestino", txt_CatPort_03_Ubi_Dest_IDDestino);
		map.put("txt_CatPort_03_Ubi_Dest_RFCDestinatario", txt_CatPort_03_Ubi_Dest_RFCDestinatario);
		map.put("txt_CatPort_03_Ubi_Dest_NombreDestinatario", txt_CatPort_03_Ubi_Dest_NombreDestinatario);
		map.put("txt_CatPort_03_Ubi_Dest_NumRegIdTrib", txt_CatPort_03_Ubi_Dest_NumRegIdTrib);
		map.put("txt_CatPort_03_Ubi_Dest_ResidenciaFiscal", txt_CatPort_03_Ubi_Dest_ResidenciaFiscal);
		map.put("txt_CatPort_03_Ubi_Dest_NumEstacion", txt_CatPort_03_Ubi_Dest_NumEstacion);
		map.put("txt_CatPort_03_Ubi_Dest_NombreEstacion", txt_CatPort_03_Ubi_Dest_NombreEstacion);
		map.put("lb_CatPort_03_Ubi_Dest_NavegacionTrafico", lb_CatPort_03_Ubi_Dest_NavegacionTrafico);
		map.put("dt_CatPort_03_Ubi_Dest_FechaHoraProgLlegada", dt_CatPort_03_Ubi_Dest_FechaHoraProgLlegada);
		map.put("txt_CatPort_04_Ubi_Direc_Calle", txt_CatPort_04_Ubi_Direc_Calle);
		map.put("txt_CatPort_04_Ubi_Direc_NumeroExterior", txt_CatPort_04_Ubi_Direc_NumeroExterior);
		map.put("txt_CatPort_04_Ubi_Direc_NumeroInterior", txt_CatPort_04_Ubi_Direc_NumeroInterior);
		map.put("txt_CatPort_04_Ubi_Direc_Colonia", txt_CatPort_04_Ubi_Direc_Colonia);
		map.put("txt_CatPort_04_Ubi_Direc_Localidad", txt_CatPort_04_Ubi_Direc_Localidad);
		map.put("txt_CatPort_04_Ubi_Direc_Referencia", txt_CatPort_04_Ubi_Direc_Referencia);
		map.put("txt_CatPort_04_Ubi_Direc_Municipio", txt_CatPort_04_Ubi_Direc_Municipio);
		map.put("txt_CatPort_04_Ubi_Direc_Estado", txt_CatPort_04_Ubi_Direc_Estado);
		map.put("txt_CatPort_04_Ubi_Direc_Pais", txt_CatPort_04_Ubi_Direc_Pais);
		map.put("txt_CatPort_04_Ubi_Direc_CodigoPostal", txt_CatPort_04_Ubi_Direc_CodigoPostal);
		map.put("txt_CatPort_05_Merca_Gral_PesoBrutoTotal", txt_CatPort_05_Merca_Gral_PesoBrutoTotal);
		map.put("txt_CatPort_05_Merca_Gral_UnidadPeso", txt_CatPort_05_Merca_Gral_UnidadPeso);
		map.put("txt_CatPort_05_Merca_Gral_PesoNetoTotal", txt_CatPort_05_Merca_Gral_PesoNetoTotal);
		map.put("txt_CatPort_05_Merca_Gral_NumTotalMercancias", txt_CatPort_05_Merca_Gral_NumTotalMercancias);
		map.put("txt_CatPort_05_Merca_Gral_CargoPorTasacion", txt_CatPort_05_Merca_Gral_CargoPorTasacion);
		map.put("txt_CatPort_06_Merca_BienesTransp", txt_CatPort_06_Merca_BienesTransp);
		map.put("txt_CatPort_06_Merca_ClaveSTCC", txt_CatPort_06_Merca_ClaveSTCC);
		map.put("txt_CatPort_06_Merca_Descripcion", txt_CatPort_06_Merca_Descripcion);
		map.put("txt_CatPort_06_Merca_Cantidad", txt_CatPort_06_Merca_Cantidad);
		map.put("txt_CatPort_06_Merca_ClaveUnidad", txt_CatPort_06_Merca_ClaveUnidad);
		map.put("txt_CatPort_06_Merca_Unidad", txt_CatPort_06_Merca_Unidad);
		map.put("txt_CatPort_06_Merca_Dimensiones", txt_CatPort_06_Merca_Dimensiones);
		map.put("txt_CatPort_06_Merca_MaterialPeligroso", txt_CatPort_06_Merca_MaterialPeligroso);
		map.put("txt_CatPort_06_Merca_CveMaterialPeligroso", txt_CatPort_06_Merca_CveMaterialPeligroso);
		map.put("txt_CatPort_06_Merca_Embalaje", txt_CatPort_06_Merca_Embalaje);
		map.put("txt_CatPort_06_Merca_DescripEmbalaje", txt_CatPort_06_Merca_DescripEmbalaje);
		map.put("txt_CatPort_06_Merca_PesoEnKg", txt_CatPort_06_Merca_PesoEnKg);
		map.put("txt_CatPort_06_Merca_ValorMercancia", txt_CatPort_06_Merca_ValorMercancia);
		map.put("txt_CatPort_06_Merca_Moneda", txt_CatPort_06_Merca_Moneda);
		map.put("txt_CatPort_06_Merca_FraccionArancelaria", txt_CatPort_06_Merca_FraccionArancelaria);
		map.put("txt_CatPort_06_Merca_UUIDComercioExt", txt_CatPort_06_Merca_UUIDComercioExt);
		map.put("txt_CatPort_07_Merca_CantTrans_Cantidad", txt_CatPort_07_Merca_CantTrans_Cantidad);
		map.put("txt_CatPort_07_Merca_CantTrans_IDOrigen", txt_CatPort_07_Merca_CantTrans_IDOrigen);
		map.put("txt_CatPort_07_Merca_CantTrans_IDDestino", txt_CatPort_07_Merca_CantTrans_IDDestino);
		map.put("lb_CatPort_07_Merca_CantTrans_CvesTransporte", lb_CatPort_07_Merca_CantTrans_CvesTransporte);
		map.put("txt_CatPort_08_Merca_DetaMerc_UnidadPeso", txt_CatPort_08_Merca_DetaMerc_UnidadPeso);
		map.put("txt_CatPort_08_Merca_DetaMerc_PesoBruto", txt_CatPort_08_Merca_DetaMerc_PesoBruto);
		map.put("txt_CatPort_08_Merca_DetaMerc_PesoNeto", txt_CatPort_08_Merca_DetaMerc_PesoNeto);
		map.put("txt_CatPort_08_Merca_DetaMerc_PesoTara", txt_CatPort_08_Merca_DetaMerc_PesoTara);
		map.put("txt_CatPort_08_Merca_DetaMerc_NumPiezas", txt_CatPort_08_Merca_DetaMerc_NumPiezas);
		map.put("txt_CatPort_09_Merca_AutoTransF_PermSCT", txt_CatPort_09_Merca_AutoTransF_PermSCT);
		map.put("txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT", txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT);
		map.put("txt_CatPort_09_Merca_AutoTransF_NombreAseg", txt_CatPort_09_Merca_AutoTransF_NombreAseg);
		map.put("txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro", txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro);
		map.put("txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular", txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular);
		map.put("txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM", txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM);
		map.put("txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM", txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM);
		map.put("txt_CatPort_11_Merca_AutoTransF_Remolque_SubTipoRem", txt_CatPort_11_Merca_AutoTransF_Remolque_SubTipoRem);
		map.put("txt_CatPort_11_Merca_AutoTransF_Remolque_Placa", txt_CatPort_11_Merca_AutoTransF_Remolque_Placa);
		map.put("txt_CatPort_12_Merca_TransMaritimo_PermSCT", txt_CatPort_12_Merca_TransMaritimo_PermSCT);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NumPermisoSCT", txt_CatPort_12_Merca_TransMaritimo_NumPermisoSCT);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NombreAseg", txt_CatPort_12_Merca_TransMaritimo_NombreAseg);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NumPolizaSeguro", txt_CatPort_12_Merca_TransMaritimo_NumPolizaSeguro);
		map.put("txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion", txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion);
		map.put("txt_CatPort_12_Merca_TransMaritimo_Matricula", txt_CatPort_12_Merca_TransMaritimo_Matricula);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NumeroOMI", txt_CatPort_12_Merca_TransMaritimo_NumeroOMI);
		map.put("txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion", txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NombreEmbarc", txt_CatPort_12_Merca_TransMaritimo_NombreEmbarc);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc", txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc);
		map.put("txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto", txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto);
		map.put("txt_CatPort_12_Merca_TransMaritimo_TipoCarga", txt_CatPort_12_Merca_TransMaritimo_TipoCarga);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NumCertITC", txt_CatPort_12_Merca_TransMaritimo_NumCertITC);
		map.put("txt_CatPort_12_Merca_TransMaritimo_Eslora", txt_CatPort_12_Merca_TransMaritimo_Eslora);
		map.put("txt_CatPort_12_Merca_TransMaritimo_Manga", txt_CatPort_12_Merca_TransMaritimo_Manga);
		map.put("txt_CatPort_12_Merca_TransMaritimo_Calado", txt_CatPort_12_Merca_TransMaritimo_Calado);
		map.put("txt_CatPort_12_Merca_TransMaritimo_LineaNaviera", txt_CatPort_12_Merca_TransMaritimo_LineaNaviera);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero", txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero", txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NumViaje", txt_CatPort_12_Merca_TransMaritimo_NumViaje);
		map.put("txt_CatPort_12_Merca_TransMaritimo_NumConocEmbarc", txt_CatPort_12_Merca_TransMaritimo_NumConocEmbarc);
		map.put("txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor", txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor);
		map.put("lb_CatPort_13_Merca_TransMaritimo_Cont_TipoContenedor", lb_CatPort_13_Merca_TransMaritimo_Cont_TipoContenedor);
		map.put("txt_CatPort_13_Merca_TransMaritimo_Cont_NumPrecinto", txt_CatPort_13_Merca_TransMaritimo_Cont_NumPrecinto);
		map.put("txt_CatPort_14_Merca_TransAereo_PermSCT", txt_CatPort_14_Merca_TransAereo_PermSCT);
		map.put("txt_CatPort_14_Merca_TransAereo_NumPermisoSCT", txt_CatPort_14_Merca_TransAereo_NumPermisoSCT);
		map.put("txt_CatPort_14_Merca_TransAereo_MatriculaAeronave", txt_CatPort_14_Merca_TransAereo_MatriculaAeronave);
		map.put("txt_CatPort_14_Merca_TransAereo_NombreAseg", txt_CatPort_14_Merca_TransAereo_NombreAseg);
		map.put("txt_CatPort_14_Merca_TransAereo_NumPolizaSeguro", txt_CatPort_14_Merca_TransAereo_NumPolizaSeguro);
		map.put("txt_CatPort_14_Merca_TransAereo_NumeroGuia", txt_CatPort_14_Merca_TransAereo_NumeroGuia);
		map.put("txt_CatPort_14_Merca_TransAereo_LugarContrato", txt_CatPort_14_Merca_TransAereo_LugarContrato);
		map.put("txt_CatPort_14_Merca_TransAereo_RFCTransportista", txt_CatPort_14_Merca_TransAereo_RFCTransportista);
		map.put("txt_CatPort_14_Merca_TransAereo_CodigoTransportista", txt_CatPort_14_Merca_TransAereo_CodigoTransportista);
		map.put("txt_CatPort_14_Merca_TransAereo_NumRegIdTribTranspor", txt_CatPort_14_Merca_TransAereo_NumRegIdTribTranspor);
		map.put("txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalTranspor", txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalTranspor);
		map.put("txt_CatPort_14_Merca_TransAereo_NombreTransportista", txt_CatPort_14_Merca_TransAereo_NombreTransportista);
		map.put("txt_CatPort_14_Merca_TransAereo_RFCEmbarcador", txt_CatPort_14_Merca_TransAereo_RFCEmbarcador);
		map.put("txt_CatPort_14_Merca_TransAereo_NumRegIdTribEmbarc", txt_CatPort_14_Merca_TransAereo_NumRegIdTribEmbarc);
		map.put("txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalEmbarc", txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalEmbarc);
		map.put("txt_CatPort_14_Merca_TransAereo_NombreEmbarcador", txt_CatPort_14_Merca_TransAereo_NombreEmbarcador);
		map.put("lb_CatPort_15_Merca_TransFerrov_TipoDeServicio", lb_CatPort_15_Merca_TransFerrov_TipoDeServicio);
		map.put("txt_CatPort_15_Merca_TransFerrov_NombreAseg", txt_CatPort_15_Merca_TransFerrov_NombreAseg);
		map.put("txt_CatPort_15_Merca_TransFerrov_NumPolizaSeguro", txt_CatPort_15_Merca_TransFerrov_NumPolizaSeguro);
		map.put("txt_CatPort_15_Merca_TransFerrov_Concesionario", txt_CatPort_15_Merca_TransFerrov_Concesionario);
		map.put("txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso", txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso);
		map.put("txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado", txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado);
		map.put("lb_CatPort_17_Merca_TransFerrov_Carro_TipoCarro", lb_CatPort_17_Merca_TransFerrov_Carro_TipoCarro);
		map.put("txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro", txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro);
		map.put("txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro", txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro);
		map.put("txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro", txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro);
		map.put("lb_CatPort_18_Merca_TransFerrov_Carro_Cont_TipoContenedor", lb_CatPort_18_Merca_TransFerrov_Carro_Cont_TipoContenedor);
		map.put("txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio", txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio);
		map.put("txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia", txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia);
		map.put("lb_CatPort_19_FigTrans_CveTransporte", lb_CatPort_19_FigTrans_CveTransporte);
		map.put("txt_CatPort_20_FigTrans_Oper_RFCOperador", txt_CatPort_20_FigTrans_Oper_RFCOperador);
		map.put("txt_CatPort_20_FigTrans_Oper_NumLicencia", txt_CatPort_20_FigTrans_Oper_NumLicencia);
		map.put("txt_CatPort_20_FigTrans_Oper_NombreOperador", txt_CatPort_20_FigTrans_Oper_NombreOperador);
		map.put("txt_CatPort_20_FigTrans_Oper_NumRegIdTribOperador", txt_CatPort_20_FigTrans_Oper_NumRegIdTribOperador);
		map.put("txt_CatPort_20_FigTrans_Oper_ResidenciaFiscalOperador", txt_CatPort_20_FigTrans_Oper_ResidenciaFiscalOperador);
		map.put("txt_CatPort_21_FigTrans_Oper_Domi_Calle", txt_CatPort_21_FigTrans_Oper_Domi_Calle);
		map.put("txt_CatPort_21_FigTrans_Oper_Domi_NumeroExterior", txt_CatPort_21_FigTrans_Oper_Domi_NumeroExterior);
		map.put("txt_CatPort_21_FigTrans_Oper_Domi_NumeroInterior", txt_CatPort_21_FigTrans_Oper_Domi_NumeroInterior);
		map.put("txt_CatPort_21_FigTrans_Oper_Domi_Colonia", txt_CatPort_21_FigTrans_Oper_Domi_Colonia);
		map.put("txt_CatPort_21_FigTrans_Oper_Domi_Localidad", txt_CatPort_21_FigTrans_Oper_Domi_Localidad);
		map.put("txt_CatPort_21_FigTrans_Oper_Domi_Referencia", txt_CatPort_21_FigTrans_Oper_Domi_Referencia);
		map.put("txt_CatPort_21_FigTrans_Oper_Domi_Municipio", txt_CatPort_21_FigTrans_Oper_Domi_Municipio);
		map.put("txt_CatPort_21_FigTrans_Oper_Domi_Estado", txt_CatPort_21_FigTrans_Oper_Domi_Estado);
		map.put("txt_CatPort_21_FigTrans_Oper_Domi_Pais", txt_CatPort_21_FigTrans_Oper_Domi_Pais);
		map.put("txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal", txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal);
		map.put("txt_CatPort_22_FigTrans_Prop_RFCPropietario", txt_CatPort_22_FigTrans_Prop_RFCPropietario);
		map.put("txt_CatPort_22_FigTrans_Prop_NombrePropietario", txt_CatPort_22_FigTrans_Prop_NombrePropietario);
		map.put("txt_CatPort_22_FigTrans_Prop_NumRegIdTribPropietario", txt_CatPort_22_FigTrans_Prop_NumRegIdTribPropietario);
		map.put("txt_CatPort_22_FigTrans_Prop_ResidenciaFiscalPropietario", txt_CatPort_22_FigTrans_Prop_ResidenciaFiscalPropietario);
		map.put("txt_CatPort_23_FigTrans_Prop_Domi_Calle", txt_CatPort_23_FigTrans_Prop_Domi_Calle);
		map.put("txt_CatPort_23_FigTrans_Prop_Domi_NumeroExterior", txt_CatPort_23_FigTrans_Prop_Domi_NumeroExterior);
		map.put("txt_CatPort_23_FigTrans_Prop_Domi_NumeroInterior", txt_CatPort_23_FigTrans_Prop_Domi_NumeroInterior);
		map.put("txt_CatPort_23_FigTrans_Prop_Domi_Colonia", txt_CatPort_23_FigTrans_Prop_Domi_Colonia);
		map.put("txt_CatPort_23_FigTrans_Prop_Domi_Localidad", txt_CatPort_23_FigTrans_Prop_Domi_Localidad);
		map.put("txt_CatPort_23_FigTrans_Prop_Domi_Referencia", txt_CatPort_23_FigTrans_Prop_Domi_Referencia);
		map.put("txt_CatPort_23_FigTrans_Prop_Domi_Municipio", txt_CatPort_23_FigTrans_Prop_Domi_Municipio);
		map.put("txt_CatPort_23_FigTrans_Prop_Domi_Estado", txt_CatPort_23_FigTrans_Prop_Domi_Estado);
		map.put("txt_CatPort_23_FigTrans_Prop_Domi_Pais", txt_CatPort_23_FigTrans_Prop_Domi_Pais);
		map.put("txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal", txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal);
		map.put("txt_CatPort_24_FigTrans_Arr_RFCArrendatario", txt_CatPort_24_FigTrans_Arr_RFCArrendatario);
		map.put("txt_CatPort_24_FigTrans_Arr_NombreArrendatario", txt_CatPort_24_FigTrans_Arr_NombreArrendatario);
		map.put("txt_CatPort_24_FigTrans_Arr_NumRegIdTribArrendatario", txt_CatPort_24_FigTrans_Arr_NumRegIdTribArrendatario);
		map.put("txt_CatPort_24_FigTrans_Arr_ResidenciaFiscalArrendatario", txt_CatPort_24_FigTrans_Arr_ResidenciaFiscalArrendatario);
		map.put("txt_CatPort_25_FigTrans_Arr_Domi_Calle", txt_CatPort_25_FigTrans_Arr_Domi_Calle);
		map.put("txt_CatPort_25_FigTrans_Arr_Domi__NumeroExterior", txt_CatPort_25_FigTrans_Arr_Domi__NumeroExterior);
		map.put("txt_CatPort_25_FigTrans_Arr_Domi__NumeroInterior", txt_CatPort_25_FigTrans_Arr_Domi__NumeroInterior);
		map.put("txt_CatPort_25_FigTrans_Arr_Domi__Colonia", txt_CatPort_25_FigTrans_Arr_Domi__Colonia);
		map.put("txt_CatPort_25_FigTrans_Arr_Domi__Localidad", txt_CatPort_25_FigTrans_Arr_Domi__Localidad);
		map.put("txt_CatPort_25_FigTrans_Arr_Domi__Referencia", txt_CatPort_25_FigTrans_Arr_Domi__Referencia);
		map.put("txt_CatPort_25_FigTrans_Arr_Domi__Municipio", txt_CatPort_25_FigTrans_Arr_Domi__Municipio);
		map.put("txt_CatPort_25_FigTrans_Arr_Domi__Estado", txt_CatPort_25_FigTrans_Arr_Domi__Estado);
		map.put("txt_CatPort_25_FigTrans_Arr_Domi__Pais", txt_CatPort_25_FigTrans_Arr_Domi__Pais);
		map.put("txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal", txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal);
		map.put("txt_CatPort_26_FigTrans_Noti_RFCNotificado", txt_CatPort_26_FigTrans_Noti_RFCNotificado);
		map.put("txt_CatPort_26_FigTrans_Noti_NombreNotificado", txt_CatPort_26_FigTrans_Noti_NombreNotificado);
		map.put("txt_CatPort_26_FigTrans_Noti_NumRegIdTribNotificado", txt_CatPort_26_FigTrans_Noti_NumRegIdTribNotificado);
		map.put("txt_CatPort_26_FigTrans_Noti_ResidenciaFiscalNotificado", txt_CatPort_26_FigTrans_Noti_ResidenciaFiscalNotificado);
		map.put("txt_CatPort_27_FigTrans_Noti_Domi_Calle", txt_CatPort_27_FigTrans_Noti_Domi_Calle);
		map.put("txt_CatPort_27_FigTrans_Noti_Domi__NumeroExterior", txt_CatPort_27_FigTrans_Noti_Domi__NumeroExterior);
		map.put("txt_CatPort_27_FigTrans_Noti_Domi__NumeroInterior", txt_CatPort_27_FigTrans_Noti_Domi__NumeroInterior);
		map.put("txt_CatPort_27_FigTrans_Noti_Domi__Colonia", txt_CatPort_27_FigTrans_Noti_Domi__Colonia);
		map.put("txt_CatPort_27_FigTrans_Noti_Domi__Localidad", txt_CatPort_27_FigTrans_Noti_Domi__Localidad);
		map.put("txt_CatPort_27_FigTrans_Noti_Domi__Referencia", txt_CatPort_27_FigTrans_Noti_Domi__Referencia);
		map.put("txt_CatPort_27_FigTrans_Noti_Domi__Municipio", txt_CatPort_27_FigTrans_Noti_Domi__Municipio);
		map.put("txt_CatPort_27_FigTrans_Noti_Domi__Estado", txt_CatPort_27_FigTrans_Noti_Domi__Estado);
		map.put("txt_CatPort_27_FigTrans_Noti_Domi__Pais", txt_CatPort_27_FigTrans_Noti_Domi__Pais);
		map.put("txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal", txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/cartaporte/cartaporteList.zul", null, map);
					
					
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	
	
	
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_ClaveProdSTCC(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_06_Merca_ClaveSTCC", txt_CatPort_06_Merca_ClaveSTCC);
		map.put("txt_CatPort_06_Merca_Descripcion", txt_CatPort_06_Merca_Descripcion);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/claveprodSTCC/claveprodSTCCList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_ClaveUnidad(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_06_Merca_ClaveUnidad", txt_CatPort_06_Merca_ClaveUnidad);
		map.put("ClaveUnidad", 1);
//		map.put("txt_CatPort_06_Merca_Unidad", txt_CatPort_06_Merca_Unidad);
		
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/claveunidad/claveunidadList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_ClaveMaterial(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_06_Merca_CveMaterialPeligroso", txt_CatPort_06_Merca_CveMaterialPeligroso);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/clavematerialpeligroso/clavematerialpeligrosoList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_ClaveEmbalaje(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_06_Merca_Embalaje", txt_CatPort_06_Merca_Embalaje);
		map.put("txt_CatPort_06_Merca_DescripEmbalaje", txt_CatPort_06_Merca_DescripEmbalaje);
		
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/tipoembalaje/tipoembalajeList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_09_PermSCT(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_09_Merca_AutoTransF_PermSCT", txt_CatPort_09_Merca_AutoTransF_PermSCT);
		map.put("PermSCT", 1);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/tipopermiso/tipopermisoList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_12_PermSCT(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_12_Merca_TransMaritimo_PermSCT", txt_CatPort_12_Merca_TransMaritimo_PermSCT);
		map.put("PermSCT", 2);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/tipopermiso/tipopermisoList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	public void onClick$btn_TipoEmbarcacion(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion", txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion);
//		map.put("PermSCT", 2);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/configmaritima/configmaritimaList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	public void onClick$btn_TipoCarga(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_12_Merca_TransMaritimo_TipoCarga", txt_CatPort_12_Merca_TransMaritimo_TipoCarga);
//		map.put("PermSCT", 2);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/tipocarga/tipocargaList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	
	public void onClick$btn_NumAutoNav(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero", txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero);
//		map.put("PermSCT", 2);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/numautorizacionnaviero/numautorizacionnavieroList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	
	
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_14_PermSCT(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_14_Merca_TransAereo_PermSCT", txt_CatPort_14_Merca_TransAereo_PermSCT);
		map.put("PermSCT", 3);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/tipopermiso/tipopermisoList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_ConfigVehicular(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular", txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular);
//		map.put("PermSCT", 3);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/configautotrans/configautotransList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_SubTipoRem(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_11_Merca_AutoTransF_Remolque_SubTipoRem", txt_CatPort_11_Merca_AutoTransF_Remolque_SubTipoRem);
//		map.put("PermSCT", 3);
//		map.put("Operador_Destinatario", 2);
		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/subtiporem/subtiporemList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_CodigoTransportista(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_14_Merca_TransAereo_CodigoTransportista", txt_CatPort_14_Merca_TransAereo_CodigoTransportista);

		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/codtranspaereo/codtranspaereoList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btn_TipoDerechoDePaso(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		System.out.println("Si entro para armar de parametros Destino");
		//Se inicializa por default la addenda
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso", txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso);

		
		try {
					Executions.createComponents("/WEB-INF/pages/catalogos/derechosdepaso/derechosdepasoList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
	}
	
	
	/**
	 * when the "btn_Proveedor" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnAgregaProdLista(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
		
		if (cb_Iva.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona IVA", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
			
		}
		
		if (cb_tasa.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona TASA", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
			
		}
		
		if (cb_Moneda.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona Moneda", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
			
		}
		
		System.out.println("Si entro para armar de parametros");
		
		if (logger.isDebugEnabled()) {
			if (event != null)
				logger.debug("--> " + event.toString());
		}
//		TbUsuario anuser = new TbUsuario();
//		listBoxDetalleFactura
		
		String alturaStr = listBoxDetalleFactura.getHeight();
		int altura = 0;
		if (alturaStr.indexOf("px") > -1) {
			alturaStr =  alturaStr.substring(0,alturaStr.indexOf("px"));
			altura = new Integer (alturaStr) + 30;
		}
		
		listBoxDetalleFactura.setHeight(altura + "px");
		
		TasaIVa = cb_tasa.getSelectedItem().getLabel();
		
//		anuser = UserServ.get

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("detalleVenta", listBoxDetalleFactura);
//		map.put("cliente", getCliente());
		map.put("tb_IvaVenta", tb_IvaFactura);
		map.put("tb_RetIvaVenta", tb_RetIvaFactura);
		map.put("tb_RetISRVenta", tb_RetISRFactura);
		map.put("tb_SubtotalVenta", tb_SubtotalFactura);
		map.put("tb_TotalVenta", tb_TotalFactura);
		map.put("tb_TotalLetraVenta", tb_TotalLetraFactura);
		map.put("TasaIVa", TasaIVa);
		map.put("ch_Retenciones", ch_Retenciones);
//		map.put("cb_Honorario", checkbox_Hono);
		map.put("detalle", detalle);
//		map.put("btnBuscaCte", btnBuscaCte);
//		map.put("btnNuevocte", btnNuevocte);
		map.put("contadorDetalles", contadorDetalles);
//		map.put("tipoDoc", tipoDoc);
//		map.put("bitacoras", bitacoras);
//		

		try {
					Executions.createComponents("/WEB-INF/pages/producto/productoList.zul", null, map);
		} catch (Exception e) {
			logger.error("onOpenWindow:: error opening window / "
					+ e.getMessage());

			// Show a error box
			if (logger.isDebugEnabled()) {
				String msg = e.getMessage();
				String title = Labels.getLabel("message_Error");
				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
						"ERROR", true);
			}

		}
		
//		contieneAddenda();
//		contieneDetallista();
	}
	
	public void onClick$btnAgregaProd(Event event) throws InterruptedException {
			
		if (cb_Iva.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona el Impuesto", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
			
		}
		
		if (cb_tasa.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona TASA", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
			
		}
		
		if (cb_Moneda.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona Moneda", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
			
		}
		
		String alturaStr = listBoxDetalleFactura.getHeight();
		int altura = 0;
		if (alturaStr.indexOf("px") > -1) {
			alturaStr =  alturaStr.substring(0,alturaStr.indexOf("px"));
			altura = new Integer (alturaStr) + 30;
		}
		
		listBoxDetalleFactura.setHeight(altura + "px");
		
//		public String llenaDetalle33 () throws Exception{
//			NumberFormat format = NumberFormat.getInstance();
//			format.setMaximumFractionDigits(2);
//			format.setMinimumFractionDigits(2);
			List <Listitem> objList = new ArrayList<Listitem>();
			List <Listitem> items = new ArrayList<Listitem>();
			List <Listcell> cells = new ArrayList<Listcell>();
			List <Object> objects = new ArrayList<Object>();
//			Double ivaTienda = sBean.getIvaTienda();
			SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyyHHmmss");
			String referencia = formato.format(new Date());
			String mensaje = "";
			Listitem itemB = null;
//			VentaService ventaservice = new VentaService();
			
//			CfdUtil cfdUtil = new CfdUtil();
			try {

//				int idprod = producto.getIdProductoServicio();
				int idprod = idproducto++;
				
				//Se obtiene los listitems del listbox
//				objList = listBoxAlmacenPrecioProducto.getChildren();
//				for (Object o : objList){
//					if (o instanceof Listitem) {
//						items.add((Listitem)o);
//					}
//				}
				
				
//				qw
//				HashMap<String, Object> map = new HashMap<String, Object>();
				
				
				//MAMP se obtiene el impuesto y la tasa del productos
//				List<TbProductoImpuesto> impuestosProducto = getVentaService().getImpuestosProducto(idprod);
//				TbProductoImpuesto impuesto =  null;
//				if(impuestosProducto.size() > 0) {
//					impuesto = impuestosProducto.get(0);
//				}
				
				
				
//				String alturaStr = listBoxDetalleVenta.getHeight();
//				int altura = 0;
//				if (alturaStr.indexOf("px") > -1) {
//					alturaStr =  alturaStr.substring(0,alturaStr.indexOf("px"));
//					altura = new Integer (alturaStr) + 30;
//				}
//				
//				listBoxDetalleVenta.setHeight(altura + "px");
//				
				
//				objList = listBoxDetalleVenta.getChildren();
//				for (Object o : objList){
//					if (o instanceof Listitem) {
//						itemB = (Listitem)o;
//						if(itemB.getId().equals(idprod+"")) {
//							mensaje = "El producto ya fue ingresado a la venta";
//							return mensaje;
//						}
//						boolean conIva=false;
//						cells = itemB.getChildren();
//						//Se recorren los cells
//						for (Listcell cell : cells){
//
//							if(cell.getId().indexOf("iva_") > -1){
//								conIva=true;
//							}
//							
////							if(cell.getId().indexOf("porcenIva_") > -1){
////								int ivapart;
////								if(impuesto.getTbImpuestosSat().getTasaocuota() != null ) {
////									Double d = new Double(impuesto.getTbImpuestosSat().getTasaocuota());
////								 	d = d * 100;
////									ivapart= new Integer(d.intValue());
////								}
////									
////								else
////									ivapart=ivaTienda.intValue();
////								
////								if(conIva && ivapart != new Integer(cell.getLabel())){
////									mensaje = "No se pueden generar facturas mixtas en IVA";
////									return mensaje;
////								}
////							}
//							
//						}
//						}
//				}
				//TODO MAMP
//				if(linea.getNuconiva()==0){
//					checkbox_Honorario.setDisabled(false);
//					//tb_RetISRVenta.setValue("");
//				}else if(linea.getNuiva()==0.0){
//					checkbox_Honorario.setDisabled(true);
//					tb_RetISRVenta.setValue("");
//					tb_RetIvaVenta.setValue("");
//					
//				}
				
				Double cantidadTotal = 0.0;
				Double cantidadAlmacen = 0.0;
//				int idAlmacen = 0;
				int existenciaAlmacen = 0;
//				int idAlmacenTienda = 0;
//				int idAlmacenCD = 0;
//				Double cantidadAlmacenTienda = 0.0;
//				Double cantidadAlmacenCD= 0.0;
//				Double cantidadAlmacenOtros = 0.0;
//				int existenciaAlmacenTienda = 0;
//				int existenciaAlmacenCD = 0;
				Double excedente = 0.0;
				Double precioTotal = 0.0;
				Double ivaTotal = 0.0;
				Double ivaUnidad = 0.0;
//				String almacenCantidad = "";
//				int noAlmacen = 0;
//				for (Listitem listItem: items){ 
//					cantidadAlmacen = 0.0;
//					noAlmacen = noAlmacen + 1;
//					//Se obtienen los listcell del listitem
//					cells = listItem.getChildren();
//					//Se recorren los cells
//					for (Listcell cell : cells){	
//						//Se identifica el almacen
//						
//						if (cell.getId().indexOf("IdAlmacen") > -1) {
//							//Se obtiene los objetos del cell			
//							idAlmacen = new Integer(cell.getLabel());
//							//Si es el primer almacen entonces es el de la tienda de la venta
//							if (noAlmacen == 1)
//								idAlmacenTienda = new Integer(cell.getLabel());
//							else if (noAlmacen == 2)
//								idAlmacenCD = new Integer(cell.getLabel());
//						}else
//						
//						//Se verifica la existencia en el almacen
//						if (cell.getId().indexOf("existencia") > -1) {
//							//Se obtiene los objetos del cell			
//							existenciaAlmacen = new Integer(cell.getLabel());
//							//Si es el primer almacen entonces es el de la tienda de la venta
//							if (noAlmacen == 1)
//								existenciaAlmacenTienda = new Integer(cell.getLabel());
//							else if (noAlmacen == 2)
//								existenciaAlmacenCD = new Integer(cell.getLabel());
	//
//								
//						} else
//						
//						if (cell.getId().indexOf("cantidad") > -1) {
//							//Se obtiene los objetos del cell			
//							objects = cell.getChildren();
//							for (Object obj : objects){
//								if (obj instanceof Doublebox) {
//									Doublebox a  = (Doublebox) obj;
//									if(a.getValue() != null && !a.getValue().equals("")) {
//										cantidadAlmacen = a.getValue();
//										//Si es el primer almacen entonces es el de la tienda de la venta
//										if (noAlmacen == 1)
//											cantidadAlmacenTienda = a.getValue();
//										else if (noAlmacen == 2)
//											cantidadAlmacenCD = a.getValue();
//										else 
//											cantidadAlmacenOtros = a.getValue();
//										
//										cantidadTotal = cantidadTotal + a.getValue();	
//									}
//								}
//							}
//						}
//						
//					}
//					
//					
//					if (cantidadAlmacen > existenciaAlmacen)
//						excedente = cantidadAlmacen - existenciaAlmacen ;
//					
//					//Se llena cadena de la siguiente forma: idAlmacen,cantidad,exedentes;
//					if (cantidadAlmacen > 0) {
//						almacenCantidad = almacenCantidad + idAlmacen + "," + cantidadAlmacen + "," + excedente + ";";
//					}
//					
//				}
				
//				if (cantidadAlmacenCD > 0 && cantidadAlmacenTienda<existenciaAlmacenTienda){
//					mensaje = "No se debe de extraer producto del CD hasta que se termine de la tienda";
//					return mensaje;
//					
//				}
//				if (cantidadAlmacenOtros > 0 && cantidadAlmacenTienda<existenciaAlmacenTienda) {
//					mensaje = "No se debe de extraer producto de otros almacenes hasta que se termine el de la tienda";
//					return mensaje;
//				}
//				
//				if (cantidadAlmacenOtros > 0 && cantidadAlmacenTienda>=existenciaAlmacenTienda && (cantidadAlmacenCD<existenciaAlmacenCD)) {
//					mensaje = "No se debe de extraer producto de otros almacenes hasta que se termine el de el CD";
//					return mensaje;
//				}
				
				//si no se ponen cantidades se pone por defaul 1 y se extrae del almacen de la tienda
//				if (cantidadTotal == 0) {
//					excedente = 0.0;
//					cantidadTotal = 1.0;
//					if (cantidadTotal > existenciaAlmacenTienda)
//						excedente = cantidadTotal;
//					almacenCantidad = idAlmacenTienda + "," + cantidadTotal + "," + excedente;
//				}
//				
//				
//				if (almacenCantidad.endsWith(";"))
//					almacenCantidad = almacenCantidad.substring(0,almacenCantidad.length()-1);
				
				

				//			TbProducto producto = (TbProducto) getFacturaService().getBusinessObjectPorId(producto.getNukidProducto(), TbProducto.class, "nukidProducto");

				//Se usa para poner el id de los elementos y que no se repita
//				contadorDetalles = new Intbox();
//				int numero = contadorDetalles.getValue();
//				int numero = contadorDetalles.getChildren().size();
				int numero = contadorDetalles.getValue();
				int unidad= 0;

				Listitem item = new Listitem();
				item.setId(idprod+"");
//				item.setId(idproducto+"");
				item.setParent(listBoxDetalleFactura);

				//Se pone en blanco ya que el la primera columna es donde esta el check
				Listcell lc;
				lc = new Listcell("");
				lc.setParent(item);

				//Se pone la columna donde va el intbox de la cantidad
				lc = new Listcell();
				lc.setId("cantidad_" +numero);
				Doublebox ib_cantidad = new Doublebox();
				
				ib_cantidad.setWidth("45px");
				ib_cantidad.setMaxlength(7);
				ib_cantidad.setId("ibcantidad_" + numero);
				ib_cantidad.setValue(cantidadTotal);
				ib_cantidad.setStyle("text-align:right");
				ComponentsCtrl.applyForward(ib_cantidad, "onFocus=onFocusUnitario");
				ComponentsCtrl.applyForward(ib_cantidad, "onBlur=onBlurUnitario");
//				if (tipoDocequals("COTIZACION" )){
//					ib_cantidad.setReadonly(false);
					ComponentsCtrl.applyForward(ib_cantidad, "onFocus=onFocusCantidad");
					ComponentsCtrl.applyForward(ib_cantidad, "onBlur=onBlurCantidad");
//					ib_cantidad.setConstraint("NO NEGATIVE");
//				} else {
					
					ib_cantidad.setReadonly(false);
//				}
				lc.appendChild(ib_cantidad);
				lc.setParent(item);

				//Se pone valor a la columna clave
//				lc = new Listcell(producto.getProductoServicio());
				lc = new Listcell("");
				lc.setId("clave_" +numero);
				
				Combobox ib_clave = new Combobox();
				
				ib_clave.setWidth("75px");
//				ib_unidad.setModel(new ListModelList(ventaServ.getUnidad()));
//				ib_unidad.setItemRenderer(new ComboFactoryModelItemRenderer());
//				ListModel dictModel= new SimpleListModel(ventaServ.getClaveProd());
//				ib_clave.setModel(dictModel);
//				ib_clave.setAutodrop(true);
//				ib_clave.setAutocomplete(true);
				
//				ib_clave.setMold("select");
				
				ib_clave.setStyle("text-align:right;");
				ComponentsCtrl.applyForward(ib_clave, "onFocus=onFocusUnitario");
				ComponentsCtrl.applyForward(ib_clave, "onBlur=onBlurUnitario");
				lc.appendChild(ib_clave);
				
				lc.setParent(item);

				//Se pone valor a la columna descripcion
//				lc = new Listcell(producto.getDescripcion());
				lc = new Listcell("");
				lc.setId("descripcion_" +numero);
				Textbox tb_Descripcion = new Textbox();
				
				tb_Descripcion.setWidth("200px");
				//ib_Producto.setMaxlength(4);
				//ib_Producto.setId("ibunitario_" + numero);
				tb_Descripcion.setValue("");
				tb_Descripcion.setStyle("text-align:right");
				ComponentsCtrl.applyForward(tb_Descripcion, "onFocus=onFocusUnitario");
				ComponentsCtrl.applyForward(tb_Descripcion, "onBlur=onBlurUnitario");
//				tb_Descripcion.setConstraint("NO NEGATIVE");
				/*if (tipoDoc.equals("COTIZACION" )){
					ib_Producto.setReadonly(false);
					ComponentsCtrl.applyForward(ib_cantidad, "onFocus=onFocusPrecio");
					ComponentsCtrl.applyForward(ib_cantidad, "onBlur=onBlurPrecio");
					ib_Producto.setConstraint("NO NEGATIVE");
				} else {
					
					ib_Producto.setReadonly(true);
				}*/
				lc.appendChild(tb_Descripcion);
				lc.setParent(item);

				//Se pone valor a la columna descripcion
//				lc = new Listcell(producto.getDescripcion());
				lc = new Listcell("");
				lc.setId("justificacion_" +numero);
				Textbox tb_justificacion = new Textbox();
				
				tb_justificacion.setWidth("200px");
				//ib_Producto.setMaxlength(4);
				//ib_Producto.setId("ibunitario_" + numero);
				tb_justificacion.setValue("");
				tb_justificacion.setStyle("text-align:right");
				ComponentsCtrl.applyForward(tb_justificacion, "onFocus=onFocusUnitario");
				ComponentsCtrl.applyForward(tb_justificacion, "onBlur=onBlurUnitario");
//				tb_Descripcion.setConstraint("NO NEGATIVE");
				/*if (tipoDoc.equals("COTIZACION" )){
					ib_Producto.setReadonly(false);
					ComponentsCtrl.applyForward(ib_cantidad, "onFocus=onFocusPrecio");
					ComponentsCtrl.applyForward(ib_cantidad, "onBlur=onBlurPrecio");
					ib_Producto.setConstraint("NO NEGATIVE");
				} else {
					
					ib_Producto.setReadonly(true);
				}*/
				lc.appendChild(tb_justificacion);
				lc.setParent(item);
				//Se pone valor a la columna unidad de salida
//				lc = new Listcell(producto.getChunidadSalida());
//				lc.setId("unidad_" +numero);
//				lc.setParent(item);
				
				/**
				 * inicio precio producto
				 */

				// se pone el precio unitario
				lc = new Listcell();
				lc.setId("unidad_"+numero);
				//lc.setParent(item);
				Listbox ib_unidad = new Listbox();
				
//				ib_unidad.setWidth("130px");
//				ib_unidad.setModel(new ListModelList(ventaServ.getUnidad()));
//				ib_unidad.setItemRenderer(new ComboFactoryModelItemRenderer());
//				ib_unidad.setMold("select");
//				ib_unidad.setStyle("text-align:right");
//				ComponentsCtrl.applyForward(ib_unidad, "onFocus=onFocusUnitario");
//				ComponentsCtrl.applyForward(ib_unidad, "onBlur=onBlurUnitario");
//				lc.appendChild(ib_unidad);
//				lc.setParent(item);
				/**
				 * Fin de Producto
				 */
				
				// se pone el precio unitario
//				lc = new Listcell();
//				lc.setId("unidad_"+numero);
//				//lc.setParent(item);
//				Textbox ib_unitarioProducto = new Textbox();
//				
//				ib_unitarioProducto.setWidth("65px");
//				//ib_Producto.setMaxlength(4);
//				//ib_Producto.setId("ibunitario_" + numero);
//				ib_unitarioProducto.setValue(unidad+"");
//				ib_unitarioProducto.setStyle("text-align:right");
//				ComponentsCtrl.applyForward(ib_unitarioProducto, "onFocus=onFocusUnitario");
//				ComponentsCtrl.applyForward(ib_unitarioProducto, "onBlur=onBlurUnitario");
//				ib_unitarioProducto.setConstraint("NO NEGATIVE");
//				/*if (tipoDoc.equals("COTIZACION" )){
//					ib_Producto.setReadonly(false);
//					ComponentsCtrl.applyForward(ib_cantidad, "onFocus=onFocusPrecio");
//					ComponentsCtrl.applyForward(ib_cantidad, "onBlur=onBlurPrecio");
//					ib_Producto.setConstraint("NO NEGATIVE");
//				} else {
//					
//					ib_Producto.setReadonly(true);
//				}*/
//				lc.appendChild(ib_unitarioProducto);
//				lc.setParent(item);

				String precio ="";
				Double precioNum = 0.0;
				
				Double precioConIva = 0.0;
				//Se obtiene el precio 
//				if (doubleb_OtroPrecioProducto.getValue() !=  null && doubleb_OtroPrecioProducto.getValue()>0)
//					precioNum = doubleb_OtroPrecioProducto.getValue();
//				else {
//					Listitem itemPrecio = lb_PrecioProducto.getSelectedItem();
//					if (itemPrecio == null)
//						itemPrecio = lb_PrecioProducto.getSelectedItem();
//					ListModelList lmlPrecio = (ListModelList) lb_PrecioProducto.getListModel();
//					ComboFactory comboFactoryUsuario = (ComboFactory) lmlPrecio.get(itemPrecio.getIndex());
//					precioNum = new Double(comboFactoryUsuario.getNombre().replace(",", ""));
//				}
				precioConIva =precioNum;
				
				//Se obtiene el precio total con iva
				precioTotal = precioConIva * cantidadTotal;
				
				int por_iva=-1;

				//Se verifica si se le desglosa el iva el producto (que el cliente tenga rfc y que la linea del producto tenga marcado que tiene iva)
				/*//Se calcula el precio de producto sin iva.
				 * if (desglosaIva && linea.getNuconiva() == 1) {
					//Evalua si la linea tiene su propio iva
					if (linea.getNuiva() != null && linea.getNuiva() >=0)
					{
						Double ivaLinea = linea.getNuiva();
						//precioNum = (precioNum * 100.00) / (100.00 + ivaLinea);
						//precioNum = (precioNum * (ivaLinea/100.00)) + precioNum;
						
						// se obtiene el iva unitario
						//ivaUnidad = (precioConIva * ivaLinea) / (100.00 + ivaLinea);
						ivaUnidad = (precioConIva * (ivaLinea/100.00));
						//Se obtiene el iva total
						//ivaTotal =precioTotal- precioNum;
						ivaTotal =ivaUnidad* cantidadTotal;
						precioTotal=precioTotal+ivaTotal;
						por_iva=ivaLinea.intValue();
					} else {
						//Se toma el iva de la tienda
						//precioNum = (precioNum * 100.00) / (100.00 + ivaTienda);
						//precioNum = (precioNum *  (ivaTienda/100.00))+precioNum;
						
						// se obtiene el iva unitario
						//ivaUnidad =  (precioConIva * ivaTienda) / (100.00 + ivaTienda);
						ivaUnidad =  (precioConIva * (ivaTienda/100.00));
						//Se obtiene el iva total
						//ivaTotal = precioTotal-precioNum;
						ivaTotal = ivaUnidad* cantidadTotal;
						precioTotal=precioTotal+ivaTotal;
						por_iva=ivaTienda.intValue();
					}
				} else {
					precioNum = precioNum;
					ivaTotal = 0;
					ivaUnidad =0;
				}*/
				/**
				 * Se calcula el precio del producto con IVA
				 */
//				if (desglosaIva && impuesto.getTbImpuestosSat().getTasaocuota() != null) {
//					//Evalua si la linea tiene su propio iva
//					if (impuesto.getTbImpuestosSat().getTasaocuota()  != null )
//					{
//						Double ivaLinea = new Double (impuesto.getTbImpuestosSat().getTasaocuota()) * 100;
//						precioNum = (precioNum * 100.00) / (100.00 + ivaLinea);
//						//Se obtiene el iva total
//						ivaTotal = precioTotal - precioNum;
//						// se obtiene el iva unitario
//						ivaUnidad = (precioConIva * ivaLinea) / (100.00 + ivaLinea);
//						por_iva=ivaLinea.intValue();
//					} else {
//						//Se toma el iva de la tienda
//						precioNum = (precioNum * 100.00) / (100.00 + ivaTienda);
//						//Se obtiene el iva total
//						ivaTotal = precioTotal - precioNum;
//						// se obtiene el iva unitario
//						ivaUnidad =  (precioConIva * ivaTienda) / (100.00 + ivaTienda);
//						por_iva=ivaTienda.intValue();
//					}
//				} else {
					precioNum = precioNum;
					ivaTotal = 0.0;
					ivaUnidad =0.0;
//				}


				precio = precioNum.toString();

				
				
				
				/**
				 * inicio precio producto
				 */

				// se pone el precio unitario
				lc = new Listcell();
				lc.setId("unitario_"+numero);
				//lc.setParent(item);
				Textbox ib_Producto = new Textbox();
				
				ib_Producto.setWidth("65px");
				//ib_Producto.setMaxlength(4);
				//ib_Producto.setId("ibunitario_" + numero);
				ib_Producto.setValue(getBigDecimalValueRoundUp(new Double(precio)).toString());
				ib_Producto.setStyle("text-align:right");
				ComponentsCtrl.applyForward(ib_Producto, "onFocus=onFocusUnitario");
				ComponentsCtrl.applyForward(ib_Producto, "onBlur=onBlurUnitario");
				ib_Producto.setConstraint("NO NEGATIVE");
				/*if (tipoDoc.equals("COTIZACION" )){
					ib_Producto.setReadonly(false);
					ComponentsCtrl.applyForward(ib_cantidad, "onFocus=onFocusPrecio");
					ComponentsCtrl.applyForward(ib_cantidad, "onBlur=onBlurPrecio");
					ib_Producto.setConstraint("NO NEGATIVE");
				} else {
					
					ib_Producto.setReadonly(true);
				}*/
				lc.appendChild(ib_Producto);
				lc.setParent(item);
				/**
				 * Fin de Producto
				 */

				//Se pone el precio unitario completo
				lc = new Listcell(precio.toString());
				lc.setId("precioUnitarioCompleto_" +numero);
				lc.setParent(item);
				
			
				

				//El importe se pone en blanco ya que se calcula depues
				//lc = new Listcell("");
				//lc.setId("importe_"+numero);
				//lc.setParent(item);
				
				// se pone el importe (se calcula mas adelante)
				lc = new Listcell();
				lc.setId("importe_"+numero);
				lc.setParent(item);
				
				//Se pone el importe completo (se calcula mas adelante)
				
				lc = new Listcell(precioTotal.toString());
				lc.setId("importeCompleto_" +numero);
				lc.setParent(item);
				
				
				/*Textbox ib_Impor = new Textbox();
				
				ib_Impor.setWidth("65px");
				//ib_Impor.setMaxlength(4);
				//ib_Impor.setId("ibimporte_" + numero);
				ib_Impor.setValue("");
				ib_Impor.setStyle("text-align:right");
				
				lc.appendChild(ib_Impor);
				lc.setParent(item);
				*/

				//Se pone el intbox para el llenado de entregados
//				lc = new Listcell();
//				lc.setId("entregado_"+numero);
//				Intbox tb_entregado= new Intbox();
//				tb_entregado.setWidth("45px");
//				tb_entregado.setFocus(true);
//				tb_entregado.setValue(0);
//				tb_entregado.setStyle("text-align:right");
//				tb_entregado.setConstraint(new MenorIgualConstraint(ib_cantidad,1));
//				lc.appendChild(tb_entregado);
//				lc.setParent(item);


				//Se pone el id del producto
//				lc = new Listcell(producto.getIdProductoServicio().toString());
//				lc.setId("id_"+numero);
//				lc.setParent(item);
				
				lc = new Listcell(""+numero);
				lc.setId("id_"+numero);
				lc.setParent(item);

				//Se pone si el producto tiene iva o no 1 Ã³ 0

				//TODO MAMP
				lc = new Listcell("1");
				lc.setId("iva_"+numero);
				lc.setParent(item);

//				//Se pone el id de nivel de precio
//				lc = new Listcell(idNivelPrecio.toString());
//				lc.setId("nivel_"+numero);
//				lc.setParent(item);
	//
//				//Se pone el id del tipo de cliente
//				lc = new Listcell(tb_IdTipoCtePrecioProducto.getValue().toString());
//				lc.setId("tipo_"+numero);
//				lc.setParent(item);
//				
//				//Se agrega cadena para identificar el id de almacen donde se obtiene el producto y su cantidad 
//				lc = new Listcell(almacenCantidad);
//				lc.setId("extraccion_"+numero);
//				lc.setParent(item);
				
				
				lc = new Listcell("");
				lc.setId("referencia_"+numero);
				lc.setParent(item);

				lc = new Listcell(por_iva+"");
				lc.setId("porcenIva_"+numero);
				lc.setParent(item);
				
				lc = new Listcell(getBigDecimalValue(precioConIva.doubleValue()).toString());
				lc.setId("unitarioIva_" +numero);
				lc.setParent(item);
				
				lc = new Listcell(getBigDecimalValue(precioTotal.doubleValue()).toString());
				lc.setId("total_" +numero);
				lc.setParent(item);
				
				lc = new Listcell(getBigDecimalValue(ivaUnidad.doubleValue()).toString());
				lc.setId("IvaUnidad_" +numero);
				lc.setParent(item);
				
				lc = new Listcell(getBigDecimalValue(ivaTotal.doubleValue()).toString());
				lc.setId("IvaTotal_" +numero);
				lc.setParent(item);
				
				//Se pone el iva completo
				lc = new Listcell(ivaTotal.toString());
				lc.setId("ivaTotalCompleto_" +numero);
				lc.setParent(item);
			

				contadorDetalles.setValue(contadorDetalles.getValue() + 1);
//				getVentaService().setIshonorario(checkbox_Honorario.isChecked());
				//TODO MAMP agregar la moneda
				String moneda = "";
//				ventaServ.calcuaTotalesDirect(listBoxDetalleFactura, detalle, false,TasaIVa, tb_IvaFactura, tb_RetIvaFactura, tb_RetISRFactura,tb_SubtotalFactura, tb_TotalFactura, tb_TotalLetraFactura,false, moneda,ch_Retenciones);
//				ventaserv.calcuaTotalesDirect(listBoxDetalleFactura, detalle,
//						true,cb_tasa.getSelectedItem().getLabel(), tb_IvaFactura, tb_RetIvaFactura, tb_RetISRFactura,
//						tb_SubtotalFactura, tb_TotalFactura, tb_TotalLetraFactura,
//						false, moneda,ch_Retenciones);
				
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("detalleVenta", listBoxDetalleFactura);
////				map.put("cliente", getCliente());
//				map.put("tb_IvaVenta", tb_IvaFactura);
//				map.put("tb_RetIvaVenta", tb_RetIvaFactura);
//				map.put("tb_RetISRVenta", tb_RetISRFactura);
//				map.put("tb_SubtotalVenta", tb_SubtotalFactura);
//				map.put("tb_TotalVenta", tb_TotalFactura);
//				map.put("tb_TotalLetraVenta", tb_TotalLetraFactura);
//				map.put("TasaIVa", TasaIVa);
//				map.put("ch_Retenciones", ch_Retenciones);
////				map.put("cb_Honorario", checkbox_Hono);
//				map.put("detalle", detalle);
////				map.put("btnBuscaCte", btnBuscaCte);
////				map.put("btnNuevocte", btnNuevocte);
//				map.put("contadorDetalles", contadorDetalles);
////				map.put("tipoDoc", tipoDoc);
////				map.put("bitacoras", bitacoras);
//				
				
//				//Se apartan las unidades del producto
//				if (tipoDoc.equals("FACTURA") || tipoDoc.equals("REMISION"))
//					getVentaService().apartaUnidades(almacenCantidad, producto.getNukidProducto(),referencia,tipoDoc);

//				listBoxDetalleFactura.setHeight(listBoxDetalleFactura.getHeight()+40+ ".px");
				
//				return mensaje;
//				idproducto++;	
			} catch (Exception e) {
				logger.error(""+e.getMessage());
				e.printStackTrace();
//				throw new Exception (""+ e.getMessage());
			}
			

		}
//	}
	
	public BigDecimal getBigDecimalValue(double value) {
		double aux = (double) Math.round(value * 1000) / 1000;
		BigDecimal subTotal = new BigDecimal(String.valueOf(aux));
		return subTotal.setScale(2, BigDecimal.ROUND_DOWN);
	}
	
	
	public BigDecimal getBigDecimalValueRoundUp(double value) {
		double aux = (double) Math.round(value * 1000) / 1000;
		BigDecimal subTotal = new BigDecimal(String.valueOf(aux));
		return subTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public BigDecimal getBigDecimalValueInteger(double value) {
		double aux = (double) Math.round(value * 1000) / 1000;
		BigDecimal subTotal = new BigDecimal(String.valueOf(aux));
		return subTotal.setScale(0, BigDecimal.ROUND_UP);
	}
	
	
	
//	public void onClick$btn_Autorizador(Event event) throws Exception {
////	public void btn_Autorizador(Event event) throws Exception {
//
//		if (logger.isDebugEnabled()) {
//			if (event != null)
//				logger.debug("--> " + event.toString());
//		}
//		
//		TbUsuario user = new TbUsuario();
//		
//		user = UserServ.getUserByUsername(Usuario);
//		
//		
//		
//HashMap<String, Object> map = new HashMap<String, Object>();
//		
////		tb_Usuarios = usuarioService.getSimpleUserByUsername(Usuario);
//		map.put("txt_Autorizador", txt_Autorizador);
//		map.put("Usuario", user);
//		map.put("IdUsuarioAuto", IdUsuarioAuto);
//		map.put("txt_CorreoAuto", txt_CorreoAuto);
////		map.put("id_user", id_user);
////		map.put("id_pr_rol", id_pr_rol);
////		map.put("id_Corporativo", id_Corporativo);
//		
//				
////		map.put("label_codigoBarras", label_CodBarrasFactura);
////		map.put("tb_codigoBarras", tb_CodigoFactura);
////		map.put("isFactura", "SI");
////		map.put("btnAgregaAddenda", btnAgregaAddenda);
////		
//
//		try {
//					Executions.createComponents("/WEB-INF/pages/usuarioAuto/userAutorizaList.zul", null, map);
//		} catch (Exception e) {
//			logger.error("onOpenWindow:: error opening window / "
//					+ e.getMessage());
//
//			// Show a error box
//			if (logger.isDebugEnabled()) {
//				String msg = e.getMessage();
//				String title = Labels.getLabel("message_Error");
//				MultiLineMessageBox.doSetTemplate();
//				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
//						"ERROR", true);
//			}
//
//		}
//	}
	
//	public void onClick$btn_Autorizador_Area(Event event) throws Exception {
////		public void btn_Autorizador(Event event) throws Exception {
//
//			if (logger.isDebugEnabled()) {
//				if (event != null)
//					logger.debug("--> " + event.toString());
//			}
//			
//			TbUsuario user = new TbUsuario();
//			
//			user = UserServ.getUserByUsername(Usuario);
//			
//			
//			
//	HashMap<String, Object> map = new HashMap<String, Object>();
//			
////			tb_Usuarios = usuarioService.getSimpleUserByUsername(Usuario);
//			map.put("txt_Autorizador_Area", txt_Autorizador_Area);
//			map.put("Usuario", user);
//			map.put("IdUsuarioAutoArea", IdUsuarioAutoArea);
//			map.put("txt_CorreoAutoArea", txt_CorreoAutoArea);
////			map.put("id_user", id_user);
////			map.put("id_pr_rol", id_pr_rol);
////			map.put("id_Corporativo", id_Corporativo);
//			
//					
////			map.put("label_codigoBarras", label_CodBarrasFactura);
////			map.put("tb_codigoBarras", tb_CodigoFactura);
////			map.put("isFactura", "SI");
////			map.put("btnAgregaAddenda", btnAgregaAddenda);
////			
//
//			try {
//						Executions.createComponents("/WEB-INF/pages/usuarioAutoArea/userAutorizaAreaList.zul", null, map);
//			} catch (Exception e) {
//				logger.error("onOpenWindow:: error opening window / "
//						+ e.getMessage());
//
//				// Show a error box
//				if (logger.isDebugEnabled()) {
//					String msg = e.getMessage();
//					String title = Labels.getLabel("message_Error");
//					MultiLineMessageBox.doSetTemplate();
//					MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK,
//							"ERROR", true);
//				}
//
//			}
//		}
	
	
	
	
	/**
	 * Metodo usado para eliminar productos del area de detalle de la factura
	 * 
	 * @param event
	 * @throws Exception
	 */
	
	
	
	public void onClick$Borrar_Concepto(Event event) throws Exception {
		
//		Listitem item = null;
//
//		Object[] a = itemList.getSelectedItems().toArray();
//
//		for (int i = 0; i < a.length; i++) {
//			item = (Listitem) a[i];
//			// Se elimina el producto de la tabla de apartado
////			VentaService.eliminaApartaUnidades(item);
//			itemList.removeChild(item);
//
//		
//		}
		
//		if (itemList.getSelectedCount() > 0) {
//			 for( Object o : itemList.getSelectedItems() ){
//					Listitem li = (Listitem)o;
////					tb sit =(CeComprobantes)li.getAttribute("data");
//					
//					System.out.println("Información de la lista para extraer datos"+ itemList.getSelectedItem().getLabel());
//					
//	    	   }
//		 }
		
		

		
		
		for (Object item : itemList.getSelectedItems()) {
        	//Los tenemos que ir cargando porque los items de las pï¿½ginas que no se hayan visitado
        	//no estarï¿½n cargados
			itemList.renderItem((Listitem) item);
	        String J = "";
	        for (Object cell : ((Listitem) item).getChildren()) {
	        	//Solo mostramos las celdas que estï¿½n visibles
	        	if(((Listcell)cell).getListheader()!=null){
		        	 if(((Listcell)cell).getListheader().isVisible()){
		        		 J += ((Listcell) cell).getLabel() ;
		        	 }
	        	}
	        }
	       
	        System.out.println(J + "\n");
//	        writer.println("03|"+i);
	        
	        String[] LisProduct = J.split("\\|");
	        
	       
//	        sb.append(i + "\n");
//        }
//	        String[] LisProductint = null;
	        int valorcadena = 0;

		for (int i = 0; i < LisProduct.length; i++) {
			
			System.out.println("Aqui el total: "+ LisProduct.length);
			
			valorcadena =  LisProduct.length;
			
			System.out.println(i +" - Aqui los productos----->> " +LisProduct[i]);

	}
		
			
		if (valorcadena == 9 || valorcadena == 8) {

			
			
			if(!LisProduct[7].equals("")){
				Double Concep_Importe = Double.parseDouble(LisProduct[7]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
				tot_concepto = tot_concepto-Concep_Importe;
			}
			if(valorcadena == 9){
				if(!LisProduct[8].equals("")){
					Double Concep_Descuento = Double.parseDouble(LisProduct[8]);
//					CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
					tot_descuento = tot_descuento-Concep_Descuento;
				}
			}
			
			
			
			txt_Factura_Subtotal.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_concepto)).doubleValue()+"");
			txt_Factura_Subtotal.focus();
			
			
			
			if(tot_descuento > 0){
				txt_Factura_Descuento.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_descuento)).doubleValue()+"");
				txt_Factura_Descuento.focus();
			}
			
//			tot_total = tot_concepto+tot_traslado-tot_retenciones -tot_descuento;
			tot_total = tot_concepto-tot_descuento;

			
			txt_Factura_Total.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_total)).doubleValue()+"");
			txt_Factura_Total.focus();
			
		}
		
		if (valorcadena == 10) {

			if(!LisProduct[7].equals("")){
				Double Concep_Importe = Double.parseDouble(LisProduct[7]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
				tot_concepto = tot_concepto-Concep_Importe;
			}
			if(!LisProduct[8].equals("")){
				Double Concep_Descuento = Double.parseDouble(LisProduct[8]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
				tot_descuento = tot_descuento-Concep_Descuento;
			}
			
			txt_Factura_Subtotal.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_concepto)).doubleValue()+"");
			txt_Factura_Subtotal.focus();
			
			
			
			if(tot_descuento > 0){
				txt_Factura_Descuento.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_descuento)).doubleValue()+"");
				txt_Factura_Descuento.focus();
			}
			
//			tot_total = tot_concepto+tot_traslado-tot_retenciones -tot_descuento;
			tot_total = tot_concepto-tot_descuento;

			
			txt_Factura_Total.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_total)).doubleValue()+"");
			txt_Factura_Total.focus();
			
		}
		
		if (valorcadena == 11) {

			if(!LisProduct[7].equals("")){
				Double Concep_Importe = Double.parseDouble(LisProduct[7]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
				tot_concepto = tot_concepto-Concep_Importe;
			}
			if(!LisProduct[8].equals("")){
				Double Concep_Descuento = Double.parseDouble(LisProduct[8]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
				tot_descuento = tot_descuento-Concep_Descuento;
			}
			
			txt_Factura_Subtotal.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_concepto)).doubleValue()+"");
			txt_Factura_Subtotal.focus();
			
			
			
			if(tot_descuento > 0){
				txt_Factura_Descuento.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_descuento)).doubleValue()+"");
				txt_Factura_Descuento.focus();
			}
			
//			tot_total = tot_concepto+tot_traslado-tot_retenciones -tot_descuento;
			tot_total = tot_concepto-tot_descuento;

			
			txt_Factura_Total.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_total)).doubleValue()+"");
			txt_Factura_Total.focus();
						
		}
		
		if (valorcadena == 14) {

			
			if(!LisProduct[7].equals("")){
				Double Concep_Importe = Double.parseDouble(LisProduct[7]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
				tot_concepto = tot_concepto-Concep_Importe;
			}
			if(!LisProduct[8].equals("")){
				Double Concep_Descuento = Double.parseDouble(LisProduct[8]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
				tot_descuento = tot_descuento-Concep_Descuento;
			}
			
	 				
		 				
		 				
		 				if(!LisProduct[13].equals("")){
		 					Double Concep_Imp_Traslado = Double.parseDouble(LisProduct[13]);
//		 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
		 					tot_traslado = tot_traslado-Concep_Imp_Traslado;
						}
		 				
		 				txt_Factura_Subtotal.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_concepto)).doubleValue()+"");
		 				txt_Factura_Subtotal.focus();
		 				
		 				
		 				
		 				if(tot_descuento > 0){
		 					txt_Factura_Descuento.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_descuento)).doubleValue()+"");
		 					txt_Factura_Descuento.focus();
		 				}
		 				
//		 				tot_total = tot_concepto+tot_traslado-tot_retenciones -tot_descuento;
		 				tot_total = tot_concepto-tot_descuento+tot_traslado;

		 				
		 				txt_Factura_Total.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_total)).doubleValue()+"");
		 				txt_Factura_Total.focus();
		 				
		 				
		}
		if (valorcadena == 15) {

			
			if(!LisProduct[7].equals("")){
				Double Concep_Importe = Double.parseDouble(LisProduct[7]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
				tot_concepto = tot_concepto-Concep_Importe;
			}
			if(!LisProduct[8].equals("")){
				Double Concep_Descuento = Double.parseDouble(LisProduct[8]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
				tot_descuento = tot_descuento-Concep_Descuento;
			}
			
			
		 				if(!LisProduct[13].equals("")){
		 					Double Concep_Imp_Traslado = Double.parseDouble(LisProduct[13]);
//		 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
		 					tot_traslado = tot_traslado-Concep_Imp_Traslado;
						}
		 				
		 				txt_Factura_Subtotal.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_concepto)).doubleValue()+"");
		 				txt_Factura_Subtotal.focus();
		 				
		 				
		 				
		 				if(tot_descuento > 0){
		 					txt_Factura_Descuento.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_descuento)).doubleValue()+"");
		 					txt_Factura_Descuento.focus();
		 				}
		 				
//		 				tot_total = tot_concepto+tot_traslado-tot_retenciones -tot_descuento;
//		 				tot_total = tot_concepto-tot_descuento;
		 				tot_total = tot_concepto-tot_descuento+tot_traslado;

		 				
		 				txt_Factura_Total.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_total)).doubleValue()+"");
		 				txt_Factura_Total.focus();
		 				
		}
		
		if (valorcadena == 16) {

			if(!LisProduct[7].equals("")){
				Double Concep_Importe = Double.parseDouble(LisProduct[7]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
				tot_concepto = tot_concepto-Concep_Importe;
			}
			if(!LisProduct[8].equals("")){
				Double Concep_Descuento = Double.parseDouble(LisProduct[8]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
				tot_descuento = tot_descuento-Concep_Descuento;
			}
			
		 				if(!LisProduct[13].equals("")){
		 					Double Concep_Imp_Traslado = Double.parseDouble(LisProduct[13]);
//		 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
		 					tot_traslado = tot_traslado-Concep_Imp_Traslado;
						}

		 				
		 				
		 				txt_Factura_Subtotal.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_concepto)).doubleValue()+"");
		 				txt_Factura_Subtotal.focus();
		 				
		 				
		 				
		 				if(tot_descuento > 0){
		 					txt_Factura_Descuento.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_descuento)).doubleValue()+"");
		 					txt_Factura_Descuento.focus();
		 				}
		 				
//		 				tot_total = tot_concepto+tot_traslado-tot_retenciones -tot_descuento;
//		 				tot_total = tot_concepto-tot_descuento;
		 				tot_total = tot_concepto-tot_descuento+tot_traslado;

		 				
		 				txt_Factura_Total.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_total)).doubleValue()+"");
		 				txt_Factura_Total.focus();

		}
		
		
		
		if (valorcadena == 19) {

			if(!LisProduct[7].equals("")){
				Double Concep_Importe = Double.parseDouble(LisProduct[7]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
				tot_concepto = tot_concepto-Concep_Importe;
			}
			if(!LisProduct[8].equals("")){
				Double Concep_Descuento = Double.parseDouble(LisProduct[8]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
				tot_descuento = tot_descuento-Concep_Descuento;
			}
			
		 				
		 				if(!LisProduct[13].equals("")){
		 					Double Concep_Imp_Traslado = Double.parseDouble(LisProduct[13]);
//		 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
		 					tot_traslado = tot_traslado-Concep_Imp_Traslado;
						}
		 				
		 				
		 				
		 				if(!LisProduct[18].equals("")){
		 					Double Concep_Imp_Retenciones = Double.parseDouble(LisProduct[18]);
//		 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Importe=\""+LisProduct[18]+"\"";
		 					tot_retenciones = tot_retenciones-Concep_Imp_Retenciones;
		 					
						}
		 				
		 				
		 				txt_Factura_Subtotal.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_concepto)).doubleValue()+"");
		 				txt_Factura_Subtotal.focus();
		 				
		 				
		 				
		 				if(tot_descuento > 0){
		 					txt_Factura_Descuento.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_descuento)).doubleValue()+"");
		 					txt_Factura_Descuento.focus();
		 				}
		 				
		 				tot_total = tot_concepto+tot_traslado-tot_retenciones -tot_descuento;
//		 				tot_total = tot_concepto-tot_descuento;
//		 				tot_total = tot_concepto-tot_descuento-tot_traslado;

		 				
		 				txt_Factura_Total.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_total)).doubleValue()+"");
		 				txt_Factura_Total.focus();			
		 				

		}
		
		if (valorcadena == 20) {

			if(!LisProduct[7].equals("")){
				Double Concep_Importe = Double.parseDouble(LisProduct[7]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
				tot_concepto = tot_concepto-Concep_Importe;
			}
			if(!LisProduct[8].equals("")){
				Double Concep_Descuento = Double.parseDouble(LisProduct[8]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
				tot_descuento = tot_descuento-Concep_Descuento;
			}
			
		 				
		 				if(!LisProduct[13].equals("")){
		 					Double Concep_Imp_Traslado = Double.parseDouble(LisProduct[13]);
//		 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
		 					tot_traslado = tot_traslado-Concep_Imp_Traslado;
						}
		 				
		 				
		 				
		 				if(!LisProduct[18].equals("")){
		 					Double Concep_Imp_Retenciones = Double.parseDouble(LisProduct[18]);
//		 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Importe=\""+LisProduct[18]+"\"";
		 					tot_retenciones = tot_retenciones-Concep_Imp_Retenciones;
		 					
						}
		 				
		 				
		 				txt_Factura_Subtotal.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_concepto)).doubleValue()+"");
		 				txt_Factura_Subtotal.focus();
		 				
		 				
		 				
		 				if(tot_descuento > 0){
		 					txt_Factura_Descuento.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_descuento)).doubleValue()+"");
		 					txt_Factura_Descuento.focus();
		 				}
		 				
		 				tot_total = tot_concepto+tot_traslado-tot_retenciones -tot_descuento;
//		 				tot_total = tot_concepto-tot_descuento;
//		 				tot_total = tot_concepto-tot_descuento-tot_traslado;

		 				
		 				txt_Factura_Total.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_total)).doubleValue()+"");
		 				txt_Factura_Total.focus();	
		 				

		}
		
		if (valorcadena == 21) {

			if(!LisProduct[7].equals("")){
				Double Concep_Importe = Double.parseDouble(LisProduct[7]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Importe=\""+LisProduct[7]+"\"";
				tot_concepto = tot_concepto-Concep_Importe;
			}
			if(!LisProduct[8].equals("")){
				Double Concep_Descuento = Double.parseDouble(LisProduct[8]);
//				CadenaLinea06Concepto = CadenaLinea06Concepto + " Descuento=\""+LisProduct[8]+"\"";
				tot_descuento = tot_descuento-Concep_Descuento;
			}
			
		 				
		 				if(!LisProduct[13].equals("")){
		 					Double Concep_Imp_Traslado = Double.parseDouble(LisProduct[13]);
//		 					CadenaLinea07ImpTras = CadenaLinea07ImpTras + " Importe=\""+LisProduct[13]+"\"";
		 					tot_traslado = tot_traslado-Concep_Imp_Traslado;
						}
		 				
		 				
		 				
		 				if(!LisProduct[18].equals("")){
		 					Double Concep_Imp_Retenciones = Double.parseDouble(LisProduct[18]);
//		 					CadenaLinea08ImpRet = CadenaLinea08ImpRet + " Importe=\""+LisProduct[18]+"\"";
		 					tot_retenciones = tot_retenciones-Concep_Imp_Retenciones;
		 					
						}
		 				
		 				
		 				txt_Factura_Subtotal.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_concepto)).doubleValue()+"");
		 				txt_Factura_Subtotal.focus();
		 				
		 				
		 				
		 				if(tot_descuento > 0){
		 					txt_Factura_Descuento.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_descuento)).doubleValue()+"");
		 					txt_Factura_Descuento.focus();
		 				}
		 				
		 				tot_total = tot_concepto+tot_traslado-tot_retenciones -tot_descuento;
//		 				tot_total = tot_concepto-tot_descuento;
//		 				tot_total = tot_concepto-tot_descuento-tot_traslado;

		 				
		 				txt_Factura_Total.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_total)).doubleValue()+"");
		 				txt_Factura_Total.focus();	

		}
		
		
		
		}
		
		if(tot_traslado > 0){
			
			txt_Factura_Total_Trasl.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_traslado)).doubleValue()+"");
			txt_Factura_Total_Trasl.focus();
		}
		
		if(tot_retenciones > 0){
			
			txt_Factura_Total_Rete.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_retenciones)).doubleValue()+"");
			txt_Factura_Total_Rete.focus();
		}
		
		
		if(lb_Factura_Tipo_Compro.getSelectedItem().getLabel().equals("Pago")){
				txt_Factura_Total.setValue("0");
				txt_Factura_Total.focus();	
				
				txt_Factura_Subtotal.setValue("0");
 				txt_Factura_Subtotal.focus();
				
			}
		
		int index = itemList.getSelectedIndex();
        if(index >= 0){
            //remove the selected item
            itemList.removeItemAt(index);
            updateItemCount();
//            detalle.remove(index_Concepto);
        }else{
            //a easy way to show a message to user
            System.out.println("Please select an item first!");
            Messagebox.show("Selecciona Un registro para eliminar ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
        }
		
		
		
	}
	
	public void onClick$Agreegar_Relacion(Event event) throws Exception {
		
		if(!ValidaUUID(txt_Factura_RElacion_UUID.getValue())){
			Messagebox.show("No Cumple con la Estructura de un UUID", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
		}
		
		String cadenaRelacion = txt_Factura_RElacion_UUID.getValue();


        Listitem itemRelacion = new Listitem(cadenaRelacion);
        
        itemRelaList.appendChild(itemRelacion);
        //select the new created item.
        itemRelaList.setSelectedItem(itemRelacion);
        updateItemRelacionCount();
        
//        System.out.println(" Agrega index_Rela: " + index_Rela + " --- " +cadenaRelacion);
        LimpiarRela();
        

	}
	
	public void onClick$Agreegar_Farms(Event event) throws Exception {
		
		
		String cadenaFarms = txt_add_Farms_15_WEIGHT_CMTY.getValue()+"|"+txt_add_Farms_15_WEIGHT_DSC.getValue()+"|"+txt_add_Farms_15_WEIGHT_NET_WT.getValue();

        Listitem itemFarms = new Listitem(cadenaFarms);
        
        itemFarmsList.appendChild(itemFarms);
        //select the new created item.
        itemFarmsList.setSelectedItem(itemFarms);
        updateItemFarmsCount();
        
//        System.out.println(" Agrega index_Rela: " + index_Rela + " --- " +cadenaFarms);
//        LimpiarFarms();
        


	}
	
	public void onClick$Borrar_Farms(Event event) throws Exception {
		
		int index = itemFarmsList.getSelectedIndex();
        if(index >= 0){
            //remove the selected item
        	itemFarmsList.removeItemAt(index);
        	updateItemFarmsCount();
        	 System.out.println(" Elimina index_Rela: " + index );

        }else{
            //a easy way to show a message to user
            System.out.println("Please select an item first!");
            Messagebox.show("Selecciona Un registro para eliminar ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
        }
		
	}
	
	public void LimpiarRela(){
		txt_Factura_RElacion_UUID.setValue("");
	}
	
	public void LimpiarFarms(){
		txt_add_Farms_15_WEIGHT_CMTY.setValue("");
		txt_add_Farms_15_WEIGHT_DSC.setValue("");
		txt_add_Farms_15_WEIGHT_NET_WT.setValue("");
	}
	
	public void Limpiar(){
		txt_Factura_RElacion_UUID.setValue("");
		txt_Factura_Serie.setValue("");
		txt_Factura_Folio.setValue("");
		dt_Factura_Fecha.setText("");
		lb_FormaPagoFactDialog.setSelectedIndex(0);
		txt_Factura_Condi_Pago.setValue("");
		txt_Factura_Subtotal.setValue("");
		txt_Factura_Descuento.setValue("");
		cb_Moneda.setSelectedIndex(0);
		txt_Factura_Tipo_Cambio.setValue("");
		txt_Factura_Total.setValue("");
		lb_Factura_Tipo_Compro.setSelectedIndex(0);
		lb_MetodoPagoFactDialog.setSelectedIndex(0);
		txt_Factura_Lugar_Expedi.setValue("");
		txt_Factura_Confirma.setValue("");
		lb_Factura_Relacion.setSelectedIndex(-1);
		txt_Factura_RElacion_UUID.setValue("");
		itemRelaList.getItems().clear();
		
		
		txt_Factura_Emi_RFC.setValue("");
		txt_Factura_Emi_Nombre.setValue("");
		txt_Factura_Emi_Regimen.setValue("");
		
		txt_Factura_Recep_RFC.setValue("");
		txt_Factura_Recep_Nombre.setValue("");
		txt_Factura_Recep_Residencia.setValue("");
		txt_Factura_Recep_No_Tribu.setValue("");
		lb_UsoDialog.setSelectedIndex(0);
		
		txt_Factura_Conceptos_Clave.setValue("");
		txt_Factura_Conceptos_No_Identi.setValue("");
		txt_Factura_Conceptos_Cantidad.setValue(null);
		txt_Factura_Conceptos_ClaveUni.setValue("");
		txt_Factura_Conceptos_Unidad.setValue("");
		txt_Factura_Conceptos_Desc.setValue("");
		txt_Factura_Conceptos_ValorUni.setValue(null);
		txt_Factura_Conceptos_Importe.setValue(null);
		txt_Factura_Conceptos_Descuento.setValue(null);
		itemList.getItems().clear();
		
		txt_Factura_Imp_Tras_Base.setValue(null);
		lb_Factura_Imp_Tras_Imp.setSelectedIndex(0);
		lb_Factura_Imp_Tras_Factor.setSelectedIndex(0);
		lb_Factura_Imp_Tras_Tasa.setSelectedIndex(0);
		txt_Factura_Imp_Tras_Importe.setValue(null);
		
		txt_Factura_Imp_Ret_Base.setValue(null);
		lb_Factura_Imp_Ret_Impuesto.setSelectedIndex(0);
		lb_Factura_Imp_Ret_Factor.setSelectedIndex(0);
		lb_Factura_Imp_Ret_Tasa.setSelectedIndex(0);
		txt_Factura_Imp_Ret_Importe.setValue(null);
		
		txt_Factura_Aduana_Pedimento.setValue("");
		
		txt_Factura_Predial_Cuenta.setValue("");
		
//		txt_Factura_parte_Clave.setValue("");
//		txt_Factura_parte_Identi.setValue("");
//		txt_Factura_parte_Cantidad.setValue("");
//		txt_Factura_parte_Unidad.setValue("");
//		txt_Factura_parte_Desc.setValue("");
//		txt_Factura_parte_Unita.setValue("");
//		txt_Factura_parte_Importe.setValue("");

		txt_Factura_Total_Rete.setValue("");
		txt_Factura_Total_Trasl.setValue("");

		txt_Factura_13_Impuesto_Rete.setValue("");
		txt_Factura_13_Importe_Rete.setValue("");

		txt_Factura_14_Impuesto_Traslado.setValue("");
		txt_Factura_14_Factor_Traslado.setValue("");
		txt_Factura_14_Tasa_Traslado.setValue("");
		txt_Factura_14_Importe_Traslado.setValue("");

		txt_add_Farms_00_CURRENCY.setValue("");

		txt_add_Farms_01_SHIPPING_DATE.setValue("");
		txt_add_Farms_01_SHIPPED_FROM.setValue("");
		txt_add_Farms_01_FREIGHT_TERMS.setValue("");

		txt_add_Farms_02_BUYER_ID.setValue("");

		txt_add_Farms_03_PO.setValue("");

		txt_add_Farms_04_Remittance_Address.setValue("");

		txt_add_Farms_05_SHIPPED_TO_NAME.setValue("");
		txt_add_Farms_05_SHIPPED_TO_ADDRESS.setValue("");
		txt_add_Farms_05_SHIPPED_TO_CITY.setValue("");
		txt_add_Farms_05_SHIPPED_ID_TAX_RFC.setValue("");

		txt_add_Farms_06_OUR_ORDER_No.setValue("");

		txt_add_Farms_07_SALESMAN.setValue("");

		txt_add_Farms_08_CARRIER.setValue("");
		txt_add_Farms_08_LICENSE_No.setValue("");

		txt_add_Farms_09_WRITTEN_AMOUNT_ENGLISH.setValue("");
		txt_add_Farms_09_WRITTEN_AMOUNT_SPANISH.setValue("");

		txt_add_Farms_10_TOTAL_QUANTITY.setValue("");

		txt_add_Farms_11_COMMENTS_LINE_1.setValue("");

		txt_add_Farms_12_COMMENTS_LINE_2.setValue("");

		txt_add_Farms_13_COMMENTS_LINE_3.setValue("");

		txt_add_Farms_14_COMMENTS_LINE_4.setValue("");

		txt_add_Farms_15_WEIGHT_CMTY.setValue("");
		txt_add_Farms_15_WEIGHT_DSC.setValue("");
		txt_add_Farms_15_WEIGHT_NET_WT.setValue("");

		
		
		
		dt_Pago_01_FechaPago.setText("");
		lb_Pago_01_FormaDePagoP.setSelectedIndex(0);
		lb_Pago_01_MonedaP.setSelectedIndex(0);
		txt_Pago_01_TipoCambioP.setValue("");
		txt_Pago_01_Monto.setValue("");
		txt_Pago_01_NumOperacion.setValue("");
		txt_Pago_01_RfcEmisorCtaOrd.setValue("");
		txt_Pago_01_NomBancoOrdExt.setValue("");
		txt_Pago_01_CtaOrdenante.setValue("");
		txt_Pago_01_RfcEmisorCtaBen.setValue("");
		txt_Pago_01_CtaBeneficiario.setValue("");
		txt_Pago_01_TipoCadPago.setValue("");
		txt_Pago_01_CertPago.setValue("");
		txt_Pago_01_CadPago.setValue("");
		txt_Pago_01_SelloPago.setValue("");

		txt_Pago_02_IdDocumento.setValue("");
		txt_Pago_02_Serie.setValue("");
		txt_Pago_02_Folio.setValue("");
		lb_Pago_02_MonedaDR.setSelectedIndex(0);
		txt_Pago_02_TipoCambioDR.setValue("");
		lb_Pago_02_MetodoDePagoDR.setSelectedIndex(0);
		txt_Pago_02_NumParcialidad.setValue("");
		txt_Pago_02_ImpSaldoAnt.setValue("");
		txt_Pago_02_ImpPagado.setValue("");
		txt_Pago_02_ImpSaldoInsoluto.setValue("");

//		txt_Pago_03_TotalImpuestosRetenidos.setValue("");
//		txt_Pago_03_TotalImpuestosTrasladados.setValue("");
//
//		txt_Pago_04_Impuesto.setValue("");
//		txt_Pago_04_Importe.setValue("");
//
//		txt_Pago_05_Impuesto.setValue("");
//		txt_Pago_05_TipoFactor.setValue("");
//		txt_Pago_05_TasaOCuota.setValue("");
//		txt_Pago_05_Importe.setValue("");

		
		
		txt_Ext_00_Version.setValue("");
		lb_Ext_00_MotivoTraslado.setSelectedIndex(0);
		txt_Ext_00_TipoOperacion.setValue("");
		txt_Ext_00_ClaveDePedimento.setValue("");
		lb_Ext_00_CertificadoOrigen.setSelectedIndex(0);
		txt_Ext_00_NumCertificadoOrigen.setValue("");
		txt_Ext_00_NumeroExportadorConfiable.setValue("");
		txt_Ext_00_Incoterm.setValue("");
		txt_Ext_00_Subdivision.setValue("");
		txt_Ext_00_Observaciones.setValue("");
		txt_Ext_00_TipoCambioUSD.setValue("");
		txt_Ext_00_TotalUSD.setValue("");

		txt_Ext_01_Curp.setValue("");

		txt_Ext_02_Calle.setValue("");	
		txt_Ext_02_NumeroExterior.setValue("");
		txt_Ext_02_NumeroInterior.setValue("");
		txt_Ext_02_Colonia.setValue("");
		txt_Ext_02_Localidad.setValue("");
		txt_Ext_02_Referencia.setValue("");
		txt_Ext_02_Municipio.setValue("");
		txt_Ext_02_Estado.setValue("");
		txt_Ext_02_Pais.setValue("");
		txt_Ext_02_CodigoPostal.setValue("");

		txt_Ext_03_NumRegIdTrib.setValue("");
		txt_Ext_03_ResidenciaFiscal.setValue("");

		txt_Ext_04_NumRegIdTrib.setValue("");

		txt_Ext_06_Calle.setValue("");
		txt_Ext_06_NumeroExterior.setValue("");
		txt_Ext_06_NumeroInterior.setValue("");
		txt_Ext_06_Colonia.setValue("");
		txt_Ext_06_Localidad.setValue("");
		txt_Ext_06_Referencia.setValue("");
		txt_Ext_06_Municipio.setValue("");
		txt_Ext_06_Estado.setValue("");
		txt_Ext_06_Pais.setValue("");
		txt_Ext_06_CodigoPostal.setValue("");

		txt_Ext_07_NumRegIdTrib.setValue("");
		txt_Ext_07_Nombre.setValue("");

		txt_Ext_08_Calle.setValue("");
		txt_Ext_08_NumeroExterior.setValue("");
		txt_Ext_08_NumeroInterior.setValue("");
		txt_Ext_08_Colonia.setValue("");
		txt_Ext_08_Localidad.setValue("");
		txt_Ext_08_Referencia.setValue("");
		txt_Ext_08_Municipio.setValue("");
		txt_Ext_08_Estado.setValue("");
		txt_Ext_08_Pais.setValue("");
		txt_Ext_08_CodigoPostal.setValue("");

		txt_Ext_09_NoIdentificacion.setValue("");
		txt_Ext_09_FraccionArancelaria.setValue("");
		txt_Ext_09_CantidadAduana.setValue("");
		txt_Ext_09_UnidadAduana.setValue("");
		txt_Ext_09_ValorUnitarioAduana.setValue("");
		txt_Ext_09_ValorDolares.setValue("");

		txt_Ext_10_Marca.setValue("");
		txt_Ext_10_Modelo.setValue("");
		txt_Ext_10_SubModelo.setValue("");
		txt_Ext_10_NumeroSerie.setValue("");
		
		
	}
	
	public void onClick$Borrar_Relacion(Event event) throws Exception {
		
		int index = itemRelaList.getSelectedIndex();
        if(index >= 0){
            //remove the selected item
        	itemRelaList.removeItemAt(index);
        	updateItemRelacionCount();
        	 System.out.println(" Elimina index_Rela: " + index );
//        	 System.out.println(" --- " +itemRelaList.getSelectedItem().getLabel());
//        	detalleRelacion.remove(index);
        }else{
            //a easy way to show a message to user
            System.out.println("Please select an item first!");
            Messagebox.show("Selecciona Un registro para eliminar ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
        }
		
	}
	
	public void onClick$Agreegar_Concepto(Event event) throws Exception {
		
		
		if(txt_Factura_Conceptos_Clave.getValue() != null){
			if(txt_Factura_Conceptos_Clave.getValue().equals("")){
				Messagebox.show("Ingresa la Clave del Producto y Servicio ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}
		}else{
			Messagebox.show("Ingresa la Clave del Producto y Servicio ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			valida = false;
			return ;
		}
		
		if(txt_Factura_Conceptos_ClaveUni.getValue() != null){
			if(txt_Factura_Conceptos_ClaveUni.getValue().equals("")){
				Messagebox.show("Ingresa la Clave Unidad ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}
		}else{
			Messagebox.show("Ingresa la Clave Unidad ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			valida = false;
			return ;
		}
		
		if(txt_Factura_Conceptos_Desc.getValue() != null){
			if(txt_Factura_Conceptos_Desc.getValue().equals("")){
				Messagebox.show("Ingresa la Descripcion del Producto ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}
		}else{
			Messagebox.show("Ingresa la Descripcion del Producto ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			valida = false;
			return ;
		}
		
		if(txt_Factura_Conceptos_Importe.getValue() != null){
			if(txt_Factura_Conceptos_Importe.getValue().equals("")){
				Messagebox.show("Ingresa el Importe ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}
		}else{
			Messagebox.show("Ingresa el Importe ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			valida = false;
			return ;
		}
		
		if(txt_Factura_Conceptos_ValorUni.getValue() != null){
			if(txt_Factura_Conceptos_ValorUni.getValue().equals("")){
				Messagebox.show("Ingresa el Valor Unitario ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}
		}else{
			Messagebox.show("Ingresa el Valor Unitario ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			valida = false;
			return ;
		}
		
		String Union = "";
		String cadema = "";
		
		String Tras_Impuesto = "";
		String Tras_Factor = "";
		String Tras_Tasa = "";
		
		 String ImpTrasImp = "";
			
			if (lb_Factura_Imp_Tras_Imp.getSelectedIndex() > 0){
	    		System.out.println("Impuesto Tras: "+ lb_Factura_Imp_Tras_Imp.getSelectedItem().getLabel());
	    		
	    		String[] LisRelacion = lb_Factura_Imp_Tras_Imp.getSelectedItem().getLabel().split("-");
	    		
	    		
				   
					for (int i = 0; i < LisRelacion.length; i++) {
								
						ImpTrasImp = LisRelacion[0];
						System.out.println(LisRelacion[0]); 
					}
			}
			
			 String ImpTrasFactor = "";
				
				if (lb_Factura_Imp_Tras_Factor.getSelectedIndex() > 0){
		    		System.out.println("Factor Tras: "+ lb_Factura_Imp_Tras_Factor.getSelectedItem().getLabel());
		    		
		    		String[] LisFactor = lb_Factura_Imp_Tras_Factor.getSelectedItem().getLabel().split("-");
		    		
		    		
					   
						for (int i = 0; i < LisFactor.length; i++) {
									
							ImpTrasFactor = LisFactor[0];
							System.out.println(LisFactor[0]); 
						}
				}			
			
				String ImpTrasTasaCuota = "";
				
				if (lb_Factura_Imp_Tras_Tasa.getSelectedIndex() > 0){
		    		System.out.println("Factor Tras: "+ lb_Factura_Imp_Tras_Tasa.getSelectedItem().getLabel());
		    		
		    		ImpTrasTasaCuota = lb_Factura_Imp_Tras_Tasa.getSelectedItem().getLabel();
		    		
							
							
				}	
			
			
			
		
        //Traslados
		String cadenaTras =txt_Factura_Imp_Tras_Base.getValue()+"|"+ImpTrasImp+"|"+ImpTrasFactor+"|"+ImpTrasTasaCuota+"|"+txt_Factura_Imp_Tras_Importe.getValue();
		
		
		String ImpRetImp = "";
		
		if (lb_Factura_Imp_Ret_Impuesto.getSelectedIndex() > 0){
    		System.out.println("Impuesto Tras: "+ lb_Factura_Imp_Ret_Impuesto.getSelectedItem().getLabel());
    		
    		String[] LisRelacion = lb_Factura_Imp_Ret_Impuesto.getSelectedItem().getLabel().split("-");
    		
    		
			   
				for (int i = 0; i < LisRelacion.length; i++) {
							
					ImpRetImp = LisRelacion[0];
					System.out.println(LisRelacion[0]); 
				}
		}
		
		 String ImpRetFactor = "";
			
			if (lb_Factura_Imp_Ret_Factor.getSelectedIndex() > 0){
	    		System.out.println("Factor Tras: "+ lb_Factura_Imp_Ret_Factor.getSelectedItem().getLabel());
	    		
	    		String[] LisFactor = lb_Factura_Imp_Ret_Factor.getSelectedItem().getLabel().split("-");
	    		
	    		
				   
					for (int i = 0; i < LisFactor.length; i++) {
								
						ImpRetFactor = LisFactor[0];
						System.out.println(LisFactor[0]); 
					}
			}			
		
			String ImpRetTasaCuota = "";
			
			if (lb_Factura_Imp_Ret_Tasa.getSelectedIndex() > 0){
	    		System.out.println("Factor Tras: "+ lb_Factura_Imp_Ret_Tasa.getSelectedItem().getLabel());
	    		
	    		ImpRetTasaCuota = lb_Factura_Imp_Ret_Tasa.getSelectedItem().getLabel();
	    		
						
						
			}	
		
		//Retenciones
		String cadenaRet = txt_Factura_Imp_Ret_Base.getValue()+"|"+ImpRetImp+"|"+ImpRetFactor+"|"+ImpRetTasaCuota+"|"+txt_Factura_Imp_Ret_Importe.getValue();
		
		if(txt_Factura_Conceptos_Descuento.getValue() != null){
			if(lb_Factura_Tipo_Compro.getSelectedItem().getLabel().equals("Pago")){
				cadema =txt_Factura_Conceptos_Clave.getValue()+"|"+txt_Factura_Conceptos_No_Identi.getValue()+"|"+1+"|"+txt_Factura_Conceptos_ClaveUni.getValue().trim().toUpperCase()+"|"+txt_Factura_Conceptos_Unidad.getValue()+"|"+txt_Factura_Conceptos_Desc.getValue()+"|"+0+"|"+0+"|"+txt_Factura_Conceptos_Descuento.getValue();
			}else{
				cadema =txt_Factura_Conceptos_Clave.getValue()+"|"+txt_Factura_Conceptos_No_Identi.getValue()+"|"+txt_Factura_Conceptos_Cantidad.getValue()+"|"+txt_Factura_Conceptos_ClaveUni.getValue().trim().toUpperCase()+"|"+txt_Factura_Conceptos_Unidad.getValue()+"|"+txt_Factura_Conceptos_Desc.getValue()+"|"+txt_Factura_Conceptos_ValorUni.getValue()+"|"+txt_Factura_Conceptos_Importe.getValue()+"|"+txt_Factura_Conceptos_Descuento.getValue();
			}
			
		}else{
			if(lb_Factura_Tipo_Compro.getSelectedItem().getLabel().equals("Pago")){
				cadema =txt_Factura_Conceptos_Clave.getValue()+"|"+txt_Factura_Conceptos_No_Identi.getValue()+"|"+1+"|"+txt_Factura_Conceptos_ClaveUni.getValue().trim().toUpperCase()+"|"+txt_Factura_Conceptos_Unidad.getValue()+"|"+txt_Factura_Conceptos_Desc.getValue()+"|"+0+"|"+0+"|"+"";
			}else{
				cadema =txt_Factura_Conceptos_Clave.getValue()+"|"+txt_Factura_Conceptos_No_Identi.getValue()+"|"+txt_Factura_Conceptos_Cantidad.getValue()+"|"+txt_Factura_Conceptos_ClaveUni.getValue().trim().toUpperCase()+"|"+txt_Factura_Conceptos_Unidad.getValue()+"|"+txt_Factura_Conceptos_Desc.getValue()+"|"+txt_Factura_Conceptos_ValorUni.getValue()+"|"+txt_Factura_Conceptos_Importe.getValue()+"|"+"";
			}
			
		}
		
		
		
		
//		double Importe = Double.parseDouble(txt_Factura_Conceptos_Importe.getValue());
		
		if(!txt_Factura_Conceptos_Importe.getValue().equals("")){
			tot_concepto = tot_concepto+txt_Factura_Conceptos_Importe.getValue();
		}
		
		txt_Factura_Subtotal.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_concepto)).doubleValue()+"");
		txt_Factura_Subtotal.focus();
		
		if(txt_Factura_Conceptos_Descuento.getValue() != null){
			if(!txt_Factura_Conceptos_Descuento.getValue().equals("")){
				tot_descuento = tot_descuento+txt_Factura_Conceptos_Descuento.getValue();
			}
		}
		
		if(tot_descuento > 0){
			txt_Factura_Descuento.setValue(tot_descuento+"");
			txt_Factura_Descuento.focus();
		}
		
		
		if(txt_Factura_Imp_Tras_Importe.getValue() != null){
			if(!txt_Factura_Imp_Tras_Importe.getValue().equals("")){
				tot_traslado = tot_traslado+txt_Factura_Imp_Tras_Importe.getValue();
			}
		}
		
		if(txt_Factura_Imp_Ret_Importe.getValue() != null){
			if(!txt_Factura_Imp_Ret_Importe.getValue().equals("")){
				tot_retenciones = tot_retenciones+txt_Factura_Imp_Ret_Importe.getValue();
			}
		}
		
		
		
		
		String cade_aduana = "Aduana_Pedimento:"+txt_Factura_Aduana_Pedimento.getValue();
		String Cadena_Predial = "Cuenta_Predial:"+txt_Factura_Predial_Cuenta.getValue();
		
		Union = cadema;
		
		if(txt_Factura_Imp_Tras_Base.getValue() != null){
			if(!txt_Factura_Imp_Tras_Base.getValue().equals("") || !ImpTrasImp.trim().equals("") || !ImpTrasFactor.trim().equals("")){
				
//				if(lb_Factura_Imp_Tras_Imp.getSelectedIndex() < 0){
					if(lb_Factura_Imp_Tras_Imp.getSelectedIndex() < 0){
						Messagebox.show("Seleciona un Tipo de Impuesto Traslado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//						valida = false;
						return ;
					}
//				}else{
//					Messagebox.show("Selecciona un Tipo de Impuesto Traslado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////					valida = false;
//					return ;
//				}
				
//				if(lb_Factura_Imp_Tras_Factor.getSelectedIndex() < 0){
					if(lb_Factura_Imp_Tras_Factor.getSelectedIndex() < 0){
						Messagebox.show("Seleciona Un Factor Traslado", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//						valida = false;
						return ;
					}
//				}else{
//					Messagebox.show("Seleciona Un Factor Traslado", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////					valida = false;
//					return ;
//				}
				
//				if(lb_Factura_Imp_Tras_Tasa.getSelectedIndex() < 0){
					if(lb_Factura_Imp_Tras_Tasa.getSelectedIndex() < 0){
						Messagebox.show("Seleciona Una Tasa de Traslado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//						valida = false;
						return ;
					}
//				}else{
//					Messagebox.show("Seleciona Una Tasa de Traslado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////					valida = false;
//					return ;
//				}
				
				if(txt_Factura_Imp_Tras_Importe.getValue() != null){
					if(txt_Factura_Imp_Tras_Importe.getValue() < 0){
						Messagebox.show("Calcula El Importe del Impuesto Trasladado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//						valida = false;
						return ;
					}
				}else{
					Messagebox.show("Calcula El Importe del Impuesto Trasladado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//					valida = false;
					return ;
				}
				
				Union = Union+"|"+ cadenaTras;
				
				Tras_Impuesto = ImpTrasImp.trim();
				Tras_Factor = ImpTrasFactor;
				Tras_Tasa = ImpTrasTasaCuota;
			}	
		}
		
		if(txt_Factura_Imp_Ret_Base.getValue() != null){
			if(!txt_Factura_Imp_Ret_Base.getValue().equals("") || !ImpRetImp.trim().equals("") || !ImpRetFactor.equals("")){
				
//				if(lb_Factura_Imp_Ret_Impuesto.getSelectedIndex() < 0){
					if(lb_Factura_Imp_Ret_Impuesto.getSelectedIndex() < 0){
						Messagebox.show("Seleciona un Tipo de Impuesto Retenido ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//						valida = false;
						return ;
					}
//				}else{
//					Messagebox.show("Selecciona un Tipo de Impuesto Retenido ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////					valida = false;
//					return ;
//				}
				
//				if(lb_Factura_Imp_Ret_Factor.getSelectedIndex() < 0){
					if(lb_Factura_Imp_Ret_Factor.getSelectedIndex() < 0){
						Messagebox.show("Seleciona Un Factor Retenido", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//						valida = false;
						return ;
					}
//				}else{
//					Messagebox.show("Seleciona Un Factor Retenido", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////					valida = false;
//					return ;
//				}
				
//				if(lb_Factura_Imp_Ret_Tasa.getSelectedIndex() < 0){
					if(lb_Factura_Imp_Ret_Tasa.getSelectedIndex() < 0){
						Messagebox.show("Seleciona Una Tasa de Retenido ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//						valida = false;
						return ;
					}
//				}else{
//					Messagebox.show("Seleciona Una Tasa de Retenido ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////					valida = false;
//					return ;
//				}
				
				if(txt_Factura_Imp_Ret_Importe.getValue() != null){
					if(txt_Factura_Imp_Ret_Importe.getValue() < 0){
						Messagebox.show("Calcula El Importe del Impuesto Retenido ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//						valida = false;
						return ;
					}
				}else{
					Messagebox.show("Calcula El Importe del Impuesto Retenido ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//					valida = false;
					return ;
				}
				
				Union = Union+"|"+ cadenaRet;
			}
		}
		
		if(!txt_Factura_Aduana_Pedimento.getValue().equals("")){
			Union = Union+"|"+cade_aduana;
		}
		if(!txt_Factura_Predial_Cuenta.getValue().equals("")){
			Union = Union+"|"+Cadena_Predial;
		}
		
		if(tot_traslado > 0){
			
			txt_Factura_Total_Trasl.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_traslado)).doubleValue()+"");
			txt_Factura_Total_Trasl.focus();
			
			txt_Factura_14_Impuesto_Traslado.setValue(Tras_Impuesto);
			txt_Factura_14_Impuesto_Traslado.focus();
			txt_Factura_14_Factor_Traslado .setValue(Tras_Factor);
			txt_Factura_14_Factor_Traslado.focus();
			txt_Factura_14_Tasa_Traslado .setValue(Tras_Tasa);
			txt_Factura_14_Tasa_Traslado.focus();
			txt_Factura_14_Importe_Traslado .setValue((cfdUtil.getBigDecimalValueRoundUp(tot_traslado)).doubleValue()+"");
			txt_Factura_14_Importe_Traslado.focus();
		}
		
		if(tot_retenciones > 0){
			
			txt_Factura_Total_Rete.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_retenciones)).doubleValue()+"");
			txt_Factura_Total_Rete.focus();
			
			txt_Factura_13_Impuesto_Rete.setValue(ImpRetImp);
			txt_Factura_13_Impuesto_Rete.focus();
			txt_Factura_13_Importe_Rete.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_retenciones)).doubleValue()+"");
			txt_Factura_13_Importe_Rete.focus();
		}
		
		

		
		
		tot_total = tot_concepto+tot_traslado-tot_retenciones -tot_descuento;

		
		txt_Factura_Total.setValue((cfdUtil.getBigDecimalValueRoundUp(tot_total)).doubleValue()+"");
		txt_Factura_Total.focus();
		
		if(lb_Factura_Tipo_Compro.getSelectedItem().getLabel().equals("Pago")){
			txt_Factura_Total.setValue("0");
			txt_Factura_Total.focus();	
			
			txt_Factura_Subtotal.setValue("0");
				txt_Factura_Subtotal.focus();
			
		}
		 
		//Union = cadema+""+ cadenaTras+""+cadenaRet;
        Listitem itemclave = new Listitem(Union);
        
        itemList.appendChild(itemclave);
        //select the new created item.
        itemList.setSelectedItem(itemclave);
        updateItemCount();
        
        
        
        
//        detalle.add(index_Concepto,Union);
//        
//        index_Concepto++;
        
////		String alturaStr = "";
////		Date fecha = new Date();
////
////		try {
////
////			Listitem item = null;
////
////			Object[] a = itemList.getSelectedItems().toArray();
////
////			for (int i = 0; i < a.length; i++) {
////				item = (Listitem) a[i];
////				// Se elimina el producto de la tabla de apartado
//////				VentaService.eliminaApartaUnidades(item);
////				itemList.removeChild(item);
////
////				alturaStr = itemList.getHeight();
////				int altura = 0;
////				if (alturaStr.indexOf("px") > -1) {
////					alturaStr = alturaStr.substring(0, alturaStr.indexOf("px"));
////					altura = new Integer(alturaStr) - 30;
////				}
////
////				itemList.setHeight(altura + "px");
////			}
////
////			
////			ventaserv.AgregaConcepto(listBoxDetalleFactura,	detalle, txt_Factura_Conceptos_Clave.getValue(),txt_Factura_Conceptos_No_Identi.getValue(),txt_Factura_Conceptos_Cantidad.getValue(),txt_Factura_Conceptos_ClaveUni.getValue(),txt_Factura_Conceptos_Unidad.getValue(),txt_Factura_Conceptos_Desc.getValue(),txt_Factura_Conceptos_ValorUni.getValue(),txt_Factura_Conceptos_Importe.getValue(),txt_Factura_Conceptos_Descuento.getValue());
//			
//		} catch (Exception e) {
//			logger.error("" + e.getMessage());
//			e.printStackTrace();
//		}
	}
	
	public void onClick$Borrar_Tras(Event event) throws Exception {
		
		
		int index = itemListTras.getSelectedIndex();
        if(index >= 0){
            //remove the selected item
        	itemListTras.removeItemAt(index);
        	updateItemTrasCount();
        }else{
            //a easy way to show a message to user
            System.out.println("Please select an item first!");
            Messagebox.show("Selecciona Un registro para eliminar ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
        }
		
	}
	public void onClick$Agreegar_Tras(Event event) throws Exception {
		
		 String ImpTrasImp = "";
		
		if (lb_Factura_Imp_Tras_Imp.getSelectedIndex() > 0){
    		System.out.println("Impuesto Tras: "+ lb_Factura_Imp_Tras_Imp.getSelectedItem().getLabel());
    		
    		String[] LisRelacion = lb_Factura_Imp_Tras_Imp.getSelectedItem().getLabel().split("-");
    		
    		
			   
				for (int i = 0; i < LisRelacion.length; i++) {
							
					ImpTrasImp = LisRelacion[0];
					System.out.println(LisRelacion[0]); 
				}
		}
		
		String ImpTrasFactor = "";
		
		if (lb_Factura_Imp_Tras_Factor.getSelectedIndex() > 0){
    		System.out.println("Factor Tras: "+ lb_Factura_Imp_Tras_Factor.getSelectedItem().getLabel());
    		
    		String[] LisFactor = lb_Factura_Imp_Tras_Factor.getSelectedItem().getLabel().split("-");
    		
    		
			   
				for (int i = 0; i < LisFactor.length; i++) {
							
					ImpTrasFactor = LisFactor[0];
					System.out.println(LisFactor[0]); 
				}
		}
		
		String ImpTrasTasaCuota = "";
		
		if (lb_Factura_Imp_Tras_Tasa.getSelectedIndex() > 0){
    		System.out.println("Factor Tras: "+ lb_Factura_Imp_Tras_Tasa.getSelectedItem().getLabel());
    		
    		ImpTrasTasaCuota = lb_Factura_Imp_Tras_Tasa.getSelectedItem().getLabel();
    		
					
					
		}	
		
		
		
        //you could new a component directly and append it to another component.
		String cadema =txt_Factura_Imp_Tras_Base.getValue()+"|"+ImpTrasImp+"|"+ImpTrasFactor+"|"+ImpTrasTasaCuota+"|"+txt_Factura_Imp_Tras_Importe.getValue();
        Listitem itemclave = new Listitem(cadema);
        
        itemListTras.appendChild(itemclave);
        //select the new created item.
        itemListTras.setSelectedItem(itemclave);
        updateItemTrasCount();
	}
	public void updateItemTrasCount() {
		
		 //update extra information for user
       itemCountTras.setValue(Integer.toString(itemListTras.getItemCount()));
		
	}
	
	public void onClick$Borrar_CRP(Event event) throws Exception {
		
		
		int index = itemCRPList.getSelectedIndex();
        if(index >= 0){
            //remove the selected item
        	itemCRPList.removeItemAt(index);
        	updateitemCRPCount();
        }else{
            //a easy way to show a message to user
            System.out.println("Please select an item first!");
            Messagebox.show("Selecciona Un registro para eliminar ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			return;
        }
		
	}
	public void onClick$Agreegar_CRP(Event event) throws Exception {
		
		
		if(txt_Pago_02_IdDocumento.getValue() != null){
			if(txt_Pago_02_IdDocumento.getValue().equals("")){
				Messagebox.show("Ingresa el IdDocumento del Complemento de pago linea 02 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");

				return ;
			}else{
				if(!ValidaUUID(txt_Pago_02_IdDocumento.getValue())){
					Messagebox.show("No Cumple con la Estructura de un UUID (IdDocumento) del Complemento de pago linea 02 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					return;
				}
			}
		}else{
			Messagebox.show("Ingresa el IdDocumento del Complemento de pago linea 02 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");

			return ;
		}
		
//		if(lb_Pago_02_MonedaDR.getSelectedIndex() < 0){
			if(lb_Pago_02_MonedaDR.getSelectedIndex() < 0){
				Messagebox.show("Selecciona la Moneda (MonedaDR) del Complemento de pago linea 02 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");

				return ;
			}
//		}else{
//			Messagebox.show("Selecciona la Moneda (MonedaDR) del Complemento de pago linea 02 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//
//			return ;
//		}
		
//		if(lb_Pago_02_MetodoDePagoDR.getSelectedIndex() < 0){
			if(lb_Pago_02_MetodoDePagoDR.getSelectedIndex() < 0){
				Messagebox.show("Selecciona Metodo de Pago (MetodoDePagoDR) del Complemento de pago linea 02 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");

				return ;
			}
//		}else{
//			Messagebox.show("Selecciona Metodo de Pago (MetodoDePagoDR) del Complemento de pago linea 02 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//
//			return ;
//		}
		
		if(txt_Pago_02_ImpSaldoAnt.getValue() != null){
			if(txt_Pago_02_ImpSaldoAnt.getValue().equals("")){
				Messagebox.show("Ingresa el Importe del SAldo Anterior (ImpSaldoAnt) del Complemento de pago linea 02 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");

				return ;
			}
		}else{
			Messagebox.show("Ingresa el Importe del SAldo Anterior (ImpSaldoAnt) del Complemento de pago linea 02", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");

			return ;
		}
		
		
        //you could new a component directly and append it to another component.
		Listitem item_Met_Pag_CRP = lb_Pago_02_MetodoDePagoDR.getSelectedItem();
        ListModelList lml_Met_Pag_CRP = (ListModelList) lb_Pago_02_MetodoDePagoDR.getListModel();
		ComboFactory comboFactory_Met_Pag_CRP = (ComboFactory) lml_Met_Pag_CRP.get(item_Met_Pag_CRP.getIndex());
		
        System.out.println(comboFactory_Met_Pag_CRP.getNombre());
        
        String[] LisProduct_MP_CRP = comboFactory_Met_Pag_CRP.getNombre().split("-");
	    
        String Metodo_Pago_CRP = "";
		   
		for (int i = 0; i < LisProduct_MP_CRP.length; i++) {
					
			Metodo_Pago_CRP = LisProduct_MP_CRP[0];
			System.out.println(LisProduct_MP_CRP[0]); 
		}
		
		
		
		String cadenaCRP =txt_Pago_02_IdDocumento.getValue()+"|"+txt_Pago_02_Serie.getValue()+"|"+txt_Pago_02_Folio.getValue()+"|"+lb_Pago_02_MonedaDR.getSelectedItem().getLabel()+"|"+txt_Pago_02_TipoCambioDR.getValue()+"|"+Metodo_Pago_CRP.trim()+"|"+txt_Pago_02_NumParcialidad.getValue()+"|"+txt_Pago_02_ImpSaldoAnt.getValue()+"|"+txt_Pago_02_ImpPagado.getValue()+"|"+txt_Pago_02_ImpSaldoInsoluto.getValue();
        Listitem itemclave = new Listitem(cadenaCRP);
        
        itemCRPList.appendChild(itemclave);
        //select the new created item.
        itemCRPList.setSelectedItem(itemclave);
        updateitemCRPCount();
	}
	
	public void updateitemCRPCount() {
		
		 //update extra information for user
		itemCRPCount.setValue(Integer.toString(itemCRPList.getItemCount()));
		
	}
	
	
	
	public void updateItemCount() {
		
		 //update extra information for user
        itemCount.setValue(Integer.toString(itemList.getItemCount()));
		
	}
	
	public void updateItemRelacionCount() {
		
		 //update extra information for user
       itemCount.setValue(Integer.toString(itemRelaList.getItemCount()));
		
	}
	
	public void updateItemFarmsCount() {
		
		 //update extra information for user
      itemFarmsCount.setValue(Integer.toString(itemFarmsList.getItemCount()));
		
	}
	
	
	
	public void onClick$btnBorraProd(Event event) throws Exception {
		String alturaStr = "";
		Date fecha = new Date();

		try {

			Listitem item = null;

			Object[] a = listBoxDetalleFactura.getSelectedItems().toArray();

			for (int i = 0; i < a.length; i++) {
				item = (Listitem) a[i];
				// Se elimina el producto de la tabla de apartado
//				VentaService.eliminaApartaUnidades(item);
				listBoxDetalleFactura.removeChild(item);

				alturaStr = listBoxDetalleFactura.getHeight();
				int altura = 0;
				if (alturaStr.indexOf("px") > -1) {
					alturaStr = alturaStr.substring(0, alturaStr.indexOf("px"));
					altura = new Integer(alturaStr) - 30;
				}

				listBoxDetalleFactura.setHeight(altura + "px");
			}

//			if (listBoxDetalleFactura.getItemCount() == 0) {
//				btnBuscaCte.setDisabled(false);
//				btnNuevocte.setDisabled(false);
//			}
			boolean desglosaIva = false;
//			if (tb_RFCCteFactura.getValue() != null
//					&& tb_RFCCteFactura.getValue().equals(""))
				desglosaIva = true;
			
			String moneda = "";
			
//			Listitem itemTasa = cb_tasa.getSelectedItem();
//			String ValorTasa = itemTasa.getLabel();
			
			
			
//			double dobleTasa = Double.parseDouble(cb_tasa.getSelectedItem().getLabel());
			double dobleTasa = Double.parseDouble(cb_tasa.getSelectedItem().getLabel());
			System.out.println("dobleTasa: "+ dobleTasa);
			System.out.println("cb_tasa.getSelectedItem().getLabel( : "+ cb_tasa.getSelectedItem().getLabel());
			
//			ventaserv.calcuaTotalesDirect(listBoxDetalleFactura,
//					detalle, desglosaIva,cb_tasa.getSelectedItem().getLabel(), tb_IvaFactura, tb_RetIvaFactura,
//					tb_RetISRFactura, tb_SubtotalFactura, tb_TotalFactura,
//					tb_TotalLetraFactura, false, moneda,ch_Retenciones);
			
			if(!tb_TotalFactura.getValue().equals("") && tb_TotalFactura.getValue() != null){
				
				
				double doble = Double.parseDouble(tb_TotalFactura.getValue());
//				if(doble > getcorp.getCantidadMax()){
//					id_row_Autoriza_area.setVisible(true);
//				}else{
//					id_row_Autoriza_area.setVisible(false);
//				}
					
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
			e.printStackTrace();
		}

	}
	
	public void onFocusCantidad1(Event event) throws Exception {
		if (event != null)
			logger.info("onFocusCantidad: " + event.getName());
		String moneda = "MXN";
		Listitem itemMoneda = cb_Moneda.getSelectedItem();
		String nombre = itemMoneda.getLabel();
		if (nombre.startsWith("EUR")) 
			moneda = "EUR";
		else if (nombre.startsWith("USD")) 
			moneda = "USD";
		else if (nombre.startsWith("CAD")) 
			moneda = "CAD";

		boolean desglosaIva = false;
//		if (tb_RFCCteFactura.getValue() != null
//				&& !tb_RFCCteFactura.getValue().equals(""))
			desglosaIva = true;
//		ventaserv.calcuaTotales(listBoxDetalleFactura, detalle,
//				desglosaIva, tb_IvaFactura, tb_RetIvaFactura, tb_RetISRFactura,
//				tb_SubtotalFactura, tb_TotalFactura, tb_TotalLetraFactura,
//				false, moneda);
		
//		ventaserv.calcuaTotales(listBoxDetalleFactura, detalle, desglosaIva, tb_IvaFactura, tb_RetIvaFactura, tb_RetISRFactura, 
//				tb_SubtotalFactura, tb_TotalFactura, tb_TotalLetraFactura, false, false);
		
if(!tb_TotalFactura.getValue().equals("") && tb_TotalFactura.getValue() != null){
			
			
			double doble = Double.parseDouble(tb_TotalFactura.getValue());
//			if(doble > getcorp.getCantidadMax()){
//				id_row_Autoriza_area.setVisible(true);
//			}else{
//				id_row_Autoriza_area.setVisible(false);
//			}
				
		}
	}

	public void onBlurCantidad1(Event event) throws Exception {
		if (event != null)
			logger.info("onBlurCantidad: " + event.getName());

		boolean desglosaIva = false;
//		if (tb_RFCCteFactura.getValue() != null
//				&& !tb_RFCCteFactura.getValue().equals(""))
			desglosaIva = true;
		
		String moneda = "MXN";
		Listitem itemMoneda = cb_Moneda.getSelectedItem();
		String nombre = itemMoneda.getLabel();
		if (nombre.startsWith("EUR")) 
			moneda = "EUR";
		else if (nombre.startsWith("USD")) 
			moneda = "USD";
		else if (nombre.startsWith("CAD")) 
			moneda = "CAD";
//		ventaserv.calcuaTotales(listBoxDetalleFactura, detalle,
//				desglosaIva, tb_IvaFactura, tb_RetIvaFactura, tb_RetISRFactura,
//				tb_SubtotalFactura, tb_TotalFactura, tb_TotalLetraFactura,
//				false, false);
		
if(!tb_TotalFactura.getValue().equals("") && tb_TotalFactura.getValue() != null){
			
			
			double doble = Double.parseDouble(tb_TotalFactura.getValue());
//			if(doble > getcorp.getCantidadMax()){
//				id_row_Autoriza_area.setVisible(true);
//			}else{
//				id_row_Autoriza_area.setVisible(false);
//			}
				
		}

	}

	public void onFocusUnitario1(Event event) throws Exception {
		if (event != null)
			logger.info("onFocusUnitario: " + event.getName());

		boolean desglosaIva = false;
//		ventaserv.setIshonorario(checkbox_Hono.isChecked());
//		if (tb_RFCCteFactura.getValue() != null
//				&& !tb_RFCCteFactura.getValue().equals(""))
			desglosaIva = true;
		
//			System.out.println(ib_clave.getRows());
		
		String moneda = "MXN";
		Listitem itemMoneda = cb_Moneda.getSelectedItem();
		String nombre = itemMoneda.getLabel();
		if (nombre.startsWith("EUR")) 
			moneda = "EUR";
		else if (nombre.startsWith("USD")) 
			moneda = "USD";
		else if (nombre.startsWith("CAD")) 
			moneda = "CAD";
		
//		Listitem itemTasa = cb_tasa.getSelectedItem();
//		String ValorTasa = itemTasa.getLabel();
		
		double dobleTasa = Double.parseDouble(cb_tasa.getSelectedItem().getLabel());
		System.out.println("dobleTasa: "+ dobleTasa);
		System.out.println("cb_tasa.getSelectedItem().getLabel( : "+ cb_tasa.getSelectedItem().getLabel());
		
//		ventaserv.calcuaTotalesDirect(listBoxDetalleFactura, detalle,
//				desglosaIva, cb_tasa.getSelectedItem().getLabel(),tb_IvaFactura, tb_RetIvaFactura, tb_RetISRFactura,
//				tb_SubtotalFactura, tb_TotalFactura, tb_TotalLetraFactura,
//				false, moneda,ch_Retenciones);
		
if(!tb_TotalFactura.getValue().equals("") && tb_TotalFactura.getValue() != null){
			
			
			double doble = Double.parseDouble(tb_TotalFactura.getValue());
//			if(doble > getcorp.getCantidadMax()){
//				id_row_Autoriza_area.setVisible(true);
//			}else{
//				id_row_Autoriza_area.setVisible(false);
//			}
				
		}
	}

	public void onBlurUnitario1(Event event) throws Exception {
		if (event != null)
			logger.info("onBlurUnitario: " + event.getName());

		boolean desglosaIva = true;
//		ventaserv.setIshonorario(checkbox_Hono.isChecked());
//		if (tb_RFCCteFactura.getValue() != null
//				&& !tb_RFCCteFactura.getValue().equals(""))
//			desglosaIva = true;
		
		
		String moneda = "MXN";
		Listitem itemMoneda = cb_Moneda.getSelectedItem();
		String nombre = itemMoneda.getLabel();
		if (nombre.startsWith("EUR")) 
			moneda = "EUR";
		else if (nombre.startsWith("USD")) 
			moneda = "USD";
		else if (nombre.startsWith("CAD")) 
			moneda = "CAD";
		
//		Listitem itemTasa = cb_tasa.getSelectedItem();
//		String ValorTasa = itemTasa.getLabel();
		
		double dobleTasa = Double.parseDouble(cb_tasa.getSelectedItem().getLabel());
		System.out.println("dobleTasa: "+ dobleTasa);
		System.out.println("cb_tasa.getSelectedItem().getLabel( : "+ cb_tasa.getSelectedItem().getLabel());
//		ventaserv.calcuaTotalesDirect(listBoxDetalleFactura, detalle,
//				desglosaIva,cb_tasa.getSelectedItem().getLabel(), tb_IvaFactura, tb_RetIvaFactura, tb_RetISRFactura,
//				tb_SubtotalFactura, tb_TotalFactura, tb_TotalLetraFactura,
//				false, moneda,ch_Retenciones);
		
if(!tb_TotalFactura.getValue().equals("") && tb_TotalFactura.getValue() != null){
			
			
			double doble = Double.parseDouble(tb_TotalFactura.getValue());
//			System.out.println("getcorp.getCantidadMax(): "+ getcorp.getCantidadMax());
//			if(doble > getcorp.getCantidadMax()){
//				id_row_Autoriza_area.setVisible(true);
//			}else{
//				id_row_Autoriza_area.setVisible(false);
//			}
//				
		}

	}

	public void onItemClicked(Event event) throws Exception {
		logger.info("-----onItemClicked--------------");
		// get the selected object
		Listitem item = listBoxDetalleFactura.getSelectedItem();
		listBoxDetalleFactura.setSelectedItem(item);

		if (item != null) {
			List<Listcell> lista = item.getChildren();
			logger.info(lista);

			for (Listcell o : lista) {
				List<Object> nueva = o.getChildren();

				for (Object n : nueva) {
					if (n instanceof Textbox) {
						logger
								.info("***********************************************");
						Textbox a = (Textbox) n;
						logger
								.info("**********************************************  *"
										+ a.getValue());
					}

				}

			}

		}
	}
	
	
	
	

	/**
	 * when the "edit" button is clicked. <br>
	 * 
	 * @param event
	 */
	public void onClick$btn_Modificar(Event event) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

		doEdit();
	}

	
	 /*
	 * when the "new" button is clicked. <br>
	 * 
	 * @param event
	 */
//	public void onClick$btn_Actualizar(Event event) throws InterruptedException {
//		
//		Properties props = new Properties();
//		InputStream is = null;
//		Calendar cal = new GregorianCalendar();
//		
//		
////		if(txt_UserName.getValue().trim().equals("")){
////			
////			Messagebox.show("El campo Login es requerido", "ï¿½Informaciï¿½n", org.zkoss.zul.Messagebox.OK, "");
////			return;
////			
////		}
//		
////		if(txt_nombre.getValue().trim().equals("")){
////			
////			Messagebox.show("El campo Nombre es requerido", "ï¿½Informaciï¿½n", org.zkoss.zul.Messagebox.OK, "");
////			return;
////			
////		}
//		
//		if(txt_Proveedor.getValue().trim().equals("")){
//
//			Messagebox.show("El campo RFC es requerido", "ï¿½Informaciï¿½n", org.zkoss.zul.Messagebox.OK, "");
//			return;
//		
//		}
////		if(!usrEnabled.isChecked()){
////
////			Messagebox.show("El campo Activo es requerido", "ï¿½Informaciï¿½n", org.zkoss.zul.Messagebox.OK, "");
////			return;
////		
////		}
//		try {
//			
//		    is = getClass().getResourceAsStream("/email.properties");
//		    props.load(is);
//		    
//		} 
//		catch(Exception e) 
//		{
//			e.printStackTrace();
//		}
//		
//		cal.setTime(new Date());
//		cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(props.getProperty("diasVigencia")));
//				
//		TbUsuario anUser = getUser();
//		
//		TbRfc part = new TbRfc();
//		TbUsuario p_Use = new TbUsuario();
//		TbUsuario p_Use_ID = new TbUsuario();
//		
//		UserServ = new UsuarioService();
//
////		id_Usuario = anUser.getIdUsuario();
//
////		id_Partner = UserServ.getIdByPartner(anUser.getNombre());
//
//		id_Partner =idRFCs;
//		id_Usuario = idUsuarios;
//		
//		
//		
//		anUser.setIdUsuario(id_Usuario);
////		anUser.getTbPersona().setChNombre(txt_nombre.getText());
//		
//		PWD = Password.generaAleatorio(10);
//		System.out.println("PAssword ---->> " + PWD);
//		try {
//			Cifrado =codi.cifrar(PWD);
//			System.out.println(Cifrado);
//		} catch (UnsupportedEncodingException e) {
//			// Error al Cifrar la Contraseï¿½a
//			Messagebox.show("MP-008", "ï¿½Error", org.zkoss.zul.Messagebox.OK, "");
//			e.printStackTrace();
//		}
//		anUser.setPassword(Cifrado);
////		anUser.setUsername(txt_UserName.getText());
////		anUser.getTbPersona().setChEmail(txt_mail.getText());
//		anUser.setPwdExpire(new Timestamp(cal.getTimeInMillis()));
//		
//		Double Activo;
//		
////		if(usrEnabled.isChecked()){
////			Activo = 1.0;
////		}else{
////			Activo = 0.0;
////		}
//		
//		int Administrator;
//		
////		if(check_admin.isChecked()){
////			Administrator = 1;
////		}else{
////			Administrator = 0;
////		}
//			
////		anUser.setStatus(Activo);
////		anUser.getTbPerfil().getTbRols().setAdministrador(Administrator);
////		anUser.setPwdExpire(ts_now);//ts_now);
//		anUser.setPwdHistory("0");
//		
//		part.setIdRfc(id_Partner);
////		part.getTbUsuarios().getTbPersona().setChNombre(txt_UserName.getText());
//		part.setChRfc(txt_Proveedor.getText().toUpperCase().trim());
//		
//		try {
////			l_ParUser = UserServ.saveParUser(p_Use);
//			
////			UserServ.saveOrUpdate(anUser);
//					
////			UserServ.saveOrUpdatePart1(part);
//
////			l_ParUser = UserServ.saveParUser(p_Use);
//			
//			
//				// now synchronize the listBox
//				ListModelList lml = (ListModelList) listBoxOrden.getListModel();
//
//				// Check if the object is new or updated
//				// -1 means that the obj is not in the list, so it's new.
//				if (lml.indexOf(anUser) == -1) {
//					lml.add(anUser);
//				} else {
//					lml.set(lml.indexOf(anUser), anUser);
//				}
//
////				Messagebox.show("Seleccione el Comprobante que se requiere Descargar", "ï¿½Informaciï¿½n!", org.zkoss.zul.Messagebox.OK, "");
//				Messagebox.show("El registro se actualizo correctamente", "ï¿½Informaciï¿½n", org.zkoss.zul.Messagebox.OK, "");
//				
////				Messagebox.show("Password:" + PWD, "ï¿½Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//				
//		} catch (HibernateException e) {
//			//Error al Actualizar en la BD
//			Messagebox.show("MP-007", "ï¿½Error", org.zkoss.zul.Messagebox.OK, "");
//			e.printStackTrace();
//
//			return;
//		} catch (Exception e) {
//			Messagebox.show("MP-007", "ï¿½Error", org.zkoss.zul.Messagebox.OK, "");
//			e.printStackTrace();
//
//			return;
//		}
//
//
////		l_id = null;
//	}


	
	public void onClick$btn_Cerrar(Event event) throws InterruptedException {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}

			ordenCompraDialogWindow.onClose();

	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++ GUI operations +++++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Closes the dialog window. <br>
	 * <br>
	 * Before closing we check if there are unsaved changes in <br>
	 * the components and ask the user if saving the modifications. <br>
	 * 
	 */
	private void doClose() throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> DataIsChanged :" + isDataChanged());
		}

//		if (isDataChanged()) {
//
//			// Show a confirm box
//			String msg = Labels.getLabel("message_Data_Modified_Save_Data_YesNo");
//			String title = Labels.getLabel("message_Information");
//
//			MultiLineMessageBox.doSetTemplate();
//			if (MultiLineMessageBox.show(msg, title, MultiLineMessageBox.YES | MultiLineMessageBox.NO, MultiLineMessageBox.QUESTION, true,
//					new EventListener() {
//						public void onEvent(Event evt) {
//							switch (((Integer) evt.getData()).intValue()) {
//							case MultiLineMessageBox.YES:
//								try {
//									doSave();
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
//							case MultiLineMessageBox.NO:
//								break; // 
//							}
//						}
//					}
//
//			) == MultiLineMessageBox.YES) {
//			}
//		}
		ordenCompraDialogWindow.onClose();

	}
	
//	public void RolUserCXP(TbDetalleComprobante orden_User) throws Exception {
//		
//
//		
//		System.out.println("Usuario = "+Usuario);
//		id_user = userserv.getIdByUserName(Usuario);
//		
//		TbUsuario anuser = userserv.getUserByUsername(Usuario);
//		
//		
//		
////		id_Corporativo = userserv.getIdByCorporativo(Usuario);
//		
//		
//		
//			
//			
////			id_pr_rol = userserv.getIdByPerfilRol(id_user);
////			
////			NameRol = userserv.getNameRol(id_pr_rol);
//			
////			restricciones.clear();
//			
//			if(NameRol.equals("Rol_Solicitante")){
//				int usuarioSesion;
//				int UsuarioOrden;
//				usuarioSesion = anuser.getIdUsuario();
//				UsuarioOrden = orden_User.getTbUsuario().getIdUsuario();
//				
//				System.out.println("usuarioSesion: "+ usuarioSesion);
//				System.out.println("UsuarioOrden: "+ UsuarioOrden);
//				if(usuarioSesion == UsuarioOrden){
//					if(orden_User.getNuautoriza() != null){
//					
//						if(orden_User.getNuautoriza() == 1 ){
//							if(orden_User.getIdUserAutorizaArea() !=null){
//								if(orden_User.getNuautorizaArea() != null){
//									if(orden_User.getNuautorizaArea() == 1){
//										row_ck_Valida.setVisible(true);
//									}
//								}else{
//									row_ck_Valida.setVisible(false);
//								}
//							}else{
//								row_ck_Valida.setVisible(false);
//							}
//	//						row_ck_Valida.setVisible(true);
//						}
//					}else{
//						row_ck_Valida.setVisible(false);
//					}
//				}else{
//					row_ck_Valida.setVisible(false);
//				}
//			}
//			if(NameRol.equals("Rol_Autorizador")){
//				int usuarioSesion;
//				int UsuarioOrden;
//				usuarioSesion = anuser.getIdUsuario();
//				UsuarioOrden = orden_User.getTbUsuario().getIdUsuario();
//				
//				System.out.println("usuarioSesion: "+ usuarioSesion);
//				System.out.println("UsuarioOrden: "+ UsuarioOrden);
//				if(usuarioSesion == UsuarioOrden){
//					if(orden_User.getNuautoriza() != null){
//					
//						if(orden_User.getNuautoriza() == 1 ){
//							if(orden_User.getIdUserAutorizaArea() !=null){
//								if(orden_User.getNuautorizaArea() != null){
//									if(orden_User.getNuautorizaArea() == 1){
//										row_ck_Valida.setVisible(true);
//									}
//								}else{
//									row_ck_Valida.setVisible(false);
//								}
//							}else{
//								row_ck_Valida.setVisible(false);
//							}
//							if(orden_User.getNuautoriza() == 1 && orden_User.getIdUserAutorizaArea() == null){
//								row_ck_Valida.setVisible(true);
//								
//							}
//						}
//					}else{
//						row_ck_Valida.setVisible(false);
//					}
//				}
//				else{
//					row_ck_Valida.setVisible(false);
//				}
//			}
////			if(NameRol.equals("Rol_Proveedor")){
////				
////				
////			}
//			if(NameRol.equals("Rol_Admin")){
//				int usuarioSesion;
//				int UsuarioOrden;
//				usuarioSesion = anuser.getIdUsuario();
//				UsuarioOrden = orden_User.getTbUsuario().getIdUsuario();
//				
//				System.out.println("usuarioSesion: "+ usuarioSesion);
//				System.out.println("UsuarioOrden: "+ UsuarioOrden);
//				
////				if(usuarioSesion == UsuarioOrden){
//					if(orden_User.getNuautoriza() != null){
//					
//						if(orden_User.getNuautoriza() == 1 ){
//							if(orden_User.getIdUserAutorizaArea() !=null){
//								if(orden_User.getNuautorizaArea() != null){
//									if(orden_User.getNuautorizaArea() == 1){
//										row_ck_Valida.setVisible(true);
//									}
//								}else{
//									row_ck_Valida.setVisible(false);
//								}
//							}else{
//								row_ck_Valida.setVisible(true);
//							}
//	//						row_ck_Valida.setVisible(true);
//						}
//					}else{
//						row_ck_Valida.setVisible(false);
//					}
////				}else{
////					row_ck_Valida.setVisible(false);
////				}
////				
//			}
//		
//			
//		
//			
//			
//			if(NameRol.equals("Rol_CxP") ){
//				int usuarioSesion;
//				int UsuarioOrden;
//				usuarioSesion = anuser.getIdUsuario();
//				UsuarioOrden = orden_User.getTbUsuario().getIdUsuario();
//				
//				System.out.println("usuarioSesion: "+ usuarioSesion);
//				System.out.println("UsuarioOrden: "+ UsuarioOrden);
//				if(orden_User.getNuautoriza() != null){
//				
//					if(orden_User.getNuautoriza() == 1 ){
//						if(orden_User.getIdUserAutorizaArea() !=null){
//							if(orden_User.getNuautorizaArea() != null){
//								if(orden_User.getNuautorizaArea() == 1){
//									row_ck_Valida.setVisible(true);
//								}
//							}else{
//								row_ck_Valida.setVisible(false);
//							}
//						}else{
//							row_ck_Valida.setVisible(true);
//						}
////						row_ck_Valida.setVisible(true);
//					}
//				}else{
//					row_ck_Valida.setVisible(false);
//				}
//			}
////			else{
////				row_ck_Valida.setVisible(false);
////			}
////			if(NameRol.equals("Rol_Soporte")){
//////				
////			}
//		
//		
//		
//		
//	
//	
//		
//		
//	}

	/**
	 * Writes the bean data to the components.<br>w
	 * 
	 * @param anUser
	 * @throws Exception 
	 */
//	public void doWriteBeanToComponents(TbComprobantes anOrden) throws Exception {
//		
//		
//		
//		
//		
//		
//		
//		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//		NumberFormat nf = NumberFormat.getInstance();
//		nf.setMaximumFractionDigits(2);
//		nf.setMinimumFractionDigits(2);
//		String alturaStr = "";
//		
//		TbProductoComprobante comprobante = new TbProductoComprobante();
////		TbProductoServicio producto = new TbProductoServicio();
//		
////		if(anOrden !=null){
////			if(anOrden.getIdDetalleComprobante()!=null)
////			{
////				listaDetalleFactura = ventaServ.getDetalleFactura(anOrden);
////			}
////		}
////		tb_ObservacionesFactura.setValue(anOrden.getObservaciones());
////		tb_bancoDialog.setValue(anOrden.getBanco());
////		tb_DocRPDialog.setValue(anOrden.getNumerocuenta());
//		
//		
//		if(anOrden !=null){
//			if(anOrden.getIdDetalleComprobante()!=null)
//			{
//				idOrden = anOrden.getIdDetalleComprobante();
//			}
//		}
//		
////		if(anOrden !=null){
////			if(anOrden.getIdProveedor()!=null)
////			{
////				TbRfcTbUsuario anRFCUser = new TbRfcTbUsuario();
////				TbRfc anrfc = new TbRfc();
////				TbUsuario proveedor = UserServ.getUserPojo(anOrden.getIdProveedor());
////				TbPersona personaProv = UserServ.getPersona(proveedor);
////				TbUsuario User_Creador = UserServ.getUserPojo(anOrden.getTbUsuario().getIdUsuario());
////				TbPersona persona_Creador = UserServ.getPersona(User_Creador);
////				anRFCUser = UserServ.gettbRfcUSer(id_corporativo, anOrden.getIdProveedor());
////				System.out.println(id_corporativo);
////				System.out.println( anOrden.getIdProveedor());
////				System.out.println(anRFCUser.getTbRfc().getIdRfc());
////				
////				anrfc = UserServ.getRFCPojo(anRFCUser.getTbRfc().getIdRfc());
////				
////				if(persona_Creador != null){
////					if(persona_Creador.getChEmail() != null){
////						Correo_Creador = persona_Creador.getChEmail();
////					}else{
////						Correo_Creador = Email_Sistema;
////					}
////					
////				}else{
////					Correo_Creador = Email_Sistema;
////				}
////				
////				
////				String nombreProve = "";
////				
////				if(personaProv != null){
////					if(personaProv.getChapPaterno() == null){
////						personaProv.setChapPaterno("");
////					}
////					if(personaProv.getChapMaterno() == null){
////						personaProv.setChapMaterno("");
////					}
////					
////					if(personaProv.getChNombre() == null || personaProv.getChNombre().equals("")){
////						if(personaProv.getChRazonSocial() == null){
////							personaProv.setChRazonSocial("");
////						}
////						nombreProve = personaProv.getChRazonSocial();
////					
////					}else{
////						
////						nombreProve = personaProv.getChNombre()+" "+personaProv.getChapPaterno()+" "+personaProv.getChapMaterno();
////					}
////				}
////				btn_Proveedor.setDisabled(true);
////				txt_CorreoProv.setValue(personaProv.getChEmail());
////				txt_Proveedor.setValue(nombreProve +"("+anrfc.getChRfc()+")");
////			}
////		}
//		
////		if(anOrden !=null){
////			if(anOrden.getIdUserAutoriza()!=null)
////			{
////				TbUsuario UserAuto = UserServ.getUserPojo(anOrden.getIdUserAutoriza());
////				TbPersona personaAuto = UserServ.getPersona(UserAuto);
////				String nombreAuto = "";
////				
////				if(personaAuto != null){
////					if(personaAuto.getChapPaterno() == null){
////						personaAuto.setChapPaterno("");
////					}
////					if(personaAuto.getChapMaterno() == null){
////						personaAuto.setChapMaterno("");
////					}
////					
////					if(personaAuto.getChNombre() == null || personaAuto.getChNombre().equals("")){
////						if(personaAuto.getChRazonSocial() == null){
////							personaAuto.setChRazonSocial("");
////						}
////						nombreAuto = personaAuto.getChRazonSocial();
////					
////					}else{
////						
////						nombreAuto = personaAuto.getChNombre()+" "+personaAuto.getChapPaterno()+" "+personaAuto.getChapMaterno();
////					}
////				}
////				txt_Autorizador.setValue(personaAuto.getChEmail());
////				btn_Autorizador.setDisabled(true);
////				txt_Autorizador.setValue(nombreAuto);
////			}
////		}
//		
////		if(anOrden !=null){
////			if(anOrden.getIdUserAutorizaArea()!=null)
////			{
////				TbUsuario UserAuto_Area = UserServ.getUserPojo(anOrden.getIdUserAutorizaArea());
////				TbPersona personaAuto_Area = UserServ.getPersona(UserAuto_Area);
////				String nombreAuto_Area = "";
////				
////				if(personaAuto_Area != null){
////					if(personaAuto_Area.getChapPaterno() == null){
////						personaAuto_Area.setChapPaterno("");
////					}
////					if(personaAuto_Area.getChapMaterno() == null){
////						personaAuto_Area.setChapMaterno("");
////					}
////					
////					if(personaAuto_Area.getChNombre() == null || personaAuto_Area.getChNombre().equals("")){
////						if(personaAuto_Area.getChRazonSocial() == null){
////							personaAuto_Area.setChRazonSocial("");
////						}
////						nombreAuto_Area = personaAuto_Area.getChRazonSocial();
////					
////					}else{
////						
////						nombreAuto_Area = personaAuto_Area.getChNombre()+" "+personaAuto_Area.getChapPaterno()+" "+personaAuto_Area.getChapMaterno();
////					}
////				}
////				txt_CorreoAutoArea.setValue(personaAuto_Area.getChEmail());
////				btn_Autorizador_Area.setDisabled(true);
////				id_row_Autoriza_area.setVisible(true);
////				txt_Autorizador_Area.setValue(nombreAuto_Area);
////			}
////		}
//		
//		if(anOrden !=null){
//			if(anOrden.getTotal()!=null)
//			{
//				tb_TotalFactura.setValue(anOrden.getTotal()+"");
//			}
//		}
//		
////		if(anOrden !=null){
////			if(anOrden.getSubtotalOrden()!=null)
////			{
////				tb_SubtotalFactura.setValue(anOrden.getSubtotalOrden()+"");
////			}
////		}
//		
////		if(anOrden !=null){
////			if(anOrden.getTotalLetraOrden()!=null)
////			{
////				tb_TotalLetraFactura.setValue(anOrden.getTotalLetraOrden()+"");
////			}
////		}
//		
////		if(anOrden !=null){
////			if(anOrden.getIvaOrden()!=null)
////			{
////				tb_IvaFactura.setValue(anOrden.getIvaOrden()+"");
////			}
////		}
//		
////		if(anOrden !=null){
////			if(anOrden.getRetOrden()!=null)
////			{
////				label_RetIva.setVisible(true);
////				tb_RetIvaFactura.setVisible(true);
////				ch_Retenciones.setChecked(true);
////				tb_RetIvaFactura.setValue(anOrden.getRetOrden()+"");
////				ch_Retenciones.setDisabled(true);
////			}
////			
////		}
//		
////		tb_Identi_OC.setValue(anOrden.getIdent_oc());
////		bd_FechaEntrega.setValue(anOrden.getFechaEntrega());
////		bd_FechaMax.setValue(anOrden.getFechaMaxEntre());
////		
////		ListModelList lml = null;
////		if(anOrden !=null){
////			if(anOrden.getIva()!=null)
////			{
////				lml = (ListModelList) cb_Iva.getListModel();
////				ComboFactory comboFactory = new ComboFactory(Integer.parseInt(anOrden.getIva()), "");
//////				System.out.println("Combo Area: "+ anOrden.getIva() + " - "+ comboFactory.getNombre());
////				cb_Iva.setSelectedIndex(lml.indexOf(comboFactory));
////				
////				Listitem itemMoneda = cb_Iva.getSelectedItem();
////				String nombre = itemMoneda.getLabel();
////
////
////				cb_tasa.setModel(new ListModelList(ventaServ.getTasa(id_corporativo,nombre)));
////				cb_tasa.setItemRenderer(new ComboFactoryModelItemRenderer());
////			} 
////		}
////		
////		
////		
////		if(anOrden !=null){
////			if(anOrden.getTasa()!=null)
////			{
////				lml = (ListModelList) cb_tasa.getListModel();
////				String tasa =anOrden.getTasa();
////				System.out.println("tasa1: "+tasa);
////				if(tasa.indexOf(".0000") == -1){
////					
////					
////					
////				}else{
////					tasa = tasa.charAt(0)+"";
////					System.out.println("Entra: "+tasa);
////				}
//////				
////				System.out.println("tasa: "+tasa);
////				ComboFactory comboFactory = new ComboFactory(Integer.parseInt(tasa), "");
//////				System.out.println("Combo Area: "+ anOrden.getIva() + " - "+ comboFactory.getNombre());
////				cb_tasa.setSelectedIndex(lml.indexOf(comboFactory));
////			} 
////		}
////		
////		if(anOrden !=null){
////			if(anOrden.getMoneda()!=null)
////			{
////				lml = (ListModelList) cb_Moneda.getListModel();
////				ComboFactory comboFactory = new ComboFactory(Integer.parseInt(anOrden.getMoneda()), "");
//////				System.out.println("Combo Area: "+ anOrden.getIva() + " - "+ comboFactory.getNombre());
////				cb_Moneda.setSelectedIndex(lml.indexOf(comboFactory));
////			} 
////		}
////		
////		if(anOrden !=null){
////			if(anOrden.getIdUserAutoriza() !=null){
////				if(anOrden.getNuautoriza() !=null){
////					if(anOrden.getNuautoriza() == 1){
////						Radio_autoriza.setChecked(true);
////						Radio_no_autoriza.setChecked(false);
////						Radio_autoriza.setDisabled(true);
////						Radio_no_autoriza.setDisabled(true);
////					}else{
////						Radio_autoriza.setChecked(false);
////						Radio_no_autoriza.setChecked(true);
////						Radio_autoriza.setDisabled(true);
////						Radio_no_autoriza.setDisabled(true);
////					}
////				}else{
////					Radio_autoriza.setDisabled(false);
////					Radio_no_autoriza.setDisabled(false);
////				}
////				
////			}
////		}
////		
////		if(anOrden !=null){
////			if(anOrden.getIdUserAutorizaArea() !=null){
////				System.out.println(anOrden.getNuautorizaArea());
////				if(anOrden.getNuautorizaArea() !=null){
////					if(anOrden.getNuautorizaArea() == 1){
////						Radio_autoriza_Area.setChecked(true);
////						Radio_no_autoriza_Area.setChecked(false);
////						Radio_autoriza_Area.setDisabled(true);
////						Radio_no_autoriza_Area.setDisabled(true);
////					}else{
////						Radio_autoriza_Area.setChecked(false);
////						Radio_no_autoriza_Area.setChecked(true);
////						Radio_autoriza_Area.setDisabled(true);
////						Radio_no_autoriza_Area.setDisabled(true);
////					}
////				}else{
////					Radio_autoriza_Area.setDisabled(false);
////					Radio_no_autoriza_Area.setDisabled(false);
////				}
//////				
////			}else{
////				Radio_autoriza_Area.setDisabled(true);
////				Radio_no_autoriza_Area.setDisabled(true);
////			}
////		}
////		
////		if(anOrden !=null){
////			if(anOrden.getCc()!=null)
////			{
////				lml = (ListModelList) lb_cc.getListModel();
////				ComboFactory comboFactory = new ComboFactory(Integer.parseInt(anOrden.getCc()), "");
//////				System.out.println("Combo Area: "+ anOrden.getIva() + " - "+ comboFactory.getNombre());
////				lb_cc.setSelectedIndex(lml.indexOf(comboFactory));
////			} 
////		}
////		int id_Cuenta = 0;
////		
////		if(anOrden !=null){
////			if(anOrden.getCuenta()!=null)
////			{
////				lml = (ListModelList) lb_Cuentas.getListModel();
////				ComboFactory comboFactory = new ComboFactory(Integer.parseInt(anOrden.getCuenta()), "");
////				System.out.println("Combo Area: "+ anOrden.getCuenta() + " - "+ comboFactory.getId());
////				id_Cuenta = comboFactory.getId();
////				lb_Cuentas.setSelectedIndex(lml.indexOf(comboFactory));
////			} 
////		}
//		
////		if(id_Cuenta >0){
////		
////				TbCuenta obcuenta = ventaServ.getCuentaTb(id_Cuenta);
////				
////		
////		
////				if(obcuenta.getOrden() == 1){
////					lb_Ordenes.setModel(new ListModelList(ventaServ.getOrdenes(id_corporativo)));
////					lb_Ordenes.setItemRenderer(new ComboFactoryModelItemRenderer());
////					
////					row_ordenes.setVisible(true);
////					
////				}else{
////					row_ordenes.setVisible(false);
////				}
////				
////				if(obcuenta.getDepartamento() == 1){
////					
////					lb_Departamento.setModel(new ListModelList(ventaServ.getPerfilOc()));
////					lb_Departamento.setItemRenderer(new ComboFactoryModelItemRenderer());
////					
////					row_Depa.setVisible(true);
////				}else{
////					row_Depa.setVisible(false);
////				}
////		}
//		
////		if(anOrden !=null){
////			if(anOrden.getOrden()!=null)
////			{
////				lml = (ListModelList) lb_Ordenes.getListModel();
////				ComboFactory comboFactory = new ComboFactory(Integer.parseInt(anOrden.getOrden()), "");
////				System.out.println("Combo Area: "+ anOrden.getCuenta() + " - "+ comboFactory.getId());
////				lb_Ordenes.setSelectedIndex(lml.indexOf(comboFactory));
////			} 
////		}
////		
////
////		
////		if(anOrden !=null){
////			if(anOrden.getDepartamento()!=null)
////			{
////				lml = (ListModelList) lb_Departamento.getListModel();
////				ComboFactory comboFactory = new ComboFactory(Integer.parseInt(anOrden.getDepartamento()), "");
//////				System.out.println("Combo Area: "+ anOrden.getIva() + " - "+ comboFactory.getNombre());
////				lb_Departamento.setSelectedIndex(lml.indexOf(comboFactory));
////			} 
////		}
////		
////		if(anOrden !=null){
////			if(anOrden.getMetododepago()!=null)
////			{
////				lml = (ListModelList) lb_MetodoPagoFactDialog.getListModel();
////				ComboFactory comboFactory = new ComboFactory(Integer.parseInt(anOrden.getMetododepago()), "");
//////				System.out.println("Combo Area: "+ anOrden.getIva() + " - "+ comboFactory.getNombre());
////				lb_MetodoPagoFactDialog.setSelectedIndex(lml.indexOf(comboFactory));
////			} 
////		}
////		
////		if(anOrden !=null){
////			if(anOrden.getFormadePago()!=null)
////			{
////				lml = (ListModelList) lb_FormaPagoFactDialog.getListModel();
////				ComboFactory comboFactory = new ComboFactory(Integer.parseInt(anOrden.getFormadePago()), "");
//////				System.out.println("Combo Area: "+ anOrden.getIva() + " - "+ comboFactory.getNombre());
////				lb_FormaPagoFactDialog.setSelectedIndex(lml.indexOf(comboFactory));
////			} 
////		}
////		
////		if(anOrden !=null){
////			if(anOrden.getUso_Cfdi()!=null)
////			{
////				lml = (ListModelList) lb_UsoDialog.getListModel();
////				ComboFactory comboFactory = new ComboFactory(anOrden.getUso_Cfdi(), "");
//////				System.out.println("Combo Area: "+ anOrden.getIva() + " - "+ comboFactory.getNombre());
////				lb_UsoDialog.setSelectedIndex(lml.indexOf(comboFactory));
////			} 
////		}
////		
////		if(anOrden !=null){
////			if(anOrden.getDocumento1()!=null)
////			{
////				bt_upload1.setDisabled(true);
////				txt_Documento1.setValue("");
////				btn_download_Cotiza.setVisible(true);
////				
////			} 
//////			bt_upload1.setDisabled(true);
////		}
////		if(anOrden !=null){
////			if(anOrden.getDocumento2()!=null)
////			{
////				bt_upload2.setDisabled(true);
////				txt_Documento2.setValue("");
////				btn_download_2.setVisible(true);
////			} 
////			
////		}
////		if(anOrden !=null){
////			if(anOrden.getDocumento3()!=null)
////			{
////				bt_upload3.setDisabled(true);
////				txt_Documento3.setValue("");
////				btn_download_3.setVisible(true);
////			}
////			
////		}
////		
////		if(anOrden !=null){
////			if(anOrden.getTbUsuario() !=null){
////				if(anOrden.getTbUsuario().getIdUsuario() !=null){
////				idUsuarios = anOrden.getTbUsuario().getIdUsuario();
////				}
////			}
////			
////		}
////		
////				
////		boolean comprobanteOk = false;
////		
////		if(anOrden != null){
////			if(anOrden.getIdDetalleComprobante() != null){
////
////				comprobanteOk = comp_Serv.existeComprobante(anOrden.getIdDetalleComprobante());
////			}
////		}
////		
////		if(comprobanteOk){
////			if(anOrden != null){
////				if(anOrden.getNuautorizadoCxP() != null){
////					if(anOrden.getNuautorizadoCxP() == 0 ){
////						if(anOrden.getTbUsuario() != null){
////							RolUserCXP(anOrden);
////							MemoryDetalleComp = anOrden;
////						}
////						
////					}else{
////						if(anOrden.getNuautorizadoCxP() == 1){
////							row_ck_Valida.setVisible(true);
////							btn_Guarda_ValidaOC.setDisabled(true);
////							Goog_ok.setVisible(true);
////							Good_Dont.setVisible(true);
////							btn_Guarda_ValidaOC.setDisabled(true);
////							Goog_ok.setChecked(true);
////							Goog_ok.setDisabled(true);
////							Good_Dont.setDisabled(true);
////							tb_Good_Rechazo.setDisabled(true);
////							tb_Good_Rechazo.setVisible(false);
////							label_Good_Rechazo.setVisible(false);
////						}
////						if(anOrden.getNuautorizadoCxP() == 2){
////							row_ck_Valida.setVisible(true);
////							btn_Guarda_ValidaOC.setDisabled(false);
////							Goog_ok.setVisible(true);
////							Good_Dont.setVisible(true);
////							btn_Guarda_ValidaOC.setDisabled(true);
////							Good_Dont.setChecked(true);
////							Goog_ok.setDisabled(true);
////							Good_Dont.setDisabled(true);
////							tb_Good_Rechazo.setValue(anOrden.getTexto_good());
////							tb_Good_Rechazo.setDisabled(true);
////							tb_Good_Rechazo.setVisible(true);
////							label_Good_Rechazo.setVisible(true);
////						}
////						
////					}
////					
////				}
////			}
////		}
////		
////		// Se llena el detalle
////		if (listaDetalleFactura.size() > 0) {
////
////			for (TbProductoComprobante cto_factura : listaDetalleFactura) {
////
////				if(cto_factura.getCantidad() !=null){
////					listBoxDetalleFactura.setHeight(50 + "px");
////					// Se obtiene el producto del detalle
////	//				producto = (TbProductoServicio) ventaServ.getBusinessObjectPorId(cto_factura.getTbProductoServicio().getIdProductoServicio(),TbProductoServicio.class, "idProductoServicio");
////					Listitem item = new Listitem();
////					item.setParent(listBoxDetalleFactura);
////					// Se pone en blanco ya que el la primera columna es donde esta
////					// el check
////					Listcell lc;
////					lc = new Listcell("");
////					lc.setParent(item);
////	
////					// Se pone la columna donde va el intbox de la cantidad
////					lc = new Listcell();
////					Doublebox ib_cantidad = new Doublebox();
////					ib_cantidad.setReadonly(true);
////					ib_cantidad.setWidth("45px");
////					ib_cantidad.setMaxlength(8);
////					ib_cantidad.setValue(cto_factura.getCantidad());
////					ib_cantidad.setStyle("text-align:right");
////					lc.appendChild(ib_cantidad);
////					lc.setParent(item);
////	
////					// Se pone valor a la columna clave
////					lc = new Listcell(cto_factura.getClave());
////					lc.setParent(item);
////	
////					// Se pone valor a la columna descripcion
////					lc = new Listcell(cto_factura.getDescripcion());
////					lc.setParent(item);
////					
////					// Se pone valor a la columna descripcion
////					lc = new Listcell(cto_factura.getJustificacion());
////					lc.setParent(item);
////	
////					// Se pone valor a la columna unidad de salida
////					lc = new Listcell(cto_factura.getUnidad());
////					lc.setParent(item);
////					
//////					if(cto_factura !=null){
//////						if(cto_factura.getUnidad()!=null)
//////						{
//////							lc = new Listcell("");
//////	//						lc.setId("unidad_"+numero);
//////							//lc.setParent(item);
//////							Listbox ib_unidad = new Listbox();
//////							
//////	//						ib_unidad.setWidth("130px");
//////	//						ib_unidad.setHeight("20px");
//////							ib_unidad.setModel(new ListModelList(ventaServ.getUnidad()));
//////							ib_unidad.setItemRenderer(new ComboFactoryModelItemRenderer());
//////							
//////							ib_unidad.setMold("select");
//////							lml = (ListModelList) ib_unidad.getListModel();
//////							ComboFactory comboFactory = new ComboFactory(Integer.parseInt(cto_factura.getUnidad().trim()), "");
//////	//						System.out.println("Combo Area: "+ cto_factura.getUnidad().trim() + " - "+ comboFactory.getId());
//////	//						id_Cuenta = comboFactory.getId();
//////							ib_unidad.setSelectedIndex(lml.indexOf(comboFactory));
//////							ib_unidad.setDisabled(true);
//////	//						ib_unidad.setStyle("text-align:right");
//////	//						ComponentsCtrl.applyForward(ib_unidad, "onFocus=onFocusUnidad");
//////	//						ComponentsCtrl.applyForward(ib_unidad, "onBlur=onBlurUnidad");
//////							lc.appendChild(ib_unidad);
//////							lc.setParent(item);
//////							
//////							
//////							
//////							
//////						} 
//////					}
////	
////					// se pone el precio unitario
////					lc = new Listcell(nf.format(cto_factura.getImporte()));
////					lc.setParent(item);
////	
////					// El importe se pone en blanco ya que se calcula depues
////					lc = new Listcell(nf.format(cto_factura.getCantidad()*cto_factura.getImporte()));
////					lc.setParent(item);
////	
////					// Se pone el intbox para el llenado de entregados
////					lc = new Listcell(nf.format(cto_factura.getCantidad()*cto_factura.getImporte()));
////					lc.setParent(item);
////	
////					// Se pone el id del producto
////					lc = new Listcell(cto_factura.getIdProductoComprobante()+"");
////					lc.setParent(item);
////	
////					// Se pone si el producto tiene iva o no 1 Ã³ 0
////					// TbLineaProducto linea = (TbLineaProducto)
////					// getVentaService().getBusinessObjectPorId(proveedorProducto.getTbLineaProducto().getNukidLineaProducto(),
////					// TbLineaProducto.class, "nukidLineaProducto");
////					lc = new Listcell("1");
////					lc.setParent(item);
////	
////					// Se pone el id de nivel de precio
////					lc = new Listcell("3");
////					lc.setParent(item);
////	
////					// Se pone el id del tipo de cliente
////					lc = new Listcell("5");
////					lc.setParent(item);
////	
////					// Se pone extra
////					lc = new Listcell("");
////					lc.setParent(item);
////	
////					// Se pone referencia
////					lc = new Listcell("");
////					lc.setParent(item);
////	
////					// Se pone PorcentajeIva
////					lc = new Listcell("");
////					lc.setParent(item);
////	
////					// Se pone el unitario con iva
////					lc = new Listcell("");
////					lc.setParent(item);
////	
////					// Se pone el total
////					lc = new Listcell("");
////					lc.setParent(item);
////					
////	//				cto_factura.getImpuestos()/100
////	
////					// Se pone el iva unitario
////					String ivaUni = "";
////					if (cto_factura.getImpuestos() != null) {
////						ivaUni = nf.format(cto_factura.getImporte() * cto_factura.getImpuestos()/100);
////					}
////					lc = new Listcell(ivaUni);
////					lc.setParent(item);
////	
////					// Se pone el iva total
////					String ivaTot = "";
////	//				if (anOrden != null) {
////	//					if (anOrden != null) {
////	//						ivaTot = nf.format(anOrden.getTotal()* cto_factura.getImpuestos()/100);
////	//					}
////	//				}
////					lc = new Listcell("");
////					lc.setParent(item);
////	
////					alturaStr = listBoxDetalleFactura.getHeight();
////					int altura = 0;
////					if (alturaStr.indexOf("px") > -1) {
////						alturaStr = alturaStr.substring(0, alturaStr.indexOf("px"));
////						altura = new Integer(alturaStr) + 50;
////					}
////					altura = altura +50;
////					listBoxDetalleFactura.setHeight(altura + "px");
////				}
////			}
////
////		}
////		
////		
////		setL_obComprobanteDetalle(anOrden);
//		return;
//	}

	/**
	 * Opens the Dialog window modal.
	 * 
	 * It checks if the dialog opens with a new or existing object and set the
	 * readOnly mode accordingly.
	 * 
	 * @param anUser
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-access")
//	public void doShowDialog(TbComprobantes anUser, Integer modalito) throws InterruptedException {
//		
////		TbRfc tb_partners = new TbRfc();
//
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> doShowDialog");
//		}
//		
//		
//		// if aUser == null then we opened the Dialog without
//		// args for a given entity, so we get a new Obj().
////		if (anUser == null) {
////			anUser = new TbComprobantes();
//////			setL_obComprobanteDetalle(new TbDetalleComprobante());
////
////		System.out.println("Biene Null el Usuario");
////
////		btn_Actualizar.setVisible(false);
////		btn_Modificar.setVisible(false);
////		button_PrintCFD.setVisible(false);
////		
////		}
////		else
////		{
////			System.out.println("Biene lleno el usuario");
////			
////
////			btn_Actualizar.setVisible(false);
////			btn_Guardar.setVisible(false);	
////			button_PrintCFD.setVisible(true);
////			doReadOnly();
////		}
//
//		try {
////			System.out.println("Valor del Autorizador Area: "+ anUser.getNuautorizaArea());
//			// fill the components with the data
//			doWriteBeanToComponents(anUser);
//
//			// stores the inital data for comparing if they are changed
//			// during user action.
//			doStoreInitValues();
//			
//			if(modalito == 1){
//				ordenCompraDialogWindow.doModal(); // open the dialog in modal mode
//			}
//
//			
//		} catch (Exception e) {
//			System.out.println("Error: "+ e);
//			Messagebox.show(e.toString(), "!Error!", org.zkoss.zul.Messagebox.OK, "");
//			
//		}
//	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++ helpers ++++++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Set the properties of the fields, like maxLength.<br>
	 */
	private void doSetFieldProperties() {

//		txt_UserName.setMaxlength(20); 
//		txt_RFC.setMaxlength(14); 
//		txt_nombre.setMaxlength(40); 
//		txt_mail.setMaxlength(60); 


	}
	

	private void parametrosIni() throws InterruptedException {
		
//		gb_Factura.setOpen(false);
//		gb_Factura_02_Relac.setOpen(false);
//		gb_Factura_04_Emi.setOpen(false);
//		gb_Factura_05_Receptor.setOpen(false);
//		gb_Factura_06_concep.setOpen(false);
//		gb_Factura_07_Imp_Tras.setOpen(false);
//		gb_Factura_08_Imp_Rete.setOpen(false);
//		gb_Factura_09_Info_Aduana.setOpen(false);
//		gb_Factura_10_Predial.setOpen(false);
////		gb_Factura_11_Compl_Part.setOpen(false);
//		gb_Factura_12_Tot_Imp.setOpen(false);
//		gb_Factura_13_Imp_Rete.setOpen(false);
//		gb_Factura_14_Imp_Traslado.setOpen(false);
		
//		txt_Factura_verzion.setValue("3.3");
//		txt_Factura_verzion.setDisabled(true);
//		
//		txt_Factura_Subtotal.setDisabled(true);
//		txt_Factura_Total.setDisabled(true);
//		
//		txt_Factura_Descuento.setDisabled(true);
		
//		System.out.println("Valor = "+ getcorp.getLugarExpedicion());
//		txt_Factura_Lugar_Expedi.setValue(getcorp.getLugarExpedicion());
//		txt_Factura_Lugar_Expedi.setDisabled(true);
//		txt_Factura_Emi_RFC.setValue(getcorp.getRfc());
//		txt_Factura_Emi_RFC.setDisabled(true);
//		txt_Factura_Emi_Nombre.setValue(getcorp.getChnombre());
//		txt_Factura_Emi_Nombre.setDisabled(true);
//		txt_Factura_Emi_Regimen.setValue(getcorp.getRegimenFiscal());
//		txt_Factura_Emi_Regimen.setDisabled(true);
		
//		txt_Factura_Conceptos_Importe.setDisabled(true);
//		
//		txt_Factura_Conceptos_ClaveUni.setDisabled(true);
//		
//		txt_Factura_Imp_Tras_Importe.setDisabled(true);
//		
//		txt_Factura_Imp_Ret_Importe.setDisabled(true);
//		
//		txt_Factura_Total_Rete.setDisabled(true);
//		txt_Factura_Total_Trasl.setDisabled(true);
//
//		txt_Factura_13_Impuesto_Rete.setDisabled(true);
//		txt_Factura_13_Importe_Rete.setDisabled(true);
//
//		txt_Factura_14_Impuesto_Traslado.setDisabled(true);
//		txt_Factura_14_Factor_Traslado.setDisabled(true);
//		txt_Factura_14_Tasa_Traslado.setDisabled(true);
//		txt_Factura_14_Importe_Traslado.setDisabled(true);
//		
//		
//		//Carta Porte Version
//		txt_CatPort_00_Version.setValue("2.0");
//		txt_CatPort_00_Version.setDisabled(true);
//		
//		txt_CatPort_02_Ubi_Ori_NumEstacion.setDisabled(true);
//		
//		txt_CatPort_02_Ubi_Ori_NombreEstacion.setDisabled(true);
//		
//		txt_CatPort_03_Ubi_Dest_NumEstacion.setDisabled(true);
//		
//		txt_CatPort_03_Ubi_Dest_NombreEstacion.setDisabled(true);
//		
//		txt_CatPort_05_Merca_Gral_UnidadPeso.setDisabled(true);
//		
//		txt_CatPort_06_Merca_ClaveSTCC.setDisabled(true);
//		
//		txt_CatPort_06_Merca_Descripcion.setDisabled(true);
//		
//		txt_CatPort_06_Merca_ClaveUnidad.setDisabled(true);
//		
////		txt_CatPort_06_Merca_Unidad.setDisabled(true);
//		
//		txt_CatPort_06_Merca_CveMaterialPeligroso.setDisabled(true);
//		
//		txt_CatPort_06_Merca_Embalaje.setDisabled(true);
//		
//		txt_CatPort_06_Merca_DescripEmbalaje.setDisabled(true);
//		
//		txt_CatPort_08_Merca_DetaMerc_UnidadPeso.setDisabled(true);
//		
//		txt_CatPort_09_Merca_AutoTransF_PermSCT.setDisabled(true);
//		
//		txt_CatPort_12_Merca_TransMaritimo_PermSCT.setDisabled(true);
//		
//		txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion.setDisabled(true);
//		
//		txt_CatPort_12_Merca_TransMaritimo_TipoCarga.setDisabled(true);
//				
//		txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular.setDisabled(true);
//		
//		txt_CatPort_11_Merca_AutoTransF_Remolque_SubTipoRem.setDisabled(true);
//		
//		txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero.setDisabled(true);
//
//		txt_CatPort_14_Merca_TransAereo_PermSCT.setDisabled(true);
//		
//		txt_CatPort_14_Merca_TransAereo_CodigoTransportista.setDisabled(true);
//		
//		txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso.setDisabled(true);
//		
//		//Carta porte Ubicacion Fecha
////		dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.setDisabled(true);
//		
//			gb_add_Farms.setOpen(false);
//			gb_Add_Farms_00.setOpen(false);
//			gb_Add_Farms_01.setOpen(false);
//			gb_Add_Farms_02.setOpen(false);
//			gb_Add_Farms_03.setOpen(false);
//			gb_Add_Farms_04.setOpen(false);
//			gb_Add_Farms_05.setOpen(false);
//			gb_Add_Farms_06.setOpen(false);
//			gb_Add_Farms_07.setOpen(false);
//			gb_Add_Farms_08.setOpen(false);
//			gb_Add_Farms_09.setOpen(false);
//			gb_Add_Farms_10.setOpen(false);
//			gb_Add_Farms_11.setOpen(false);
//			gb_Add_Farms_12.setOpen(false);
//			gb_Add_Farms_13.setOpen(false);
//			gb_Add_Farms_14.setOpen(false);
//			gb_Add_Farms_15.setOpen(false);
//			gb_Comple_CP.setOpen(false);
//			gb_Comple_CP.setVisible(false);
//			gb_00_CP.setOpen(false);
//			gb_01_CP.setOpen(false);
//			gb_02_CP.setOpen(false);
//			gb_03_CP.setOpen(false);
//			gb_04_4_CP.setOpen(false);
//			gb_04_CP.setOpen(false);
//			gb_05_CP.setOpen(false);
//			gb_06_CP.setOpen(false);
//			gb_07_CP.setOpen(false);
//			gb_08_CP.setOpen(false);
//			gb_09_CP.setOpen(false);
//			gb_10_CP.setOpen(false);
//			gb_11_CP.setOpen(false);
//			gb_12_CP.setOpen(false);
//			gb_13_CP.setOpen(false);
//			gb_14_CP.setOpen(false);
//			gb_15_CP.setOpen(false);
//			gb_16_CP.setOpen(false);
//			gb_17_CP.setOpen(false);
//			gb_18_CP.setOpen(false);
//			gb_19_CP.setOpen(false);
//			gb_20_CP.setOpen(false);
//			gb_21_CP.setOpen(false);
//			gb_22_CP.setOpen(false);
//			gb_23_CP.setOpen(false);
//			gb_24_CP.setOpen(false);
//			gb_25_CP.setOpen(false);
//			gb_26_CP.setOpen(false);
//			gb_27_CP.setOpen(false);
//		gb_Comple_Pagos.setVisible(false);
//		gb_Comple_Pagos.setOpen(false);
//			gb_01_Pagos.setOpen(false);
//			gb_02_Pagos.setOpen(false);
////			gb_03_Pagos.setOpen(false);
////			gb_04_Pagos.setOpen(false);
////			gb_05_Pagos.setOpen(false);
//			
//			txt_Ext_00_Version.setValue("1.1");
//			txt_Ext_00_Version.setDisabled(true);
//			
//			txt_Ext_00_TipoOperacion.setValue("2");
//			txt_Ext_00_TipoOperacion.setDisabled(true);
//			
//			txt_Ext_00_ClaveDePedimento.setValue("A1");
//			txt_Ext_00_ClaveDePedimento.setDisabled(true);
//			
//		gb_Comple_Comer_Ext.setVisible(false);
//		gb_Comple_Comer_Ext.setOpen(false);
//			gb_Comer_Exte_00.setOpen(false);
//			gb_Comer_Exte_01.setOpen(false);
//			gb_Comer_Exte_02.setOpen(false);
//			gb_Comer_Exte_03.setOpen(false);
//			gb_Comer_Exte_04.setOpen(false);
//			gb_Comer_Exte_06.setOpen(false);
//			gb_Comer_Exte_07.setOpen(false);
//			gb_Comer_Exte_08.setOpen(false);
//			gb_Comer_Exte_09.setOpen(false);
//			gb_Comer_Exte_10.setOpen(false);
//			
//			txt_Factura_Serie.setMaxlength(25);
//			txt_Factura_Folio.setMaxlength(40);
//			
//			txt_Factura_RElacion_UUID.setMaxlength(36);
//			
//			lb_CatPort_00_EntradaSalidaMerc.setVisible(false);
//			lb_CatPort_00_ViaEntradaSalida.setVisible(false);
//			
//			dbb_CatPort_00_TotalDistRec.setMaxlength(8);
//			txt_CatPort_01_DistanciaRecorrida.setMaxlength(8);
//			txt_CatPort_02_Ubi_Ori_IDOrigen.setMaxlength(8);
//			
//			txt_CatPort_02_Ubi_Ori_RFCRemitente.setMaxlength(13);
//			
//			txt_CatPort_02_Ubi_Ori_NombreRemitente.setMaxlength(254);
//			txt_CatPort_02_Ubi_Ori_NumRegIdTrib.setMaxlength(40);
//			
//			txt_CatPort_03_Ubi_Dest_NombreEstacion.setMaxlength(50);
//			
//			txt_CatPort_04_4_Ubi_Direc_Calle.setMaxlength(100);
//			txt_CatPort_04_4_Ubi_Direc_NumeroExterior.setMaxlength(55);
//			txt_CatPort_04_4_Ubi_Direc_NumeroInterior.setMaxlength(55);
//			txt_CatPort_04_4_Ubi_Direc_Colonia.setMaxlength(120);
//			txt_CatPort_04_4_Ubi_Direc_Localidad.setMaxlength(120);
//			txt_CatPort_04_4_Ubi_Direc_Referencia.setMaxlength(250);
//			txt_CatPort_04_4_Ubi_Direc_Municipio.setMaxlength(120);
//			txt_CatPort_04_4_Ubi_Direc_Estado.setMaxlength(30);
//			txt_CatPort_04_4_Ubi_Direc_Pais.setMaxlength(3);
//			txt_CatPort_04_4_Ubi_Direc_CodigoPostal.setMaxlength(12);
//			
//			txt_CatPort_03_Ubi_Dest_IDDestino.setMaxlength(8);
//			txt_CatPort_03_Ubi_Dest_RFCDestinatario.setMaxlength(13);
//			txt_CatPort_03_Ubi_Dest_NombreDestinatario.setMaxlength(254);
//			txt_CatPort_03_Ubi_Dest_NumRegIdTrib.setMaxlength(40);
//			txt_CatPort_03_Ubi_Dest_NombreEstacion.setMaxlength(50);
//			
//			txt_CatPort_04_Ubi_Direc_Calle.setMaxlength(100);
//			txt_CatPort_04_Ubi_Direc_NumeroExterior.setMaxlength(55);
//			txt_CatPort_04_Ubi_Direc_NumeroInterior.setMaxlength(55);
//			txt_CatPort_04_Ubi_Direc_Colonia.setMaxlength(120);
//			txt_CatPort_04_Ubi_Direc_Localidad.setMaxlength(120);
//			txt_CatPort_04_Ubi_Direc_Referencia.setMaxlength(250);
//			txt_CatPort_04_Ubi_Direc_Municipio.setMaxlength(120);
//			txt_CatPort_04_Ubi_Direc_Estado.setMaxlength(30);
//			txt_CatPort_04_Ubi_Direc_Pais.setMaxlength(3);
//			txt_CatPort_04_Ubi_Direc_CodigoPostal.setMaxlength(12);
//			
//			txt_CatPort_05_Merca_Gral_PesoBrutoTotal.setMaxlength(30);
//			txt_CatPort_05_Merca_Gral_PesoNetoTotal.setMaxlength(30);
//			txt_CatPort_05_Merca_Gral_NumTotalMercancias.setMaxlength(20);
//			txt_CatPort_05_Merca_Gral_CargoPorTasacion.setMaxlength(15);
//			
//			txt_CatPort_06_Merca_BienesTransp.setMaxlength(100);
//			txt_CatPort_06_Merca_Descripcion.setMaxlength(1000);
//			txt_CatPort_06_Merca_Cantidad.setMaxlength(20);
//			txt_CatPort_06_Merca_ClaveUnidad.setMaxlength(5);
//			txt_CatPort_06_Merca_Unidad.setMaxlength(20);
//			txt_CatPort_06_Merca_Dimensiones.setMaxlength(100);
//			txt_CatPort_06_Merca_DescripEmbalaje.setMaxlength(100);
//			txt_CatPort_06_Merca_PesoEnKg.setMaxlength(50);
//			txt_CatPort_06_Merca_ValorMercancia.setMaxlength(100);
//			txt_CatPort_06_Merca_Moneda.setMaxlength(5);
//			txt_CatPort_06_Merca_FraccionArancelaria.setMaxlength(50);
//			txt_CatPort_06_Merca_UUIDComercioExt.setMaxlength(36);
//			
//			txt_CatPort_07_Merca_CantTrans_Cantidad.setMaxlength(20);
//			txt_CatPort_07_Merca_CantTrans_IDOrigen.setMaxlength(8);
//			txt_CatPort_07_Merca_CantTrans_IDDestino.setMaxlength(8);
//			
//			txt_CatPort_08_Merca_DetaMerc_PesoBruto.setMaxlength(20);
//			txt_CatPort_08_Merca_DetaMerc_PesoNeto.setMaxlength(20);
//			txt_CatPort_08_Merca_DetaMerc_PesoTara.setMaxlength(20);
//			txt_CatPort_08_Merca_DetaMerc_NumPiezas.setMaxlength(20);
//			
//			txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT.setMaxlength(50);
//			txt_CatPort_09_Merca_AutoTransF_NombreAseg.setMaxlength(50);
//			txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro.setMaxlength(30);
//			
//			txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM.setMaxlength(7);
//			txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM.setMaxlength(4);
//			
//			txt_CatPort_11_Merca_AutoTransF_Remolque_Placa.setMaxlength(7);
//			
//			txt_CatPort_12_Merca_TransMaritimo_NumPermisoSCT.setMaxlength(30);
//			txt_CatPort_12_Merca_TransMaritimo_NombreAseg.setMaxlength(50);
//			txt_CatPort_12_Merca_TransMaritimo_NumPolizaSeguro.setMaxlength(30);
//			txt_CatPort_12_Merca_TransMaritimo_Matricula.setMaxlength(30);
//			txt_CatPort_12_Merca_TransMaritimo_NumeroOMI.setMaxlength(7);
//			txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion.setMaxlength(4);
//			txt_CatPort_12_Merca_TransMaritimo_NombreEmbarc.setMaxlength(50);
//			txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc.setMaxlength(3);
//			txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto.setMaxlength(20);
//			txt_CatPort_12_Merca_TransMaritimo_NumCertITC.setMaxlength(20);
//			txt_CatPort_12_Merca_TransMaritimo_Eslora.setMaxlength(20);
//			txt_CatPort_12_Merca_TransMaritimo_Manga.setMaxlength(20);
//			txt_CatPort_12_Merca_TransMaritimo_Calado.setMaxlength(20);
//			txt_CatPort_12_Merca_TransMaritimo_LineaNaviera.setMaxlength(50);
//			txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero.setMaxlength(100);
//			txt_CatPort_12_Merca_TransMaritimo_NumViaje.setMaxlength(30);
//			txt_CatPort_12_Merca_TransMaritimo_NumConocEmbarc.setMaxlength(30);
//			
//			txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor.setMaxlength(15);
//			txt_CatPort_13_Merca_TransMaritimo_Cont_NumPrecinto.setMaxlength(20);
//			
//			txt_CatPort_14_Merca_TransAereo_NumPermisoSCT.setMaxlength(50);
//			txt_CatPort_14_Merca_TransAereo_MatriculaAeronave.setMaxlength(10);
//			txt_CatPort_14_Merca_TransAereo_NombreAseg.setMaxlength(50);
//			txt_CatPort_14_Merca_TransAereo_NumPolizaSeguro.setMaxlength(30);
//			txt_CatPort_14_Merca_TransAereo_NumeroGuia.setMaxlength(15);
//			txt_CatPort_14_Merca_TransAereo_LugarContrato.setMaxlength(50);
//			txt_CatPort_14_Merca_TransAereo_RFCTransportista.setMaxlength(13);
//			txt_CatPort_14_Merca_TransAereo_NumRegIdTribTranspor.setMaxlength(40);
//			txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalTranspor.setMaxlength(3);
//			txt_CatPort_14_Merca_TransAereo_NombreTransportista.setMaxlength(254);
//			txt_CatPort_14_Merca_TransAereo_NumRegIdTribEmbarc.setMaxlength(40);
//			txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalEmbarc.setMaxlength(3);
//			txt_CatPort_14_Merca_TransAereo_NombreEmbarcador.setMaxlength(254);
//			
//			txt_CatPort_15_Merca_TransFerrov_NombreAseg.setMaxlength(50);
//			txt_CatPort_15_Merca_TransFerrov_NumPolizaSeguro.setMaxlength(30);
//			txt_CatPort_15_Merca_TransFerrov_Concesionario.setMaxlength(13);
//			
//			txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado.setMaxlength(20);
//			
//			txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro.setMaxlength(15);
//			txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro.setMaxlength(15);
//			txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro.setMaxlength(20);
//			
//			txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio.setMaxlength(20);
//			txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia.setMaxlength(20);
//			
//			txt_CatPort_20_FigTrans_Oper_RFCOperador.setMaxlength(13);
//			txt_CatPort_20_FigTrans_Oper_NumLicencia.setMaxlength(16);
//			txt_CatPort_20_FigTrans_Oper_NombreOperador.setMaxlength(254);
//			txt_CatPort_20_FigTrans_Oper_NumRegIdTribOperador.setMaxlength(40);
//			txt_CatPort_20_FigTrans_Oper_ResidenciaFiscalOperador.setMaxlength(3);
//			
//			txt_CatPort_21_FigTrans_Oper_Domi_Calle.setMaxlength(100);
//			txt_CatPort_21_FigTrans_Oper_Domi_NumeroExterior.setMaxlength(55);
//			txt_CatPort_21_FigTrans_Oper_Domi_NumeroInterior.setMaxlength(55);
//			txt_CatPort_21_FigTrans_Oper_Domi_Colonia.setMaxlength(120);
//			txt_CatPort_21_FigTrans_Oper_Domi_Localidad.setMaxlength(120);
//			txt_CatPort_21_FigTrans_Oper_Domi_Referencia.setMaxlength(250);
//			txt_CatPort_21_FigTrans_Oper_Domi_Municipio.setMaxlength(120);
//			txt_CatPort_21_FigTrans_Oper_Domi_Estado.setMaxlength(30);
//			txt_CatPort_21_FigTrans_Oper_Domi_Pais.setMaxlength(3);
//			txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal.setMaxlength(12);
//			
//			txt_CatPort_22_FigTrans_Prop_RFCPropietario.setMaxlength(13);
//			txt_CatPort_22_FigTrans_Prop_NombrePropietario.setMaxlength(254);
//			txt_CatPort_22_FigTrans_Prop_NumRegIdTribPropietario.setMaxlength(40);
//			txt_CatPort_22_FigTrans_Prop_ResidenciaFiscalPropietario.setMaxlength(3);
//			
//			txt_CatPort_23_FigTrans_Prop_Domi_Calle.setMaxlength(100);
//			txt_CatPort_23_FigTrans_Prop_Domi_NumeroExterior.setMaxlength(55);
//			txt_CatPort_23_FigTrans_Prop_Domi_NumeroInterior.setMaxlength(55);
//			txt_CatPort_23_FigTrans_Prop_Domi_Colonia.setMaxlength(120);
//			txt_CatPort_23_FigTrans_Prop_Domi_Localidad.setMaxlength(120);
//			txt_CatPort_23_FigTrans_Prop_Domi_Referencia.setMaxlength(250);
//			txt_CatPort_23_FigTrans_Prop_Domi_Municipio.setMaxlength(120);
//			txt_CatPort_23_FigTrans_Prop_Domi_Estado.setMaxlength(30);
//			txt_CatPort_23_FigTrans_Prop_Domi_Pais.setMaxlength(3);
//			txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal.setMaxlength(12);
//			
//			txt_CatPort_24_FigTrans_Arr_RFCArrendatario.setMaxlength(13);
//			txt_CatPort_24_FigTrans_Arr_NombreArrendatario.setMaxlength(254);
//			txt_CatPort_24_FigTrans_Arr_NumRegIdTribArrendatario.setMaxlength(40);
//			txt_CatPort_24_FigTrans_Arr_ResidenciaFiscalArrendatario.setMaxlength(3);
//			
//			txt_CatPort_25_FigTrans_Arr_Domi_Calle.setMaxlength(100);
//			txt_CatPort_25_FigTrans_Arr_Domi__NumeroExterior.setMaxlength(55);
//			txt_CatPort_25_FigTrans_Arr_Domi__NumeroInterior.setMaxlength(55);
//			txt_CatPort_25_FigTrans_Arr_Domi__Colonia.setMaxlength(120);
//			txt_CatPort_25_FigTrans_Arr_Domi__Localidad.setMaxlength(120);
//			txt_CatPort_25_FigTrans_Arr_Domi__Referencia.setMaxlength(250);
//			txt_CatPort_25_FigTrans_Arr_Domi__Municipio.setMaxlength(120);
//			txt_CatPort_25_FigTrans_Arr_Domi__Estado.setMaxlength(30);
//			txt_CatPort_25_FigTrans_Arr_Domi__Pais.setMaxlength(3);
//			txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal.setMaxlength(12);
//			
//			txt_CatPort_26_FigTrans_Noti_RFCNotificado.setMaxlength(13);
//			txt_CatPort_26_FigTrans_Noti_NombreNotificado.setMaxlength(254);
//			txt_CatPort_26_FigTrans_Noti_NumRegIdTribNotificado.setMaxlength(40);
//			txt_CatPort_26_FigTrans_Noti_ResidenciaFiscalNotificado.setMaxlength(3);
//			
//			txt_CatPort_27_FigTrans_Noti_Domi_Calle.setMaxlength(100);
//			txt_CatPort_27_FigTrans_Noti_Domi__NumeroExterior.setMaxlength(55);
//			txt_CatPort_27_FigTrans_Noti_Domi__NumeroInterior.setMaxlength(55);
//			txt_CatPort_27_FigTrans_Noti_Domi__Colonia.setMaxlength(120);
//			txt_CatPort_27_FigTrans_Noti_Domi__Localidad.setMaxlength(120);
//			txt_CatPort_27_FigTrans_Noti_Domi__Referencia.setMaxlength(250);
//			txt_CatPort_27_FigTrans_Noti_Domi__Municipio.setMaxlength(120);
//			txt_CatPort_27_FigTrans_Noti_Domi__Estado.setMaxlength(30);
//			txt_CatPort_27_FigTrans_Noti_Domi__Pais.setMaxlength(3);
//			txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal.setMaxlength(12);
//			
//			
//			gb_Comple_Pagos.setVisible(false);
			
			


	}
	
	public boolean ValidaUUID(String UUID_Relacion){
		
		 boolean ValidoUUID = false;
	       Pattern pat = Pattern.compile("[a-f0-9A-F]{8}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{12}");
	       Matcher mat = pat.matcher(UUID_Relacion);

	              if(mat.matches()){

	                     System.out.println("Cadena Valida");
	                     ValidoUUID=true;
	              }
	              else{
	            	  
	                     System.out.println("Cadena Invalida");
	                     ValidoUUID=false;
	             }
	             
	     return ValidoUUID;
	             
	}
	public boolean ValidaRFC(String RFC){
		
		 boolean ValidoRFC = false;
		 String Cadena = "^([A-Za-z\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Za-z|\\d]{3})$";
		 System.out.println("Valor Cadena Pattern = "+ Cadena);
	       Pattern pat = Pattern.compile(Cadena);
	       Matcher mat = pat.matcher(RFC);

	              if(mat.matches()){

	                     System.out.println("Cadena Valida");
	                     ValidoRFC=true;
	              }
	              else{
	            	  
	                     System.out.println("Cadena Invalida");
	                     ValidoRFC=false;
	             }
	             
	     return ValidoRFC;
	             
	}
	
	public boolean ValidaIdOrigen(String RFC){
		
		 boolean ValidaIdOrigen = false;
		 String Cadena = "OR[0-9]{6}";
		 System.out.println("Valor Cadena Pattern = "+ Cadena);
	       Pattern pat = Pattern.compile(Cadena);
	       Matcher mat = pat.matcher(RFC);

	              if(mat.matches()){

	                     System.out.println("Cadena Valida");
	                     ValidaIdOrigen=true;
	              }
	              else{
	            	  
	                     System.out.println("Cadena Invalida");
	                     ValidaIdOrigen=false;
	             }
	             
	     return ValidaIdOrigen;
	           
	}
	
	public boolean ValidaDimenciones(String Dimencion){
		
		 boolean ValidaDimen = false;
		 String Cadena = "[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{2}cm|[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{2}plg";
		 System.out.println("Valor Cadena Pattern = "+ Cadena);
	       Pattern pat = Pattern.compile(Cadena);
	       Matcher mat = pat.matcher(Dimencion);

	              if(mat.matches()){

	                     System.out.println("Cadena Valida");
	                     ValidaDimen=true;
	              }
	              else{
	            	  
	                     System.out.println("Cadena Invalida");
	                     ValidaDimen=false;
	             }
	             
	     return ValidaDimen;
	           
	}
	
	
	
	public boolean ValidaIdDestino(String RFC){
		
		 boolean ValidaIdDestino = false;
		 String Cadena = "DE[0-9]{6}";
		 System.out.println("Valor Cadena Pattern = "+ Cadena);
	       Pattern pat = Pattern.compile(Cadena);
	       Matcher mat = pat.matcher(RFC);

	              if(mat.matches()){

	                     System.out.println("Cadena Valida");
	                     ValidaIdDestino=true;
	              }
	              else{
	            	  
	                     System.out.println("Cadena Invalida");
	                     ValidaIdDestino=false;
	             }
	             
	     return ValidaIdDestino;
	           
	}
	
	public boolean ValidaPlaca(String Placa){
		
		 boolean ValidaPlaca = false;
		 String Cadena = "[^(?!.*\\s)-]{6,7}";
		 System.out.println("Valor Cadena Pattern = "+ Cadena);
	       Pattern pat = Pattern.compile(Cadena);
	       Matcher mat = pat.matcher(Placa);

	              if(mat.matches()){

	                     System.out.println("Cadena Valida");
	                     ValidaPlaca=true;
	              }
	              else{
	            	  
	                     System.out.println("Cadena Invalida");
	                     ValidaPlaca=false;
	             }
	             
	     return ValidaPlaca;
	           
	}
	
	public boolean ValidaAnio(String Anio){
		
		 boolean ValidaAnio = false;
		 String Cadena = "(19[0-9]{2}|20[0-9]{2})";
		 System.out.println("Valor Cadena Pattern = "+ Cadena);
	       Pattern pat = Pattern.compile(Cadena);
	       Matcher mat = pat.matcher(Anio);

	              if(mat.matches()){

	                     System.out.println("Cadena Valida");
	                     ValidaAnio=true;
	              }
	              else{
	            	  
	                     System.out.println("Cadena Invalida");
	                     ValidaAnio=false;
	             }
	             
	     return ValidaAnio;
	           
	}
	
	
	
	public boolean ValidaCartaPorte_02() throws InterruptedException{
		
		 boolean ValidaCartaPorte_02 = true;
		 
		 if(dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getValue() != null){
				if(dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getValue().equals("")){
					Messagebox.show("Ingresa la fecha y hora estimada en la que salen los bienes o mercancÃ­as del origen, en el Complemento Carta Porte Elemento UbicaciÃ³n Origen - Atributo: NFechaHoraSalida  ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_02 = false;
					return ValidaCartaPorte_02;
				}
			}else{
				Messagebox.show("Ingresa la fecha y hora estimada en la que salen los bienes o mercancÃ­as del origen, en el Complemento Carta Porte Elemento UbicaciÃ³n Origen - Atributo: NFechaHoraSalida  ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_02 = false;
				return ValidaCartaPorte_02;
			}
		 
		 if(txt_CatPort_02_Ubi_Ori_IDOrigen.getValue() != null){
				if(!txt_CatPort_02_Ubi_Ori_IDOrigen.getValue().equals("")){
					if(!ValidaIdOrigen(txt_CatPort_02_Ubi_Ori_IDOrigen.getValue())){
						Messagebox.show("No Cumple con la Estructura Del IDOrigen (OR[0-9]{6})", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_02 = false;
						return ValidaCartaPorte_02;
					}
				}
			}
		 
		
	             
	     return ValidaCartaPorte_02;
	             
	}
	
	public boolean ValidaCartaPorte_03() throws InterruptedException{
		
		 boolean ValidaCartaPorte_03 = true;
		 
		 if(dt_CatPort_03_Ubi_Dest_FechaHoraProgLlegada.getValue() != null){
				if(dt_CatPort_03_Ubi_Dest_FechaHoraProgLlegada.getValue().equals("")){
					Messagebox.show("Ingresa la fecha y hora en la que estima arriben a su destino los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento UbicaciÃ³n Destino - Atributo: FechaHoraProgLlegada  ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_03 = false;
					return ValidaCartaPorte_03;
				}
			}else{
				Messagebox.show("Ingresa la fecha y hora en la que estima arriben a su destino los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento UbicaciÃ³n Destino - Atributo: FechaHoraProgLlegada  ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_03 = false;
				return ValidaCartaPorte_03;
			}
		 
		 if(txt_CatPort_03_Ubi_Dest_IDDestino.getValue() != null){
				if(!txt_CatPort_03_Ubi_Dest_IDDestino.getValue().equals("")){
					if(!ValidaIdDestino(txt_CatPort_03_Ubi_Dest_IDDestino.getValue())){
						Messagebox.show("No Cumple con la Estructura Del IDDestino (DE[0-9]{6})", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_03 = false;
						return ValidaCartaPorte_03;
					}
				}
			}
		 
		 if(txt_CatPort_03_Ubi_Dest_RFCDestinatario.getValue() != null){
				if(!txt_CatPort_03_Ubi_Dest_RFCDestinatario.getValue().equals("")){
					if(!ValidaRFC(txt_CatPort_03_Ubi_Dest_RFCDestinatario.getValue().toUpperCase())){
						Messagebox.show("No Cumple con la Estructura Del RFCDestinatario", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_03 = false;
						return ValidaCartaPorte_03;
					}
				}
			}
		 
		 
	             
	     return ValidaCartaPorte_03;
	             
	}
	
	public boolean ValidaCartaPorte_04_4() throws InterruptedException{
		
		 boolean ValidaCartaPorte_04_4 = true;
		 
		 //la calle en la que estÃ¡ ubicado el domicilio de origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte
		 if(txt_CatPort_04_4_Ubi_Direc_Calle.getValue() != null){
				if(txt_CatPort_04_4_Ubi_Direc_Calle.getValue().equals("")){
					Messagebox.show("Ingresa la calle en la que estÃ¡ ubicado el domicilio de origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_04_4 = false;
					return ValidaCartaPorte_04_4;
				}
			}else{
				Messagebox.show("Ingresa la calle en la que estÃ¡ ubicado el domicilio de origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_04_4 = false;
				return ValidaCartaPorte_04_4;
			}
		 //el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte. El catÃ¡logo se publica en el portal del SAT en Internet y es conforme con la especificaciÃ³n ISO 3166-2
		 if(txt_CatPort_04_4_Ubi_Direc_Estado.getValue() != null){
				if(txt_CatPort_04_4_Ubi_Direc_Estado.getValue().equals("")){
					Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte. El catÃ¡logo se publica en el portal del SAT en Internet y es conforme con la especificaciÃ³n ISO 3166-2, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_04_4 = false;
					return ValidaCartaPorte_04_4;
				}
			}else{
				Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte. El catÃ¡logo se publica en el portal del SAT en Internet y es conforme con la especificaciÃ³n ISO 3166-2, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_04_4 = false;
				return ValidaCartaPorte_04_4;
			}
		 //la clave del paÃ­s en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, conforme con el catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1
		 if(txt_CatPort_04_4_Ubi_Direc_Pais.getValue() != null){
				if(txt_CatPort_04_4_Ubi_Direc_Pais.getValue().equals("")){
					Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, conforme con el catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_04_4 = false;
					return ValidaCartaPorte_04_4;
				}
			}else{
				Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, conforme con el catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_04_4 = false;
				return ValidaCartaPorte_04_4;
			}
		 //el cÃ³digo postal (PO, BOX) en donde se encuentra el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte
		 if(txt_CatPort_04_4_Ubi_Direc_CodigoPostal.getValue() != null){
				if(txt_CatPort_04_4_Ubi_Direc_CodigoPostal.getValue().equals("")){
					Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_04_4 = false;
					return ValidaCartaPorte_04_4;
				}
			}else{
				Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_04_4 = false;
				return ValidaCartaPorte_04_4;
			}
	             
	     return ValidaCartaPorte_04_4;
	             
	}
	
	public boolean ValidaCartaPorte_04() throws InterruptedException{
		
		 boolean ValidaCartaPorte_04 = true;
		 
		 //la calle en la que estÃ¡ ubicado el domicilio de origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte
		 if(txt_CatPort_04_Ubi_Direc_Calle.getValue() != null){
				if(txt_CatPort_04_Ubi_Direc_Calle.getValue().equals("")){
					Messagebox.show("Ingresa la calle en la que estÃ¡ ubicado el domicilio de origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_04 = false;
					return ValidaCartaPorte_04;
				}
			}else{
				Messagebox.show("Ingresa la calle en la que estÃ¡ ubicado el domicilio de origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_04 = false;
				return ValidaCartaPorte_04;
			}
		 //el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte. El catÃ¡logo se publica en el portal del SAT en Internet y es conforme con la especificaciÃ³n ISO 3166-2
		 if(txt_CatPort_04_Ubi_Direc_Estado.getValue() != null){
				if(txt_CatPort_04_Ubi_Direc_Estado.getValue().equals("")){
					Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte. El catÃ¡logo se publica en el portal del SAT en Internet y es conforme con la especificaciÃ³n ISO 3166-2, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_04 = false;
					return ValidaCartaPorte_04;
				}
			}else{
				Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte. El catÃ¡logo se publica en el portal del SAT en Internet y es conforme con la especificaciÃ³n ISO 3166-2, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_04 = false;
				return ValidaCartaPorte_04;
			}
		 //la clave del paÃ­s en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, conforme con el catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1
		 if(txt_CatPort_04_Ubi_Direc_Pais.getValue() != null){
				if(txt_CatPort_04_Ubi_Direc_Pais.getValue().equals("")){
					Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, conforme con el catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_04 = false;
					return ValidaCartaPorte_04;
				}
			}else{
				Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, conforme con el catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_04 = false;
				return ValidaCartaPorte_04;
			}
		 //el cÃ³digo postal (PO, BOX) en donde se encuentra el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte
		 if(txt_CatPort_04_Ubi_Direc_CodigoPostal.getValue() != null){
				if(txt_CatPort_04_Ubi_Direc_CodigoPostal.getValue().equals("")){
					Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_04 = false;
					return ValidaCartaPorte_04;
				}
			}else{
				Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra el domicilio del origen y/o destino de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento UbicaciÃ³n Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_04 = false;
				return ValidaCartaPorte_04;
			}
	             
	     return ValidaCartaPorte_04;
	             
	}
	
	public boolean ValidaCartaPorte_05() throws InterruptedException{
		
		 boolean ValidaCartaPorte_05 = true;
		 
		 //el nÃºmero total de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, identificÃ¡ndose por cada nodo "Mercancia" registrado en el complemento
		 if(txt_CatPort_05_Merca_Gral_NumTotalMercancias.getValue() != null){
				if(txt_CatPort_05_Merca_Gral_NumTotalMercancias.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero total de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, identificÃ¡ndose por cada nodo \"Mercancia\" registrado en el complemento, en el Complemento Carta Porte Elemento Mercancia General - Atributo: NumTotalMercancias   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_05 = false;
					return ValidaCartaPorte_05;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero total de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, identificÃ¡ndose por cada nodo \"Mercancia\" registrado en el complemento, en el Complemento Carta Porte Elemento Mercancia General - Atributo: NumTotalMercancias   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_05 = false;
				return ValidaCartaPorte_05;
			}
		
	     return ValidaCartaPorte_05;
	             
	}
	
	public boolean ValidaCartaPorte_06() throws InterruptedException{
		
		 boolean ValidaCartaPorte_06 = true;
		 
		 //Peso en Kilogramos
		 if(txt_CatPort_06_Merca_PesoEnKg.getValue() != null){
				if(txt_CatPort_06_Merca_PesoEnKg.getValue().equals("")){
					Messagebox.show("Ingresa el peso en kilogramos de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento Mercancia - Atributo: PesoEnKg   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_06 = false;
					return ValidaCartaPorte_06;
				}
			}else{
				Messagebox.show("Ingresa el peso en kilogramos de los bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento Mercancia - Atributo: PesoEnKg   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_06 = false;
				return ValidaCartaPorte_06;
			}
		 
		 
		 if(txt_CatPort_06_Merca_Dimensiones.getValue() != null){
				if(!txt_CatPort_06_Merca_Dimensiones.getValue().equals("")){
					if(!ValidaDimenciones(txt_CatPort_06_Merca_Dimensiones.getValue())){
						Messagebox.show("No Cumple con la Estructura De las Dimenciones Ejemplo (30/40/30cm o 30/40/30plg)", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_06 = false;
						return ValidaCartaPorte_06;
					}
				}
			}
		 
		 
		
	     return ValidaCartaPorte_06;
	             
	}
	
	public boolean ValidaCartaPorte_07() throws InterruptedException{
		
		 boolean ValidaCartaPorte_07 = true;
		 
		 //Cantidad
		 if(txt_CatPort_07_Merca_CantTrans_Cantidad.getValue() != null){
				if(txt_CatPort_07_Merca_CantTrans_Cantidad.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero de bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento Mercancia Cantidad Transporta - Atributo: Cantidad   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_07 = false;
					return ValidaCartaPorte_07;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero de bienes o mercancÃ­as que se trasladan en los distintos medios de transporte, en el Complemento Carta Porte Elemento Mercancia Cantidad Transporta - Atributo: Cantidad   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_07 = false;
				return ValidaCartaPorte_07;
			}
		 
		//Id Origen
		 if(txt_CatPort_07_Merca_CantTrans_IDOrigen.getValue() != null){
				if(txt_CatPort_07_Merca_CantTrans_IDOrigen.getValue().equals("")){
					Messagebox.show("Ingresa la clave del identificador del origen de los bienes o mercancÃ­as que se trasladan por los distintos medios de transporte, de acuerdo al valor registrado en el nodo â€œOrigenâ€�, del elemento â€œUbicacionâ€�, en el Complemento Carta Porte Elemento Mercancia Cantidad Transporta - Atributo: IDOrigen   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_07 = false;
					return ValidaCartaPorte_07;
				}else{
					if(!ValidaIdOrigen(txt_CatPort_07_Merca_CantTrans_IDOrigen.getValue())){
						Messagebox.show("No Cumple con la Estructura Del IDOrigen (OR[0-9]{6})", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_07 = false;
						return ValidaCartaPorte_07;
					}
				}
			}else{
				Messagebox.show("Ingresa la clave del identificador del origen de los bienes o mercancÃ­as que se trasladan por los distintos medios de transporte, de acuerdo al valor registrado en el nodo â€œOrigenâ€�, del elemento â€œUbicacionâ€�, en el Complemento Carta Porte Elemento Mercancia Cantidad Transporta - Atributo: IDOrigen   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_07 = false;
				return ValidaCartaPorte_07;
			}
		 
		//Id Destino
		 if(txt_CatPort_07_Merca_CantTrans_IDDestino.getValue() != null){
				if(txt_CatPort_07_Merca_CantTrans_IDDestino.getValue().equals("")){
					Messagebox.show("Ingresa la clave del identificador del destino de los bienes o mercancÃ­as que se trasladan por los distintos medios de transporte, de acuerdo al valor registrado en el nodo â€œOrigenâ€�, del elemento â€œUbicacionâ€�, en el Complemento Carta Porte Elemento Mercancia Cantidad Transporta - Atributo: IDDestino   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_07 = false;
					return ValidaCartaPorte_07;
				}else{
					if(!ValidaIdDestino(txt_CatPort_07_Merca_CantTrans_IDDestino.getValue())){
						Messagebox.show("No Cumple con la Estructura Del IDDestino (DE[0-9]{6})", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_07 = false;
						return ValidaCartaPorte_07;
					}
				}
			}else{
				Messagebox.show("Ingresa la clave del identificador del destino de los bienes o mercancÃ­as que se trasladan por los distintos medios de transporte, de acuerdo al valor registrado en el nodo â€œOrigenâ€�, del elemento â€œUbicacionâ€�, en el Complemento Carta Porte Elemento Mercancia Cantidad Transporta - Atributo: IDDestino   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_07 = false;
				return ValidaCartaPorte_07;
			}
		 
		 return ValidaCartaPorte_07;
	             
	}
	
	public boolean ValidaCartaPorte_08() throws InterruptedException{
		
		 boolean ValidaCartaPorte_08 = true;
		 
		 //Unidad Peso
		 if(txt_CatPort_08_Merca_DetaMerc_UnidadPeso.getValue() != null){
				if(txt_CatPort_08_Merca_DetaMerc_UnidadPeso.getValue().equals("")){
					Messagebox.show("Ingresa la clave de unidad de medida estandarizada del peso de los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Mercancia Detalle Mercancia - Atributo: UnidadPeso   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_08 = false;
					return ValidaCartaPorte_08;
				}
			}else{
				Messagebox.show("Ingresa la clave de unidad de medida estandarizada del peso de los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Mercancia Detalle Mercancia - Atributo: UnidadPeso   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_08 = false;
				return ValidaCartaPorte_08;
			}
		 
		//Peso Bruto
		 if(txt_CatPort_08_Merca_DetaMerc_PesoBruto.getValue() != null){
				if(txt_CatPort_08_Merca_DetaMerc_PesoBruto.getValue().equals("")){
					Messagebox.show("Ingresa el peso total bruto de los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Mercancia Detalle Mercancia - Atributo: PesoBruto   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_08 = false;
					return ValidaCartaPorte_08;
				}
			}else{
				Messagebox.show("Ingresa el peso total bruto de los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Mercancia Detalle Mercancia - Atributo: PesoBruto   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_08 = false;
				return ValidaCartaPorte_08;
			}
		 
		//Peso Neto
		 if(txt_CatPort_08_Merca_DetaMerc_PesoNeto.getValue() != null){
				if(txt_CatPort_08_Merca_DetaMerc_PesoNeto.getValue().equals("")){
					Messagebox.show("Ingresa el peso total neto de los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Mercancia Detalle Mercancia - Atributo: PesoNeto   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_08 = false;
					return ValidaCartaPorte_08;
				}
			}else{
				Messagebox.show("Ingresa el peso total neto de los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Mercancia Detalle Mercancia - Atributo: PesoNeto   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_08 = false;
				return ValidaCartaPorte_08;
			}
		 
		//Peso Tara
		 if(txt_CatPort_08_Merca_DetaMerc_PesoTara.getValue() != null){
				if(txt_CatPort_08_Merca_DetaMerc_PesoTara.getValue().equals("")){
					Messagebox.show("Ingresa el peso bruto, menos el peso neto de las mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Mercancia Detalle Mercancia - Atributo: PesoTara   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_08 = false;
					return ValidaCartaPorte_08;
				}
			}else{
				Messagebox.show("Ingresa el peso bruto, menos el peso neto de las mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Mercancia Detalle Mercancia - Atributo: PesoTara   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_08 = false;
				return ValidaCartaPorte_08;
			}
		
	     return ValidaCartaPorte_08;
	             
	}
	
	public boolean ValidaCartaPorte_09() throws InterruptedException{
		
		 boolean ValidaCartaPorte_09 = true;
		 
		 //Permiso SCT
		 if(txt_CatPort_09_Merca_AutoTransF_PermSCT.getValue() != null){
				if(txt_CatPort_09_Merca_AutoTransF_PermSCT.getValue().equals("")){
					Messagebox.show("Ingresa la clave del tipo de permiso proporcionado por la SCT, el cual debe corresponder de acuerdo al tipo de autotransporte utilizado para el traslado de los bienes o mercancÃ­as registrado en el catÃ¡logo catCartaPorte:c_TipoPermiso, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal - Atributo: PermSCT   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_09 = false;
					return ValidaCartaPorte_09;
				}
			}else{
				Messagebox.show("Ingresa la clave del tipo de permiso proporcionado por la SCT, el cual debe corresponder de acuerdo al tipo de autotransporte utilizado para el traslado de los bienes o mercancÃ­as registrado en el catÃ¡logo catCartaPorte:c_TipoPermiso, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal - Atributo: PermSCT   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_09 = false;
				return ValidaCartaPorte_09;
			}
		 
		//Numero de Permiso SCT
		 if(txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT.getValue() != null){
				if(txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero del permiso otorgado por la SCT, el cual se debe capturar de acuerdo al tipo de autotransporte utilizado para el traslado de los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal - Atributo: NumPermisoSCT   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_09 = false;
					return ValidaCartaPorte_09;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero del permiso otorgado por la SCT, el cual se debe capturar de acuerdo al tipo de autotransporte utilizado para el traslado de los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal - Atributo: NumPermisoSCT   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_09 = false;
				return ValidaCartaPorte_09;
			}
		 
		//Nombre de la Aseguradora
		 if(txt_CatPort_09_Merca_AutoTransF_NombreAseg.getValue() != null){
				if(txt_CatPort_09_Merca_AutoTransF_NombreAseg.getValue().equals("")){
					Messagebox.show("Ingresa el nombre de la aseguradora que cubre los riesgos del autotransporte utilizado para el traslado de los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal - Atributo: NombreAseg   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_09 = false;
					return ValidaCartaPorte_09;
				}
			}else{
				Messagebox.show("Ingresa el nombre de la aseguradora que cubre los riesgos del autotransporte utilizado para el traslado de los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal - Atributo: NombreAseg   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_09 = false;
				return ValidaCartaPorte_09;
			}
		 
		//Numero de Poliza del Seguro
		 if(txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro.getValue() != null){
				if(txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero de pÃ³liza asignado por la aseguradora, que cubre los riesgos del autotransporte utilizado para el traslado de los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal - Atributo: NumPolizaSeguro   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_09 = false;
					return ValidaCartaPorte_09;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero de pÃ³liza asignado por la aseguradora, que cubre los riesgos del autotransporte utilizado para el traslado de los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal - Atributo: NumPolizaSeguro   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_09 = false;
				return ValidaCartaPorte_09;
			}
		
	     return ValidaCartaPorte_09;
	             
	}
	
	public boolean ValidaCartaPorte_10() throws InterruptedException{
		
		 boolean ValidaCartaPorte_10 = true;
		 
		 //la clave de nomenclatura del autotransporte que es utilizado para transportar los bienes o mercancÃ­as
		 if(txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular.getValue() != null){
				if(txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular.getValue().equals("")){
					Messagebox.show("Ingresa la clave de nomenclatura del autotransporte que es utilizado para transportar los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal IdentificaciÃ³n Vehicular - Atributo: ConfigVehicular   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_10 = false;
					return ValidaCartaPorte_10;
				}
			}else{
				Messagebox.show("Ingresa la clave de nomenclatura del autotransporte que es utilizado para transportar los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal IdentificaciÃ³n Vehicular - Atributo: ConfigVehicular   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_10 = false;
				return ValidaCartaPorte_10;
			}
		 
		//el valor de la placa vehicular del autotransporte que es utilizado para transportar los bienes o mercancÃ­as, se deben registrar solo los caracteres alfanumÃ©ricos, sin guiones y espacios
		 if(txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM.getValue() != null){
				if(txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM.getValue().equals("")){
					Messagebox.show("Ingresa el valor de la placa vehicular del autotransporte que es utilizado para transportar los bienes o mercancÃ­as, se deben registrar solo los caracteres alfanumÃ©ricos, sin guiones y espacios, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal IdentificaciÃ³n Vehicular - Atributo: PlacaVM   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_10 = false;
					return ValidaCartaPorte_10;
				}else{
					
					if(!ValidaPlaca(txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM.getValue())){
						Messagebox.show("No Cumple con la Estructura De la PlacaVM ([^(?!.*\\s)-]{6,7})", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_10 = false;
						return ValidaCartaPorte_10;
					}
					
				}
			}else{
				Messagebox.show("Ingresa el valor de la placa vehicular del autotransporte que es utilizado para transportar los bienes o mercancÃ­as, se deben registrar solo los caracteres alfanumÃ©ricos, sin guiones y espacios, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal IdentificaciÃ³n Vehicular - Atributo: PlacaVM   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_10 = false;
				return ValidaCartaPorte_10;
			}
		 
		//El aÃ±o del autotransporte que es utilizado para transportar los bienes o mercancÃ­as.
		 if(txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM.getValue() != null){
				if(txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM.getValue().equals("")){
					Messagebox.show("Ingresa el aÃ±o del autotransporte que es utilizado para transportar los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal IdentificaciÃ³n Vehicular - Atributo: AnioModeloVM   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_10 = false;
					return ValidaCartaPorte_10;
				}else{
					
					if(!ValidaAnio(txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM.getValue())){
						Messagebox.show("No Cumple con la Estructura Del AÃ±o ((19[0-9]{2}|20[0-9]{2}))", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_10 = false;
						return ValidaCartaPorte_10;
					}
				}
			}else{
				Messagebox.show("Ingresa el aÃ±o del autotransporte que es utilizado para transportar los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Auto Transporte Federal IdentificaciÃ³n Vehicular - Atributo: AnioModeloVM   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_10 = false;
				return ValidaCartaPorte_10;
			}
		 
		
		
	     return ValidaCartaPorte_10;
	             
	}
	
	public boolean ValidaCartaPorte_12() throws InterruptedException{
		
		 boolean ValidaCartaPorte_12 = true;
		 
		 //Clave de identificaciÃ³n del transporte del tipo de embarcaciÃ³n que es utilizado para trasladar los bienes o mercancÃ­as
		 if(txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion.getValue() != null){
				if(txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion.getValue().equals("")){
					Messagebox.show("Ingresa la clave de identificaciÃ³n del transporte del tipo de embarcaciÃ³n que es utilizado para trasladar los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: TipoEmbarcacion   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_12 = false;
					return ValidaCartaPorte_12;
				}
			}else{
				Messagebox.show("Ingresa la clave de identificaciÃ³n del transporte del tipo de embarcaciÃ³n que es utilizado para trasladar los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: TipoEmbarcacion   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_12 = false;
				return ValidaCartaPorte_12;
			}
		 
		 //nÃºmero de la matrÃ­cula o registro de la embarcaciÃ³n que es utilizada para transportar los bienes o mercancÃ­as
		 if(txt_CatPort_12_Merca_TransMaritimo_Matricula.getValue() != null){
				if(txt_CatPort_12_Merca_TransMaritimo_Matricula.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero de la matrÃ­cula o registro de la embarcaciÃ³n que es utilizada para transportar los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: Matricula   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_12 = false;
					return ValidaCartaPorte_12;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero de la matrÃ­cula o registro de la embarcaciÃ³n que es utilizada para transportar los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: Matricula   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_12 = false;
				return ValidaCartaPorte_12;
			}
		 
		 //el nÃºmero de identificaciÃ³n asignado por la OrganizaciÃ³n MarÃ­tima Internacional a la embarcaciÃ³n encargada de transportar los bienes o mercancÃ­as
		 if(txt_CatPort_12_Merca_TransMaritimo_NumeroOMI.getValue() != null){
				if(txt_CatPort_12_Merca_TransMaritimo_NumeroOMI.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero de identificaciÃ³n asignado por la OrganizaciÃ³n MarÃ­tima Internacional a la embarcaciÃ³n encargada de transportar los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: NumeroOMI   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_12 = false;
					return ValidaCartaPorte_12;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero de identificaciÃ³n asignado por la OrganizaciÃ³n MarÃ­tima Internacional a la embarcaciÃ³n encargada de transportar los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: NumeroOMI   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_12 = false;
				return ValidaCartaPorte_12;
			}
		 
		 //el paÃ­s correspondiente a la nacionalidad de la embarcaciÃ³n que transporta los bienes o mercancÃ­as
		 if(txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc.getValue() != null){
				if(txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc.getValue().equals("")){
					Messagebox.show("Ingresa el paÃ­s correspondiente a la nacionalidad de la embarcaciÃ³n que transporta los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: NacionalidadEmbarc   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_12 = false;
					return ValidaCartaPorte_12;
				}
			}else{
				Messagebox.show("Ingresa el paÃ­s correspondiente a la nacionalidad de la embarcaciÃ³n que transporta los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: NacionalidadEmbarc   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_12 = false;
				return ValidaCartaPorte_12;
			}
		 
		 //el valor de las unidades de arqueo bruto conforme a las medidas internacionales definidas por el ITC para cada tipo de buque o embarcaciÃ³n en la que se transportan los bienes o mercancÃ­as
		 if(txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto.getValue() != null){
				if(txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto.getValue().equals("")){
					Messagebox.show("Ingresa el valor de las unidades de arqueo bruto conforme a las medidas internacionales definidas por el ITC para cada tipo de buque o embarcaciÃ³n en la que se transportan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: UnidadesDeArqBruto   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_12 = false;
					return ValidaCartaPorte_12;
				}
			}else{
				Messagebox.show("Ingresa el valor de las unidades de arqueo bruto conforme a las medidas internacionales definidas por el ITC para cada tipo de buque o embarcaciÃ³n en la que se transportan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: UnidadesDeArqBruto   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_12 = false;
				return ValidaCartaPorte_12;
			} 
		 
		 //el tipo de carga bajo el cual se tipifican los bienes o mercancÃ­as que se transportan en la embarcaciÃ³n
		 if(txt_CatPort_12_Merca_TransMaritimo_TipoCarga.getValue() != null){
				if(txt_CatPort_12_Merca_TransMaritimo_TipoCarga.getValue().equals("")){
					Messagebox.show("Ingresa el tipo de carga bajo el cual se tipifican los bienes o mercancÃ­as que se transportan en la embarcaciÃ³n, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: TipoCarga   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_12 = false;
					return ValidaCartaPorte_12;
				}
			}else{
				Messagebox.show("Ingresa el tipo de carga bajo el cual se tipifican los bienes o mercancÃ­as que se transportan en la embarcaciÃ³n, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: TipoCarga   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_12 = false;
				return ValidaCartaPorte_12;
			}
		 
		 //el nÃºmero del certificado emitido por la ITC para la embarcaciÃ³n o buque que transporta los bienes o mercancÃ­as
		 if(txt_CatPort_12_Merca_TransMaritimo_NumCertITC.getValue() != null){
				if(txt_CatPort_12_Merca_TransMaritimo_NumCertITC.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero del certificado emitido por la ITC para la embarcaciÃ³n o buque que transporta los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: NumCertITC   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_12 = false;
					return ValidaCartaPorte_12;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero del certificado emitido por la ITC para la embarcaciÃ³n o buque que transporta los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: NumCertITC   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_12 = false;
				return ValidaCartaPorte_12;
			}
		 
		 //el nombre del agente naviero autorizado para gestionar el traslado de los bienes o mercancÃ­as vÃ­a marÃ­tima
		 if(txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero.getValue() != null){
				if(txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero.getValue().equals("")){
					Messagebox.show("Ingresa el nombre del agente naviero autorizado para gestionar el traslado de los bienes o mercancÃ­as vÃ­a marÃ­tima, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: NombreAgenteNaviero   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_12 = false;
					return ValidaCartaPorte_12;
				}
			}else{
				Messagebox.show("Ingresa el nombre del agente naviero autorizado para gestionar el traslado de los bienes o mercancÃ­as vÃ­a marÃ­tima, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: NombreAgenteNaviero   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_12 = false;
				return ValidaCartaPorte_12;
			}
		 
		 //el nÃºmero de registro de autorizaciÃ³n como agente naviero consignatario emitido por la SCT
		 if(txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero.getValue() != null){
				if(txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero de registro de autorizaciÃ³n como agente naviero consignatario emitido por la SCT, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: NumAutorizacionNaviero   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_12 = false;
					return ValidaCartaPorte_12;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero de registro de autorizaciÃ³n como agente naviero consignatario emitido por la SCT, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: NumAutorizacionNaviero   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_12 = false;
				return ValidaCartaPorte_12;
			}
		 
		 //Expresa el aÃ±o de la embarcaciÃ³n en la que se transportan los bienes o mercancÃ­as.
		 if(txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion.getValue() != null){
				if(txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion.getValue().equals("")){
					Messagebox.show("Ingresa el aÃ±o de la embarcaciÃ³n en la que se transportan los bienes o mercancÃ­as., en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: AnioEmbarcacion   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_12 = false;
					return ValidaCartaPorte_12;
				} else{
					
					if(!ValidaAnio(txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion.getValue())){
						Messagebox.show("No Cumple con la Estructura Del AÃ±o ((19[0-9]{2}|20[0-9]{2}))", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_12 = false;
						return ValidaCartaPorte_12;
					}
				}
			}else{
				Messagebox.show("Ingresa el aÃ±o de la embarcaciÃ³n en la que se transportan los bienes o mercancÃ­as., en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo - Atributo: AnioEmbarcacion   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_12 = false;
				return ValidaCartaPorte_12;
			}
		
		 
		
	             
	     return ValidaCartaPorte_12;
	             
	}
	
	public boolean ValidaCartaPorte_13() throws InterruptedException{
		
		 boolean ValidaCartaPorte_13 = true;
		 
		 //la matrÃ­cula o nÃºmero de identificaciÃ³n del contenedor marÃ­timo donde se transportan los bienes o mercancÃ­as, el cual estÃ¡ integrado por el cÃ³digo del propietario, el nÃºmero de serie y el dÃ­gito de control
		 if(txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor.getValue() != null){
				if(txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor.getValue().equals("")){
					Messagebox.show("Ingresa la matrÃ­cula o nÃºmero de identificaciÃ³n del contenedor marÃ­timo donde se transportan los bienes o mercancÃ­as, el cual estÃ¡ integrado por el cÃ³digo del propietario, el nÃºmero de serie y el dÃ­gito de control, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo Conteedor - Atributo: MatriculaContenedor   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_13 = false;
					return ValidaCartaPorte_13;
				}
			}else{
				Messagebox.show("Ingresa la matrÃ­cula o nÃºmero de identificaciÃ³n del contenedor marÃ­timo donde se transportan los bienes o mercancÃ­as, el cual estÃ¡ integrado por el cÃ³digo del propietario, el nÃºmero de serie y el dÃ­gito de control, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo Conteedor - Atributo: MatriculaContenedor   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_13 = false;
				return ValidaCartaPorte_13;
			}
		 
		 //la clave de identificaciÃ³n para el tipo de contenedor marÃ­timo donde se transportan los bienes o mercancÃ­as
		 if(lb_CatPort_13_Merca_TransMaritimo_Cont_TipoContenedor.getSelectedItem().getLabel() != null){
				if(lb_CatPort_13_Merca_TransMaritimo_Cont_TipoContenedor.getSelectedIndex() < 0){
		 
					Messagebox.show("Ingresa la clave de identificaciÃ³n para el tipo de contenedor marÃ­timo donde se transportan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo Conteedor - Atributo: TipoContenedor   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_13 = false;
					return ValidaCartaPorte_13;
				}
			}else{
				Messagebox.show("Ingresa la clave de identificaciÃ³n para el tipo de contenedor marÃ­timo donde se transportan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Maritimo Conteedor - Atributo: TipoContenedor   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_13 = false;
				return ValidaCartaPorte_13;
			}
		 
		  return ValidaCartaPorte_13;
	             
	}
	
	public boolean ValidaCartaPorte_14() throws InterruptedException{
		
		 boolean ValidaCartaPorte_14 = true;
		 
		 //la clave del tipo de permiso proporcionado por la SCT o la autoridad anÃ¡loga, para el transporte de bienes o mercancÃ­as vÃ­a aÃ©rea
		 if(txt_CatPort_14_Merca_TransAereo_PermSCT.getValue() != null){
				if(txt_CatPort_14_Merca_TransAereo_PermSCT.getValue().equals("")){
					Messagebox.show("Ingresa la clave del tipo de permiso proporcionado por la SCT o la autoridad anÃ¡loga, para el transporte de bienes o mercancÃ­as vÃ­a aÃ©rea, en el Complemento Carta Porte Elemento Mercancia Transporte Aereo - Atributo: PermSCT   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_14 = false;
					return ValidaCartaPorte_14;
				}
			}else{
				Messagebox.show("Ingresa la clave del tipo de permiso proporcionado por la SCT o la autoridad anÃ¡loga, para el transporte de bienes o mercancÃ­as vÃ­a aÃ©rea, en el Complemento Carta Porte Elemento Mercancia Transporte Aereo - Atributo: PermSCT   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_14 = false;
				return ValidaCartaPorte_14;
			}
		 
		 //el nÃºmero de permiso o algÃºn valor anÃ¡logo proporcionado por la SCT o la autoridad anÃ¡loga, para el transporte de bienes o mercancÃ­as vÃ­a aÃ©rea
		 if(txt_CatPort_14_Merca_TransAereo_NumPermisoSCT.getValue() != null){
				if(txt_CatPort_14_Merca_TransAereo_NumPermisoSCT.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero de permiso o algÃºn valor anÃ¡logo proporcionado por la SCT o la autoridad anÃ¡loga, para el transporte de bienes o mercancÃ­as vÃ­a aÃ©rea, en el Complemento Carta Porte Elemento Mercancia Transporte Aereo - Atributo: NumPermisoSCT   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_14 = false;
					return ValidaCartaPorte_14;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero de permiso o algÃºn valor anÃ¡logo proporcionado por la SCT o la autoridad anÃ¡loga, para el transporte de bienes o mercancÃ­as vÃ­a aÃ©rea, en el Complemento Carta Porte Elemento Mercancia Transporte Aereo - Atributo: NumPermisoSCT   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_14 = false;
				return ValidaCartaPorte_14;
			}
		 
		 //el nÃºmero de matrÃ­cula de la aeronave que opera en territorio nacional y que se compone de valores alfanumÃ©ricos mÃ¡s el carÃ¡cter especial de guion medio â€œ-â€œ, con una longitud de 10 posiciones
		 if(txt_CatPort_14_Merca_TransAereo_MatriculaAeronave.getValue() != null){
				if(txt_CatPort_14_Merca_TransAereo_MatriculaAeronave.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero de matrÃ­cula de la aeronave que opera en territorio nacional y que se compone de valores alfanumÃ©ricos mÃ¡s el carÃ¡cter especial de guion medio â€œ-â€œ, con una longitud de 10 posiciones, en el Complemento Carta Porte Elemento Mercancia Transporte Aereo - Atributo: MatriculaAeronave   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_14 = false;
					return ValidaCartaPorte_14;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero de matrÃ­cula de la aeronave que opera en territorio nacional y que se compone de valores alfanumÃ©ricos mÃ¡s el carÃ¡cter especial de guion medio â€œ-â€œ, con una longitud de 10 posiciones, en el Complemento Carta Porte Elemento Mercancia Transporte Aereo - Atributo: MatriculaAeronave   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_14 = false;
				return ValidaCartaPorte_14;
			}
		 
		 //el nÃºmero de guÃ­a aÃ©rea con el que se trasladan los bienes o mercancÃ­as
		 if(txt_CatPort_14_Merca_TransAereo_NumeroGuia.getValue() != null){
				if(txt_CatPort_14_Merca_TransAereo_NumeroGuia.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero de guÃ­a aÃ©rea con el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Aereo - Atributo: NumeroGuia   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_14 = false;
					return ValidaCartaPorte_14;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero de guÃ­a aÃ©rea con el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Aereo - Atributo: NumeroGuia   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_14 = false;
				return ValidaCartaPorte_14;
			}
		 
		 //el valor del cÃ³digo que tiene asignado el transportista y debe contener una clave vÃ¡lida del catÃ¡logo â€œcatCartaPorte:c_CodigoTransporteAereoâ€�
		 if(txt_CatPort_14_Merca_TransAereo_CodigoTransportista.getValue() != null){
				if(txt_CatPort_14_Merca_TransAereo_CodigoTransportista.getValue().equals("")){
					Messagebox.show("Ingresa el valor del cÃ³digo que tiene asignado el transportista y debe contener una clave vÃ¡lida del catÃ¡logo â€œcatCartaPorte:c_CodigoTransporteAereoâ€�, en el Complemento Carta Porte Elemento Mercancia Transporte Aereo - Atributo: CodigoTransportista   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_14 = false;
					return ValidaCartaPorte_14;
				}
			}else{
				Messagebox.show("Ingresa el valor del cÃ³digo que tiene asignado el transportista y debe contener una clave vÃ¡lida del catÃ¡logo â€œcatCartaPorte:c_CodigoTransporteAereoâ€�, en el Complemento Carta Porte Elemento Mercancia Transporte Aereo - Atributo: CodigoTransportista   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_14 = false;
				return ValidaCartaPorte_14;
			}
		 
		 if(txt_CatPort_14_Merca_TransAereo_RFCTransportista.getValue() != null){
				if(!txt_CatPort_14_Merca_TransAereo_RFCTransportista.getValue().equals("")){
					if(!ValidaRFC(txt_CatPort_14_Merca_TransAereo_RFCTransportista.getValue().toUpperCase())){
						Messagebox.show("No Cumple con la Estructura Del RFCTransportista", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_14 = false;
						return ValidaCartaPorte_14;
					}
				}
			}
		 
		 if(txt_CatPort_14_Merca_TransAereo_RFCEmbarcador.getValue() != null){
				if(!txt_CatPort_14_Merca_TransAereo_RFCEmbarcador.getValue().equals("")){
					if(!ValidaRFC(txt_CatPort_14_Merca_TransAereo_RFCEmbarcador.getValue().toUpperCase())){
						Messagebox.show("No Cumple con la Estructura Del RFCEmbarcador", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_14 = false;
						return ValidaCartaPorte_14;
					}
				}
			}
		 
		  return ValidaCartaPorte_14;
	             
	}
	
	public boolean ValidaCartaPorte_15() throws InterruptedException{
		
		 boolean ValidaCartaPorte_15 = true;
		 
		 //la clave del tipo de servicio proporcionado para el traslado de los bienes o mercancÃ­as vÃ­a fÃ©rrea
		 if(lb_CatPort_15_Merca_TransFerrov_TipoDeServicio.getSelectedItem().getLabel() != null){
				if(lb_CatPort_15_Merca_TransFerrov_TipoDeServicio.getSelectedIndex() < 0){
					Messagebox.show("Ingresa la clave del tipo de servicio proporcionado para el traslado de los bienes o mercancÃ­as vÃ­a fÃ©rrea, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario - Atributo: TipoDeServicio   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_15 = false;
					return ValidaCartaPorte_15;
				}
			}else{
				Messagebox.show("Ingresa la clave del tipo de servicio proporcionado para el traslado de los bienes o mercancÃ­as vÃ­a fÃ©rrea, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario - Atributo: TipoDeServicio   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_15 = false;
				return ValidaCartaPorte_15;
			}
		 
		 if(txt_CatPort_15_Merca_TransFerrov_Concesionario.getValue() != null){
				if(!txt_CatPort_15_Merca_TransFerrov_Concesionario.getValue().equals("")){
					if(!ValidaRFC(txt_CatPort_15_Merca_TransFerrov_Concesionario.getValue().toUpperCase())){
						Messagebox.show("No Cumple con la Estructura Del Concesionario", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						ValidaCartaPorte_15 = false;
						return ValidaCartaPorte_15;
					}
				}
			}
		 
		 
		 
		  return ValidaCartaPorte_15;
	             
	}
	
	public boolean ValidaCartaPorte_16() throws InterruptedException{
		
		 boolean ValidaCartaPorte_16 = true;
		 
		 //la clave del derecho de paso pagado por el transportista en las vÃ­as fÃ©rreas de las cuales no es concesionario o asignatario
		 if(txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso.getValue() != null){
				if(txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso.getValue().equals("")){
					Messagebox.show("Ingresa la clave del derecho de paso pagado por el transportista en las vÃ­as fÃ©rreas de las cuales no es concesionario o asignatario, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Derechos De Paso - Atributo: TipoDerechoDePaso   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_16 = false;
					return ValidaCartaPorte_16;
				}
			}else{
				Messagebox.show("Ingresa la clave del derecho de paso pagado por el transportista en las vÃ­as fÃ©rreas de las cuales no es concesionario o asignatario, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Derechos De Paso - Atributo: TipoDerechoDePaso   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_16 = false;
				return ValidaCartaPorte_16;
			}
		 
		 //el total de kilÃ³metros pagados por el transportista en las vÃ­as fÃ©rreas de las cuales no es concesionario o asignatario con el derecho de paso
		 if(txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado.getValue() != null){
				if(txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado.getValue().equals("")){
					Messagebox.show("Ingresa el total de kilÃ³metros pagados por el transportista en las vÃ­as fÃ©rreas de las cuales no es concesionario o asignatario con el derecho de paso, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Derechos De Paso - Atributo: KilometrajePagado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_16 = false;
					return ValidaCartaPorte_16;
				}
			}else{
				Messagebox.show("Ingresa el total de kilÃ³metros pagados por el transportista en las vÃ­as fÃ©rreas de las cuales no es concesionario o asignatario con el derecho de paso, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Derechos De Paso - Atributo: KilometrajePagado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_16 = false;
				return ValidaCartaPorte_16;
			}
		 
		 
		 
		  return ValidaCartaPorte_16;
	             
	}
	
	public boolean ValidaCartaPorte_17() throws InterruptedException{
		
		 boolean ValidaCartaPorte_17 = true;
		 
		 //la clave del tipo de carro utilizado para el traslado de los bienes o mercancÃ­as vÃ­a fÃ©rrea
		 if(lb_CatPort_17_Merca_TransFerrov_Carro_TipoCarro.getSelectedItem().getLabel() != null){
				if(lb_CatPort_17_Merca_TransFerrov_Carro_TipoCarro.getSelectedIndex() < 0){
					Messagebox.show("Ingresa la clave del tipo de carro utilizado para el traslado de los bienes o mercancÃ­as vÃ­a fÃ©rrea, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro - Atributo: TipoCarro   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_17 = false;
					return ValidaCartaPorte_17;
				}
			}else{
				Messagebox.show("Ingresa la clave del tipo de carro utilizado para el traslado de los bienes o mercancÃ­as vÃ­a fÃ©rrea, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro - Atributo: TipoCarro   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_17 = false;
				return ValidaCartaPorte_17;
			}
		 
		 //el nÃºmero de contenedor, carro de ferrocarril o nÃºmero econÃ³mico del vehÃ­culo en el que se trasladan los bienes o mercancÃ­as vÃ­a fÃ©rrea
		 if(txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro.getValue() != null){
				if(txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero de contenedor, carro de ferrocarril o nÃºmero econÃ³mico del vehÃ­culo en el que se trasladan los bienes o mercancÃ­as vÃ­a fÃ©rrea, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro - Atributo: MatriculaCarro   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_17 = false;
					return ValidaCartaPorte_17;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero de contenedor, carro de ferrocarril o nÃºmero econÃ³mico del vehÃ­culo en el que se trasladan los bienes o mercancÃ­as vÃ­a fÃ©rrea, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro - Atributo: MatriculaCarro   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_17 = false;
				return ValidaCartaPorte_17;
			}
		 
		 //el nÃºmero de guÃ­a asignado al contenedor, carro de ferrocarril o vehÃ­culo en el que se trasladan los bienes o mercancÃ­as vÃ­a fÃ©rrea
		 if(txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro.getValue() != null){
				if(txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro.getValue().equals("")){
					Messagebox.show("Ingresa el nÃºmero de guÃ­a asignado al contenedor, carro de ferrocarril o vehÃ­culo en el que se trasladan los bienes o mercancÃ­as vÃ­a fÃ©rrea, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro - Atributo: GuiaCarro   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_17 = false;
					return ValidaCartaPorte_17;
				}
			}else{
				Messagebox.show("Ingresa el nÃºmero de guÃ­a asignado al contenedor, carro de ferrocarril o vehÃ­culo en el que se trasladan los bienes o mercancÃ­as vÃ­a fÃ©rrea, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro - Atributo: GuiaCarro   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_17 = false;
				return ValidaCartaPorte_17;
			}
		 
		 //la cantidad de las toneladas netas contenidas en el contenedor, carro de ferrocarril o vehÃ­culo en el que se trasladan los bienes o mercancÃ­as vÃ­a fÃ©rrea
		 if(txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro.getValue() != null){
				if(txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro.getValue().equals("")){
					Messagebox.show("Ingresa la cantidad de las toneladas netas contenidas en el contenedor, carro de ferrocarril o vehÃ­culo en el que se trasladan los bienes o mercancÃ­as vÃ­a fÃ©rrea, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro - Atributo: ToneladasNetasCarro   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_17 = false;
					return ValidaCartaPorte_17;
				}
			}else{
				Messagebox.show("Ingresa la cantidad de las toneladas netas contenidas en el contenedor, carro de ferrocarril o vehÃ­culo en el que se trasladan los bienes o mercancÃ­as vÃ­a fÃ©rrea, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro - Atributo: ToneladasNetasCarro   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_17 = false;
				return ValidaCartaPorte_17;
			}
		 
		 
		 
		  return ValidaCartaPorte_17;
	             
	}
	
	public boolean ValidaCartaPorte_18() throws InterruptedException{
		
		 boolean ValidaCartaPorte_18 = true;
		 
		 // r la clave con las que se identifica el tipo de contenedor o vagÃ³n en el que se traslada los bienes o mercancÃ­as
		 if(lb_CatPort_18_Merca_TransFerrov_Carro_Cont_TipoContenedor.getSelectedItem().getLabel() != null){
				if(lb_CatPort_18_Merca_TransFerrov_Carro_Cont_TipoContenedor.getSelectedIndex() < 0){
					Messagebox.show("Ingresa r la clave con las que se identifica el tipo de contenedor o vagÃ³n en el que se traslada los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro Contenedor - Atributo: TipoContenedor   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_18 = false;
					return ValidaCartaPorte_18;
				}
			}else{
				Messagebox.show("Ingresa r la clave con las que se identifica el tipo de contenedor o vagÃ³n en el que se traslada los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro Contenedor - Atributo: TipoContenedor   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_18 = false;
				return ValidaCartaPorte_18;
			}
		 
		 //el peso en kilogramos del contenedor vacÃ­o en el que se trasladan los bienes o mercancÃ­as
		 if(txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio.getValue() != null){
				if(txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio.getValue().equals("")){
					Messagebox.show("Ingresa el peso en kilogramos del contenedor vacÃ­o en el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro Contenedor - Atributo: PesoContenedorVacio   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_18 = false;
					return ValidaCartaPorte_18;
				}
			}else{
				Messagebox.show("Ingresa el peso en kilogramos del contenedor vacÃ­o en el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro Contenedor - Atributo: PesoContenedorVacio   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_18 = false;
				return ValidaCartaPorte_18;
			}
		 
		 //el peso neto en kilogramos de los bienes o mercancÃ­as que trasladan en el contenedor
		 if(txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia.getValue() != null){
				if(txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia.getValue().equals("")){
					Messagebox.show("Ingresa el peso neto en kilogramos de los bienes o mercancÃ­as que trasladan en el contenedor, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro Contenedor - Atributo: PesoNetoMercancia   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_18 = false;
					return ValidaCartaPorte_18;
				}
			}else{
				Messagebox.show("Ingresa el peso neto en kilogramos de los bienes o mercancÃ­as que trasladan en el contenedor, en el Complemento Carta Porte Elemento Mercancia Transporte Ferroviario Carro Contenedor - Atributo: PesoNetoMercancia   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_18 = false;
				return ValidaCartaPorte_18;
			}
		 
				 
		  return ValidaCartaPorte_18;
	             
	}
	
	public boolean ValidaCartaPorte_19() throws InterruptedException{
		
		 boolean ValidaCartaPorte_19 = true;
		 
		 // la clave que identifica el medio por el cual se transportan los bienes o mercancÃ­as, dicha clave debe ser distinta a â€œ05â€� que corresponde a â€œDuctoâ€�
		 if(lb_CatPort_19_FigTrans_CveTransporte.getSelectedItem().getLabel() != null){
				if(lb_CatPort_19_FigTrans_CveTransporte.getSelectedIndex() < 0){
					Messagebox.show("Ingresa la clave que identifica el medio por el cual se transportan los bienes o mercancÃ­as, dicha clave debe ser distinta a â€œ05â€� que corresponde a â€œDuctoâ€�, en el Complemento Carta Porte Elemento Cantidad Transporta - Atributo: CveTransporte   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_19 = false;
					return ValidaCartaPorte_19;
				}
			}else{
				Messagebox.show("Ingresa la clave que identifica el medio por el cual se transportan los bienes o mercancÃ­as, dicha clave debe ser distinta a â€œ05â€� que corresponde a â€œDuctoâ€�, en el Complemento Carta Porte Elemento Cantidad Transporta - Atributo: CveTransporte   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_19 = false;
				return ValidaCartaPorte_19;
			}
		 	 
		  return ValidaCartaPorte_19;
	             
	}
	
	public boolean ValidaCartaPorte_21() throws InterruptedException{
		
		 boolean ValidaCartaPorte_21 = true;
		 
		 //la calle en que estÃ¡ ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as
		 if(txt_CatPort_21_FigTrans_Oper_Domi_Calle.getValue() != null){
				if(txt_CatPort_21_FigTrans_Oper_Domi_Calle.getValue().equals("")){
					Messagebox.show("Ingresa la calle en que estÃ¡ ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Cantidad Transporta Operador Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_21 = false;
					return ValidaCartaPorte_21;
				}
			}else{
				Messagebox.show("Ingresa la calle en que estÃ¡ ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Cantidad Transporta Operador Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_21 = false;
				return ValidaCartaPorte_21;
			}
		 
		 //el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as
		 if(txt_CatPort_21_FigTrans_Oper_Domi_Estado.getValue() != null){
				if(txt_CatPort_21_FigTrans_Oper_Domi_Estado.getValue().equals("")){
					Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Cantidad Transporta Operador Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_21 = false;
					return ValidaCartaPorte_21;
				}
			}else{
				Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Cantidad Transporta Operador Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_21 = false;
				return ValidaCartaPorte_21;
			}
		 
		 //la clave del paÃ­s en donde se encuentra ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1
		 if(txt_CatPort_21_FigTrans_Oper_Domi_Pais.getValue() != null){
				if(txt_CatPort_21_FigTrans_Oper_Domi_Pais.getValue().equals("")){
					Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento Cantidad Transporta Operador Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_21 = false;
					return ValidaCartaPorte_21;
				}
			}else{
				Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento Cantidad Transporta Operador Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_21 = false;
				return ValidaCartaPorte_21;
			}
		 
		 //el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as
		 if(txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal.getValue() != null){
				if(txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal.getValue().equals("")){
					Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Cantidad Transporta Operador Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_21 = false;
					return ValidaCartaPorte_21;
				}
			}else{
				Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Cantidad Transporta Operador Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_21 = false;
				return ValidaCartaPorte_21;
			}
		 	 
		  return ValidaCartaPorte_21;
	             
	}
	
	public boolean ValidaCartaPorte_23() throws InterruptedException{
		
		 boolean ValidaCartaPorte_23 = true;
		 
		 //la calle en que estÃ¡ ubicado el domicilio del propietario del medio de transporte
		 if(txt_CatPort_23_FigTrans_Prop_Domi_Calle.getValue() != null){
				if(txt_CatPort_23_FigTrans_Prop_Domi_Calle.getValue().equals("")){
					Messagebox.show("Ingresa la calle en que estÃ¡ ubicado el domicilio del propietario del medio de transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Propietario Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_23 = false;
					return ValidaCartaPorte_23;
				}
			}else{
				Messagebox.show("Ingresa la calle en que estÃ¡ ubicado el domicilio del operador del autotransporte de carga federal en el que se trasladan los bienes o mercancÃ­as, en el Complemento Carta Porte Elemento Cantidad Transporta Operador Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_23 = false;
				return ValidaCartaPorte_23;
			}
		 
		 //el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del propietario del transporte
		 if(txt_CatPort_23_FigTrans_Prop_Domi_Estado.getValue() != null){
				if(txt_CatPort_23_FigTrans_Prop_Domi_Estado.getValue().equals("")){
					Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del propietario del transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Propietario Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_23 = false;
					return ValidaCartaPorte_23;
				}
			}else{
				Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del propietario del transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Propietario Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_23 = false;
				return ValidaCartaPorte_23;
			}
		 
		 //clave del paÃ­s en donde se encuentra ubicado el domicilio del propietario del transporte, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1
		 if(txt_CatPort_23_FigTrans_Prop_Domi_Pais.getValue() != null){
				if(txt_CatPort_23_FigTrans_Prop_Domi_Pais.getValue().equals("")){
					Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio del propietario del transporte, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento Cantidad Transporta Propietario Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_23 = false;
					return ValidaCartaPorte_23;
				}
			}else{
				Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio del propietario del transporte, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento Cantidad Transporta Propietario Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_23 = false;
				return ValidaCartaPorte_23;
			}
		 
		 //el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio del propietario del transporte
		 if(txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal.getValue() != null){
				if(txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal.getValue().equals("")){
					Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio del propietario del transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Propietario Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_23 = false;
					return ValidaCartaPorte_23;
				}
			}else{
				Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio del propietario del transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Propietario Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_23 = false;
				return ValidaCartaPorte_23;
			}
		 	 
		  return ValidaCartaPorte_23;
	             
	}
	
	public boolean ValidaCartaPorte_25() throws InterruptedException{
		
		 boolean ValidaCartaPorte_25 = true;
		 
		 //la calle en que estÃ¡ ubicado el domicilio del arrendatario del medio de transporte.
		 if(txt_CatPort_25_FigTrans_Arr_Domi_Calle.getValue() != null){
				if(txt_CatPort_25_FigTrans_Arr_Domi_Calle.getValue().equals("")){
					Messagebox.show("Ingresa la calle en que estÃ¡ ubicado el domicilio del arrendatario del medio de transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Arrendatario Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_25 = false;
					return ValidaCartaPorte_25;
				}
			}else{
				Messagebox.show("Ingresa la calle en que estÃ¡ ubicado el domicilio del arrendatario del medio de transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Arrendatario Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_25 = false;
				return ValidaCartaPorte_25;
			}
		 
		 //el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del arrendatario del transporte
		 if(txt_CatPort_25_FigTrans_Arr_Domi__Estado.getValue() != null){
				if(txt_CatPort_25_FigTrans_Arr_Domi__Estado.getValue().equals("")){
					Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del arrendatario del transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Arrendatario Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_25 = false;
					return ValidaCartaPorte_25;
				}
			}else{
				Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio del arrendatario del transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Arrendatario Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_25 = false;
				return ValidaCartaPorte_25;
			}
		 
		 //la clave del paÃ­s en donde se encuentra ubicado el domicilio del arrendatario del transporte, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1
		 if(txt_CatPort_25_FigTrans_Arr_Domi__Pais.getValue() != null){
				if(txt_CatPort_25_FigTrans_Arr_Domi__Pais.getValue().equals("")){
					Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio del arrendatario del transporte, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento Cantidad Transporta Arrendatario Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_25 = false;
					return ValidaCartaPorte_25;
				}
			}else{
				Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio del arrendatario del transporte, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento Cantidad Transporta Arrendatario Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_25 = false;
				return ValidaCartaPorte_25;
			}
		 
		 //el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio del arrendatario del transporte
		 if(txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal.getValue() != null){
				if(txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal.getValue().equals("")){
					Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio del arrendatario del transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Arrendatario Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_25 = false;
					return ValidaCartaPorte_25;
				}
			}else{
				Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio del arrendatario del transporte, en el Complemento Carta Porte Elemento Cantidad Transporta Arrendatario Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_25 = false;
				return ValidaCartaPorte_25;
			}
		 	 
		  return ValidaCartaPorte_25;
	             
	}
	
	public boolean ValidaCartaPorte_27() throws InterruptedException{
		
		 boolean ValidaCartaPorte_27 = true;
		 
		 //la calle en que estÃ¡ ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan
		 if(txt_CatPort_27_FigTrans_Noti_Domi_Calle.getValue() != null){
				if(txt_CatPort_27_FigTrans_Noti_Domi_Calle.getValue().equals("")){
					Messagebox.show("Ingresa la calle en que estÃ¡ ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Cantidad Transporta Notificado Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_27 = false;
					return ValidaCartaPorte_27;
				}
			}else{
				Messagebox.show("Ingresa la calle en que estÃ¡ ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Cantidad Transporta Notificado Domicilio - Atributo: Calle   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_27 = false;
				return ValidaCartaPorte_27;
			}
		 
		 //el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan
		 if(txt_CatPort_27_FigTrans_Noti_Domi__Estado.getValue() != null){
				if(txt_CatPort_27_FigTrans_Noti_Domi__Estado.getValue().equals("")){
					Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Cantidad Transporta Notificado Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_27 = false;
					return ValidaCartaPorte_27;
				}
			}else{
				Messagebox.show("Ingresa el estado, entidad, regiÃ³n, comunidad, u otra figura anÃ¡loga en donde se encuentra ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Cantidad Transporta Notificado Domicilio - Atributo: Estado   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_27 = false;
				return ValidaCartaPorte_27;
			}
		 
		 // la clave del paÃ­s en donde se encuentra ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1
		 if(txt_CatPort_27_FigTrans_Noti_Domi__Pais.getValue() != null){
				if(txt_CatPort_27_FigTrans_Noti_Domi__Pais.getValue().equals("")){
					Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento Cantidad Transporta Notificado Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_27 = false;
					return ValidaCartaPorte_27;
				}
			}else{
				Messagebox.show("Ingresa la clave del paÃ­s en donde se encuentra ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan, conforme al catÃ¡logo c_Pais publicado en el portal del SAT en Internet que estÃ¡ basado en la especificaciÃ³n ISO 3166-1, en el Complemento Carta Porte Elemento Cantidad Transporta Notificado Domicilio - Atributo: Pais   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_27 = false;
				return ValidaCartaPorte_27;
			}
		 
		 //el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan
		 if(txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal.getValue() != null){
				if(txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal.getValue().equals("")){
					Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Cantidad Transporta Notificado Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					ValidaCartaPorte_27 = false;
					return ValidaCartaPorte_27;
				}
			}else{
				Messagebox.show("Ingresa el cÃ³digo postal (PO, BOX) en donde se encuentra ubicado el domicilio de la persona notificada del arribo del medio de transporte con los bienes o mercancÃ­as que se trasladan, en el Complemento Carta Porte Elemento Cantidad Transporta Notificado Domicilio - Atributo: CodigoPostal   ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				ValidaCartaPorte_27 = false;
				return ValidaCartaPorte_27;
			}
		 	 
		  return ValidaCartaPorte_27;
	             
	}
	
	public boolean Valida() throws InterruptedException{
		boolean valida = true;
		
		//Comprobante
		
		
		
		if(txt_Factura_Serie.getValue() != null){
			if(txt_Factura_Serie.getValue().equals("")){
				Messagebox.show("Ingresa una Serie ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				valida = false;
				return valida;
			}
		}else{
			Messagebox.show("Ingresa una Serie ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
		
		if(txt_Factura_Folio.getValue() != null){
			if(txt_Factura_Folio.getValue().equals("")){
				Messagebox.show("Ingresa un Folio ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				valida = false;
				return valida;
			}
		}else{
			Messagebox.show("Ingresa un Folio ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
	
		if(dt_Factura_Fecha.getValue() != null){
			if(dt_Factura_Fecha.getValue().equals("")){
				Messagebox.show("Ingresa una Fecha ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				valida = false;
				return valida;
			}
		}else{
			Messagebox.show("Ingresa una Fecha ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
		
		
		if(txt_Factura_Subtotal.getValue() != null){
			if(txt_Factura_Subtotal.getValue().equals("")){
				Messagebox.show("Ingresa El Subtotal ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				valida = false;
				return valida;
			}
		}else{
			Messagebox.show("Ingresa El Subtotal ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
		
		
		if (cb_Moneda.getSelectedIndex() < 0){
			Messagebox.show("Selecciona la Moneda ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
		
		if(txt_Factura_Total.getValue() != null){
			if(txt_Factura_Total.getValue().equals("")){
				Messagebox.show("Ingresa el Total ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				valida = false;
				return valida;
			}
		}else{
			Messagebox.show("Ingresa el Total ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
		
		
		
		if (lb_Factura_Tipo_Compro.getSelectedIndex() < 0){
			Messagebox.show("Selecciona el Tipo de Comprobante ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
		Listitem itemTipo = lb_Factura_Tipo_Compro.getSelectedItem();
		String nombre = itemTipo.getLabel();
		
			
		
		if (nombre.startsWith("Traslado") || nombre.startsWith("Pago") ){			
			
			
			
		}else{
			if (lb_MetodoPagoFactDialog.getSelectedIndex() < 0){
				Messagebox.show("Selecciona el Metodo de Pago ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				valida = false;
				return valida;
			}
			
			
			if (lb_FormaPagoFactDialog.getSelectedIndex() < 0){
				Messagebox.show("Selecciona la Forma de Pago ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				valida = false;
				return valida;
			}
		}
		
		if(txt_Factura_Lugar_Expedi.getValue() != null){
			if(txt_Factura_Lugar_Expedi.getValue().equals("")){
				Messagebox.show("Ingresa El Lugar de ExpediciÃ³n ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				valida = false;
				return valida;
			}
		}else{
			Messagebox.show("Ingresa El Lugar de ExpediciÃ³n ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
		
		//Receptor
		if(!ValidaRFC(txt_Factura_Emi_RFC.getValue())){
			Messagebox.show("No Cumple con la Estructura El RFC del Emisor", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
		
		if(!ValidaRFC(txt_Factura_Recep_RFC.getValue())){
			Messagebox.show("No Cumple con la Estructura El RFC del Receptor", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
		
		if (lb_UsoDialog.getSelectedIndex() < 0){
			Messagebox.show("Selecciona el Uso CFDI ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
		}
		
		
		if (lb_CatPort_00_TranspInternac.getSelectedIndex() > 0){
			if(!Valida_Comple_Carta_Porte()){
				Messagebox.show("No Cumple con la Estructura del Complemento de Carta Porte", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				valida = false;
				return valida;
			}
		}
		
		if (lb_Pago_01_FormaDePagoP.getSelectedIndex() > 0){
			if(!ValidaPago()){
				Messagebox.show("No Cumple con la Estructura del Complemento Recepcion de Pagos", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				valida = false;
				return valida;
			}
		}
		
//		if (lb_Pago_01_FormaDePagoP.getSelectedIndex() > 0){
			
//		}
		
//		try {
//			if(ventaserv.getComprobanteExiste(txt_Factura_Serie.getValue(),txt_Factura_Folio.getValue(),txt_Factura_Emi_RFC.getValue())){
//				Messagebox.show("Ya se encuentra un registro con esa Serie y ese folio", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
//				return valida;
//			}
//		} catch (WrongValueException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		
		return valida;
	}
	
	public boolean ValidaPago() throws InterruptedException{
		boolean validaPago = true;
		
		//Cmplemento de Recepcion de Pagos CRP
		
		if(dt_Pago_01_FechaPago.getValue() != null){
			if(dt_Pago_01_FechaPago.getValue().equals("")){
				Messagebox.show("Ingresa una Fecha de Pago (FechaPago) del Complemento de Pagos linea 01 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				validaPago = false;
				return validaPago;
			}
		}else{
			Messagebox.show("Ingresa una Fecha de Pago (FechaPago) del Complemento de Pagos linea 01 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			validaPago = false;
			return validaPago;
		}
		
		
		if (lb_Pago_01_FormaDePagoP.getSelectedIndex() < 0){
			Messagebox.show("Selecciona Una Forma de Pago (FormaDePagoP) del Complemento de Pagos linea 01 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			validaPago = false;
			return validaPago;
		}
		
		if (lb_Pago_01_MonedaP.getSelectedIndex() < 0){
			Messagebox.show("Selecciona Un aMoneda (MonedaP) del Complemento de Pagos linea 01 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			validaPago = false;
			return validaPago;
		}
	
		if(txt_Pago_01_Monto.getValue() != null){
			if(txt_Pago_01_Monto.getValue().equals("")){
				Messagebox.show("Ingresa un Monto del Complemento de Pagos linea 01 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
				validaPago = false;
				return validaPago;
			}
		}else{
			Messagebox.show("Ingresa un Monto del Complemento de Pagos linea 01 ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			validaPago = false;
			return validaPago;
		}
		
		
		return validaPago;
	}
	
	
	public boolean Valida_Comple_Carta_Porte() throws InterruptedException{
		boolean valida_Carta_Porte = true;
		
		//Complemento Carta Porte
		if(lb_CatPort_00_TranspInternac.getSelectedIndex() < 0){
			Messagebox.show("Selecciona un valor de TranspInternac  ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida_Carta_Porte = false;
			return valida_Carta_Porte ;
			
		}else{
			
			if(lb_CatPort_00_TranspInternac.getSelectedIndex() == 1){
				lb_CatPort_00_EntradaSalidaMerc.setVisible(true);
				lb_CatPort_00_ViaEntradaSalida.setVisible(true);
				
				if(lb_CatPort_00_EntradaSalidaMerc.getSelectedIndex() < 0){
					Messagebox.show("Selecciona una entrada o Salida de Mercancia  (EntradaSalidaMerc) ya que el Valor de TranspInternac es SI  ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					valida_Carta_Porte = false;
					return valida_Carta_Porte ;
				}
				
				if(lb_CatPort_00_ViaEntradaSalida.getSelectedIndex() < 0){
					Messagebox.show("Selecciona una Via de entrada o Salida de Mercancia  (ViaEntradaSalida) ya que el Valor de TranspInternac es SI  ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					valida_Carta_Porte = false;
					return valida_Carta_Porte ;
				}
			}else{
				lb_CatPort_00_EntradaSalidaMerc.setVisible(false);
				lb_CatPort_00_ViaEntradaSalida.setVisible(false);
			}
			
		}
		
		
		
		
		if(!txt_CatPort_02_Ubi_Ori_IDOrigen.getValue().equals("")|| !txt_CatPort_02_Ubi_Ori_RFCRemitente.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NombreRemitente.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NumRegIdTrib.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_ResidenciaFiscal.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NumEstacion.getValue().equals("") || !txt_CatPort_02_Ubi_Ori_NombreEstacion.getValue().equals("") || lb_CatPort_02_Ubi_Ori_NavegacionTrafico.getSelectedIndex() > 0 || !dt_CatPort_02_Ubi_Ori_NFechaHoraSalida.getValue().equals("") ){
			
			if(!ValidaCartaPorte_02()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}	
			
		}
		
		if(!txt_CatPort_04_4_Ubi_Direc_Calle.getValue().equals("")|| !txt_CatPort_04_4_Ubi_Direc_NumeroExterior.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_NumeroInterior.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Colonia.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Localidad.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Referencia.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Municipio.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Estado.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_Pais.getValue().equals("") || !txt_CatPort_04_4_Ubi_Direc_CodigoPostal.getValue().equals("")){

			if(!ValidaCartaPorte_04_4()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
				
			}
		}
		
		if(!txt_CatPort_03_Ubi_Dest_IDDestino.getValue().equals("")|| !txt_CatPort_03_Ubi_Dest_RFCDestinatario.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NombreDestinatario.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NumRegIdTrib.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_ResidenciaFiscal.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NumEstacion.getValue().equals("") || !txt_CatPort_03_Ubi_Dest_NombreEstacion.getValue().equals("") || lb_CatPort_03_Ubi_Dest_NavegacionTrafico.getSelectedIndex() > 0 || !dt_CatPort_03_Ubi_Dest_FechaHoraProgLlegada.getValue().equals("") ){

			if(!ValidaCartaPorte_03()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}
		}
		
		if(!txt_CatPort_04_Ubi_Direc_Calle.getValue().equals("")|| !txt_CatPort_04_Ubi_Direc_NumeroExterior.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_NumeroInterior.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Colonia.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Localidad.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Referencia.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Municipio.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Estado.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_Pais.getValue().equals("") || !txt_CatPort_04_Ubi_Direc_CodigoPostal.getValue().equals("")){

			if(!ValidaCartaPorte_04()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
				
			}
		}
		
		if(!txt_CatPort_05_Merca_Gral_PesoBrutoTotal.getValue().equals("")|| !txt_CatPort_05_Merca_Gral_UnidadPeso.getValue().equals("") || !txt_CatPort_05_Merca_Gral_PesoNetoTotal.getValue().equals("") || !txt_CatPort_05_Merca_Gral_NumTotalMercancias.getValue().equals("") || !txt_CatPort_05_Merca_Gral_CargoPorTasacion.getValue().equals("")){

			
			if(!ValidaCartaPorte_05()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}
		}
		
		if(!txt_CatPort_06_Merca_BienesTransp.getValue().equals("")|| !txt_CatPort_06_Merca_ClaveSTCC.getValue().equals("") || !txt_CatPort_05_Merca_Gral_PesoNetoTotal.getValue().equals("") || !txt_CatPort_06_Merca_Descripcion.getValue().equals("") || !txt_CatPort_06_Merca_Cantidad.getValue().equals("") || !txt_CatPort_06_Merca_ClaveUnidad.getValue().equals("") || !txt_CatPort_06_Merca_Unidad.getValue().equals("") || !txt_CatPort_06_Merca_Dimensiones.getValue().equals("") || !txt_CatPort_06_Merca_MaterialPeligroso.getValue().equals("") || !txt_CatPort_06_Merca_CveMaterialPeligroso.getValue().equals("") || !txt_CatPort_06_Merca_Embalaje.getValue().equals("") || !txt_CatPort_06_Merca_DescripEmbalaje.getValue().equals("") || !txt_CatPort_06_Merca_PesoEnKg.getValue().equals("") || !txt_CatPort_06_Merca_ValorMercancia.getValue().equals("") || !txt_CatPort_06_Merca_Moneda.getValue().equals("") || !txt_CatPort_06_Merca_FraccionArancelaria.getValue().equals("") || !txt_CatPort_06_Merca_UUIDComercioExt.getValue().equals("")){
			
			if(!ValidaCartaPorte_06()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}
		}
		
		if(!txt_CatPort_07_Merca_CantTrans_Cantidad.getValue().equals("")|| !txt_CatPort_07_Merca_CantTrans_IDOrigen.getValue().equals("") || !txt_CatPort_07_Merca_CantTrans_IDDestino.getValue().equals("") || lb_CatPort_07_Merca_CantTrans_CvesTransporte.getSelectedIndex() > 0){
			
			if(!txt_CatPort_02_Ubi_Ori_IDOrigen.getValue().equals("")){
				
				if(!ValidaCartaPorte_07()){
					valida_Carta_Porte = false;
					return valida_Carta_Porte ;
				}
			}
			
		}
		
		if(!txt_CatPort_08_Merca_DetaMerc_UnidadPeso.getValue().equals("")|| !txt_CatPort_08_Merca_DetaMerc_PesoBruto.getValue().equals("") || !txt_CatPort_08_Merca_DetaMerc_PesoNeto.getValue().equals("") || !txt_CatPort_08_Merca_DetaMerc_PesoTara.getValue().equals("") || !txt_CatPort_08_Merca_DetaMerc_NumPiezas.getValue().equals("")){
			
			if(lb_CatPort_07_Merca_CantTrans_CvesTransporte.getSelectedIndex() == 2){
				
				if(!ValidaCartaPorte_08()){
					valida_Carta_Porte = false;
					return valida_Carta_Porte ;
				}
			}
			
			
		}
		
		if(!txt_CatPort_09_Merca_AutoTransF_PermSCT.getValue().equals("")|| !txt_CatPort_09_Merca_AutoTransF_NumPermisoSCT.getValue().equals("") || !txt_CatPort_09_Merca_AutoTransF_NombreAseg.getValue().equals("") || !txt_CatPort_09_Merca_AutoTransF_NumPolizaSeguro.getValue().equals("") ){
			
			if(!ValidaCartaPorte_09()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}
		}
		
		if(!txt_CatPort_10_Merca_AutoTransF_IdenOfi_ConfigVehicular.getValue().equals("")|| !txt_CatPort_10_Merca_AutoTransF_IdenOfi_PlacaVM.getValue().equals("") || !txt_CatPort_10_Merca_AutoTransF_IdenOfi_AnioModeloVM.getValue().equals("") ){
			
			if(!ValidaCartaPorte_10()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}
		}
		
		if(!txt_CatPort_12_Merca_TransMaritimo_PermSCT.getValue().equals("")|| !txt_CatPort_12_Merca_TransMaritimo_NumPermisoSCT.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NombreAseg.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumPolizaSeguro.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_TipoEmbarcacion.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Matricula.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumeroOMI.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NombreEmbarc.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NacionalidadEmbarc.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_AnioEmbarcacion.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_UnidadesDeArqBruto.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_TipoCarga.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumCertITC.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Eslora.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Manga.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_Calado.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_LineaNaviera.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NombreAgenteNaviero.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumAutorizacionNaviero.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumViaje.getValue().equals("") || !txt_CatPort_12_Merca_TransMaritimo_NumConocEmbarc.getValue().equals("") ){

			
    			if(!ValidaCartaPorte_12()){
    				valida_Carta_Porte = false;
    				return valida_Carta_Porte ;
				}
		}
		
		
		if(!txt_CatPort_13_Merca_TransMaritimo_Cont_MatriculaContenedor.getValue().equals("")|| lb_CatPort_13_Merca_TransMaritimo_Cont_TipoContenedor.getSelectedIndex() > 0 || !txt_CatPort_13_Merca_TransMaritimo_Cont_NumPrecinto.getValue().equals("")){

			if(!ValidaCartaPorte_13()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}

		if(!txt_CatPort_14_Merca_TransAereo_PermSCT.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_NumPermisoSCT.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_MatriculaAeronave.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NombreAseg.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NumPolizaSeguro.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NumeroGuia.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_LugarContrato.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_RFCTransportista.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_CodigoTransportista.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NumRegIdTribTranspor.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalTranspor.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_NombreTransportista.getValue().equals("") || !txt_CatPort_14_Merca_TransAereo_RFCEmbarcador.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_NumRegIdTribEmbarc.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_ResidenciaFiscalEmbarc.getValue().equals("")|| !txt_CatPort_14_Merca_TransAereo_NombreEmbarcador.getValue().equals("")){

			if(!ValidaCartaPorte_14()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}

		if(lb_CatPort_15_Merca_TransFerrov_TipoDeServicio.getSelectedIndex() > 0 || !txt_CatPort_15_Merca_TransFerrov_NombreAseg.getValue().equals("") || !txt_CatPort_15_Merca_TransFerrov_NumPolizaSeguro.getValue().equals("") || !txt_CatPort_15_Merca_TransFerrov_Concesionario.getValue().equals("")){
					
			if(!ValidaCartaPorte_15()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}
					
		if(!txt_CatPort_16_Merca_TransFerrov_DerePso_TipoDerechoDePaso.getValue().equals("")|| !txt_CatPort_16_Merca_TransFerrov_DerePso_KilometrajePagado.getValue().equals("") ){

			if(!ValidaCartaPorte_16()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}

		if(lb_CatPort_17_Merca_TransFerrov_Carro_TipoCarro.getSelectedIndex() > 0 || !txt_CatPort_17_Merca_TransFerrov_Carro_MatriculaCarro.getValue().equals("") || !txt_CatPort_17_Merca_TransFerrov_Carro_GuiaCarro.getValue().equals("") || !txt_CatPort_17_Merca_TransFerrov_Carro_ToneladasNetasCarro.getValue().equals("") ){

			if(!ValidaCartaPorte_17()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}

		if(lb_CatPort_18_Merca_TransFerrov_Carro_Cont_TipoContenedor.getSelectedIndex() > 0 || !txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoContenedorVacio.getValue().equals("") || !txt_CatPort_18_Merca_TransFerrov_Carro_Cont_PesoNetoMercancia.getValue().equals("")){

			if(!ValidaCartaPorte_18()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}

		if(lb_CatPort_19_FigTrans_CveTransporte.getSelectedIndex() > 0){

			if(!ValidaCartaPorte_19()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}

		if(!txt_CatPort_21_FigTrans_Oper_Domi_Calle.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_NumeroExterior.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_NumeroInterior.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Colonia.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Localidad.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Referencia.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Municipio.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Estado.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_Pais.getValue().equals("") || !txt_CatPort_21_FigTrans_Oper_Domi_CodigoPostal.getValue().equals("")){

			if(!ValidaCartaPorte_21()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}

		if(!txt_CatPort_23_FigTrans_Prop_Domi_Calle.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_NumeroExterior.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_NumeroInterior.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Colonia.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Localidad.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Referencia.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Municipio.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Estado.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_Pais.getValue().equals("") || !txt_CatPort_23_FigTrans_Prop_Domi_CodigoPostal.getValue().equals("")){

			if(!ValidaCartaPorte_23()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}

		if(!txt_CatPort_25_FigTrans_Arr_Domi_Calle.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__NumeroExterior.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__NumeroInterior.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Colonia.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Localidad.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Referencia.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Municipio.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Estado.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__Pais.getValue().equals("") || !txt_CatPort_25_FigTrans_Arr_Domi__CodigoPostal.getValue().equals("")){

			if(!ValidaCartaPorte_25()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}

		if(!txt_CatPort_27_FigTrans_Noti_Domi_Calle.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__NumeroExterior.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__NumeroInterior.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Colonia.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Localidad.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Referencia.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Municipio.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Estado.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__Pais.getValue().equals("") || !txt_CatPort_27_FigTrans_Noti_Domi__CodigoPostal.getValue().equals("")){

			if(!ValidaCartaPorte_27()){
				valida_Carta_Porte = false;
				return valida_Carta_Porte ;
			}

		}
		
		return valida_Carta_Porte;
	}
	/**
	 * Stores the init values in mem vars. <br>
	 */
	private void doStoreInitValues() {

		if (logger.isDebugEnabled()) {
			logger.debug("--> doStoreInitValues");
		}
	}

	/**
	 * Resets the init values from mem vars. <br>
	 */
	private void doResetInitValues() {
//		usrLoginname.setValue(oldVar_usrLoginname);

	}

	/**
	 * Checks, if data are changed since the last call of <br>
	 * doStoreInitData() . <br>
	 * 
	 * @return true, if data are changed, otherwise false
	 */
	private boolean isDataChanged() {
		boolean changed = false;

//		if (oldVar_usrLoginname != txt_UserName.getValue()) {
//			changed = true;
//		}
		return changed;
	}

	/**
	 * Sets the Validation by setting the accordingly constraints to the fields.
	 */
	private void doSetValidation() {

		setValidationOn(true);

//		usrLoginname.setConstraint("NO EMPTY");

	}

	/**
	 * Disables the Validation by setting empty constraints.
	 */
	private void doRemoveValidation() {

		setValidationOn(false);

//		usrLoginname.setConstraint("");

	}

	/**
	 * Set the components for edit mode. <br>
	 */
	private void doEdit() {
		btn_Actualizar.setVisible(false);
//		txt_UserName.setReadonly(false);
//		txt_nombre.setReadonly(false);
		txt_CatPort_02_Ubi_Ori_NumEstacion.setReadonly(false);
//		txt_mail.setReadonly(false);
//		usrEnabled.setDisabled(false);
//		check_admin.setDisabled(false);

		btn_Modificar.setVisible(false);

		// remember the old vars
		doStoreInitValues();
	}

	/**
	 * Set the components to ReadOnly. <br>
	 */
	public void doReadOnly() {

		txt_Autorizador.setReadonly(true);
		db_TipoCambio.setReadonly(true);
		tb_Identi_OC.setReadonly(true);
		txt_CatPort_02_Ubi_Ori_NumEstacion.setReadonly(true);
		bd_FechaEntrega.setReadonly(true);
		bd_FechaMax.setReadonly(true);
		txt_Autorizador_Area.setReadonly(true);
		cb_Iva.setDisabled(true);
		cb_Moneda.setDisabled(true);
		cb_tasa.setDisabled(true);
		lb_cc.setDisabled(true);
		lb_Cuentas.setDisabled(true);
		lb_Departamento.setDisabled(true);
		lb_Estatus_Orden.setDisabled(true);
		lb_FormaPagoFactDialog.setDisabled(true);
		lb_MetodoPagoFactDialog.setDisabled(true);
		lb_UsoDialog.setDisabled(true);
		lb_Ordenes.setDisabled(true);
//		tb_bancoDialog.setReadonly(true);
//		tb_DocRPDialog.setReadonly(true);
		db_TipoCambio.setReadonly(true);
		txt_Documento1.setReadonly(true);
		txt_Documento2.setReadonly(true);
		txt_Documento3.setReadonly(true);
		tb_ObservacionesFactura.setReadonly(true);
		btnAgregaProdLista.setDisabled(true);
		btnBorraProd.setDisabled(true);
//		txt_Documento4.setReadonly(true);
		Radio_autoriza_Area.setDisabled(true);
		Radio_no_autoriza_Area.setDisabled(true);
		Radio_autoriza.setDisabled(true);
		Radio_no_autoriza.setDisabled(true);
//		usrEnabled.setDisabled(true);
//		check_admin.setDisabled(true);

	}

	/**
	 * Clears the components values. <br>
	 */
	public void doClear() {

		// temporarely disable the validation to allow the field's clearing
//		doRemoveValidation();
		
		

//		txt_mail.setValue("");
//		txt_nombre.setValue("");
		txt_CatPort_02_Ubi_Ori_NumEstacion.setValue("");
//		txt_UserName.setValue("");
//		usrEnabled.setChecked(true);
//		check_admin.setChecked(true);
		
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++++ crud operations +++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	/**
	 * Create a new TbSecUser object. <br>
	 */
	private void doNew() {

		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
//		setL_obComprobanteDetalle(new TbDetalleComprobante());

//		setPersona(new TbPersona());
		
//		setDireccion(new TbDireccion());

		// these comps needed to be init
//		usrEnabled.setChecked(false);

		doClear(); // clear all commponents
		doEdit(); // edit mode

		btnCtrl.setBtnStatus_New();

		// remember the old vars
		doStoreInitValues();
	}

	/**
	 * Saves the components to table. <br>
	 * @throws Exception 
	 */
//	public void doSave() throws Exception {
//		
//		l_obUser = UserServ.getUserByUsername(Usuario);
//		tbcorp = UserServ.getTbCorporativo(l_obUser.getIdCorporativo());
//		
////		boolean autoriza = false;
//		boolean autoriza_Area = false;
//		
//		TbPersona personaUser = UserServ.getPersona(l_obUser);
//				
//		MailSender ms = new MailSender();
//		MailSender msAuto = new MailSender();
//		MailSender msAutoArea = new MailSender();
//		MailSender msAutoUser = new MailSender();
//		
//		if(doValida()){
//		
//		
//				
//				TbDetalleComprobante obDetaComp = new TbDetalleComprobante();
//				
//				TbProductoComprobante obProdCompro = new TbProductoComprobante();
//								
//				obDetaComp.setIdProveedor(Integer.parseInt (IdProveedor.getValue()));
//				obDetaComp.setFechaEntrega(new Timestamp(bd_FechaEntrega.getValue().getTime()));
//				obDetaComp.setIdUserAutoriza(Integer.parseInt (IdUsuarioAuto.getValue()));
//				
//				obDetaComp.setIdent_oc(tb_Identi_OC.getValue());
//				
//				
//				Listitem item = cb_Iva.getSelectedItem();
//				ListModelList lml1 = (ListModelList) cb_Iva.getListModel();
//				
//				item = cb_Iva.getSelectedItem();
//				lml1 = (ListModelList) cb_Iva.getListModel();
//				ComboFactory comboFactory = (ComboFactory) lml1.get(item.getIndex());
//				
//				System.out.println("Iva comboFactory.getId:"+comboFactory.getId());
//				
//				
//				
//				
//				item = cb_Iva.getSelectedItem();
//				lml1 = (ListModelList) cb_Iva.getListModel();
//		//		item = lb_GpoCliente.getSelectedItem();
//				if(item!=null)
//				{
//					lml1 = (ListModelList) cb_Iva.getListModel();
//					
//					comboFactory = (ComboFactory) lml1.get(item.getIndex());
//					if(comboFactory.getId()!=-1)
//					{
//						System.out.println("Iva comboFactory.getId 2:"+comboFactory.getId());
//						obDetaComp.setIva(comboFactory.getId()+"");
//					}
//				}
//				
//				
//				item = cb_tasa.getSelectedItem();
//				if(item!=null)		{
//					lml1 = (ListModelList) cb_tasa.getListModel();
//					comboFactory = (ComboFactory) lml1.get(item.getIndex());
//					if(comboFactory.getId()!=-1)
//					{
//		//				getTb_Rol().setIdRol(comboFactory.getId());
//						System.out.println("Combofactory.getid Tasa: " + comboFactory.getId());
//						System.out.println("Combofactory.getNombre Tasa:: " + comboFactory.getNombre());
//		//				anrol.setIdRol(comboFactory.getId());
//		//				anPerfilRol.setTbRol(anrol);
//		//				getCliente().setTbGrupoCliente(getGrupoCliente());
//						obDetaComp.setTasa(comboFactory.getId()+"");
//					}
//				}
//				
//				item = cb_Moneda.getSelectedItem();
//				if(item!=null)		{
//					lml1 = (ListModelList) cb_Moneda.getListModel();
//					comboFactory = (ComboFactory) lml1.get(item.getIndex());
//					if(comboFactory.getId()!=-1)
//					{
//		//				getTb_Rol().setIdRol(comboFactory.getId());
//						System.out.println("Combofactory.getid Moneda: " + comboFactory.getId());
//						System.out.println("Combofactory.getNombre Moneda:: " + comboFactory.getNombre());
//		//				anrol.setIdRol(comboFactory.getId());
//		//				anPerfilRol.setTbRol(anrol);
//		//				getCliente().setTbGrupoCliente(getGrupoCliente());
//						obDetaComp.setMoneda(comboFactory.getId()+"");
//					}
//				}
//				
//				item = lb_cc.getSelectedItem();
//				if(item!=null)		{
//					lml1 = (ListModelList) lb_cc.getListModel();
//					comboFactory = (ComboFactory) lml1.get(item.getIndex());
//					if(comboFactory.getId()!=-1)
//					{
//		//				getTb_Rol().setIdRol(comboFactory.getId());
//						System.out.println("Combofactory.getid cc: " + comboFactory.getId());
//						System.out.println("Combofactory.getNombre cc:: " + comboFactory.getNombre());
//		//				anrol.setIdRol(comboFactory.getId());
//		//				anPerfilRol.setTbRol(anrol);
//		//				getCliente().setTbGrupoCliente(getGrupoCliente());
//		//				obDetaComp.setMoneda(comboFactory.getId()+"");
//						obDetaComp.setCc(comboFactory.getId()+"");
//					}
//				}
//				
//				obDetaComp.setTotalLetraOrden(tb_TotalLetraFactura.getValue());
//				obDetaComp.setTotal(Double.parseDouble(tb_TotalFactura.getValue()));
//				obDetaComp.setSubtotalOrden(Double.parseDouble(tb_SubtotalFactura.getValue()));
//				obDetaComp.setIvaOrden(Double.parseDouble(tb_IvaFactura.getValue()));
//				
//				if(!tb_RetIvaFactura.getValue().equals("")){
//					obDetaComp.setRetOrden(Double.parseDouble(tb_RetIvaFactura.getValue()));
//				}
//				
//				item = lb_FormaPagoFactDialog.getSelectedItem();
//				if(item!=null)		{
//					lml1 = (ListModelList) lb_FormaPagoFactDialog.getListModel();
//					comboFactory = (ComboFactory) lml1.get(item.getIndex());
//					if(comboFactory.getId()!=-1)
//					{
//		//				getTb_Rol().setIdRol(comboFactory.getId());
//						System.out.println("Combofactory.getid Forma: " + comboFactory.getId());
//						System.out.println("Combofactory.getNombre Forma:: " + comboFactory.getNombre());
//		//				anrol.setIdRol(comboFactory.getId());
//		//				anPerfilRol.setTbRol(anrol);
//		//				getCliente().setTbGrupoCliente(getGrupoCliente());
//		//				obDetaComp.setMoneda(comboFactory.getId()+"");
//						obDetaComp.setFormadePago(comboFactory.getId()+"");
//					}
//				}
//		
//		
//				item = lb_MetodoPagoFactDialog.getSelectedItem();
//				if(item!=null)		{
//					lml1 = (ListModelList) lb_MetodoPagoFactDialog.getListModel();
//					comboFactory = (ComboFactory) lml1.get(item.getIndex());
//					if(comboFactory.getId()!=-1)
//					{
//		//				getTb_Rol().setIdRol(comboFactory.getId());
//						System.out.println("Combofactory.getid Metod: " + comboFactory.getId());
//						System.out.println("Combofactory.getNombre metodo:: " + comboFactory.getNombre());
//		//				
//						obDetaComp.setMetododepago(comboFactory.getId()+"");
//					}
//				}
//				
//				item = lb_UsoDialog.getSelectedItem();
//				if(item!=null)		{
//					lml1 = (ListModelList) lb_UsoDialog.getListModel();
//					comboFactory = (ComboFactory) lml1.get(item.getIndex());
//					if(comboFactory.getId()!=-1)
//					{
//		//				getTb_Rol().setIdRol(comboFactory.getId());
//						System.out.println("Combofactory.getid Metod: " + comboFactory.getId());
//						System.out.println("Combofactory.getNombre metodo:: " + comboFactory.getNombre());
//		//				
//						obDetaComp.setUso_Cfdi(comboFactory.getId());
//					}
//				}
//				
//				
//		//		obDetaComp.setBanco(tb_bancoDialog.getValue());
//		//		obDetaComp.setNumerocuenta(tb_DocRPDialog.getValue());
//				
//				item = lb_Cuentas.getSelectedItem();
//				if(item!=null)		{
//					lml1 = (ListModelList) lb_Cuentas.getListModel();
//					comboFactory = (ComboFactory) lml1.get(item.getIndex());
//					if(comboFactory.getId()!=-1)
//					{
//		//				getTb_Rol().setIdRol(comboFactory.getId());
//						System.out.println("Combofactory.getid Cuenta: " + comboFactory.getId());
//						System.out.println("Combofactory.getNombre Cuenta:: " + comboFactory.getNombre());
//		//				
//						obDetaComp.setCuenta(comboFactory.getId()+"");
//					}
//				}
//				
//				
//				
//				
//				if (lb_Cuentas.getSelectedIndex() < 0){
//					
//					Messagebox.show("Selecciona la Cuenta", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//					return;
//					
//				}else{
//					
//					Listitem item2 = lb_Cuentas.getSelectedItem();
//					ListModelList lml12 = (ListModelList) lb_Cuentas.getListModel();
//					ComboFactory comboFactory2 = (ComboFactory) lml12.get(item2.getIndex());
//					
//					TbCuenta obcuenta = ventaServ.getCuentaTb(comboFactory2.getId());
//					
//		
//		
//					if(obcuenta.getOrden() == 1){
//						
//						if (lb_Ordenes.getSelectedIndex() < 0){
//							
//							Messagebox.show("Selecciona la Orden", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//							return;
//							
//						}else{
//							
//							item = lb_Ordenes.getSelectedItem();
//							if(item!=null)		{
//								lml1 = (ListModelList) lb_Ordenes.getListModel();
//								comboFactory = (ComboFactory) lml1.get(item.getIndex());
//								if(comboFactory.getId()!=-1)
//								{
//		//							getTb_Rol().setIdRol(comboFactory.getId());
//									System.out.println("Combofactory.getid Ordenes: " + comboFactory.getId());
//									System.out.println("Combofactory.getNombre Ordenes:: " + comboFactory.getNombre());
//		//							
//									
//									obDetaComp.setOrden(comboFactory.getId()+"");
//								}
//							}
//							
//						}
//						
//					}
//					
//					if(obcuenta.getDepartamento() == 1){
//						
//						if (lb_Departamento.getSelectedIndex() < 0){
//							
//							Messagebox.show("Selecciona el departamento", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//							return;
//							
//						}else{
//							
//							item = lb_Departamento.getSelectedItem();
//							if(item!=null)		{
//								lml1 = (ListModelList) lb_Departamento.getListModel();
//								comboFactory = (ComboFactory) lml1.get(item.getIndex());
//								if(comboFactory.getId()!=-1)
//								{
//		//							getTb_Rol().setIdRol(comboFactory.getId());
//									System.out.println("Combofactory.getid Departamento: " + comboFactory.getId());
//									System.out.println("Combofactory.getNombre Departamento:: " + comboFactory.getNombre());
//		//							
//									
//		//							obDetaComp.setOrden();
//									obDetaComp.setDepartamento(comboFactory.getId()+"");
//								}
//							}
//							
//						}
//					
//				}
//					
//				}
//				
//				
//				
//				obDetaComp.setFechaMaxEntre(new Timestamp(bd_FechaMax.getValue().getTime()));
//				
//				
//				if(!tb_TotalFactura.getValue().equals("") && tb_TotalFactura.getValue() != null){
//					
//					
//					double doble = Double.parseDouble(tb_TotalFactura.getValue());
//					if(doble > getcorp.getCantidadMax()){
//						if (txt_Autorizador_Area.getValue().equals("")){
//							
//							Messagebox.show("Selecciona la persona que realizara una segunda Autorizacion de la OC", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//							autoriza_Area = false;
//							return;
//							
//						}else{
//							obDetaComp.setIdUserAutorizaArea(Integer.parseInt (IdUsuarioAutoArea.getValue()));
//							autoriza_Area = true;
//						}
//					}
//						
//				}
//				
//		
//				Listitem itemMoneda = cb_Moneda.getSelectedItem();
//				String nombre = itemMoneda.getLabel();
//				if( cb_Moneda.getSelectedIndex() >= 0){
//					if (nombre.startsWith("MXN")){ 
//						
//					}else{
//						if (db_TipoCambio.getValue().equals("")){
//							
//							Messagebox.show("Ingresa el Tipo de Cambio", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//							return;
//							
//						}else{
//							obDetaComp.setTipocambio(db_TipoCambio.getValue());
//						}
//							
//					}
//				}
//				
//				
//				obDetaComp.setObservaciones(tb_ObservacionesFactura.getValue());
//				obDetaComp.setEstatusFactura("OC Creada");
//				obDetaComp.setTbUsuario(l_obUser);
//				obDetaComp.setTbCorporativo(tbcorp);
//				
//				Date date = new Date();  
//		        Timestamp ts=new Timestamp(date.getTime()); 
//				obDetaComp.setFechaCreacion(ts);
//				obDetaComp.setNuautorizadoCxP(0);
//				
//				
//		//		if(!txt_Documento1.getValue().equalsIgnoreCase("")){
//		//			byte bytesP [] = mediaArchivo1.getByteData();
//		//			 Blob dataBlobP = new SerialBlob(bytesP);
//		//			 obDetaComp.setDocumento1(dataBlobP);
//		//		}
//		//		if(!txt_Documento2.getValue().equalsIgnoreCase("")){
//		//			byte bytesP2 [] = mediaArchivo2.getByteData();
//		//			 Blob dataBlobP2 = new SerialBlob(bytesP2);
//		//			 obDetaComp.setDocumento2(dataBlobP2);
//		//		}
//		//		if(!txt_Documento3.getValue().equalsIgnoreCase("")){
//		//			byte bytesP3 [] = mediaArchivo3.getByteData();
//		//			 Blob dataBlobP3 = new SerialBlob(bytesP3);
//		//			 obDetaComp.setDocumento3(dataBlobP3);
//		//		}
//				
//				
//				//Para cada detalle se inserta en BD, se modifica el inventario y se inserta mov. a inventario
//				
//				
//				try {
//					
//					//Se guarda la orden de compra y se obtiene el ID de la Orden
//					int id_Orden_Compra = ventaserv.saveDetalleCompro(obDetaComp);
//					
//					//Se valida si esta lleno el documento de cotizaciÃ³n para que se actualice el Documento
//					if(!txt_Documento1.getValue().equalsIgnoreCase("") && !Ruta_mediaArchivo1.equals("")){
//						//Se guarda el Archivo en la tabla RFC se hace un UPDATE de un registro ya guardado
//						ventaServ.setUpdateDocumentORden(Ruta_mediaArchivo1,"documento1",id_Orden_Compra);
//						try{
//							//Una vez guardado de elimina el archivo temporal que se guardo en la carpeta tomcat
//							dodelete(Ruta_mediaArchivo1);
//							
//						}catch (Exception e) {
//							System.out.println(e.toString());
//						}
//					}
//					
//					//Se valida si esta lleno el documento2 para que se actualice el Documento
//					if(!txt_Documento2.getValue().equalsIgnoreCase("") && !Ruta_mediaArchivo2.equals("")){
//						//Se guarda el Archivo en la tabla RFC se hace un UPDATE de un registro ya guardado
//						ventaServ.setUpdateDocumentORden(Ruta_mediaArchivo2,"documento2",id_Orden_Compra);
//						try{
//							//Una vez guardado de elimina el archivo temporal que se guardo en la carpeta tomcat
//							dodelete(Ruta_mediaArchivo2);
//							
//						}catch (Exception e) {
//							System.out.println(e.toString());
//						}
//					}
//					//Se valida si esta lleno el documento 3 para que se actualice el Documento
//					if(!txt_Documento3.getValue().equalsIgnoreCase("") && !Ruta_mediaArchivo3.equals("")){
//						//Se guarda el Archivo en la tabla RFC se hace un UPDATE de un registro ya guardado
//						ventaServ.setUpdateDocumentORden(Ruta_mediaArchivo3,"documento3",id_Orden_Compra);
//						try{
//							//Una vez guardado de elimina el archivo temporal que se guardo en la carpeta tomcat
//							dodelete(Ruta_mediaArchivo3);
//							
//						}catch (Exception e) {
//							System.out.println(e.toString());
//						}
//					}
//					
//								
////					for(int indice = 0;indice<detalle.size();indice++)
////					{
////					    System.out.println("Valor de la cadena: "+ detalle.get(indice));
////					    
////					    String[] LisProduct = detalle.get(indice).toString().split(";");
////					    
////					   
////							for (int i = 0; i < LisProduct.length; i++) {
////										
////								
////								 LisProduct = LisProduct[i].split(",");
////								 
////								 if (LisProduct.length > 5){
////									 
////								 for (int A = 0; A < LisProduct.length; A++) {
////		//							 System.out.println(LisProduct[A].replace("[", ""));
////									 System.out.println("VAlor de A: "+ A + " Valor de la lista: " + LisProduct[A]);
////									 
////									 
////										
////								 }
////								 
////								 						 
////								 obProdCompro.setIdDetalleComprobante(id_Orden_Compra);
////								 String  cantidad = LisProduct[0].replace("[", "");
////								 cantidad = cantidad.substring(0,cantidad.indexOf("."));
////								 System.out.println("Cantidad: "+cantidad);
////								 obProdCompro.setCantidad(Integer.parseInt(cantidad));
////								 
//////								 String[] arrayClave = LisProduct[1].split("=");
////								 
////								// En este momento tenemos un array en el que cada elemento es un color.
//////								for (int x = 0; x < arrayClave.length; x++) {
//////									System.out.println(arrayClave[x]);
//////									
//////								}
////								 obProdCompro.setClave(LisProduct[1]);
////								 System.out.println("1: "+LisProduct[1]);
////								 
////		//						 obProdCompro.setProducto(LisProduct[1]);
////								 obProdCompro.setDescripcion(LisProduct[2]);
////								 System.out.println("2: "+LisProduct[2]);
////								 obProdCompro.setJustificacion(LisProduct[3]);
////								 System.out.println("3: "+LisProduct[3]);
////								 System.out.println("4: "+LisProduct[4]);
////								 System.out.println("5: "+LisProduct[5]);
////		//						 Double.parseDouble(tb_TotalFactura.getValue());
////								 System.out.println("3: "+LisProduct[3]);
////								 obProdCompro.setUnidad(LisProduct[4]);
////								 obProdCompro.setImporte(Double.parseDouble(LisProduct[5]));
////								 System.out.println("6: "+LisProduct[6]);
////								 System.out.println("7: "+LisProduct[7]);
////								 System.out.println("8: "+LisProduct[8]);
////		//						 System.out.println("PrecioCompl: "+LisProduct[9]);
////		//						 System.out.println("importeCompleto1: "+LisProduct[10]);
////		//						 System.out.println("importeCompleto1: "+LisProduct[11]);
////		//						 System.out.println("IdProducto: "+LisProduct[7]);
////		//						 obProductServi = ventaServ.getTbProdServ(Integer.parseInt(LisProduct[7].trim()));
////		//						 obProdCompro.setTbProductoServicio(obProductServi);
////		//						 System.out.println("Id_Prod: "+LisProduct[12]);
////		//						 System.out.println("Id_Prod: "+LisProduct[13]);
////		//						 System.out.println("Iva1: "+LisProduct[14]);
////		//						 System.out.println("Iva1: "+LisProduct[15]);
////								 System.out.println("Impuestos: "+LisProduct[11]);
////								 obProdCompro.setImpuestos(Double.parseDouble(LisProduct[11]));
////								 obProdCompro.setTbCorporativo(tbcorp);
////								 obProdCompro.setTbUsuario(l_obUser);
////		//						 System.out.println("Referencia: "+LisProduct[16]);
////		//						 System.out.println("Referencia: "+LisProduct[17]);
////		//						 System.out.println("PorcenIva1: "+LisProduct[18]);
////		//						 System.out.println("PorcenIva1: "+LisProduct[19]);
////		//						 System.out.println("Total: "+LisProduct[20]);
////		//						 System.out.println("Total: "+LisProduct[21]);
////		//						 System.out.println("IvaUnidad: "+LisProduct[22]);
////		//						 System.out.println("IvaUnidad: "+LisProduct[23]);
////		//						 System.out.println("IvaTotal: "+LisProduct[24]);
////		//						 System.out.println("IvaTotal: "+LisProduct[25].replace("]", ""));
////								 
////								 ventaServ.saveProductoCompro(obProdCompro);
////								 }
////								 
////								//obProdCompro
////								
////								
////		//						idAlmacen = new Integer(almacenCantidad[i].substring(0,
////		//								almacenCantidad[i].indexOf(",")));
////		//						cadenaAux = almacenCantidad[i].substring(almacenCantidad[i]
////		//								.indexOf(",") + 1, almacenCantidad[i].length());
////		//						cantidadAlmacen = new Double(cadenaAux.substring(0, cadenaAux
////		//								.indexOf(",")));
////		//						exedentes = new Double(cadenaAux.substring(cadenaAux
////		//								.indexOf(",") + 1, cadenaAux.length()));
////		//						almacen = (TbAlmacen) getVentaCFDIService().getBusinessObjectPorId(
////		//								idAlmacen, TbAlmacen.class, "nukidAlmacen");
////							}
////							
////					}
//					
//		//			ms.setTo(txt_CorreoProv.getValue().trim());
//		//			ms.setMessage("Se Creo una Orden de Compra para Usted favor de consultarla en el portal =  http://127.0.0.1:8080/Portal_Rolex/ ");
//		//			
//		//			if(ms.sendMessage())
//		//			{
//		////				us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//		//			
//		//				Messagebox.show("Correo enviado correctamente al proveedor", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//		////				Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//		//			}
//		//			else
//		//			{
//		//				Messagebox.show("No se pudo Realizar la operaciÃ³n del correo al Proveedor", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//		//				
//		//			}
//		//			
//					msAuto.setTo(txt_CorreoAuto.getValue().trim());
//					msAuto.setMessage("Se Creo una Orden de Compra para Que la Autorize favor de consultarla en el portal =  "+Url_Sistema);
//					
//					if(msAuto.sendMessageUser())
//					{
//		//				us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//					
//						Messagebox.show("Correo enviado correctamente al Autorizador", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//		//				Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//					}
//					else
//					{
//						Messagebox.show("No se pudo Realizar la operaciÃ³n del correo al Autorizador", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//						
//					}
//					
//		//			String Cuantos = "(1 de 1) Autorizaciones";
//		//			if(autoriza_Area = true){
//		//				Cuantos = "(1 de 2) Autorizaciones";
//		//			}
//						
//					
//					if(personaUser != null){
//						if(!personaUser.getChEmail().equals("")){
//							msAutoUser.setTo(personaUser.getChEmail());
//							
//							
//							msAutoUser.setMessage("Se envio la orden de compra al Autorizador en espera de Actualizacion ");
//							
//							if(msAutoUser.sendMessageUser())
//							{
//				//				us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//							
//								Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//				//				Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//							}
//							else
//							{
//								Messagebox.show("No se pudo Realizar la operaciÃ³n del correo al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//								
//							}
//						}
//						else{
//							msAutoUser.setTo(Email_Sistema);
//							
//							
//							msAutoUser.setMessage("Se envio la orden de compra al Autorizador en espera de Actualizacion ");
//							
//							if(msAutoUser.sendMessageUser())
//							{
//				//				us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//							
//								Messagebox.show("Correo enviado correctamente al Usuario Generico por que es Super Administrador", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//				//				Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//							}
//							else
//							{
//								Messagebox.show("No se pudo Realizar la operaciÃ³n del correo al Autorizador", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//								
//							}
//							}
//					}else{
//						msAutoUser.setTo(Email_Sistema);
//						
//						
//						msAutoUser.setMessage("Se envio la orden de compra al Autorizador en espera de Actualizacion ");
//						
//						if(msAutoUser.sendMessageUser())
//						{
//			//				us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//						
//							Messagebox.show("Correo enviado correctamente al Usuario Generico", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			//				Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//						}
//						else
//						{
//							Messagebox.show("No se pudo Realizar la operaciÃ³n del correo al Autorizador", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//							
//						}
//					}
//		//			
//		//			if(!txt_CorreoAutoArea.getValue().equals("")){
//		//				msAutoArea.setTo(txt_CorreoAutoArea.getValue().trim());
//		//				msAutoArea.setMessage("Se Creo una Orden de Compra para Que la Autorize favor de consultarla en el portal =  http://127.0.0.1:8080/Portal_Rolex/ ");
//		//				
//		//				if(msAutoArea.sendMessage())
//		//				{
//		//	//				us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//		//				
//		//					Messagebox.show("Correo enviado correctamente al Autorizador del Area", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//		//	//				Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//		//				}
//		//				else
//		//				{
//		//					Messagebox.show("No se pudo Realizar la operaciÃ³n del correo al Autorizador del Area", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//		//					
//		//				}
//		//			}
//		//			
//					
//		//			ListModelList lml = (ListModelList) listBoxOrden.getListModel();
//		//
//		//			// Check if the object is new or updated
//		//			// -1 means that the obj is not in the list, so it's new.
//		//			if (lml.indexOf(obDetaComp) == -1) {
//		//				lml.add(obDetaComp);
//		//			} else {
//		//				lml.set(lml.indexOf(obDetaComp), obDetaComp);
//		//			}
//					
//					msAutoUser.setTo(tbcorp.getChCorreo());
//					
//					;
//					if(autoriza_Area = true){
//						
//						TbPersona Per_Direccion =userserv.getPersonaAuto(obDetaComp.getIdUserAutorizaArea());
//					
//					msAutoUser.setMessage("Se creo una Orden de Compra con un Total de : "+ obDetaComp.getTotal() + " El Usuario Autorizador es: " + Per_Direccion.getChNombre() +" "+Per_Direccion.getChapPaterno()+" "+Per_Direccion.getChapMaterno());
//					
//					if(msAutoUser.sendMessageUser())
//					{
//		//				us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//					
//						Messagebox.show("Correo enviado correctamente a la DirecciÃ³n", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//		//				Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//					}
//					else
//					{
//						Messagebox.show("No se pudo Realizar la operaciÃ³n del correo a la Direcci¡", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//						
//					}
//					}
////					obDetaComp.setIdUserAutorizaArea
//					
//					Messagebox.show("operaciÃ³n Realizada Correctamente", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			
//				doResetInitValues();
//		
//				doReadOnly();
//				
//				btn_Modificar.setVisible(false);
//				btn_Cerrar.setVisible(true);
//				btn_Guardar.setVisible(false);
//				
//				return;
//			}
//	}
	
	private String  saveFile(Media media, String nombre) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		String pathImagenes ="";
		String DireccionCompleta ="";
		
		try {
			
			InputStream fin = media.getStreamData();
			in = new BufferedInputStream(fin);


//			File baseDir = new File("");
			
//			
			String realString=Sessions.getCurrent().getWebApp().getRealPath("");
			String l_stCreKey=realString.substring(0, realString.lastIndexOf(File.separator))+File.separator;
		
//			if(pathImagenes.lastIndexOf("bin")>-1)
//				pathImagenes = pathImagenes.substring(0,pathImagenes.lastIndexOf("bin"));
//			else
//				pathImagenes+=File.separator;
			
			pathImagenes = l_stCreKey +"logos";

			File dirImagenes =  new File(pathImagenes);

			if (!dirImagenes.exists()) {
				dirImagenes.mkdirs();
			}

			File file = null;
			file = new File(pathImagenes + File.separator + nombre);

			OutputStream fout = new FileOutputStream(file);
			out = new BufferedOutputStream(fout);
			byte buffer[] = new byte[1024];
			int ch = in.read(buffer);
			while (ch != -1) {
				out.write(buffer, 0, ch);
				ch = in.read(buffer);
			}
			logger.info("sucessed upload :" + media.getName());
			DireccionCompleta = pathImagenes + File.separator + nombre;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (out != null)
					out.close();

				if (in != null)
					in.close();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		return DireccionCompleta;
	}
	
	public boolean doValida() throws InterruptedException{
		
//Messagebox.show("operaciÃ³n realizada correctamente", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
		boolean valida = true;
		
		
		if (tb_Identi_OC.getValue().equals("")){
			
			Messagebox.show("Ingresa una palabra Clave para identificar la Orden de Compra", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		if (txt_CatPort_02_Ubi_Ori_NumEstacion.getValue().equals("")){
			
			Messagebox.show("Selecciona Un Proveedor", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		if (bd_FechaEntrega.getValue() == null){
			
			Messagebox.show("Selecciona la fecha de Entrega", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		if (bd_FechaMax.getValue() == null){
			
			Messagebox.show("Selecciona la fecha Maxima de Entrega", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
//		if (lb_Estatus_Orden.getSelectedIndex() < 0){
//			
//			Messagebox.show("Selecciona la fecha Maxima de Entrega", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			return;
//			
//		}
		if (txt_Documento1.getValue().equals("")){
			
			Messagebox.show("Favor de llenar por lo menos el documento de CotizaciÃ³n", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		if (txt_Autorizador.getValue().equals("")){
			
			Messagebox.show("Selecciona la persona que Autorizara la OC", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		if(!tb_TotalFactura.getValue().equals("") && tb_TotalFactura.getValue() != null){
			
			
			double doble = Double.parseDouble(tb_TotalFactura.getValue());
//			if(doble > getcorp.getCantidadMax()){
//				if (txt_Autorizador_Area.getValue().equals("")){
//					
//					Messagebox.show("Selecciona la persona que realizara una segunda Autorizacion de la OC", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//					valida = false;
//					return valida;
//					
//				}
//			}
				
		}
		
		if (cb_Iva.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona el Impuesto", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		if (cb_tasa.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona TASA", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		if (cb_Moneda.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona Moneda", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		if (lb_cc.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona la Cuenta por Cobrar", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		if (lb_FormaPagoFactDialog.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona la Forma de Pago", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}

		if (lb_MetodoPagoFactDialog.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona el Metodo de Pago", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		if (lb_UsoDialog.getSelectedIndex() < 0){
			
			Messagebox.show("Selecciona el Uso del CFDI", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
			valida = false;
			return valida;
			
		}
		
		
		Listitem itemMoneda = cb_Moneda.getSelectedItem();
		String nombre = itemMoneda.getLabel();
		if( cb_Moneda.getSelectedIndex() >= 0){
			if (nombre.startsWith("MXN")){ 
				
			}else{
				if (db_TipoCambio.getValue().equals("")){
					
					Messagebox.show("Ingresa el Tipo de Cambio", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
					valida = false;
					return valida;
					
				}
			}
		}
		
//		if (tb_bancoDialog.getValue().equals("")){
//					
//					Messagebox.show("Ingresa el nombre del Banco", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//					return;
//					
//				}
//		if (tb_DocRPDialog.getValue().equals("")){
//			
//			Messagebox.show("Ingresa el numero de cuenta o numero de tarjeta", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			return;
//			
//		}
		
		
		
//		if (lb_Cuentas.getSelectedIndex() < 0){
//			
//			Messagebox.show("Selecciona la Cuenta", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			valida = false;
//			return valida;
			
//		}else{
//			
//			Listitem item = lb_Cuentas.getSelectedItem();
//			ListModelList lml1 = (ListModelList) lb_Cuentas.getListModel();
//			ComboFactory comboFactory = (ComboFactory) lml1.get(item.getIndex());
//			
//			TbCuenta obcuenta = ventaServ.getCuentaTb(comboFactory.getId());
//			
//
//
//			if(obcuenta.getOrden() == 1){
//				
//				if (lb_Ordenes.getSelectedIndex() < 0){
//					
//					Messagebox.show("Selecciona la Orden", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//					valida = false;
//					return valida;
//					
//				}
//				
//			}
//			
//			if(obcuenta.getDepartamento() == 1){
//				
//				if (lb_Departamento.getSelectedIndex() < 0){
//					
//					Messagebox.show("Selecciona el departamento", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//					valida = false;
//					return valida;
//					
//				}
//			
//		}
//			
//		}
		
		for(int indice = 0;indice<detalle.size();indice++)
		{
		    System.out.println("Valor de la cadena: "+ detalle.get(indice));
		    
		    String[] LisProduct = detalle.get(indice).toString().split(";");
		    
		   
				for (int i = 0; i < LisProduct.length; i++) {
							
					
					 LisProduct = LisProduct[i].split(",");
					 
					 if (LisProduct.length > 5){
						 
					 for (int A = 0; A < LisProduct.length; A++) {
//						 System.out.println(LisProduct[A].replace("[", ""));
						 System.out.println("VAlor de A: "+ A + " Valor de la lista: " + LisProduct[A]);
						 
						 
							
					 }
					 
					 						 
//					 obProdCompro.setIdDetalleComprobante(id_Orden_Compra);
					 String  cantidad = LisProduct[0].replace("[", "");
					 cantidad = cantidad.substring(0,cantidad.indexOf("."));
					 System.out.println("Cantidad: "+cantidad);
					 if(cantidad.trim().equals("0")){
						 Messagebox.show("La cantidad del producto no puede ser 0", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						 valida = false;
							return valida;
					 }
//					 obProdCompro.setCantidad(Integer.parseInt(cantidad));
					 
//					 obProdCompro.setClave(LisProduct[1]);
					 System.out.println("1: "+LisProduct[1]);
					 if(LisProduct[1].trim().equals(".") || LisProduct[1].trim().equals("")){
						 Messagebox.show("La Clave del producto no puede ser vacio", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						 valida = false;
							return valida;
					 }
//					 obProdCompro.setProducto(LisProduct[1]);
//					 obProdCompro.setDescripcion(LisProduct[2]);
					 System.out.println("2: "+LisProduct[2]);
					 if(LisProduct[2].trim().equals("..")){
						 Messagebox.show("La Descripcion del producto no puede ser vacio", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						 valida = false;
							return valida;
					 }
//					 obProdCompro.setJustificacion(LisProduct[3]);
					 System.out.println("3: "+LisProduct[3]);
					 if(LisProduct[3].trim().equals("..")){
						 Messagebox.show("La Justificacion del producto no puede ser vacio", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						 valida = false;
							return valida;
					 }
					 System.out.println("4: "+LisProduct[4]);
					 if(LisProduct[4].trim().equals("0.0") || LisProduct[4].trim().equals("")){
						 Messagebox.show("La Unidad del producto no puede ser vacio", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						 valida = false;
							return valida;
					 }
					 System.out.println("5: "+LisProduct[5]);
//					 System.out.println("3: "+LisProduct[3]);
					 if(LisProduct[5].trim().equals("0.0")){
						 Messagebox.show("El Importe del producto no puede ser 0", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
						 valida = false;
							return valida;
					 }
//					 obProdCompro.setUnidad(LisProduct[4]);
//					 obProdCompro.setImporte(Double.parseDouble(LisProduct[5]));
					 System.out.println("6: "+LisProduct[6]);
					 System.out.println("7: "+LisProduct[7]);
					 System.out.println("8: "+LisProduct[8]);
					 System.out.println("Impuestos: "+LisProduct[11]);
//					 obProdCompro.setImpuestos(Double.parseDouble(LisProduct[11]));
//					 obProdCompro.setTbCorporativo(tbcorp);
//					 obProdCompro.setTbUsuario(l_obUser);
					 
//					 ventaServ.saveProductoCompro(obProdCompro);
					 }

				}
				
		}
		
	
		return valida;
		
	}

	private void doSaveParnertsUser() throws InterruptedException {
//		TbUsuario p_Use = new TbUsuario();
//		TbRfc p_Use_ID = new TbRfc();
		
//		p_Use_ID.setIdRfc(IdPartner);
//		p_Use.setIdUsuario(IdUser);
//		System.out.println("idpartner ------------------->>>>"+ p_Use_ID.getIdRfc());
//		System.out.println("IdUser ------------------->>>>"+ p_Use_ID.getIdUsuario());
		System.out.println();
		
//		p_Use.setId(p_Use_ID);
		
		try {
//			UserServ.saveParUser(p_Use);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Messagebox.show("MP-011", "ERROR", org.zkoss.zul.Messagebox.OK, "");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * when the "save" button is clicked. <br>
	 * 
	 * @param event
	 * @throws Exception 
	 */
	public void onCheck$Radiogroup_autoriza(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		System.out.println("Radio_autoriza: "+ Radio_autoriza.isChecked());
		System.out.println("Radio_no_autoriza: "+ Radio_no_autoriza.isChecked());
		
		if(Radio_autoriza.isChecked() == true){
			row_Rechazo.setVisible(false);
		}
		if(Radio_no_autoriza.isChecked() == true){
			row_Rechazo.setVisible(true);
		}

		btn_Guarda_Auto.setVisible(true);
		btn_Modificar.setVisible(false);
		
	}
	
	/**
	 * when the "save" button is clicked. <br>
	 * 
	 * @param event
	 * @throws Exception 
	 */
	public void onCheck$Radiogroup_autorizaArea(Event event) throws Exception {

		
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		System.out.println("Radio_autoriza: "+ Radio_autoriza_Area.isChecked());
		System.out.println("Radio_no_autoriza: "+ Radio_no_autoriza_Area.isChecked());
		
		if(Radio_autoriza_Area.isChecked() == true){
			row_Rechazo.setVisible(false);
		}
		if(Radio_no_autoriza_Area.isChecked() == true){
			row_Rechazo.setVisible(true);
		}
		
		btn_Guarda_Auto_Area.setVisible(true);
		btn_Modificar.setVisible(false);
	}
	
public void onClick$btn_Guarda_Auto(Event event) throws InterruptedException, SendFailedException, MessagingException {
	
//			TbDetalleComprobante detalleCompOrden = new TbDetalleComprobante();
//			TbUsuario usCreaOC = UserServ.getUserPojo(idUsuarios);
//			TbPersona personaUser = UserServ.getPersona(usCreaOC);
			MailSender ms = new MailSender();
			MailSender msAutoArea = new MailSender();
			MailSender msAutoUser = new MailSender();
			
		//	 + "\n" +
			
//			detalleCompOrden.setIdDetalleComprobante(idOrden);
			
//			if(Radio_autoriza.isChecked() == true){
////				System.out.println("Se Activa el Campo Autorizado: "+ 1);
//				detalleCompOrden.setNuautoriza(AUTORIZA_ORDEN_COMPRA_SI);
//				if(!txt_Autorizador_Area.getValue().equals("")){
//					detalleCompOrden.setEstatusFactura("OC Parcialmente Autorizada");
//										//					System.out.println("Se Guarda el Campo Estatus: "+ "OC Parcial Mente Autorizada");
////					System.out.println("Se envia un correo al Autorizador del Area: " + txt_CorreoAutoArea.getValue());
//									msAutoArea.setTo(txt_CorreoAutoArea.getValue().trim());
//									msAutoArea.setMessage("Se Creo una Orden de Compra para Que la Autorize favor de consultarla en el portal =    "+Url_Sistema);
//									
//									if(msAutoArea.sendMessageUser())
//									{
//				//						us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//									
//										Messagebox.show("Correo enviado correctamente al Autorizador del Area", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//				//						Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//									}
//									else
//									{
//										Messagebox.show("No se pudo Realizar la operaciÃ³n del correo al Autorizador", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//										
//									}
//					
//					if(personaUser != null){
//						if(!personaUser.getChEmail().equals("")){
//							System.out.println("Se envia un correo al Usuario: "+ personaUser.getChEmail() );
//							
//							msAutoArea.setTo(personaUser.getChEmail().trim());
//							msAutoArea.setMessage("Se paso la OC para validacion del Usuario Autorizador de Area: "+txt_Autorizador_Area.getValue()+" en el portal =    "+Url_Sistema);
//							
//							if(msAutoArea.sendMessageUser())
//							{
////								us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//							
//								Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////								Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//							}
//							else
//							{
//								Messagebox.show("No se pudo Realizar la operaciÃ³n del correo alUsuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//								
//							}
//						}
//					}else{
//						System.out.println("Se envia un correo al Usuario: Generico por que es Super Administrador "+Email_Sistema);
//						
//						msAutoArea.setTo(Email_Sistema);
//						msAutoArea.setMessage("Se paso la OC para validacion del Usuario Autorizador de Area: "+txt_Autorizador_Area.getValue()+" en el portal =    "+Url_Sistema);
//						
//						if(msAutoArea.sendMessageUser())
//						{
////							us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//						
//							Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////							Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//						}
//						else
//						{
//							Messagebox.show("No se pudo Realizar la operaciÃ³n del correo al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//							
//						}
//					}
//					
//					
//					
//				}else
//				{	
//					detalleCompOrden.setEstatusFactura("OC Autorizada");
//					System.out.println("Se Guarda el Campo Estatus: "+ "OC Autorizada");
//					System.out.println("Se envia un correo al Proveedor: " + txt_CorreoProv.getValue());
//
//					msAutoArea.setTo(txt_CorreoProv.getValue().trim());
//					msAutoArea.setMessage("Su Orden de compra fue aceptada, favor de consultar la informacion para la creacion de la Factura");
//					
//					if(msAutoArea.sendMessageUser())
//					{
////						us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//					
//						Messagebox.show("Correo enviado correctamente al Proveedor", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////						Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//					}
//					else
//					{
//						Messagebox.show("No se pudo Realizar la operaciÃ³n del correo al Proveedor", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//						
//					}
//					if(personaUser != null){
//						if(!personaUser.getChEmail().equals("")){
//							System.out.println("Se envia un correo al Usuario: "+ personaUser.getChEmail() );
////							Messagebox.show("Se Guarda el Campo Estatus: "+ "OC Autorizada" + "\n" +"Se envia un correo al Proveedor: " + txt_CorreoProv.getValue() + "\n" +"Se envia un correo al Usuario: "+ personaUser.getChEmail(), "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////							return;
//							msAutoArea.setTo(personaUser.getChEmail().trim());
//							msAutoArea.setMessage("Su Orden de compra fue aceptada");
//							
//							if(msAutoArea.sendMessageUser())
//							{
////								us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//							
//								Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////								Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//							}
//							else
//							{
//								Messagebox.show("No se pudo Realizar la operaciÃ³n del correo del usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//								
//							}
//						}
//					}else{
//						System.out.println("Se envia un correo al Usuario: Generico por que es Super Administrador = "+Email_Sistema);
//						msAutoArea.setTo(Email_Sistema);
//						msAutoArea.setMessage("Su Orden de compra fue aceptada");
//						
//						if(msAutoArea.sendMessageUser())
//						{
////							us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//						
//							Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////							Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//						}
//						else
//						{
//							Messagebox.show("No se pudo Realizar la operaciÃ³n del correo del usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//							
//						}
//					}
//				}
//				try {
//					ventaServ.setUpdateAutoriza(detalleCompOrden);
//					btn_Guarda_Auto.setVisible(false);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
			
			
			
			
			
//			if(Radio_no_autoriza.isChecked() == true){
//				
//				if (tb_Rechazo.getValue().equals("")){
//					
//					Messagebox.show("Ingresa el Motivo del Rechazo", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//					return;
//					
//				}
//				detalleCompOrden.setNuautoriza(AUTORIZA_ORDEN_COMPRA_NO);
//				detalleCompOrden.setEstatusFactura("OC Rechazada");
//				
//				System.out.println("Se Activa el Campo Autorizado: "+ 0);
//				
//					System.out.println("Se Guarda el Campo Estatus: "+ "OC Rechazado por el Autorizador: " + txt_Autorizador.getValue());
//		//			
//					System.out.println("Motivo del rechazo: "+ tb_Rechazo.getValue());
//					
//					if(personaUser != null){
//						if(!personaUser.getChEmail().equals("")){
//							System.out.println("Se envia un correo al Usuario: "+ personaUser.getChEmail() );
//							msAutoArea.setTo(personaUser.getChEmail());
//							msAutoArea.setMessage("Su Orden de compra fue rechazada por: "+ txt_Autorizador.getValue() + "\n" + " Motivo del rechazo: "+ tb_Rechazo.getValue());
//							
//							if(msAutoArea.sendMessageUser())
//							{
////								us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//							
//								Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////								Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//							}
//							else
//							{
//								Messagebox.show("No se pudo Realizar la operaciÃ³n del correo del usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//								
//							}
//						}
//					}else{
//						System.out.println("Se envia un correo al Usuario: Generico "+Email_Sistema);
//						msAutoArea.setTo(Email_Sistema);
//						msAutoArea.setMessage("Su Orden de compra fue rechazada por: "+ txt_Autorizador.getValue() + "\n" + " Motivo del rechazo: "+ tb_Rechazo.getValue());
//						
//						if(msAutoArea.sendMessageUser())
//						{
////							us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//						
//							Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////							Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//						}
//						else
//						{
//							Messagebox.show("No se pudo Realizar la operaciÃ³n del correo del usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//							
//						}
//					}
//					try {
//						ventaServ.setUpdateAutoriza(detalleCompOrden);
//						btn_Guarda_Auto.setVisible(false);
//						Radio_autoriza.setDisabled(true);
//						Radio_no_autoriza.setDisabled(true);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				
//			}
			
			Radio_autoriza.setDisabled(true);
			Radio_no_autoriza.setDisabled(true);
			
			
			
	}
	
//	public void onClick$btn_Guarda_Auto_Area(Event event) throws InterruptedException, SendFailedException, MessagingException {
//		
//		TbDetalleComprobante detalleCompOrden = new TbDetalleComprobante();
//		TbUsuario usCreaOC = UserServ.getUserPojo(idUsuarios);
//		TbPersona personaUser = UserServ.getPersona(usCreaOC);
//		MailSender ms = new MailSender();
//		MailSender msAutoArea = new MailSender();
//		MailSender msAutoUser = new MailSender();
//		
//	//	 + "\n" +
//		
//		detalleCompOrden.setIdDetalleComprobante(idOrden);
//		
//		if(Radio_autoriza_Area.isChecked() == true){
////			System.out.println("Se Activa el Campo Autorizado: "+ 1);
//			detalleCompOrden.setNuautorizaArea(AUTORIZA_ORDEN_COMPRA_SI);
//			
//				detalleCompOrden.setEstatusFactura("OC Autorizada");
//				System.out.println("Se Guarda el Campo Estatus: "+ "OC Autorizada");
//				System.out.println("Se envia un correo al Proveedor: " + txt_CorreoProv.getValue());
//
//				msAutoArea.setTo(txt_CorreoProv.getValue().trim());
//				msAutoArea.setMessage("Su Orden de compra fue aceptada, favor de consultar la informacion para la creacion de la Factura");
//				
//				if(msAutoArea.sendMessageUser())
//				{
////					us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//				
//					Messagebox.show("Correo enviado correctamente al Proveedor", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////					Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//				}
//				else
//				{
//					Messagebox.show("No se pudo Realizar la operaciÃ³n del correo al Proveedor", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//					
//				}
//				if(personaUser != null){
//					if(!personaUser.getChEmail().equals("")){
//						System.out.println("Se envia un correo al Usuario: "+ personaUser.getChEmail() );
////						Messagebox.show("Se Guarda el Campo Estatus: "+ "OC Autorizada" + "\n" +"Se envia un correo al Proveedor: " + txt_CorreoProv.getValue() + "\n" +"Se envia un correo al Usuario: "+ personaUser.getChEmail(), "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////						return;
//						msAutoArea.setTo(personaUser.getChEmail().trim());
//						msAutoArea.setMessage("Su Orden de compra fue aceptada");
//						
//						if(msAutoArea.sendMessageUser())
//						{
////							us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//						
//							Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////							Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//						}
//						else
//						{
//							Messagebox.show("No se pudo Realizar la operaciÃ³n del correo del usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//							
//						}
//					}
//				}else{
//					System.out.println("Se envia un correo al Usuario: Generico por que es Super Administrador = "+Email_Sistema);
//					msAutoArea.setTo(Email_Sistema);
//					msAutoArea.setMessage("Su Orden de compra fue aceptada");
//					
//					if(msAutoArea.sendMessageUser())
//					{
////						us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//					
//						Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////						Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//					}
//					else
//					{
//						Messagebox.show("No se pudo Realizar la operaciÃ³n del correo del usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//						
//					}
//				}
//			
//				try {
//					ventaServ.setUpdateAutorizaArea(detalleCompOrden);
//					btn_Guarda_Auto_Area.setVisible(false);
//					Radio_autoriza_Area.setDisabled(true);
//					Radio_no_autoriza_Area.setDisabled(true);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		}
//		
//		
//		
//		
//		
//		if(Radio_no_autoriza_Area.isChecked() == true){
//			
//			if (tb_Rechazo.getValue().equals("")){
//				
//				Messagebox.show("Ingresa el Motivo del Rechazo", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				return;
//				
//			}
//			detalleCompOrden.setNuautorizaArea(AUTORIZA_ORDEN_COMPRA_NO);
//			detalleCompOrden.setEstatusFactura("OC Rechazada");
//			detalleCompOrden.setMotirechaza(tb_Rechazo.getValue().toUpperCase());
//			System.out.println("Se Activa el Campo Autorizado: "+ 0);
//			
//				System.out.println("Se Guarda el Campo Estatus: "+ "OC Rechazado por el Autorizador: " + txt_Autorizador.getValue());
//	//			
//				System.out.println("Motivo del rechazo: "+ tb_Rechazo.getValue());
//				
//				if(personaUser != null){
//					if(!personaUser.getChEmail().equals("")){
//						System.out.println("Se envia un correo al Usuario: "+ personaUser.getChEmail() );
//						msAutoArea.setTo(personaUser.getChEmail());
//						msAutoArea.setMessage("Su Orden de compra fue rechazada por: "+ txt_Autorizador_Area.getValue() + "\n" + " Motivo del rechazo: "+ tb_Rechazo.getValue());
//						
//						if(msAutoArea.sendMessageUser())
//						{
////							us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//						
//							Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////							Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//						}
//						else
//						{
//							Messagebox.show("No se pudo Realizar la operaciÃ³n del correo del usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//							
//						}
//					}
//				}else{
//					System.out.println("Se envia un correo al Usuario: Generico "+Email_Sistema);
//					msAutoArea.setTo(Email_Sistema);
//					msAutoArea.setMessage("Su Orden de compra fue rechazada por: "+ txt_Autorizador.getValue() + "\n" + " Motivo del rechazo: "+ tb_Rechazo.getValue());
//					
//					if(msAutoArea.sendMessageUser())
//					{
////						us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//					
//						Messagebox.show("Correo enviado correctamente al Usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
////						Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
//					}
//					else
//					{
//						Messagebox.show("No se pudo Realizar la operaciÃ³n del correo del usuario", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//						
//					}
//				}
//				try {
//					ventaServ.setUpdateAutorizaArea(detalleCompOrden);
//					btn_Guarda_Auto_Area.setVisible(false);
//					Radio_autoriza_Area.setDisabled(true);
//					Radio_no_autoriza_Area.setDisabled(true);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		}
//		
//		Radio_autoriza_Area.setDisabled(true);
//		Radio_no_autoriza_Area.setDisabled(true);
//		
//		
//		
//	}
	
	//Se descarga el archivo Cotiza y te da la opcion de guardarlo
//	public void onClick$btn_download_Cotiza(Event event) throws InterruptedException {
//				
//				
//		
//				if(MemoryDetalleComp.getDocumento1() != null){
//					
//					int blobLengthpdf =0;
//					try {
//						blobLengthpdf = (int) MemoryDetalleComp.getDocumento1().length();
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}  
//					
//					byte[] blobAsBytespdf = null;
//					try {
//						blobAsBytespdf = MemoryDetalleComp.getDocumento1().getBytes(1, blobLengthpdf);
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					
//			        try {
//			        	Messagebox.show("Inciando la Descarga de la Cotizacion", "¡Informacion!", org.zkoss.zul.Messagebox.OK, "");
//							Filedownload.save(blobAsBytespdf, null,MemoryDetalleComp.getIdDetalleComprobante()+"_Cotizacion.pdf");
//	
//					
//					} catch (Exception e) {
//						
//						Messagebox.show("Ocurrio un Error Durante la descarga del PDF", "¡Error!", org.zkoss.zul.Messagebox.OK, "");
//					}
//				}else{
//					Messagebox.show("No Cuenta con Archivo para descargarr", "¡I n f o r m a t i v o!", org.zkoss.zul.Messagebox.OK, "");
//				}
//	}
	//Se descarga el archivo actaCons y te da la opcion de guardarlo
//	public void onClick$btn_download_2(Event event) throws InterruptedException {
//				
//				
//		
//				if(MemoryDetalleComp.getDocumento2() != null){
//					
//					int blobLengthpdf =0;
//					try {
//						blobLengthpdf = (int) MemoryDetalleComp.getDocumento2().length();
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}  
//					
//					byte[] blobAsBytespdf = null;
//					try {
//						blobAsBytespdf = MemoryDetalleComp.getDocumento2().getBytes(1, blobLengthpdf);
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					
//			        try {
//			        	Messagebox.show("Inciando la Descarga del Documento2", "¡Informacion!", org.zkoss.zul.Messagebox.OK, "");
//							Filedownload.save(blobAsBytespdf, null,MemoryDetalleComp.getIdDetalleComprobante()+"_Documento2.pdf");
//	
//					
//					} catch (Exception e) {
//						
//						Messagebox.show("Ocurrio un Error Durante la descarga del PDF", "¡Error!", org.zkoss.zul.Messagebox.OK, "");
//					}
//				}else{
//					Messagebox.show("No Cuenta con Archivo para descargarr", "¡I n f o r m a t i v o!", org.zkoss.zul.Messagebox.OK, "");
//				}
//	}
	//Se descarga el archivo actaCons y te da la opcion de guardarlo
//	public void onClick$btn_download_3(Event event) throws InterruptedException {
//				
//				
//		
//				if(MemoryDetalleComp.getDocumento3() != null){
//					
//					int blobLengthpdf =0;
//					try {
//						blobLengthpdf = (int) MemoryDetalleComp.getDocumento3().length();
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}  
//					
//					byte[] blobAsBytespdf = null;
//					try {
//						blobAsBytespdf = MemoryDetalleComp.getDocumento3().getBytes(1, blobLengthpdf);
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					
//			        try {
//			        	Messagebox.show("Inciando la Descarga del Documento3", "¡Informacion!", org.zkoss.zul.Messagebox.OK, "");
//							Filedownload.save(blobAsBytespdf, null,MemoryDetalleComp.getIdDetalleComprobante()+"_Documento3.pdf");
//	
//					
//					} catch (Exception e) {
//						
//						Messagebox.show("Ocurrio un Error Durante la descarga del PDF", "¡Error!", org.zkoss.zul.Messagebox.OK, "");
//					}
//				}else{
//					Messagebox.show("No Cuenta con Archivo para descargarr", "¡I n f o r m a t i v o!", org.zkoss.zul.Messagebox.OK, "");
//				}
//	}
	
	
	public void dodelete(String Ruta){
		 try{

	            File archivo = new File(Ruta);

	            boolean estatus = archivo.delete();;

	            if (!estatus) {

	                System.out.println("Error no se ha podido eliminar el  archivo");

	           }else{

	                System.out.println("Se ha eliminado el archivo exitosamente");

	           }

	        }catch(Exception e){

	           System.out.println(e);

	        }
		
	}
	
//	public void onClick$button_PrintCFD(Event event) throws JRException {
//		if (logger.isDebugEnabled()) {
//			logger.debug("--> " + event.toString());
//		}
//
//		try {
//			TbDetalleComprobante l_obFact = new TbDetalleComprobante();
//
////			if (l_obFact.getChfolio() != null) {
//				System.out
//						.println("FacturaCFDIDialogCtrl.onClick$button_PrintCFD()+"
//								+ idOrden);
//
////				Cfdifactura cfd = (Cfdifactura) getVentaCFDIService().getCfd(
////						getFactura().getTbTienda().getNukidTienda(),
////						getFactura().getChfolio(), getFactura().getChserie(),getFactura().getUuid());
//				if (idOrden != null){
//					doPrintReportFactura(idOrden);
//			}else{
//				Messagebox.show("No tiene datos la Orden de Compra", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				return;
//				
//							}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public void onClick$txt_Factura_Conceptos_Importe(Event event) throws JRException {
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		
		System.out.println("Entro aqui");
		
		
		
		Double importeCompleto = 0.0;
		
		double cantidad = txt_Factura_Conceptos_Cantidad.getValue();
		double unitario = txt_Factura_Conceptos_ValorUni.getValue();

		try {
			
			importeCompleto = cantidad * unitario;
			
			txt_Factura_Conceptos_Importe.setValue((cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue());
			
//			subtotal = subtotal + (cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void doPrintReportFactura(int idOrden) {
		File l_obFileRepPres = null;
		JasperReport subReporte = null;
		
//		File l_obFileRepPresReg = null;
//		JasperReport subReporteReg = null;
//		
//		File l_obFileImp = null;
//		JasperReport subReporteImp = null;
//		
//		File l_obFileAduana = null;
//		JasperReport subReporteAduana = null;

		try {
			// TbTienda tienda = (TbTienda)
			// getVentaService().getBusinessObjectPorId(new
			// Integer(p_cfd.getId().getClfilial()), TbTienda.class,
			// "nukidTienda");

			// Get the real path for the report
//			File baseDir = new File("");
//			String realString = Sessions.getCurrent().getWebApp().getRealPath(
//					"");
//			String l_stCreKey = realString.substring(0, realString
//					.lastIndexOf(File.separator))
//					+ File.separator;
//
//			String pathImagenes = baseDir.getAbsolutePath();
//			System.out
//					.println("FacturaDialogCtrl.doPrintReportFactura()pathImagenes="
//							+ pathImagenes);
//			if (pathImagenes.lastIndexOf("bin") > -1)
//				pathImagenes = pathImagenes.substring(0, pathImagenes
//						.lastIndexOf("bin"));
//			else
//				pathImagenes += File.separator;
			// String l_stRealPat=pathImagenes +
			// "webapps"+File.separator+"logos"+File.separator;
			// pathImagenes = pathImagenes +
			// "webapps"+File.separator+"logos"+File.separator + p_chRuta;
//			String l_stRealPat = l_stCreKey + "logos" + File.separator;
//			pathImagenes = l_stCreKey + "logos" + File.separator + p_chRuta;
			//Temporal quitar la siguiente linea y dejar la anterior
//			pathImagenes = l_stCreKey + "logos" + File.separator + "cbbtestTimbre.png";
			String l_stJasperFact="";
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
			String strFecha = "2013-01-22";
//			try {
//				if(p_cfd.getFegeneracion().compareTo(formatoDelTexto.parse(strFecha))>=0){
//					
//					System.out.println("Valor del RFC ------------------------------->>  "+tb_RFCCteFactura.getValue());
//					
//					if(tb_RFCCteFactura.getValue().equals("TACA840901VA3")){
////						if(tb_RFCCteFactura.getValue().equals("PEP9207167XA")){
//						l_stJasperFact="/WEB-INF/reportes/ventas/imprimeFacturaCFDIPEMEX.jasper";
//					}else{
//						l_stJasperFact="/WEB-INF/reportes/ventas/imprimeFacturaCFDI.jasper";
//					}
//					
//					System.out.println("Valor del RFC aca ------------------------------->>  "+tb_RFCCteFactura.getValue());
//				}else
					l_stJasperFact="/WEB-INF/reportes/imprimeFacturaCFDI.jasper";
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
				
			String repSrc = Sessions.getCurrent().getWebApp().getRealPath(l_stJasperFact);
			l_obFileRepPres = new File(Sessions.getCurrent().getWebApp().getRealPath("/WEB-INF/reportes/imprimeSubFacturaCfdi.jasper"));
			subReporte = (JasperReport) JRLoader.loadObject(l_obFileRepPres);
			
			
//			l_obFileImp = new File(Sessions.getCurrent().getWebApp().getRealPath("/WEB-INF/reportes/ventas/imprimeSubImpFacturaCfdi.jasper"));
//			subReporteImp = (JasperReport) JRLoader.loadObject(l_obFileImp);
//			
//			l_obFileAduana = new File(Sessions.getCurrent().getWebApp().getRealPath("/WEB-INF/reportes/ventas/imprimeSubAduanaFacturaCfdi.jasper"));
//			subReporteAduana = (JasperReport) JRLoader.loadObject(l_obFileAduana);
			
//			l_obFileRepPresReg = new File(
//					Sessions
//							.getCurrent()
//							.getWebApp()
//							.getRealPath(
//									"/WEB-INF/reportes/ventas/imprimeSubRegimenFactura.jasper"));
//
//			subReporteReg = (JasperReport) JRLoader.loadObject(l_obFileRepPresReg);
			

			HashMap<String, Object> repParams = new HashMap<String, Object>();

//			if (p_chRuta == null || p_chRuta.equals("")) {
//				repParams.put("imagen", null);
//
//			} else {
//				repParams.put("imagen", pathImagenes);
//
//			}
//			if (p_chRfc == null || p_chRfc.equals(""))
//				repParams.put("imagenRFC", null);
//			else
//				repParams.put("imagenRFC", l_stRealPat + p_chRfc);
			
			 Charset charset = Charset.forName("ISO-8859-1");
			 CharsetEncoder encoder = charset.newEncoder();
			 byte[] b = null;
//			 try {
//				 
//				 String sCadena = p_cfd.getSello();
//				 
//				 String sSubCadena = sCadena.substring((sCadena.length()-8),sCadena.length());
////					System.out.println(sSubCadena);
//			 
//			 
//			 
//			 // Convert a string to ISO-8859-1 bytes in a ByteBuffer
//				 //3.2
//			 //ByteBuffer bbuf = encoder.encode(CharBuffer.wrap("?re="+p_cfd.getRfc()+"&rr="+tb_RFCCteFactura.getValue()+"&tt="+p_obFact.getNutotal()+"&id="+p_cfd.getId().getUuid()));
//				 //3.3
//			 ByteBuffer bbuf = encoder.encode(CharBuffer.wrap("https://verificacfdi.facturaelectronica.sat.gob.mx/default.aspx"+"?&id="+p_cfd.getId().getUuid()+"&re="+p_cfd.getRfc()+"&rr="+tb_RFCCteFactura.getValue()+"&tt="+p_obFact.getNutotal()+"&fe="+sSubCadena));
//			 b = bbuf.array();
//			 } catch (CharacterCodingException e) {
//			 System.out.println(e.getMessage());
//			 }
			  
//			 String data="";
//			 try {
//			 data = new String(b, "ISO-8859-1");
//			 } catch (UnsupportedEncodingException e) {
//			 System.out.println(e.getMessage());
//			 }
//			  
//			 // get a byte matrix for the data
//			 BitMatrix matrix = null;
//			 int h = 100;
//			 int w = 100;
//			 com.google.zxing.Writer writer = new QRCodeWriter();
//			 try {
//			 matrix = writer.encode(data,
//			 com.google.zxing.BarcodeFormat.QR_CODE, w, h);
//			 } catch (com.google.zxing.WriterException e) {
//			 System.out.println(e.getMessage());
//			 }
//			  
////			 String filePath = "D:\\rtc\\temp\\qr_png.PNG";
////			 File file = new File(filePath);
//			 ByteArrayOutputStream ou=new ByteArrayOutputStream();
//			 try {
////			 MatrixToImageWriter.writeToFile(matrix, "PNG", file);
//				 MatrixToImageWriter.writeToStream(matrix, "PNG", ou);
////			 System.out.println("printing to " + file.getAbsolutePath());
//			 } catch (IOException e) {
//			 System.out.println(e.getMessage());
//			 }



			repParams.put("imprimeSubFactura", subReporte);
//			repParams.put("imprimeSubImpFactura", subReporteImp);
//			repParams.put("imprimeSubRegimenFactura", subReporteReg);
//			repParams.put("imprimeSubAduanaFactura", subReporteAduana);
			repParams.put("Title", "Orden de Compra");
			repParams.put("filial", idOrden+"");
			String moneda = ""; 
			
			
//			moneda = p_obFact.getMoneda();
//			if (p_obFact.getNutipoMoneda() == 0){
//				moneda = "MXN";
//			}
//			if (p_obFact.getNutipoMoneda() == 1){
//				moneda = "USD";
//			}
//			if (p_obFact.getNutipoMoneda() == 2){
//				moneda = "EURO";
//			}
//			if (p_obFact.getNutipoMoneda() == 3){
//				moneda = "CAD";
//			}
			repParams.put("moneda", "MXN");			
			repParams.put("folio",  idOrden+"");
//			if(p_cfd.getSerie()==null)
//				repParams.put("serie", "");
//			else
//				repParams.put("serie", p_cfd.getSerie());
//			  byte[] imageInByte = ou.toByteArray();
////			    System.out.println("........length......"+imageInByte);
//			    ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
//			    
//			repParams.put("cbb", bis);
//
//			if (logger.isDebugEnabled()) {
//				logger.debug("JasperReport : " + repSrc);
//			}

			Component parent = ordenCompraDialogWindow.getRoot();

			new JRreportWindow(parent, true, repParams, repSrc, "pdf");

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * when the "save" button is clicked. <br>
	 * 
	 * @param event
	 * @throws Exception 
	 */
	public void onCheck$GoodRadiogroup(Event event) throws Exception {

		
		if (logger.isDebugEnabled()) {
			logger.debug("--> " + event.toString());
		}
		System.out.println("Goog_ok: "+ Goog_ok.isChecked());
		System.out.println("Good_Dont: "+ Good_Dont.isChecked());
		
		if(Goog_ok.isChecked() == true){
			label_Good_Rechazo.setVisible(false);
			tb_Good_Rechazo.setVisible(false);
			tb_Good_Rechazo.setValue("");
		}
		if(Good_Dont.isChecked() == true){
			label_Good_Rechazo.setVisible(true);
			tb_Good_Rechazo.setVisible(true);
		}
		
//		btn_Guarda_Auto_Area.setVisible(true);
//		btn_Modificar.setVisible(false);
	}
	
	public static String formatearCalendar(Calendar c) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
		return df.format(c.getTime());
	}
	
	public void SignCFD()
	{
		//Se inicializa el properties
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/utils/sg.properties");
		try {
			
			//Se cargan los Valores del Archivo Properties 
			prop.load(in);

			//Cargamos la direccion donde se encuentra nuestro archivo XML y se lo asignamos a una variable String
			pathIn = prop.getProperty("folderIn");
			//Cargamos la direccion de salida del XML ya firmado y Timbrado
			pathOut = prop.getProperty("forlderOut");
			//Se carga el Numero de Serie del Certificado dado de alta en la BD
			keyId = prop.getProperty("keyId");
			//erraos el InputStream que tiene los valores del properties
			
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Limpiamos los objetos
		prop = null;
		in = null;

	}	// constructor
	
//	private static CFD2Sign[] CFD2SignList (String path,String nombre) throws Exception {
//		
//		//Todos los archivos que se encontraron en el PathIn
//		File dir = new File(path);
//		
//		//Todos los archivos de ese directorio con Extencion xml
//		String[] files = dir.list(new FileFilter(nombre));
//		
//		//Crea un arreglo de Objetos tipo cfd2Sign del tama�o de la cantidad de archivos xml encontrados
//		CFD2Sign[] cfd2Signs = new CFD2Sign[files.length];
//		Document document;
//		File file;
//		
//		//Obtiene los Bytes de los Archivos
//		for(int i=0; i<files.length; i++) {
//			document = new Document();
//			document.setFilename(files[i]);
//			document.setCompressed(false);
//			
//			file = new File(path + files[i]);
//			document.setData(utils.FileUtils.GetBytesFromFile(file));
//					
//			cfd2Signs[i] = new CFD2Sign();
//			cfd2Signs[i].setCfd(document);	
//		}
//		
//		return cfd2Signs;		
//	}
	
	public void onFocusEntregado(Event event) throws Exception {
		logger
				.info("onFocusEntregado Se cambia entregados: "
						+ event.getName());

	}

	public void onBlurentregado(Event event) throws Exception {
		if (event != null)
			logger.info("onBlurentregado Se cambia entregados: "
					+ event.getName());

	}
	
	public void onFocusCantidad(Event event) throws Exception {
		if (event != null)
			logger.info("onFocusCantidad: " + event.getName());
		
	}

	public void onBlurCantidad(Event event) throws Exception {
		if (event != null)
			logger.info("onBlurCantidad: " + event.getName());

		

	}
	
	
	
	public void onClick$btnImporte(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Entro a Calcula Importe--> " + event.toString());
		}
		
		CfdUtil cfdUtil = new CfdUtil();
		
		Double importeCompleto = 0.0;
		
		double cantidad = txt_Factura_Conceptos_Cantidad.getValue();
		double unitario = txt_Factura_Conceptos_ValorUni.getValue();

		try {
			
			importeCompleto = cantidad * unitario;
			
			txt_Factura_Conceptos_Importe.setValue((cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue());
			
//			subtotal = subtotal + (cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onClick$btn_T_Importe(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Entro a Calcula Importe--> " + event.toString());
		}
		
		if(txt_Factura_Imp_Tras_Base.getValue() != null){
			if(txt_Factura_Imp_Tras_Base.getValue() < 0){
				Messagebox.show("Agrega la Base del Impuesto Traslado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}
		}else{
			Messagebox.show("Agrega la Base del Impuesto Traslado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			valida = false;
			return ;
		}
		
//		if(lb_Factura_Imp_Tras_Imp.getSelectedIndex() < 0){
			if(lb_Factura_Imp_Tras_Imp.getSelectedIndex() < 0){
				Messagebox.show("Seleciona un Tipo de Impuesto Traslado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}
//		}else{
//			Messagebox.show("Selecciona un Tipo de Impuesto Traslado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////			valida = false;
//			return ;
//		}
		
//		if(lb_Factura_Imp_Tras_Factor.getSelectedIndex() < 0){
			if(lb_Factura_Imp_Tras_Factor.getSelectedIndex() < 0){
				Messagebox.show("Seleciona Un Factor Traslado", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}
//		}else{
//			Messagebox.show("Seleciona Un Factor Traslado", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////			valida = false;
//			return ;
//		}
		
//		if(lb_Factura_Imp_Tras_Tasa.getSelectedIndex() < 0){
			if(lb_Factura_Imp_Tras_Tasa.getSelectedIndex() < 0){
				Messagebox.show("Seleciona Una Tasa de Traslado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}
//		}else{
//			Messagebox.show("Seleciona Una Tasa de Traslado ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
////			valida = false;
//			return ;
//		}
//		
		
		CfdUtil cfdUtil = new CfdUtil();
		
		Double importeCompleto = 0.0;
		
		double Base = txt_Factura_Imp_Tras_Base.getValue();
		double Tasa = Double.parseDouble(lb_Factura_Imp_Tras_Tasa.getSelectedItem().getLabel());

		try {
			
			importeCompleto = Base * Tasa;
			
			txt_Factura_Imp_Tras_Importe.setValue((cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue());
			
//			subtotal = subtotal + (cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onClick$btn_R_Importe(Event event) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Entro a Calcula Importe--> " + event.toString());
		}
		
		if(txt_Factura_Imp_Ret_Base.getValue() != null){
			if(txt_Factura_Imp_Tras_Base.getValue() < 0){
				Messagebox.show("Agrega la Base del Impuesto Retencion ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}
		}else{
			Messagebox.show("Agrega la Base del Impuesto Retencion ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//			valida = false;
			return ;
		}

			if(lb_Factura_Imp_Ret_Impuesto.getSelectedIndex() < 0){
				Messagebox.show("Seleciona un Tipo de Impuesto Retencion ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}

			if(lb_Factura_Imp_Ret_Factor.getSelectedIndex() < 0){
				Messagebox.show("Seleciona Un Factor Retencion", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}

			if(lb_Factura_Imp_Ret_Tasa.getSelectedIndex() < 0){
				Messagebox.show("Seleciona Una Tasa de Retencion ", "¡ Información !", org.zkoss.zul.Messagebox.OK, "");
//				valida = false;
				return ;
			}

		
		CfdUtil cfdUtil = new CfdUtil();
		
		Double importeCompleto = 0.0;
		
		double Base = txt_Factura_Imp_Ret_Base.getValue();
		double Tasa = Double.parseDouble(lb_Factura_Imp_Ret_Tasa.getSelectedItem().getLabel());

		try {
			
			importeCompleto = Base * Tasa;
			
			txt_Factura_Imp_Ret_Importe.setValue((cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue());
			
//			subtotal = subtotal + (cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public void onFocusUnitario(Event event) throws Exception {
//		if (event != null)
//			logger.info("onFocusUnitario: " + event.getName());
//		
//		System.out.println("Entro aqui");
//		
//		CfdUtil cfdUtil = new CfdUtil();
//		
//		Double importeCompleto = 0.0;
//		
//		double cantidad = Double.parseDouble(txt_Factura_Conceptos_Cantidad.getValue());
//		double unitario = Double.parseDouble(txt_Factura_Conceptos_ValorUni.getValue());
//
//		try {
//			
//			importeCompleto = cantidad * unitario;
//			
//			txt_Factura_Conceptos_Importe.setValue((cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue()+"");
//			
////			subtotal = subtotal + (cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//
//	
//	}
//
//	public void onBlurUnitario(Event event) throws Exception {
//		if (event != null)
//			logger.info("onBlurUnitario: " + event.getName());
//		
//		System.out.println("Entro aqui");
//		
//		CfdUtil cfdUtil = new CfdUtil();
//		
//		Double importeCompleto = 0.0;
//		
//		double cantidad = Double.parseDouble(txt_Factura_Conceptos_Cantidad.getValue());
//		double unitario = Double.parseDouble(txt_Factura_Conceptos_ValorUni.getValue());
//
//		try {
//			
//			importeCompleto = cantidad * unitario;
//			
//			txt_Factura_Conceptos_Importe.setValue((cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue()+"");
//			
////			subtotal = subtotal + (cfdUtil.getBigDecimalValueRoundUp(importeCompleto)).doubleValue();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//
//		
//
//	}
	
	

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// ++++++++++++++++++ getter / setter +++++++++++++++++++//
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

//	public TbUsuario getUser() {
//		return l_obUser;
//	}
//
//	public void setUser(TbUsuario user) {
//		this.l_obUser = user;
//	}

	public void setValidationOn(boolean validationOn) {
		this.validationOn = validationOn;
	}

	public boolean isValidationOn() {
		return validationOn;
	}

//	public List<TbRol> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(List<TbRol> roles) {
//		this.roles = roles;
//	}
//
//	public TbPersona getPersona() {
//		return l_obPersona;
//	}
//
//	public void setPersona(TbPersona persona) {
//		this.l_obPersona = persona;
//	}

//	public TbDetalleComprobante getL_obComprobanteDetalle() {
//		return l_obComprobanteDetalle;
//	}
//
//	public void setL_obComprobanteDetalle(TbDetalleComprobante lObComprobanteDetalle) {
//		l_obComprobanteDetalle = lObComprobanteDetalle;
//	}


}