package common;

import java.math.BigDecimal;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Currencies {
	@JsonProperty("Code")
	private String code;
	@JsonProperty("Date")
	private Date date;
	@JsonProperty("Mid")
	private BigDecimal mid;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getMid() {
		return mid;
	}
	public void setMid(BigDecimal mid) {
		this.mid = mid;
	}
}
