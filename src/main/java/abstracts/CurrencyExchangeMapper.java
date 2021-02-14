package abstracts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import common.CountryDto;
import common.ExchangeRateDto;
import entity.Country;
import entity.CurrencyRate;

@Mapper
public interface CurrencyExchangeMapper {

	CurrencyExchangeMapper INSTANCE = Mappers.getMapper(CurrencyExchangeMapper.class);
	
    @Mapping(target = "currencyId", ignore = true)
    CurrencyRate mapToCurrencyRate(ExchangeRateDto currencyRate);
    
    ExchangeRateDto mapToExchangeRateDto(CurrencyRate currencyRate);
    
    @Mapping(target = "countryId", ignore = true)
    Country mapToCountry(CountryDto countryDto);
    
    CountryDto mapToCountryDto(Country country);
}