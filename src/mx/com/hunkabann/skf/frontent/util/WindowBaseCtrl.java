package mx.com.hunkabann.skf.frontent.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Window;

public abstract class WindowBaseCtrl extends Window implements AfterCompose, Serializable {

	private static final long serialVersionUID = -2179229704315045689L;
	protected transient AnnotateDataBinder binder;

	protected transient Map<String, Object> args;

	public void doOnCreateCommon(Window w) throws Exception {
		binder = new AnnotateDataBinder(w);
		binder.loadAll();
	}

	@SuppressWarnings("unchecked")
	public void doOnCreateCommon(Window w, Event fe) throws Exception {
		doOnCreateCommon(w);
		CreateEvent ce = (CreateEvent) ((ForwardEvent) fe).getOrigin();
		args = ce.getArg();
	}

	public WindowBaseCtrl() {
		super();
		this.setStyle("body {padding 0 0;}");
	}

	//@Override
	public void afterCompose() {
		processRecursive(this, this);

		// Components.wireVariables(this, this); // auto wire variables
		// Components.addForwards(this, this); // auto forward
	}

	/**
	 * Go recursive through all founded components and wires all vars and added
	 * all forwarders for ALL window components. <br>
	 * 
	 * @param main
	 * @param child
	 */
	@SuppressWarnings("unchecked")
	private void processRecursive(Window main, Window child) {
		Components.wireVariables(main, child);
		Components.addForwards(main, this);
		List<Component> winList = child.getChildren();
		for (Component window : winList) {
			if (window instanceof Window) {
				processRecursive(main, (Window) window);
			}
		}
	}

}
