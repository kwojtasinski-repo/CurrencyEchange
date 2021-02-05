package com.exchange.ExchangesRateMaven.Service.Common;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

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
	
}
