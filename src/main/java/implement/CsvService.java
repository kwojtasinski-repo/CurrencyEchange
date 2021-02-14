package implement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import abstracts.CountryConverter;
import common.CountryDto;
import common.CurrencyCodesWithCountries;
import common.ExchangeRateDto;
import common.ListCurrencies;
import exception.CurrencyNotFound;
import exception.ParsingExchangeRate;
import exception.UncheckedIOException;

public class CsvService implements CountryConverter {
	File file;
	
	public CsvService(File file) {
		// TODO Auto-generated constructor stub
		this.file = file;
	}
	
	@Override
	public CountryDto getCodeByCurrencyName(String countryName) {
		// TODO Auto-generated method stub
		return getCountry(countryName);
	}
	
	private CountryDto getCountry(String countryName) {
		try {
			CsvMapper objectMapper = new CsvMapper();
			CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
	        ObjectReader reader = objectMapper.readerFor(CurrencyCodesWithCountries.class).with(bootstrapSchema);
	        MappingIterator<CurrencyCodesWithCountries> iterator = reader.readValues(file);

	        List<CurrencyCodesWithCountries> countriesWithCurrencyCodes = new ArrayList<CurrencyCodesWithCountries>();
	        iterator.forEachRemaining(countriesWithCurrencyCodes::add);
	        CurrencyCodesWithCountries countryWithCurrencyCode = countriesWithCurrencyCodes.stream().filter(t->t.getCountryName().equals(countryName.toUpperCase()) && t.getWithdrawalDate().length() == 0).findFirst().orElse(null);
	        CountryDto countryDto = new CountryDto();
	        countryDto.setCountryName(countryWithCurrencyCode.getCountryName());
	        countryDto.setCurrencyCode(countryWithCurrencyCode.getCurrencyCode());
	        return countryDto;
		} catch (FileNotFoundException e) {
			throw new CurrencyNotFound(e.getMessage());
		} catch (IOException e) {
			throw new UncheckedIOException(e.getMessage());
		}
	}
}
