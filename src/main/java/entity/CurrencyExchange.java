package entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "CURRENCY_EXCHANGE")
public class CurrencyExchange implements Serializable {

	private static final long serialVersionUID = -6229374435585191890L;

	@EmbeddedId
	private CurrencyExchangeKey id;

	@ManyToOne
	@MapsId("countryId")
	@JoinColumn(name = "id_country")
	private Country country;

	@ManyToOne
	@MapsId("currencyId")
	@JoinColumn(name = "id_currency_rate")
	private CurrencyRate currencyRate;

	public CurrencyExchangeKey getId() {
		return id;
	}

	public void setId(CurrencyExchangeKey id) {
		this.id = id;
	}

	public CurrencyExchange() {
		// TODO Auto-generated constructor stub
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public CurrencyRate getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(CurrencyRate currencyRate) {
		this.currencyRate = currencyRate;
	}
	
	public CurrencyExchange(CurrencyExchangeKey id, Country country, CurrencyRate currencyRate) {
		this.id = id;
		this.country = country;
		this.currencyRate = currencyRate;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
