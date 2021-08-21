package sa52.team03.adproject.service;

import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class MachineLearningPythonServices {
	
	public void Connect(String link) throws Exception {
		
		//URL change once python ml deploy to cloud
		URL url = new URL(link);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type","application/json; utf-8");
		con.setDoOutput(true);
	}
	
	
	
}
