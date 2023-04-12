package mx.com.hunkabann.skf.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Password {
	
	public static String NUMEROS = "0123456789";
	public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
	public static String ESPECIALES = "Ò—@#$%!";	
	
	
	/**
	 * Verifica que se un pwd valido
	 * @param p_stPwd
	 * @return
	 */
	public static boolean valido(String p_stPwd)
	{
		boolean l_return = false;

		
//		String expresion = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[Ò—@#$%!]).{8,40})";
		String expresion = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{8,40})";
		Pattern patron = Pattern.compile(expresion) ;
		Matcher m = patron.matcher(p_stPwd);
		
		if (m.find())
			l_return = true;
		else
			l_return = false;
		
		expresion = null;
		patron = null;
		m = null;
		
		return l_return;
		
	}	// valido
	
	
	public static String generaAleatorio(int p_length)
	{
		String l_stReturn = "";
//		String l_data = NUMEROS + MAYUSCULAS + MINUSCULAS + ESPECIALES;
		String l_data = NUMEROS + MAYUSCULAS + MINUSCULAS;
		
		do
		{
			l_stReturn = "";
			
			for (int i = 0; i < p_length; i++) 
				l_stReturn += (l_data.charAt((int)(Math.random() * l_data.length())));
			
		}while(!valido(l_stReturn) && p_length >= 8);
		
		return l_stReturn;
		
	}	// generaAleatorio
	
	public static String getEncrypt(String cadena) { 
		
		StandardPBEStringEncryptor s = new StandardPBEStringEncryptor(); 
		s.setPassword("uniquekey"); 
		
		return s.encrypt(cadena);
		
		} // getencrypt

	public static String getDecrypt(String cadena) {
		
		StandardPBEStringEncryptor s = new StandardPBEStringEncryptor(); 
		
		s.setPassword("uniquekey"); 
		String devuelve = ""; 
		try { 
			devuelve = s.decrypt(cadena); 
					
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		return devuelve; 
		} //getdecrypt
	
	
	public static void main(String args[]) throws UnsupportedEncodingException
	{
		
		Codificador cod = new Codificador();
		String pwd = "arturo";
		
	
		System.out.println(cod.cifrar(pwd));
		
		pwd = Password.generaAleatorio(18);
		
		System.out.println(pwd);
		System.out.println(Password.valido(pwd));
		
	}
	

}	// end of class
