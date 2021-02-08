package com.exchange.ExchangesRateMaven.Service.Common;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.exchange.ExchangesRateMaven.Service.Abstract.DataMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ListCurrencies extends JsonDeserializer<List<Currencies>> implements DataMapping {
	@JsonProperty("Currencies")
	private List<Currencies> Currencies;
	
	@Override
	public List<Currencies> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Currencies> getCurrencies() {
		return Currencies;
	}

	public void setCurrencies(List<Currencies> currencies) {
		Currencies = currencies;
	}

	@Override
	public BigDecimal getCurrencyRate(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		BigDecimal rate = null;
		for(Currencies  cur : this.Currencies) {
			if(cur.getCode().toUpperCase().equals(currencyCode) && isSameDay(cur.getDate(), date)) {
				rate = cur.getMid();
			}
		}
		return rate;
	}
	
	public boolean isSameDay(Date date1, Date date2) {
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	    return fmt.format(date1).equals(fmt.format(date2));
	}
}
