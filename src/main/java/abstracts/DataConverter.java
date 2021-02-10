package abstracts;

import common.ExchangeRate;

public interface DataConverter {
	ExchangeRate getCurrencyRate(String dataString);
	String getFormat();
}
