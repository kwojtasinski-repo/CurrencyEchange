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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "CURRENCY")
public class Currency implements Serializable {
	
	private static final long serialVersionUID = -7147697261773739949L;

	@Id
	@Column(name = "id_currency")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Long currencyCodeId;
	
	@Column(name="currency_name")
	private String currencyName;
	
	@Column(name="currency_code")
	private String currencyCode;
	
	@OneToMany(mappedBy = "currency", cascade = CascadeType.REMOVE)
	private Set<CurrencyCountry> countryWithCurrencies = new HashSet<>();
	
	public Long getId() {
		return currencyCodeId;
	}
	public void setId(Long id) {
		this.currencyCodeId = id;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
