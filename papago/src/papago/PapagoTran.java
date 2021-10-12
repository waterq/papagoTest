package papago;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PapagoTran {

	private static PapagoTran papagoTran = null;

	private final Map<String, String> requestHeaders;

	private final String apiURL = "https://openapi.naver.com/v1/papago/n2mt";

	private PapagoTran() {
		String clientId = "Z4gVqSXcMQ4UeOj8L2ZD";
		String clientSecret = "ykN6s89slT";

		requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
	}

	/**
	 * @return
	 */
	public static PapagoTran getPapagoTran() {
		if (papagoTran == null) {
			papagoTran = new PapagoTran();
		}

		return papagoTran;
	}

	/**
	 * @param text
	 * @param lang
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public String translate(String text, String lang) throws MalformedURLException, IOException {
		String input = URLEncoder.encode(text, "UTF-8");
		String postParam = "source=ko&target=" + lang + "&text=" + input;

		HttpURLConnection con = (HttpURLConnection) new URL(apiURL).openConnection();
		try {
			con.setRequestMethod("POST");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}
			con.setDoOutput(true);
			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
				wr.write(postParam.getBytes());
				wr.flush();
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { 
				// 정상 응답
				return readBody(con.getInputStream());
			} else { 
				// 에러 응답
				return readBody(con.getErrorStream());
			}
		} finally {
			con.disconnect();
		}
	}

	private static String readBody(InputStream body) throws IOException {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new IOException("API 응답을 읽는데 실패했습니다.", e);
		}
	}

}
