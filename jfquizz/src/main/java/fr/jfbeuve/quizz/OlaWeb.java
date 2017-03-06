package fr.jfbeuve.quizz;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class OlaWeb {
	private HttpURLConnection con;
	
	private String host;
	
	public OlaWeb(String _host){
		host = _host;
		System.out.println("http://"+host+":9090/set_dmx");
	}

	public void disconnect() {
		con.disconnect();	
	}

	synchronized public void send(int[] data) {
		StringBuffer param = new StringBuffer("u=0&d=");
		for (int i = 0; i < data.length; i++) {
			if(i!=0) param.append(",");
			param.append(data[i]);
		}
		System.out.println(param.toString());
		try {
			URL url = new URL("http://"+host+":9090/set_dmx");
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(param.toString());
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			if(responseCode!=200) System.err.println("HTTP ERROR "+responseCode);
		} catch (IOException e) {
			System.err.println("http://"+host+":9090/set_dmx");
			e.printStackTrace();
		}
	}

}
