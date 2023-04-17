package mx.com.hunkabann.skf.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mx.com.hunkabann.skf.backend.UsuarioService;
import mx.com.hunkabann.skf.mapeo.TbUsuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class PolicyManager implements UserDetailsService/*, AuthenticationProvider*/ {
	
	UsuarioService usuarioService = new UsuarioService();
	public UserDetails _userDetails;
	Collection<Integer>  rights;

	
	

	public UserDetails loadUserByUsername(String p_stUser)
			throws UsernameNotFoundException{
		
		GrantedAuthority[] grantedAuthorities = null;
		TbUsuario usuario = null;
		
		try {
			
			usuario = usuarioService.getUserByUsername(p_stUser);
			
		} catch (Exception e) {
			
			System.out.println("chafio-----------");
			
			throw new UsernameNotFoundException(e.getMessage());
		}
		
		if(usuario == null || usuario.getChPassword() == null || usuario.getChPassword() == null)
		{
			System.out.println("usuario no valido -----------");
			throw new UsernameNotFoundException("Invalid User");
			
		}
		
		
		
		if(usuario.getNuActivo() == null || !usuario.getNuActivo().equals(true) )
		{
			System.out.println("usuario no activo -----------");
			throw new UsernameNotFoundException("Usuario no activo");
		}
		
//		if(usuario.getPwdExpire() == null || usuario.getPwdExpire().getTime() < (new Date()).getTime())
//		{
//			System.out.println("expiro vigencia -----------");
//			throw new UsernameNotFoundException("Expir� vigencia del pwd");
//		}
		
		grantedAuthorities = getGrantedAuthority(usuario);
		
		UserDetails userDetails = new UserImpl(usuario, grantedAuthorities);
		_userDetails = userDetails;
		
		
		return userDetails;
		
	}	// loadUserByUsername
	
	
	
	private GrantedAuthority[] getGrantedAuthority(TbUsuario user) {
		
		try {
			rights = (usuarioService.getRightsByUser(user));
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		
		if (rights.size() == 0) {
			throw new UsernameNotFoundException("Invalid User");
		}
		
		
		ArrayList<GrantedAuthority> rechteGrantedAuthorities = new ArrayList<GrantedAuthority>(rights.size());
		
		for(Integer autoridad : rights)
			rechteGrantedAuthorities.add(new GrantedAuthorityImpl(autoridad.toString()));
		
		
		GrantedAuthority[] grantedAuthorities = rechteGrantedAuthorities.toArray(new GrantedAuthority[rechteGrantedAuthorities.size()]);
		
		
		return grantedAuthorities;
		
	}	// getGrantedAuthority


	/*

	@Override
	public Authentication authenticate(Authentication arg0) throws AuthenticationException {
		return null;
	}



	@Override
	public boolean supports(Class<? extends Object> arg0) {
		return true;
	}
	*/
	

}	// end of class