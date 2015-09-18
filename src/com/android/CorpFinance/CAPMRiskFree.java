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

public class CAPMRiskFree extends Fragment {

	public Button buttonCalculate;
	public Button buttonReset;
	public static EditText eShares1;
	public static EditText ePrice1;
	public static EditText eReturn1;
	public static EditText eShares2;
	public static EditText ePrice2;
	public static EditText eReturn2;
	public static EditText eShares3;
	public static EditText ePrice3;
	public static EditText eReturn3;
	public static EditText ePremium;
	public static EditText eBeta;
	public static TextView tvMessage;
	
	FinanceCalculations oFinanceCalcs = new FinanceCalculations();
	
	public static double dShares1;
	public static double dPrice1;
	public static double dReturn1;
	
	public static double dShares2;
	public static double dPrice2;
	public static double dReturn2;
	
	public static double dShares3;
	public static double dPrice3;
	public static double dReturn3;
	
	public static double dPremium;
	public static double dBeta;

	public static double dTotalWorth = 0;
	public static double dPortionExpextedReturn = 0;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.capm_riskfree, null);
	
		eShares1 = (EditText)view.findViewById(R.id.editTextShare1);
		ePrice1 = (EditText)view.findViewById(R.id.editTextPrice1);
		eReturn1 = (EditText)view.findViewById(R.id.editTextReturn1);
		
		eShares2 = (EditText)view.findViewById(R.id.EditTextShares2);
		ePrice2 = (EditText)view.findViewById(R.id.EditTextPrice2);
		eReturn2 = (EditText)view.findViewById(R.id.EditTextReturn2);
		
		eShares3 = (EditText)view.findViewById(R.id.EditTextShares3);
		ePrice3 = (EditText)view.findViewById(R.id.EditTextPrice3);
		eReturn3 = (EditText)view.findViewById(R.id.EditTextReturn3);
		
		ePremium = (EditText)view.findViewById(R.id.editTextPremium);
		eBeta = (EditText)view.findViewById(R.id.editTextBeta);
    	
		buttonCalculate = (Button)view.findViewById(R.id.buttonCalculate);
    	buttonReset = (Button)view.findViewById(R.id.buttonReset);
    	tvMessage = (TextView)view.findViewById(R.id.textView);
   	
		buttonReset.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DecimalFormat df = new DecimalFormat("#.00");
				
				eShares1.setText(df.format(560)) ;
				ePrice1.setText(df.format(4.00)) ;
				eReturn1.setText(df.format(16.50)) ;
				
				eShares2.setText(df.format(560)) ;
				ePrice2.setText(df.format(7.00)) ;
				eReturn2.setText(df.format(10.5)) ;
				
				eShares3.setText(df.format(600)) ;
				ePrice3.setText(df.format(7.00)) ;
				eReturn3.setText(df.format(10.4)) ;
				
				ePremium.setText(df.format(4.40));
				eBeta.setText(df.format(1.64));
				tvMessage.setText("");
			}
		});
		
		buttonCalculate.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				if(!validateData()) {
					dShares1 = Double.parseDouble(eShares1.getText().toString());
					dPrice1 = Double.parseDouble(ePrice1.getText().toString());
					dReturn1 = Double.parseDouble(eReturn1.getText().toString());
					
					dShares2 = Double.parseDouble(eShares2.getText().toString());
					dPrice2 = Double.parseDouble(ePrice2.getText().toString());
					dReturn2 = Double.parseDouble(eReturn2.getText().toString());
					
					dShares3 = Double.parseDouble(eShares3.getText().toString());
					dPrice3 = Double.parseDouble(ePrice3.getText().toString());
					dReturn3 = Double.parseDouble(eReturn3.getText().toString());
					
					dPremium = Double.parseDouble(ePremium.getText().toString());
					dBeta = Double.parseDouble(eBeta.getText().toString());
					
					DecimalFormat df = new DecimalFormat("#.0000000");
					
					dTotalWorth = dShares1 * dPrice1 +
								  dShares2 * dPrice2 + 
								  dShares3 * dPrice3 ;
					
					dPortionExpextedReturn = 
							(getPortion(dShares1,dPrice1) * dReturn1) +
							(getPortion(dShares2,dPrice2) * dReturn2) +
							(getPortion(dShares3,dPrice3) * dReturn3) ;
							
							
					double dRiskFreeRate = 0;
					dRiskFreeRate = dPortionExpextedReturn - (dBeta*dPremium);
					
					
					String message = "Risk Free Rate is " + 
									 df.format(dRiskFreeRate).toString() + " %.";
					tvMessage.setText(message);
				}
			}
		});
    	
    	return view;
	}
	
	public static double getPortion(double dShares, double dPrice) {
		return (dShares * dPrice) / (dTotalWorth);
	}
	
	public boolean validateData() {	
		boolean error = false;
		
		if( !oFinanceCalcs.isNumeric(eShares1.getText().toString()) ) {
			eShares1.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(ePrice1.getText().toString()) ) {
			ePrice1.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eReturn1.getText().toString()) ) {
			eReturn1.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(ePremium.getText().toString()) ) {
			ePremium.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eBeta.getText().toString()) ) {
			eBeta.setText("Enter Numbers."); error = true;
		}
		
		return error;
	}

}
