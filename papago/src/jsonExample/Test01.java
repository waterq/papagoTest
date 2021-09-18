package jsonExample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test01 {

	public static void main(String[] args) throws ParseException {
		String json1 = "{ \"name\" : \"dave\", \"department\" : { \"name\" : \"hr\", \"location\" : \"seoul\" }}";
		String json2 = "[ { \"name\" : \"dave\", " + "\"department\" : {\"name\" : \"hr\", \"location\" : \"seoul\"}}, "
				+ "{\"name\" : \"paul\", \"department\" : {\"name\" : \"development\", \"location\" : \"tokyo\"}}]";

//		test(json1);
		test(json2);
	}

	private static void test(String json) throws ParseException {
		JSONParser parser = new JSONParser();
		Object object = parser.parse(json);

		if (object instanceof JSONArray) {
			// jsonArray 클래스를 이용한 처리
			JSONArray jsonArray = (JSONArray) object;
			JSONObject jsonObject = (JSONObject) jsonArray.get(0);
			System.out.println(jsonObject.get("name"));
			System.out.println(jsonObject.get("department"));
			jsonObject = (JSONObject) jsonArray.get(1);
			System.out.println(jsonObject.get("name"));
			System.out.println(jsonObject.get("department"));
		} else if (object instanceof JSONObject) {
			// jsonObject 클래스를 이용한 처리
			JSONObject jsonObject = (JSONObject) object;
			System.out.println(jsonObject.get("name"));
			System.out.println(jsonObject.get("department"));
		} else {

			// Other types
		}

	}

}
