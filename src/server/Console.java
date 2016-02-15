package server;

public class Console {

	public static void main(String[] args) {
		System.out.println("Simple Http Server - Multithreaded - Henry Strobel");

		if (args.length > 0)
			Globals.DIR = args[1];
		Thread serv = new Thread(new Server());
		serv.start();
		System.out.println("Started http server!");
	}

}
