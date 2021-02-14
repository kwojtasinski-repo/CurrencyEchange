package abstracts;

import common.CountryDto;

public interface CountryConverter {
	CountryDto getCodeByCurrencyName(String currencyName);
}
