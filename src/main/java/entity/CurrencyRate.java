package entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "CURRENCY_RATE")
public class CurrencyRate {
	
	@Id
	@Column(name = "id_currency_rate")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Long currencyId;
	
	@Column(name="currency_code")
	@NotNull
	private String currencyCode;
	
	@Column(name="currency_date")
	@NotNull
	private java.sql.Date currencyDate;
	
	@Column(name="currency_rate", columnDefinition="Decimal(19,4)")
	@NotNull
	private BigDecimal currencyRate;
	
	@OneToMany(mappedBy = "currencyRate")
	private Set<CurrencyExchange> exchangings = new HashSet<>();
	
	public Long getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
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
