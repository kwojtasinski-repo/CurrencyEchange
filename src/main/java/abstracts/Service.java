package abstracts;

import java.util.Date;

import entity.CurrencyRate;

public interface Service {
	Date getLastCurrencyRateDate();
	CurrencyRate getExchangeRate(String currencyCode, Date date);
	void setFormat(String format);
	String getFormat();
}
