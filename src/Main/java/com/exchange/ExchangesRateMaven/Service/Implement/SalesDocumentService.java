package com.exchange.ExchangesRateMaven.Service.Implement;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.exchange.ExchangesRateMaven.Service.Abstract.Adaptee;
import com.exchange.ExchangesRateMaven.Service.Abstract.DataMapping;
import com.exchange.ExchangesRateMaven.Service.Abstract.ExchangeRate;
import com.exchange.ExchangesRateMaven.Service.Abstract.SalesDocument;
import com.exchange.ExchangesRateMaven.Service.Abstract.Service;
import com.exchange.ExchangesRateMaven.Service.Common.CurrencyRateFromApiJson;
import com.exchange.ExchangesRateMaven.Service.Common.CurrencyRateFromApiXml;
import com.exchange.ExchangesRateMaven.Service.Common.ExchangeManager;
import com.exchange.ExchangesRateMaven.Service.Common.ExchangeRatesSeriesJson;
import com.exchange.ExchangesRateMaven.Service.Common.ExchangeRatesSeriesXml;
import com.exchange.ExchangesRateMaven.Service.Common.FileReadService;
import com.exchange.ExchangesRateMaven.Service.Common.Format;
import com.exchange.ExchangesRateMaven.Service.Common.ListCurrencies;

public class SalesDocumentService implements SalesDocument {

	@Override
	public BigDecimal insert(BigDecimal money, String currencyCode, Date date) {
		// TODO Auto-generated method stub
		String string = "2021-02-05";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateArchival = new Date(1000L);
		try {
			dateArchival = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataMapping jsonFile = new ListCurrencies();
		DataMapping xmlFile = new ListCurrencies();
		DataMapping json = new ExchangeRatesSeriesJson();
		DataMapping xml = new ExchangeRatesSeriesXml();
		Service serviceJson = new CurrencyRateFromApiJson(new Date(1009926000000L));
		Service serviceXml = new CurrencyRateFromApiXml(new Date(1009926000000L));
		Service serviceFileJson = new FileReadService("C:\\Projects\\ExchangesRateMaven\\src\\main\\java\\com\\exchange\\ExchangesRateMaven\\Service\\Implement\\CurrencyJson.json", Format.JSON, dateArchival);
		Service serviceFileXml = new FileReadService("C:\\Projects\\ExchangesRateMaven\\src\\main\\java\\com\\exchange\\ExchangesRateMaven\\Service\\Implement\\CurrencyXml.xml", Format.XML, dateArchival);
		Adaptee adapteeWebXml = new ExchangeManager(serviceXml, xml);
		Adaptee adapteeWebJson = new ExchangeManager(serviceJson, json);
		Adaptee adapteeFileJson = new ExchangeManager(serviceFileJson, jsonFile);
		Adaptee adapteeFileXml = new ExchangeManager(serviceFileXml, xmlFile);
		ExchangeRate exchangeRate = new ExchangeRateAdapter(adapteeFileXml); // inject service which has implemented getCurrencyRate
		
		BigDecimal currency = exchangeRate.calculateCurrency(money, currencyCode, date);
		return currency;
	}
	
	public static void main(String[] args) {
		SalesDocumentService s = new SalesDocumentService();
		String string = "2021-02-06";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = new Date(1000L);
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.insert(new BigDecimal("500"), "eur", date));
	}
}
