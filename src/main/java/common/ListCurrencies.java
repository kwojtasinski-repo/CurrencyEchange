package common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ListCurrencies extends JsonDeserializer<List<Currencies>> {
	@JsonProperty("Currencies")
	private List<Currencies> currencies;
	
	@Override
	public List<Currencies> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Currencies> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currencies> currencies) {
		this.currencies = currencies;
	}

	public Currencies getCurrency(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		Currencies currency = null;
		for(Currencies  cur : this.currencies) {
			if(cur.getCode().toUpperCase().equals(currencyCode.toUpperCase()) && isSameDay(cur.getDate(), date)) {
				currency = cur;
			}
		}
		return currency;
	}
	
	public boolean isSameDay(Date date1, Date date2) {
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	    return fmt.format(date1).equals(fmt.format(date2));
	}
}
