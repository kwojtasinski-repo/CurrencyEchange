package common;

import java.math.BigDecimal;
import java.util.Date;

public class ExchangedCurrencyDto {
	private String currencyCode;
	private Date currencyDate;
	private BigDecimal currencyExchanged;
	private BigDecimal currencyToExchange;
	private BigDecimal currencyRate;
	private String currencyCodeMain;
	
	public ExchangedCurrencyDto(String currencyCode, Date currencyDate, BigDecimal currencyToExchange, BigDecimal currencyExchanged, BigDecimal currencyRate, String currencyCodeMain) {
		// TODO Auto-generated constructor stub
		this.currencyCode = currencyCode;
		this.currencyDate = currencyDate;
		this.currencyToExchange = currencyToExchange;
		this.currencyExchanged = currencyExchanged;
		this.currencyRate = currencyRate;
		this.currencyCodeMain = currencyCodeMain;
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

	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getCurrencyCodeMain() {
		return currencyCodeMain;
	}

	public void setCurrencyCodeMain(String currencyCodeMain) {
		this.currencyCodeMain = currencyCodeMain;
	}
}
