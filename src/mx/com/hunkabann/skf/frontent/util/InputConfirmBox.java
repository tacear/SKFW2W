package mx.com.hunkabann.skf.frontent.util;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

public class InputConfirmBox extends Window {

	private static final long serialVersionUID = 8109634704496621100L;
	private static final Logger logger = Logger.getLogger(InputConfirmBox.class);

	public static String show(Component parent, String anQuestion) {
		return new InputConfirmBox(parent, anQuestion).textbox.getText();
	}

	private final String question;

	private final Textbox textbox;

	private InputConfirmBox(Component parent, String anQuestion) {
		super();
		question = anQuestion;
		textbox = new Textbox();

		setParent(parent);

		createBox();
	}

	private void createBox() {

		setWidth("350px");
		setHeight("110px");
		setTitle(Labels.getLabel("message_Information"));
		setId("confBox");
		setVisible(true);
		setClosable(true);
		addEventListener("onOK", new OnCloseListener());

		Vbox vbox = new Vbox();
		vbox.setParent(this);

		Label label = new Label();
		label.setValue(question);
		label.setParent(vbox);

		Separator sp = new Separator();
		sp.setBar(true);
		sp.setParent(vbox);

		Hbox hbox = new Hbox();
		hbox.setParent(vbox);

		Separator sep = new Separator();
		sep.setParent(hbox);

		textbox.setType("password");
		textbox.setWidth("100px");
		textbox.setParent(hbox);

		try {
			doModal();
		} catch (SuspendNotAllowedException e) {
			logger.fatal("", e);
		} catch (InterruptedException e) {
			logger.fatal("", e);
		}
	}

	final class OnCloseListener implements EventListener {
		//@Override
		public void onEvent(Event event) throws Exception {
			onClose();
		}
	}

}
