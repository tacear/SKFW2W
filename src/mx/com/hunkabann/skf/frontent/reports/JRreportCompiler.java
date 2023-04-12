package mx.com.hunkabann.skf.frontent.reports;

import java.io.InputStream;
import java.util.Collection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class JRreportCompiler {

	public JRreportCompiler() {

	}

	@SuppressWarnings("unchecked")
	public boolean compileReport(String aReportName) {

		boolean result = false;

		try {

			InputStream inputStream = getClass().getResourceAsStream(aReportName);

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			Collection collection = JasperCompileManager.verifyDesign(jasperDesign);
			for (Object object : collection) {
				object.toString();
			}

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			if (jasperReport != null) {
				result = true;
			}

		} catch (JRException ex) {
			String connectMsg = "JasperReports: Could not create the report " + ex.getMessage() + " " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		} catch (Exception ex) {
			String connectMsg = "Could not create the report " + ex.getMessage() + " " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}
		return result;
	}
}
