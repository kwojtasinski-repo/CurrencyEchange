package com.exchange.ExchangesRateMaven.Service.Common;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.exchange.ExchangesRateMaven.Service.Abstract.DataMapping;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ExchangeRatesSeriesJson extends JsonDeserializer<List<RatesJson>> implements DataMapping {
	private String table;
	private String currency;
	private String code;
	private List<RatesJson> rates;
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<RatesJson> getRates() {
		return rates;
	}
	public void setRates(List<RatesJson> rates) {
		this.rates = rates;
	}
	
	
	@Override
    public List<RatesJson> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		InnerRates innerRates = jp.readValueAs(InnerRates.class);

        return innerRates.elements;
    }

    private static class InnerRates {
        public List<RatesJson> elements;
    }

	@Override
	public BigDecimal getCurrencyRate(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		BigDecimal rate = null;
		for(RatesJson ratesJson : this.rates) {
			if(isSameDay(ratesJson.getEffectiveDate(), date)) {
				rate = ratesJson.getMid();
			}
		}
		return rate;
	}
	
	public boolean isSameDay(Date date1, Date date2) {
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	    return fmt.format(date1).equals(fmt.format(date2));
	}
}
