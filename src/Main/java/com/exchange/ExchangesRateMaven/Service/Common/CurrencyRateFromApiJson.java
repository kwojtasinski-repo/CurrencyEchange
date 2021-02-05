package com.exchange.ExchangesRateMaven.Service.Common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.exchange.ExchangesRateMaven.Domain.Entity.Currency;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.exchange.ExchangesRateMaven.Service.Abstract.Adaptee;
import com.exchange.ExchangesRateMaven.Service.Common.Format;

public class CurrencyRateFromApiJson implements Adaptee {
	private Date currencyDate;
	private String currencyCode;
	private Format format;
	private Date lastArchiveCurrencyRate = new Date(1009926000000L); 
	
	public CurrencyRateFromApiJson(Date lastArchiveCurrencyRate) {
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
	public String getDataAsString(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		return getRateFromApi(currencyCode, Format.JSON, date);
	}
	
	private String getRateFromApi(String currencyCode, Format format, Date date) {
		HttpURLConnection apiConnection = null;
		BigDecimal rate = null;
		String response=null;
		this.format = format;
		try {
			//checkDate(date);
			apiConnection = getApiConnection("A", currencyCode, setDateFormat(date));
			setDefaultHttpUrlConnectionSettings(apiConnection, format);
			response = getResponseData(apiConnection);
			//String response = getResponse(apiConnection);
			//rate = getCurrencyRateFromResponse(response, format);
		} catch (IOException ex) 	{
			// TODO Auto-generated catch block
			System.out.println(ex.getMessage());
		} catch (Exception ex) 	{
			// TODO Auto-generated catch block
			System.out.println(ex.getMessage());
		}
	
		return response;
	}
	
	private void checkDate(Date date) throws Exception {
		Date lastArchivalCurrencyRateDate = new Date(1009926000000L);
		Date currentDate = new Date();
		if(date.before(lastArchivalCurrencyRateDate)) {
			throw new Exception("Date cannot be before 2 January 2002");
		}
		if(date.after(new Date())) {
			throw new Exception("Date cannot be after " + setDateFormat(currentDate));
		}
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
		if(txt.contains("zakres dat")) {
			return new String("");
		}
		return txt;
	}
	
	private BigDecimal getCurrencyRateFromResponse(String txt, Format format) throws Exception {
		// TODO Auto-generated method stub
		return format.equals(Format.JSON) ? getCurrencyRateFromResponseJson(txt) : getCurrencyRateFromResponseXml(txt);
	}

	private BigDecimal getCurrencyRateFromResponseXml(String txt) throws Exception {
		// TODO Auto-generated method stub
		BigDecimal rate = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
	    Document document = null;
		try {
			builder = factory.newDocumentBuilder();
			InputStream inputStream = new ByteArrayInputStream(txt.getBytes());
		    document = builder.parse(inputStream);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		System.out.println(root.getNodeName());

		//all currencies
		NodeList nList = document.getElementsByTagName("Rate");
		for (int i = 0; i < nList.getLength(); i++){
			Node node = nList.item(i);
			System.out.println("");    //Just a separator
			if (node.getNodeType() == Node.ELEMENT_NODE){
			    //Print each employee's detail
			    Element element = (Element) node;
			    rate = new BigDecimal(element.getElementsByTagName("Mid").item(0).getTextContent());
			    System.out.println("Mid : " + rate);
			}
		}
		if(rate == null) {
			throw new Exception("Check xml format : " + txt);
		}
		
		return rate;
	}

	private BigDecimal getCurrencyRateFromResponseJson(String txt) throws Exception {
		// TODO Auto-generated method stub
		JSONObject requestFromApi = null;
		BigDecimal rate = null;
		requestFromApi = new JSONObject(txt);
		System.out.println(txt);
		System.out.println(requestFromApi);
		
		try {
			System.out.println(requestFromApi.getJSONArray("rates").getJSONObject(0));
			rate = new BigDecimal(requestFromApi.getJSONArray("rates").getJSONObject(0).get("mid").toString());
		} catch (JSONException e)	{
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		if(rate == null) {
			throw new Exception("Check json format : " + txt);
		}
		
		return rate;
	}
	
	private String getResponse(HttpURLConnection apiConnection) throws Exception {
		boolean gotResponse = false;
		String response = null;
		System.out.println(apiConnection.getRequestProperties());
		while(!gotResponse) {
			response = getResponseData(apiConnection);
			//gotResponse = checkResponseString(response);
			currencyDate = new Date(currencyDate.getTime() - Duration.ofDays(1).toMillis());
			apiConnection = getApiConnection("A", currencyCode, setDateFormat(currencyDate));
			setDefaultHttpUrlConnectionSettings(apiConnection, this.format);
		}
		return response;
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
}
