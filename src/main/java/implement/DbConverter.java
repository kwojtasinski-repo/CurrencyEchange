package implement;

import abstracts.DataConverter;
import entity.CurrencyRate;

public class DbConverter  implements DataConverter {

	@Override
	public <T> CurrencyRate getCurrencyRate(T data) {
		// TODO Auto-generated method stub
		return (CurrencyRate) data;
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
