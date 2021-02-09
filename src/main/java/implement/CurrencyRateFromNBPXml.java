package implement;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import common.ExchangeRate;
import common.ExchangeRatesSeriesXml;
import exception.ParsingExchangeRate;

public class CurrencyRateFromNBPXml extends ExchangeServiceNBP {

	public CurrencyRateFromNBPXml(Date lastCurrencyRateDate) {
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
			XmlMapper objectMapper = new XmlMapper();
			ExchangeRatesSeriesXml xml = objectMapper.readValue(data, ExchangeRatesSeriesXml.class);
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
