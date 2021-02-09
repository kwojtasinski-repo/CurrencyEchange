package implement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import common.ExchangeRate;
import common.ListCurrencies;
import exception.CurrencyNotFound;
import exception.ParsingExchangeRate;
import exception.UncheckedIOException;

public class CurrencyRateFromFileJson extends ExchangeServiceFile {
	
	public CurrencyRateFromFileJson(File file, Date lastCurrencyRateDate) {
		super(file, lastCurrencyRateDate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ExchangeRate getCurrencyRate(File file, String currencyCode, Date date) {
		// TODO Auto-generated method stub
		try (FileReader reader = new FileReader(file)){
			JsonMapper objectMapper = new JsonMapper();
			ListCurrencies json = objectMapper.readValue(reader, ListCurrencies.class);
			ExchangeRate rateModified = new ExchangeRate();
			rateModified.setCurrencyCode(json.getCurrency(currencyCode, date).getCode());
			rateModified.setCurrencyDate(json.getCurrency(currencyCode, date).getDate());
			rateModified.setCurrencyRate(json.getCurrency(currencyCode, date).getMid());
			return rateModified;
		} catch(JsonMappingException e) {
			throw new ParsingExchangeRate(e.getMessage()); 
		} catch(JsonProcessingException e) {
			throw new ParsingExchangeRate(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new CurrencyNotFound(e.getMessage());
		} catch (IOException e) {
			throw new UncheckedIOException(e.getMessage());
		}
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return "json";
	}

}
