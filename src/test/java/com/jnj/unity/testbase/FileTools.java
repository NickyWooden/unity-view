package com.jnj.unity.testbase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTools {

	protected static String NEWLINE = System.getProperty("line.separator");

	protected void writeReport(String fileOutPath, StringBuilder sb, boolean appendMode) {
		File file = new File(fileOutPath);
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream out = new FileOutputStream(file, appendMode);

			out.write(sb.toString().getBytes("UTF-8"));

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected String showCompareResult(boolean result) {
		return result ? "OK" : "NG";
	}
	
	protected String caseTitleStr(int tabCount, String caseTitleStr){
		switch (tabCount) {
		case 0:
			return caseTitleStr; 
		case 1:
			return "\t"+caseTitleStr; 
		case 2:
			return "\t\t"+caseTitleStr; 
		case 3:
			return "\t\t\t"+caseTitleStr; 
		case 4:
			return "\t\t\t\t"+caseTitleStr; 
		default:
			return "\t"+caseTitleStr; 
		}
	}
	
	protected String expectStr(int tabCount, String expectStr){
		switch (tabCount) {
		case 0:
			return "expected    result:"+expectStr; 
		case 1:
			return "\t"+"expected    result:"+expectStr; 
		case 2:
			return "\t\t"+"expected    result:"+expectStr; 
		case 3:
			return "\t\t\t"+"expected    result:"+expectStr; 
		case 4:
			return "\t\t\t\t"+"expected    result:"+expectStr; 
		default:
			return "\t"+"expected    result:"+expectStr; 
		}
	}
	
	protected String actualStr(int tabCount, String actualStr){
		switch (tabCount) {
		case 0:
			return "actual      result:"+actualStr; 
		case 1:
			return "\t"+"actual      result:"+actualStr; 
		case 2:
			return "\t\t"+"actual      result:"+actualStr; 
		case 3:
			return "\t\t\t"+"actual      result:"+actualStr; 
		case 4:
			return "\t\t\t\t"+"actual      result:"+actualStr; 
		default:
			return "\t"+"actual      result:"+actualStr; 
		}
	}
	
	protected String resultStr(int tabCount, String caseName,String resultStr){
		switch (tabCount) {
		case 0:
			return caseName+" test result:"+resultStr; 
		case 1:
			return "\t"+caseName+" test result:"+resultStr; 
		case 2:
			return "\t\t"+caseName+" test result:"+resultStr; 
		case 3:
			return "\t\t\t"+caseName+" test result:"+resultStr; 
		case 4:
			return "\t\t\t\t"+caseName+" test result:"+resultStr; 
		default:
			return "\t"+caseName+" test result:"+resultStr; 
		}
	}
}
