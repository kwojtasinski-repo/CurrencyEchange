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
@Table(name = "COUNTRY")
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "id_country")
	private Long countryId;
	
	@Column(name = "country_name")
	@NotNull
	private String countryName;
	
	@Column(name="currency_to_exchange")
	@NotNull
	private BigDecimal currencyToExchange;
	
	@Column(name="currency_exchanged")
	@NotNull
	private BigDecimal currencyExchanged;
	
	@Column(name="currency_rate_date")
	@NotNull
	private java.sql.Date currencyDate;

	@OneToMany(mappedBy = "country")
	private Set<CurrencyExchange> exchangings = new HashSet<>();
	
	public Country() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
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
}
