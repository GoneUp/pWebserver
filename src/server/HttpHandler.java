package server;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpHandler implements Runnable {

	private Socket socket;

	public HttpHandler(Socket s) {
		socket = s;
	}

	public void run() {
		try {
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			while (true) {
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null && line.length() != 0) {
					sb.append(line).append("\n");
				}

				if (line == null || socket.isClosed()){
					//socket closed
					System.out.printf("closing %s\n", Thread
							.currentThread().toString());
					return;
				}
				
				line = sb.toString();
				if (!line.equals("")) {
					System.out.printf("new message on %s: %s \n", Thread
							.currentThread().toString(), line);
					process(line);
				}	
				

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * GET / HTTP/1.1 Host: 127.0.0.1 Connection: keep-alive Cache-Control:
	 * max-age=0 Accept:
	 * text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,;q=0.8
	 * Upgrade-Insecure-Requests: 1 User-Agent: Mozilla/5.0 (Windows NT 6.1;
	 * Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109
	 * Safari/537.36 Accept-Encoding: gzip, deflate, sdch Accept-Language:
	 * de-DE,de;q=0.8,en-US;q=0.6,en;q=0.4
	 */

	private void process(String request) throws IOException {
		if (!request.startsWith("GET"))
			return;
		String[] split = request.split(" ");

		String path = Globals.DIR + split[1];
		byte[] output = Globals.buildHtmlResponse(path);

		if (output != null)
			socket.getOutputStream().write(output);
	}

}
