package implement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import abstracts.CountryConverter;
import common.CurrencyCodesWithCountries;
import exception.CsvServiceException;
import exception.UncheckedIOException;

public class CsvService implements CountryConverter {
	File file;
	
	public CsvService(File file) {
		// TODO Auto-generated constructor stub
		this.file = file;
	}
	
	@Override
	public String getCodeByCountryName(String countryName) {
		return getCurrencyCode(countryName.toUpperCase());
	}
	
	@Override
	public String getCountryByCurrencyName(String currencyName) {
		return getCountry(currencyName.toUpperCase());
	}
	
	private String getCurrencyCode(String countryName) {
		try {
			CsvMapper objectMapper = new CsvMapper();
			CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
	        ObjectReader reader = objectMapper.readerFor(CurrencyCodesWithCountries.class).with(bootstrapSchema);
	        MappingIterator<CurrencyCodesWithCountries> iterator = reader.readValues(file);

	        List<CurrencyCodesWithCountries> countriesWithCurrencyCodes = new ArrayList<CurrencyCodesWithCountries>();
	        iterator.forEachRemaining(countriesWithCurrencyCodes::add);
	        CurrencyCodesWithCountries countryWithCurrencyCode = countriesWithCurrencyCodes.stream().filter(t->t.getCountryName().equals(countryName) && t.getWithdrawalDate().length() == 0).findFirst().orElse(null);
	        if(countryWithCurrencyCode == null) {
	        	throw new CsvServiceException("Check your file if " + countryName + " exists");
	        }
	        return countryWithCurrencyCode.getCountryName();
		} catch (FileNotFoundException e) {
			throw new CsvServiceException("Check if your file of location "+ file.getPath() +" exists");
		} catch (IOException e) {
			throw new UncheckedIOException(e.getMessage());
		}
	}
	
	private String getCountry(String currencyCode) {
		try {
			CsvMapper objectMapper = new CsvMapper();
			CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
	        ObjectReader reader = objectMapper.readerFor(CurrencyCodesWithCountries.class).with(bootstrapSchema);
	        MappingIterator<CurrencyCodesWithCountries> iterator = reader.readValues(file);

	        List<CurrencyCodesWithCountries> countriesWithCurrencyCodes = new ArrayList<CurrencyCodesWithCountries>();
	        iterator.forEachRemaining(countriesWithCurrencyCodes::add);
	        CurrencyCodesWithCountries countryWithCurrencyCode = countriesWithCurrencyCodes.stream().filter(t->t.getCurrencyCode().equals(currencyCode.toUpperCase()) && t.getWithdrawalDate().length() == 0).findFirst().orElse(null);
	        if(countryWithCurrencyCode == null) {
	        	throw new CsvServiceException("Check your file if " + currencyCode + " exists");
	        }
	        return countryWithCurrencyCode.getCountryName();
		} catch (FileNotFoundException e) {
			throw new CsvServiceException("Check if your file of location "+ file.getPath() +" exists");
		} catch (IOException e) {
			throw new UncheckedIOException(e.getMessage());
		}
	}
}
