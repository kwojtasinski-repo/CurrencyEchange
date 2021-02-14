package common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyCodesWithCountries {
	@JsonProperty("Entity")
	private String countryName;
	
	@JsonProperty("Currency")
	private String	currency;
	
	@JsonProperty("AlphabeticCode")
	private String currencyCode;
	
	@JsonProperty("NumericCode")
	private int numericCode;
	
	@JsonProperty("MinorUnit")
	private String minorUnit;
	
	@JsonProperty("WithdrawalDate")
	private String withdrawalDate;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(int numericCode) {
		this.numericCode = numericCode;
	}

	public String getMinorUnit() {
		return minorUnit;
	}

	public void setMinorUnit(String minorUnit) {
		this.minorUnit = minorUnit;
	}

	public String getWithdrawalDate() {
		return withdrawalDate;
	}

	public void setWithdrawalDate(String withdrawalDate) {
		this.withdrawalDate = withdrawalDate;
	} 
}
