package implement;

import java.util.Date;

import common.ExchangeRate;

public class ServiceNBPJson extends ServiceNBP {

	@Override
	protected String getFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ExchangeRate getExchangeRateFromApi(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		ServiceNBP s = new ServiceNBPJson();
		s.getLastCurrencyRateDate();
	}
}
