/**
 * 
 */
package mx.com.hunkabann.skf.backend.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.xmlbeans.XmlException;

//import mx.gob.sat.cfd.x3.ComprobanteDocument;
//import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Emisor;

/**
 * @author hunkabann
 *
 */
public class ValidaCFDIUtil {

	/**
	 * 
	 */
	public ValidaCFDIUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 File fstream;
//		try {
////			fstream = new File("C:\\rtc\\Tomcat 6.0\\webapps\\out\\214_VME130319QV1 _B266F3B9-7148-4EFD-846E-D5BB4961F255.xml");
////			fstream = new File("C:\\rtc\\Tomcat 6.0\\webapps\\out\\214_VME130319QV1 _testVal.xml");
////			fstream = new File("C:\\rtc\\Tomcat 6.0\\webapps\\out\\202_HUN050812U53_A_249.xml");
//			fstream = new File("C:\\rtc\\xmlbeans-2.4.0\\samples\\Any\\build.xml");
//			
//		 
////		 ComprobanteDocument comp=ComprobanteDocument.Factory.parse(fstream);
////		 
////		 System.out.println("ValidaCFDIUtil.main()Sello"+comp.getComprobante().getSello());
////		 
////		 Emisor emi=comp.getComprobante().getEmisor();
////		 
////		 System.out.println("ValidaCFDIUtil.main()emi.getNombre()="+emi.getNombre());
////		 System.out.println("ValidaCFDIUtil.main()emi.getRfc()="+emi.getRfc());
//		
//		}
//		catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (XmlException e) {
//			if(e.getMessage().indexOf("is not a valid Comprobante")>0)
//				System.out.println("Esta versi�n por el momento no puede validar CFD 2");
//			else if(e.getMessage().indexOf("The document is not a Comprobante")>0)
//				System.out.println("El XMl no es un comprobante Fiscal Digital");
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
