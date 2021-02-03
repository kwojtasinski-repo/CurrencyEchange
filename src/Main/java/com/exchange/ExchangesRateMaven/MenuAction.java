package com.exchange.ExchangesRateMaven;

public class MenuAction
{
	public MenuAction(int id, String name, String category) 
	{
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.category = category;
	}
	
	private int id;
	private String name;
	private String category;
	
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
