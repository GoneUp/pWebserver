package server;

import java.io.*;
import java.nio.charset.Charset;
import java.text.*;
import java.util.*;

public class Globals {
	final static int HTTPBUFFER = 80;
	static int PORT = 80;
	static String DIR = "C:";
	
	public static byte[] buildHtmlResponse(String dirpath){
		/* HTTP/1.1 200 OK\r\n
		 * Date: Mon, 15 Feb 2016 19:28:23 GMT\r\n
		 * Content-Type: text/html
		 * Server: Konstanz
		 * Content-Length: xxx [html len in bytes]
			
		 */
		File dir = new File(dirpath);
		if (!dir.exists()) return null;
		
		//create content
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><title> Directory Listening </title> </head><body>\n");
		sb.append(String.format("Path: %s <br>\n", dir.getAbsolutePath()));
		for (File f : dir.listFiles()){
			sb.append(String.format("Datei: %s <br>\n", f.getAbsolutePath()));
		}
		sb.append("</body></html>\n");
		byte[] content = sb.toString().getBytes(Charset.defaultCharset());
		
		//create header
		sb = new StringBuilder();
		sb.append("HTTP/1.1 200 OK\r\n");
		sb.append("Date: " + getServerTime() + "\r\n");
		sb.append("Content-Type: text/html; charset=UTF-8 \r\n");
		sb.append("Server: Konstanz \r\n");
		sb.append("Content-Length: " + content.length + "\r\n");
		sb.append("\r\n"); //header end
		byte[] header = sb.toString().getBytes(Charset.forName("US-ASCII"));
		
		//merge
		byte[] request = new byte[header.length + content.length];
		System.arraycopy(header, 0, request, 0, header.length);
		System.arraycopy(content, 0, request, header.length, content.length);		
		
		return request;
	}
	
	private static String getServerTime() {
	    Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat(
	        "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
	    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	    return dateFormat.format(calendar.getTime());
	}
}
