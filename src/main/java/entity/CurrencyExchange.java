package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "CURRENCY_EXCHANGE")
public class CurrencyExchange {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Long id;
	
	@Column(name="currency_code_to_exchange")
	private String currencyCode;
	
	@Column(name="currency_code_exchanged")
	private String currencyCodeMain;
	
	@Column(name="currency_rate_date")
	private java.sql.Date currencyDate;
	
	@Column(name="currency_exchanged")
	private BigDecimal currencyExchanged;
	
	@Column(name="currency_to_exchange")
	private BigDecimal currencyToExchange;
	
	@Column(name="currency_rate")
	private BigDecimal currencyRate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public void setCurrencyDate(java.sql.Date currencyDate) {
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
	public BigDecimal getRate() {
		return currencyRate;
	}
	public void setRate(BigDecimal currencyRate) {
		this.currencyRate= currencyRate;
	}
	public String getCurrencyCodeMain() {
		return currencyCodeMain;
	}
	public void setCurrencyCodeMain(String currencyCodeMain) {
		this.currencyCodeMain = currencyCodeMain;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + " " + currencyToExchange + " " + currencyExchanged + " " + currencyRate + " " + currencyCode + " " + currencyCodeMain + " " + currencyDate;  
	}
}
