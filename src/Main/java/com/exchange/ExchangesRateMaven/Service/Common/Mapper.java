package com.exchange.ExchangesRateMaven.Service.Common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Mapper {
	public static Object mapObject(String data, Format format, Object object) throws Exception {
		ObjectMapper objectMapper = null;
		if(format == Format.JSON) {
			objectMapper = new JsonMapper();
		}
		else if(format == Format.XML) {
			objectMapper = new XmlMapper();
		}
		else {
			throw new Exception("Bad data format expected Json or Xml");
		}
		object = objectMapper.readValue(data, object.getClass());
		return object;
	}
}
