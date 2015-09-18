package com.android.slidingmenu;

import java.text.DecimalFormat;

import com.android.slidingmenu.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class NetWorth extends Fragment {

	public Button buttonCalculate;
	public Button buttonReset;
	public static EditText eTotalIncome;
	public static EditText eTotalExpenses;
	public static EditText eTotalLoans;
	public static EditText eNetWorth;
	public static TextView tvNetWorth;
	public static TextView tvMessage;
	
	FinanceCalculations oFinanceCalcs = new FinanceCalculations();
	
	public static double dTotalIncome =0;
	public static double dTotalExpense=0;
	public static double dTotalLoans=0;
	public static double dNetWorth=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout3, null);
	
		eTotalIncome = (EditText)view.findViewById(R.id.editTextTotalIncome);
    	eTotalExpenses = (EditText)view.findViewById(R.id.editTextTotalExpenses);
    	eTotalLoans = (EditText)view.findViewById(R.id.editTextTotalLoans);
    	eNetWorth = (EditText)view.findViewById(R.id.editTextNetWorth);
    	tvNetWorth = (TextView)view.findViewById(R.id.textViewNetWorth);
    	buttonCalculate = (Button)view.findViewById(R.id.bCalculateNetWorth);
    	buttonReset = (Button)view.findViewById(R.id.bResetNetWorth);
    	tvMessage = (TextView)view.findViewById(R.id.textViewMessageNetWorth);
   	
		buttonReset.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				double valueOutput = 0;
				DecimalFormat df = new DecimalFormat("#.00");
				
				eTotalIncome.setText(df.format(valueOutput)) ;
				eTotalExpenses.setText(df.format(valueOutput)) ;
				eTotalLoans.setText(df.format(valueOutput)) ;
				eNetWorth.setText(df.format(valueOutput));
				hideOutputText(eNetWorth, tvNetWorth);
				tvMessage.setText("");
			}
		});
		
		buttonCalculate.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				if(!validateData()) {
					dTotalIncome = Double.parseDouble(eTotalIncome.getText().toString());
					dTotalExpense = Double.parseDouble(eTotalExpenses.getText().toString());
					dTotalLoans = Double.parseDouble(eTotalLoans.getText().toString());
					dNetWorth = Double.parseDouble(eNetWorth.getText().toString());
					
					DecimalFormat df = new DecimalFormat("#.000");
					showOutputText(eNetWorth, tvNetWorth);
					
					dNetWorth = oFinanceCalcs.calculateNetWorth(dTotalIncome, dTotalExpense, dTotalLoans);
					eNetWorth.setText(df.format(dNetWorth).toString());
					
					String message = "Your current Net Worth is " + 
									 df.format(dNetWorth).toString();
					tvMessage.setText(message);
				}
			}
		});
    	
    	return view;
	}
	
	public static void hideOutputText (EditText givenET, TextView givenTV) {
		givenET.setVisibility(View.GONE);
		givenTV.setVisibility(View.GONE);
	}
	
	public static void showOutputText (EditText givenET, TextView givenTV) {
		givenET.setVisibility(View.VISIBLE);
		givenTV.setVisibility(View.VISIBLE);
	}
	
	public boolean validateData() {	
		boolean error = false;
		
		if( !oFinanceCalcs.isNumeric(eTotalIncome.getText().toString()) ) {
			eTotalIncome.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eTotalExpenses.getText().toString()) ) {
			eTotalExpenses.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eTotalLoans.getText().toString()) ) {
			eTotalLoans.setText("Enter Numbers."); error = true;
		}
		
		return error;
	}
}
