package com.android.CorpFinance;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.slidingmenu.FinanceCalculations;
import com.android.slidingmenu.R;

public class Payback extends Fragment {

	public Button buttonCalculate;
	public Button buttonReset;
	public static EditText ePaybackPeriod;
	public static EditText eCashFlow0;
	public static EditText eCashFlow1;
	public static EditText eCashFlow2;
	public static TextView tvMessage;
	
	FinanceCalculations oFinanceCalcs = new FinanceCalculations();
	
	public static double dPaybackPeriod =0;
	public static double dCashFlow0=0;
	public static double dCashFlow1=0;
	public static double dCashFlow2=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.payback_layout, null);
	
		ePaybackPeriod = (EditText)view.findViewById(R.id.editTextPaybackPeriod);
		eCashFlow0 = (EditText)view.findViewById(R.id.editTextCF0);
		eCashFlow1 = (EditText)view.findViewById(R.id.editTextCF1);
		eCashFlow2 = (EditText)view.findViewById(R.id.editTextCF2);
    	buttonCalculate = (Button)view.findViewById(R.id.buttonCalculate);
    	buttonReset = (Button)view.findViewById(R.id.buttonReset);
    	tvMessage = (TextView)view.findViewById(R.id.textView);
   	
		buttonReset.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DecimalFormat df = new DecimalFormat("#.00");
				
				ePaybackPeriod.setText(df.format(2.34)) ;
				eCashFlow0.setText(df.format(214)) ;
				eCashFlow1.setText(df.format(53)) ;
				eCashFlow2.setText(df.format(76));
				tvMessage.setText("");
			}
		});
		
		buttonCalculate.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				if(!validateData()) {
					dPaybackPeriod = Double.parseDouble(ePaybackPeriod.getText().toString());
					dCashFlow0 = Double.parseDouble(eCashFlow0.getText().toString());
					dCashFlow1 = Double.parseDouble(eCashFlow1.getText().toString());
					dCashFlow2 = Double.parseDouble(eCashFlow2.getText().toString());
					
					DecimalFormat df = new DecimalFormat("#.000");
					
					double output = 0;
					output = (Math.abs(dCashFlow0) - (dCashFlow1 + dCashFlow2))
									/
							 (dPaybackPeriod - 2.0);
					
					String message = "Your next cash flow should be " + 
									 df.format(output).toString();
					tvMessage.setText(message);
				}
			}
		});
    	
    	return view;
	}
	
	public boolean validateData() {	
		boolean error = false;
		
		if( !oFinanceCalcs.isNumeric(ePaybackPeriod.getText().toString()) ) {
			ePaybackPeriod.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eCashFlow0.getText().toString()) ) {
			eCashFlow0.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eCashFlow1.getText().toString()) ) {
			eCashFlow1.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eCashFlow2.getText().toString()) ) {
			eCashFlow2.setText("Enter Numbers."); error = true;
		}
		
		return error;
	}
}
