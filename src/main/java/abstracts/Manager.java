package abstracts;

import java.math.BigDecimal;
import java.util.Date;

import common.ExchangedCurrency;

public interface Manager {
	ExchangedCurrency exchangeCurrencyToPLN(String currencyCode, Date date, BigDecimal value);
}
