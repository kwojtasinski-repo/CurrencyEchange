package entity;

import java.util.HashSet;
import java.util.Set;

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
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "id_country")
	private Long countryId;
	
	@Column(name = "country_name")
	@NotNull
	private String countryName;

	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private Set<CurrencyExchange> exchangings = new HashSet<>();
	
	@OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
	private Set<CurrencyCountry> currencies = new HashSet<>();
	
	public Country() {
		// TODO Auto-generated constructor stub
	}
	
	public void addCurrency(CurrencyCountry currencyCountry) {
		currencies.add(currencyCountry);
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
}
