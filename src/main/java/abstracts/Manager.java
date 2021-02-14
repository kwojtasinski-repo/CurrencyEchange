package abstracts;

import java.math.BigDecimal;
import java.util.Date;

import common.ExchangeRateDto;
import common.ExchangedCurrencyDto;

public interface Manager {
	ExchangedCurrencyDto exchangeCurrencyToPLN(String currencyCode, Date date, BigDecimal value);
	ExchangeRateDto getCurrencyRate(String currencyCode, Date date, BigDecimal value);
	BigDecimal exchangeCurrencyToPLNByCountryName(String countryName, Date date, BigDecimal value);
}
