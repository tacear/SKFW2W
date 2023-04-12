package mx.com.hunkabann.skf.frontent.util;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ComboFactoryModelItemRenderer implements ListitemRenderer, Serializable {

	private static final long serialVersionUID = 1123L;
	@SuppressWarnings("unused")
	private transient static final Logger logger = Logger.getLogger(ComboFactoryModelItemRenderer.class);

	//@Override
	public void render(Listitem item, Object data) throws Exception {

		ComboFactory comboFactory = (ComboFactory) data;

		// if (logger.isDebugEnabled()) {
		// logger.debug("--> " + typ.getStpTypname());
		// }

		Listcell lc = new Listcell(comboFactory.getNombre());
		lc.setParent(item);
		item.setTooltiptext(comboFactory.getNombre());
		item.setAttribute("data", data);
	}

}