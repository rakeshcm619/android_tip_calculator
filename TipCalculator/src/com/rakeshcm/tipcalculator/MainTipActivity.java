package com.rakeshcm.tipcalculator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainTipActivity extends Activity {
	private final int BILL_REQUEST_CODE = 20;
	BillArrayAdapter billArrayAdapter;
	ArrayList<Bill> bills;
	ListView lvBills;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_tip);
		
		//bills = Bill.getFakeBills();
		readItems();
		billArrayAdapter = new BillArrayAdapter(this, bills);
		
		lvBills = (ListView) findViewById(R.id.lvBills);
		lvBills.setAdapter(billArrayAdapter);
		
		setupListViewListener();
	}
	
	private void setupListViewListener() {
		lvBills.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				bills.remove(position);
				billArrayAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
    }
	
	public void startTipCalculator(View v) {
		Intent i = new Intent(this, BillCalculateActivity.class);
		startActivityForResult(i, BILL_REQUEST_CODE);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO: add checks for result if necessary
		if (requestCode == BILL_REQUEST_CODE) {
			readItems();
			billArrayAdapter.notifyDataSetChanged();
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
