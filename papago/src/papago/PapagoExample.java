package papago;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.PapagoTran;

public class PapagoExample {

	private String[] langs = { "en", "ja", "zh-CN", "ru" };

	public static void main(String[] args) throws MalformedURLException, IOException, ParseException {
		PapagoExample example = new PapagoExample();
		ArrayList<String> list = new ArrayList<String>();
		list.add("천인계획");
		list.add("오바마");
		list.add("시진핑");
		list.add("마약");

		for (String text : list) {
			Map<String, String> result = example.translate(text);
			System.out.println(result);
		}
	}

	private Map<String, String> translate(String text) throws MalformedURLException, IOException, ParseException {
		PapagoTran papagoTran = PapagoTran.getPapagoTran();
		JSONParser parser = new JSONParser();

		Map<String, String> result = new HashMap<String, String>();
		for (String lang : langs) {
			String translate = papagoTran.translate(text, lang);
			
			JSONObject json = (JSONObject) parser.parse(translate);
			JSONObject json1 = (JSONObject)json.get("message");
			JSONObject json2 = (JSONObject)json1.get("result");
			
			result.put(lang, json2.get("translatedText").toString());
		}

		return result;
	}

}
