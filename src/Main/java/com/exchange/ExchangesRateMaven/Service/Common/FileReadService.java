package com.exchange.ExchangesRateMaven.Service.Common;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import com.exchange.ExchangesRateMaven.Service.Abstract.Service;

public class FileReadService implements Service {
	private Date lastArchivalCurrencyDate;
	private Format format;
	private String path;
	
	public FileReadService(String path, Format format,Date lastArchivalCurrencyDate) {
		// TODO Auto-generated constructor stub
		this.format = format;
		this.lastArchivalCurrencyDate = lastArchivalCurrencyDate;
		this.path = path;
	}
	
	@Override
	public String getData(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		return readFile(currencyCode, date);
	}
	
	private String readFile(String currencyCode, Date date) {
		StringBuilder sb = new StringBuilder();
		try {
		      File myObj = new File(this.path);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        data = data.replaceAll("[\n\r]", data);
		        sb.append(data);
		        System.out.println(data);
		      }
		      myReader.close();
		    } catch (Exception e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String string = "2021-02-05";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = new Date(1000L);
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileReadService service = new FileReadService("C:\\Projects\\ExchangesRateMaven\\src\\main\\java\\com\\exchange\\ExchangesRateMaven\\Service\\Implement\\CurrencyJson.json", Format.JSON, date);
		System.out.println(service.getData("", date));
	}

	@Override
	public Date getLastArchiveCurrencyRate() {
		// TODO Auto-generated method stub
		return this.lastArchivalCurrencyDate;
	}

	@Override
	public Format getFormat() {
		// TODO Auto-generated method stub
		return this.format;
	}

}
