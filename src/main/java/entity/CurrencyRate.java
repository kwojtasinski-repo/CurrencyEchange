package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "CURRENCY_RATE")
public class CurrencyRate implements Serializable {
	
	private static final long serialVersionUID = 690055885142562289L;

	@Id
	@Column(name = "id_currency_rate")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Long currencyId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="id_currency", nullable=false)
    private Currency currency;
	
	@Column(name="currency_date")
	@NotNull
	private java.sql.Date currencyDate;
	
	@Column(name="currency_rate", columnDefinition="Decimal(19,4)")
	@NotNull
	private BigDecimal currencyRate;
	
	@OneToMany(mappedBy = "currencyRate", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<CurrencyExchange> exchangings = new HashSet<>();
	
	public CurrencyRate() {
		// TODO Auto-generated constructor stub
	}
	
	public CurrencyRate(Currency currency, java.util.Date currencyDate, BigDecimal currencyRate) {
		this.currency = currency;
		this.currencyDate = new java.sql.Date(currencyDate.getTime());
		this.currencyRate = currencyRate;
	}
	
	public Long getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public java.sql.Date getCurrencyDate() {
		return currencyDate;
	}
	
	public void setCurrencyDate(java.util.Date currencyDate) {
		this.currencyDate = new java.sql.Date(currencyDate.getTime());
	}
	
	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}
	
	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}
}
