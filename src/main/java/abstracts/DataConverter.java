package abstracts;

import entity.CurrencyRate;

public interface DataConverter {
	<T> CurrencyRate getCurrencyRate(T data);
	String getFormat();
}
