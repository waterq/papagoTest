package jsonExample;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapExample1 {

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		
		String json = "{\"name\":\"mkyong\", \"age\":\"37\"}";

		json = "{\"message\":{\"@type\":\"response\",\"@service\":\"naverservice.nmt.proxy\",\"@version\":\"1.0.0\",\"result\":{\"srcLangType\":\"ko\",\"tarLangType\":\"en\",\"translatedText\":\"Hello. How are you doing today?\",\"engineType\":\"N2MT\",\"pivot\":null}}}";

		try {

			// convert JSON string to Map
			Map<String, String> map = mapper.readValue(json, Map.class);
			
			// it works
			// Map<String, String> map = mapper.readValue(json, new
			// TypeReference<Map<String, String>>() {});

			System.out.println(map);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
