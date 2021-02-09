package implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import abstracts.Service;
import common.ExchangeRate;
import exception.CurrencyExchangeHttpException;
import exception.UncheckedIOException;

public abstract class ExchangeServiceNBP implements Service {
	private Date lastCurrencyRateDate;
	private static final String RATE_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";
	
	public ExchangeServiceNBP(Date lastCurrencyRateDate) {
		// TODO Auto-generated constructor stub
		this.lastCurrencyRateDate = lastCurrencyRateDate;
	}
	
	@Override
	public Date getLastCurrencyRateDate() {
		// TODO Auto-generated method stub
		return lastCurrencyRateDate;
	}
	
	@Override 
	public ExchangeRate getExchangeRate(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		return getCurrencyRate(currencyCode, date);
	}
	
	public abstract ExchangeRate getCurrencyRate(String currencyCode, Date date);
	public abstract String getFormat();
	
	public String getRateFromApi(ExchangeRate rate) {
		boolean isResponse = false;
		String response = null;
		HttpURLConnection apiConnection = null;
		while(!isResponse) {
			try {
				apiConnection = getApiConnection(RATE_URL + rate.getCurrencyCode() + "/" + setDateFormat(rate.getCurrencyDate()) + "/");
				setDefaultHttpUrlConnectionSettings(apiConnection, getFormat());
				response = getResponseData(apiConnection);
				if(checkIfResponseContainsData(response)) {
					isResponse = true;
				}
				else {
					rate.setCurrencyDate(setDateDay(rate.getCurrencyDate(), -1));
				}
			} catch (Exception ex) 	{
				// TODO Auto-generated catch block
				throw new UncheckedIOException(ex.getMessage());
			}
		}
		return response;
	}
	
	private Date setDateDay(Date date, int dayOfDate) { // set Date here not in other scope
		// TODO Auto-generated method stub
		//currencyDate - day
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, dayOfDate);
		return cal.getTime();
	}
	
	private boolean checkIfResponseContainsData(String data) {
		if(data.equals("Currency rate for this day doesnt exist")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	private String setDateFormat(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
		
	private String checkResponseString(String txt) {
		System.out.println("checkResponseString = " + txt);
		if(txt.length()==0) {
			throw new CurrencyExchangeHttpException("Check url address if correct. Response from Api is null");
		} 
		if(txt.equals("404 NotFound")) {  //if(txt.contains("404 NotFound")) 
			throw new CurrencyExchangeHttpException("Check url address if correct. Response from Api is null");
		}
		if(txt.contains("Brak danych")){
			return new String("Currency rate for this day doesnt exist");
		}
		if(txt.contains("400 BadRequest")) {
			throw new CurrencyExchangeHttpException("Check url address if correct. Response from Api is null");
		}
		return txt;
	}
	
	private String getResponseData(HttpURLConnection apiConnection) { 
		try {
			int code = 0;
			code = apiConnection.getResponseCode();
			System.out.println("from API " + apiConnection + "\ncode " + code);
			System.out.println("Status " + code);
			StringBuilder response = new StringBuilder();
			if(code == 200) {
			    BufferedReader br = new BufferedReader(new InputStreamReader(apiConnection.getInputStream(), "utf-8"));
			    String responseLine = null;
			    while ((responseLine = br.readLine()) != null) {
			        response.append(responseLine.trim());
			        System.out.println(response);
			    }
			    System.out.println("Response Request:");
			    System.out.println(response.toString());
			} else if(code == 404) {
				System.out.println("im in code = 404");
				BufferedReader br = new BufferedReader(new InputStreamReader(apiConnection.getErrorStream()));
			    String responseLine = null;
			    while ((responseLine = br.readLine()) != null) {
			        response.append(responseLine.trim());
			        System.out.println(response);
			    }
			    System.out.println("Response Request:");
			    System.out.println(response.toString());
			} else {
				throw new CurrencyExchangeHttpException("Check url format and settings : " + apiConnection);
			}
			String resp = response.toString().replaceAll("\\P{Print}","");
			resp = checkResponseString(resp);
			return resp;
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			throw new CurrencyExchangeHttpException(e.getMessage());
		} catch(IOException ex) {
			throw new CurrencyExchangeHttpException(ex.getMessage());
		} catch (Exception e) {
			throw new UncheckedIOException(e.getMessage());
		}
	}
	
	private HttpURLConnection getApiConnection(String url) {  
		try {
			URL restApiUrl = new URL(url);
		    HttpURLConnection restApiURLConnection = (HttpURLConnection) restApiUrl.openConnection();
		    System.out.println("restApiUrl = " + restApiUrl);
		    System.out.println("HttpURLConnection " + restApiURLConnection);
		    return restApiURLConnection;
		} catch (IOException e) {
			throw new CurrencyExchangeHttpException(e.getMessage());
		} catch (Exception ex) 	{
			// TODO Auto-generated catch block
			throw new UncheckedIOException(ex.getMessage());
		}
	}
	
	private void setDefaultHttpUrlConnectionSettings(HttpURLConnection apiConnection, String format) {
		try {
			apiConnection.setRequestMethod("GET");
			apiConnection.setDoOutput(true);
			apiConnection.setRequestProperty("Content-Type", "application/" + format);
			apiConnection.setRequestProperty("Accept", "application/json" + format);
			apiConnection.setUseCaches(false);
			apiConnection.setConnectTimeout(5000);
			apiConnection.setReadTimeout(5000);
			System.out.println(apiConnection);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			throw new CurrencyExchangeHttpException(e.getMessage());
		} catch (Exception ex) 	{
			// TODO Auto-generated catch block
			throw new UncheckedIOException(ex.getMessage());
		}
	}
}
