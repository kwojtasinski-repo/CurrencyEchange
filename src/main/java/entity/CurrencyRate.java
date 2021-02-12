package entity;

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
@Table(name = "CURRENCY_RATE")
public class CurrencyRate {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Long id;
	
	@Column(name="currency_code")
	private String currencyCode;
	
	@Column(name="currency_date")
	private java.sql.Date currencyDate;
	
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
	public java.sql.Date getCurrencyDate() {
		return currencyDate;
	}
	public void setCurrencyDate(java.sql.Date currencyDate) {
		this.currencyDate = currencyDate;
	}
	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}
}
