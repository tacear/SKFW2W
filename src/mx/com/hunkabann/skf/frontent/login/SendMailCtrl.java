package mx.com.hunkabann.skf.frontent.login;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.util.Codificador;
import mx.com.hunkabann.skf.util.MailSender;
import mx.com.hunkabann.skf.util.Password;

import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class SendMailCtrl extends GenericForwardComposer implements Serializable {
	
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	// controles
	protected transient Window sendMailWindow;
	protected transient Textbox txEmail;
	protected transient Button btnAceptar;
	protected transient Button btnCancelar;
	
	
	
	
	public void onCreate$sendMailWindow(Event event) throws Exception 
	{
		
		Map<String, Object> args = getCreationArgsMap(event);

		sendMailWindow.doModal();
		
	}	// onCreate
	
	
	
	

	public void onClick$btnAceptar(Event event) throws Exception
	{
		try {
			
			UsuarioService us = new UsuarioService();
			MailSender ms = new MailSender();
			Codificador c = new Codificador();
			
//			List<String> users = us.getUserByEmail(txEmail.getText().trim());
//			
//			if(users.size() == 0)
//			{
//				Messagebox.show("El email proporcionado no est� registrado", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//				return;
//			}
//			String l_stNewPwd = Password.generaAleatorio(10);
			
			
			ms.setTo(txEmail.getText().trim());
//			ms.setMessage(l_stNewPwd);
//			l_stNewPwd = c.cifrar(l_stNewPwd);
//
//			if(ms.sendMessage())
//			{
//				us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
//			
//				Messagebox.show("Operaci�n realizada correctamente", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			}
//			else
//			{
//				Messagebox.show("No se pudo Realizar la operaci�n", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//				
//			}
			

			sendMailWindow.onClose();
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			if(e.toString().indexOf("Formato de direcci�n de correo") > -1)
				return;
			
			Messagebox.show(e.getMessage(), "�Aviso!", org.zkoss.zul.Messagebox.OK, "");
			
			logger.severe(e.getMessage());
		
		}
		
	}	// onClick$btnAceptar
	
	
	
	
	
	public void onClick$btnCancelar(Event event) throws Exception
	{
		sendMailWindow.onClose();
		
	}	// onClick$btnCancelar
	
	
	
	
	public void onClose$sendMailWindow(Event event) throws Exception {
		
		// libera recursos

	}	// onClose$sendMailWindow
	
 
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCreationArgsMap(Event event) {
		
		CreateEvent ce = (CreateEvent) ((ForwardEvent) event).getOrigin();
		return (Map<String, Object>) ce.getArg();
		
	}	// getCreationArgsMap



}	// SendMailCtrl
