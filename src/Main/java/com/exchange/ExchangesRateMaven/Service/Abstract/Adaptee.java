package com.exchange.ExchangesRateMaven.Service.Abstract;

import java.math.BigDecimal;
import java.util.Date;

public interface Adaptee {
	String getDataAsString(String currencyCode, Date date);
	Date getLastArchiveCurrencyRate();
	BigDecimal getCurrencyRate(String currencyCode, Date date);
}
