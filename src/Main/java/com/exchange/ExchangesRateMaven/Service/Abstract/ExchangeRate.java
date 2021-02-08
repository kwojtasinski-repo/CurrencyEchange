package com.exchange.ExchangesRateMaven.Service.Abstract;
import java.math.BigDecimal;
import java.util.Date;

public interface ExchangeRate {
	BigDecimal getCurrencyRate(String currencyCode, Date date);
	BigDecimal calculateCurrency(BigDecimal currencyValue, String currencyCode, Date date);
}
