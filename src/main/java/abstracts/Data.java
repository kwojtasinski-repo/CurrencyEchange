package abstracts;

import java.util.Date;

import common.ExchangeRate;

public interface Data {
	ExchangeRate getCurrencyRate(String currencyCode, Date date);
	String getFormat();
}
