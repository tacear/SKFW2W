package mx.com.hunkabann.skf.frontent.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.hunkabann.skf.util.Constantes;

//import mx.com.hunkabann.sipv.util.Constantes;

final public class ComboFactory implements Serializable, Constantes {

	public ComboFactory(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;

	}

	private static final long serialVersionUID = 512987197845989141L;
	final private int id;
	final private String nombre;
	public static ComboFactory EMPTY = new ComboFactory(-1, "");
	public static ComboFactory SELECCIONE = new ComboFactory(-1, "SELECCIONE");
	public static ComboFactory TODOS = new ComboFactory(-1, "TODOS");
	public static ComboFactory TODAS = new ComboFactory(-1, "TODAS");
	public static ComboFactory NINGUNO = new ComboFactory(-1, "------");

	public final static List<ComboFactory> ALLDAYS;

	static {
		List<ComboFactory> result = new ArrayList<ComboFactory>();

		result.add(new ComboFactory(1, "LUNES"));
		result.add(new ComboFactory(2, "MARTES"));
		result.add(new ComboFactory(3, "MIÉRCOLES"));
		result.add(new ComboFactory(4, "JUEVES"));
		result.add(new ComboFactory(5, "VIERNES"));
		result.add(new ComboFactory(6, "SÁBADO"));
		result.add(new ComboFactory(7, "DOMINGO"));

		ALLDAYS = Collections.unmodifiableList(result);
	}

	public final static List<ComboFactory> STATUS_PAGO_CXC;
	final private static Map<Integer, ComboFactory> PAGO_CXC_MAP;

	static {
		List<ComboFactory> result = new ArrayList<ComboFactory>();

		result.add(new ComboFactory(IN_STATUS_PAGOS_CTAXCOBRAR_NA,
				ST_STATUS_PAGOS_CTAXCOBRAR_NA));
		result.add(new ComboFactory(IN_STATUS_PAGOS_CTAXCOBRAR_CHQ_DEVUELTO,
				ST_STATUS_PAGOS_CTAXCOBRAR_CHQ_DEVUELTO));
		result.add(new ComboFactory(IN_STATUS_PAGOS_CTAXCOBRAR_CHQ_FIRME,
				ST_STATUS_PAGOS_CTAXCOBRAR_CHQ_FIRME));
		result.add(new ComboFactory(IN_STATUS_PAGOS_CTAXCOBRAR_CHQ_SBC,
				ST_STATUS_PAGOS_CTAXCOBRAR_CHQ_SBC));

		STATUS_PAGO_CXC = Collections.unmodifiableList(result);
		PAGO_CXC_MAP = new HashMap<Integer, ComboFactory>(result.size());

		for (ComboFactory comboFactory : result) {
			PAGO_CXC_MAP.put(Integer.valueOf(comboFactory.getId()),
					comboFactory);
		}
	}

	public static ComboFactory getCXCById(int id) {
		return PAGO_CXC_MAP.get(Integer.valueOf(id));
	}

	public final static List<ComboFactory> STATUS_ORDEN_COMPRA;
	final private static Map<Integer, ComboFactory> ORDEN_COMPRA_MAP;

	static {
		List<ComboFactory> result = new ArrayList<ComboFactory>();

		result.add(new ComboFactory(IN_STATUS_ORDEN_COMPRA_CREADO, "CREADA"));
		result.add(new ComboFactory(IN_STATUS_ORDEN_COMPRA_SURTIDO_PARACIAL,
				"PARCIALMENTE SURTIDA"));
		result.add(new ComboFactory(IN_STATUS_ORDEN_COMPRA_SURTIDO, "SURTIDA"));
		result.add(new ComboFactory(IN_STATUS_ORDEN_COMPRA_ENTREGA_CLIENTE,
				"ENTREGA CLIENTE"));
		result.add(new ComboFactory(IN_STATUS_ORDEN_COMPRA_ENTREGADA,
				"ENTREGADA"));
		result.add(new ComboFactory(IN_STATUS_ORDEN_COMPRA_CANCELADO,
				"CANCELADA"));

		STATUS_ORDEN_COMPRA = Collections.unmodifiableList(result);
		ORDEN_COMPRA_MAP = new HashMap<Integer, ComboFactory>(result.size());

		for (ComboFactory comboFactory : result) {
			ORDEN_COMPRA_MAP.put(Integer.valueOf(comboFactory.getId()),
					comboFactory);
		}
	}

	public static ComboFactory getOrdenCompraById(int id) {
		return ORDEN_COMPRA_MAP.get(Integer.valueOf(id));
	}

	public final static List<ComboFactory> STATUS_VALE;
	final private static Map<Integer, ComboFactory> VALE_MAP;

	static {
		List<ComboFactory> result = new ArrayList<ComboFactory>();

		result.add(new ComboFactory(IN_STATUS_VALE_CREADO, "CREADO"));
		result.add(new ComboFactory(IN_STATUS_VALE_SURTIDO, "SURTIDO"));
		result.add(new ComboFactory(IN_STATUS_VALE_CANCELADO, "CANCELADO"));

		STATUS_VALE = Collections.unmodifiableList(result);
		VALE_MAP = new HashMap<Integer, ComboFactory>(result.size());

		for (ComboFactory comboFactory : result) {
			VALE_MAP.put(Integer.valueOf(comboFactory.getId()), comboFactory);
		}
	}

	public static ComboFactory getValeById(int id) {
		return VALE_MAP.get(Integer.valueOf(id));
	}

	public final static List<ComboFactory> STATUS_REQUISICION;
	final private static Map<Integer, ComboFactory> REQUISICION_MAP;

	static {
		List<ComboFactory> result = new ArrayList<ComboFactory>();

		result.add(new ComboFactory(IN_STATUS_REQUISICION_CREADA, "CREADA"));
		result.add(new ComboFactory(IN_STATUS_REQUISICION_EN_ORDEN_COMPRA,
				"ORDEN COMPRA"));
		result.add(new ComboFactory(IN_STATUS_REQUISICION_SURTIDA, "SURTIDA"));
		result.add(new ComboFactory(IN_STATUS_REQUISICION_PARCIALMENTE_SURTIDA,
				"PARCIALMENTE SURTIDA"));
		result.add(new ComboFactory(IN_STATUS_REQUISICION_EN_TRANSITO,
				"EN TRANSITO"));
		result.add(new ComboFactory(IN_STATUS_REQUISICION_CANCELADA,
				"CANCELADA"));

		STATUS_REQUISICION = Collections.unmodifiableList(result);
		REQUISICION_MAP = new HashMap<Integer, ComboFactory>(result.size());

		for (ComboFactory comboFactory : result) {
			REQUISICION_MAP.put(Integer.valueOf(comboFactory.getId()),
					comboFactory);
		}
	}

	public static ComboFactory getRequisicionById(int id) {
		return REQUISICION_MAP.get(Integer.valueOf(id));
	}

	public final static List<ComboFactory> STATUS_CTA_X_PAGAR;
	final private static Map<Integer, ComboFactory> CTA_X_PAGAR_MAP;

	static {
		List<ComboFactory> result = new ArrayList<ComboFactory>();

		result.add(new ComboFactory(IN_STATUS_CTAXPAGAR_CREADA, "CREADA"));
		result.add(new ComboFactory(IN_STATUS_CTAXPAGAR_PAGO_PARCIAL,
				"PAGO PARCIAL"));
		result.add(new ComboFactory(IN_STATUS_CTAXPAGAR_PAGADA, "PAGADA"));
		result
				.add(new ComboFactory(IN_STATUS_CTAXPAGAR_CANCELADA,
						"CANCELADA"));

		STATUS_CTA_X_PAGAR = Collections.unmodifiableList(result);
		CTA_X_PAGAR_MAP = new HashMap<Integer, ComboFactory>(result.size());

		for (ComboFactory comboFactory : result) {
			CTA_X_PAGAR_MAP.put(Integer.valueOf(comboFactory.getId()),
					comboFactory);
		}
	}

	public static ComboFactory getCtaXPagarById(int id) {
		return CTA_X_PAGAR_MAP.get(Integer.valueOf(id));
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean equals(ComboFactory comboFactory) {
		return getId() == comboFactory.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof ComboFactory) {
			ComboFactory comboFactory = (ComboFactory) obj;
			return equals(comboFactory);
		}

		return false;
	}
}
