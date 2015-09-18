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

public class FisherInflation extends Fragment {

	public Button buttonCalculate;
	public Button buttonReset;
	public static EditText eCashFlow0;
	public static EditText eCashFlow1;
	public static EditText eCashFlow2;
	public static EditText eCashFlow3;
	public static EditText eCashFlow4;
	public static EditText eCashFlow5;
	public static EditText eCashFlow6;
	public static EditText eCashFlow7;
	public static EditText eCostOfCapital;
	public static EditText eInflationRate;
	public static TextView tvMessage;
	
	FinanceCalculations oFinanceCalcs = new FinanceCalculations();
	
	public static double dCashFlow0=0;
	public static double dCashFlow1=0;
	public static double dCashFlow2=0;
	public static double dCashFlow3=0;
	public static double dCashFlow4=0;
	public static double dCashFlow5=0;
	public static double dCashFlow6=0;
	public static double dCashFlow7=0;
	public static double dCostOfCapital=0;
	public static double dInflationRate=0;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fisher_inflation_layout, null);
	
		eCashFlow0 = (EditText)view.findViewById(R.id.editTextCashFlow0);
		eCashFlow1 = (EditText)view.findViewById(R.id.editTextCashFlow1);
		eCashFlow2 = (EditText)view.findViewById(R.id.editTextCashFlow2);
		eCashFlow3 = (EditText)view.findViewById(R.id.editTextCashFlow3);
		eCashFlow4 = (EditText)view.findViewById(R.id.EditTextCashFlow4);
		eCashFlow5 = (EditText)view.findViewById(R.id.editTextCashFlow5);
		eCashFlow6 = (EditText)view.findViewById(R.id.EditTextCashFlow6);
		eCashFlow7 = (EditText)view.findViewById(R.id.EditTextCashFlow7);
		eCostOfCapital = (EditText)view.findViewById(R.id.EditTextCostOfCapital);
		eInflationRate = (EditText)view.findViewById(R.id.EditTextInflationRate);
    	buttonCalculate = (Button)view.findViewById(R.id.buttonCalculate);
    	buttonReset = (Button)view.findViewById(R.id.buttonReset);
    	tvMessage = (TextView)view.findViewById(R.id.textView);
   	
		buttonReset.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DecimalFormat df = new DecimalFormat("#.00");

				eCashFlow0.setText(df.format(2600));
				eCashFlow1.setText(df.format(1320)) ;
				eCashFlow2.setText(df.format(1320)) ;
				eCashFlow3.setText(df.format(1320)) ;
				eCashFlow4.setText(df.format(1320));
				eCashFlow5.setText(df.format(1320));
				eCashFlow6.setText(df.format(0));
				eCashFlow7.setText(df.format(0));
				eCostOfCapital.setText(df.format(9.00)) ;
				eInflationRate.setText(df.format(7.00)) ;
				tvMessage.setText("");
			}
		});
		
		buttonCalculate.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				if(!validateData()) {
					dCashFlow0 = Double.parseDouble(eCashFlow0.getText().toString());
					dCashFlow1 = Double.parseDouble(eCashFlow1.getText().toString());
					dCashFlow2 = Double.parseDouble(eCashFlow2.getText().toString());
					dCashFlow3 = Double.parseDouble(eCashFlow3.getText().toString());
					dCashFlow4 = Double.parseDouble(eCashFlow4.getText().toString());
					dCashFlow5 = Double.parseDouble(eCashFlow5.getText().toString());
					dCashFlow6 = Double.parseDouble(eCashFlow6.getText().toString());
					dCashFlow7 = Double.parseDouble(eCashFlow7.getText().toString());
					dCostOfCapital = Double.parseDouble(eCostOfCapital.getText().toString());
					dInflationRate = Double.parseDouble(eInflationRate.getText().toString());
					
					DecimalFormat df = new DecimalFormat("#.0000000");
					
					double difference = getNPVWithInflation() - getNPV();
					
					
					String message = "NPV is " + getNPV() + "\r\n" +
									 "NPV with Inflation is" + getNPVWithInflation() +
									 "Difference is " + difference;
					
					tvMessage.setText(message );
				}
			}
		});
    	
    	return view;
	}
	
	public boolean validateData() {	
		boolean error = false;
		
		if( !oFinanceCalcs.isNumeric(eCashFlow1.getText().toString()) ) {
			eCashFlow1.setText("Enter Numbers."); error = true;
		}
//		if( !oFinanceCalcs.isNumeric(eSalariesRent.getText().toString()) ) {
//			eSalariesRent.setText("Enter Numbers."); error = true;
//		}
//		if( !oFinanceCalcs.isNumeric(eDepreciation.getText().toString()) ) {
//			eDepreciation.setText("Enter Numbers."); error = true;
//		}
//		if( !oFinanceCalcs.isNumeric(eEBIT.getText().toString()) ) {
//			eEBIT.setText("Enter Numbers."); error = true;
//		}
//		if( !oFinanceCalcs.isNumeric(eInventoryRatio.getText().toString()) ) {
//			eInventoryRatio.setText("Enter Numbers."); error = true;
//		}
		
		return error;
	}
	
	public static double getNPV() {
		double dNPV = - dCashFlow0 + 
					   	dCashFlow1/Math.pow((1+dCostOfCapital/100), 1) +
					   	dCashFlow2/Math.pow((1+dCostOfCapital/100), 2) +
					   	dCashFlow3/Math.pow((1+dCostOfCapital/100), 3) +
					   	dCashFlow4/Math.pow((1+dCostOfCapital/100), 4) +
					   	dCashFlow5/Math.pow((1+dCostOfCapital/100), 5) +
					   	dCashFlow6/Math.pow((1+dCostOfCapital/100), 6) +
					   	dCashFlow7/Math.pow((1+dCostOfCapital/100), 7);
		return dNPV;
	}
	
	public static double getNPVWithInflation() {
		double dNPV = - dCashFlow0 + 
					   	(dCashFlow1*Math.pow((1+dInflationRate/100), 0))/Math.pow((1+dCostOfCapital/100), 1) +
					   	(dCashFlow2*Math.pow((1+dInflationRate/100), 1))/Math.pow((1+dCostOfCapital/100), 2) +
					   	(dCashFlow3*Math.pow((1+dInflationRate/100), 2))/Math.pow((1+dCostOfCapital/100), 3) +
					   	(dCashFlow4*Math.pow((1+dInflationRate/100), 3))/Math.pow((1+dCostOfCapital/100), 4) +
					   	(dCashFlow5*Math.pow((1+dInflationRate/100), 4))/Math.pow((1+dCostOfCapital/100), 5) +
					   	(dCashFlow6*Math.pow((1+dInflationRate/100), 5))/Math.pow((1+dCostOfCapital/100), 6) +
					   	(dCashFlow7*Math.pow((1+dInflationRate/100), 6))/Math.pow((1+dCostOfCapital/100), 7);
		return dNPV;
	}
	
	
}
