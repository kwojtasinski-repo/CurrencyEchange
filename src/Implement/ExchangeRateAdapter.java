package Implement;
import java.math.BigDecimal;

import org.json.JSONException;

import Abstract.ExchangeRate;
import Common.ExchangeAdaptee;

public class ExchangeRateAdapter implements ExchangeRate
{
	private ExchangeAdaptee _adaptee;
	
	public ExchangeRateAdapter(ExchangeAdaptee adaptee) 
	{
		// TODO Auto-generated constructor stub
		_adaptee = adaptee;
	}
	
	
	@Override
	public BigDecimal getCurrencyRate(String currencyCode) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal calculateCurrency(BigDecimal currencyValue, String currencyCode) 
	{
		// TODO Auto-generated method stub
		BigDecimal rate = new BigDecimal("1");
		try {
			rate = _adaptee.getCurrencyRate(currencyCode);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return currencyValue.multiply(rate);
	}
	
}
