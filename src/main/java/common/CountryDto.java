package common;

import java.math.BigDecimal;
import java.util.Date;

public class CountryDto {
	private String countryName;
	private BigDecimal currencyToExchange;
	private BigDecimal currencyExchanged;
	private java.sql.Date currencyDate;
	private String currencyCode;
	
	public CountryDto() {
		// TODO Auto-generated constructor stub
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public BigDecimal getCurrencyToExchange() {
		return currencyToExchange;
	}

	public void setCurrencyToExchange(BigDecimal currencyToExchange) {
		this.currencyToExchange = currencyToExchange;
	}

	public BigDecimal getCurrencyExchanged() {
		return currencyExchanged;
	}

	public void setCurrencyExchanged(BigDecimal currencyExchanged) {
		this.currencyExchanged = currencyExchanged;
	}

	public java.sql.Date getCurrencyDate() {
		return currencyDate;
	}

	public void setCurrencyDate(java.sql.Date currencyDate) {
		this.currencyDate = currencyDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
