package mx.com.hunkabann.skf.frontent.util;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Textbox;

public class NoEmptyAndEqualStringsConstraint implements Constraint, java.io.Serializable {

	private static final long serialVersionUID = 4052163775381888061L;
	private final Component compareComponent;

	public NoEmptyAndEqualStringsConstraint(Component compareComponent) {
		super();
		this.compareComponent = compareComponent;
	}

	//@Override
	public void validate(Component comp, Object value) throws WrongValueException {

		if (comp instanceof Textbox) {

			final String enteredValue = (String) value;
			if (compareComponent instanceof Textbox) {
				//if (enteredValue.isEmpty()) {
				if (enteredValue.length() == 0) {
					throw new WrongValueException(comp, Labels.getLabel("message.error.CannotBeEmpty"));
				}

				final String comparedValue = ((Textbox) compareComponent).getValue();
				if (!enteredValue.equals(comparedValue)) {
					throw new WrongValueException(comp, Labels.getLabel("message.error.RetypedPasswordMustBeSame"));
				}
			}
		}
	}

}
