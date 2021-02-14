package abstracts;

import common.CountryDto;
import common.ExchangeRateDto;
import entity.Country;
import entity.CurrencyRate;
import java.sql.Date;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-14T21:41:37+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
*/
public class CurrencyExchangeMapperImpl implements CurrencyExchangeMapper {

    @Override
    public CurrencyRate mapToCurrencyRate(ExchangeRateDto currencyRate) {
        if ( currencyRate == null ) {
            return null;
        }

        CurrencyRate currencyRate1 = new CurrencyRate();

        currencyRate1.setCurrencyCode( currencyRate.getCurrencyCode() );
        if ( currencyRate.getCurrencyDate() != null ) {
            currencyRate1.setCurrencyDate( new Date( currencyRate.getCurrencyDate().getTime() ) );
        }
        currencyRate1.setCurrencyRate( currencyRate.getCurrencyRate() );

        return currencyRate1;
    }

    @Override
    public ExchangeRateDto mapToExchangeRateDto(CurrencyRate currencyRate) {
        if ( currencyRate == null ) {
            return null;
        }

        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();

        exchangeRateDto.setCurrencyCode( currencyRate.getCurrencyCode() );
        exchangeRateDto.setCurrencyDate( currencyRate.getCurrencyDate() );
        exchangeRateDto.setCurrencyRate( currencyRate.getCurrencyRate() );

        return exchangeRateDto;
    }

    @Override
    public Country mapToCountry(CountryDto countryDto) {
        if ( countryDto == null ) {
            return null;
        }

        Country country = new Country();

        country.setCountryName( countryDto.getCountryName() );
        country.setCurrencyToExchange( countryDto.getCurrencyToExchange() );
        country.setCurrencyExchanged( countryDto.getCurrencyExchanged() );
        country.setCurrencyDate( countryDto.getCurrencyDate() );

        return country;
    }

    @Override
    public CountryDto mapToCountryDto(Country country) {
        if ( country == null ) {
            return null;
        }

        CountryDto countryDto = new CountryDto();

        countryDto.setCountryName( country.getCountryName() );
        countryDto.setCurrencyToExchange( country.getCurrencyToExchange() );
        countryDto.setCurrencyExchanged( country.getCurrencyExchanged() );
        countryDto.setCurrencyDate( country.getCurrencyDate() );

        return countryDto;
    }
}
