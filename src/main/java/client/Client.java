package main.java.client;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

	private static int x0 = 1;
	private static int x10 = 10;
	private static int threads = 200;
	static final Logger logger = LogManager.getLogger(Client.class.getName());

	public static void main(String[] args) {
		logger.info("Começou!");
		long tempoInicial = System.currentTimeMillis();
		if(Integer.valueOf(args[0]) < 5) {
			computeTaskVariable(Integer.valueOf(args[0]));
		}else {
			computeCallVariable(Integer.valueOf(args[0]));
			FileWriter arq;
			try {
				arq = new FileWriter("/home/igorn/workspace/finalizou.txt");
				arq.close();				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.info("Tempo total=" + (System.currentTimeMillis() - tempoInicial));
		System.exit(0);
	}		
	
	public static void computeCallVariable(int option) {
		int qntthreads = 0;
		for (int i = 1; i < 100; i++) {
			switch (option) {
			// Crescente
			case 5:
				qntthreads = i;
				break;
			// Decrescente
			case 6:
				qntthreads = 101 - i;
				break;
			// Constante
			case 7:
				qntthreads = 50;
				break;
			// Constante
			case 8:
				qntthreads = (int) ((Math.sin((2 * Math.PI) / 50 * i) * 50) + 50);
				break;
			default:
				break;
			}
			logger.info("Quantidade de threads: " + qntthreads);
			ExecutorService executor = Executors.newFixedThreadPool(qntthreads);
			List<Future<String>> list = new ArrayList<Future<String>>();
			for (int j = 0; j < 100; j++) {
				list.add(executor.submit(new Request(x0, x10, 1000000)));
				if(list.size() >= qntthreads) {
					for (Future<String> fut : list) {
						try {
							logger.info(fut.get());
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							logger.error(e.getMessage());
						}
					}
					list.clear();
				}
			}
		}
	}
		
	public static void computeTaskVariable(int option) { 
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		List<Future<String>> list = new ArrayList<Future<String>>();
		Future<String> future = null;
		for (int i = 1; i < 10000000; i++) {
			switch (option) {
			// Crescente
			case 1:
				future = executor.submit(new Request(x0, x10, (i * 0.2 * 500)));
				break;
			// Decrescente
			case 2:
				future = executor.submit(new Request(x0, x10, (1000250 - (i * 0.2 * 500))));
				break;
			// Constante
			case 3:
				future = executor.submit(new Request(x0, x10, 500000));
				break;
			// Onda
			case 4:
				future = executor.submit(new Request(x0, x10, ((1 * 500000 * Math.sin(0.00125 * i)) + (1 * 500000 + 500))));
				break;
			default:
				break;
			}
			list.add(future);			
		}
		for (Future<String> fut : list) {
			try {
				logger.info(fut.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}		
		executor.shutdown();
	}
}
