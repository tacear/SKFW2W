package mx.com.hunkabann.skf.frontent.util;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class ComboBoxFactoryModelItemRenderer implements ComboitemRenderer, Serializable {

	private static final long serialVersionUID = 1123L;

	private transient static final Logger logger = Logger.getLogger(ComboBoxFactoryModelItemRenderer.class);

	public void render(Comboitem item, Object data) throws Exception {

		ComboFactory comboFactory = (ComboFactory) data;

		if (logger.isDebugEnabled()) {
		logger.debug("--> " + comboFactory.getNombre());
		}
		
		item.setLabel(comboFactory.getNombre());
		item.setValue(comboFactory.getId()); 
		 
		item.setAttribute("data", data);
	}
}