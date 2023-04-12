package mx.com.hunkabann.skf.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import mx.com.hunkabann.skf.policy.UnicodeFormatter;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import sun.misc.BASE64Encoder;


public class Codificador implements PasswordEncoder, Serializable 
{
	private static final long serialVersionUID = 1L;
	private boolean imprime = false;
	
	
	
	public String encodePassword(String arg0, Object arg1) {
		
//		System.out.println("Método no implementado");
		
		return "Método no implementado";
		//throw new RuntimeException("Método no implementado");
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object token) {
		
		String l_rawPass = "";
		
		if(imprime)
		{
			System.out.println("encPass: " + encPass);	// <-- de base
			System.out.println("rawPass: " + rawPass);	// <-- a cifrar
		}
		
		/*******************/
		
		byte[] defaultBytes = rawPass.getBytes();
		
		l_rawPass = new String(restoreBytes(defaultBytes));
		
		
		/*************************/
		try {
			
			
			rawPass =  cifrar(l_rawPass);
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			return false;
			
		}

		if(imprime)
			System.out.println("rawPass despues de cifrar: " + rawPass);
		
		if(encPass.equals(rawPass) && !rawPass.equals("-1"))
			return true;
		
		
		return false;
		
	}	// isPasswordValid
	
	
	
	private byte[] restoreBytes(byte[] p_InputBytes)
	{
		byte[] l_return = null;
		List<Byte> listByte = new ArrayList<Byte>();
		byte l_byte = 0;
		
	    for (int k = 0; k < p_InputBytes.length; k++) {
	    	
	        if(UnicodeFormatter.byteToHex(p_InputBytes[k]).equals("c3"))
	        {
	        	continue;
	        	
	        }
	        
	        if(UnicodeFormatter.byteToHex(p_InputBytes[k]).equals("3f"))
	        {
	        	l_byte = (byte) ((Character.digit("d1".charAt(0), 16) << 4) + Character.digit("d1".charAt(1), 16));
	        	listByte.add(l_byte);
	        	continue;

	        }
	        if(UnicodeFormatter.byteToHex(p_InputBytes[k]).equals("b1"))
	        {
	        	l_byte = (byte) ((Character.digit("f1".charAt(0), 16) << 4) + Character.digit("f1".charAt(1), 16));
	        	listByte.add(l_byte);
	        	continue;
	        }

        	listByte.add(p_InputBytes[k]);
	    
	    }
	    
	    l_return = new byte[listByte.size()];
	    
	    for(int i = 0; i < listByte.size(); i++)
	    {
	    	l_return[i] = ((Byte)listByte.get(i)).byteValue();
//	    	System.out.println(UnicodeFormatter.byteToHex(l_return[i]) + " - " + (new String(new byte[] {l_return[i]})));
	    }
	    
	    listByte.clear();
		
		return l_return;
		
	}	// restoreBytes
	
	
	
	public static void printBytes(byte[] array, String name) {
	    for (int k = 0; k < array.length; k++) {
	        System.out.println(name + "[" + k + "] = " + "0x" +
	            UnicodeFormatter.byteToHex(array[k]));
	        
	    }
	}	
	
	

	@SuppressWarnings({ "static-access" })
	public String cifrar(String p_stPwd) throws UnsupportedEncodingException {
		
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
		
//	   return Password.getEncrypt(p_stPwd);
	   
	}	// cifrar
	
	
	
	
//	public String deCifrar(String p_stCifrado)
//	{
//		return Password.getDecrypt(p_stCifrado);
//		
//	}	// deCifrar
	
	
	

	public boolean validaPass(String p_stValue) {
		
//		boolean validado = true;
//		String cadena = "abcdefghijklmnopqrstuvxyz0123456789";
//		
//		for(int i = 0; i < p_stValue.length(); i++)
//		{
//			System.out.println("---------------: " + p_stValue.toLowerCase().charAt(i));
//			
//			if(cadena.indexOf(p_stValue.toLowerCase().charAt(i) + "") == -1)
//			{
//				System.out.println("entro");
//				validado = false;
//				break;
//			}
//			
//		}
		
		return Password.valido(p_stValue);
		
	}	// validaPass
	

}	// end of class
