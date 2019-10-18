package main.java.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Request implements Runnable {

	private double a;
	private double b;
	private double n;
    private static int HTTP_COD_SUCESSO = 200;

	public Request(double a, double b, double n) {
		this.a = a;
		this.b = b;
		this.n = n;
	}

	@Override
	public void run() {
		double result = 0;
		URL url;
		try {
			long tempoInicial = System.currentTimeMillis();
			url = new URL("http://localhost:4000/?a=" + a + "&b=" + b + "&n=" + n);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				throw new RuntimeException("HTTP error code : " + con.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				Object obj = JSONValue.parse(output);
				JSONObject jsonObject = (JSONObject) obj;
				try {
					result = (double) jsonObject.get("result");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			System.out.println("a=" + a + " b=" + b + " n=" + n + " Result= " + result + " Tempo total="
					+ (System.currentTimeMillis() - tempoInicial));
			con.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
