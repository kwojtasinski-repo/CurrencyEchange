package com.exchange.ExchangesRateMaven.Service.Abstract;

import java.math.BigDecimal;
import java.util.Date;

public interface DataMapping {
	BigDecimal getCurrencyRate(String currencyCode, Date date);
}
