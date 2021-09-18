package jsonExample;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class XmltoJson {

	public static void main(String[] args) {
		try (InputStream inputStream = new FileInputStream(new File("./data/ex1.xml"))) {
			String xml = IOUtils.toString(inputStream, "UTF-8");
			JSONObject jObject = XML.toJSONObject(xml);
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			Object json = mapper.readValue(jObject.toString(), Object.class);
			String output = mapper.writeValueAsString(json);
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
