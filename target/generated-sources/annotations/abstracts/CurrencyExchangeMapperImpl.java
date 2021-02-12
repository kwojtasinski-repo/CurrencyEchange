package abstracts;

import common.ExchangeRate;
import common.ExchangedCurrency;
import entity.CurrencyExchange;
import entity.CurrencyRate;
import java.math.BigDecimal;
import java.sql.Date;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-12T11:28:46+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
*/
public class CurrencyExchangeMapperImpl implements CurrencyExchangeMapper {

    @Override
    public CurrencyExchange mapToCurrencyExchange(ExchangedCurrency currencyExchange) {
        if ( currencyExchange == null ) {
            return null;
        }

        CurrencyExchange currencyExchange1 = new CurrencyExchange();

        currencyExchange1.setCurrencyCode( currencyExchange.getCurrencyCode() );
        if ( currencyExchange.getCurrencyDate() != null ) {
            currencyExchange1.setCurrencyDate( new Date( currencyExchange.getCurrencyDate().getTime() ) );
        }
        currencyExchange1.setCurrencyExchanged( currencyExchange.getCurrencyExchanged() );
        currencyExchange1.setCurrencyToExchange( currencyExchange.getCurrencyToExchange() );
        currencyExchange1.setCurrencyCodeMain( currencyExchange.getCurrencyCodeMain() );

        return currencyExchange1;
    }

    @Override
    public ExchangedCurrency mapToExchangedCurrency(CurrencyExchange currencyExchange) {
        if ( currencyExchange == null ) {
            return null;
        }

        String currencyCode = null;
        java.util.Date currencyDate = null;
        BigDecimal currencyExchanged = null;
        BigDecimal currencyToExchange = null;
        String currencyCodeMain = null;

        currencyCode = currencyExchange.getCurrencyCode();
        currencyDate = currencyExchange.getCurrencyDate();
        currencyExchanged = currencyExchange.getCurrencyExchanged();
        currencyToExchange = currencyExchange.getCurrencyToExchange();
        currencyCodeMain = currencyExchange.getCurrencyCodeMain();

        BigDecimal currencyRate = null;

        ExchangedCurrency exchangedCurrency = new ExchangedCurrency( currencyCode, currencyDate, currencyToExchange, currencyExchanged, currencyRate, currencyCodeMain );

        return exchangedCurrency;
    }

    @Override
    public CurrencyRate mapToCurrencyRate(ExchangeRate currencyRate) {
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
    public ExchangeRate mapToExchangeRate(CurrencyRate currencyRate) {
        if ( currencyRate == null ) {
            return null;
        }

        ExchangeRate exchangeRate = new ExchangeRate();

        exchangeRate.setCurrencyCode( currencyRate.getCurrencyCode() );
        exchangeRate.setCurrencyDate( currencyRate.getCurrencyDate() );
        exchangeRate.setCurrencyRate( currencyRate.getCurrencyRate() );

        return exchangeRate;
    }
}
