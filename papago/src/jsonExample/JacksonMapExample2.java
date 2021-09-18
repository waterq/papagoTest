package jsonExample;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapExample2 {
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();

		Map<String, String> map = new HashMap<>();
		map.put("name", "mkyong");
		map.put("age", "37");

		try {

			// convert map to JSON string
			String json = mapper.writeValueAsString(map);

			System.out.println(json); // compact-print

			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

			System.out.println(json); // pretty-print

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
