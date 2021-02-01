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
        
        System.out.println("Test exchange currency in PLN");
        BigDecimal currencyExchanged = exchangeRate.calculateCurrency(new BigDecimal("100.00"), "CHF");
        System.out.println("Currency Exchanged " + currencyExchanged);
	}

}
