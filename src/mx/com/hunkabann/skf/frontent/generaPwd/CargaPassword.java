package mx.com.hunkabann.skf.frontent.generaPwd;

import java.io.UnsupportedEncodingException;

import mx.com.hunkabann.skf.util.Codificador;

public class CargaPassword {
	
	static Codificador codi = new Codificador(); 
	
	public String PasswordName(String Nombre){
		String Cifrado ="";
		System.out.println("PAssword ---->> " + Nombre);
		try {
			Cifrado =codi.cifrar(Nombre);
			System.out.println(Cifrado);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cifrado;
	}

}
