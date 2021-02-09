package abstracts;

import java.util.Date;

import common.ExchangeRate;

public interface Service {
	Date getLastCurrencyRateDate();
	ExchangeRate getExchangeRate(String currencyCode, Date date);
}
