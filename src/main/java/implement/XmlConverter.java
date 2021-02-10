package implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import abstracts.DataConverter;
import common.ExchangeRate;
import common.ExchangeRatesSeriesXml;
import exception.ParsingExchangeRate;

public class XmlConverter implements DataConverter {

	@Override
	public ExchangeRate getCurrencyRate(String dataString) {
		// TODO Auto-generated method stub
		try {
			XmlMapper objectMapper = new XmlMapper();
			ExchangeRatesSeriesXml xml = objectMapper.readValue(dataString, ExchangeRatesSeriesXml.class);
			ExchangeRate rateModified = new ExchangeRate();
			rateModified.setCurrencyCode(xml.getCode());
			rateModified.setCurrencyDate(xml.getRates().get(0).getEffectiveDate());
			rateModified.setCurrencyRate(xml.getRates().get(0).getMid());
			return rateModified;
		} catch(JsonMappingException e) {
			throw new ParsingExchangeRate(e.getMessage());
		} catch(JsonProcessingException e) {
			throw new ParsingExchangeRate(e.getMessage());
		}
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return "xml";
	}

}
