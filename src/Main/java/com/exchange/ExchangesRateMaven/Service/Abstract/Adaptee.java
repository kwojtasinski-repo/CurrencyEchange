package com.exchange.ExchangesRateMaven.Service.Abstract;

import java.util.Date;

public interface Adaptee {
	String getDataAsString(String currencyCode, Date date);
	Date getLastArchiveCurrencyRate();
}
