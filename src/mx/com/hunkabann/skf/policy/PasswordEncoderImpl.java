package mx.com.hunkabann.skf.policy;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import sun.misc.BASE64Encoder;

public class PasswordEncoderImpl implements PasswordEncoder, Serializable {

	private static final long serialVersionUID = 1L;

	public PasswordEncoderImpl() {
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object token) {
		
		System.out.println("PasswordEncoderImpl, isPasswordValid :");
		
		try {
			System.out.println("2");
			rawPass =  sifrar(rawPass);
			System.out.println("21");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Operacion fallo: " + e);
			return false;
		}

		if(encPass.equals(rawPass)&&!rawPass.equals("-1"))
		{
			System.out.println("23");
			return true;
		}
		
		System.out.println("aqui se puede hacer la validacion");
		
		return false;
	}

	public String encodePassword(String rawPass, Object token) throws DataAccessException {
		throw new RuntimeException("Methode wird nicht unterstützt!");
	}
	
	@SuppressWarnings("static-access")
	public synchronized String sifrar(String p_stPwd) throws UnsupportedEncodingException
	{
		String l_stPassword = "";
		try
		{

			MessageDigest sha = null;
            
            //SHA-1 (Secure Hash Algorithm 1)
            sha = sha.getInstance("SHA-1");
            
            //Convert the plaintext password (eg, "hello") into a byte-representation using UTF-8 encoding format.
            //Apply this array to the message digest object created earlier
            sha.update(p_stPwd.getBytes("UTF-8"));
            
            //Create a String representation of the byte array representing the digested password value
            byte raw[] = sha.digest();             
            
            //Return the String representation of the newly generated hash back to our registration servlet
            //so that it can be stored in the database.
            l_stPassword = (new BASE64Encoder()).encode(raw); 

       	
			
		}
	   catch( NoSuchAlgorithmException ex){
			System.out.println("Operacion fallo: " + ex);
			return "-1";
		}
		
	   return l_stPassword;
	}

	public boolean validaPass(String value) {
		boolean validado=true;
		String cadena = "abcdefghijklmnopqrstuvxyz0123456789";
		for(int i=0;i<value.length();i++)
		{
			System.out.println("---------------: "+value.toLowerCase().charAt(i));
			if(cadena.indexOf(value.toLowerCase().charAt(i)+"")==-1)
			{
				System.out.println("entro");
				validado=false;
				break;
			}
		}
		return validado;
	}
	
}
