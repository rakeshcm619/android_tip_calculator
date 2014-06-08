package com.rakeshcm.tipcalculator;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BillArrayAdapter extends ArrayAdapter<Bill> {
	public BillArrayAdapter(Context context, ArrayList<Bill> bills) {
        super(context, R.layout.item_bills, bills);
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
    	 Bill bill = getItem(position);    
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bills, parent, false);
        }
        // Lookup view for data population
        TextView tvItemDescription = (TextView) convertView.findViewById(R.id.tvItemDescription);
        TextView tvItemBillAmount = (TextView) convertView.findViewById(R.id.tvItemBillAmount);
        TextView tvItemTipAmount = (TextView) convertView.findViewById(R.id.tvItemTipAmount);
        TextView tvItemTotalAmount = (TextView) convertView.findViewById(R.id.tvItemTotalAmount);
        // Populate the data into the template view using the data object
        tvItemDescription.setText(bill.getDescription());
        tvItemBillAmount.setText(bill.getBillAmount());
        tvItemTipAmount.setText(bill.getTipAmount());
        tvItemTotalAmount.setText(bill.getTotalAmount());
        // Return the completed view to render on screen
        return convertView;
    }
}
