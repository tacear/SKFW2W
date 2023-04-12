package mx.com.hunkabann.skf.frontent.generaPwd;

import java.io.UnsupportedEncodingException;

import org.python.antlr.PythonParser.pass_stmt_return;

import mx.com.hunkabann.skf.util.Codificador;
import mx.com.hunkabann.skf.util.Password;

public class GeneraPassword {
	
//	Password pass = new Password();
	static Codificador codi = new Codificador(); 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String PWD = Password.generaAleatorio(10);
		System.out.println("PAssword ---->> " + PWD);
		try {
			String Cifrado =codi.cifrar(PWD);
			System.out.println(Cifrado);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
