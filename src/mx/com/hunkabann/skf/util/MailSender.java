package mx.com.hunkabann.skf.util;
/**********************************************
* Clase     : MailSender
* Proposito : Envio de mensajes (correo), a traves de un servidor SMTP
* Autor     : RTC, Hunkabann
* Fecha     : 30 / Septiembre / 2010
**********************************************/
 

/**********************************************
*
* MODIFICACIONES
*      <INICIALES>, <EMPRESA>; <COMENTARIO INDICANDO LA MODIFICACION>
*
**********************************************/

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;

import org.zkoss.util.resource.Labels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
//import javax.activation.*;

public class MailSender
{
	
	private String i_stHost    = "";
	private String i_stFrom    = "";
	private String i_stTo      = "";
	private String i_stSubject = "";
	private String i_stMessage = "";
	private String i_stPwd = "";
	
	public MailSender()
	{
	} // fin constructor
	
	public void setHost(String p_stHost)
	{
		// establece el servidor smtp (ip o alias)
		i_stHost = p_stHost;
		
	} // fin de setHost
	
	public String getHost()
	{
		// regresa el servidor smtp
		return i_stHost;
	
	}	// getHost
	
	public void setFrom(String p_stFrom)
	{
		// establece el remitente del correo
		i_stFrom = p_stFrom;
		
	} // fin de setFrom
	
	public String getFrom()
	{
		//regresa el remitente del correo
		return i_stFrom;
	
	}	// getFrom
	
	public void setTo(String p_stTo)
	{
		// esteblece el destinatario del correo
		i_stTo = p_stTo;
		
	} // fin de setTo
	
	public String getTo()
	{
		// regresa el destinatario del correo
		return i_stTo;
	
	}	// getTo
	
	public void setSubject(String p_stSubject)
	{
		// establece el asunto del correo
		i_stSubject = p_stSubject;
		
	} // fin de setSubject
	
	public String getSubject()
	{
		// regresa el asunto del correo
		return i_stSubject;
	
	}	// getSubject
	
	public void setMessage(String p_stMessage)
	{
		// establece el mensaje del correo
		i_stMessage = p_stMessage;
		
	} // fin de setMessage
	
	public String getMessage()
	{
		// regresa el mensaje del correo
		return i_stMessage;
	
	}	// getMessage
	
	
	

	public boolean sendMessage() throws SendFailedException, MessagingException 
	{
		Properties email_props = new Properties();
		InputStream is = null;
		boolean enviado = false;
		 
		try {
			
		    is = getClass().getResourceAsStream("/email.properties");
		    email_props.load(is);
		    
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}

		
         Properties props = new Properties();//propiedades a agragar
         
         i_stFrom = email_props.getProperty("from");
         i_stPwd = email_props.getProperty("pwdFrom");
         
         String l_stNewPwd = Password.generaAleatorio(10);
         
         props.put("mail.smtp.user", i_stFrom);//correo origen
         props.put("mail.smtp.host", email_props.getProperty("smtp"));//tipo de servidor
         props.put("mail.smtp.port", email_props.getProperty("port"));//puerto de salida
         props.put("mail.smtp.starttls.enable", "true");//inicializar el servidor
         props.put("mail.smtp.auth", "true");//autentificacion
         props.put("mail.smtp.socketFactory.port", email_props.getProperty("port"));//activar el puerto
         props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.socketFactory.fallback", "false");
         SecurityManager security = System.getSecurityManager();
         
         try {
             Authenticator auth = new AutentificadorSMTP();//autentificar el correo
             Session session = Session.getInstance(props, auth);//se inica una session
             // session.setDebug(true);
             MimeMessage msg = new MimeMessage(session);//se crea un objeto donde ira la estructura del correo
             msg.setText(email_props.getProperty("mensaje") + "\n" + i_stMessage);//seteo el cuertpo del mensaje
             msg.setSubject(email_props.getProperty("asunto"));//setea asusto (opcional)
             msg.setFrom(new InternetAddress(i_stFrom));//agrega la la propiedad del correo origen
             msg.addRecipient(Message.RecipientType.TO, new InternetAddress(i_stTo));//agrega el destinatario
             Transport.send(msg);//envia el mensaje
             enviado = true;
         } catch (Exception mex) {//en caso de que ocurra un error se crea una excepcion
        	 enviado = false;
        	 System.out.println("Error al Enviar el Email: " + mex.toString());
         }//fin try-catch
		
		return enviado;
         
	} //sendMessage
	
	public boolean sendMessageErrComplemento(String error) throws SendFailedException, MessagingException 
	{
		Properties email_props = new Properties();
		InputStream is = null;
		boolean enviado = false;
		 
		try {
			
		    is = getClass().getResourceAsStream("/email.properties");
		    email_props.load(is);
		    
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}

		
         Properties props = new Properties();//propiedades a agragar
         
         i_stFrom = email_props.getProperty("from");
         i_stPwd = email_props.getProperty("pwdFrom");
         
         String l_stNewPwd = Password.generaAleatorio(10);
         
         props.put("mail.smtp.user", i_stFrom);//correo origen
         props.put("mail.smtp.host", email_props.getProperty("smtp"));//tipo de servidor
         props.put("mail.smtp.port", email_props.getProperty("port"));//puerto de salida
         props.put("mail.smtp.starttls.enable", "true");//inicializar el servidor
         props.put("mail.smtp.auth", "true");//autentificacion
         props.put("mail.smtp.socketFactory.port", email_props.getProperty("port"));//activar el puerto
         props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.socketFactory.fallback", "false");
         SecurityManager security = System.getSecurityManager();
         
         try {
             Authenticator auth = new AutentificadorSMTP();//autentificar el correo
             Session session = Session.getInstance(props, auth);//se inica una session
             // session.setDebug(true);
             MimeMessage msg = new MimeMessage(session);//se crea un objeto donde ira la estructura del correo
             msg.setText(email_props.getProperty("mensajeUsuarioErrComplemento") + "\n" + i_stMessage+ "\n" +error);//seteo el cuertpo del mensaje
             msg.setSubject(email_props.getProperty("asuntoErrComplemento"));//setea asusto (opcional)
             msg.setFrom(new InternetAddress(i_stFrom));//agrega la la propiedad del correo origen
             msg.addRecipient(Message.RecipientType.TO, new InternetAddress(i_stTo));//agrega el destinatario
             Transport.send(msg);//envia el mensaje
             enviado = true;
         } catch (Exception mex) {//en caso de que ocurra un error se crea una excepcion
        	 enviado = false;
        	 System.out.println("Error al Enviar el Email: " + mex.toString());
         }//fin try-catch
		
		return enviado;
         
	} //sendMessage
	
	public boolean sendMessageErrComprobante(String error) throws SendFailedException, MessagingException 
	{
		Properties email_props = new Properties();
		InputStream is = null;
		boolean enviado = false;
		 
		try {
			
		    is = getClass().getResourceAsStream("/email.properties");
		    email_props.load(is);
		    
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}

		
         Properties props = new Properties();//propiedades a agragar
         
         i_stFrom = email_props.getProperty("from");
         i_stPwd = email_props.getProperty("pwdFrom");
         
         String l_stNewPwd = Password.generaAleatorio(10);
         
         props.put("mail.smtp.user", i_stFrom);//correo origen
         props.put("mail.smtp.host", email_props.getProperty("smtp"));//tipo de servidor
         props.put("mail.smtp.port", email_props.getProperty("port"));//puerto de salida
         props.put("mail.smtp.starttls.enable", "true");//inicializar el servidor
         props.put("mail.smtp.auth", "true");//autentificacion
         props.put("mail.smtp.socketFactory.port", email_props.getProperty("port"));//activar el puerto
         props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.socketFactory.fallback", "false");
         SecurityManager security = System.getSecurityManager();
         
         try {
             Authenticator auth = new AutentificadorSMTP();//autentificar el correo
             Session session = Session.getInstance(props, auth);//se inica una session
             // session.setDebug(true);
             MimeMessage msg = new MimeMessage(session);//se crea un objeto donde ira la estructura del correo
             msg.setText(email_props.getProperty("mensajeUsuarioErr") + "\n" + i_stMessage+ "\n" +error);//seteo el cuertpo del mensaje
             msg.setSubject(email_props.getProperty("asuntoErr"));//setea asusto (opcional)
             msg.setFrom(new InternetAddress(i_stFrom));//agrega la la propiedad del correo origen
             msg.addRecipient(Message.RecipientType.TO, new InternetAddress(i_stTo));//agrega el destinatario
             Transport.send(msg);//envia el mensaje
             enviado = true;
         } catch (Exception mex) {//en caso de que ocurra un error se crea una excepcion
        	 enviado = false;
        	 System.out.println("Error al Enviar el Email: " + mex.toString());
         }//fin try-catch
		
		return enviado;
         
	} //sendMessage
	
	public boolean sendMessageUser() throws SendFailedException, MessagingException 
	{
		Properties email_props = new Properties();
		InputStream is = null;
		boolean enviado = false;
		 
		try {
			
		    is = getClass().getResourceAsStream("/email.properties");
		    email_props.load(is);
		    
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}

		
         Properties props = new Properties();//propiedades a agragar
         
         i_stFrom = email_props.getProperty("from");
         i_stPwd = email_props.getProperty("pwdFrom");
         
         String l_stNewPwd = Password.generaAleatorio(10);
         
         props.put("mail.smtp.user", i_stFrom);//correo origen
         props.put("mail.smtp.host", email_props.getProperty("smtp"));//tipo de servidor
         props.put("mail.smtp.port", email_props.getProperty("port"));//puerto de salida
         props.put("mail.smtp.starttls.enable", "true");//inicializar el servidor
         props.put("mail.smtp.auth", "true");//autentificacion
         props.put("mail.smtp.socketFactory.port", email_props.getProperty("port"));//activar el puerto
         props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.socketFactory.fallback", "false");
         SecurityManager security = System.getSecurityManager();
         
         try {
             Authenticator auth = new AutentificadorSMTP();//autentificar el correo
             Session session = Session.getInstance(props, auth);//se inica una session
             // session.setDebug(true);
             MimeMessage msg = new MimeMessage(session);//se crea un objeto donde ira la estructura del correo
             msg.setText(email_props.getProperty("mensajeUsuario") + "\n" + i_stMessage);//seteo el cuertpo del mensaje
             msg.setSubject(email_props.getProperty("asunto"));//setea asusto (opcional)
             msg.setFrom(new InternetAddress(i_stFrom));//agrega la la propiedad del correo origen
             msg.addRecipient(Message.RecipientType.TO, new InternetAddress(i_stTo));//agrega el destinatario
             Transport.send(msg);//envia el mensaje
             enviado = true;
         } catch (Exception mex) {//en caso de que ocurra un error se crea una excepcion
        	 enviado = false;
        	 System.out.println("Error al Enviar el Email: " + mex.toString());
         }//fin try-catch
		
		return enviado;
         
	} //sendMessage
	public boolean sendMessageOrden() throws SendFailedException, MessagingException 
	{
		Properties email_props = new Properties();
		InputStream is = null;
		boolean enviado = false;
		 
		try {
			
		    is = getClass().getResourceAsStream("/email.properties");
		    email_props.load(is);
		    
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}

		
         Properties props = new Properties();//propiedades a agragar
         
         i_stFrom = email_props.getProperty("from");
         i_stPwd = email_props.getProperty("pwdFrom");
         
         String l_stNewPwd = Password.generaAleatorio(10);
         
         props.put("mail.smtp.user", i_stFrom);//correo origen
         props.put("mail.smtp.host", email_props.getProperty("smtp"));//tipo de servidor
         props.put("mail.smtp.port", email_props.getProperty("port"));//puerto de salida
         props.put("mail.smtp.starttls.enable", "true");//inicializar el servidor
         props.put("mail.smtp.auth", "true");//autentificacion
         props.put("mail.smtp.socketFactory.port", email_props.getProperty("port"));//activar el puerto
         props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.socketFactory.fallback", "false");
         SecurityManager security = System.getSecurityManager();
         
         try {
             Authenticator auth = new AutentificadorSMTP();//autentificar el correo
             Session session = Session.getInstance(props, auth);//se inica una session
             // session.setDebug(true);
             MimeMessage msg = new MimeMessage(session);//se crea un objeto donde ira la estructura del correo
             msg.setText(email_props.getProperty("mensajeOrden") + "\n" + i_stMessage);//seteo el cuertpo del mensaje
             msg.setSubject(email_props.getProperty("asuntoOrden"));//setea asusto (opcional)
             msg.setFrom(new InternetAddress(i_stFrom));//agrega la la propiedad del correo origen
             msg.addRecipient(Message.RecipientType.TO, new InternetAddress(i_stTo));//agrega el destinatario
             Transport.send(msg);//envia el mensaje
             enviado = true;
         } catch (Exception mex) {//en caso de que ocurra un error se crea una excepcion
        	 enviado = false;
        	 System.out.println("Error al Enviar el Email: " + mex.toString());
         }//fin try-catch
		
		return enviado;
         
	} //sendMessage
	
    private class AutentificadorSMTP extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(i_stFrom, i_stPwd);
        }
    }	
	


} // termian clase MailSender

