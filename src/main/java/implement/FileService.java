package implement;

import java.util.Date;

import abstracts.DataConverter;
import abstracts.Service;
import entity.CurrencyRate;

public class FileService implements Service {
	private DataConverter converter;
	private Service service;
	private Date lastArchivalDate;
	
	public FileService(Service service, DataConverter converter, Date date) {
		this.converter = converter;
		this.service = service;
		this.lastArchivalDate = date;
	}
	
	@Override
	public Date getLastCurrencyRateDate() {
		// TODO Auto-generated method stub
		return lastArchivalDate;
	}

	@Override
	public CurrencyRate getExchangeRate(String currencyCode, Date date) {
		CurrencyRate currencyRate = converter.getCurrencyRate(getCurrencyRateFromFile(currencyCode, date));
		if(currencyRate != null) {
			return currencyRate;
		}
		return service.getExchangeRate(currencyCode, date);
	}

	@Override
	public void setFormat(String format) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return converter.getFormat();
	}

	private String getCurrencyRateFromFile(String currencyCode, Date date) {
		return null;
	}
}
