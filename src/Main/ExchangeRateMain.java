package Main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Abstract.ExchangeRate;
import Common.ExchangeAdaptee;
import Implement.ExchangeRateAdapter;

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
		System.out.println("Exchange currency");
		System.out.println("Currency for 1" + currencyCode + " is " + exchangeRate.getCurrencyRate(currencyCode) + " PLN");
		BigDecimal currencyExchanged = exchangeRate.calculateCurrency(money, currencyCode);
		System.out.println("Current cash " + money + " " + currencyCode);
		System.out.println("Cash exchanged " + currencyExchanged.setScale(2, RoundingMode.HALF_UP) + " " + "PLN");
	}

}
