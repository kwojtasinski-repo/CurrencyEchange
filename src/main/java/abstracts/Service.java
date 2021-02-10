package abstracts;

import java.util.Date;

public interface Service {
	Date getLastCurrencyRateDate();
	String getExchangeRate(String currencyCode, Date date);
	void setFormat(String format);
	String getFormat();
}
