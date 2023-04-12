package mx.com.hunkabann.skf.workspace;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Workspace for the application. One workspace server. <br>
 * <br>
 * Here are stored several properties for the application. <br>
 * <br>
 * 1. Language properties files. <br>
 * 2. Default values for creating new entries for: <br>
 * - users. <br>
 * 
 * <b>Not used at time!</b><br>
 * 
 */
public class ApplicationWorkspace implements Serializable {

	private static ApplicationWorkspace instance = new ApplicationWorkspace();

	private static final long serialVersionUID = -1397646202890802880L;
	@SuppressWarnings("unused")
	private transient final static Logger logger = Logger.getLogger(ApplicationWorkspace.class);

	/**
	 * Default Constructor, cannot invoked from outer this class. <br>
	 */
	private ApplicationWorkspace() {

	}

	public static ApplicationWorkspace getInstance() {
		return instance;
	}

}

// Properties prop = new Properties();
// prop.load(new FileInputStream(propPath + "/" + propFileNameGerman));

