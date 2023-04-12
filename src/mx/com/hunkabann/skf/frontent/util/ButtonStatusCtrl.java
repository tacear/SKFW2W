package mx.com.hunkabann.skf.frontent.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//import mx.com.hunkabann.sipv.workspace.UserWorkspace;

import mx.com.hunkabann.skf.workspace.UserWorkspace;

import org.zkoss.zul.Button;


public class ButtonStatusCtrl implements Serializable {

	private static final long serialVersionUID = -4907914938602465474L;

	private static enum ButtonEnum {
		New, Edit, Delete, Save, Close;
	}

	private final Map<ButtonEnum, Button> buttons = new HashMap<ButtonEnum, Button>(5);

	@SuppressWarnings("unused")
	final private UserWorkspace workspace;

	/** rightName prefix */
	@SuppressWarnings("unused")
	private final String _rightPrefix;

	/**
	 * Var for disable/enable or visible/not visible mode of the butttons. <br>
	 * true = disable the button <br>
	 * false = make the button unvisible<br>
	 */
	private final boolean disableButtons = false;

	/**
	 * Constructor
	 * 
	 * @param btnNew
	 *            (New Button)
	 * @param btnEdit
	 *            (Edit Button)
	 * @param btnDelete
	 *            (Delete Button)
	 * @param btnSave
	 *            (Save Button)
	 * @param btnClose
	 *            (Close Button)
	 */
	public ButtonStatusCtrl(UserWorkspace userWorkspace, String rightPrefix, Button btnNew, Button btnEdit, Button btnDelete, Button btnSave,
			Button btnClose) {
		super();
		this.workspace = userWorkspace;

		this._rightPrefix = rightPrefix + "btn";

		buttons.put(ButtonEnum.New, btnNew);
		buttons.put(ButtonEnum.Edit, btnEdit);
		buttons.put(ButtonEnum.Delete, btnDelete);
		buttons.put(ButtonEnum.Save, btnSave);
		buttons.put(ButtonEnum.Close, btnClose);

		setBtnImages();
	}
	
	
	public ButtonStatusCtrl(UserWorkspace userWorkspace, String rightPrefix, Button btnNew, Button btnEdit, Button btnSave,	Button btnClose) {
		super();
		this.workspace = userWorkspace;

		this._rightPrefix = rightPrefix + "btn";

		buttons.put(ButtonEnum.New, btnNew);
		buttons.put(ButtonEnum.Edit, btnEdit);
		buttons.put(ButtonEnum.Save, btnSave);
		buttons.put(ButtonEnum.Close, btnClose);

		setBtnImages();
	}
	

	/**
	 * Set the images fore the buttons.<br>
	 */
	private void setBtnImages() {
		String imagePath = "/images/icons/";

		setImage(ButtonEnum.New, imagePath + "btn_new2_16x16.gif");
		setImage(ButtonEnum.Edit, imagePath + "btn_edit2_16x16.gif");
		setImage(ButtonEnum.Delete, imagePath + "btn_delete2_16x16.gif");
		setImage(ButtonEnum.Save, imagePath + "btn_save2_16x16.gif");
		setImage(ButtonEnum.Close, imagePath + "btn_exitdoor2_16x16.gif");
	}

	
	/**
	 * Set all Buttons for the Mode NEW is pressed. <br>
	 */
	public void setBtnStatus_New() {
		if (disableButtons) {
			setDisabled(ButtonEnum.New, true);
			setDisabled(ButtonEnum.Edit, true);
			//setDisabled(ButtonEnum.Delete, true);
			setDisabled(ButtonEnum.Save, false);
			setDisabled(ButtonEnum.Close, false);
		} else {
			setVisible(ButtonEnum.New, false);
			setVisible(ButtonEnum.Edit, false);
			//setVisible(ButtonEnum.Delete, false);
			setVisible(ButtonEnum.Save, true);
			setVisible(ButtonEnum.Close, true);
		}
		setVisible(ButtonEnum.Delete, false);
	}

	/**
	 * Set all Buttons for the Mode EDIT is pressed. <br>
	 */
	public void setBtnStatus_Edit() {
		if (disableButtons) {
			setDisabled(ButtonEnum.New, true);
			setDisabled(ButtonEnum.Edit, true);
			//setDisabled(ButtonEnum.Delete, true);
			setDisabled(ButtonEnum.Save, false);
			setDisabled(ButtonEnum.Close, false);
		} else {
			setVisible(ButtonEnum.New, false);
			setVisible(ButtonEnum.Edit, false);
			//setVisible(ButtonEnum.Delete, false);
			setVisible(ButtonEnum.Save, true);
			setVisible(ButtonEnum.Close, true);
		}
		setVisible(ButtonEnum.Delete, false);
	}

	/**
	 * Not needed yet, because after pressed the delete button <br>
	 * the window is closing. <br>
	 */
	public void setBtnStatus_Delete() {
	}

	/**
	 * Set all Buttons for the Mode SAVE is pressed. <br>
	 */
	public void setBtnStatus_Save() {
		setInitEdit();
	}

	/**
	 * Set all Buttons for the Mode init in EDIT mode. <br>
	 * This means that the Dialog window is opened and <br>
	 * shows data. <br>
	 */
	public void setInitEdit() {
		if (disableButtons) {
			setDisabled(ButtonEnum.New, false);
			setDisabled(ButtonEnum.Edit, false);
			//setDisabled(ButtonEnum.Delete, false);
			setDisabled(ButtonEnum.Save, true);
			setDisabled(ButtonEnum.Close, false);
		} else {
			setVisible(ButtonEnum.New, true);
			setVisible(ButtonEnum.Edit, true);
			//setVisible(ButtonEnum.Delete, true);
			setVisible(ButtonEnum.Save, false);
			setVisible(ButtonEnum.Close, true);
		}
		setVisible(ButtonEnum.Delete, false);
	}

	/**
	 * Set all Buttons for the Mode init in NEW mode. <br>
	 * This means that the Dialog window is freshly new <br>
	 * and have no data. <br>
	 */
	public void setInitNew() {
		if (disableButtons) {
			setDisabled(ButtonEnum.New, true);
			setDisabled(ButtonEnum.Edit, true);
			//setDisabled(ButtonEnum.Delete, true);
			setDisabled(ButtonEnum.Save, false);
			setDisabled(ButtonEnum.Close, false);
		} else {
			setVisible(ButtonEnum.New, false);
			setVisible(ButtonEnum.Edit, false);
			//setVisible(ButtonEnum.Delete, false);
			setVisible(ButtonEnum.Save, true);
			setVisible(ButtonEnum.Close, true);
		}
		setVisible(ButtonEnum.Delete, false);
	}

	/**
	 * Sets the image of a button.<br>
	 * 
	 * @param b
	 * @param imagePath
	 *            path and image name
	 */
	private void setImage(ButtonEnum b, String imagePath) {
		buttons.get(b).setImage(imagePath);
	}

	/**
	 * Set the button visible.<br>
	 * 
	 * @param b
	 * @param visible
	 *            True or False
	 */
	private void setVisible(ButtonEnum b, boolean visible) {
		if (visible) {
			//derechos de permisos
			//if (workspace.isAllowed(_rightPrefix + b.name())) {
				buttons.get(b).setVisible(visible);
			//}
		} else {
			buttons.get(b).setVisible(visible);
		}
	}

	/**
	 * Sets the button disabled.<br>
	 * 
	 * @param b
	 * @param disabled
	 *            True or False
	 */
	private void setDisabled(ButtonEnum b, boolean disabled) {
		if (disabled) {
			buttons.get(b).setDisabled(disabled);
		} else {
			//derechos de permisos
			//if (workspace.isAllowed(_rightPrefix + b.name())) {
				buttons.get(b).setDisabled(disabled);
			}
		//}
	}
}
