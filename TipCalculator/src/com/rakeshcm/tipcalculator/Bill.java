package com.rakeshcm.tipcalculator;

import java.util.ArrayList;

public class Bill {
	private String description;
	private String billAmount;
	private String tipAmount;
	private String totalAmount;
	
	public Bill(String description, String billAmount, String tipAmount, String totalAmount) {
		this.setBillAmount(billAmount);
		this.setDescription(description);
		this.setTipAmount(tipAmount);
		this.setTotalAmount(totalAmount);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}

	public String getTipAmount() {
		return tipAmount;
	}

	public void setTipAmount(String tipAmount) {
		this.tipAmount = tipAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public static ArrayList<Bill> getFakeBills() {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		bills.add(new Bill("bamboo garden", "500", "50", "550"));
		bills.add(new Bill("PF changs", "400", "40", "440"));
		return bills;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return description + ", " + billAmount + ", " + tipAmount + ", " + totalAmount;
	}
	
}
