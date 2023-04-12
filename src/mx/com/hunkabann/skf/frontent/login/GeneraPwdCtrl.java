package mx.com.hunkabann.skf.frontent.login;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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

public class GeneraPwdCtrl extends GenericForwardComposer implements Serializable {
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	// controles
	protected transient Window generaPwdWindow;
	protected transient Textbox txUsuario;
	protected transient Textbox txCurrentPwd;
	protected transient Textbox txNewPwd;
	protected transient Textbox txNewPwdAgain;
	protected transient Textbox txEmail;
	protected transient Button btnAceptar;
	protected transient Button btnCancelar;	
	
	private UsuarioService service = new UsuarioService();
	String PWD = "";
	Codificador codi = new Codificador(); 
	String Cifrado = "";
	
	
	public void onCreate$generaPwdWindow(Event event) throws Exception 
	{
		
		Map<String, Object> args = getCreationArgsMap(event);

		generaPwdWindow.doModal();
		
	}	// onCreate
	
	
	public void onClick$btnAceptar(Event event) throws Exception
	{
		
		MailSender ms = new MailSender();
		
		// verifica que todos los campos esten llenos
		if(txUsuario.getText().trim().equals(""))
		{
			txUsuario.focus();
			Messagebox.show("El campo Usuario no debe de estar vacío", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
			return;
		}
		// verifica que todos los campos esten llenos
		if(txEmail.getText().trim().equals(""))
		{
			txEmail.focus();
			Messagebox.show("El campo Email no debe de estar vacío", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
			return;
		}
//		if(txCurrentPwd.getText().trim().equals("")){
//			txCurrentPwd.focus();
//			Messagebox.show("El campo contrase�a no debe de estar vac�o", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			txCurrentPwd.focus();
//			return;
//		}
//		if(txNewPwd.getText().trim().equals(""))
//		{
//			txNewPwd.focus();
//			Messagebox.show("El campo nueva contraseña no debe de estar vacío", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			txNewPwd.focus();
//			return;
//		}
//		if(txNewPwdAgain.getText().trim().equals(""))
//		{
//			txNewPwdAgain.focus();
//			Messagebox.show("El campo retecleo de nueva contrase�a no debe de estar vac�o", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			txNewPwdAgain.focus();
//			return;
//		}
//		
//		// verifica que el nuevo pwd en 2 los campos coincida
//		if(!txNewPwd.getText().trim().equals(txNewPwdAgain.getText().trim()))
//		{
//			txNewPwdAgain.setText("");
//			txNewPwd.setText("");
//			txNewPwd.focus();
//			Messagebox.show("La nueva contrase�a no coincide", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			txNewPwd.focus();
//
//			return;
//		}
		
		PWD = Password.generaAleatorio(10);
		System.out.println("PAssword ---->> " + PWD);
		try {
			Cifrado =codi.cifrar(PWD);
			System.out.println(Cifrado);
		} catch (UnsupportedEncodingException e) {
			// Error al Cifrar la Contraseña
			Messagebox.show("Error al Cifrar la contraseña", "¡Error", org.zkoss.zul.Messagebox.OK, "");
			e.printStackTrace();
		}
//		TbUsuario anUser = new TbUsuario();
//		anUser.setPassword(Cifrado);
		
		
		// verifica que usuario y pwd existenen bd
//		TbUsuario user = service.getSimpleUserByUsername(txUsuario.getText().trim());
//		
//		
//		if(user == null)
//		{
//			txUsuario.focus();
//			Messagebox.show("Usuario no registrado", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			return;
//			
//		}
//		TbPersona person = service.getPersonaEmail(user,txEmail.getValue().trim());
//		
//		if(person == null)
//		{
//			txEmail.focus();
//			Messagebox.show("Email no registrado", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			return;
//			
//		}
		
//		Codificador cod = new Codificador();
//		
//		if(!user.getPassword().equals(cod.cifrar(txCurrentPwd.getText().trim())))
//		{
//			txUsuario.focus();
//			Messagebox.show("Credenciales no coincidentes", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			return;
//			
//		}
//		
//		if(!Password.valido(txNewPwd.getText().trim()))
//		{
//			txNewPwdAgain.setText("");
//			txNewPwd.setText("");
//			txNewPwd.focus();
//			Messagebox.show("La nueva contrase�a no es valida", "� A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			txNewPwd.focus();
//			return;

//		}
		
		
		// actualiza la bd
//		service.actualizaPwdByUsername(user,Cifrado );
			
//		Messagebox.show("Operación realizada correctamente", "! A v i s o !", org.zkoss.zul.Messagebox.OK, "");
		
		ms.setTo(txEmail.getValue().trim());
		ms.setMessage("El Usuario Fue Actualizado en el proceso de Loguin UserName: "+ txUsuario.getText().trim() +"  Password: " + PWD);
		
		if(ms.sendMessage())
		{
//			us.actualizaPwd(txEmail.getText().trim(), l_stNewPwd);
		
			Messagebox.show("Operación realizada correctamente, El usuario fue guardado y fue enviada la contraseña", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
//			Messagebox.show("Password:" + PWD, "¡Informacion!   "+txt_UserName.getText(), org.zkoss.zul.Messagebox.OK, "");
		}
		else
		{
			Messagebox.show("No se pudo Realizar la operación, El usuario no fue guardado y no fue enviada la contraseña", "¡ A v i s o !", org.zkoss.zul.Messagebox.OK, "");
			
		}

		generaPwdWindow.onClose();
			
		
	}	// onClick$btnAceptar
	
	
	
	
	
	public void onClick$btnCancelar(Event event) throws Exception
	{
		generaPwdWindow.onClose();
		
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
