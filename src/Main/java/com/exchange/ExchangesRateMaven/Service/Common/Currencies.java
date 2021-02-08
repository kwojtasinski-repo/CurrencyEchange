package com.exchange.ExchangesRateMaven.Service.Common;

import java.math.BigDecimal;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Currencies {
	@JsonProperty("Code")
	private String Code;
	@JsonProperty("Date")
	private Date Date;
	@JsonProperty("Mid")
	private BigDecimal Mid;
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public BigDecimal getMid() {
		return Mid;
	}
	public void setMid(BigDecimal mid) {
		Mid = mid;
	}
}
