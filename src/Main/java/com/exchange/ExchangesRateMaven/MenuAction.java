package com.exchange.ExchangesRateMaven;

public class MenuAction
{
	public MenuAction(int id, String name) 
	{
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
	}
	
	private int id;
	private String name;
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
}
