package Main;

import java.math.BigDecimal;

import Abstract.ExchangeRate;
import Common.ExchangeAdaptee;
import Implement.ExchangeRateAdapter;

public class ExchangeRateMain {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		ExchangeAdaptee adaptee = new ExchangeAdaptee();
        ExchangeRate exchangeRate = new ExchangeRateAdapter(adaptee);
		String currencyCode = "CHF";
        
		System.out.println("Test exchange currency in PLN");
		System.out.println("Currency for " + currencyCode + " " + exchangeRate.getCurrencyRate(currencyCode) + "PLN");
		BigDecimal currencyExchanged = exchangeRate.calculateCurrency(new BigDecimal("100.00"), currencyCode);
		System.out.println("Currency Exchanged " + currencyExchanged + "PLN");
	}

}
