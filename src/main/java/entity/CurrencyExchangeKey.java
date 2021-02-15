package entity;

import java.io.Serializable;

import javax.persistence.Column;

public class CurrencyExchangeKey implements Serializable {

	private static final long serialVersionUID = -6640289532212586226L;

	@Column(name = "id_country")
    private Long countryId;

    @Column(name = "id_currency_rate")
    private Long currencyId;
    
	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	
	public CurrencyExchangeKey() {
		// TODO Auto-generated constructor stub
	}
	
	public CurrencyExchangeKey(Long countryId, Long currencyId){
		this.countryId = countryId;
		this.currencyId = currencyId;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((countryId == null) ? 0 : countryId.hashCode());
        result = prime * result + ((currencyId == null) ? 0 : currencyId.hashCode());
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
        CurrencyExchangeKey other = (CurrencyExchangeKey) obj;
        if (countryId == null) {
            if (other.countryId != null)
                return false;
        } else if (!countryId.equals(other.countryId))
            return false;
        if (currencyId == null) {
            if (other.currencyId != null)
                return false;
        } else if (!currencyId.equals(other.currencyId))
            return false;
        return true;
    }
}
