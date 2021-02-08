package com.exchange.ExchangesRateMaven.Service.Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.exchange.ExchangesRateMaven.Service.Abstract.Service;

public class CurrencyRateFromApiXml  implements Service {
	private Date currencyDate;
	private String currencyCode;
	private Format format;
	private Date lastArchiveCurrencyRate = new Date(1009926000000L); 
	
	public CurrencyRateFromApiXml(Date lastArchiveCurrencyRate) {
		// TODO Auto-generated constructor stub
		this.lastArchiveCurrencyRate = lastArchiveCurrencyRate;
	}
	
	public BigDecimal getCurrencyRate(String currencyCode, Date date) {
		this.currencyDate = date;
		this.currencyCode = currencyCode;
		//return getRateFromApi(currencyCode, Format.JSON, date);
		return null;
	}
	
	@Override
	public Date getLastArchiveCurrencyRate() {
		// TODO Auto-generated method stub
		return lastArchiveCurrencyRate;
	}
	
	@Override
	public String getData(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		return getRateFromApi(currencyCode, Format.XML, date);
	}
	
	private String getRateFromApi(String currencyCode, Format format, Date date) {
		HttpURLConnection apiConnection = null;
		String response=null;
		this.format = format;
		try {
			apiConnection = getApiConnection("A", currencyCode, setDateFormat(date));
			setDefaultHttpUrlConnectionSettings(apiConnection, format);
			response = getResponseData(apiConnection);
		} catch (IOException ex) 	{
			// TODO Auto-generated catch block
			System.out.println(ex.getMessage());
		} catch (Exception ex) 	{
			// TODO Auto-generated catch block
			System.out.println(ex.getMessage());
		}
	
		return response;
	}
	
	private String setDateFormat(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	private String checkResponseString(String txt) throws Exception {
		System.out.println("checkResponseString = " + txt);
		if(txt.length()==0) {
			throw new Exception("Check url address if correct. Response from Api is null");
		} 
		if(txt.equals("404 NotFound")) {  //if(txt.contains("404 NotFound")) 
			throw new Exception("Check url address if correct. Response from Api is null");
		}
		if(txt.contains("Brak danych")){
		//if(!txt.contains("<?xml version")  || !txt.contains("{")) {
			System.out.println("zawiera Brak danych");
			return new String("");
		}
		if(txt.contains("400 BadRequest")) {
			throw new Exception("Check url address if correct. Response from Api is null");
		}
		return txt;
	}
	
	private String getResponseData(HttpURLConnection apiConnection) throws Exception {
		int code = 0;
		try {
			code = apiConnection.getResponseCode();
			System.out.println("from API " + apiConnection + "\ncode " + code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Status " + code);
		StringBuilder response = new StringBuilder();
		if(code == 200) {
			try	{
			    BufferedReader br = new BufferedReader(new InputStreamReader(apiConnection.getInputStream(), "utf-8"));
			    String responseLine = null;
			    while ((responseLine = br.readLine()) != null) {
			        response.append(responseLine.trim());
			        System.out.println(response);
			    }
			    System.out.println("Response Request:");
			    System.out.println(response.toString());
			} catch(IOException ex) {
				System.out.println(ex.getMessage());
				//ex.printStackTrace();
			}
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
			throw new Exception("Check url format and settings : " + apiConnection);
		}
		String resp = response.toString().replaceAll("\\P{Print}","");
		resp = checkResponseString(resp);
		return resp;
	}
	
	private HttpURLConnection getApiConnection(String table, String code, String date) throws IOException {  
	    URL restApiUrl = new URL("http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + code + "/" + date + "/");
	    HttpURLConnection restApiURLConnection = (HttpURLConnection) restApiUrl.openConnection();
	    System.out.println("restApiUrl = " + restApiUrl);
	    System.out.println("HttpURLConnection " + restApiURLConnection);
	    return restApiURLConnection;
	}
	
	private void setDefaultHttpUrlConnectionSettings(HttpURLConnection apiConnection, Format format) throws ProtocolException {
		apiConnection.setRequestMethod("GET");
		apiConnection.setDoOutput(true);
		apiConnection.setRequestProperty("Content-Type", "application/" + format);
		apiConnection.setRequestProperty("Accept", "application/json" + format);
		apiConnection.setUseCaches(false);
		apiConnection.setConnectTimeout(15000);
		apiConnection.setReadTimeout(15000);
		System.out.println(apiConnection);
		//return apiConnection;
	}

	@Override
	public Format getFormat() {
		// TODO Auto-generated method stub
		return this.format;
	}
}
