package Main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Abstract.ExchangeRate;
import Common.ExchangeAdaptee;
import Entity.CurrencyTradeRate;
import Implement.ExchangeRateAdapter;
import Repository.CurrencyTradeRateRepository;
import Repository.EntityRepository;

public class ExchangeRateMain {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		ExchangeAdaptee adaptee = new ExchangeAdaptee();
		ExchangeRate exchangeRate = new ExchangeRateAdapter(adaptee);
		System.out.println("Welcome in Currency Exchange\nPlease enter your currency");
		Map<Integer, String> currencyDict = new HashMap<Integer, String>();
		currencyDict.put(1, "EUR");
		currencyDict.put(2, "USD");
		currencyDict.put(3, "CHF");
		currencyDict.put(4, "GBP");
		for(Integer key : currencyDict.keySet())
		{
			System.out.println("Press "+ key +" if you want to exchange " + currencyDict.get(key));
		}
		Scanner input = new Scanner(System.in);
		Integer currencyDictKey = 2;
		try 
		{
		    currencyDictKey = Integer.parseInt(input.next());
		}
		catch (Exception e) 
		{
		    System.out.println(e.getMessage());
		}
		
		String currencyCode = "EUR";
		if(currencyDict.get(currencyDictKey) != null)
		{
			currencyCode = currencyDict.get(currencyDictKey);
		}
		
		
		BigDecimal money = new BigDecimal("100");
		try 
		{
			System.out.println("Please Enter the money you want to exchange");
			money = input.nextBigDecimal();
		}
		catch (Exception e) 
		{
		    System.out.println("Error " + e.getMessage());
		}
		input.close();
		
		BigDecimal rate = exchangeRate.getCurrencyRate(currencyCode);
		System.out.println("Exchange currency");
		System.out.println("Currency for 1" + currencyCode + " is " + rate + " PLN");
		BigDecimal currencyExchanged = exchangeRate.calculateCurrency(money, currencyCode);
		System.out.println("Current cash " + money + " " + currencyCode);
		System.out.println("Cash exchanged " + currencyExchanged.setScale(2, RoundingMode.HALF_UP) + " " + "PLN");
		EntityRepository<CurrencyTradeRate> currencyTradeRateRepo = new CurrencyTradeRateRepository();
		CurrencyTradeRate tradeRate = new CurrencyTradeRate();
		tradeRate.setId(0L);
		tradeRate.setCashExchanged(currencyExchanged);
		tradeRate.setCashToExchange(money);
		tradeRate.setCurrencyCodeExchanging(currencyCode);
		tradeRate.setCurrencyCodeMain("PLN");
		tradeRate.setCurrencyRate(rate);
        java.util.Date date = new java.util.Date();
		tradeRate.setCurrencyRateDate(new java.sql.Timestamp(date.getTime()));
		Long id = currencyTradeRateRepo.add(tradeRate);
		System.out.println("Id after insert to db " + id);
	}

}
