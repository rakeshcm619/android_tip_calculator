package com.rakeshcm.tipcalculator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class BillCalculateActivity extends Activity {
	ArrayList<Bill> bills;
	EditText etBillAmount;
	TextView tvTipPercentNumber;
	TextView tvTipAmountNumber;
	TextView tvTotalAmountNumber;
	TextView tvMessage;
	EditText etDescription;
	
	SeekBar sbTipPercent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_calculate);
		readItems();
		init();
		
		setListener();
	}
	
	private void init() {
		etBillAmount = (EditText) findViewById(R.id.etBillAmount);
		tvTipPercentNumber = (TextView) findViewById(R.id.tvTipPercentNumber);
		tvTipAmountNumber = (TextView) findViewById(R.id.tvTipAmountNumber);
		tvTotalAmountNumber = (TextView) findViewById(R.id.tvTotalAmountNumber);
		tvMessage = (TextView) findViewById(R.id.tvMessage);
		etDescription = (EditText) findViewById(R.id.etDescriptionText);
		
		sbTipPercent = (SeekBar) findViewById(R.id.sbTipPercent);
	}
	
	private void setListener() {
		etBillAmount.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.toString().equalsIgnoreCase("")) {
					tvMessage.setText("Enter the Bill Amount");
					tvTipPercentNumber.setText("0.0");
					tvTipAmountNumber.setText("0.00");
					sbTipPercent.setProgress(0);
				}
				tvTotalAmountNumber.setText(s);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		sbTipPercent.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				Double billAmount = 0.0;
				int currentProgress = progress;
				try {
					billAmount = Double.parseDouble(etBillAmount.getText().toString());
					tvMessage.setText("");
				}
				catch(NumberFormatException ex) {
					tvMessage.setText("Invalid Bill Amount");
					sbTipPercent.setProgress(0);
					tvTipPercentNumber.setText("0.0");
					tvTipAmountNumber.setText("0.00");
					currentProgress = 0;
				}
				
				Double tipPercent =  ((double)currentProgress/(double)100);
				tvTipPercentNumber.setText(tipPercent.toString());
				
				Double tipAmount = billAmount * (tipPercent/100);
				tipAmount = ((double)Math.round(tipAmount*100)/100.00);
				tvTipAmountNumber.setText(tipAmount.toString());
				
				Double totalAmount = tipAmount + billAmount;
				totalAmount = ((double)Math.round(totalAmount*100)/100.00);
				tvTotalAmountNumber.setText(totalAmount.toString());
				
				
			}
		});
	}
	
	public void cancelBillAction(View v) {
		setResult(RESULT_CANCELED);
		finish();
	}
	
	public void saveBill(View v) {
		if(etDescription.getText().toString().equalsIgnoreCase("") || etBillAmount.getText().toString().equalsIgnoreCase("")) {
			tvMessage.setText("Invalid Bill Amount or Description");
		}
		else {
			Bill newItem = new Bill(etDescription.getText().toString(), etBillAmount.getText().toString(), 
									tvTipAmountNumber.getText().toString(), tvTotalAmountNumber.getText().toString());
			bills.add(newItem);
			saveItems();
	    	setResult(RESULT_OK);
			finish();
		}
	}
	
	private void readItems() {
    	File fileDir = getFilesDir();
    	File todoFile = new File(fileDir, "tipcalc.txt");
    	ArrayList<String> jsonBills = new ArrayList<String>();
    	
    	try {
    		jsonBills = new ArrayList<String>(FileUtils.readLines(todoFile));
    		if(bills == null) {
    			bills = deStringify(jsonBills);
    		}
    		else {
    			bills.clear();
    			bills.addAll(deStringify(jsonBills));
    		}
    	}
    	catch (IOException ex) {
    		if(bills == null) {
    			bills = new ArrayList<Bill>();
    		}
    		else {
    			bills.clear();
    		}
    		ex.printStackTrace();
    	}
    }
	
    private void saveItems() {
    	File fileDir = getFilesDir();
    	File todoFile = new File(fileDir, "tipcalc.txt");
    	try {
    		ArrayList<String> jsonBills = stringifyBills(bills);
    		FileUtils.writeLines(todoFile, jsonBills);
    	}
    	catch (IOException ex) {
    		ex.printStackTrace();
    	}
    }
    
	private ArrayList<Bill> deStringify(ArrayList<String> jsonBills) {
		ArrayList<Bill> readBills = new ArrayList<Bill>();
		Gson fromJsonBills = new Gson();
		for (String eachBill : jsonBills) {
			readBills.add(fromJsonBills.fromJson(eachBill, Bill.class));
		}
		return readBills;
	}
	
	private ArrayList<String> stringifyBills(ArrayList<Bill> objectBills) {
		ArrayList<String> jsonBills = new ArrayList<String>();
		Gson toJsonBills = new Gson();
		for (Bill eachBill : objectBills) {
			jsonBills.add(toJsonBills.toJson(eachBill));
		}
		return jsonBills;
	}
}
