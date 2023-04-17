package mx.com.hunkabann.skf.policy;

import java.io.Serializable;

import mx.com.hunkabann.skf.mapeo.TbUsuario;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserImpl extends User implements Serializable{
	
	final private String usrToken;

//	final private long userId;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserImpl(TbUsuario user, GrantedAuthority[] grantedAuthorities) throws IllegalArgumentException {
		super(user.getChUsuario(), user.getChPassword(), true, true, true, true, grantedAuthorities);
		
		this.usrToken = "";
		
	}
	
	public Md5Token getToken() {
		
		if (StringUtils.isBlank(getUsrToken())) {
			return null;
		}
		
		return new Md5Token(getUsrToken());
	}

	private String getUsrToken() {
		return this.usrToken;
	}
	


}	// end of class
