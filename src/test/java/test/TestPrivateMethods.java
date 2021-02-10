package test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import common.ExchangeRate;
import exception.CurrencyExchangeHttpException;

import org.powermock.api.easymock.PowerMock;

import implement.ExchangeServiceNBP;

@RunWith(PowerMockRunner.class)
public class TestPrivateMethods {
	
	@Test(expected=CurrencyExchangeHttpException.class)
    public void testReplaceData() throws Exception {
        final String modifyDataMethodName = "getResponseData";
        String url = "http://api.nbp.pl:6060/api/exchangerates/rates/A/CHF";

        URL restApiUrl = new URL(url);
    	HttpURLConnection apiConnection = (HttpURLConnection) restApiUrl.openConnection();
		apiConnection.setRequestMethod("GET");
		apiConnection.setDoOutput(true);
		apiConnection.setRequestProperty("Content-Type", "application/json");
		apiConnection.setRequestProperty("Accept", "application/json");
		apiConnection.setUseCaches(false);
		apiConnection.setConnectTimeout(5000);
		apiConnection.setReadTimeout(5000);
		ExchangeRate rate = new ExchangeRate();
		rate.setCurrencyCode("CHF");
		rate.setCurrencyDate(new Date());
		rate.setCurrencyRate(new BigDecimal("500.00"));
		
        // Mock only the modifyData method
        ExchangeServiceNBP exchangeService = PowerMock.createPartialMock(ExchangeServiceNBP.class, modifyDataMethodName);
      
        // Expect the private method call to "modifyData"
        PowerMock.expectPrivate(exchangeService, modifyDataMethodName, apiConnection).andReturn(true);

        PowerMock.replay(exchangeService);

        assertTrue(exchangeService.getRateFromApi(rate) == null);

        PowerMock.verify(exchangeService);
    }
}
