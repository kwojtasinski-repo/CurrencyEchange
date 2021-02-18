package abstracts;

import java.util.Date;

import entity.CurrencyRate;

public interface Manager {
	CurrencyRate exchangeCurrencyToPLN(String currencyCode, Date date);
}
