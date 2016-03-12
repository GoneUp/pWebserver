package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {

	private ServerSocket socket;
	private List<Thread> httpClients;

	public void run() {
		try {
			httpClients = new LinkedList<Thread>();
			socket = new ServerSocket(80);	

			while (true) {
				Socket s = socket.accept();
				Thread client = new Thread(new HttpHandler(s));
				httpClients.add(client);
				client.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
