package mx.com.hunkabann.skf.util;

public interface Constantes
{
	//status cheques pagos cta x cobrar
	public final int IN_STATUS_PAGOS_CTAXCOBRAR_NA = 0;
	public final int IN_STATUS_PAGOS_CTAXCOBRAR_CHQ_DEVUELTO = 1;
	public final int IN_STATUS_PAGOS_CTAXCOBRAR_CHQ_FIRME = 2;
	public final int IN_STATUS_PAGOS_CTAXCOBRAR_CHQ_SBC = 3;
	public final int IN_STATUS_PAGOS_CTAXCOBRAR_CANCELADA = 4;
	public final String ST_STATUS_PAGOS_CTAXCOBRAR_CHQ_DEVUELTO = "DEVUELTO";
	public final String ST_STATUS_PAGOS_CTAXCOBRAR_CHQ_FIRME = "FIRME";
	public final String ST_STATUS_PAGOS_CTAXCOBRAR_CHQ_SBC = "SALVO BUEN COBRO";
	public final String ST_STATUS_PAGOS_CTAXCOBRAR_NA = "NO APLICA";
	public final String ST_STATUS_PAGOS_CTAXCOBRAR_NOTACREDITO = "DEVOLUCION";

	// status de vale
	public final int IN_STATUS_VALE_CREADO = 1;
	public final int IN_STATUS_VALE_SURTIDO = 2;
	public final int IN_STATUS_VALE_CANCELADO = 3;

	//status de requisicion
	public final int IN_STATUS_REQUISICION_CREADA = 1;
	public final int IN_STATUS_REQUISICION_EN_ORDEN_COMPRA = 2;
	public final int IN_STATUS_REQUISICION_SURTIDA = 3;
	public final int IN_STATUS_REQUISICION_PARCIALMENTE_SURTIDA = 4;
	public final int IN_STATUS_REQUISICION_EN_TRANSITO = 5;
	public final int IN_STATUS_REQUISICION_CANCELADA = 6;


	//status de factura
	public final String IN_STATUS_FACTURA_CARGADA = "FACTURA CARGADA";
	public final String IN_STATUS_FACTURA_PAGADA = "FACTURA PAGADA";
	public final String IN_STATUS_ORDEN_COMPLETADA = "ORDEN COMPLETADA";
	public final Integer IN_STATUS_FACTURA_CANCELADA = 3;
	public final Integer IN_STATUS_FACTURA_CANCELADA_DEV_PARCIAL = 4;
	public final Integer IN_STATUS_FACTURA_CANCELADA_DEV_TOTAL = 5;
	public final Integer IN_STATUS_FACTURA_CANCELADA_SISTEMA = 6;
	public final String IN_STATUS_COMPLEMENTO_CARGADO = "CRP CARGADO";
	public final String IN_STATUS_COMPLEMENTO_PENDIENTE = "CRP PENDIENTE";

	//status tipo devolucion
	public final Integer IN_STATUS_DEVOLUCION_TOTAL = 1;
	public final Integer IN_STATUS_DEVOLUCION_PARCIAL = 2;

	//status de remision
	public final Integer IN_STATUS_REMISION_CREADA = 1;
	public final Integer IN_STATUS_REMISION_PAGADA = 2;
	public final Integer IN_STATUS_REMISION_CANCELADA = 3;
	public final Integer IN_STATUS_REMISION_CANCELADA_DEV_PARCIAL = 4;
	public final Integer IN_STATUS_REMISION_CANCELADA_DEV_TOTAL = 5;
	public final Integer IN_STATUS_REMISION_CANCELADA_SISTEMA = 6;
	public final Integer IN_STATUS_REMISION_FACTURADA = 7;


	//status de NOTA CREDITO
	public final Integer IN_STATUS_NOTA_CREDITO_CREADA = 1;
	public final Integer IN_STATUS_NOTA_CREDITO_PAGADA = 2;
	public final Integer IN_STATUS_NOTA_CREDITO_CANCELADA = 3;

	//status de orden de compra
	public final int IN_STATUS_ORDEN_COMPRA_CREADO = 1;
	public final int IN_STATUS_ORDEN_COMPRA_SURTIDO_PARACIAL = 2;
	public final int IN_STATUS_ORDEN_COMPRA_SURTIDO = 3;
	public final int IN_STATUS_ORDEN_COMPRA_ENTREGA_CLIENTE = 4;
	public final int IN_STATUS_ORDEN_COMPRA_ENTREGADA = 5;
	public final int IN_STATUS_ORDEN_COMPRA_CANCELADO = 6;
	
	

	// status ctaXCobrar
	public final Byte IN_STATUS_CTAXCOBRAR_CREADA = 1;
	public final Byte IN_STATUS_CTAXCOBRAR_CANCELADA = 2;
	public final Byte IN_STATUS_CTAXCOBRAR_PAGADA = 3;
	public final Byte IN_STATUS_CTAXCOBRAR_PAGO_PARCIAL = 4;
	public final Byte IN_STATUS_CTAXCOBRAR_DEVUELTA = 5;
	public final Byte IN_STATUS_CTAXCOBRAR_DEVUELTA_PARCIAL = 6;
	public final Byte IN_STATUS_CTAXCOBRAR_INCOBRABLE = 7;

	// status ctaXPagar
	public final Byte IN_STATUS_CTAXPAGAR_CREADA = 1;
	public final Byte IN_STATUS_CTAXPAGAR_CANCELADA = 2;
	public final Byte IN_STATUS_CTAXPAGAR_PAGO_PARCIAL = 3;
	public final Byte IN_STATUS_CTAXPAGAR_PAGADA = 4;

	// status pago ctaXPagar
	public final Byte IN_STATUS_PAGO_CTAXPAGAR_PAGADA = 1;
	public final Byte IN_STATUS_PAGO_CTAXPAGAR_CANCELADA = 2;

	// otro tipo de constantes
	public final Byte IN_MIN_CANCELA_DOC = 30;
	public final int IN_COBRANZA_CARGO = 1;
	public final int IN_COBRANZA_ABONO = 2;
	public final int IN_COBRANZA_EXISTE_FP_CHQ_DEV = 200;
	public final int IN_COBRANZA_OK = 0;
	public final int IN_NOTA_CREDITO_DEV_TOTAL = 1;
	public final int IN_NOTA_CREDITO_DEV_PARCIAL = 2;
	public final String ST_COBRANZA_CARGO = "CARGO";
	public final String ST_COBRANZA_ABONO = "ABONO";

	//Conceptos de movimiento a inventario
	public final String ST_CONCEPTO_CXC_FACTURA = "CREADA POR FACTURA";
	public final String ST_CONCEPTO_CXC_REMISION = "CREADA POR REMISI�N";
	public final String ST_CONCEPTO_MOV_INV_FACTURA = "FACTURACI�N";
	public final String ST_CONCEPTO_MOV_INV_REMISION = "REMISI�N";
	public final String ST_CONCEPTO_MOV_INV_VALE = "GENERACI�N DE VALE";
	public final String ST_CONCEPTO_MOV_INV_VALE_CANCELADO = "CANCELACI�N DE VALE";
	public final String ST_CONCEPTO_MOV_INV_VALE_SURTIDO = "VALE SURTIDO";
	public final String ST_CONCEPTO_MOV_INV_CANCELA_FACTURA = "CANCELA FACTURA";
	public final String ST_CONCEPTO_MOV_INV_CANCELA_REMISION  = "CANCELA REMISI�N";
	public final String ST_CONCEPTO_MOV_INV_DEVOLUCION_FACTURA  = "DEVOLUCION FACTURA";
	public final String ST_CONCEPTO_MOV_INV_DEVOLUCION_REMISION  = "DEVOLUCION REMISION";
	public final String ST_CONCEPTO_MOV_INV_AJUSTE_POSITIVO  = "AJUSTE POSITIVO";
	public final String ST_CONCEPTO_MOV_INV_AJUSTE_NEGATIVO  = "AJUSTE NEGATIVO";

	//Conceptos de forma de pago para cuentas por pagar
	public final String ST_CONCEPTO_CTAS_X_PAGAR_DESCUENTO_FINANCIERO = "DESCUENTO FINANCIERO";

	//Ctos forma de pago  para cuentas por cobrar
	public final String ST_CTO_FORMA_PAGO_DEVOLUCION = "DEVOLUCI�N";
	public final String ST_CTO_FORMA_PAGO_CTAS_INCOBRABLES = "CUENTAS INCOBRABLES (INFORMATIVO)";
	public final String ST_CTO_FORMA_PAGO_NOTA_CREDITO = "PAGO CON NOTA DE CREDITO";
	public final String ST_CTO_FORMA_PAGO_COBRANZA_NOTA_CRED = "COBRANZA NOTA DE CREDITO";
	public final String ST_CTO_FORMA_PAGO_DEV_EFECTIVO = "DEV. DE DINERO (INFORMATIVO)";




	// ------------- para bitacora  -----------------

	//	Administraci�n	1..50
	public final int IN_ADMON_PERMISO_NUEVO 		= 1;
	public final int IN_ADMON_PERMISO_ACTUALIZA 	= 2;
	public final int IN_ADMON_GRUPO_NUEVO 			= 3;
	public final int IN_ADMON_GRUPO_ACTUALIZA 		= 4;
	public final int IN_ADMON_ROL_NUEVO 			= 5;
	public final int IN_ADMON_ROL_ACTUALIZA 		= 6;
	public final int IN_ADMON_ROL_USUARIO_NUEVO 	= 7;
	public final int IN_ADMON_ROL_USUARIO_ACTUALIZA = 8;
	public final int IN_ADMON_ROL_GRUPO_NUEVO 		= 9;
	public final int IN_ADMON_ROL_GRUPO_ACTUALIZA 	= 10;
	public final int IN_ADMON_PERMISO_GRUPO_NUEVO 	= 11;
	public final int IN_ADMON_PERMISO_GRUPO_ACTUALIZA = 12;
	public final int IN_ADMON_USUARIO_NUEVO 		= 13;
	public final int IN_ADMON_USUARIO_ACTUALIZA 	= 14;

	//	Cat�logos	51..100
	public final int IN_CAT_CORPORATIVO_NUEVO 		= 51;
	public final int IN_CAT_CORPORATIVO_ACTUALIZA	= 52;
	public final int IN_CAT_CENTRO_DISTRIBUCION_NUEVO = 53;
	public final int IN_CAT_CENTRO_DISTRIBUCION_ACTUALIZA = 54;
	public final int IN_CAT_PUESTO_NUEVO 			= 55;
	public final int IN_CAT_PUESTO_ACTUALIZA 		= 56;
	public final int IN_CAT_PAQUETES_NUEVO 			= 63;
	public final int IN_CAT_PAQUETES_ACTUALIZA 		= 64;
	public final int IN_CAT_PERSONAL_NUEVO 			= 57;
	public final int IN_CAT_PERSONAL_ACTUALIZA 		= 58;
	public final int IN_CAT_NIVEL_PRECIO_NUEVO 		= 59;
	public final int IN_CAT_NIVEL_PRECIO_ACTUALIZA 	= 60;
	public final int IN_CAT_TIENDA_NUEVO 			= 61;
	public final int IN_CAT_TIENDA_ACTUALIZA 		= 62;

	//	Clientes	101..150
	public final int IN_CTE_GPO_CTE_NUEVO 			= 101;
	public final int IN_CTE_GPO_CTE_ACTUALIZA 		= 102;
	public final int IN_CTE_TIPO_CTE_NUEVO 			= 103;
	public final int IN_CTE_TIPO_CTE_ACTUALIZA 		= 104;
	public final int IN_CTE_CALIFICA_CTE_NUEVO 		= 105;
	public final int IN_CTE_CALIFICA_CTE_ACTUALIZA 	= 106;
	public final int IN_CTE_CTE_NUEVO 				= 107;
	public final int IN_CTE_CTE_ACTUALIZA 			= 108;
	public final int IN_CTE_PERSONA_NUEVO 			= 109;
	public final int IN_CTE_PERSONA_ACTUALIZA 		= 110;
	public final int IN_CTE_DIRECCION_NUEVO 		= 111;
	public final int IN_CTE_DIRECCION_ACTUALIZA 	= 112;
	public final int IN_CTE_VENDEDOR_NUEVO 			= 113;
	public final int IN_CTE_VENDEDOR_ACTUALIZA 		= 114;
	public final int IN_CTE_VENDEDOR_ELIMINA 		= 115;

	//	Proveedores	151..200
	public final int IN_PROV_GPO_PROV_NUEVO 		= 151;
	public final int IN_PROV_GPO_PROV_ACTUALIZA 	= 152;
	public final int IN_PROV_PROV_NUEVO 			= 153;
	public final int IN_PROV_PROV_ACTUALIZA 		= 154;
	public final int IN_PROV_FACT_PROV_NUEVO 		= 155;
	public final int IN_PROV_FACT_PROV_ACTUALIZA 	= 156;

	//	Inventario	201..250
	public final int IN_INV_LINEA_PROD_NUEVO 		= 201;
	public final int IN_INV_LINEA_PROD_ACTUALIZA 	= 202;
	public final int IN_INV_PROD_NUEVO 				= 203;
	public final int IN_INV_PROD_ACTUALIZA 			= 204;
	public final int IN_INV_CTO_MOV_INV_NUEVO 		= 205;
	public final int IN_INV_CTO_MOV_INV_ACTUALIZA 	= 206;
	public final int IN_INV_PRECIO_ACTUALIZA 		= 207;
	public final int IN_INV_INV_ACTUALIZA 			= 208;
	public final int IN_INV_RECEP_PROD_NUEVO 		= 209;
	public final int IN_INV_RECEP_PROD_ACTUALIZA 	= 210;
	public final int IN_INV_ENTREGA_ACTUALIZA 		= 211;
	public final int IN_INV_PROV_PROD_NUEVO 		= 212;
	public final int IN_INV_PROV_PROD_ACTUALIZA		= 213;
	public final int IN_INV_PROV_PROD_ELIMINA		= 214;
	public final int IN_INV_PRECIO_PROD_NUEVO 		= 215;
	public final int IN_INV_PRECIO_PROD_ACTUALIZA	= 216;
	public final int IN_INV_PRECIO_PROD_ELIMINA		= 217;
	public final int IN_INV_AJUSTE_INVENTARIO		= 218;

	//	Compras	251..300
	public final int IN_COMPRA_VALE_NUEVO 			= 251;
	public final int IN_COMPRA_VALE_ACTUALIZA 		= 252;
	public final int IN_COMPRA_REQ_NUEVO 			= 253;
	public final int IN_COMPRA_REQ_ACTUALIZA 		= 254;
	public final int IN_COMPRA_ORD_COMPRA_NUEVO 	= 255;
	public final int IN_COMPRA_ORD_COMPRA_ACTUALIZA = 256;
	public final int IN_COMPRA_DEV_PROV_NUEVO 		= 257;
	public final int IN_COMPRA_DEV_PROV_ACTUALIZA 	= 258;
	public final int IN_COMPRA_FACT_PROV_NUEVO 		= 259;
	public final int IN_COMPRA_FACT_PROV_ACTUALIZA 	= 260;

	//	Ventas	301..350
	public final int IN_VTA_FACT_NUEVO 				= 301;
	public final int IN_VTA_FACT_ACTUALIZA 			= 302;
	public final int IN_VTA_REMI_NUEVO 				= 303;
	public final int IN_VTA_REMI_ACTUALIZA 			= 304;
	public final int IN_VTA_COTIZA_NUEVO 			= 305;
	public final int IN_VTA_COTIZA_ACTUALIZA 		= 306;
	public final int IN_VTA_PRESU_NUEVO 			= 307;
	public final int IN_VTA_PRESU_ACTUALIZA 		= 308;
	public final int IN_VTA_CTA_X_COBRAR_NUEVO 		= 309;
	public final int IN_VTA_CTA_X_COBRAR_ACTUALIZA 	= 310;
	public final int IN_VTA_REQ_NUEVO		 		= 311;
	public final int IN_VTA_REQ_ACTUALIZA		 	= 312;
	public final int IN_VTA_VALE_NUEVO		 		= 313;
	public final int IN_VTA_VALE_ACTUALIZA		 	= 314;
	public final int IN_VTA_AUTORIZA_CAMBIO_PRECIO  = 315;


	//	Cobranza	351..400
	public final int IN_COBRANZA_CTO_FORMA_PAGO_NUEVO 		= 351;
	public final int IN_COBRANZA_CTO_FORMA_PAGO_ACTUALIZA 	= 352;
	public final int IN_COBRANZA_CTO_CTA_X_COBRAR_NUEVO 	= 353;
	public final int IN_COBRANZA_CTO_CTA_X_COBRAR_ACTUALIZA = 354;
	public final int IN_COBRANZA_CTA_X_PAGAR_NUEVO 			= 355;
	public final int IN_COBRANZA_CTA_X_PAGAR_ACTUALIZA 		= 356;
	public final int IN_COBRANZA_NOTA_CRED_NUEVO 			= 357;
	public final int IN_COBRANZA_NOTA_CRED_ACTUALIZA 		= 358;
	public final int IN_COBRANZA_CXC_FACTURA_ACTUALIZA		= 359;
	public final int IN_COBRANZA_CXC_REMISION_ACTUALIZA		= 360;
	public final int IN_COBRANZA_CXC_ACTUALIZA				= 361;
	public final int IN_COBRANZA_CXC_PAGO_ACTUALIZA			= 362;
	public final int IN_COBRANZA_CXC_PAGO_NUEVO				= 363;
	public final int IN_COBRANZA_CIERRE_DIA_TDA				= 364;
	public final int IN_COBRANZA_CIERRE_DIA_CORP			= 365;

	//	Pagos	401..450
	public final int IN_PAGO_CTO_CTA_X_PAGAR_NUEVO 	= 401;
	public final int IN_PAGO_CTO_CTA_X_PAGAR_ACTUALIZA = 402;
	public final int IN_PAGO_PAGO_NUEVO 			= 403;
	public final int IN_PAGO_PAGO_ACTUALIZA 		= 404;
	public final int IN_PAGO_CTA_X_PAGAR_NUEVO 		= 405;


	//	Caja	451..500
	public final int IN_CAJA_CAJA_NUEVO 			= 451;
	public final int IN_CAJA_CAJA_ACTUALIZA 		= 452;
	public final int IN_CAJA_RECEP_PAGO_NUEVO 		= 453;
	public final int IN_CAJA_RECEP_PAGO_ACTUALIZA 	= 454;
	public final int IN_CAJA_CORTE 					= 455;
	public final int IN_CAJA_CAJA_PERSONAL_NUEVO 			= 456;
	public final int IN_CAJA_CAJA_PERSONAL_ACTUALIZA 		= 457;
	public final int IN_CAJA_RECEPCION_PAGOS_IMPRIMEFACT 	= 458;
	public final int IN_CAJA_RECEPCION_PAGOS_FACTURA 	= 459;
	public final int IN_CAJA_RECEPCION_PAGOS_REMISION 	= 460;
	public final int IN_CAJA_RECEPCION_PAGOS_ACTUALIZA_CLIENTE 	= 461;
	public final int IN_CAJA_RECEPCION_PAGOS_NUEVO_PAGOCXC 	= 462;
	public final int IN_CAJA_RECEPCION_PAGOS_ACTUALIZA_CXC 	= 463;
	public final int IN_CAJA_FRACC_MON_NUEVO 		= 464;
	public final int IN_CAJA_FRACC_MON_ACTUALIZA 	= 465;
	public final int IN_CAJA_CANCELA_PAGOCXC 		= 466;
	public final int IN_CAJA_PAGO_NOTA_CREDITO 		= 467;
	public final int IN_CAJA_INICIO 				= 468;

	//CFD  501...510
	public final int IN_CFD_SELLOS_NUEVO 			= 501;
	public final int IN_CFD_SELLOS_ACTUALIZA 		= 502;
	public final int IN_CFD_FOLIO_NUEVO 			= 503;
	public final int IN_CFD_FOLIO_ACTUALIZA 		= 504;
	
	public final int CORP_INICIO_ROLEX 			= 1;
	public final int CORP_INICIO_SERVICIOS 		= 2;


	//Nota Credito

	public final int IN_NOTA_CREDITO_IMPRIME		= 551;
	
	//Autorizacion
	public final int AUTO_ORDEN_COMPRA		= 1;
	public final int AUTORIZA_ORDEN_COMPRA_SI		= 1;
	public final int AUTORIZA_ORDEN_COMPRA_NO		= 0;
	public final int PROVEEDOR_ACTIVO		= 1;
	public final int USUARIO_ACTIVO		= 1;
	public final int USUARIO_NO_ACTIVO		= 0;
	public final int ACTIVA_OC_CXP		= 1;
	public final int NO_ACTIVA_OC_CXP		= 0;
	
	//Status Orden de compra
	public final String IN_STATUS_ORDEN_COMPRA_AUTORIZADA = "OC Autorizada";
	public final String IN_STATUS_ORDEN_COMPRA_PROCESO_PAGO = "PROCESO DE PAGO";

	//

}	// end of file
