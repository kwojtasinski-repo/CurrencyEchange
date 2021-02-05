package com.exchange.ExchangesRateMaven.Service.Abstract;

import java.math.BigDecimal;
import java.util.Date;

import com.exchange.ExchangesRateMaven.Domain.Entity.Currency;

public interface SalesDocument {
	BigDecimal insert(BigDecimal currency, String currencyCode, Date date);
}
