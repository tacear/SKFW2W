package utils;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter {

	String ext;
	public FileFilter(String ext) {
		this.ext = ext;
	}
	
	public boolean accept(File dir, String name){
		return name.toUpperCase().endsWith(ext.toUpperCase());
	}
}
