package mx.com.hunkabann.skf.policy;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.hunkabann.skf.backend.UsuarioService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class AuthentificationListener implements AuthenticationFailureHandler{
	
	UsuarioService usuarioService = new UsuarioService();
	

	
	public void onAuthenticationFailure(HttpServletRequest req,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
//		TbUsuario usuario = null;
		String codRet = "";
		
		
		
		System.out.println("excepcion: " + exception.getMessage());
		
		UsernamePasswordAuthenticationToken user =(UsernamePasswordAuthenticationToken)exception.getAuthentication();
		
		try {
			
//			usuario = usuarioService.getUserByUsername(user.getName());
//			System.out.println("usuario -------->>>> " +usuario.getUsername() + "    Estatus: " + usuario.getStatus() );
			
		} catch (Exception e) {
			
			System.out.println("chafio----------- " + e.getMessage());
			
			if(e.getMessage() == null){
				
				codRet = "Credenciales Invalidas";
				
				
			}else{
			
//			throw new UsernameNotFoundException(e.getMessage());
			if(e.getMessage().equals("Cannot open connection"))
				codRet = "No se pudo hacer conexion con la BD";
			else
				codRet = e.getMessage();
			}
		}
		
//		if(codRet.equals(""))
//		{
//			if(usuario == null || usuario.getUsername() == null || usuario.getPassword() == null)
//			{
//				//System.out.println("usuario no valido -----------");
////				throw new UsernameNotFoundException("Invalid User");
//				codRet = "Usuario no Valido";
//				
//			}
//		}
//		
//		if(codRet.equals(""))
//		{
//			System.out.println(!usuario.getStatus().equals(1));
//			System.out.println(usuario.getStatus() == null);
//			if(usuario.getStatus() == null || !usuario.getStatus().equals(1) )
////				if(usuario.getActivo() == null || !usuario.getActivo().equals("1") )
//			{
//				//System.out.println("usuario no activo -----------");
////				throw new UsernameNotFoundException("Usuario no activo");
//				codRet = "Usuario no Activo";
//			}
//		}
//		
//		if(codRet.equals(""))
//		{
//			if(usuario.getPwdExpire() == null || usuario.getPwdExpire().getTime() < (new Date()).getTime())
//			{
//				//System.out.println("expiro vigencia -----------");
////				throw new UsernameNotFoundException("Expir� vigencia del pwd");
//				codRet = "La Vigencia del Password Expiro Favor de Regenerar la contraseña";
//			}
//		}
		

		int intentos = 0;
		 
		System.out.println(req.getSession().getAttribute(user.getName()));
		 
		if(req.getSession().getAttribute(user.getName()) == null)
		{
			intentos = 1;
		}
		else
		{
			intentos = Integer.parseInt(req.getSession().getAttribute(user.getName()).toString());
			intentos++;
			 
		}
		 
		req.getSession().setAttribute(user.getName(), intentos + "");
		 
		if(intentos > 2)
		{
			UsuarioService service= new UsuarioService();
			try {
//				service.setUserInactiveByUserName(user.getName());
				codRet = "Se excedio el Maximo de Intentos Permitidos";
			} catch (Exception e) {
				e.printStackTrace();
			}
			req.getSession().removeAttribute(user.getName());
			 
		}
		
		if(codRet.equals(""))
		{
			codRet = "Datos invalidos, favor de Verificarlos";
		}
		
		//System.out.println("codRet: " + codRet);
		 
		 
		// user contains required data
		response.sendRedirect("login.zul?login_error=" + codRet);	
          
	}	// onAuthenticationFailure
	
	

}	// end of class
