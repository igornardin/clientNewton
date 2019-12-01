package main.java.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

	private static int x0 = 1;
	private static int x10 = 10;
	private static int threads = 15;
	static final Logger logger = LogManager.getLogger(Client.class.getName());

	public static void main(String[] args) {
		logger.info("Começou!");
		int add_threads = 0;
		long tempoInicial = System.currentTimeMillis();
		for (int i = 1; i < 1000000; i++) {
			add_threads++;
			switch (args[0]) {
			// Crescente
			case "1":
				new Thread(new Request(x0, x10, (i * 0.2 * 500))).start();
				break;
			// Decrescente
			case "2":
				new Thread(new Request(x0, x10, (1000250 - (i * 0.2 * 500))));
				break;
			// Constante
			case "3":
				new Thread(new Request(x0, x10, 500000));
				break;
			// Constante
			case "4":
				new Thread(new Request(x0, x10, ((1 * 500000 * Math.sin(0.00125 * i)) + (1 * 500000 + 500))));
				break;
			default:
				break;
			}
			if (add_threads >= threads) {
				add_threads = 0;
				while (Thread.activeCount() > 1) {
				}				
			}
		}
		logger.info("Tempo total=" + (System.currentTimeMillis() - tempoInicial));
	}
}
