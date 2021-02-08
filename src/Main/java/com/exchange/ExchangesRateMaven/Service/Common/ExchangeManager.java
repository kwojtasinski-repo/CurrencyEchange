package com.exchange.ExchangesRateMaven.Service.Common;

import java.math.BigDecimal;
import java.util.Date;

import com.exchange.ExchangesRateMaven.Service.Abstract.Adaptee;
import com.exchange.ExchangesRateMaven.Service.Abstract.DataMapping;
import com.exchange.ExchangesRateMaven.Service.Abstract.Service;

public class ExchangeManager implements Adaptee {
	private DataMapping _dataToMap;
	private Service _service;
	private Date _date;
	public ExchangeManager(Service service, DataMapping dataToMap) {
		// TODO Auto-generated constructor stub
		this._dataToMap = dataToMap;
		this._service = service;
	}
	
	@Override
	public BigDecimal getCurrencyRate(String currencyCode, Date date) {
		try {
			String dataString = getDataAsString(currencyCode, date);
			if(dataString.length()==0) {
				return null;
			}
			System.out.println(dataString);
			_dataToMap = (DataMapping) Mapper.mapObject(dataString, _service.getFormat(), _dataToMap);
			return _dataToMap.getCurrencyRate(currencyCode, date);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
		
	@Override
	public String getDataAsString(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		String dataString = _service.getData(currencyCode, date);
		return dataString ;
	}
	@Override
	public Date getLastArchiveCurrencyRate() {
		// TODO Auto-generated method stub
		return _service.getLastArchiveCurrencyRate();
	}
}
