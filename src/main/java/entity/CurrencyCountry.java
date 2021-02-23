package entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "CURRENCY_COUNTRY")
public class CurrencyCountry {
	
	@EmbeddedId
	private CurrencyCountryKey id;

	@ManyToOne
	@MapsId("countryId")
	@JoinColumn(name = "id_country")
	private Country country;

	@ManyToOne
	@MapsId("currencyCodeId")
	@JoinColumn(name = "id_currency")
	private Currency currency;
	
	public CurrencyCountryKey getId() {
		return id;
	}

	public void setId(CurrencyCountryKey id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
		CurrencyExchange otherObj = (CurrencyExchange) obj;
		if (getId() == null) {
			if (otherObj.getId() == null) {
				return false;
			}
		} else if (!getId().equals(otherObj.getId())) {
			return false;
		}
		return true;
	}
}
