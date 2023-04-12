package mx.com.hunkabann.skf.frontent.login;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.util.Codificador;
import mx.com.hunkabann.skf.util.Password;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class CambiaPwdCtrl extends GenericForwardComposer implements Serializable {
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	// controles
	protected transient Window cambiaPwdWindow;
	protected transient Textbox txNewPwd;
	protected transient Textbox txNewPwdAgain;
	protected transient Button btnAceptar;
	protected transient Button btnCancelar;	
	
	private UsuarioService service = new UsuarioService();
//	private TbUsuario user;
	
	
	public void onCreate$cambiaPwdWindow(Event event) throws Exception 
	{
		
		Map<String, Object> args = getCreationArgsMap(event);
//		user = (TbUsuario)args.get("usuario");

		cambiaPwdWindow.doModal();
		
		
	}	// onCreate
	
	
	public void onClick$btnAceptar(Event event) throws Exception
	{
		
		// verifica que todos los campos esten llenos
		if(txNewPwd.getText().trim().equals(""))
		{
			txNewPwd.focus();
			Messagebox.show("El campo nueva contrase�a no debe de estar vac�o", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
			txNewPwd.focus();
			return;
		}
		if(txNewPwdAgain.getText().trim().equals(""))
		{
			txNewPwdAgain.focus();
			Messagebox.show("El campo retecleo de nueva contrase�a no debe de estar vac�o", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
			txNewPwdAgain.focus();
			return;
		}
		
		// verifica que el nuevo pwd en 2 los campos coincida
		if(!txNewPwd.getText().trim().equals(txNewPwdAgain.getText().trim()))
		{
			txNewPwdAgain.setText("");
			txNewPwd.setText("");
			txNewPwd.focus();
			Messagebox.show("La nueva contrase�a no coincide", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
			txNewPwd.focus();

			return;
		}
		
		if(!Password.valido(txNewPwd.getText().trim()))
		{
			txNewPwdAgain.setText("");
			txNewPwd.setText("");
			txNewPwd.focus();
			Messagebox.show("La nueva contrase�a no es valida", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
			txNewPwd.focus();
			return;

		}
		
		Codificador cod = new Codificador();
		
		
		// actualiza la bd
//		service.actualizaPwdByUsername(user, cod.cifrar(txNewPwd.getText().trim()));
			
		Messagebox.show("Operaci�n realizada correctamente", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");

		cambiaPwdWindow.onClose();
			
		
	}	// onClick$btnAceptar
	
	
	
	
	
	public void onClick$btnCancelar(Event event) throws Exception
	{
		Executions.sendRedirect("/j_spring_logout");		
		
	}	// onClick$btnCancelar
	
	
	
	
	public void onClose$sendMailWindow(Event event) throws Exception {
		
		// libera recursos

	}	// onClose$sendMailWindow
	
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCreationArgsMap(Event event) {
		
		CreateEvent ce = (CreateEvent) ((ForwardEvent) event).getOrigin();
		return (Map<String, Object>) ce.getArg();
		
	}	// getCreationArgsMap
	
	

}	// endof class
