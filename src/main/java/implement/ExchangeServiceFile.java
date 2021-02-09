package implement;

import java.io.File;
import java.util.Date;

import abstracts.Service;
import common.ExchangeRate;

public abstract class ExchangeServiceFile implements Service {
	private Date lastCurrencyRateDate;
	private File file;
	
	public ExchangeServiceFile(File file, Date lastCurrencyRateDate) {
		// TODO Auto-generated constructor stub
		this.lastCurrencyRateDate = lastCurrencyRateDate;
		this.file = file;
	}
	
	@Override
	public Date getLastCurrencyRateDate() {
		// TODO Auto-generated method stub
		return lastCurrencyRateDate;
	}

	@Override
	public ExchangeRate getExchangeRate(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		return getCurrencyRate(file, currencyCode, date);
	}

	public abstract ExchangeRate getCurrencyRate(File file, String currencyCode, Date date);
	public abstract String getFormat();
}
