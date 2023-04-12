package utils;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtils {

	public static byte[] GetBytesFromFile (File archivo) throws Exception
	{
		long l = archivo.length();
		byte abyte[] = new byte[(int) l];
		FileInputStream entrada = new FileInputStream(archivo);
		int i = 0;
		for (int j = 0; i < abyte.length
				&& (j = entrada.read(abyte, i, abyte.length - i)) >= 0; i += j)
			;
		entrada.close();

		return abyte;

	}
	
	public static boolean SaveBytesToFile(String filename, byte[] theBytes) {
		DataOutputStream dos = null;
		try {
			FileOutputStream f = new FileOutputStream(filename);
			BufferedOutputStream bf = new BufferedOutputStream(f);
			dos = new DataOutputStream(bf);
			dos.write(theBytes, 0, theBytes.length);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (dos != null)
					dos.close();
			} catch (Exception x) {
			}
		}

		return true;
	}

}
