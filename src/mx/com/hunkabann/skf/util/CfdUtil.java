package mx.com.hunkabann.skf.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CfdUtil {
	private static DecimalFormat dfRedondeo = new DecimalFormat("#0.00");

	public double redondea(double value) {
		return Double.parseDouble(dfRedondeo.format(value));
	}

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
}
