package main.java.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Request implements Callable<String> {

	private double a;
	private double b;
	private double n;
    private static int HTTP_COD_SUCESSO = 200;
    static final Logger logger = LogManager.getLogger(Request.class.getName());

	public Request(double a, double b, double n) {
		this.a = a;
		this.b = b;
		this.n = n;
	}

	@Override
	public String call() {
		double result = 0;
		String hostname = "";
		String resultado ="";
		URL url;
		try {
			long tempoInicial = System.currentTimeMillis();
			url = new URL("http://127.0.0.1:4000/?a=" + (int)a + "&b=" + (int)b + "&n=" + (int)n);
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
					hostname = (String) jsonObject.get("hostname");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			con.disconnect();
			resultado = "Hostname=" + hostname + " a=" + a + " b=" + b + " n=" + n + " Result= " + result + " Tempo="
					+ (System.currentTimeMillis() - tempoInicial);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return resultado;
	}

}
