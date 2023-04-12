package mx.com.hunkabann.skf.util;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

public class MenorIgualConstraint implements Constraint,java.io.Serializable {

	private transient Component compareComponent;
	private transient Integer tipo;

	public MenorIgualConstraint(Component compareComponent, Integer tipo) {
		super();
		this.compareComponent = compareComponent;
		this.tipo = tipo;
	}


	public void validate(Component comp, Object value)
			throws WrongValueException {
		if (comp instanceof Intbox) {

			Integer enteredValue = 0;
			Integer comparedValue = 0;

			if (value != null)
				enteredValue = (Integer) value;

			if (compareComponent instanceof Intbox) {
				if (((Intbox) compareComponent).getValue() != null)
					comparedValue = ((Intbox) compareComponent).getValue();
				else
					comparedValue = 0;
			}else if (compareComponent instanceof Doublebox) {
				if (((Doublebox) compareComponent).getValue() != null)
					comparedValue = ((Doublebox) compareComponent).getValue().intValue();
				else
					comparedValue = 0;
			}

			if (enteredValue.intValue() > comparedValue.intValue()) {
				if (tipo == 1)
					throw new WrongValueException(comp, "La cantidad a entregar no puede ser mayor que la cantidad vendida ");
				else  if (tipo == 2)
					throw new WrongValueException(comp, "La cantidad de producto no puede ser mayor a la existencia ");
				else  if (tipo == 3)//by RTC
					throw new WrongValueException(comp, "La cantidad a devolver de producto no puede ser mayor a la vendida ");

			}
		}else if (comp instanceof Doublebox) {

			Double enteredValue = 0.0;
			Double comparedValue = 0.0;

			if (value != null)
				enteredValue = (Double) value;

			if (compareComponent instanceof Doublebox) {
				if (((Doublebox) compareComponent).getValue() != null)
					comparedValue = ((Doublebox) compareComponent).getValue();
				else
					comparedValue = 0.0;
			}
			if (enteredValue > comparedValue) {
				if (tipo == 1)
					throw new WrongValueException(comp, "La cantidad a entregar no puede ser mayor que la cantidad vendida ");
				else  if (tipo == 2)
					throw new WrongValueException(comp, "La cantidad de producto no puede ser mayor a la existencia ");
				else  if (tipo == 3)//by RTC
					throw new WrongValueException(comp, "La cantidad a devolver de producto no puede ser mayor a la vendida ");
				else  if (tipo == 4)//by MMAP
					throw new WrongValueException(comp, "La importe a devolver no puede ser mayor al importe del total de la factutra");

			}
		}
		else if (comp instanceof Textbox) {

			Double enteredValue = 0.0;
			Double comparedValue = 0.0;

			if (value != null)
				enteredValue = new Double(value.toString());
			
			
			comparedValue = new Double(((Textbox) compareComponent).getValue());

			if (enteredValue > comparedValue) {
				if (tipo == 1)
					throw new WrongValueException(comp, "La cantidad a entregar no puede ser mayor que la cantidad vendida ");
				else  if (tipo == 2)
					throw new WrongValueException(comp, "La cantidad de producto no puede ser mayor a la existencia ");
				else  if (tipo == 3)//by RTC
					throw new WrongValueException(comp, "La cantidad a devolver de producto no puede ser mayor a la vendida ");
				else  if (tipo == 4)//by MMAP
					throw new WrongValueException(comp, "La subtotal a devolver no puede ser mayor al subtotal del total de la factutra");

			}
		}
	}



}
