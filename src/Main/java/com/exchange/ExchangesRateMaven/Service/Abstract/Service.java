package com.exchange.ExchangesRateMaven.Service.Abstract;

import java.util.Date;

import com.exchange.ExchangesRateMaven.Service.Common.Format;

public interface Service {
	String getData(String currencyCode, Date date);
	Date getLastArchiveCurrencyRate();
	Format getFormat();
}
