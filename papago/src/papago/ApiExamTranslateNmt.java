package papago;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.JsonUtil;

// ���̹� ������ (Papago SMT) API ����
public class ApiExamTranslateNmt {

	public static void main(String[] args) throws ParseException {
		String clientId = "YOUR_CLIENT_ID";// ���ø����̼� Ŭ���̾�Ʈ ���̵�";
		String clientSecret = "YOUR_CLIENT_SECRET";// ���ø����̼� Ŭ���̾�Ʈ ��ũ����";

		clientId = "Z4gVqSXcMQ4UeOj8L2ZD";
		clientSecret = "ykN6s89slT";

		String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
		String text;
		try {
//			text = URLEncoder.encode("�ȳ��ϼ���. ���� ����� ����ϱ�?", "UTF-8");
			text = URLEncoder.encode("õ�ΰ�ȹ", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("���ڵ� ����", e);
		}

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);

		String responseBody = post(apiURL, requestHeaders, text);

		System.out.println(responseBody);
		System.out.println();

		parsingMessage(responseBody);
	}

	private static void parsingMessage(String responseBody) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(responseBody);
		Map<String, Object> mapFromJsonObject1 = JsonUtil.getMapFromJsonObject(json);
		System.out.println(mapFromJsonObject1);
		JSONObject message = (JSONObject) json.get("message");
		Map<String, Object> mapFromJsonObject2 = JsonUtil.getMapFromJsonObject(message);
		System.out.println(mapFromJsonObject2);
		JSONObject result = (JSONObject) message.get("result");
		Map<String, Object> mapFromJsonObject3 = JsonUtil.getMapFromJsonObject(result);
		System.out.println(mapFromJsonObject3);
	}

	private static String post(String apiUrl, Map<String, String> requestHeaders, String text) {
		HttpURLConnection con = connect(apiUrl);

		String wordSource = "ko", wordTarget = "ja";
        try {
			wordSource = URLEncoder.encode("ko", "UTF-8");
	        wordTarget = URLEncoder.encode("ja", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        
//		String postParams = "source=ko&target=en&text=" + text; // �������: �ѱ��� (ko) -> �������: ���� (en)
		String postParams = "source="+wordSource+"&target="+wordTarget+"&text=" + text; // �������: �ѱ��� (ko) -> �������: ���� (en)
		try {
			con.setRequestMethod("POST");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			con.setDoOutput(true);
			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
				byte[] bytes = postParams.getBytes();
				
//				wr.write(postParams.getBytes());
				wr.write(bytes);
				wr.flush();
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // ���� ����
				return readBody(con.getInputStream());
			} else { // ���� ����
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API ��û�� ���� ����", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL�� �߸��Ǿ����ϴ�. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("������ �����߽��ϴ�. : " + apiUrl, e);
		}
	}

	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API ������ �дµ� �����߽��ϴ�.", e);
		}
	}
}