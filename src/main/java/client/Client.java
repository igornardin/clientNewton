package main.java.client;

public class Client {
	
    private static int x0 = 1;
    private static int x10 = 10;
	
	public static void main(String[] args) {
		System.out.println("Começou!");
		for (int i = 0; i < 1000000; i++) {
			switch (args[0]) {
			//Crescente
			case "1":
				new Thread(new Request(x0, x10, (i * 0.2 * 500))).start();
				break;
			//Decrescente
			case "2":
				new Thread(new Request(x0, x10, (1000250 - (i * 0.2 * 500))));
				break;
			//Constante
			case "3":
				new Thread(new Request(x0, x10, 500000));
				break;
			//Constante
			case "4":
				new Thread(new Request(x0, x10, ((1 * 500000 * Math.sin(0.00125 * i)) + (1 * 500000 + 500))));
				break;

			default:
				break;
			}
		
		}
	}
}
