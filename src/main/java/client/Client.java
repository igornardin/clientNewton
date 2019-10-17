package main.java.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Client {
	
    private static int HTTP_COD_SUCESSO = 200;
    private static int x0 = 1;
    private static int x10 = 10;
	
	public static void main(String[] args) {
		System.out.println("Começou!");
		for (int i = 0; i < 10000; i++) {
			switch (args[0]) {
			//Crescente
			case "1":
				request(x0, x10, (i * 0.2 * 500));
				break;
			//Decrescente
			case "2":
				request(x0, x10, (1000250 - (i * 0.2 * 500)));
				break;
			//Constante
			case "3":
				request(x0, x10, 500000);
				break;
			//Constante
			case "4":
				request(x0, x10, ((1 * 500000 * Math.sin(0.00125 * i)) + (1 * 500000 + 500)));
				break;

			default:
				break;
			}		
		}
	}
	
	private static void request(double a, double b, double n) {
		double result = 0;
        URL url;
		try {
			long tempoInicial = System.currentTimeMillis();
			url = new URL("http://localhost:4000/newton?a=" + a + "&b="+b+"&n="+n);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();	
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
			    throw new RuntimeException("HTTP error code : "+ con.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				Object obj=JSONValue.parse(output);
				JSONObject jsonObject = (JSONObject) obj; 
				try {
					result = (double) jsonObject.get("result");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			System.out.println("a="+a+" b="+b+" n="+n+" Result= "+result+" Tempo total="+(System.currentTimeMillis() - tempoInicial));	
			con.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
