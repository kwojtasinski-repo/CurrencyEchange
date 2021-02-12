package abstracts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import common.ExchangeRate;
import common.ExchangedCurrency;
import entity.CurrencyExchange;
import entity.CurrencyRate;

@Mapper
public interface CurrencyExchangeMapper {

	CurrencyExchangeMapper INSTANCE = Mappers.getMapper(CurrencyExchangeMapper.class);
	
    @Mapping(target = "id", ignore = true)
	CurrencyExchange mapToCurrencyExchange(ExchangedCurrency currencyExchange);
    
    ExchangedCurrency mapToExchangedCurrency(CurrencyExchange currencyExchange);
    
    @Mapping(target = "id", ignore = true)
    CurrencyRate mapToCurrencyRate(ExchangeRate currencyRate);
    
    ExchangeRate mapToExchangeRate(CurrencyRate currencyRate);
}