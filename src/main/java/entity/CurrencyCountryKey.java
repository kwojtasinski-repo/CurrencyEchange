package entity;

import java.io.Serializable;

import javax.persistence.Column;

public class CurrencyCountryKey implements Serializable {

	private static final long serialVersionUID = 7055053706261761212L;

	@Column(name = "id_country")
    private Long countryId;

    @Column(name = "id_currency")
    private Long currencyCodeId;

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getCurrencyCodeId() {
		return currencyCodeId;
	}

	public void setCurrencyCodeId(Long currencyCodeId) {
		this.currencyCodeId = currencyCodeId;
	}
	
	@Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + ((countryId == null) ? 0 : countryId.hashCode());
        result = prime * result + ((currencyCodeId == null) ? 0 : currencyCodeId.hashCode());
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
        CurrencyCountryKey other = (CurrencyCountryKey) obj;
        if (countryId == null) {
            if (other.getCountryId() != null)
                return false;
        } else if (!countryId.equals(other.getCountryId()))
            return false;
        if (currencyCodeId == null) {
            if (other.currencyCodeId != null)
                return false;
        } else if (!currencyCodeId.equals(other.currencyCodeId))
            return false;
        return true;
    }
}
