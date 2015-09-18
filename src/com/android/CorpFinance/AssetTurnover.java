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

public class AssetTurnover extends Fragment {

	public Button buttonCalculate;
	public Button buttonReset;
	public static EditText eROE;
	public static EditText eInterestExpense;
	public static EditText eCouponRateBonds;
	public static EditText eEquityMultiplier;
	public static EditText eGrossProfits;
	public static EditText eCOGSPercentSales;
	public static TextView tvMessage;
	
	FinanceCalculations oFinanceCalcs = new FinanceCalculations();
	
	public static double dROE=0;
	public static double dInterestExpense=0;
	public static double dCouponRateBonds=0;
	public static double dEquityMultiplier=0;
	public static double dGrossProfits=0;
	public static double dCOGSPercentSales=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.asset_turnover_layout, null);
	
		eROE = (EditText)view.findViewById(R.id.editTextROE);
		eInterestExpense = (EditText)view.findViewById(R.id.editTextInterestExpense);
		eCouponRateBonds = (EditText)view.findViewById(R.id.editTextCouponRateBonds);
		eEquityMultiplier = (EditText)view.findViewById(R.id.editTextEquityMultiplier);
		eGrossProfits = (EditText)view.findViewById(R.id.EditTextGrossProfits);
		eCOGSPercentSales = (EditText)view.findViewById(R.id.EditTextCOGSPercentSales);
    	buttonCalculate = (Button)view.findViewById(R.id.buttonCalculate);
    	buttonReset = (Button)view.findViewById(R.id.buttonReset);
    	tvMessage = (TextView)view.findViewById(R.id.textView);
   	
		buttonReset.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DecimalFormat df = new DecimalFormat("#.00");

				eROE.setText(df.format(12.20));
				eInterestExpense.setText(df.format(8300)) ;
				eCouponRateBonds.setText(df.format(7.70)) ;
				eEquityMultiplier.setText(df.format(2.2)) ;
				eGrossProfits.setText(df.format(1823300));
				eCOGSPercentSales.setText(df.format(55.00));
				tvMessage.setText("");
			}
		});
		
		buttonCalculate.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				if(!validateData()) {
					dROE = Double.parseDouble(eROE.getText().toString());
					dInterestExpense = Double.parseDouble(eInterestExpense.getText().toString());
					dCouponRateBonds = Double.parseDouble(eCouponRateBonds.getText().toString());
					dEquityMultiplier = Double.parseDouble(eEquityMultiplier.getText().toString());
					dGrossProfits = Double.parseDouble(eGrossProfits.getText().toString());
					dCOGSPercentSales = Double.parseDouble(eCOGSPercentSales.getText().toString());
					
					DecimalFormat df = new DecimalFormat("#.0000000");
					
					double dTotalAssetTurnover = getTotalAssetTurnover();					
					String message1 = "The Total Asset Turnover is " + 
										df.format(dTotalAssetTurnover).toString() + " ."; 
					tvMessage.setText(message1);
				}
			}
		});
    	
    	return view;
	}
	
	public boolean validateData() {	
		boolean error = false;
		
		if( !oFinanceCalcs.isNumeric(eROE.getText().toString()) ) {
			eROE.setText("Enter Numbers."); error = true;
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
	
	public static double getSales() {
		return dGrossProfits / (1-(dCOGSPercentSales/100))  ;
	}
	
	public static double getDebt() {
		return dInterestExpense / (dCouponRateBonds/100);
	}
	
	public static double getTotalAsset() {
		return (-1) * (dEquityMultiplier * getDebt()) 
						/
				(1 - dEquityMultiplier );
	}
	
	public static double getTotalAssetTurnover() {
		return getSales() / getTotalAsset();
	}
	
	
	
	
}
