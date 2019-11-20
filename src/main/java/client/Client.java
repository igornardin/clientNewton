package main.java.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

	private static int x0 = 1;
	private static int x10 = 10;
	static final Logger logger = LogManager.getLogger(Client.class.getName());

	public static void main(String[] args) {
		logger.info("Começou!");
		long tempoInicial = System.currentTimeMillis();
		for (int i = 1; i < 1000000; i++) {
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
			while (Thread.activeCount() >= 15) {
			}
		}
		logger.info("Tempo total=" + (System.currentTimeMillis() - tempoInicial));
	}
}
