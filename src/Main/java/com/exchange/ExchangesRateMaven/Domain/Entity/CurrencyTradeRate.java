package com.exchange.ExchangesRateMaven.Domain.Entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class CurrencyTradeRate {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="currency_code_exchanging")
	private String currencyCodeExchanging;
	
	@Column(name="currency_rate")
	private BigDecimal currencyRate;
	
	@Column(name="currency_code_main")
	private String currencyCodeMain;
	
	@Column(name="currency_rate_date")
	private java.sql.Timestamp currencyRateDate;
	
	@Column(name="cash_to_exchange")
	private BigDecimal cashToExchange;
	
	@Column(name="cash_exchanged")
	private BigDecimal cashExchanged;
	
	public Long getId() {  
	    return id;  
	}
	
	public void setId(Long id) {  
	    this.id = id;  
	}
	
	public String getCurrencyCodeExchanging() {  
	    return currencyCodeExchanging;  
	}
	
	public void setCurrencyCodeExchanging(String currencyCodeExchanging) {  
	    this.currencyCodeExchanging = currencyCodeExchanging;  
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
	
	public java.sql.Timestamp getCurrencyRateDate() {  
	    return currencyRateDate;  
	}
	
	public void setCurrencyRateDate(java.sql.Timestamp currencyRateDate) {  
	    this.currencyRateDate = currencyRateDate;  
	}
	
	public BigDecimal getCashToExchange() {  
	    return cashToExchange;  
	}
	
	public void setCashToExchange(BigDecimal cashToExchange) {  
	    this.cashToExchange = cashToExchange;  
	}
	
	public BigDecimal getCashExchanged() {  
	    return cashExchanged;  
	}
	
	public void setCashExchanged(BigDecimal cashExchanged) {  
	    this.cashExchanged = cashExchanged;  
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId() + " " + getCurrencyCodeExchanging() + " " + getCurrencyRate() + " " + getCurrencyCodeMain() + " " + getCurrencyRateDate() + " "+ getCashToExchange() + " " + getCashExchanged();
	}
}
