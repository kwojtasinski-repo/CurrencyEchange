package implement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import abstracts.DataConverter;
import common.ExchangeRatesSeriesJson;
import entity.CurrencyRate;
import exception.ParsingExchangeRate;

public class JsonConverter implements DataConverter {

	@Override
	public <String> CurrencyRate getCurrencyRate(String data) {
		// TODO Auto-generated method stub
		try {
			java.lang.String dataString = (java.lang.String) data;
			if(dataString.length()==0) {
				throw new ParsingExchangeRate("While conversion error occurred. Please check your data format");
			}
			JsonMapper objectMapper = new JsonMapper();
			ExchangeRatesSeriesJson json = objectMapper.readValue(dataString, ExchangeRatesSeriesJson.class);
			CurrencyRate rateModified = new CurrencyRate();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
			DateTimeFormatter fIn = DateTimeFormatter.ofPattern( "yyyy-MM-dd" , Locale.GERMANY);  // As a habit, specify the desired/expected locale, though in this case the locale is irrelevant.
			LocalDate localDate = LocalDate.parse( format.format(json.getRates().get(0).getEffectiveDate()), fIn);
			ZoneId defaultZoneId = ZoneId.systemDefault();
			rateModified.setCurrencyCode(json.getCode());
			rateModified.setCurrencyDate(Date.from(localDate.atStartOfDay(defaultZoneId).toInstant()));// json.getRates().get(0).getEffectiveDate());
			rateModified.setCurrencyRate(json.getRates().get(0).getMid());
			return rateModified;
		} catch(JsonMappingException e) {
			throw new ParsingExchangeRate("While conversion error occurred. Please check your data format"); 
		} catch(JsonProcessingException e) {
			throw new ParsingExchangeRate("While conversion error occurred. Please check your data format");
		}
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return "json";
	}
}
