package com.exchange.ExchangesRateMaven.Service.Common;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class JsonReader {
	public static BigDecimal getCurrencyRateFromJson(String txt) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jsonObj = null;
		BigDecimal rate = null;
		jsonObj = new JSONObject(txt);
		System.out.println(txt);
		System.out.println(jsonObj);
		
		try {
			System.out.println(jsonObj.getJSONArray("rates").getJSONObject(0));
			rate = new BigDecimal(jsonObj.getJSONArray("rates").getJSONObject(0).get("mid").toString());
		} catch (JSONException e)	{
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		if(rate == null) {
			throw new Exception("Check json format : " + txt);
		}
		
		return rate;
	}
	
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		JsonMapper jsonMapper = new JsonMapper();
		//System.out.println(loadXMLToObject("<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Table>A</Table><Currency>frank szwajcarski</Currency><Code>CHF</Code><Rates><Rate><No>024/A/NBP/2021</No><EffectiveDate>2021-02-05</EffectiveDate><Mid>4.1590</Mid></Rate></Rates></ExchangeRatesSeries>"));
	    ExchangeRatesSeriesJson ers = jsonMapper.readValue("{\"table\":\"A\",\"currency\":\"frank szwajcarski\",\"code\":\"CHF\",\"rates\":[{\"no\":\"024/A/NBP/2021\",\"effectiveDate\":\"2021-02-05\",\"mid\":4.1590}]}", ExchangeRatesSeriesJson.class);
	    for(RatesJson rate : ers.getRates()) {
	    	System.out.println(rate.getNo() + ";  " + rate.getEffectiveDate() + ";  " + rate.getMid());
	    }
	    
	}
}
