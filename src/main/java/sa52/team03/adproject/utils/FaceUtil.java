package sa52.team03.adproject.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FaceUtil {

	static String ACCESS_TOKEN;

	static String clientId = "xxGzIaemhGmLSzounyfi3VcY";
	static String clientSecret = "Kmo9EETvRgBEzXK89x5yoSGIk7FRXnMy";

	public static String getFaceAPIToken() {

		String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
		String getAccessTokenUrl = authHost + "grant_type=client_credentials" + "&client_id=" + clientId
				+ "&client_secret=" + clientSecret;
		try {

			URL realUrl = new URL(getAccessTokenUrl);
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}

			JSONObject jsonObject = JSON.parseObject(result.toString());

			return jsonObject.getString("access_token");

		} catch (Exception e) {
			System.err.print("fail to get token！");
			e.printStackTrace(System.err);
		}
		return null;
	}

	public static String addFace(String stringBase64, int useId) {

		if (ACCESS_TOKEN == null) {
			ACCESS_TOKEN = getFaceAPIToken();
		}
		
		String hostUrl = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add?";
		String url = hostUrl + "&access_token=" + ACCESS_TOKEN;

		try {

			URL realUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();

			try (PrintWriter writer = new PrintWriter(connection.getOutputStream())) {
				Map<String, Object> map = new HashMap<>();
				map.put("image", stringBase64);
				map.put("image_type", "BASE64");
				map.put("group_id", "student");
				map.put("user_id", useId);
				map.put("quality_control", "LOW");
				writer.write(JSONObject.toJSONString(map));
				writer.flush();
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}

			JSONObject jsonObject = JSON.parseObject(result.toString());

			return jsonObject.getString("error_msg");

		} catch (Exception e) {
			System.err.print("fail to get token！");
			e.printStackTrace(System.err);
		}
		return null;
	}

	public static String deleteUser(int useId) {

		if (ACCESS_TOKEN == null) {
			ACCESS_TOKEN = getFaceAPIToken();
		}
		
		String hostUrl = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/delete?";
		String url = hostUrl + "&access_token=" + ACCESS_TOKEN;

		try {

			URL realUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();

			try (PrintWriter writer = new PrintWriter(connection.getOutputStream())) {
				Map<String, Object> map = new HashMap<>();
				map.put("group_id", "student");
				map.put("user_id", useId);
				writer.write(JSONObject.toJSONString(map));
				writer.flush();
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}

			JSONObject jsonObject = JSON.parseObject(result.toString());

			return jsonObject.getString("error_msg");

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

}
