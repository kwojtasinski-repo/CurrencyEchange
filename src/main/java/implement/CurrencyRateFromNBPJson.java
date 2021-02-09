package implement;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import common.ExchangeRate;
import common.ExchangeRatesSeriesJson;
import exception.ParsingExchangeRate;

public class CurrencyRateFromNBPJson extends ExchangeServiceNBP {

	public CurrencyRateFromNBPJson(Date lastCurrencyRateDate) {
		super(lastCurrencyRateDate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ExchangeRate getCurrencyRate(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		ExchangeRate rate = new ExchangeRate();
		rate.setCurrencyCode(currencyCode);
		rate.setCurrencyDate(date);
		String data = getRateFromApi(rate);
		try {
			JsonMapper objectMapper = new JsonMapper();
			ExchangeRatesSeriesJson json = objectMapper.readValue(data, ExchangeRatesSeriesJson.class);
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
