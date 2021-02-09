package abstracts;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;

import common.ExchangeRate;
import common.ExchangedCurrency;

public abstract class CurrencyExchangeFileService {
	
	public ExchangedCurrency exchangeCurrencyToPLN(String currencyCode, Date date, BigDecimal value) {
		// TODO Auto-generated method stub
		checkInputArguments(currencyCode, date, value);
		return null;
	}
	
	private void checkInputArguments(String currencyCode, Date date, BigDecimal value) {
		if(currencyCode == null || currencyCode.length() !=3 || Pattern.matches("[a-zA-Z]+", currencyCode)) {
			throw new IllegalArgumentException("Invalid currency code");
		}
		if(date == null || date.after(new Date())) {
			throw new IllegalArgumentException("Invalid date");
		}
		if(value == null) {
			throw new IllegalArgumentException("Invalid value");
		}
	}
	
	abstract protected Date getLastCurrencyRateDate();
	abstract protected ExchangeRate getExchangeRate(String currencyCode, Date date);
}
