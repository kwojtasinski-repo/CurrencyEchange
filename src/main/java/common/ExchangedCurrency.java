package common;

import java.math.BigDecimal;
import java.util.Date;

public class ExchangedCurrency {
	private String currencyCode;
	private Date currencyDate;
	private BigDecimal currencyExchanged;
	private BigDecimal currencyToExchange;
	
	public ExchangedCurrency(String currencyCode, Date currencyDate, BigDecimal currencyToExchange, BigDecimal currencyExchanged) {
		// TODO Auto-generated constructor stub
		this.currencyCode = currencyCode;
		this.currencyDate = currencyDate;
		this.currencyToExchange = currencyToExchange;
		this.currencyExchanged = currencyExchanged;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Date getCurrencyDate() {
		return currencyDate;
	}
	public void setCurrencyDate(Date currencyDate) {
		this.currencyDate = currencyDate;
	}
	public BigDecimal getCurrencyExchanged() {
		return currencyExchanged;
	}
	public void setCurrencyExchanged(BigDecimal currencyExchanged) {
		this.currencyExchanged = currencyExchanged;
	}
	public BigDecimal getCurrencyToExchange() {
		return currencyToExchange;
	}
	public void setCurrencyToExchange(BigDecimal currencyToExchange) {
		this.currencyToExchange = currencyToExchange;
	}
}
