package com.exchange.ExchangesRateMaven.Service.Common;

import java.math.BigDecimal;
import java.util.Date;

public class RatesJson {
	private String no;
	private Date effectiveDate;
	private BigDecimal mid;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public BigDecimal getMid() {
		return mid;
	}
	public void setMid(BigDecimal mid) {
		this.mid = mid;
	}
}
