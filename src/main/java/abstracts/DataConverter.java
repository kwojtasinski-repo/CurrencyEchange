package abstracts;

import common.ExchangeRateDto;

public interface DataConverter {
	ExchangeRateDto getCurrencyRate(String dataString);
	String getFormat();
}
