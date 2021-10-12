package papago;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PapagoExample {

	private String[] langs = { "en", "ja", "zh-CN", "ru" };

	public static void main(String[] args) throws MalformedURLException, IOException {
		PapagoExample example = new PapagoExample();
		ArrayList<String> list = new ArrayList<String>();
		list.add("천인계획");
		list.add("오바마");
		list.add("시진핑");
		list.add("마약");

		for (String text : list) {
			Map<String, String> result = example.translate(text);
			System.out.println(text + " : " + result);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> translate(String text) throws MalformedURLException, IOException {
		Map<String, String> transMap = new HashMap<String, String>();
		PapagoTran papagoTran = PapagoTran.getPapagoTran();
		ObjectMapper mapper = new ObjectMapper();
		for (String lang : langs) {
			String translate = papagoTran.translate(text, lang);
			Map<String, Object> json = mapper.readValue(translate, new TypeReference<Map<String, Object>>() {
			});
			Map<String, Object> message = (Map<String, Object>) json.get("message");
			Map<String, Object> result = (Map<String, Object>) message.get("result");

			transMap.put(lang, result.get("translatedText").toString());
		}

		return transMap;
	}

}
