package implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import abstracts.DataConverter;
import common.ExchangeRate;
import common.ExchangeRatesSeriesJson;
import exception.ParsingExchangeRate;

public class JsonConverter implements DataConverter {

	@Override
	public ExchangeRate getCurrencyRate(String dataString) {
		// TODO Auto-generated method stub
		try {
			if(dataString.length()==0) {
				return null;
			}
			JsonMapper objectMapper = new JsonMapper();
			ExchangeRatesSeriesJson json = objectMapper.readValue(dataString, ExchangeRatesSeriesJson.class);
			ExchangeRate rateModified = new ExchangeRate();
			rateModified.setCurrencyCode(json.getCode());
			rateModified.setCurrencyDate(json.getRates().get(0).getEffectiveDate());
			rateModified.setCurrencyRate(json.getRates().get(0).getMid());
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
		return "json";
	}

}
