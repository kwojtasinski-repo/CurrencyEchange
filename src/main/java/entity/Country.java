package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "COUNTRY")
public class Country implements Serializable {
	
	private static final long serialVersionUID = -3236921240216741430L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "id_country")
	private Long countryId;
	
	@Column(name = "country_name")
	@NotNull
	private String countryName;

	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<CurrencyExchange> exchangings = new HashSet<>();
	
	@OneToMany(mappedBy = "country", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private Set<CurrencyCountry> currencies = new HashSet<>();
	
	public Country() {
		// TODO Auto-generated constructor stub
	}
	
	public void addCurrency(CurrencyCountry currencyCountry) {
		getCurrencies().add(currencyCountry);
	}
	
	public Country(String countryName) {
		// TODO Auto-generated constructor stub
		this.countryName = countryName;
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

	public Set<CurrencyCountry> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(Set<CurrencyCountry> currencies) {
		this.currencies = currencies;
	}

	public Set<CurrencyExchange> getExchangings() {
		return exchangings;
	}

	public void setExchangings(Set<CurrencyExchange> exchangings) {
		this.exchangings = exchangings;
	}
}
