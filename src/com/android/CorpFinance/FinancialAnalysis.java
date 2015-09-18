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

public class FinancialAnalysis extends Fragment {

	public Button buttonCalculate;
	public Button buttonReset;
	public static EditText eCOGS;
	public static EditText eSalariesRent;
	public static EditText eDepreciation;
	public static EditText eEBIT;
	public static EditText eInventoryRatio;
	public static EditText eCurrentAssets;
	public static EditText eTotalAssets;
	public static EditText eLongTermDebt;
	public static EditText eStEquity;
	public static TextView tvMessage;
	
	FinanceCalculations oFinanceCalcs = new FinanceCalculations();
	
	public static double dCOGS =0;
	public static double dSalariesRent=0;
	public static double dDepreciation=0;
	public static double dEBIT=0;
	public static double dInventoryRatio=0;
	public static double dCurrentAssets=0;
	public static double dTotalAssets=0;
	public static double dLongTermDebt=0;
	public static double dStEquity=0;
	
	public static double dSales = 0;
	public static double dGrossProfitMargin = 0;
	public static double dInventory = 0;
	public static double dCurrentLiablities = 0;
	public static double dQuickRatio = 0;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.financial_analysis, null);
	
		eCOGS = (EditText)view.findViewById(R.id.editTextCOGS);
		eSalariesRent = (EditText)view.findViewById(R.id.editTextSalariesRent);
		eDepreciation = (EditText)view.findViewById(R.id.editTextDepreciation);
		eEBIT = (EditText)view.findViewById(R.id.editTextEBIT);
		eInventoryRatio = (EditText)view.findViewById(R.id.EditTextInventoryRatio);
		eCurrentAssets = (EditText)view.findViewById(R.id.EditTextCurrentAssets);
		eTotalAssets = (EditText)view.findViewById(R.id.EditTextTotalAssets);
		eLongTermDebt = (EditText)view.findViewById(R.id.EditTextLongTermDebt);
		eStEquity = (EditText)view.findViewById(R.id.EditTextStEquity);
    	buttonCalculate = (Button)view.findViewById(R.id.buttonCalculate);
    	buttonReset = (Button)view.findViewById(R.id.buttonReset);
    	tvMessage = (TextView)view.findViewById(R.id.textView);
   	
		buttonReset.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DecimalFormat df = new DecimalFormat("#.00");
				
				eCOGS.setText(df.format(233340)) ;
				eSalariesRent.setText(df.format(185550)) ;
				eDepreciation.setText(df.format(33800)) ;
				eEBIT.setText(df.format(203200));
				eInventoryRatio.setText(df.format(7.1));
				eCurrentAssets.setText(df.format(3099060)) ;
				eTotalAssets.setText(df.format(4123460)) ;
				eLongTermDebt.setText(df.format(1003320));
				eStEquity.setText(df.format(1409330));
				tvMessage.setText("");
			}
		});
		
		buttonCalculate.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				if(!validateData()) {
					dCOGS = Double.parseDouble(eCOGS.getText().toString());
					dSalariesRent = Double.parseDouble(eSalariesRent.getText().toString());
					dDepreciation = Double.parseDouble(eDepreciation.getText().toString());
					dEBIT = Double.parseDouble(eEBIT.getText().toString());
					dInventoryRatio = Double.parseDouble(eInventoryRatio.getText().toString());
					dCurrentAssets = Double.parseDouble(eCurrentAssets.getText().toString());
					dTotalAssets = Double.parseDouble(eTotalAssets.getText().toString());
					dLongTermDebt = Double.parseDouble(eLongTermDebt.getText().toString());
					dStEquity = Double.parseDouble(eStEquity.getText().toString());
					
					DecimalFormat df = new DecimalFormat("#.0000000");
					
					dSales = getSales();
					dGrossProfitMargin =((dSales - dCOGS)
											  /
										 dSales ) ;
					
					dInventory = dSales/dInventoryRatio;
					dCurrentLiablities = dTotalAssets - dLongTermDebt - dStEquity ;
					
					dQuickRatio = ( (dCurrentAssets - dInventory)
										/
								   dCurrentLiablities );
					
					
					String message = "Gross Profit margin is " + 
									 df.format(dGrossProfitMargin).toString() + 
									 "and Quick Ratio is " + 
									 df.format(dQuickRatio).toString();
					tvMessage.setText(message);
				}
			}
		});
    	
    	return view;
	}
	
	public boolean validateData() {	
		boolean error = false;
		
		if( !oFinanceCalcs.isNumeric(eCOGS.getText().toString()) ) {
			eCOGS.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eSalariesRent.getText().toString()) ) {
			eSalariesRent.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eDepreciation.getText().toString()) ) {
			eDepreciation.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eEBIT.getText().toString()) ) {
			eEBIT.setText("Enter Numbers."); error = true;
		}
		if( !oFinanceCalcs.isNumeric(eInventoryRatio.getText().toString()) ) {
			eInventoryRatio.setText("Enter Numbers."); error = true;
		}
		
		return error;
	}
	
	public double getSales() {
		return dEBIT + dCOGS + dSalariesRent + dDepreciation;
	}
}
